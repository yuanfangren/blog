package com.ren.blog.dao;

import java.util.List;


import com.ren.blog.bean.Person;

/**
 * 测试SSM联通性dao
 * @author RYF
 *
 */
public interface PersonDao {
	List<Person> findall();
}
