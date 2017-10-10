package com.jumper.angel.app.usercenter.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * 用户保健号关联 entity
 * @author gyx
 * @time 2016年12月7日
 */
public class UserHealthNumber implements Serializable{
	
	private static final long serialVersionUID = -8417400826980425678L;
	//主键
	private long id;
	//用户id
	private Integer userId;
	//医院id
	private Integer hospitalId;
	//保健号
	private String healthNum;
	//添加时间
	private Date addTime;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getHospitalId() {
		return hospitalId;
	}
	public void setHospitalId(Integer hospitalId) {
		this.hospitalId = hospitalId;
	}
	public String getHealthNum() {
		return healthNum;
	}
	public void setHealthNum(String healthNum) {
		this.healthNum = healthNum;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	
	
}
