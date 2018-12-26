package com.ren.blog.dao;

import java.util.List;

import com.ren.blog.bean.UserBean;

/**
 * 用户操作dao
 * @author RYF
 *
 */
public interface UserDao {

	/**
	 * 根据用户名和密码查询用户
	 * @param userBean
	 * @return userBean
	 */
	UserBean searchUserByNameAndPwd(UserBean user);


	/**
	 * 根据用户名查询用户
	 * @param user
	 * @return
	 */
	UserBean searchUserByName(UserBean user);

	/**
	 * 删除用户
	 * @param ids
	 * @return
	 */
	int deleteUserByIds(int[] ids);

	/**
	 * 更新用户
	 * @param user
	 * @return
	 */
	int updateUser(UserBean user);

	/**
	 * 根据id获取用户信息 
	 * @param user_id
	 * @return
	 */
	UserBean getUserById(int user_id);

	/**
	 * 获取用户总个数
	 * @return
	 */
	int getUserCount();

	/**
	 * 新增用户
	 * @param user
	 * @return
	 */
	int addUser(UserBean user);

	/**
	 * 获取用户列表
	 * @return
	 */
	List<UserBean> getList();


	/**
	 * 更新用户 包括密码
	 * @param user
	 * @return
	 */
	int updateUserAndPas(UserBean user);


	/**
	 * 个人更新资料
	 * @param user
	 * @return
	 */
	int updateUser_person(UserBean user);

	/**
	 * 个人更新资料包括密码
	 * @param user
	 * @return
	 */
	int updateUserAndPas_person(UserBean user);

	
	
}
