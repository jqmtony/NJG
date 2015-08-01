/**
 * @(#)FdcFieldUtil.java 1.0 2014-3-19
 * @author 王正
 * @email SkyIter@live.com
 * Copyright 2014 Kingdee, Inc. All rights reserved
 */

package com.kingdee.eas.fdc.basedata.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * 描述：房地产字段工具类
 * 
 * @author 王正
 * @email SkyIter@live.com
 * @createDate 2014-3-19
 * @version 1.0, 2014-3-19
 * @see
 * @since JDK1.4
 */
public class FdcFieldUtil {

	/**
	 * 取得字段值
	 * 
	 * @param object
	 * @param field
	 *            字段
	 * @param isIncludeNotPublic
	 *            是否包括非公共的字段
	 * @return
	 */
	public static Object getValue(Object object, Field field, boolean isIncludeNotPublic) {
		Object rs = null;

		if (null != field) {
			boolean flag = field.isAccessible();

			if (!flag) {
				field.setAccessible(true);
			}

			try {
				if (Modifier.isStatic(field.getModifiers())) {
					rs = field.get(null);
				} else {
					rs = field.get(object);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			field.setAccessible(flag);
		}

		return rs;
	}

	/**
	 * 设置字段值
	 * 
	 * @param object
	 * @param field
	 *            字段
	 * @param fieldValue
	 *            字段值
	 * @param isIncludeNotPublic
	 *            是否包括非公共的字段
	 */
	public static void setValue(Object object, Field field, Object fieldValue, boolean isIncludeNotPublic) {
		if (null != field) {
			boolean flag = field.isAccessible();

			if (!flag) {
				field.setAccessible(true);
			}

			try {
				int modifiers = field.getModifiers();

				if (!Modifier.isFinal(modifiers)) {
					if (Modifier.isStatic(modifiers)) {
						field.set(null, fieldValue);
					} else {
						field.set(object, fieldValue);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			field.setAccessible(flag);
		}
	}

	/**
	 * 取得字段值
	 * 
	 * @param object
	 * @param fieldName
	 *            字段名称
	 * @param isIncludeNotPublic
	 *            是否包括非公共的字段
	 * @return
	 */
	public static Object getValue(Object object, String fieldName, boolean isIncludeNotPublic) {
		Object rs = null;

		Field field = FdcReflectUtil.getField(object.getClass(), fieldName, isIncludeNotPublic);
		if (null != field) {
			boolean flag = field.isAccessible();

			if (!flag) {
				field.setAccessible(true);
			}

			try {
				if (Modifier.isStatic(field.getModifiers())) {
					rs = field.get(null);
				} else {
					rs = field.get(object);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			field.setAccessible(flag);
		}

		return rs;
	}

	/**
	 * 设置字段值
	 * 
	 * @param object
	 * @param fieldName
	 *            字段名称
	 * @param fieldValue
	 *            字段值
	 * @param isIncludeNotPublic
	 *            是否包括非公共的字段
	 */
	public static void setValue(Object object, String fieldName, Object fieldValue, boolean isIncludeNotPublic) {
		Field field = FdcReflectUtil.getField(object.getClass(), fieldName, isIncludeNotPublic);
		if (null != field) {
			boolean flag = field.isAccessible();

			if (!flag) {
				field.setAccessible(true);
			}

			try {
				int modifiers = field.getModifiers();

				if (!Modifier.isFinal(modifiers)) {
					if (Modifier.isStatic(modifiers)) {
						field.set(null, fieldValue);
					} else {
						field.set(object, fieldValue);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			field.setAccessible(flag);
		}
	}

}
