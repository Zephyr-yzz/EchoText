package com.example.speechmate.network;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {
    @Multipart
    @POST("audio/transcriptions")
    Call<TranscriptionResponse> transcribeAudio(
        @Part MultipartBody.Part file,
        @Part("model") RequestBody model
    );

    @POST("chat/completions")
    Call<OptimizationResponse> optimizeText(@Body OptimizationRequest request);
} 