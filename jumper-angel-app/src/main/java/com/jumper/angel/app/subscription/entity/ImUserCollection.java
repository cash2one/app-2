package com.jumper.angel.app.subscription.entity;

import java.io.Serializable;

/**
 * 
 * @author gyx
 * @time 2016年12月19日
 */
public class ImUserCollection implements Serializable {
	private static final long serialVersionUID = -3704996589398370078L;
	
	//收藏id
	private long collecId;
	//用户id
	private String userId;
	//帖子id
	private long debatePostId;
	//创建时间（收藏时间）
	private long createDate;
	public long getCollecId() {
		return collecId;
	}
	public void setCollecId(long collecId) {
		this.collecId = collecId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public long getDebatePostId() {
		return debatePostId;
	}
	public void setDebatePostId(long debatePostId) {
		this.debatePostId = debatePostId;
	}
	public long getCreateDate() {
		return createDate;
	}
	public void setCreateDate(long createDate) {
		this.createDate = createDate;
	}
	
	
}
