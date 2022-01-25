package com.bob.mate.chat.config.websocket;

import com.bob.mate.chat.handler.ChatHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


/**
 * EnableWebSocket = WebSocket 활성화
 * WebSocket에 접속하기 위한 EndPonit는  /chat으로 설정
 * 도메인이 다른 서버에도 접속 가능하도록 CORS : setallowdOrigins("*") 를 추가
 * ws://localhost:8080/chat
 */
@Configuration
@RequiredArgsConstructor
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final ChatHandler chatHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatHandler, "ws/chat").setAllowedOrigins("*");
    }
}
