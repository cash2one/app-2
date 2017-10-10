package com.jumper.angel.frame.common;

/**
 * 构建一个page的工厂PageUtil类
 * @作者 秦晓伟
 *
 */
public class PageUtil {
	/**
	 * 使用起源页创造新的页
	 * @param page
	 * @param totalRecords
	 * @return
	 */  
	public static Page createPage(Page page,int totalRecords)
	{
		return createPage(page.getEveryPage(),page.getCurrentPage(),totalRecords);
	}
	/**
	 * 重载createPage()方法
	 * @param everyPage
	 * @param currentPage
	 * @param totalRecords
	 * @return
	 */
	public static Page createPage(int everyPage,int currentPage,int totalRecords)
	{
		everyPage = getEveryPage(everyPage);
		currentPage = getCurrentPage(currentPage,totalRecords);
		int beginIndex = getBeginIndex(everyPage, currentPage);
		int totalPage = getTotalPage(everyPage, totalRecords);
		boolean hasPrePage = hasPrePage(currentPage);
		boolean hasNextPage = hasNextPage(currentPage, totalPage);
		return new Page(hasPrePage,hasNextPage,everyPage,
				totalPage,currentPage,beginIndex,totalRecords);
	}
	/**
	 * 每页的数量
	 * @param everyPage
	 * @return
	 */
	private static int getEveryPage(int everyPage)
	{
		return everyPage == 0?15:everyPage;
	}
	/**
	 * 当前页
	 * @param currentPage
	 * @return
	 */
	private static int getCurrentPage(int currentPage,int totalPage)
	{
		if(currentPage < 1)
		{
			currentPage = 1;
		}
		if(currentPage > totalPage)
		{
			currentPage = totalPage;
		}
		return currentPage;
//		return currentPage == 0?1:currentPage;
	}
	/**
	 * 起始点
	 * @param everyPage
	 * @param currentPage
	 * @return
	 */
	private static int getBeginIndex(int everyPage, int currentPage)
	{
		if(currentPage == 0) {
			currentPage = 1;
		}
		return (currentPage - 1) * everyPage;
	}
	/**
	 * 总页数
	 * @param everyPage
	 * @param totalRecords
	 * @return
	 */
	public static int getTotalPage(int everyPage,int totalRecords)
	{
		int totalPage = 0;
		if(totalRecords % everyPage == 0)
		{
			totalPage = totalRecords/everyPage;
		}
		else
		{
			totalPage = totalRecords/everyPage + 1;
		}
		return totalPage;
	}
	/**
	 * 上一页
	 * @param currentPage
	 * @return
	 */
	private static boolean hasPrePage(int currentPage)
	{
		return currentPage == 1?false:true;
	}
	/**
	 * 下一页
	 * @param currentPage
	 * @param totalPage
	 * @return
	 */
	private static boolean hasNextPage(int currentPage,int totalPage)
	{
		return currentPage == totalPage || currentPage==0?false:true;
	}
}