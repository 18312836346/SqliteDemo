package com.example.sqlitedemo.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sqlitedemo.entity.Student;
import com.example.sqlitedemo.entity.StudentOrm;
import com.example.sqlitedemo.utils.DBHelper;
import com.example.sqlitedemo.utils.DBOrmHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentOrmDao implements StudentDao {

   private DBOrmHelper helper;
    private SQLiteDatabase db;
    private Dao<StudentOrm,Integer> dao;

    public StudentOrmDao(Context context){
        //调用DBHelper类的构造方法时
        // 如发现demo.db不存在会调用onCreate创建
        //若发现demo.db存在，且version的版本与已有的不一致，则调用onUpgrade方法更新

        try {

            helper=DBOrmHelper.newInstance( context );
            dao = helper.getDao( StudentOrm.class );

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void insert(Student student) {

    }

    @Override
    public void update(Student student) {

    }

    @Override
    public void insert(StudentOrm student) {

        try {

            dao.create( student );

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void update(StudentOrm student) {

        try {
            dao.update( student );

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(String studentName) {


    }

    @Override
    public void delete(int _id) {

    }


    public  void delete(StudentOrm student){
        try {
            dao.delete( student );
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @Override
    public List <Student> selectAllStudents() {
        return null;

    }

    @Override
    public List<StudentOrm> selectAll() {

        List<StudentOrm> students = null;
        try {

            students=dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;

//        //1、获取SQLiteDateBase对象
//        db = helper.getReadableDatabase();
//
//        // 2. 执行SQL查询
//        Cursor cursor = db.rawQuery( sql,null );//跟Result类似
//        //3、处理结果
//        if (cursor!=null&&cursor.getCount()>0){
//
//            students = new ArrayList<>(  );
//            while (cursor.moveToNext()){
//                Student student = new Student( );
//                student.setId( cursor.getInt( cursor.getColumnIndex( "_id" ) ) );
//                student.setName( cursor.getString( cursor.getColumnIndex( "name" )));
//                student.setGrade( cursor.getString( cursor.getColumnIndex( "grade" ) ) );
//                student.setAge( cursor.getInt( cursor.getColumnIndex( "age" ) ) );
//                students.add( student );
//            }
//
//            //关闭cursor
//            cursor.close();
//        }
//
//        db.close();
//        //4、返回
//
    }




    //给CursorAdapter适配器使用
    @Override
    public Cursor selectByCursor() {

        db = helper.getReadableDatabase();
        return db.query( "t_student",
                null,null,
                null,null,
                null,null);


    }

    //条件查询
    @Override
    public Cursor selectByCursor(String condition, String[] conditionArgs) {

        db = helper.getReadableDatabase();
        return db.query( "t_student",
                null,
                condition,conditionArgs,
                null,null,null);



    }




}
