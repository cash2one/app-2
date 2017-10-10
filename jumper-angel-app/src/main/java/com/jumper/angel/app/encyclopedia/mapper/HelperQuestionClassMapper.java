package com.jumper.angel.app.encyclopedia.mapper;

import java.util.List;

import com.jumper.angel.app.encyclopedia.entity.HelperQuestionClass;
/**
 * 孕期百科类型Mapper
 * @author gyx
 * @time 2016年12月1日
 */
public interface HelperQuestionClassMapper {

	/**
	 * 获取孕期百科常见问题列表
	 * @return
	 */
	List<HelperQuestionClass> getAllQuestionClass();
	
}
