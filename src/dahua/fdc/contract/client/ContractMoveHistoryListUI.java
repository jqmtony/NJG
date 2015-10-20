/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.framework.ICoreBase;

/**
 * 合同交底历史记录列表界面
 */
public class ContractMoveHistoryListUI extends
		AbstractContractMoveHistoryListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ContractMoveHistoryListUI.class);

	/**
	 * output class constructor
	 */
	public ContractMoveHistoryListUI() throws Exception {
		super();
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		// super.tblMain_tableClicked(e);
	}

	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	protected String getEditUIName() {
		// TODO Auto-generated method stub
		return null;
	}

	protected boolean isIgnoreCUFilter() {

		return true;
	}

	protected void beforeExcutQuery(EntityViewInfo ev) {
		String contractID = (String) getUIContext().get(UIContext.ID);
		FilterInfo filter = new FilterInfo();

		filter.getFilterItems().add(
				new FilterItemInfo("contractBillID", contractID));
		if (ev.getFilter() == null) {
			ev.setFilter(filter);
		} else {
			try {

				ev.getFilter().mergeFilter(filter, "and");
			} catch (BOSException e) {
				handUIExceptionAndAbort(e);
			}
		}
		super.beforeExcutQuery(ev);
	}
}