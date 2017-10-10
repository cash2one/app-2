package com.jumper.angel.frame.util;

import java.net.URL;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * 缓存工具类
 * 
 * @Description TODO
 * @author qinxiaowei
 * @date 2017年3月28日
 * @Copyright: Copyright (c) 2017 Shenzhen 天使医生 Technology Co., Ltd. Inc. All
 *             rights reserved.
 */
public class EhcacheUtil {

	private static final String path = "conf/ehcache.xml";

	private URL url;

	private CacheManager manager;

	private static EhcacheUtil ehCache;

	private EhcacheUtil(String path) {
		url = EhcacheUtil.class.getClassLoader().getResource(path);
		manager = CacheManager.create(url);
	}

	public static EhcacheUtil getInstance() {
		if (ehCache== null) {
			ehCache= new EhcacheUtil(path);
		}
		return ehCache;
	}
	
	public void put(String cacheName, String key, Object value) {
		Cache cache = manager.getCache(cacheName);
		Element element = new Element(key, value);
		cache.put(element);
	} 

	public Object get(String cacheName, String key) {
		Cache cache = manager.getCache(cacheName);
		Element element = cache.get(key);
		return element == null ? null : element.getObjectValue();
	} 

	public Cache get(String cacheName) {
		return manager.getCache(cacheName);
	} 

	public void remove(String cacheName, String key) { 
		Cache cache = manager.getCache(cacheName); 
		cache.remove(key);
	}
}
