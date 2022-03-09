package com.bob.mate.domain.user.entity;

import com.bob.mate.global.audit.AuditListener;
import com.bob.mate.global.audit.Auditable;
import com.bob.mate.global.audit.TimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
@EntityListeners(AuditListener.class)
public class UploadFile implements Auditable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "upload_file_id")
    private Long id;

    private String uploadFilename;
    private String storeFilename;
    private String filePath;

    private TimeEntity timeEntity;


    @Builder
    public UploadFile(String uploadFilename, String storeFilename, String filePath) {
        this.uploadFilename = uploadFilename;
        this.storeFilename = storeFilename;
        this.filePath = filePath;
    }

    public static UploadFile createUploadFile(String imgUrl) {
        return UploadFile.builder()
                .storeFilename(imgUrl)
                .build();
    }

    public void addUploadFile(UploadFile uploadFile) {
        this.uploadFilename = uploadFile.getUploadFilename();
        this.storeFilename = uploadFile.getStoreFilename();
        this.filePath = uploadFile.getFilePath();
    }

    @Override
    public void setTimeEntity(TimeEntity timeEntity) {
        this.timeEntity = timeEntity;
    }
}
