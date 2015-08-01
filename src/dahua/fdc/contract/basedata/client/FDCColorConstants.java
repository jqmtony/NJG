package com.kingdee.eas.fdc.basedata.client;

import java.awt.Color;

/**
 *  颜色方案来源：http://uiserver/Guide/2006%20EAS/color.htm
 */
public interface FDCColorConstants {
	/**
	 * 必录色
	 */
	public static Color requiredColor = new Color(0xFCFBDF);
	/**
	 * 日小计色
	 */
	public static Color daySubTotalColor = new Color(0xF5F5E6);
	/**
	 * 日合计色
	 */
	public static Color dayTotalColor = new Color(0xF0EDD9);
	/**
	 * 期间合计色
	 */
	public static Color periodTotalColor = new Color(0xE8E4CB);
	/**
	 * 年合计色
	 */
	public static Color yearTotalColor = new Color(0xE9E2B8);
	/**
	 * 合计色
	 */
	public static Color totalColor = new Color(0xF6F6BF);
	/**
	 * 不可编辑色
	 */
	public static Color cantEditColor = new Color(0xE8E8E3);
	/**
	 * 警告色
	 */
	public static Color warnColor = new Color(0xFFEA67);

	/**
	 * 中性警告色
	 */
	public static final Color middleWarmColor = new Color(0xFF, 0xEA, 0x67);
	/**
	 * 锁定色
	 */
	public static Color lockColor = new Color(0xF0EDD9);
	
	
	public final static Color KDTABLE_TOTAL_BG_COLOR = new Color(0xF6F6B6);

	public final static Color KDTABLE_SUBTOTAL_BG_COLOR = new Color(0xF5F5E6);
}
