package com.jake.sns.fixture;

import com.jake.sns.user.entity.UserEntity;

public class UserEntityFixture {
    public static UserEntity get(String username, String password) {
        UserEntity result = new UserEntity();
        result.setId(1L);
        result.setUsername(username);
        result.setPassword(password);

        return result;
    }
}
