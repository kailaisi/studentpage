package com.tcsl.service;

public interface StudentService {
    public Pager<Student>  findStudent(Student model, int pageNum, int pageSize);
}
