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
public class UserDto {

    private Long id;
    private String email;
    private Role role;
    private String username;
    private Address address;
    private Gender gender;
    private String phoneNumber;
    private int age;


    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.username = user.getUserProfile().getUsername();
        this.address = user.getUserProfile().getAddress();
        this.gender = user.getUserProfile().getGender();
        this.phoneNumber = user.getUserProfile().getPhoneNumber();
        this.age = user.getUserProfile().getAge();
    }
}
