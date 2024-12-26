package com.example.speechmate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.database.Cursor; // 导入 Cursor 类
import android.widget.ImageView;
import android.widget.Toast; // 导入 Toast 类

import com.example.speechmate.data.entity.DatabaseHelper;
import com.example.speechmate.ui.home.HomeFragment;
import com.example.speechmate.ui.profile.ProfileFragment;

public class login extends AppCompatActivity {

//    数据库验证信息
    private EditText editUser, editPassword;
    private Button buttonLogin;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 显示返回按钮
        toolbar.setNavigationIcon(R.drawable.baseline_keyboard_arrow_left_24); // 设置返回图标，确保你有这个图标
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 显示返回按钮
        getSupportActionBar().setTitle("登录"); // 设置标题

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 返回上一个界面
            }
        });

        Button b1=findViewById(R.id.button_2);
        b1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent=new Intent(login.this,register.class);
                startActivity(intent);
            }
        });

//        数据库验证信息
        editUser = findViewById(R.id.edit_user);
        editPassword = findViewById(R.id.edit_paw);
        buttonLogin = findViewById(R.id.button_login);
        databaseHelper = new DatabaseHelper(this);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

    }

//    数据库验证信息
    private void loginUser() {
        String username = editUser.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if (databaseHelper.checkUser(username, password)) {
            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
            // 更新个人中心界面的昵称
            Cursor cursor = databaseHelper.getUser(username);
            if (cursor.moveToFirst()) {
                String sign = cursor.getString(cursor.getColumnIndexOrThrow("sign"));  // 获取签名
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("nickname", username); // 传递用户名作为昵称
                intent.putExtra("sign", sign); // 传递签名
                startActivity(intent);
                finish(); // 结束登录界面
            }
        }
        else {
            Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
        }
    }
}