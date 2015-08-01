/**
 * @(#)FdcEditUIComponent.java 1.0 2014-3-19
 * @author 王正
 * @email SkyIter@live.com
 * Copyright 2014 Kingdee, Inc. All rights reserved
 */

package com.kingdee.eas.fdc.basedata;

import com.kingdee.eas.framework.client.EditUI;

/**
 * 描述：房地产编辑界面UI组件
 * 
 * @author 王正
 * @email SkyIter@live.com
 * @createDate 2014-3-19
 * @version 1.0, 2014-3-19
 * @see
 * @since JDK1.4
 */
public class FdcEditUIComponent extends FdcUIComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8493991391527985865L;

	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * EistUI基类
	 */
	protected EditUI editUI;

	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////

	public EditUI getEditUI() {
		return editUI;
	}

	public void setEditUI(EditUI editUI) {
		this.editUI = editUI;
	}

}
