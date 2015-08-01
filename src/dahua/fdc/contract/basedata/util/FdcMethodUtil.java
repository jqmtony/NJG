/**
 * @(#)FdcMethodUtil.java 1.0 2013-11-14
 * @author 王正
 * @email SkyIter@live.com
 * Copyright 2013 Kingdee, Inc. All rights reserved
 */

package com.kingdee.eas.fdc.basedata.util;

import java.lang.reflect.Method;

/**
 * 描述：房地产方法工具类
 * 
 * @author 王正
 * @email SkyIter@live.com
 * @createDate 2013-11-14
 * @version 1.0, 2013-11-14
 * @see
 * @since JDK1.4
 */
public class FdcMethodUtil {

	/**
	 * 调用方法
	 * 
	 * @param object
	 *            对象
	 * @param methodName
	 *            方法名称
	 * @param parameterTypes
	 *            参数类型
	 * @param args
	 *            参数
	 * @param isIncludeNotPublic
	 *            是否包括非公共的方法
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
	 * 调用无参数方法
	 * 
	 * @param object
	 *            对象
	 * @param methodName
	 *            方法名称
	 * @param isIncludeNotPublic
	 *            是否包括非公共的方法
	 * @return
	 */
	public static Object invokeNoParameterMethod(Object object, String methodName, boolean isIncludeNotPublic) {
		return invokeMethod(object, methodName, null, null, isIncludeNotPublic);
	}

	/**
	 * 调用静态方法
	 * 
	 * @param classz
	 *            类类型
	 * @param methodName
	 *            方法名称
	 * @param parameterTypes
	 *            参数类型
	 * @param args
	 *            参数
	 * @param isIncludeNotPublic
	 *            是否包括非公共的方法
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
	 * 调用无参数静态方法
	 * 
	 * @param classz
	 *            类类型
	 * @param methodName
	 *            方法名称
	 * @param isIncludeNotPublic
	 *            是否包括非公共的方法
	 * @return
	 */
	public static Object invokeNoParameterStaticMethod(Class classz, String methodName, boolean isIncludeNotPublic) {
		return invokeStaticMethod(classz, methodName, null, null, isIncludeNotPublic);
	}
}
