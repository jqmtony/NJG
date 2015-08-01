/*
 * @(#)KDContractBizPromptBox.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.fdc.finance.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;

/**
 * 描述:
 * @author zhaoqin  date:2013-7-24
 * @version EAS6.1
 */
public class KDContractBizPromptBox extends KDBizPromptBox {
	private static final Logger logger = Logger.getLogger("com.kingdee.eas.fdc.finance.client.KDContractBizPromptBox");

	/**
	 * 描述：构造函数
	 */
	public KDContractBizPromptBox() {
		super();
	}

	/**
	 * @see com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox#configSelector(java.lang.Object)
	 */
	protected void configSelector(Object param) {
		if (selector instanceof KDContractPromptDialog) {
			KDContractPromptDialog dlg = (KDContractPromptDialog) selector;
			if (isRefresh()) {
				dlg.setQueryInfo(queryAgent.getQueryInfo());
				dlg.setEntityViewInfo(queryAgent.getRuntimeEntityView());
				dlg.setSelectorCollection(queryAgent.getSelectorCollection());
				dlg.setQueryExecutor(queryAgent.getQueryExecutor());
				setRefresh(false);
			}
			//dlg.setEntityViewInfo(queryAgent.getRuntimeEntityView());
			dlg.setMainOrgContext(queryAgent.getMainOrgContext());
			dlg.setMainBizOrgsType(getMainBizOrgsType());
			dlg.setMainBizOrgs(this.getMainBizOrgs());
			dlg.setCurrentMainBizOrgUnit(this.getCurrentMainBizOrgUnit());
			dlg.setEnabledMultiSelection(queryAgent.isEnabledMultiSelection());
			dlg.setReturnValueType(queryAgent.getReturnValueType());
			dlg.setUseCacheObject(isUseCacheObject());
			dlg.setEnableToMaintainBizdata(getEnableToMaintainBizdata());
			dlg.setIsDefaultFilterFieldsEnabled(getIsDefaultFilterFieldsEnabled());
			dlg.addCommonF7KDTableListener(getCommonF7KDTableListener());
			dlg.setHasHideQuichFilterPanel(this.getIsHideQuichFilterPanel());
			dlg.setLastData(this.getData());
			dlg.setMergeColumnKeys(this.getMergeColumnKeys());
			dlg.setPrecisionColumns(this.getPrecisionColumns());
			dlg.setPrecisionDisplayColumns(this.getPrecisionDisplayColumns());
		}
	}
}
