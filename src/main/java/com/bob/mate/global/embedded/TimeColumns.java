package com.bob.mate.global.embedded;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.OffsetDateTime;

@Embeddable
public class TimeColumns {
    @Column(name = "created_at", columnDefinition = "timestamp with time zone default current_timestamp")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "timestamp with time zone default current_timestamp")
    private OffsetDateTime updatedAt;
}
