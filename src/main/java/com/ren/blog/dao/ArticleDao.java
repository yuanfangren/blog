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
	 
}
