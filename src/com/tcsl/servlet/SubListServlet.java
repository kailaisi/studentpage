package com.tcsl.servlet;

import com.google.gson.Gson;
import com.tcsl.service.Pager;
import com.tcsl.service.Student;
import com.tcsl.service.StudentService;
import com.tcsl.service.StudentServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SubListServlet extends HttpServlet {

    private StudentService service;

    @Override
    public void init() throws ServletException {
        super.init();
        service = new StudentServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String sex = req.getParameter("sex");
        int gendar = Integer.parseInt(sex);
        String pageNumStr = req.getParameter("PageNum");
       int pageNum = Integer.parseInt(pageNumStr);
        String pageSizeStr = req.getParameter("PageSize");
        int pageSize = Integer.parseInt(pageSizeStr);
        Student search = new Student(name, gendar);
        Pager<Student> student = service.findStudent(search, pageNum,pageSize );
        Gson gson = new Gson();
        String json = gson.toJson(student.getData());
        PrintWriter out = resp.getWriter();
        out.println(json);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
