package com.bob.mate.domain.chat.service;

import com.bob.mate.domain.chat.dto.ChatRoomRequest;
import com.bob.mate.domain.chat.entity.ChatRoom;
import com.bob.mate.domain.chat.entity.Notice;
import com.bob.mate.domain.chat.entity.NoticeType;
import com.bob.mate.domain.chat.repository.MessageRepository;
import com.bob.mate.domain.user.entity.User;
import com.bob.mate.domain.user.service.UserService;
import com.bob.mate.global.exception.CustomException;
import com.bob.mate.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MessageService {

    private final UserService userService;
    private final ChatRoomService chatRoomService;
    private final MessageRepository messageRepository;

    @Transactional
    public Notice message(ChatRoomRequest chatRoomRequest) {
        User sender = userService.findById(chatRoomRequest.getUserId());
        ChatRoom chatRoom = chatRoomService.findById(chatRoomRequest.getRoomSubscribeId());

        Notice message = Notice.builder()
                .chatRoom(chatRoom)
                .senderId(sender.getId())
                .content(chatRoomRequest.getContent())
                .type(NoticeType.MESSAGE)
                .build();

        return messageRepository.save(message);
    }


    @Transactional
    public Notice chatNotifications(ChatRoomRequest chatRoomRequest) {
        User sender = userService.findById(chatRoomRequest.getUserId());
        User subscriber = userService.findById(chatRoomRequest.getTargetId());
        if (sender == subscriber) {
            throw new CustomException(ErrorCode.BAD_REQUEST_CHAT);
        } else {
            return Notice
                    .builder()
                    .senderId(sender.getId())
                    .subscribeId(subscriber.getId())
                    .content(chatRoomRequest.getContent())
                    .type(NoticeType.MESSAGE)
                    .build();
        }
    }
}
