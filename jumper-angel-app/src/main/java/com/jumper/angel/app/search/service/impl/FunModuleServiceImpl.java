package com.jumper.angel.app.search.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumper.angel.app.search.mapper.FunModuleMapper;
import com.jumper.angel.app.search.mapper.HospitalModuleHostMapper;
import com.jumper.angel.app.search.model.po.FunModulePo;
import com.jumper.angel.app.search.model.po.HospitalModuleHost;
import com.jumper.angel.app.search.service.FunModuleService;

/**
 * 功能模块 ServiceImpl
 * @author gyx
 * @time 2016年12月13日
 */
@Service
public class FunModuleServiceImpl implements FunModuleService{
	
	@Autowired
	private FunModuleMapper funModuleMapper;
	
	@Autowired
	private HospitalModuleHostMapper hospitalModuleHostMapper;

	/**
	 * 通过关键字查询app功能模块
	 */
	@Override
	public List<FunModulePo> searchFunModule(String keywords) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("keywords", keywords);
		List<FunModulePo> funModuleList = funModuleMapper.searchFunModule(map);
		if(funModuleList != null && funModuleList.size() > 0){
			return funModuleList;
		}
		return null;
	}

	/**
	 * 查询医院定制化模块是否开通
	 */
	@Override
	public HospitalModuleHost findHospitalModuleHost(int hospitalId,
			int moduleNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("hospitalId", hospitalId);
		map.put("moduleNum", moduleNum);
		HospitalModuleHost moduleHost = hospitalModuleHostMapper.findHospitalModuleHost(map);
		if(moduleHost != null){
			return moduleHost;
		}
		return null;
	}
	
}
