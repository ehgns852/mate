package com.bob.mate.domain.chat.entity;

import com.bob.mate.domain.user.entity.User;
import com.bob.mate.global.audit.AuditListener;
import com.bob.mate.global.audit.Auditable;
import com.bob.mate.global.audit.TimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@EntityListeners(AuditListener.class)
@Entity
public class Room implements Auditable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "room_id")
    private Long id;

    private User sender;

    private User receiver;

    @Embedded
    private TimeEntity timeEntity;

    @OneToMany(mappedBy = "room", orphanRemoval = true)
    private List<Chat> chats = new ArrayList<>();

    @Override
    public void setTimeEntity(TimeEntity timeEntity) {
        this.timeEntity = timeEntity;
    }
}
