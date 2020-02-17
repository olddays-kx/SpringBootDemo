package com.kuang.base.jpa.foundation.service;

import java.util.List;

import com.kuang.base.jpa.foundation.domain.User;

public interface UserService {
	/**
	 * 根据ID查找用户
	 */
	User findUserById(Long id);

	/**
	 * 查找所有的用户
	 */
	List<User> findByAll();

	/**
	 * 新增用户
	 */
	boolean saveUser(User user);

	/**
	 * 删除用户
	 */
	boolean deleteUser(Long id);

	User findByParam(String userName, String password);
}
