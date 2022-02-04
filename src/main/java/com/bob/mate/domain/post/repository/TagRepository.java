package com.bob.mate.domain.post.repository;

import com.bob.mate.domain.post.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
