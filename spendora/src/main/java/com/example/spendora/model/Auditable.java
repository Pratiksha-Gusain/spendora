package com.example.spendora.model;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;
/**
 * Base class for entities that need audit fields.
 * Automatically sets createdAt and updatedAt timestamps.
 */
@MappedSuperclass
@Data
public abstract class Auditable {

    private Long createdAt;
    private Long updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = System.currentTimeMillis();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = System.currentTimeMillis();
    }
}
