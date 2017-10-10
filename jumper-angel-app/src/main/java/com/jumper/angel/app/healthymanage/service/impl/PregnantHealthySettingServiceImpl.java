package com.jumper.angel.app.healthymanage.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumper.angel.app.healthymanage.entity.PregnantHealthySetting;
import com.jumper.angel.app.healthymanage.mapper.PregnantHealthySettingMapper;
import com.jumper.angel.app.healthymanage.service.PregnantHealthySettingService;
import com.jumper.angel.frame.common.MonitorProject;
import com.jumper.angel.frame.util.TimeUtils;
import com.jumper.dubbo.bean.bo.PregnantHealthySettingBo;

/**
 * 用户健康管理工具ServiceImpl
 * @author gyx
 * @time 2016年12月9日
 */
@Service
public class PregnantHealthySettingServiceImpl implements PregnantHealthySettingService{
	private final static Logger logger = Logger.getLogger(PregnantHealthySettingServiceImpl.class);
	
	@Autowired
	private PregnantHealthySettingMapper pregnantHealthySettingMapper;
	/**
	 * 获取用户健康管理工具
	 */
	@Override
	public List<PregnantHealthySettingBo> findUserPregnantHealthySetting(
			int user_id) {
		//获取用户添加的健康管理项目
		List<PregnantHealthySetting> settingList = pregnantHealthySettingMapper.findUserPregnantHealthySetting(user_id);
		if(settingList != null && settingList.size()>0){
			List<PregnantHealthySettingBo> voSettingList = getVOPregnantHealthySetting(settingList);
			return voSettingList;
		}
		return null;
	}
	
	/**
	 * 组装用户管理工具
	 * @param settingList
	 * @return
	 */
	private List<PregnantHealthySettingBo> getVOPregnantHealthySetting(
			List<PregnantHealthySetting> settingList) {
		List<PregnantHealthySettingBo> voSettingList = new ArrayList<PregnantHealthySettingBo>();
		if(settingList != null && settingList.size()>0){
			for (PregnantHealthySetting pregnantHealthySetting : settingList) {
				PregnantHealthySettingBo voSettings = new PregnantHealthySettingBo();
				if(pregnantHealthySetting.getProject() != null){
					voSettings.setProject(pregnantHealthySetting.getProject());
					voSettings.setProjectName(MonitorProject.getName(pregnantHealthySetting.getProject()-1));
					voSettingList.add(voSettings);
				}
				
			}
		}
		return voSettingList;
	}
	/**
	 * 添加或减少监测数据
	 */
	@Override
	public boolean updateTool(int user_id, int project, int flag) {
		boolean b = false;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_id", user_id);
		map.put("project", project);
		map.put("state", flag);
		if(flag == 0){//添加
			//查询用户是否已经添加过改健康工具
			List<PregnantHealthySetting> settings = pregnantHealthySettingMapper.findPregnantHealthySettingByProject(map);
			//已经添加过，就修改状态为启用
			if(settings != null && settings.size()>0){
				try {
					pregnantHealthySettingMapper.updatePregnantHealthySetting(map);
					b = true;
					logger.error("updatePregnantHealthySetting success!user_id="+user_id+",project="+project+",state="+flag);
				} catch (Exception e) {
					b = false;
					logger.error("updatePregnantHealthySetting fail!user_id="+user_id+",project="+project+",state="+flag);
					e.printStackTrace();
				}
			}else{
				//没有添加过，就添加一条记录
				map.put("add_time", TimeUtils.getCurrentTime());
				try {
					pregnantHealthySettingMapper.addPregnantHealthySetting(map);
					b = true;
					logger.error("addPregnantHealthySetting success!user_id="+user_id+",project="+project+",state="+flag);
				} catch (Exception e) {
					b = false;
					logger.error("addPregnantHealthySetting fail!user_id="+user_id+",project="+project+",state="+flag);
					e.printStackTrace();
				}
			}
		}else if(flag == 1){//禁用（减少）
			//直接修改状态为禁用
			try {
				pregnantHealthySettingMapper.updatePregnantHealthySetting(map);
				b = true;
				logger.error("updatePregnantHealthySetting success!user_id="+user_id+",project="+project+",state="+flag);
			} catch (Exception e) {
				b = false;
				logger.error("updatePregnantHealthySetting fail!user_id="+user_id+",project="+project+",state="+flag);
				e.printStackTrace();
			}
		}
		return b;
	}
	
}
