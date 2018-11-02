package com.ren.blog.bean;

/**
 * 文章 blog_article
 * @author RYF
 *
 */
public class ArticleBean {
	
	/**
	 * 文章主键
	 */
	private int article_id;
	
	/**
	 * 文章标题
	 */
	private String article_title;
	
	/**
	 * 文章创建时间
	 */
	private String article_createtime;
	
	/**
	 * 文章更新时间
	 */
	private String article_updatetime;
	
	/**
	 * 文章所属栏目ID
	 */
	private int channel_id;
	
	/**
	 * 文章内容
	 */
	private String article_content;
	
	/**
	 * 文章内容摘要
	 */
	private String article_remark;
	
	/**
	 * 文章状态 1是发布 0是草稿
	 */
	private int article_status;
	
	/**
	 * 文章所属栏目的名称--非字段
	 */
	private String channel_name;
	
	/**
	 * 更新时间-年 --非字段
	 */
	private String article_year;
	
	/**
	 * 更新时间-月 --非字段
	 */
	private String article_months;
	
	/**
	 * 更新时间-月-日 --非字段
	 */
	private String article_monthsday;
	
	
	public String getArticle_remark() {
		return article_remark;
	}

	public void setArticle_remark(String article_remark) {
		this.article_remark = article_remark;
	}

	public String getArticle_monthsday() {
		return article_monthsday;
	}

	public void setArticle_monthsday(String article_monthsday) {
		this.article_monthsday = article_monthsday;
	}

	public String getChannel_name() {
		return channel_name;
	}

	public void setChannel_name(String channel_name) {
		this.channel_name = channel_name;
	}

	public String getArticle_year() {
		return article_year;
	}

	public void setArticle_year(String article_year) {
		this.article_year = article_year;
	}

	public String getArticle_months() {
		return article_months;
	}

	public void setArticle_months(String article_months) {
		this.article_months = article_months;
	}

	public String getChannle_name() {
		return channel_name;
	}

	public void setChannle_name(String channel_name) {
		this.channel_name = channel_name;
	}

	public int getArticle_status() {
		return article_status;
	}

	public void setArticle_status(int article_status) {
		this.article_status = article_status;
	}

	public int getArticle_id() {
		return article_id;
	}

	public void setArticle_id(int article_id) {
		this.article_id = article_id;
	}

	public String getArticle_title() {
		return article_title;
	}

	public void setArticle_title(String article_title) {
		this.article_title = article_title;
	}

	public String getArticle_createtime() {
		return article_createtime;
	}

	public void setArticle_createtime(String article_createtime) {
		this.article_createtime = article_createtime;
	}

	public String getArticle_updatetime() {
		return article_updatetime;
	}

	public void setArticle_updatetime(String article_updatetime) {
		this.article_updatetime = article_updatetime;
	}

	public int getChannel_id() {
		return channel_id;
	}

	public void setChannel_id(int channel_id) {
		this.channel_id = channel_id;
	}

	public String getArticle_content() {
		return article_content;
	}

	public void setArticle_content(String article_content) {
		this.article_content = article_content;
	}
	
}
