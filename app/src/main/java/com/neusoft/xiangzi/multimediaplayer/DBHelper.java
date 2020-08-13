package com.neusoft.xiangzi.multimediaplayer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBHelper {
    private static final String TAG = "DBHelper";
    SQLiteDatabase db;
    Context context;

    public DBHelper(Context context) {
        this.context = context;
    }

    public boolean open() {
        String path = context.getFilesDir() +"/"+"user_info.db";
        db = SQLiteDatabase.openOrCreateDatabase(path,null);

        //创建表格
        String sql = "create table if not exists user_info"+
                "(username varchar(50) , useraccount varchar(50) primary key,"+
                "password varchar(20), fullname varchar(50))";
        try {
            db.execSQL(sql);
            return true;
        }catch (Exception e){
            Log.e(TAG, "open: error "+e.toString());
            e.printStackTrace();
            return false;
        }
    }

    public boolean insert(String username, String useraccount ,String password, String fullname) {

        if(db!=null && db.isOpen()) {
            //准备插入数据
            ContentValues values = new ContentValues();
            values.put("username",username);
            values.put("useraccount",useraccount);
            values.put("password",password);
            values.put("fullname",fullname);

            //进行数据库插入
            long c = db.insert("user_info",null,values);

            if (c > 0) return true;
            else return false;
        }else {
            return false;
        }
    }

    public boolean checkAccountPassword(String account, String password) {

        Cursor c = db.query("user_info",//表名
                null,//要显示的列名，相当于select后面的内容
                "useraccount=? and password=?",//相当于where后面的内容
                new String[]{account,password},//替代selection中问号内容
                null, null, null);
        if (c.getCount() > 0) return true;
        else return false;
    }
}
