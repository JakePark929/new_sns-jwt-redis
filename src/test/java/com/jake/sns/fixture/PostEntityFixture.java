package com.jake.sns.fixture;

import com.jake.sns.post.entity.PostEntity;
import com.jake.sns.user.entity.UserEntity;

public class PostEntityFixture {
    public static PostEntity get(String username, Long postId) {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUsername(username);

        PostEntity result = new PostEntity();
        result.setUser(user);
        result.setId(postId);

        return result;
    }
}
