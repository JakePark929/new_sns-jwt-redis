package com.jake.sns.domain.user.controller;

import com.jake.sns.common.response.CommonResponse;
import com.jake.sns.domain.alarm.dto.response.AlarmResponse;
import com.jake.sns.domain.user.dto.User;
import com.jake.sns.domain.user.dto.request.UserLoginRequest;
import com.jake.sns.domain.user.dto.request.UserSignUpRequest;
import com.jake.sns.domain.user.dto.response.UserLoginResponse;
import com.jake.sns.domain.user.dto.response.UserSignupResponse;
import com.jake.sns.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public CommonResponse<UserLoginResponse> login(@RequestBody UserLoginRequest request) {
        log.info("getUsername: {}, getPassword: {}", request.getUsername(), request.getPassword());
        String token = userService.login(request.getUsername(), request.getPassword());
        return CommonResponse.success(new UserLoginResponse(token));
    }

    @PostMapping("/sign-up")
    public CommonResponse<UserSignupResponse> signUp(@RequestBody UserSignUpRequest request) {
        User user = userService.signUp(request.getUsername(), request.getPassword());
        return CommonResponse.success(UserSignupResponse.fromUser(user));
    }

    @GetMapping("/alarm")
    public CommonResponse<Page<AlarmResponse>> alarm(Pageable pageable, Authentication authentication) {
        return CommonResponse.success(userService.alarmList(authentication.getName(), pageable)
                .map(AlarmResponse::fromAlarm));
    }
}
