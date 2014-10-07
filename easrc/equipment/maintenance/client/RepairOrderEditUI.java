/**
 * output package name
 */
package com.kingdee.eas.port.equipment.maintenance.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.cp.bc.BizCollUtil;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.port.equipment.record.EquIdInfo;
import com.kingdee.eas.port.equipment.record.client.EquIdEditUI;
import com.kingdee.eas.port.equipment.uitl.ToolHelp;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.xr.app.XRBillStatusEnum;
import com.kingdee.eas.xr.helper.PersonXRHelper;
import com.kingdee.eas.xr.helper.Tool;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;

/**
 * output class name
 */
public class RepairOrderEditUI extends AbstractRepairOrderEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(RepairOrderEditUI.class);
    
    /**
     * output class constructor
     */
    public RepairOrderEditUI() throws Exception
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
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	if(editData.getStatus().equals(XRBillStatusEnum.DELETED)){
    		MsgBox.showInfo("单据已作废，不允许修改!");
  			SysUtil.abort();
    	}
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	if(editData.getStatus().equals(XRBillStatusEnum.DELETED)){
    		MsgBox.showInfo("不允许删除作废单据!");
  			SysUtil.abort();
    	}
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
     * output actionAudit_actionPerformed
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAudit_actionPerformed(e);
    }

    /**
     * output actionUnAudit_actionPerformed
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionUnAudit_actionPerformed(e);
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.equipment.maintenance.RepairOrderFactory.getRemoteInstance();
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
        com.kingdee.eas.port.equipment.maintenance.RepairOrderInfo objectValue = new com.kingdee.eas.port.equipment.maintenance.RepairOrderInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
      
    	Tool.checkGroupAddNew();
    	
    	try {
			objectValue.setBizDate(SysUtil.getAppServerTime(null));
		} catch (EASBizException e) {
			e.printStackTrace();
		}
		objectValue.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		
		PersonInfo personInfo = com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUserInfo().getPerson();
		if(personInfo==null)
		{
			MsgBox.showWarning("当前用户不是职员用户，不能执行此操作！");SysUtil.abort();
		}
		objectValue.setRepairPerson(personInfo);
		objectValue.setRepairDepart(PersonXRHelper.getPosiMemByDeptUser(personInfo));
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

	protected void initWorkButton() {
		super.initWorkButton();
		btnequInfomation.setIcon(EASResource.getIcon("imgTbtn_list"));
	}
	
	public void onLoad() throws Exception {
		prmtslDepart.setEnabled(false);
		this.kdtE1.getColumn("seq").getStyleAttributes().setHided(true);
		this.kdtE1.getColumn("FaLocation").getStyleAttributes().setHided(true);
		this.kdtE1.getColumn("yujingDate").getStyleAttributes().setHided(true);
		txtmaintenanceProgram.setVisible(false);
		chkselfStudy.setVisible(false);
		chkoutsourcing.setVisible(false);
		txtselfAmount.setVisible(false);
		contselfAmount.setVisible(false);
		txtoutAmount.setVisible(false);
		contoutAmount.setVisible(false);
		contmaintenanceProgram.setVisible(false);
		prmtequName.setVisible(false);
		contequName.setVisible(false);
		txtequModel.setVisible(false);
		contequModel.setVisible(false);
		txtequAddress.setVisible(false);
		contequAddress.setVisible(false);
		pkrepairDate.setVisible(false);
		contrepairDate.setVisible(false);
		pktransferTime.setVisible(false);
		conttransferTime.setVisible(false);
		if(getOprtState().equals(OprtState.ADDNEW)){
		    txtselfAmount.setEnabled(false);
		    txtoutAmount.setEnabled(false);
		}
		super.onLoad();
		this.setUITitle("维保任务单");
		 EntityViewInfo evi = new EntityViewInfo();
		 FilterInfo filter = new FilterInfo();
		 String id = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
		 DateFormat FORMAT_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		   Date date = null;
		    try {
		      date = SysUtil.getAppServerTime(null);
		    } catch (EASBizException e1) {
		      e1.printStackTrace();
		    }
		    StringBuffer sb = new StringBuffer();
		    sb.append(" select CFEqmNumberID from cT_OPE_EqmIO  ");
		    sb.append(" where CFInOrgUnitID='").append(id).append("'");
		    sb.append(" and CFRentStart<={ts '" + FORMAT_TIME.format(date) + "'}");
		    sb.append(" and CFRentEnd>={ts '" + FORMAT_TIME.format(date) + "'}");
		    sb.append(" and fstatus = '4'");
		 filter.getFilterItems().add(new FilterItemInfo("ssOrgUnit.id",id ,CompareType.EQUALS));
//		 filter.getFilterItems().add(new FilterItemInfo("sbStatus","1",CompareType.EQUALS));
		 filter.getFilterItems().add(new FilterItemInfo("id", sb.toString(), CompareType.INNER));
	 	 filter.setMaskString("#0 or #1");
		 evi.setFilter(filter);
		 prmtequName.setEntityViewInfo(evi);
//		 prmtequName.setSelector(ToolHelp.initPrmtEquIdByF7Color(evi, false)); 
		 
		   KDBizPromptBox kdtE1_repairPerson_PromptBox = new KDBizPromptBox();
	        kdtE1_repairPerson_PromptBox.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");
	        kdtE1_repairPerson_PromptBox.setVisible(true);
	        kdtE1_repairPerson_PromptBox.setEditable(true);
	        kdtE1_repairPerson_PromptBox.setDisplayFormat("$number$");
	        kdtE1_repairPerson_PromptBox.setEditFormat("$number$");
	        kdtE1_repairPerson_PromptBox.setCommitFormat("$number$");
	        KDTDefaultCellEditor kdtE1_repairPerson_CellEditor = new KDTDefaultCellEditor(kdtE1_repairPerson_PromptBox);
	        kdtE1.getColumn("repairPerson").setEditor(kdtE1_repairPerson_CellEditor);
	        
	        String id1 = SysContext.getSysContext().getCurrentStorageUnit().getId().toString();
	        KDBizPromptBox kdtE1_replaceSparePart_PromptBox = new KDBizPromptBox();
	        kdtE1_replaceSparePart_PromptBox.setQueryInfo("com.kingdee.eas.port.equipment.rpt.F7MaterialQuery");
//	        kdtE1_replaceSparePart_PromptBox.setQueryInfo("com.kingdee.eas.fi.rpt.app.F7MaterialQuery");
	        kdtE1_replaceSparePart_PromptBox.setVisible(true);
	        kdtE1_replaceSparePart_PromptBox.setEditable(true);
	        kdtE1_replaceSparePart_PromptBox.setDisplayFormat("$number$");
	        kdtE1_replaceSparePart_PromptBox.setEditFormat("$number$");
	        kdtE1_replaceSparePart_PromptBox.setCommitFormat("$number$");
	   	     EntityViewInfo evi1 = new EntityViewInfo();
			 FilterInfo filter1 = new FilterInfo();
	 		 filter1.getFilterItems().add(new FilterItemInfo("Storage.id",id1 ,CompareType.EQUALS));
			 evi1.setFilter(filter1);
			 kdtE1_replaceSparePart_PromptBox.setEntityViewInfo(evi1);
			 KDTDefaultCellEditor kdtEntry_feeType_CellEditor = new KDTDefaultCellEditor(kdtE1_replaceSparePart_PromptBox);
			 kdtE1.getColumn("replaceSparePart").setEditor(kdtEntry_feeType_CellEditor);
		 
	    Tool.setPersonF7(kdtE1_repairPerson_PromptBox, this, SysContext.getSysContext().getCurrentCtrlUnit().getId().toString());
		Tool.setPersonF7(this.prmtassignee, this, SysContext.getSysContext().getCurrentCtrlUnit().getId().toString());
		Tool.setPersonF7(this.prmtrepairPerson, this, SysContext.getSysContext().getCurrentCtrlUnit().getId().toString());
		Tool.setPersonF7(this.prmtdeliveryPerson, this, SysContext.getSysContext().getCurrentCtrlUnit().getId().toString());
		Tool.setPersonF7(this.prmtbaoxiuren, this, SysContext.getSysContext().getCurrentCtrlUnit().getId().toString());
		Tool.setPersonF7(this.prmtrecipient, this, SysContext.getSysContext().getCurrentCtrlUnit().getId().toString());
//		if(chkselfStudy.getSelected() == 32){
//			txtselfAmount.setEnabled(true);
//		}else{
//			 txtselfAmount.setEnabled(false);
//			 txtselfAmount.setValue(null);
//		}
//		if(chkoutsourcing.getSelected() == 32){
//			 txtoutAmount.setEnabled(true);
//		}else{
//			txtoutAmount.setEnabled(false);
//			txtoutAmount.setValue(null);
//		}
//		
//		//自修
//		chkselfStudy.addActionListener(new ActionListener(){
//			public void actionPerformed(ActionEvent e) {
//				if(chkselfStudy.getSelected() == 32){
//					 txtselfAmount.setEnabled(true);
//				}else{
//					 txtselfAmount.setEnabled(false);
//					 txtselfAmount.setValue(null);
//				}
//			}
//		});
//		//委外修理
//		chkoutsourcing.addActionListener(new ActionListener(){
//			public void actionPerformed(ActionEvent e) {
//				if(chkoutsourcing.getSelected() == 32){
//					 txtoutAmount.setEnabled(true);
//				}else{
//					txtoutAmount.setEnabled(false);
//					txtoutAmount.setValue(null);
//				}
//			}
//		});
	}
	

	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		Calendar ca = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (pkBizDate.getValue() != null) {
			for (int i = 0; i < kdtE1.getRowCount(); i++) {
				if(kdtE1.getCell(i, "yujingzhouqi").getValue() != null ){
					String date = sdf.format(pkBizDate.getValue());
					BigDecimal yujingzhouqi = UIRuleUtil.getBigDecimal(kdtE1.getCell(i, "yujingzhouqi").getValue());
					ca.setTime(sdf.parse(date));
					ca.add(Calendar.DATE,yujingzhouqi.intValue());
					Date date1 = ca.getTime();
					kdtE1.getCell(i, "yujingDate").setValue(date1);
				}
			}
		}
	}
	
	protected void prmtassignee_dataChanged(DataChangeEvent e) throws Exception {
		super.prmtassignee_dataChanged(e);
		if(BizCollUtil.isF7ValueChanged(e)&&e.getNewValue()!=null)
			this.prmtslDepart.setValue(PersonXRHelper.getPosiMemByDeptUser((PersonInfo)e.getNewValue()));
		else
			this.prmtslDepart.setValue(null);
	}
	
	protected void prmtrepairPerson_dataChanged(DataChangeEvent e)
			throws Exception {
		super.prmtrepairPerson_dataChanged(e);
		if(BizCollUtil.isF7ValueChanged(e)&&e.getNewValue()!=null)
			this.prmtrepairDepart.setValue(PersonXRHelper.getPosiMemByDeptUser((PersonInfo)e.getNewValue()));
		else
			this.prmtrepairDepart.setValue(null);
	}
	
	public void actionEquInfomation_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionEquInfomation_actionPerformed(e);
		 if(editData.getId() ==null){
			  MsgBox.showInfo("请先保存单据！");
				SysUtil.abort();
		  }
			  if(prmtequName.getValue() !=null){
			    String id = ((EquIdInfo)prmtequName.getData()).getId().toString();
				IUIWindow uiWindow = null;
				UIContext context = new UIContext(this);
				context.put("ID", id);
				context.put("anid", editData.getId().toString());
				uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(EquIdEditUI.class.getName(), context, null, OprtState.VIEW);
				uiWindow.show(); 
			  }
	}
}