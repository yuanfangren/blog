package com.ren.blog.bean;

/**
 * 日志实体 blog_log
 * @author RYF
 *
 */
public class LogBean {

	/**
	 * id
	 */
	String log_id;
	
	/**
	 * 日志简单描述信息
	 */
	String log_desc;
	
	/**
	 * 操作类型 TODO 增删改、登录、注销等 需要写个 RNUM RYF 20181113
	 */
	int log_opertype;
	
	/**
	 * 操作模块 TODO 用户、栏目、文章 需要一个 RNUM RYF 20181113 
	 */
	int log_opermodule;
	
	/**
	 * 操作用户ID
	 */
	int user_id;
	
	/**
	 * 操作日期
	 */
	String log_operdate;
	
	/**
	 * 操作Ip
	 */
	String log_operip;
	
	/**
	 * 操作结果类型  1 正常 0 异常
	 */
	int log_result;
	
	/**
	 * 异常描述
	 */
	String log_resultdesc;
	
	/**
	 * 备注
	 */
	String note;

	public String getLog_id() {
		return log_id;
	}

	public void setLog_id(String log_id) {
		this.log_id = log_id;
	}

	public String getLog_desc() {
		return log_desc;
	}

	public void setLog_desc(String log_desc) {
		this.log_desc = log_desc;
	}

	public int getLog_opertype() {
		return log_opertype;
	}

	public void setLog_opertype(int log_opertype) {
		this.log_opertype = log_opertype;
	}

	public int getLog_opermodule() {
		return log_opermodule;
	}

	public void setLog_opermodule(int log_opermodule) {
		this.log_opermodule = log_opermodule;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getLog_operdate() {
		return log_operdate;
	}

	public void setLog_operdate(String log_operdate) {
		this.log_operdate = log_operdate;
	}

	public String getLog_operip() {
		return log_operip;
	}

	public void setLog_operip(String log_operip) {
		this.log_operip = log_operip;
	}

	public int getLog_result() {
		return log_result;
	}

	public void setLog_result(int log_result) {
		this.log_result = log_result;
	}

	public String getLog_resultdesc() {
		return log_resultdesc;
	}

	public void setLog_resultdesc(String log_resultdesc) {
		this.log_resultdesc = log_resultdesc;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	
}
