package com.bob.mate.domain.user.entity;


import com.bob.mate.domain.user.dto.UserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class UserProfile {


    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_profile_id")
    private Long id;

    @Column(nullable = false)
    private String username;

    @Embedded
    private Address address;

    private String phoneNumber;

    @Enumerated(STRING)
    private Gender gender;

    @OneToOne(mappedBy = "userProfile")
    private User user;

    private int age;


    @Builder
    public UserProfile(String username, Address address, String phoneNumber, Gender gender, int age) {
        this.username = username;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.age = age;
    }

    /**
     * 생성 메서드
     */
    public static UserProfile createProfile(String username, Address address, String phoneNumber, Gender gender, int age) {
        return UserProfile.builder()
                .username(username)
                .address(address)
                .phoneNumber(phoneNumber)
                .gender(gender)
                .age(age)
                .build();
    }

    public void setUser(User user) {
        this.user = user;
    }

}
