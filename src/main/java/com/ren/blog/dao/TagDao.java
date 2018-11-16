package com.ren.blog.dao;

import java.util.List;

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
	 * @param Tag 标签
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
	 * @return
	 */
	public List<TagBean> getTagByNames(String[] tagNames);

}
