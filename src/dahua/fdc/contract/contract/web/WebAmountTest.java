package com.kingdee.eas.fdc.contract.web;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.kingdee.eas.util.SysUtil;

public class WebAmountTest {

	public static String getDecimalFormat(String str) {
		DecimalFormat fmt = new DecimalFormat("###,##0.00");
		String outStr = null;
		double d;
		try {
			d = Double.parseDouble(str);
			outStr = fmt.format(d);
		} catch (Exception e) {
			SysUtil.abort();
		}
		return outStr;
	}
	public static String getDecimalFormat2(BigDecimal bigDecimal) {
		String st=bigDecimal.toString();
		DecimalFormat fmt = new DecimalFormat("###,##0.00");
		String outStr = null;
		double d;
		try {
			d = Double.parseDouble(st);
			outStr = fmt.format(d);
		} catch (Exception e) {
			SysUtil.abort();
		}
		return outStr;
	}
/*

	private String getFormatter(String str) {
		NumberFormat n = NumberFormat.getNumberInstance();
		double d;
		String outStr = null;
		try {
			d = Double.parseDouble(str);
			outStr = n.format(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outStr;
	}

	private String getCurrency(String str) {
		NumberFormat n = NumberFormat.getCurrencyInstance();
		double d;
		String outStr = null;
		try {
			d = Double.parseDouble(str);
			outStr = n.format(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outStr;
	}
	*/
}
