package com.ren.blog.controller;

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
import com.ren.blog.bean.TagBean;
import com.ren.blog.service.TagService;
import com.ren.blog.util.GlobalParameter;
import com.ren.blog.util.PageUtils;
import com.ren.blog.util.UnifyResultJsonUtils;

/**
 * 标签管理控制器
 * @author RYF
 *
 */
@Controller
public class TagController {
	
	private Logger logger = Logger.getLogger(TagController.class);
	
	@Autowired
	private TagService TagService;
	
	/**
	 * 新增标签 
	 * @param Tag 标签
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/Tag/addTag",method=RequestMethod.POST)
	public JSONObject addTag(HttpServletResponse res,HttpServletRequest req,
			TagBean Tag){
		try {
			
			if(Tag.getTag_name().length()>15) {
				return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO, 2, "名称不能超过15个字符");
			}
			
			
			int count = TagService.addTag(Tag);
			if(count > 0 ) {
				logger.info("新增标签：标签名称="+Tag.getTag_name());
				return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_OK, 0, "新增成功");
			}else {
				logger.info("新增标签：标签名称="+Tag.getTag_name()+" 未成功");
				return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO, 1, "新增未成功");
			}
			
		} catch (Exception e) {
			logger.error("新增标签：【"+Tag.getTag_name()+"】异常",e);
			return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO, 7, "新增标签异常");
		}
	}
	
	/** 
	 * 获取所有的标签列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/Tag/getList",method=RequestMethod.POST)
	public JSONObject getList(){
		JSONObject jo = new JSONObject();
		List<TagBean> list = TagService.getList();
		jo.put("list", list);
		return jo;
	}
	
	/**
	 * 分页获取标签列表
	 * @param page 分页信息 pageSize每页大小 pageNum 当前页
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/Tag/getListPage",method=RequestMethod.POST)
	public JSONObject getListPage(PageUtils page){
		JSONObject jo = new JSONObject();
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<TagBean> list = TagService.getList();
		//获取标签个数
		int count = TagService.getTagCount();
		page.setTotal(count);
		jo.put("list", list);
		jo.put("page", page);
		return jo;
	}
	
	/**
	 * 根据标签ID获取标签
	 * @param Tag_id 标签ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/Tag/getTagById",method=RequestMethod.POST)
	public JSONObject getTagById(int Tag_id){
		JSONObject jo = new JSONObject();
		TagBean Tag = TagService.getTagById(Tag_id);
		jo.put("Tag", Tag);
		return jo;
	}
	
	/**
	 * 更新标签 
	 * @param Tag 标签
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/Tag/updateTag",method=RequestMethod.POST)
	public JSONObject updateTag( TagBean Tag){
		try {
			int count = TagService.updateTag(Tag);
			if(count > 0 ) {
				logger.info("更新标签：标签ID="+Tag.getTag_id());
				return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_OK, 0, "更新成功");
			}else {
				logger.info("更新标签：标签ID="+Tag.getTag_id()+" 未成功");
				return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO, 1, "更新未成功");
			}
		} catch (Exception e) {
			logger.info("更新标签：标签ID="+Tag.getTag_id()+" 异常");
			return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO, 7, "更新标签异常");
		}
	}
	
	/**
	 * 删除标签 
	 * @param ids 标签ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/Tag/deleteTagByIds",method=RequestMethod.POST)
	public JSONObject deleteTagByIds(@RequestParam(value = "ids[]") int[] ids){
		try {
			int count = TagService.deleteTagByIds(ids);
			if(count > 0 ) {
				logger.info("删除标签：IDS="+ids);
				return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_OK, 0, "删除成功");
			}else {
				logger.info("更新标签：标签ID="+ids +" 未成功");
				return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO, 1, "删除未成功");
			}
		} catch (Exception e) {
			logger.error("删除标签:IDS=【"+ids+"】异常", e);
			return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO, 7, "删除标签异常");
		}
	}
}
