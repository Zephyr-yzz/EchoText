package com.example.speechmate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import com.example.speechmate.data.entity.DatabaseHelper;
import android.util.Log;

public class eidt extends AppCompatActivity {
    private EditText editUsername, editPassword, editPhone, editSign;
    private Button buttonSave;
    private DatabaseHelper databaseHelper;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eidt);

        // 初始化数据库帮助类
        databaseHelper = new DatabaseHelper(this);

        // 获取 Intent 中传递的用户名
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        Log.d("EditActivity", "Received username: " + username); // 添加日志

        // 设置 Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 启用返回按钮
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_keyboard_arrow_left_24); // 设置返回图标
        getSupportActionBar().setTitle("修改信息");// 设置 Toolbar 标题

        // 初始化 EditText 和 Button
        editUsername = findViewById(R.id.edit_user);
        editPassword = findViewById(R.id.edit_paw);
        editPhone = findViewById(R.id.edit_contact);
        editSign = findViewById(R.id.edit_sign);
        buttonSave = findViewById(R.id.button_save);

        // 加载用户信息
        loadUserData(username);

        // 保存按钮点击事件
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserData();
            }
        });

    }

    private void loadUserData(String username) {
        Log.d("EditActivity", "Loading user data for username: " + username); // 添加日志
        if (username == null) {
                Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            Cursor cursor = databaseHelper.getUser(username);
            if (cursor != null && cursor.moveToFirst()) {
                editUsername.setText(cursor.getString(cursor.getColumnIndexOrThrow("username")));
                editPassword.setText(cursor.getString(cursor.getColumnIndexOrThrow("paw")));
                editPhone.setText(cursor.getString(cursor.getColumnIndexOrThrow("pho")));
                editSign.setText(cursor.getString(cursor.getColumnIndexOrThrow("sign")));
            } else {
                Toast.makeText(this, "用户信息加载失败", Toast.LENGTH_SHORT).show();
            }
    }

    private void saveUserData() {
        String newUsername = editUsername.getText().toString().trim();
        String newPassword = editPassword.getText().toString().trim();
        String newPhone = editPhone.getText().toString().trim();
        String newSign = editSign.getText().toString().trim();

        // 创建AsyncTask来更新用户信息
        UpdateUserTask task = new UpdateUserTask(newUsername, newPassword, newPhone, newSign);
        task.execute();
        
    }

    private class UpdateUserTask extends AsyncTask<Void, Void, Boolean> {
        private String newUsername, newPassword, newPhone, newSign;

        public UpdateUserTask(String newUsername, String newPassword, String newPhone, String newSign) {
            this.newUsername = newUsername;
            this.newPassword = newPassword;
            this.newPhone = newPhone;
            this.newSign = newSign;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            Log.d("EditActivity", "Updating user in background...");
            Log.d("EditActivity", "Updating user with username: " + username + ", newUsername: " + newUsername + ", password: " + newPassword + ", phone: " + newPhone + ", sign: " + newSign);
            return databaseHelper.updateUser(username, newUsername, newPassword, newPhone, newSign);
        }

        @Override
        protected void onPostExecute(Boolean isUpdated) {
            if (isUpdated) {
                Log.d("EditActivity", "Information updated successfully.");
                Toast.makeText(eidt.this, "信息更新成功", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Log.d("EditActivity", "Information update failed.");
                Toast.makeText(eidt.this, "信息更新失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish(); // 返回上一个活动
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}