package com.jumper.angel.app.search.model.po;

public class Topic {
	private Long topicId;
	/**
	 * 话题名称
	 */
	private String topicName;
	/**
	 * 话题简介
	 */
	private String topicProfile;
	/**
	 * 话题组主题图URL
	 */
	private String thematicImg;
	/**
	 * 话题人员数
	 */
	private Integer topicMembership;
	/**
	 * 话题帖子数量
	 */
	private Integer totalposts;
	/**
	 * 数据状态
	 */
	private Integer dataState;
	/**
	 * 是否删除
	 */
	private Integer isDelete;
	/**
	 * 创建人
	 */
	private String createUserId;
	/**
	 * 创建时间
	 */
	private Long createTime;

	private String createDate;

	/** 是否已加入 1：已加入 0：未加入 */
	private Integer yesOrNo;

	public Long getTopicId() {
		return topicId;
	}

	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName == null ? null : topicName.trim();
	}

	public String getTopicProfile() {
		return topicProfile;
	}

	public void setTopicProfile(String topicProfile) {
		this.topicProfile = topicProfile == null ? null : topicProfile.trim();
	}

	public String getThematicImg() {
		return thematicImg;
	}

	public void setThematicImg(String thematicImg) {
		this.thematicImg = thematicImg == null ? null : thematicImg.trim();
	}

	public Integer getTopicMembership() {
		return topicMembership;
	}

	public void setTopicMembership(Integer topicMembership) {
		this.topicMembership = topicMembership;
	}

	public Integer getTotalposts() {
		return totalposts;
	}

	public void setTotalposts(Integer totalposts) {
		this.totalposts = totalposts;
	}

	public Integer getDataState() {
		return dataState;
	}

	public void setDataState(Integer dataState) {
		this.dataState = dataState;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId == null ? null : createUserId.trim();
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Integer getYesOrNo() {
		return yesOrNo;
	}

	public void setYesOrNo(Integer yesOrNo) {
		this.yesOrNo = yesOrNo;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

}