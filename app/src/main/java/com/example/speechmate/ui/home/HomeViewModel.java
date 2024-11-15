package com.example.speechmate.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.speechmate.repository.SpeechRepository;
import com.example.speechmate.network.OptimizationResponse;
import com.example.speechmate.network.TranscriptionResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@HiltViewModel
public class HomeViewModel extends ViewModel {
    private final MutableLiveData<String> originalText = new MutableLiveData<>();
    private final MutableLiveData<String> optimizedText = new MutableLiveData<>();
    private final MutableLiveData<List<String>> tags = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    
    private final SpeechRepository repository;

    @Inject
    public HomeViewModel(SpeechRepository repository) {
        this.repository = repository;
        tags.setValue(new ArrayList<>());
    }

    public void processRecording(String filePath) {
        isLoading.setValue(true);
        error.setValue(null);

        // 1. 转写音频
        repository.transcribeAudio(filePath).enqueue(new Callback<TranscriptionResponse>() {
            @Override
            public void onResponse(Call<TranscriptionResponse> call, Response<TranscriptionResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String transcribedText = response.body().getText();
                    originalText.setValue(transcribedText);
                    
                    // 2. 优化文本
                    optimizeTranscribedText(transcribedText);
                } else {
                    error.setValue("转写失败：" + response.message());
                    isLoading.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<TranscriptionResponse> call, Throwable t) {
                error.setValue("转写失败：" + t.getMessage());
                isLoading.setValue(false);
            }
        });
    }

    private void optimizeTranscribedText(String text) {
        repository.optimizeText(text).enqueue(new Callback<OptimizationResponse>() {
            @Override
            public void onResponse(Call<OptimizationResponse> call, Response<OptimizationResponse> response) {
                isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    String optimized = response.body().getOptimizedText();
                    processOptimizedText(optimized);
                } else {
                    error.setValue("优化失败：" + response.message());
                }
            }

            @Override
            public void onFailure(Call<OptimizationResponse> call, Throwable t) {
                isLoading.setValue(false);
                error.setValue("优化失败：" + t.getMessage());
            }
        });
    }

    private void processOptimizedText(String text) {
        // 分离标签和优化后的文本
        String[] parts = text.split("#");
        if (parts.length > 0) {
            optimizedText.setValue(parts[0].trim());
            
            if (parts.length > 1) {
                List<String> tagList = new ArrayList<>();
                for (int i = 1; i < parts.length; i++) {
                    tagList.add("#" + parts[i].trim());
                }
                tags.setValue(tagList);
            }
        }
    }

    public LiveData<String> getOriginalText() {
        return originalText;
    }

    public LiveData<String> getOptimizedText() {
        return optimizedText;
    }

    public LiveData<List<String>> getTags() {
        return tags;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getError() {
        return error;
    }
} 