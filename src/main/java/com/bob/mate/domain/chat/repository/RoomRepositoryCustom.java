package com.bob.mate.domain.chat.repository;

import com.bob.mate.domain.chat.entity.Room;

import java.util.Optional;

public interface RoomRepositoryCustom {

    Optional<Room> findByHostIdAndGuestId(Long hostId, Long guestId);

}
