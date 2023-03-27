package com.jake.sns.domain.post.service;

import com.jake.sns.domain.comment.dto.Comment;
import com.jake.sns.domain.comment.entity.CommentEntity;
import com.jake.sns.domain.comment.repository.CommentRepository;
import com.jake.sns.domain.post.entity.PostEntity;
import com.jake.sns.domain.post.repository.PostRepository;
import com.jake.sns.exception.ErrorCode;
import com.jake.sns.exception.SnsApplicationException;
import com.jake.sns.domain.like.entity.LikeEntity;
import com.jake.sns.domain.like.repository.LikeRepository;
import com.jake.sns.domain.post.dto.Post;
import com.jake.sns.domain.user.entity.UserEntity;
import com.jake.sns.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public Page<Post> list(Pageable pageable) {
        return postRepository.findAll(pageable).map(Post::fromEntity);
    }

    @Transactional(readOnly = true)
    public Page<Post> my(String username, Pageable pageable) {
        // user exist
        UserEntity userEntity = getUserOrException(username);

        return postRepository.findAllByUser(userEntity, pageable).map(Post::fromEntity);
    }

    @Transactional
    public void create(String title, String body, String username) {
        // user exist
        UserEntity userEntity = getUserOrException(username);

        // post save
        PostEntity saved = postRepository.save(PostEntity.of(title, body, userEntity));

        // return
    }

    @Transactional
    public Post modify(String title, String body, String username, Long postId) {
        // user exist
        UserEntity userEntity = getUserOrException(username);
        // post exist
        PostEntity postEntity = getPostOrException(postId);

        // post permission
        if (postEntity.getUser() != userEntity) {
            throw new SnsApplicationException(ErrorCode.INVALID_PERMISSION, String.format("%s has no permission with %s", username, postId));
        }

        postEntity.setTitle(title);
        postEntity.setBody(body);

        return Post.fromEntity(postRepository.saveAndFlush(postEntity));
    }

    @Transactional
    public void delete(String username, Long postId) {
        // user exist
        UserEntity userEntity = getUserOrException(username);
        // post exist
        PostEntity postEntity = getPostOrException(postId);

        // post permission
        if (postEntity.getUser() != userEntity) {
            throw new SnsApplicationException(ErrorCode.INVALID_PERMISSION, String.format("%s has no permission with %s", username, postId));
        }

        postRepository.delete(postEntity);
    }

    @Transactional
    public void like(Long postId, String username) {
        // user exist
        UserEntity userEntity = getUserOrException(username);
        // post exist
        PostEntity postEntity = getPostOrException(postId);

        // check liked -> throw
         likeRepository.findByUserAndPost(userEntity, postEntity).ifPresent(it -> {
             throw new SnsApplicationException(ErrorCode.ALREADY_LIKED, String.format("username %s already like post %d", username, postId));
         });

        // like save
        likeRepository.save(LikeEntity.of(userEntity, postEntity));
    }

    @Transactional(readOnly = true)
    public Integer likeCount(Long postId) {
        // post exist
        PostEntity postEntity = getPostOrException(postId);

        // count
//        List<LikeEntity> likeEntities = likeRepository.findAllByPost(postEntity);
//        return likeEntities.size();

        return likeRepository.countAllByPost(postEntity);
    }

    @Transactional(readOnly = true)
    public Page<Comment> getComments(Long postId, Pageable pageable) {
        // post exist
        PostEntity postEntity = getPostOrException(postId);
        return commentRepository.findAllByPost(postEntity, pageable).map(Comment::fromEntity);
    }

    @Transactional
    public void comment(Long postId, String username, String context) {
        // user exist
        UserEntity userEntity = getUserOrException(username);
        // post exist
        PostEntity postEntity = getPostOrException(postId);

        // comment save
        commentRepository.save(CommentEntity.of(userEntity, postEntity, context));
    }

    // user exist
    private UserEntity getUserOrException(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", username)));
    }

    // post exist
    private PostEntity getPostOrException(Long postId) {
        return postRepository.findById(postId).orElseThrow(() ->
                new SnsApplicationException(ErrorCode.POST_NOT_FOUND, String.format("%s not fonded", postId)));
    }
}
