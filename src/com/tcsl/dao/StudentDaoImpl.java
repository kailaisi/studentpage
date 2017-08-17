package com.tcsl.dao;

import com.tcsl.service.Pager;
import com.tcsl.service.Student;
import com.tcsl.utils.Constants;
import com.tcsl.utils.JdbcUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StudentDaoImpl implements StudentDao {
    @Override
    public Pager<Student> findStudent(Student model, int pageNum, int pageSize) {
        List<Student> students = findAllStudent(model);
        return new Pager<>(students,pageSize,pageNum);
    }

    @Override
    public void insert(Student stu) {
        JdbcUtil jdbcUtil = new JdbcUtil();
        for (int i=0;i<10000000l;i++) {
            try {
                jdbcUtil.insert();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Student> findAllStudent(Student model){
        List<Student> result=new ArrayList<>();
        List<Object> params=new ArrayList<>();
        JdbcUtil util =null;
        StringBuilder sb=new StringBuilder();
        sb.append("select * from Student where 1=1");
        String name = model.getName();
        int age = model.getAge();
        int sex = model.getSex();
        if(name!=null && !name.equals("")){
            sb.append(" and name like ?");
            params.add("%"+name+"%");
        }
        if(sex== Constants.GENDER_FEMALE || sex==Constants.GENDER_MALE){
            sb.append(" and sex=?");
            params.add(sex);
        }
        try {
            util=new JdbcUtil();
            util.getConnection();
            List<Map<String, Object>> maps = util.findResult(sb.toString(), params);
            if(maps!=null){
                for (Map<String,Object> map:maps){
                    Student student = new Student(map);
                    result.add(student);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(util!=null){
                util.close();
            }
        }
        return  result;
    }
}
