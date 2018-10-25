package com.ren.blog.dao;

import java.util.List;

import com.ren.blog.bean.Article;

/**
 * 文章管理dao
 * @author RYF
 *
 */
public interface ArticleDao {

	List<Article> getList();

	int getArticleCount();

	int deleteArticleByIds(int[] ids);

	int addArticle(Article article);

	Article getArticleById(int article_id);

	int updateArticle(Article article);

	List<Article> getAllList();
	 
}
