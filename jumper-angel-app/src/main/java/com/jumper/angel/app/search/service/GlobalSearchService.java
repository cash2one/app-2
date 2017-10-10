package com.jumper.angel.app.search.service;

import org.springframework.data.domain.Page;
import com.jumper.angel.app.search.model.bo.HelperQuestionsBo;
import com.jumper.core.exception.ServiceException;

/**
 * @Description 全局搜索服务接口接口定义
 * @author 徐运贤
 * @date 2016-12-7
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public interface GlobalSearchService {
	/**
	 * 查找孕期百科问题
	 */
	Page<HelperQuestionsBo> findHelperQuestionsList(String title) throws ServiceException;
}
