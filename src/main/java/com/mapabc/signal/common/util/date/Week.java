package com.mapabc.signal.common.util.date;


/**
 * @author yinguijin
 * @version 1.0
 * @Description: [星期工具类]
 * Created on 2019/4/16 15:07
 */
public enum Week {

	MONDAY("星期一", "周一", "Monday", "Mon.", 1),
	TUESDAY("星期二", "周二","Tuesday", "Tues.", 2),
	WEDNESDAY("星期三", "周三","Wednesday", "Wed.", 3),
	THURSDAY("星期四", "周四","Thursday","Thur.", 4),
	FRIDAY("星期五", "周五","Friday", "Fri.", 5),
	SATURDAY("星期六", "周六","Saturday", "Sat.", 6),
	SUNDAY("星期日", "周日","Sunday", "Sun.", 7);

	String name_cn;
	String name_cnWeek;
	String name_en;
	String name_enShort;
	int number;

	Week(String name_cn, String name_cnWeek,String name_en, String name_enShort, int number) {
		this.name_cn = name_cn;
		this.name_cnWeek = name_cnWeek;
		this.name_en = name_en;
		this.name_enShort = name_enShort;
		this.number = number;
	}

	public String getChineseName() {
		return name_cn;
	}

	public String getWeekName() {
		return name_cnWeek;
	}

	public String getName() {
		return name_en;
	}

	public String getShortName() {
		return name_enShort;
	}

	public int getNumber() {
		return number;
	}

}