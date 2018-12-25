package com.ren.blog.bean;

/**
 * 首页统计bean
 * @author RYF
 *
 */
public class HomeStatisticsBean {
	
	private Integer home_id;
	
	/**
	 * 访问IP
	 */
	private String home_ip;
	
	/**
	 * 访问时间
	 */
	private String home_time;

	public Integer getHome_id() {
		return home_id;
	}

	public void setHome_id(Integer home_id) {
		this.home_id = home_id;
	}

	public String getHome_ip() {
		return home_ip;
	}

	public void setHome_ip(String home_ip) {
		this.home_ip = home_ip;
	}

	public String getHome_time() {
		return home_time;
	}

	public void setHome_time(String home_time) {
		this.home_time = home_time;
	}
	
	
	
}
