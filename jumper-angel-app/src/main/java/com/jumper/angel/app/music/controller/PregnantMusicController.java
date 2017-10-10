package com.jumper.angel.app.music.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jumper.angel.app.music.service.PregnantMusicInfoService;
import com.jumper.angel.frame.common.Page;
import com.jumper.angel.frame.common.PageUtil;
import com.jumper.angel.frame.common.Result;
import com.jumper.angel.frame.util.Const;
import com.jumper.angel.frame.util.ResultMsg;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * 孕期电台Controller
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-12-1
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
@Controller
@RequestMapping("music")
public class PregnantMusicController {
	
	private final static Logger logger = Logger.getLogger(PregnantMusicController.class);
	
	@Autowired
	private PregnantMusicInfoService pregnantMusicInfoService;
	
	/**
	 * 查询孕期电台
	 * @version 1.0
	 * @createTime 2016-12-1,上午10:42:12
	 * @updateTime 2016-12-1,上午10:42:12
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param pageIndex 页数
	 * @param everyPage 每页显示的记录数
	 * @return
	 */
	@RequestMapping(value="findMusic/{pageIndex}/{everyPage}", method=RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="查询孕期电台", httpMethod="GET", notes="查询孕期电台")
	public ResultMsg findMusic(@ApiParam(name="pageIndex", value="页数", required = true) @PathVariable("pageIndex") int pageIndex,
			@ApiParam(name="everyPage", value="每页显示的记录数", required = true) @PathVariable("everyPage") int everyPage) {
		try {
			//电台
			int category = 3;
			//实例化Page对象
			Page page = new Page();
			//设置第几页
			page.setCurrentPage(pageIndex);
			//每页显示的记录数
			page.setEveryPage(everyPage);
			//总记录数
			int count = pregnantMusicInfoService.findCount(category);
			page = PageUtil.createPage(page, count);
			//电台信息
			List<Map<String, Object>> list = pregnantMusicInfoService.findPregnantMusicByCategory(category, page.getBeginIndex(), page.getEveryPage());
			//分页结果
			Result result = new Result(page, list);
			if(result.getContent()!=null && result.getContent().size()>0) {
				return new ResultMsg(Const.SUCCESS, "获取孕期电台信息成功！", result.getContent());
			} else {
				return new ResultMsg(Const.NODATA, "请求的数据为空！", new ArrayList<Map<String, Object>>());
			}
		} catch (Exception e) {
			logger.error("findMusic()", e);
			return new ResultMsg(Const.FAILED, "获取孕期电台信息失败！");
		}
	}
	
	/**
	 * 测试接口
	 * @version 1.0
	 * @createTime 2016-12-1,上午11:10:09
	 * @updateTime 2016-12-1,上午11:10:09
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @return
	 */
	@RequestMapping(value="findTest", method=RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="测试接口", httpMethod="GET", notes="测试接口")
	public ResultMsg findTest() {
		return new ResultMsg(1, "测试接口成功！");
	}
}
