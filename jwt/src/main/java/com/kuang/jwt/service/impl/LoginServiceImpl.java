package com.kuang.jwt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.kuang.base.jpa.foundation.domain.User;
import com.kuang.base.jpa.foundation.service.UserService;
import com.kuang.jwt.exception.ViolationException;
import com.kuang.jwt.service.LoginService;


public class LoginServiceImpl implements LoginService {

	@Autowired
	private UserService userService;

	@Override
	public String getToken(String userName, String password)throws NullPointerException, ViolationException {
		if ("".equals(userName) || "".equals(password)) {
			throw new NullPointerException("用户名或密码不能为空");
		}
		if (userName == null || password == null) {
			throw new NullPointerException("用户名或密码不能为空");
		}
		if (this.userService.findByParam(userName, password) == null) {
			throw new ViolationException("登录失败，用户名或密码错误");
		}
		//查找用户名
		User user = this.userService.findByParam(userName, password);
		Date date = new Date();
		long currentTime = System.currentTimeMillis() + 60 * 60 * 1000; //一个小时
		Date end = new Date(currentTime);
		String token = JWT.create().withAudience(String.valueOf(user.getId())).withIssuedAt(date).withExpiresAt(end)
							.sign(Algorithm.HMAC256(user.getPassword()));
		return token;
	}
}
