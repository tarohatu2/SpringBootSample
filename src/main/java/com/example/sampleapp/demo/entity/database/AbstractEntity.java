package com.example.sampleapp.demo.entity.database;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractEntity {

    @Column(name = "created_datetime")
    private Date createdDateTime;

    @Column(name = "updated_datetime")
    private Date updatedDateTime;

    @PrePersist
    public void onPrePersist() {
        setCreatedDateTime(new Date());
        setUpdatedDateTime(new Date());
    }

    @PreUpdate
    public void onPreUpdate() {
        setUpdatedDateTime(new Date());
    }
}
