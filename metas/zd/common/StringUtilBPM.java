package com.kingdee.eas.bpm.common;

public class StringUtilBPM{
	public static String isNULl(String str)
    {
		if(str == null)
			return "";
		return str.trim();
    }
}
