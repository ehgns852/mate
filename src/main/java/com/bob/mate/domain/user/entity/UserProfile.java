package com.bob.mate.domain.user.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@ToString(of = {"id", "nickName", "gender"})
public class UserProfile {


    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_profile_id")
    private Long id;

    @Column(nullable = false)
    private String nickName;

    @Embedded
    private Address address;

    private Integer phoneNumber;

    @Enumerated(STRING)
    private Gender gender;

    @OneToOne(mappedBy = "userProfile", fetch = LAZY)
    private User user;

    private Integer age;

    private String provider;
    private String providerId;

    private String imageUrl;



    @Builder
    public UserProfile(String nickName, Address address, Integer phoneNumber, Gender gender, Integer age, String provider, String providerId, String imageUrl) {
        this.nickName = nickName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.age = age;
        this.provider = provider;
        this.providerId = providerId;
        this.imageUrl = imageUrl;
    }

    /**
     * 생성 메서드
     */
    public static UserProfile createProfile(String nickName, Gender gender,String provider,String providerId,String imageUrl) {
        return UserProfile.builder()
                .nickName(nickName)
                .gender(gender)
                .imageUrl(imageUrl)
                .provider(provider)
                .providerId(providerId)
                .build();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void createNickName(String nickName) {
        this.nickName = nickName;
    }


    public void addProfile(Address address, Integer phoneNumber, Gender gender, String imgUrl) {
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.imageUrl = imgUrl;
    }
}
