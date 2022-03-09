package com.bob.mate.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomResponse {

    private String roomSubscribeId;

    private String title;

    private Long hostId;

    private Long guestId;

    private boolean isActive;

    private String message;
}
