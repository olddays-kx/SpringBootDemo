package com.kuang.jwt.service;


import com.kuang.jwt.exception.ViolationException;

/**
 *
 * @param userName 登陆名
 * @param password 登陆密码
 * @return 用户登陆获取token
 * @throws NullPointerException 登陆用户为空
 * @throws ViolationException 登陆用户用户名或密码错误
 */
public interface LoginService {
	String getToken(String userName, String password) throws NullPointerException, ViolationException;
}
