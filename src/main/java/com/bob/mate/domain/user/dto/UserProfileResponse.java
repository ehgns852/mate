package com.bob.mate.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse {

    @Schema(description = "회원 프로필에 저장된 이미지 URI")
    private String imageUrl;

    @Schema(description = "성공 메시지")
    private String message;
}
