package com.jake.sns.domain.like.repository;

import com.jake.sns.domain.like.entity.LikeEntity;
import com.jake.sns.domain.post.entity.PostEntity;
import com.jake.sns.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    Optional<LikeEntity> findByUserAndPost(UserEntity user, PostEntity post);
    Integer countAllByPost(PostEntity post);
//    @Query(value = "SELECT COUNT(*) FROM like entity WHERE entity.post = :post")
//    Integer countByPost(@Param("post") PostEntity post);

//    List<LikeEntity> findAllByPost(PostEntity post);
}
