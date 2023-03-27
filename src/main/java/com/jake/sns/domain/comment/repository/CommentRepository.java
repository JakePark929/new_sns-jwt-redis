package com.jake.sns.domain.comment.repository;

import com.jake.sns.domain.comment.entity.CommentEntity;
import com.jake.sns.domain.like.entity.LikeEntity;
import com.jake.sns.domain.post.entity.PostEntity;
import com.jake.sns.domain.user.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    Page<CommentEntity> findAllByPost(PostEntity post, Pageable pageable);
}
