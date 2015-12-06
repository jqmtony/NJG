/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.client;

import java.awt.Component;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.swing.SpinnerNumberModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.programming.client.CostAccountPromptBox;
import com.kingdee.eas.fdc.finance.utils.TableHelper;
import com.kingdee.eas.fm.ecore.app.bean.commercialdraft.ContractInformation;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.swing.KDWorkButton;

/**
 * output class name
 */
public class OtherSplitBillEditUI extends AbstractOtherSplitBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(OtherSplitBillEditUI.class);
    
    /**
     * output class constructor
     */
    public OtherSplitBillEditUI() throws Exception
    {
        super();
    }
    /**
     * output loadFields method
     */
    public void loadFields()
    {
        super.loadFields();
        TableHelper.getFootRow(kdtEntrys, new String[]{"rate", "amount"});
    }

    public void onLoad() throws Exception {
    	this.contstate.setEnabled(false);
    	this.contcurProject.setEnabled(false);
    	this.contauditTime.setEnabled(false);
    	this.contBizDate.setEnabled(false);
    	this.contDescription.setVisible(false);
    	this.contcontractNumber.setVisible(false);
    	
    	this.txtadjustAmount.setRequired(true);
    	this.prmtcontract.setRequired(true);
    	this.btnRemove.setVisible(false);
    	super.onLoad();
    	if(this.editData.getBizDate()!=null){
			Calendar cal = Calendar.getInstance();
			cal.setTime(this.editData.getBizDate());
			int year=cal.get(Calendar.YEAR);
			int month=cal.get(Calendar.MONTH)+1;
			
			this.spYear.setValue(year);
			this.spMonth.setValue(month);
		}
    	this.spYear.setModel(new SpinnerNumberModel(this.spYear.getIntegerVlaue().intValue(),1,10000,1));
		this.spMonth.setModel(new SpinnerNumberModel(this.spMonth.getIntegerVlaue().intValue(),1,12,1));
    	chkMenuItemSubmitAndAddNew.setSelected(false);
    	menuSubmitOption.setEnabled(false);
    	CurProjectInfo project = this.editData.getCurProject();
    	if(project != null) {
    		EntityViewInfo evi = new EntityViewInfo();
    		FilterInfo filter = new FilterInfo();
    		evi.setFilter(filter);
    		filter.getFilterItems().add(new FilterItemInfo("curProject.id", project.getId().toString(), CompareType.EQUALS));
    		this.prmtcontract.setEntityViewInfo(evi);
    	}
    	
    	CostAccountPromptBox selector = new CostAccountPromptBox(this);
    	KDBizPromptBox prmtAccount = (KDBizPromptBox) this.kdtEntrys.getColumn("costAccount").getEditor().getComponent();
    	prmtAccount.setDisplayFormat("$name$");
    	prmtAccount.setEditFormat("$longNumber$");
    	prmtAccount.setCommitFormat("$longNumber$");
    	prmtAccount.setSelector(selector);
    	
    	EntityViewInfo entityView = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		if (project != null) {
			filter.getFilterItems().add(new FilterItemInfo("curProject.id", project.getId().toString(), CompareType.EQUALS));
		} else {
			filter.getFilterItems().add(new FilterItemInfo("curProject.id", "error", CompareType.EQUALS));
		}
		entityView.setFilter(filter);
		prmtAccount.setEntityViewInfo(entityView);
		
		KDWorkButton addNewLineButton = this.kdtEntrys_detailPanel.getAddNewLineButton();
		KDWorkButton insertLineButton = this.kdtEntrys_detailPanel.getInsertLineButton();
		KDWorkButton removeLineButton = this.kdtEntrys_detailPanel.getRemoveLinesButton();
		addNewLineButton.removeActionListener(addNewLineButton.getActionListeners()[0]);
		insertLineButton.removeActionListener(insertLineButton.getActionListeners()[0]);
		removeLineButton.removeActionListener(removeLineButton.getActionListeners()[0]);
		
		addNewLineButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionAddLine(e);
			}
		});
		insertLineButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionAddLine(e);
			}
		});
		removeLineButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = kdtEntrys.getSelectManager().getActiveRowIndex();
				if(index == -1) {
					MsgBox.showWarning("没有选中分录,无法删除!");
					SysUtil.abort();
				}
				kdtEntrys.removeRow(index);
				TableHelper.getFootRow(kdtEntrys, new String[]{"rate", "amount"});
			}
		});
    }
    /**
     * 校验
     */
    protected void verifyInput(ActionEvent actionevent) throws Exception {
    	super.verifyInput(actionevent);
    	if(txtNumber.getText() == null || txtNumber.getText().trim().equals("")) {
    		MsgBox.showWarning("单据编号为空!请修改!");
    		SysUtil.abort();
    	}
    	if(txtadjustAmount.getBigDecimalValue() == null) {
    		MsgBox.showWarning("调整金额为空!请修改!");
    		SysUtil.abort();
    	}
    	if(prmtcontract.getValue() == null) {
    		MsgBox.showWarning("合同为空!请修改!");
    		SysUtil.abort();
    	}
    	if(prmtcurProject.getValue() == null) {
    		MsgBox.showWarning("工程项目为空!请修改!");
    		SysUtil.abort();
    	}
    	if(kdtEntrys.getRowCount() == 0) {
    		MsgBox.showWarning("分录不能为空!请修改!");
    		SysUtil.abort();
    	}
    	BigDecimal totalRate = BigDecimal.ZERO;
    	for(int i = 0; i < kdtEntrys.getRowCount(); i++) {
    		IRow row = kdtEntrys.getRow(i);
    		CostAccountInfo account = (CostAccountInfo) row.getCell("costAccount").getValue();
    		BigDecimal rate = (BigDecimal) row.getCell("rate").getValue();
    		BigDecimal amount = (BigDecimal) row.getCell("amount").getValue();
    		if(account == null || rate == null || amount == null) {
    			MsgBox.showWarning("成本科目,拆分比例以及金额不能为空!请修改!");
        		SysUtil.abort();
    		}
    		totalRate = rate.add(totalRate);
    	}
    	if(totalRate.compareTo(FDCHelper.ONE_HUNDRED) != 0) {
    		MsgBox.showWarning("拆分比例之和不等于100，请修改");
    		SysUtil.abort();
    	}
    }
    /**
     * 审核
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
    	if(!editData.getState().equals(FDCBillStateEnum.SUBMITTED)) {
    		MsgBox.showWarning("非提交状态单据无法审核");
    		SysUtil.abort();
    	}
    	super.actionAudit_actionPerformed(e);
    }
    /**
     * 反审核
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
    	if(!editData.getState().equals(FDCBillStateEnum.AUDITTED)) {
    		MsgBox.showWarning("非审核状态单据无法反审核");
    		SysUtil.abort();
    	}
    	super.actionUnAudit_actionPerformed(e);
    }
    /**
     * 
     */
    protected void kdtEntrys_editStopped(KDTEditEvent e) throws Exception {
    	BigDecimal total = this.txtadjustAmount.getBigDecimalValue();
    	BigDecimal totalRate = BigDecimal.ZERO;
    	IRow row = kdtEntrys.getRow(e.getRowIndex());
    	if(kdtEntrys.getColumnKey(e.getColIndex()).equals("rate")) {
    		BigDecimal rate = (BigDecimal) row.getCell("rate").getValue();
    		if(rate != null && rate.compareTo(FDCHelper.ONE_HUNDRED) > 0) {
    			MsgBox.showWarning("拆分比例大于100，请调整!");
    			row.getCell("rate").setValue(null);
    			row.getCell("amount").setValue(null);
    			SysUtil.abort();
    		}
    		row.getCell("amount").setValue(rate == null ? BigDecimal.ZERO : rate.multiply(total).divide(new BigDecimal(100), BigDecimal.ROUND_HALF_UP));
    	}
    	if(kdtEntrys.getColumnKey(e.getColIndex()).equals("amount")) {
    		BigDecimal amount = (BigDecimal) row.getCell("amount").getValue();
    		if(amount != null && amount.compareTo(total) > 0) {
    			MsgBox.showWarning("拆分金额大于总调整金额，请调整!");
    			row.getCell("rate").setValue(null);
    			row.getCell("amount").setValue(null);
    			SysUtil.abort();
    		}
    		row.getCell("rate").setValue(amount == null ? BigDecimal.ZERO : amount.divide(total, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
    	}
    	this.txtadjustAmount.setValue(total);
    	TableHelper.getFootRow(kdtEntrys, new String[]{"rate", "amount"});
    }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * 新增行
     * @param e
     */
    protected void actionAddLine(ActionEvent e) {
    	BigDecimal adjAmount = txtadjustAmount.getBigDecimalValue();
    	if(adjAmount == null) {
    		MsgBox.showWarning("请先填写调整金额!");
    		SysUtil.abort();
    	} else if(adjAmount.compareTo(BigDecimal.ZERO) == 0) {
    		MsgBox.showWarning("调整金额不能为0,请修改!");
    		SysUtil.abort();
    	}
    	kdtEntrys.addRow();
	}
    /**
     * output btnAddLine_actionPerformed method
     */
    protected void btnAddLine_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.btnAddLine_actionPerformed(e);
    }

    /**
     * output menuItemEnterToNextRow_itemStateChanged method
     */
    protected void menuItemEnterToNextRow_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
        super.menuItemEnterToNextRow_itemStateChanged(e);
    }

    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    /**
     * output actionHelp_actionPerformed
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }

    /**
     * output actionAbout_actionPerformed
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionSendMessage_actionPerformed
     */
    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMessage_actionPerformed(e);
    }

    /**
     * output actionCalculator_actionPerformed
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }

    /**
     * output actionExport_actionPerformed
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExport_actionPerformed(e);
    }

    /**
     * output actionExportSelected_actionPerformed
     */
    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelected_actionPerformed(e);
    }

    /**
     * output actionRegProduct_actionPerformed
     */
    public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRegProduct_actionPerformed(e);
    }

    /**
     * output actionPersonalSite_actionPerformed
     */
    public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPersonalSite_actionPerformed(e);
    }

    /**
     * output actionProcductVal_actionPerformed
     */
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }

    /**
     * output actionExportSave_actionPerformed
     */
    public void actionExportSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSave_actionPerformed(e);
    }

    /**
     * output actionExportSelectedSave_actionPerformed
     */
    public void actionExportSelectedSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelectedSave_actionPerformed(e);
    }

    /**
     * output actionKnowStore_actionPerformed
     */
    public void actionKnowStore_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionKnowStore_actionPerformed(e);
    }

    /**
     * output actionAnswer_actionPerformed
     */
    public void actionAnswer_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAnswer_actionPerformed(e);
    }

    /**
     * output actionRemoteAssist_actionPerformed
     */
    public void actionRemoteAssist_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoteAssist_actionPerformed(e);
    }

    /**
     * output actionPopupCopy_actionPerformed
     */
    public void actionPopupCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupCopy_actionPerformed(e);
    }

    /**
     * output actionHTMLForMail_actionPerformed
     */
    public void actionHTMLForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForMail_actionPerformed(e);
    }

    /**
     * output actionExcelForMail_actionPerformed
     */
    public void actionExcelForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForMail_actionPerformed(e);
    }

    /**
     * output actionHTMLForRpt_actionPerformed
     */
    public void actionHTMLForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForRpt_actionPerformed(e);
    }

    /**
     * output actionExcelForRpt_actionPerformed
     */
    public void actionExcelForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForRpt_actionPerformed(e);
    }

    /**
     * output actionLinkForRpt_actionPerformed
     */
    public void actionLinkForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLinkForRpt_actionPerformed(e);
    }

    /**
     * output actionPopupPaste_actionPerformed
     */
    public void actionPopupPaste_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupPaste_actionPerformed(e);
    }

    /**
     * output actionToolBarCustom_actionPerformed
     */
    public void actionToolBarCustom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionToolBarCustom_actionPerformed(e);
    }

    /**
     * output actionCloudFeed_actionPerformed
     */
    public void actionCloudFeed_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudFeed_actionPerformed(e);
    }

    /**
     * output actionCloudShare_actionPerformed
     */
    public void actionCloudShare_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudShare_actionPerformed(e);
    }

    /**
     * output actionCloudScreen_actionPerformed
     */
    public void actionCloudScreen_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudScreen_actionPerformed(e);
    }

    /**
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSave_actionPerformed(e);
    }

    /**
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
    }

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelCancel_actionPerformed(e);
    }

    /**
     * output actionFirst_actionPerformed
     */
    public void actionFirst_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionFirst_actionPerformed(e);
    }

    /**
     * output actionPre_actionPerformed
     */
    public void actionPre_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPre_actionPerformed(e);
    }

    /**
     * output actionNext_actionPerformed
     */
    public void actionNext_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNext_actionPerformed(e);
    }

    /**
     * output actionLast_actionPerformed
     */
    public void actionLast_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLast_actionPerformed(e);
    }

    /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }

    /**
     * output actionCopy_actionPerformed
     */
    public void actionCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopy_actionPerformed(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }

    /**
     * output actionSubmitOption_actionPerformed
     */
    public void actionSubmitOption_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmitOption_actionPerformed(e);
    }

    /**
     * output actionReset_actionPerformed
     */
    public void actionReset_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionReset_actionPerformed(e);
    }

    /**
     * output actionMsgFormat_actionPerformed
     */
    public void actionMsgFormat_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMsgFormat_actionPerformed(e);
    }

    /**
     * output actionAddLine_actionPerformed
     */
    public void actionAddLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddLine_actionPerformed(e);
    }

    /**
     * output actionCopyLine_actionPerformed
     */
    public void actionCopyLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopyLine_actionPerformed(e);
    }

    /**
     * output actionInsertLine_actionPerformed
     */
    public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionInsertLine_actionPerformed(e);
    }

    /**
     * output actionRemoveLine_actionPerformed
     */
    public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoveLine_actionPerformed(e);
    }

    /**
     * output actionCreateFrom_actionPerformed
     */
    public void actionCreateFrom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateFrom_actionPerformed(e);
    }

    /**
     * output actionCopyFrom_actionPerformed
     */
    public void actionCopyFrom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopyFrom_actionPerformed(e);
    }

    /**
     * output actionAuditResult_actionPerformed
     */
    public void actionAuditResult_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAuditResult_actionPerformed(e);
    }

    /**
     * output actionTraceUp_actionPerformed
     */
    public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceUp_actionPerformed(e);
    }

    /**
     * output actionTraceDown_actionPerformed
     */
    public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceDown_actionPerformed(e);
    }

    /**
     * output actionViewSubmitProccess_actionPerformed
     */
    public void actionViewSubmitProccess_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewSubmitProccess_actionPerformed(e);
    }

    /**
     * output actionViewDoProccess_actionPerformed
     */
    public void actionViewDoProccess_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewDoProccess_actionPerformed(e);
    }

    /**
     * output actionMultiapprove_actionPerformed
     */
    public void actionMultiapprove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMultiapprove_actionPerformed(e);
    }

    /**
     * output actionNextPerson_actionPerformed
     */
    public void actionNextPerson_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNextPerson_actionPerformed(e);
    }

    /**
     * output actionStartWorkFlow_actionPerformed
     */
    public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionStartWorkFlow_actionPerformed(e);
    }

    /**
     * output actionVoucher_actionPerformed
     */
    public void actionVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionVoucher_actionPerformed(e);
    }

    /**
     * output actionDelVoucher_actionPerformed
     */
    public void actionDelVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionDelVoucher_actionPerformed(e);
    }

    /**
     * output actionWorkFlowG_actionPerformed
     */
    public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkFlowG_actionPerformed(e);
    }

    /**
     * output actionCreateTo_actionPerformed
     */
    public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateTo_actionPerformed(e);
    }

    /**
     * output actionSendingMessage_actionPerformed
     */
    public void actionSendingMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendingMessage_actionPerformed(e);
    }

    /**
     * output actionSignature_actionPerformed
     */
    public void actionSignature_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSignature_actionPerformed(e);
    }

    /**
     * output actionWorkflowList_actionPerformed
     */
    public void actionWorkflowList_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkflowList_actionPerformed(e);
    }

    /**
     * output actionViewSignature_actionPerformed
     */
    public void actionViewSignature_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewSignature_actionPerformed(e);
    }

    /**
     * output actionSendMail_actionPerformed
     */
    public void actionSendMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMail_actionPerformed(e);
    }

    /**
     * output actionLocate_actionPerformed
     */
    public void actionLocate_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLocate_actionPerformed(e);
    }

    /**
     * output actionNumberSign_actionPerformed
     */
    public void actionNumberSign_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNumberSign_actionPerformed(e);
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.OtherSplitBillFactory.getRemoteInstance();
    }

    /**
     * output createNewDetailData method
     */
    protected IObjectValue createNewDetailData(KDTable table)
    {
		
        return null;
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
    	ContractBillInfo contractInfo = (ContractBillInfo) getUIContext().get("Contract");
    	Date bizDate = (Date) getUIContext().get("BizDate");
    	CurProjectInfo project = (CurProjectInfo) getUIContext().get("project");
    	
        com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.OtherSplitBillInfo objectValue = new com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.OtherSplitBillInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
		
        if(bizDate == null)
        	bizDate = Calendar.getInstance().getTime();
        objectValue.setBizDate(bizDate);
        objectValue.setContract(contractInfo);
        objectValue.setCurProject(project);
        
        return objectValue;
    }

}