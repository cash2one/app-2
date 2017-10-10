package com.jumper.angel.app.subscription.vo;

import java.io.Serializable;

/**
 * 评论返回数据entity
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-12-2
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class NewsInformationCommentsVo implements Serializable {

	private static final long serialVersionUID = -1153898328390980927L;
	
	private Integer id;
	
    private NewsUserInfoVo user;//所对应用户
    
    private int userId;
    
    private Integer parentId;//上级评论
    
    private String content;//评论内容
    
    private Integer isFocusImage;
    
    private String addTime;
    
    private Integer praiseCount;//点赞数量
    
    private Boolean praiseState;//点赞状态
    
    private String replyUser;//回复给用户xx
    //评论数
    private Integer commentCount;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public NewsUserInfoVo getUser() {
		return user;
	}

	public void setUser(NewsUserInfoVo user) {
		this.user = user;
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getIsFocusImage() {
		return isFocusImage;
	}

	public void setIsFocusImage(Integer isFocusImage) {
		this.isFocusImage = isFocusImage;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public Integer getPraiseCount() {
		return praiseCount;
	}

	public void setPraiseCount(Integer praiseCount) {
		this.praiseCount = praiseCount;
	}

	public Boolean getPraiseState() {
		return praiseState;
	}

	public void setPraiseState(Boolean praiseState) {
		this.praiseState = praiseState;
	}

	public String getReplyUser() {
		return replyUser;
	}

	public void setReplyUser(String replyUser) {
		this.replyUser = replyUser;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}
}
