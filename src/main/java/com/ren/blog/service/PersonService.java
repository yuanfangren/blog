package com.ren.blog.service;

import java.util.List;

import com.ren.blog.bean.Person;

/**
 * 测试SSM联通性的service
 * @author RYF
 *
 */
public interface PersonService {
	
	List<Person> findAll();
}
