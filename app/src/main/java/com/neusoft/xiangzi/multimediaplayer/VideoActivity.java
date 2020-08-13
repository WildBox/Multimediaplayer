package com.neusoft.xiangzi.multimediaplayer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class VideoActivity extends AppCompatActivity {
    /**
     *功能描述：多媒体播放器
     *程序作者：箱子
     */

    VideoView videoView;
    MediaController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        videoView = findViewById(R.id.videoView);

        //注册按钮
        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到音乐界面
                Intent i = new Intent(VideoActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回主视频界面
                Intent i = new Intent(VideoActivity.this, ViolistActivity.class);
                startActivity(i);
            }
        });
        findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到发现界面
                Intent i = new Intent(VideoActivity.this,RecomActivity.class);
                startActivity(i);
            }
        });
        findViewById(R.id.button8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到我的界面
                Intent i = new Intent(VideoActivity.this,HomeActivity.class);
                startActivity(i);
            }
        });

        Intent intent = getIntent();
        String path = intent.getStringExtra("path");
        String title = intent.getStringExtra("title");
        TextView textView = findViewById(R.id.textView4);
        textView.setText(title);
        videoView.setVideoURI(Uri.parse(path));
        videoView.start();
        //设置媒体播放控制器
        controller = new MediaController(this);
        videoView.setMediaController(controller);
}
}
