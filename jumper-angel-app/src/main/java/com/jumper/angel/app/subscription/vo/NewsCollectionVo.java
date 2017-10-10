package com.jumper.angel.app.subscription.vo;

import java.io.Serializable;

/**
 * 收藏订阅文章封装列表
 * @author gyx
 * @time 2016年12月14日
 */
 
public class NewsCollectionVo implements Serializable {

	private static final long serialVersionUID = -1078538850877270017L;
	
	private Integer id;
	//标题
	private String title;	
	//图片地址
	private String imageUrl;
	//简介
	private String keywords;
	//文章来源
	private String sourceFrom;
	//时间（展示）
	private String addTime;
	//排序时间
	private String sortTime;
	
	//类型(1:文章；2：话题)
	private int type;
	//频道id或话题id
	private int parentId;

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

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getSortTime() {
		return sortTime;
	}

	public void setSortTime(String sortTime) {
		this.sortTime = sortTime;
	}
	
	
}
