package com.ren.blog.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ren.blog.bean.ArticleBean;
import com.ren.blog.bean.ArticleTagBean;
import com.ren.blog.bean.TagBean;
import com.ren.blog.dao.ArticleDao;
import com.ren.blog.dao.TagDao;
import com.ren.blog.service.ArticleService;
import com.ren.blog.util.LogAnnotation;

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
	@LogAnnotation(desc="删除文章",operType = 3,operModule=1)
	public int deleteArticleByIds(int[] ids) {
		tagDao.deleteArticleTagByArticleIds(ids);
		articleDao.deleteArticleByIds(ids);
		return 1;
	}

	@Override
	@LogAnnotation(desc="新增新文章",operType = 1,operModule=1)
	public int addArticle(ArticleBean article,String[] tagNames) {
		
		 articleDao.addArticle(article);
		
		List<ArticleTagBean> tagList = new ArrayList<>();
		List<ArticleTagBean> articleTagList = new ArrayList<>();
		if(tagNames != null) {
			
			for(int i=0;i<tagNames.length;i++) {
				ArticleTagBean at = new ArticleTagBean();
				at.setTag_name(tagNames[i]);
				at.setArticle_id(article.getArticle_id());
				tagList.add(at);
			}
			
			List<TagBean> tags = tagDao.getTagByNames(tagNames);
			//移除已有标签
			if(tags != null && tags.size()>0) {
				Iterator<ArticleTagBean> iter = tagList.iterator();
				while (iter.hasNext()) {
					ArticleTagBean item = iter.next();
			        for (TagBean tagBean : tags) {
			        	if (item.getTag_name().equals(tagBean.getTag_name())) {
			        		ArticleTagBean b = new ArticleTagBean();
			        		b.setTag_id(tagBean.getTag_id());
			        		b.setTag_name(tagBean.getTag_name());
			        		b.setArticle_id(article.getArticle_id());
			        		articleTagList.add(b);
				            iter.remove();
				        }
					}
			        
			    }
			}
		}
		
		//新增以前没有的标签并返回主键
		if(tagList != null && tagList.size()>0) {
			tagDao.addTagList(tagList);
		}
		articleTagList.addAll(tagList);//合并
		//新增文章和标签的关联
		if(articleTagList != null && articleTagList.size()>0) {
			tagDao.addArticleTagList(articleTagList);
		}
		 return 1;
	}

	@Override
	public ArticleBean getArticleById(int article_id) {
		return articleDao.getArticleById(article_id);
	}

	@Override
	@LogAnnotation(desc="更新文章",operType = 2,operModule=1)
	public int updateArticle(ArticleBean article,String[] tagNames) {
		
		articleDao.updateArticle(article);
		
		List<ArticleTagBean> tagList = new ArrayList<>();
		List<ArticleTagBean> articleTagList = new ArrayList<>();
		if(tagNames != null) {
			
			for(int i=0;i<tagNames.length;i++) {
				ArticleTagBean at = new ArticleTagBean();
				at.setTag_name(tagNames[i]);
				at.setArticle_id(article.getArticle_id());
				tagList.add(at);
			}
			
			List<TagBean> tags = tagDao.getTagByNames(tagNames);
			//移除已有标签
			if(tags != null && tags.size()>0) {
				Iterator<ArticleTagBean> iter = tagList.iterator();
				while (iter.hasNext()) {
					ArticleTagBean item = iter.next();
			        for (TagBean tagBean : tags) {
			        	if (item.getTag_name().equals(tagBean.getTag_name())) {
			        		ArticleTagBean b = new ArticleTagBean();
			        		b.setTag_id(tagBean.getTag_id());
			        		b.setTag_name(tagBean.getTag_name());
			        		b.setArticle_id(article.getArticle_id());
			        		articleTagList.add(b);
				            iter.remove();
				        }
					}
			        
			    }
			}
		}
		
		//新增以前没有的标签并返回主键
		if(tagList != null && tagList.size()>0) {
			tagDao.addTagList(tagList);
		}
		articleTagList.addAll(tagList);//合并
		//新增文章和标签的关联
		if(articleTagList != null && articleTagList.size()>0) {
			
			//先删除，避免重复插入关联
			tagDao.deleteArticleTagByArticleId(article.getArticle_id());
			
			tagDao.addArticleTagList(articleTagList);
		}
		
		
		return 1;
	}

	@Override
	public List<ArticleBean> getAllList() {
		return articleDao.getAllList();
	}

	@Override
	@LogAnnotation(desc="更新文章状态",operType = 2,operModule=1)
	public int updateArticleStatus(ArticleBean article) {
		return articleDao.updateArticleStatus(article);
	}

	@Override
	public List<ArticleTagBean> getArticleTagByArticleId(int article_id) {
		return tagDao.getArticleTagByArticleId(article_id);
	}

	@Override
	public List<ArticleBean> getArticleByChannelId(int channel_id) {
		return articleDao.getArticleByChannelId(channel_id);
	}

	@Override
	public List<ArticleBean> getArticleByTagId(int tag_id) {
		return articleDao.getArticleByTagId(tag_id);
	}


}
