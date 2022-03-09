package com.bob.mate.domain.chat.repository;

import com.bob.mate.domain.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String>, ChatRoomRepositoryCustom {
    Optional<ChatRoom> findByRoomSubscribeId(String roomSubscribeId);
}
