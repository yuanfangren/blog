package com.ren.blog.bean;

/**
 * 用户表 blog_user
 * @author RYF
 *
 */
public class UserBean {
	
	/**
	 * id
	 */
	private int user_id;
	
	/**
	 * 登录名  5-14个字符，只能是数字、字母、下划线
	 */
	private String user_username;
	
	/**
	 * 昵称 6-14个字符 ,只能是数字、字母、下划线
	 */
	private String user_nickname;
	
	/**
	 * 用户密码 6-14个字符 ,只能是字母、数字、下划线、@
	 */
	private String user_password;
	
	/**
	 * 用户创建时间
	 */
	private String user_createtime;
	
	/**
	 * 用户更新时间
	 */
	private String user_updatetime;
	
	/**
	 * 用户类型 0 是管理员 1是普通用户 TODO ryf 用户权限要跟上
	 */
	private int user_type;
	
	/**
	 * 用户邮箱  不能超过25个字符
	 */
	private String user_email;
	
	
	public int getUser_type() {
		return user_type;
	}

	public void setUser_type(int user_type) {
		this.user_type = user_type;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUser_username() {
		return user_username;
	}

	public void setUser_username(String user_username) {
		this.user_username = user_username;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getUser_nickname() {
		return user_nickname;
	}

	public void setUser_nickname(String user_nickname) {
		this.user_nickname = user_nickname;
	}

	public String getUser_createtime() {
		return user_createtime;
	}

	public void setUser_createtime(String user_createtime) {
		this.user_createtime = user_createtime;
	}

	public String getUser_updatetime() {
		return user_updatetime;
	}

	public void setUser_updatetime(String user_updatetime) {
		this.user_updatetime = user_updatetime;
	}
	
	
}
