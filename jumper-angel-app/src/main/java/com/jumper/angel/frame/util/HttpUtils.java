package com.jumper.angel.frame.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * Http工具类
 * @Description TODO
 * @author qinxiaowei
 * @date 2017年4月18日
 * @Copyright: Copyright (c) 2017 Shenzhen 天使医生 Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class HttpUtils {
	
	private final static Logger logger = Logger.getLogger(HttpUtils.class);
	
	/**
	 * get请求
	 * @version v.1.0
	 * @createTime 2017年4月18日,下午2:05:34
	 * @updateTime 2017年4月18日,下午2:05:34
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param url
	 * @return
	 */
	public static String get(String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        try {
            CloseableHttpResponse response = httpClient.execute(httpget);
            HttpEntity httpEntity = response.getEntity();
            String strResult = EntityUtils.toString(httpEntity);
            logger.debug("url:"+url+",response:"+strResult);
            return strResult;
        } catch (Exception ex) {
            logger.warn("excption:{}", ex);
        } finally {
            httpget.releaseConnection();
            try {
                httpClient.close();
            } catch (IOException e) {
                logger.error("httpclient.close() Failure :{} " + e);
            }
        }
        return null;
    }
	
	/**
	 * 带参数的get请求
	 * @version v.1.0
	 * @createTime 2017年4月18日,下午2:09:34
	 * @updateTime 2017年4月18日,下午2:09:34
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param url
	 * @param parMap
	 * @return
	 */
	public static String get(String url, Map<String, String> parMap) {
        String body = null;
        HttpClientBuilder httpclientBuilder = HttpClientBuilder.create();
        CloseableHttpClient httpclient = httpclientBuilder.build();
        HttpGet httpget = new HttpGet(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        Set<Map.Entry<String, String>> paramsEntry = parMap.entrySet();
        for (Map.Entry<String, String> paramEntry : paramsEntry) {
            nvps.add(new BasicNameValuePair(paramEntry.getKey(), paramEntry.getValue()));
        }
        try {
            String str = EntityUtils.toString(new UrlEncodedFormEntity(nvps, "UTF-8"));
            httpget.setURI(new URI(httpget.getURI().toString() + "?" + str));
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                //状态码，一般状态码为200时，为正常状态
                logger.debug("http get status is : "+response.getStatusLine());
                HttpEntity entity = response.getEntity();
                body = EntityUtils.toString(entity, "UTF-8");
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
        } catch (Exception e) {
            logger.info("Http Get Method Failure : {}", e);
        } finally {
            httpget.releaseConnection();
            try {
                httpclient.close();
            } catch (IOException e) {
                logger.error("httpclient.close() Failure :{} " + e);
            }
        }
        logger.info("response body："+body);
        return body;
    }
	
	/**
	 * 带参数post请求
	 * @version v.1.0
	 * @createTime 2017年4月18日,下午2:09:24
	 * @updateTime 2017年4月18日,下午2:09:24
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param url
	 * @param map
	 * @return
	 */
	public static String post(String url, Map<String, String> map) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpost = new HttpPost(url);
        if (map != null) {
            List<NameValuePair> nvps = new ArrayList<>();
            for (String key : map.keySet()) {
                nvps.add(new BasicNameValuePair(key, map.get(key)));
            }
            httpost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
        }
        try {
            CloseableHttpResponse response = httpClient.execute(httpost);
            String strResult = EntityUtils.toString(response.getEntity());
            logger.debug("url:"+url+",map:"+map+",response:"+strResult);
            return strResult;
        } catch (Exception ex) {
            logger.warn("excption:{}", ex);
        } finally {
            httpost.releaseConnection();
            try {
                httpClient.close();
            } catch (IOException e) {
                logger.error("httpclient.close() Failure :{} " + e);
            }
        }
        return null;
    }
	
	public static String doPost(String urlStr,String param ){
		try{
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");
			String paramStr = param;
			conn.setDoInput(true);
			conn.setDoOutput(true);
			OutputStream os = conn.getOutputStream();    
			os.write(paramStr.toString().getBytes("utf-8"));    
			os.close();        
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			String line ;
			String result ="";
			while( (line =br.readLine()) != null ){
				result += ""+line;
			}
			br.close();
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			return "获取异常";
		}
	}
}
