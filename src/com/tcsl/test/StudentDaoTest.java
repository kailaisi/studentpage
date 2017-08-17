package com.tcsl.test;


import com.tcsl.dao.JDBCStudentDaoImpl;
import com.tcsl.dao.StudentDao;
import com.tcsl.service.Student;

import java.sql.SQLException;

public class StudentDaoTest {
    public static void main(String[] args) {
        StudentDao dao=new JDBCStudentDaoImpl();
        try {
                dao.insert(new Student("a", 1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
