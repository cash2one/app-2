package com.jumper.angel.app.encyclopedia.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * HelperQuestions entity. @author MyEclipse Persistence Tools
 */

public class HelperQuestions implements Serializable {
	
	private static final long serialVersionUID = -8417400826980425678L;

	private Integer id;
	//问题的类型id
	private Integer questionTypeId;
	//常见问题标题
	private String title;
	//常见问题回答
	private String answer;
	//问题的状态,0:正常,1:不显示
	private Integer status;
	//添加时间
	private Timestamp addTime;
	//问题详情url
	private String detailsUrl;
	//图片路径
	private String imgUrl;
	//问题描述
	private String questionIntorduction;
	private String url;

	public HelperQuestions() {
	}

	public HelperQuestions(Integer questionTypeId,
			Timestamp addTime) {
		this.questionTypeId = questionTypeId;
		this.addTime = addTime;
	}

	public HelperQuestions(Integer questionTypeId, String title,
			String answer, Integer status, Timestamp addTime,
			String detailsUrl, String imgUrl, String questionIntorduction,
			String url) {
		this.questionTypeId = questionTypeId;
		this.title = title;
		this.answer = answer;
		this.status = status;
		this.addTime = addTime;
		this.detailsUrl = detailsUrl;
		this.imgUrl = imgUrl;
		this.questionIntorduction = questionIntorduction;
		this.url = url;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getQuestionTypeId() {
		return questionTypeId;
	}

	public void setQuestionTypeId(Integer questionTypeId) {
		this.questionTypeId = questionTypeId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Timestamp getAddTime() {
		return this.addTime;
	}

	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}

	public String getDetailsUrl() {
		return this.detailsUrl;
	}

	public void setDetailsUrl(String detailsUrl) {
		this.detailsUrl = detailsUrl;
	}

	public String getImgUrl() {
		return this.imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getQuestionIntorduction() {
		return this.questionIntorduction;
	}

	public void setQuestionIntorduction(String questionIntorduction) {
		this.questionIntorduction = questionIntorduction;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}