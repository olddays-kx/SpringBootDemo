package com.kuang.base.jpa.foundation.repository;

import com.kuang.base.jpa.foundation.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	User findUserByUserNameAndPassword(String userName, String password);
}
