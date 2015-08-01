/**
 * @(#)FdcClassUtil.java 1.0 2013-9-28
 * Copyright 2013 Kingdee, Inc. All rights reserved
 */

package com.kingdee.eas.fdc.basedata.util;

/**
 * 房地产 类工具类
 * 
 * @author 王正
 * @email SkyIter@live.com
 * @date 2013-9-28
 * @see
 * @since JDK1.4
 */
public class FdcClassUtil {

	/**
	 * 取得简单类名
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
	 * 取得简单类名
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
