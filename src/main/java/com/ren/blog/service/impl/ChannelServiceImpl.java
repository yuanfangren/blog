package com.ren.blog.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ren.blog.bean.Channel;
import com.ren.blog.dao.ChannelDao;
import com.ren.blog.service.ChannelService;

/**
 * 栏目管理service实现类
 * @author RYF
 *
 */
@Service
public class ChannelServiceImpl implements ChannelService{

	@Autowired
	private ChannelDao channelDao;
	
	@Override
	public int addChannel(Channel channel) {
		return channelDao.addChannel(channel);
	}

	@Override
	public List<Channel> getList() {
		return channelDao.getList();
	}

	@Override
	public Channel getChannelById(int channel_id) {
		return channelDao.getChannelById(channel_id);
	}

	@Override
	public int updateChannel(Channel channel) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		channel.setChannel_updatetime(sdf.format(new Date()));
		return channelDao.updateChannel(channel);
	}

	@Override
	public int deleteChannelByIds(int[] ids) {
		return channelDao.deleteChannelByIds(ids);
	}

	@Override
	public int getChannelCount() {
		return channelDao.getchannelCount();
	}


	 


}
