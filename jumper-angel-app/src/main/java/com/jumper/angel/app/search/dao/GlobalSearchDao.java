package com.jumper.angel.app.search.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.jumper.angel.app.search.model.bo.HelperQuestionsBo;

/**
 * @Description 全局搜索DAO接口定义
 * @author 徐运贤
 * @date 2016-12-7
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public interface GlobalSearchDao {

	/**
	 * 查找孕期百科问题
	 */
	Page<HelperQuestionsBo> findHelperQuestionsList(String title) throws Exception;
}
