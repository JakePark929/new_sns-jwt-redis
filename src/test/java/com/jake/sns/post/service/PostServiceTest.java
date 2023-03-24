package com.jake.sns.post.service;

import com.jake.sns.exception.ErrorCode;
import com.jake.sns.exception.SnsApplicationException;
import com.jake.sns.fixture.PostEntityFixture;
import com.jake.sns.fixture.UserEntityFixture;
import com.jake.sns.post.entity.PostEntity;
import com.jake.sns.post.repository.PostRepository;
import com.jake.sns.user.entity.UserEntity;
import com.jake.sns.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PostServiceTest {
    @Autowired
    private PostService postService;

    @MockBean
    private PostRepository postRepository;
    @MockBean
    private UserRepository userRepository;

    @Test
    void 피드목록요청이_성공한경우() {
        Pageable pageable = mock(Pageable.class);
        when(postRepository.findAll(pageable)).thenReturn(Page.empty());

        Assertions.assertDoesNotThrow(() -> postService.list(pageable));
    }

    @Test
    void 내피드목록요청이_성공한경우() {
        Pageable pageable = mock(Pageable.class);
        UserEntity user = mock(UserEntity.class);

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(postRepository.findAllByUser(user, pageable)).thenReturn(Page.empty());

        Assertions.assertDoesNotThrow(() -> postService.my("", pageable));
    }

    @Test
    void 포스트작성이_성공한경우() {
        String title = "title";
        String body = "body";
        String username = "username";

        // mocking
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(mock(UserEntity.class)));
        when(postRepository.save(any())).thenReturn(mock(PostEntity.class));

        Assertions.assertDoesNotThrow(() -> postService.create(title, body, username));
    }

    @Test
    void 포스트작성시_요청한유저가_존재하지않는경우() {
        String title = "title";
        String body = "body";
        String username = "username";

        // mocking
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(postRepository.save(any())).thenReturn(mock(PostEntity.class));

        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class, () -> postService.create(title, body, username));
        Assertions.assertEquals(ErrorCode.USER_NOT_FOUND, e.getErrorCode());
    }

    @Test
    void 포스트수정이_성공한경우() {
        String title = "title";
        String body = "body";
        String username = "username";
        Long postId = 1L;

        PostEntity postEntity = PostEntityFixture.get(username, postId, 1L);
        UserEntity userEntity = postEntity.getUser();

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(postRepository.findById(postId)).thenReturn(Optional.of(postEntity));
        when(postRepository.saveAndFlush(any())).thenReturn(postEntity);

        Assertions.assertDoesNotThrow(() -> postService.modify(title, body, username, postId));
    }

    @Test
    void 포스트수정시_포스트가_존재하지않는_경우() {
        String title = "title";
        String body = "body";
        String username = "username";
        Long postId = 1L;

        PostEntity postEntity = PostEntityFixture.get(username, postId, 1L);
        UserEntity userEntity = postEntity.getUser();

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class, () -> postService.modify(title, body, username, postId));
        Assertions.assertEquals(ErrorCode.POST_NOT_FOUND, e.getErrorCode());
    }

    @Test
    void 포스트수정시_권한이_없는_경우() {
        String title = "title";
        String body = "body";
        String username = "username";
        Long postId = 1L;

        PostEntity postEntity = PostEntityFixture.get(username, postId, 1L);
        UserEntity writer = UserEntityFixture.get("username1", "password", 2L);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(writer));
        when(postRepository.findById(postId)).thenReturn(Optional.of(postEntity));

        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class, () -> postService.modify(title, body, username, postId));
        Assertions.assertEquals(ErrorCode.INVALID_PERMISSION, e.getErrorCode());
    }

    @Test
    void 포스트삭제가_성공한경우() {
        String username = "username";
        Long postId = 1L;

        PostEntity postEntity = PostEntityFixture.get(username, postId, 1L);
        UserEntity userEntity = postEntity.getUser();

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(postRepository.findById(postId)).thenReturn(Optional.of(postEntity));

        Assertions.assertDoesNotThrow(() -> postService.delete(username, 1L));
    }

    @Test
    void 포스트삭제시_포스트가_존재하지않는_경우() {
        String username = "username";
        Long postId = 1L;

        PostEntity postEntity = PostEntityFixture.get(username, postId, 1L);
        UserEntity userEntity = postEntity.getUser();

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class, () -> postService.delete(username, 1L));
        Assertions.assertEquals(ErrorCode.POST_NOT_FOUND, e.getErrorCode());
    }

    @Test
    void 포스트삭제시_권한이_없는_경우() {
        String username = "username";
        Long postId = 1L;

        PostEntity postEntity = PostEntityFixture.get(username, postId, 1L);
        UserEntity writer = UserEntityFixture.get("username1", "password", 2L);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(writer));
        when(postRepository.findById(postId)).thenReturn(Optional.of(postEntity));

        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class, () -> postService.delete(username, 1L));
        Assertions.assertEquals(ErrorCode.INVALID_PERMISSION, e.getErrorCode());
    }
}
