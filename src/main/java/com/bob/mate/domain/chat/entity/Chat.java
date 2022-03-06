package com.bob.mate.domain.chat.entity;

import com.bob.mate.domain.user.entity.User;
import com.bob.mate.global.audit.AuditListener;
import com.bob.mate.global.audit.Auditable;
import com.bob.mate.global.audit.TimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@RequiredArgsConstructor
@Entity
@EntityListeners(AuditListener.class)
public class Chat implements Auditable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "chat_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(nullable = false, name = "room_id")
    private Room room;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    private User sender;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    private User receiver;

    @Lob
    @Column(nullable = false)
    private String content;

    @Embedded
    private TimeEntity timeEntity;

    public Chat(Room room, User sender, User receiver, String content) {
        this.room = room;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }

    @Override
    public void setTimeEntity(TimeEntity timeEntity) {
        this.timeEntity = timeEntity;
    }
}
