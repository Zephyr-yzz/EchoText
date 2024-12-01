package com.example.speechmate.ui.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.speechmate.R;
import com.example.speechmate.databinding.FragmentHomeBinding;
import com.google.android.material.chip.Chip;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment {
    private static final int PERMISSION_REQUEST_CODE = 1;
    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;
    private MediaRecorder mediaRecorder;
    private boolean isRecording = false;
    private String currentRecordingPath;

    private enum RecordingState {
        IDLE,
        RECORDING,
        PAUSED
    }

    private RecordingState recordingState = RecordingState.IDLE;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        
        setupRecordButton();
        observeViewModel();
    }

    private void setupRecordButton() {
        binding.fabRecord.setOnClickListener(v -> {
            if (checkPermissions()) {
                switch (recordingState) {
                    case IDLE:
                        startRecording();
                        break;
                    case RECORDING:
                        pauseRecording();
                        break;
                    case PAUSED:
                        resumeRecording();
                        break;
                }
            } else {
                requestPermissions();
            }
        });
    }

    private boolean checkPermissions() {
        return ContextCompat.checkSelfPermission(requireContext(), 
                Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(requireActivity(),
                new String[]{Manifest.permission.RECORD_AUDIO},
    
                PERMISSION_REQUEST_CODE);
    }

    private void startRecording() {
        currentRecordingPath = generateRecordingFilePath();
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mediaRecorder.setOutputFile(currentRecordingPath);

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
            isRecording = true;
            binding.fabRecord.setImageResource(R.drawable.ic_stop);
            Toast.makeText(requireContext(), "录音开始", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "录音失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopRecording() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            isRecording = false;
            binding.fabRecord.setImageResource(R.drawable.ic_mic);
            Toast.makeText(requireContext(), "录音结束", Toast.LENGTH_SHORT).show();
            
            // 开始处理录音文件
            viewModel.processRecording(currentRecordingPath);
        }
    }

    private String generateRecordingFilePath() {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File recordingsDir = new File(requireContext().getExternalFilesDir(null), "Recordings");
        if (!recordingsDir.exists()) {
            recordingsDir.mkdirs();
        }
        return new File(recordingsDir, "REC_" + timestamp + ".mp3").getAbsolutePath();
    }

    private void observeViewModel() {
        viewModel.getOriginalText().observe(getViewLifecycleOwner(), text -> {
            binding.tvOriginalText.setText(text);
        });

        viewModel.getOptimizedText().observe(getViewLifecycleOwner(), text -> {
            binding.tvOptimizedText.setText(text);
        });

        viewModel.getTags().observe(getViewLifecycleOwner(), tags -> {
            binding.chipGroupTags.removeAllViews();
            for (String tag : tags) {
                Chip chip = new Chip(requireContext());
                chip.setText(tag);
                binding.chipGroupTags.addView(chip);
            }
        });

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            binding.fabRecord.setEnabled(!isLoading);
        });

        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (isRecording) {
            stopRecording();
        }
        binding = null;
    }
} 