/**
 * output package name
 */
package com.kingdee.eas.port.pm.invite.client;

import java.awt.Dimension;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.Arrays;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.basedata.assistant.ProjectInfo;
import com.kingdee.eas.basedata.master.cssp.ISupplier;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fm.ecore.app.bean.node.RegnInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStock;
import com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockEntryPersonCollection;
import com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockEntryPersonInfo;
import com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockFactory;
import com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockInfo;
import com.kingdee.eas.port.pm.invite.IInviteReportEntry2;
import com.kingdee.eas.port.pm.invite.InvitePlanInfo;
import com.kingdee.eas.port.pm.invite.InviteReportEntry2Collection;
import com.kingdee.eas.port.pm.invite.InviteReportEntry2Factory;
import com.kingdee.eas.port.pm.invite.InviteReportEntry2Info;
import com.kingdee.eas.port.pm.invite.InviteReportFactory;
import com.kingdee.eas.port.pm.invite.InviteReportInfo;
import com.kingdee.eas.port.pm.invite.OpenRegistrationFactory;
import com.kingdee.eas.port.pm.invite.judgeSolution;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.xr.app.XRBillStatusEnum;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTHead;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;

/**
 * output class name
 */
public class OpenRegistrationEditUI extends AbstractOpenRegistrationEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(OpenRegistrationEditUI.class);
    
    /**
     * output class constructor
     */
    public OpenRegistrationEditUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	// TODO Auto-generated method stub
    	initConpoment();
    	super.onLoad();
    	ProjectInfo info = (ProjectInfo) getUIContext().get("treeInfo");
    	if(info != null) {
    		EntityViewInfo evi = new EntityViewInfo();
    		FilterInfo filter = new FilterInfo();
    		evi.setFilter(filter);
    		filter.getFilterItems().add(new FilterItemInfo("proName.longnumber", info.getLongNumber()+"%", CompareType.LIKE));
    		filter.getFilterItems().add(new FilterItemInfo("status","4", CompareType.EQUALS));
    		prmtreportName.setEntityViewInfo(evi);
		}
    	com.kingdee.eas.port.pm.invite.client.InviteReportEditUI.initContainerButton(this.kDContainer1, this.kdtEntry_detailPanel);
    	
    	this.pkopDate.setTimeEnabled(true);
    	this.pkopDate.setDatePattern("yyyy-MM-dd HH:mm");
    }
    private void initConpoment() {
    	chkcancel.setEnabled(false);
    	txtNumber.setRequired(true);
    	txtregName.setRequired(true);
    	pkopDate.setRequired(true);
    	prmtreportName.setRequired(true);
    	txtcoefficient.setRequired(true);
    	txtopLocation.setRequired(true);
    	prmtreportName.setDisplayFormat("$reportName$");
    	contreportNumber.setEnabled(false);
    	btnCopyLine.setVisible(false);
    	btnAddLine.setVisible(false);
    	btnInsertLine.setVisible(false);
    	btnRemoveLine.setVisible(false);
    }

    protected void verifyInput(ActionEvent e) throws Exception {
    	// TODO Auto-generated method stub
    	com.kingdee.eas.xr.helper.ClientVerifyXRHelper.verifyNull(this, txtregName, "开标登记名称");
    	com.kingdee.eas.xr.helper.ClientVerifyXRHelper.verifyNull(this, prmtreportName, "招标方案");
    	com.kingdee.eas.xr.helper.ClientVerifyXRHelper.verifyNull(this, txtopLocation, "开标地点");
    	com.kingdee.eas.xr.helper.ClientVerifyXRHelper.verifyNull(this, pkopDate, "开标时间");
    	
    	InviteReportInfo planInfo = (InviteReportInfo)prmtreportName.getValue();
		
		String oql = "select id where reportName.id='"+planInfo.getId()+"' and cancel<>'1'";
		if(editData.getId()!=null)
			oql = oql+"and id <>'"+editData.getId()+"'";
		if(OpenRegistrationFactory.getRemoteInstance().exists(oql))
		{
			MsgBox.showWarning("当前开标登记<"+planInfo.getReportName()+">已有对应的开标登记，不允许重复登记！");SysUtil.abort();
		}
		
    	
//    	if(judgeSolution.integrate.equals(invite.getJudgeSolution())) {
//    		//综合评分时评标系数必填
//    		com.kingdee.eas.xr.helper.ClientVerifyXRHelper.verifyNull(this, txtcoefficient, "评标系数");
//    		BigDecimal coffient = new BigDecimal(txtcoefficient.getText());
//    		if(coffient.compareTo(new BigDecimal(0.9)) < 0 || coffient.compareTo(new BigDecimal(1.0)) > 0) {
//    			MsgBox.showWarning("评标系数大于等于0.9小于等于1!!");
//    			SysUtil.abort();
//    		}
//    	}
    	
    	for(int i = 0; i < this.kdtEntry.getRowCount(); i++) {
    		IRow row = this.kdtEntry.getRow(i);
    		if(row.getCell("payDeposit").getValue().equals(true))
    			if(row.getCell("deposit").getValue() == null) {
    				MsgBox.showWarning("已勾选是否已交投标保证金，保证金金额不能为空!");
    				SysUtil.abort();
    			}
    	}
    	//报价,工期,质量不能为空
    	com.kingdee.eas.xr.helper.ClientVerifyXRHelper.verifyKDTColumnNull(this, this.kdtEntry, "quotedPrice");
    	com.kingdee.eas.xr.helper.ClientVerifyXRHelper.verifyKDTColumnNull(this, this.kdtEntry, "prjPeriod");
    	com.kingdee.eas.xr.helper.ClientVerifyXRHelper.verifyKDTColumnNull(this, this.kdtEntry, "quality");
    	super.verifyInput(e);
    }

    public void prmtreportName_Changed() throws Exception {
    	// TODO Auto-generated method stub
    	super.prmtreportName_Changed();
    	if(!getOprtState().equals(OprtState.VIEW)) {
        	InviteReportInfo invite = (InviteReportInfo) this.prmtreportName.getValue();
        	if(judgeSolution.integrate.equals(invite.getJudgeSolution())) {
        		txtcoefficient.setEnabled(true);
        		txtcoefficient.setRequired(true);
        	}
        	else
        		txtcoefficient.setEnabled(false);
        	invite = InviteReportFactory.getRemoteInstance().getInviteReportInfo(new ObjectUuidPK(invite.getId()));
        	InviteReportEntry2Collection entry2collection = invite.getEntry2(); //获取方案申报招标单位分录信息
        	IMarketSupplierStock isupplier = MarketSupplierStockFactory.getRemoteInstance();
        	IInviteReportEntry2 ientry2 = InviteReportEntry2Factory.getRemoteInstance();
    		kdtEntry.removeRows();

    		if(invite != null) {
    	    	for(int i = 0; i < entry2collection.size(); i++) {
//    	    		InviteReportEntry2Info entryinfo = (InviteReportEntry2Info)entry2collection.get(i);
    	    		InviteReportEntry2Info entryinfo = ientry2.getInviteReportEntry2Info(new ObjectUuidPK(((InviteReportEntry2Info)entry2collection.get(i)).getId()));
    	    		MarketSupplierStockInfo supplierInfo = isupplier.getMarketSupplierStockInfo(
    	    													new ObjectUuidPK(entryinfo.getEvaEnterprise().getId()));
    	    		IRow row = kdtEntry.addRow();
    	    		row.getCell("supplierName").setValue(supplierInfo);
    	    		//获取授权联系人
    	    		MarketSupplierStockEntryPersonCollection personColl = supplierInfo.getEntryPerson();
    	    		for(int j = 0; j < personColl.size(); j++) {
    	    			MarketSupplierStockEntryPersonInfo personEntryInfo = personColl.get(j);
    	    			if(personEntryInfo.isIsDefault()) {
    	    				row.getCell("contact").setValue(personEntryInfo.getPersonName());
    	    				row.getCell("telephone").setValue(personEntryInfo.getPhone());
    	    				row.getCell("payDeposit").setValue(false);
    	    				break;
    	    			}
    	    		}
    	    	}
    		}
    	}
    }
    @Override
    public void actionDoCancel_actionPerformed(ActionEvent e) throws Exception {
    	// TODO Auto-generated method stub
    	if(!XRBillStatusEnum.getEnum("AUDITED").equals(editData.getStatus())) {
    		MsgBox.showWarning("不允许非审核单据作废!");
    		SysUtil.abort();
    	}
    	super.actionDoCancel_actionPerformed(e);
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
        return com.kingdee.eas.port.pm.invite.OpenRegistrationFactory.getRemoteInstance();
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
        com.kingdee.eas.port.pm.invite.OpenRegistrationInfo objectValue = new com.kingdee.eas.port.pm.invite.OpenRegistrationInfo();
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

}