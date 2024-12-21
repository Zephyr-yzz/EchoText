package com.example.speechmate;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class Mine extends AppCompatActivity {
    private ImageView imageViewAbout;
    private ImageView imageViewHelp;
    private ImageView imageViewPolicy;
    private Dialog aboutDialog;
    private Dialog helpDialog;
    private Dialog policyDialog;//隐私弹窗

    private static final int PICK_IMAGE = 1;
    private static final int REQUEST_PERMISSION = 100;
    private ImageView imgAvatar;
    private TextView textNickname;
    private TextView textSign;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);
        TextView textView=findViewById(R.id.username);
        textView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(Mine.this,login.class);
                startActivity(intent);
            }
        });

//        更新用户名和签名
        textNickname = findViewById(R.id.username);
        textSign = findViewById(R.id.sign);

        String nickname = getIntent().getStringExtra("nickname");
        String sign = getIntent().getStringExtra("sign");
        if (nickname != null) {
            textNickname.setText(nickname);
        }
        else {
            textNickname.setText("登录/注册"); // 设置默认昵称
        }
        if (sign != null) {
            textSign.setText(sign); // 更新签名
        }
        else {
            textSign.setText("这个人很懒，什么都没有留下~"); // 设置默认签名
        }

//        设置头像
        imgAvatar = findViewById(R.id.avatar); // 确保这里的 ID 与布局文件一致

        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 检查权限
                if (ContextCompat.checkSelfPermission(Mine.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Mine.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
                } else {
                    openGallery();
                }
            }
        });

        // 初始化ImageView
        imageViewAbout = findViewById(R.id.rlv_about);// 确保rlv_about是activity_main.xml中的ImageView ID
        imageViewHelp = findViewById(R.id.rlv_help);
        imageViewPolicy = findViewById(R.id.rlv_policy);

// 设置ImageView的点击事件监听器
        imageViewAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 显示关于Dialog
                showAboutDialog();
            }
        });
        imageViewPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shouPolicyDialog();
            }


        });
        // 关闭当前页面，跳转到帮助页面
        imageViewHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Mine.this, HelpCenterActivity.class);
                startActivity(intent);
            }
        });



//        intent=new Intent(getActivity(), UserLoginActivity.class);
//        startActivity(intent);
        //修改信息
        ImageView editButton = findViewById(R.id.edit);
//        Intent intent = getIntent();
        String currentUsername = getIntent().getStringExtra("username"); // 获取传递的用户名
        Log.d("MineActivity", "Current username: " + currentUsername); // 添加日志
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Log.d("MineActivity", "Edit button clicked,username"+currentUsername);
                Intent editintent = new Intent(Mine.this, eidt.class);
                editintent.putExtra("username", currentUsername); // currentUsername 是当前登录用户的用户名
                startActivity(editintent);
            }
        });


    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            imgAvatar.setImageURI(selectedImage); // 设置选择的头像
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                Toast.makeText(this, "权限被拒绝", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //
    private void shouPolicyDialog() {
        policyDialog = new Dialog(this, android.R.style.ThemeOverlay_Material_Dialog_Alert);
        policyDialog.setContentView(R.layout.privacy_policy);

        // 查找对话框中的按钮
        RadioButton radioAgree = policyDialog.findViewById(R.id.radio_agree);
        RadioButton radioExit = policyDialog.findViewById(R.id.radio_exit);

        radioAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                policyDialog.dismiss();
                // 同意隐私政策的额外逻辑（如果需要）
            }
        });

        radioExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建一个确认退出的对话框
                AlertDialog.Builder exitDialogBuilder = new AlertDialog.Builder(Mine.this); // 假设这是你的活动类名

                // 使用自定义布局
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.confirm, null);
                exitDialogBuilder.setView(dialogView);

                // 获取 TextView 并设置颜色
                TextView dialogMessage = dialogView.findViewById(R.id.dialog_message);
                dialogMessage.setTextColor(getResources().getColor(R.color.black));
                exitDialogBuilder.setTitle("确认退出")
                        .setCancelable(false) // 如果不希望对话框可以通过点击外部区域取消，设置为false
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 用户点击了确定按钮，退出应用
                                finish(); // 这将关闭当前活动，如果这是主活动，应用将退出
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 用户点击了取消按钮，什么也不做
                                dialog.dismiss();
                            }
                        });

                // 显示确认退出的对话框
                AlertDialog exitDialog = exitDialogBuilder.create();
                exitDialog.show();

                // 注意：这里不需要再调用 policyDialog.dismiss(); 因为用户还没有决定是否退出
            }
        });

        policyDialog.show();
    }


    private void showAboutDialog() {
        // 创建Dialog对象
        aboutDialog = new Dialog(this,android.R.style.ThemeOverlay_Material_Dialog_Alert);
        // 加载自定义布局
        aboutDialog.setContentView(R.layout.about);
        // 显示Dialog
        aboutDialog.show();
    }
}