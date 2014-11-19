/**
 * output package name
 */
package com.kingdee.eas.port.equipment.maintenance.client;

import java.awt.Color;
import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.bot.BOTMappingFactory;
import com.kingdee.bos.metadata.bot.BOTMappingInfo;
import com.kingdee.bos.metadata.bot.IBOTMapping;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.port.equipment.maintenance.IEquMaintBook;
import com.kingdee.eas.port.equipment.maintenance.IMonMainPlanE1;
import com.kingdee.eas.port.equipment.maintenance.IRepairOrderE1;
import com.kingdee.eas.port.equipment.maintenance.MonMainPlanE1Collection;
import com.kingdee.eas.port.equipment.maintenance.MonMainPlanE1Factory;
import com.kingdee.eas.port.equipment.maintenance.MonMainPlanFactory;
import com.kingdee.eas.port.equipment.maintenance.MonMainPlanInfo;
import com.kingdee.eas.port.equipment.maintenance.RepairOrderE1Factory;
import com.kingdee.eas.port.equipment.uitl.ToolHelp;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.KDTableUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class MonMainPlanListUI extends AbstractMonMainPlanListUI
{
    private static final Logger logger = CoreUIObject.getLogger(MonMainPlanListUI.class);
    
    
    private IRepairOrderE1 IRepairOrderE1 = RepairOrderE1Factory.getRemoteInstance();
    private IRepairOrderE1 IEquMaintBook = RepairOrderE1Factory.getRemoteInstance();
    /**
     * output class constructor
     */
    public MonMainPlanListUI() throws Exception
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
//        super.actionCreateTo_actionPerformed(e);
    	checkSelected();
    	int selectIndex[] = KDTableUtil.getSelectedRows(tblMain);
    	
    	StringBuffer sb = new StringBuffer();
    	String innerId = "'null'";
    	String billId = "'null'";
    	
    	for (int j = 0; j < selectIndex.length; j++) 
    	{
    		String entryId = UIRuleUtil.getString(this.tblMain.getRow(selectIndex[j]).getCell("E1.id").getValue());
    		if(IRepairOrderE1.exists("select id where sourceBillID='"+entryId+"'"))
    			sb.append("第<"+(selectIndex[j]+1)+">行，颜色标记为黄色的已经生成维保任务了！	\n");
    		else
    		{
    			innerId = innerId+",'"+entryId+"'";
    			billId = billId+",'"+UIRuleUtil.getString(this.tblMain.getRow(selectIndex[j]).getCell("id").getValue())+"'";
    		}
		}
    	
    	if(sb!=null&&!"".equals(sb.toString().trim()))
    	{
    		MsgBox.showConfirm3a("有不满足条件的记录，请查看详细信息！", sb.toString());
    		return;
    	}
    	else
    	{
    		IBOTMapping botMapping = BOTMappingFactory.getRemoteInstance();
    		BOTMappingInfo botInfo = (BOTMappingInfo) botMapping.getValue("where name='NJP00023'");
    		if(botInfo== null)
    		{
    			MsgBox.showWarning("规则错误，请检查BOTP！");SysUtil.abort();
    		}
    		
    		IMonMainPlanE1 IMonMainPlanE1 = MonMainPlanE1Factory.getRemoteInstance();
    		
    		CoreBillBaseCollection eqcollection = MonMainPlanFactory.getRemoteInstance().getCoreBillBaseCollection("where id in("+billId+")");
    		
    		for (int i = 0; i < eqcollection.size(); i++)
    		{
    			MonMainPlanInfo Info = (MonMainPlanInfo)eqcollection.get(i);
    			String oql = "where id in("+innerId+") and parent.id='"+Info.getId()+"'";
    			
    			MonMainPlanE1Collection e1Collection = IMonMainPlanE1.getMonMainPlanE1Collection(oql);
    			Info.getE1().clear();
    			for (int j = 0; j < e1Collection.size(); j++) 
    			{
    				Info.getE1().add(e1Collection.get(j));
				}
			}
    		ToolHelp.generateDestBill("A0CD335E", "F96E9B71",eqcollection , new ObjectUuidPK(botInfo.getId()));
    		MsgBox.showInfo("生成维保任务成功！");
    		
    		refresh(null);
    	}
    }

    public void actionScrws_actionPerformed(ActionEvent e) throws Exception {
//    	super.actionScrws_actionPerformed(e);
    	checkSelected();
    	int selectIndex[] = KDTableUtil.getSelectedRows(tblMain);
    	
    	StringBuffer sb = new StringBuffer();
    	String innerId = "'null'";
    	String billId = "'null'";
    	
    	for (int j = 0; j < selectIndex.length; j++) 
    	{
    		String entryId = UIRuleUtil.getString(this.tblMain.getRow(selectIndex[j]).getCell("E1.id").getValue());
    		if(IEquMaintBook.exists("select id where sourceBillID='"+entryId+"'"))
    			sb.append("第<"+(selectIndex[j]+1)+">行，颜色标记为黄色的已经生成设备二级维保任务单了！	\n");
    		else
    		{
    			innerId = innerId+",'"+entryId+"'";
    			billId = billId+",'"+UIRuleUtil.getString(this.tblMain.getRow(selectIndex[j]).getCell("id").getValue())+"'";
    		}
		}
    	
    	if(sb!=null&&!"".equals(sb.toString().trim()))
    	{
    		MsgBox.showConfirm3a("有不满足条件的记录，请查看详细信息！", sb.toString());
    		return;
    	}
    	else
    	{
    		IBOTMapping botMapping = BOTMappingFactory.getRemoteInstance();
    		BOTMappingInfo botInfo = (BOTMappingInfo) botMapping.getValue("where name='NJPWB0000233456666'");
    		if(botInfo== null)
    		{
    			MsgBox.showWarning("规则错误，请检查BOTP！");SysUtil.abort();
    		}
    		
    		IMonMainPlanE1 IMonMainPlanE1 = MonMainPlanE1Factory.getRemoteInstance();
    		
    		CoreBillBaseCollection eqcollection = MonMainPlanFactory.getRemoteInstance().getCoreBillBaseCollection("where id in("+billId+")");
    		
    		for (int i = 0; i < eqcollection.size(); i++)
    		{
    			MonMainPlanInfo Info = (MonMainPlanInfo)eqcollection.get(i);
    			String oql = "where id in("+innerId+") and parent.id='"+Info.getId()+"'";
    			
    			MonMainPlanE1Collection e1Collection = IMonMainPlanE1.getMonMainPlanE1Collection(oql);
    			Info.getE1().clear();
    			for (int j = 0; j < e1Collection.size(); j++) 
    			{
    				Info.getE1().add(e1Collection.get(j));
				}
			}
    		ToolHelp.generateDestBill("A0CD335E", "14FFF66B",eqcollection , new ObjectUuidPK(botInfo.getId()));
    		MsgBox.showInfo("生成设备二级维保任务书成功！");
    		
    		refresh(null);
    	}
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
        return com.kingdee.eas.port.equipment.maintenance.MonMainPlanFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.port.equipment.maintenance.MonMainPlanInfo objectValue = new com.kingdee.eas.port.equipment.maintenance.MonMainPlanInfo();
		
        return objectValue;
    }

    public void onLoad() throws Exception {
    	btnScrws.setVisible(false);
    	String id = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
    	if(id.equals("6vYAAAAABBfM567U")){
    		btnScrws.setVisible(true);
    		btnCreateTo.setVisible(false);
    	}
    	
    	
    	tblMain.addKDTDataFillListener(new KDTDataFillListener() {
            public void afterDataFill(KDTDataRequestEvent e)
            {
                try
                {
                    tblMain_afterDataFill(e);
                }
                catch(Exception exc)
                {
                    handUIException(exc);
                }
            }
        });
    	super.onLoad();
//    	this.setUITitle("维保计划");
    	
    	this.btnCreateTo.setText("生成维保任务单");
    	this.btnCreateTo.setToolTipText("生成维保任务单");
    }
    
    //去除CU隔离
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	
	
    private void tblMain_afterDataFill(KDTDataRequestEvent e){
        for (int i = e.getFirstRow(); i <= e.getLastRow(); i++)
        {
        
        	//维保任务单
        	 if (UIRuleUtil.isNotNull(this.tblMain.getRow(i).getCell("E1.id").getValue())) 
        	 {
        		 IRow row = this.tblMain.getRow(i);
        		 String entryId = UIRuleUtil.getString(row.getCell("E1.id").getValue());
        		 try
        		 {
					if(IRepairOrderE1.exists("select id where sourceBillID='"+entryId+"'"))
						row.getStyleAttributes().setBackground(Color.yellow);
					if(IRepairOrderE1.exists("select id where sourceBillID='"+entryId+"' and status='4'"))
						row.getStyleAttributes().setBackground(Color.green);
        		 } catch (EASBizException e1) 
        		 {
        			 e1.printStackTrace();
        		 } catch (BOSException e1)
        		 {
        			 e1.printStackTrace();
        		 }
        	 }
//        	 
//        	 //设备二级维保任务书
//        	 if (UIRuleUtil.isNotNull(this.tblMain.getRow(i).getCell("E1.id").getValue())) 
//        	 {
//        		 IRow row = this.tblMain.getRow(i);
//        		 String entryId = UIRuleUtil.getString(row.getCell("E1.id").getValue());
//        		 try
//        		 {
//					if(IEquMaintBook.exists("select id where sourceBillID='"+entryId+"'"))
//						row.getStyleAttributes().setBackground(Color.yellow);
//					if(IEquMaintBook.exists("select id where sourceBillID='"+entryId+"' and status='4'"))
//						row.getStyleAttributes().setBackground(Color.green);
//        		 } catch (EASBizException e1) 
//        		 {
//        			 e1.printStackTrace();
//        		 } catch (BOSException e1)
//        		 {
//        			 e1.printStackTrace();
//        		 }
//        	 }
        }
    }
    
	protected IQueryExecutor getQueryExecutor(IMetaDataPK arg0,EntityViewInfo arg1) {
		EntityViewInfo viewInfo = (EntityViewInfo)arg1.clone();
		FilterInfo filInfo = new FilterInfo();
		String id = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
		filInfo.getFilterItems().add(new FilterItemInfo("CU.id",id ,CompareType.EQUALS));
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
	
	protected void initWorkButton() {
		super.initWorkButton();
		btnScrws.setIcon(EASResource.getIcon("imgTbtn_associatecreate"));
	}
}