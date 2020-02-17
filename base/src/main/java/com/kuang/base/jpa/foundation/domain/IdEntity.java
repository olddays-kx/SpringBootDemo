package com.kuang.base.jpa.foundation.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class IdEntity implements Serializable {

	/**
	 * id
	 */
	@Id
	@GeneratedValue
	private Long id;

	/**
	 * 添加时间
	 */
	private Date addTime;

	/**
	 * 创建人
	 */
	private String creator;

	/**
	 * 删除状态 0正常 1删除
	 */
	private boolean deleteStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public boolean isDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(boolean deleteStatus) {
		this.deleteStatus = deleteStatus;
	}
}
