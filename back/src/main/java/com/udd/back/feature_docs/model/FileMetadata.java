package com.udd.back.feature_docs.model;

import com.udd.back.feature_auth.model.User;
import com.udd.back.feature_docs.enumeration.FileStatus;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class FileMetadata {

    @Id
    @Column
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FileStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    private User author;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public FileMetadata() {
    }

    public FileMetadata(UUID id, FileStatus status, User author) {
        this.id = id;
        this.status = status;
        this.author = author;
    }

    public UUID getId() {
        return id;
    }

    public String getStringId() {
        return id.toString();
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public FileStatus getStatus() {
        return status;
    }

    public void setStatus(FileStatus status) {
        this.status = status;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

}
