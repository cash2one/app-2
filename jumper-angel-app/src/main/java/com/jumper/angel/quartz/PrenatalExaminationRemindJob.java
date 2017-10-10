package com.jumper.angel.quartz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jumper.angel.app.home.entity.PregnantAntenatalExaminationInfo;
import com.jumper.angel.app.home.mapper.PregnantAntenatalExaminationInfoMapper;
import com.jumper.angel.frame.util.ConfigProUtils;
import com.jumper.angel.frame.util.Const;
import com.jumper.angel.frame.util.DateUtil;
import com.jumper.angel.frame.util.TimeUtils;
import com.jumper.common.model.im.bo.ImAccountsBo;
import com.jumper.common.model.user.bo.ChatUserBo;
import com.jumper.common.model.user.vo.UserBaseInfo;
import com.jumper.common.service.im.ImAccountsService;
import com.jumper.common.service.im.ImMsgService;
import com.jumper.common.service.user.ChatUserService;
import com.jumper.common.util.json.JsonUtils;
import com.jumper.common.utils.CommonReturnMsg;
import com.jumper.dubbo.bean.bo.UserBasicInfo;
import com.jumper.dubbo.bean.bo.UserIdExpertDateBo;
import com.jumper.dubbo.bean.po.UserInfo;
import com.jumper.dubbo.service.UserService;


/**
 * 产检提醒调度任务
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-10-27
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
@Component
public class PrenatalExaminationRemindJob {
	
	private static Logger logger = Logger.getLogger(PrenatalExaminationRemindJob.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PregnantAntenatalExaminationInfoMapper pregnantAntenatalExaminationInfoMapper; 
	
	@Autowired
	private ImMsgService imMsgService;

//	@Autowired
//	private ChatUserService chatUserService;
	
	@Autowired
	private ImAccountsService imAccountsService;
	
	/**
	 * 启动执行该调度
	 * @version 1.0
	 * @createTime 2016-11-1,下午4:27:23
	 * @updateTime 2016-11-1,下午4:27:23
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 */
	@Scheduled(cron="0 0 10 * * ?")//每天早上10点执行任务调度
//	@Scheduled(cron="0 0/1 * * * ?")//每一分钟执行一次
	public void runPrenatalExaminationRemindJob() {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			logger.info("********* "+format.format(new Date())+" prenatalExaminationRemind run() *********");
			List<Map<String, Object>> ids = new ArrayList<Map<String, Object>>();
			//查询所有预产期不为空的怀孕用户
			List<UserIdExpertDateBo> userInfoList = userService.getUEs();
			if(userInfoList != null && userInfoList.size() > 0){
				for(int i = 1; i< 14; i++){
					Map<String, Object> map = new HashMap<String, Object>();
					List<Integer> idList = new ArrayList<Integer>();
					for (UserIdExpertDateBo userIdExpertDateBo : userInfoList) {
						int[] week = DateUtil.getPregnantWeek(TimeUtils.convertToDate(userIdExpertDateBo.getExpertDate()), TimeUtils.getCurrentTime());
						PregnantAntenatalExaminationInfo pregnantAntenatalExaminationInfo = pregnantAntenatalExaminationInfoMapper.findPrenatalRemind(week[0]);
						if(pregnantAntenatalExaminationInfo != null && week[1] == 1){//处于提醒周的第一天就推送
							if(pregnantAntenatalExaminationInfo.getExaminationNumbers() == i){//存到第i次产检对应的用户列表中
								idList.add(userIdExpertDateBo.getUserId());
							}
						}
					}
					map.put("users", idList);//用户id
					map.put("examinationNumbers", i);//产检次数
					ids.add(map);
				}
			}
			
			int examinationNumbers = 0;
			//满足条件的推送并且插入数据到我的消息表中
			if(ids != null && ids.size() > 0){
				for (Map<String, Object> maps : ids) {
					//第n次产检的用户组id
					List<Integer> idList = (List<Integer>) maps.get("users");
					//产检次数
					examinationNumbers = (int) maps.get("examinationNumbers");
					//推送内容
					String content = "亲爱的妈妈，建议您在本周去医院做第"+examinationNumbers+"次产检。本次产检的小提示，请点击查看详情。";
					String data = "";
					logger.info("pregnantexamination im sendSystemMsg content:"+content+"examinationNumbers:"+examinationNumbers+" ;ids:"+idList);
//					CommonReturnMsg imResult = imMsgService.selDoctorIMMsg("yf_"+11485, 1, 5);
//					System.out.println(imResult);
					for (Integer id : idList) {
						String chatId = "";
//						if(id == 10048){
							//推送消息
//							CommonReturnMsg ret = imMsgService.sendSystemMsg(ConfigProUtils.get("bus_code"), "ys_888", content);//yf_+id
							/*CommonReturnMsg msg = chatUserService.getChatIdByUserId(String.valueOf(id),2);*/
							CommonReturnMsg msg = imAccountsService.sel(String.valueOf(id), "yh_"+String.valueOf(id));
							if(msg.getMsg() > 0){				// 若根据UserId查询到相对应的chatID
								if(msg.getData() != null){
//									HashMap<String, Object> paramMap = (HashMap<String, Object>) msg.getData();
//									ChatUserBo chatUserBo = (ChatUserBo) msg.getData();
									ImAccountsBo imAccountsBo = (ImAccountsBo) msg.getData();
									chatId = imAccountsBo.getChatId();
								}
							} else{
								// 根据UserId查询用户信息
								UserInfo userInfo = userService.getAllUserInfoByUserId(id);
								
								UserBaseInfo userBaseInfo = new UserBaseInfo();
								userBaseInfo.setUserId(String.valueOf(id));
								userBaseInfo.setUserName((userInfo.getNickName()==""||userInfo.getNickName()==null)?userInfo.getId().toString():userInfo.getNickName());
								userBaseInfo.setUserType(1);		// 1： 天使医生用户	2：建海用户		3：弘扬用户
								String faceUrl = "";
								if(userInfo.getUserImg()!=null && !"".equals(userInfo.getUserImg())){
									if(userInfo.getUserImg().contains("http")){
										userBaseInfo.setUserAvatarImg(userInfo.getUserImg());
										faceUrl = userInfo.getUserImg();
									}else{
										userBaseInfo.setUserAvatarImg(ConfigProUtils.get("file_path")+userInfo.getUserImg());
										faceUrl = ConfigProUtils.get("file_path")+userInfo.getUserImg();
									}
								}else{
									userBaseInfo.setUserAvatarImg(ConfigProUtils.get("file_path")+"/group1/M00/04/43/wKgCQ1hjn_eAFNT_AAATdi5bu8Y639.png");
									faceUrl = ConfigProUtils.get("file_path")+"/group1/M00/04/43/wKgCQ1hjn_eAFNT_AAATdi5bu8Y639.png";
								}
								userBaseInfo.setAccountsType(2);//患者用户
								/*CommonReturnMsg rsp = chatUserService.addChatByUserInfo(userBaseInfo);*///添加用户到im系统获取chatID
								String nick = (userInfo.getNickName()==""||userInfo.getNickName()==null)?userInfo.getId().toString():userInfo.getNickName();
								CommonReturnMsg rsp = imAccountsService.add("101", "yh_"+String.valueOf(id), nick, faceUrl);
								if(rsp.getMsg() > 0){
									if(rsp.getData() != null){
//										HashMap<String, Object> paramMap = JsonUtils.parseJSONMap(rsp.getData().toString());
//										HashMap<String, Object> paramMap = (HashMap<String, Object>) rsp.getData();
										ImAccountsBo bo = (ImAccountsBo) rsp.getData();
										chatId = bo.getChatId();
									}
								}
							}
							if (!StringUtils.isEmpty(chatId)){//产检提醒推送type传0，im将业务代码传给前端进行识别，以兼容iOS离线推送的两套逻辑type冲突
 								CommonReturnMsg ret = imMsgService.sendSystemMsg(ConfigProUtils.get("bus_code"), chatId, examinationNumbers+"", "产检提醒", content, data, 0, 2L);
								/*if(ret.getMsg() == Const.SUCCESS){ 
									logger.info("pregnantexamination sendSystemMsg success!");
								}*/
							}

//						}
						
					}
				}
			}
			//清空list
			ids.clear();
		} catch(Exception ex) {
			logger.error("pregnantexamination sendSystemMsg fail!"+ex.getMessage());
		}
	}
	
}
