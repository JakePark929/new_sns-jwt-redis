package com.jake.sns.domain.user.service;

import com.jake.sns.common.util.JwtTokenUtils;
import com.jake.sns.domain.alarm.dto.Alarm;
import com.jake.sns.domain.alarm.repository.AlarmRepository;
import com.jake.sns.domain.user.cache.UserCacheRepository;
import com.jake.sns.domain.user.dto.User;
import com.jake.sns.domain.user.entity.UserEntity;
import com.jake.sns.exception.ErrorCode;
import com.jake.sns.exception.SnsApplicationException;
import com.jake.sns.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final AlarmRepository alarmRepository;
    private final UserCacheRepository userCacheRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMs;

    public String login(String username, String password) {
        // 회원가입 여부 체크
//        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() ->
//                new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", username)));
        
        // redis 적용
        User user = loadUserByUsername(username);
        userCacheRepository.setUser(user);
        
        // 비밀번호 체크
//        if (!userEntity.getPassword().equals(password)) {
//        if (!encoder.matches(password, userEntity.getPassword())) {
        if (!encoder.matches(password, user.getPassword())) {
            throw new SnsApplicationException(ErrorCode.INVALID_PASSWORD);
        }

        // 토큰 생성
        String token = JwtTokenUtils.generateToken(username, secretKey, expiredTimeMs);

        return token;
    }

    @Transactional
    public User signUp(String username, String password) {
        // 회원가입하려는 username 으로 회원가입된 user 가 있는지
        userRepository.findByUsername(username).ifPresent(it -> {
            throw new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, String.format("%s is duplicated", username));
        });

        // 회원가입 진행 = user 를 등록
        UserEntity userEntity = userRepository.save(UserEntity.of(username, encoder.encode(password)));

        return User.fromEntity(userEntity);
    }

//    public Page<Alarm> alarmList(String username, Pageable pageable) {
//        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", username)));
//        return alarmRepository.findAllByUser(userEntity, pageable).map(Alarm::fromEntity);
//    }
    public Page<Alarm> alarmList(Long userId, Pageable pageable) {
        return alarmRepository.findAllByUserId(userId, pageable).map(Alarm::fromEntity);
    }

    public User loadUserByUsername(String username) {
        // redis 적용
        return userCacheRepository.getUser(username).orElseGet(() ->
                userRepository.findByUsername(username).map(User::fromEntity).orElseThrow(() ->
                        new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", username))));

//        return userRepository.findByUsername(username).map(User::fromEntity).orElseThrow(() ->
//                new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", username)));
    }
}
