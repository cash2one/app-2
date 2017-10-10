package com.jumper.angel.app.usercenter.controller;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.jumper.anglesound.service.HealthBaseService;

import com.jumper.angel.app.usercenter.entity.AppVersion;
import com.jumper.angel.app.usercenter.service.AppVersionService;
import com.jumper.angel.app.usercenter.service.UserHealthNumberService;
import com.jumper.angel.app.usercenter.vo.VOUser;
import com.jumper.angel.app.usercenter.vo.VOUserInfo;
import com.jumper.angel.frame.util.ConfigProUtils;
import com.jumper.angel.frame.util.Const;
import com.jumper.angel.frame.util.IntervalUtils;
import com.jumper.angel.frame.util.ResultMsg;
import com.jumper.angel.frame.util.TimeUtils;
import com.jumper.common.service.im.ImAccountsService;
import com.jumper.common.service.im.ImMsgService;
import com.jumper.dubbo.bean.po.UserAuth;
import com.jumper.dubbo.bean.po.UserExtraInfo;
import com.jumper.dubbo.bean.po.UserInfo;
import com.jumper.dubbo.service.HospitalService;
import com.jumper.dubbo.service.UserService;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * 用户中心控制器
 * @author gyx
 * @time 2016年12月5日
 */
@Controller
@RequestMapping("usercenter")
public class UserCenterController {
	
	private final static Logger logger = Logger.getLogger(UserCenterController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private HospitalService hospitalService;
	
	@Autowired
	private HealthBaseService healthBaseService;
	
	@Autowired
	private AppVersionService appVersionService;
	
	/*@Autowired
	private IdWorker idWorker;*/
	
	@Autowired
	private ImMsgService imMsgService;
	
	@Autowired
	private UserHealthNumberService userHealthNumberService;
	
	@Autowired
	private ImAccountsService imAccountsService;
	
	/**
	 * 获取用户基本信息
	 * @param user_id
	 * @return
	 */
	@RequestMapping(value = "findUserInfo/{user_id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取用户基本信息", httpMethod = "GET", notes = "获取用户基本信息")
	public ResultMsg findUserInfo(@ApiParam(name = "user_id", value = "用户id", required = true)@PathVariable("user_id")long user_id){
		try {
			if(StringUtils.isEmpty(user_id+"")){
				return new ResultMsg(Const.FAILED, "用户id不能为空！");
			}
			UserInfo userInfo = userService.getAllUserInfoByUserId((int)user_id);
			if(userInfo == null){
				return new ResultMsg(Const.FAILED, "用户信息不存在！");
			}
			if(userInfo.getStatus() == null || userInfo.getStatus() == 0){
				return new ResultMsg(Const.FAILED, "该用户已被禁用！");
			}
			List<VOUserInfo> voList = new ArrayList<VOUserInfo>();
			VOUserInfo voUserInfo = userHealthNumberService.getVOUserInfo(userInfo,"v4.0");
			voList.add(voUserInfo);
			return new ResultMsg(Const.SUCCESS, "获取用户基本信息成功！", voList);
		} catch (Exception e) {
			logger.error("findUserInfo():"+e.getMessage());
			return new ResultMsg(Const.FAILED, "获取用户基本信息失败！");
		}
	}
	
	/**
	 * 获取用户基本信息
	 * @param user_id
	 * @return
	 */
	@RequestMapping(value = "v4.1.5/findUserInfo/{user_id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "v4.1.5获取用户基本信息", httpMethod = "GET", notes = "获取用户基本信息")
	public ResultMsg findUserInfo415(@ApiParam(name = "user_id", value = "用户id", required = true)@PathVariable("user_id")long user_id){
		try {
			if(StringUtils.isEmpty(user_id+"")){
				return new ResultMsg(Const.FAILED, "用户id不能为空！");
			}
			UserInfo userInfo = userService.getAllUserInfoByUserId((int)user_id);
			if(userInfo == null){
				return new ResultMsg(Const.FAILED, "用户信息不存在！");
			}
			if(userInfo.getStatus() == null || userInfo.getStatus() == 0){
				return new ResultMsg(Const.FAILED, "该用户已被禁用！");
			}
			List<VOUserInfo> voList = new ArrayList<VOUserInfo>();
			VOUserInfo voUserInfo = userHealthNumberService.getVOUserInfo(userInfo,"v4.1.5");
			voList.add(voUserInfo);
			return new ResultMsg(Const.SUCCESS, "获取用户基本信息成功！", voList);
		} catch (Exception e) {
			logger.error("findUserInfo():"+e.getMessage());
			return new ResultMsg(Const.FAILED, "获取用户基本信息失败！");
		}
	}
	
	/**
	 * 义乌妇幼用户信息接口
	 * @version v.1.0
	 * @createTime 2017年7月20日,上午10:08:39
	 * @updateTime 2017年7月20日,上午10:08:39
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param userId 用户ID
	 * @param hospitalId 医院ID
	 * @return
	 */
	@RequestMapping(value = "v1.01/findUserInfo/{userId}/{hospitalId}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "v1.01获取用户基本信息(义乌)", httpMethod = "GET", notes = "v1.01获取用户基本信息(义乌)")
	public ResultMsg findUserInfo101(@ApiParam(name = "userId", value = "用户id", required = true)@PathVariable("userId") String userId,
			@ApiParam(name = "hospitalId", value = "医院id", required = true)@PathVariable("hospitalId") String hospitalId){
		try {
			if(StringUtils.isEmpty(userId)){
				return new ResultMsg(Const.FAILED, "用户id不能为空");
			}
			if(StringUtils.isEmpty(hospitalId)){
				return new ResultMsg(Const.FAILED, "医院id不能为空");
			}
			//用户信息
			UserInfo userInfo = userService.getAllUserInfoByUserId(Integer.parseInt(userId));
			if(userInfo == null){
				return new ResultMsg(Const.FAILED, "用户信息不存在");
			}
			if(userInfo.getStatus() == null || userInfo.getStatus() == 0){
				return new ResultMsg(Const.FAILED, "该用户已被禁用");
			}
			//用户拓展信息
			UserExtraInfo extraInfo = userService.getExtraUserInfoByUserId(userInfo.getId());
			//判断绑定医院是否是同一家医院
			if(Integer.parseInt(hospitalId) != extraInfo.getCommonHospital().intValue()) {
				extraInfo.setCommonHospital(Integer.parseInt(hospitalId));
				//更新绑定医院
				userService.updUserExtraInfo(extraInfo);
			}
			List<VOUserInfo> voList = new ArrayList<VOUserInfo>();
			//组装用户信息数据
			VOUserInfo voUserInfo = userHealthNumberService.getVOUserInfo(userInfo,"v4.1.5");
			voList.add(voUserInfo);
			return new ResultMsg(Const.SUCCESS, "获取用户基本信息成功", voList);
		} catch (Exception e) {
			logger.error("findUserInfo():"+e.getMessage());
			return new ResultMsg(Const.FAILED, "获取用户基本信息失败");
		}
	}
	
	/**
	 * 修改用户头像
	 * @param user_id
	 * @param request
	 * @return
	 */
	/*@RequestMapping(value = "updateUserImage/{user_id}/{file}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "修改用户头像", httpMethod = "GET", notes = "修改用户头像")
	public ResultMsg updateUserImage(
			@ApiParam(name = "user_id", value = "用户id", required = true)@PathVariable("user_id")long user_id, 
			@ApiParam(name = "file", value = "文件", required = true)@PathVariable("file") MultipartFile file){
		try {
			if(StringUtils.isEmpty(user_id+"")){
				return new ResultMsg(Const.FAILED, "用户id不能为空！");
			}
			UserInfo userInfo = userService.getAllUserInfoByUserId((int)user_id);
			if(userInfo == null){
				return new ResultMsg(Const.FAILED, "获取用户基本信息失败！");
			}
			
			
			//调dubbo上传文件
			ReturnMsg returnMsg = fileService.singleFileUpload(file);
			
			logger.info("=======File=========");
			if(returnMsg.getMsg()>0){//上传成功
				if (returnMsg.getData() != null){
					logger.info("==============returnMsg.getData()=================");
					logger.info(returnMsg.getData());
					Map<String, Object> paramMap = JsonUtils.readObject2Map(returnMsg.getData().toString());
					//文件名
					String fileUrl = paramMap.get("fileUrl")!=null?paramMap.get("fileUrl").toString():"";
					//域名
					String ip = paramMap.get("ip")!=null?paramMap.get("ip").toString():"";
					logger.info("update user image ==========> ip:"+ip+",fileUrl:"+fileUrl);
					//更新数据库用户头像信息
					userInfo.setUserImg(fileUrl);
					int flag = userService.updUserInfo(userInfo);
					if(flag>0){
						//返回用户信息
						logger.info("update user image success!");
						List<VOUserInfo> volist = new ArrayList<VOUserInfo>();
						volist.add(getVOUserInfo(userInfo));
						return new ResultMsg(Const.SUCCESS, "修改用户头像成功！", volist);
					}
				}
			}else{
				logger.error("updateUserImage fail!");
				return new ResultMsg(Const.FAILED, "用户头像上传失败！", new ArrayList<Object>());
			}
		} catch (Exception e) {
			logger.error("updateUserImage():"+e.getMessage());
			return new ResultMsg(Const.FAILED, "修改用户头像信息失败！");
		}
		return new ResultMsg(Const.FAILED, "修改用户头像信息失败！");
	}*/
	/**
	 * 修改用户头像信息（移动端直接调上传服务）
	 * @param user_id
	 * @param userImg
	 * @return
	 */
	@RequestMapping(value = "updateUserImage", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "修改用户头像", httpMethod = "GET", notes = "修改用户头像")
	public ResultMsg updateUserImage(
			@ApiParam(name = "user_id", value = "用户id", required = true)@RequestParam("user_id")long user_id, 
			@ApiParam(name = "userImg", value = "头像名称", required = true)@RequestParam("userImg") String userImg){
		try {
			
			/*CommonReturnMsg msg = imMsgService.selDoctorIMMsg("ys_137", 1, 20);
			System.out.println(msg);*/
			if(StringUtils.isEmpty(user_id+"")){
				return new ResultMsg(Const.FAILED, "用户id不能为空！");
			}
			if(StringUtils.isEmpty(userImg)){
				return new ResultMsg(Const.FAILED, "用户头像信息不能为空！");
			}
			UserInfo userInfo = userService.getAllUserInfoByUserId((int)user_id);
			if(userInfo == null){
				return new ResultMsg(Const.FAILED, "用户不存在！");
			}
			String userImgs = URLDecoder.decode(userImg, "UTF-8");
			userInfo.setUserImg(userImgs);
			int flag = userService.updUserInfo(userInfo);
			if(flag>0){
				imAccountsService.edit1("101", "yh_"+userInfo.getId(), userInfo.getNickName(), userInfo.getUserImg());
				//返回用户信息
				logger.info("update user image success!");
				List<VOUserInfo> volist = new ArrayList<VOUserInfo>();
				volist.add(userHealthNumberService.getVOUserInfo(userInfo,"v4.0"));
				return new ResultMsg(Const.SUCCESS, "修改用户头像成功！", volist);
			}else{
				return new ResultMsg(Const.FAILED, "修改用户头像失败！");
			}
		} catch (Exception e) {
			logger.error("updateUserImage():"+e.getMessage());
			return new ResultMsg(Const.FAILED, "修改用户头像信息失败！");
		}
	}
	
	/**
	 * 修改用户基本信息
	 * @param voUserInfo
	 * @return
	 */
	@RequestMapping(value = "updateUserInfo", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "修改用户基本信息", httpMethod = "POST", notes = "修改用户基本信息")
	public ResultMsg updateUserInfo(@ApiParam(name = "param", value = "用户基本信息json数据", required = true)@RequestBody VOUser voUserInfo,
			HttpServletRequest request){
		try {
			UserInfo userInfo = new UserInfo();
			UserExtraInfo extraInfo = new UserExtraInfo();
			//用户id
			if(StringUtils.isEmpty(voUserInfo.getUser_id()+"")){
				return new ResultMsg(Const.FAILED, "用户id不能为空！");
			}
			userInfo = userService.getAllUserInfoByUserId(voUserInfo.getUser_id());
			extraInfo = userService.getExtraUserInfoByUserId(voUserInfo.getUser_id());
			//用户手机号码不允许修改（后期处理）
			
			//真实姓名
			if(StringUtils.isNotEmpty(voUserInfo.getReal_name())){
				extraInfo.setRealName(voUserInfo.getReal_name());
//				return new ResultMsg(Const.FAILED, "用户真实姓名不能为空！");
			}
			
			//用户生日
			if(StringUtils.isNotEmpty(voUserInfo.getBirthday())){
				String birthday = voUserInfo.getBirthday();
				if(StringUtils.isNotBlank(birthday)){
					SimpleDateFormat sdfs = new SimpleDateFormat(Const.YYYYMMDD);
					extraInfo.setBirthday(sdfs.parse(birthday));
				}else{
					extraInfo.setBirthday(null);
				}
//				return new ResultMsg(Const.FAILED, "用户生日不能为空！");
			}

			//用户年龄
			if(StringUtils.isNotEmpty(voUserInfo.getAge()+"") && StringUtils.isNumeric(voUserInfo.getAge()+"")){
				extraInfo.setAge(voUserInfo.getAge());
//				return new ResultMsg(Const.FAILED, "用户年龄不能为空！");
			}
			
			//用户昵称
			if(StringUtils.isNotEmpty(voUserInfo.getNick_name())){
				userInfo.setNickName(voUserInfo.getNick_name());
//				return new ResultMsg(Const.FAILED, "用户昵称不能为空！");
			}
			
			//身份证号码
			if(StringUtils.isNotEmpty(voUserInfo.getId_card())){
				extraInfo.setIdentification(voUserInfo.getId_card());
//				return new ResultMsg(Const.FAILED, "身份证号码不能为空！");
			}
			
			//所在省份
			if(StringUtils.isNotEmpty(voUserInfo.getProvince()+"") && voUserInfo.getProvince()> 0){
				userInfo.setProvince(voUserInfo.getProvince());
//				return new ResultMsg(Const.FAILED, "用户所在省份不能为空！");
			}
			
			//所在城市
			if(StringUtils.isNotEmpty(voUserInfo.getCity()+"") && voUserInfo.getCity()> 0){
				userInfo.setCity(voUserInfo.getCity());
//				return new ResultMsg(Const.FAILED, "用户所在城市不能为空！");
			}
			
			//当前身份
			if(StringUtils.isEmpty(voUserInfo.getIdentity()+"") || voUserInfo.getIdentity()<0 || voUserInfo.getIdentity()>2){
				return new ResultMsg(Const.FAILED, "用户当前身份不能为空！");
			}
			extraInfo.setCurrentIdentity(voUserInfo.getIdentity());
			if(voUserInfo.getIdentity() == 0){//怀孕中
				//预产期和末次月经必须填写其中一项
				if(StringUtils.isEmpty(voUserInfo.getPre_date()) && StringUtils.isEmpty(voUserInfo.getLast_period())){
					return new ResultMsg(Const.FAILED, "请输入预产期或末次月经！");
				}else{
					//预产期
					if(StringUtils.isNotEmpty(voUserInfo.getPre_date())){
						logger.info("=================ExpectedDateOfConfinement==================");
						logger.info("before:"+voUserInfo.getPre_date());
						String pre_date = voUserInfo.getPre_date();
						logger.info("caculate:"+TimeUtils.convertToDate(pre_date));
						userInfo.setExpectedDateOfConfinement(TimeUtils.convertToDate(pre_date));
						logger.info("after:"+userInfo.getExpectedDateOfConfinement());
						Date lastPeriod = IntervalUtils.getLastPeriodDay(pre_date);
						extraInfo.setLastPeriod(lastPeriod);
					}
					//末次月经
					if(StringUtils.isNotEmpty(voUserInfo.getLast_period())){
						String lastPeriods = voUserInfo.getLast_period();
						Date pregancyDate = IntervalUtils.getPregancyDay(lastPeriods);
						userInfo.setExpectedDateOfConfinement(pregancyDate);
						extraInfo.setLastPeriod(TimeUtils.convertToDate(lastPeriods)); //更改末次月经，修改对应的预产期
					}
				}
			}else if(voUserInfo.getIdentity() == 1){//辣妈
				//宝宝生日
				if(StringUtils.isEmpty(voUserInfo.getBaby_birthday())){
					return new ResultMsg(Const.FAILED, "宝宝生日不能为空！");
				}
				String babyBirth = voUserInfo.getBaby_birthday();
				SimpleDateFormat sdf = new SimpleDateFormat(Const.YYYYMMDD);
				extraInfo.setBabyBirthday(sdf.parse(babyBirth));
				//宝宝性别
				if(StringUtils.isEmpty(voUserInfo.getBaby_sex()+"")){
					return new ResultMsg(Const.FAILED, "宝宝性别不能为空！");
				}
				//如果用户没有填写预产期，同步宝宝生日为预产期，否则不同步
				if(userInfo.getExpectedDateOfConfinement() == null){
					//已有宝宝时，预产期设置为宝宝生日那一天
					userInfo.setExpectedDateOfConfinement(TimeUtils.convertToDate(voUserInfo.getBaby_birthday()));
					//根据预产期同步末次月经
					Date lastPeriod = IntervalUtils.getLastPeriodDay(voUserInfo.getBaby_birthday());
					extraInfo.setLastPeriod(lastPeriod);
				}
				
				extraInfo.setBabySex(voUserInfo.getBaby_sex());
			}
			
			//用户身高
			if(StringUtils.isNotEmpty(voUserInfo.getHeight())){
				extraInfo.setHeight(voUserInfo.getHeight().split("\\.")[0]);
//				return new ResultMsg(Const.FAILED, "用户身高不能为空！");
			}
			
			//用户体重
			if(StringUtils.isNotEmpty(voUserInfo.getWeight()+"")){
				extraInfo.setWeight(voUserInfo.getWeight());
//				return new ResultMsg(Const.FAILED, "用户体重不能为空！");
			}
			//编辑chatId
//			imAccountsService.edit(voUserInfo.getUser_id()+"", voUserInfo.getId_card());
			imAccountsService.edit1("101", "yh_"+voUserInfo.getUser_id(), voUserInfo.getNick_name(), userInfo.getUserImg());
			//更新用户表和扩展表
			userService.updUserInfoAndExtraInfo(userInfo, extraInfo);
			//保健号必须是英文或数字
			//添加或修改用户保健号(非必填)
			if(StringUtils.isNotEmpty(voUserInfo.getHealthNum())){
				boolean b = userHealthNumberService.addOrUpdateUserHealthNum(userInfo.getId(),extraInfo.getCommonHospital(),voUserInfo.getHealthNum());
				if(b){
					logger.info("addOrUpdateUserHealthNum success!");
				}
			}
			
			//更新完成用户数据以后，更改用户当前身份所对应的孕阶段及孕周
			userHealthNumberService.doChangeUserState(userInfo, extraInfo);
			
			//调用health接口
			if (voUserInfo.getIdentity() == 1) {
				  Integer babySex = null;
				  String babyBirth = voUserInfo.getBaby_birthday(); 
				  if(StringUtils.isNotBlank(voUserInfo.getBaby_sex()+"") && StringUtils.isNumeric(voUserInfo.getBaby_sex()+"")){
					  babySex = Integer.valueOf(voUserInfo.getBaby_sex());
				  }
				  int healthBase = healthBaseService.setUserChildbirthEnd(voUserInfo.getUser_id(), babyBirth, babySex);
				  if(healthBase>0){
					  logger.info("invoke healthBaseService setUserChildbirthEnd success! ");
				  }else{
					  logger.info("invoke healthBaseService setUserChildbirthEnd fail! ");
				  }
			}
			//修改成功以后返回用户基本信息
			List<VOUserInfo> voList = new ArrayList<VOUserInfo>();
			VOUserInfo voUserInfo2 = userHealthNumberService.getVOUserInfo(userInfo,"v4.0");
			voList.add(voUserInfo2);
			return new ResultMsg(Const.SUCCESS, "修改用户基本信息成功！", voList);
		} catch (Exception e) {
			logger.error("updateUserInfo():"+e.getMessage());
			e.printStackTrace();
			return new ResultMsg(Const.FAILED, "修改用户基本信息失败！");
		}
	}
	
	/**
	 * 修改用户基本信息
	 * @param voUserInfo
	 * @return
	 */
	@RequestMapping(value = "v4.1.5/updateUserInfo", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "v4.1.5修改用户基本信息", httpMethod = "POST", notes = "修改用户基本信息")
	public ResultMsg updateUserInfo415(@ApiParam(name = "param", value = "用户基本信息json数据", required = true)@RequestBody VOUser voUserInfo,
			HttpServletRequest request){
		try {
			UserInfo userInfo = new UserInfo();
			UserExtraInfo extraInfo = new UserExtraInfo();
			//用户id
			if(StringUtils.isEmpty(voUserInfo.getUser_id()+"")){
				return new ResultMsg(Const.FAILED, "用户id不能为空！");
			}
			userInfo = userService.getAllUserInfoByUserId(voUserInfo.getUser_id());
			extraInfo = userService.getExtraUserInfoByUserId(voUserInfo.getUser_id());
			//用户手机号码不允许修改（后期处理）
			
			//真实姓名
			if(StringUtils.isNotEmpty(voUserInfo.getReal_name())){
				extraInfo.setRealName(voUserInfo.getReal_name());
//				return new ResultMsg(Const.FAILED, "用户真实姓名不能为空！");
			}
			
			//用户生日
			if(StringUtils.isNotEmpty(voUserInfo.getBirthday())){
				String birthday = voUserInfo.getBirthday();
				if(StringUtils.isNotBlank(birthday)){
					SimpleDateFormat sdfs = new SimpleDateFormat(Const.YYYYMMDD);
					extraInfo.setBirthday(sdfs.parse(birthday));
				}else{
					extraInfo.setBirthday(null);
				}
//				return new ResultMsg(Const.FAILED, "用户生日不能为空！");
			}

			//用户年龄
			if(StringUtils.isNotEmpty(voUserInfo.getAge()+"") && StringUtils.isNumeric(voUserInfo.getAge()+"")){
				extraInfo.setAge(voUserInfo.getAge());
//				return new ResultMsg(Const.FAILED, "用户年龄不能为空！");
			}
			
			//用户昵称
			if(StringUtils.isNotEmpty(voUserInfo.getNick_name())){
				userInfo.setNickName(voUserInfo.getNick_name());
//				return new ResultMsg(Const.FAILED, "用户昵称不能为空！");
			}
			
			//身份证号码
			if(StringUtils.isNotEmpty(voUserInfo.getId_card())){
				extraInfo.setIdentification(voUserInfo.getId_card());
//				return new ResultMsg(Const.FAILED, "身份证号码不能为空！");
			}
			
			//所在省份
			if(StringUtils.isNotEmpty(voUserInfo.getProvince()+"") && voUserInfo.getProvince()> 0){
				userInfo.setProvince(voUserInfo.getProvince());
//				return new ResultMsg(Const.FAILED, "用户所在省份不能为空！");
			}
			
			//所在城市
			if(StringUtils.isNotEmpty(voUserInfo.getCity()+"") && voUserInfo.getCity()> 0){
				userInfo.setCity(voUserInfo.getCity());
//				return new ResultMsg(Const.FAILED, "用户所在城市不能为空！");
			}
			
			//当前身份
			if(StringUtils.isEmpty(voUserInfo.getIdentity()+"") || voUserInfo.getIdentity()<0 || voUserInfo.getIdentity()>2){
				return new ResultMsg(Const.FAILED, "用户当前身份不能为空！");
			}
			extraInfo.setCurrentIdentity(voUserInfo.getIdentity());
			if(voUserInfo.getIdentity() == 0){//怀孕中
				//预产期和末次月经必须填写其中一项
				if(StringUtils.isEmpty(voUserInfo.getPre_date()) && StringUtils.isEmpty(voUserInfo.getLast_period())){
					return new ResultMsg(Const.FAILED, "请输入预产期或末次月经！");
				}else{
					//预产期
					if(StringUtils.isNotEmpty(voUserInfo.getPre_date())){
						logger.info("=================ExpectedDateOfConfinement==================");
						logger.info("before:"+voUserInfo.getPre_date());
						String pre_date = voUserInfo.getPre_date();
						logger.info("caculate:"+TimeUtils.convertToDate(pre_date));
						userInfo.setExpectedDateOfConfinement(TimeUtils.convertToDate(pre_date));
						logger.info("after:"+userInfo.getExpectedDateOfConfinement());
						Date lastPeriod = IntervalUtils.getLastPeriodDay(pre_date);
						extraInfo.setLastPeriod(lastPeriod);
					}
					//末次月经
					if(StringUtils.isNotEmpty(voUserInfo.getLast_period())){
						String lastPeriods = voUserInfo.getLast_period();
						Date pregancyDate = IntervalUtils.getPregancyDay(lastPeriods);
						userInfo.setExpectedDateOfConfinement(pregancyDate);
						extraInfo.setLastPeriod(TimeUtils.convertToDate(lastPeriods)); //更改末次月经，修改对应的预产期
					}
				}
			}else if(voUserInfo.getIdentity() == 1){//辣妈
				//宝宝生日
				if(StringUtils.isEmpty(voUserInfo.getBaby_birthday())){
					return new ResultMsg(Const.FAILED, "宝宝生日不能为空！");
				}
				String babyBirth = voUserInfo.getBaby_birthday();
				SimpleDateFormat sdf = new SimpleDateFormat(Const.YYYYMMDD);
				extraInfo.setBabyBirthday(sdf.parse(babyBirth));
				//宝宝性别
				if(StringUtils.isEmpty(voUserInfo.getBaby_sex()+"")){
					return new ResultMsg(Const.FAILED, "宝宝性别不能为空！");
				}
				//如果用户没有填写预产期，同步宝宝生日为预产期，否则不同步
				if(userInfo.getExpectedDateOfConfinement() == null){
					//已有宝宝时，预产期设置为宝宝生日那一天
					userInfo.setExpectedDateOfConfinement(TimeUtils.convertToDate(voUserInfo.getBaby_birthday()));
					//根据预产期同步末次月经
					Date lastPeriod = IntervalUtils.getLastPeriodDay(voUserInfo.getBaby_birthday());
					extraInfo.setLastPeriod(lastPeriod);
				}
				
				extraInfo.setBabySex(voUserInfo.getBaby_sex());
			}
			
			//用户身高
			if(StringUtils.isNotEmpty(voUserInfo.getHeight())){
				extraInfo.setHeight(voUserInfo.getHeight().split("\\.")[0]);
//				return new ResultMsg(Const.FAILED, "用户身高不能为空！");
			}
			
			//用户体重
			if(StringUtils.isNotEmpty(voUserInfo.getWeight()+"")){
				extraInfo.setWeight(voUserInfo.getWeight());
//				return new ResultMsg(Const.FAILED, "用户体重不能为空！");
			}
			//编辑chatId
//			imAccountsService.edit(voUserInfo.getUser_id()+"", voUserInfo.getId_card());
			imAccountsService.edit1("101", "yh_"+voUserInfo.getUser_id(), voUserInfo.getNick_name(), userInfo.getUserImg());
			//更新用户表和扩展表
			userService.updUserInfoAndExtraInfo(userInfo, extraInfo);
			//保健号必须是英文或数字
			//添加或修改用户保健号(非必填)
			if(StringUtils.isNotEmpty(voUserInfo.getHealthNum())){
				boolean b = userHealthNumberService.addOrUpdateUserHealthNum(userInfo.getId(),extraInfo.getCommonHospital(),voUserInfo.getHealthNum());
				if(b){
					logger.info("addOrUpdateUserHealthNum success!");
				}
			}
			
			//更新完成用户数据以后，更改用户当前身份所对应的孕阶段及孕周
			userHealthNumberService.doChangeUserState(userInfo, extraInfo);
			
			//调用health接口
			if (voUserInfo.getIdentity() == 1) {
				  Integer babySex = null;
				  String babyBirth = voUserInfo.getBaby_birthday(); 
				  if(StringUtils.isNotBlank(voUserInfo.getBaby_sex()+"") && StringUtils.isNumeric(voUserInfo.getBaby_sex()+"")){
					  babySex = Integer.valueOf(voUserInfo.getBaby_sex());
				  }
				  int healthBase = healthBaseService.setUserChildbirthEnd(voUserInfo.getUser_id(), babyBirth, babySex);
				  if(healthBase>0){
					  logger.info("invoke healthBaseService setUserChildbirthEnd success! ");
				  }else{
					  logger.info("invoke healthBaseService setUserChildbirthEnd fail! ");
				  }
			}
			//修改成功以后返回用户基本信息
			List<VOUserInfo> voList = new ArrayList<VOUserInfo>();
			VOUserInfo voUserInfo2 = userHealthNumberService.getVOUserInfo(userInfo,"v4.1.5");
			voList.add(voUserInfo2);
			return new ResultMsg(Const.SUCCESS, "修改用户基本信息成功！", voList);
		} catch (Exception e) {
			logger.error("updateUserInfo():"+e.getMessage());
			e.printStackTrace();
			return new ResultMsg(Const.FAILED, "修改用户基本信息失败！");
		}
	}
	
	/*@RequestMapping(value = "completeUserInfo", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "完善用户基本信息", httpMethod = "POST", notes = "完善用户基本信息")
	public ResultMsg completeUserInfo(@ApiParam(name = "param", value = "用户基本信息json数据", required = true)@RequestBody VOUser voUserInfo,
			HttpServletRequest request){
		try {
			UserInfo userInfo = new UserInfo();
			UserExtraInfo extraInfo = new UserExtraInfo();
			//用户id
			if(StringUtils.isEmpty(voUserInfo.getUser_id()+"")){
				return new ResultMsg(Const.FAILED, "用户id不能为空！");
			}
			userInfo = userService.getAllUserInfoByUserId(voUserInfo.getUser_id());
			extraInfo = userService.getExtraUserInfoByUserId(voUserInfo.getUser_id());
			//用户手机号码不允许修改（后期处理）
			
			//真实姓名
			if(StringUtils.isEmpty(voUserInfo.getReal_name())){
				return new ResultMsg(Const.FAILED, "用户真实姓名不能为空！");
			}
			extraInfo.setRealName(voUserInfo.getReal_name());
			
			//用户生日
			if(StringUtils.isEmpty(voUserInfo.getBirthday())){
				return new ResultMsg(Const.FAILED, "用户生日不能为空！");
			}
			String birthday = voUserInfo.getBirthday();
			if(StringUtils.isNotBlank(birthday)){
				SimpleDateFormat sdfs = new SimpleDateFormat(Const.YYYYMMDD);
				extraInfo.setBirthday(sdfs.parse(birthday));
			}else{
				extraInfo.setBirthday(null);
			}

			//用户年龄
			if(StringUtils.isEmpty(voUserInfo.getAge()+"") || !StringUtils.isNumeric(voUserInfo.getAge()+"")){
				return new ResultMsg(Const.FAILED, "用户年龄不能为空！");
			}
			extraInfo.setAge(voUserInfo.getAge());
			
			//用户昵称
			if(StringUtils.isEmpty(voUserInfo.getNick_name())){
				return new ResultMsg(Const.FAILED, "用户昵称不能为空！");
			}
			userInfo.setNickName(voUserInfo.getNick_name());
			
			//身份证号码
			if(StringUtils.isEmpty(voUserInfo.getId_card())){
				return new ResultMsg(Const.FAILED, "身份证号码不能为空！");
			}
			extraInfo.setIdentification(voUserInfo.getId_card());
			
			//所在省份
			if(StringUtils.isEmpty(voUserInfo.getProvince()+"") || voUserInfo.getProvince()<=0){
				return new ResultMsg(Const.FAILED, "用户所在省份不能为空！");
			}
			userInfo.setProvince(voUserInfo.getProvince());
			
			//所在城市
			if(StringUtils.isEmpty(voUserInfo.getCity()+"") || voUserInfo.getCity()<=0){
				return new ResultMsg(Const.FAILED, "用户所在城市不能为空！");
			}
			userInfo.setCity(voUserInfo.getCity());
			
			//当前身份
			if(StringUtils.isEmpty(voUserInfo.getIdentity()+"") || voUserInfo.getIdentity()<0 || voUserInfo.getIdentity()>1){
				return new ResultMsg(Const.FAILED, "用户当前身份不能为空！");
			}
			extraInfo.setCurrentIdentity(voUserInfo.getIdentity());
			if(voUserInfo.getIdentity() == 0){//怀孕中
				//预产期和末次月经必须填写其中一项
				if(StringUtils.isEmpty(voUserInfo.getPre_date()) && StringUtils.isEmpty(voUserInfo.getLast_period())){
					return new ResultMsg(Const.FAILED, "请输入预产期或末次月经！");
				}else{
					//预产期
					if(StringUtils.isNotEmpty(voUserInfo.getPre_date())){
						String pre_date = voUserInfo.getPre_date();
						userInfo.setExpectedDateOfConfinement(TimeUtils.convertToDate(pre_date));
						Date lastPeriod = IntervalUtils.getLastPeriodDay(pre_date);
						extraInfo.setLastPeriod(lastPeriod);
					}
					//末次月经
					if(StringUtils.isNotEmpty(voUserInfo.getLast_period())){
						String lastPeriods = voUserInfo.getLast_period();
						Date pregancyDate = IntervalUtils.getPregancyDay(lastPeriods);
						userInfo.setExpectedDateOfConfinement(pregancyDate);
						extraInfo.setLastPeriod(TimeUtils.convertToDate(lastPeriods)); //更改末次月经，修改对应的预产期
					}
				}
			}else if(voUserInfo.getIdentity() == 1){//辣妈
				//宝宝生日
				if(StringUtils.isEmpty(voUserInfo.getBaby_birthday())){
					return new ResultMsg(Const.FAILED, "宝宝生日不能为空！");
				}
				String babyBirth = voUserInfo.getBaby_birthday();
				SimpleDateFormat sdf = new SimpleDateFormat(Const.YYYYMMDD);
				extraInfo.setBabyBirthday(sdf.parse(babyBirth));
				//宝宝性别
				if(StringUtils.isEmpty(voUserInfo.getBaby_sex()+"")){
					return new ResultMsg(Const.FAILED, "宝宝性别不能为空！");
				}
				extraInfo.setBabySex(voUserInfo.getBaby_sex());
			}
			
			//用户身高
			if(StringUtils.isEmpty(voUserInfo.getHeight())){
				return new ResultMsg(Const.FAILED, "用户身高不能为空！");
			}
			extraInfo.setHeight(voUserInfo.getHeight().split("\\.")[0]);
			
			//用户体重
			if(StringUtils.isEmpty(voUserInfo.getWeight()+"")){
				return new ResultMsg(Const.FAILED, "用户体重不能为空！");
			}
			extraInfo.setWeight(voUserInfo.getWeight());
			
			//更新用户表和扩展表
			userService.updUserInfoAndExtraInfo(userInfo, extraInfo);
			//保健号必须是英文或数字
			//添加或修改用户保健号(非必填)
			if(StringUtils.isNotEmpty(voUserInfo.getHealthNum())){
				boolean b = userHealthNumberService.addOrUpdateUserHealthNum(userInfo.getId(),extraInfo.getCommonHospital(),voUserInfo.getHealthNum());
				if(b){
					logger.info("addOrUpdateUserHealthNum success!");
				}
			}
			
			//更新完成用户数据以后，更改用户当前身份所对应的孕阶段及孕周
			doChangeUserState(userInfo, extraInfo);
			
			//调用health接口
			if (voUserInfo.getIdentity() == 1) {
				  Integer babySex = null;
				  String babyBirth = voUserInfo.getBaby_birthday(); 
				  if(StringUtils.isNotBlank(voUserInfo.getBaby_sex()+"") && StringUtils.isNumeric(voUserInfo.getBaby_sex()+"")){
					  babySex = Integer.valueOf(voUserInfo.getBaby_sex());
				  }
				  int healthBase = healthBaseService.setUserChildbirthEnd(voUserInfo.getUser_id(), babyBirth, babySex);
				  if(healthBase>0){
					  logger.info("invoke healthBaseService setUserChildbirthEnd success! ");
				  }else{
					  logger.info("invoke healthBaseService setUserChildbirthEnd fail! ");
				  }
			}
			//修改成功以后返回用户基本信息
			List<VOUserInfo> voList = new ArrayList<VOUserInfo>();
			VOUserInfo voUserInfo2 = getVOUserInfo(userInfo);
			voList.add(voUserInfo2);
			return new ResultMsg(Const.SUCCESS, "修改用户基本信息成功！", voList);
		} catch (Exception e) {
			logger.error("updateUserInfo():"+e.getMessage());
			return new ResultMsg(Const.FAILED, "修改用户基本信息失败！");
		}
	}*/
	
	/**
	 * 用户授权信息
	 * @version 1.0
	 * @createTime 2016-12-8,下午5:23:30
	 * @updateTime 2016-12-8,下午5:23:30
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @return
	 */
	@RequestMapping(value="findUserAuth/{userId}", method=RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="用户授权信息", httpMethod="GET", notes="用户授权信息")
	public ResultMsg findUserAuth(@ApiParam(name="userId", value="用户ID", required = true) @PathVariable("userId") int userId) {
		try {
			//查询数据库得到用户医疗信息查看授权
			UserAuth auth = userService.getUserAuthByUserId(userId);
			if(null == auth) {
				auth = new UserAuth();
				//1开启状态,2关闭状态
				int allowNum=1;
				auth.setAuthUser(userId);//用户id
				auth.setAllowView(allowNum);//查看所有医院 1是，２否
				auth.setAllowOur(allowNum);//允许医生查看本医院数据  1是，２否
				auth.setAllowOut(allowNum);//允许医生查看非本院数据,其它医院  1是，２否
				auth.setAddTime(new Date());//添加时间
				auth.setUpdateTime(new Date());//修改时间
				userService.saveUserAuth(auth);
			}
			return new ResultMsg(Const.SUCCESS, "获取用户授权成功！", auth);
		} catch (Exception e) {
			logger.error("findUserAuth()", e);
			return new ResultMsg(Const.FAILED, "获取用户授权失败！");
		}
	}
	
	/**
	 * 修改用户授权
	 * @version 1.0
	 * @createTime 2016-12-9,上午11:19:57
	 * @updateTime 2016-12-9,上午11:19:57
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param param 修改用户授权参数 json
	 * @return
	 */
	@RequestMapping(value="updateAuth", method=RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value="修改用户授权", httpMethod="POST", notes="修改用户授权")
	public ResultMsg updateAuth(@ApiParam(name="param", value="修改用户授权参数", required = true) @RequestBody UserAuth param,
			HttpServletRequest request) {
		try {
			UserAuth auth = userService.getUserAuthByUserId(param.getAuthUser());
			//设置查看所有医院 1是，２否
			if(param.getAllowView() != null) {
				auth.setAllowView(param.getAllowView());
			}
			//允许医生查看本医院数据  1是，２否
			if(param.getAllowOur() != null) {
				auth.setAllowOur(param.getAllowOur());
			}
			//允许医生查看非本院数据,其它医院  1是，２否
			if(param.getAllowOut() != null) {
				auth.setAllowOut(param.getAllowOut());
			}
			//记录修改时间
			auth.setUpdateTime(new Date());
			//修改
			userService.updUserAuth(auth);
			return new ResultMsg(Const.SUCCESS, "修改用户授权成功！", auth);
		} catch (Exception e) {
			logger.error("updateAuth()", e);
			return new ResultMsg(Const.FAILED, "修改用户授权失败！");
		}
	}
	
	/**
	 * 获取版本号
	 * @version 1.0
	 * @createTime 2016-12-9,下午6:17:30
	 * @updateTime 2016-12-9,下午6:17:30
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @return
	 */
	@RequestMapping(value="appVersion/{appType}", method=RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="获取版本号", httpMethod="GET", notes="获取版本号")
	public ResultMsg appVersion(@ApiParam(name="appType", value="手机类型(0:android 1:ios)", required = true) @PathVariable("appType") int appType) {
		try {
			//版本号信息
			AppVersion app = appVersionService.findAppVersion(appType);
			if(!StringUtils.isEmpty(app.getDownloadUrl())) {
				//是否包含http
				if(!app.getDownloadUrl().contains("http")) {
					app.setDownloadUrl(ConfigProUtils.get("file_path")+app.getDownloadUrl());
				}
			}
			return new ResultMsg(Const.SUCCESS, "获取版本号成功！", app);
		} catch (Exception e) {
			logger.error("appVersion():"+e.getMessage());
			return new ResultMsg(Const.FAILED, "获取版本号失败！");
		}
	}
	
	/**
	 * 监测修改用户身高体重信息
	 * @param user_id 用户id
	 * @param height 身高
	 * @param weight 体重
	 * @param age 年龄
	 * @return 用户所有信息
	 */
	@RequestMapping(value = "updateUserExtraInfo/{user_id}/{height}/{weight}/{age}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "修改用户身高体重信息", httpMethod = "GET", notes = "修改用户身高体重")
	public ResultMsg updateUserExtraInfo(
			@ApiParam(name = "user_id", value = "用户id", required = true)@PathVariable("user_id")int user_id, 
			@ApiParam(name = "height", value = "用户身高", required = true)@PathVariable("height") String height,
			@ApiParam(name = "weight", value = "用户体重", required = true)@PathVariable("weight") double weight,
			@ApiParam(name = "age", value = "用户年龄", required = true)@PathVariable("age") int age){
		try {
			
			if(StringUtils.isEmpty(user_id+"")){
				return new ResultMsg(Const.FAILED, "用户id不能为空！");
			}
			UserExtraInfo extraInfo = userService.getExtraUserInfoByUserId(user_id);
			if(extraInfo == null){
				return new ResultMsg(Const.FAILED, "用户不存在！");
			}
			extraInfo.setHeight(height.split("\\.")[0]);
			extraInfo.setWeight(weight);
			extraInfo.setAge(age);
			int flag = userService.updUserExtraInfo(extraInfo);
			if(flag > 0){
				List<VOUserInfo> voList = new ArrayList<VOUserInfo>();
				UserInfo userInfo = userService.getAllUserInfoByUserId(user_id);
				VOUserInfo voUserInfo = userHealthNumberService.getVOUserInfo(userInfo,"v4.0");
				voList.add(voUserInfo);
				logger.info("update userExtraInfo success!");
				return new ResultMsg(Const.SUCCESS, "修改用户身高体重信息成功！", voList);
			}else{
				logger.info("update userExtraInfo failed!");
				return new ResultMsg(Const.FAILED, "修改用户身高体重信息失败！");
			}
		} catch (Exception e) {
			logger.error("updateUserExtraInfo():"+e.getMessage());
			return new ResultMsg(Const.FAILED, "修改用户身高体重信息失败！");
		}
	}
	
	
	
}
