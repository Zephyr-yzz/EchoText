package com.example.speechmate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.speechmate.data.entity.DatabaseHelper;


public class register extends AppCompatActivity {

//    设置头像
    private static final int PICK_IMAGE = 1;
    private static final int REQUEST_PERMISSION = 100;
    private ImageView imgAvatar;
//数据库
    private EditText editUser, editPassword, editPhone, editSign;
    private Button buttonRegister;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 显示返回按钮
        toolbar.setNavigationIcon(R.drawable.baseline_keyboard_arrow_left_24); // 设置返回图标，确保你有这个图标
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 显示返回按钮
        getSupportActionBar().setTitle("注册"); // 设置标题

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 返回上一个界面
            }

        });

//        设置头像
        imgAvatar = findViewById(R.id.imgavatar);

        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 检查权限
                if (ContextCompat.checkSelfPermission(register.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // 请求权限
                    ActivityCompat.requestPermissions(register.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
                } else {
                    // 权限已被授予，打开图库
                    openGallery();
                }
            }
        });

//        数据库
        editUser = findViewById(R.id.edit_user);
        editPassword = findViewById(R.id.edit_paw);
        editPhone = findViewById(R.id.edit_contact);
        editSign = findViewById(R.id.edit_sign);
        buttonRegister = findViewById(R.id.button_4);
        databaseHelper = new DatabaseHelper(this);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

//    设置头像及权限申请
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
                // 权限被授予，打开图库
                openGallery();
            } else {
                // 权限被拒绝
                Toast.makeText(this, "权限被拒绝", Toast.LENGTH_SHORT).show();
            }
        }
    }

//    数据库
    private void registerUser() {
        String username = editUser.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String phone = editPhone.getText().toString().trim();
        String sign = editSign.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "用户名、密码和联系方式不能为空", Toast.LENGTH_SHORT).show();
            return;
    }

        boolean isInserted = databaseHelper.insertUser(username, password, phone, sign);
        if (isInserted) {
            Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
            finish(); // 返回登录界面
        }
        else {
            Toast.makeText(this, "注册失败", Toast.LENGTH_SHORT).show();
        }
}
}
