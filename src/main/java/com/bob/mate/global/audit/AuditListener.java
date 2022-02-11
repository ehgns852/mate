package com.bob.mate.global.audit;

import org.springframework.context.annotation.Configuration;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.OffsetDateTime;

@Configuration
public class AuditListener {
    @PrePersist
    public void setCreatedDate(Auditable auditable) {
        TimeEntity timeEntity = auditable.getTimeEntity();

        if (timeEntity == null) {
            timeEntity = new TimeEntity();
            auditable.setTimeEntity(timeEntity);
        }

        timeEntity.setCreatedAt(OffsetDateTime.now());
        timeEntity.setUpdatedAt(OffsetDateTime.now());
    }

    @PreUpdate
    public void setUpdatedDate(Auditable auditable) {
        TimeEntity timeEntity = auditable.getTimeEntity();
        timeEntity.setUpdatedAt(OffsetDateTime.now());
    }
}
