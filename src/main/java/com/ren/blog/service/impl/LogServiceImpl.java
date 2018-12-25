package com.ren.blog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ren.blog.bean.LogBean;
import com.ren.blog.dao.LogDao;
import com.ren.blog.service.LogService;
/**
 * 日志 serviceimpl
 * @author RYF
 *
 */
@Service
public class LogServiceImpl implements LogService {

	@Autowired
	private LogDao logDao;
	
	@Override
	public void addLog(LogBean logBean) {
		logDao.addLog(logBean);
	}

	@Override
	public List<LogBean> getList() {
		return logDao.getList();
	}

	@Override
	public int getLogCount() {
		return logDao.getLogCount();
	}

	@Override
	public int deleteLogByIds(int[] ids) {
		return logDao.deleteLogByIds(ids);
	}

	@Override
	public LogBean getLogById(String log_id) {
		return logDao.getLogById(log_id);
	}

}
