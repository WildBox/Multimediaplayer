package com.neusoft.xiangzi.multimediaplayer;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class RecomActivity extends AppCompatActivity {
    /**
     *功能描述：多媒体播放器
     *程序作者：箱子
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recom);

        WebView wv = findViewById(R.id.webView);
        wv.loadUrl("https://y.music.163.com/m/");
    }
}