package com.bob.mate.domain.chat.repository;

import com.bob.mate.domain.chat.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
