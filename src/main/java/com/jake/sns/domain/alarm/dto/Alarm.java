package com.jake.sns.domain.alarm.dto;

import com.jake.sns.constant.AlarmType;
import com.jake.sns.domain.alarm.AlarmArgs;
import com.jake.sns.domain.alarm.entity.AlarmEntity;
import com.jake.sns.domain.user.dto.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;

@Slf4j
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Alarm {
    private Long id;
//    private User user;
    private AlarmType alarmType;
    private AlarmArgs alarmArgs;
    private Timestamp registeredAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;

    public static Alarm fromEntity(AlarmEntity entity) {
//        log.info("==== call alarm from entity");
        return new Alarm(
                entity.getId(),
//                User.fromEntity(entity.getUser()),
                entity.getAlarmType(),
                entity.getAlarmArgs(),
                entity.getRegisteredAt(),
                entity.getUpdatedAt(),
                entity.getDeletedAt()
        );
    }
}
