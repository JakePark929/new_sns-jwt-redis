package com.jake.sns.domain.comment.dto;

import com.jake.sns.domain.comment.entity.CommentEntity;
import com.jake.sns.domain.post.entity.PostEntity;
import com.jake.sns.domain.user.dto.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private Long id;
    private String context;
    private String username;
    private Long postId;

    private Timestamp registeredAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;

    public static Comment fromEntity(CommentEntity entity) {
        return new Comment(
                entity.getId(),
                entity.getContext(),
                entity.getUser().getUsername(),
                entity.getPost().getId(),
                entity.getRegisteredAt(),
                entity.getUpdatedAt(),
                entity.getDeletedAt()
        );
    }
}
