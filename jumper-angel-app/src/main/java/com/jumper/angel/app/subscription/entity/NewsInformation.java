package com.jumper.angel.app.subscription.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * 资讯信息Entity
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-12-1
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class NewsInformation implements Serializable {
    
	private static final long serialVersionUID = -7106686341518400070L;

	private Integer id;

    private Integer channelId;

    private Integer specialTopicId;

    private String title;

    private String newsUrl;

    private String imageUrl;

    private Integer isFocusImage;

    private Date addTime;

    private Integer clicks;

    private Integer isPush;

    private String introduct;

    private String fromLogoUrl;

    private String sourceFrom;

    private String keywords;

    private Integer isPublish;

    private Integer shareNum;

    private Integer praise;

    private String period;

    private Integer hospitalId;

    private Date top;

    private Date publishTime;

    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public Integer getSpecialTopicId() {
        return specialTopicId;
    }

    public void setSpecialTopicId(Integer specialTopicId) {
        this.specialTopicId = specialTopicId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl == null ? null : newsUrl.trim();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
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

    public Integer getClicks() {
        return clicks;
    }

    public void setClicks(Integer clicks) {
        this.clicks = clicks;
    }

    public Integer getIsPush() {
        return isPush;
    }

    public void setIsPush(Integer isPush) {
        this.isPush = isPush;
    }

    public String getIntroduct() {
        return introduct;
    }

    public void setIntroduct(String introduct) {
        this.introduct = introduct == null ? null : introduct.trim();
    }

    public String getFromLogoUrl() {
        return fromLogoUrl;
    }

    public void setFromLogoUrl(String fromLogoUrl) {
        this.fromLogoUrl = fromLogoUrl == null ? null : fromLogoUrl.trim();
    }

    public String getSourceFrom() {
        return sourceFrom;
    }

    public void setSourceFrom(String sourceFrom) {
        this.sourceFrom = sourceFrom == null ? null : sourceFrom.trim();
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords == null ? null : keywords.trim();
    }

    public Integer getIsPublish() {
        return isPublish;
    }

    public void setIsPublish(Integer isPublish) {
        this.isPublish = isPublish;
    }

    public Integer getShareNum() {
        return shareNum;
    }

    public void setShareNum(Integer shareNum) {
        this.shareNum = shareNum;
    }

    public Integer getPraise() {
        return praise;
    }

    public void setPraise(Integer praise) {
        this.praise = praise;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period == null ? null : period.trim();
    }

    public Integer getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Integer hospitalId) {
        this.hospitalId = hospitalId;
    }

    public Date getTop() {
        return top;
    }

    public void setTop(Date top) {
        this.top = top;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}