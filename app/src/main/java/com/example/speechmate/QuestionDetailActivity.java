package com.example.speechmate;

// QuestionDetailActivity.java

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

public class QuestionDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Toolbar toolbar = findViewById(R.id.toolbar2);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 显示返回按钮
        toolbar.setNavigationIcon(R.drawable.baseline_keyboard_arrow_left_24); // 设置返回图标，确保你有这个图标
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 显示返回按钮
        getSupportActionBar().setTitle("问题答案"); // 设置标题


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 返回上一个界面
            }
        });

        // 从Intent中获取问题文本
        Intent intent = getIntent();
        String question = intent.getStringExtra("question");

        // 这里应该有一个机制来获取问题的回答，这里简单起见，我们假设有一个固定的回答
        String answer = getAnswerForQuestion(question);

        // 设置TextView来显示回答
        TextView answerTextView = findViewById(R.id.answer_text_view);
        answerTextView.setText(answer);
    }

    // 这是一个简单的方法，用于根据问题返回回答（在实际应用中，你应该从数据库或API中获取回答）
    private String getAnswerForQuestion(String question) {
        if (question.equals("How do I create an account?")) {
            return "To create an account, go to the Sign Up page and fill in the required information.";
        } else if (question.equals("What are the login requirements?")) {
            return "You need a valid email address and password to log in.";
        }
        // 添加更多问题和回答...
        return "Sorry, no answer available for this question.";
    }
}