package com.ren.blog.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ren.blog.service.HomeStatisticsService;
import com.ren.blog.util.CommonUtils;
import com.ren.blog.util.GlobalParameter;

/**
 * 首页拦截器
 * 用来记录访问情况的
 * @author RYF
 *
 */
public class HomeInterceptor implements HandlerInterceptor{
	
	@Autowired
	private HomeStatisticsService homeStatisticsService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		Date date = new Date();
		String time = CommonUtils.getFormatDate(date, GlobalParameter.YYYYMMDDHHMMSS);
		String ip = CommonUtils.getIpAddr(request);
		homeStatisticsService.add(time,ip);
		
		
	}

	
	
}
