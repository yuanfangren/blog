package com.ren.blog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ren.blog.bean.Article;
import com.ren.blog.dao.ArticleDao;
import com.ren.blog.service.ArticleService;

/**
 * 栏目管理service实现类
 * @author RYF
 *
 */
@Service
public class ArticleServiceImpl implements ArticleService{

	@Autowired
	private ArticleDao articleDao;

	@Override
	public List<Article> getList() {
		return articleDao.getList();
	}

	@Override
	public int getArticleCount() {
		return articleDao.getArticleCount();
	}

	@Override
	public int deleteArticleByIds(int[] ids) {
		return articleDao.deleteArticleByIds(ids);
	}

	@Override
	public int addArticle(Article article) {
		return articleDao.addArticle(article);
	}

	@Override
	public Article getArticleById(int article_id) {
		return articleDao.getArticleById(article_id);
	}

	@Override
	public int updateArticle(Article article) {
		return articleDao.updateArticle(article);
	}

	@Override
	public List<Article> getAllList() {
		return articleDao.getAllList();
	}


}
