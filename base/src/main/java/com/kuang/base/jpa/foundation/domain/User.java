package com.kuang.base.jpa.foundation.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户
 */
@Entity
@Table(name = "s_user")
public class User extends IdEntity {

	private String userName;

	private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
