package com.ren.blog.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.ren.blog.util.CustomException;
import com.ren.blog.util.GlobalParameter;
import com.ren.blog.util.LogAnnotation;
import com.ren.blog.util.MD5;
import com.ren.blog.util.PageUtils;
import com.ren.blog.util.UnifyResultJsonUtils;

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
		try {
			
			if(StringUtils.isEmpty(user.getUser_username())) {
				return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO, 2, "用户名不能为空");
			}
			
			if(StringUtils.isEmpty(user.getUser_password())) {
				return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO, 3, "密码不能为空");
			}
			
			if(StringUtils.isEmpty(user.getUser_email())) {
				return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO, 4, "邮箱不能为空");
			}
			
			UserBean userBean = userService.usernamerepeat(user);
			if(userBean != null) {
				return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO, 6, "用户名已存在");
			} 
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = sdf.format(new Date());
			user.setUser_createtime(date);
			user.setUser_updatetime(date);

			try {
				user.setUser_password(MD5.md5(user.getUser_password(), GlobalParameter.MD5KEY).toUpperCase());
			} catch (Exception e) {
				logger.error("密码明文转MD5异常");
				return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO, 5, "密码明文转MD5异常");
			}
			
			
			
			int count = userService.addUser(user);
			if(count > 0) {
				logger.info("新增用户：用户名称="+user.getUser_username());
				return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_OK,0, "新增成功");
			}else {
				logger.info("新增用户未成功：用户名称="+user.getUser_username());
				return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO,1, "新增未成功");
			}
			
		} catch (Exception e) {
			logger.error("新增用户：【"+user.getUser_username()+"】异常",e);
			return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO,7, "新增用户异常");
		}
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
	 * 个人获取个人信息
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/user/getUserUser",method=RequestMethod.POST)
	public JSONObject getUserUser(HttpServletRequest req){
		JSONObject jo = new JSONObject();
		UserBean user =  (UserBean) req.getSession().getAttribute(GlobalParameter.SESSION_USER_KEY);
		UserBean userbean = null;
		if(user != null) {
			 userbean = userService.getUserById(user.getUser_id());
		}
		jo.put("user", userbean);
		return jo;
	}
	
	/**
	 * 更新用户
	 * @param user 用户
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/user/updateUser",method=RequestMethod.POST)
	public JSONObject updateUser(HttpServletRequest req, UserBean user){
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
					logger.error("密码明文转MD5异常");
					return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO, 5, "密码明文转MD5异常");
				}
				count = userService.updateUserAndPas(user);
			}
			
			if(count > 0) {
				logger.info("更新用户：用户名称="+user.getUser_username());
				req.getSession().setAttribute(GlobalParameter.SESSION_USER_KEY, user);
				return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_OK, 0, "更新成功");
			}else {
				logger.info("更新用户未成功：用户名称="+user.getUser_username());
				return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO,1, "更新未成功");
			}
			
		} catch (Exception e) {
			logger.error("更新用户：【"+user.getUser_username()+"】异常",e);
			return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO,7, "更新用户异常");
		}
	}
	
	
	/**
	 * 用户更新个人信息
	 * @param user 用户
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/user/updateUserEditor",method=RequestMethod.POST)
	public JSONObject updateUserEditor(HttpServletRequest req, UserBean user){
		try {
			UserBean userB =  (UserBean) req.getSession().getAttribute(GlobalParameter.SESSION_USER_KEY);
			if(userB == null) {
				logger.info("更新用户未成功，当前用户未登录");
				return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO,3, "更新未成功,当前用户未登录");
			}
			user.setUser_id(userB.getUser_id());
			int count  = 0;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = sdf.format(new Date());
			user.setUser_updatetime(date);
			if(StringUtils.isEmpty(user.getUser_password())) {
				count = userService.updateUser_person(user);
			}else {
				try {
					user.setUser_password(MD5.md5(user.getUser_password(), GlobalParameter.MD5KEY).toUpperCase());
				} catch (Exception e) {
					logger.error("密码明文转MD5异常");
					return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO, 5, "密码明文转MD5异常");
				}
				count = userService.updateUserAndPas_person(user);
			}
			
			if(count > 0) {
				logger.info("更新用户：用户名称="+user.getUser_username());
				 req.getSession().setAttribute(GlobalParameter.SESSION_USER_KEY, user);
				return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_OK, 0, "更新成功");
			}else {
				logger.info("更新用户未成功：用户名称="+user.getUser_username());
				return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO,1, "更新未成功");
			}
			
		} catch (Exception e) {
			logger.error("更新用户：【"+user.getUser_username()+"】异常",e);
			return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO,7, "更新用户异常");
		}
	}
	
	/**
	 * 删除用户
	 * @param ids 用户ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/user/deleteUserByIds",method=RequestMethod.POST)
	public JSONObject deleteChannelByIds(@RequestParam(value = "ids[]") int[] ids){
		try {
			int count = userService.deleteUserByIds(ids);
			if(count > 0 ) {
				logger.info("删除用户：IDS="+ids);
				return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_OK, 0, "删除成功");
			}else {
				logger.info("删除用户：IDS="+ids+" 未成功");
				return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO, 1, "删除未成功");
			}
			
		} catch (Exception e) {
			logger.error("删除用户:IDS=【"+ids+"】异常", e);
			return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO, 7, "删除用户异常");
		}
	}
	
	/**
	 * 登录
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/user/login",method=RequestMethod.POST)
	@LogAnnotation(desc="登录",operType = 5,operModule=4)
	public JSONObject login(UserBean user,HttpSession session) {
		
		if(StringUtils.isEmpty(user.getUser_username())) {
			return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO, 2, "用户名不能为空");
		}
		
		if(StringUtils.isEmpty(user.getUser_password())) {
			return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO, 3, "密码不能为空");
		}
		
		if(!Pattern.matches(GlobalParameter.USERNAME, user.getUser_username())) {
			return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO, 3, GlobalParameter.USERNAME_DESC);
		}
		
		if(!Pattern.matches(GlobalParameter.PASSWORD, user.getUser_password())) {
			return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO, 3, GlobalParameter.PASSWORD_DESC);
		}
		
		try {
			user.setUser_password(MD5.md5(user.getUser_password(), GlobalParameter.MD5KEY).toUpperCase());
		} catch (Exception e) {
			logger.error("密码明文转MD5异常");
			return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO, 5, "密码明文转MD5异常");
		}
		
		UserBean userBean = userService.login(user);
		if(userBean != null) {
			if(0 == userBean.getUser_status()) {
				return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO, 6, "该用户已被禁用");
			}
			//登录成功后session
			session.setAttribute(GlobalParameter.SESSION_USER_KEY, userBean);
			return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_OK, 0, "登录成功");
		}else {
			return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO, 1, "用户名或者密码不正确");
		}
	}
	
	/**
	 * 注册
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/user/register",method=RequestMethod.POST)
	public JSONObject register(UserBean user) {
		
		if(StringUtils.isEmpty(user.getUser_username())) {
			return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO, 2, "用户名不能为空");
		}
		
		if(StringUtils.isEmpty(user.getUser_password())) {
			return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO, 3, "密码不能为空");
		}
		
		if(StringUtils.isEmpty(user.getUser_email())) {
			return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO, 4, "邮箱不能为空");
		}
		
		
		UserBean userBean = userService.usernamerepeat(user);
		if(userBean != null) {
			return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO, 6, "用户名已存在");
		} 
		
		if(!Pattern.matches(GlobalParameter.USERNAME, user.getUser_username())) {
			return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO, 7, GlobalParameter.USERNAME_DESC);
		}
		
		if(!Pattern.matches(GlobalParameter.PASSWORD, user.getUser_password())) {
			return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO, 7, GlobalParameter.PASSWORD_DESC);
		}
		
		if(!Pattern.matches(GlobalParameter.EMAIL1, user.getUser_email())) {
			return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO, 7, GlobalParameter.EMAIL1_DESC);
		}
		
		try {
			user.setUser_password(MD5.md5(user.getUser_password(), GlobalParameter.MD5KEY).toUpperCase());
		} catch (Exception e) {
			logger.error("密码明文转MD5异常",e);
			return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO, 5, "密码明文转MD5异常");
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(new Date());
		user.setUser_createtime(date);
		user.setUser_updatetime(date);
		
		user.setUser_type(1);//普通用户
		user.setUser_status(1);//启用
		
		int count = userService.addUser(user);
		if(count > 0) {
			return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_OK, 0, "注册成功");
		}else {
			return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO, 1, "注册未成功");
		}
	}
	
	/**
	 * 验证用户名是否存在
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/user/usernamerepeat",method=RequestMethod.POST)
	public JSONObject usernamerepeat(UserBean user) {
		
		if(StringUtils.isEmpty(user.getUser_username())) {
			return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_NO, 2, "用户名不能为空");
		}
		UserBean userBean = userService.usernamerepeat(user);
		if(userBean != null) {
			return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_OK, 0, "用户名已存在");
		}else {
			return UnifyResultJsonUtils.getUnifyResultJson(GlobalParameter.RETURN_STATUS_OK, 1, "用户名不存在");
		}
	}
	
	/**
	 * 跳转到login页面
	 * @return
	 * @throws CustomException 
	 */
	@RequestMapping("/login")
	public String loginPage(HttpServletRequest req) throws CustomException {
		
		UserBean user =  (UserBean) req.getSession().getAttribute(GlobalParameter.SESSION_USER_KEY);
		if(user == null) {
			return "login/login";
		}else {
			return "article/articleList";
		}
		
		
	}
	
	/**
	 * 注销
	 * @param session
	 * @return
	 * @throws CustomException
	 */
	@RequestMapping("/logout")
	public String logoutPage(HttpSession session) throws CustomException {
		session.removeAttribute(GlobalParameter.SESSION_USER_KEY);
		return "login/login";
		
	}
	
}
