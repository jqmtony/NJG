/**
 * output package name
 */
package com.kingdee.eas.port.equipment.operate.client;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.port.equipment.operate.ComproductionInfo;
import com.kingdee.eas.scm.common.client.SCMClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.eas.xr.app.XRBillStatusEnum;
import com.kingdee.eas.xr.helper.Tool;
import com.kingdee.eas.xr.helper.XRSQLBuilder;
import com.kingdee.eas.xr.helper.common.FDCUIWeightWorker;
import com.kingdee.eas.xr.helper.common.IFDCWork;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class ComproductionEditUI extends AbstractComproductionEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ComproductionEditUI.class);

	/**
	 * output class constructor
	 */
	public ComproductionEditUI() throws Exception {
		super();
	}

	/**
	 * output loadFields method
	 */
	public void loadFields() {
		// 注销监听器
		detachListeners();
		super.loadFields();
		// 最后加上监听器
		attachListeners();
		KDTMergeManager mm = this.kdtEntrys.getMergeManager();
		if (this.kdtEntrys.getRowCount() > 0) {
			mm.mergeBlock(0, 1, 10, 1);
			mm.mergeBlock(11, 1, 21, 1);
		}

		for (int i = 11; i < this.kdtEntrys.getRowCount(); i++) {
			this.kdtEntrys.getRow(i).getStyleAttributes().setLocked(true);
			this.kdtEntrys.getRow(i).getStyleAttributes().setBackground(
					Color.yellow);
		}
	}

	protected void initWorkButton() {
		super.initWorkButton();
		btnAudit.setIcon(EASResource.getIcon("imgTbtn_auditing"));
		btnUnAudit.setIcon(EASResource.getIcon("imgTbtn_fauditing"));
	}

	private void attachListeners() {
		addDataChangeListener(this.prmtreportingUnit);
		addDataChangeListener(this.pkBizDate);
	}

	private void detachListeners() {
		removeDataChangeListener(this.prmtreportingUnit);
		removeDataChangeListener(this.pkBizDate);
	}

	protected Map listenersMap = new HashMap();

	protected void addDataChangeListener(JComponent com) {
		EventListener[] listeners = (EventListener[]) listenersMap.get(com);
		if (listeners != null && listeners.length > 0) {
			if (com instanceof KDPromptBox) {
				for (int i = 0; i < listeners.length; i++) {
					((KDPromptBox) com)
							.addDataChangeListener((DataChangeListener) listeners[i]);
				}
			} else if (com instanceof KDFormattedTextField) {
				for (int i = 0; i < listeners.length; i++) {
					((KDFormattedTextField) com)
							.addDataChangeListener((DataChangeListener) listeners[i]);
				}
			} else if (com instanceof KDDatePicker) {
				for (int i = 0; i < listeners.length; i++) {
					((KDDatePicker) com)
							.addDataChangeListener((DataChangeListener) listeners[i]);
				}
			} else if (com instanceof KDComboBox) {
				for (int i = 0; i < listeners.length; i++) {
					((KDComboBox) com)
							.addItemListener((ItemListener) listeners[i]);
				}
			}
		}

	}

	protected void removeDataChangeListener(JComponent com) {
		EventListener[] listeners = null;
		if (com instanceof KDPromptBox) {
			listeners = com.getListeners(DataChangeListener.class);
			for (int i = 0; i < listeners.length; i++) {
				((KDPromptBox) com)
						.removeDataChangeListener((DataChangeListener) listeners[i]);
			}
		} else if (com instanceof KDFormattedTextField) {
			listeners = com.getListeners(DataChangeListener.class);
			for (int i = 0; i < listeners.length; i++) {
				((KDFormattedTextField) com)
						.removeDataChangeListener((DataChangeListener) listeners[i]);
			}
		} else if (com instanceof KDDatePicker) {
			listeners = com.getListeners(DataChangeListener.class);
			for (int i = 0; i < listeners.length; i++) {
				((KDDatePicker) com)
						.removeDataChangeListener((DataChangeListener) listeners[i]);
			}
		} else if (com instanceof KDComboBox) {
			listeners = com.getListeners(ItemListener.class);
			for (int i = 0; i < listeners.length; i++) {
				((KDComboBox) com)
						.removeItemListener((ItemListener) listeners[i]);
			}
		}

		if (listeners != null && listeners.length > 0) {
			listenersMap.put(com, listeners);
		}
	}

	/**
	 * 
	 * 描述：避免在新增单据（未作修改）直接关闭时，出现保存提示 放在后台线程执行以优化
	 * 
	 * @author:liupd 创建时间：2006-10-13
	 *               <p>
	 */
	protected void handleOldData() {
		if (!(getOprtState() == STATUS_FINDVIEW || getOprtState() == STATUS_VIEW)) {
			FDCUIWeightWorker.getInstance().addWork(new IFDCWork() {
				public void run() {
					storeFields();
					initOldData(editData);
				}
			});
		}
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output btnAddLine_actionPerformed method
	 */
	protected void btnAddLine_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		super.btnAddLine_actionPerformed(e);
	}

	/**
	 * output menuItemEnterToNextRow_itemStateChanged method
	 */
	protected void menuItemEnterToNextRow_itemStateChanged(
			java.awt.event.ItemEvent e) throws Exception {
		super.menuItemEnterToNextRow_itemStateChanged(e);
	}

	/**
	 * output actionPageSetup_actionPerformed
	 */
	public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception {
		super.actionPageSetup_actionPerformed(e);
	}

	/**
	 * output actionExitCurrent_actionPerformed
	 */
	public void actionExitCurrent_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExitCurrent_actionPerformed(e);
	}

	/**
	 * output actionHelp_actionPerformed
	 */
	public void actionHelp_actionPerformed(ActionEvent e) throws Exception {
		super.actionHelp_actionPerformed(e);
	}

	/**
	 * output actionAbout_actionPerformed
	 */
	public void actionAbout_actionPerformed(ActionEvent e) throws Exception {
		super.actionAbout_actionPerformed(e);
	}

	/**
	 * output actionOnLoad_actionPerformed
	 */
	public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception {
		super.actionOnLoad_actionPerformed(e);
	}

	/**
	 * output actionSendMessage_actionPerformed
	 */
	public void actionSendMessage_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionSendMessage_actionPerformed(e);
	}

	/**
	 * output actionCalculator_actionPerformed
	 */
	public void actionCalculator_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCalculator_actionPerformed(e);
	}

	/**
	 * output actionExport_actionPerformed
	 */
	public void actionExport_actionPerformed(ActionEvent e) throws Exception {
		super.actionExport_actionPerformed(e);
	}

	/**
	 * output actionExportSelected_actionPerformed
	 */
	public void actionExportSelected_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExportSelected_actionPerformed(e);
	}

	/**
	 * output actionRegProduct_actionPerformed
	 */
	public void actionRegProduct_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionRegProduct_actionPerformed(e);
	}

	/**
	 * output actionPersonalSite_actionPerformed
	 */
	public void actionPersonalSite_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionPersonalSite_actionPerformed(e);
	}

	/**
	 * output actionProcductVal_actionPerformed
	 */
	public void actionProcductVal_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionProcductVal_actionPerformed(e);
	}

	/**
	 * output actionExportSave_actionPerformed
	 */
	public void actionExportSave_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExportSave_actionPerformed(e);
	}

	/**
	 * output actionExportSelectedSave_actionPerformed
	 */
	public void actionExportSelectedSave_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExportSelectedSave_actionPerformed(e);
	}

	/**
	 * output actionKnowStore_actionPerformed
	 */
	public void actionKnowStore_actionPerformed(ActionEvent e) throws Exception {
		super.actionKnowStore_actionPerformed(e);
	}

	/**
	 * output actionAnswer_actionPerformed
	 */
	public void actionAnswer_actionPerformed(ActionEvent e) throws Exception {
		super.actionAnswer_actionPerformed(e);
	}

	/**
	 * output actionRemoteAssist_actionPerformed
	 */
	public void actionRemoteAssist_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionRemoteAssist_actionPerformed(e);
	}

	/**
	 * output actionPopupCopy_actionPerformed
	 */
	public void actionPopupCopy_actionPerformed(ActionEvent e) throws Exception {
		super.actionPopupCopy_actionPerformed(e);
	}

	/**
	 * output actionHTMLForMail_actionPerformed
	 */
	public void actionHTMLForMail_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionHTMLForMail_actionPerformed(e);
	}

	/**
	 * output actionExcelForMail_actionPerformed
	 */
	public void actionExcelForMail_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExcelForMail_actionPerformed(e);
	}

	/**
	 * output actionHTMLForRpt_actionPerformed
	 */
	public void actionHTMLForRpt_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionHTMLForRpt_actionPerformed(e);
	}

	/**
	 * output actionExcelForRpt_actionPerformed
	 */
	public void actionExcelForRpt_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExcelForRpt_actionPerformed(e);
	}

	/**
	 * output actionLinkForRpt_actionPerformed
	 */
	public void actionLinkForRpt_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionLinkForRpt_actionPerformed(e);
	}

	/**
	 * output actionPopupPaste_actionPerformed
	 */
	public void actionPopupPaste_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionPopupPaste_actionPerformed(e);
	}

	/**
	 * output actionToolBarCustom_actionPerformed
	 */
	public void actionToolBarCustom_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionToolBarCustom_actionPerformed(e);
	}

	/**
	 * output actionCloudFeed_actionPerformed
	 */
	public void actionCloudFeed_actionPerformed(ActionEvent e) throws Exception {
		super.actionCloudFeed_actionPerformed(e);
	}

	/**
	 * output actionCloudShare_actionPerformed
	 */
	public void actionCloudShare_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCloudShare_actionPerformed(e);
	}

	/**
	 * output actionCloudScreen_actionPerformed
	 */
	public void actionCloudScreen_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCloudScreen_actionPerformed(e);
	}

	/**
	 * output actionSave_actionPerformed
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
	}

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		ComproductionInfo info = ((ComproductionInfo) editData);
		if (XRBillStatusEnum.AUDITED.equals(info.getState())
				|| XRBillStatusEnum.SUBMITED.equals(info.getState())) {
			MsgBox.showWarning(this, "单据状态已经在审核中或者已审核，不能再提交");
			SysUtil.abort();
		}
		super.actionSubmit_actionPerformed(e);
		if (!getOprtState().equals(OprtState.ADDNEW)) {
			actionSave.setEnabled(false);
		} else {
			actionSave.setEnabled(true);
		}
		this.setOprtState("VIEW");
		handleOldData();
		loadData();
		actionSave.setEnabled(false);
		actionRemove.setEnabled(false);
		actionAudit.setEnabled(true);
	}

	/**
	 * output actionCancel_actionPerformed
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		super.actionCancel_actionPerformed(e);
	}

	/**
	 * output actionCancelCancel_actionPerformed
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCancelCancel_actionPerformed(e);
	}

	/**
	 * output actionFirst_actionPerformed
	 */
	public void actionFirst_actionPerformed(ActionEvent e) throws Exception {
		super.actionFirst_actionPerformed(e);
	}

	/**
	 * output actionPre_actionPerformed
	 */
	public void actionPre_actionPerformed(ActionEvent e) throws Exception {
		super.actionPre_actionPerformed(e);
	}

	/**
	 * output actionNext_actionPerformed
	 */
	public void actionNext_actionPerformed(ActionEvent e) throws Exception {
		super.actionNext_actionPerformed(e);
	}

	/**
	 * output actionLast_actionPerformed
	 */
	public void actionLast_actionPerformed(ActionEvent e) throws Exception {
		super.actionLast_actionPerformed(e);
	}

	/**
	 * output actionPrint_actionPerformed
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrint_actionPerformed(e);
	}

	/**
	 * output actionPrintPreview_actionPerformed
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionPrintPreview_actionPerformed(e);
	}

	/**
	 * output actionCopy_actionPerformed
	 */
	public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
		super.actionCopy_actionPerformed(e);
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if (getOprtState().equals("VIEW"))
			checkCanEdit();
		super.actionEdit_actionPerformed(e);
		if (editData.getState().equals(XRBillStatusEnum.SUBMITED)) {
			actionSave.setEnabled(false);
		}
	}

	protected void initDataStatus() {
		super.initDataStatus();
		setStatus();
	}

	public void setStatus() {
		actionTraceUp.setEnabled(true);
		btnTraceUp.setEnabled(true);
		menuItemTraceUp.setEnabled(true);
		actionTraceDown.setEnabled(true);
		btnTraceDown.setEnabled(true);
		menuItemTraceDown.setEnabled(true);
		ComproductionInfo info = (ComproductionInfo) dataBinder
				.getValueObject();
		if ("ADDNEW".equals(getOprtState())) {
			actionSave.setEnabled(true);
			actionSubmit.setEnabled(true);
			actionTraceUp.setEnabled(false);
			menuItemTraceUp.setEnabled(false);
			actionTraceDown.setEnabled(false);
			menuItemTraceDown.setEnabled(false);
			setRemoveLineStatus(true);
			setCreateFromStatus(true);
			setAuditStatus(false);
			setUnAuditStatus(false);
			btnCopy.setEnabled(false);
			btnRemove.setEnabled(false);
			menuItemCopy.setEnabled(false);
			menuItemRemove.setEnabled(false);
		} else if ("VIEW".equals(getOprtState())
				|| "FINDVIEW".equals(getOprtState())) {
			setAddLineStatus(false);
			setRemoveLineStatus(false);
			setCreateFromStatus(false);
			if (info.getState() != null
					&& info.getState() == XRBillStatusEnum.SUBMITED) {
				if ("FINDVIEW".equals(getOprtState()))
					setAuditStatus(false);
				else
					setAuditStatus(true);
			} else {
				setAuditStatus(false);
			}
			if (info.getState() != null
					&& info.getState() == XRBillStatusEnum.AUDITED) {
				if ("FINDVIEW".equals(getOprtState()))
					setUnAuditStatus(false);
				else
					setUnAuditStatus(true);
			} else {
				setUnAuditStatus(false);
			}
			if ("FINDVIEW".equals(getOprtState())) {
				btnCopy.setEnabled(false);
				btnRemove.setEnabled(false);
				menuItemCopy.setEnabled(false);
				menuItemRemove.setEnabled(false);
			} else {
				btnCopy.setEnabled(true);
				btnRemove.setEnabled(true);
				menuItemCopy.setEnabled(true);
				menuItemRemove.setEnabled(true);
			}
		} else if ("EDIT".equals(getOprtState())) {
			actionSave.setEnabled(true);
			actionSubmit.setEnabled(true);
			setRemoveLineStatus(true);
			setCreateFromStatus(true);
			if (info.getState() != null
					&& info.getState() == XRBillStatusEnum.SUBMITED)
				setAuditStatus(true);
			else
				setAuditStatus(false);
			if (info.getState() != null
					&& info.getState() == XRBillStatusEnum.AUDITED)
				setUnAuditStatus(true);
			else
				setUnAuditStatus(false);
			btnCopy.setEnabled(true);
			btnRemove.setEnabled(true);
			menuItemCopy.setEnabled(true);
			menuItemRemove.setEnabled(true);
		}
	}

	protected void setAddLineStatus(boolean status) {
		actionAddLine.setEnabled(status);
		actionInsertLine.setEnabled(status);
		menuItemAddLine.setEnabled(status);
		menuItemInsertLine.setEnabled(status);
		btnAddLine.setEnabled(status);
		btnInsertLine.setEnabled(status);
	}

	protected void setRemoveLineStatus(boolean status) {
		actionRemoveLine.setEnabled(status);
		menuItemRemoveLine.setEnabled(status);
		btnRemoveLine.setEnabled(status);
	}

	protected void setCreateFromStatus(boolean status) {
		actionCreateFrom.setEnabled(status);
		menuItemCreateFrom.setEnabled(status);
		btnCreateFrom.setEnabled(status);
	}

	protected void setAuditStatus(boolean status) {
		actionAudit.setEnabled(status);
		btnAudit.setEnabled(status);
	}

	protected void setUnAuditStatus(boolean status) {
		actionUnAudit.setEnabled(status);
		btnUnAudit.setEnabled(status);
	}

	protected void checkCanEdit() throws Exception {
		if (editData == null && editData.getId() == null) {
			SysUtil.abort();
		} else {
			XRBillStatusEnum baseStatus = editData.getState();
			if (baseStatus != null) {
				int baseStatusValue = baseStatus.getValue();
				if (baseStatusValue != 0 && baseStatusValue != 1
						&& baseStatusValue != 2) {
					MsgBox.showError(this, SCMClientUtils.getResource("BillAt")
							+ baseStatus.getAlias()
							+ SCMClientUtils.getResource("CantBeEdited"));
					SysUtil.abort();
				}
			}
		}
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
	}

	/**
	 * output actionAttachment_actionPerformed
	 */
	public void actionAttachment_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionAttachment_actionPerformed(e);
	}

	/**
	 * output actionSubmitOption_actionPerformed
	 */
	public void actionSubmitOption_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionSubmitOption_actionPerformed(e);
	}

	/**
	 * output actionReset_actionPerformed
	 */
	public void actionReset_actionPerformed(ActionEvent e) throws Exception {
		super.actionReset_actionPerformed(e);
	}

	/**
	 * output actionMsgFormat_actionPerformed
	 */
	public void actionMsgFormat_actionPerformed(ActionEvent e) throws Exception {
		super.actionMsgFormat_actionPerformed(e);
	}

	/**
	 * output actionAddLine_actionPerformed
	 */
	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddLine_actionPerformed(e);
	}

	/**
	 * output actionCopyLine_actionPerformed
	 */
	public void actionCopyLine_actionPerformed(ActionEvent e) throws Exception {
		super.actionCopyLine_actionPerformed(e);
	}

	/**
	 * output actionInsertLine_actionPerformed
	 */
	public void actionInsertLine_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionInsertLine_actionPerformed(e);
	}

	/**
	 * output actionRemoveLine_actionPerformed
	 */
	public void actionRemoveLine_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionRemoveLine_actionPerformed(e);
	}

	/**
	 * output actionCreateFrom_actionPerformed
	 */
	public void actionCreateFrom_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCreateFrom_actionPerformed(e);
	}

	/**
	 * output actionCopyFrom_actionPerformed
	 */
	public void actionCopyFrom_actionPerformed(ActionEvent e) throws Exception {
		super.actionCopyFrom_actionPerformed(e);
	}

	/**
	 * output actionAuditResult_actionPerformed
	 */
	public void actionAuditResult_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionAuditResult_actionPerformed(e);
	}

	/**
	 * output actionTraceUp_actionPerformed
	 */
	public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception {
		super.actionTraceUp_actionPerformed(e);
	}

	/**
	 * output actionTraceDown_actionPerformed
	 */
	public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception {
		super.actionTraceDown_actionPerformed(e);
	}

	/**
	 * output actionViewSubmitProccess_actionPerformed
	 */
	public void actionViewSubmitProccess_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionViewSubmitProccess_actionPerformed(e);
	}

	/**
	 * output actionViewDoProccess_actionPerformed
	 */
	public void actionViewDoProccess_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionViewDoProccess_actionPerformed(e);
	}

	/**
	 * output actionMultiapprove_actionPerformed
	 */
	public void actionMultiapprove_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionMultiapprove_actionPerformed(e);
	}

	/**
	 * output actionNextPerson_actionPerformed
	 */
	public void actionNextPerson_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionNextPerson_actionPerformed(e);
	}

	/**
	 * output actionStartWorkFlow_actionPerformed
	 */
	public void actionStartWorkFlow_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionStartWorkFlow_actionPerformed(e);
	}

	/**
	 * output actionVoucher_actionPerformed
	 */
	public void actionVoucher_actionPerformed(ActionEvent e) throws Exception {
		super.actionVoucher_actionPerformed(e);
	}

	/**
	 * output actionDelVoucher_actionPerformed
	 */
	public void actionDelVoucher_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionDelVoucher_actionPerformed(e);
	}

	/**
	 * output actionWorkFlowG_actionPerformed
	 */
	public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception {
		super.actionWorkFlowG_actionPerformed(e);
	}

	/**
	 * output actionCreateTo_actionPerformed
	 */
	public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception {
		super.actionCreateTo_actionPerformed(e);
	}

	/**
	 * output actionSendingMessage_actionPerformed
	 */
	public void actionSendingMessage_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionSendingMessage_actionPerformed(e);
	}

	/**
	 * output actionSignature_actionPerformed
	 */
	public void actionSignature_actionPerformed(ActionEvent e) throws Exception {
		super.actionSignature_actionPerformed(e);
	}

	/**
	 * output actionWorkflowList_actionPerformed
	 */
	public void actionWorkflowList_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionWorkflowList_actionPerformed(e);
	}

	/**
	 * output actionViewSignature_actionPerformed
	 */
	public void actionViewSignature_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionViewSignature_actionPerformed(e);
	}

	/**
	 * output actionSendMail_actionPerformed
	 */
	public void actionSendMail_actionPerformed(ActionEvent e) throws Exception {
		super.actionSendMail_actionPerformed(e);
	}

	/**
	 * output actionLocate_actionPerformed
	 */
	public void actionLocate_actionPerformed(ActionEvent e) throws Exception {
		super.actionLocate_actionPerformed(e);
	}

	/**
	 * output actionNumberSign_actionPerformed
	 */
	public void actionNumberSign_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionNumberSign_actionPerformed(e);
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception {
		return com.kingdee.eas.port.equipment.operate.ComproductionFactory
				.getRemoteInstance();
	}

	/**
	 * output createNewDetailData method
	 */
	protected IObjectValue createNewDetailData(KDTable table) {

		return null;
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		com.kingdee.eas.port.equipment.operate.ComproductionInfo objectValue = new com.kingdee.eas.port.equipment.operate.ComproductionInfo();
		objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo) (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
		Tool.checkGroupAddNew();
		return objectValue;
	}

	/** 防止提交之后出现空白单据 **/
	protected void afterSubmitAddNew() {

	}

	protected boolean isContinueAddNew() {
		return false;
	}

	/**
	 * zhangjuan 2014.5.16 实现功能：审核改变单据状态
	 **/
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
		ObjectUuidPK pk = new ObjectUuidPK(editData.getId());
		setDataObject(getValue(pk));
		loadFields();
		setSumEntry();
		actionSave.setEnabled(false);
		actionRemove.setEnabled(false);
		actionSubmit.setEnabled(false);
		actionAudit.setEnabled(false);
		actionUnAudit.setEnabled(true);
		this.setOprtState("VIEW");
		// loadData();
		setSaved(true);
	}

	/**
	 * zhangjuan 2014.5.16 实现功能：反审核改变单据状态
	 **/
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionUnAudit_actionPerformed(e);
		ObjectUuidPK pk = new ObjectUuidPK(editData.getId());
		setDataObject(getValue(pk));
		loadFields();
		setSumEntry();
		actionEdit.setEnabled(true);
		actionSave.setEnabled(true);
		actionSubmit.setEnabled(false);
		actionAudit.setEnabled(false);
		actionUnAudit.setEnabled(false);
		setSaved(true);
	}

	String cleName[] = { "吞吐量（万吨）或产值（万元）", "作业量单位（万吨、万元）", "自然吨（万吨）",
			"电（万kwh）", "柴油（吨）", "汽油（吨）", "煤炭（吨）", "蒸汽（吨）", "水（万吨）", "液化天然气（吨）",
			"能耗折算量合计（吨标煤）" };

	/**
	 * zhangjuan 2014.5.15 实现功能：单据单开是展现分录的固定列。
	 * **/
	public void onLoad() throws Exception {
		state.setEnabled(false);
		pkAuditTime.setEnabled(false);
		super.onLoad();
		this.actionAddNew.setVisible(false);
		if (OprtState.ADDNEW.equals(getOprtState())) {
			state.setSelectedItem(XRBillStatusEnum.ADD);
			this.pkBizDate.setValue(new Date());
			for (int i = 0; i < cleName.length; i++) {
				IRow IRow = this.kdtEntrys.addRow();
				IRow.getCell("project").setValue("本月");
				IRow.getCell("project1").setValue(cleName[i]);
			}
			for (int i = 0; i < cleName.length; i++) {
				IRow IRow = this.kdtEntrys.addRow();
				IRow.getCell("project").setValue("累计");
				IRow.getCell("project1").setValue(cleName[i]);
			}
			this.prmtreportingUnit.setValue(SysContext.getSysContext().getCurrentAdminUnit());
		}
		this.kdtEntrys.getColumn("project").setWidth(30);
		this.kdtEntrys.getColumn("project1").setWidth(180);
		KDTMergeManager mm = this.kdtEntrys.getMergeManager();
		mm.mergeBlock(0, 1, 10, 1);
		mm.mergeBlock(11, 1, 21, 1);
		if (editData.getState().equals(XRBillStatusEnum.SUBMITED)) {
			actionEdit.setEnabled(false);
			actionSave.setEnabled(false);
			actionSubmit.setEnabled(false);
			actionAudit.setEnabled(true);
			actionUnAudit.setEnabled(false);
		}
		if (editData.getState().equals(XRBillStatusEnum.AUDITED)) {
			actionEdit.setEnabled(false);
			actionSave.setEnabled(false);
			actionSubmit.setEnabled(false);
			actionAudit.setEnabled(false);
			actionUnAudit.setEnabled(true);
		}

		kdtEntrys_detailPanel.setVisible(false);
		kdtEntrys.setBounds(new Rectangle(12, 66, 991, 378));
		kdtEntrys_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel) com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEntrys,new com.kingdee.eas.port.equipment.operate.ComproductionEntryInfo(),null, false);
		this.add(kdtEntrys, new KDLayout.Constraints(12, 66, 991, 378,KDLayout.Constraints.ANCHOR_TOP| KDLayout.Constraints.ANCHOR_BOTTOM| KDLayout.Constraints.ANCHOR_LEFT| KDLayout.Constraints.ANCHOR_RIGHT));

		this.kdtEntrys.getColumn("project1").getStyleAttributes().setLocked(true);
		this.kdtEntrys.getColumn("project").getStyleAttributes().setLocked(true);
		this.kdtEntrys.getColumn("samePerformance").getStyleAttributes().setLocked(true);
		this.kdtEntrys.getColumn("increaseDecrease").getStyleAttributes().setLocked(true);
		this.kdtEntrys.getColumn("increaseRate").getStyleAttributes().setLocked(true);
		this.kdtEntrys.getColumn("samePeriod").getStyleAttributes().setLocked(true);
		this.kdtEntrys.getColumn("increaseRate1").getStyleAttributes().setLocked(true);
		this.kdtEntrys.getColumn("excessSection").getStyleAttributes().setLocked(true);


		for (int i = 11; i < this.kdtEntrys.getRowCount(); i++) {
			this.kdtEntrys.getRow(i).getStyleAttributes().setLocked(true);
			this.kdtEntrys.getRow(i).getStyleAttributes().setBackground(Color.yellow);
		}

		setSumEntry();
		setEntry();
		
	}
	
	protected KDTable getDetailTable() {
		return null;
	}

	private void setSumEntry() throws SQLException, BOSException {
		for (int i = 11; i < this.kdtEntrys.getRowCount(); i++) {
			int column = 2;
			for (int j = 3; j < this.kdtEntrys.getColumnCount(); j++) {
				this.kdtEntrys.getCell(i, j).setValue(BigDecimal.ZERO);
				column++;
			}
		}
		if (prmtreportingUnit.getData() == null || this.pkBizDate.getSqlDate() == null)
			return;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String year = (df.format(this.pkBizDate.getSqlDate())).substring(0, 4);

		StringBuffer sb = new StringBuffer();
		sb.append("/*dialect*/ select A.FSEQ,nvl(sum(a.CFStagePerformance),0) bqsj,nvl(sum(a.CFProEnergy),0) zxscnh,");
		sb.append(" nvl(sum(a.CFFzproEnergy),0) fzscnh,nvl(sum(a.CFLifeEnergy),0) shnh,nvl(sum(a.CFOtherEnergy),0) qtnh,'0','0','0',nvl(sum(a.CFPeriodCon),0) bqdh,'0','0','0'");
		sb.append(" from CT_OPE_ComproductionEntry a");
		sb.append(" left join CT_OPE_Comproduction b on a.fparentid = b.fid");
		sb.append(" where a.CFProject = '本月' and b.CFReportingUnitID = '"+ ((AdminOrgUnitInfo) prmtreportingUnit.getData()).getId().toString() + "' AND to_char(b.fbizdate,'YYYY')='"+ year + "' AND b.cfstate = '4'");
		sb.append(" group by A.FSEQ  ORDER BY A.FSEQ");
		IRowSet rowSet = new XRSQLBuilder().appendSql(sb.toString()).executeQuery();
		int rowindex = 11;
		while (rowSet.next()) {
			int column = 2;
			for (int j = 3; j < this.kdtEntrys.getColumnCount(); j++) {
				this.kdtEntrys.getCell(rowindex, j).setValue(
						rowSet.getBigDecimal(column));
				column++;
			}
			rowindex++;
		}
	}

	protected void prmtreportingUnit_dataChanged(DataChangeEvent e)
			throws Exception {
		super.prmtreportingUnit_dataChanged(e);
		setSumEntry();
		setEntry();
	}

	protected void pkBizDate_dataChanged(DataChangeEvent e) throws Exception {
		super.pkBizDate_dataChanged(e);
		setSumEntry();
		setEntry();
	}

	private void setEntry() throws SQLException, BOSException {
		if (prmtreportingUnit.getData() == null || this.pkBizDate.getSqlDate() == null)
			return;
		String colName[] = {"samePerformance","samePeriod"} ;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String yymmdd = df.format(this.pkBizDate.getSqlDate());
		String yearMonth = String.valueOf(Integer.parseInt(yymmdd.substring(0,4)) - 1)+ "-" + yymmdd.substring(5, 7);
		
		StringBuffer sb = new StringBuffer();
		sb.append("/*dialect*/ select A.FSEQ,");
		sb.append(" nvl(sum(a.CFStagePerformance),0) qntq,nvl(sum(a.CFPeriodCon),0) qntqdh");
		sb.append(" from CT_OPE_ComproductionEntry a");
		sb.append(" left join CT_OPE_Comproduction b on a.fparentid = b.fid");
		sb.append(" where a.CFProject = '本月' and b.CFReportingUnitID = '"
				+ ((AdminOrgUnitInfo) prmtreportingUnit.getData()).getId().toString() + "' AND to_char(b.fbizdate,'YYYY-MM')='" + yearMonth + "' AND b.cfstate = '4'");
		sb.append(" group by A.FSEQ  ORDER BY A.FSEQ");
		IRowSet rowSet = new XRSQLBuilder().appendSql(sb.toString()).executeQuery();
		int rowindex = 0;
		while (rowSet.next()) {
			int column = 2;
			for (int j = 0; j < colName.length; j++) {
				this.kdtEntrys.getCell(rowindex, colName[j]).setValue(rowSet.getBigDecimal(column));
				this.kdtEntrys.getCell(rowindex+11, colName[j]).setValue(rowSet.getBigDecimal(column));
				column++;
			}
			rowindex++;
		}
	}
	
	
	
	
	/**
	 * zhangjuan 
	 * 2014.5.16 
	 * 实现功能：值改变事件，实现分录的值计算。
	 **/
	protected void kdtEntrys_editStopped(KDTEditEvent e) throws Exception {
		super.kdtEntrys_editStopped(e);
		if(e.getRowIndex()==-1||e.getColIndex()==-1){return;}
		BigDecimal a = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(e.getRowIndex(), "stagePerformance").getValue());//本期实绩
		BigDecimal b = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(e.getRowIndex(), "samePerformance").getValue());//去年同期实绩
		this.kdtEntrys.getCell(e.getRowIndex(), "increaseDecrease").setValue(a.subtract(b));//同比增减 = 本期实绩-去年同期实绩
		BigDecimal c = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(e.getRowIndex(), "increaseDecrease").getValue());//同比增减
		//增减率 = 去年同期实绩/同比增减*0.01
		 this.kdtEntrys.getCell(e.getRowIndex(), "increaseRate").setValue(Math.abs(Double.parseDouble(String.valueOf((c.compareTo(BigDecimal.ZERO))!=0?b.divide(c,  2, BigDecimal.ROUND_UP).multiply(new BigDecimal("0.01")):BigDecimal.ZERO))));
		BigDecimal d = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(e.getRowIndex(), "periodCon").getValue());//本期单耗
		BigDecimal f  = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(e.getRowIndex(), "samePeriod").getValue());//去年同期单耗
		BigDecimal g = d.subtract(f);//同比增减
		//增减率 = 去年同期单耗/同比增减*0.01
		  this.kdtEntrys.getCell(e.getRowIndex(), "increaseRate1").setValue(Math.abs(Double.parseDouble(String.valueOf((g.compareTo(BigDecimal.ZERO))!=0?f.divide(g,  2, BigDecimal.ROUND_UP).multiply(new BigDecimal("0.01")):BigDecimal.ZERO))));
		  BigDecimal h = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(e.getRowIndex(), "proEnergy").getValue());//装卸生产能耗
		  BigDecimal k = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(e.getRowIndex(), "fzproEnergy").getValue());//辅助生产能耗
		  BigDecimal l = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(e.getRowIndex(), "lifeEnergy").getValue());//生活能耗
		  BigDecimal m = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(e.getRowIndex(), "otherEnergy").getValue());//其他能耗
		  BigDecimal n = h.add(k).add(l).add(m);//本月的耗量
		  
		//获取上个月的耗能量 = 装卸生产能耗+辅助生产能耗+生活能耗+其他能耗
		  if (prmtreportingUnit.getData() == null || this.pkBizDate.getSqlDate() == null)
				return;
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
			
			Calendar calend = Calendar.getInstance();
			calend.setTime(this.pkBizDate.getSqlDate());
			calend.add(Calendar.MONTH, -1);
			
			String yymmdd = df.format(calend.getTime());
			
			String yearMonth = String.valueOf(yymmdd.substring(0,4)+ "-" + yymmdd.substring(5, 7));
			StringBuffer sb = new StringBuffer();
			sb.append("/*dialect*/select A.FSEQ,");
			sb.append("(nvl(a.CFProEnergy,0)+nvl(a.CFFzproEnergy,0)+nvl(a.CFLifeEnergy,0)+nvl(a.CFOtherEnergy,0)) tbjc");
			sb.append(" from CT_OPE_ComproductionEntry a");
			sb.append(" left join CT_OPE_Comproduction b on a.fparentid = b.fid");
			sb.append(" where a.CFProject = '本月' ");
			sb.append("and b.CFReportingUnitID = '"+((AdminOrgUnitInfo) prmtreportingUnit.getData()).getId().toString() + "'");
			sb.append("and to_char(b.fbizdate,'YYYY-MM')='" + yearMonth + "'");
			sb.append("and b.cfstate = '4'");
			IRowSet rowSet = new XRSQLBuilder().appendSql(sb.toString()).executeQuery();
			
			int rowindex = 0;
			while (rowSet.next()) {
				int column = 2;
				for (int j = 0; j < this.kdtEntrys.getColumnCount(); j++) {
					BigDecimal  tbjc = UIRuleUtil.getBigDecimal(rowSet.getBigDecimal("tbjc"));
					this.kdtEntrys.getCell(rowindex, "excessSection").setValue(n.subtract(tbjc));
					this.kdtEntrys.getCell(rowindex+11, "excessSection").setValue(n.subtract(tbjc));
					column++;
				}
				rowindex++;
			}
			
	}


}