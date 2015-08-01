/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.*;
import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.contract.ConNoCostSplitCollection;
import com.kingdee.eas.fdc.contract.ConNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ConNoCostSplitInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitInfo;
import com.kingdee.eas.fdc.contract.ContractSettlementBillCollection;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillInfo;
import com.kingdee.eas.fdc.finance.client.ContractPayPlanEditUI;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class CostAcctFullInfoUI extends AbstractCostAcctFullInfoUI {
	public static final String resourcePath = "com.kingdee.eas.fdc.contract.client.ContractFullResource";

	/**
	 * output class constructor
	 */
	public CostAcctFullInfoUI() throws Exception {
		super();
	}

	private String getNoSplitId(String contractId) throws BOSException {
		ConNoCostSplitCollection contractCostSplitCollection = ConNoCostSplitFactory
				.getRemoteInstance().getConNoCostSplitCollection(
						"select id where contractBill='" + contractId + "'");
		if (contractCostSplitCollection.size() > 0) {
			ConNoCostSplitInfo info = contractCostSplitCollection.get(0);
			return info.getId().toString();
		}
		return null;
	}

	private String getSplitId(String contractId) throws BOSException {
		ContractCostSplitCollection contractCostSplitCollection = ContractCostSplitFactory
				.getRemoteInstance().getContractCostSplitCollection(
						"select id where contractBill='" + contractId + "'");
		if (contractCostSplitCollection.size() > 0) {
			ContractCostSplitInfo info = contractCostSplitCollection.get(0);
			return info.getId().toString();
		}
		return null;
	}

	private String getSettleId(String contractId) throws BOSException {
		ContractSettlementBillCollection settles = ContractSettlementBillFactory
				.getRemoteInstance().getContractSettlementBillCollection(
						"select id where contractBill.Id='" + contractId + "'");
		if (settles.size() > 0) {
			ContractSettlementBillInfo info = settles.get(0);
			return info.getId().toString();
		}
		return null;
	}

	public void loadFields() {
		super.loadFields();
	}

	private void addPanel(String id, String uiClass, String title)
			throws UIException {
		if (id == null) {
			return;
		}
		UIContext uiContext = new UIContext(ui);
		uiContext.put(UIContext.ID, id);
		CoreUIObject plUI = null;
		plUI = (CoreUIObject) UIFactoryHelper.initUIObject(uiClass, uiContext,
				null, "VIEW");
		this.plContrastFullInfo.add(plUI, title);
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	public void onLoad() throws Exception {
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionSave.setVisible(false);
		this.actionSubmit.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.menuSubmitOption.setVisible(false);
		actionPrint.setVisible(false);
		actionPrintPreview.setVisible(false);
		actionPre.setVisible(false);
		actionFirst.setVisible(false);
		actionLast.setVisible(false);
		actionNext.setVisible(false);
		super.onLoad();
		plContrastFullInfo.removeAll();
		String acctId = (String) this.getUIContext().get(UIContext.ID);
		addPanel(acctId, CostAcctDyFullInfoUI.class.getName(), this
				.getResouce("settleInfo"));
	}

	private String getResouce(String resName) {
		return EASResource.getString(resourcePath, resName);
	}
	/**
	 * 根据id显示窗体
	 */
	public static boolean showDialogWindows(IUIObject ui, String id)
			throws UIException {
		UIContext uiContext = new UIContext(ui);
		uiContext.put(UIContext.ID, id);
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(ContractFullInfoUI.class.getName(), uiContext, null,
						"VIEW");
		uiWindow.show();
		return true;
	}

	protected IObjectValue createNewData() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ContractBillFactory.getRemoteInstance();
	}
}