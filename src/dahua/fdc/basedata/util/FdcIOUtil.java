/**
 * @(#)FdcIOUtil.java 1.0 2013-9-28
 * Copyright 2013 Kingdee, Inc. All rights reserved
 */

package com.kingdee.eas.fdc.basedata.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * 房地产 输入输出工具类
 * 
 * @author 王正
 * @email SkyIter@live.com
 * @date 2013-9-28
 * @see
 * @since JDK1.4
 */
public class FdcIOUtil {

	/**
	 * 默认缓冲区大小.
	 */
	private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

	/**
	 * 行_分割器
	 */
	public static final String LINE_SEPARATOR;

	static {
		StringWriter buf = new StringWriter(4);
		PrintWriter out = new PrintWriter(buf);
		out.println();
		LINE_SEPARATOR = buf.toString();
	}

	/**
	 * 安静地关闭输入流
	 * 
	 * @param input
	 */
	public static void closeQuietly(InputStream input) {
		try {
			if (input != null) {
				input.close();
			}
		} catch (IOException ioe) {
			// ignore
		}
	}

	/**
	 * 安静地关闭输出流
	 * 
	 * @param output
	 */
	public static void closeQuietly(OutputStream output) {
		try {
			if (output != null) {
				output.close();
			}
		} catch (IOException ioe) {
			// ignore
		}
	}

	/**
	 * 安静地关闭Reader
	 * 
	 * @param input
	 */
	public static void closeQuietly(Reader input) {
		try {
			if (input != null) {
				input.close();
			}
		} catch (IOException ioe) {
			// ignore
		}
	}

	/**
	 * 安静地关闭Writer
	 * 
	 * @param output
	 */
	public static void closeQuietly(Writer output) {
		try {
			if (output != null) {
				output.close();
			}
		} catch (IOException ioe) {
			// ignore
		}
	}

	/**
	 * 读取行
	 * 
	 * @param input
	 * @return
	 * @throws IOException
	 */
	public static List readLines(InputStream input) throws IOException {
		InputStreamReader reader = new InputStreamReader(input);

		return readLines(reader);
	}

	/**
	 * 读取行
	 * 
	 * @param input
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	public static List readLines(InputStream input, String encoding) throws IOException {
		if (encoding == null) {
			return readLines(input);
		} else {
			InputStreamReader reader = new InputStreamReader(input, encoding);
			return readLines(reader);
		}
	}

	/**
	 * 读取行
	 * 
	 * @param input
	 * @return
	 * @throws IOException
	 */
	public static List readLines(Reader input) throws IOException {
		BufferedReader reader = new BufferedReader(input);
		List list = new ArrayList();
		String line = reader.readLine();
		while (line != null) {
			list.add(line);
			line = reader.readLine();
		}

		return list;
	}

	/**
	 * 写
	 * 
	 * @param data
	 * @param output
	 * @throws IOException
	 */
	public static void write(String data, OutputStream output) throws IOException {
		if (data != null) {
			output.write(data.getBytes());
		}
	}

	/**
	 * 写
	 * 
	 * @param data
	 * @param output
	 * @param encoding
	 * @throws IOException
	 */
	public static void write(String data, OutputStream output, String encoding) throws IOException {
		if (data != null) {
			if (encoding == null) {
				write(data, output);
			} else {
				output.write(data.getBytes(encoding));
			}
		}
	}

	/**
	 * 写行
	 * 
	 * @param lines
	 * @param lineEnding
	 * @param output
	 * @throws IOException
	 */
	public static void writeLines(Collection lines, String lineEnding, OutputStream output) throws IOException {
		if (lines == null) {
			return;
		}
		if (lineEnding == null) {
			lineEnding = LINE_SEPARATOR;
		}
		for (Iterator it = lines.iterator(); it.hasNext();) {
			Object line = it.next();
			if (line != null) {
				output.write(line.toString().getBytes());
			}
			output.write(lineEnding.getBytes());
		}
	}

	/**
	 * 写行
	 * 
	 * @param lines
	 * @param lineEnding
	 * @param output
	 * @param encoding
	 * @throws IOException
	 */
	public static void writeLines(Collection lines, String lineEnding, OutputStream output, String encoding)
			throws IOException {
		if (encoding == null) {
			writeLines(lines, lineEnding, output);
		} else {
			if (lines == null) {
				return;
			}
			if (lineEnding == null) {
				lineEnding = LINE_SEPARATOR;
			}
			for (Iterator it = lines.iterator(); it.hasNext();) {
				Object line = it.next();
				if (line != null) {
					output.write(line.toString().getBytes(encoding));
				}
				output.write(lineEnding.getBytes(encoding));
			}
		}
	}

	/**
	 * 写行
	 * 
	 * @param lines
	 * @param lineEnding
	 * @param writer
	 * @throws IOException
	 */
	public static void writeLines(Collection lines, String lineEnding, Writer writer) throws IOException {
		if (lines == null) {
			return;
		}
		if (lineEnding == null) {
			lineEnding = LINE_SEPARATOR;
		}
		for (Iterator it = lines.iterator(); it.hasNext();) {
			Object line = it.next();
			if (line != null) {
				writer.write(line.toString());
			}
			writer.write(lineEnding);
		}
	}

	/**
	 * 读取输入流，转换成字符串
	 * 
	 * @param input
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	public static String toString(InputStream input, String encoding) throws IOException {
		StringWriter sw = new StringWriter();
		copy(input, sw, encoding);

		return sw.toString();
	}

	/**
	 * 按照指定编码格式，拷贝输入流到Writer中
	 * 
	 * @param input
	 * @param output
	 * @param encoding
	 * @throws IOException
	 */
	public static void copy(InputStream input, Writer output, String encoding) throws IOException {
		if (encoding == null) {
			copy(input, output);
		} else {
			InputStreamReader in = new InputStreamReader(input, encoding);
			copy(in, output);
		}
	}

	/**
	 * 拷贝输入流到Writer中
	 * 
	 * @param input
	 * @param output
	 * @throws IOException
	 */
	public static void copy(InputStream input, Writer output) throws IOException {
		InputStreamReader in = new InputStreamReader(input);
		copy(in, output);
	}

	/**
	 * 拷贝Reader到Writer中
	 * 
	 * @param input
	 * @param output
	 * @return
	 * @throws IOException
	 */
	public static int copy(Reader input, Writer output) throws IOException {
		long count = copyLarge(input, output);
		if (count > Integer.MAX_VALUE) {
			return -1;
		}

		return (int) count;
	}

	/**
	 * 拷贝大Reader到Writer中
	 * 
	 * @param input
	 * @param output
	 * @return
	 * @throws IOException
	 */
	public static long copyLarge(Reader input, Writer output) throws IOException {
		char[] buffer = new char[DEFAULT_BUFFER_SIZE];
		long count = 0;
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}

		return count;
	}
}
