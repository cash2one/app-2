package com.jumper.angel.app.encyclopedia.service;

import java.util.List;
import java.util.Map;

import com.jumper.angel.app.encyclopedia.vo.VOHelperQuestionInfo;

/**
 * 孕期百科问题Service
 * @author gyx
 * @time 2016年12月1日
 */
public interface HelperQuestionsService {

	/**
	 * 根据类型id获取孕期百科问题分页数据
	 * @param map
	 * @return
	 */
	List<VOHelperQuestionInfo> findQuestionList(Map<String, Object> map);
	
}
