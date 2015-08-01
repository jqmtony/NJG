/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class InvalidWorkLoadSplitListUI extends AbstractInvalidWorkLoadSplitListUI
{
    private static final Logger logger = CoreUIObject.getLogger(InvalidWorkLoadSplitListUI.class);
    
    private static final String BILLSPLITSTATE = "costSplit.splitState";
    
    public InvalidWorkLoadSplitListUI() throws Exception {
		super();
	}
 
    public void onLoad() throws Exception {
    	super.onLoad();
    	actionQuery.setEnabled(true);
    	actionQuery.setVisible(true);
    	actionDelVoucher.setEnabled(false);
    	actionDelVoucher.setVisible(false);
    	actionVoucher.setEnabled(false);
    	actionVoucher.setVisible(false);
    	actionTraceDown.setEnabled(false);
    	actionTraceDown.setVisible(false);
    	actionEdit.setVisible(false);
    	actionEdit.setEnabled(false);
    	actionRemove.setVisible(false);
    	actionRemove.setEnabled(false);
    	actionAddNew.setVisible(false);
    	actionAddNew.setEnabled(false);
    	actionAudit.setEnabled(false);
    	actionAudit.setVisible(false);
    	actionUnAudit.setEnabled(false);
    	actionUnAudit.setVisible(false);
    	
    	actionWorkFlowG.setEnabled(false);
    	actionWorkFlowG.setVisible(false);
    	menuWorkFlow.setVisible(false);
    	menuWorkFlow.setEnabled(false);
    	menuBiz.setEnabled(false);
    	menuBiz.setVisible(false);
    	actionAuditResult.setVisible(false);
    	actionAuditResult.setEnabled(false);
    	this.setSplitStateColor();    	
    }

    protected boolean isVoucherVisible() {
    	return false;
    }
    
    protected ICoreBase getBizInterface() throws Exception {
    	return PaymentSplitFactory.getRemoteInstance();
    }
    
    protected String getEditUIName() {
    	return WorkLoadSplitEditUI.class.getName();
    }

	protected String getKeyFieldName() {
		return "costSplit.id";
	}

	// 完全拆分工作量（绿色）、部分拆分工作量（黄色）、未拆分工作量
	protected void setSplitStateColor() {
		String splitState;
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			IRow row = tblMain.getRow(i);

			ICell cell = row.getCell(BILLSPLITSTATE);
			if (cell.getValue() == null || cell.getValue().toString().equals("")) {
				splitState = CostSplitStateEnum.NOSPLIT.toString();
				cell.setValue(splitState);
			} else {
				splitState = cell.getValue().toString();
			}
			
			if (splitState.equals(CostSplitStateEnum.ALLSPLIT.toString())) {
				row.getStyleAttributes().setBackground(FDCSplitClientHelper.COLOR_ALLSPLIT);
			} else if (splitState.equals(CostSplitStateEnum.PARTSPLIT.toString())) {
				row.getStyleAttributes().setBackground(	FDCSplitClientHelper.COLOR_PARTSPLIT);
			} else {
				row.getStyleAttributes().setBackground(FDCSplitClientHelper.COLOR_NOSPLIT);
			}
		}
	}
}