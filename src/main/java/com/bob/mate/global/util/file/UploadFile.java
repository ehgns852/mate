package com.bob.mate.global.util.file;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@RequiredArgsConstructor
public class UploadFile {

    private final MultipartFile multipartFile;
    private final String fileName;
    private final String contentType;

    public static UploadFile of(MultipartFile multipartFile, String fileName, String contentType) {
        return new UploadFile(multipartFile, fileName, contentType);
    }
}
