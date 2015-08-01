/**
 * @(#)FdcBooleanUtil.java 1.0 2014-12-2
 * @author ����
 * @email SkyIter@live.com
 * Copyright 2014 Kingdee, Inc. All rights reserved
 */

package com.kingdee.eas.fdc.basedata.util;

import org.apache.commons.lang.BooleanUtils;

/**
 * ���������ز� ����������
 * 
 * @author ����
 * @email SkyIter@live.com
 * @createDate 2014-12-2
 * @version 1.0, 2014-12-2
 * @see
 * @since JDK1.4
 */
public class FdcBooleanUtil extends BooleanUtils {

	/**
	 * ������ת���ɲ�������
	 * 
	 * @param str
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-12-2
	 */
	public static Boolean toBooleanObject(String str) {
		if (null == str) {
			return null;
		}

		Boolean flag = BooleanUtils.toBooleanObject(str);
		if (null != flag) {
			return flag;
		}

		if ("t".equalsIgnoreCase(str))
			return Boolean.TRUE;
		if ("f".equalsIgnoreCase(str))
			return Boolean.FALSE;
		if ("y".equalsIgnoreCase(str))
			return Boolean.TRUE;
		if ("n".equalsIgnoreCase(str))
			return Boolean.FALSE;
		if ("1".equalsIgnoreCase(str))
			return Boolean.TRUE;
		if ("0".equalsIgnoreCase(str)) {
			return Boolean.FALSE;
		}

		return null;
	}
}
