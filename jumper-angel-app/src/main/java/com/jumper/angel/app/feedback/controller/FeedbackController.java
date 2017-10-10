package com.jumper.angel.app.feedback.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jumper.angel.app.feedback.entity.AppFeedback;
import com.jumper.angel.app.feedback.service.AppFeedbackService;
import com.jumper.angel.frame.util.ConfigProUtils;
import com.jumper.angel.frame.util.Const;
import com.jumper.angel.frame.util.ResultMsg;
import com.jumper.angel.frame.util.TimeUtils;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * 意见反馈控制器
 * @author gyx
 * @time 2016年12月5日
 */
@Controller
@RequestMapping("feedback")
public class FeedbackController {
	private final static Logger logger = Logger.getLogger(FeedbackController.class);
	
	@Autowired
	private AppFeedbackService appFeedbackService;
	
	/**
	 * 提供客服联系电话
	 * @return
	 */
	@RequestMapping(value = "findServicePhone", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取客服电话", httpMethod = "GET", notes = "获取客服电话")
	public ResultMsg findShopUrl(){
		try {
			String service_phone = ConfigProUtils.get("service_phone");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("service_phone", service_phone);
			map.put("account_safe_url", ConfigProUtils.get("base_url")+"/user/showAccountSafe");
			map.put("function_service_url", ConfigProUtils.get("base_url")+"/user/showFunctionService");
			map.put("other_questions_url", ConfigProUtils.get("base_url")+"/user/showOtherQuestion");
			List list = new ArrayList();
			list.add(map);
			logger.info("客服电话："+service_phone);
			return new ResultMsg(Const.SUCCESS, "获取客服电话成功！", list);
		} catch (Exception e) {
			logger.error("findShopUrl()"+e.getMessage());
			return new ResultMsg(Const.FAILED, "获取客服电话失败！");
		}
	}
	
	/**
	 * 添加APP用户反馈意见
	 * @param content
	 * @param status
	 * @param contact_way
	 * @return
	 */	
	@RequestMapping(value = "addUserFeedback", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "添加用户反馈", httpMethod = "GET", notes = "添加用户反馈")
	public ResultMsg addUserFeedback(@ApiParam(name = "content", value = "反馈内容", required = true)@RequestParam("content")String content,
			@ApiParam(name = "status", value = "状态(状态:1:天使医生用户端；2：天使医生医生端)", required = true)@RequestParam("status")int status,
			@ApiParam(name = "contact_way", value = "联系方式：(手机号码/电子邮箱   可为空)", required = false)@RequestParam("contact_way")String contact_way){
		try {
			if(StringUtils.isBlank(content)){
				return new ResultMsg(Const.FAILED, "反馈内容不能为空！");
			}
			//状态值不合法默认为用户反馈
			if(StringUtils.isEmpty(status+"") || !StringUtils.isNumeric(status+"") || status<1 || status>2){
				status = 1;
			}
			AppFeedback feedback = new AppFeedback();
//			String contents = new String(content.getBytes("ISO-8859-1"),"UTF-8");
			feedback.setContent(content);
			feedback.setStatus(status);
			feedback.setAddTime(TimeUtils.getCurrentTime());
			if(StringUtils.isNotEmpty(contact_way)){
				feedback.setContactWay(contact_way);
			}
			boolean b = appFeedbackService.addUserFeedback(feedback);
			if(b){
				logger.info("addUserFeedback success:content="+content+",status="+status+",contact_way="+contact_way+",add_time="+TimeUtils.getCurrentTime());
				return new ResultMsg(Const.SUCCESS, "添加用户反馈意见成功！");
			}else{
				logger.info("addUserFeedback fail:content="+content+",status="+status+",contact_way="+contact_way+",add_time="+TimeUtils.getCurrentTime());
				return new ResultMsg(Const.FAILED, "添加用户反馈意见失败！");
			}
		} catch (Exception e) {
			logger.error("addUserFeedback():"+e.getMessage());
			return new ResultMsg(Const.FAILED, "添加用户反馈失败！");
		}
	}
	
}
