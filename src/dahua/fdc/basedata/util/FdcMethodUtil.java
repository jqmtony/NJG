/**
 * @(#)FdcMethodUtil.java 1.0 2013-11-14
 * @author ����
 * @email SkyIter@live.com
 * Copyright 2013 Kingdee, Inc. All rights reserved
 */

package com.kingdee.eas.fdc.basedata.util;

import java.lang.reflect.Method;

/**
 * ���������ز�����������
 * 
 * @author ����
 * @email SkyIter@live.com
 * @createDate 2013-11-14
 * @version 1.0, 2013-11-14
 * @see
 * @since JDK1.4
 */
public class FdcMethodUtil {

	/**
	 * ���÷���
	 * 
	 * @param object
	 *            ����
	 * @param methodName
	 *            ��������
	 * @param parameterTypes
	 *            ��������
	 * @param args
	 *            ����
	 * @param isIncludeNotPublic
	 *            �Ƿ�����ǹ����ķ���
	 * @return
	 */
	public static Object invokeMethod(Object object, String methodName, Class[] parameterTypes, Object[] args,
			boolean isIncludeNotPublic) {
		Object rs = null;

		Method method = FdcReflectUtil.getMethod(object.getClass(), methodName, parameterTypes, isIncludeNotPublic);
		if (null != method) {
			boolean flag = method.isAccessible();

			if (!flag) {
				method.setAccessible(true);
			}
			try {
				rs = method.invoke(object, args);
			} catch (Exception e) {
				e.printStackTrace();
			}

			method.setAccessible(flag);
		}

		return rs;
	}

	/**
	 * �����޲�������
	 * 
	 * @param object
	 *            ����
	 * @param methodName
	 *            ��������
	 * @param isIncludeNotPublic
	 *            �Ƿ�����ǹ����ķ���
	 * @return
	 */
	public static Object invokeNoParameterMethod(Object object, String methodName, boolean isIncludeNotPublic) {
		return invokeMethod(object, methodName, null, null, isIncludeNotPublic);
	}

	/**
	 * ���þ�̬����
	 * 
	 * @param classz
	 *            ������
	 * @param methodName
	 *            ��������
	 * @param parameterTypes
	 *            ��������
	 * @param args
	 *            ����
	 * @param isIncludeNotPublic
	 *            �Ƿ�����ǹ����ķ���
	 * @return
	 */
	public static Object invokeStaticMethod(Class classz, String methodName, Class[] parameterTypes, Object[] args,
			boolean isIncludeNotPublic) {
		Object rs = null;

		Method method = FdcReflectUtil.getMethod(classz, methodName, parameterTypes, isIncludeNotPublic);
		if (null != method) {
			boolean flag = method.isAccessible();

			if (!flag) {
				method.setAccessible(true);
			}
			try {
				rs = method.invoke(null, args);
			} catch (Exception e) {
				e.printStackTrace();
			}

			method.setAccessible(flag);
		}

		return rs;
	}

	/**
	 * �����޲�����̬����
	 * 
	 * @param classz
	 *            ������
	 * @param methodName
	 *            ��������
	 * @param isIncludeNotPublic
	 *            �Ƿ�����ǹ����ķ���
	 * @return
	 */
	public static Object invokeNoParameterStaticMethod(Class classz, String methodName, boolean isIncludeNotPublic) {
		return invokeStaticMethod(classz, methodName, null, null, isIncludeNotPublic);
	}
}
