package com.bob.mate.domain.chat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class ChatRoomRequest {
    private Long userId;
    private String userSubscribeId;
    private Long roomSubscribeId;
    private String title;
    private String content;
    private boolean isActive;


}
