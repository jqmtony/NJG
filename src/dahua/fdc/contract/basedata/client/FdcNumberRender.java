/**
 * @(#)FdcNumberRender.java 1.0 2014-6-6
 * @author 王正
 * @email SkyIter@live.com
 * Copyright 2014 Kingdee, Inc. All rights reserved
 */

package com.kingdee.eas.fdc.basedata.client;

/**
 * 描述：房地产 编码渲染类
 * <p>
 * 列存放的是一个对象，但是只显示编码
 * 
 * @author 王正
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
