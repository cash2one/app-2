package com.jumper.angel.app.music.mapper;

import java.util.List;
import java.util.Map;

import com.jumper.angel.app.music.entity.PregnantMusicInfo;

/**
 * 孕期电台Mapper
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-12-1
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public interface PregnantMusicInfoMapper {
	
	/**
	 * 查询电台信息
	 * @version 1.0
	 * @createTime 2016-12-1,上午10:20:05
	 * @updateTime 2016-12-1,上午10:20:05
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param param
	 * @return
	 */
	public List<PregnantMusicInfo> findPregnantMusicByCategory(Map<String, Object> param);
	
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
	public int findCount(int category);
}
