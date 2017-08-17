package com.tcsl.service;

import java.util.List;

public class Pager<T> {
    private List<T> origin;
    //每页的数据个数
    private int pageSize;
    //当前页数
    private int currentPage;
    //总条数
    private int totalNum;
    //总页数
    private int totalPage;
    private List<T> data;
    public Pager() {
    }

    public Pager(List<T> origin, int pageSize, int pageNum) {
        if(origin==null){
            return;
        }
        this.totalNum=origin.size();
        this.origin = origin;
        this.pageSize = pageSize;
        this.totalPage=totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
        this.currentPage = pageNum>totalPage?totalPage:pageNum;
        int start=pageSize*(currentPage-1);
        int end=pageSize*currentPage>totalNum?totalNum:pageSize*currentPage;
        data=origin.subList(start,end);
    }

    public Pager(int pageSize, int currentPage, int totalRecord, int totalPage,
                 List<T> dataList) {
        super();
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.data = dataList;
    }

    public List<T> getData() {
        return data;
    }
}
