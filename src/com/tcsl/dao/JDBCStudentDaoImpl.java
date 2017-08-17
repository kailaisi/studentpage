package com.tcsl.dao;

import com.tcsl.service.Pager;
import com.tcsl.service.Student;
import com.tcsl.utils.Constants;
import com.tcsl.utils.JdbcUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JDBCStudentDaoImpl implements StudentDao {

    @Override
    public Pager<Student> findStudent(Student model, int pageNum, int pageSize) {
        return findAllStudent(model, pageNum, pageSize);
    }

    @Override
    public void insert(Student stu) throws SQLException {
        JdbcUtil jdbcUtil = new JdbcUtil();
        jdbcUtil.insert();
    }

    private Pager<Student> findAllStudent(Student model, int pageNum, int pageSize) {
        Pager<Student> pager = null;
        List<Object> params = new ArrayList<>();
        JdbcUtil util = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select * from Student where 1=1");

        StringBuilder countSql = new StringBuilder(
                "select count(name) as totalRecord from Student where 1=1 ");

        String name = model.getName();
        int age = model.getAge();
        int sex = model.getSex();
        if (name != null && !name.equals("")) {
            sb.append(" and name like ?");
            countSql.append(" and name like ?");
            params.add("%" + name + "%");
        }
        if (sex == Constants.GENDER_FEMALE || sex == Constants.GENDER_MALE) {
            sb.append(" and sex=?");
            countSql.append(" and sex=?");
            params.add(sex);
        }
        int start = pageSize * (pageNum - 1);
        sb.append(" limit " + start + "," + pageSize);
        List<Student> result = new ArrayList<>();
        try {
            util = new JdbcUtil();
            util.getConnection();
            List<Map<String, Object>> count = util.findResult(countSql.toString(), params);
            int totalRecord = ((Long) count.get(0).get("totalRecord")).intValue();
            List<Map<String, Object>> maps = util.findResult(sb.toString(), params);
            if (maps != null) {
                for (Map<String, Object> map : maps) {
                    Student student = new Student(map);
                    result.add(student);
                }
            }
            //获取总页数
            int totalPage = totalRecord / pageSize;
            if (totalRecord % pageSize != 0) {
                totalPage++;
            }
            // 组装pager对象
            pager = new Pager<>(pageSize, pageNum,
                    totalRecord, totalPage, result);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (util != null) {
                util.close();
            }
        }
        return pager;
    }
}
