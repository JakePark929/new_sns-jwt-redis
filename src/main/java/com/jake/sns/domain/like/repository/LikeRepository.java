package com.jake.sns.domain.like.repository;

import com.jake.sns.domain.like.entity.LikeEntity;
import com.jake.sns.domain.post.entity.PostEntity;
import com.jake.sns.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    Optional<LikeEntity> findByUserAndPost(UserEntity user, PostEntity post);
    Integer countAllByPost(PostEntity post);

    @Transactional
    @Modifying
    @Query(value = "UPDATE \"like\" SET deleted_at = NOW() WHERE post_id = :postId", nativeQuery = true)
    void deleteAllByPost(@Param("postId") Long postId);
//    @Query(value = "SELECT COUNT(*) FROM like entity WHERE entity.post = :post")
//    Integer countByPost(@Param("post") PostEntity post);

//    List<LikeEntity> findAllByPost(PostEntity post);
}
