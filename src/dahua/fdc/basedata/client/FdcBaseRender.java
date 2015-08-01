/**
 * @(#)FdcBaseRender.java 1.0 2014-6-6
 * @author 王正
 * @email SkyIter@live.com
 * Copyright 2014 Kingdee, Inc. All rights reserved
 */

package com.kingdee.eas.fdc.basedata.client;

import java.awt.Graphics;
import java.awt.Shape;

import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Style;
import com.kingdee.bos.dao.IObjectValue;

/**
 * 描述：房地产基础渲染类
 * 
 * @author 王正
 * @email SkyIter@live.com
 * @createDate 2014-6-6
 * @version 1.0, 2014-6-6
 * @see
 * @since JDK1.4
 */
public class FdcBaseRender extends ObjectValueRender {

	public FdcBaseRender(IDataFormat format) {
		super();
		setFormat(format);
	}

	public FdcBaseRender(String style) {
		super();
		setFormat(new BizDataFormat(style));
	}

	public void draw(Graphics graphics, Shape clip, Object object, Style cellStyle, Object extObject) {
		// 排除空的情况以提升性能、排除使用了EcNumberRender子类的F7对象
		if (object != null && !(this instanceof com.kingdee.eas.fdc.basedata.client.FdcNumberRender)) {
			// 不是IObjectValue的话(例如BigDecimal)就把格式清空，否则BOS底层会打印控制台警告，非常影响性能
			if (!(object instanceof IObjectValue)) {
				setFormat(null);
			}
		}
		
		super.draw(graphics, clip, object, cellStyle, null);
	}
}
