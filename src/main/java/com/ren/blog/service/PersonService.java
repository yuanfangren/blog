package com.ren.blog.service;

import java.util.List;

import com.ren.blog.bean.PersonBean;

/**
 * 测试SSM联通性的service
 * @author RYF
 *
 */
public interface PersonService {
	
	List<PersonBean> findAll();
}
