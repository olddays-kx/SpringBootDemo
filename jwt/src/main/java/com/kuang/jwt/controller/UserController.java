package com.kuang.jwt.controller;

import com.kuang.base.jpa.foundation.domain.User;
import com.kuang.base.jpa.foundation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/add_user.htm")
	public void addUser() {
	}

	@RequestMapping("/select_user.htm")
	public String selectUser() {
		User user = this.userService.findUserById(1L);
		return user.getUserName();
	}
}

