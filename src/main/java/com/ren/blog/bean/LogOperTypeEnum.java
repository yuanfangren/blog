package com.ren.blog.bean;

/**
 * 日志操作类型 枚举
 * @author RYF
 *
 */
public enum LogOperTypeEnum {
	add("新增",1),
	update("更新",2),
	delete("删除",3),
	blend("混合",4),
	login("登录",5);
	
	private String name;
	private int index;
	
	private LogOperTypeEnum(String name,int index) {
		this.name = name;
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
}
