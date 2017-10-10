package com.jumper.angel.frame.util;

import java.util.Comparator;

import com.jumper.angel.app.subscription.vo.NewsCollectionVo;
import com.jumper.angel.app.subscription.vo.NewsInformationVo;

/**
 * 根据list中的时间排序(降序排列)
 * @author gyx
 * @time 2016年12月9日
 */
@SuppressWarnings("rawtypes")
public class ComparatorList implements Comparator{

	@Override
	public int compare(Object o1, Object o2) {
		NewsCollectionVo vo1 = (NewsCollectionVo) o1;
		NewsCollectionVo vo2 = (NewsCollectionVo) o2;
		int flag = vo2.getSortTime().compareTo(vo1.getSortTime());
		return flag;
	}
	
}
