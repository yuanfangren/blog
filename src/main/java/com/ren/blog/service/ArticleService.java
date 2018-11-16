package com.ren.blog.service;

import java.util.List;

import com.ren.blog.bean.ArticleBean;

/**
 * 文章管理service接口
 * @author RYF
 *
 */
public interface ArticleService {

	List<ArticleBean> getList();

	int getArticleCount();

	int deleteArticleByIds(int[] ids);

	int addArticle(ArticleBean article);

	ArticleBean getArticleById(int article_id);

	int updateArticle(ArticleBean article,String[] tags);

	List<ArticleBean> getAllList();

	int updateArticleStatus(ArticleBean article);
	
}
