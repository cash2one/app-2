package com.jumper.angel.app.usercenter.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * APP版本
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-12-9
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class AppVersion implements Serializable {
    
	private static final long serialVersionUID = -510488147029924820L;

	private Integer id;

    private Integer appType;

    private String versionNo;

    private String downloadUrl;

    private Date createTime;

    private String versionName;

    private Integer forcedUpdate;

    private String remark;
    //是否提供下载(0：不下载，只提供地址  1：下载，直接下载)
    private int isDownload;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAppType() {
        return appType;
    }

    public void setAppType(Integer appType) {
        this.appType = appType;
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo == null ? null : versionNo.trim();
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl == null ? null : downloadUrl.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName == null ? null : versionName.trim();
    }

    public Integer getForcedUpdate() {
        return forcedUpdate;
    }

    public void setForcedUpdate(Integer forcedUpdate) {
        this.forcedUpdate = forcedUpdate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public int getIsDownload() {
		return isDownload;
	}

	public void setIsDownload(int isDownload) {
		this.isDownload = isDownload;
	}
}