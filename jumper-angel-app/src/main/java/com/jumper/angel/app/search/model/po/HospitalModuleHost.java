package com.jumper.angel.app.search.model.po;
/**
 * 医院模块对应的host,只有合作医院才有
 * @author Administrator
 *
 */
public class HospitalModuleHost implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7327195070757170275L;
	private Integer id;					//主键
	private Integer hospitalId;				//医院id
	private String host;					//地址
	private Integer moduleNum;			//模块编号  1表示建册  类别2表示高危
	private String	remark;				//备注
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getHospitalId() {
		return hospitalId;
	}
	public void setHospitalId(Integer hospitalId) {
		this.hospitalId = hospitalId;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public Integer getModuleNum() {
		return moduleNum;
	}
	public void setModuleNum(Integer moduleNum) {
		this.moduleNum = moduleNum;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
