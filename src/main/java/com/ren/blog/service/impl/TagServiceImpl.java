package com.ren.blog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ren.blog.bean.TagBean;
import com.ren.blog.dao.TagDao;
import com.ren.blog.service.TagService;
import com.ren.blog.util.LogAnnotation;

/**
 * 标签管理service实现类
 * @author RYF
 *
 */
@Service
public class TagServiceImpl implements TagService{

	@Autowired
	private TagDao TagDao;
	
	@Override
	@LogAnnotation(desc="新增标签",operType = 1,operModule=3)
	public int addTag(TagBean Tag) {
		return TagDao.addTag(Tag);
	}

	@Override
	public List<TagBean> getList() {
		return TagDao.getList();
	}

	@Override
	public TagBean getTagById(int Tag_id) {
		return TagDao.getTagById(Tag_id);
	}

	@Override
	@LogAnnotation(desc="更新标签",operType = 2,operModule=3)
	public int updateTag(TagBean Tag) {
		return TagDao.updateTag(Tag);
	}

	@Override
	@LogAnnotation(desc="删除标签",operType = 3,operModule=3)
	public int deleteTagByIds(int[] ids) {
		TagDao.deleteArticleTagByTagId(ids);
		TagDao.deleteTagByIds(ids);
		return 1;
	}

	@Override
	public int getTagCount() {
		return TagDao.getTagCount();
	}

	@Override
	public List<TagBean> getListAritcle() {
		return TagDao.getListAritcle();
	}


	 


}
