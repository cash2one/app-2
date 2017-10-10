package com.jumper.angel.app.search.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.jumper.angel.app.music.mapper.PregnantMusicInfoMapper;
import com.jumper.angel.app.search.dao.GlobalSearchDao;
import com.jumper.angel.app.search.model.bo.HelperQuestionsBo;
import com.jumper.angel.app.search.service.GlobalSearchService;
import com.jumper.core.exception.ServiceException;

/**
 * @Description 全局搜索服务接口接口实现
 * @author 徐运贤
 * @date 2016-12-7
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
@Service
public class GlobalSearchServiceImpl implements GlobalSearchService {

	@Autowired
	private GlobalSearchDao globalSearchDao;
	@Override
	public Page<HelperQuestionsBo> findHelperQuestionsList(String title) throws ServiceException{
		// TODO Auto-generated method stub
		try {
			return globalSearchDao.findHelperQuestionsList(title);
		} catch (Exception e) {
			// TODO: handle exception
			throw new ServiceException(e.getMessage());
		}
		
	}

}
