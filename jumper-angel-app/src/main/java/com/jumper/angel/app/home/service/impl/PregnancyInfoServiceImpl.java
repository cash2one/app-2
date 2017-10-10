package com.jumper.angel.app.home.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jumper.angel.app.home.entity.PregnancyInfo;
import com.jumper.angel.app.home.mapper.PregnancyInfoMapper;
import com.jumper.angel.app.home.service.PregnancyInfoService;

/**
 * 孕期信息ServiceImpl
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-11-30
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
@Service
@Transactional
public class PregnancyInfoServiceImpl implements PregnancyInfoService {
	
	@Autowired
	private PregnancyInfoMapper pregnancyInfoMapper;
	
	/**
	 * 通过用户ID和孕周查询孕期信息
	 * @version 1.0
	 * @createTime 2016-11-30,上午10:34:40
	 * @updateTime 2016-11-30,上午10:34:40
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param week 孕周
	 * @return
	 */
	public PregnancyInfo findPrepnancyInfoByUserIdAndWeek(int week) {
		//孕期信息
		return pregnancyInfoMapper.findPrepnancyInfoByUserIdAndWeek(week);
	}
}
