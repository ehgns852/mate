package com.bob.mate.global.config.redis;

import com.bob.mate.domain.chat.entity.Notice;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisPublisher {
    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(ChannelTopic topic, Notice notice) {
        redisTemplate.convertAndSend(topic.getTopic(), notice);
    }

}
