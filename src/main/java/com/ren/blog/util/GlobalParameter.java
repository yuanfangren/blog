package com.ren.blog.util;

/**
 * 公共参数
 * @author RYF
 *
 */
public class GlobalParameter {

	/**
	 * MD5秘钥
	 */
	public static final String MD5KEY = "RYF";
	
	/**
	 * 用户登录session key
	 */
	public static final String SESSION_USER_KEY = "session_user_key";
	
	/**
	 * 返回JSON串中 状态的 键名称
	 */
	public static final String RETURN_STATUS = "status";
	
	/**
	 *  返回JSON串中 状态的 成功的 值
	 */
	public static final String RETURN_STATUS_OK = "ok";
	
	/**
	 *  返回JSON串中 状态的 失败的 值
	 */
	public static final String RETURN_STATUS_NO = "no";
	
	/**
	 * 返回JSON串中 状态编码的 键名称
	 */
	public static final String RETURN_CODE = "code";
	
	/**
	 * 返回JSON串中 编码的文字说明的 键名称
	 */
	public static final String RETURN_MSG = "msg";
	
	/**
	 * 返回JSON串中 返回结果集的  键名称
	 */
	public static final String RETURN_RESULT = "result";
	
	/**
	 * 默认编码格式
	 */
	public static final String ENCODING = "UTF-8";
	
	/**
	 * 默认ContentType
	 */
	public static final String CONTENTTYPE = "application/json;charset=UTF-8";
	
	/**
	 * yyyy-MM-dd 类型日期
	 */
	public static final String YYYYMMDDHHMMSS ="yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 用户名正则 
	 */
	public static final String USERNAME ="[0-9a-zA-Z_]{5,14}";
	public static final String USERNAME_DESC ="用户名需要5-14个字符，只能是数字、字母、下划线";
	
	/**
	 * 密码正则
	 */
	public static final String PASSWORD ="^[0-9A-Za-z_@]{6,14}$";
	public static final String PASSWORD_DESC ="用户密码 6-14个字符 ,只能是字母、数字、下划线、@";
	
	/**
	 * 邮箱正则
	 */
	public static final String EMAIL1 ="^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$";
	public static final String EMAIL1_DESC ="邮箱格式不正确";
	
		
}
