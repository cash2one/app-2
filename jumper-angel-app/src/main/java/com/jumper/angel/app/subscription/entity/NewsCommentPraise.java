package com.jumper.angel.app.subscription.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * 评论点赞
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-12-6
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class NewsCommentPraise implements Serializable {
    
	private static final long serialVersionUID = -6023855880958451900L;

	private Integer id;

    private String mobileSequence;

    private Integer commentId;

    private Date addTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMobileSequence() {
        return mobileSequence;
    }

    public void setMobileSequence(String mobileSequence) {
        this.mobileSequence = mobileSequence == null ? null : mobileSequence.trim();
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}