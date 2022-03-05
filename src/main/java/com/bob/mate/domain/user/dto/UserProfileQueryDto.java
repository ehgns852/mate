package com.bob.mate.domain.user.dto;

import com.bob.mate.domain.user.entity.Address;
import com.bob.mate.domain.user.entity.Gender;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserProfileQueryDto {

    private String imgUrl;
    private String nickName;
    private Gender gender;
    private Address address;
    private String email;
    private String phoneNumber;


    @QueryProjection
    public UserProfileQueryDto(String imgUrl, String nickName, Gender gender, Address address, String email, String phoneNumber) {
        this.imgUrl = imgUrl;
        this.nickName = nickName;
        this.gender = gender;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
