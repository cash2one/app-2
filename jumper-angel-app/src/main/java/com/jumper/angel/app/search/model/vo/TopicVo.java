package com.jumper.angel.app.search.model.vo;

/**
 * 话题封装类
 * @author gyx
 * @time 2016年12月16日
 */
public class TopicVo {
	
	/*** 话题id*/
	private Long topicId;
	
	/*** 话题名称*/
	private String topicName;
	
	/*** 话题简介*/
	private String topicProfile;
	
	/*** 话题组主题图URL*/
	private String thematicImg;
	
	/*** 话题人员数 */
	private Integer topicMembership;
	
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
		this.topicProfile = topicProfile;
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

}