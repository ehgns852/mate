package com.bob.mate.domain.chat.repository;

import com.bob.mate.domain.chat.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Notice, Long> {
}
