/**
 * @(#)FdcNameRender.java 1.0 2014-11-4
 * @author ����
 * @email SkyIter@live.com
 * Copyright 2014 Kingdee, Inc. All rights reserved
 */

package com.kingdee.eas.fdc.basedata.client;

/**
 * ���������ز� ������Ⱦ��
 * <p>
 * �д�ŵ���һ�����󣬵���ֻ��ʾ����
 * 
 * @author ����
 * @email SkyIter@live.com
 * @createDate 2014-11-4
 * @version 1.0, 2014-11-4
 * @see
 * @since JDK1.4
 */
public class FdcNameRender extends FdcBaseRender {

	static FdcNameRender render;

	private FdcNameRender(String style) {
		super(style);
	}

	public static FdcNameRender getInstance() {
		if (render == null) {
			render = new FdcNameRender("$name$");
		}

		return render;
	}
	
}
