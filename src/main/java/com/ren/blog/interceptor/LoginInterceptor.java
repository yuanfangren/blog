package com.ren.blog.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ren.blog.bean.UserBean;
import com.ren.blog.util.GlobalParameter;

/**
 * 登录拦截器
 * @author RYF
 *
 */
public class LoginInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler)
			throws Exception {
		//String servletPath = req.getServletPath();
		//不是前台请求就要判断是否登录了
		UserBean user =  (UserBean) req.getSession().getAttribute(GlobalParameter.SESSION_USER_KEY);
		if(user == null) {
			String type = req.getHeader("X-Requested-With");
			if("XMLHttpRequest".equals(type)) {
				 res.setHeader("SESSIONSTATUS", "TIMEOUT");
                 res.setHeader("CONTEXTPATH", req.getContextPath()+"/login");
                 res.setStatus(HttpServletResponse.SC_FORBIDDEN);//403 禁止
			}else {
				res.sendRedirect(req.getContextPath()+"/login");
			}
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

}
