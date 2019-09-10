package com.example.sqlitedemo.dao;

import com.example.sqlitedemo.entity.Student;

import java.util.List;

public interface StudentDao {


    void insert (Student student);
    void update (Student student);
    void delete (String studentName);

    List<Student> selectAllStudents();
}
