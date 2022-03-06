package com.bob.mate.domain.chat.service;

import com.bob.mate.domain.chat.dto.LatestChatResponse;
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
}
