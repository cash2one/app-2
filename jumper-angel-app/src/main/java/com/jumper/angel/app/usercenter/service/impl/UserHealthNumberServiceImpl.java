package com.jumper.angel.app.usercenter.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.jpush.api.report.UsersResult.User;

import com.jumper.angel.app.usercenter.entity.UserHealthNumber;
import com.jumper.angel.app.usercenter.mapper.UserHealthNumberMapper;
import com.jumper.angel.app.usercenter.service.UserHealthNumberService;
import com.jumper.angel.app.usercenter.vo.VOUserInfo;
import com.jumper.angel.frame.common.PregnantStage;
import com.jumper.angel.frame.util.ConfigProUtils;
import com.jumper.angel.frame.util.Const;
import com.jumper.angel.frame.util.DateUtil;
import com.jumper.angel.frame.util.IntervalUtils;
import com.jumper.angel.frame.util.TimeUtils;
import com.jumper.core.service.impl.IdWorkerImpl;
import com.jumper.dubbo.bean.po.AddrCity;
import com.jumper.dubbo.bean.po.AddrCountry;
import com.jumper.dubbo.bean.po.AddrProvince;
import com.jumper.dubbo.bean.po.HospitalInfo;
import com.jumper.dubbo.bean.po.UserExtraInfo;
import com.jumper.dubbo.bean.po.UserInfo;
import com.jumper.dubbo.service.AuthKeyService;
import com.jumper.dubbo.service.HospitalService;
import com.jumper.dubbo.service.UserService;
/**
 * 用户保健号 ServiceImpl
 * @author gyx
 * @time 2016年12月7日
 */
@Service
public class UserHealthNumberServiceImpl implements UserHealthNumberService{
	private final static Logger logger = Logger.getLogger(UserHealthNumberServiceImpl.class);
	
	@Autowired
	private UserHealthNumberMapper userHealthNumberMapper;
	@Autowired
	private IdWorkerImpl idWorkerImpl;
	@Autowired
	private UserService userService;
	@Autowired
	private HospitalService hospitalService;
	@Autowired
	private AuthKeyService authKeyService;
	
	
	/**
	 * 添加或修改用户保健号
	 */
	@Override
	public boolean addOrUpdateUserHealthNum(Integer user_id, Integer commonHospitalId,
			String healthNum) {
		boolean b = false;
		//查找用户在当前关联医院是否有保健号，有的话就更新，没有就添加
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_id", user_id);
		map.put("hospital_id", commonHospitalId);
		UserHealthNumber healthNumber = userHealthNumberMapper.findUserHealthNumber(map);
		if(healthNumber != null){
			//修改保健号信息
			try {
				healthNumber.setHealthNum(healthNum);
				userHealthNumberMapper.updateUserHealthNumber(healthNumber);
				logger.info("updateUserHealthNumber success! id="+healthNumber.getId()+", userId="+user_id+",hospitalId="+commonHospitalId+",healthNum="+healthNum);
				b = true;
			} catch (Exception e) {
				logger.info("updateUserHealthNumber fail! userId="+user_id+",hospitalId="+commonHospitalId+",healthNum="+healthNum+",the error is:"+e.getMessage());
				b = false;
			}
		}else{
			//添加保健号信息
			UserHealthNumber userHealthNumber = new UserHealthNumber();
//			IdWorkerImpl workerImpl = new IdWorkerImpl(2L);
//			long id = workerImpl.nextId();
			long id = idWorkerImpl.nextId();
			logger.info("IdWorkerImpl create user_health_number nextId is:"+id);
			userHealthNumber.setId(id);
			userHealthNumber.setUserId(user_id);
			userHealthNumber.setHospitalId(commonHospitalId);
			userHealthNumber.setHealthNum(healthNum);
			userHealthNumber.setAddTime(TimeUtils.getCurrentTime());
			try {
				userHealthNumberMapper.addUserHealthNumber(userHealthNumber);
				logger.info("addUserHealthNumber success! id="+id+",userId="+user_id+",hospitalId="+commonHospitalId+",healthNum="+healthNum);
				b = true;
			} catch (Exception e) {
				logger.info("addUserHealthNumber fail! id="+id+",userId="+user_id+",hospitalId="+commonHospitalId+",healthNum="+healthNum);
				b = false;
			}
		}
		return b;
	}
	
	/**
	 * 获取用户保健号
	 */
	@Override
	public UserHealthNumber findUserHealthNumber(Integer user_id,
			Integer commonHospitalId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_id", user_id);
		map.put("hospital_id", commonHospitalId);
		UserHealthNumber healthNumber = userHealthNumberMapper.findUserHealthNumber(map);
		if(healthNumber != null){
			return healthNumber;
		}
		return null;
	}
	
	/**
	 * 组装用户基本信息
	 * @param userInfo
	 * @return
	 */
	@Override
	public VOUserInfo getVOUserInfo(UserInfo userInfo, String version) {
		try {
			if(userInfo == null)
				return null;
			VOUserInfo userObj = new VOUserInfo();
			/*if(userInfo.getGold() != null){
				userObj.setGold(userInfo.getGold());
			}*/
			//城市信息
			if(userInfo.getCity()!=null){
				AddrCity city = new AddrCity();
				city.setId(userInfo.getCity());
				List<AddrCity> cityInfos = userService.getCityInfoByCondition(city);
				if(cityInfos != null && cityInfos.size()>0){
					AddrCity cityInfo = cityInfos.get(0);
					if(cityInfo != null && StringUtils.isNotEmpty(cityInfo.getCityname())){
						userObj.setCity(cityInfo.getId());
						userObj.setCity_name(cityInfo.getCityname());
					}else{
						userObj.setCity_name("");
					}
				}
				
			}
			//省份信息
			if(userInfo.getProvince()!=null){
				AddrProvince province = new AddrProvince();
				province.setId(userInfo.getProvince());
				List<AddrProvince> provinceInfos = userService.getProvinceInfoByCondition(province);
				if(provinceInfos != null && provinceInfos.size()>0){
					AddrProvince provinceInfo = provinceInfos.get(0);
					if(provinceInfo != null && StringUtils.isNotEmpty(provinceInfo.getProname())){
						userObj.setProvince(provinceInfo.getId());
						userObj.setProvice_name(provinceInfo.getProname());
					}else{
						userObj.setProvice_name("");
					}
				}
				
			}
			//国家信息
			if(userInfo.getCountry() != null){
				AddrCountry country = new AddrCountry();
				country.setId(userInfo.getCountry());
				List<AddrCountry> countryInfos = userService.getCountryInfoByCondition(country);
				if(countryInfos != null && countryInfos.size()>0){
					AddrCountry countryInfo = countryInfos.get(0);
					if(countryInfo != null && StringUtils.isNotEmpty(countryInfo.getCountry())){
						userObj.setCountry(countryInfo.getId());
						userObj.setCountry_name(countryInfo.getCountry());
					}else{
						userObj.setCountry_name("");
					}
				}
			}
			
			userObj.setId(userInfo.getId());
			userObj.setMobile(userInfo.getMobile());
			if(userInfo.getNickName()!=null)
				userObj.setNick_name(userInfo.getNickName());
			else
				userObj.setNick_name("");
			//用户状态：0禁用，1激活
			userObj.setStatus(userInfo.getStatus());
			//是否开启推送消息,0:关闭,1:开启
			userObj.setSwitch_push_msg(userInfo.getIsSwitchPushMsg());
			
			//用户头像
			if(userInfo.getUserImg() != null){
				//第三方登录,不需要加头
				if(userInfo.getUserImg().contains("http")){
					userObj.setUser_img(userInfo.getUserImg());
				}else{
					userObj.setUser_img(ConfigProUtils.get("file_path") + userInfo.getUserImg());
				}
			}else{
				userObj.setUser_img("");
			}
			//用户注册时间
//			DateFormat sdf = new SimpleDateFormat(Const.DATE_PATTERN_YYYYMMDD_HHMMSS);
//			userObj.setReg_time(sdf.format(userInfo.getRegTime()));
			
			UserExtraInfo extraInfo = userService.getExtraUserInfoByUserId(userInfo.getId());
			if(extraInfo != null){
				//用户真实名称
				if (extraInfo.getRealName()!=null) {
					userObj.setRealname(extraInfo.getRealName());
				}else {
					userObj.setRealname("");
				}
				//用户身份证信息
				if (StringUtils.isNotEmpty(extraInfo.getIdentification())) {
					String num=extraInfo.getIdentification();
					//String ident=num.substring(0, 6)+"********"+num.substring(num.length()-4,num.length());
					userObj.setIdentification(num);
				}else {
					userObj.setIdentification("");
				}
				//用户年龄
				if (extraInfo.getAge()!=null) {
					userObj.setAge(extraInfo.getAge());
				}else {
					userObj.setAge(0);
				}
				//联系方式
				if(extraInfo.getContactPhone() != null){
					userObj.setContactPhone(extraInfo.getContactPhone());
				}else{
					userObj.setContactPhone("");
				}
				//用户孕前体重
				if(extraInfo.getWeight() != null){
					userObj.setWeight(extraInfo.getWeight());
				}else{
					userObj.setWeight(0);
				}
				//用户身高
				if(StringUtils.isNotEmpty(extraInfo.getHeight())){
					userObj.setHeight(extraInfo.getHeight());
				}else{
					userObj.setHeight("0");
				}
				//关联（常用）医院信息
				if(extraInfo.getCommonHospital() != null && extraInfo.getCommonHospital() != 0){
					HospitalInfo info = hospitalService.getHospitalInfoById(extraInfo.getCommonHospital());
					if(info != null){
						userObj.setHospitalName(info.getName());
						userObj.setHospitalId(extraInfo.getCommonHospital());
					}
				}
				//用户当前状态（怀孕0或者已生宝宝1）
				userObj.setCurrentIdentity(extraInfo.getCurrentIdentity());
				//已有宝宝，宝宝生日和性别
				if(extraInfo.getCurrentIdentity() == 1){//已怀孕
					if(extraInfo.getBabyBirthday() != null){
						userObj.setBaby_birthday(TimeUtils.converStringDate(extraInfo.getBabyBirthday(), Const.YYYYMMDD));
					}
					userObj.setBaby_sex(extraInfo.getBabySex());
				}
				
				if(extraInfo.getCurrentIdentity() == 0 && userInfo.getExpectedDateOfConfinement() != null){/** 怀孕状态 **/
					//计算预产期,280天,40周
					Date expDate = userInfo.getExpectedDateOfConfinement();
					if(expDate!=null){
//						int[] expInfo = IntervalUtils.getPregnantWeek(expDate);
						int[] expInfo = null ;
						if(version.equals("v4.0")){
							expInfo = DateUtil.getPregnantWeek(expDate, new Date());
						}else if(version.equals("v4.1.5")){
							expInfo = DateUtil.getPregnantWeek_415(expDate, new Date());
						}
						userObj.setExpected_week(expInfo[0]);
						userObj.setExpected_day(expInfo[1]);
						userObj.setExpected_days(expInfo[2]);
						userObj.setExpected_confinement(TimeUtils.converStringDate(expDate,Const.YYYYMMDD));
					}else{
						userObj.setExpected_confinement("");
					}
				}else if(extraInfo.getCurrentIdentity() == 1 && extraInfo.getBabyBirthday() != null){/** 已有宝宝 **/
					Date babyBirth = extraInfo.getBabyBirthday();
					int[] baby = IntervalUtils.getBabyAgeAndMonth(babyBirth);
					userObj.setExpected_week(baby[0]);
					userObj.setExpected_day(baby[1]);
					userObj.setExpected_days(0);
					userObj.setExpected_confinement(TimeUtils.converStringDate(userInfo.getExpectedDateOfConfinement(),Const.YYYYMMDD));
				}else if(extraInfo.getCurrentIdentity() == 2){
					userObj.setExpected_week(-1);
					userObj.setExpected_day(-1);
				}
				//用户生日
				if(null != extraInfo.getBirthday()){
					Date birthday = extraInfo.getBirthday();
					userObj.setBirthday(TimeUtils.converStringDate(birthday, Const.YYYYMMDD));
				}
				//用户末次月经
				if(null != extraInfo.getLastPeriod()){
					userObj.setLastPeriod(TimeUtils.converStringDate(extraInfo.getLastPeriod(), Const.YYYYMMDD));
				}
				//个人信息检测
				if (null==extraInfo.getCheckStatus()||extraInfo.getCheckStatus()==0) {
					userObj.setCheckstatus(0);
				}else if (extraInfo.getCheckStatus()==1) {
					userObj.setCheckstatus(extraInfo.getCheckStatus());
					userObj.setBindHospitalName(extraInfo.getBindhospitalname());
				}
				//用户保健号
				if(extraInfo.getCommonHospital() != null && extraInfo.getCommonHospital() != 0){
					UserHealthNumber healthNumber = findUserHealthNumber(userInfo.getId(), extraInfo.getCommonHospital());
					if(healthNumber != null){
						userObj.setHealthCardId(healthNumber.getHealthNum());
					}else{
						userObj.setHealthCardId("");
					}
				}
				//获取用户的accessKey
				userObj.setAccessKey(authKeyService.getKeyByUserId(userInfo.getId()));
			}
			return userObj;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 更改用户当前身份所对应的孕阶段及孕周
	 * @param userInfo
	 * @param eInfo
	 * @throws ParseException
	 */
	@Override
	public void doChangeUserState(UserInfo userInfo, UserExtraInfo eInfo){
		try {
			if(userInfo != null){
				if(eInfo != null){
					int state = eInfo.getCurrentIdentity();
					if(state == 0){//怀孕中
						//怀孕中，取预产期计算怀孕周数
						Date pregnantTime = userInfo.getExpectedDateOfConfinement();
						//此处很多数据预产期为空
						if(pregnantTime != null){
							int[] pregnantDate = DateUtil.getPregnantWeek(pregnantTime, new Date());
							if(pregnantDate != null && pregnantDate.length > 0){
								//计算怀孕多少周 
								int week = pregnantDate[0];
								if(week < 0){
									week = 40 - week;
								}
								int pStage = DateUtil.getPregnantStageByWeek(0, week);
								if(userInfo.getPregnantWeek() != week){
									userInfo.setPregnantWeek(week);
									if(userInfo.getPregnantStage() != pStage){
										userInfo.setPregnantStage(pStage);
									}
									int flag = userService.updUserInfo(userInfo);
									if(flag > 0){
										logger.info("doChangeUserState success where identity=0");
									}else{
										logger.info("doChangeUserState fail where identity=0");
									}
								}
							}
						}
					}else if(state == 1){//辣妈
						//辣妈，取宝宝生日计算宝宝大小
						Date babyBirthday = eInfo.getBabyBirthday();
						//此处宝宝生日很多数据为空
						if(babyBirthday != null){
							int[] babyDate = IntervalUtils.getBabyBirthWeek(babyBirthday);
							if(babyDate != null && babyDate.length > 0){
								int week = babyDate[0];
								int pStage = DateUtil.getPregnantStageByWeek(1, week);
								if(userInfo.getPregnantWeek() != week){
									userInfo.setPregnantWeek(week);
									if(userInfo.getPregnantStage() != pStage){
										userInfo.setPregnantStage(pStage);
									}
									int flag = userService.updUserInfo(userInfo);
									if(flag > 0){
										logger.info("doChangeUserState success where identity=1");
									}else{
										logger.info("doChangeUserState fail where identity=1");
									}
								}
							}
						}
					}
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	
}
