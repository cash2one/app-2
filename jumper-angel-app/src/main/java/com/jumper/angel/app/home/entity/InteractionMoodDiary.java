package com.jumper.angel.app.home.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * 宝妈日记entity
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-11-30
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class InteractionMoodDiary implements Serializable {
	
	private static final long serialVersionUID = -8417400826980425678L;

	private Integer id;

    private String content;

    private String imgUrl;

    private Date addTime;

    private Integer isDelete;

    private Integer userId;

    private Integer moodExpression;

    private Integer pregnantWeekId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMoodExpression() {
        return moodExpression;
    }

    public void setMoodExpression(Integer moodExpression) {
        this.moodExpression = moodExpression;
    }

    public Integer getPregnantWeekId() {
        return pregnantWeekId;
    }

    public void setPregnantWeekId(Integer pregnantWeekId) {
        this.pregnantWeekId = pregnantWeekId;
    }
}