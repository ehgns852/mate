package com.bob.mate.domain.user.entity;

import com.bob.mate.domain.comment.entity.Comment;
import com.bob.mate.domain.post.entity.Post;
import com.bob.mate.domain.post.entity.PostLike;
import com.bob.mate.global.audit.AuditListener;
import com.bob.mate.global.audit.Auditable;
import com.bob.mate.global.audit.TimeEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@EntityListeners(AuditListener.class)
@ToString(of = {"id", "email"})
@Table(name = "member")
public class User implements Auditable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToOne(fetch = LAZY, cascade = ALL, orphanRemoval = true)
    @JoinColumn(name = "user_profile_id")
    private UserProfile userProfile;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = ALL)
    private List<Post> posts = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "post")
    private List<PostLike> postLikes;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = ALL)
    private List<Comment> comments = new ArrayList<>();


    @Enumerated(STRING)
    private Role role;

    @Embedded
    private TimeEntity timeEntity;


    @Builder
    public User(String email, UserProfile userProfile, Role role, TimeEntity timeEntity) {
        this.email = email;
        this.userProfile = userProfile;
        this.role = role;
        this.timeEntity = timeEntity;
    }

    /**
     * 생성 메서드
     */
    public static User createUser(String email,String nickName, Gender gender, String provider, String providerId){

        UserProfile profile = UserProfile.createProfile(nickName,gender,provider, providerId);

        User user = User.builder()
                .email(email)
                .role(Role.USER)
                .build();

        user.addUserProfile(profile);

        return user;
    }

    /**
     * 연관관계 메서드
     */
    public void addUserProfile(UserProfile userProfile){
        this.userProfile = userProfile;
        userProfile.setUser(this);
    }


    /**
     * AuditListener
     */
    public void setTimeEntity(TimeEntity timeEntity) {
        this.timeEntity = timeEntity;
    }

}
