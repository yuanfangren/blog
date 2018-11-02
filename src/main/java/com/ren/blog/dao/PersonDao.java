package com.ren.blog.dao;

import java.util.List;


import com.ren.blog.bean.PersonBean;

/**
 * 测试SSM联通性dao
 * @author RYF
 *
 */
public interface PersonDao {
	List<PersonBean> findall();
}
