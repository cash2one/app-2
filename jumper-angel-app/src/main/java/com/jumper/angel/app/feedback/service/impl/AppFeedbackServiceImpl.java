package com.jumper.angel.app.feedback.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumper.angel.app.feedback.controller.FeedbackController;
import com.jumper.angel.app.feedback.entity.AppFeedback;
import com.jumper.angel.app.feedback.mapper.AppFeedbackMapper;
import com.jumper.angel.app.feedback.service.AppFeedbackService;

/**
 * 用户APP意见反馈 ServiceImpl
 * @author gyx
 * @time 2016年12月5日
 */
@Service
public class AppFeedbackServiceImpl implements AppFeedbackService{
	private final static Logger logger = Logger.getLogger(AppFeedbackServiceImpl.class);
	@Autowired
	private AppFeedbackMapper appFeedbackMapper;

	@Override
	public boolean addUserFeedback(AppFeedback feedback) {
		try {
			appFeedbackMapper.addUserFeedback(feedback);
			return true;
		} catch (Exception e) {
			logger.error("addUserFeedback():"+e.getMessage());
			return false;
		}
	}
	
}
