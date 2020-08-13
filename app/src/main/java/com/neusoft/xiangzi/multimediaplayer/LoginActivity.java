package com.neusoft.xiangzi.multimediaplayer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    /**
     *功能描述：多媒体播放器
     *程序作者：箱子
     */

    EditText etAccount;
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etAccount = findViewById(R.id.editText);
        etPassword = findViewById(R.id.editText2);

        findViewById(R.id.button14).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到注册界面
                Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });

        findViewById(R.id.button13).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //验证登录账号，密码
                String inputAccount = etAccount.getText().toString();
                String inputPwd = etPassword.getText().toString();
                //从preferences中获取注册的用户名密码
                SharedPreferences preferences = getSharedPreferences("user_data",MODE_PRIVATE);
                String regAccount = preferences.getString("user_account","");
                String regNPwd = preferences.getString("user_password","");

                //使用数据库//账号密码验证验证用账号、密码
                DBHelper db = new DBHelper(LoginActivity.this);
                db.open();
                if (db.checkAccountPassword(inputAccount, inputPwd)) {
                    //验证成功
                    Intent i = new Intent(LoginActivity.this,HomeActivity.class);
                    startActivity(i);
                }else {
                    //验证失败etAccount
                    Toast.makeText(LoginActivity.this,
                            "账号或密码错误！",Toast.LENGTH_SHORT).show();
                };
            }
        });
    }
}
