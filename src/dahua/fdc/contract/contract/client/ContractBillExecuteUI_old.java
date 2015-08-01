/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillExecuteDataHander;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractPropertyEnum;
import com.kingdee.eas.fdc.contract.IContractBill;
import com.kingdee.eas.fdc.finance.ContractPayPlanCollection;
import com.kingdee.eas.fdc.finance.ContractPayPlanFactory;
import com.kingdee.eas.fdc.finance.IContractPayPlan;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.IPaymentBill;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;

/**
 * 描述:合同执行情况一览表
 * @author jackwang  date:2007-5-22 <p>
 * @version EAS5.3
 */
public class ContractBillExecuteUI_old extends AbstractContractBillExecuteUI {
	private static final Logger logger = CoreUIObject.getLogger(ContractBillExecuteUI_old.class);
	private Map dataMap=null;
	/**
	 * output class constructor
	 */
	public ContractBillExecuteUI_old() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		initControl();
		super.onLoad();
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
	}

	/**
	 * 设置表格属性
	 */
	protected void initTable() {
		KDTable table = this.tblMain;
		table.checkParsed();
		table.setRefresh(false);
		table.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
		table.getColumn("contractBill.amt").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		
		table.getColumn("contractBill.oriAmt").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(2));			
		table.getColumn("contractBill.oriAmt").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		
		table.getColumn("payPlanAmt").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		table.getColumn("payRealAmt").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		table.getColumn("contractBillLastAmt").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		
		table.getColumn("contractBill.amt").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
						
		table.getColumn("payPlanAmt").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		table.getColumn("payRealAmt").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		table.getColumn("contractBillLastAmt").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		table.setColumnMoveable(true);
//		FDCTableHelper.setColumnMoveable(table, true);
		table.getColumn("payPlanDate").getStyleAttributes().setHided(true);
		table.getColumn("payPlanAmt").getStyleAttributes().setHided(true);
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
		this.menuItemSubmit.setVisible(false);
		this.MenuItemAttachment.setVisible(false);
		this.menuItemRecense.setVisible(false);
	}

	protected void fillTable() throws Exception {
		tblMain.removeRows(false);
		tblMain.setUserObject(null);
		cbHs.clear();
		String selectObjId = getSelectObjId();
		CurProjectInfo project = getSelectProject();
		if (project != null && project.isIsLeaf()) {
			String projectName = project.getDisplayName();
			projectName = projectName.replaceAll("_", "\\\\");
			IContractBill icb = ContractBillFactory.getRemoteInstance();
			EntityViewInfo evi = new EntityViewInfo();
			evi.getSelector().add("*");
			evi.getSelector().add("currency.name");
			evi.getSelector().add("currency.precision");
			
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("curProject.id", selectObjId));
			filter.getFilterItems().add(new FilterItemInfo("isAmtWithoutCost", String.valueOf(0)));
			evi.setFilter(filter);
			SorterItemInfo sorter = new SorterItemInfo("number");
			sorter.setSortType(SortType.DESCEND);
			evi.getSorter().add(sorter);
			ContractBillCollection cbc = icb.getContractBillCollection(evi);
			
			if (cbc != null && cbc.size() != 0) {
				String[] contractIdList = new String[cbc.size()];
				for (int i = 0; i < cbc.size(); i++) {
					contractIdList[i] = cbc.get(i).getId().toString();
				}

				Map awCostMap = ContractClientUtils.getAmtWithoutCost_Batch(contractIdList);
				Map lastAmtMap = ContractClientUtils.getLastAmt_Batch(contractIdList);
				
				IRow row;				
				Object tmpValue;
				String nodeId;
				for (int i = 0; i < cbc.size(); i++) {
					nodeId = cbc.get(i).getId().toString();
					
					int pre = cbc.get(i).getCurrency().getPrecision();		
					String curFormat = FDCClientHelper.getNumberFormat(pre,true);
					
					row = tblMain.addRow();
					
					row.getCell("contractBill.oriAmt").getStyleAttributes().setNumberFormat(curFormat);
					
					row.getCell("project.name").setValue(projectName);
					row.getCell("contractBill.number").setValue(cbc.get(i).getNumber());
					row.getCell("contractBill.name").setValue(cbc.get(i).getName());
					row.getCell("currency.name").setValue(cbc.get(i).getCurrency().getName());
					
					if((ContractPropertyEnum.SUPPLY.equals(cbc.get(i).getContractPropert())
							||ContractPropertyEnum.THREE_PARTY.equals(cbc.get(i).getContractPropert()))
							&&cbc.get(i).isIsAmtWithoutCost()){
						
						tmpValue = awCostMap.get(nodeId);
						if(tmpValue == null){
							row.getCell("contractBill.amt").setValue(FDCConstants.B0);
							row.getCell("contractBill.oriAmt").setValue(FDCConstants.B0);
						}else{
							row.getCell("contractBill.amt").setValue(FDCHelper.toBigDecimal(tmpValue));
							row.getCell("contractBill.oriAmt").setValue(FDCHelper.toBigDecimal(tmpValue));
						}
					}else{
						row.getCell("contractBill.oriAmt").setValue(cbc.get(i).getOriginalAmount());
						row.getCell("contractBill.amt").setValue(cbc.get(i).getAmount());
					}
					row.getCell("contractBill.hasSettled").setValue(new Boolean(cbc.get(i).isHasSettled()));

					tmpValue = lastAmtMap.get(nodeId);
					if(tmpValue == null){
						row.getCell("contractBillLastAmt").setValue(FDCConstants.B0);
					}else{
						row.getCell("contractBillLastAmt").setValue(FDCHelper.toBigDecimal(tmpValue));
					}
										
					this.cbHs.add(cbc.get(i).getId().toString());
					row.setUserObject(cbc.get(i));

				}
			}
			planMap.clear();
			getPlanDatas();
			realMap.clear();
			getRealDatas();
			fillOtherDatas();
			addGatherRow();
			addContractSubSumRow();
		}
	}
	/*
	 * 添加统计行
	 */
	private void addGatherRow() {
		BigDecimal oriAmt = FDCHelper.ZERO;
		BigDecimal amt = FDCHelper.ZERO;
		BigDecimal contractBillLastAmt = FDCHelper.ZERO;
		BigDecimal planAmt = FDCHelper.ZERO;
		BigDecimal realAmt = FDCHelper.ZERO;
//		tblMain.getMergeManager().mergeBlock(0, 0, 0, 1);
//		List amountColumns = new ArrayList();
//		for (int j = 2; j < this.tblMain.getColumnCount(); j++) {
//			amountColumns.add(tblMain.getColumn(j).getKey());
//		}
		for(int i=0;i<this.tblMain.getRowCount();i++){
			if(tblMain.getRow(i).getUserObject()!=null){//合同行
				if(this.tblMain.getRow(i).getCell("contractBill.oriAmt").getValue()!=null){
					oriAmt = oriAmt.add((BigDecimal) this.tblMain.getRow(i).getCell("contractBill.oriAmt").getValue());
				}
				if(this.tblMain.getRow(i).getCell("contractBill.amt").getValue()!=null){
					amt = amt.add((BigDecimal) this.tblMain.getRow(i).getCell("contractBill.amt").getValue());
				}
				if(this.tblMain.getRow(i).getCell("contractBillLastAmt").getValue()!=null){
					contractBillLastAmt = contractBillLastAmt.add((BigDecimal) this.tblMain.getRow(i).getCell("contractBillLastAmt").getValue());
				}
			}else{//添加行
				if(this.tblMain.getRow(i).getCell("payPlanAmt").getValue()!=null){
					planAmt = planAmt.add((BigDecimal) this.tblMain.getRow(i).getCell("payPlanAmt").getValue());
				}
				if(this.tblMain.getRow(i).getCell("payRealAmt").getValue()!=null){
					realAmt = realAmt.add((BigDecimal) this.tblMain.getRow(i).getCell("payRealAmt").getValue());
				}
			}			
		}
//		if (rootNodeList.size() != 0) {
//			BigDecimal amt,temp;
//			for (int i = 0; i < amountColumns.size(); i++) {
//				amt = new BigDecimal(0);
//				for (int j = 0; j < rootNodeList.size(); j++) {
//					temp = new BigDecimal(0);
//					int t = ((Integer) rootNodeList.get(j)).intValue();
//					if (tblMain.getRow(t).getCell(i + 2).getValue() != null) {
//						temp = (BigDecimal) (tblMain.getRow(t).getCell(i + 2).getValue());
//					}
//					amt = amt.add(temp);
//				}
//				row.getCell(i + 2).setValue(amt);
//			}
//		}
		IRow row = this.tblMain.addRow();
		row.getCell("project.name").setValue("合计");
//		row.getCell("contractBill.oriAmt").setValue(oriAmt);
		row.getCell("contractBill.amt").setValue(amt);
		row.getCell("contractBillLastAmt").setValue(contractBillLastAmt);
		row.getCell("payPlanAmt").setValue(planAmt);
		row.getCell("payRealAmt").setValue(realAmt);
	}
	
	/*
	 * 添加按合同小计行
	 */
	private void addContractSubSumRow() {
		BigDecimal planAmt = FDCHelper.ZERO;
		BigDecimal realAmt = FDCHelper.ZERO;
		
		for(int i=this.tblMain.getRowCount()-2;i>=0;i--){
			if(tblMain.getRow(i).getUserObject()!=null){//合同行
				if(this.tblMain.getRow(i).getCell("contractBill.amt").getValue()!=null){
					if(planAmt.intValue()!=0){
						this.tblMain.getRow(i).getCell("payPlanAmt").setValue(planAmt);
					}
					if(realAmt.intValue()!=0){
						this.tblMain.getRow(i).getCell("payRealAmt").setValue(realAmt);
					}
			
					planAmt = BigDecimal.valueOf(0);
					realAmt = BigDecimal.valueOf(0);
				}
			}else{//添加明细计划行
				if(this.tblMain.getRow(i).getCell("payPlanAmt").getValue()!=null){
					planAmt = planAmt.add((BigDecimal) this.tblMain.getRow(i).getCell("payPlanAmt").getValue());
				}
				if(this.tblMain.getRow(i).getCell("payRealAmt").getValue()!=null){
					realAmt = realAmt.add((BigDecimal) this.tblMain.getRow(i).getCell("payRealAmt").getValue());
				}
			}			
		}
	}
	HashSet cbHs = new HashSet();

	Map planMap = new HashMap();

	Map realMap = new HashMap();

	private void getPlanDatas() throws Exception {
		planMap.clear();
		
		if (cbHs.size() != 0) {
			// get
			IContractPayPlan icpp = ContractPayPlanFactory.getRemoteInstance();
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractId", cbHs, CompareType.INCLUDE));
			evi.setFilter(filter);
			evi.getSelector().add(new SelectorItemInfo("id"));
			evi.getSelector().add(new SelectorItemInfo("payDate"));
			evi.getSelector().add(new SelectorItemInfo("payAmount"));
			evi.getSelector().add(new SelectorItemInfo("contractId"));
			SorterItemInfo sorter = new SorterItemInfo("payDate");
			sorter.setSortType(SortType.ASCEND);
			evi.getSorter().add(sorter);
			ContractPayPlanCollection cppc = icpp.getContractPayPlanCollection(evi);
			if (cppc != null && cppc.size() != 0) {
				for (int i = 0; i < cppc.size(); i++) {
					Map map = new HashMap();
					map.put("payDate", cppc.get(i).getPayDate());
					map.put("payAmount", cppc.get(i).getPayAmount());
					if (!planMap.containsKey(cppc.get(i).getContractId())) {
						ArrayList al = new ArrayList();
						al.add(map);
						planMap.put(cppc.get(i).getContractId(), al);
					} else {
						ArrayList al = (ArrayList) planMap.get(cppc.get(i).getContractId());
						al.add(map);
						planMap.put(cppc.get(i).getContractId(), al);
					}
				}
			}
			// fill
			// for(int i=0;i<this.tblMain.getRowCount();i++){
			// ContractBillInfo cbi = (ContractBillInfo)
			// tblMain.getRow(i).getUserObject();
			// if(cbi!=null&&planMap.containsKey(cbi.getId().toString())){
			// ArrayList planAl = (ArrayList)
			// planMap.get(cbi.getId().toString());
			// int tmp = i;
			// for(int j=0;j<planAl.size();j++){
			// Map map = (Map) planAl.get(j);
			// IRow row = this.tblMain.addRow(tmp+1+j);
			// row.getCell("payPlanDate").setValue(map.get("payDate"));
			// row.getCell("payPlanAmt").setValue(map.get("payAmount"));
			// i++;
			// }
			// }
			// }
		}
		planMap.clear();//因为计划两列已按万科要求隐藏，所以计划数据无用，故清掉
	}

	private void getRealDatas() throws Exception {
		realMap.clear();
		if (cbHs.size() != 0) {
			// get
			IPaymentBill ipb = PaymentBillFactory.getRemoteInstance();
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractBillId", cbHs, CompareType.INCLUDE));
			filter.getFilterItems().add(new FilterItemInfo("billStatus", BillStatusEnum.PAYED));
			evi.setFilter(filter);
			evi.getSelector().add(new SelectorItemInfo("id"));
			evi.getSelector().add(new SelectorItemInfo("payDate"));
			evi.getSelector().add(new SelectorItemInfo("actPayAmt"));
			evi.getSelector().add(new SelectorItemInfo("contractBillId"));
			SorterItemInfo sorter = new SorterItemInfo("payDate");
			sorter.setSortType(SortType.ASCEND);
			evi.getSorter().add(sorter);
			PaymentBillCollection pbc = ipb.getPaymentBillCollection(evi);
			if (pbc != null && pbc.size() != 0) {
				for (int i = 0; i < pbc.size(); i++) {
					Map map = new HashMap();
					map.put("payDate", pbc.get(i).getPayDate());
					map.put("payAmount", pbc.get(i).getActPayAmt());
					if (!realMap.containsKey(pbc.get(i).getContractBillId())) {
						ArrayList al = new ArrayList();
						al.add(map);
						realMap.put(pbc.get(i).getContractBillId(), al);
					} else {
						ArrayList al = (ArrayList) realMap.get(pbc.get(i).getContractBillId());
						al.add(map);
						realMap.put(pbc.get(i).getContractBillId(), al);
					}
				}
			}
			// fill
			// for(int i=0;i<this.tblMain.getRowCount();i++){
			// ContractBillInfo cbi = (ContractBillInfo)
			// tblMain.getRow(i).getUserObject();
			// if(cbi!=null&&realMap.containsKey(cbi.getId().toString())){
			// ArrayList realAl = (ArrayList)
			// realMap.get(cbi.getId().toString());
			// int tmp = i;
			// for(int j=0;j<realAl.size();j++){
			// Map map = (Map) realAl.get(j);
			// IRow row = this.tblMain.addRow(tmp+1+j);
			// row.getCell("payRealDate").setValue(map.get("payDate"));
			// row.getCell("payRealAmt").setValue(map.get("payAmount"));
			// i++;
			// }
			// }
			// }

		}
	}

	private void fillOtherDatas() {
		// fill
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			if (tblMain.getRow(i).getUserObject() != null) {
				ContractBillInfo cbi = (ContractBillInfo) tblMain.getRow(i).getUserObject();
				ArrayList planAl = new ArrayList();
				ArrayList realAl = new ArrayList();
				if (planMap.containsKey(cbi.getId().toString())) {
					planAl = (ArrayList) planMap.get(cbi.getId().toString());
				}
				if (realMap.containsKey(cbi.getId().toString())) {
					realAl = (ArrayList) realMap.get(cbi.getId().toString());
				}
				if (planAl != null && planAl.size() != 0) {
					if (realAl != null && realAl.size() != 0) {
						if (planAl.size() > realAl.size()) {
							int tmp = i;
							for (int j = 0; j < planAl.size(); j++) {
								Map map = (Map) planAl.get(j);
								IRow row = this.tblMain.addRow(tmp + 1 + j);
								row.getCell("payPlanDate").setValue(map.get("payDate"));
								row.getCell("payPlanAmt").setValue(map.get("payAmount"));
								i++;
							}
							for (int j = 0; j < realAl.size(); j++) {
								Map map = (Map) realAl.get(j);
								IRow row = this.tblMain.getRow(tmp + 1 + j);
								row.getCell("payRealDate").setValue(map.get("payDate"));
								row.getCell("payRealAmt").setValue(map.get("payAmount"));
							}
						} else {
							int tmp = i;
							for (int j = 0; j < realAl.size(); j++) {
								Map map = (Map) realAl.get(j);
								IRow row = this.tblMain.addRow(tmp + 1 + j);
								row.getCell("payRealDate").setValue(map.get("payDate"));
								row.getCell("payRealAmt").setValue(map.get("payAmount"));
								i++;
							}
							for (int j = 0; j < planAl.size(); j++) {
								Map map = (Map) planAl.get(j);
								IRow row = this.tblMain.getRow(tmp + 1 + j);
								row.getCell("payPlanDate").setValue(map.get("payDate"));
								row.getCell("payPlanAmt").setValue(map.get("payAmount"));
							}
						}
					} else {
						int tmp = i;
						for (int j = 0; j < planAl.size(); j++) {
							Map map = (Map) planAl.get(j);
							IRow row = this.tblMain.addRow(tmp + 1 + j);
							row.getCell("payPlanDate").setValue(map.get("payDate"));
							row.getCell("payPlanAmt").setValue(map.get("payAmount"));
							i++;
						}
					}
				} else {
					if (realAl != null && realAl.size() != 0) {
						int tmp = i;
						for (int j = 0; j < realAl.size(); j++) {
							Map map = (Map) realAl.get(j);
							IRow row = this.tblMain.addRow(tmp + 1 + j);
							row.getCell("payRealDate").setValue(map.get("payDate"));
							row.getCell("payRealAmt").setValue(map.get("payAmount"));
							i++;
						}
					} else {
						// 无数据
					}
				}
//				//填充计划内容
//				if (planAl != null && planAl.size() != 0) {
//					int tmp = i;
//					for (int j = 0; j < planAl.size(); j++) {
//						Map map = (Map) planAl.get(j);
//						IRow row = this.tblMain.addRow(tmp + 1 + j);
//						row.getCell("payPlanDate").setValue(map.get("payDate"));
//						row.getCell("payPlanAmt").setValue(map.get("payAmount"));
//						i++;
//					}
//				}
//				//填充实际付款内容
//				if (realAl != null && realAl.size() != 0) {
//					int tmp = i;
//					for (int j = 0; j < realAl.size(); j++) {
//						Map map = (Map) realAl.get(j);
//						IRow row = this.tblMain.addRow(tmp + 1 + j);
//						row.getCell("payRealDate").setValue(map.get("payDate"));
//						row.getCell("payRealAmt").setValue(map.get("payAmount"));
//						i++;
//					}
//				}
			
			}
		}
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		super.tblMain_tableClicked(e);
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception {
		// super.tblMain_tableSelectChanged(e);
	}
	/**
	 * 获取query中的主键列名称，返回供编辑/删除时获取主键用，默认值为"id"，继承类可以重载
	 * 
	 */
	protected String getKeyFieldName() {
		return "contractBill.number";
	}

	protected FilterInfo getDefaultFilterForQuery() {
		return new FilterInfo();
	}

	protected void tblMain_doRequestRowSetForHasQueryPK(RequestRowSetEvent e) {

	}

	protected String getEditUIName() {
		return null;
	}
	protected boolean isCanOrderTable() {
		return false;
	}

	protected void initCtrlListener() {
		super.initCtrlListener();
		tblMain.addKDTDataFillListener(new KDTDataFillListener() {
			public void afterDataFill(KDTDataRequestEvent e) {
				tblMain_tableAfterDataFill(e);
			}
		});
	}

	protected void tblMain_tableAfterDataFill(KDTDataRequestEvent e) {
		int start = e.getFirstRow();
		int end = e.getLastRow();		
		//TODO 虚模式取数以后再做吧
	}
}