package com.ren.blog.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

/**
 * 通用的一些小工具
 * @author RYF
 *
 */
public class CommonUtils {
	
	/**
	 * 替换空格和换行符
	 * @param str
	 * @return
	 */
	public static String getStringNoBlank(String str) {   
        if(str!=null && !"".equals(str)) {   
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");   
            Matcher m = p.matcher(str);   
            String strNoBlank = m.replaceAll("");   
            return strNoBlank;   
        }else {   
            return str;   
        }   
	}
	
	/**
	 * 替换换行符
	 * @param str
	 * @return
	 */
	public static String getStringNoHH(String str) {   
        if(str!=null && !"".equals(str)) {   
            Pattern p = Pattern.compile("\t|\r|\n");   
            Matcher m = p.matcher(str);   
            String strNoBlank = m.replaceAll("");   
            return strNoBlank;   
        }else {   
            return str;   
        }   
	}
	
	/** 
     * 获取用户真实IP地址，不使用request.getRemoteAddr()的原因是有可能用户使用了代理软件方式避免真实IP地址, 
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值 
     *  
     * @return ip
     */
    public static  String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for"); 
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {  
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if( ip.indexOf(",")!=-1 ){
                ip = ip.split(",")[0];
            }
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("X-Real-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
        } 
        return ip;  
    }
	
	/**
	 * 格式化日期
	 * @param date 日期
	 * @param format 格式
	 * @return
	 */
	public static String getFormatDate(Date date,String format) {
		if(StringUtils.isBlank(format)) {
			format = "yyyy-MM-dd";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
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
