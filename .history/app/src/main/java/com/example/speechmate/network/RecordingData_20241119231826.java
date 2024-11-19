package com.example.speechmate.network;

public class RecordingData {
    private String originalText;
    private String optimizedText;
    private long timestamp;
    private String audioPath;

    // 构造函数
    public RecordingData(String originalText, String optimizedText, long timestamp, String audioPath) {
        this.originalText = originalText;
        this.optimizedText = optimizedText;
        this.timestamp = timestamp;
        this.audioPath = audioPath;
    }

    // Getters
    public String getOriginalText() {
        return originalText;
    }

    public String getOptimizedText() {
        return optimizedText;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getAudioPath() {
        return audioPath;
    }

    // Setters
    public void setOriginalText(String originalText) {
        this.originalText = originalText;
    }

    public void setOptimizedText(String optimizedText) {
        this.optimizedText = optimizedText;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }
} 