package com.bob.mate.domain.chat.dto;

import com.bob.mate.domain.chat.entity.Chat;
import com.bob.mate.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Getter
@NoArgsConstructor
@Builder
public class LatestChatResponse {

    private Long roomId;

    private Long id;

    private String name;

    private String imageUrl;

    private String latestMessage;

    private OffsetDateTime createDate;

    public static LatestChatResponse of(User partner, Chat latestChat) {
        return LatestChatResponse.builder()
                .roomId(latestChat.getRoom().getId())
                .id(partner.getId())
                .name(partner.getUserProfile().getNickName())
                .imageUrl(partner.getUserProfile().getUploadFile().getStoreFilename())
                .latestMessage(latestChat.getContent())
                .createDate(latestChat.getTimeEntity().getCreatedDate())
                .build();
    }
}
