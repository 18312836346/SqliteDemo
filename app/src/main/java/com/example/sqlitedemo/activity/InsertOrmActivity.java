package com.example.sqlitedemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.example.sqlitedemo.R;
import com.example.sqlitedemo.entity.Student;
import com.example.sqlitedemo.entity.StudentOrm;
import com.example.sqlitedemo.service.StudentService;
import com.example.sqlitedemo.service.StudentServiceImpl;

public class InsertOrmActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText  name;
    private Spinner grade;
    private  EditText age;

    private Button sure;
    private Button cancel;


    private Student student;
    private StudentService studentService;
    private String flag;
    private Student sp;

    private int selectedPos;
    private Student selectedStudent;
    private static final int MODIFY_REQUEST = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_insert );

        studentService = new StudentServiceImpl(this);


        initView();
        initData();

    }

    private void initData() {

        Intent intent = getIntent();
        flag = intent.getStringExtra( "flag" );

        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            student = (Student) bundle.getSerializable( "student" );
            if (student != null){


                SpinnerAdapter spinnerAdapter = grade.getAdapter();
                for(int i=0;i<spinnerAdapter.getCount();i++){
                    if (spinnerAdapter.getItem( i ).toString().equals( student.getGrade() )){
                        grade.setSelection( i );
                    }

                }

                name.setText( student.getName());
                name.setEnabled( false );

//                grade.setText( student.getGrade());
                age.setText( String.valueOf( student.getAge() ) );

            }

        }





    }

    private void initView() {


        name = findViewById( R.id.et_name );
        grade = findViewById( R.id.sp_grade );
        age = findViewById( R.id.et_age );
        sure = findViewById( R.id.bt_sure);
        cancel = findViewById( R.id.bt_cancel );


        sure.setOnClickListener( this );
        cancel.setOnClickListener( this );


    }

    @Override
    public void onClick(View v) {
         switch (v.getId()){

             case R.id.bt_sure:
                    updateStudent();
                 break;

             case R.id.bt_cancel:


                 break;


         }

    }

    private void updateStudent() {

             grade.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
                 @Override
                 public void onItemSelected(AdapterView <?> parent, View view, int position, long id) {


                 }

                 @Override
                 public void onNothingSelected(AdapterView <?> adapterView) {

                 }
             } );



        if(student == null) {
            student = new Student();
        }

        student.setName(name.getText().toString());
        student.setGrade(String.valueOf( grade.getSelectedItem() ));
        student.setAge(Integer.parseInt(age.getText().toString()));


        if("修改".equals(flag)) {

            studentService.update( student );

        } else if("添加".equals(flag)) {
            studentService.insert(student);
        }

     // 将修改的数据返回MainActivity
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("student", student);
        intent.putExtras(bundle);
        setResult( Activity.RESULT_OK, intent);
        finish();
    }




}
