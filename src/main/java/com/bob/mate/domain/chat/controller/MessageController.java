package com.bob.mate.domain.chat.controller;

import com.bob.mate.domain.chat.dto.ChatRoomRequest;
import com.bob.mate.domain.chat.entity.Notice;
import com.bob.mate.domain.chat.service.MessageService;
import com.bob.mate.global.config.redis.RedisPublisher;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final RedisPublisher redisPublisher;
    private final SimpMessageSendingOperations messagingTemplate;

    @Operation(description = "채팅방 메세지 보내기", method = "MESSAGE")
    @MessageMapping("/chat/messages")
    public void message(ChatRoomRequest chatRoomRequest) {
        log.debug("/chat/messages");

        Long roomId = chatRoomRequest.getRoomSubscribeId();
        Notice message = messageService.message(chatRoomRequest);
        ChannelTopic topic = ChannelTopic.of("/rooms/" + roomId);
        redisPublisher.publish(topic, message);
    }

    @Operation(description = "유저에게 알림보내기")
    @MessageMapping("/new/notices")
    public void chatNotifications(ChatRoomRequest chatRoomRequest) {
        log.debug("/new/notices");
        Long userId = chatRoomRequest.getTargetId();
        Notice notice = messageService.chatNotifications(chatRoomRequest);
        messagingTemplate.convertAndSend("/sub/notice/user" + userId, notice);

    }
}
