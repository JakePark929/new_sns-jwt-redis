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

        PostEntity postEntity = PostEntityFixture.get(username, postId);
        UserEntity userEntity = postEntity.getUser();

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(postRepository.findById(postId)).thenReturn(Optional.of(postEntity));

        Assertions.assertDoesNotThrow(() -> postService.modify(title, body, username, postId));
    }

    @Test
    void 포스트수정시_포스트가_존재하지않는_경우() {
        String title = "title";
        String body = "body";
        String username = "username";
        Long postId = 1L;

        PostEntity postEntity = PostEntityFixture.get(username, postId);
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

        PostEntity postEntity = PostEntityFixture.get(username, postId);
        UserEntity writer = UserEntityFixture.get("username1", "password");

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(writer));
        when(postRepository.findById(postId)).thenReturn(Optional.of(postEntity));

        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class, () -> postService.modify(title, body, username, postId));
        Assertions.assertEquals(ErrorCode.INVALID_PERMISSION, e.getErrorCode());
    }
}
