package com.bob.mate.domain.chat.entity;

import com.bob.mate.domain.user.entity.User;
import com.bob.mate.global.audit.AuditListener;
import com.bob.mate.global.audit.Auditable;
import com.bob.mate.global.audit.TimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@EntityListeners(AuditListener.class)
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Room implements Auditable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "room_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User sender;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
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
