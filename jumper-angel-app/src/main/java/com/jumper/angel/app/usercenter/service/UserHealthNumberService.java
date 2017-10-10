package com.jumper.angel.app.usercenter.service;

import com.jumper.angel.app.usercenter.entity.UserHealthNumber;
import com.jumper.angel.app.usercenter.vo.VOUserInfo;
import com.jumper.dubbo.bean.po.UserExtraInfo;
import com.jumper.dubbo.bean.po.UserInfo;

/**
 * 用户保健号service
 * @author gyx
 * @time 2016年12月7日
 */
public interface UserHealthNumberService {

	/**
	 * 添加或修改用户保健号
	 * @param id
	 * @param commonHospital
	 * @param healthNum
	 * @return
	 */
	boolean addOrUpdateUserHealthNum(Integer user_id, Integer commonHospitalId,
			String healthNum);
	
	/**
	 * 获取用户保健号
	 * @param user_id
	 * @param commonHospitalId
	 * @return
	 */
	UserHealthNumber findUserHealthNumber(Integer user_id, Integer commonHospitalId);

	VOUserInfo getVOUserInfo(UserInfo userInfo,String version);

	void doChangeUserState(UserInfo userInfo, UserExtraInfo extraInfo);
	
}
