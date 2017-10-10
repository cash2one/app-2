package com.jumper.angel.app.encyclopedia.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumper.angel.app.encyclopedia.entity.HelperQuestionClass;
import com.jumper.angel.app.encyclopedia.entity.HelperQuestionType;
import com.jumper.angel.app.encyclopedia.mapper.HelperQuestionClassMapper;
import com.jumper.angel.app.encyclopedia.mapper.HelperQuestionTypeMapper;
import com.jumper.angel.app.encyclopedia.service.HelperQuestionClassService;
import com.jumper.angel.app.encyclopedia.vo.VOHelperQuestionClass;
import com.jumper.angel.app.encyclopedia.vo.VOHelperQuestionType;

/**
 * 孕期百科类型ServiceImpl
 * @author gyx
 * @time 2016年12月1日
 */
@Service
@Transactional
public class HelperQuestionClassServiceImpl implements
		HelperQuestionClassService {
	@Autowired
	private HelperQuestionClassMapper helperQuestionClassMapper;
	
	@Autowired
	private HelperQuestionTypeMapper helperQuestionTypeMapper;

	/**
	 * 获取孕期百科常见问题类型列表
	 */
	@Override
	public List<HelperQuestionClass> getAllQuestionClass() {
		List<HelperQuestionClass> helperQuestionClassList = helperQuestionClassMapper.getAllQuestionClass();
		if(helperQuestionClassList != null && helperQuestionClassList.size() > 0){
			return helperQuestionClassList;
		}
		return null;
	}

	/**
	 * 组装孕期百科类型列表
	 */
	@Override
	public List<VOHelperQuestionClass> getVOHelperQuestionClass(
			List<HelperQuestionClass> list) {
		List<VOHelperQuestionClass> voList = new ArrayList<VOHelperQuestionClass>();
		for (HelperQuestionClass helperQuestionClass : list) {
			VOHelperQuestionClass voHelperQuestionClass = getVOHelperQuestionClass(helperQuestionClass);
			voList.add(voHelperQuestionClass);
		}
		return voList;
	}
	
	
	/**
	 * 封装孕期百科类型实体辅助方法
	 * @param helperQuestionClass
	 * @return
	 */
	public VOHelperQuestionClass getVOHelperQuestionClass(HelperQuestionClass helperQuestionClass){
		VOHelperQuestionClass voHelperQuestionClass = new VOHelperQuestionClass();
		//设置id
		voHelperQuestionClass.setId(helperQuestionClass.getId());
		//设置name
		if(helperQuestionClass.getName()!=null){
			voHelperQuestionClass.setName(helperQuestionClass.getName());
		}else{
			voHelperQuestionClass.setName("");
		}
		List<VOHelperQuestionType> voList = new ArrayList<VOHelperQuestionType>();
		//通过父问题id获取子类型列表
		List<HelperQuestionType> types = helperQuestionTypeMapper.findQuestionTypeByQuestionClassId(helperQuestionClass.getId());
		if(types != null && types.size() != 0){
			for(HelperQuestionType type : types){
				VOHelperQuestionType voType = new VOHelperQuestionType();
				voType.setId(type.getId());
				if(type.getName() != null){
					voType.setName(type.getName());
				}else{
					voType.setName("");
				}
				voList.add(voType);
			}
		}
		//设置子类型列表
		voHelperQuestionClass.setTypeList(voList);
		return voHelperQuestionClass;
	}
	
	
}
