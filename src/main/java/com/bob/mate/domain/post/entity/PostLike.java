package com.bob.mate.domain.post.entity;

import com.bob.mate.domain.user.entity.User;
import com.bob.mate.global.audit.AuditListener;
import com.bob.mate.global.audit.Auditable;
import com.bob.mate.global.audit.TimeEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "post")
@EntityListeners(AuditListener.class)
public class PostLike implements Auditable {

    @Id
    @Column(name = "post_like_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private User user;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Embedded
    private TimeEntity timeEntity;

    @Override
    public void setTimeEntity(TimeEntity timeEntity) {
        this.timeEntity = timeEntity;
    }
}
