package com.whs.utils;

import org.testng.annotations.Test;

import java.sql.*;

public class DBConn {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL="jdbc:mysql://localhost:3306/mytest?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC";
    static final String DBUSER="root";
    static final String DBPWD="";

    /**
     * @purpose sql查询，返回查询结果中的某一列,如果有多条记录，只返回最后一条（如果是查询数据库记录条数，可以这么操作：第二个传参count(*)，同时sql语句里写select count(*)...）
     * @param sql
     * @param column  提取查询结果中的某一列
     * @return  返回提取出的某一列
     */
    public String connMysql(String sql,String column){
        Connection conn=null;
        Statement stmt=null;
        ResultSet rs=null;
        String result="";
        try {
            // （1）加载JDBC驱动
            Class.forName(JDBC_DRIVER);
            // （2）与数据库建立连接
            conn= DriverManager.getConnection(DB_URL,DBUSER,DBPWD);
            //（3）发送SQL语句，并得到返回结果
            stmt=conn.createStatement();
            rs=stmt.executeQuery(sql);// 查询操作 // executeUpdate(String sql) 插入删除更新操作
            //（4）处理返回结果
            while (rs.next()){
                 result=rs.getString(column);//如果有多条数据，这里其实赋值给result的是最后一条
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "DB Error!";
        }finally {
            //关闭连接
            if(conn!=null){
                try {//（5）释放资源
                    rs.close();
                    stmt.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void testDB(){
//        String result=connMysql("select count(*) from task where TO_DAYS(createTime) =TO_DAYS(NOW()) and taskType=1","count(*)");
        String result=connMysql("select * from task where TO_DAYS(createTime) =TO_DAYS(NOW()) and taskType=1","taskName");
      System.out.println("--------------"+result);

        int sign=1;//1代表今天已签到，0代表没有查找到今日签到记录

    }

}
