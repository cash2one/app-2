package com.jumper.angel.app.bindhospital.service;

import com.jumper.angel.app.bindhospital.entity.BindHospitalLog;

/**
 * 绑定医院流水service
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-12-13
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public interface BindHospitalLogService {

	/**
	 * 新增绑定医院流水记录
	 * @version 1.0
	 * @createTime 2016-12-13,上午11:54:51
	 * @updateTime 2016-12-13,上午11:54:51
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param log
	 * @param commonHospital 绑定医院ID
	 * @param targetHospital 绑定目标医院ID
	 */
	public void addBindHospitalLog(BindHospitalLog log, long commonHospital, long targetHospital);
}
