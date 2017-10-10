package com.jumper.angel.app.encyclopedia.vo;

import java.io.Serializable;

public class VOHelperQuestionInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	//问题标题
	private String title;
	//图片路径
	private String imgUrl;
	//问题描述
	private String introduction;
	//详情地址
	private String detailsUrl;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getDetailsUrl() {
		return detailsUrl;
	}
	public void setDetailsUrl(String detailsUrl) {
		this.detailsUrl = detailsUrl;
	}
}
