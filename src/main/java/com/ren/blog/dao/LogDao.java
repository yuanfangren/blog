package com.ren.blog.dao;

import com.ren.blog.bean.LogBean;

/**
 * 日志dao
 * @author RYF
 *
 */
public interface LogDao {

	/**
	 * 新增日志
	 * @param logBean LogBean
	 */
	void addLog(LogBean logBean);

}
