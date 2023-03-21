package com.jake.sns.user.service;

import com.jake.sns.exception.SnsApplicationException;
import com.jake.sns.user.entity.User;
import com.jake.sns.user.fixture.UserFixture;
import com.jake.sns.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void 회원가입이_정상적으로_동작하는_경우() {
        String username = "username";
        String password = "password";

        User fixture = UserFixture.get(username, password);

        // mocking
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(Optional.of(fixture));

        Assertions.assertDoesNotThrow(() -> userService.signUp(username, password));
    }

    @Test
    void 회원가입시_username_으로_회원가입한_유저가_이미_있는경우() {
        String username = "username";
        String password = "password";

        User fixture = UserFixture.get(username, password);

        // mocking
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(Optional.of(fixture));

        Assertions.assertThrows(SnsApplicationException.class, () -> userService.signUp(username, password));
    }

    @Test
    void 로그인이_정상적으로_동작하는_경우() {
        String username = "username";
        String password = "password";

        User fixture = UserFixture.get(username, password);

        // mocking
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(fixture));

        Assertions.assertDoesNotThrow(() -> userService.login(username, password));
    }

    @Test
    void 로그인시_username_으로_회원가입한_유저가_없는경우() {
        String username = "username";
        String password = "password";

        // mocking
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        Assertions.assertThrows(SnsApplicationException.class, () -> userService.login(username, password));
    }

    @Test
    void 로그인시_password_가_틀린_경우() {
        String username = "username";
        String password = "password";
        String wrongPassword = "wrongPassword";

        User fixture = UserFixture.get(username, wrongPassword);

        // mocking
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(fixture));

        Assertions.assertThrows(SnsApplicationException.class, () -> userService.login(username, password));
    }
}
