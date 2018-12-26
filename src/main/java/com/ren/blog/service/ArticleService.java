package com.ren.blog.service;

import java.util.List;

import com.ren.blog.bean.ArticleBean;
import com.ren.blog.bean.ArticleTagBean;

/**
 * 文章管理service接口
 * @author RYF
 *
 */
public interface ArticleService {

	List<ArticleBean> getList();

	int getArticleCount();

	int deleteArticleByIds(int[] ids);

	int addArticle(ArticleBean article,String[] tags);

	ArticleBean getArticleById(int article_id);

	int updateArticle(ArticleBean article,String[] tags);

	List<ArticleBean> getAllList();

	int updateArticleStatus(ArticleBean article);

	List<ArticleTagBean> getArticleTagByArticleId(int article_id);

	List<ArticleBean> getArticleByChannelId(int channel_id);

	List<ArticleBean> getArticleByTagId(int tag_id);

	ArticleBean getArticleByIdAndPublic(int article_id);

	List<ArticleBean> getList(int user_id);

	int getArticleCount(int user_id);
	
}
