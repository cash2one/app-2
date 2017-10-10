package com.jumper.angel.frame.common;

import java.util.List;

public class Result {
	//分页信息
	private Page page;
	//每页显示的集合
	private List content;
	//默认的构造方法
	public Result()
	{
		super();
	}
	//带参数的构造方法
	public Result(Page page,List content)
	{
		this.page = page;
		this.content = content;
	}
	//以下为封装属性
	/**
	 * 每页显示的集合
	 * @return
	 */
	public List getContent() {
		return content;
	}
	public void setContent(List content) {
		this.content = content;
	}
	/**
	 * 分页信息
	 * @return
	 */
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
}

