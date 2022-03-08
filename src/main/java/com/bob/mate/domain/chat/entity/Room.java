package com.bob.mate.domain.chat.entity;

import com.bob.mate.domain.user.entity.User;
import com.bob.mate.global.audit.AuditListener;
import com.bob.mate.global.audit.Auditable;
import com.bob.mate.global.audit.TimeEntity;
import lombok.Builder;
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
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_room_to_host"))
    private User host;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_room_to_guest"))
    private User guest;

    @Embedded
    private TimeEntity timeEntity;

    @OneToMany(mappedBy = "room", orphanRemoval = true)
    private List<Chat> chats = new ArrayList<>();

    public static Room createRoom(User host, User guest) {
        return Room.builder()
                .host(host)
                .guest(guest)
                .build();
    }

    @Override
    public void setTimeEntity(TimeEntity timeEntity) {
        this.timeEntity = timeEntity;
    }

    @Builder
    public Room(User host, User guest, TimeEntity timeEntity, List<Chat> chats) {
        this.host = host;
        this.guest = guest;
        this.timeEntity = timeEntity;
        this.chats = chats;
    }
}
