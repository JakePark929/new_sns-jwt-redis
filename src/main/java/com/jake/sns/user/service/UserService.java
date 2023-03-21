package com.jake.sns.user.service;

import com.jake.sns.exception.SnsApplicationException;
import com.jake.sns.user.entity.User;
import com.jake.sns.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    // TODO: implement
    public User signUp(String username, String password) {
        // 회원가입하려는 username 으로 회원가입된 user 가 있는지
        Optional<User> userEntity =  userRepository.findByUsername(username);

        // 회원가입 진행 = user 를 등록
        userRepository.save(new User());

        return new User();
    }

    // TODO: implement
    public String login(String username, String password) {
        // 회원가입 여부 체크
        User userEntity = userRepository.findByUsername(username).orElseThrow(SnsApplicationException::new);

        // 비밀번호 체크
        if (!userEntity.getPassword().equals(password)) {
            throw new SnsApplicationException();
        }

        // 토큰 생성

        return "";
    }
}
