package com.tcsl.service;

import com.tcsl.dao.JDBCStudentDaoImpl;
import com.tcsl.dao.StudentDao;

public class StudentServiceImpl implements StudentService{
    StudentDao dao;

    public StudentServiceImpl() {
        dao=new JDBCStudentDaoImpl();
    }

    @Override
    public Pager<Student> findStudent(Student model, int pageNum, int pageSize) {
        return dao.findStudent(model,pageNum,pageSize);
    }
}
