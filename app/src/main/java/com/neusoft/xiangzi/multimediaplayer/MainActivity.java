package com.neusoft.xiangzi.multimediaplayer;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     *功能描述：多媒体播放器
     *程序作者：箱子
     */

    MediaPlayer player;
    ProgressBar progressBar;
    List<Map<String, String>> mediaList;
    ListView listView;
    SimpleAdapter adapter;
    int currentMusicIndex;//当前播放的歌曲的索引
    int flag = 0;//歌曲循环
    Button play_bt;
    Button cycle_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaList = new ArrayList<>();
        listView = findViewById(R.id.listView);
        adapter = new SimpleAdapter(this,mediaList,R.layout.musiclist,
                new String[]{"title","author","duration"},
                new int[]{R.id.tv_title,R.id.tv_artist,R.id.tv_duration});
        listView.setAdapter(adapter);
        play_bt = findViewById(R.id.bt_play);
        cycle_bt = findViewById(R.id.bt_cycle);

        //进度条
        progressBar = findViewById(R.id.progressBar);

        player = MediaPlayer.create(this,R.raw.light);

        //注册按钮
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到视频界面
                Intent i = new Intent(MainActivity.this,ViolistActivity.class);
                startActivity(i);
            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到发现界面
                Intent i = new Intent(MainActivity.this,RecomActivity.class);
                startActivity(i);
            }
        });
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到我的界面
                Intent i = new Intent(MainActivity.this,HomeActivity.class);
                startActivity(i);
            }
        });
        findViewById(R.id.bt_play).setOnClickListener(this);
        findViewById(R.id.bt_last).setOnClickListener(this);
        findViewById(R.id.bt_next).setOnClickListener(this);
        findViewById(R.id.bt_cycle).setOnClickListener(this);

        //进程更新线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                //线程运行内容
                while (true) {
                    //更新进度条
                    if(player.isPlaying()) {
                        progressBar.setProgress(player.getCurrentPosition());
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        //点击listView播放
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> AdapterView, View view, int i, long l) {
                player.reset();
                currentMusicIndex = i;
                try {
                    player.setDataSource(mediaList.get(i).get("path"));
                    player.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                progressBar.setMax(player.getDuration());
                player.start();
            }
        });

        getAllMediaList(this);
        adapter.notifyDataSetChanged();

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
        //music
        uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        projection = new String[]{
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION,
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

    //单曲循环播放
    private void single() {
        Toast.makeText(MainActivity.this,"当前为单曲循环",Toast.LENGTH_SHORT).show();
        player.setLooping(true);
        player.start();
    }

    //随机播放
    private void random() {
        Toast.makeText(MainActivity.this, "当前为随机循环", Toast.LENGTH_SHORT).show();
        currentMusicIndex = new Random().nextInt(mediaList.size());
        try {
            player.setDataSource(mediaList.get(currentMusicIndex).get("path"));
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        progressBar.setMax(player.getDuration());
        player.start();
    }

    //列表循环播放
    private void sequence() {
        Toast.makeText(MainActivity.this,"当前为列表循环",Toast.LENGTH_SHORT).show();
        if (currentMusicIndex >= mediaList.size() - 1) {
            currentMusicIndex = 0;
            player.reset();
            try {
                player.setDataSource(mediaList.get(currentMusicIndex).get("path"));
                player.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            progressBar.setMax(player.getDuration());
            player.start();
        } else {
            currentMusicIndex++;
            player.reset();
            try {
                player.setDataSource(mediaList.get(currentMusicIndex).get("path"));
                player.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            progressBar.setMax(player.getDuration());
            player.start();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_play:
                if (player.isPlaying()) {
                    progressBar.setMax(player.getDuration());
                    player.pause();
                    play_bt.setText("播放");
                } else {
                    progressBar.setMax(player.getDuration());
                    player.start();
                    play_bt.setText("暂停");
                }
                break;
            case R.id.bt_last:
                if (currentMusicIndex <= 0) {
                    Toast.makeText(MainActivity.this, "已经是第一首了", Toast.LENGTH_SHORT).show();
                } else {
                    currentMusicIndex--;
                    player.reset();
                    try {
                        player.setDataSource(mediaList.get(currentMusicIndex).get("path"));
                        player.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    player.start();
                }
                break;
            case R.id.bt_next:
                if (currentMusicIndex >= mediaList.size() - 1) {
                    Toast.makeText(MainActivity.this, "已经是最后一首了", Toast.LENGTH_SHORT).show();
                } else {
                    currentMusicIndex++;
                    player.reset();
                    try {
                        player.setDataSource(mediaList.get(currentMusicIndex).get("path"));
                        player.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    player.start();
                }
                break;
            case R.id.bt_cycle:
                flag++;
                if (flag >= 4) {
                    flag = 1;
                    sequence();
                    cycle_bt.setText("列表");
                } else {
                    if (flag == 1) {
                        sequence();
                        cycle_bt.setText("列表");
                    } else if (flag == 2) {
                        single();
                        cycle_bt.setText("单曲");
                    } else if (flag == 3) {
                        random();
                        cycle_bt.setText("随机");
                    }
                }
                break;
        }
    }
}
