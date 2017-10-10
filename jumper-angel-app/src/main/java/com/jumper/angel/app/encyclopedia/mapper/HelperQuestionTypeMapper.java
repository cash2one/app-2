package com.jumper.angel.app.encyclopedia.mapper;

import java.util.List;

import com.jumper.angel.app.encyclopedia.entity.HelperQuestionType;
/**
 * 孕期百科类型Mapper
 * @author gyx
 * @time 2016年12月1日
 */
public interface HelperQuestionTypeMapper {

	/**
	 * 通过父类型id获取子类型列表
	 * @param id
	 * @return
	 */
	List<HelperQuestionType> findQuestionTypeByQuestionClassId(Integer id);

	
	
}
