package com.jumper.angel.app.encyclopedia.service;

import java.util.List;

import com.jumper.angel.app.encyclopedia.entity.HelperQuestionClass;
import com.jumper.angel.app.encyclopedia.vo.VOHelperQuestionClass;

/**
 * 常见问题类型Service
 * @author gyx
 * @time 2016年12月1日
 */
public interface HelperQuestionClassService {

	/**
	 * 获取孕期百科类型列表
	 * @return
	 */
	List<HelperQuestionClass> getAllQuestionClass();

	/**
	 * 组装孕期百科类型
	 * @param list
	 * @return
	 */
	List<VOHelperQuestionClass> getVOHelperQuestionClass(
			List<HelperQuestionClass> list);

}
