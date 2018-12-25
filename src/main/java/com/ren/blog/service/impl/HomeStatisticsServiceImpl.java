package com.ren.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ren.blog.bean.HomeStatisticsBean;
import com.ren.blog.dao.HomeStatisticsDao;
import com.ren.blog.service.HomeStatisticsService;
/**
 * 首页统计service
 * @author RYF
 *
 */
@Service
public class HomeStatisticsServiceImpl implements HomeStatisticsService {

	@Autowired
	private HomeStatisticsDao homeStatisticsDao;
	
	@Override
	public void add(String time, String ip) {
		HomeStatisticsBean bean = new HomeStatisticsBean();
		bean.setHome_ip(ip);
		bean.setHome_time(time);
		homeStatisticsDao.add(bean);
	}

}
