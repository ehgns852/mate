package com.bob.mate.domain.chat.controller;

import com.bob.mate.domain.chat.dto.ChatRequest;
import com.bob.mate.domain.chat.dto.RoomIdResponse;
import com.bob.mate.domain.chat.dto.RoomRequest;
import com.bob.mate.domain.chat.service.ChattingService;
import com.bob.mate.domain.chat.service.RoomService;
import com.bob.mate.global.config.redis.RedisChat;
import com.bob.mate.global.config.redis.RedisPublisher;
import com.bob.mate.global.exception.CustomException;
import com.bob.mate.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/rooms")
@RestController
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;
    private final ChattingService chattingService;
    private final RedisPublisher redisPublisher;


    @MessageMapping("/{roomId}")
    public void publish(@DestinationVariable Long roomId,
                        @RequestBody @Validated ChatRequest chatRequest) {
        log.info("MessageMapping In");
        chattingService.save(roomId, chatRequest);
        ChannelTopic channelTopic = new ChannelTopic("/rooms/" + roomId);
        log.info("channelTopic = {}", channelTopic.getTopic());
        redisPublisher.publish(channelTopic, new RedisChat(roomId, chatRequest.getSenderId(), chatRequest.getReceiverId(), chatRequest.getMessage()));
    }

    @GetMapping
    public ResponseEntity<RoomIdResponse> createOrGetRoom(@Validated RoomRequest roomRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomException(ErrorCode.BAD_REQUEST_ROOM);
        }
        RoomIdResponse roomId = roomService.getOrCreate(roomRequest);
        return ResponseEntity.ok(roomId);
    }



//    @PostMapping("/enter")
//    public ResponseEntity<?> enterRoom(@RequestBody ChatRequest chatRequest) {
//        log.info("enter Room");
//        roomService.enterRoom(chatRequest);
//    }
}
