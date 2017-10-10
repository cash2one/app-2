package com.jumper.angel.frame.common;

/**
 * 监测项目
 * @author gyx
 * @time 2016年12月9日
 */
public enum MonitorProject {
	
	
	FETAL_HEART         (1,"胎心"),
	BLOOD_OXYGEN        (2,"血氧"),
	HEART_RATE          (3,"心率"),
	TEMPERATURE         (4,"体温"),
	WEIGHT              (5,"体重"),
	BLOOD_PRESSURE      (6,"血压"),
	BLOOD_SUGAR 	    (7,"血糖"),
	FETAL_MOVEMENT      (8,"胎动"),
	URINE               (9,"尿液"),
	ECG                 (10,"心电"),
	BLOOD_FAT           (11,"血脂");
	
	
	private final int type;
	private String name;
	private MonitorProject(int type, String name){
		this.type = type;
		this.name = name;
	}
	
	public int getType() {
		return type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public static String getName(int index){
		MonitorProject[] projectList = MonitorProject.values();
		if(index>=0 && index < projectList.length){
			return projectList[index].getName();
		}
		return null;
	}
	
}
	
	
