package com.jumper.angel.app.encyclopedia.mapper;

import java.util.List;
import java.util.Map;

import com.jumper.angel.app.encyclopedia.entity.HelperQuestions;

/**
 * 孕期百科问题Mapper
 * @author gyx
 * @time 2016年12月1日
 */
public interface HelperQuestionsMapper {

	/**
	 * 通过类型获取问题列表分页数据
	 * @param map
	 * @return
	 */
	public List<HelperQuestions> findQuestionList(Map<String, Object> map);
	
}
