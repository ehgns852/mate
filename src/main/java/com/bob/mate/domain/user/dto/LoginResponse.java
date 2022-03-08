package com.bob.mate.domain.user.dto;

import com.bob.mate.domain.user.entity.Gender;
import com.bob.mate.domain.user.entity.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponse {
    private Long id;
    private String name;
    private String email;
    private String imageUrl;
    private Role role;
    private String tokenType;
    private String accessToken;
    private String refreshToken;
    boolean ProfileSaveUser;
    private Gender gender;

    @Builder
    public LoginResponse(Long id, String name, String email, String imageUrl, Role role, String tokenType, String accessToken, String refreshToken, boolean profileSaveUser, Gender gender) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
        this.role = role;
        this.tokenType = tokenType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.ProfileSaveUser = profileSaveUser;
        this.gender = gender;
    }
}
