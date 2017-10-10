package com.jumper.angel.app.search.mapper;

import java.util.List;
import java.util.Map;

import com.jumper.angel.app.search.model.po.Topic;

/**
 * 话题 mapper
 * @author gyx
 * @time 2016年12月16日
 */
public interface TopicMapper {

	/**
	 * 条件查询获取话题数量
	 * @param keyword
	 * @return
	 */
	Integer findTopicCount(Map<String, Object> map);

	/**
	 * 条件搜索话题列表
	 * @param map
	 * @return
	 */
	List<Topic> searchTopicList(Map<String, Object> map);
	
   
}