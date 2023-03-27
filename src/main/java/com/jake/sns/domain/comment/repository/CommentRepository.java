package com.jake.sns.domain.comment.repository;

import com.jake.sns.domain.comment.entity.CommentEntity;
import com.jake.sns.domain.post.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    Page<CommentEntity> findAllByPost(PostEntity post, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "UPDATE comment SET deleted_at = NOW() WHERE post_id = :postId", nativeQuery = true)
    void deleteAllByPost(@Param("postId") Long postId); // 가지고와서 삭제할 필요가 없음..
}
