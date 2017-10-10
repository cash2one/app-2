package com.jumper.angel.app.subscription.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * 频道Entity
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-12-1
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class NewsChanels implements Serializable {
	
	private static final long serialVersionUID = -3704996589398370078L;

	private Integer id;

    private String chanelName;

    private Date addTime;

    private Integer orderBy;

    private String channelDesc;

    private String imgUrl;

    private Integer channelVer;

    private Integer subNum;

    private Boolean isDefaultSub;

    private Boolean isVideo;

    private Integer hospitalId;

    private Byte state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChanelName() {
        return chanelName;
    }

    public void setChanelName(String chanelName) {
        this.chanelName = chanelName == null ? null : chanelName.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public String getChannelDesc() {
        return channelDesc;
    }

    public void setChannelDesc(String channelDesc) {
        this.channelDesc = channelDesc == null ? null : channelDesc.trim();
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public Integer getChannelVer() {
        return channelVer;
    }

    public void setChannelVer(Integer channelVer) {
        this.channelVer = channelVer;
    }

    public Integer getSubNum() {
        return subNum;
    }

    public void setSubNum(Integer subNum) {
        this.subNum = subNum;
    }

    public Boolean getIsDefaultSub() {
        return isDefaultSub;
    }

    public void setIsDefaultSub(Boolean isDefaultSub) {
        this.isDefaultSub = isDefaultSub;
    }

    public Boolean getIsVideo() {
        return isVideo;
    }

    public void setIsVideo(Boolean isVideo) {
        this.isVideo = isVideo;
    }

    public Integer getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Integer hospitalId) {
        this.hospitalId = hospitalId;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }
}