package com.example.speechmate.network;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Arrays;

public class OptimizationRequest {
    @SerializedName("model")
    private String model = "internlm/internlm2_5-7b-chat";

    @SerializedName("messages")
    private List<Message> messages;

    public OptimizationRequest(String text) {
        this.messages = Arrays.asList(
            new Message("system", "你是一个文本优化助手，请帮我优化以下文本，使其更加通顺、准确，并提供相关的主题标签。"),
            new Message("user", text)
        );
    }

    static class Message {
        @SerializedName("role")
        private String role;
        
        @SerializedName("content")
        private String content;

        Message(String role, String content) {
            this.role = role;
            this.content = content;
        }
    }
} 