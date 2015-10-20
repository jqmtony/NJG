/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.IPaymentLayout;
import com.kingdee.eas.fdc.contract.PaymentLayoutCollection;
import com.kingdee.eas.fdc.contract.PaymentLayoutFactory;
import com.kingdee.eas.fdc.contract.PaymentLayoutInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class PaymentLayoutListUI extends AbstractPaymentLayoutListUI
{
    private static final Logger logger = CoreUIObject.getLogger(PaymentLayoutListUI.class);
    
    /**
     * output class constructor
     */
    public PaymentLayoutListUI() throws Exception
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
    	if(e.getClickCount()>1){
    		String selId = String.valueOf(this.tblMain.getValueAt(e.getRowIndex(),0));
    		if(UIRuleUtil.isNull(selId)){
    			FDCMsgBox.showInfo("单据ID为空,找不到单据.");
    			return;
    		}
        	UIContext uiContext = new UIContext(this);
        	uiContext.put("UI", this);
        	uiContext.put(UIContext.ID, selId);
    		IUIFactory uiFactory = null;
    		uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWWIN);
    		IUIWindow curDialog = uiFactory.create(ContractProgrammingEditUI.class
    				.getName(), uiContext, null, OprtState.VIEW);
    		curDialog.show();
    	}
    }

    /**
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
        super.tblMain_tableSelectChanged(e);
        //选中行之后,显示相应的清单信息,多选情况下，获取所有的选择块信息
        KDTSelectBlock sb;
        int size = tblMain.getSelectManager().size(); // 获取选择块的总个数
        this.kDTable2.removeRows();
        for (int i = 0; i < size; i++)
        {
            // 获取第i个选择块
            sb = tblMain.getSelectManager().get(i);
            // 遍历每个选择块的所有行
            for (int j = sb.getTop(); j <= sb.getBottom(); j++)
            {
            	String selId = String.valueOf(tblMain.getValueAt(j,0)).trim();
            	IPaymentLayout ip = PaymentLayoutFactory.getRemoteInstance();
            	PaymentLayoutCollection pc = ip.getPaymentLayoutCollection("where ContractPlanNo='"+selId+"'");
            	for(int m=0; m<pc.size(); m++){
            		PaymentLayoutInfo info = pc.get(m);
            		IRow row = this.kDTable2.addRow();
            		row.getCell("id").setValue(info.getId());
            		row.getCell("ghbm").setValue(info.getNumber());
            		row.getCell("ghmc").setValue(info.getPlanName());
            		row.getCell("ghrq").setValue(info.getBizDate());
            		row.getCell("ghje").setValue(info.getPlanAmount());
            		row.getCell("fkjd").setValue(Boolean.valueOf(info.isProgressPayout()));
            		row.getCell("remark").setValue(info.getRemark());
            		row.getCell("hyghNo").setValue(info.getContractPlanNo());
            	}
            }
        }
    }

    /**
     * output menuItemImportData_actionPerformed method
     */
    protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.menuItemImportData_actionPerformed(e);
    }

    /**
     * output treeProject_valueChanged method
     */
    protected void treeProject_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
        super.treeProject_valueChanged(e);
        Set hs = getSelectObjLeafIds();
        Iterator i = hs.iterator();
        StringBuffer str = new StringBuffer("");
        while(i.hasNext()){
               String temp = (String)i.next();
               str.append(temp).append(",");
        }      
        FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		filterItems.add(new FilterItemInfo("isFinal", Boolean.TRUE));
		filterItems.add(new FilterItemInfo("curProject.id",str.toString(),CompareType.INCLUDE));
		logger.info(filter);
		this.mainQuery.setFilter(filter);
		this.tblMain.refresh();
		if (getMainTable().getRowCount() > 0) {
			getMainTable().getSelectManager().select(0, 0);
		}else{
			this.kDTable2.removeRows();
		}
		
    }

    /**
     * output kDTable2_tableClicked method
     */
    protected void kDTable2_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.kDTable2_tableClicked(e);
        if(e.getClickCount()>1){
        	String selId = String.valueOf(this.kDTable2.getValueAt(e.getRowIndex(),0));
        	String projectId = tblMain.getRow(
					tblMain.getSelectManager().getActiveRowIndex()).getCell(
					"curProject.id").getValue() == null ? "" : tblMain.getRow(
					tblMain.getSelectManager().getActiveRowIndex()).getCell(
					"curProject.id").getValue().toString().trim();
			UIContext uiContext = new UIContext(this);
        	uiContext.put("UI", this);
        	uiContext.put("projectId", projectId);
        	uiContext.put(UIContext.ID, selId);
    		IUIFactory uiFactory = null;
    		uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWWIN);
    		IUIWindow curDialog = uiFactory.create(PaymentLayoutEditUI.class
    				.getName(), uiContext, null, OprtState.VIEW);
    		curDialog.show();
        }
    }

    /**
     * output kDTable2_tableSelectChanged method
     */
    protected void kDTable2_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
        super.kDTable2_tableSelectChanged(e);
    }

    /**
     * output btnAddNew_mouseClicked method
     */
    protected void btnAddNew_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output kDWorkButton2_mouseClicked method
     */
    protected void kDWorkButton2_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
        super.kDWorkButton2_mouseClicked(e);
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
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
//        super.actionAddNew_actionPerformed(e);
        int selRow = tblMain.getSelectManager().getActiveRowIndex();
    	if(selRow<0){
    		FDCMsgBox.showInfo("请在合约规划列表中选择一条工程项目.");
    		return ;
    	}
    	String id = String.valueOf(tblMain.getValueAt(selRow, 0));
    	if(UIRuleUtil.isNull(id)){
    		FDCMsgBox.showInfo("合约规划编号为空,不能新增规划清单");
    		return;
    	}
    	if(!isExistPl(id)){
    		FDCMsgBox.showInfo("新增失败,该合约规划已经有一条规划清单记录了.");
    		return;
    	}
    	BigDecimal amount = UIRuleUtil.getBigDecimal(tblMain.getValueAt(selRow, 5));
    	PaymentLayoutInfo info = new PaymentLayoutInfo();
    	info.setContractPlanNo(id);
    	info.setPlanAmount(amount);//合同金额
    	String projectId = tblMain.getRow(selRow).getCell("curProject.id").getValue()==null?"":tblMain.getRow(selRow).getCell("curProject.id").getValue().toString().trim();
    	UIContext uiContext = new UIContext(this);
    	uiContext.put("UI", this);
    	uiContext.put(UIContext.INIT_DATAOBJECT, info);
    	uiContext.put("projectId", projectId);
    	IUIFactory uiFactory = null;
		uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWWIN);
		IUIWindow curDialog = uiFactory.create(PaymentLayoutEditUI.class
				.getName(), uiContext, null, OprtState.ADDNEW);
		curDialog.show();
    }

    /**
     * 检查合约规划是否已经生成了规划清单
     * @param contractId 合约规划id
     * @return
     */
    private boolean isExistPl(String contractId){
    	try {
			IPaymentLayout ip = PaymentLayoutFactory.getRemoteInstance();
			PaymentLayoutCollection pc = ip.getPaymentLayoutCollection("where ContractPlanNo='"+contractId+"'");
			if(UIRuleUtil.isNull(pc) || pc.size()==0){
				return true;
			}
		} catch (BOSException e) {
			logger.error(e.getMessage());
			handUIExceptionAndAbort(e);
		}
    	return false;
    }
    /**
     * output actionView_actionPerformed
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
//        super.actionView_actionPerformed(e);
//    	logger.info("事件来源:"+e.getSource());
//    	int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
//    	String selId = String.valueOf(tblMain.getValueAt(rowIndex,0));
//    	UIContext uiContext = new UIContext(this);
//    	uiContext.put(UIContext.ID, selId);
//		IUIFactory uiFactory = null;
//		uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWWIN);
//		IUIWindow curDialog = uiFactory.create(ContractProgrammingEditUI.class
//				.getName(), uiContext, null, OprtState.VIEW);
//		curDialog.show();
		
		int rowIndex = kDTable2.getSelectManager().getActiveRowIndex();
		if(rowIndex>-1){
	    	String selId = String.valueOf(this.kDTable2.getValueAt(rowIndex,0));
	    	String projectId = tblMain.getRow(
					tblMain.getSelectManager().getActiveRowIndex()).getCell(
					"curProject.id").getValue() == null ? "" : tblMain.getRow(
					tblMain.getSelectManager().getActiveRowIndex()).getCell(
					"curProject.id").getValue().toString().trim();
	    	UIContext uiContext = new UIContext(this);
	    	uiContext.put("UI", this);
	    	uiContext.put("projectId", projectId);
	    	uiContext.put(UIContext.ID, selId);
			IUIFactory uiFactory = null;
			uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWWIN);
			IUIWindow curDialog = uiFactory.create(PaymentLayoutEditUI.class
					.getName(), uiContext, null, OprtState.EDIT);
			curDialog.show();
		}
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
//        super.actionEdit_actionPerformed(e);
    	int rowIndex = kDTable2.getSelectManager().getActiveRowIndex(); 
    	String selId = String.valueOf(this.kDTable2.getValueAt(rowIndex,0));
    	String projectId = tblMain.getRow(
				tblMain.getSelectManager().getActiveRowIndex()).getCell(
				"curProject.id").getValue() == null ? "" : tblMain.getRow(
				tblMain.getSelectManager().getActiveRowIndex()).getCell(
				"curProject.id").getValue().toString().trim();
    	UIContext uiContext = new UIContext(this);
    	uiContext.put("UI", this);
    	uiContext.put("projectId", projectId);
    	uiContext.put(UIContext.ID, selId);
		IUIFactory uiFactory = null;
		if (selId.equals("null")) {
			FDCMsgBox.showInfo("请在付款规划清单列表中选择一条记录.");
		}else{
			uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWWIN);
			IUIWindow curDialog = uiFactory.create(PaymentLayoutEditUI.class
					.getName(), uiContext, null, OprtState.EDIT);
			curDialog.show();
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
     * output actionRefresh_actionPerformed
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRefresh_actionPerformed(e);
        kDTable2.refresh();
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

//    public ICoreBase getBillInterface() throws Exception {
//		return PaymentLayoutFactory.getRemoteInstance();
//	}

    protected  void fetchInitData() throws Exception{	
    	return;
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

	protected void audit(List ids) throws Exception {
		// TODO Auto-generated method stub
		
	}

	protected ICoreBase getRemoteInterface() throws BOSException {
		return PaymentLayoutFactory.getRemoteInstance();
	}

	protected void unAudit(List ids) throws Exception {
		
	}
	public void onLoad() throws Exception {

		if (!SysContext.getSysContext().getCurrentOrgUnit().isIsCostOrgUnit()) {
			FDCMsgBox.showInfo("当前组织不是成本中心，不能进入！");
			SysUtil.abort();
		}
		super.onLoad();
		this.kDTable2.setEditable(false);
		tblMain.getColumn("isFinal").getStyleAttributes().setHided(true);
		tblMain.getColumn("edition").getStyleAttributes().setHided(true);
		
		updateButtonStatus();
	}
	
	// 重写此基类方法 放出按钮
	protected void updateButtonStatus() {

		super.updateButtonStatus();
		this.actionAddNew.setVisible(true);
		this.actionEdit.setVisible(true);
		this.actionRemove.setVisible(true);
		this.actionAddNew.setEnabled(true);
		this.actionEdit.setEnabled(true);
		this.actionRemove.setEnabled(true);
		this.menuEdit.setVisible(true);
		this.actionAuditResult.setVisible(false);
		this.actionQuery.setVisible(true);
		this.actionAbout.setEnabled(true);
		this.actionQuery.setEnabled(true);

//		this.actionAudit.setEnabled(true);
//		this.actionUnAudit.setEnabled(true);
	}
}