package com.ren.blog.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 自定义全局异常处理器
 * @author RYF
 *
 */
public class CustomExceptionResolver implements HandlerExceptionResolver {
	
	private static Logger logger = Logger.getLogger(CustomExceptionResolver.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		
		CustomException customException = null;
		
		if(ex instanceof CustomException ) {
			customException = (CustomException)ex;
		}else {
			//不是自定义异常，抛出未知错误
			customException = new CustomException("未知错误");
		}
		String type = request.getHeader("X-Requested-With");
		if("XMLHttpRequest".equals(type)) {//ajax请求
			try {
				UnifyResultJsonUtils.returnResultJson(request, response, "no", 7, "未知错误");
				return new ModelAndView();
			} catch (IOException e) {
				logger.error("全局异常处理器返回异常信息错误", e);
			}
		}else {
			//错误信息
			String message=customException.getMessage();
			
			ModelAndView modelAndView=new ModelAndView();
			
			//将错误信息传到页面
			modelAndView.addObject("message",message);
			
			//指向到错误界面
			modelAndView.setViewName("exception/error");
			
			return modelAndView;
		}
		
		return null;
	}

}
