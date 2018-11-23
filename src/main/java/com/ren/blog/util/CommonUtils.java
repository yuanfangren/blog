package com.ren.blog.util;

import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;

/**
 * 通用的一些小工具
 * @author RYF
 *
 */
public class CommonUtils {
	
	/**
	 * 获取客户端IP
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) {
	    String ip = request.getHeader("x-forwarded-for");
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getRemoteAddr();
	    }
	    if (ip.contains(",")) {
	        return ip.split(",")[0];
	    } else {
	        return ip;
	    }
	}
	
	
	/**
	 * 通过HashSet判断String数组是否有重复数据
	 * @param params String[] 
	 * @return  返回false则无重复数据，否则有
	 */
	public static boolean cheakIsRepeat(String[] params) {
		
		HashSet<String> hashSet = new HashSet<>();
		for(int i=0;i<params.length;i++) {
			hashSet.add(params[i]);
		}
		
		if(hashSet.size() == params.length) {
			return false;
		}
		return true;
	}
	
}
