package com.example.speechmate.ui.profile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.example.speechmate.databinding.FragmentProfileBinding;
import com.example.speechmate.login;
import com.example.speechmate.HelpCenterActivity;
import com.example.speechmate.eidt;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        // 绑定用户名点击事件
        binding.username.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), login.class);
            startActivity(intent);
        });

        // 绑定帮助中心点击事件
        binding.rlvHelp.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), HelpCenterActivity.class);
            startActivity(intent);
        });

        // 绑定头像点击事件
        binding.avatar.setOnClickListener(v -> {
            // 检查权限
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            } else {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        // 绑定编辑资料点击事件
        binding.rlvEdit.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), eidt.class);
            startActivity(intent);
        });

        // 绑定关于我们点击事件
        binding.rlvAbout.setOnClickListener(v -> {
            // TODO: 实现关于我们的弹窗或页面跳转
        });

        // 绑定隐私政策点击事件 
        binding.rlvPolicy.setOnClickListener(v -> {
            // TODO: 实现隐私政策的弹窗或页面跳转
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
} 