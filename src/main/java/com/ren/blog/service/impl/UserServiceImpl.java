package com.ren.blog.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ren.blog.bean.UserBean;
import com.ren.blog.dao.UserDao;
import com.ren.blog.service.UserService;
import com.ren.blog.util.LogAnnotation;

/**
 * 用户操作service实现类
 * @author RYF
 *
 */
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	public UserDao userDao;

	@Override
	public UserBean login(UserBean user) {
		return userDao.searchUserByNameAndPwd(user);
	}


	@Override
	public UserBean usernamerepeat(UserBean user) {
		return userDao.searchUserByName(user);
	}

	@Override
	@LogAnnotation(desc="删除用户",operType = 3,operModule=4)
	public int deleteUserByIds(int[] ids) {
		return userDao.deleteUserByIds(ids);
	}

	@Override
	@LogAnnotation(desc="更新用户",operType = 2,operModule=4)
	public int updateUser(UserBean user) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		user.setUser_updatetime(sdf.format(new Date()));
		return userDao.updateUser(user);
	}

	@Override
	public UserBean getUserById(int user_id) {
		return userDao.getUserById(user_id);
	}

	@Override
	public int getUserCount() {
		return userDao.getUserCount();
	}

	@LogAnnotation(desc="新增/注册用户",operType = 1,operModule=4)
	public int addUser(UserBean user) {
		return userDao.addUser(user);
	}

	@Override
	public List<UserBean> getList() {
		return userDao.getList();
	}


	@Override
	@LogAnnotation(desc="更新用户（包括密码）",operType = 2,operModule=4)
	public int updateUserAndPas(UserBean user) {
		return userDao.updateUserAndPas(user);
	}


	@Override
	@LogAnnotation(desc="个人更新用户",operType = 2,operModule=4)
	public int updateUser_person(UserBean user) {
		return userDao.updateUser_person(user);
	}


	@Override
	@LogAnnotation(desc="个人更新用户（包括密码）",operType = 2,operModule=4)
	public int updateUserAndPas_person(UserBean user) {
		return userDao.updateUserAndPas_person(user);
	}

}
