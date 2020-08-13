package com.neusoft.xiangzi.multimediaplayer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    /**
     *功能描述：多媒体播放器
     *程序作者：箱子
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences preferences = getSharedPreferences("user_data",MODE_PRIVATE);
        String name = preferences.getString("user_name","");
        TextView textView = findViewById(R.id.textView3);
        textView.setText(name);

        findViewById(R.id.button19).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到音乐界面
                Intent i = new Intent(HomeActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
        findViewById(R.id.button20).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到视频界面
                Intent i = new Intent(HomeActivity.this,ViolistActivity.class);
                startActivity(i);
            }
        });
        findViewById(R.id.button21).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到发现界面
                Intent i = new Intent(HomeActivity.this,RecomActivity.class);
                startActivity(i);
            }
        });
        findViewById(R.id.button23).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到登录界面
                Intent i = new Intent(HomeActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
    }
}
