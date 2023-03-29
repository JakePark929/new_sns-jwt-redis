package com.jake.sns.domain.alarm.event;

import com.jake.sns.constant.AlarmType;
import com.jake.sns.domain.alarm.AlarmArgs;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// UserEntity userEntity, AlarmType alarmType, AlarmArgs alarmArgs

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlarmEvent {
    private Long receiveUserId;
    private AlarmType alarmType;
    private AlarmArgs alarmArgs;
}
