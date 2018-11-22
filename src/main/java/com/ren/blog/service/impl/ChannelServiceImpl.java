package com.ren.blog.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ren.blog.bean.ChannelBean;
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
	public int addChannel(ChannelBean channel) {
		return channelDao.addChannel(channel);
	}

	@Override
	public List<ChannelBean> getList() {
		return channelDao.getList();
	}

	@Override
	public ChannelBean getChannelById(int channel_id) {
		return channelDao.getChannelById(channel_id);
	}

	@Override
	public int updateChannel(ChannelBean channel) {
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

	@Override
	public List<ChannelBean> getListPublic() {
		return channelDao.getListPublic();
	}

	@Override
	public ChannelBean channelnamerepeat(ChannelBean channel) {
		return channelDao.channelnamerepeat(channel);
	}


	 


}
