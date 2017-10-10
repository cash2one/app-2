package com.jumper.angel.app.home.service;

import com.jumper.angel.app.home.entity.PregnancyInfo;

/**
 * 孕期信息Service
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-11-30
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public interface PregnancyInfoService {
	
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
	public PregnancyInfo findPrepnancyInfoByUserIdAndWeek(int week);
}
