package com.bob.mate.domain.user.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private Long id;

    private String userId;

    private String password;


    public UserDto(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
