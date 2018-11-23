package com.ren.blog.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ren.blog.bean.ChannelBean;
import com.ren.blog.dao.ChannelDao;
import com.ren.blog.service.ChannelService;
import com.ren.blog.util.LogAnnotation;

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
	@LogAnnotation(desc="新增栏目",operType = 1,operModule=2)
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
	@LogAnnotation(desc="更新栏目",operType = 2,operModule=2)
	public int updateChannel(ChannelBean channel) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		channel.setChannel_updatetime(sdf.format(new Date()));
		return channelDao.updateChannel(channel);
	}

	@Override
	@LogAnnotation(desc="删除栏目",operType = 3,operModule=2)
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
