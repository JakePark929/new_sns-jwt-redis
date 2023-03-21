package com.jake.sns.user.controller;

import com.jake.sns.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    // TODO: implement
    @PostMapping
    public void signUp() {
        userService.signUp("", "");
    }
}
