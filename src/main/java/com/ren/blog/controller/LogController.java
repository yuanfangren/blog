package com.ren.blog.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.ren.blog.bean.LogBean;
import com.ren.blog.service.LogService;
import com.ren.blog.util.GlobalParameter;
import com.ren.blog.util.PageUtils;
import com.ren.blog.util.UnifyResultJsonUtils;

/**
 * 操作日志请求
 * @author RYF
 *
 */
@Controller
public class LogController {
	
	private Logger logger = Logger.getLogger(LogController.class);
	
	@Autowired
	private LogService logService;
	
	/**
	 * 分页获取日志列表
	 * @param page 分页信息 pageSize每页大小 pageNum 当前页
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/log/getLogPage",method=RequestMethod.POST)
	public JSONObject getListPage(PageUtils page){
		JSONObject jo = new JSONObject();
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<LogBean> list = logService.getList();
		int count = logService.getLogCount();
		page.setTotal(count);
		jo.put("list", list);
		jo.put("page", page);
		return jo;
	} 
	
	/**
	 * 根据log_id 获取日志信息
	 * @param log_id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/log/getLogById",method=RequestMethod.POST)
	public JSONObject getLogById(String log_id){
		JSONObject jo = new JSONObject();
		LogBean logBean = logService.getLogById(log_id);
		jo.put("logBean", logBean);
		return jo;
	}
	
	/**
	 * 删除日志
	 * @param ids 日志id数组[]
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/log/deleteLogByIds",method=RequestMethod.POST)
	public JSONObject deleteArticleByIds(@RequestParam(value = "ids[]") int[] ids){
		try {
			int count = logService.deleteLogByIds(ids);
			if(count > 0) {
				logger.info("删除日志：IDS="+ids);
				return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_OK,0, "删除成功");
			}else {
				logger.info("删除日志：IDS="+ids+"未成功");
				return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO,1, "删除未成功");
			}
		} catch (Exception e) {
			logger.error("删除日志：IDS=【"+ids+"】异常", e);	
			return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO,7, "删除异常");
		}
	}
}
