package com.jumper.angel.app.home.service;

import java.util.List;

import com.jumper.angel.app.home.entity.PregnantAntenatalExaminationInfo;


/**
 * 孕期产检信息Service
 * @author gyx
 * @time 2016年12月1日
 */
public interface PregnantAntenatalExaminationInfoService {

	/**
	 * 根据孕周判断是否显示产检提醒入口
	 * @param pregnantWeek
	 * @return
	 */
	PregnantAntenatalExaminationInfo findPrenatalRemind(int pregnantWeek);

	/**
	 * 根据孕周查询产检提醒信息
	 * @param pregnantWeek 孕周
	 * @return
	 */
	List<PregnantAntenatalExaminationInfo> findPrenatalRemindByWeek(
			int pregnantWeek);

	/**
	 * 查询第一次产检信息
	 * @param i
	 * @return
	 */
	PregnantAntenatalExaminationInfo findPreRemindByNumbers(int number);
	
}
