package com.ren.blog.bean;

/**
 * 栏目 blog_channel
 * @author RYF 
 *
 */
public class ChannelBean {
	/**
	 * 栏目ID
	 */
	private int channel_id;
	/**
	 * 栏目名称
	 */
	private String channel_name;	
	/**
	 * 栏目顺序
	 */
	private int channel_order;
	/**
	 * 栏目创建时间
	 */
	private String channel_createtime;
	/**
	 * 栏目更新时间
	 */
	private String channel_updatetime;
	/**
	 * 栏目描述
	 */
	private String channel_desc;
	
	/**
	 * 栏目包含文章个数 ----非字段
	 */
	private int article_count;
	
	/**
	 * 更新前的栏目名
	 */
	private String channel_oldname;
	
	
	
	public String getChannel_oldname() {
		return channel_oldname;
	}
	public void setChannel_oldname(String channel_oldname) {
		this.channel_oldname = channel_oldname;
	}
	public int getArticle_count() {
		return article_count;
	}
	public void setArticle_count(int article_count) {
		this.article_count = article_count;
	}
	public int getChannel_id() {
		return channel_id;
	}
	public void setChannel_id(int channel_id) {
		this.channel_id = channel_id;
	}
	public String getChannel_name() {
		return channel_name;
	}
	public void setChannel_name(String channel_name) {
		this.channel_name = channel_name;
	}
	public int getChannel_order() {
		return channel_order;
	}
	public void setChannel_order(int channel_order) {
		this.channel_order = channel_order;
	}
	public String getChannel_createtime() {
		return channel_createtime;
	}
	public void setChannel_createtime(String channel_createtime) {
		this.channel_createtime = channel_createtime;
	}
	public String getChannel_updatetime() {
		return channel_updatetime;
	}
	public void setChannel_updatetime(String channel_updatetime) {
		this.channel_updatetime = channel_updatetime;
	}
	public String getChannel_desc() {
		return channel_desc;
	}
	public void setChannel_desc(String channel_desc) {
		this.channel_desc = channel_desc;
	}
	
	
}
