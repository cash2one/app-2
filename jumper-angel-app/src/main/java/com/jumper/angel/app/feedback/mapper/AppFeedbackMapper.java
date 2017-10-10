package com.jumper.angel.app.feedback.mapper;

import com.jumper.angel.app.feedback.entity.AppFeedback;

/**
 * APP意见反馈mapper
 * @author gyx
 * @time 2016年12月5日
 */
public interface AppFeedbackMapper {

	/**
	 * 添加用户意见反馈
	 * @param feedback
	 */
	void addUserFeedback(AppFeedback feedback);
	
}
