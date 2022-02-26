package com.bob.mate.global.test;

import com.bob.mate.domain.user.entity.Gender;
import com.bob.mate.domain.user.entity.Role;
import com.bob.mate.domain.user.entity.User;
import com.bob.mate.domain.user.entity.UserProfile;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
/**
 * 테스트를 위한 dummy
 */
public class UserDummy {

    private Long id;

    private String nickName;
    private String email;
    private Gender gender;
    private String provider;



    public static User createUserDummy(Long id, String nickName, String email, Gender gender, String provider) {

        return User.builder()
                .id(id)
                .email(email)
                .userProfile(UserProfile.createProfile(nickName, gender, provider, "djsakld", "dlksajldas"))
                .role(Role.USER)
                .build();
    }

}
