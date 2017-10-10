package com.jumper.angel.app.bindhospital.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * 绑定医院流水entity
 * @Description TODO
 * @author qinxiaowei
 * @date 2016-12-13
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class BindHospitalLog implements Serializable {
   
	private static final long serialVersionUID = -7924785694213232328L;

	private Long id;

    private Long userId;

    private Long hospitalId;

    private Integer oprationStatus;

    private String mobileIp;

    private Double lng;

    private Double lat;

    private String mobileMac;

    private String versionName;

    private Integer mobileType;

    private String cause;

    private Integer firstBinding;

    private Date bindingDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public Integer getOprationStatus() {
        return oprationStatus;
    }

    public void setOprationStatus(Integer oprationStatus) {
        this.oprationStatus = oprationStatus;
    }

    public String getMobileIp() {
        return mobileIp;
    }

    public void setMobileIp(String mobileIp) {
        this.mobileIp = mobileIp == null ? null : mobileIp.trim();
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getMobileMac() {
        return mobileMac;
    }

    public void setMobileMac(String mobileMac) {
        this.mobileMac = mobileMac == null ? null : mobileMac.trim();
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName == null ? null : versionName.trim();
    }

    public Integer getMobileType() {
        return mobileType;
    }

    public void setMobileType(Integer mobileType) {
        this.mobileType = mobileType;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause == null ? null : cause.trim();
    }

    public Integer getFirstBinding() {
        return firstBinding;
    }

    public void setFirstBinding(Integer firstBinding) {
        this.firstBinding = firstBinding;
    }

    public Date getBindingDate() {
        return bindingDate;
    }

    public void setBindingDate(Date bindingDate) {
        this.bindingDate = bindingDate;
    }
}