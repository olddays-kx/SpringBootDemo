package com.kuang.base.jpa.foundation.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import com.kuang.base.jpa.foundation.domain.User;
import com.kuang.base.jpa.foundation.repository.UserRepository;
import com.kuang.base.jpa.foundation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	public User findUserByUserNameAndPassword(String userName, String password) {
		return this.userRepository.findUserByUserNameAndPassword(userName, password);
	}

	@Override
	public User findUserById(Long id) {
		User user = this.userRepository.getOne(id);
		if (user != null) {
			return user;
		}
		return null;
	}

	@Override
	public List<User> findByAll() {
		return this.userRepository.findAll();
	}

	@Override
	public boolean saveUser(User user) {
		boolean flag = false;
		try {
			this.userRepository.save(user);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean deleteUser(Long id) {
		boolean flag = false;
		try {
			this.userRepository.deleteById(id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public User findByParam(String userName, String password) {
		return findUserByUserNameAndPassword(userName, password);
	}
}
