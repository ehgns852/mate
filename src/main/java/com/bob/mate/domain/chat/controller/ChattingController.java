package com.bob.mate.domain.chat.controller;

import com.bob.mate.domain.chat.dto.LatestChatResponse;
import com.bob.mate.domain.chat.service.ChattingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/messages")
public class ChattingController {

    private final ChattingService chattingService;

    @GetMapping("{userId}")
    public List<LatestChatResponse> findAllLatestChats(@PathVariable Long userId) {
        return chattingService.findAllLatestChats(userId);
    }
}
