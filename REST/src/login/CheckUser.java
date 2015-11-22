package login;

import java.io.IOException;

import java.sql.SQLException;
import db.util.JdbcUtils;

import javax.ws.rs.Consumes;
import javax.ws.rs.QueryParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Produces("application/json")
@Consumes("application/json")
@Path("user")
public class CheckUser {
	
	@GET
	@Produces("application/json")
	@Path("query")
	//根据用户id获取用户信息
	public static Object checkUser(@QueryParam("userID") String ID) throws SQLException{
		String sql = "select * from user where user_password=?";
		Object params[] = {ID};
		System.out.println(params[0]);
		return JdbcUtils.query(sql,params,true);
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
