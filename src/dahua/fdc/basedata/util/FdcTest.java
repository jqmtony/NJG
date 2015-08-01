/**
 * @(#)FdcTest.java 1.0 2014-11-29
 * @author 王正
 * @email SkyIter@live.com
 * Copyright 2014 Kingdee, Inc. All rights reserved
 */

package com.kingdee.eas.fdc.basedata.util;

/**
 * 描述：房地产测试类
 * 
 * @author 王正
 * @email SkyIter@live.com
 * @createDate 2014-11-29
 * @version 1.0, 2014-11-29
 * @see
 * @since JDK1.4
 */
public class FdcTest {
	
	public static void main(String[] args) {
		String[] arr = new String[4];
		arr[0] = "w:/webservice";
		// arr[1] = "w:/eas/metadata";
		// arr[1] = "w:/apusic/metas";
		// arr[1] = "w:/eas/Server/server/metas";
		arr[1] = "w:/workspace750_0920_750rs/eas_metas/metadata";
		arr[2] = "w:/webservice/java";
		arr[3] = "w:/webservice/wsdl";
		
		com.kingdee.bos.webservice.tool.Generator.main(arr);

	}
	
}
