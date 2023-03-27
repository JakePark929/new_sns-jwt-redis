package com.jake.sns.domain.post.repository;

import com.jake.sns.domain.post.entity.PostEntity;
import com.jake.sns.domain.user.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    Page<PostEntity> findAllByUser(UserEntity entity, Pageable pageable);
}
