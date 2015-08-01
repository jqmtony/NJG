/**
 * @(#)FdcResourceUtil.java 1.0 2014-2-13
 * @author 王正
 * @email SkyIter@live.com
 * Copyright 2014 Kingdee, Inc. All rights reserved
 */

package com.kingdee.eas.fdc.basedata.util;

import java.io.File;

/**
 * 描述：房地产资源工具类
 * 
 * @author 王正
 * @email SkyIter@live.com
 * @createDate 2014-2-13
 * @version 1.0, 2014-2-13
 * @see
 * @since JDK1.4
 */
public class FdcResourceUtil {

	/**
	 * 描述：取得用户目录
	 * 
	 * @return
	 * @author RD_skyiter_wang
	 * @createDate 2014-2-13
	 */
	public static String getUserDir() {
		return System.getProperty("user.dir");
	}

	/**
	 * 描述：取得当前类.class文件的URI目录，不包括自己
	 * 
	 * @param classz
	 * @return
	 * @author RD_skyiter_wang
	 * @createDate 2014-2-13
	 */
	public static String getClassFilePath(Class classz) {
		String path = classz.getResource("").getPath();

		return path;
	}

	/**
	 * 描述：取得ClassLoader的绝对路径
	 * 
	 * @param classz
	 * @return
	 * @author RD_skyiter_wang
	 * @createDate 2014-2-13
	 */
	public static String getClassLoaderAbsolutePath() {
		// 取得当前类.class文件的URI目录，不包括自己
		// System.out.println(FdcResourceUtil.class.getResource("").getPath());
		// 取得当前的classpath的绝对路径
		// System.out.println(FdcResourceUtil.class.getResource("/").getPath());
		// 取得当前的classpath的绝对路径
		//System.out.println(Thread.currentThread().getContextClassLoader().getResource("").getPath(
		// ));
		// 取得当前的classpath的绝对路径
		// System.out.println(FdcResourceUtil.class.getClassLoader().getResource("").getPath());
		// 取得当前的classpath的绝对路径
		// System.out.println(ClassLoader.getSystemResource("").getPath());
		// System.out.println(new File("").getAbsolutePath());

		// 取得ClassLoader的绝对路径
		String path = new File("").getAbsolutePath();

		return path;
	}

	public static void main(String[] args) throws Exception {
		String path = new File("").getAbsolutePath();
		File file = new File(path);

		System.out.println(file.getPath());
		System.out.println(file.getCanonicalPath());
		System.out.println(file.toURL());
		System.out.println(file.getParent());
		System.out.println(file.getParentFile().getParentFile().getPath());

		System.out.println(getClassFilePath(FdcResourceUtil.class));
	}
}
