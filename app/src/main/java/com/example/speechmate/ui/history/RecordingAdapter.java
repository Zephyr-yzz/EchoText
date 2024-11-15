package com.example.speechmate.ui.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.speechmate.data.entity.RecordingEntity;
import com.example.speechmate.databinding.ItemRecordingHistoryBinding;
import com.google.android.material.chip.Chip;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecordingAdapter extends RecyclerView.Adapter<RecordingAdapter.RecordingViewHolder> {
    private List<RecordingEntity> recordings = new ArrayList<>();
    private final OnRecordingClickListener listener;

    public interface OnRecordingClickListener {
        void onPlayClick(RecordingEntity recording);
        void onDeleteClick(RecordingEntity recording);
    }

    public RecordingAdapter(OnRecordingClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecordingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRecordingHistoryBinding binding = ItemRecordingHistoryBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new RecordingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordingViewHolder holder, int position) {
        holder.bind(recordings.get(position));
    }

    @Override
    public int getItemCount() {
        return recordings.size();
    }

    public void setRecordings(List<RecordingEntity> recordings) {
        this.recordings = recordings;
        notifyDataSetChanged();
    }

    class RecordingViewHolder extends RecyclerView.ViewHolder {
        private final ItemRecordingHistoryBinding binding;
        private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

        RecordingViewHolder(ItemRecordingHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(RecordingEntity recording) {
            binding.tvDate.setText(dateFormat.format(recording.getCreatedAt()));
            binding.tvOriginalText.setText(recording.getOriginalText());
            binding.tvOptimizedText.setText(recording.getOptimizedText());

            // 设置标签
            binding.chipGroupTags.removeAllViews();
            if (recording.getTags() != null) {
                String[] tags = recording.getTags().split(",");
                for (String tag : tags) {
                    if (!tag.trim().isEmpty()) {
                        Chip chip = new Chip(binding.getRoot().getContext());
                        chip.setText(tag.trim());
                        binding.chipGroupTags.addView(chip);
                    }
                }
            }

            // 设置点击事件
            binding.btnPlay.setOnClickListener(v -> listener.onPlayClick(recording));
            binding.btnDelete.setOnClickListener(v -> listener.onDeleteClick(recording));
        }
    }
} 