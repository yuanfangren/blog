package com.ren.blog.bean;

/**
 * 日志操作模块 枚举
 * @author RYF
 *
 */
public enum LogOperModuleEnum {
	
	article("文章",1),
	channel("栏目",2),
	tag("标签",3),
	user("用户",4);
	
	private String name;
	private int index;
	
	private LogOperModuleEnum(String name,int index) {
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
