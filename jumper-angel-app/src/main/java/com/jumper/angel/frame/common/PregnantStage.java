package com.jumper.angel.frame.common;
/**
 * 孕期阶段枚举
 * @author rent
 * @date 2015-07-23
 */
public enum PregnantStage {

	EARLY_PREGNANT(1, "孕早期",   0,   12),
	MID_PREGNANT  (2, "孕中期",   13,  28),
	LATE_PREGNANT (3, "孕晚期",   29,  39),
	LITTLE_SIX    (4, "0-6月",    0,   25),
	LITTLE_YEAR   (5, "6月-1岁",  26,  52),
	LITTLE_THREE  (6, "1-3岁",    53,  156);
	
	/** 类型 **/
	private int type;
	/** 描述 **/
	private String desc;
	/** 始末周 **/
	private int[] week = new int[2];

	private PregnantStage(int type, String desc,int start, int end) {
		this.type = type;
		this.desc = desc;
		this.week[0] = start;
		this.week[1] = end;
	}
	public int[] getWeek() {
		return week;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	/**
	 * 根据标示获取描述
	 * @param type
	 * @return
	 */
	public static PregnantStage getObject(int type){
		PregnantStage[] values = PregnantStage.values();
		if(type>0 && type<=values.length){
			return values[type-1];
		}else{
			return values[0];
		}
	}
	
	/**
	 * 根据周和身份获取 阶段
	 * @param week
	 * @param currentIdentity
	 * @return
	 */
	public static PregnantStage getStage(int week, Byte currentIdentity) {
		PregnantStage[] values = PregnantStage.values();
		if(week <= 0){
			week = 1;
		}else{
			if(currentIdentity == 0 && week > 40){
				week=40;
			}else if(currentIdentity == 1 && week > 157){
				week=157;
			}
		}
		for(PregnantStage stage : values){
			int[] weeks = stage.getWeek();
			if(currentIdentity == 0 && stage.getType() <= 3){
				if(weeks[0] <= week && week <= weeks[1]){
					return stage;
				}
			}else if(currentIdentity == 1 && stage.getType() >= 4){
				if(weeks[0] <= week && week <= weeks[1]){
					return stage;
				}
			}
		}
		return null;
	}
}
