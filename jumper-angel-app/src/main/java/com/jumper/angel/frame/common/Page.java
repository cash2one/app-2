package com.jumper.angel.frame.common;

/**
 * 创建一个Page类
 * @作者 秦晓伟
 *
 */
public class Page {
	//是否有上一页
	private boolean hasPrePage;
	//是否有下一页
	private boolean hasNextPage;
	//每页显示数量
	private int everyPage;
	//总页数
	private int totalPage;
	//当前页
	private int currentPage;
	//起始页
	private int beginIndex;
	//总记录数
	private int totalCount;
	//构造方法
	public Page(){};
	//带一个参数的构造方法
	public Page(int everyPage)
	{
		this.everyPage = everyPage;
	}
	//带所有参数的构造方法
	public Page(boolean hasPrePage,boolean hasNextPage,int everyPage,
			int totalPage,int currentPage,int beginIndex,int totalCount)
	{
		this.hasPrePage = hasPrePage;
		this.hasNextPage = hasNextPage;
		this.everyPage = everyPage;
		this.totalPage = totalPage;
		this.currentPage = currentPage;
		this.beginIndex = beginIndex;
		this.totalCount = totalCount;
	}
	/******************************* 以下为封装属性 *******************************/
	
	/**
	 * 起始页数
	 * @return
	 */
	public int getBeginIndex() {
		return beginIndex;
	}
	/**
	 * 设置起始页数
	 * @param beginIndex
	 */
	public void setBeginIndex(int beginIndex) {
		this.beginIndex = beginIndex;
	}
	/**
	 * 当前页
	 * @return
	 */
	public int getCurrentPage() {
		return currentPage;
	}
	/**
	 * 设置当前页
	 * @param currentPage
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	/**
	 * 每页显示多少条数据
	 * @return
	 */
	public int getEveryPage() {
		return everyPage;
	}
	/**
	 * 设置每页显示多少条数据
	 * @param everyPage
	 */
	public void setEveryPage(int everyPage) {
		this.everyPage = everyPage;
	}
	/**
	 * 是否有下一页
	 * @return
	 */
	public boolean isHasNextPage() {
		return hasNextPage;
	}
	/**
	 * 设置是否有下一页
	 * @param hasNextPage
	 */
	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}
	/**
	 * 是否有上一页
	 * @return
	 */
	public boolean isHasPrePage() {
		return hasPrePage;
	}
	/**
	 * 设置是否有上一页
	 * @param hasPrePage
	 */
	public void setHasPrePage(boolean hasPrePage) {
		this.hasPrePage = hasPrePage;
	}
	/**
	 * 总记录数
	 * @return
	 */
	public int getTotalCount() {
		return totalCount;
	}
	/**
	 * 设置总记录数
	 * @param totalCount
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	/**
	 * 总页数
	 * @return
	 */
	public int getTotalPage() {
		return totalPage;
	}
	/**
	 * 设置总页数
	 * @param totalPage
	 */
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
}