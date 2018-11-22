package com.ren.blog.dao;

import java.util.List;

import com.ren.blog.bean.ChannelBean;

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
	public int addChannel(ChannelBean channel);
	
	/**
	 * 获取栏目个数
	 * @return
	 */
	public int getchannelCount();

	/**
	 * 获取栏目列表
	 * @return
	 */
	public List<ChannelBean> getList();

	/**
	 * 获取栏目信息
	 * @param channel_id 栏目ID
	 * @return
	 */
	public ChannelBean getChannelById(int channel_id);

	/**
	 * 更新栏目
	 * @param channel 栏目
	 * @return
	 */
	public int updateChannel(ChannelBean channel);

	/**
	 * 删除栏目
	 * @param ids 栏目id的数组
	 * @return
	 */
	public int deleteChannelByIds(int[] ids);

	/**
	 * 获取所有栏目及该栏目已发布文章个数
	 * @return
	 */
	public List<ChannelBean> getListPublic();

	/**
	 * 验证栏目名是否重复--通过栏目名获取栏目
	 * @param channel
	 * @return
	 */
	public ChannelBean channelnamerepeat(ChannelBean channel);

}
