package com.example.speechmate.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Date;

@Entity(tableName = "recordings")
public class RecordingEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String filePath;
    private long duration;
    private Date createdAt;
    private String originalText;
    private String optimizedText;
    private String tags;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getOriginalText() {
        return originalText;
    }

    public void setOriginalText(String originalText) {
        this.originalText = originalText;
    }

    public String getOptimizedText() {
        return optimizedText;
    }

    public void setOptimizedText(String optimizedText) {
        this.optimizedText = optimizedText;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
} 