package com.example.sqlitedemo.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sqlitedemo.utils.DBHelper;
import com.example.sqlitedemo.entity.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements StudentDao {

    private DBHelper helper;
    private SQLiteDatabase db;


    public StudentDaoImpl(Context context){
        //调用DBHelper类的构造方法时
        // 如发现demo.db不存在会调用onCreate创建
        //若发现demo.db存在，且version的版本与已有的不一致，则调用onUpgrade方法更新
        helper= new DBHelper( context,1);
    }
    @Override
    public void insert(Student student) {
        //1、获取db对象
        db= helper.getWritableDatabase();
        String sql="insert into t_student values(null,?,?,?)";
        //执行sql
        db.execSQL(  sql,new Object[]{
                student.getName(),
                student.getGrade(),
                student.getAge()

        }  );

        db.close();
    }

    @Override
    public void update(Student student) {

        //1、获取db对象
        db= helper.getWritableDatabase();
        // 2. 执行sql
        String sql="update t_student  set name= ? where grade =?";
        db.execSQL( sql,new Object[]{
                student.getName(),
                student.getGrade()
        } );



    }

    @Override
    public void delete(String studentName) {


        //1、获取db对象
        db= helper.getWritableDatabase();
        // 2. 执行sql
        String sql="delete from t_student where name =?";
        db.execSQL( sql,new Object[]{studentName});


    }

    @Override
    public List<Student> selectAllStudents() {
        String sql = "select * from t_student";
        List<Student> students = null;

        //1、获取SQLiteDateBase对象
        db = helper.getReadableDatabase();

        // 2. 执行SQL查询
        Cursor cursor = db.rawQuery( sql,null );//跟Result类似
        //3、处理结果
        if (cursor!=null&&cursor.getCount()>0){

            students = new ArrayList<>(  );
            while (cursor.moveToNext()){
                Student student = new Student( );
                student.setId( cursor.getInt( cursor.getColumnIndex( "id" ) ) );
                student.setName( cursor.getString( cursor.getColumnIndex( "name" )));
                student.setGrade( cursor.getString( cursor.getColumnIndex( "grade" ) ) );
                student.setAge( cursor.getInt( cursor.getColumnIndex( "age" ) ) );
                students.add( student );
            }

            //关闭cursor
            cursor.close();
        }

        db.close();
        //4、返回


        return students;
    }


}
