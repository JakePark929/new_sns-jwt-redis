package com.jake.sns.domain.user.cache;

import com.jake.sns.domain.user.dto.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Repository
public class UserCacheRepository {
    private final RedisTemplate<String, User> userRedisTemplate;
    private final static Duration USER_CACHE_TTL = Duration.ofDays(3);

    public Optional<User> getUser(String username) {
        String key = getKey(username);
        User user = userRedisTemplate.opsForValue().get(key);
        log.info("Get User from Redis {} , {}", key, user);
        userRedisTemplate.opsForValue().get(key);
        return Optional.ofNullable(user);
    }

    public void setUser(User user) {
        String key = getKey(user.getUsername());
        log.info("Set User to Redis {} , {}", key, user);
//        userRedisTemplate.opsForValue().setIfAbsent(key, user, USER_CACHE_TTL);
        userRedisTemplate.opsForValue().set(key, user, USER_CACHE_TTL);
    }

    private String getKey(String username) {
        return "USER:" + username;
    }
}
