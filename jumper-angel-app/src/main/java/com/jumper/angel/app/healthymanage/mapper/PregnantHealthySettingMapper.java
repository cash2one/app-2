package com.jumper.angel.app.healthymanage.mapper;

import java.util.List;
import java.util.Map;

import com.jumper.angel.app.healthymanage.entity.PregnantHealthySetting;

/**
 * 用户健康管理工具mapper
 * @author gyx
 * @time 2016年12月9日
 */
public interface PregnantHealthySettingMapper {

	/**
	 * 获取用户健康管理工具
	 * @param user_id
	 * @return
	 */
	List<PregnantHealthySetting> findUserPregnantHealthySetting(int user_id);

	/**
	 * 查询用户是否已经添加过该健康工具
	 * @param map
	 * @return
	 */
	List<PregnantHealthySetting> findPregnantHealthySettingByProject(
			Map<String, Object> map);

	/**
	 * 修改用户管理工具状态
	 * @param map
	 */
	void updatePregnantHealthySetting(Map<String, Object> map);

	/**
	 * 添加用户健康工具
	 * @param map
	 */
	void addPregnantHealthySetting(Map<String, Object> map);
	
}
