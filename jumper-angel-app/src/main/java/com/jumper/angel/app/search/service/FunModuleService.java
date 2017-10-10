package com.jumper.angel.app.search.service;

import java.util.List;

import com.jumper.angel.app.search.model.po.FunModulePo;
import com.jumper.angel.app.search.model.po.HospitalModuleHost;

/**
 * 功能模块 Service
 * @author gyx
 * @time 2016年12月13日
 */
public interface FunModuleService {

	/**
	 * 根据关键字查询模块
	 * @param keywrods 关键字
	 * @param hospitalId 医院id
	 * @return
	 */
	List<FunModulePo> searchFunModule(String keywords);

	/**
	 * 查询定制化模块是否开通
	 * @param hospitalId 医院id
	 * @param moduleNum 模块编号
	 * @return
	 */
	HospitalModuleHost findHospitalModuleHost(int hospitalId, int moduleNum);
	
}
