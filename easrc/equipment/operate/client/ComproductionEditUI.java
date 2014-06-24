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
		// ע��������
		detachListeners();
		super.loadFields();
		// �����ϼ�����
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
	 * �������������������ݣ�δ���޸ģ�ֱ�ӹر�ʱ�����ֱ�����ʾ ���ں�̨�߳�ִ�����Ż�
	 * 
	 * @author:liupd ����ʱ�䣺2006-10-13
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
			MsgBox.showWarning(this, "����״̬�Ѿ�������л�������ˣ��������ύ");
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

	/** ��ֹ�ύ֮����ֿհ׵��� **/
	protected void afterSubmitAddNew() {

	}

	protected boolean isContinueAddNew() {
		return false;
	}

	/**
	 * zhangjuan 2014.5.16 ʵ�ֹ��ܣ���˸ı䵥��״̬
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
	 * zhangjuan 2014.5.16 ʵ�ֹ��ܣ�����˸ı䵥��״̬
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

	String cleName[] = { "����������֣����ֵ����Ԫ��", "��ҵ����λ����֡���Ԫ��", "��Ȼ�֣���֣�",
			"�磨��kwh��", "���ͣ��֣�", "���ͣ��֣�", "ú̿���֣�", "�������֣�", "ˮ����֣�", "Һ����Ȼ�����֣�",
			"�ܺ��������ϼƣ��ֱ�ú��" };

	/**
	 * zhangjuan 2014.5.15 ʵ�ֹ��ܣ����ݵ�����չ�ַ�¼�Ĺ̶��С�
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
				IRow.getCell("project").setValue("����");
				IRow.getCell("project1").setValue(cleName[i]);
			}
			for (int i = 0; i < cleName.length; i++) {
				IRow IRow = this.kdtEntrys.addRow();
				IRow.getCell("project").setValue("�ۼ�");
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
		
		kdtEntrys.getRow(10).getStyleAttributes().setLocked(true);
	
		for (int i = 0; i < this.kdtEntrys.getRowCount(); i++) {
			kdtEntrys.getCell(i, "periodCon").getStyleAttributes().setLocked(true);
		}

		for (int i = 3; i < this.kdtEntrys.getRowCount(); i++) {
			kdtEntrys.getCell(i, "stagePerformance").getStyleAttributes().setLocked(true);
		}
		for (int i = 0; i < 3; i++) {
			kdtEntrys.getCell(i, "proEnergy").getStyleAttributes().setLocked(true);
			kdtEntrys.getCell(i, "fzproEnergy").getStyleAttributes().setLocked(true);
			kdtEntrys.getCell(i, "lifeEnergy").getStyleAttributes().setLocked(true);
			kdtEntrys.getCell(i, "otherEnergy").getStyleAttributes().setLocked(true);
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
		sb.append(" nvl(sum(a.CFFzproEnergy),0) fzscnh,nvl(sum(a.CFLifeEnergy),0) shnh,nvl(sum(a.CFOtherEnergy),0) qtnh,nvl(sum(a.CFSamePerformance),0) spfa,nvl(sum(a.CFIncreaseDecrease),0) icdc,'0',nvl(sum(a.CFPeriodCon),0) bqdh,nvl(sum(a.CFSamePeriod),0) spod,'0',nvl(sum(a.CFExcessSection),0) estn");
		sb.append(" from CT_OPE_ComproductionEntry a");
		sb.append(" left join CT_OPE_Comproduction b on a.fparentid = b.fid");
		sb.append(" where a.CFProject = '����' and b.CFReportingUnitID = '"+ ((AdminOrgUnitInfo) prmtreportingUnit.getData()).getId().toString() + "' AND to_char(b.fbizdate,'YYYY')='"+ year + "' AND b.cfstate = '4'");
		sb.append(" group by A.FSEQ  ORDER BY A.FSEQ");
		IRowSet rowSet = new XRSQLBuilder().appendSql(sb.toString()).executeQuery();
		int rowindex = 11;
		while (rowSet.next()) {
			int column = 2;
			for (int j = 3; j < this.kdtEntrys.getColumnCount(); j++) {
				this.kdtEntrys.getCell(rowindex, j).setValue(rowSet.getBigDecimal(column));
				column++;
			}
			BigDecimal a1 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(rowindex, "samePerformance").getValue());
			BigDecimal a2 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(rowindex, "increaseDecrease").getValue());
			BigDecimal a3 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(rowindex, "periodCon").getValue());
			BigDecimal a4 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(rowindex, "samePeriod").getValue());
			BigDecimal a5 = a3.subtract(a4);
			 this.kdtEntrys.getCell(rowindex, "increaseRate").setValue(Double.parseDouble(String.valueOf((a1.compareTo(BigDecimal.ZERO))!=0?a2.divide(a1,  4, BigDecimal.ROUND_UP).multiply(new BigDecimal("100")):BigDecimal.ZERO)));
			 this.kdtEntrys.getCell(rowindex, "increaseRate1").setValue(Double.parseDouble(String.valueOf((a4.compareTo(BigDecimal.ZERO))!=0?a5.divide(a4,  4, BigDecimal.ROUND_UP).multiply(new BigDecimal("100")):BigDecimal.ZERO)));
			 rowindex+=1;
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
		sb.append(" where a.CFProject = '����' and b.CFReportingUnitID = '"
				+ ((AdminOrgUnitInfo) prmtreportingUnit.getData()).getId().toString() + "' AND to_char(b.fbizdate,'YYYY-MM')='" + yearMonth + "' AND b.cfstate = '4'");
		sb.append(" group by A.FSEQ  ORDER BY A.FSEQ");
		IRowSet rowSet = new XRSQLBuilder().appendSql(sb.toString()).executeQuery();
		if(rowSet.size()>0)
		{
			int rowindex = 0;
			while(rowSet.next())
			{
				this.kdtEntrys.getCell(rowindex, colName[0]).setValue(rowSet.getBigDecimal(2));
				this.kdtEntrys.getCell(rowindex, colName[1]).setValue(rowSet.getBigDecimal(3));
				rowindex+=1;
			}
		}
		else
		{
			int column = 2;
			for (int i = 0; i < 11; i++) {
				for (int j = 0; j < colName.length; j++) {
					this.kdtEntrys.getCell(i, colName[j]).setValue(null);
				}
			}
		}
	}
	
	
	
	
	/**
	 * zhangjuan 
	 * 2014.5.16 
	 * ʵ�ֹ��ܣ�ֵ�ı��¼���ʵ�ַ�¼��ֵ���㡣
	 **/
	
	protected void kdtEntrys_editStopped(KDTEditEvent e) throws Exception {
		super.kdtEntrys_editStopped(e);
		if(e.getRowIndex()==-1||e.getColIndex()==-1){return;}
		
		for (int i = 3; i <10; i++) {
			/**
			 * ����ʵ�����磺4.04;���ͣ�1.4571�����ͣ�1.4714��ú̿��0.7143��
			 * ������0.129��ˮ��0.857��Һ����Ȼ����1.7572����=װж�����ܺ�+���������ܺ�+�����ܺ�+�����ܺ�
			 **/
			
			BigDecimal aa = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(i, "proEnergy").getValue());
			BigDecimal bb = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(i, "fzproEnergy").getValue());
			BigDecimal cc = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(i, "lifeEnergy").getValue());
			BigDecimal dd = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(i, "otherEnergy").getValue());
			kdtEntrys.getCell(i, "stagePerformance").setValue(aa.add(bb).add(cc).add(dd));
			BigDecimal ee = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(3, "stagePerformance").getValue()).multiply(new BigDecimal(4.04));
			BigDecimal ff = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(4, "stagePerformance").getValue()).multiply(new BigDecimal(1.4571));
			BigDecimal gg = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(5, "stagePerformance").getValue()).multiply(new BigDecimal(1.4714));
			BigDecimal hh = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(6, "stagePerformance").getValue()).multiply(new BigDecimal(0.7143));
			BigDecimal ii = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(7, "stagePerformance").getValue()).multiply(new BigDecimal(0.129));
			BigDecimal jj = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(8, "stagePerformance").getValue()).multiply(new BigDecimal(0.857));
			BigDecimal kk = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(9, "stagePerformance").getValue()).multiply(new BigDecimal(1.7572));
			kdtEntrys.getCell(10, "stagePerformance").setValue(ee.add(ff).add(gg).add(hh).add(ii).add(jj).add(kk));
			BigDecimal ee1 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(3, "proEnergy").getValue()).multiply(new BigDecimal(4.04));
			BigDecimal ff1 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(4, "proEnergy").getValue()).multiply(new BigDecimal(1.4571));
			BigDecimal gg1 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(5, "proEnergy").getValue()).multiply(new BigDecimal(1.4714));
			BigDecimal hh1 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(6, "proEnergy").getValue()).multiply(new BigDecimal(0.7143));
			BigDecimal ii1 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(7, "proEnergy").getValue()).multiply(new BigDecimal(0.129));
			BigDecimal jj1 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(8, "proEnergy").getValue()).multiply(new BigDecimal(0.857));
			BigDecimal kk1 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(9, "proEnergy").getValue()).multiply(new BigDecimal(1.7572));
			kdtEntrys.getCell(10, "proEnergy").setValue(ee1.add(ff1).add(gg1).add(hh1).add(ii1).add(jj1).add(kk1));
			BigDecimal ee2 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(3, "fzproEnergy").getValue()).multiply(new BigDecimal(4.04));
			BigDecimal ff2 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(4, "fzproEnergy").getValue()).multiply(new BigDecimal(1.4571));
			BigDecimal gg2 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(5, "fzproEnergy").getValue()).multiply(new BigDecimal(1.4714));
			BigDecimal hh2 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(6, "fzproEnergy").getValue()).multiply(new BigDecimal(0.7143));
			BigDecimal ii2 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(7, "fzproEnergy").getValue()).multiply(new BigDecimal(0.129));
			BigDecimal jj2 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(8, "fzproEnergy").getValue()).multiply(new BigDecimal(0.857));
			BigDecimal kk2 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(9, "fzproEnergy").getValue()).multiply(new BigDecimal(1.7572));
			kdtEntrys.getCell(10, "fzproEnergy").setValue(ee2.add(ff2).add(gg2).add(hh2).add(ii2).add(jj2).add(kk2));
			BigDecimal ee3 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(3, "lifeEnergy").getValue()).multiply(new BigDecimal(4.04));
			BigDecimal ff3 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(4, "lifeEnergy").getValue()).multiply(new BigDecimal(1.4571));
			BigDecimal gg3 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(5, "lifeEnergy").getValue()).multiply(new BigDecimal(1.4714));
			BigDecimal hh3 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(6, "lifeEnergy").getValue()).multiply(new BigDecimal(0.7143));
			BigDecimal ii3 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(7, "lifeEnergy").getValue()).multiply(new BigDecimal(0.129));
			BigDecimal jj3 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(8, "lifeEnergy").getValue()).multiply(new BigDecimal(0.857));
			BigDecimal kk3 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(9, "lifeEnergy").getValue()).multiply(new BigDecimal(1.7572));
			kdtEntrys.getCell(10, "lifeEnergy").setValue(ee3.add(ff3).add(gg3).add(hh3).add(ii3).add(jj3).add(kk3));
			BigDecimal ee4 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(3, "otherEnergy").getValue()).multiply(new BigDecimal(4.04));
			BigDecimal ff4 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(4, "otherEnergy").getValue()).multiply(new BigDecimal(1.4571));
			BigDecimal gg4 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(5, "otherEnergy").getValue()).multiply(new BigDecimal(1.4714));
			BigDecimal hh4 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(6, "otherEnergy").getValue()).multiply(new BigDecimal(0.7143));
			BigDecimal ii4 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(7, "otherEnergy").getValue()).multiply(new BigDecimal(0.129));
			BigDecimal jj4 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(8, "otherEnergy").getValue()).multiply(new BigDecimal(0.857));
			BigDecimal kk4 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(9, "otherEnergy").getValue()).multiply(new BigDecimal(1.7572));
			kdtEntrys.getCell(10, "otherEnergy").setValue(ee4.add(ff4).add(gg4).add(hh4).add(ii4).add(jj4).add(kk4));
		}
		BigDecimal a = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(e.getRowIndex(), "stagePerformance").getValue());//����ʵ��
		BigDecimal b = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(e.getRowIndex(), "samePerformance").getValue());//ȥ��ͬ��ʵ��
		this.kdtEntrys.getCell(e.getRowIndex(), "increaseDecrease").setValue(a.subtract(b));//ͬ������ = ����ʵ��-ȥ��ͬ��ʵ��
		BigDecimal c = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(e.getRowIndex(), "increaseDecrease").getValue());//ͬ������
		//������ = ͬ������/ȥ��ͬ��ʵ��*0.01
		 this.kdtEntrys.getCell(e.getRowIndex(), "increaseRate").setValue(Double.parseDouble(String.valueOf((b.compareTo(BigDecimal.ZERO))!=0?c.divide(b,  4, BigDecimal.ROUND_UP).multiply(new BigDecimal("100")):BigDecimal.ZERO)));
		BigDecimal d = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(e.getRowIndex(), "periodCon").getValue());//���ڵ���
		BigDecimal f  = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(e.getRowIndex(), "samePeriod").getValue());//ȥ��ͬ�ڵ���
		BigDecimal g = d.subtract(f);//ͬ������
		//������ = ͬ������/ȥ��ͬ�ڵ���*0.01
		  this.kdtEntrys.getCell(e.getRowIndex(), "increaseRate1").setValue(Double.parseDouble(String.valueOf((f.compareTo(BigDecimal.ZERO))!=0?g.divide(f,  4, BigDecimal.ROUND_UP).multiply(new BigDecimal("100")):BigDecimal.ZERO)));
		  BigDecimal h = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(e.getRowIndex(), "proEnergy").getValue());//װж�����ܺ�
		  BigDecimal k = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(e.getRowIndex(), "fzproEnergy").getValue());//���������ܺ�
		  BigDecimal l = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(e.getRowIndex(), "lifeEnergy").getValue());//�����ܺ�
		  BigDecimal m = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(e.getRowIndex(), "otherEnergy").getValue());//�����ܺ�
		  BigDecimal n = h.add(k).add(l).add(m);//���µĺ���
		  
		  
//		//��ȡ�ϸ��µı���ʵ�� 
//		  if (prmtreportingUnit.getData() == null || this.pkBizDate.getSqlDate() == null)
//				return;
//			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
//			
//			Calendar calend = Calendar.getInstance();
//			calend.setTime(this.pkBizDate.getSqlDate());
//			calend.add(Calendar.MONTH, -1);
//			
//			String yymmdd = df.format(calend.getTime());
//			
//			String yearMonth = String.valueOf(yymmdd.substring(0,4)+ "-" + yymmdd.substring(5, 7));
//			StringBuffer sb = new StringBuffer();
//			sb.append("/*dialect*/select A.FSEQ,");
//			sb.append("nvl(a.CFStagePerformance,0) tbjc");
//			sb.append(" from CT_OPE_ComproductionEntry a");
//			sb.append(" left join CT_OPE_Comproduction b on a.fparentid = b.fid");
//			sb.append(" where a.CFProject = '����' ");
//			sb.append("and b.CFReportingUnitID = '"+((AdminOrgUnitInfo) prmtreportingUnit.getData()).getId().toString() + "'");
//			sb.append("and to_char(b.fbizdate,'YYYY-MM')='" + yearMonth + "'");
//			sb.append("and b.cfstate = '4'");
//			IRowSet rowSet = new XRSQLBuilder().appendSql(sb.toString()).executeQuery();
//			
//			int rowindex = 0;
//			this.kdtEntrys.getCell(rowindex, "excessSection").setValue(a);
//			this.kdtEntrys.getCell(1, "excessSection").setValue(kdtEntrys.getCell(1, "stagePerformance").getValue());
//			this.kdtEntrys.getCell(2, "excessSection").setValue(kdtEntrys.getCell(2, "stagePerformance").getValue());
//			this.kdtEntrys.getCell(3, "excessSection").setValue(kdtEntrys.getCell(3, "stagePerformance").getValue());
//			this.kdtEntrys.getCell(4, "excessSection").setValue(kdtEntrys.getCell(4, "stagePerformance").getValue());
//			this.kdtEntrys.getCell(5, "excessSection").setValue(kdtEntrys.getCell(5, "stagePerformance").getValue());
//			this.kdtEntrys.getCell(6, "excessSection").setValue(kdtEntrys.getCell(6, "stagePerformance").getValue());
//			this.kdtEntrys.getCell(7, "excessSection").setValue(kdtEntrys.getCell(7, "stagePerformance").getValue());
//			this.kdtEntrys.getCell(8, "excessSection").setValue(kdtEntrys.getCell(8, "stagePerformance").getValue());
//			this.kdtEntrys.getCell(9, "excessSection").setValue(kdtEntrys.getCell(9, "stagePerformance").getValue());
//			this.kdtEntrys.getCell(10, "excessSection").setValue(kdtEntrys.getCell(10, "stagePerformance").getValue());
//			while (rowSet.next()) {
//				BigDecimal  tbjc = UIRuleUtil.getBigDecimal(rowSet.getBigDecimal("tbjc"));
//				BigDecimal abc = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(rowindex, "stagePerformance").getValue());
//				this.kdtEntrys.getCell(rowindex, "excessSection").setValue(abc.subtract(tbjc));
//				rowindex+=1;
//			}
			
	
			
			//�������ı��ڵ���=�ܺ��������ϼƣ��ֱ�ú������ʵ��/����������ʵ��
				BigDecimal aaaa = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(10, "stagePerformance").getValue());
				BigDecimal bbbb = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(0, "stagePerformance").getValue());
				kdtEntrys.getCell(0, "periodCon").setValue(Double.parseDouble(String.valueOf((bbbb.compareTo(BigDecimal.ZERO))!=0?aaaa.divide(bbbb,  4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
				
			    BigDecimal cccc = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(10, "stagePerformance").getValue());
				BigDecimal dddd = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(10, "samePerformance").getValue());
				this.kdtEntrys.getCell(10, "increaseDecrease").setValue(cccc.subtract(dddd));
				BigDecimal eeee = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(10, "increaseDecrease").getValue());
				BigDecimal ffff = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(10, "periodCon").getValue());
				BigDecimal gggg = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(10, "samePeriod").getValue());
				BigDecimal hhhh = ffff.subtract(gggg);
				 this.kdtEntrys.getCell(10, "increaseRate").setValue(Double.parseDouble(String.valueOf((dddd.compareTo(BigDecimal.ZERO))!=0?eeee.divide(dddd,  4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
				 this.kdtEntrys.getCell(10, "increaseRate1").setValue(Double.parseDouble(String.valueOf((gggg.compareTo(BigDecimal.ZERO))!=0?hhhh.divide(gggg,  4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
				 
				//ˮ����ȱ��ڵ���=����ʵ��/��Ȼ��
				BigDecimal aaaa0 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(3, "stagePerformance").getValue());
				BigDecimal aaaa1 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(4, "stagePerformance").getValue());
				BigDecimal aaaa2 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(5, "stagePerformance").getValue());
				BigDecimal aaaa3 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(6, "stagePerformance").getValue());
				BigDecimal aaaa4 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(7, "stagePerformance").getValue());
				BigDecimal aaaa5 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(8, "stagePerformance").getValue());
				BigDecimal aaaa6 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(9, "stagePerformance").getValue());
				BigDecimal aaaa7 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(10, "stagePerformance").getValue());
				BigDecimal bbbb0 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(2, "stagePerformance").getValue());
				kdtEntrys.getCell(3, "periodCon").setValue(Double.parseDouble(String.valueOf((bbbb0.compareTo(BigDecimal.ZERO))!=0?aaaa0.divide(bbbb0, 4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
				kdtEntrys.getCell(4, "periodCon").setValue(Double.parseDouble(String.valueOf((bbbb0.compareTo(BigDecimal.ZERO))!=0?aaaa1.divide(bbbb0, 4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
				kdtEntrys.getCell(5, "periodCon").setValue(Double.parseDouble(String.valueOf((bbbb0.compareTo(BigDecimal.ZERO))!=0?aaaa2.divide(bbbb0, 4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
				kdtEntrys.getCell(6, "periodCon").setValue(Double.parseDouble(String.valueOf((bbbb0.compareTo(BigDecimal.ZERO))!=0?aaaa3.divide(bbbb0, 4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
				kdtEntrys.getCell(7, "periodCon").setValue(Double.parseDouble(String.valueOf((bbbb0.compareTo(BigDecimal.ZERO))!=0?aaaa4.divide(bbbb0, 4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
				kdtEntrys.getCell(8, "periodCon").setValue(Double.parseDouble(String.valueOf((bbbb0.compareTo(BigDecimal.ZERO))!=0?aaaa5.divide(bbbb0, 4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
				kdtEntrys.getCell(9, "periodCon").setValue(Double.parseDouble(String.valueOf((bbbb0.compareTo(BigDecimal.ZERO))!=0?aaaa6.divide(bbbb0, 4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
				kdtEntrys.getCell(10, "periodCon").setValue(Double.parseDouble(String.valueOf((bbbb0.compareTo(BigDecimal.ZERO))!=0?aaaa7.divide(bbbb0, 4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
				//������ͬ�Ƚڳ���(��-����+)=�����ڵ���-ȥ��ͬ�ڵ��ģ�*����������ʵ��
				BigDecimal qq = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(0, "periodCon").getValue());
				BigDecimal ww = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(0, "samePeriod").getValue());
				BigDecimal rr = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(0, "stagePerformance").getValue());
				BigDecimal qw = qq.subtract(ww);//���ڵ���-ȥ��ͬ�ڵ���
				kdtEntrys.getCell(0, "excessSection").setValue(qw.multiply(rr));
				//ˮ�����ͬ�Ƚڳ���(��-����+)=�����ڵ���-ȥ��ͬ�ڵ��ģ�*��Ȼ�ֱ���ʵ��
				for (int i = 3; i < 11; i++) {
					BigDecimal tt = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(i, "periodCon").getValue());
					BigDecimal yy = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(i, "samePeriod").getValue());
					BigDecimal uu = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(2, "stagePerformance").getValue());
					BigDecimal ty = tt.subtract(yy);//���ڵ���-ȥ��ͬ�ڵ���
					kdtEntrys.getCell(i, "excessSection").setValue(ty.multiply(uu));
				}
				
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String time = (df.format(this.pkBizDate.getSqlDate())).substring(0, 7);
		String id = ((AdminOrgUnitInfo)prmtreportingUnit.getData()).getId().toString();
		StringBuffer sb = new StringBuffer();
		sb.append("/*dialect*/select to_char(fbizdate,'yyyy-mm') datetime");
		sb.append(" from CT_OPE_Comproduction");
		sb.append(" where CFReportingUnitID = '"+id+"'");
		sb.append(" and to_char(fbizdate,'yyyy-mm')='"+time+"'");
		IRowSet rowSet = new XRSQLBuilder().appendSql(sb.toString()).executeQuery();
		if(rowSet.size()>0){
			MsgBox.showInfo("����λ�������й�˾���ܱ���������������!");
  			SysUtil.abort();
		}
	}
}