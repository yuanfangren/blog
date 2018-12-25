package com.ren.blog.dao;

import java.util.List;

import com.ren.blog.bean.ArticleBean;

/**
 * 文章管理dao
 * @author RYF
 *
 */
public interface ArticleDao {

	List<ArticleBean> getList();

	int getArticleCount();

	int deleteArticleByIds(int[] ids);

	int addArticle(ArticleBean article);

	ArticleBean getArticleById(int article_id);

	int updateArticle(ArticleBean article);

	List<ArticleBean> getAllList();

	/**
	 * 根据文章ID更新文章状态
	 * @param article
	 * @return
	 */
	int updateArticleStatus(ArticleBean article);

	List<ArticleBean> getArticleByChannelId(int channel_id);

	List<ArticleBean> getArticleByTagId(int tag_id);

	/**
	 * 根据文章ID获取文章 只能查询到已发布文章，若该文章未发布，则查询不到
	 * @param article_id
	 * @return
	 */
	ArticleBean getArticleByIdAndPublic(int article_id);
	 
}
