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
             //��ȡdb.properties�ļ��е����ݿ�������Ϣ
             InputStream in = JdbcUtils.class.getClassLoader().getResourceAsStream("db.properties");
             Properties prop = new Properties();
             prop.load(in);
             
             //��ȡ���ݿ���������
             driver = prop.getProperty("driver");
             //��ȡ���ݿ�����URL��ַ
             url = prop.getProperty("url");
             //��ȡ���ݿ������û���
             username = prop.getProperty("username");
             //��ȡ���ݿ���������
             password = prop.getProperty("password");
             
             //�������ݿ�����
             Class.forName(driver);
             
         }catch (Exception e) {
             throw new ExceptionInInitializerError(e);
         }
     }
     
     /**
     * @Method: getConnection
     * @Description: ��ȡ���ݿ����Ӷ���
     * @return Connection���ݿ����Ӷ���
     * @throws SQLException
     */ 
     public static Connection getConnection() throws SQLException{
         return DriverManager.getConnection(url, username,password);
     }
     
     /**
     * @Method: release
     * @Description: �ͷ���Դ��
     *     Ҫ�ͷŵ���Դ����Connection���ݿ����Ӷ��󣬸���ִ��SQL�����Statement���󣬴洢��ѯ�����ResultSet����
     * @param conn
     * @param st
     * @param rs
     */ 
     public static void release(Connection conn,Statement st,ResultSet rs){
         if(rs!=null){
             try{
                 //�رմ洢��ѯ�����ResultSet����
                 rs.close();
             }catch (Exception e) {
                 e.printStackTrace();
             }
             rs = null;
         }
         if(st!=null){
             try{
                 //�رո���ִ��SQL�����Statement����
                 st.close();
             }catch (Exception e) {
                 e.printStackTrace();
             }
         }
         
         if(conn!=null){
             try{
                 //�ر�Connection���ݿ����Ӷ���
                 conn.close();
             }catch (Exception e) {
                 e.printStackTrace();
             }
         }
     }
     
     /**
     * @Method: update
     * @Description: ���ܸ���
     * ����ʵ���CUD�������������ͬ���������͸����ݿ��SQL��䲻ͬ���ѣ�
     * ��˿��԰�CUD������������ͬ�����ȡ���������һ��update�����У�������������ձ仯��SQL���
     * @param sql Ҫִ�е�SQL
     * @param params ִ��SQLʱʹ�õĲ���
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
             int i = st.executeUpdate();//����ӡ�������
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
     * @Description:���ܲ�ѯ��(pageΪtrueʱ��ʾ��ҳ��Ϊfalseʱ��ʾ����ҳ)
     * ʵ���R��������SQL��䲻֮ͬ�⣬���ݲ�����ʵ�岻ͬ����ResultSet��ӳ��Ҳ������ͬ��
     * ��˿���һ��query���������Բ�����ʽ���ձ仯��SQL����⣬����ʹ�ò���ģʽ��qurey�����ĵ����߾�����ΰ�ResultSet�е�����ӳ�䵽ʵ�������
     * @param sql Ҫִ�е�SQL
     * @param params ִ��SQLʱʹ�õĲ���
     * @param rsh ��ѯ���صĽ����������
     * @return JSON��ʽ����
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
             
             //�Բ�ѯ�õ���ResultSet���и�ʽ����
             String result = "";
             ArrayList list = new ArrayList();
     		 HashMap fin = new HashMap();
     		 
             // ��ȡ����  
     	     ResultSetMetaData metaData = rs.getMetaData();  
     	     int columnCount = metaData.getColumnCount(); 
     	     
     	     //��ȡ����
     	     rs.last();
     	     int rowCount = rs.getRow();
     	     System.out.println(rowCount);
     	     
     	     if (rowCount > 0) {
     	    	 // ����ResultSet�е�ÿ������  
     	    	 rs.first();
     	    	 do {   
     	    	 	// ����ÿһ��  
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
