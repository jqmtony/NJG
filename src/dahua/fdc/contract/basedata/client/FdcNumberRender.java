/**
 * @(#)FdcNumberRender.java 1.0 2014-6-6
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
 * @createDate 2014-6-6
 * @version 1.0, 2014-6-6
 * @see
 * @since JDK1.4
 */
public class FdcNumberRender extends FdcBaseRender {

	static FdcNumberRender render;

	private FdcNumberRender(String style) {
		super(style);
	}

	public static FdcNumberRender getInstance() {
		if (render == null) {
			render = new FdcNumberRender("$number$");
		}

		return render;
	}

}
