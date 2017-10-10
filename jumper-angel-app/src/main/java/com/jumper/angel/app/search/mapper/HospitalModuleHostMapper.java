package com.jumper.angel.app.search.mapper;

import java.util.Map;

import com.jumper.angel.app.search.model.po.HospitalModuleHost;

/**
 * 医院模块定制化实体
 * @author gyx
 * @time 2017年3月1日
 */
public interface HospitalModuleHostMapper {

	/**
	 * 查询医院定制化模块
	 * @param map
	 * @return
	 */
	HospitalModuleHost findHospitalModuleHost(Map<String, Object> map);

}
