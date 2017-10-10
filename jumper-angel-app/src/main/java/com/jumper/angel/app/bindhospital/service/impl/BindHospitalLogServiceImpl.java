package com.jumper.angel.app.bindhospital.service.impl;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jumper.angel.app.bindhospital.entity.BindHospitalLog;
import com.jumper.angel.app.bindhospital.mapper.BindHospitalLogMapper;
import com.jumper.angel.app.bindhospital.service.BindHospitalLogService;
import com.jumper.core.service.impl.IdWorkerImpl;
/**
 * 绑定医院流水serviceImpl
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-12-13
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
@Service
public class BindHospitalLogServiceImpl implements BindHospitalLogService {

	@Autowired
	private BindHospitalLogMapper bindHospitalLogMapper;
	
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
	public void addBindHospitalLog(BindHospitalLog log, long commonHospital, long targetHospital) {
		//非首次绑定
		if(log.getFirstBinding() == 0) {
			log.setId(new IdWorkerImpl(2).nextId());
			//解绑
			log.setOprationStatus(1);
			//解绑的医院
			log.setHospitalId(commonHospital);
			//解绑时间
			log.setBindingDate(new Date());
			//添加
			bindHospitalLogMapper.addBindHospitalLog(log);
		}
		log.setId(new IdWorkerImpl(2).nextId());
		//绑定
		log.setOprationStatus(0);
		//绑定的医院
		log.setHospitalId(targetHospital);
		//绑定时间
		log.setBindingDate(new Date());
		bindHospitalLogMapper.addBindHospitalLog(log);
	}
}
