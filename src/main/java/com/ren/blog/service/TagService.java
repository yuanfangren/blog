package com.ren.blog.service;

import java.util.List;

import com.ren.blog.bean.TagBean;

/**
 * 标签管理service接口
 * @author RYF
 *
 */
public interface TagService {
	
	/**
	 * 新增标签
	 * @param Tag 标签实体类
	 * @return 返回新增返回结果
	 */
	public int addTag(TagBean Tag);

	/**
	 * 获取标签列表
	 * @return
	 */
	public List<TagBean> getList();

	/**
	 * 根据标签ID获取标签信息
	 * @param Tag_id 标签ID
	 * @return
	 */
	public TagBean getTagById(int Tag_id);

	/**
	 * 更新标签
	 * @param Tag 标签
	 * @return
	 */
	public int updateTag(TagBean Tag);

	/**
	 * 删除标签 
	 * @param ids 标签id数组
	 * @return
	 */
	public int deleteTagByIds(int[] ids);

	/**
	 * 获取标签的个数
	 * @return
	 */
	public int getTagCount();
	
}