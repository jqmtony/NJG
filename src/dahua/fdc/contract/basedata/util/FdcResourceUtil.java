/**
 * @(#)FdcResourceUtil.java 1.0 2014-2-13
 * @author ����
 * @email SkyIter@live.com
 * Copyright 2014 Kingdee, Inc. All rights reserved
 */

package com.kingdee.eas.fdc.basedata.util;

import java.io.File;

/**
 * ���������ز���Դ������
 * 
 * @author ����
 * @email SkyIter@live.com
 * @createDate 2014-2-13
 * @version 1.0, 2014-2-13
 * @see
 * @since JDK1.4
 */
public class FdcResourceUtil {

	/**
	 * ������ȡ���û�Ŀ¼
	 * 
	 * @return
	 * @author RD_skyiter_wang
	 * @createDate 2014-2-13
	 */
	public static String getUserDir() {
		return System.getProperty("user.dir");
	}

	/**
	 * ������ȡ�õ�ǰ��.class�ļ���URIĿ¼���������Լ�
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
	 * ������ȡ��ClassLoader�ľ���·��
	 * 
	 * @param classz
	 * @return
	 * @author RD_skyiter_wang
	 * @createDate 2014-2-13
	 */
	public static String getClassLoaderAbsolutePath() {
		// ȡ�õ�ǰ��.class�ļ���URIĿ¼���������Լ�
		// System.out.println(FdcResourceUtil.class.getResource("").getPath());
		// ȡ�õ�ǰ��classpath�ľ���·��
		// System.out.println(FdcResourceUtil.class.getResource("/").getPath());
		// ȡ�õ�ǰ��classpath�ľ���·��
		//System.out.println(Thread.currentThread().getContextClassLoader().getResource("").getPath(
		// ));
		// ȡ�õ�ǰ��classpath�ľ���·��
		// System.out.println(FdcResourceUtil.class.getClassLoader().getResource("").getPath());
		// ȡ�õ�ǰ��classpath�ľ���·��
		// System.out.println(ClassLoader.getSystemResource("").getPath());
		// System.out.println(new File("").getAbsolutePath());

		// ȡ��ClassLoader�ľ���·��
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
