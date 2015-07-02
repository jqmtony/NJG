/**
 * output package name
 */
package com.kingdee.eas.custom.richinf.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.eas.basedata.master.cssp.CustomerCollection;
import com.kingdee.eas.basedata.master.cssp.CustomerFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.custom.richinf.BillState;
import com.kingdee.eas.custom.richinf.IRichExamed;
import com.kingdee.eas.custom.richinf.IRichInvoiceRequest;
import com.kingdee.eas.custom.richinf.RichExamedFactory;
import com.kingdee.eas.custom.richinf.RichExamedInfo;
import com.kingdee.eas.custom.richinf.RichInvoiceRequestCollection;
import com.kingdee.eas.custom.richinf.RichInvoiceRequestFactory;
import com.kingdee.eas.fm.common.FMIsqlFacadeFactory;
import com.kingdee.eas.fm.common.IFMIsqlFacade;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class RichInvoiceRequestEditUI extends AbstractRichInvoiceRequestEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(RichInvoiceRequestEditUI.class);
    
    /**
     * output class constructor
     */
    public RichInvoiceRequestEditUI() throws Exception
    {
        super();
        //本次申请开票金额
        
    }
    
    private void kpCompany_DataChanged(DataChangeEvent dce){
    	if(dce.getNewValue() == null){
    		
		}else if(!dce.getNewValue().equals(dce.getOldValue())){
			
		}
    }
    
    private void amount_changePerform() {
//    	if(reqSumAmount != null) {
//    		txtreqSumAmount.setValue(txtamount.getBigDecimalValue().add(reqSumAmount));
//    	}else {
//    		txtreqSumAmount.setValue(txtamount.getBigDecimalValue());
//    	}
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	
    	kdtEntrys.getColumn("ysAmount").getStyleAttributes().setLocked(true);
    	kdtEntrys.getColumn("djd").getStyleAttributes().setLocked(true);
    	kdtEntrys.getHeadRow(0).getCell("khyhxAmount").setValue("客户已申请金额");
    	kdtEntrys.getHeadRow(0).getCell("nbyhxAmount").setValue("内部已申请金额");
    	actionAudit.setEnabled(true);
    	actionCreateFrom.setVisible(false);
    	actionTraceUp.setVisible(true);
    	actionTraceDown.setVisible(true);
    	actionTraceUp.setEnabled(true);
    	actionTraceDown.setEnabled(true);
    	
    	chkMenuItemSubmitAndAddNew.setVisible(false);
    	chkMenuItemSubmitAndAddNew.setEnabled(false);
    	if(getBOTPViewStatus()==1) {
    		if(editData.getBillState() == null)
    			billState.setSelectedItem(BillState.SAVE);
    		String ldNumber = editData.getLdNumber();
    		//初始化累计开票申请金额，根据落单号累加之前的申请开票金额
    		StringBuffer sb = new StringBuffer();
    		sb.append("select amount where ldNumber='");
    		sb.append(ldNumber);
    		sb.append("' and billState<>'SAVE'  order by reqSumAmount desc ");
    		IRichInvoiceRequest ir = RichInvoiceRequestFactory.getRemoteInstance();
    		RichInvoiceRequestCollection riColl = ir.getRichInvoiceRequestCollection(sb.toString());
    		BigDecimal requestTotal = BigDecimal.ZERO;
    		for(int i=riColl.size()-1; i>=0; i--) {
    			requestTotal = requestTotal.add(riColl.get(i).getAmount());
    		}
    		txtreqSumAmount.setValue(requestTotal);
    		
    		//初始化累计到检单金额，根据落单号找到之前的金额之和，再加上此次分录的金额
    		IFMIsqlFacade iff = FMIsqlFacadeFactory.getRemoteInstance();
    		sb = new StringBuffer();
    		sb.append("select DISTINCT rire.CFDjdID djdid,rire.CFYsAmount jsamount from ");
        	sb.append("CT_RIC_RichInvoiceRequestEntry rire left join CT_RIC_RichInvoiceRequest requ ");
        	sb.append("on rire.fparentid=requ.fid where requ.cfBillState<>'SAVE' and requ.CFLdNumber='");
        	sb.append(ldNumber+"'");
        	IRowSet rs = iff.executeQuery(sb.toString(),null);
        	BigDecimal djTotal = BigDecimal.ZERO;
        	Set<String> befores = new HashSet<String>();
        	if(rs != null && rs.size() > 0) {
        		try {
    				while(rs.next()){
    					befores.add(rs.getString("djdid"));
    					djTotal = djTotal.add(rs.getBigDecimal("jsamount"));
    				}
    			} catch (SQLException e) {
    				handUIException(e);
    			}
        	}
        	for(int i=kdtEntrys.getRowCount3()-1; i>=0; i--) {
        		if(!befores.contains(((RichExamedInfo)kdtEntrys.getCell(i,"djd").getValue()).getId().toString())){
        			if(kdtEntrys.getCell(i,"ysAmount").getValue() != null){
        				djTotal = djTotal.add((BigDecimal)kdtEntrys.getCell(i,"ysAmount").getValue());
        			}
        		}
        	}
        	txtdjAmount.setValue(djTotal);
        	
        	//初始化累计已开发票金额
        	String sql = "select FAmount from T_AR_OtherBill where FBillStatus=3 and CFLdNo='"+ldNumber+"'";
        	rs = iff.executeQuery(sql,null);
        	if(rs != null && rs.size() > 0) {
        		BigDecimal fpTotal = BigDecimal.ZERO;
        		try {
    				while(rs.next()){
    					fpTotal = fpTotal.add(rs.getBigDecimal("FAmount"));
    				}
    			} catch (SQLException e) {
    				handUIException(e);
    			}
    			txtinvoicedAmount.setValue(fpTotal);
    			
        	}
        	
        	if(editData.isDjkp() && editData.getSourceBillId()!=null){
        		RichExamedInfo reInfo = RichExamedFactory.getRemoteInstance().getRichExamedInfo(new ObjectUuidPK(editData.getSourceBillId()));
        		String kpid = reInfo.getKpCompany().getId().toString();
        		CustomerCollection cc = CustomerFactory.getRemoteInstance().getCustomerCollection("where internalcompany='"+kpid+"'");
        		if(cc.size() > 0) {
        			prmtkpUnit.setValue(cc.get(0));
        		}
        	}
    	}
    	if(editData.isDjkp()){
    		kdtEntrys.getColumn("khyhxAmount").getStyleAttributes().setHided(true);
        	kdtEntrys.getColumn("nbyhxAmount").getStyleAttributes().setLocked(true);
        	String djid = null;
        	for(int i=kdtEntrys.getRowCount3()-1; i>=0; i--) {
        		djid = ((RichExamedInfo)kdtEntrys.getCell(i,"djd").getValue()).getId().toString();
        		kdtEntrys.getCell(i,"nbyhxAmount").setValue(getYsqAmount(djid,true));
        	}
    	}else{
    		kdtEntrys.getColumn("khyhxAmount").getStyleAttributes().setLocked(true);
        	kdtEntrys.getColumn("nbyhxAmount").getStyleAttributes().setHided(true);
        	String djid = null;
        	for(int i=kdtEntrys.getRowCount3()-1; i>=0; i--) {
        		djid = ((RichExamedInfo)kdtEntrys.getCell(i,"djd").getValue()).getId().toString();
        		kdtEntrys.getCell(i,"khyhxAmount").setValue(getYsqAmount(djid,false));
        	}
    	}
    	//根据当前财务组织过滤销售员
    	CompanyOrgUnitInfo couInfo = SysContext.getSysContext().getCurrentFIUnit();
    	if(couInfo != null){
    		EntityViewInfo evi1 = new EntityViewInfo();
    		FilterInfo filter1 = new FilterInfo();
    		evi1.setFilter(filter1);
    		filter1.getFilterItems().add(new FilterItemInfo("AdminOrgUnit.longNumber",couInfo.getLongNumber()+"%",CompareType.LIKE));
    		prmtsales.setEntityViewInfo(evi1);
    		
    	}
    	
    	if(OprtState.EDIT.equals(getOprtState()) && BillState.SUBMIT.equals(editData.getBillState())){
    		actionSave.setEnabled(false);
    	}else if(OprtState.VIEW.equals(getOprtState()) && BillState.AUDIT.equals(editData.getBillState())){
    		actionEdit.setEnabled(false);
    		actionRemove.setEnabled(false);
    	}
    	
    	kdtEntrys.addKDTEditListener(new KDTEditAdapter(){
    		@Override
    		public void editStopped(KDTEditEvent e) {
    			if("bencisq".equals(kdtEntrys.getColumnKey(e.getColIndex()))) {
    				txtamount.setValue(UIRuleUtil.getBigDecimal(UIRuleUtil.sum(kdtEntrys,"bencisq")));
    			}
    		}
    	});
    	
    	if(kdtEntrys.getRowCount3()>0){
    		//签约,到检,开票,付款
        	IRichExamed ire = RichExamedFactory.getRemoteInstance();
        	RichExamedInfo reinfo = null;
        	Set ids = new HashSet();
        	for(int i=kdtEntrys.getRowCount3()-1; i>=0; i--) {
        		reinfo = ire.getRichExamedInfo(new ObjectUuidPK(((RichExamedInfo)kdtEntrys.getCell(i,"djd").getValue()).getId()));
        		if(reinfo.getQyUnit() != null) {
        			ids.add(reinfo.getQyUnit().getId().toString());
        		}
				if(reinfo.getKpUnit() != null) {
					ids.add(reinfo.getKpUnit().getId().toString());  			
				}
				if(reinfo.getDjUnit() != null) {
					ids.add(reinfo.getDjUnit().getId().toString());
				}
				if(reinfo.getFkUnit() != null) {
					ids.add(reinfo.getFkUnit().getId().toString());
				}
        	}
        	EntityViewInfo evi = new EntityViewInfo();
        	FilterInfo filter = new FilterInfo();
        	evi.setFilter(filter);
        	filter.getFilterItems().add(new FilterItemInfo("id",ids,CompareType.INCLUDE));
        	prmtkpUnit.setEntityViewInfo(evi);
    	}
    }
    
    
    @Override
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
    	if(BillState.SUBMIT.equals(editData.getBillState())){
    		RichInvoiceRequestFactory.getRemoteInstance().audit(editData);
    		editData.setBillState(BillState.AUDIT);
    		loadData();
    		
    		setOprtState(OprtState.VIEW);
    		actionRemove.setEnabled(false);
    		actionEdit.setEnabled(false);
    		actionCopy.setEnabled(false);
    		MsgBox.showInfo("审核成功！");
    	}
    	else
    		MsgBox.showInfo("单据不是提交状态，不能进行此操作！");
    }
    
    private String getDestBySrc(String srcid){
    	StringBuffer sb = new StringBuffer();
    	sb.append("select FDestObjectID from T_BOT_Relation where FSrcObjectID=?");
    	try {
    		IRowSet rs = iff.executeQuery(sb.toString(),new Object[]{srcid});
    		if(rs.next()){
    			return rs.getString("FDestObjectID");
    		}
		} catch (EASBizException e) {
			handUIException(e);
		} catch (BOSException e) {
			handUIException(e);
		} catch (SQLException e) {
			handUIException(e);
		}
    	return null;
    }
    
    @Override
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
    	if(BillState.AUDIT.equals(editData.getBillState())){
    		if(getDestBySrc(editData.getId().toString()) != null){
    			MsgBox.showInfo("开票申请单关联应收单，不能反审核！");
				SysUtil.abort();
    		}
    		RichInvoiceRequestFactory.getRemoteInstance().unAudit(editData);
    		editData.setBillState(BillState.SUBMIT);
    		loadData();
    		actionEdit.setEnabled(true);
    		actionRemove.setEnabled(true);
    		MsgBox.showInfo("反审核成功！");
    	}
    	else
    		MsgBox.showInfo("单据不是审核状态，不能进行此操作！");
    }
    
    
    @Override
    public void kdtEntrys_Changed(int rowIndex, int colIndex) throws Exception {
    }
    
    public void onShow() throws Exception {
    	super.onShow();
    	
        contDescription.setVisible(false);
    	stateEnable();
    	initEntrys();
    }
    
    private void initEntrys() {
    	kdtEntrys_detailPanel.getInsertLineButton().setVisible(false);
    	kdtEntrys_detailPanel.getInsertLineButton().setVisible(false);
    	kdtEntrys_detailPanel.getAddNewLineButton().setVisible(false);
    	kdtEntrys_detailPanel.getAddNewLineButton().setVisible(false);
	}
    
    @Override
    protected void verifyInput(ActionEvent actionevent) throws Exception {
    	//super.verifyInput(actionevent);
    	if(prmtsales.getValue()==null) {
    		MsgBox.showInfo("销售员不能为空！");
    		prmtsales.grabFocus();
    		SysUtil.abort();
    	}
    	
    	if(prmtkpUnit.getValue()==null){
    		MsgBox.showInfo("开票抬头不能为空！");
    		prmtkpUnit.grabFocus();
    		SysUtil.abort();
    	}
    	if(txtldNumber.getText()==null || "".equals(txtldNumber.getText())){
    		MsgBox.showInfo("落单号不能为空！");
    		txtldNumber.grabFocus();
    		SysUtil.abort();
    	}
    	if(kdtEntrys.getRowCount3() > 0) {
    		if(editData.isDjkp()){
    			checkEntrys("nbyhxAmount");
    		}else {
    			checkEntrys("khyhxAmount");
    		}
    	}else if(txtamount.getBigDecimalValue() == null){
    		MsgBox.showInfo("本次申请开票金额不能为空！");
    		txtamount.grabFocus();
    		SysUtil.abort();
    	}
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
    		buffer.append("and rreq.CFDjkp=1 ");
    	}else{
    		buffer.append("and rreq.CFDjkp<>1 ");
    	}
    	if(editData.getId() != null) {
    		buffer.append("and rreq.fid<>'");
    		buffer.append(editData.getId().toString());
        	buffer.append("' ");
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

	private void checkEntrys(String colName) {
		BigDecimal amount = null;
		BigDecimal ysq = null;
		BigDecimal benci = null;
		String djid = null;
		for(int i=kdtEntrys.getRowCount3()-1; i>=0; i--) {
			benci = (BigDecimal)kdtEntrys.getCell(i,"bencisq").getValue();
			djid = ((RichExamedInfo)kdtEntrys.getCell(i,"djd").getValue()).getId().toString();
			if(benci == null){
				MsgBox.showInfo("第"+(i+1)+"行的本次申请开票金额不能为空！");
				SysUtil.abort();
			}else{
				if(benci.compareTo(BigDecimal.ZERO) == 0){
					MsgBox.showInfo("第"+(i+1)+"行的本次申请开票金额不能为零！");
					SysUtil.abort();
				}
				amount = (BigDecimal)kdtEntrys.getCell(i,"ysAmount").getValue();
				if("nbyhxAmount".equals(colName)){
					ysq = getYsqAmount(djid,true);
				}else{
					ysq = getYsqAmount(djid,false);
				}
				 
				if(benci.compareTo(amount.subtract(ysq))>0){
					MsgBox.showInfo("第"+(i+1)+"行的本次申请金额不能超出未申请金额："+amount.subtract(ysq));
					SysUtil.abort();
				}
			}
		}
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
        actionSave.setEnabled(false);
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
        if( editData.getBillState().equals(BillState.SUBMIT)){
    		actionSave.setEnabled(false);
    	}
        stateEnable();
    }

	private void stateEnable() {
		contdjAmount.setEnabled(false);
    	//累计申请开票金额
    	contreqSumAmount.setEnabled(false);
    	//累计已开票金额
    	continvoicedAmount.setEnabled(false);
    	contbizState.setEnabled(false);
    	contbillState.setEnabled(false);
    	chkdjkp.setEnabled(false);
    	if(kdtEntrys.getRowCount3() > 0) {
    		contamount.setEnabled(false);
    		for(int i=kdtEntrys.getRowCount3()-1; i>=0; i--) {
    			
    		}
    	}
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
    	
    	if(!BillState.AUDIT.equals(editData.getBillState())){
			MsgBox.showInfo("单据不是已审核状态！请先审核！");
			SysUtil.abort();
		}
    	
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
        return com.kingdee.eas.custom.richinf.RichInvoiceRequestFactory.getRemoteInstance();
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
        com.kingdee.eas.custom.richinf.RichInvoiceRequestInfo objectValue = new com.kingdee.eas.custom.richinf.RichInvoiceRequestInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
        objectValue.setBizDate(new Date());
        return objectValue;
    }

}