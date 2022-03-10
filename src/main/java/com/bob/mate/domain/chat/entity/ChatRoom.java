package com.bob.mate.domain.chat.entity;

import com.bob.mate.domain.user.entity.User;
import com.bob.mate.global.audit.AuditListener;
import com.bob.mate.global.audit.Auditable;
import com.bob.mate.global.audit.TimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@EntityListeners(AuditListener.class)
public class ChatRoom implements Auditable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "chat_room_id")
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "host")
    private User host;

    @ManyToOne
    @JoinColumn(name = "guest")
    private User guest;

    private boolean isActive;

    private TimeEntity timeEntity;

    @Builder
    public ChatRoom(User host, String title) {
        this.title = title;
        this.host = host;
        this.isActive = true;
    }

    @Override
    public void setTimeEntity(TimeEntity timeEntity) {
        this.timeEntity = timeEntity;
    }

    public ChatRoom enter(User guest) {
        if (this.host == guest) {
            return this;
        }
        this.guest = guest;
        this.isActive = true;
        return this;
    }

    public void exit(User user) {
        if (user == this.host) {
            this.host = null;
            this.isActive = false;
        } else if (user == this.guest) {
            this.guest = null;
        }
    }
}
