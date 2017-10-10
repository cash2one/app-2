package com.jumper.angel.app.subscription.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * 评论entity
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-12-2
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class NewsInformationComments implements Serializable {
    
	private static final long serialVersionUID = 3693370298175784119L;

	private Integer id;

    private Integer parentId;

    private Integer newsInformationId;

    private Integer userId;

    private String content;

    private Integer isFocusImage;

    private Date addTime;

    private Integer praise;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getNewsInformationId() {
        return newsInformationId;
    }

    public void setNewsInformationId(Integer newsInformationId) {
        this.newsInformationId = newsInformationId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getIsFocusImage() {
        return isFocusImage;
    }

    public void setIsFocusImage(Integer isFocusImage) {
        this.isFocusImage = isFocusImage;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Integer getPraise() {
        return praise;
    }

    public void setPraise(Integer praise) {
        this.praise = praise;
    }
}