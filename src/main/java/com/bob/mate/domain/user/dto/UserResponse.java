package com.bob.mate.domain.user.dto;


import com.bob.mate.domain.user.entity.Address;
import com.bob.mate.domain.user.entity.Gender;
import com.bob.mate.domain.user.entity.Role;
import com.bob.mate.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {

    private Long id;
    private String email;
    private Role role;
    private String nickName;
    private Address address;
    private Gender gender;
    private Integer phoneNumber;
    private Integer age;
    private String imgUrl;
    private String message;


    public UserResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.nickName = user.getUserProfile().getNickName();
        this.address = user.getUserProfile().getAddress();
        this.gender = user.getUserProfile().getGender();
        this.phoneNumber = user.getUserProfile().getPhoneNumber();
        this.age = user.getUserProfile().getAge();
    }

    public UserResponse(String message, String imgUrl) {
        this.message = message;
        this.imgUrl = imgUrl;
    }

    public UserResponse(String message) {
        this.message = message;
    }
}