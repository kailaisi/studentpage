package com.tcsl.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

public class JdbcUtil {
     private  static  String  URL;
     private static  String  NAME;
     private static  String PWD;
     private static String Driver;
     private Connection conn;
    private ResultSet resultSet;
    private PreparedStatement ps;

    private static void loadConfig(){
         try {
         InputStream inputStream = JdbcUtil.class.getResourceAsStream("/jdbc.property");
         Properties property=new Properties();
             property.load(inputStream);
             URL=property.getProperty("jdbc.url");
             NAME=property.getProperty("jdbc.username");
             URL=property.getProperty("jdbc.url");
             PWD=property.getProperty("jdbc.pwd");
             Driver=property.getProperty("jdbc.driver");
         } catch (IOException e) {
             throw new RuntimeException("加载配置信息出错");
         }
     }
    static {
         loadConfig();
    }

    public JdbcUtil() {
    }
    public Connection getConnection(){
        try {
            System.out.println(URL);
            System.out.println(NAME);
            System.out.println(PWD);
            Class.forName(Driver);
            conn= DriverManager.getConnection(URL,NAME,PWD);
           // conn= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mygirl","root","wu03102896528");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("连接错误，无法连接数据库",e);
        }
        return conn;
    }

    public List<Map<String,Object>> findResult(String sql, List<Object> params)throws SQLException{
        List<Map<String,Object>> result=new ArrayList<>();
        ps = conn.prepareStatement(sql);
        int index=0;
        if(params!=null && params.size()>0){
            for (int i=0;i<params.size();i++) {
                ps.setObject(i+1, params.get(i));
            }
        }
        resultSet = ps.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        while (resultSet.next()){
            Map<String,Object> map=new HashMap<>();
            for (int i=0;i<columnCount;i++){
                String name = metaData.getColumnName(i+1);
                Object value = resultSet.getObject(name);
                if(null==value){
                    value="";
                }
                map.put(name,value);
            }
            result.add(map);
        }
        return result;
    }

    public void close() {
        if(resultSet!=null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(ps!=null){
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void insert() throws SQLException {
        System.out.println(System.currentTimeMillis());
        Connection conn = getConnection();
        conn.setAutoCommit(false);
        String str=String.format("INSERT INTO student(name,age,sex) VALUES (?,?,?)");
        for(int k=0;k<25000;k++) {
            PreparedStatement ps = conn.prepareStatement(str);
            for (int i = 0; i < 250000; i++) {
                Random random1 = new Random();
                int i1 = random1.nextInt(10) + 2;
                StringBuilder sb = new StringBuilder("");
                for (int j = 0; j < i1; j++) {
                    int i2 = 'A' + random1.nextInt(25);
                    sb.append((char) i2);
                }
                sb.toString();
                ps.setString(1, sb.toString());
                ps.setInt(2, random1.nextInt(80) + 1);
                ps.setInt(3, random1.nextInt(50) % 2);
                ps.addBatch();
            }
            ps.executeBatch();
        }
        System.out.println(System.currentTimeMillis());
    }
}
