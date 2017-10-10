package com.jumper.angel.app.music.service;

import java.util.List;
import java.util.Map;
/**
 * 孕期电台Service
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-12-1
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public interface PregnantMusicInfoService {

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
	public List<Map<String, Object>> findPregnantMusicByCategory(int category, int beginIndex, int everyPage);
	
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
