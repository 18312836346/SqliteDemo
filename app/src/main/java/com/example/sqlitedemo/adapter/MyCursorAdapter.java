package com.example.sqlitedemo.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.sqlitedemo.R;
import com.example.sqlitedemo.activity.MainActivity;
import com.example.sqlitedemo.entity.Student;

import java.util.List;

public class MyCursorAdapter extends CursorAdapter {


    public MyCursorAdapter(Context context, Cursor c) {
        super( context, c, 0 );
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {


        View  view = LayoutInflater.from(context).inflate( R.layout.item_student,parent,
                false);
        return view;
    }

    //设置每个控件
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView tvName = view.findViewById( R.id.tv_name );
        TextView tvGrade = view.findViewById( R.id.tv_grade );
        TextView tvAge = view.findViewById( R.id.tv_age );


        //赋值
        tvName.setText( cursor.getString( cursor.getColumnIndex( "name" ) ) );
        tvGrade.setText( cursor.getString( cursor.getColumnIndex( "grade" ) ) );
        tvAge.setText( String.valueOf(cursor.getInt( cursor.getColumnIndex("age" ))));
    }


}
