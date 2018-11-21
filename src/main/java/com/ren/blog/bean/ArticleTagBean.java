package com.ren.blog.bean;

/**
 * 文章标签关联 blog_article_tag
 * @author RYF
 *
 */
public class ArticleTagBean {

	/**
	 * 主键ID
	 */
	private int article_tag_id;
	
	/**
	 * 标签ID
	 */
	private int tag_id;
	
	/**
	 * 文章ID
	 */
	private int article_id;
	
	/**
	 * 标签名称----非字段
	 */
	private String tag_name;
	
	

	public String getTag_name() {
		return tag_name;
	}

	public void setTag_name(String tag_name) {
		this.tag_name = tag_name;
	}

	public int getArticle_tag_id() {
		return article_tag_id;
	}

	public void setArticle_tag_id(int article_tag_id) {
		this.article_tag_id = article_tag_id;
	}

	public int getTag_id() {
		return tag_id;
	}

	public void setTag_id(int tag_id) {
		this.tag_id = tag_id;
	}

	public int getArticle_id() {
		return article_id;
	}

	public void setArticle_id(int article_id) {
		this.article_id = article_id;
	}
	
	
		
}
