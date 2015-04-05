/**
 * output package name
 */
package com.kingdee.eas.port.equipment.special.client;

import java.awt.event.*;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.bot.BOTMappingFactory;
import com.kingdee.bos.metadata.bot.BOTMappingInfo;
import com.kingdee.bos.metadata.bot.IBOTMapping;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fm.ecore.app.bean.node.MsgInf;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.port.equipment.maintenance.IMonMainPlanE1;
import com.kingdee.eas.port.equipment.maintenance.MonMainPlanE1Collection;
import com.kingdee.eas.port.equipment.maintenance.MonMainPlanE1Factory;
import com.kingdee.eas.port.equipment.maintenance.MonMainPlanFactory;
import com.kingdee.eas.port.equipment.maintenance.MonMainPlanInfo;
import com.kingdee.eas.port.equipment.operate.ComproductionInfo;
import com.kingdee.eas.port.equipment.special.AnnualYearDetailEntryCollection;
import com.kingdee.eas.port.equipment.special.AnnualYearDetailEntryFactory;
import com.kingdee.eas.port.equipment.special.AnnualYearDetailFactory;
import com.kingdee.eas.port.equipment.special.AnnualYearDetailInfo;
import com.kingdee.eas.port.equipment.special.IAnnualYearDetail;
import com.kingdee.eas.port.equipment.special.IAnnualYearDetailEntry;
import com.kingdee.eas.port.equipment.special.OverhaulNoticeInfo;
import com.kingdee.eas.port.equipment.uitl.ToolHelp;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.KDTableUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.eas.xr.app.XRBillStatusEnum;

/**
 * output class name
 */
public class AnnualYearDetailListUI extends AbstractAnnualYearDetailListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AnnualYearDetailListUI.class);
    
    /**
     * output class constructor
     */
    public AnnualYearDetailListUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
    }

    /**
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
        super.tblMain_tableSelectChanged(e);
    }

    /**
     * output menuItemImportData_actionPerformed method
     */
    protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.menuItemImportData_actionPerformed(e);
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
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionView_actionPerformed
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionView_actionPerformed(e);
    }

  

    

    /**
     * output actionRefresh_actionPerformed
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRefresh_actionPerformed(e);
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
     * output actionLocate_actionPerformed
     */
    public void actionLocate_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLocate_actionPerformed(e);
    }

    /**
     * output actionQuery_actionPerformed
     */
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQuery_actionPerformed(e);
    }

    /**
     * output actionImportData_actionPerformed
     */
    public void actionImportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionImportData_actionPerformed(e);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }

    /**
     * output actionExportData_actionPerformed
     */
    public void actionExportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportData_actionPerformed(e);
    }

    /**
     * output actionToExcel_actionPerformed
     */
    public void actionToExcel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionToExcel_actionPerformed(e);
    }

    /**
     * output actionStartWorkFlow_actionPerformed
     */
    public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionStartWorkFlow_actionPerformed(e);
    }

    /**
     * output actionPublishReport_actionPerformed
     */
    public void actionPublishReport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPublishReport_actionPerformed(e);
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
     * output actionQueryScheme_actionPerformed
     */
    public void actionQueryScheme_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQueryScheme_actionPerformed(e);
    }

    /**
     * output actionCreateTo_actionPerformed
     */
    public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateTo_actionPerformed(e);
//	    	checkSelected();
//	    	int selectIndex[] = KDTableUtil.getSelectedRows(tblMain);
//	    	
//	    	StringBuffer sb = new StringBuffer();
//	    	String innerId = "'null'";
//	    	String billId = "'null'";
//	    	
//	     	for (int j = 0; j < selectIndex.length; j++) 
//	    	{
//	    		String entryId = UIRuleUtil.getString(this.tblMain.getRow(selectIndex[j]).getCell("Entry.id").getValue());
//	    	
//	    			innerId = innerId+",'"+entryId+"'";
//	    			billId = billId+",'"+UIRuleUtil.getString(this.tblMain.getRow(selectIndex[j]).getCell("id").getValue())+"'";
//			}
//	    	
//	    	if(sb!=null&&!"".equals(sb.toString().trim()))
//	    	{
//	    		MsgBox.showConfirm3a("�в����������ļ�¼����鿴��ϸ��Ϣ��", sb.toString());
//	    		return;
//	    	}
//	    	else
//	    	{
//	    		IBOTMapping botMapping = BOTMappingFactory.getRemoteInstance();
//	    		BOTMappingInfo botInfo = (BOTMappingInfo) botMapping.getValue("where name='NJP00006'");
//	    		if(botInfo== null)
//	    		{
//	    			MsgBox.showWarning("�����������BOTP��");SysUtil.abort();
//	    		}
//	    		
//	    		IAnnualYearDetailEntry IAnnualYearDetailEntry = AnnualYearDetailEntryFactory.getRemoteInstance();
//	    		
//	    		CoreBillBaseCollection eqcollection = AnnualYearDetailFactory.getRemoteInstance().getCoreBillBaseCollection("where id in("+billId+")");
//	    		
//	    		for (int i = 0; i < eqcollection.size(); i++)
//	    		{
//	    			AnnualYearDetailInfo Info = (AnnualYearDetailInfo)eqcollection.get(i);
//	    			String oql = "where id in("+innerId+") and parent.id='"+Info.getId()+"'";
//	    			
//	    			AnnualYearDetailEntryCollection e1Collection = IAnnualYearDetailEntry.getAnnualYearDetailEntryCollection(oql);
//	    			Info.getEntry().clear();
//	    			for (int j = 0; j < e1Collection.size(); j++) 
//	    			{
//	    				Info.getEntry().add(e1Collection.get(j));
//					}
//				}
//	    		ToolHelp.generateDestBill("22366297", "D2BED306",eqcollection , new ObjectUuidPK(botInfo.getId()));
//	    		MsgBox.showInfo("��������֪ͨ���ɹ���");
//	    		refresh(null);
//	    	}
    }

    /**
     * output actionCopyTo_actionPerformed
     */
    public void actionCopyTo_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopyTo_actionPerformed(e);
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
     * output actionAuditResult_actionPerformed
     */
    public void actionAuditResult_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAuditResult_actionPerformed(e);
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
     * output actionWorkFlowG_actionPerformed
     */
    public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkFlowG_actionPerformed(e);
    }

    /**
     * output actionSendSmsMessage_actionPerformed
     */
    public void actionSendSmsMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendSmsMessage_actionPerformed(e);
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
     * output actoinViewSignature_actionPerformed
     */
    public void actoinViewSignature_actionPerformed(ActionEvent e) throws Exception
    {
        super.actoinViewSignature_actionPerformed(e);
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
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.equipment.special.AnnualYearDetailFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.port.equipment.special.AnnualYearDetailInfo objectValue = new com.kingdee.eas.port.equipment.special.AnnualYearDetailInfo();
		
        return objectValue;
    }
    /**
     * ɾ��
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	
    	 checkSelected();
         String billID = getSelectedKeyValue();
         if(billID == null)
             return;
         ObjectUuidPK pk = new ObjectUuidPK(BOSUuid.read(billID));
         SelectorItemCollection sc = new SelectorItemCollection();
         Object o = getBizInterface().getValue(pk, sc);
         AnnualYearDetailInfo ayInfo = (AnnualYearDetailInfo)o;
         if(ayInfo.getStatus()== XRBillStatusEnum.RELEASED){
       	  MsgBox.showInfo("�˵������´����ɾ��!");
   			SysUtil.abort();
         }
         if(ayInfo.getStatus()== XRBillStatusEnum.FINISH){
          	  MsgBox.showInfo("�˵�������ɣ�������ɾ��!");
      			SysUtil.abort();
            }
         if(ayInfo.getStatus()== XRBillStatusEnum.EXECUTION){
          	  MsgBox.showInfo("�˵�����ִ�У�������ɾ��!");
      			SysUtil.abort();
            }
        super.actionRemove_actionPerformed(e);
  	 
    }
    
    /**
     * �޸�
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	
    	  checkSelected();
          String billID = getSelectedKeyValue();
          if(billID == null)
              return;
          ObjectUuidPK pk = new ObjectUuidPK(BOSUuid.read(billID));
          SelectorItemCollection sc = new SelectorItemCollection();
          Object o = getBizInterface().getValue(pk, sc);
          AnnualYearDetailInfo ayInfo = (AnnualYearDetailInfo)o;
          if(ayInfo.getStatus()== XRBillStatusEnum.RELEASED&&ayInfo.isIsConfirmation()){
        	  MsgBox.showInfo("�˵�����ȷ�ϣ������޸�!");
    			SysUtil.abort();
          }
        super.actionEdit_actionPerformed(e);
  	
    }

    protected void initWorkButton()
    {
        super.initWorkButton();
        btnIssued.setIcon(EASResource.getIcon("imgTbtn_makeknown"));
        btnUnIssued.setIcon(EASResource.getIcon("imgTbtn_fmakeknown"));
        btnEntry.setIcon(EASResource.getIcon("imgTbtn_readin"));
    }
    
    public void actionEntry_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected();
    	String billID = getSelectedKeyValue();
        ObjectUuidPK pk = new ObjectUuidPK(BOSUuid.read(billID));
        Object o = getBizInterface().getValue(pk);
        AnnualYearDetailInfo onInfo = (AnnualYearDetailInfo)o;
        if((onInfo.getStatus().equals(XRBillStatusEnum.RELEASED)||onInfo.getStatus().equals(XRBillStatusEnum.EXECUTION))&&onInfo.isIsConfirmation()){
	    	IUIWindow uiWindow = null;
			UIContext context = new UIContext(this);
			context.put("ID", billID);
			context.put("FeedInfor", billID);
			if(onInfo.getSourceBillId() !=null){
			context.put("SId", onInfo.getSourceBillId());
			}
			String oprstate = "";
			if(onInfo.getStatus().equals(XRBillStatusEnum.RELEASED))
				oprstate=OprtState.EDIT;
			else
				oprstate=OprtState.VIEW;
			uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(AnnualYearDetailEditUI.class.getName(), context, null, oprstate);
			uiWindow.show(); 
	    }
	    else
	    {
	    	MsgBox.showWarning("����״̬�����´����û��ȷ�ϣ�����¼������Ϣ!");SysUtil.abort();
	    }
    }
    
 
    //�´�
    protected void btnIssued_actionPerformed(ActionEvent e) throws Exception {
    	super.btnIssued_actionPerformed(e);
  	  checkSelected();
      String billID = getSelectedKeyValue();
      if(billID == null)
          return;
      ObjectUuidPK pk = new ObjectUuidPK(BOSUuid.read(billID));
      SelectorItemCollection sc = new SelectorItemCollection();
      Object o = getBizInterface().getValue(pk, sc);
      AnnualYearDetailInfo ayInfo = (AnnualYearDetailInfo)o;
      
	      if(ayInfo.getStatus()== XRBillStatusEnum.AUDITED){
	    	  ayInfo.setStatus(XRBillStatusEnum.RELEASED);
	    	((IAnnualYearDetail)getBillInterface()).update(new ObjectUuidPK(ayInfo.getId()), ayInfo);
	    
	    	  refresh(e);
	    	  return;
	      }else if(ayInfo.getStatus()== XRBillStatusEnum.RELEASED){
	    	  MsgBox.showInfo("�˵������´�!");
				SysUtil.abort();
	      }else{
	    	  MsgBox.showInfo("�˵���û����ˣ��������´�!");
				SysUtil.abort();
	      }
	      
    	
    }
    
    //���´�
    protected void btnUnIssued_actionPerformed(ActionEvent e) throws Exception {
    	super.btnUnIssued_actionPerformed(e);
    	checkSelected();
        String billID = getSelectedKeyValue();
        if(billID == null)
            return;
        ObjectUuidPK pk = new ObjectUuidPK(BOSUuid.read(billID));
        SelectorItemCollection sc = new SelectorItemCollection();
        Object o = getBizInterface().getValue(pk, sc);
        AnnualYearDetailInfo ayInfo = (AnnualYearDetailInfo)o;
        if(ayInfo.getStatus()== XRBillStatusEnum.RELEASED){
	    	  ayInfo.setStatus(XRBillStatusEnum.AUDITED);
	    	  ((IAnnualYearDetail)getBillInterface()).update(new ObjectUuidPK(ayInfo.getId()), ayInfo);
	    	  refresh(e);
	    	  return;
	      }else{
	    	  MsgBox.showInfo("�˵���û���´�������´�!");
				SysUtil.abort();
	      }
        
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	}
    /**
     * �����
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionUnAudit_actionPerformed(e);
        String billID = getSelectedKeyValue();
        if(billID == null)
            return;
        ObjectUuidPK pk = new ObjectUuidPK(BOSUuid.read(billID));
        SelectorItemCollection sc = new SelectorItemCollection();
        Object o = getBizInterface().getValue(pk, sc);
        AnnualYearDetailInfo ayInfo = (AnnualYearDetailInfo)o;
        if(ayInfo.getStatus()== XRBillStatusEnum.RELEASED){
        	MsgBox.showInfo("�˵������´���������!");
			SysUtil.abort();
        }
    }
    //ȥ��CU����
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	
	//����ʹ�õ�λ��������ϸ
	protected IQueryExecutor getQueryExecutor(IMetaDataPK arg0,EntityViewInfo arg1) {
		EntityViewInfo viewInfo = (EntityViewInfo)arg1.clone();
		FilterInfo filInfo = new FilterInfo();
		String id = SysContext.getSysContext().getCurrentAdminUnit().getId().toString();
		filInfo.getFilterItems().add(new FilterItemInfo("useDpatmen.id",id ,CompareType.EQUALS));
       filInfo.getFilterItems().add(new FilterItemInfo("status",5 ,CompareType.EQUALS));
       filInfo.getFilterItems().add(new FilterItemInfo("status",90 ,CompareType.EQUALS));
       filInfo.getFilterItems().add(new FilterItemInfo("status",10,CompareType.EQUALS));
       filInfo.getFilterItems().add(new FilterItemInfo("status",11 ,CompareType.EQUALS));
       filInfo.setMaskString("#0 and (#1 or #2 or #3 or #4)");
		if(viewInfo.getFilter()!=null)
	    	{
	    
					try {
						viewInfo.getFilter().mergeFilter(filInfo, "and");
					} catch (BOSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			
	    	}
	    	else
	    	{
	    		viewInfo.setFilter(filInfo);
	    	}
		  if ("00000000-0000-0000-0000-000000000000CCE7AED4".equals(id)){
		      viewInfo = (EntityViewInfo)arg1.clone();
		    }
		    if ("6vYAAAAAAQvM567U".equals(id)){
			      viewInfo = (EntityViewInfo)arg1.clone();
			    }
		return super.getQueryExecutor(arg0, viewInfo);
	}
}