package com.jumper.angel.app.subscription.vo;

import java.io.Serializable;

/**
 * 资讯信息返回数据entity
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-12-2
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class NewsInformationVo implements Serializable {

	private static final long serialVersionUID = -1078538850877270017L;
	
	private Integer id;
	//标题
	private String title;	
	//图片地址
	private String imageUrl;
	//logo地址
	private String fromLogoUrl;
	//关键字
	private String keywords;
	//文章来源
	private String sourceFrom;
	//新闻链接
	private String newsUrl;
	//阅读量
	private int clickCount;
	//分享数
	private int shareCount;
	//总评论数
	private int commentCount;
	//收藏数
	private int praise;
	//时间
	private String addTime;
	//是否收藏过
	private boolean collection;
	//焦点图
	private Integer isFocusImage;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getFromLogoUrl() {
		return fromLogoUrl;
	}

	public void setFromLogoUrl(String fromLogoUrl) {
		this.fromLogoUrl = fromLogoUrl;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getSourceFrom() {
		return sourceFrom;
	}

	public void setSourceFrom(String sourceFrom) {
		this.sourceFrom = sourceFrom;
	}

	public int getClickCount() {
		return clickCount;
	}

	public void setClickCount(int clickCount) {
		this.clickCount = clickCount;
	}

	public String getNewsUrl() {
		return newsUrl;
	}

	public void setNewsUrl(String newsUrl) {
		this.newsUrl = newsUrl;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public int getShareCount() {
		return shareCount;
	}

	public void setShareCount(int shareCount) {
		this.shareCount = shareCount;
	}

	public boolean isCollection() {
		return collection;
	}

	public void setCollection(boolean collection) {
		this.collection = collection;
	}

	public Integer getIsFocusImage() {
		return isFocusImage;
	}

	public void setIsFocusImage(Integer isFocusImage) {
		this.isFocusImage = isFocusImage;
	}

	public int getPraise() {
		return praise;
	}

	public void setPraise(int praise) {
		this.praise = praise;
	}
}
