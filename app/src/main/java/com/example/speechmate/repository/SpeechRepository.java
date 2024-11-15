package com.example.speechmate.repository;

import com.example.speechmate.network.ApiService;
import com.example.speechmate.network.OptimizationRequest;
import com.example.speechmate.network.OptimizationResponse;
import com.example.speechmate.network.TranscriptionResponse;

import java.io.File;
import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

@Singleton
public class SpeechRepository {
    private final ApiService apiService;

    @Inject
    public SpeechRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public Call<TranscriptionResponse> transcribeAudio(String filePath) {
        File file = new File(filePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("audio/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        RequestBody model = RequestBody.create(MediaType.parse("text/plain"), "whisper-1");
        
        return apiService.transcribeAudio(body, model);
    }

    public Call<OptimizationResponse> optimizeText(String text) {
        return apiService.optimizeText(new OptimizationRequest(text));
    }
} 