/*
 * @(#)FdcObjectValueUtil.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.fdc.basedata.util;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.util.Assert;

/**
 * 房地产 对象值工具类
 * 
 * @author 王正
 * @email SkyIter@live.com
 * @date 2013-9-9
 * @see
 * @since 1.4
 */
public class FdcObjectValueUtil {
	
	/**
	 * 取得属性值
	 * <p>
	 * 1、支持级联属性<br>
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
			// 不为空，且还有下级
			else if ((len - 1) != i) {
				tempObjectValue = (IObjectValue) value;
			}
		}

		return value;
	}

	// ///////////////////////////////////////////////////////////////////////////////////////
	// Locale ：本地化
	// ///////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 设置本地化属性值
	 * 
	 * @param objectValue
	 *            对象；非空
	 * @param key
	 *            属性名；非空
	 * @param valueArr
	 *            属性值数组；数组非空，元素可空
	 * @param localeArr
	 *            本地化数组；数组非空，元素非空
	 */
	public static void setLocalValue(IObjectValue objectValue, String key, Object[] valueArr, Locale[] localeArr) {
		int valueArrLen = valueArr.length;
		int localeArrLen = localeArr.length;
		boolean cond = valueArrLen == localeArrLen;
		String description = "属性值数组valueArr长度为" + valueArrLen + "，本地化数组localeArr的长度为" + localeArrLen + "，不一致 ";
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
	 * 取得本地化属性值Map
	 * <p>
	 * 其中key存放本地化实例，value存放属性值
	 * 
	 * @param objectValue
	 *            对象；非空
	 * @param key
	 *            属性名；非空
	 * @param localeArr
	 *            本地化数组；数组非空，元素非空
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
	 * 取得本地化属性值数组
	 * <p>
	 * 其中key存放本地化实例，value存放属性值
	 * 
	 * @param objectValue
	 *            对象；非空
	 * @param key
	 *            属性名；非空
	 * @param localeArr
	 *            本地化数组；数组非空，元素非空
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
	 * 拷贝本地化属性值
	 * 
	 * @param srcObjectValue
	 *            源对象；非空
	 * @param destObjectValue
	 *            目标对象；非空
	 * @param key
	 *            属性名；非空
	 * @param localeArr
	 *            本地化数组；数组非空，元素非空
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
