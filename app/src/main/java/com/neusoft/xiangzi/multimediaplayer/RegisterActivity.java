package com.neusoft.xiangzi.multimediaplayer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    /**
     *功能描述：多媒体播放器
     *程序作者：箱子
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void registerClick(View v) {
        //获取SharedPreferences对象，准备操作数据
        SharedPreferences preferences = getSharedPreferences("user_data",MODE_PRIVATE);
        //获取昵称 账号 密码 真实姓名数据
        EditText etName = findViewById(R.id.editText3);
        EditText etAccount = findViewById(R.id.editText4);
        EditText etPassword = findViewById(R.id.editText5);
        EditText etRealName = findViewById(R.id.editText6);
        //保存注册信息
        SharedPreferences.Editor editor = preferences.edit();//获取编辑器
        editor.putString("user_name",etName.getText().toString());//保存昵称
        editor.putString("user_account",etName.getText().toString());//保存账号
        editor.putString("user_password",etPassword.getText().toString());//保存密码
        editor.putString("user_realname",etRealName.getText().toString());//保存真实姓名
        //提交保存
        editor.commit();

        //信息保存到数据库
        boolean ret;
        DBHelper dbHelper = new DBHelper(this);
        dbHelper.open();
        ret = dbHelper.insert(etName.getText().toString(),
                etAccount.getText().toString(),
                etPassword.getText().toString(),
                etRealName.getText().toString());
        if (!ret) {
            Toast.makeText(this,"数据库操作失败！",Toast.LENGTH_SHORT).show();
        }

        //返回登录界面
        Toast.makeText(this,"账号注册成功!",Toast.LENGTH_SHORT).show();
        finish();
    }
}
