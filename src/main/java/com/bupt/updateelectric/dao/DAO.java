package com.bupt.updateelectric.dao;

import java.sql.*;

/**
 * Created by zyf on 2018/6/14.
 */
public class DAO {
        private static Connection getConn() {
            Connection conn = null;
            try {
                Class.forName("org.sqlite.JDBC"); //加载对应驱动
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            //url为本地创建数据库表的目录文件
            String url = "jdbc:sqlite::resource:electric.db";

            try{
                conn = DriverManager.getConnection(url);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return conn;
        }
       //增加数据
        public static int insert(String deviceName, String token) {
            Connection conn = getConn();
            int i = 0;
            String sql = "insert into electric (deviceName,token) values(?,?)";
            PreparedStatement pstmt;
            try {
                pstmt = (PreparedStatement) conn.prepareStatement(sql);
                pstmt.setString(1, deviceName);
                pstmt.setString(2, token);
                i = pstmt.executeUpdate();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return i;
        }
        //更新数据
        public static int update(String deviceName, String token) {
            Connection conn = getConn();
            int i = 0;
            String sql = "update electric set deviceName='" + deviceName + "' where token=" + token ;
            PreparedStatement pstmt;
            try {
                pstmt = (PreparedStatement) conn.prepareStatement(sql);
                i = pstmt.executeUpdate();
                System.out.println("result: " + i);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return i;
        }
        //获取数据
        public static String getAll(String deviceName) {
            String token = null;
            Connection conn = getConn();
            String sql = "select token from electric where deviceName='" + deviceName + "'";
            PreparedStatement pstmt;
            try {
                pstmt = (PreparedStatement)conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    token = rs.getString("token");
                }
                rs.close();
                return token;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
        //删除数据
        public static int delete(String deviceName) {
            Connection conn = getConn();
            int i = 0;
            String sql = "delete from electric where deviceName='" + deviceName + "'";
            PreparedStatement pstmt;
            try {
                pstmt = (PreparedStatement) conn.prepareStatement(sql);
                i = pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return i;
        }
}
