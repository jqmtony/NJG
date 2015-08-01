/**
 * @(#)FdcBaseRender.java 1.0 2014-6-6
 * @author ����
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
 * ���������ز�������Ⱦ��
 * 
 * @author ����
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
		// �ų��յ�������������ܡ��ų�ʹ����EcNumberRender�����F7����
		if (object != null && !(this instanceof com.kingdee.eas.fdc.basedata.client.FdcNumberRender)) {
			// ����IObjectValue�Ļ�(����BigDecimal)�ͰѸ�ʽ��գ�����BOS�ײ���ӡ����̨���棬�ǳ�Ӱ������
			if (!(object instanceof IObjectValue)) {
				setFormat(null);
			}
		}
		
		super.draw(graphics, clip, object, cellStyle, null);
	}
}
