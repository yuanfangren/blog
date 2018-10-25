package com.ren.blog.dao;

import java.util.List;

import com.ren.blog.bean.Channel;

/**
 * 栏目管理dao
 * @author RYF
 *
 */
public interface ChannelDao {
	/**
	 * 新增栏目
	 * @param channel 栏目
	 * @return
	 */
	public int addChannel(Channel channel);
	
	/**
	 * 获取栏目个数
	 * @return
	 */
	public int getchannelCount();

	/**
	 * 获取栏目列表
	 * @return
	 */
	public List<Channel> getList();

	/**
	 * 获取栏目信息
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
	 * @param ids 栏目id的数组
	 * @return
	 */
	public int deleteChannelByIds(int[] ids);

}
