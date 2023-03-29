package com.jake.sns.domain.alarm.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
public class EmitterRepository {
    private final Map<String, SseEmitter> emitterMap = new HashMap<>();
    
    // 다중 인스턴스일때는 전체 서버에 전달해 사용자를 확인해야함
    
    public Optional<SseEmitter> get(Long userId) {
        final String key = getKey(userId);
        log.info("Get sseEmitter {}", userId);
        return Optional.ofNullable(emitterMap.get(key));
    }

    public SseEmitter save(Long userId, SseEmitter sseEmitter) {
        final String key = getKey(userId);
        emitterMap.put(key, sseEmitter);
        log.info("Set sseEmitter {}", userId);
        return sseEmitter;
    }

    public void delete(Long userId) {
        emitterMap.remove(getKey(userId));
    }

    private String getKey(Long userId) {
        return "Emitter:UID: " + userId;
    }
}
