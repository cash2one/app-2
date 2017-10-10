package com.jumper.angel.app.home.mapper;

import java.util.List;

import com.jumper.angel.app.home.entity.PregnantAntenatalExaminationInfo;

/**
 * 孕期产检信息mapper
 * @author gyx
 * @time 2016年12月1日
 */
public interface PregnantAntenatalExaminationInfoMapper {

	/**
	 * 通过孕周获取孕期产检信息
	 * @param pregnantWeek
	 * @return
	 */
	public PregnantAntenatalExaminationInfo findPrenatalRemind(int pregnantWeek);

	/**
	 * 根据孕周查询产检提醒信息
	 * @param pregnantWeek 孕周
	 * @return
	 */
	public List<PregnantAntenatalExaminationInfo> findPrenatalRemindByWeek(
			int pregnantWeek);

	/**
	 * 根据产检次数查询产检信息
	 * @param number
	 * @return
	 */
	public PregnantAntenatalExaminationInfo findPreRemindByNumbers(int number);
	
}
