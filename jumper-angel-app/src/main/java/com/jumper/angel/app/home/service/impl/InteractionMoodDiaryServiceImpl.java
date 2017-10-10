package com.jumper.angel.app.home.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumper.angel.app.home.entity.InteractionMoodDiary;
import com.jumper.angel.app.home.mapper.InteractionMoodDiaryMapper;
import com.jumper.angel.app.home.service.InteractionMoodDiaryService;
/**
 * 宝妈日记Service
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-11-30
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
@Service
@Transactional
public class InteractionMoodDiaryServiceImpl implements InteractionMoodDiaryService {
	
	@Autowired
	private InteractionMoodDiaryMapper interactionMoodDiaryMapper;
	
	/**
	 * 新增宝妈日记
	 * @version 1.0
	 * @createTime 2016-11-30,下午4:49:22
	 * @updateTime 2016-11-30,下午4:49:22
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param diary
	 */
	public void save(InteractionMoodDiary diary) {
		interactionMoodDiaryMapper.save(diary);
	}
}
