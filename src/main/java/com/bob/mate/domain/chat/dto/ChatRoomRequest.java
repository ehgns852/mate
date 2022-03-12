package com.bob.mate.domain.chat.dto;

import com.bob.mate.domain.chat.entity.NoticeType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class ChatRoomRequest {
    private Long userId;
    private Long targetId;
    private String userSubscribeId;
    private Long roomSubscribeId;
    private String title;
    private String content;
    private boolean isActive;
    private NoticeType type;


}

