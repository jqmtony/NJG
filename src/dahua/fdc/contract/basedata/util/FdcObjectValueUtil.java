/*
 * @(#)FdcObjectValueUtil.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.fdc.basedata.util;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.util.Assert;

/**
 * ���ز� ����ֵ������
 * 
 * @author ����
 * @email SkyIter@live.com
 * @date 2013-9-9
 * @see
 * @since 1.4
 */
public class FdcObjectValueUtil {
	
	/**
	 * ȡ������ֵ
	 * <p>
	 * 1��֧�ּ�������<br>
	 * 
	 * @param objectValue
	 * @param key
	 * @return
	 */
	public static Object get(IObjectValue objectValue, String key) {
		Object value = null;
		if (null == objectValue || FdcStringUtil.isBlank(key)) {
			return null;
		}

		IObjectValue tempObjectValue = objectValue;
		String[] keyArr = key.split("\\.");
		String subKey = null;
		for (int i = 0, len = keyArr.length; i < len; i++) {
			subKey = keyArr[i];
			value = tempObjectValue.get(subKey);

			if (null == value) {
				break;
			}
			// ��Ϊ�գ��һ����¼�
			else if ((len - 1) != i) {
				tempObjectValue = (IObjectValue) value;
			}
		}

		return value;
	}

	// ///////////////////////////////////////////////////////////////////////////////////////
	// Locale �����ػ�
	// ///////////////////////////////////////////////////////////////////////////////////////

	/**
	 * ���ñ��ػ�����ֵ
	 * 
	 * @param objectValue
	 *            ���󣻷ǿ�
	 * @param key
	 *            ���������ǿ�
	 * @param valueArr
	 *            ����ֵ���飻����ǿգ�Ԫ�ؿɿ�
	 * @param localeArr
	 *            ���ػ����飻����ǿգ�Ԫ�طǿ�
	 */
	public static void setLocalValue(IObjectValue objectValue, String key, Object[] valueArr, Locale[] localeArr) {
		int valueArrLen = valueArr.length;
		int localeArrLen = localeArr.length;
		boolean cond = valueArrLen == localeArrLen;
		String description = "����ֵ����valueArr����Ϊ" + valueArrLen + "�����ػ�����localeArr�ĳ���Ϊ" + localeArrLen + "����һ�� ";
		Assert.that(cond, description);

		Locale locale = null;
		Object value = null;
		for (int i = 0, length = localeArr.length; i < length; i++) {
			value = valueArr[i];
			locale = localeArr[i];
			objectValue.put(key, value, locale);
		}
	}

	/**
	 * ȡ�ñ��ػ�����ֵMap
	 * <p>
	 * ����key��ű��ػ�ʵ����value�������ֵ
	 * 
	 * @param objectValue
	 *            ���󣻷ǿ�
	 * @param key
	 *            ���������ǿ�
	 * @param localeArr
	 *            ���ػ����飻����ǿգ�Ԫ�طǿ�
	 */
	public static Map getLocalValueMap(IObjectValue objectValue, String key, Locale[] localeArr) {
		Map map = new LinkedHashMap();

		Locale locale = null;
		Object value = null;
		for (int i = 0, length = localeArr.length; i < length; i++) {
			locale = localeArr[i];
			value = objectValue.get(key, locale);
			map.put(locale, value);
		}

		return map;
	}

	/**
	 * ȡ�ñ��ػ�����ֵ����
	 * <p>
	 * ����key��ű��ػ�ʵ����value�������ֵ
	 * 
	 * @param objectValue
	 *            ���󣻷ǿ�
	 * @param key
	 *            ���������ǿ�
	 * @param localeArr
	 *            ���ػ����飻����ǿգ�Ԫ�طǿ�
	 */
	public static Object[] getLocalValueArr(IObjectValue objectValue, String key, Locale[] localeArr) {
		Object[] valueArr = new Object[localeArr.length];

		Locale locale = null;
		Object value = null;
		for (int i = 0, length = localeArr.length; i < length; i++) {
			locale = localeArr[i];
			value = objectValue.get(key, locale);
			valueArr[i] = value;
		}

		return valueArr;
	}

	/**
	 * �������ػ�����ֵ
	 * 
	 * @param srcObjectValue
	 *            Դ���󣻷ǿ�
	 * @param destObjectValue
	 *            Ŀ����󣻷ǿ�
	 * @param key
	 *            ���������ǿ�
	 * @param localeArr
	 *            ���ػ����飻����ǿգ�Ԫ�طǿ�
	 */
	public static void copyLocalValue(IObjectValue srcObjectValue, IObjectValue destObjectValue, String key, Locale[] localeArr) {
		Locale locale = null;
		Object srcValue = null;
		for (int i = 0, length = localeArr.length; i < length; i++) {
			locale = localeArr[i];
			srcValue = srcObjectValue.get(key, locale);

			destObjectValue.put(key, srcValue, locale);
		}
	}
}
