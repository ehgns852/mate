package com.bob.mate.domain.post.service;

import com.bob.mate.domain.post.dto.TagResponse;
import com.bob.mate.domain.post.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagService {

    private final TagRepository tagRepository;
//    private final TagMapper tagMapper;
//
//
//    public ResponseEntity<List<TagResponse>> getAllTags() {
//        List<TagResponse> tagResponses = tagRepository
//                .findAll().stream().map(tagMapper::mapEntityToResponse).collect(Collectors.toList());
//
//        return ResponseEntity.status(200).body(tagResponses);
//    }



}
