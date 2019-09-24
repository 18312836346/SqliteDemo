package com.example.sqlitedemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.sqlitedemo.R;
import com.example.sqlitedemo.adapter.MyCursorAdapter;
import com.example.sqlitedemo.dao.StudentDao;
import com.example.sqlitedemo.dao.StudentDaoImpl;
import com.example.sqlitedemo.entity.Student;
import com.example.sqlitedemo.entity.StudentOrm;
import com.example.sqlitedemo.service.StudentService;
import com.example.sqlitedemo.service.StudentServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class MainOrmActivity extends AppCompatActivity implements View.OnClickListener {


    private static final int ADD_REQUEST = 100;
    private static final int MODIFY_REQUEST = 101;

    private ListView studentList;
    private List <Student> students;

//    private StudentAdapter studentAdapter;

    private MyCursorAdapter studentAdapter;

    private  StudentDao dao;


    private StudentService studentService;



    private Button insert;
    private Button update;
    private  Button delete;

    private Student selectedStudent;
    private StudentDaoImpl studentDaoImpl;
    private int selectedPos;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );


        dao =new StudentDaoImpl(this);


        insert = findViewById( R.id.btn_insert );
        update = findViewById( R.id.btn_update );
        delete = findViewById( R.id.btn_delete );




        insert.setOnClickListener( this );
        update.setOnClickListener( this );

        delete.setOnClickListener( this );

        // 从SQLite数据库获取数据
        initData();
        initView();
    }

    private void initView() {

        studentList = findViewById( R.id.lv_student );

        studentAdapter = new MyCursorAdapter(this,dao.selectByCursor());

        studentList.setAdapter(studentAdapter);

        studentList.setOnItemClickListener( new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView <?> parent, View view, int position, long id) {

//
//        studentAdapter.setSelectItem( position );

        selectedPos = position;

//        selectedStudent = (Student) parent.getItemAtPosition( position );

//        studentAdapter.notifyDataSetInvalidated();

        Cursor cursor = (Cursor) parent.getItemAtPosition( position );
        selectedStudent = new Student(  );

        selectedStudent.setId( cursor.getInt( cursor.getColumnIndex("_id") ) );
        selectedStudent.setName( cursor.getString( cursor.getColumnIndex( "name" ) ) );
        selectedStudent.setGrade( cursor.getString( cursor.getColumnIndex( "grade" ) ) );
        selectedStudent.setAge( cursor.getInt( cursor.getColumnIndex( "age" ) ) );


    }
} );



    }

    private void initData() {

        // 从SQLite数据库获取物品列表
        studentService = new StudentServiceImpl(this);
        students = studentService.getAllStudents();

        // 若数据库中没数据，则初始化数据列表，防止ListView报错
        if(students == null) {
            students = new ArrayList<>();
        }




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_insert:

                Intent intent = new Intent( MainOrmActivity.this, InsertActivity.class );
                intent.putExtra( "flag", "添加" );
                startActivityForResult( intent, ADD_REQUEST );

                break;

            case R.id.btn_update:

                if (selectedStudent != null) {

                    Intent intent1 = new Intent( MainOrmActivity.this, InsertActivity.class );
                    intent1.putExtra( "flag", "修改" );

                    Bundle bundle = new Bundle();
                    bundle.putSerializable( "student", selectedStudent );
                    intent1.putExtras( bundle );

                    startActivityForResult( intent1, MODIFY_REQUEST );

                }

                break;

            case R.id.btn_delete:
//              if (selectedStudent!=null){
//                      studentService.delete( selectedStudent.getName() );
//                      studentAdapter.changeCursor( dao.selectByCursor() );
//
//
//             }
//             break;

                                        studentService.delete( selectedStudent.getName() );
                                        students.remove( 0);
                                        studentAdapter.notifyDataSetInvalidated();



                                  break;

        }


    }




    // 接收InsertActivity的返回的添加或修改后的student对象，更新student，刷新列表
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (data != null) {
            Bundle bundle = data.getExtras();
            if (bundle == null) {
                return;
            }
            // 更新students列表
            selectedStudent = (Student) bundle.get("student");
            if (requestCode == MODIFY_REQUEST) {
                students.set(selectedPos, selectedStudent);
            } else if (requestCode == ADD_REQUEST) {
                students.add(selectedStudent);
            }
            // 刷新ListView
            studentAdapter.notifyDataSetChanged();
        }

        studentAdapter.changeCursor( dao.selectByCursor());
    }


}
