package com.example.speechmate.ui.history;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.speechmate.data.entity.RecordingEntity;
import com.example.speechmate.databinding.FragmentHistoryBinding;
import dagger.hilt.android.AndroidEntryPoint;
import java.io.IOException;

@AndroidEntryPoint
public class HistoryFragment extends Fragment implements RecordingAdapter.OnRecordingClicListener {
    private FragmentHistoryBinding binding;
    private HistoryViewModel viewModel;
    private RecordingAdapter adapter;
    private MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HistoryViewModel.class);
        
        setupRecyclerView();
        observeViewModel();
    }

    private void setupRecyclerView() {
        adapter = new RecordingAdapter(this);
        binding.recyclerView.setAdapter(adapter);
    }

    private void observeViewModel() {
        viewModel.getAllRecordings().observe(getViewLifecycleOwner(), recordings -> {
            adapter.setRecordings(recordings);
        });
    }

    @Override
    public void onPlayClick(RecordingEntity recording) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(recording.getFilePath());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            Toast.makeText(requireContext(), "播放失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDeleteClick(RecordingEntity recording) {
        viewModel.deleteRecording(recording);
    }

    @Override
    public void onEditClick(RecordingEntity recording) {
        Toast.makeText(requireContext(), "开始编辑", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveClick(RecordingEntity recording, String originalText, String optimizedText) {
        viewModel.updateRecording(recording, originalText, optimizedText);
        Toast.makeText(requireContext(), "保存成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        binding = null;
    }
} 