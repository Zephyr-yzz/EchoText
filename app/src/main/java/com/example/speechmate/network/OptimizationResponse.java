package com.example.speechmate.network;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class OptimizationResponse {
    @SerializedName("choices")
    private List<Choice> choices;

    public String getOptimizedText() {
        if (choices != null && !choices.isEmpty()) {
            return choices.get(0).message.content;
        }
        return "";
    }

    static class Choice {
        @SerializedName("message")
        Message message;
    }

    static class Message {
        @SerializedName("content")
        String content;
    }
} 