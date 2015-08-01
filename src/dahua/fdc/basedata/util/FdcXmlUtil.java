/**
 * @(#)FdcXmlUtil.java 1.0 2014-3-19
 * @author ����
 * @email SkyIter@live.com
 * Copyright 2014 Kingdee, Inc. All rights reserved
 */

package com.kingdee.eas.fdc.basedata.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

/**
 * ���������ز�Xml������
 * 
 * @author ����
 * @email SkyIter@live.com
 * @createDate 2014-3-19
 * @version 1.0, 2014-3-19
 * @see
 * @since JDK1.4
 */
public class FdcXmlUtil {

	/**
	 * ��XML
	 * 
	 * @param fileName
	 * @return
	 */
	public static Document read(String fileName) {
		Document document = null;

		FileInputStream fis = null;
		SAXBuilder builder = null;
		try {
			fis = new FileInputStream(fileName);
			builder = new SAXBuilder();
			document = builder.build(fis);
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			FdcIOUtil.closeQuietly(fis);
		}

		return document;
	}

	/**
	 * дXML
	 * 
	 * @param document
	 * @param fileName
	 */
	public static void write(Document document, String fileName) {
		FileOutputStream fos = null;
		XMLOutputter outter = null;
		try {
			fos = new FileOutputStream(fileName);
			// outter = new XMLOutputter("\t", true, "UTF-8");
			// ��4�ո�����, ����, UTF-8����
			outter = new XMLOutputter("    ", true, "UTF-8");
			outter.setTrimAllWhite(true);
			outter.output(document, fos);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			FdcIOUtil.closeQuietly(fos);
		}
	}

}
