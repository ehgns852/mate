package com.bob.mate.domain.aws.controller;

import com.bob.mate.domain.aws.service.S3Service;
import com.bob.mate.global.dto.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/aws-s3")
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping("/upload")
    public CustomResponse upload(@RequestParam("image")MultipartFile file) throws IOException {
        return s3Service.upload(file);
    }
}
