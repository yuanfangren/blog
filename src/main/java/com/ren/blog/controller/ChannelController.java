package com.ren.blog.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.ren.blog.bean.ChannelBean;
import com.ren.blog.service.ChannelService;
import com.ren.blog.util.GlobalParameter;
import com.ren.blog.util.PageUtils;
import com.ren.blog.util.UnifyResultJsonUtils;

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
			ChannelBean channel){
		try {
			
			if(StringUtils.isEmpty(channel.getChannel_name())) {
				return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO, 2, "栏目名不能为空");
			}
			
			ChannelBean channelBean = channelService.channelnamerepeat(channel);
			if(channelBean != null) {
				return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO, 6, "栏目名已存在");
			} 
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = sdf.format(new Date());
			channel.setChannel_createtime(date);
			channel.setChannel_updatetime(date);
			int count = channelService.addChannel(channel);
			if(count > 0 ) {
				logger.info("新增栏目：栏目名称="+channel.getChannel_name());
				return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_OK, 0, "新增成功");
			}else {
				logger.info("新增栏目：栏目名称="+channel.getChannel_name()+" 未成功");
				return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO, 1, "新增未成功");
			}
			
		} catch (Exception e) {
			logger.error("新增栏目：【"+channel.getChannel_name()+"】异常",e);
			return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO, 7, "新增栏目异常");
		}
	}
	
	/** 
	 * 获取所有的栏目列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/channel/getList",method=RequestMethod.POST)
	public JSONObject getList(){
		JSONObject jo = new JSONObject();
		List<ChannelBean> list = channelService.getList();
		jo.put("list", list);
		return jo;
	}
	
	/**
	 * 获取所有栏目及已发布文章个数-
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/front/channel/getListPublic",method=RequestMethod.POST)
	public JSONObject getListPublic(){
		JSONObject jo = new JSONObject();
		List<ChannelBean> list = channelService.getListPublic();
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
		List<ChannelBean> list = channelService.getList();
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
		ChannelBean channel = channelService.getChannelById(channel_id);
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
	public JSONObject updateChannel( ChannelBean channel){
		try {
			if(StringUtils.isEmpty(channel.getChannel_name())) {
				return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO, 2, "栏目名不能为空");
			}
			if(StringUtils.isEmpty(channel.getChannel_oldname())) {
				return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO, 2, "原有栏目名不能为空");
			}
			
			ChannelBean channelBean = channelService.channelnamerepeat(channel);
			if(channelBean != null && !channelBean.getChannel_name().equals(channel.getChannel_oldname())) {
				return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO, 6, "栏目名已存在");
			} 
			
			int count = channelService.updateChannel(channel);
			if(count > 0 ) {
				logger.info("更新栏目：栏目ID="+channel.getChannel_id());
				return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_OK, 0, "更新成功");
			}else {
				logger.info("更新栏目：栏目ID="+channel.getChannel_id()+" 未成功");
				return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO, 1, "更新未成功");
			}
		} catch (Exception e) {
			logger.info("更新栏目：栏目ID="+channel.getChannel_id()+" 异常");
			return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO, 7, "更新栏目异常");
		}
	}
	
	/**
	 * 删除栏目 
	 * @param ids 栏目ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/channel/deleteChannelByIds",method=RequestMethod.POST)
	public JSONObject deleteChannelByIds(@RequestParam(value = "ids[]") int[] ids){
		try {
			int count = channelService.deleteChannelByIds(ids);
			if(count > 0 ) {
				logger.info("删除栏目：IDS="+ids);
				return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_OK, 0, "删除成功");
			}else {
				logger.info("更新栏目：栏目ID="+ids +" 未成功");
				return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO, 1, "删除未成功");
			}
		} catch (Exception e) {
			logger.error("删除栏目:IDS=【"+ids+"】异常", e);
			return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO, 7, "删除栏目异常");
		}
	}
}
