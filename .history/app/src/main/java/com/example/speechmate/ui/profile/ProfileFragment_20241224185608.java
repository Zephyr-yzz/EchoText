package com.example.speechmate.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.speechmate.databinding.FragmentProfileBinding;
import com.example.speechmate.login;
import com.example.speechmate.HelpCenterActivity;
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        // 绑定用户名点击事件
        binding.username.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), login.class);
            startActivity(intent);
        });

        // 设置默认用户名和签名
        String nickname = getActivity().getIntent().getStringExtra("nickname");
        String sign = getActivity().getIntent().getStringExtra("sign");
        if (nickname != null) {
            binding.username.setText(nickname);
        } else {
            binding.username.setText("登录/注册");
        }
        if (sign != null) {
            binding.sign.setText(sign);
        } else {
            binding.sign.setText("这个人很懒，什么都没有留下~");
        }

        // 绑定头像点击事件
        binding.avatar.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            } else {
                openGallery();
            }
        });

        // 绑定帮助中心点击事件
        binding.rlvHelp.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), HelpCenterActivity.class);
            startActivity(intent);
        });

        // 绑定关于点击事件
        binding.rlvAbout.setOnClickListener(v -> {
            showAboutDialog();
        });

        // 绑定隐私政策点击事件
        binding.rlvPolicy.setOnClickListener(v -> {
            showPolicyDialog();
        });

        // 绑定编辑按钮点击事件
        String currentUsername = getActivity().getIntent().getStringExtra("username");
        binding.edit.setOnClickListener(v -> {
            Intent editIntent = new Intent(getActivity(), eidt.class);
            editIntent.putExtra("username", currentUsername);
            startActivity(editIntent);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
} 