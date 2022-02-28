package com.bob.mate.domain.user.dto;

import com.bob.mate.domain.user.entity.Address;
import com.bob.mate.domain.user.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserProfileRequest {


    @NotNull
    private Gender gender;

    @NotNull
    private Address address;

    @NotNull
    @NotBlank
    private String email;

    @NotNull
    private Integer phoneNumber;
}
