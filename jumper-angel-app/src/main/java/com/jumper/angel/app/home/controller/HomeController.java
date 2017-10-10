package com.jumper.angel.app.home.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jumper.angel.app.home.entity.InteractionMoodDiary;
import com.jumper.angel.app.home.entity.PregnancyInfo;
import com.jumper.angel.app.home.entity.PregnantAntenatalExaminationInfo;
import com.jumper.angel.app.home.service.InteractionMoodDiaryService;
import com.jumper.angel.app.home.service.PregnancyInfoService;
import com.jumper.angel.app.home.service.PregnantAntenatalExaminationInfoService;
import com.jumper.angel.app.subscription.entity.NewsChanels;
import com.jumper.angel.app.subscription.entity.UserSubscribeChannel;
import com.jumper.angel.app.subscription.service.NewsChanelsService;
import com.jumper.angel.app.subscription.service.UserSubscribeChannelService;
import com.jumper.angel.app.subscription.vo.UserSubscribeChannelVo;
import com.jumper.angel.frame.util.ConfigProUtils;
import com.jumper.angel.frame.util.Const;
import com.jumper.angel.frame.util.DateUtil;
import com.jumper.angel.frame.util.ResultMsg;
import com.jumper.angel.frame.util.TimeUtils;
import com.jumper.dubbo.bean.bo.UserBasicInfo;
import com.jumper.dubbo.bean.po.UserExtraInfo;
import com.jumper.dubbo.bean.po.UserInfo;
import com.jumper.dubbo.service.UserService;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * 首页控制器
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-11-29
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
@Controller
@RequestMapping("home")
public class HomeController {
	
	private final static Logger logger = Logger.getLogger(HomeController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PregnancyInfoService pregnancyInfoService;
	
	@Autowired
	private InteractionMoodDiaryService interactionMoodDiaryService;
	
	@Autowired
	private UserSubscribeChannelService userSubscribeChannelService;
	
	@Autowired
	private PregnantAntenatalExaminationInfoService pregnantAntenatalExaminationInfoService;
	
	@Autowired
	private NewsChanelsService newsChanelsService;
	
	/**
	 * 孕期日历
	 * @version 1.0
	 * @createTime 2016-11-30,上午9:15:56
	 * @updateTime 2016-11-30,上午9:15:56
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @return
	 */
	@RequestMapping(value="pregnancyCalendar/{userId}/{date}", method=RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="孕期日历", httpMethod="GET", notes="孕期日历")
	public ResultMsg pregnancyCalendar(@ApiParam(name="userId", value="用户ID", required = true) @PathVariable("userId") long userId, 
			@ApiParam(name="date", value="日期(格式：yyyy-MM-dd)", required = true) @PathVariable("date") String date) {
		try {
			//用户信息
			UserInfo userInfo = userService.getAllUserInfoByUserId((int)userId);
			if(userInfo == null) {
				return new ResultMsg(Const.FAILED, "该用户不存在！");
			}
			//用户拓展数据
			UserExtraInfo userExtra = userService.getExtraUserInfoByUserId(userInfo.getId());
			if(userExtra.getCurrentIdentity() == 0) {
				if(userInfo.getExpectedDateOfConfinement() == null) {
					return new ResultMsg(Const.FAILED, "用户预产期为空！");
				}
			}
			//计算孕周 [周, 天, 离预产期的天数]
			int[] week = DateUtil.getPregnantWeek(userInfo.getExpectedDateOfConfinement(), TimeUtils.convertToDate(date));
			//孕期信息
			PregnancyInfo pregnancyInfo = pregnancyInfoService.findPrepnancyInfoByUserIdAndWeek(week[0]);
			//返回
			Map<String, Object> result = new HashMap<String, Object>();
			if(week[1] == 0) {
				if(week[0] == 0){
					result.put("week", "备孕期");//孕周
				}else{
					result.put("week", "孕"+pregnancyInfo.getWeek()+"周");//孕周
				}
			} else {
				result.put("week", "孕"+pregnancyInfo.getWeek()+"周+"+week[1]+"天");//孕周
			}
			//有宝宝
			if(TimeUtils.convertToDate(date).getTime() >= userInfo.getExpectedDateOfConfinement().getTime()) {
				result.put("week", "孕40周");//孕周
				result.put("distanceDate", "0天");//距离预产期天数
			} else {
				result.put("distanceDate", week[2]+"天");//距离预产期天数
			}
			result.put("fetalWeight", pregnancyInfo.getFetalWeight()+"g");//胎重
			result.put("fetalHeight", pregnancyInfo.getFetalHeight()+"cm");//身长
			result.put("bothNeck", pregnancyInfo.getBothNeck());//双顶颈
			result.put("weekDescription", pregnancyInfo.getWeekDescription());//孕期描述
			result.put("imageUrl", ConfigProUtils.get("file_path")+pregnancyInfo.getImageUrl());//图片
			return new ResultMsg(Const.SUCCESS, "获取孕期信息成功！", result);
		} catch(Exception e) {
			logger.error("pregnancyCalendar()", e);
			return new ResultMsg(Const.FAILED, "获取孕期信息失败！");
		}
	}
	
	/**
	 * v4.1.5孕期日历
	 * @version v.4.1.5
	 * @createTime 2017年5月12日,下午4:29:20
	 * @updateTime 2017年5月12日,下午4:29:20
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param userId
	 * @param date
	 * @return
	 */
	@RequestMapping(value="v4.1.5/pregnancyCalendar/{userId}/{date}", method=RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="v4.1.5孕期日历", httpMethod="GET", notes="v4.1.5孕期日历")
	public ResultMsg pregnancyCalendar_415(@ApiParam(name="userId", value="用户ID", required = true) @PathVariable("userId") long userId, 
			@ApiParam(name="date", value="日期(格式：yyyy-MM-dd)", required = true) @PathVariable("date") String date) {
		try {
			//用户信息
			UserInfo userInfo = userService.getAllUserInfoByUserId((int)userId);
			if(userInfo == null) {
				return new ResultMsg(Const.FAILED, "该用户不存在！");
			}
			//用户拓展数据
			UserExtraInfo userExtra = userService.getExtraUserInfoByUserId(userInfo.getId());
			//用户是备孕期
			if(userExtra.getCurrentIdentity().intValue() == 2) {
				//孕期信息
				PregnancyInfo pregnancyInfo = pregnancyInfoService.findPrepnancyInfoByUserIdAndWeek(-1);
				//胎重
				String fetalWeight = StringUtils.isEmpty(pregnancyInfo.getFetalWeight())?"0":pregnancyInfo.getFetalWeight();
				//身长
				String fetalHeight = StringUtils.isEmpty(pregnancyInfo.getFetalHeight())?"0":pregnancyInfo.getFetalHeight();
				//双顶颈
				String bothNeck = StringUtils.isEmpty(pregnancyInfo.getBothNeck())?"0":pregnancyInfo.getBothNeck();
				//返回
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("week", "备孕期");
				result.put("distanceDate", "280天");//距离预产期天数
				result.put("fetalWeight", fetalWeight+"g");//胎重
				result.put("fetalHeight", fetalHeight+"cm");//身长
				result.put("bothNeck", bothNeck);//双顶颈
				result.put("weekDescription", pregnancyInfo.getWeekDescription());//孕期描述
				result.put("imageUrl", ConfigProUtils.get("file_path")+pregnancyInfo.getImageUrl());//图片
				return new ResultMsg(Const.SUCCESS, "获取孕期信息成功！", result);
			}
			if(userExtra.getCurrentIdentity() == 0) {
				if(userInfo.getExpectedDateOfConfinement() == null) {
					return new ResultMsg(Const.FAILED, "用户预产期为空！");
				}
			}
			//计算孕周 [周, 天, 离预产期的天数]
			int[] week = DateUtil.getPregnantWeek_415(userInfo.getExpectedDateOfConfinement(), TimeUtils.convertToDate(date));
			//孕期信息
			PregnancyInfo pregnancyInfo = pregnancyInfoService.findPrepnancyInfoByUserIdAndWeek(week[0]>40?40:week[0]);
			//胎重
			String fetalWeight = StringUtils.isEmpty(pregnancyInfo.getFetalWeight())?"0":pregnancyInfo.getFetalWeight();
			//身长
			String fetalHeight = StringUtils.isEmpty(pregnancyInfo.getFetalHeight())?"0":pregnancyInfo.getFetalHeight();
			//双顶颈
			String bothNeck = StringUtils.isEmpty(pregnancyInfo.getBothNeck())?"0":pregnancyInfo.getBothNeck();
			//返回
			Map<String, Object> result = new HashMap<String, Object>();
			if(week[1] == 0) {
				if(week[0] == 0){
					result.put("week", "备孕期");//孕周
				}else{
					result.put("week", "孕"+week[0]+"周");//孕周
				}
			} else {
				result.put("week", "孕"+week[0]+"周+"+week[1]+"天");//孕周
			}
			//有宝宝
			if(TimeUtils.convertToDate(date).getTime() >= userInfo.getExpectedDateOfConfinement().getTime()) {
//				result.put("week", "孕40周");//孕周
				result.put("distanceDate", "0天");//距离预产期天数
			} else {
				result.put("distanceDate", week[2]+"天");//距离预产期天数
			}
			result.put("fetalWeight", fetalWeight+"g");//胎重
			result.put("fetalHeight", fetalHeight+"cm");//身长
			result.put("bothNeck", bothNeck);//双顶颈
			result.put("weekDescription", pregnancyInfo.getWeekDescription());//孕期描述
			result.put("imageUrl", ConfigProUtils.get("file_path")+pregnancyInfo.getImageUrl());//图片
			return new ResultMsg(Const.SUCCESS, "获取孕期信息成功！", result);
		} catch(Exception e) {
			logger.error("pregnancyCalendar()", e);
			return new ResultMsg(Const.FAILED, "获取孕期信息失败！");
		}
	}
	
	/**
	 * 新增宝妈日记
	 * @version 1.0
	 * @createTime 2016-11-30,下午5:21:18
	 * @updateTime 2016-11-30,下午5:21:18
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @return
	 */
	@RequestMapping(value="addMoodDiary", method=RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value="新增宝妈日记", httpMethod="POST", notes="新增宝妈日记")
	public ResultMsg addMoodDiary(@ApiParam(name="param", value="宝妈日记json数据", required = true) @RequestBody InteractionMoodDiary diary,
			HttpServletRequest request) {
		try {
			//用户信息
			UserBasicInfo userInfo = userService.getBasicInfoByUserId((int) diary.getUserId());
			if(userInfo == null) {
				return new ResultMsg(Const.FAILED, "该用户不存在！");
			}
			if(diary.getContent() == null || StringUtils.isEmpty(diary.getContent())) {
				return new ResultMsg(Const.FAILED, "内容不能为空！");
			}
			if(diary.getMoodExpression() == 0) {
				return new ResultMsg(Const.FAILED, "请选择心情！");
			}
			//新增宝妈日记
			interactionMoodDiaryService.save(diary);
			return new ResultMsg(Const.SUCCESS, "日记新增成功！");
		} catch (Exception e) {
			logger.error("addMoodDiary()", e);
			return new ResultMsg(Const.FAILED, "日记新增失败！");
		}
	}
	
	/**
	 * 根据孕周获取产检提醒入口
	 * @param pregnantWeek
	 * @return
	 */
	@RequestMapping(value="findPrenatalRemind/{pregnantWeek}", method=RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="产检提醒", httpMethod="GET", notes="判断是否显示产检提醒入口")
	public ResultMsg findPrenatalRemind(@ApiParam(name="pregnantWeek", value="孕周", required=true)@PathVariable("pregnantWeek") int pregnantWeek){
		try{
			if(StringUtils.isEmpty(pregnantWeek+"")){
				return new ResultMsg(Const.FAILED, "孕周不能为空！");
			}
			if(pregnantWeek == -1){
				Map<String, Object> result = new HashMap<String, Object>();
				//备孕期默认显示第一次产检信息
				PregnantAntenatalExaminationInfo examinationInfo = pregnantAntenatalExaminationInfoService.findPreRemindByNumbers(1);
				result.put("examinationNumbers", 1);
				result.put("pregAnteExamInfoId", examinationInfo.getId());
				result.put("isShow", 0);
				return new ResultMsg(Const.SUCCESS, "默认显示第一次产检信息！", result);
			}
			//当前孕周对应的产检信息
			PregnantAntenatalExaminationInfo pregnantAntenatalExaminationInfo = null;
			List<PregnantAntenatalExaminationInfo> pregList = pregnantAntenatalExaminationInfoService.findPrenatalRemindByWeek(pregnantWeek);
			if(pregList != null && pregList.size() == 1){
				pregnantAntenatalExaminationInfo = pregList.get(0);
			}
			if(pregList != null && pregList.size() > 1){
				for (PregnantAntenatalExaminationInfo pregAnteExamInfo : pregList) {
					if(pregnantWeek <= pregAnteExamInfo.getRemindWeek()){
						pregnantAntenatalExaminationInfo = pregAnteExamInfo;
						break;
					}
				}
			}
			if(pregnantAntenatalExaminationInfo == null){//表中没有孕周范围内的数据
				Map<String, Object> result = new HashMap<String, Object>();
				//表中没有孕周范围内的数据，默认查询第一次产检信息
				PregnantAntenatalExaminationInfo examinationInfo = pregnantAntenatalExaminationInfoService.findPreRemindByNumbers(1);
				result.put("examinationNumbers", 1);
				result.put("pregAnteExamInfoId", examinationInfo.getId());
				result.put("isShow", 0);
				return new ResultMsg(Const.SUCCESS, "该孕周没有对应的产检信息！", result);
				
			}else if(pregnantAntenatalExaminationInfo.getRemindWeek() != pregnantWeek){//当前孕周不提醒产检
				Map<String, Object> result = new HashMap<String, Object>();
				//产检次数
				Integer examinationNumbers = pregnantAntenatalExaminationInfo.getExaminationNumbers();
				Integer pregAnteExamInfoId = pregnantAntenatalExaminationInfo.getId();
				result.put("examinationNumbers", examinationNumbers);
				result.put("pregAnteExamInfoId", pregAnteExamInfoId);
				//不显示产检提醒入口
				result.put("isShow", 0);
				return new ResultMsg(Const.SUCCESS, "不显示产检提醒入口！", result);
			}else{//当前孕周需要提醒产检
				Map<String, Object> result = new HashMap<String, Object>();
				//产检次数
				Integer examinationNumbers = pregnantAntenatalExaminationInfo.getExaminationNumbers();
				Integer pregAnteExamInfoId = pregnantAntenatalExaminationInfo.getId();
				//首页提示文案
				String remindInfo = "您应该做第"+examinationNumbers+"次产检了！";
				result.put("examinationNumbers", examinationNumbers);
				result.put("pregAnteExamInfoId", pregAnteExamInfoId);
				//显示产检提醒入口
				result.put("isShow", 1);
				result.put("remindInfo", remindInfo);
				return new ResultMsg(Const.SUCCESS, "显示产检提醒入口！", result);
			}
		}catch(Exception e){
			logger.error("findPrenatalRemind()", e);
			return new ResultMsg(Const.FAILED, "获取产检提醒入口失败！");
		}
	}
	
	/**
	 * 用户订阅信息
	 * @version 1.0
	 * @createTime 2016-12-1,下午5:04:12
	 * @updateTime 2016-12-1,下午5:04:12
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="subscribeChannel/{userId}/{period}", method=RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="用户订阅信息", httpMethod="GET", notes="用户订阅信息")
	public ResultMsg subscribeChannel(@ApiParam(name="userId", value="用户ID", required = true) @PathVariable("userId") long userId,
			@ApiParam(name="period", value="孕期阶段", required = true) @PathVariable("period") int period) {
		try {
			//用户订阅信息
			List<Map<String, Object>> list = userSubscribeChannelService.findUserSubscribeChannel(userId, period);
			if(list!=null && list.size()>0) {
				return new ResultMsg(Const.SUCCESS, "获取用户订阅成功！", list);
			} else {
				return new ResultMsg(Const.NODATA, "请求的数据为空！", new ArrayList<Map<String, Object>>());
			}
		} catch (Exception e) {
			logger.error("subscribeChannel()", e);
			return new ResultMsg(Const.FAILED, "获取用户订阅失败！");
		}
	}
	
	/**
	 * 频道订阅
	 * @version 1.0
	 * @createTime 2016-12-6,上午11:22:42
	 * @updateTime 2016-12-6,上午11:22:42
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="findSubscribeChannel/{userId}", method=RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="频道订阅", httpMethod="GET", notes="频道订阅")
	public ResultMsg findSubscribeChannel(@ApiParam(name="userId", value="用户ID", required = true) @PathVariable("userId") long userId) {
		try {
			//用户信息
			UserInfo userInfo = userService.getAllUserInfoByUserId((int)userId);
			if(userInfo == null) {
				return new ResultMsg(Const.FAILED, "该用户不存在！");
			}
			//用户拓展数据
			UserExtraInfo userExtra = userService.getExtraUserInfoByUserId(userInfo.getId());
			//计算孕周 [周, 天, 离预产期的天数]
			int[] week = DateUtil.getPregnantWeek(userInfo.getExpectedDateOfConfinement(), new Date());
			//通过孕周得到孕期阶段
			int stage = DateUtil.getPregnantStageByWeek(userExtra.getCurrentIdentity().intValue(), week[0]);
			//查询符合条件的频道
			List<UserSubscribeChannelVo> list = userSubscribeChannelService.findUserSubscribeChannelAll(userId, stage);
			if(list!=null && list.size()>0) {
				return new ResultMsg(Const.SUCCESS, "获取频道订阅列表成功！", list);
			} else {
				return new ResultMsg(Const.NODATA, "请求的数据为空！", new ArrayList<UserSubscribeChannelVo>());
			}
		} catch (Exception e) {
			logger.error("findSubscribeChannel()", e);
			return new ResultMsg(Const.FAILED, "获取频道订阅失败！");
		}
	}
	
	/**
	 * 订阅
	 * @version 1.0
	 * @createTime 2016-12-6,上午11:51:26
	 * @updateTime 2016-12-6,上午11:51:26
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param userId 用户ID
	 * @param channelId 频道ID
	 * @param flag 订阅状态 0:订阅 1:取消订阅
	 * @return
	 */
	@RequestMapping(value="subscription/{userId}/{channelId}/{flag}", method=RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="订阅", httpMethod="GET", notes="订阅")
	public ResultMsg subscription(@ApiParam(name="userId", value="用户ID", required = true) @PathVariable("userId") long userId,
			@ApiParam(name="channelId", value="频道ID", required = true) @PathVariable("channelId") long channelId,
			@ApiParam(name="flag", value="订阅状态(0:订阅    1:取消订阅)", required = true) @PathVariable("flag") int flag) {
		try {
			//订阅
			if(flag == 0) {
				UserSubscribeChannel channel = new UserSubscribeChannel();
				channel.setUserId((int)userId);
				channel.setChannelId((int)channelId);
				channel.setAddTime(new Date());
				userSubscribeChannelService.saveUserSubscribeChannel(channel);
				return new ResultMsg(Const.SUCCESS, "订阅成功！");
			} else {//取消订阅
				//频道信息
				NewsChanels channel = newsChanelsService.findNewChannelById((int)channelId);
				if(channel.getChanelName().equals("孕期知识")) {
					return new ResultMsg(Const.FAILED, "该频道不能取消订阅！");
				}
				userSubscribeChannelService.deleteUserSubscribeChannel(userId, channelId);
				return new ResultMsg(Const.SUCCESS, "取消订阅成功！");
			}
		} catch (Exception e) {
			logger.error("SubscribeChannel()", e);
			return new ResultMsg(Const.FAILED, "频道订阅失败！");
		}
	}
}
