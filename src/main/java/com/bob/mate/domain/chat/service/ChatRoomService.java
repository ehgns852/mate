package com.bob.mate.domain.chat.service;

import com.bob.mate.domain.chat.dto.ChatRoomRequest;
import com.bob.mate.domain.chat.dto.ChatRoomResponse;
import com.bob.mate.domain.chat.entity.ChatRoom;
import com.bob.mate.domain.chat.repository.ChatRoomRepository;
import com.bob.mate.domain.user.entity.User;
import com.bob.mate.domain.user.service.UserService;
import com.bob.mate.global.exception.CustomException;
import com.bob.mate.global.exception.ErrorCode;
import com.bob.mate.global.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatRoomService {

    private final UserService userService;
    private final ChatRoomRepository chatRoomRepository;
    private final Util util;



    @Transactional
    public ChatRoomResponse createRoom(ChatRoomRequest chatRequest) {
        User user = util.findCurrentUser();

        ChatRoom chatRoom = ChatRoom.builder()
                .host(user)
                .title(chatRequest.getTitle())
                .build();
         chatRoomRepository.save(chatRoom);

         return ChatRoomResponse.builder()
                 .roomId(chatRoom.getId())
                 .hostId(chatRoom.getHost().getId())
                 .title(chatRoom.getTitle())
                 .isActive(chatRoom.isActive())
                 .message("방 생성 완료")
                 .build();

    }

    @Transactional
    public ChatRoomResponse enterRoom(ChatRoomRequest chatRoomRequest) {
//        User user = util.findCurrentUser();
        User guest = userService.findById(chatRoomRequest.getUserId());

        ChatRoom chatRoom = findById(chatRoomRequest.getRoomSubscribeId());

        chatRoom.enter(guest);

            return ChatRoomResponse.builder()
                    .roomId(chatRoom.getId())
                    .guestId(chatRoom.getGuest().getId())
                    .hostId(chatRoom.getHost().getId())
                    .isActive(chatRoom.isActive())
                    .title(chatRoom.getTitle())
                    .message("방 입장 완료")
                    .build();
    }

    @Transactional
    public ChatRoomResponse exitRoom(ChatRoomRequest chatRoomRequest) {
//        User user = util.findCurrentUser();
        User user = userService.findById(chatRoomRequest.getUserId());
        ChatRoom chatRoom = findById(chatRoomRequest.getRoomSubscribeId());

        chatRoom.exit(user);
        if (!chatRoom.isActive()) {
            chatRoomRepository.delete(chatRoom);
        }
        return ChatRoomResponse.builder()
                .message("방 나가기 완료")
                .roomId(chatRoom.getId())
                .build();
    }

    public ChatRoom findById(Long id){
        return  chatRoomRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CHATROOM));
    }


    public List<ChatRoomResponse> findAll() {
        List<ChatRoom> chatRooms = chatRoomRepository.findAll();

         return chatRooms.stream()
                .map(chatRoom -> ChatRoomResponse.builder().roomId(chatRoom.getId()).build())
                .collect(Collectors.toList());
    }
}
