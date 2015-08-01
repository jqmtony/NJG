package com.kingdee.eas.fdc.material.client;

import java.math.BigDecimal;

import com.kingdee.eas.util.client.EASResource;

/**
 * �������׹�������
 * @author pengwei_hou
 * @Date 2008-9-20
 */
public class PartAUtils {
	
	
	/**
	 * �����������ж��ź�С�����ַ���תΪBigDecimal
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
	 * ����������������ֳ���
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
	 * ��������ȡ��Դ������Դ�ļ�com.kingdee.eas.fdc.material.MaterialResource��
	 * @param resName	��Դ������
	 * @return String ��ȡ����Դ
	 */
	public static String getRes(String resName) {
		return EASResource
				.getString(
						"com.kingdee.eas.fdc.material.MaterialResource",
						resName);
	} 
}
