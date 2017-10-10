package com.jumper.angel.app.feedback.service;

import com.jumper.angel.app.feedback.entity.AppFeedback;

/**
 * 用户反馈意见Service
 * @author gyx
 * @time 2016年12月5日
 */
public interface AppFeedbackService {

	/**
	 * 添加用户意见反馈
	 * @param feedback
	 * @return
	 */
	boolean addUserFeedback(AppFeedback feedback);
	
}
