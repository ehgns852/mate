package com.bob.mate.domain.chat.service;

import com.bob.mate.domain.chat.dto.RoomIdResponse;
import com.bob.mate.domain.chat.dto.RoomRequest;
import com.bob.mate.domain.chat.entity.Room;
import com.bob.mate.domain.chat.repository.RoomRepository;
import com.bob.mate.domain.user.service.UserService;
import com.bob.mate.global.config.redis.RedisSubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomService {

    private final UserService userService;
    private final RoomRepository roomRepository;

    private final RedisMessageListenerContainer redisMessageListener;
    private final RedisSubscriber redisSubscriber;
    private final Map<String, ChannelTopic> topics = new HashMap<>();

    public Room findById(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 방입니다."));
    }


    public List<Room> findAllByUserId(Long id) {
        return null;
    }

    @Transactional
    public RoomIdResponse getOrCreate(RoomRequest roomRequest) {
        Room room = roomRepository.findByHostIdAndGuestId(roomRequest.getHostId(), roomRequest.getGuestId())
                .orElseGet(() ->
                        Room.createRoom(
                                userService.findById(roomRequest.getHostId()),
                                userService.findById(roomRequest.getGuestId())
                        ));
        Room savedRoom = roomRepository.save(room);

        addRedisMessageListener(savedRoom);

        return new RoomIdResponse(savedRoom.getId());
    }

    private void addRedisMessageListener(Room savedRoom) {
        String roomId = "/rooms/" + savedRoom.getId();
        if (!topics.containsKey(roomId)) {
            ChannelTopic topic = new ChannelTopic(roomId);
            redisMessageListener.addMessageListener(redisSubscriber, topic);
            topics.put(roomId, topic);
        }
    }




}
