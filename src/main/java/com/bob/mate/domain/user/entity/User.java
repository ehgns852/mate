package com.bob.mate.domain.user.entity;

import com.bob.mate.domain.post.entity.Post;
import com.bob.mate.global.audit.AuditListener;
import com.bob.mate.global.audit.Auditable;
import com.bob.mate.global.audit.TimeEntity;
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

    private String email;

    @OneToOne(fetch = LAZY, cascade = ALL, orphanRemoval = true)
    @JoinColumn(name = "user_profile_id")
    private UserProfile userProfile;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();


    @Enumerated(STRING)
    private Role role;

    @Embedded
    private TimeEntity timeEntity;


    @Builder
    public User(Long id, String email, UserProfile userProfile, Role role) {
        this.id = id;
        this.email = email;
        this.userProfile = userProfile;
        this.role = role;
    }

    /**
     * 생성 메서드
     */
    public static User createUser(String email,String nickName, Gender gender, String provider, String providerId){

        final String imageUrl = "af766592-38bc-4d1f-9cf7-663ecf43b82213.png";

        UserProfile profile = UserProfile.createProfile(nickName,gender,provider, providerId);
        UploadFile uploadFile = UploadFile.createUploadFile(imageUrl);
        profile.addImgUrl(uploadFile);

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



    /**
     * 회원 프로필 생성 및 변경 (이미지 파일 업로드)
     */
    public void addUploadImg(String nickname, Address address, String phoneNumber, String email, Gender gender, UploadFile uploadFile) {
        this.email = email;
        getUserProfile().addUploadImg(nickname, address, phoneNumber, gender, uploadFile);
    }

    /**
     * 회원 프로필 생성 및 변경 (이미지 파일 업로드 X)
     */
    public void updateUserProfile(String nickname, Address address, String phoneNumber, String email, Gender gender) {
        this.email = email;
        getUserProfile().addProfile(nickname, address, phoneNumber, gender);
    }
}

