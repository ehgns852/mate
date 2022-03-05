package com.bob.mate.domain.user.controller;

import com.bob.mate.global.util.file.FileStore;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

@RestController
@RequiredArgsConstructor
public class ResourceController {

    private final FileStore fileStore;

    @Operation(summary = "파일을 저장 경로에서 가져오는 API", description = "파일 이름을 받아서 저장 경로에서 가져오는 API")
    @ApiResponse(responseCode = "200", description = "파일을 정상적으로 가져왔을 경우")
    @GetMapping("/image/{filename}")
    public Resource showImage(@PathVariable String filename) throws MalformedURLException {

            return new UrlResource("file:" + fileStore.getFullPath(filename));
    }

}
