package com.bob.mate.domain.chat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoomIdResponse {

    private Long roomId;

    public RoomIdResponse(Long roomId) {
        this.roomId = roomId;
    }
}
