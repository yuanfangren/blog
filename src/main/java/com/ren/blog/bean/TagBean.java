package com.ren.blog.bean;

/**
 * 标签实体 BLOG_TAG
 * @author RYF
 *
 */
public class TagBean {

	/**
	 * 标签ID
	 */
	private int tag_id;
	
	/**
	 * 标签名称
	 */
	private String tag_name;

	public int getTag_id() {
		return tag_id;
	}

	public void setTag_id(int tag_id) {
		this.tag_id = tag_id;
	}

	public String getTag_name() {
		return tag_name;
	}

	public void setTag_name(String tag_name) {
		this.tag_name = tag_name.trim();
	}
	
	
	
}
