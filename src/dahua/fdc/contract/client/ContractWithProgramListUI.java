/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.contract.ContractWithProgramFactory;
import com.kingdee.eas.fdc.finance.ContractPayPlanFactory;
import com.kingdee.eas.fdc.finance.client.ContractPayPlanEditUI;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ContractWithProgramListUI extends AbstractContractWithProgramListUI
{
    /**
     * output class constructor
     */
    public ContractWithProgramListUI() throws Exception
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

    
    protected void treeProject_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
        super.treeProject_valueChanged(e);
    }

    public void actionView_actionPerformed(ActionEvent e) throws Exception
	{
		//super.actionView_actionPerformed(e);
	}
    
    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	if (getSelectContractId() == null) {
			MsgBox.showWarning(this, "没有选中合同，无法修改关联合约规划");
			SysUtil.abort();
		}
		boolean hasMutex = false;
		List list = new ArrayList();
		String id = this.getSelectContractId();
		list.add(id + "contractWithProgram");
		try {
			FDCClientUtils.requestDataObjectLock(this, list, "edit");
			ContractWithProgramEditUI.showEditUI(this, getSelectContractId(),
					"EDIT");
			this.displayBillByContract(null);
		} catch (Throwable e1) {
			this.handUIException(e1);
			hasMutex = FDCClientUtils.hasMutexed(e1);
		} finally {
			if (!hasMutex) {
				try {
					FDCClientUtils.releaseDataObjectLock(this, list);
				} catch (Throwable e1) {
					this.handUIException(e1);
				}
			}
		}
    }

    protected void tblProgramming_tableClicked(KDTMouseEvent e)
    		throws Exception {
//    	super.tblProgramming_tableClicked(e);
    }

	protected void audit(List ids) throws Exception {
		// TODO Auto-generated method stub
		
	}
	//数据对象变化时，刷新界面状态
    protected void setActionState(){

    }
    
	protected SelectorItemCollection genBillQuerySelector() {
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("id");
		selectors.add("contractBill");
		selectors.add("contractProgram.*");
		return null;
	}

	protected KDTable getBillListTable() {
		return this.tblProgramming;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ContractWithProgramFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return ContractWithProgramEditUI.class.getName();
	}

	protected ICoreBillBase getRemoteInterface() throws BOSException {
		return null;
	}

	protected boolean isFootVisible() {
		return false;
	}
	protected String getKeyFieldName()
	{
		return "id";
	}
	
	protected void unAudit(List ids) throws Exception {
		
	}
	protected void displayBillByContract(EntityViewInfo view)
			throws BOSException {
		String contractId = getSelectContractId();
		if (contractId == null) {
			return;
		}
		ContractWithProgramHandler.fillTable(contractId, getBillListTable(), true);
	}
	
	protected void fetchInitData() throws Exception {
//		super.fetchInitData();
	}

	//默认附件不可见
    protected boolean isShowAttachmentAction()
    {
        return false;
    }
    
	private String getSelectContractId() {
		int rowIndex = this.getMainTable().getSelectManager()
				.getActiveRowIndex();
		if(rowIndex==-1){
			return null;
		}
		String contractId = (String) getMainTable().getCell(rowIndex,
				getKeyFieldName()).getValue();
		return contractId;
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		this.actionAddNew.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionView.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.actionWorkFlowG.setVisible(false);
		this.actionAudit.setVisible(false);
		this.actionUnAudit.setVisible(false);
		this.menuBiz.setVisible(false);
		this.menuWorkFlow.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		actionAuditResult.setEnabled(false);
		actionAuditResult.setVisible(false);
		FDCClientHelper.setActionEnable(new ItemAction[] { actionAddNew,
				actionAttachment, actionPrint, actionPrintPreview,
				actionWorkFlowG, actionAudit, actionUnAudit, actionRemove,
				actionView, actionCancel, actionCancelCancel }, false);
//		CostCenterOrgUnitInfo currentOrg = SysContext.getSysContext()
//				.getCurrentCostUnit();
//		if (currentOrg == null || !currentOrg.isIsBizUnit()) {
//			actionEdit.setEnabled(false);
//		}
		getBillListTable().getColumn("number").setWidth(100);
		getBillListTable().getColumn("name").setWidth(100);
		getBillListTable().getColumn("acctNumber").setWidth(120);
		getBillListTable().getColumn("acctName").setWidth(120);
		getBillListTable().getColumn("desc").setWidth(200);
	}
	
	/**
	 * 返回定位字段的集合
	 * @author zhiyuan_tang 2010/07/12
	 */
	protected String[] getLocateNames() {
		return new String[] {"number", "contractName", "partB.name", "contractType.name", "signDate"};
	}
}