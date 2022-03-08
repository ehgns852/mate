package com.bob.mate.domain.chat.service;

import com.bob.mate.domain.chat.dto.ChatRequest;
import com.bob.mate.domain.chat.dto.LatestChatResponse;
import com.bob.mate.domain.chat.entity.Chat;
import com.bob.mate.domain.chat.entity.Room;
import com.bob.mate.domain.chat.repository.ChatRepository;
import com.bob.mate.domain.user.entity.User;
import com.bob.mate.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChattingService {

    private final UserService userService;
    private final RoomService roomService;
    private final ChatRepository chatRepository;


    public List<LatestChatResponse> findAllLatestChats(Long userId) {
        User findUser = userService.findById(userId);
        roomService.findAllByUserId(findUser.getId());
        return  null;
    }

    @Transactional
    public void save(Long roomId, ChatRequest chatRequest) {
        User sender = userService.findById(chatRequest.getSenderId());
        User receiver = userService.findById(chatRequest.getReceiverId());
        Room room = roomService.findById(roomId);
        Chat chat = Chat.builder()
                .room(room)
                .sender(sender)
                .receiver(receiver)
                .content(chatRequest.getMessage())
                .build();
        chatRepository.save(chat);
    }
}
