/**
 * @(#)FdcFileUtil.java 1.0 2013-9-28
 * Copyright 2013 Kingdee, Inc. All rights reserved
 */

package com.kingdee.eas.fdc.basedata.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;

/**
 * ���ز� �ļ�������
 * 
 * @author ����
 * @email SkyIter@live.com
 * @date 2013-9-28
 * @see
 * @since JDK1.4
 */
public class FdcFileUtil {
	/**
	 * ��������
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static FileInputStream openInputStream(File file) throws IOException {
		if (file.exists()) {
			if (file.isDirectory()) {
				throw new IOException("File '" + file + "' exists but is a directory");
			}
			if (file.canWrite() == false) {
				throw new IOException("File '" + file + "' cannot be written to");
			}
		} else {
			File parent = file.getParentFile();
			if (parent != null && parent.exists() == false) {
				if (parent.mkdirs() == false) {
					throw new IOException("File '" + file + "' could not be created");
				}
			}
		}

		return new FileInputStream(file);
	}

	/**
	 * �������
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static FileOutputStream openOutputStream(File file) throws IOException {
		if (file.exists()) {
			if (file.isDirectory()) {
				throw new IOException("File '" + file + "' exists but is a directory");
			}
			if (file.canWrite() == false) {
				throw new IOException("File '" + file + "' cannot be written to");
			}
		} else {
			File parent = file.getParentFile();
			if (parent != null && parent.exists() == false) {
				if (parent.mkdirs() == false) {
					throw new IOException("File '" + file + "' could not be created");
				}
			}
		}

		return new FileOutputStream(file);
	}

	/**
	 * ���ļ����ַ���
	 * 
	 * @param file
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	public static String readFileToString(File file, String encoding) throws IOException {
		InputStream in = null;
		try {
			in = openInputStream(file);
			return FdcIOUtil.toString(in, encoding);
		} finally {
			FdcIOUtil.closeQuietly(in);
		}
	}

	/**
	 * ���ļ����ַ���
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String readFileToString(File file) throws IOException {
		return readFileToString(file, null);
	}

	/**
	 * д�ַ������ļ���
	 * 
	 * @param file
	 * @param data
	 * @param encoding
	 * @throws IOException
	 */
	public static void writeStringToFile(File file, String data, String encoding) throws IOException {
		OutputStream out = null;
		try {
			out = openOutputStream(file);
			FdcIOUtil.write(data, out, encoding);
		} finally {
			FdcIOUtil.closeQuietly(out);
		}
	}

	/**
	 * д�ַ������ļ���
	 * 
	 * @param file
	 * @param data
	 * @throws IOException
	 */
	public static void writeStringToFile(File file, String data) throws IOException {
		writeStringToFile(file, data, null);
	}

	/**
	 * ���ж��ļ���List��
	 * 
	 * @param file
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	public static List readLines(File file, String encoding) throws IOException {
		InputStream in = null;
		try {
			in = openInputStream(file);
			return FdcIOUtil.readLines(in, encoding);
		} finally {
			FdcIOUtil.closeQuietly(in);
		}
	}

	/**
	 * ���ж��ļ���List��
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static List readLines(File file) throws IOException {
		return readLines(file, null);
	}

	/**
	 * ����д�뵽�ļ���
	 * 
	 * @param file
	 * @param encoding
	 * @param lines
	 * @throws IOException
	 */
	public static void writeLines(File file, String encoding, Collection lines) throws IOException {
		writeLines(file, encoding, lines, null);
	}

	/**
	 * ����д�뵽�ļ���
	 * 
	 * @param file
	 * @param lines
	 * @throws IOException
	 */
	public static void writeLines(File file, Collection lines) throws IOException {
		writeLines(file, null, lines, null);
	}

	/**
	 * ����д�뵽�ļ���
	 * 
	 * @param file
	 * @param encoding
	 * @param lines
	 * @param lineEnding
	 * @throws IOException
	 */
	public static void writeLines(File file, String encoding, Collection lines, String lineEnding) throws IOException {
		OutputStream out = null;
		try {
			out = openOutputStream(file);
			FdcIOUtil.writeLines(lines, lineEnding, out, encoding);
		} finally {
			FdcIOUtil.closeQuietly(out);
		}
	}

	/**
	 * ����д�뵽�ļ���
	 * 
	 * @param file
	 * @param lines
	 * @param lineEnding
	 * @throws IOException
	 */
	public static void writeLines(File file, Collection lines, String lineEnding) throws IOException {
		writeLines(file, null, lines, lineEnding);
	}
}
