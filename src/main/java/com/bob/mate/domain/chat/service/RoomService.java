package com.bob.mate.domain.chat.service;

import com.bob.mate.domain.chat.entity.Room;
import com.bob.mate.domain.chat.repository.RoomRepository;
import com.bob.mate.domain.user.service.UserService;
import com.bob.mate.global.config.redis.RedisSubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomService {

    private final UserService userService;
    private final RoomRepository roomRepository;

    private final RedisMessageListenerContainer redisMessageListenerContainer;
    private final RedisSubscriber redisSubscriber;
    private final Map<String, ChannelTopic> topics = new HashMap<>();


    public List<Room> findAllByUserId(Long id) {
        return null;
    }
}
