package com.jake.sns.fixture;

import com.jake.sns.domain.post.entity.PostEntity;
import com.jake.sns.domain.user.entity.UserEntity;

public class PostEntityFixture {
    public static PostEntity get(String username, Long postId, Long userId) {
        UserEntity user = new UserEntity();
        user.setId(userId);
        user.setUsername(username);

        PostEntity result = new PostEntity();
        result.setUser(user);
        result.setId(postId);

        return result;
    }
}
