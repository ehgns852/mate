package com.bob.mate.domain.user.dto;


import com.bob.mate.domain.user.entity.Address;
import com.bob.mate.domain.user.entity.Gender;
import com.bob.mate.domain.user.entity.Role;
import lombok.Builder;
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
    private String phoneNumber;
    private Integer age;
    private Boolean saveUser;

    @Builder
    public UserResponse(Long id, String email, Role role, String nickName, Address address, Gender gender, String phoneNumber, Integer age, Boolean saveUser) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.nickName = nickName;
        this.address = address;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.saveUser = saveUser;
    }
}