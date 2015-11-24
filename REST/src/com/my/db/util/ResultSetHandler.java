package com.my.db.util;


import java.sql.ResultSet;
import java.util.HashMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * @ClassName: ResultSetHandler
 * @Description:结果集处理器接口
 *
 */ 
 public class ResultSetHandler {
    
     public Object handler(ResultSet rs) {
    	 String result="";
    	 HashMap fin = new HashMap();
    	 JSONObject job = JSONObject.fromObject(rs);
    	 if(job.containsKey("data")){
    		 JSONArray json1 = job.getJSONArray("data");
    		 if(json1.size() != 0){
    			 JSONArray json = (JSONArray)job.getJSONArray("data").get(0);
				 JSONObject k = json.getJSONObject(0);
				 JSONObject data = k.getJSONObject("data");	
				 fin.put("message", "ok");
				 fin.put("data", data);
				 }else{
					 fin.put("message", "no");
					 }
    		 }else{
    			 fin.put("message", "no");
    			 }
    	 result = JSONObject.fromObject(fin).toString();
 		 System.out.println(result);
 		 return result;
     }
 }
