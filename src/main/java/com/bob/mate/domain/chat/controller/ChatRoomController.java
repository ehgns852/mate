package com.bob.mate.domain.chat.controller;

import com.bob.mate.domain.chat.dto.ChatRoomRequest;
import com.bob.mate.domain.chat.dto.ChatRoomResponse;
import com.bob.mate.domain.chat.entity.ChatRoom;
import com.bob.mate.domain.chat.service.ChatRoomService;
import com.bob.mate.global.config.redis.RedisSubscriber;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final RedisMessageListenerContainer redisMessageListener;
    private final RedisSubscriber redisSubscriber;

    @Operation(description = "방만들기", method = "POST")
    @PostMapping("/new/room")
    public ChatRoomResponse createRoom(@RequestBody ChatRoomRequest chatRoomRequest) {
        log.info("new Room");
        ChatRoomResponse createRoom = chatRoomService.createRoom(chatRoomRequest);
        //redis 방입장 구독
        ChannelTopic topic = new ChannelTopic(createRoom.getRoomSubscribeId());
        redisMessageListener.addMessageListener(redisSubscriber, topic);
        return createRoom;
    }

    @Operation(description = "방입장", method = "POST")
    @PostMapping("/rooms/enter")
    public ChatRoomResponse enterRoom(@RequestBody ChatRoomRequest chatRoomRequest) {
        log.info("enter Room");
        ChatRoomResponse enteredRoom = chatRoomService.enterRoom(chatRoomRequest);
        ChannelTopic topic = ChannelTopic.of(enteredRoom.getRoomSubscribeId());
        redisMessageListener.addMessageListener(redisSubscriber, topic);
        return enteredRoom;
    }

    @Operation(description = "방 나가기", method = "POST")
    @PostMapping("/rooms/exit")
    public ChatRoomResponse exitRoom(@RequestBody ChatRoomRequest chatRoomRequest) {
        log.info("exitRoom In");
        ChatRoomResponse exitRoom = chatRoomService.exitRoom(chatRoomRequest);
        log.info("exitRoom = {}" ,exitRoom);
        ChannelTopic topic = ChannelTopic.of(exitRoom.getRoomSubscribeId());
        redisMessageListener.removeMessageListener(redisSubscriber, topic);
        return exitRoom;
    }


}
