package com.example.sqlitedemo.service;

import com.example.sqlitedemo.entity.Student;

import java.util.List;

public interface StudentService {

    public List<Student> getAllStudents();
    public  void insert (Student student);
    public   void update (Student student);
    public   void delete (String studentName);
}
