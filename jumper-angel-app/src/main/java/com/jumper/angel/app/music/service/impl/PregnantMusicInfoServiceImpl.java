package com.jumper.angel.app.music.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumper.angel.app.music.entity.PregnantMusicInfo;
import com.jumper.angel.app.music.mapper.PregnantMusicInfoMapper;
import com.jumper.angel.app.music.service.PregnantMusicInfoService;
import com.jumper.angel.frame.util.ConfigProUtils;
/**
 * 孕期电台ServiceImpl
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-12-1
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
@Service
public class PregnantMusicInfoServiceImpl implements PregnantMusicInfoService {
	
	@Autowired
	private PregnantMusicInfoMapper pregnantMusicInfoMapper;
	
	/**
	 * 查询电台信息
	 * @version 1.0
	 * @createTime 2016-12-1,上午10:20:05
	 * @updateTime 2016-12-1,上午10:20:05
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param category 音乐类型 (3：电台)
	 * @param beginIndex 页数
	 * @param everyPage 每页显示的记录数
	 * @return
	 */
	public List<Map<String, Object>> findPregnantMusicByCategory(int category, int beginIndex, int everyPage) {
		//返回数据
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		//参数
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("category", category);
		param.put("beginIndex", beginIndex);
		param.put("everyPage", everyPage);
		//电台信息
		List<PregnantMusicInfo> list = pregnantMusicInfoMapper.findPregnantMusicByCategory(param);
		for(int i=0; i<list.size(); i++) {
			PregnantMusicInfo music = list.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", music.getId());
			map.put("name", music.getName());
			map.put("voiceUrl", ConfigProUtils.get("file_path")+music.getVoiceUrl());
			map.put("length", music.getLength());
			map.put("coverUrl", ConfigProUtils.get("file_path")+music.getCoverUrl());
			result.add(map);
		}
		return result;
	}
	
	/**
	 * 查询电台总记录数
	 * @version 1.0
	 * @createTime 2016-12-1,上午10:45:03
	 * @updateTime 2016-12-1,上午10:45:03
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param category
	 * @return
	 */
	public int findCount(int category) {
		return pregnantMusicInfoMapper.findCount(category);
	}
}
