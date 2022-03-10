package com.bob.mate.domain.chat.service;

import com.bob.mate.domain.chat.dto.ChatRoomRequest;
import com.bob.mate.domain.chat.entity.ChatRoom;
import com.bob.mate.domain.chat.entity.Notice;
import com.bob.mate.domain.chat.entity.NoticeType;
import com.bob.mate.domain.chat.repository.MessageRepository;
import com.bob.mate.domain.user.entity.User;
import com.bob.mate.domain.user.service.UserService;
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
}
