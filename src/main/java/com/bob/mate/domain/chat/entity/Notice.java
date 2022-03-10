package com.bob.mate.domain.chat.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Notice {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "message_id")
    private Long id;
    private String title;
    private String content;
    private Long senderId;
    private Long targetId;
    private Long subscribeId;
    private boolean isRead;

    @Enumerated(EnumType.STRING)
    private NoticeType type;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;


    @Builder
    public Notice(String title, String content, Long senderId, Long targetId, Long subscribeId, NoticeType type, ChatRoom chatRoom) {
        this.title = title;
        this.content = content;
        this.senderId = senderId;
        this.targetId = targetId;
        this.subscribeId = subscribeId;
        this.isRead = false;
        this.type = type;
        this.chatRoom = chatRoom;
    }


    public void read(){
        this.isRead = true;
    }
}
