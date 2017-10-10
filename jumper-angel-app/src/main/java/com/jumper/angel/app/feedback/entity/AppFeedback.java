package com.jumper.angel.app.feedback.entity;

import java.io.Serializable;
import java.sql.Timestamp;
/**
 * 用户反馈 entity
 * @author gyx
 * @time 2016年12月5日
 */
public class AppFeedback implements Serializable {

	private static final long serialVersionUID = -8417400826980425678L;
	
	private Integer id;
	//反馈内容
	private String content;
	//添加时间
	private Timestamp addTime;
	//状态：1:天使医生用户端；2：天使医生医生端
	private Integer status;
	//联系方式
	private String contactWay;

	public AppFeedback() {
	}

	public AppFeedback(String content, Timestamp addTime, Integer status,
			String contactWay) {
		this.content = content;
		this.addTime = addTime;
		this.status = status;
		this.contactWay = contactWay;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getAddTime() {
		return this.addTime;
	}

	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getContactWay() {
		return this.contactWay;
	}

	public void setContactWay(String contactWay) {
		this.contactWay = contactWay;
	}

}