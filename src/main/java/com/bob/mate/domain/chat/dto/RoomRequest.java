package com.bob.mate.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomRequest {

    @NotNull
    private Long hostId;

    @NotNull
    private Long guestId;

}
