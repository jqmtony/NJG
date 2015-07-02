/**
 * output package name
 */
package com.kingdee.eas.custom.richinf.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.custom.richinf.IRichCompayWriteOffDjEntry;
import com.kingdee.eas.custom.richinf.IRichCustomWriteOffDjEntry;
import com.kingdee.eas.custom.richinf.IRichExamed;
import com.kingdee.eas.custom.richinf.IRichInvoiceRequestEntry;
import com.kingdee.eas.custom.richinf.RichCompayWriteOffDjEntryFactory;
import com.kingdee.eas.custom.richinf.RichCustomWriteOffDjEntryFactory;
import com.kingdee.eas.custom.richinf.RichExamedFactory;
import com.kingdee.eas.custom.richinf.RichExamedInfo;
import com.kingdee.eas.custom.richinf.RichInvoiceRequestEntryFactory;
import com.kingdee.eas.custom.richinf.RichInvoiceRequestFactory;
import com.kingdee.eas.fm.common.FMIsqlFacadeFactory;
import com.kingdee.eas.fm.common.IFMIsqlFacade;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class RichExamedListUI extends AbstractRichExamedListUI
{
    private static final Logger logger = CoreUIObject.getLogger(RichExamedListUI.class);
    
    /**
     * output class constructor
     */
    public RichExamedListUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
    	super.onLoad();
//    	actionAddNew.setVisible(false);
    	actionRemove.setVisible(false);
    }
    
    public void actionDate_actionPerformed(ActionEvent e) throws Exception {
        super.actionDate_actionPerformed(e);

        CompanyOrgUnitInfo currentFIUnit = SysContext.getSysContext().getCurrentFIUnit();
        IUIWindow myWindow = null;
        myWindow = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory").create(DateUI.class.getName(), getUIContext(), null, OprtState.ADDNEW);
        myWindow.show();
      }
    public void beforeTransform(IObjectCollection iobjectcollection, String s) {
    }
    
    /**
     * output actionRefresh_actionPerformed
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRefresh_actionPerformed(e);
//        EASRichFacadeFactory.getRemoteInstance().saveExamBill(new Date(), "");
    }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    
    @Override
    protected String getEditUIModal() {
    	return UIFactoryName.NEWTAB;
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
    	checkSelected();
    	if(RichInvoiceRequestFactory.getRemoteInstance().getRichInvoiceRequestCollection("where sourceBillId='"+getSelectedKeyValue()+"'").size() > 0) {
    		MsgBox.showInfo("此单据已关联生成开票申请单，不能进行此操作！");
    		SysUtil.abort();
    	}
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
    
    IFMIsqlFacade iff = FMIsqlFacadeFactory.getRemoteInstance();
    
    public BigDecimal getYsqAmount(String djid,boolean djkp){
    	BigDecimal result = BigDecimal.ZERO;
    	StringBuffer buffer = new StringBuffer();
    	buffer.append("select rrentry.fid,rrentry.CFBencisq from CT_RIC_RichInvoiceRequestEntry rrentry left join ");
    	buffer.append("CT_RIC_RichInvoiceRequest rreq on rreq.fid=rrentry.fparentid where rrentry.CFDjdID='");
    	buffer.append(djid);
    	buffer.append("' ");
    	if(djkp){
    		buffer.append("and rreq.CFDjkp=1");
    	}else{
    		buffer.append("and rreq.CFDjkp<>1");
    	}
    	try {
    		IRowSet rs = iff.executeQuery(buffer.toString(),null);
    		while(rs.next()) {
    			if(rs.getBigDecimal("CFBencisq") != null){
    				result = result.add(rs.getBigDecimal("CFBencisq"));
    			}
    		}
		} catch (EASBizException e) {
			handUIException(e);
		} catch (BOSException e) {
			handUIException(e);
		} catch (SQLException e) {
			handUIException(e);
		}
    	return result;
    }
    
    /**
     * output actionCreateTo_actionPerformed
     */
    IRichExamed ire = RichExamedFactory.getRemoteInstance();
    IRichInvoiceRequestEntry  ireq = RichInvoiceRequestEntryFactory.getRemoteInstance();
    IRichCompayWriteOffDjEntry icompay = RichCompayWriteOffDjEntryFactory.getRemoteInstance();
    IRichCustomWriteOffDjEntry icustom = RichCustomWriteOffDjEntryFactory.getRemoteInstance();
    
    public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception{
    	KDTSelectBlock selectBlock = null;
    	KDTSelectManager selectManger = tblMain.getSelectManager();
    	RichExamedInfo info = null;
//    	BigDecimal khyhx = null;
//    	BigDecimal nbyhx = null;
    	BigDecimal amount = null;
    	String djid = null;
    	for (int i = 0; i < selectManger.size(); i++) {
    		selectBlock = selectManger.get(i);
    		for (int j = selectBlock.getBeginRow(); j <=selectBlock.getEndRow(); j++) {
    			djid = (String)tblMain.getCell(j,"id").getValue();
    			info = ire.getRichExamedInfo(new ObjectUuidPK(djid));
    			amount = info.getAmount();
    			if(amount == null){
    				MsgBox.showInfo("第"+(j+1)+"行到检单数据异常，请重新选择！");
    				SysUtil.abort();
    			}
    			if(ireq.getRichInvoiceRequestEntryCollection("select id where djd.id='"+djid+"'").size()==0){
    				if(icompay.getRichCompayWriteOffDjEntryCollection("select id where djNo.id='"+djid+"'").size()>0){
    					MsgBox.showInfo("第"+(j+1)+"行到检单已在内部核销单中，不能下推开票申请！");
        				SysUtil.abort();
    				}
    				if(icustom.getRichCustomWriteOffDjEntryCollection("select id where djNo.id='"+djid+"'").size()>0){
    					MsgBox.showInfo("第"+(j+1)+"行到检单已在客户核销单中，不能下推开票申请！");
        				SysUtil.abort();
    				}
    			}
//    			nbyhx = info.getNbyhxAmount();
//    			khyhx = info.getYhxAmount();
    			if(info.isDj()) {
    				boolean flag1 = getYsqAmount(djid,true).compareTo(amount)==0;
    				boolean flag2 = getYsqAmount(djid,false).compareTo(amount)==0;
    				if(flag1 && flag2){
    					MsgBox.showInfo("第"+(j+1)+"行到检单客户和内部金额都已全部申请，请重新选择！");
    					SysUtil.abort();
    				}
    				if(flag1){
    					MsgBox.showInfo("第"+(j+1)+"行到检单内部金额已全部申请，单据转换时请选择默认规则！");
    				}
    				if(flag2){
    					MsgBox.showInfo("第"+(j+1)+"行到检单客户金额已全部申请，单据转换时请选择只对内部规则！");
    				}
    				
//    				if(nbyhx!=null && nbyhx.compareTo(amount)==0 && khyhx!=null && amount.compareTo(khyhx)==0){
//    					MsgBox.showInfo("第"+(j+1)+"行到检单客户和内部金额都已全部核销，请重新选择！");
//    					SysUtil.abort();
//    				}
//    				if(khyhx!=null && khyhx.compareTo(amount)==0 && (nbyhx==null || amount.compareTo(nbyhx)>0)){
//    					MsgBox.showInfo("第"+(j+1)+"行到检单客户金额已全部核销，单据转换时请选择只对内部规则！");
//    				}
//    				if(nbyhx!=null && nbyhx.compareTo(amount)==0 && (khyhx==null || amount.compareTo(khyhx)>0)){
//    					MsgBox.showInfo("第"+(j+1)+"行到检单内部金额已全部核销，单据转换时请选择默认规则！");
//    				} 
    			}else {
//    				if(khyhx != null && khyhx.compareTo(amount) == 0){
//    					MsgBox.showInfo("第"+(j+1)+"行到检单客户金额已全部核销，请重新选择！");
//    					SysUtil.abort();
//    				}
    				if(getYsqAmount(djid,false).compareTo(amount)==0){
    					MsgBox.showInfo("第"+(j+1)+"行到检单客户金额已全部申请，请重新选择！");
    					SysUtil.abort();
    				}
    				
    			}
    			
			}
		}
    	
        super.actionCreateTo_actionPerformed(e);
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
     * output actionTDPrint_actionPerformed
     */
    public void actionTDPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTDPrint_actionPerformed(e);
    }

    /**
     * output actionTDPrintPreview_actionPerformed
     */
    public void actionTDPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTDPrintPreview_actionPerformed(e);
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.custom.richinf.RichExamedFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.custom.richinf.RichExamedInfo objectValue = new com.kingdee.eas.custom.richinf.RichExamedInfo();
		
        return objectValue;
    }

}