package com.ren.blog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ren.blog.bean.TagBean;
import com.ren.blog.dao.TagDao;
import com.ren.blog.service.TagService;

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
	public int updateTag(TagBean Tag) {
		return TagDao.updateTag(Tag);
	}

	@Override
	public int deleteTagByIds(int[] ids) {
		return TagDao.deleteTagByIds(ids);
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
