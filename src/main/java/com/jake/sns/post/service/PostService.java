package com.jake.sns.post.service;

import com.jake.sns.exception.ErrorCode;
import com.jake.sns.exception.SnsApplicationException;
import com.jake.sns.post.entity.PostEntity;
import com.jake.sns.post.repository.PostRepository;
import com.jake.sns.user.entity.UserEntity;
import com.jake.sns.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public void create(String title, String body, String username) {
        // user find
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() ->
                new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", username)));

        // post save
        PostEntity saved = postRepository.save(PostEntity.of(title, body, userEntity));

        // return
    }

    @Transactional
    public void modify(String title, String body, String username, Long postId) {
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() ->
                new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", username)));

        // post exist

        // post permission
    }
}
