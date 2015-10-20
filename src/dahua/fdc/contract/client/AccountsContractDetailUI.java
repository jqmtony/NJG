/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.Action;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.aimcost.ProjectCostRptFacadeFactory;
import com.kingdee.eas.fdc.basedata.ContractSourceInfo;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostSplitBillTypeEnum;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ParamValue;
import com.kingdee.eas.fdc.basedata.RetValue;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class AccountsContractDetailUI extends AbstractAccountsContractDetailUI
{
    private static final Logger logger = CoreUIObject.getLogger(AccountsContractDetailUI.class);
    
    
    private RetValue retValue ;
    private boolean displayContract = true;
    private boolean displayNoText   = true;
    
    /**
     * output class constructor
     */
    public AccountsContractDetailUI() throws Exception
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

    public void actionDisplayAll_actionPerformed(ActionEvent e)
			throws Exception {
    	displayContract = true;
    	displayNoText   = true;
    	refresh(e);
	}

	public void actionDisplayConNoText_actionPerformed(ActionEvent e)
			throws Exception {
		displayContract = false;
    	displayNoText   = true;
    	refresh(e);
	}

	public void actionDisplayContract_actionPerformed(ActionEvent e)
			throws Exception {
		displayContract = true;
    	displayNoText   = false;
    	refresh(e);
	}
	
	protected void refresh(ActionEvent e) throws Exception
    {
		fetchData();
    	fillData();
    }

	/**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    	if(e.getClickCount()==1){
			//****  do  nothing  ****
		}
		if (e.getClickCount() == 2) {

			int rowIndex = e.getRowIndex();

			if(getMainTable().getRow(rowIndex)==null)
				return;
			
			Object value = getMainTable().getRow(rowIndex).getUserObject();
			if (value == null)
				return;
			
			this.setCursorOfWair();
			if(value!=null&& value instanceof ContractBillInfo){
				ContractBillInfo contractInfo=(ContractBillInfo)value;
				UIContext uiContext = new UIContext(ui);
				uiContext.put(UIContext.ID, contractInfo.getId().toString());
				// 创建UI对象并显示
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
						.create(ContractFullInfoUI.class.getName(), uiContext, null,
								"VIEW");
				uiWindow.show();
			}
			if(value!=null&&value instanceof ContractWithoutTextInfo){
				logger.info("此合同是无文本合同！");
				ContractWithoutTextInfo contractInfo=(ContractWithoutTextInfo)value;
				UIContext uiContext = new UIContext(ui);
				uiContext.put(UIContext.ID, contractInfo.getId().toString());
				// 创建UI对象并显示
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
						.create(ContractWithoutTextEditUI.class.getName(), uiContext, null,
								"VIEW");
				uiWindow.show();
			}
			this.setCursorOfDefault();
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

	protected ICoreBase getBizInterface() throws Exception {
		return ContractBillFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return ContractFullInfoUI.class.getName();
	}
	public static boolean showDialogWindows(IUIObject ui, java.util.Map param)
			throws UIException {
		UIContext uiContext = new UIContext(ui);
		uiContext.put("accountInfo", param.get("accountInfo"));
		uiContext.put("selectObjId", param.get("selectObjId"));
		uiContext.put("leafPrjIds", param.get("leafPrjIds"));
		uiContext.put("selectObjIsPrj", param.get("selectObjIsPrj"));
		uiContext.put("node", param.get("node"));
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(AccountsContractDetailUI.class.getName(), uiContext, null,
						"VIEW");
		uiWindow.show();
		return true;
	}
	public void onLoad() throws Exception{
		initControl();
		super.onLoad();
		initTable();
		fetchData();
		fillData();
	}
	private void fillData() {
		tblMain.removeRows();
		Map uiContext = this.getUIContext();
		CostAccountInfo costAccountInfo = (CostAccountInfo)uiContext.get("accountInfo");
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) uiContext.get("node");
//		uiContext.put("selectObjId", param.get("selectObjId"));
//		uiContext.put("leafPrjIds", param.get("leafPrjIds"));
//		uiContext.put("selectObjIsPrj", param.get("selectObjIsPrj"));
		int level = node.getLevel();
		for(int i=0;i<node.getChildCount();i++){
			fillData(level,(DefaultKingdeeTreeNode)node.getChildAt(i));
		}
		gatherDatas();
	}
	private void fillData(int level ,DefaultKingdeeTreeNode node){
		tblMain.getTreeColumn().setDepth(node.getLevel()-level+1);
		IRow row = tblMain.addRow();
		String name = node.getText() ;
		//row.setTreeLevel(node.getLevel()-level);
		row.getCell("curProject").setValue(name);
		row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
		if(node.isLeaf()&&node.getUserObject() instanceof CurProjectInfo){
			RetValue projectCostAccounts = (RetValue)retValue.get("projectCostAccounts");
			RetValue accountContracts = (RetValue)retValue.get("accountContracts");
			RetValue accountContractSplitValues = (RetValue)retValue.get("accountContractSplitValues");
			Map contractBillMap = (Map)retValue.get("contractBillMap");
			Map contractWithoutTextMap = (Map) retValue.get("contractWithoutTextMap");
			
			Map totalSettlePriceMap = (Map) retValue.get("totalSettlePriceMap");
			Map hasPayAmtMap = (Map)retValue.get("hasPayAmtMap");
			Map lastPriceMap = (Map)retValue.get("lastPriceMap");
			CurProjectInfo curProject = (CurProjectInfo)node.getUserObject();
			String curProjectId = curProject.getId().toString();
			List costAccounts = (List)projectCostAccounts.get(curProjectId);
			if(costAccounts!=null){
				for(Iterator it=costAccounts.iterator();it.hasNext();){
					CostAccountInfo costAccount = (CostAccountInfo)it.next();
					row = tblMain.addRow();
					row.setTreeLevel(node.getLevel()-level);
					row.getCell("costAccount").setValue(costAccount.getLongNumber().replace('!', '.'));
					row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
					List contractIds = (List)accountContracts.get(costAccount.getId().toString());
					if(contractIds!=null){
						for(Iterator contractIt = contractIds.iterator();contractIt.hasNext();){
							String contractId = (String)contractIt.next();
							FDCBillInfo contractBill = null;
							boolean isInvite = false;
							boolean isTotalSett = false;
							if(contractBillMap!=null&&contractBillMap.containsKey(contractId)){
								contractBill = (ContractBillInfo)contractBillMap.get(contractId);
								if(((ContractBillInfo)contractBill).getContractSourceId()!=null)
								{
									isInvite = ContractSourceInfo.INVITE_VALUE.equals(((ContractBillInfo)contractBill).getContractSourceId().getId().toString());
									isTotalSett = ((ContractBillInfo)contractBill).isHasSettled();
								}
								
								
							}else if(contractWithoutTextMap!=null&&contractWithoutTextMap.containsKey(contractId)){
								contractBill = (ContractWithoutTextInfo)contractWithoutTextMap.get(contractId);
							}
							if(contractBill!=null){
								row = tblMain.addRow();
								row.setUserObject(contractBill);
								row.getCell("contractNumber").setValue(contractBill.getNumber());
								row.getCell("contractName").setValue(contractBill.getName());
								row.setTreeLevel(node.getLevel()-level+1);
								if(contractBill.containsKey("curProject")){
									CurProjectInfo conCur = (CurProjectInfo)contractBill.get("curProject");
									row.getCell("contractCurProject").setValue(conCur.getName());
								}
								if(contractBill.containsKey("partB")){
									SupplierInfo supplier = (SupplierInfo)contractBill.get("partB");
									row.getCell("partB").setValue(supplier.getName());
								}
								if(contractBill.containsKey("signDate")){
									row.getCell("date").setValue(contractBill.get("signDate"));
								}
								
								if(contractBill instanceof ContractBillInfo){
									row.getCell("isInvite").setValue(Boolean.valueOf(isInvite));
									//是否最终结算
									row.getCell("isTotalSettle").setValue(Boolean.valueOf(isTotalSett));
								}
								
								
								
								
								//合同金额
								row.getCell("amount").setValue(contractBill.getAmount());
								//已付款金额
								BigDecimal hasPayAmt = FDCHelper.toBigDecimal(hasPayAmtMap.get(contractId));
								if(hasPayAmt.compareTo(FDCHelper.ZERO)!=0)
									row.getCell("paidAmt").setValue(hasPayAmt);
								
								//最新造价lastPriceMap
								if(lastPriceMap!=null)
									row.getCell("lastPrice").setValue(lastPriceMap.get(contractId));
								
								RetValue accountContractSplitValue = (RetValue)accountContractSplitValues.get(costAccount.getId().toString()+contractId);
								
								BigDecimal totalSettlePrice = FDCHelper.ZERO;
								BigDecimal allNotPay = FDCHelper.ZERO;
								
								if(totalSettlePriceMap!=null){
									totalSettlePrice = FDCHelper.toBigDecimal(totalSettlePriceMap.get(contractId));
									/***
									 * 完工未付款
									 * 完工未付款 = 合同已实现产值 - 合同已付款
									 */
									allNotPay = totalSettlePrice.subtract(hasPayAmt);
								}
								
								if(accountContractSplitValue!=null){
									//合同拆分金额
									row.getCell("contractSplitAmt").setValue(accountContractSplitValue.getBigDecimal(CostSplitBillTypeEnum.CONTRACTSPLIT_VALUE));
									//变更拆分金额
									row.getCell("conChangeSplitAmt").setValue(accountContractSplitValue.getBigDecimal(CostSplitBillTypeEnum.CNTRCHANGESPLIT_VALUE));
									//结算拆分金额 
									row.getCell("settlementSplitAmt").setValue(accountContractSplitValue.getBigDecimal(CostSplitBillTypeEnum.SETTLEMENTSPLIT_VALUE));
									//付款拆分金额
									row.getCell("paymentSplitAmt").setValue(accountContractSplitValue.getBigDecimal(CostSplitBillTypeEnum.PAYMENTSPLIT_VALUE));
								}
								//已实现产值
								if(totalSettlePrice.compareTo(FDCHelper.ZERO)!=0)
									row.getCell("totalSettlePrice").setValue(totalSettlePrice);
								
								//完工未付款
								if(allNotPay.compareTo(FDCHelper.ZERO)!=0)
									row.getCell("allNotPaid").setValue(allNotPay);
								/***
								 * 付款率 = 已付款/已实现产值
								 */
								if(hasPayAmt.compareTo(FDCHelper.ZERO)!=0&&totalSettlePrice.compareTo(FDCHelper.ZERO)!=0){
									row.getCell("payPercent").setValue(hasPayAmt.divide(totalSettlePrice,2,BigDecimal.ROUND_HALF_UP));
								}
								
							}
						}
					}
					
				}
			}
			
		}else{
			
			for(int i=0;i<node.getChildCount();i++){
				fillData(level,(DefaultKingdeeTreeNode)node.getChildAt(i));
			}
		}
	}
	private List getUnionColumn(){
		
		List amountColumns = new ArrayList();
//		amountColumns.add("totalSettlePrice");
		amountColumns.add("contractSplitAmt");
		amountColumns.add("conChangeSplitAmt");
		amountColumns.add("settlementSplitAmt");
		amountColumns.add("paymentSplitAmt");
		return amountColumns;
	}
	/*
	 * 汇总数据
	 */
	private void gatherDatas() {
		KDTable table = this.tblMain;
		List amountColumns = getUnionColumn();
		List zeroLeverRowList = new ArrayList();
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			if (row.getTreeLevel() == 0) {
				zeroLeverRowList.add(row);
			}
			if (row.getUserObject() == null) {// 非叶结点
				// 设置汇总行
				int level = row.getTreeLevel();
				List rowList = new ArrayList();
				for (int j = i + 1; j < table.getRowCount(); j++) {
					IRow rowAfter = table.getRow(j);
					if (rowAfter.getTreeLevel() <= level) {
						break;
					}
					rowList.add(rowAfter);
				}
				for (int k = 0; k < amountColumns.size(); k++) {
					String colName = (String) amountColumns.get(k);
					BigDecimal amount = FMConstants.ZERO;
					boolean hasData = false;
					for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) rowList.get(rowIndex);
						if (rowAdd.getCell(colName).getStyleAttributes().getFontColor().equals(Color.RED)) {
						}
						Object value = rowAdd.getCell(colName).getValue();
						if (value != null) {
							if (value instanceof BigDecimal) {
								amount = amount.add((BigDecimal) value);
							} else if (value instanceof Integer) {
								amount = amount.add(new BigDecimal(((Integer) value).toString()));
							}
							hasData = true;
						}
					}
					if (hasData) {
						row.getCell(colName).setValue(amount);
					}
				}
			} else {

			}
		}
	}

	private void fetchData() throws BOSException, EASBizException {
		
		ParamValue paramValue = new ParamValue();
		Map uiContext = this.getUIContext();
		CostAccountInfo costAccountInfo = (CostAccountInfo)uiContext.get("accountInfo");
		paramValue.put("acctLongNumber", costAccountInfo.getLongNumber());
		paramValue.put("leafPrjIds", uiContext.get("leafPrjIds"));
		paramValue.put("displayContract", Boolean.valueOf(displayContract));
		paramValue.put("displayNoText", Boolean.valueOf(displayNoText));
		retValue = ProjectCostRptFacadeFactory.getRemoteInstance().getCollectionContractAcctCostDetails(paramValue);
		/***
		 * 完工未付款
		 * 完工未付款 = 合同已实现产值 - 合同已付款
		 */
		/***
		 * 付款率 = 已付款/已实现产值
		 */
	}

	private void initTable() {
		KDTable table = this.tblMain;
		table.checkParsed();
		table.setRefresh(false);
		table.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		table.getViewManager().setFreezeView(-1, 2);
		table.setColumnMoveable(true);
		FDCHelper.formatTableNumber(table, "amount");
		FDCHelper.formatTableNumber(table, "totalSettlePrice");
		FDCHelper.formatTableNumber(table, "contractSplitAmt");
		FDCHelper.formatTableNumber(table, "conChangeSplitAmt");
		FDCHelper.formatTableNumber(table, "settlementSplitAmt");
		FDCHelper.formatTableNumber(table, "paymentSplitAmt");
		FDCHelper.formatTableNumber(table, "paidAmt");
		FDCHelper.formatTableNumber(table, "lastPrice");
		FDCHelper.formatTableNumber(table, "allNotPaid");
		FDCHelper.formatTableNumber(table, "payPercent");
		
	}
    protected void initUserConfig()
    {
    	super.initUserConfig();
    }

	private void initControl() {
		this.actionImportData.setVisible(false);
		this.menuBiz.setVisible(false);
		this.menuBiz.setEnabled(false);
		this.menuItemCancel.setVisible(false);
		this.menuItemCancelCancel.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionEdit.setEnabled(false);
		this.actionRemove.setVisible(false);
		this.actionRemove.setEnabled(false);
		this.actionView.setVisible(false);
		this.actionView.setEnabled(false);
		this.actionQuery.setVisible(false);
		this.actionQuery.setEnabled(false);
		this.actionLocate.setVisible(false);
		this.actionQuery.setEnabled(false);
		this.menuBiz.setVisible(false);
		this.menuEdit.setVisible(false);
		this.menuView.setVisible(false);
//		this.menuItemSubmit.setVisible(false);
		this.MenuItemAttachment.setVisible(false);
//		this.menuItemRecense.setVisible(false);
		actionDisplayAll.putValue(Action.SMALL_ICON,EASResource.getIcon("imgTbtn_assistantlistaccount"));
		actionDisplayContract.putValue(Action.SMALL_ICON,EASResource.getIcon("imgTbtn_assistantlistaccount"));
		actionDisplayConNoText.putValue(Action.SMALL_ICON,EASResource.getIcon("imgTbtn_assistantlistaccount"));
		this.menuTool.add(actionDisplayAll);
		this.menuTool.add(actionDisplayContract);
		this.menuTool.add(actionDisplayConNoText);
		this.toolBar.add(actionDisplayAll);
		this.toolBar.add(actionDisplayContract);
		this.toolBar.add(actionDisplayConNoText);
		actionDisplayAll.setEnabled(true);
		actionDisplayContract.setEnabled(true);
		actionDisplayConNoText.setEnabled(true);
	}

}