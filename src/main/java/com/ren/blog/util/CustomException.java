package com.ren.blog.util;

/**
 * 自定义异常
 * @author RYF
 *
 */
public class CustomException extends Exception{
	
	private static final long serialVersionUID = 1L;
	/**
	 * 异常信息
	 */
	private String msg;
	
	public CustomException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
