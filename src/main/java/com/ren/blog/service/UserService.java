package com.ren.blog.service;

import java.util.List;

import com.ren.blog.bean.UserBean;

/**
 * 用户操作 service
 * @author RYF
 *
 */
public interface UserService {

	UserBean login(UserBean user);


	/**
	 * 验证用户名是否重复
	 * @param user
	 * @return
	 */
	UserBean usernamerepeat(UserBean user);

	int deleteUserByIds(int[] ids);

	int updateUser(UserBean user);

	UserBean getUserById(int user_id);

	int getUserCount();

	int addUser(UserBean user);

	List<UserBean> getList();


	int updateUserAndPas(UserBean user);


}
