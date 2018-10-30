package com.ren.blog.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.ren.blog.bean.Channel;
import com.ren.blog.service.ChannelService;
import com.ren.blog.util.PageUtils;

/**
 * 栏目管理控制器
 * @author RYF
 *
 */
@Controller
public class ChannelController {
	
	private Logger logger = Logger.getLogger(ChannelController.class);
	
	@Autowired
	private ChannelService channelService;
	
	/**
	 * 新增栏目 
	 * @param channel 栏目
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/channel/addChannel",method=RequestMethod.POST)
	public JSONObject addChannel(HttpServletResponse res,HttpServletRequest req,
			Channel channel){
		JSONObject jo = new JSONObject();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = sdf.format(new Date());
			channel.setChannel_createtime(date);
			channel.setChannel_updatetime(date);
			int count = channelService.addChannel(channel);
			jo.put("count", count);
			logger.info("新增栏目：栏目名称="+channel.getChannel_name());
		} catch (Exception e) {
			logger.error("新增栏目：【"+channel.getChannel_name()+"】异常",e);
		}
		return jo;
	}
	
	/** 
	 * 获取所有的栏目列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/channel/getList",method=RequestMethod.POST)
	public JSONObject getList(){
		JSONObject jo = new JSONObject();
		List<Channel> list = channelService.getList();
		jo.put("list", list);
		return jo;
	}
	
	/**
	 * 分页获取栏目列表
	 * @param page 分页信息 pageSize每页大小 pageNum 当前页
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/channel/getListPage",method=RequestMethod.POST)
	public JSONObject getListPage(PageUtils page){
		JSONObject jo = new JSONObject();
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<Channel> list = channelService.getList();
		//获取栏目个数
		int count = channelService.getChannelCount();
		page.setTotal(count);
		jo.put("list", list);
		jo.put("page", page);
		return jo;
	}
	
	/**
	 * 根据栏目ID获取栏目
	 * @param channel_id 栏目ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/channel/getChannelById",method=RequestMethod.POST)
	public JSONObject getChannelById(int channel_id){
		JSONObject jo = new JSONObject();
		Channel channel = channelService.getChannelById(channel_id);
		jo.put("channel", channel);
		return jo;
	}
	
	/**
	 * 更新栏目 
	 * @param channel 栏目
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/channel/updateChannel",method=RequestMethod.POST)
	public JSONObject updateChannel( Channel channel){
		JSONObject jo = new JSONObject();
		try {
			int count = channelService.updateChannel(channel);
			jo.put("count", count);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}
	
	/**
	 * 删除栏目 
	 * @param channel 栏目
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/channel/deleteChannelByIds",method=RequestMethod.POST)
	public JSONObject deleteChannelByIds(@RequestParam(value = "ids[]") int[] ids){
		JSONObject jo = new JSONObject();
		try {
			int count = channelService.deleteChannelByIds(ids);
			jo.put("count", count);
			logger.info("删除栏目：IDS="+ids);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除栏目:IDS=【"+ids+"】异常", e);
		}
		return jo;
	}
}
