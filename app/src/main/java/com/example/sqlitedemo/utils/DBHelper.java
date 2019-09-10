package com.example.sqlitedemo.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sqlitedemo.entity.Student;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper( Context context,int version) {
        super( context, "demo.db", null, version );
    }

    //1、当app发现没有demo.db时会自动调用onCreate创建数据库
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL( Student.TBL_STUDENT );
    }

    //2、当app发现有demo.db时，
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL( "drop table if exists t_student" );

        onCreate( db );
    }
}
