package com.jumper.angel.app.subscription.vo;

import java.io.Serializable;

public class UserSubscribeChannelVo implements Serializable {
	
	private static final long serialVersionUID = 3024405035686664867L;

	private Integer channelId = 0;     //频道id
	
	private String name = "";          //频道名称
	
	private String channelDesc  = "";  //频道描述
	
	private String imgUrl = "";        //频道图片
	
	private Boolean isSubscribe = false;  //是否订阅了此频道

	private Boolean isOpenClass = false;

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getChannelDesc() {
		return channelDesc;
	}

	public void setChannelDesc(String channelDesc) {
		this.channelDesc = channelDesc;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Boolean getIsSubscribe() {
		return isSubscribe;
	}

	public void setIsSubscribe(Boolean isSubscribe) {
		this.isSubscribe = isSubscribe;
	}

	public Boolean getIsOpenClass() {
		return isOpenClass;
	}

	public void setIsOpenClass(Boolean isOpenClass) {
		this.isOpenClass = isOpenClass;
	}
}
