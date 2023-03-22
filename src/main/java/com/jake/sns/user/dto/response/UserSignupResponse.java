package com.jake.sns.user.dto.response;

import com.jake.sns.constant.UserRole;
import com.jake.sns.user.dto.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSignupResponse {
    private Long id;
    private String username;
    private UserRole role;

    public static UserSignupResponse fromUser(User user) {
        return new UserSignupResponse(
                user.getId(),
                user.getUsername(),
                user.getRole()
        );
    }
}
