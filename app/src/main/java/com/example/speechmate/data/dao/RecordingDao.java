package com.example.speechmate.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.example.speechmate.data.entity.RecordingEntity;
import java.util.List;

@Dao
public interface RecordingDao {
    @Insert
    long insert(RecordingEntity recording);

    @Update
    void update(RecordingEntity recording);

    @Delete
    void delete(RecordingEntity recording);

    @Query("SELECT * FROM recordings ORDER BY createdAt DESC")
    LiveData<List<RecordingEntity>> getAllRecordings();

    @Query("SELECT * FROM recordings WHERE id = :id")
    LiveData<RecordingEntity> getRecording(long id);
} 