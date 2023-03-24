package com.jake.sns.fixture;

import com.jake.sns.user.entity.UserEntity;

public class UserEntityFixture {
    public static UserEntity get(String username, String password, Long userId) {
        UserEntity result = new UserEntity();
        result.setId(userId);
        result.setUsername(username);
        result.setPassword(password);

        return result;
    }
}
