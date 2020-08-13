package com.neusoft.xiangzi.multimediaplayer;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViolistActivity extends AppCompatActivity {
    /**
     *功能描述：多媒体播放器
     *程序作者：箱子
     */

    MediaController controller;
    List<Map<String, String>> mediaList;
    ListView violistView;
    SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_violist);

        controller = new MediaController(this);

        mediaList = new ArrayList<>();
        violistView = findViewById(R.id.violistView);
        adapter = new SimpleAdapter(this,mediaList,R.layout.videolist,
                new String[]{"title","author","duration"},
                new int[]{R.id.tv_title2,R.id.tv_artist2,R.id.tv_duration2});
        violistView.setAdapter(adapter);

        //注册按钮
        findViewById(R.id.button9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到音乐界面
                Intent i = new Intent(ViolistActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
        findViewById(R.id.button11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到发现界面
                Intent i = new Intent(ViolistActivity.this,RecomActivity.class);
                startActivity(i);
            }
        });
        findViewById(R.id.button12).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到我的界面
                Intent i = new Intent(ViolistActivity.this,HomeActivity.class);
                startActivity(i);
            }
        });

        getAllMediaList(this);
        adapter.notifyDataSetChanged();

        violistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> AdapterView, View view, int i, long l) {
                Intent intent = new Intent(ViolistActivity.this, VideoActivity.class);
                intent.putExtra("path",mediaList.get(i).get("path"));
                intent.putExtra("title",mediaList.get(i).get("title"));
                startActivity(intent);
            }
        });

        //请求访问权限
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    List<Map<String, String>> getAllMediaList(Context context) {
        mediaList.clear();
        Uri uri;
        String[] projection;
        //video
        uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        projection = new String[]{
                MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.ARTIST,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.DURATION,
           };
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        while (cursor.moveToNext()) {
            Map<String, String> m = new HashMap<>();
            m.put("title", cursor.getString(0));
            m.put("author", cursor.getString(1));
            m.put("path", cursor.getString(2));
            m.put("duration", cursor.getString(3));
            mediaList.add(m);
            Log.d("tag",m.toString());
        }
        return mediaList;
    }
}
