package com.jumper.angel.app.usercenter.mapper;

import java.util.Map;

import com.jumper.angel.app.usercenter.entity.UserHealthNumber;

/**
 * 用户保健号Mapper
 * @author gyx
 * @time 2016年12月7日
 */
public interface UserHealthNumberMapper {

	/**
	 * 查找用户保健号
	 * @param map
	 * @return
	 */
	UserHealthNumber findUserHealthNumber(Map<String, Object> map);

	/**
	 * 修改用户保健号
	 * @param healthNumber
	 */
	void updateUserHealthNumber(UserHealthNumber healthNumber);

	/**
	 * 添加用户保健号
	 * @param map
	 */
	void addUserHealthNumber(UserHealthNumber healthNumber);

}
