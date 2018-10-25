package com.ren.blog.util;

/**
 * 分页工具
 * @author RYF
 *
 */
public class PageUtils {
	
	/**
	 * 每页大小
	 */
	private int pageSize = 10;
	
	/**
	 * 当前页
	 */
	private int pageNum=1;
	
	/**
	 * 总记录数
	 */
	private long total;
	

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
}
