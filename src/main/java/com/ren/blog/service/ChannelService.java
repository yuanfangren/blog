package com.ren.blog.service;

import java.util.List;

import com.ren.blog.bean.Channel;

/**
 * 栏目管理service接口
 * @author RYF
 *
 */
public interface ChannelService {
	
	/**
	 * 新增栏目
	 * @param channel 栏目实体类
	 * @return 返回新增返回结果
	 */
	public int addChannel(Channel channel);

	/**
	 * 获取栏目列表
	 * @return
	 */
	public List<Channel> getList();

	/**
	 * 根据栏目ID获取栏目信息
	 * @param channel_id 栏目ID
	 * @return
	 */
	public Channel getChannelById(int channel_id);

	/**
	 * 更新栏目
	 * @param channel 栏目
	 * @return
	 */
	public int updateChannel(Channel channel);

	/**
	 * 删除栏目 
	 * @param ids 栏目id数组
	 * @return
	 */
	public int deleteChannelByIds(int[] ids);

	/**
	 * 获取栏目的个数
	 * @return
	 */
	public int getChannelCount();
	
}
