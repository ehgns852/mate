package com.bob.mate.domain.comment.entity;

import com.bob.mate.domain.post.entity.Post;
import com.bob.mate.domain.user.entity.User;
import com.bob.mate.global.audit.AuditListener;
import com.bob.mate.global.audit.Auditable;
import com.bob.mate.global.audit.TimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "post")
@EntityListeners(AuditListener.class)
public class Comment implements Auditable {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "like_count")
    @ColumnDefault("0")
    private Integer likeCount;

    @ColumnDefault("false")
    private Boolean liked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Embedded
    private TimeEntity timeEntity;

    @Override
    public void setTimeEntity(TimeEntity timeEntity) {
        this.timeEntity = timeEntity;
    }

    public Comment(String content, User user) {
        this.content = content;
        this.user = user;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void likeComment(Integer likeCount, Boolean liked) {
        this.likeCount = likeCount;
        this.liked = liked;
    }
}
