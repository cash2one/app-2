package com.jumper.angel.app.subscription.vo;

import java.io.Serializable;

/**
 * 返回用户信息entity
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-12-2
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class NewsUserInfoVo implements Serializable {

	private static final long serialVersionUID = -6629767221208728423L;

	private int id;
	
	private String nickName = "";
	
	private String userImg = "";

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getUserImg() {
		return userImg;
	}

	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}
}
