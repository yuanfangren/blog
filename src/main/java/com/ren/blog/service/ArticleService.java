package com.ren.blog.service;

import java.util.List;

import com.ren.blog.bean.Article;

/**
 * 文章管理service接口
 * @author RYF
 *
 */
public interface ArticleService {

	List<Article> getList();

	int getArticleCount();

	int deleteArticleByIds(int[] ids);

	int addArticle(Article article);

	Article getArticleById(int article_id);

	int updateArticle(Article article);

	List<Article> getAllList();
	
}
