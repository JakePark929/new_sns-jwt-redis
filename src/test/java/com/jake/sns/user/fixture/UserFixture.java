package com.jake.sns.user.fixture;

import com.jake.sns.user.entity.User;

public class UserFixture {
    public static User get(String username, String password) {
        User result = new User();
        result.setId(1L);
        result.setUsername(username);
        result.setPassword(password);

        return result;
    }
}
