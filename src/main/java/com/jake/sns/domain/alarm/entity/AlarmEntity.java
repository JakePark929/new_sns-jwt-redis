package com.jake.sns.domain.alarm.entity;

import com.jake.sns.constant.AlarmType;
import com.jake.sns.domain.alarm.AlarmArgs;
import com.jake.sns.domain.post.entity.PostEntity;
import com.jake.sns.domain.user.entity.UserEntity;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
@Table(name = "\"alarm\"", indexes = {
        @Index(name = "user_id_idx", columnList = "user_id")
})
@SQLDelete(sql = "UPDATE \"alarm\" SET deleted_at = NOW() where id=?")
@Where(clause = "deleted_at is NULL")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Entity
public class AlarmEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 알람을 받은사람
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;

    @Type(type = "jsonb") // json, jsonb - jsonb 는 압축 및 인덱스 제공
    @Column(columnDefinition = "json")
    private AlarmArgs alarmArgs;

    @Column(name = "registered_at")
    private Timestamp registeredAt;
    @Column(name = "updated_at")
    private Timestamp updatedAt;
    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    @PrePersist
    void registeredAt() {
        this.registeredAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }

    public static AlarmEntity of(UserEntity userEntity, AlarmType alarmType, AlarmArgs alarmArgs) {
        AlarmEntity entity = new AlarmEntity();
        entity.setUser(userEntity);
        entity.setAlarmType(alarmType);
        entity.setAlarmArgs(alarmArgs);
        return entity;
    }
}
