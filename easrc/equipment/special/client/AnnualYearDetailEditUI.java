/**
 * output package name
 */
package com.kingdee.eas.port.equipment.special.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.DataAccessException;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.port.equipment.base.enumbase.CheckResult;
import com.kingdee.eas.port.equipment.base.enumbase.CheckType;
import com.kingdee.eas.port.equipment.record.EquIdFactory;
import com.kingdee.eas.port.equipment.record.EquIdInfo;
import com.kingdee.eas.port.equipment.record.client.EquIdEditUI;
import com.kingdee.eas.port.equipment.special.AnnualYearDetailEntryInfo;
import com.kingdee.eas.port.equipment.special.AnnualYearDetailFactory;
import com.kingdee.eas.port.equipment.special.AnnualYearDetailInfo;
import com.kingdee.eas.port.equipment.special.AnnualYearPlanEntryCollection;
import com.kingdee.eas.port.equipment.special.AnnualYearPlanEntryFactory;
import com.kingdee.eas.port.equipment.special.AnnualYearPlanEntryInfo;
import com.kingdee.eas.port.equipment.special.AnnualYearPlanFactory;
import com.kingdee.eas.port.equipment.special.AnnualYearPlanInfo;
import com.kingdee.eas.port.equipment.special.IAnnualYearDetail;
import com.kingdee.eas.port.equipment.uitl.ToolHelp;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.eas.xr.app.XRBillStatusEnum;
import com.kingdee.eas.xr.helper.Tool;

/**
 * output class name
 */
public class AnnualYearDetailEditUI extends AbstractAnnualYearDetailEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AnnualYearDetailEditUI.class);
    private KDBizPromptBox kdtE1_equNumber_PromptBox = null;
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
    	if(editData.getStatus().equals(XRBillStatusEnum.RELEASED)){
    		MsgBox.showInfo("此单据已下达，不需要提交!");
			SysUtil.abort();
    	}
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
    	Tool.checkGroupAddNew();
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
	        btnConfirm.setIcon(EASResource.getIcon("imgTbtn_affirm"));
	        btnUnConfirm.setIcon(EASResource.getIcon("imgTbtn_faffirm"));
	    }

	public void onLoad() throws Exception {
		txtNumber.setEnabled(false);
		pkBizDate.setVisible(false);
		contBizDate.setVisible(false);
		this.kdtEntry.getColumn("seq").getStyleAttributes().setHided(true);
		this.kdtEntry.getColumn("check").getStyleAttributes().setLocked(true);
//		this.kdtEntry.getColumn("planDate").getStyleAttributes().setLocked(true);
		this.kdtEntry.getColumn("endDate").getStyleAttributes().setLocked(true);
		this.kdtEntry.getColumn("sjjcrq").getStyleAttributes().setLocked(true);
		this.kdtEntry.getColumn("checkType").getStyleAttributes().setLocked(true);
		this.kdtEntry.getColumn("beizhu").getStyleAttributes().setLocked(true);
		this.kdtEntry.getColumn("result").getStyleAttributes().setLocked(true);
		prmtuseDpatmen.setEnabled(false);
		if(getUIContext().get("FeedInfor")!=null)
		{
			txtNumber.setEnabled(false);
			comboStatus.setEnabled(false);
			txtDescription.setEnabled(false);
		}
//		kdtEntry_detailPanel.getAddNewLineButton().setVisible(false);
//		kdtEntry_detailPanel.getRemoveLinesButton().setVisible(false);
//		kdtEntry_detailPanel.getInsertLineButton().setVisible(false);
		super.onLoad();
		btnIssued.setEnabled(false);
		btnUnIssued.setEnabled(false);
		btnConfirm.setEnabled(false);
		btnUnConfirm.setEnabled(false);
		if(editData.getStatus().equals(XRBillStatusEnum.AUDITED)){
			btnIssued.setEnabled(true);
		}
		this.kdtEntry.getColumn("useUnit").getStyleAttributes().setHided(true);
		if(editData.getStatus().equals(XRBillStatusEnum.RELEASED)&&! editData.isIsConfirmation()){
		    btnUnIssued.setEnabled(true);
		    btnConfirm.setEnabled(true);
		}
		if(editData.isIsConfirmation()){
			btnConfirm.setEnabled(false);
			btnUnConfirm.setEnabled(true);
			btnEdit.setEnabled(false);
			btnAddNew.setEnabled(false);
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
			this.kdtEntry.getColumn("result").getStyleAttributes().setLocked(false);
			this.kdtEntry.getColumn("check").getStyleAttributes().setLocked(false);
			this.kdtEntry.getColumn("sjjcrq").getStyleAttributes().setLocked(false);
//			this.pkBizDate.setEnabled(true);
		
			
//			this.kdtEntry_detailPanel.getAddNewLineButton().setEnabled(false);
//			this.kdtEntry_detailPanel.getInsertLineButton().setEnabled(false);
//			this.kdtEntry_detailPanel.getRemoveLinesButton().setEnabled(false);			
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
		
		EntityViewFilterEquId();
			 
			KDWorkButton addNew = kdtEntry_detailPanel.getAddNewLineButton();
			KDWorkButton insert = kdtEntry_detailPanel.getInsertLineButton();
			KDWorkButton remove = kdtEntry_detailPanel.getRemoveLinesButton();
			if(addNew.getActionListeners()[0]!=null)
				addNew.removeActionListener(addNew.getActionListeners()[0]);
			if(insert.getActionListeners()[0]!=null)
				insert.removeActionListener(insert.getActionListeners()[0]);
			if(remove.getActionListeners()[0]!=null)
				remove.removeActionListener(remove.getActionListeners()[0]);
			
			addNew.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e) {
					try {
						kdtEntry_detailPanel.actionAddnewLine_actionPerformed(e);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					EntityViewFilterEquId();
				}
				
			});
			insert.addActionListener(new ActionListener(){
				
				public void actionPerformed(ActionEvent e) {
					try {
						kdtEntry_detailPanel.actionInsertLine_actionPerformed(e);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					EntityViewFilterEquId();
				}
				
			});
			remove.addActionListener(new ActionListener(){
				
				public void actionPerformed(ActionEvent e) {
					try {
						kdtEntry_detailPanel.actionRemoveLine_actionPerformed(e);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					EntityViewFilterEquId();
				}
				
			});
			 
	}
	
	public void kdtEntry_Changed(int rowIndex, int colIndex) throws Exception {
	}
	
	protected void kdtEntry_editStopped(KDTEditEvent e) throws Exception {
		super.kdtEntry_editStopped(e);
		if(e.getRowIndex()==-1)
			return;
		if(e.getColIndex()==this.kdtEntry.getColumnIndex("zdaNumber"))
		{
			if(this.kdtEntry.getCell(e.getRowIndex(), "zdaNumber").getValue() != null){
				String id = ((EquIdInfo)this.kdtEntry.getCell(e.getRowIndex(), "zdaNumber").getValue()).getId().toString();
				EquIdInfo eqInfo = 	EquIdFactory.getRemoteInstance().getEquIdInfo(new ObjectUuidPK(id));
				this.kdtEntry.removeRow(e.getRowIndex());
				IRow row = this.kdtEntry.addRow();
				row.getCell("zdaNumber").setValue(eqInfo);
				if(eqInfo.isCityTest()&&eqInfo.isPortTest()){
					InitEntry(row, eqInfo, CheckType.city);
					row = kdtEntry.addRow();
					row.getCell("zdaNumber").setValue(eqInfo);
					InitEntry(row, eqInfo, CheckType.port);
				}
				else
				{
					if(eqInfo.isCityTest())
					{
						InitEntry(row, eqInfo, CheckType.city);
					}
					if(eqInfo.isPortTest())
					{
						InitEntry(row, eqInfo, CheckType.port);
					}
					if(eqInfo.isChuanCheck())
					{
						InitEntry(row, eqInfo, CheckType.chuan);
					}
				}
				EntityViewFilterEquId();
			  }
		}
	}
	
	private void InitEntry(IRow row,EquIdInfo equIdInfo,CheckType checktype) throws DataAccessException, BOSException
	{
		AnnualYearDetailEntryInfo entryInfo = new AnnualYearDetailEntryInfo();
		entryInfo.setId(BOSUuid.create(entryInfo.getBOSType()));
		row.setUserObject(entryInfo);
		row.getCell("equipmentName").setValue(UIRuleUtil.getString(UIRuleUtil.getProperty((IObjectValue)row.getCell("zdaNumber").getValue(),"name")));
		row.getCell("code").setValue(UIRuleUtil.getString(UIRuleUtil.getProperty((IObjectValue)row.getCell("zdaNumber").getValue(),"code")));
		row.getCell("useUnit").setValue(UIRuleUtil.getString(UIRuleUtil.getProperty((IObjectValue)row.getCell("zdaNumber").getValue(),"ssOrgUnit.name")));
		row.getCell("state").setValue(UIRuleUtil.getString(UIRuleUtil.getProperty((IObjectValue)row.getCell("zdaNumber").getValue(),"tzsbStatus")));
		row.getCell("address").setValue(UIRuleUtil.getString(UIRuleUtil.getProperty((IObjectValue)row.getCell("zdaNumber").getValue(),"address.name")));
		row.getCell("companyNumber").setValue(UIRuleUtil.getString(UIRuleUtil.getProperty((IObjectValue)row.getCell("zdaNumber").getValue(),"innerNumber")));
		row.getCell("NO").setValue(UIRuleUtil.getString(UIRuleUtil.getProperty((IObjectValue)row.getCell("zdaNumber").getValue(),"model")));
		row.getCell("engineNumber").setValue(UIRuleUtil.getString(UIRuleUtil.getProperty((IObjectValue)row.getCell("zdaNumber").getValue(),"engineNumber")));
		row.getCell("carNumber").setValue(UIRuleUtil.getString(UIRuleUtil.getProperty((IObjectValue)row.getCell("zdaNumber").getValue(),"carNumber")));
		row.getCell("weight").setValue(UIRuleUtil.getString(UIRuleUtil.getProperty((IObjectValue)row.getCell("zdaNumber").getValue(),"ratedWeight")));
		row.getCell("useDate").setValue(UIRuleUtil.getString(UIRuleUtil.getProperty((IObjectValue)row.getCell("zdaNumber").getValue(),"qyDate")));
		row.getCell("createUnit").setValue(UIRuleUtil.getString(UIRuleUtil.getProperty((IObjectValue)row.getCell("zdaNumber").getValue(),"mader")));
		row.getCell("checkType").setValue(checktype);
		if(checktype.equals(checktype.city)&&equIdInfo.getDayone() !=null){
			row.getCell("planDate").setValue(equIdInfo.getDayone());
		}
		if(checktype.equals(checktype.city)&&equIdInfo.getTextDate1() !=null){
			row.getCell("endDate").setValue(equIdInfo.getTextDate1());
		}
		if(checktype.equals(checktype.port)&& equIdInfo.getDaytow() !=null){
			row.getCell("planDate").setValue(equIdInfo.getDaytow());
		}
		if(checktype.equals(checktype.port)&& equIdInfo.getTestDay() !=null){
			row.getCell("endDate").setValue(equIdInfo.getTestDay());
		}
		if(checktype.equals(checktype.chuan)&& equIdInfo.getDaytow() !=null){
			row.getCell("planDate").setValue(equIdInfo.getDaytow());
		}
		if(checktype.equals(checktype.chuan)&& equIdInfo.getTestDay() !=null){
			row.getCell("endDate").setValue(equIdInfo.getTestDay());
		}
	}
	
	private void EntityViewFilterEquId()
	{
		Set<String> set = new HashSet<String>(); 
		set.add("9999999999999999999999999");
		for (int i = 0; i < this.kdtEntry.getRowCount(); i++) 
		{
			IRow row = this.kdtEntry.getRow(i);
			if(UIRuleUtil.isNotNull(row.getCell("zdaNumber").getValue()))
				set.add(((EquIdInfo)row.getCell("zdaNumber").getValue()).getId().toString());
		}
		kdtE1_equNumber_PromptBox = new KDBizPromptBox();
        kdtE1_equNumber_PromptBox.setQueryInfo("com.kingdee.eas.port.equipment.record.app.EquIdQuery");
        kdtE1_equNumber_PromptBox.setVisible(true);
        kdtE1_equNumber_PromptBox.setEditable(true);
        kdtE1_equNumber_PromptBox.setDisplayFormat("$number$");
        kdtE1_equNumber_PromptBox.setEditFormat("$number$");
        kdtE1_equNumber_PromptBox.setCommitFormat("$number$");
        
   	    EntityViewInfo evi = new EntityViewInfo();
		 FilterInfo filter = new FilterInfo();
//		 filter.getFilterItems().add(new FilterItemInfo("sbStatus","1",CompareType.EQUALS));
		 String id = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
 		 filter.getFilterItems().add(new FilterItemInfo("ssOrgUnit.id",id ,CompareType.EQUALS));
 		 filter.getFilterItems().add(new FilterItemInfo("special",1 ,CompareType.EQUALS));
// 		 filter.getFilterItems().add(new FilterItemInfo("id",set ,CompareType.NOTINCLUDE));
		 evi.setFilter(filter);
		 
//		kdtE1_equNumber_PromptBox.setEntityViewInfo(evi);
		 kdtE1_equNumber_PromptBox.setSelector(ToolHelp.initPrmtEquIdByF7Color(evi, false));
		 KDTDefaultCellEditor kdtEntry_feeType_CellEditor = new KDTDefaultCellEditor(kdtE1_equNumber_PromptBox);
		 kdtEntry.getColumn("zdaNumber").setEditor(kdtEntry_feeType_CellEditor);
	}
	
	
	//确认检测信息
	public void actionConfirmation_actionPerformed(ActionEvent e)throws Exception {
		for(int i = 0;i<kdtEntry.getRowCount();i++){
			if(UIRuleUtil.getBoolean(kdtEntry.getCell(i, "check").getValue())){
				CheckResult result = UIRuleUtil.isNotNull(kdtEntry.getCell(i, "result").getValue())?(CheckResult)kdtEntry.getCell(i, "result").getValue():null;
				if(result==null||result.equals(CheckResult.NULL))
				{
					MsgBox.showInfo("第{"+(i+1)+"}行设备是否检测已勾选，检测结果不能为空!");
					SysUtil.abort();
				}
			}
		}
		super.actionConfirmation_actionPerformed(e);
		storeFields();
		editData.setStatus(XRBillStatusEnum.EXECUTION);
	    AnnualYearDetailFactory.getRemoteInstance().update(new ObjectUuidPK(editData.getId()), editData);
		if(getUIContext().get("SId")!=null){
			for (int j = 0; j < kdtEntry.getRowCount(); j++) {
				String id3 = (String) getUIContext().get("SId");
				AnnualYearPlanInfo annInfo = AnnualYearPlanFactory.getRemoteInstance().getAnnualYearPlanInfo(new ObjectUuidPK(id3));
				AnnualYearPlanEntryCollection aeCollection = 	AnnualYearPlanEntryFactory.getRemoteInstance().getAnnualYearPlanEntryCollection("where parent = '"+annInfo.getId()+"'");
				for(int i=0;i<aeCollection.size();i++){
					String id1 = ((EquIdInfo)aeCollection.get(i).getZdaNumber()).getId().toString();
					String id2 =  ((EquIdInfo)kdtEntry.getCell(j, "zdaNumber").getValue()).getId().toString();
					if(id1.equals(id2)){
						aeCollection.get(i).setActualDate((Date) kdtEntry.getCell(j, "sjjcrq").getValue());
						aeCollection.get(i).setPlanDate((Date) kdtEntry.getCell(j, "planDate").getValue());
						AnnualYearPlanEntryFactory.getRemoteInstance().update(new ObjectUuidPK(aeCollection.get(i).getId()), aeCollection.get(i));
					}
					EquIdInfo eiInfo = EquIdFactory.getRemoteInstance().getEquIdInfo(new ObjectUuidPK(id1));
					eiInfo.setActrueTime((Date) kdtEntry.getCell(j, "sjjcrq").getValue());
					EquIdFactory.getRemoteInstance().update(new ObjectUuidPK(eiInfo.getId()), eiInfo);
				}
				
			}
	    
	    }
		
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
		if(getUIContext().get("SId")!=null){
			for (int j = 0; j < kdtEntry.getRowCount(); j++) {
				String id3 = (String) getUIContext().get("SId");
				AnnualYearPlanInfo annInfo = AnnualYearPlanFactory.getRemoteInstance().getAnnualYearPlanInfo(new ObjectUuidPK(id3));
				AnnualYearPlanEntryCollection aeCollection = 	AnnualYearPlanEntryFactory.getRemoteInstance().getAnnualYearPlanEntryCollection("where parent = '"+annInfo.getId()+"'");
				for(int i=0;i<aeCollection.size();i++){
					String id1 = ((EquIdInfo)aeCollection.get(i).getZdaNumber()).getId().toString();
					String id2 =  ((EquIdInfo)kdtEntry.getCell(j, "zdaNumber").getValue()).getId().toString();
					if(id1.equals(id2)){
						aeCollection.get(i).setActualDate(null);
						AnnualYearPlanEntryFactory.getRemoteInstance().update(new ObjectUuidPK(aeCollection.get(i).getId()), aeCollection.get(i));
					}
					EquIdInfo eiInfo = EquIdFactory.getRemoteInstance().getEquIdInfo(new ObjectUuidPK(id1));
					eiInfo.setActrueTime(null);
					EquIdFactory.getRemoteInstance().update(new ObjectUuidPK(eiInfo.getId()), eiInfo);
				}
				
			}
	    
	    }
		this.setOprtState("EDIT");
		loadData();
		unLockUI();
		MsgBox.showInfo("反确认检测信息成功！");
		
		btnConfirmation.setEnabled(true);
		btnUnConfirmation.setEnabled(false);
        
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
			ObjectUuidPK pk = new ObjectUuidPK(editData.getId());
			setDataObject(getValue(pk));
			loadFields();
			btnUnIssued.setEnabled(true);
			btnIssued.setEnabled(false);
			btnUnAudit.setEnabled(false);
			btnSave.setEnabled(false);
			btnSubmit.setEnabled(false);
			btnConfirm.setEnabled(true);
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
			btnEdit.setEnabled(true);
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
        super.actionEdit_actionPerformed(e);
        btnSave.setEnabled(true);
    }

    
    protected void checkCanEdit() throws Exception {
    	
    	
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
    	if(editData.getStatus().equals(XRBillStatusEnum.FINISH)){
    		MsgBox.showInfo("此单据已完成，不允许删除!");
			SysUtil.abort();
    	}
    	if(editData.getStatus().equals(XRBillStatusEnum.EXECUTION)){
    		MsgBox.showInfo("此单据已执行，不允许删除!");
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
		if(info.getStatus() != null&& info.getStatus() == XRBillStatusEnum.RELEASED&&!info.isIsConfirmation()){
		    btnUnIssued.setEnabled(true);
			btnIssued.setEnabled(false);
			btnConfirm.setEnabled(true);
		}
    }
    
    //确认明细
    public void actionConfirm_actionPerformed(ActionEvent e) throws Exception {
    	super.actionConfirm_actionPerformed(e);
    	storeFields();
    	editData.setIsConfirmation(true);
    	((IAnnualYearDetail)getBillInterface()).update(new ObjectUuidPK(editData.getId()), editData);
    	btnConfirm.setEnabled(false);
    	btnUnConfirm.setEnabled(true);
    	btnEdit.setEnabled(false);
    	btnUnIssued.setEnabled(false);
    	btnSave.setEnabled(false);
    	btnSubmit.setEnabled(false);
		ObjectUuidPK pk = new ObjectUuidPK(editData.getId());
		setDataObject(getValue(pk));
		loadFields();
		setSaved(true);
    }
    
      //反确认明细
    public void actionUnConfirm_actionPerformed(ActionEvent e) throws Exception {
    	super.actionUnConfirm_actionPerformed(e);
    	storeFields();
    	editData.setIsConfirmation(false);
    	((IAnnualYearDetail)getBillInterface()).update(new ObjectUuidPK(editData.getId()), editData);
    	btnConfirm.setEnabled(true);
    	btnUnConfirm.setEnabled(false);
    	btnEdit.setEnabled(true);
    	btnUnIssued.setEnabled(true);
		ObjectUuidPK pk = new ObjectUuidPK(editData.getId());
		setDataObject(getValue(pk));
		loadFields();
		setSaved(true);
    }
    
    
	protected void kdtEntry_tableClicked(KDTMouseEvent e) throws Exception {
		super.kdtEntry_tableClicked(e);
		 if ((e.getButton() == 1) && (e.getClickCount() == 2))
	        {
			  if(editData.getId() ==null){
				  MsgBox.showInfo("请先保存单据！");
					SysUtil.abort();
			  }else{
			  if(e.getRowIndex() != -1){
				  if(kdtEntry.getCell(e.getRowIndex(), "zdaNumber").getValue() !=null){
				    String id = ((EquIdInfo)kdtEntry.getCell(e.getRowIndex(), "zdaNumber").getValue()).getId().toString();
					IUIWindow uiWindow = null;
					UIContext context = new UIContext(this);
					context.put("ID", id);
					context.put("anid", editData.getId().toString());
					uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(EquIdEditUI.class.getName(), context, null, OprtState.VIEW);
					uiWindow.show(); 
				  }
			    }
			  }
	        }

	}
}