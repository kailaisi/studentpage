package com.tcsl.dao;

import com.tcsl.service.Pager;
import com.tcsl.service.Student;

import java.sql.SQLException;

public interface StudentDao {
    Pager<Student> findStudent(Student model, int pageNum, int pageSize);
    void insert(Student stu) throws SQLException;
}
