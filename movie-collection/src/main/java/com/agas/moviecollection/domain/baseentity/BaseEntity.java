package com.agas.moviecollection.domain.baseentity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Setter
@Getter(value= AccessLevel.PUBLIC)
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;

    @Column(name = "created_at", columnDefinition = "DATE")
    @CreatedDate
    private Instant createdAt;

    @Column(name = "last_modified_by")
    @LastModifiedBy
    private String lastModifiedBy;

    @Column(name="is_deleted")
    private Boolean deleted;

    @Column(name = "modified_at", columnDefinition = "DATE")
    @LastModifiedDate
    private Instant modifiedAt;

    @PrePersist
    public void prePersist(){
        this.createdBy = getCreatedBy()!=null?getCreatedBy():"SYSTEM";
        this.lastModifiedBy = getLastModifiedBy()!=null?getLastModifiedBy():"SYSTEM";
    }
    @PreUpdate
    public void preModified(){
        this.lastModifiedBy = "SYSTEM MODIFIED";
    }
}
