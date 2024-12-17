package com.example.speechmate.repository;

import com.example.speechmate.network.ApiService;
import com.example.speechmate.network.OptimizationRequest;
import com.example.speechmate.network.OptimizationResponse;
import com.example.speechmate.network.RecordingData;
import com.example.speechmate.network.TranscriptionResponse;
import com.example.speechmate.data.AppDatabase;
import com.example.speechmate.data.entity.RecordingEntity;

import java.io.File;
import java.util.Date;
import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

@Singleton
public class SpeechRepository {
    private final ApiService apiService;
    private final AppDatabase database;

    @Inject
    public SpeechRepository(ApiService apiService, AppDatabase database) {
        this.apiService = apiService;
        this.database = database;
    }

    public Call<TranscriptionResponse> transcribeAudio(String filePath) {
        File file = new File(filePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("audio/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        RequestBody model = RequestBody.create(MediaType.parse("text/plain"), "FunAudioLLM/SenseVoiceSmall");
        
        return apiService.transcribeAudio(body, model);
    }

    public Call<OptimizationResponse> optimizeText(String text, boolean isTranslation) {
        return apiService.optimizeText(new OptimizationRequest(text, isTranslation));
    }

    public void saveRecording(String filePath, String originalText, String optimizedText, String tags) {
        RecordingEntity recording = new RecordingEntity();
        recording.setFilePath(filePath);
        recording.setOriginalText(originalText);
        recording.setOptimizedText(optimizedText);
        recording.setTags(tags);
        recording.setCreatedAt(new Date());

        new Thread(() -> {
            database.recordingDao().insert(recording);
        }).start();
    }
} 