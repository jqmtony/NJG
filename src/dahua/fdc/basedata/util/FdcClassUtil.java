/**
 * @(#)FdcClassUtil.java 1.0 2013-9-28
 * Copyright 2013 Kingdee, Inc. All rights reserved
 */

package com.kingdee.eas.fdc.basedata.util;

/**
 * ���ز� �๤����
 * 
 * @author ����
 * @email SkyIter@live.com
 * @date 2013-9-28
 * @see
 * @since JDK1.4
 */
public class FdcClassUtil {

	/**
	 * ȡ�ü�����
	 * 
	 * @param classz
	 * @return
	 */
	public static String getSimpleName(Class classz) {
		String simpleName = null;

		String className = classz.getName();
		int lastIndex = className.lastIndexOf(".");
		simpleName = className.substring(lastIndex + 1);

		return simpleName;
	}

	/**
	 * ȡ�ü�����
	 * 
	 * @param className
	 * @return
	 */
	public static String getSimpleName(String className) {
		String simpleName = null;

		int lastIndex = className.lastIndexOf(".");
		simpleName = className.substring(lastIndex + 1);

		return simpleName;
	}

}
