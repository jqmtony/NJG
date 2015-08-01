/**
 * @(#)FdcFieldUtil.java 1.0 2014-3-19
 * @author ����
 * @email SkyIter@live.com
 * Copyright 2014 Kingdee, Inc. All rights reserved
 */

package com.kingdee.eas.fdc.basedata.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * ���������ز��ֶι�����
 * 
 * @author ����
 * @email SkyIter@live.com
 * @createDate 2014-3-19
 * @version 1.0, 2014-3-19
 * @see
 * @since JDK1.4
 */
public class FdcFieldUtil {

	/**
	 * ȡ���ֶ�ֵ
	 * 
	 * @param object
	 * @param field
	 *            �ֶ�
	 * @param isIncludeNotPublic
	 *            �Ƿ�����ǹ������ֶ�
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
	 * �����ֶ�ֵ
	 * 
	 * @param object
	 * @param field
	 *            �ֶ�
	 * @param fieldValue
	 *            �ֶ�ֵ
	 * @param isIncludeNotPublic
	 *            �Ƿ�����ǹ������ֶ�
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
	 * ȡ���ֶ�ֵ
	 * 
	 * @param object
	 * @param fieldName
	 *            �ֶ�����
	 * @param isIncludeNotPublic
	 *            �Ƿ�����ǹ������ֶ�
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
	 * �����ֶ�ֵ
	 * 
	 * @param object
	 * @param fieldName
	 *            �ֶ�����
	 * @param fieldValue
	 *            �ֶ�ֵ
	 * @param isIncludeNotPublic
	 *            �Ƿ�����ǹ������ֶ�
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
