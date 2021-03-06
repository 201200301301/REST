package com.my.user.login;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.SQLException;
import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.QueryParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.my.db.util.JdbcUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;


@Produces("application/json")
@Consumes("application/json")
@Path("user")
public class CheckUser {
	
	@GET
	@Produces("application/json")
	@Path("query")
	//根据用户id获取用户信息
	public static Object checkUser(@QueryParam("userID") String ID) throws SQLException{
		
		String sql = "select * from user where id=?";
		Object params[] = {ID};
		System.out.println(params[0]);
		return JdbcUtils.query(sql,params,true);
		}
	
	
	@POST
	@Produces( { "application/json", "application/xml" }) 
	@Consumes("application/x-www-form-urlencoded")//表单格式
	@Path("check")
	//根据用户id和用户密码对用户进行验证
	public static Object checkUser(@Context HttpServletRequest request,
			@FormParam("userID") String ID,
			@FormParam("password") String password) throws SQLException{
		
		String sql = "select user_password from user where id=?";
		Object params[] = {ID};
		System.out.println(params[0]);
		String re = JdbcUtils.query(sql,params,false);
		
		//对JSON数据进行解析，获取password进行比较
		String result = "";
		HashMap fin = new HashMap();
		try {
			JSONObject data = JSONObject.fromObject(re);
			String message = data.getString("message");
			System.out.println("message:" + message);
			if (message.equals("no")) {
				fin.put("message","err");
			}else {
				JSONArray data1 = (JSONArray)data.getJSONArray("data");
				JSONObject pass = data1.getJSONObject(0);
				String pass1 = pass.getString("user_password");
				System.out.println("password:" + pass1);
				if (pass1.equals(password)) {
					fin.put("message","ok");
				}else {
					fin.put("message","no");
				}
			}
			result = JSONObject.fromObject(fin).toString();
			
			//session处理
			
			//使用request对象的getSession()获取session，如果session不存在则创建一个
			HttpSession session = request.getSession();
			//将数据存储到session中
			session.setAttribute("username", "haiyang");
			
			
		}catch(JSONException e) {
			e.printStackTrace();
		}
        System.out.println(result);
		return result;
		}
	
	
	@POST
	@Produces("application/json")
	@Consumes("application/x-www-form-urlencoded")
	@Path("insert")
	//向用户表插入一条记录
	public static Object insertUser(@FormParam("userID") String ID,
			@FormParam("username") String name,
			@FormParam("password") String password
			)throws SQLException{
		String sql = "insert into user(id,user_name,user_password) values (?,?,?)";
		Object params[] = {ID,name,password};
		System.out.println(params[1]);
		return JdbcUtils.update(sql,params);
		}
}
