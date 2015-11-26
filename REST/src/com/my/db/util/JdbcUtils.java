package com.my.db.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
 
public class JdbcUtils {
 
     private static String driver = null;
     private static String url = null;
     private static String username = null;
     private static String password = null;
     
     static{
         try{
             //读取db.properties文件中的数据库连接信息
             InputStream in = JdbcUtils.class.getClassLoader().getResourceAsStream("db.properties");
             Properties prop = new Properties();
             prop.load(in);
             
             //获取数据库连接驱动
             driver = prop.getProperty("driver");
             //获取数据库连接URL地址
             url = prop.getProperty("url");
             //获取数据库连接用户名
             username = prop.getProperty("username");
             //获取数据库连接密码
             password = prop.getProperty("password");
             
             //加载数据库驱动
             Class.forName(driver);
             
         }catch (Exception e) {
             throw new ExceptionInInitializerError(e);
         }
     }
     
     /**
     * @Method: getConnection
     * @Description: 获取数据库连接对象
     * @return Connection数据库连接对象
     * @throws SQLException
     */ 
     public static Connection getConnection() throws SQLException{
         return DriverManager.getConnection(url, username,password);
     }
     
     /**
     * @Method: release
     * @Description: 释放资源，
     *     要释放的资源包括Connection数据库连接对象，负责执行SQL命令的Statement对象，存储查询结果的ResultSet对象
     * @param conn
     * @param st
     * @param rs
     */ 
     public static void release(Connection conn,Statement st,ResultSet rs){
         if(rs!=null){
             try{
                 //关闭存储查询结果的ResultSet对象
                 rs.close();
             }catch (Exception e) {
                 e.printStackTrace();
             }
             rs = null;
         }
         if(st!=null){
             try{
                 //关闭负责执行SQL命令的Statement对象
                 st.close();
             }catch (Exception e) {
                 e.printStackTrace();
             }
         }
         
         if(conn!=null){
             try{
                 //关闭Connection数据库连接对象
                 conn.close();
             }catch (Exception e) {
                 e.printStackTrace();
             }
         }
     }
     
     /**
     * @Method: update
     * @Description: 万能更新
     * 所有实体的CUD操作代码基本相同，仅仅发送给数据库的SQL语句不同而已，
     * 因此可以把CUD操作的所有相同代码抽取到工具类的一个update方法中，并定义参数接收变化的SQL语句
     * @param sql 要执行的SQL
     * @param params 执行SQL时使用的参数
     * @throws SQLException
     */ 
     public static String update(String sql,Object params[]) throws SQLException{
         Connection conn = null;
         PreparedStatement st = null;
         ResultSet rs = null;
         
         String result = "";
         HashMap fin = new HashMap();
         
         try{
             conn = getConnection();
             st = conn.prepareStatement(sql);
             for(int i=0;i<params.length;i++){
                 st.setObject(i+1, params[i]);
             }
             int i = st.executeUpdate();//返回印象的行数
             if (i > 0) {
            	 fin.put("message", "ok");
             }else {
            	 fin.put("message", "no");
             }
             result = JSONObject.fromObject(fin).toString();
             System.out.println(result);
 			 return result;
         }catch (SQLException e) {
     			e.printStackTrace();
     			fin.put("message", "error");
     			result = JSONObject.fromObject(fin).toString();
                System.out.println(result);
    			return result;
         }finally{
             release(conn, st, rs);
         }
     }
     
     /**
     * @Method: query
     * @Description:万能查询，(page为true时表示分页，为false时表示不分页)
     * 实体的R操作，除SQL语句不同之外，根据操作的实体不同，对ResultSet的映射也各不相同，
     * 因此可义一个query方法，除以参数形式接收变化的SQL语句外，可以使用策略模式由qurey方法的调用者决定如何把ResultSet中的数据映射到实体对象中
     * @param sql 要执行的SQL
     * @param params 执行SQL时使用的参数
     * @param rsh 查询返回的结果集处理器
     * @return JSON格式数据
     * @throws SQLException
     */ 
     public static String query(String sql,Object params[], boolean page) throws SQLException{
         Connection conn = null;
         PreparedStatement st = null;
         ResultSet rs = null;
         
         try{
             conn = getConnection();
             st = conn.prepareStatement(sql);
             for(int i=0;i<params.length;i++){
                 st.setObject(i+1, params[i]);
             }
             rs = st.executeQuery(); 
             
             //对查询得到的ResultSet进行格式处理
             String result = "";
             ArrayList list = new ArrayList();
     		 HashMap fin = new HashMap();
     		 
             // 获取列数  
     	     ResultSetMetaData metaData = rs.getMetaData();  
     	     int columnCount = metaData.getColumnCount(); 
     	     
     	     //获取行数
     	     rs.last();
     	     int rowCount = rs.getRow();
     	     System.out.println(rowCount);
     	     
     	     if (rowCount > 0) {
     	    	 // 遍历ResultSet中的每条数据  
     	    	 rs.first();
     	    	 do {   
     	    	 	// 遍历每一列  
     	    		HashMap map = new HashMap();
     	    	 	for (int i = 0; i < columnCount; i++) {  
     	    	 		String columnName =metaData.getColumnLabel(i+1);  
     	    	 		String value = rs.getString(columnName);  
     	    	 		map.put(columnName, value); 
     	    	 		}
     	    	 	list.add(map);
     	    	 }while (rs.next());
     	    	JSONArray j = JSONArray.fromObject(list);
     	    	if (!page) {
     	    		fin.put("message", "ok");
             	    fin.put("data", j);
     	    	}else {
     	    		fin.put("message", "ok");
     	    		fin.put("total", rowCount);
             	    fin.put("data", j);
     	    	}
         	    }else {
     	    		 fin.put("message", "no");
     	    	 }
     	    result = JSONObject.fromObject(fin).toString();
			System.out.println(result);
			return result;
         }finally{
             release(conn, st, rs);
         }
     }
}
