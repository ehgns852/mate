package com.bob.mate.global.util.file;

import com.bob.mate.domain.user.entity.UploadFile;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Component
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileStore {
    public static String CLOUD_FRONT_DOMAIN_NAME = "d3afymv2nzz1pw.cloudfront.net";


    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);

        return UploadFile.builder()
                .uploadFilename(originalFilename)
                .storeFilename(storeFileName)
                .build();
    }

    /**
     * randomUUID.확장자로 파일 이름 저장
     */
    private String createStoreFileName(String originalFilename){
        String ext = extracted(originalFilename);
        String uuid = UUID.randomUUID().toString();
        String filename = uuid + "." + ext;
        return "https://" + CLOUD_FRONT_DOMAIN_NAME + "/" + filename;
    }

    /**
     * 확장자 추출 메서드
     */
    private String extracted(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
