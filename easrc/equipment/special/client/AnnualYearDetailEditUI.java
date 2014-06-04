/**
 * output package name
 */
package com.kingdee.eas.port.equipment.special.client;

import java.awt.event.*;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.port.equipment.operate.ComproductionInfo;
import com.kingdee.eas.port.equipment.special.AnnualYearDetailFactory;
import com.kingdee.eas.port.equipment.special.AnnualYearDetailInfo;
import com.kingdee.eas.port.equipment.special.IAnnualYearDetail;
import com.kingdee.eas.port.equipment.special.OverhaulNoticeFactory;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.eas.xr.app.XRBillStatusEnum;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTextField;

/**
 * output class name
 */
public class AnnualYearDetailEditUI extends AbstractAnnualYearDetailEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AnnualYearDetailEditUI.class);
    
    /**
     * output class constructor
     */
    public AnnualYearDetailEditUI() throws Exception
    {
        super();
    }
    /**
     * output loadFields method
     */
    public void loadFields()
    {
        super.loadFields();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
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
        return com.kingdee.eas.port.equipment.special.AnnualYearDetailFactory.getRemoteInstance();
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
        com.kingdee.eas.port.equipment.special.AnnualYearDetailInfo objectValue = new com.kingdee.eas.port.equipment.special.AnnualYearDetailInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
		
        return objectValue;
    }
	@Override
	protected void attachListeners() {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void detachListeners() {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected KDTextField getNumberCtrl() {
		// TODO Auto-generated method stub
		return null;
	}

	  protected void initWorkButton()
	    {
	        super.initWorkButton();
	        btnIssued.setIcon(EASResource.getIcon("imgTbtn_makeknown"));
	        btnUnIssued.setIcon(EASResource.getIcon("imgTbtn_fmakeknown"));
	        btnConfirmation.setIcon(EASResource.getIcon("imgTbtn_affirm"));
	        btnUnConfirmation.setIcon(EASResource.getIcon("imgTbtn_faffirm"));
	    }

	public void onLoad() throws Exception {
		txtNumber.setEnabled(false);
		this.kdtEntry.getColumn("seq").getStyleAttributes().setHided(true);
		if(getUIContext().get("FeedInfor")!=null)
		{
			txtNumber.setEnabled(false);
			comboStatus.setEnabled(false);
			txtDescription.setEnabled(false);
		}
		super.onLoad();
		btnIssued.setEnabled(false);
		btnUnIssued.setEnabled(false);
		if(editData.getStatus().equals(XRBillStatusEnum.AUDITED)){
			btnIssued.setEnabled(true);
		}
		if(editData.getStatus().equals(XRBillStatusEnum.RELEASED)){
		    btnUnIssued.setEnabled(true);
		}
		if(getUIContext().get("FeedInfor")!=null)
		{
			this.toolBar.removeAll();
			this.toolBar.add(btnAddNew);
			this.toolBar.add(btnConfirmation);
			this.toolBar.add(btnUnConfirmation);
			
			this.toolBar.add(btnUnIssued);
			this.toolBar.add(btnIssued);
			this.toolBar.add(btnAudit);
			this.toolBar.add(btnUnAudit);
			
			btnUnIssued.setVisible(false);
			btnIssued.setVisible(false);
			btnAudit.setVisible(false);
			btnUnAudit.setVisible(false);
			
			this.kdtEntry.getColumn("zdaNumber").getStyleAttributes().setLocked(true);
			this.kdtEntry.getColumn("planDate").getStyleAttributes().setLocked(true);
			this.kdtEntry.getColumn("endDate").getStyleAttributes().setLocked(true);
			this.kdtEntry.getColumn("checkType").getStyleAttributes().setLocked(true);
			this.kdtEntry.getColumn("beizhu").getStyleAttributes().setLocked(true);
		
			
			this.kdtEntry_detailPanel.getAddNewLineButton().setEnabled(false);
			this.kdtEntry_detailPanel.getInsertLineButton().setEnabled(false);
			this.kdtEntry_detailPanel.getRemoveLinesButton().setEnabled(false);			
			btnAddNew.setVisible(false);
			
			if(editData.getStatus().equals(XRBillStatusEnum.RELEASED)){
				btnConfirmation.setEnabled(true);
				btnUnConfirmation.setEnabled(false);
			}
			else if(editData.getStatus().equals(XRBillStatusEnum.EXECUTION))
			{
				btnConfirmation.setEnabled(false);
				btnUnConfirmation.setEnabled(true);
			}
			
		}
		else{
			btnConfirmation.setVisible(false);
			btnUnConfirmation.setVisible(false);
		}
		
		 KDBizPromptBox kdtE1_equNumber_PromptBox = new KDBizPromptBox();
	        kdtE1_equNumber_PromptBox.setQueryInfo("com.kingdee.eas.port.equipment.record.app.EquIdQuery");
	        kdtE1_equNumber_PromptBox.setVisible(true);
	        kdtE1_equNumber_PromptBox.setEditable(true);
	        kdtE1_equNumber_PromptBox.setDisplayFormat("$number$");
	        kdtE1_equNumber_PromptBox.setEditFormat("$number$");
	        kdtE1_equNumber_PromptBox.setCommitFormat("$number$");
	   	 EntityViewInfo evi = new EntityViewInfo();
			 FilterInfo filter = new FilterInfo();
			 filter.getFilterItems().add(new FilterItemInfo("sbStatus","3",CompareType.NOTEQUALS));
			 String id = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
	 		 filter.getFilterItems().add(new FilterItemInfo("ssOrgUnit.id",id ,CompareType.EQUALS));
			 evi.setFilter(filter);
			kdtE1_equNumber_PromptBox.setEntityViewInfo(evi);
			 KDTDefaultCellEditor kdtEntry_feeType_CellEditor = new KDTDefaultCellEditor(kdtE1_equNumber_PromptBox);
			 kdtEntry.getColumn("zdaNumber").setEditor(kdtEntry_feeType_CellEditor);
	}
	
	
	//确认检测信息
	public void actionConfirmation_actionPerformed(ActionEvent e)throws Exception {
		super.actionConfirmation_actionPerformed(e);
		storeFields();
		editData.setStatus(XRBillStatusEnum.EXECUTION);
	    AnnualYearDetailFactory.getRemoteInstance().update(new ObjectUuidPK(editData.getId()), editData);
		MsgBox.showInfo("确认检测信息成功！");
		this.setOprtState("VIEW");
		loadData();
		
		btnConfirmation.setEnabled(false);
		btnUnConfirmation.setEnabled(true);
        
        setSaved(true);
	}
	
	//反确认检测信息
	public void actionUnConfirmation_actionPerformed(ActionEvent e)throws Exception {
		super.actionUnConfirmation_actionPerformed(e);
		storeFields();
		editData.setStatus(XRBillStatusEnum.RELEASED);
		AnnualYearDetailFactory.getRemoteInstance().update(new ObjectUuidPK(editData.getId()), editData);
		
		this.setOprtState("EDIT");
		loadData();
		unLockUI();
		MsgBox.showInfo("反确认检测信息成功！");
		
		btnConfirmation.setEnabled(true);
		btnUnConfirmation.setEnabled(false);
//		
//		btnUnIssued.setVisible(true);
//		btnUnIssued.setEnabled(true);
//		btnIssued.setVisible(true);
//		btnAudit.setVisible(true);
//		btnUnAudit.setVisible(true);
        
        this.kdtEntry_detailPanel.getAddNewLineButton().setEnabled(false);
		this.kdtEntry_detailPanel.getInsertLineButton().setEnabled(false);
		this.kdtEntry_detailPanel.getRemoveLinesButton().setEnabled(false);
        setSaved(true);
	}
	
	//下达
	protected void btnIssued_actionPerformed(ActionEvent e) throws Exception {
		super.btnIssued_actionPerformed(e);
		if(editData.getStatus().equals(XRBillStatusEnum.AUDITED)){
			storeFields();
			editData.setStatus(XRBillStatusEnum.RELEASED);
			((IAnnualYearDetail)getBillInterface()).update(new ObjectUuidPK(editData.getId()), editData);
			btnUnIssued.setEnabled(true);
			btnIssued.setEnabled(false);
			btnUnAudit.setEnabled(false);
			ObjectUuidPK pk = new ObjectUuidPK(editData.getId());
			setDataObject(getValue(pk));
			loadFields();
			setSaved(true);
		}else{
			MsgBox.showInfo("此单据未审核，不允许下达!");
			SysUtil.abort();
		}
	}
	
	//反下达
	protected void btnUnIssued_actionPerformed(ActionEvent e) throws Exception {
		super.btnUnIssued_actionPerformed(e);
		if(editData.getStatus().equals(XRBillStatusEnum.RELEASED)){
			
	
			storeFields();
			editData.setStatus(XRBillStatusEnum.AUDITED);
			((IAnnualYearDetail)getBillInterface()).update(new ObjectUuidPK(editData.getId()), editData);
			
			
			btnUnIssued.setEnabled(false);
			btnIssued.setEnabled(true);
			ObjectUuidPK pk = new ObjectUuidPK(editData.getId());
			setDataObject(getValue(pk));
			loadFields();
			setSaved(true);
		}else{
			MsgBox.showInfo("此单据未下达，不允许反下达!");
			SysUtil.abort();
		}
	}
	 /**
     * 审核
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAudit_actionPerformed(e);
        btnIssued.setEnabled(true);
        
    }

    /**
     * 反审核
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionUnAudit_actionPerformed(e);
        btnIssued.setEnabled(false);
    	if(editData.getStatus().equals(XRBillStatusEnum.RELEASED)){
    		MsgBox.showInfo("此单据已下达，不允许反审核!");
			SysUtil.abort();
    	}
        
    }
    
    /**
     * 修改
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	if(editData.getStatus().equals(XRBillStatusEnum.RELEASED)){
    		MsgBox.showInfo("此单据已下达，不允许修改!");
			SysUtil.abort();
    	}
        super.actionEdit_actionPerformed(e);
    }

    /**
     * 删除
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	if(editData.getStatus().equals(XRBillStatusEnum.RELEASED)){
    		MsgBox.showInfo("此单据已下达，不允许删除!");
			SysUtil.abort();
    	}
        super.actionRemove_actionPerformed(e);
    }
    
    protected void initDataStatus() {
    	super.initDataStatus();
    	AnnualYearDetailInfo info = (AnnualYearDetailInfo) dataBinder.getValueObject();
    	if ("ADDNEW".equals(getOprtState())) {
    		btnIssued.setEnabled(false);
    		btnUnIssued.setEnabled(false);
    	}else if ("EDIT".equals(getOprtState())) {
    		btnIssued.setEnabled(false);
    		btnUnIssued.setEnabled(false);
    	}else
    	if(info.getStatus() != null&& info.getStatus() == XRBillStatusEnum.TEMPORARILYSAVED){
    		btnIssued.setEnabled(false);
    		btnUnIssued.setEnabled(false);
    	}else
    	if(info.getStatus() != null&& info.getStatus() == XRBillStatusEnum.SUBMITED){
    		btnIssued.setEnabled(false);
    		btnUnIssued.setEnabled(false);
    	}else
    	if(info.getStatus() != null&& info.getStatus() == XRBillStatusEnum.AUDITED){
			btnIssued.setEnabled(true);
			btnUnIssued.setEnabled(false);
		}else
		if(info.getStatus() != null&& info.getStatus() == XRBillStatusEnum.RELEASED){
		    btnUnIssued.setEnabled(true);
			btnIssued.setEnabled(false);
		}
    }
}