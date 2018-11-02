package com.ren.blog.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ren.blog.bean.UserBean;
import com.ren.blog.dao.UserDao;
import com.ren.blog.service.UserService;

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
	public int deleteUserByIds(int[] ids) {
		return userDao.deleteUserByIds(ids);
	}

	@Override
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

	@Override
	public int addUser(UserBean user) {
		return userDao.addUser(user);
	}

	@Override
	public List<UserBean> getList() {
		return userDao.getList();
	}


	@Override
	public int updateUserAndPas(UserBean user) {
		return userDao.updateUserAndPas(user);
	}

}
