package com.ren.blog.dao;

import java.util.List;

import com.ren.blog.bean.ArticleTagBean;
import com.ren.blog.bean.TagBean;

/**
 * 标签管理dao
 * @author RYF
 *
 */
public interface TagDao {
	/**
	 * 新增标签
	 * @param Tag 标签
	 * @return
	 */
	public int addTag(TagBean Tag);
	
	/**
	 * 获取标签个数
	 * @return
	 */
	public int getTagCount();

	/**
	 * 获取标签列表
	 * @return
	 */
	public List<TagBean> getList();

	/**
	 * 获取标签信息
	 * @param Tag_id 标签ID
	 * @return
	 */
	public TagBean getTagById(int Tag_id);

	/**
	 * 更新标签
	 * @param Tag 标签 TagBean
	 * @return
	 */
	public int updateTag(TagBean Tag);

	/**
	 * 删除标签
	 * @param ids 标签id的数组
	 * @return
	 */
	public int deleteTagByIds(int[] ids);

	/**
	 * 根据 标签名称S 获取标签
	 * @param tagNames String[]
	 * @return List<TagBean>
	 */
	public List<TagBean> getTagByNames(String[] tagNames);

	/**
	 * 批量插入标签表
	 * @param tagList List<ArticleTagBean> 
	 */
	public void addTagList(List<ArticleTagBean> tagList);

	/**
	 * 批量插入 文章标签关联表
	 * @param articleTagList List<ArticleTagBean>
	 */
	public void addArticleTagList(List<ArticleTagBean> articleTagList);

	/**
	 * 删除 文章标签关联关系 tag_id 和 article_id
	 * @param articleTagList ArticleTagBean
	 */
	public void deleteArticleTagByList(List<ArticleTagBean> articleTagList);

	/**
	 * 删除 根据文章ID删除文章和标签的关联表
	 * @param article_id int
	 */
	public void deleteArticleTagByArticleId(int article_id);

	/**
	 * 根据文章ID获取该文章的标签信息
	 * @param article_id
	 * @return List<ArticleTagBean>
	 */
	public List<ArticleTagBean> getArticleTagByArticleId(int article_id);

	/**
	 * 获取所有有文章的标签
	 * @return
	 */
	public List<TagBean> getListAritcle();

	/**
	 * 根据标签ID s 删除文章标签关联表
	 * @param ids
	 */
	public void deleteArticleTagByTagId(int[] ids);

	/**
	 * 根据文章ID s 删除文章标签关联表
	 * @param ids
	 */
	public void deleteArticleTagByArticleIds(int[] ids);

}
