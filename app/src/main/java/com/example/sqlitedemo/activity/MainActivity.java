package com.example.sqlitedemo.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sqlitedemo.R;
import com.example.sqlitedemo.adapter.MyCursorAdapter;
import com.example.sqlitedemo.adapter.StudentAdapter;
import com.example.sqlitedemo.dao.StudentDao;
import com.example.sqlitedemo.dao.StudentDaoImpl;
import com.example.sqlitedemo.entity.Student;
import com.example.sqlitedemo.service.StudentService;
import com.example.sqlitedemo.service.StudentServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button person;



    private static final int ADD_REQUEST = 100;
    private static final int MODIFY_REQUEST = 101;

    private ListView studentList;
    private List<Student> students;

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

    private ArrayList<String> contacts;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        person = findViewById( R.id.btn_person );




        dao =new StudentDaoImpl(this);


        insert = findViewById( R.id.btn_insert );
        update = findViewById( R.id.btn_update );
        delete = findViewById( R.id.btn_delete );


        person.setOnClickListener( this );

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

                Intent intent = new Intent( MainActivity.this, InsertActivity.class );
                intent.putExtra( "flag", "添加" );
                startActivityForResult( intent, ADD_REQUEST );

                break;

            case R.id.btn_update:

                if (selectedStudent != null) {

                    Intent intent1 = new Intent( MainActivity.this, InsertActivity.class );
                    intent1.putExtra( "flag", "修改" );

                    Bundle bundle = new Bundle();
                    bundle.putSerializable( "student", selectedStudent );
                    intent1.putExtras( bundle );

                    startActivityForResult( intent1, MODIFY_REQUEST );

                }

                break;

            case R.id.btn_delete:
                if (selectedStudent != null) {
                    studentService.delete( selectedStudent.getName() );
                    studentAdapter.changeCursor( dao.selectByCursor() );


                }
                break;

//                                        studentService.delete( selectedStudent.getName() );
//                                        students.remove( 0);
//                                        studentAdapter.notifyDataSetInvalidated();
//
//
//
//                                  break;


            case R.id.btn_person:

                //判断是否有运行时的权限
                if (ContextCompat.checkSelfPermission( this, Manifest.permission.READ_CONTACTS ) != PackageManager
                        .PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions( this, new String[]{Manifest.permission.READ_CONTACTS},
                            1 );

                } else {

                    //读取联系人
                    readContacts();

                }
                break;
        }


    }


    private void readContacts() {

        //1、获取联系人的cursor,组装联系人字符串放入list
    Cursor cursor = getContentResolver().query( ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        null,null,null,null);

    contacts = new ArrayList<>();
    if (cursor!=null && cursor.moveToFirst()){

        do {
            String name = cursor.getString( cursor.getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
            ) );
            String phone = cursor.getString( cursor.getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.NUMBER
            ) );
            contacts.add( name +"："+phone );

        }while(cursor.moveToNext());

        cursor.close();

    }

    //设置Adapter
    if (contacts.isEmpty()){
        Toast.makeText( MainActivity.this, "没有联系人", Toast.LENGTH_SHORT ).show();
        return;

    }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter <>(MainActivity.this,android.R.layout.simple_list_item_1,contacts );
        studentList.setAdapter( arrayAdapter );
      studentList.setOnItemClickListener( new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView <?> parent, View view, int position, long id) {

          }
      } );


    }





    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );

        if (requestCode==1&&grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){

            readContacts();
        }

    }




    // 接收InsertActivity的返回的添加或修改后的student对象，更新student，刷新列表
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
//        if (data != null) {
//            Bundle bundle = data.getExtras();
//            if (bundle == null) {
//                return;
//            }
//            // 更新students列表
//            selectedStudent = (Student) bundle.get("student");
//            if (requestCode == MODIFY_REQUEST) {
//                students.set(selectedPos, selectedStudent);
//            } else if (requestCode == ADD_REQUEST) {
//                students.add(selectedStudent);
//            }
//            // 刷新ListView
//            studentAdapter.notifyDataSetChanged();
//        }

        studentAdapter.changeCursor( dao.selectByCursor());
    }


}
