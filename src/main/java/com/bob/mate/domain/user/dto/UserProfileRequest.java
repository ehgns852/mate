package com.bob.mate.domain.user.dto;

import com.bob.mate.domain.user.entity.Address;
import com.bob.mate.domain.user.entity.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserProfileRequest {

    @NotNull
    @NotBlank
    @Size(min = 2, max = 10)
    private String nickName;

    @NotNull
    private Gender gender;

    @NotNull
    private Address address;

    @NotNull
    @NotBlank
    private String email;

    @NotNull
    private String phoneNumber;

}
