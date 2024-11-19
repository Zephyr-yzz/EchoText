package com.example.speechmate.di;

import android.content.Context;
import androidx.room.Room;
import com.example.speechmate.data.AppDatabase;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {
    
    @Provides
    @Singleton
    public AppDatabase provideDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context,
                AppDatabase.class, "speechmate_database")
                .fallbackToDestructiveMigration()  // 允许数据库重建
                .build();
    }
} 