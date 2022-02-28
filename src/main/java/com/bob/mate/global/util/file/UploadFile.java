package com.bob.mate.global.util.file;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class UploadFile {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "upload_file_id")
    private Long id;

    private String uploadFilename;
    private String storeFilename;
    private String filePath;


    public UploadFile(String uploadFilename, String storeFilename, String filePath) {
        this.uploadFilename = uploadFilename;
        this.storeFilename = storeFilename;
        this.filePath = filePath;
    }
}
