package com.jumper.angel.app.home.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumper.angel.app.home.entity.PregnantAntenatalExaminationInfo;
import com.jumper.angel.app.home.mapper.PregnantAntenatalExaminationInfoMapper;
import com.jumper.angel.app.home.service.PregnantAntenatalExaminationInfoService;
/**
 * 孕期产检信息ServiceImpl
 * @author home-admin
 *
 */
@Service
@Transactional
public class PregnantAntenatalExaminationInfoServiceImpl implements
		PregnantAntenatalExaminationInfoService {
	@Autowired
	private PregnantAntenatalExaminationInfoMapper pregnantAntenatalExaminationInfoMapper;
	
	/**
	 * 根据孕周获取孕期产检信息
	 */
	@Override
	public PregnantAntenatalExaminationInfo findPrenatalRemind(int pregnantWeek) {
		PregnantAntenatalExaminationInfo pregnantAntenatalExaminationInfo = pregnantAntenatalExaminationInfoMapper.findPrenatalRemind(pregnantWeek);
		if(pregnantAntenatalExaminationInfo!=null){
			return pregnantAntenatalExaminationInfo;
		}
		return null;
	}

	/**
	 * 根据孕周查询产检提醒信息
	 */
	@Override
	public List<PregnantAntenatalExaminationInfo> findPrenatalRemindByWeek(
			int pregnantWeek) {
		List<PregnantAntenatalExaminationInfo> pregList = pregnantAntenatalExaminationInfoMapper.findPrenatalRemindByWeek(pregnantWeek);
		if(pregList != null && pregList.size() > 0){
			return pregList;
		}
		return null;
	}

	/**
	 * 根据产检次数查询产检信息
	 */
	@Override
	public PregnantAntenatalExaminationInfo findPreRemindByNumbers(int number) {
		PregnantAntenatalExaminationInfo examinationInfo = pregnantAntenatalExaminationInfoMapper.findPreRemindByNumbers(number);
		if(examinationInfo != null){
			return examinationInfo;
		}
		return null;
	}
	
}
