package com.jake.sns.domain.alarm.service;

import com.jake.sns.constant.AlarmType;
import com.jake.sns.domain.alarm.AlarmArgs;
import com.jake.sns.domain.alarm.dto.Alarm;
import com.jake.sns.domain.alarm.entity.AlarmEntity;
import com.jake.sns.domain.alarm.repository.AlarmRepository;
import com.jake.sns.domain.alarm.repository.EmitterRepository;
import com.jake.sns.constant.ErrorCode;
import com.jake.sns.domain.user.dto.response.UserResponse;
import com.jake.sns.domain.user.entity.UserEntity;
import com.jake.sns.domain.user.repository.UserRepository;
import com.jake.sns.exception.SnsApplicationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class AlarmService {
    private final EmitterRepository emitterRepository;
    private final AlarmRepository alarmRepository;
    private final UserRepository userRepository;

    private final static Long DEFAULT_TIMEOUT = 60L * 1000 * 60;
    private final static String ALARM_NAME = "alarm";

    public SseEmitter connectAlarm(Long userId) {
        SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);
        emitterRepository.save(userId, sseEmitter);
        sseEmitter.onCompletion(() -> emitterRepository.delete(userId));
        sseEmitter.onTimeout(() -> emitterRepository.delete(userId));

        try {
            sseEmitter.send(SseEmitter.event().id("").name(ALARM_NAME).data("connect completed"));
        } catch (IOException e) {
            throw new SnsApplicationException(ErrorCode.ALARM_CONNECT_ERROR);
        }

        return sseEmitter;
    }

//    public void send(Long alarmId, Long userId) {
    public void send(AlarmType type, AlarmArgs args, Long receiveUserId) {
        UserEntity userEntity = userRepository.findById(receiveUserId).orElseThrow(() -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND));

        // alarm event save
        AlarmEntity alarmEntity = alarmRepository.save(AlarmEntity.of(userEntity, type, args));

        emitterRepository.get(receiveUserId).ifPresentOrElse(sseEmitter -> {
            try {
                sseEmitter.send(SseEmitter.event().id(alarmEntity.getId().toString()).name(ALARM_NAME).data("new alarm"));
            } catch (IOException e) {
                emitterRepository.delete(receiveUserId);
                throw new SnsApplicationException(ErrorCode.ALARM_CONNECT_ERROR);
            }
        }, () -> log.info("No emitter founded"));
    }
}
