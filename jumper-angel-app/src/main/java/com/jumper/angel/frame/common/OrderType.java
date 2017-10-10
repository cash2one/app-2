package com.jumper.angel.frame.common;

public enum OrderType {
	/*FETAL_HEARTRATE_MONITOR  (0,"胎心监护"),
	HOSPITAL_CONSULTANT      (1,"医院问诊"),
	PREGNANT_SCHOOL          (2,"孕妇学校"),
	TUWEN_CONSULTANT         (3,"图文咨询"),
	PRIVATE_DOCTOR           (4,"私人医生"),
	MY_GUAHAO                (5,"我的挂号"),
	NETWORK_ORDER        (6,"网络诊室"),
	DEVICE_LEASE		(7,"设备租赁");*/
	TUWEN_CONSULTANT         (1,"图文咨询"),
	HOSPITAL_CONSULTANT      (2,"医院问诊"),
	FETAL_HEARTRATE_MONITOR  (3,"胎心监护"),
	ONLINE_CONSULTANT        (4,"在线问诊"),
	PRIVATE_DOCTOR           (5,"私人医生"),
	NETWORK_ORDER        	 (6,"网络诊室"),
	DEVICE_LEASE		     (7,"设备租赁"),
	FETAL_HEARTRATE_READING  (8,"胎心解读");
	
	
	private final int type;
	
	private String name;
	
	public int getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private OrderType(int type,String name){
		this.type = type;
		this.name = name;
	}
	
	public static String getName(int index){
		OrderType[] orderList = OrderType.values();
		if(index>=0 && index < orderList.length){
			return orderList[index].getName();
		}
		return null;
	}

}
