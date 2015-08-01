package com.kingdee.eas.fdc.material.client;

import java.math.BigDecimal;

import com.kingdee.eas.util.client.EASResource;

/**
 * 描述：甲供工具类
 * @author pengwei_hou
 * @Date 2008-9-20
 */
public class PartAUtils {
	
	
	/**
	 * 描述：将带有逗号和小数的字符串转为BigDecimal
	 * @param str
	 */
	public static BigDecimal toBigDecimal(String str){
		
		StringBuffer result = new StringBuffer();
		for(int i=0;i<str.length();i++)
		{
			if(str.charAt(i)!=',')
			{
				result.append(str.charAt(i));
			}
		}
		
		return new BigDecimal(result.toString());
	}
	
	/**
	 * 描述：金额整数部分长度
	 * @param amt
	 * @return size
	 */
	public static int getAmtSize(String amt){
		int index = 0;
		for(int i=0; i<amt.length(); i++){
			if(amt.charAt(i) == '.')
				index = i;
		}
		if(index == 0){
			return amt.length();
		}
		return amt.substring(0, index).length();
	}
	
	/**
	 * 描述：获取资源（从资源文件com.kingdee.eas.fdc.material.MaterialResource）
	 * @param resName	资源项名称
	 * @return String 获取的资源
	 */
	public static String getRes(String resName) {
		return EASResource
				.getString(
						"com.kingdee.eas.fdc.material.MaterialResource",
						resName);
	} 
}
