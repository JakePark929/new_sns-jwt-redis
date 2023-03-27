package com.jake.sns.domain.alarm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlarmArgs {
    // 알람을 발생시킨 사람
    private Long fromUserId;
    // 알람주체의 id
    private Long targetId;

//    private List<Long> targetIds;
//    private Long alarmOccurId;
}

// comment: 00씨가 새 코엔트를 작성했습니다 -> postId, commentId
// 00외 2명이 새 코메느를 작성했습니다. -> commentId, commentId