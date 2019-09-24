package com.example.sqlitedemo.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;


@DatabaseTable(tableName = "t_student")
public class StudentOrm implements Serializable {

    public static  String TBL_STUDENT="create table t_student(" +
            "_id integer primary key autoincrement not null," +
            "name varchar(200) not null," +
            "grade varchar(200) not null," +
            "age integer(1000) not null)";


    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(index = true,columnName = "name",dataType = DataType.STRING)
    private String name;

    @DatabaseField
    private String grade;

    @DatabaseField(columnName = "age",dataType = DataType.INTEGER,canBeNull = true)
    private int age;


    public StudentOrm(){


    }

    public StudentOrm(int id, String name, String grade, int age) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.age = age;
    }

    public static String getTblStudent() {
        return TBL_STUDENT;
    }

    public static void setTblStudent(String tblStudent) {
        TBL_STUDENT = tblStudent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", grade='" + grade + '\'' +
                ", age=" + age +
                '}';
    }
}
