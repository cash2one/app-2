package com.jumper.angel.app.subscription.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * 收藏entity
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-12-2
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class NewsCollection implements Serializable {
  
	private static final long serialVersionUID = -3155270017066089468L;

	private Integer id;

    private Integer userId;

    private Integer newsInformationId;

    private Date addTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getNewsInformationId() {
        return newsInformationId;
    }

    public void setNewsInformationId(Integer newsInformationId) {
        this.newsInformationId = newsInformationId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}