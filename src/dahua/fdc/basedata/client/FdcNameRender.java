/**
 * @(#)FdcNameRender.java 1.0 2014-11-4
 * @author 王正
 * @email SkyIter@live.com
 * Copyright 2014 Kingdee, Inc. All rights reserved
 */

package com.kingdee.eas.fdc.basedata.client;

/**
 * 描述：房地产 名称渲染类
 * <p>
 * 列存放的是一个对象，但是只显示名称
 * 
 * @author 王正
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
