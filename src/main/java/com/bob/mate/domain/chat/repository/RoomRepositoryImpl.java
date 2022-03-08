package com.bob.mate.domain.chat.repository;

import com.bob.mate.domain.chat.entity.QRoom;
import com.bob.mate.domain.chat.entity.Room;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.bob.mate.domain.chat.entity.QRoom.*;

@RequiredArgsConstructor
public class RoomRepositoryImpl implements RoomRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Room> findByHostIdAndGuestId(Long hostId, Long guestId) {
                return Optional.ofNullable(queryFactory
                        .selectFrom(room)
                        .where(room.host.id.eq(hostId).and(room.guest.id.eq(guestId)))
                        .fetchOne());
    }

}
