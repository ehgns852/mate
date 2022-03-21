package com.bob.mate.domain.aws.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.bob.mate.domain.user.entity.UploadFile;
import com.bob.mate.global.util.file.FileStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class S3Service {

    private static final String DEFAULT_IMAGE = "https://d3afymv2nzz1pw.cloudfront.net/doji.png";

    private final AmazonS3Client amazonS3Client;
    private final FileStore fileStore;


    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    public UploadFile upload(MultipartFile file) throws IOException {
        UploadFile uploadFile = fileStore.storeFile(file);
        String filename = StringUtils.getFilename(uploadFile.getStoreFilename());

        log.info("filename = {}", filename);


        amazonS3Client.putObject(new PutObjectRequest(bucket, filename, file.getInputStream(), null)
                .withCannedAcl(CannedAccessControlList.BucketOwnerRead));

        return uploadFile;
    }

    public void deleteImage(String storeFilename) {
        String filename = StringUtils.getFilename(storeFilename);
        if (!Objects.equals(storeFilename, DEFAULT_IMAGE) && amazonS3Client.doesObjectExist(bucket, filename)) {
            log.info("S3Uploader, S3에서 이미지(이미지명: {})를 삭제했습니다.", storeFilename);
            amazonS3Client.deleteObject(bucket, storeFilename);
        }
    }

}
