package com.ren.blog.util;

import java.util.HashSet;

/**
 * 通用的一些小工具
 * @author RYF
 *
 */
public class CommonUtils {
	
	
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
