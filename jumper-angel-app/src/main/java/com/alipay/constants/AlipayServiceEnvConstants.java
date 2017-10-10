

/**

 * Alipay.com Inc.

 * Copyright (c) 2004-2014 All Rights Reserved.

 */

package com.alipay.constants;


/**
 * 支付宝服务窗环境常量（demo中常量只是参考，需要修改成自己的常量值）
 * 
 * @author taixu.zqq
 * @version $Id: AlipayServiceConstants.java, v 0.1 2014年7月24日 下午4:33:49 taixu.zqq Exp $
 */
public class AlipayServiceEnvConstants {

    /**支付宝公钥-从支付宝生活号详情页面获取*/
	public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApXOOXvg1nBkFUV9FYM8XaIRLhPgRWooktiSReuCFf/PlmFu8Ycn3ceFsUcpoeqLKYPAbmQiAytvWnzIW8/UVqsVemBDtAwWC0jamVvpOmuNW7OUpKiyUW5hGiU7wK3sFiDxOb0B2JW35xk855xBU1QwyreH2KXG3wl9fVTFkHy0lqiy3SGiFy3Kia0PD4OhajlQX6N+WaC6MWZ9RvLRMLGeMgCZ4C1LLYlcnTHJ+gGNW/S6e9wmH9QS4hKmAJfXBTkFbzvMIPjIJbTGDb9glMFjUmsqVmLt4nFNuYHP/0z4ljEj29N0N5C7OJC57Zd9DElCV23zvw1NFURqWbyr1gwIDAQAB";
    
    /**签名编码-视支付宝服务窗要求*/
    public static final String SIGN_CHARSET      = "GBK";

    /**字符编码-传递给支付宝的数据编码*/
    public static final String CHARSET           = "GBK";

    /**签名类型-视支付宝服务窗要求*/
    public static final String SIGN_TYPE         = "RSA2";
    
    /**开发者账号PID*/
    public static final String PARTNER           = "2088102248442061";

    /** 服务窗appId  */
    //TODO !!!! 注：该appId必须设为开发者自己的生活号id  
    public static final String APP_ID            = "2015121100957751";
    
   // public static final String APP_ID            = "2015121100957751";
    
    //public static final String APP_ID            = "2016073000125490";

    //TODO !!!! 注：该私钥为测试账号私钥  开发者必须设置自己的私钥 , 否则会存在安全隐患 
    public static final String PRIVATE_KEY       = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDBLBGcQJ1Ge9vc+TfsAKRUkguLO/4PrLP11OQV9NGJA+6hN7Pe+mTHexE6wnXwdRgSiG3G2+O4V/kZ9ldd3KzEY0Td3GVblyVMRW8SrNmV8QpEV99cfwLZNxP1QpoX0pLmfwRyUT4VFfqrUTdKBg2IoLcBTZKTQjJxOlP3+gVO2pzAwhHTKrgFZHQUQcj86G3nUbE1IYND/d7taloWiA7Vl+D01W3qkf3qkr80CAWEnHqoWJEQPTivz8HrQ8BL7ptrGfskeAfPhxXvxtDOooDLryq3H6eHjgsqbfbCkTnED4XvIKDBJauzxrYfAOe2U/ZElrVa9p/30V5Yl7mq2VcHAgMBAAECggEBAJlOjDth3HAP5JIfCA65A7JKpfa+igeOnNRBPTNftNv0zvIL7yjLuCfIuAfWRRutEonC1Ly5zqSCzSOZqgTIGiVSU1lwjNB/yA4cHObJX3XscqWCx7X05yHU4HYJgZneeM+Nhs3jRE4fv20va53H5/3C7vqNwgI0yyupivKG7qeA3ioyClYVbnSvPgXEslejBYMxt/u4ZrAUbE5o6hiuh3awA3/i2gIgoMa9qx7hiyx85h07yrZzpixTP19Bu2ieOm8Q3pudUt8wldXTLwrDuLiNdCHzUEYzOMsijn/9eqXLladn0fxglz9/adFq3uKI0HcJIh2+TO3hm0/34Kp4ZsECgYEA8bqwMOEDo2IVaCfMwCvV1Cyqwc8jLvH/rrG9/u1PvDh2fBDsL3gTqLhCbbp60BMJDdC1W1zZsy+V7PsZtm6MFh+DyZ835fZ4ihCVjZ4SyfLnE1aC0fZopDMtBUwrpXit7GSU9bHtQunUoyugJjgZxkrakRgSJ9r7O9RFV1xmxnkCgYEAzJOGgaMHV1541JONs+HM8IWEwuCWWLBIMVxtfZZJgmjhto488m2O/nmcXOkjebDuRXna9z13lIret+LoroWMt1rZH6RCUzPcBcRBKnjSUgtKKj2/knu6cYvXJx5pixCA/BplldC9BygjcchWTNYOTuzpCzRNQQZ/gLcPqLfPqX8CgYEAt2swxeX3HMIP/Dg25JpeZeB+sRC7YaO+no+/2u+20NsHDJzKmjpCsIMf5KLbDYdkF1eGRQzvI0e4O1lM6WB4a1D+br6ha5zZWem7kPmgyBvHuqSqHycZv34Vay+g7q3jSrwHS2b24Q8MicNQga+P3yZtMitRm2xNtdgbxW4XjgECgYBnppNu2WAd10LXSc9xBYXlk3C5oDrswpxBTjg3ek7SYTuDWDvvBK1/p7Qeqw0LWibPUrcrr5wHyfigKLqFuiqat8/spTfpmgvvbAkscLz3TrsL/e8zU7caUqT3MAR/riP8F4/CwVkU6DRVMRDwqz/io0xMHfrKaHVvjNDFRiRUFwKBgFFXZ+b9chR6RgSD4AGXbujdJb3sgoH/juaf85Y7ueBnh0ICTrrPssOXDtrlawGQSL77huQ0+FqArB60rGM5ZnoowbB445PvuNlMWW/TXFWATg3/FyPHKC12CodZl9Iyta34FyiGCbFukfIbJPr+Ny8A3ZToDq7z6UUgMdZQVTZf";
    
    //TODO !!!! 注：该公钥为测试账号公钥  开发者必须设置自己的公钥 ,否则会存在安全隐患
    public static final String PUBLIC_KEY        = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwSwRnECdRnvb3Pk37ACkVJILizv+D6yz9dTkFfTRiQPuoTez3vpkx3sROsJ18HUYEohtxtvjuFf5GfZXXdysxGNE3dxlW5clTEVvEqzZlfEKRFffXH8C2TcT9UKaF9KS5n8EclE+FRX6q1E3SgYNiKC3AU2Sk0IycTpT9/oFTtqcwMIR0yq4BWR0FEHI/Oht51GxNSGDQ/3e7WpaFogO1Zfg9NVt6pH96pK/NAgFhJx6qFiRED04r8/B60PAS+6baxn7JHgHz4cV78bQzqKAy68qtx+nh44LKm32wpE5xA+F7yCgwSWrs8a2HwDntlP2RJa1Wvaf99FeWJe5qtlXBwIDAQAB";
    /**支付宝网关*/
    public static final String ALIPAY_GATEWAY    = "https://openapi.alipay.com/gateway.do";
    
    //public static final String ALIPAY_GATEWAY    = "https://openapi.alipaydev.com/gateway.do";
    /**授权访问令牌的授权类型*/
    public static final String GRANT_TYPE        = "authorization_code";
}