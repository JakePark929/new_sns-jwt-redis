package com.jake.sns.post.repository;

import com.jake.sns.post.entity.PostEntity;
import com.jake.sns.user.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    Page<PostEntity> findAllByUser(UserEntity entity, Pageable pageable);
}
