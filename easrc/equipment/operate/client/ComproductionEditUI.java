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

import org.apache.commons.httpclient.util.URIUtil;
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
import com.kingdee.eas.basedata.assistant.PeriodFactory;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.port.equipment.operate.ComproductionFactory;
import com.kingdee.eas.port.equipment.operate.ComproductionInfo;
import com.kingdee.eas.scm.common.client.SCMClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.eas.xr.app.XRBillStatusEnum;
import com.kingdee.eas.xr.helper.PersonXRHelper;
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
	 private Color a = new Color(187,255,255);
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
		this.actionAddNew.setVisible(false);
		super.loadFields();
		// 最后加上监听器
		attachListeners();
		KDTMergeManager mm = this.kdtEntrys.getMergeManager();
		if (this.kdtEntrys.getRowCount() > 0) {
			mm.mergeBlock(0, 1, 9, 1);
			mm.mergeBlock(10, 1, 19, 1);
		}

		for (int i = 10; i < this.kdtEntrys.getRowCount(); i++) {
			this.kdtEntrys.getRow(i).getStyleAttributes().setLocked(true);
			this.kdtEntrys.getRow(i).getStyleAttributes().setBackground(Color.yellow);
		}
		for (int j = 0; j < 2; j++) {
			 if(this.kdtEntrys.getRowCount()>=2){
				 this.kdtEntrys.getCell(j, "stagePerformance").getStyleAttributes().setBackground(a);
			 }
		}
		for (int k = 0; k < 10; k++) {
			 if(this.kdtEntrys.getRowCount()>=9){
				 this.kdtEntrys.getCell(k, "samePerformance").getStyleAttributes().setBackground(a);
				 this.kdtEntrys.getCell(k, "samePeriod").getStyleAttributes().setBackground(a);
				 }
		}
		for (int l = 2; l < 9; l++) {
			 if(this.kdtEntrys.getRowCount()>=7){
				 this.kdtEntrys.getCell(l, "proEnergy").getStyleAttributes().setBackground(a);
				 this.kdtEntrys.getCell(l, "fzproEnergy").getStyleAttributes().setBackground(a);
				 this.kdtEntrys.getCell(l, "lifeEnergy").getStyleAttributes().setBackground(a);
				 this.kdtEntrys.getCell(l, "otherEnergy").getStyleAttributes().setBackground(a);
			 }
		}
		
		contBizDate.setBoundLabelText("填报日期");
	}

	protected void initWorkButton() {
		super.initWorkButton();
		btnAudit.setIcon(EASResource.getIcon("imgTbtn_auditing"));
		btnUnAudit.setIcon(EASResource.getIcon("imgTbtn_fauditing"));
	}

	private void attachListeners() {
		addDataChangeListener(this.prmtreportingUnit);
		addDataChangeListener(this.pkBizDate);
		addDataChangeListener(this.prmtreportMonth);
	}

	private void detachListeners() {
		removeDataChangeListener(this.prmtreportingUnit);
		removeDataChangeListener(this.pkBizDate);
		removeDataChangeListener(this.prmtreportMonth);
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
		setEntry1();
		setSumEntry();
		storeFields();
		ComproductionFactory.getRemoteInstance().save(new ObjectUuidPK(editData.getId()), editData);
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
		setEntry1();
		setSumEntry();
		storeFields();
		ComproductionFactory.getRemoteInstance().save(new ObjectUuidPK(editData.getId()), editData);
		actionEdit.setEnabled(true);
		actionSave.setEnabled(true);
		actionSubmit.setEnabled(false);
		actionAudit.setEnabled(false);
		actionUnAudit.setEnabled(false);
		setSaved(true);
	}

	String cleName[] = { "吞吐量（万吨）或产值（万元）",  "自然吨（万吨）",
			"电（万kwh）", "柴油（吨）", "汽油（吨）", "煤炭（吨）", "蒸汽（吨）", "水（万吨）", "液化天然气（吨）",
			"能耗折算量合计（吨标煤）" };

	/**
	 * zhangjuan 2014.5.15 实现功能：单据单开是展现分录的固定列。
	 * **/
	public void onLoad() throws Exception {
		state.setEnabled(false);
		pkAuditTime.setEnabled(false);
		super.onLoad();
		prmtreportMonth.setRequired(true);
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
			this.prmtCreator.setValue(SysContext.getSysContext().getCurrentUserInfo().getPerson());
			setEntry();
//			setEntry1();
		}
//		setSumEntry();
		this.kdtEntrys.getColumn("project").setWidth(30);
		this.kdtEntrys.getColumn("project1").setWidth(180);
		KDTMergeManager mm = this.kdtEntrys.getMergeManager();
		mm.mergeBlock(0, 1, 9, 1);
		mm.mergeBlock(10, 1, 19, 1);
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
		kdtEntrys.setBounds(new Rectangle(12, 90, 991, 363));
		kdtEntrys_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel) com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEntrys,new com.kingdee.eas.port.equipment.operate.ComproductionEntryInfo(),null, false);
		this.add(kdtEntrys, new KDLayout.Constraints(12, 90, 991, 363,KDLayout.Constraints.ANCHOR_TOP| KDLayout.Constraints.ANCHOR_BOTTOM| KDLayout.Constraints.ANCHOR_LEFT| KDLayout.Constraints.ANCHOR_RIGHT));

		this.kdtEntrys.getColumn("project1").getStyleAttributes().setLocked(true);
		this.kdtEntrys.getColumn("project").getStyleAttributes().setLocked(true);
		this.kdtEntrys.getColumn("samePerformance").getStyleAttributes().setLocked(true);
		this.kdtEntrys.getColumn("increaseDecrease").getStyleAttributes().setLocked(true);
		this.kdtEntrys.getColumn("increaseRate").getStyleAttributes().setLocked(true);
		this.kdtEntrys.getColumn("samePeriod").getStyleAttributes().setLocked(true);
		this.kdtEntrys.getColumn("increaseRate1").getStyleAttributes().setLocked(true);
		this.kdtEntrys.getColumn("excessSection").getStyleAttributes().setLocked(true);


		for (int i = 10; i < this.kdtEntrys.getRowCount(); i++) {
			this.kdtEntrys.getRow(i).getStyleAttributes().setLocked(true);
			this.kdtEntrys.getRow(i).getStyleAttributes().setBackground(Color.yellow);
		}
		for (int i = 0; i < 2; i++) {
			 if(this.kdtEntrys.getRowCount()>=2){
				 this.kdtEntrys.getCell(i, "stagePerformance").getStyleAttributes().setBackground(a);
			 }
		}
		for (int k = 0; k < 10; k++) {
			 if(this.kdtEntrys.getRowCount()>=10){
				 this.kdtEntrys.getCell(k, "samePerformance").getStyleAttributes().setBackground(a);
				 this.kdtEntrys.getCell(k, "samePeriod").getStyleAttributes().setBackground(a);
				 }
		}
		for (int i = 2; i < 9; i++) {
			 if(this.kdtEntrys.getRowCount()>=7){
				 this.kdtEntrys.getCell(i, "proEnergy").getStyleAttributes().setBackground(a);
				 this.kdtEntrys.getCell(i, "fzproEnergy").getStyleAttributes().setBackground(a);
				 this.kdtEntrys.getCell(i, "lifeEnergy").getStyleAttributes().setBackground(a);
				 this.kdtEntrys.getCell(i, "otherEnergy").getStyleAttributes().setBackground(a);
			 }
		}
		kdtEntrys.getRow(9).getStyleAttributes().setLocked(true);
	
		for (int i = 0; i < this.kdtEntrys.getRowCount(); i++) {
			kdtEntrys.getCell(i, "periodCon").getStyleAttributes().setLocked(true);
		
		}

		for(int i = 0; i < 10; i++){
			kdtEntrys.getCell(i, "samePerformance").getStyleAttributes().setLocked(false);
			kdtEntrys.getCell(i, "samePeriod").getStyleAttributes().setLocked(false);
		}
		
		for (int i = 2; i < this.kdtEntrys.getRowCount(); i++) {
			kdtEntrys.getCell(i, "stagePerformance").getStyleAttributes().setLocked(true);
		}
		for (int i = 0; i < 2; i++) {
			kdtEntrys.getCell(i, "proEnergy").getStyleAttributes().setLocked(true);
			kdtEntrys.getCell(i, "fzproEnergy").getStyleAttributes().setLocked(true);
			kdtEntrys.getCell(i, "lifeEnergy").getStyleAttributes().setLocked(true);
			kdtEntrys.getCell(i, "otherEnergy").getStyleAttributes().setLocked(true);
		}  
	
		Tool.setRespDeptF7(this.prmtreportingUnit, this, SysContext.getSysContext().getCurrentCtrlUnit().getId().toString());
		this.kdtEntrys.getColumn("proEnergy").getStyleAttributes().setNumberFormat("#,##0.0000");
		this.kdtEntrys.getColumn("fzproEnergy").getStyleAttributes().setNumberFormat("#,##0.0000");
		this.kdtEntrys.getColumn("lifeEnergy").getStyleAttributes().setNumberFormat("#,##0.0000");
		this.kdtEntrys.getColumn("otherEnergy").getStyleAttributes().setNumberFormat("#,##0.0000");
		this.kdtEntrys.getColumn("samePerformance").getStyleAttributes().setNumberFormat("#,##0.0000");
		this.kdtEntrys.getColumn("increaseDecrease").getStyleAttributes().setNumberFormat("#,##0.0000");
		this.kdtEntrys.getColumn("periodCon").getStyleAttributes().setNumberFormat("#,##0.0000");
		this.kdtEntrys.getColumn("samePeriod").getStyleAttributes().setNumberFormat("#,##0.0000");
		this.kdtEntrys.getColumn("excessSection").getStyleAttributes().setNumberFormat("#,##0.0000");
		this.kdtEntrys.getColumn("stagePerformance").getStyleAttributes().setNumberFormat("#,##0.0000");

		
		
	}
	
	protected KDTable getDetailTable() {
		return null;
	}

	private void setSumEntry() throws SQLException, BOSException {
//		for (int i = 10; i < this.kdtEntrys.getRowCount(); i++) {
//			int column = 2;
//			for (int j = 3; j < this.kdtEntrys.getColumnCount(); j++) {
//				this.kdtEntrys.getCell(i, j).setValue(BigDecimal.ZERO);
//				column++;
//			}
//		}
		if (prmtreportingUnit.getData() == null || this.prmtreportMonth.getValue() == null)
			return;
		String id = ((PeriodInfo)prmtreportMonth.getData()).getId().toString();
		try {
			PeriodInfo perInfo = PeriodFactory.getRemoteInstance().getPeriodInfo(new ObjectUuidPK(id));
			int year = perInfo.getPeriodYear();
			

			StringBuffer sb = new StringBuffer();
			sb.append("/*dialect*/ select A.FSEQ,nvl(sum(a.CFStagePerformance),0) bqsj,nvl(sum(a.CFProEnergy),0) zxscnh,");
			sb.append(" nvl(sum(a.CFFzproEnergy),0) fzscnh,nvl(sum(a.CFLifeEnergy),0) shnh,nvl(sum(a.CFOtherEnergy),0) qtnh");
			sb.append(" from CT_OPE_ComproductionEntry a");
			sb.append(" left join CT_OPE_Comproduction b on a.fparentid = b.fid");
			sb.append(" left join T_BD_Period c on b.CFReportMonthID = c.fid");
			sb.append(" where a.CFProject = '本月' and b.CFReportingUnitID = '"+ ((AdminOrgUnitInfo) prmtreportingUnit.getData()).getId().toString() + "' AND c.FPeriodYear='"+ year + "' AND b.cfstate = '4'");
			sb.append(" group by A.FSEQ  ORDER BY A.FSEQ");
			IRowSet rowSet = new XRSQLBuilder().appendSql(sb.toString()).executeQuery();
			int rowindex = 10;
			while (rowSet.next()) {
				int column = 2;
				for (int j = 3; j < this.kdtEntrys.getColumnCount()-7; j++) {
					this.kdtEntrys.getCell(rowindex, j).setValue(rowSet.getBigDecimal(column));
					column++;
				}
				BigDecimal a3 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(10, "stagePerformance").getValue());
				BigDecimal a6 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(19, "stagePerformance").getValue());
				BigDecimal abc20 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(11, "stagePerformance").getValue());
				this.kdtEntrys.getCell(10, "periodCon").setValue(Double.parseDouble(String.valueOf((a3.compareTo(BigDecimal.ZERO))!=0?a6.divide(a3,  4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
				this.kdtEntrys.getCell(11, "periodCon").setValue(Double.parseDouble(String.valueOf((abc20.compareTo(BigDecimal.ZERO))!=0?a6.divide(abc20,  4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
				
				BigDecimal abc1 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(10, "samePerformance").getValue());
				BigDecimal abc2 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(11, "samePerformance").getValue());
				BigDecimal abc3 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(12, "samePerformance").getValue());
				BigDecimal abc4 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(13, "samePerformance").getValue());
				BigDecimal abc5 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(14, "samePerformance").getValue());
				BigDecimal abc6 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(15, "samePerformance").getValue());
				BigDecimal abc7 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(16, "samePerformance").getValue());
				BigDecimal abc8 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(17, "samePerformance").getValue());
				BigDecimal abc9 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(18, "samePerformance").getValue());
				BigDecimal abc10 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(19, "samePerformance").getValue());
				
				 BigDecimal a7 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(11, "stagePerformance").getValue());
				 BigDecimal a8 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(12, "stagePerformance").getValue());
				 BigDecimal a9 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(13, "stagePerformance").getValue());
				 BigDecimal a11 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(14, "stagePerformance").getValue());
				 BigDecimal a12 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(15, "stagePerformance").getValue());
				 BigDecimal a13 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(16, "stagePerformance").getValue());
				 BigDecimal a14 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(17, "stagePerformance").getValue());
				 BigDecimal a15 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(18, "stagePerformance").getValue());
				 
				 this.kdtEntrys.getCell(10, "increaseDecrease").setValue(a3.subtract(abc1));
				 this.kdtEntrys.getCell(11, "increaseDecrease").setValue(a7.subtract(abc2));
				 this.kdtEntrys.getCell(12, "increaseDecrease").setValue(a8.subtract(abc3));
				 this.kdtEntrys.getCell(13, "increaseDecrease").setValue(a9.subtract(abc4));
				 this.kdtEntrys.getCell(14, "increaseDecrease").setValue(a11.subtract(abc5));
				 this.kdtEntrys.getCell(15, "increaseDecrease").setValue(a12.subtract(abc6));
				 this.kdtEntrys.getCell(16, "increaseDecrease").setValue(a13.subtract(abc7));
				 this.kdtEntrys.getCell(17, "increaseDecrease").setValue(a14.subtract(abc8));
				 this.kdtEntrys.getCell(18, "increaseDecrease").setValue(a15.subtract(abc9));
				 this.kdtEntrys.getCell(19, "increaseDecrease").setValue(a6.subtract(abc10));
				 
				 
				 
				 this.kdtEntrys.getCell(12, "periodCon").setValue(Double.parseDouble(String.valueOf((a7.compareTo(BigDecimal.ZERO))!=0?a8.divide(a7,  4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
				 this.kdtEntrys.getCell(13, "periodCon").setValue(Double.parseDouble(String.valueOf((a7.compareTo(BigDecimal.ZERO))!=0?a9.divide(a7,  4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
				 this.kdtEntrys.getCell(14, "periodCon").setValue(Double.parseDouble(String.valueOf((a7.compareTo(BigDecimal.ZERO))!=0?a11.divide(a7,  4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
				 this.kdtEntrys.getCell(15, "periodCon").setValue(Double.parseDouble(String.valueOf((a7.compareTo(BigDecimal.ZERO))!=0?a12.divide(a7,  4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
				 this.kdtEntrys.getCell(16, "periodCon").setValue(Double.parseDouble(String.valueOf((a7.compareTo(BigDecimal.ZERO))!=0?a13.divide(a7,  4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
				 this.kdtEntrys.getCell(17, "periodCon").setValue(Double.parseDouble(String.valueOf((a7.compareTo(BigDecimal.ZERO))!=0?a14.divide(a7,  4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
				 this.kdtEntrys.getCell(18, "periodCon").setValue(Double.parseDouble(String.valueOf((a7.compareTo(BigDecimal.ZERO))!=0?a15.divide(a7,  4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
				 this.kdtEntrys.getCell(19, "periodCon").setValue(Double.parseDouble(String.valueOf((a7.compareTo(BigDecimal.ZERO))!=0?a6.divide(a7,  4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
				 
				//累计值吞吐量的去年同期单耗=能耗折算量合计（吨标煤）去年同期实绩/吞吐量去年实绩
					BigDecimal aaaaa = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(19, "samePerformance").getValue());
					BigDecimal bbbbb = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(10, "samePerformance").getValue());
					BigDecimal bbbbb00 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(11, "samePerformance").getValue());
					kdtEntrys.getCell(10, "samePeriod").setValue(Double.parseDouble(String.valueOf((bbbbb.compareTo(BigDecimal.ZERO))!=0?aaaaa.divide(bbbbb,  4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
					kdtEntrys.getCell(11, "samePeriod").setValue(Double.parseDouble(String.valueOf((bbbbb00.compareTo(BigDecimal.ZERO))!=0?aaaaa.divide(bbbbb00,  4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));

					//累计水、电等去年同期单耗=去年同期实绩/去年自然吨
					BigDecimal aaaaa0 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(12, "samePerformance").getValue());
					BigDecimal aaaaa1 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(13, "samePerformance").getValue());
					BigDecimal aaaaa2 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(14, "samePerformance").getValue());
					BigDecimal aaaaa3 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(15, "samePerformance").getValue());
					BigDecimal aaaaa4 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(16, "samePerformance").getValue());
					BigDecimal aaaaa5 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(17, "samePerformance").getValue());
					BigDecimal aaaaa6 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(18, "samePerformance").getValue());
					BigDecimal aaaaa7 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(19, "samePerformance").getValue());
					BigDecimal bbbbb0 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(11, "samePerformance").getValue());
					kdtEntrys.getCell(12, "samePeriod").setValue(Double.parseDouble(String.valueOf((bbbbb0.compareTo(BigDecimal.ZERO))!=0?aaaaa0.divide(bbbbb0, 4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
					kdtEntrys.getCell(13, "samePeriod").setValue(Double.parseDouble(String.valueOf((bbbbb0.compareTo(BigDecimal.ZERO))!=0?aaaaa1.divide(bbbbb0, 4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
					kdtEntrys.getCell(14, "samePeriod").setValue(Double.parseDouble(String.valueOf((bbbbb0.compareTo(BigDecimal.ZERO))!=0?aaaaa2.divide(bbbbb0, 4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
					kdtEntrys.getCell(15, "samePeriod").setValue(Double.parseDouble(String.valueOf((bbbbb0.compareTo(BigDecimal.ZERO))!=0?aaaaa3.divide(bbbbb0, 4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
					kdtEntrys.getCell(16, "samePeriod").setValue(Double.parseDouble(String.valueOf((bbbbb0.compareTo(BigDecimal.ZERO))!=0?aaaaa4.divide(bbbbb0, 4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
					kdtEntrys.getCell(17, "samePeriod").setValue(Double.parseDouble(String.valueOf((bbbbb0.compareTo(BigDecimal.ZERO))!=0?aaaaa5.divide(bbbbb0, 4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
					kdtEntrys.getCell(18, "samePeriod").setValue(Double.parseDouble(String.valueOf((bbbbb0.compareTo(BigDecimal.ZERO))!=0?aaaaa6.divide(bbbbb0, 4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
					kdtEntrys.getCell(19, "samePeriod").setValue(Double.parseDouble(String.valueOf((bbbbb0.compareTo(BigDecimal.ZERO))!=0?aaaaa7.divide(bbbbb0, 4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
					
				 
				 
					for (int i = 10; i < 20; i++) {
						 if(this.kdtEntrys.getRowCount()>=20){
							 BigDecimal a1 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(rowindex, "samePerformance").getValue());
							 BigDecimal a2 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(rowindex, "increaseDecrease").getValue());
							 BigDecimal a16 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(rowindex, "periodCon").getValue());
							 BigDecimal a4 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(rowindex, "samePeriod").getValue());
							 BigDecimal a17 = a16.subtract(a4);
							 this.kdtEntrys.getCell(rowindex, "increaseRate").setValue(Double.parseDouble(String.valueOf((a1.compareTo(BigDecimal.ZERO))!=0?a2.divide(a1,  4, BigDecimal.ROUND_UP).multiply(new BigDecimal("100")):BigDecimal.ZERO)));
							 this.kdtEntrys.getCell(rowindex, "increaseRate1").setValue(Double.parseDouble(String.valueOf((a4.compareTo(BigDecimal.ZERO))!=0?a17.divide(a4,  4, BigDecimal.ROUND_UP).multiply(new BigDecimal("100")):BigDecimal.ZERO)));
							 
							
						 }
					}
					
					BigDecimal a28 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(10, "periodCon").getValue());
					BigDecimal a29 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(11, "periodCon").getValue());
					BigDecimal a30 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(12, "periodCon").getValue());
					BigDecimal a31 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(13, "periodCon").getValue());
					BigDecimal a32 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(14, "periodCon").getValue());
					BigDecimal a33 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(15, "periodCon").getValue());
					BigDecimal a34 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(16, "periodCon").getValue());
					BigDecimal a35 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(17, "periodCon").getValue());
					BigDecimal a36 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(18, "periodCon").getValue());
					BigDecimal a37 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(19, "periodCon").getValue());
					
					BigDecimal a38 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(10, "samePeriod").getValue());
					BigDecimal a39 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(11, "samePeriod").getValue());
					BigDecimal a40 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(12, "samePeriod").getValue());
					BigDecimal a41 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(13, "samePeriod").getValue());
					BigDecimal a42 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(14, "samePeriod").getValue());
					BigDecimal a43 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(15, "samePeriod").getValue());
					BigDecimal a44 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(16, "samePeriod").getValue());
					BigDecimal a45 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(17, "samePeriod").getValue());
					BigDecimal a46 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(18, "samePeriod").getValue());
					BigDecimal a47 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(19, "samePeriod").getValue());
					
					 
					 this.kdtEntrys.getCell(10, "excessSection").setValue((a28.subtract(a38)).multiply(a3));
					 this.kdtEntrys.getCell(11, "excessSection").setValue((a29.subtract(a39)).multiply(abc20));
					 this.kdtEntrys.getCell(12, "excessSection").setValue((a30.subtract(a40)).multiply(a7));
					 this.kdtEntrys.getCell(13, "excessSection").setValue((a31.subtract(a41)).multiply(a7));
					 this.kdtEntrys.getCell(14, "excessSection").setValue((a32.subtract(a42)).multiply(a7));
					 this.kdtEntrys.getCell(15, "excessSection").setValue((a33.subtract(a43)).multiply(a7));
					 this.kdtEntrys.getCell(16, "excessSection").setValue((a34.subtract(a44)).multiply(a7));
					 this.kdtEntrys.getCell(17, "excessSection").setValue((a35.subtract(a45)).multiply(a7));
					 this.kdtEntrys.getCell(18, "excessSection").setValue((a36.subtract(a46)).multiply(a7));
					 this.kdtEntrys.getCell(19, "excessSection").setValue((a37.subtract(a47)).multiply(a7));
					 
					
				 rowindex+=1;
			}
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	protected void prmtreportingUnit_dataChanged(DataChangeEvent e)
			throws Exception {
		super.prmtreportingUnit_dataChanged(e);
		setEntry();
//		setEntry1();
	}


	protected void prmtreportMonth_dataChanged(DataChangeEvent e)
			throws Exception {
		super.prmtreportMonth_dataChanged(e);
		setEntry();
//		setEntry1();
	}
	
	
	//本月的去年同期
	private void setEntry() throws SQLException, BOSException {
		if (prmtreportingUnit.getData() == null ||prmtreportMonth.getData()==null)
			return;
		String colName[] = {"samePerformance"} ;
		String id = ((PeriodInfo)prmtreportMonth.getData()).getId().toString();
		PeriodInfo perInfo;
		try {
			perInfo = PeriodFactory.getRemoteInstance().getPeriodInfo(new ObjectUuidPK(id));
			int year = perInfo.getPeriodYear()-1;
			int month = perInfo.getPeriodNumber();
			
			StringBuffer sb = new StringBuffer();
			sb.append("/*dialect*/ select A.FSEQ,");
			sb.append(" nvl(sum(a.CFStagePerformance),0) qntq");
			sb.append(" from CT_OPE_ComproductionEntry a");
			sb.append(" left join CT_OPE_Comproduction b on a.fparentid = b.fid");
			sb.append(" left join T_BD_Period c on b.CFReportMonthID = c.fid");
			sb.append(" where a.CFProject = '本月' and b.CFReportingUnitID = '"
					+ ((AdminOrgUnitInfo) prmtreportingUnit.getData()).getId().toString() + "' AND c.fPeriodYear='" + year + "'  AND C.FPeriodNumber='" + month + "'   AND b.cfstate = '4'");
			sb.append(" group by A.FSEQ  ORDER BY A.FSEQ");
			IRowSet rowSet = new XRSQLBuilder().appendSql(sb.toString()).executeQuery();
			if(rowSet.size()>0)
			{
				int rowindex = 0;
				while(rowSet.next())
				{
					this.kdtEntrys.getCell(rowindex, colName[0]).setValue(rowSet.getBigDecimal(2));
//					this.kdtEntrys.getCell(rowindex, colName[1]).setValue(rowSet.getBigDecimal(3));
					rowindex+=1;
				}
			}
			else
			{
				int column = 2;
				for (int i = 0; i < 10; i++) {
					for (int j = 0; j < colName.length; j++) {
						this.kdtEntrys.getCell(i, colName[j]).setValue(null);
					}
				}
			}
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
    //累计的去年同期
	private void setEntry1() throws SQLException, BOSException {
		if (prmtreportingUnit.getData() == null ||prmtreportMonth.getData()==null)
			return;
		String colName[] = {"samePerformance"} ;
		String id = ((PeriodInfo)prmtreportMonth.getData()).getId().toString();
		PeriodInfo perInfo;
		try {
			perInfo = PeriodFactory.getRemoteInstance().getPeriodInfo(new ObjectUuidPK(id));
			int year = perInfo.getPeriodYear();
//			int month = perInfo.getPeriodNumber();
			
			StringBuffer sb = new StringBuffer();
			sb.append("/*dialect*/ select A.FSEQ,");
			sb.append(" nvl(sum(a.CFsamePerformance),0) qntq");
			sb.append(" from CT_OPE_ComproductionEntry a");
			sb.append(" left join CT_OPE_Comproduction b on a.fparentid = b.fid");
			sb.append(" left join T_BD_Period c on b.CFReportMonthID = c.fid");
			sb.append(" where a.CFProject = '本月' and b.CFReportingUnitID = '"
					+ ((AdminOrgUnitInfo) prmtreportingUnit.getData()).getId().toString() + "' AND c.fPeriodYear='" + year + "'   AND b.cfstate = '4'");
			sb.append(" group by A.FSEQ  ORDER BY A.FSEQ");
			IRowSet rowSet = new XRSQLBuilder().appendSql(sb.toString()).executeQuery();
			if(rowSet.size()>0)
			{
				int rowindex = 10;
				while(rowSet.next())
				{
					this.kdtEntrys.getCell(rowindex, colName[0]).setValue(rowSet.getBigDecimal(2));
//					this.kdtEntrys.getCell(rowindex, colName[1]).setValue(rowSet.getBigDecimal(3));
					rowindex+=1;
				}
			}
			else
			{
				int column = 2;
				for (int i = 10; i < 20; i++) {
					for (int j = 0; j < colName.length; j++) {
						this.kdtEntrys.getCell(i, colName[j]).setValue(null);
					}
				}
			}
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		
		for (int i = 2; i <9; i++) {
			/**
			 * 本期实绩（电：4.04;柴油：1.4571；汽油：1.4714；煤炭：0.7143；
			 * 蒸汽：0.129；水：0.857；液化天然气：1.7572；）=装卸生产能耗+辅助生产能耗+生活能耗+其他能耗
			 **/
			
			BigDecimal aa = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(i, "proEnergy").getValue());
			BigDecimal bb = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(i, "fzproEnergy").getValue());
			BigDecimal cc = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(i, "lifeEnergy").getValue());
			BigDecimal dd = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(i, "otherEnergy").getValue());
			kdtEntrys.getCell(i, "stagePerformance").setValue(aa.add(bb).add(cc).add(dd));
			BigDecimal ee = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(2, "stagePerformance").getValue()).multiply(new BigDecimal(4.04));
			BigDecimal ff = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(3, "stagePerformance").getValue()).multiply(new BigDecimal(1.4571));
			BigDecimal gg = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(4, "stagePerformance").getValue()).multiply(new BigDecimal(1.4714));
			BigDecimal hh = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(5, "stagePerformance").getValue()).multiply(new BigDecimal(0.7143));
			BigDecimal ii = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(6, "stagePerformance").getValue()).multiply(new BigDecimal(0.129));
			BigDecimal jj = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(7, "stagePerformance").getValue()).multiply(new BigDecimal(0.857));
			BigDecimal kk = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(8, "stagePerformance").getValue()).multiply(new BigDecimal(1.7572));
			kdtEntrys.getCell(9, "stagePerformance").setValue(ee.add(ff).add(gg).add(hh).add(ii).add(jj).add(kk));
			BigDecimal ee1 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(2, "proEnergy").getValue()).multiply(new BigDecimal(4.04));
			BigDecimal ff1 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(3, "proEnergy").getValue()).multiply(new BigDecimal(1.4571));
			BigDecimal gg1 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(4, "proEnergy").getValue()).multiply(new BigDecimal(1.4714));
			BigDecimal hh1 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(5, "proEnergy").getValue()).multiply(new BigDecimal(0.7143));
			BigDecimal ii1 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(6, "proEnergy").getValue()).multiply(new BigDecimal(0.129));
			BigDecimal jj1 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(7, "proEnergy").getValue()).multiply(new BigDecimal(0.857));
			BigDecimal kk1 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(8, "proEnergy").getValue()).multiply(new BigDecimal(1.7572));
			kdtEntrys.getCell(9, "proEnergy").setValue(ee1.add(ff1).add(gg1).add(hh1).add(ii1).add(jj1).add(kk1));
			BigDecimal ee2 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(2, "fzproEnergy").getValue()).multiply(new BigDecimal(4.04));
			BigDecimal ff2 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(3, "fzproEnergy").getValue()).multiply(new BigDecimal(1.4571));
			BigDecimal gg2 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(4, "fzproEnergy").getValue()).multiply(new BigDecimal(1.4714));
			BigDecimal hh2 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(5, "fzproEnergy").getValue()).multiply(new BigDecimal(0.7143));
			BigDecimal ii2 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(6, "fzproEnergy").getValue()).multiply(new BigDecimal(0.129));
			BigDecimal jj2 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(7, "fzproEnergy").getValue()).multiply(new BigDecimal(0.857));
			BigDecimal kk2 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(8, "fzproEnergy").getValue()).multiply(new BigDecimal(1.7572));
			kdtEntrys.getCell(9, "fzproEnergy").setValue(ee2.add(ff2).add(gg2).add(hh2).add(ii2).add(jj2).add(kk2));
			BigDecimal ee3 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(2, "lifeEnergy").getValue()).multiply(new BigDecimal(4.04));
			BigDecimal ff3 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(3, "lifeEnergy").getValue()).multiply(new BigDecimal(1.4571));
			BigDecimal gg3 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(4, "lifeEnergy").getValue()).multiply(new BigDecimal(1.4714));
			BigDecimal hh3 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(5, "lifeEnergy").getValue()).multiply(new BigDecimal(0.7143));
			BigDecimal ii3 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(6, "lifeEnergy").getValue()).multiply(new BigDecimal(0.129));
			BigDecimal jj3 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(7, "lifeEnergy").getValue()).multiply(new BigDecimal(0.857));
			BigDecimal kk3 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(8, "lifeEnergy").getValue()).multiply(new BigDecimal(1.7572));
			kdtEntrys.getCell(9, "lifeEnergy").setValue(ee3.add(ff3).add(gg3).add(hh3).add(ii3).add(jj3).add(kk3));
			BigDecimal ee4 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(2, "otherEnergy").getValue()).multiply(new BigDecimal(4.04));
			BigDecimal ff4 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(3, "otherEnergy").getValue()).multiply(new BigDecimal(1.4571));
			BigDecimal gg4 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(4, "otherEnergy").getValue()).multiply(new BigDecimal(1.4714));
			BigDecimal hh4 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(5, "otherEnergy").getValue()).multiply(new BigDecimal(0.7143));
			BigDecimal ii4 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(6, "otherEnergy").getValue()).multiply(new BigDecimal(0.129));
			BigDecimal jj4 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(7, "otherEnergy").getValue()).multiply(new BigDecimal(0.857));
			BigDecimal kk4 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(8, "otherEnergy").getValue()).multiply(new BigDecimal(1.7572));
			kdtEntrys.getCell(9, "otherEnergy").setValue(ee4.add(ff4).add(gg4).add(hh4).add(ii4).add(jj4).add(kk4));
		}
		BigDecimal a = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(e.getRowIndex(), "stagePerformance").getValue());//本期实绩
		BigDecimal b = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(e.getRowIndex(), "samePerformance").getValue());//去年同期实绩
		this.kdtEntrys.getCell(e.getRowIndex(), "increaseDecrease").setValue(a.subtract(b));//同比增减 = 本期实绩-去年同期实绩
		BigDecimal c = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(e.getRowIndex(), "increaseDecrease").getValue());//同比增减
		//增减率 = 同比增减/去年同期实绩*100
		 this.kdtEntrys.getCell(e.getRowIndex(), "increaseRate").setValue(Double.parseDouble(String.valueOf((b.compareTo(BigDecimal.ZERO))!=0?c.divide(b,  4, BigDecimal.ROUND_UP).multiply(new BigDecimal("100")):BigDecimal.ZERO)));
		
		  BigDecimal h = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(e.getRowIndex(), "proEnergy").getValue());//装卸生产能耗
		  BigDecimal k = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(e.getRowIndex(), "fzproEnergy").getValue());//辅助生产能耗
		  BigDecimal l = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(e.getRowIndex(), "lifeEnergy").getValue());//生活能耗
		  BigDecimal m = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(e.getRowIndex(), "otherEnergy").getValue());//其他能耗
		  BigDecimal n = h.add(k).add(l).add(m);//本月的耗量
         
		  //去年同期实绩折算量合计
		  BigDecimal ee1 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(2, "samePerformance").getValue()).multiply(new BigDecimal(4.04));
			BigDecimal ff1 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(3, "samePerformance").getValue()).multiply(new BigDecimal(1.4571));
			BigDecimal gg1 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(4, "samePerformance").getValue()).multiply(new BigDecimal(1.4714));
			BigDecimal hh1 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(5, "samePerformance").getValue()).multiply(new BigDecimal(0.7143));
			BigDecimal ii1 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(6, "samePerformance").getValue()).multiply(new BigDecimal(0.129));
			BigDecimal jj1 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(7, "samePerformance").getValue()).multiply(new BigDecimal(0.857));
			BigDecimal kk1 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(8, "samePerformance").getValue()).multiply(new BigDecimal(1.7572));
			kdtEntrys.getCell(9, "samePerformance").setValue(ee1.add(ff1).add(gg1).add(hh1).add(ii1).add(jj1).add(kk1));
			
			//吞吐量的本期单耗=能耗折算量合计（吨标煤）本期实绩/吞吐量本期实绩
				BigDecimal aaaa = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(9, "stagePerformance").getValue());
				BigDecimal bbbb = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(0, "stagePerformance").getValue());
				BigDecimal bbbb00 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(1, "stagePerformance").getValue());
				kdtEntrys.getCell(0, "periodCon").setValue(Double.parseDouble(String.valueOf((bbbb.compareTo(BigDecimal.ZERO))!=0?aaaa.divide(bbbb,  4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
				kdtEntrys.getCell(1, "periodCon").setValue(Double.parseDouble(String.valueOf((bbbb00.compareTo(BigDecimal.ZERO))!=0?aaaa.divide(bbbb00,  4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
				
				
				//吞吐量的去年同期单耗=能耗折算量合计（吨标煤）去年同期实绩/吞吐量去年实绩
				BigDecimal aaaaa = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(9, "samePerformance").getValue());
				BigDecimal bbbbb = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(0, "samePerformance").getValue());
				BigDecimal bbbbb00 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(1, "samePerformance").getValue());
				kdtEntrys.getCell(0, "samePeriod").setValue(Double.parseDouble(String.valueOf((bbbbb.compareTo(BigDecimal.ZERO))!=0?aaaaa.divide(bbbbb,  4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
				kdtEntrys.getCell(1, "samePeriod").setValue(Double.parseDouble(String.valueOf((bbbbb00.compareTo(BigDecimal.ZERO))!=0?aaaaa.divide(bbbbb00,  4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
				
			    BigDecimal cccc = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(9, "stagePerformance").getValue());
				BigDecimal dddd = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(9, "samePerformance").getValue());
				this.kdtEntrys.getCell(9, "increaseDecrease").setValue(cccc.subtract(dddd));
				BigDecimal eeee = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(9, "increaseDecrease").getValue());
				BigDecimal ffff = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(9, "periodCon").getValue());
				BigDecimal gggg = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(9, "samePeriod").getValue());
				BigDecimal hhhh = ffff.subtract(gggg);
				 this.kdtEntrys.getCell(9, "increaseRate").setValue(Double.parseDouble(String.valueOf((dddd.compareTo(BigDecimal.ZERO))!=0?eeee.divide(dddd,  4, BigDecimal.ROUND_UP).multiply(new BigDecimal("100")):BigDecimal.ZERO)));
				 this.kdtEntrys.getCell(9, "increaseRate1").setValue(Double.parseDouble(String.valueOf((gggg.compareTo(BigDecimal.ZERO))!=0?hhhh.divide(gggg,  4, BigDecimal.ROUND_UP).multiply(new BigDecimal("100")):BigDecimal.ZERO)));
				 
				//水、电等本期单耗=本期实绩/自然吨
				BigDecimal aaaa0 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(2, "stagePerformance").getValue());
				BigDecimal aaaa1 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(3, "stagePerformance").getValue());
				BigDecimal aaaa2 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(4, "stagePerformance").getValue());
				BigDecimal aaaa3 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(5, "stagePerformance").getValue());
				BigDecimal aaaa4 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(6, "stagePerformance").getValue());
				BigDecimal aaaa5 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(7, "stagePerformance").getValue());
				BigDecimal aaaa6 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(8, "stagePerformance").getValue());
				BigDecimal aaaa7 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(9, "stagePerformance").getValue());
				BigDecimal bbbb0 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(1, "stagePerformance").getValue());
				kdtEntrys.getCell(2, "periodCon").setValue(Double.parseDouble(String.valueOf((bbbb0.compareTo(BigDecimal.ZERO))!=0?aaaa0.divide(bbbb0, 4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
				kdtEntrys.getCell(3, "periodCon").setValue(Double.parseDouble(String.valueOf((bbbb0.compareTo(BigDecimal.ZERO))!=0?aaaa1.divide(bbbb0, 4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
				kdtEntrys.getCell(4, "periodCon").setValue(Double.parseDouble(String.valueOf((bbbb0.compareTo(BigDecimal.ZERO))!=0?aaaa2.divide(bbbb0, 4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
				kdtEntrys.getCell(5, "periodCon").setValue(Double.parseDouble(String.valueOf((bbbb0.compareTo(BigDecimal.ZERO))!=0?aaaa3.divide(bbbb0, 4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
				kdtEntrys.getCell(6, "periodCon").setValue(Double.parseDouble(String.valueOf((bbbb0.compareTo(BigDecimal.ZERO))!=0?aaaa4.divide(bbbb0, 4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
				kdtEntrys.getCell(7, "periodCon").setValue(Double.parseDouble(String.valueOf((bbbb0.compareTo(BigDecimal.ZERO))!=0?aaaa5.divide(bbbb0, 4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
				kdtEntrys.getCell(8, "periodCon").setValue(Double.parseDouble(String.valueOf((bbbb0.compareTo(BigDecimal.ZERO))!=0?aaaa6.divide(bbbb0, 4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
				kdtEntrys.getCell(9, "periodCon").setValue(Double.parseDouble(String.valueOf((bbbb0.compareTo(BigDecimal.ZERO))!=0?aaaa7.divide(bbbb0, 4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
				
				//水、电等去年同期单耗=去年同期实绩/去年自然吨
				BigDecimal aaaaa0 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(2, "samePerformance").getValue());
				BigDecimal aaaaa1 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(3, "samePerformance").getValue());
				BigDecimal aaaaa2 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(4, "samePerformance").getValue());
				BigDecimal aaaaa3 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(5, "samePerformance").getValue());
				BigDecimal aaaaa4 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(6, "samePerformance").getValue());
				BigDecimal aaaaa5 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(7, "samePerformance").getValue());
				BigDecimal aaaaa6 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(8, "samePerformance").getValue());
				BigDecimal aaaaa7 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(9, "samePerformance").getValue());
				BigDecimal bbbbb0 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(1, "samePerformance").getValue());
				kdtEntrys.getCell(2, "samePeriod").setValue(Double.parseDouble(String.valueOf((bbbbb0.compareTo(BigDecimal.ZERO))!=0?aaaaa0.divide(bbbbb0, 4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
				kdtEntrys.getCell(3, "samePeriod").setValue(Double.parseDouble(String.valueOf((bbbbb0.compareTo(BigDecimal.ZERO))!=0?aaaaa1.divide(bbbbb0, 4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
				kdtEntrys.getCell(4, "samePeriod").setValue(Double.parseDouble(String.valueOf((bbbbb0.compareTo(BigDecimal.ZERO))!=0?aaaaa2.divide(bbbbb0, 4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
				kdtEntrys.getCell(5, "samePeriod").setValue(Double.parseDouble(String.valueOf((bbbbb0.compareTo(BigDecimal.ZERO))!=0?aaaaa3.divide(bbbbb0, 4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
				kdtEntrys.getCell(6, "samePeriod").setValue(Double.parseDouble(String.valueOf((bbbbb0.compareTo(BigDecimal.ZERO))!=0?aaaaa4.divide(bbbbb0, 4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
				kdtEntrys.getCell(7, "samePeriod").setValue(Double.parseDouble(String.valueOf((bbbbb0.compareTo(BigDecimal.ZERO))!=0?aaaaa5.divide(bbbbb0, 4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
				kdtEntrys.getCell(8, "samePeriod").setValue(Double.parseDouble(String.valueOf((bbbbb0.compareTo(BigDecimal.ZERO))!=0?aaaaa6.divide(bbbbb0, 4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
				kdtEntrys.getCell(9, "samePeriod").setValue(Double.parseDouble(String.valueOf((bbbbb0.compareTo(BigDecimal.ZERO))!=0?aaaaa7.divide(bbbbb0, 4, BigDecimal.ROUND_UP):BigDecimal.ZERO)));
				
				
				BigDecimal d = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(e.getRowIndex(), "periodCon").getValue());//本期单耗
				BigDecimal f  = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(e.getRowIndex(), "samePeriod").getValue());//去年同期单耗
				BigDecimal g = d.subtract(f);//同比增减
				//增减率 = 同比增减/去年同期单耗*100
				  this.kdtEntrys.getCell(e.getRowIndex(), "increaseRate1").setValue(Double.parseDouble(String.valueOf((f.compareTo(BigDecimal.ZERO))!=0?g.divide(f,  4, BigDecimal.ROUND_UP).multiply(new BigDecimal("100")):BigDecimal.ZERO)));
				
				  
				  //吞吐量同比节超量(节-，超+)=（本期单耗-去年同期单耗）*吞吐量本期实绩
				BigDecimal qq = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(0, "periodCon").getValue());
				BigDecimal ww = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(0, "samePeriod").getValue());
				BigDecimal rr = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(0, "stagePerformance").getValue());
				BigDecimal uu1 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(1, "stagePerformance").getValue());
				BigDecimal qw = qq.subtract(ww);//本期单耗-去年同期单耗
				kdtEntrys.getCell(0, "excessSection").setValue(qw.multiply(rr));
				kdtEntrys.getCell(1, "excessSection").setValue(qw.multiply(uu1));
				//水、电等同比节超量(节-，超+)=（本期单耗-去年同期单耗）*自然吨本期实绩
				for (int i = 2; i < 10; i++) {
					BigDecimal tt = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(i, "periodCon").getValue());
					BigDecimal yy = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(i, "samePeriod").getValue());
					BigDecimal uu = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(1, "stagePerformance").getValue());
					BigDecimal ty = tt.subtract(yy);//本期单耗-去年同期单耗
					kdtEntrys.getCell(i, "excessSection").setValue(ty.multiply(uu));
				}
				
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		 if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(prmtreportMonth.getText())) {
	 			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"报表月份"});
	 		}
		super.verifyInput(e);
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//		String time = (df.format(this.pkBizDate.getSqlDate())).substring(0, 7);
//		String id = ((AdminOrgUnitInfo)prmtreportingUnit.getData()).getId().toString();
//		StringBuffer sb = new StringBuffer();
//		sb.append("/*dialect*/select to_char(fbizdate,'yyyy-mm') datetime");
//		sb.append(" from CT_OPE_Comproduction");
//		sb.append(" where CFReportingUnitID = '"+id+"'");
//		sb.append(" and to_char(fbizdate,'yyyy-mm')='"+time+"'and fnumber <>'"+editData.getNumber()+"'");
//		IRowSet rowSet = new XRSQLBuilder().appendSql(sb.toString()).executeQuery();
//		if(rowSet.size()>0){
//			MsgBox.showInfo("本单位本月已有公司产能报表，不允许再新增!");
//  			SysUtil.abort();
//		}
	}
}