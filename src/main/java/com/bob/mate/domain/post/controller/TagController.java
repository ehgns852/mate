package com.bob.mate.domain.post.controller;

import com.bob.mate.domain.post.dto.TagResponse;
import com.bob.mate.domain.post.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @GetMapping("/tags")
    public ResponseEntity<List<TagResponse>> getAllTags() {
        return tagService.getAllTags();
    }
}
