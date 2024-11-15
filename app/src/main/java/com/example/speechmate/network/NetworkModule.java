package com.example.speechmate.network;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import javax.inject.Singleton;

@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {
    private static final String BASE_URL = "https://api.siliconflow.cn/v1/";
    private static final String API_KEY = "sk-ocmpdklzmrhsktdsuqnarbpdpzgfzmkyijuwbyjfkblqlcua";

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
            .addInterceptor(chain -> chain.proceed(
                chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer " + API_KEY)
                    .build()))
            .addInterceptor(new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY))
            .build();
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }

    @Provides
    @Singleton
    public ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }
} 