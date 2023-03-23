package com.jake.sns.user.controller;

import com.jake.sns.common.response.CommonResponse;
import com.jake.sns.user.dto.User;
import com.jake.sns.user.dto.request.UserLoginRequest;
import com.jake.sns.user.dto.request.UserSignUpRequest;
import com.jake.sns.user.dto.response.UserLoginResponse;
import com.jake.sns.user.dto.response.UserSignupResponse;
import com.jake.sns.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public CommonResponse<UserSignupResponse> signUp(@RequestBody UserSignUpRequest request) {
        User user = userService.signUp(request.getUsername(), request.getPassword());
        return CommonResponse.success(UserSignupResponse.fromUser(user));
    }

    @PostMapping("/login")
    public CommonResponse<UserLoginResponse> login(@RequestBody UserLoginRequest request) {
        String token = userService.login(request.getUsername(), request.getPassword());
        return CommonResponse.success(new UserLoginResponse(token));
    }
}
