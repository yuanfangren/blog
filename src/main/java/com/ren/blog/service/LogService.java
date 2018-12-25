package com.ren.blog.service;

import java.util.List;

import com.ren.blog.bean.LogBean;

public interface LogService {
	
	public void addLog(LogBean logBean);

	public List<LogBean> getList();

	public int getLogCount();

	public int deleteLogByIds(int[] ids);

	public LogBean getLogById(String log_id);
	
}
