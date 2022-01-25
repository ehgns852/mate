package com.bob.mate.chat.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;


/**
 * 소켓 통신은 서버와 클라이언트가 1:n의 관계를 맺는다. 하나의 서버에 다수 클라이언트가 접속 할 수 있다.
 * 따라서 서버는 다수의 클라이언트가 보낸 메세지를 처리할 핸들러가 필요하다.
 * 텍스트 기반의 채팅을 구현 -> "TextWebSocketHandler" 를 상속 받는다.
 */
@Component
@Slf4j
public class ChatHandler extends TextWebSocketHandler {

    private static List<WebSocketSession> list = new ArrayList<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload = {}", payload);
        for (WebSocketSession sess : list) {
            sess.sendMessage(message);
        }

    }

    /**
     * 클라이언트가 접속 시 호출되는 메서드
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        list.add(session);
        log.info(session + "클라이언트 접속");
    }


    /**
     * 클라이언트가 해제 시 호출되는 메서드
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info(session + "클라이어느 접속 해제");
        list.remove(session);
    }

}
