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
import com.ren.blog.bean.UserBean;
import com.ren.blog.service.UserService;
import com.ren.blog.util.GlobalParameter;
import com.ren.blog.util.MD5;
import com.ren.blog.util.PageUtils;

/**
 * 用户操作控制器
 * @author RYF
 * 
 */
@Controller
public class UserController {
	
	private Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	
	/**
	 * 新增用户
	 * @param user 用户
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/user/addUser",method=RequestMethod.POST)
	public JSONObject addUser(HttpServletResponse res,HttpServletRequest req,
			UserBean user){
		JSONObject jo = new JSONObject();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = sdf.format(new Date());
			user.setUser_createtime(date);
			user.setUser_updatetime(date);

			try {
				user.setUser_password(MD5.md5(user.getUser_password(), GlobalParameter.MD5KEY).toUpperCase());
			} catch (Exception e) {
				jo.put("status", "no");
				jo.put("code", "5");
				jo.put("result", "密码明文转MD5异常");
				logger.error("密码明文转MD5异常");
				return jo;
			}
			int count = userService.addUser(user);
			if(count > 0) {
				jo.put("status", "ok");
				jo.put("code", "3");
				jo.put("result", "新增成功");
				logger.info("新增用户：用户名称="+user.getUser_username());
			}else {
				jo.put("status", "no");
				jo.put("code", "6");
				jo.put("result", "新增未成功");
				logger.info("新增用户未成功：用户名称="+user.getUser_username());
			}
			
		} catch (Exception e) {
			logger.error("新增用户：【"+user.getUser_username()+"】异常",e);
		}
		return jo;
	}
	
	/**
	 * 分页获取用户列表
	 * @param page 分页信息 pageSize每页大小 pageNum 当前页
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/user/getListPage",method=RequestMethod.POST)
	public JSONObject getListPage(PageUtils page){
		JSONObject jo = new JSONObject();
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<UserBean> list = userService.getList();
		//获取用户个数
		int count = userService.getUserCount();
		page.setTotal(count);
		jo.put("list", list);
		jo.put("page", page);
		return jo;
	}
	
	/**
	 * 根据用户ID获取用户
	 * @param user_id 用户ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/user/getUserById",method=RequestMethod.POST)
	public JSONObject getUserById(int user_id){
		JSONObject jo = new JSONObject();
		UserBean user = userService.getUserById(user_id);
		jo.put("user", user);
		return jo;
	}
	
	/**
	 * 更新用户
	 * @param user 用户
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/user/updateUser",method=RequestMethod.POST)
	public JSONObject updateUser( UserBean user){
		JSONObject jo = new JSONObject();
		try {
			int count  = 0;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = sdf.format(new Date());
			user.setUser_updatetime(date);
			if(StringUtils.isEmpty(user.getUser_password())) {
				count = userService.updateUser(user);
			}else {
				
				try {
					user.setUser_password(MD5.md5(user.getUser_password(), GlobalParameter.MD5KEY).toUpperCase());
				} catch (Exception e) {
					jo.put("status", "no");
					jo.put("code", "5");
					jo.put("result", "密码明文转MD5异常");
					logger.error("密码明文转MD5异常");
					return jo;
				}
				count = userService.updateUserAndPas(user);
			}
			
			if(count > 0) {
				jo.put("status", "ok");
				jo.put("code", "3");
				jo.put("result", "更新成功");
				logger.info("更新用户：用户名称="+user.getUser_username());
			}else {
				jo.put("status", "no");
				jo.put("code", "6");
				jo.put("result", "更新未成功");
				logger.info("更新用户未成功：用户名称="+user.getUser_username());
			}
			
		} catch (Exception e) {
			jo.put("status", "no");
			jo.put("code", "7");
			jo.put("result", "更新用户异常");
			logger.error("更新用户：【"+user.getUser_username()+"】异常",e);
		}
		return jo;
	}
	
	/**
	 * 删除用户
	 * @param ids 用户ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/user/deleteUserByIds",method=RequestMethod.POST)
	public JSONObject deleteChannelByIds(@RequestParam(value = "ids[]") int[] ids){
		JSONObject jo = new JSONObject();
		try {
			int count = userService.deleteUserByIds(ids);
			jo.put("count", count);
			logger.info("删除用户：IDS="+ids);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除用户:IDS=【"+ids+"】异常", e);
		}
		return jo;
	}
	
	/**
	 * 登录
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/user/login",method=RequestMethod.POST)
	public JSONObject login(UserBean user) {
		JSONObject jo = new JSONObject();
		
		if(StringUtils.isEmpty(user.getUser_username())) {
			jo.put("status", "no");
			jo.put("code", "1");
			jo.put("result", "用户名不能为空");
			return jo;
		}
		
		if(StringUtils.isEmpty(user.getUser_password())) {
			jo.put("status", "no");
			jo.put("code", "2");
			jo.put("result", "密码不能为空");
			return jo;
		}
		
		try {
			user.setUser_password(MD5.md5(user.getUser_password(), GlobalParameter.MD5KEY).toUpperCase());
		} catch (Exception e) {
			jo.put("status", "no");
			jo.put("code", "5");
			jo.put("result", "密码明文转MD5异常");
			logger.error("密码明文转MD5异常");
			return jo;
		}
		
		UserBean userBean = userService.login(user);
		if(userBean != null) {
			//登录成功后session设置TODO
			
			jo.put("status", "ok");
			jo.put("code", "3");
			jo.put("result", "登录成功");
		}else {
			jo.put("status", "no");
			jo.put("code", "4");
			jo.put("result", "用户名或者密码不正确");
		}
		return jo;
	}
	
	/**
	 * 注册
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/user/register",method=RequestMethod.POST)
	public JSONObject register(UserBean user) {
		JSONObject jo = new JSONObject();
		
		if(StringUtils.isEmpty(user.getUser_username())) {
			jo.put("status", "no");
			jo.put("code", "1");
			jo.put("result", "用户名不能为空");
			return jo;
		}
		
		if(StringUtils.isEmpty(user.getUser_password())) {
			jo.put("status", "no");
			jo.put("code", "2");
			jo.put("result", "密码不能为空");
			return jo;
		}
		
		if(StringUtils.isEmpty(user.getUser_email())) {
			jo.put("status", "no");
			jo.put("code", "4");
			jo.put("result", "邮箱不能为空");
			return jo;
		}
		
		try {
			user.setUser_password(MD5.md5(user.getUser_password(), GlobalParameter.MD5KEY).toUpperCase());
		} catch (Exception e) {
			jo.put("status", "no");
			jo.put("code", "5");
			jo.put("result", "密码明文转MD5异常");
			logger.error("密码明文转MD5异常");
			return jo;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(new Date());
		user.setUser_createtime(date);
		user.setUser_updatetime(date);
		
		int count = userService.addUser(user);
		if(count > 0) {
			jo.put("status", "ok");
			jo.put("code", "3");
			jo.put("result", "注册成功");
		}else {
			jo.put("status", "no");
			jo.put("code", "6");
			jo.put("result", "注册未成功");
		}
		return jo;
	}
	
	/**
	 * 验证用户名是否存在
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/user/usernamerepeat",method=RequestMethod.POST)
	public JSONObject usernamerepeat(UserBean user) {
		JSONObject jo = new JSONObject();
		
		if(StringUtils.isEmpty(user.getUser_username())) {
			jo.put("status", "no");
			jo.put("code", "1");
			jo.put("result", "用户名不能为空");
			return jo;
		}
		UserBean userBean = userService.usernamerepeat(user);
		if(userBean != null) {
			jo.put("status", "ok");
			jo.put("code", "3");
			jo.put("result", "用户名已存在");
		}else {
			jo.put("status", "no");
			jo.put("code", "2");
			jo.put("result", "用户名不存在");
		}
		return jo;
	}
	
}
