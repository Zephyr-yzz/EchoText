package com.example.speechmate.network;

import com.google.gson.annotations.SerializedName;

public class TranscriptionResponse {
    @SerializedName("text")
    private String text;

    public String getText() {
        return text;
    }
} 