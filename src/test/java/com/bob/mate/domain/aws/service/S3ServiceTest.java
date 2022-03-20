package com.bob.mate.domain.aws.service;

import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;

class S3ServiceTest {

    private static final String imageUrl = "https://d3afymv2nzz1pw.cloudfront.net/7e59c1de-7cce-4acb-9ac4-e81a46d965e5.jpg";

    @Test
    public void test(){
        //given
        String filename = StringUtils.stripFilenameExtension(imageUrl);
        System.out.println("filename = " + filename);

        String filename1 = StringUtils.getFilename(imageUrl);
        System.out.println("filename1 = " + filename1);
        String filename3 = StringUtils.stripFilenameExtension(filename1);
        System.out.println("filename3 = " + filename3);

        //when

        //then

    }

}