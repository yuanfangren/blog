package com.ren.blog.service.impl;

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

}
