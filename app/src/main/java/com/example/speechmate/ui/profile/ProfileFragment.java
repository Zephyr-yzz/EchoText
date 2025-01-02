package com.example.speechmate.ui.profile;
import static android.content.Intent.getIntent;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.speechmate.HelpCenterActivity;
import com.example.speechmate.MainActivity;
import com.example.speechmate.R;
import com.example.speechmate.databinding.FragmentProfileBinding;
import com.example.speechmate.eidt;
import com.example.speechmate.login;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProfileFragment extends Fragment {
    private ImageView imageViewAbout;
    private ImageView imageViewHelp;
    private ImageView imageViewPolicy;
    private Dialog aboutDialog;
    private Dialog helpDialog;
    private Dialog policyDialog;//隐私弹窗
    private FragmentProfileBinding binding;
    private static final int PICK_IMAGE = 1;
    private static final int REQUEST_PERMISSION = 100;
    private ImageView imgAvatar;
    private TextView textNickname;
    private TextView textSign;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

//     public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//         super.onViewCreated(view, savedInstanceState);
        
//         // 使用binding来访问Fragment中的视图
//         binding.username.setOnClickListener(v -> {
//             Intent intent = new Intent(getActivity(), login.class);
//             startActivity(intent);
//         });

//         // 绑定帮助中心点击事件
//         binding.rlvHelp.setOnClickListener(v -> {
//             Intent intent = new Intent(getActivity(), HelpCenterActivity.class);
//             startActivity(intent);
//         });

//         // 其他点击事件绑定...
//     }
//     @Override
//     public void onDestroyView() {
//         super.onDestroyView();
//         binding = null;
//     }
// } 
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 绑定用户名点击事件
        binding.username.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), login.class);
            startActivity(intent);
        });

        // 绑定头像点击事件
        binding.avatar.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            } else {
                openGallery();
            }
        });

        // 绑定修改信息点击事件
        binding.edit.setOnClickListener(v -> {
            String currentUsername = binding.username.getText().toString();
            Intent editIntent = new Intent(getActivity(), eidt.class);
            editIntent.putExtra("username", currentUsername);
            startActivity(editIntent);
        });

        // 绑定使用帮助点击事件
        binding.rlvHelp.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), HelpCenterActivity.class);
            startActivity(intent);
        });

        // 绑定关于我们点击事件
        binding.rlvAbout.setOnClickListener(v -> {
            showAboutDialog();
        });

        // 绑定隐私政策点击事件
        binding.rlvPolicy.setOnClickListener(v -> {
            showPolicyDialog();
        });

        // 绑定退出登录点击事件
        binding.quit.setOnClickListener(v -> {
            // 设置 MainActivity 中的 isLogin 为 false
            MainActivity.isLogin = false; // 确保 MainActivity 中的 isLogin 是 public static
            MainActivity.Name = null; // 恢复默认值
            MainActivity.Sign = null; // 恢复默认值

            // 返回到登录界面
            Intent intent = new Intent(getActivity(), login.class);
            startActivity(intent);
            requireActivity().finish(); // 结束当前 Activity
        });

        // 接收登录传递的用户信息
        if (getArguments() != null) {
            String nickname = getArguments().getString("nickname");
            String sign = getArguments().getString("sign");
            Log.d("ProfileFragment", "Nickname: " + nickname + ", Sign: " + sign); // Debug log
            if (nickname != null) {
                Log.d("123","1234");
                binding.username.setText(nickname); // 更新昵称
            } else {
                binding.username.setText("用户12345"); // 设置默认值
            }
            if (sign != null) {
                binding.sign.setText(sign); // 更新签名
            } else {
                binding.sign.setText("这个人很懒，什么都没留下~"); // 设置默认值
            }
        } else {
            // 设置默认值
            binding.username.setText("登录/注册"); // 设置默认用户名
            binding.sign.setText("这个人很懒，什么都没留下~"); // 设置默认签名
        }
    }


    // 打开图库方法
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 100);
    }

    // 处理图片选择结果
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            binding.avatar.setImageURI(selectedImage);
        }
    }

    // 处理权限请求结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                Toast.makeText(requireContext(), "权限被拒绝", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // 显示关于我们对话框
    private void showAboutDialog() {
        Dialog aboutDialog = new Dialog(requireContext(), android.R.style.ThemeOverlay_Material_Dialog_Alert);
        aboutDialog.setContentView(R.layout.about);
        aboutDialog.show();
    }

    // 显示隐私政策对话框
    private void showPolicyDialog() {
        Dialog policyDialog = new Dialog(requireContext(), android.R.style.ThemeOverlay_Material_Dialog_Alert);
        policyDialog.setContentView(R.layout.privacy_policy);

        RadioButton radioAgree = policyDialog.findViewById(R.id.radio_agree);
        RadioButton radioExit = policyDialog.findViewById(R.id.radio_exit);

        radioAgree.setOnClickListener(v -> policyDialog.dismiss());
        radioExit.setOnClickListener(v -> requireActivity().finish());

        policyDialog.show();
    }

    @Override
    public void onStart(){
        super.onStart();

    }
}
