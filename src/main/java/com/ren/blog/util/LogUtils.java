package com.ren.blog.util;


import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ren.blog.bean.LogBean;
import com.ren.blog.bean.UserBean;
import com.ren.blog.service.LogService;

/**
 * 日志记录切面
 * @author RYF
 *
 */

@Aspect
@Order(2)
@Component("LogUtils")
public class LogUtils {
	
	
	@Autowired
	private LogService logService;
	
	@Pointcut("@annotation(com.ren.blog.util.LogAnnotation)")
    public void serviceAspect()
    {
    }

	/**
	 * 正常返回
	 * @param joinPoint
	 */
	@AfterReturning(value="serviceAspect()")
	public void afterReturning(JoinPoint joinPoint) {
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
			logBean.setLog_opermodule(log.operModule());
			logBean.setLog_opertype(log.operType());
			logBean.setLog_operip(CommonUtils.getIpAddr(req));
			logBean.setLog_result(1);//正常
			logService.addLog(logBean);
		}
	}
	
	/**
	 * 异常返回
	 * @param joinPoint
	 */
	@AfterThrowing(value="serviceAspect()",throwing="e")
	public void afterThrowing(JoinPoint joinPoint,Throwable e) {
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
			String ex = e.getClass().getName();
			String ex1 = e.getMessage();
			 
			String dist = CommonUtils.getStringNoHH(ex1);
			if(dist.length()>3001) {
				dist = dist.substring(0, 3000);
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = sdf.format(new Date());
			//日志入库
			LogBean logBean = new LogBean();
			logBean.setLog_desc(log.desc());
			logBean.setLog_operdate(date);
			logBean.setNote(log.note());
			logBean.setUser_id(user_id);
			logBean.setLog_opermodule(log.operModule());
			logBean.setLog_opertype(log.operType());
			logBean.setLog_operip(CommonUtils.getIpAddr(req));
			logBean.setLog_result(2);//异常
			logBean.setLog_resultdesc(ex);
			logBean.setNote(dist);
			logService.addLog(logBean);
		}
	}
	
}
