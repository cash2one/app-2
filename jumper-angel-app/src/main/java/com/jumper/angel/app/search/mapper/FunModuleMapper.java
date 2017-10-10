package com.jumper.angel.app.search.mapper;

import java.util.List;
import java.util.Map;

import com.jumper.angel.app.search.model.po.FunModulePo;

/**
 * 功能模块 mapper
 * @author gyx
 * @time 2016年12月13日
 */
public interface FunModuleMapper {

	/**
	 * 通过关键字查询模块
	 * @param keywrods 关键字
	 * @return
	 */
	public List<FunModulePo> searchFunModule(Map<String, Object> map);
	
}
