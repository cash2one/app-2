package com.jumper.angel.app.healthymanage.service;

import java.util.List;

import com.jumper.dubbo.bean.bo.PregnantHealthySettingBo;

/**
 * 用户健康管理工具Service
 * @author gyx
 * @time 2016年12月9日
 */
public interface PregnantHealthySettingService {

	/**
	 * 获取用户健康管理工具
	 * @param user_id
	 * @return
	 */
	List<PregnantHealthySettingBo> findUserPregnantHealthySetting(int user_id);

	/**
	 * 添加或减少监测数据
	 * @param user_id
	 * @param project
	 * @param flag
	 * @return
	 */
	boolean updateTool(int user_id, int project, int flag);
	
}
