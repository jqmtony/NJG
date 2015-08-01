/**
 * @(#)FdcListUIComponent.java 1.0 2014-3-19
 * @author 王正
 * @email SkyIter@live.com
 * Copyright 2014 Kingdee, Inc. All rights reserved
 */

package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.eas.base.uiframe.SysMenuItemInfo;
import com.kingdee.eas.framework.client.ListUI;

/**
 * 描述：房地产序时簿界面UI组件
 * 
 * @author 王正
 * @email SkyIter@live.com
 * @createDate 2014-3-19
 * @version 1.0, 2014-3-19
 * @see
 * @since JDK1.4
 */
public class FdcListUIComponent extends FdcUIComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8000972206181886577L;

	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * ListUI基类
	 */
	protected ListUI listUI;

	/**
	 * 主Query的主键
	 */
	protected IMetaDataPK mainQueryPK;

	/**
	 * 套打Query的主键
	 */
	protected IMetaDataPK printQueryPK;

	/**
	 * 系统菜单项
	 */
	protected SysMenuItemInfo sysMenuItemInfo;

	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////

	public ListUI getListUI() {
		return listUI;
	}

	public void setListUI(ListUI listUI) {
		this.listUI = listUI;
	}

	public IMetaDataPK getMainQueryPK() {
		return mainQueryPK;
	}

	public void setMainQueryPK(IMetaDataPK mainQueryPK) {
		this.mainQueryPK = mainQueryPK;
	}

	public IMetaDataPK getPrintQueryPK() {
		return printQueryPK;
	}

	public void setPrintQueryPK(IMetaDataPK printQueryPK) {
		this.printQueryPK = printQueryPK;
	}

	public SysMenuItemInfo getSysMenuItemInfo() {
		return sysMenuItemInfo;
	}

	public void setSysMenuItemInfo(SysMenuItemInfo sysMenuItemInfo) {
		this.sysMenuItemInfo = sysMenuItemInfo;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////

}
