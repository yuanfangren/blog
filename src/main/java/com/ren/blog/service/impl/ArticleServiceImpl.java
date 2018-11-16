package com.ren.blog.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ren.blog.bean.ArticleBean;
import com.ren.blog.bean.TagBean;
import com.ren.blog.dao.ArticleDao;
import com.ren.blog.dao.TagDao;
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
	
	@Autowired
	private TagDao tagDao;

	@Override
	public List<ArticleBean> getList() {
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
	public int addArticle(ArticleBean article) {
		return articleDao.addArticle(article);
	}

	@Override
	public ArticleBean getArticleById(int article_id) {
		return articleDao.getArticleById(article_id);
	}

	@Override
	public int updateArticle(ArticleBean article,String[] tagNames) {
		if(tagNames != null) {
			List<TagBean> tags = tagDao.getTagByNames(tagNames);
			//移除已有标签
			if(tags != null && tags.size()>0) {
				
				List<String> beans = new ArrayList<>();
				for(int i=0;i<tagNames.length;i++) {
					 beans.add(tagNames[i]);
				}
				
				for (TagBean tagBean : tags) {
					for(int i=0;i<tagNames.length;i++) {
						if(tagBean.getTag_name().equals(tagNames[i])) {
							 
						}
					}
				}
			}
			
		}
		return articleDao.updateArticle(article);
	}

	@Override
	public List<ArticleBean> getAllList() {
		return articleDao.getAllList();
	}

	@Override
	public int updateArticleStatus(ArticleBean article) {
		return articleDao.updateArticleStatus(article);
	}


}
