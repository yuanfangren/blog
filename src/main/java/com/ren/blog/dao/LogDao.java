package com.ren.blog.dao;

import java.util.List;

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

	List<LogBean> getList();

	int getLogCount();

	int deleteLogByIds(int[] ids);

	LogBean getLogById(String log_id);

}
