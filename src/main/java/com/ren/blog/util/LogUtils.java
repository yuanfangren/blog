package com.ren.blog.util;


import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ren.blog.bean.LogBean;
import com.ren.blog.bean.UserBean;

/**
 * 日志记录切面
 * @author RYF
 *
 */
public class LogUtils {
	
	public void after(JoinPoint joinPoint) {
		
		HttpServletRequest req =  ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		
		UserBean user =(UserBean)req.getSession().getAttribute(GlobalParameter.SESSION_USER_KEY);
		
		int user_id = 0;
		if(user != null) {
			user_id = user.getUser_id();
		}
		
		MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		LogAnnotation log = method.getAnnotation(LogAnnotation.class);
		if(log != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = sdf.format(new Date());
			//日志入库
			LogBean logBean = new LogBean();
			logBean.setLog_desc(log.desc());
			logBean.setLog_operdate(date);
			logBean.setNote(log.note());
			logBean.setUser_id(user_id);
			logBean.setLog_operip(log.operIp());
			logBean.setLog_opermodule(log.operModule());
			logBean.setLog_opertype(log.operType());
		}
		System.out.println("=====================================================");
		System.out.println("=====================================================");
		System.out.println("=====================================================");
		System.out.println("=====================================================");
		System.out.println("=====================================================");
	}
	
}
