package com.ren.blog.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

/**
 * 统一返回JSON格式处理类
 * @author RYF
 *
 */
public class UnifyResultJsonUtils {
	
	/**
	 * 返回状态 ok 代表成功， no 代表失败
	 */
	private String status;
	
	/**
	 * 状态编码，一般为数字，除了默认0为返回成功外，无统一规范，方便获取后重新描述文字说明 
	 */
	private int code;
	
	/**
	 * 返回状态编码的文字说明
	 */
	private String msg;
	
	/**
	 * 真正的返回结果
	 */
	private Object result;
	
	public UnifyResultJsonUtils(String status) {
		this.status = status;
	}
	
	public UnifyResultJsonUtils(String status,int code,String msg) {
		this.status = status;
		this.code = code;
		this.msg = msg;
	}
	
	public UnifyResultJsonUtils(String status,int code,String msg,Object result) {
		this.status = status;
		this.code = code;
		this.msg = msg;
		this.result = result;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
	
	
	public JSONObject getUnifyResultJson() {
		JSONObject jo = new JSONObject();
		jo.put("status", this.status);
		jo.put("code", this.code);
		jo.put("msg", this.msg);
		jo.put("result", this.result);
		return jo;
	}
	
	/**
	 * 只返回状态
	 * @param status 成功ok  失败no
	 * @return
	 */
	public static JSONObject getUnifyResultJson(String status) {
		JSONObject jo = new JSONObject();
		jo.put("status", status);
		return jo;
	}
	
	/**
	 * 返回状态、状态编码、编码描述
	 * @param status  成功ok  失败no
	 * @param code 状态编码
	 * @param msg 编码描述
	 * @return
	 */
	public static JSONObject getUnifyResultJson(String status,int code,String msg) {
		JSONObject jo = new JSONObject();
		jo.put("status", status);
		jo.put("code", code);
		jo.put("msg", msg);
		return jo;
	}
	
	/**
	 * 返回状态、状态编码、编码描述、真正的返回结果
	 * @param status 成功ok  失败no
	 * @param code 状态编码
	 * @param msg 编码描述
	 * @param result 真正的返回结果
	 * @return
	 */
	public static JSONObject getUnifyResultJson(String status,int code,String msg,Object result) {
		JSONObject jo = new JSONObject();
		jo.put("status", status);
		jo.put("code", code);
		jo.put("msg", msg);
		jo.put("result", result);
		return jo;
	}
	
	/**
	 * 直接返回json字符串，不带结果集
	 * @param request
	 * @param response
	 * @param status 返回状态 ok no
	 * @param code 返回状态码
	 * @param msg 返回信息描述
	 * @throws IOException
	 */
	public static void returnResultJson(HttpServletRequest request, HttpServletResponse response,
			String status,int code,String msg) throws IOException {
		JSONObject jo = new JSONObject();
		jo.put("status", status);
		jo.put("code", code);
		jo.put("msg", msg);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(jo.toString());
	}
	/**
	 * 直接返回json字符串，带结果集
	 * @param request
	 * @param response 
	 * @param status 返回状态 ok no
	 * @param code 返回状态码
	 * @param msg 返回信息描述
	 * @param result 结果集
	 * @throws IOException
	 */
	public static void returnResultJson(HttpServletRequest request, HttpServletResponse response,
			String status,int code,String msg,Object result) throws IOException {
		JSONObject jo = new JSONObject();
		jo.put("status", status);
		jo.put("code", code);
		jo.put("msg", msg);
		jo.put("result", result);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(jo.toString());
	}
	
}
