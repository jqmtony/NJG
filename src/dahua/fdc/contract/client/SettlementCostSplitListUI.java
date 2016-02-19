/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.BooleanEnum;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.contract.ClearSplitFacadeFactory;
import com.kingdee.eas.fdc.contract.ConNoCostSplitCollection;
import com.kingdee.eas.fdc.contract.ConNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ConNoCostSplitInfo;
import com.kingdee.eas.fdc.contract.ConSplitExecStateEnum;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitInfo;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.ISettlementCostSplit;
import com.kingdee.eas.fdc.contract.SettNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitInfo;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.fdc.finance.SettledMonthlyHelper;
import com.kingdee.eas.fdc.finance.client.PaymentSplitListUI;
import com.kingdee.eas.fm.common.client.FMClientHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 结算拆分序时薄，包括成本类和非成本类的
 * @Modified By Owen_wen 2010-11-30
 */
public class SettlementCostSplitListUI extends AbstractSettlementCostSplitListUI {
	private static final Logger logger = CoreUIObject.getLogger(SettlementCostSplitListUI.class);

	/**
	 * output class constructor
	 */
	public SettlementCostSplitListUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		if (isCostSplit()) {
			initParam = SettlementCostSplitFactory
					.getRemoteInstance().fetchInitParam();
		} else {
			initParam =  SettNoCostSplitFactory.getRemoteInstance().fetchInitParam();
		}
		super.onLoad();
		setActionVouchers();
		if(!isMoreSetter()){
			tblMain.getColumn("curSettlePrice").getStyleAttributes().setHided(true);
			tblMain.getColumn("totalSettlePrice").getStyleAttributes().setHided(true);
		}else{
			tblMain.getColumn("settlePrice").getStyleAttributes().setHided(true);
		}
	}
	
	public void setActionVouchers() throws EASBizException, BOSException {
		if (!isFinacial) {
			if (isIncorporation) {
				FDCClientHelper.setActionEnable(new ItemAction[] { actionVoucher, actionDelVoucher }, false);
				FDCClientHelper.setActionEnable(new ItemAction[] { actionClearSplit, actionViewInvalid }, true);
			} else
				FDCClientHelper.setActionEnable(new ItemAction[] { actionVoucher, actionDelVoucher,
						actionClearSplit }, false);
		}
		boolean isSettle = FDCUtils.IsSettlementCostSplitVoucher(null, OrgConstants.SYS_CU_ID);
		if (isSettle) {
			if (SysContext.getSysContext().getCurrentOrgUnit().isIsCompanyOrgUnit()) {
				FDCClientHelper.setActionEnable(new ItemAction[] { actionVoucher, actionDelVoucher,
						actionTraceDown }, true);
			}
		} else {
			FDCClientHelper.setActionEnable(
					new ItemAction[] { actionVoucher, actionDelVoucher, actionTraceDown }, false);
		}
	}
	
	protected void refresh(ActionEvent e) throws Exception {
		super.refresh(e);
		setActionVouchers();
	}

	protected void updateButtonStatus() {
		super.updateButtonStatus();

		// 如果是虚体成本中心，则不能增、删、改
//		if (!SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit()) {
//			actionAddNew.setEnabled(false);
//			actionEdit.setEnabled(false);
//			actionRemove.setEnabled(false);
//			actionCostSplit.setEnabled(false);
//			actionAddNew.setVisible(false);
//			actionEdit.setVisible(false);
//			actionRemove.setVisible(false);
//			actionCostSplit.setVisible(false);
//
//			menuEdit.setVisible(false);
//			// actionView.setVisible(false);
//			// actionView.setEnabled(false);
//		}
		actionRemove.setVisible(true);
		actionRemove.setEnabled(true);
		menuItemSwitchView.setEnabled(false);
		menuItemSwitchView.setVisible(false);
		menuEdit.setVisible(true);
		if(isAdjustVourcherModel()){
			menuItemClearSplit.setText("作废结算拆分");
			btnClearSplit.setText("作废结算拆分");
		}
	}

	public void actionCostSplit_actionPerformed(ActionEvent e) throws Exception {
		checkContract();
		checkImp();
		// 变更结算
		if (isCostSplit()) {
			autoChangeSettle();
		}
		super.actionCostSplit_actionPerformed(e);
	}

	private boolean isCostSplit() {
		boolean isCostSplit = false;
		// 非成本科目拆分
		int[] selectRows = KDTableUtil.getSelectedRows(getMainTable());
		if (selectRows.length < 1) {
			return false;
		}
		Object costSplit = getMainTable().getCell(selectRows[0], "isCostSplit").getValue();
		if (costSplit instanceof Boolean) {
			isCostSplit = ((Boolean) costSplit).booleanValue();
		}
		return isCostSplit;
	}

	/**
	 * output getEditUIName method
	 */
	protected String getEditUIName() {
		if (isCostSplit()) {
			return com.kingdee.eas.fdc.contract.client.SettlementCostSplitEditUI.class
					.getName();
		} else {
			return SettlementNoCostEditUI.class.getName();
		}
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception {
		if (isCostSplit()) {
			return com.kingdee.eas.fdc.contract.SettlementCostSplitFactory.getRemoteInstance();
		} else {
			return SettNoCostSplitFactory.getRemoteInstance();
		}
	}

	//结算拆分列表界面暂时去掉状态过滤，出私包给陈亮，稍后还要改回来
	protected void filterByBillState(EntityViewInfo ev) {
		super.filterByBillState(ev);
		
//		FilterInfo newFilter = new FilterInfo();
//		Set set = new HashSet(3);
//		set.add(FDCBillStateEnum.INVALID_VALUE);
//		newFilter.getFilterItems().add(new FilterItemInfo("state", set, CompareType.NOTINCLUDE));
//		newFilter.appendFilterItem("contractBill.isAmtWithoutCost",Boolean.FALSE);
//		try {
//			if (ev.getFilter() == null) {
//				ev.setFilter(newFilter);
//			} else {
//				ev.getFilter().mergeFilter(newFilter, "and");
//			}
//		} catch (Exception e) {
//			handUIExceptionAndAbort(e);
//		}
	}
	
	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		com.kingdee.eas.fdc.contract.SettlementCostSplitInfo objectValue = new com.kingdee.eas.fdc.contract.SettlementCostSplitInfo();
		return objectValue;
	}

	protected ICoreBase getRemoteInterface() throws BOSException {
		if (isCostSplit()) {
			return com.kingdee.eas.fdc.contract.SettlementCostSplitFactory.getRemoteInstance();
		} else {
			return SettNoCostSplitFactory.getRemoteInstance();
		}
	}

	public String[] getMergeColumnKeys() {
		// TODO Auto-generated method stub
		return new String[0];
	}

	/**
	 * 返回工程项目在ListUI Query中对应的主实体的工程项目关联属性的路径，子类必须实现
	 * 
	 * @author sxhong Date 2006-11-13
	 * @return
	 */
	protected String getCurProjectPath() {
		return "contractBill.curProject";
	}

	protected boolean checkBeforeSplit() throws Exception {
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		if (selectRows.length > 1) {
			MsgBox.showWarning(this, FDCSplitClientHelper.getRes("multiRowSelected"));
			SysUtil.abort();
		}
		boolean isAddNew = super.checkBeforeSplit();
		if (isAddNew) {
			// 到数据库内进行检查
			String billId = getMainTable().getCell(selectRows[0], "id").getValue().toString();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("settlementBill.id", billId));
			filter.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
			if (getRemoteInterface().exists(filter)) {
				EntityViewInfo view = new EntityViewInfo();
				view.setFilter(filter);
				String costBillID = getRemoteInterface().getCollection(view).get(0).getId().toString();
				getMainTable().getCell(selectRows[0], getKeyFieldName()).setValue(costBillID);
				isAddNew = false;
			}
		}
		if (!isAddNew && isCostSplit()
				&& SettledMonthlyHelper.existObject(null, costSplit, getSelectedKeyValue())) {
			MsgBox.showWarning(this, "成本月结已经引用本数据，不能修改，如需修改，请先作废本合同拆分！");
			SysUtil.abort();
		}
		return isAddNew;
	}

	protected void freezeTableColumn() {
		int index = getMainTable().getColumnIndex("number");
		getMainTable().getViewManager().setFreezeView(-1, index + 1);
	}

	private void checkContract() throws Exception {
		checkSelected();
		int selectRows[] = KDTableUtil.getSelectedRows(getMainTable());
		if (selectRows.length > 1) {
			MsgBox.showWarning(this, FDCSplitClientHelper.getRes("multiRowSelected"));
			SysUtil.abort();
		}

		Object obj = getMainTable().getCell(selectRows[0], "contractId").getValue();
		
		if (obj == null) return;
		String contractId = obj.toString();
		
		if (isCostSplit()) { //成本拆分
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractBill", contractId));
			filter.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
			view.setFilter(filter);
			ContractCostSplitCollection contcoll = ContractCostSplitFactory.getRemoteInstance()
					.getContractCostSplitCollection(view);
			Iterator iter = contcoll.iterator();
			if (iter.hasNext()) {
				ContractCostSplitInfo cont = (ContractCostSplitInfo) iter.next();
				// Added By Owen_wen 2010-12-03  --begin
				boolean conSplitIsNotAudited = !FDCBillStateEnum.AUDITTED.equals(cont.getState());
				boolean changeSplitIsNotAudited = FDCSplitClientHelper.checkConChangeIsAudited(contractId, true);
				if (conSplitIsNotAudited && changeSplitIsNotAudited) //合同拆分和变更都未审批
					FDCSplitClientHelper.getParamValueAndShowMsg("conAndChangeSpltNotAudited", this);
				if (conSplitIsNotAudited)// 合同拆分未审批
					FDCSplitClientHelper.getParamValueAndShowMsg("conSplitNotAudit", this);
				if (changeSplitIsNotAudited)// 变更拆分未审批
					FDCSplitClientHelper.getParamValueAndShowMsg("changeSpliteNotAudit", this);
				// --end
				
				String splitState = cont.getSplitState().toString();
				if (splitState.equals(CostSplitStateEnum.ALLSPLIT.toString())) {
					// 变更必须全部进行了拆分 by sxhong
					checkConChange(contractId, true);
					return;
				}
			} else {
				// 对应的合同还未进行拆分，不能进行此操作！
				MsgBox.showWarning(this, FDCSplitClientHelper.getRes("conNotSplit"));
				SysUtil.abort();
			}
			// 0金额合同只要求有变更拆分
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("amount");
			final ContractBillInfo contractBillInfo = ContractBillFactory.getRemoteInstance()
					.getContractBillInfo(new ObjectUuidPK(contractId), selector);
			if (contractBillInfo.getAmount() == null
					|| contractBillInfo.getAmount().compareTo(FDCHelper.ZERO) == 0) {
				// 变更必须全部进行了拆分 by sxhong
				checkConChange(contractId, true);
			} else {
				MsgBox.showWarning(this, FDCSplitClientHelper.getRes("conNotSplit"));
				SysUtil.abort();
			}

		} else { // 非成本拆分
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractBill", contractId));
			filter.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
			view.setFilter(filter);
			ConNoCostSplitCollection contcoll = ConNoCostSplitFactory.getRemoteInstance()
					.getConNoCostSplitCollection(view);
			Iterator iter = contcoll.iterator();
			if (iter.hasNext()) {
				ConNoCostSplitInfo cont = (ConNoCostSplitInfo) iter.next();
				// Added By Owen_wen 2010-12-03  --begin
				boolean conSplitIsNotAudited = !FDCBillStateEnum.AUDITTED.equals(cont.getState());
				boolean changeSplitIsNotAudited = FDCSplitClientHelper.checkConChangeIsAudited(contractId, false);
				if (conSplitIsNotAudited && changeSplitIsNotAudited) //合同拆分和变更都未审批
					FDCSplitClientHelper.getParamValueAndShowMsg("conAndChangeSpltNotAudited", this);
				if (conSplitIsNotAudited)// 合同拆分未审批
					FDCSplitClientHelper.getParamValueAndShowMsg("conSplitNotAudit", this);
				if (changeSplitIsNotAudited)// 变更拆分未审批
					FDCSplitClientHelper.getParamValueAndShowMsg("changeSpliteNotAudit", this);
				// --end	
				String splitState = cont.getSplitState().toString();
				if (splitState.equals(CostSplitStateEnum.ALLSPLIT.toString())) {
					checkConChange(contractId, false);
					return;
				}
			} else {
				MsgBox.showWarning(this, FDCSplitClientHelper.getRes("conNotSplit"));
				SysUtil.abort();
			}

			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("amount");
			final ContractBillInfo contractBillInfo = ContractBillFactory.getRemoteInstance()
					.getContractBillInfo(new ObjectUuidPK(contractId), selector);
			if (contractBillInfo.getAmount() == null
					|| contractBillInfo.getAmount().compareTo(FDCHelper.ZERO) == 0) {
				checkConChange(contractId, false);
			} else {
				MsgBox.showWarning(this, FDCSplitClientHelper.getRes("conNotSplit"));
				SysUtil.abort();
			}
		}
	}
	
	/**
	 * 检查: 变更是否拆分;
	 * 
	 * @param contractBillId
	 */
	private void checkConChange(String contractBillId, boolean isCostSplit) throws Exception {
		
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select fid from T_CON_ContractChangeBill where fcontractbillid=?");
		builder.addParam(contractBillId);
		IRowSet rowSet = builder.executeQuery();
		int changeCount = rowSet.size();
		if (changeCount == 0) {
			return;
		}
		String ids[] = new String[changeCount];
		int i = 0;
		while (rowSet.next()) {
			ids[i++] = rowSet.getString("fid");
		}
		
		builder.clear();
		String tableName;
		if (isCostSplit) {
			tableName = "T_CON_ConChangeSplit";
		} else {
			tableName = "T_CON_ConChangeNoCostSplit";
		}
		builder.appendSql("select fstate from " + tableName + " where FIsInvalid=0 and ");
		builder.appendParam("FContractChangeID", ids);
		rowSet = builder.executeQuery();
		int splitCount = rowSet.size();
		if (splitCount < changeCount) {
			MsgBox.showWarning(this, "存在未拆分的变更签证确认，不允许进行结算拆分");
			SysUtil.abort();
		}
	}

	public void actionViewInvalid_actionPerformed(ActionEvent e)
			throws Exception {
		IUIWindow testUI = null;
		UIContext uiContext = new UIContext(this);
		// uiContext.put(UIContext.ID, info.getId());
		try {
			testUI = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(
					"com.kingdee.eas.fdc.contract.client.InvalidSettleSplitListUI", uiContext, null);
			testUI.show();
		} catch (BOSException exe) {
			handUIExceptionAndAbort(exe);
		}
	}

	private void autoChangeSettle() throws Exception {
		String costBillID = getSelectedKeyValue();
		if (costBillID != null
				&& !costBillID.equals(splitBillNullID.toString())) {
			// 已经拆分的直接返回
			return;
		}
		IRow row = KDTableUtil.getSelectedRow(getMainTable());
		String settleId = row.getCell("id").getValue().toString();
		String conId = row.getCell("contractId").getValue().toString();
		
		/* modified by zhaoqin for R130927-0088 on 2013/12/25 start */
		if(!isImportConSplit(settleId)) {	// FDC018_IMPORTCONSPLIT
			return ;
		}
		/* modified by zhaoqin for R130927-0088 on 2013/12/25 end */
		
		boolean b = false;
		BigDecimal conPrice = FDCUtils.getConAndChange(null, conId);
		BigDecimal settlePrice = (BigDecimal) row.getCell("settlePrice").getValue();
		b = FDCHelper.subtract(settlePrice, conPrice).compareTo(FDCHelper.ZERO) == 0;
		if (b) {
			//			FDCSplitClientHelper.autoChangeSettle2(settleId);
		} else {
			FDCSplitClientHelper.autoChangeSettle4(settleId);
		}
	}
	
	/**
	 * get value of "FDC018_IMPORTCONSPLIT" for R130927-0088
	 * 
	 * @author RD_zhaoqin
	 * @date 2013/12/25
	 */
	private boolean isImportConSplit(String settleId) throws BOSException, SQLException {
		boolean isImport = false;
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select pi.fvalue_l2 as fvalue_l2 ");
		builder.appendSql("from T_CON_ContractSettlementBill cs ");
		builder.appendSql("join t_fdc_curproject cp on cp.fid = cs.fcurprojectid ");
		builder.appendSql("join t_bas_paramitem pi on pi.forgunitid = cp.fcostcenterid ");
		builder.appendSql("join t_bas_param p on p.fid = pi.fkeyid ");
		builder.appendSql("where p.fnumber = ").appendParam(FDCConstants.FDC_PARAM_IMPORTCONSPLIT);
		builder.appendSql("and cs.fid = ").appendParam(settleId);
		IRowSet rs = builder.executeQuery();
		if(rs.next()) {
			isImport = Boolean.valueOf(rs.getString("fvalue_l2")).booleanValue();
			
		}
		return isImport;
	}

	protected void checkImp() throws Exception {
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		for (int i = 0; i < selectRows.length; i++) {
			String billId = getMainTable().getCell(selectRows[i], getCostBillIdFieldName()).getValue().toString();
			if (billId == null)
				continue;
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("contractBill.id");
			ContractSettlementBillInfo settle = ContractSettlementBillFactory.getRemoteInstance()
					.getContractSettlementBillInfo(new ObjectUuidPK(billId), selector);
			FilterInfo filter = new FilterInfo();
			filter = new FilterInfo();
			filter.getFilterItems()
					.add(
							new FilterItemInfo("paymentbill.contractBillId", settle.getContractBill().getId()
									.toString()));
			filter.getFilterItems().add(new FilterItemInfo("hasEffected", Boolean.TRUE, CompareType.EQUALS));
			filter.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));

			FilterInfo filter2 = new FilterInfo();
			filter2 = new FilterInfo();
			filter2.getFilterItems().add(
					new FilterItemInfo("workLoadConfirmBill.contractBill.id", settle.getContractBill().getId()
							.toString()));
			// 标识结算后的工程量拆分 by hpw 2010.07.23
			filter2.getFilterItems().add(new FilterItemInfo("hasEffected", Boolean.TRUE, CompareType.EQUALS));
			filter2.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));

			boolean hasPaymentSplit = PaymentSplitFactory.getRemoteInstance().exists(filter);
			boolean hasWorkLoadSplit = PaymentSplitFactory.getRemoteInstance().exists(filter2);

			if (hasPaymentSplit || hasWorkLoadSplit) {
				MsgBox.showError(this, FDCSplitClientHelper.getRes("impByPay"));
				SysUtil.abort();
			}
			boolean hasPaymentNoCostSplit = PaymentNoCostSplitFactory.getRemoteInstance().exists(filter);
			if (hasPaymentNoCostSplit) {
				MsgBox.showError(this, FDCSplitClientHelper.getRes("impByPay"));
				SysUtil.abort();
			}
		}
	}

	private void checkStateSplit() {
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		if (selectRows.length > 0) {
				int rowIndex = selectRows[0];
				IRow row = tblMain.getRow(rowIndex);
				if (row == null) {
					abort();
				}

				ICell cell = row.getCell(getSplitStateFieldName());
				if ("未拆分".equals(cell.getValue())) {
					FDCMsgBox.showWarning("单据未拆分，不能执行此操作！");
					abort();
				}
		}
	}
	public void actionClearSplit_actionPerformed(ActionEvent e)
			throws Exception {
		checkSelected();
		super.actionClearSplit_actionPerformed(e);
		checkStateSplit();
		String selectedContractBillID = getSelectedCostBillID();
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("contractBill.id");
		ContractSettlementBillInfo settle = ContractSettlementBillFactory
				.getRemoteInstance().getContractSettlementBillInfo(
						new ObjectUuidPK(selectedContractBillID), selector);
		String ids = getSelectedKeyValue();
		if (isCostSplit(ids)
				&& SplitClearClientHelper.isNewVersionSettlement(settle.getId()
						.toString())) {
			if (MsgBox
					.showConfirm2(this,
							"此结算单相关的拆分（结算拆分、付款拆分）存在拆分期间在当前期间之后的版本，需要将当前期间之后的相关拆分清除，确认是否操作？") == MsgBox.OK) {
				boolean success = true;
				try {
					String result = ClearSplitFacadeFactory.getRemoteInstance()
							.clearPeriodConSplit(settle.getId().toString(),
									"settlement");
					if (result != null) {
						success = false;
						String msg = "已存在之后期间的付款拆分生成的凭证,凭证号为:" + result
								+ " 。请先删除凭证！";
						if (isAdjustVourcherModel()) {
							msg = "已存在之后期间的付款单生成的凭证,凭证号为:" + result
									+ " 。请先删除凭证！";
						}
						MsgBox.showError(this, msg);
						SysUtil.abort();
					}
				} catch (Exception ex) {
					success = false;
					handUIException(ex);
				}
				if (success) {
					refresh(e);
					FDCClientUtils.showOprtOK(this);
				}
			}
		} else {
			if (!(isCostSplit(ids) && (SettledMonthlyHelper.existObject(null,
					costSplit, getSelectedKeyValue())))) {
				SplitClearClientHelper.checkSettleClearable(this, settle
						.getContractBill().getId().toString(), true);
			} else {
				SplitClearClientHelper.checkSettleClearable(this, settle
						.getContractBill().getId().toString(), false);
			}
			String msg = "作废付款拆分/结算拆分将把结算单对应的合同相关拆分作废，若对应的合同有已经生成凭证的付款拆分，则此合同进入待处理流程,待处理合同凭证处理将自动处理已生成凭证的付款拆分，确认是否操作？";
			if (isAdjustVourcherModel()) {
				msg = "作废结算拆分将把结算相关拆分作废，若对应的合同存在被付款单引用生成凭证的付款拆分或者生成凭证的调整单，则此合同进入待处理流程，需要财务进行后续凭证处理，确认是否操作？";
				checkImp2();
			}
			if (MsgBox.showConfirm2(this, msg) == MsgBox.OK) {
				// SplitClearClientHelper.checkClearable(this,
				// selectedContractBillID);
				boolean sccuss = true;
				try {
					ClearSplitFacadeFactory.getRemoteInstance().clearSettle(
							settle.getContractBill().getId().toString());
				} catch (Exception ex) {
					sccuss = false;
					handUIException(ex);
				}

				if (sccuss) {
					refresh(e);
					FDCClientUtils.showOprtOK(this);
				}
			}
		}
	}
	
	protected void checkImp2() throws Exception {
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		for (int i = 0; i < selectRows.length; i++) {
			String billId = getMainTable().getCell(selectRows[i], getCostBillIdFieldName()).getValue().toString();
			if (billId == null)
				continue;
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("contractBill.id");
			ContractSettlementBillInfo settle = ContractSettlementBillFactory.getRemoteInstance().getContractSettlementBillInfo(
					new ObjectUuidPK(billId), selector);
			FilterInfo filter = new FilterInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("paymentbill.contractBillId", settle.getContractBill().getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("hasEffected", Boolean.TRUE, CompareType.EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));

			FilterInfo filter2 = new FilterInfo();
			filter2 = new FilterInfo();
			filter2.getFilterItems().add(
					new FilterItemInfo("workLoadConfirmBill.contractBill.id", settle.getContractBill().getId().toString()));
			// 标识结算后的工程量拆分 by hpw 2010.07.23
			filter2.getFilterItems().add(new FilterItemInfo("hasEffected", Boolean.TRUE, CompareType.EQUALS));
			filter2.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));

			boolean hasPaymentSplit = PaymentSplitFactory.getRemoteInstance().exists(filter);
			boolean hasWorkLoadSplit = PaymentSplitFactory.getRemoteInstance().exists(filter2);

			if (!(hasPaymentSplit || hasWorkLoadSplit)) {
				FDCMsgBox.showWarning("该结算拆分没有被后续拆分引用，请点击“拆分”按钮直接修改！");
				SysUtil.abort();
			}
		}
	}

	private static final BOSObjectType costSplit = new SettlementCostSplitInfo()
			.getBOSType();

	private boolean isCostSplit(String id) {
		return id == null ? false : BOSUuid.read(id).getType()
				.equals(costSplit);
	}

	private boolean isNoCostSplit(String id) {
		return id == null ? false : !BOSUuid.read(id).getType().equals(
				costSplit);
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList ids = getSelectedIdValues();
		if (ids != null && ids.size() > 1) {
			for (Iterator iter = ids.iterator(); iter.hasNext();) {
				String id = (String) iter.next();
				if (isCostSplit(id)
						&& (SettledMonthlyHelper.existObject(null, costSplit,
								getSelectedKeyValue()))) {
					MsgBox.showWarning(this,
							"成本月结已经引用本数据，不能删除，如需修改，请把此付款对应的合同进入待处理流程！");
					SysUtil.abort();
				}
			}
			checkBeforeRemove();
			checkImp();
			if (confirmRemove()) {
				List costSplits = new ArrayList();
				List noCostSplits = new ArrayList();
				for (Iterator iter = ids.iterator(); iter.hasNext();) {
					String id = (String) iter.next();
					if (isCostSplit(id)) {
						costSplits.add(id);
					}
					if (isNoCostSplit(id)) {
						noCostSplits.add(id);
					}
				}
				if (costSplits.size() > 0) {
					IObjectPK[] pks = new ObjectUuidPK[costSplits.size()];
					for (int i = 0; i < costSplits.size(); i++) {
						pks[i] = new ObjectUuidPK(costSplits.get(i).toString());
					}
					SettlementCostSplitFactory.getRemoteInstance().delete(pks);
				}

				if (noCostSplits.size() > 0) {
					IObjectPK[] pks = new ObjectUuidPK[costSplits.size()];
					for (int i = 0; i < noCostSplits.size(); i++) {
						pks[i] = new ObjectUuidPK(noCostSplits.get(i)
								.toString());
					}
					SettNoCostSplitFactory.getRemoteInstance().delete(pks);
				}
				// afterRemove();
				showOprtOKMsgAndRefresh();
			}

		} else {
			//update by renliang
			if(ids!=null && ids.size()>0){
				for (Iterator iter = ids.iterator(); iter.hasNext();) {
					String id = (String) iter.next();
					if (isCostSplit(id)
							&& (SettledMonthlyHelper.existObject(null, costSplit,
									getSelectedKeyValue()))) {
						MsgBox.showWarning(this,
								"成本月结已经引用本数据，不能删除，如需修改，请把此付款对应的合同进入待处理流程！");
						SysUtil.abort();
					}
				}
				super.actionRemove_actionPerformed(e);
			}
		}
	}
	
	public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception
    {
		checkSelected();
		List idList = ContractClientUtils.getSelectedIdValues(
				getBillListTable(), getKeyFieldName());
		int size = idList.size();
		for (int i = 0; i < size; i++) {// 检查
			String id = idList.get(i).toString();

			IObjectPK pk = new ObjectUuidPK(BOSUuid.read(id));
			if (!getBizInterface().exists(pk)) {
				MsgBox.showWarning(this, "存在未拆分的结算单，不能生成凭证！");
				SysUtil.abort();
			}
			else{
				super.actionTraceDown_actionPerformed(e);
			}
		}
		
    }
	
	/**
     * output actionVoucher_actionPerformed
     */
    public void actionVoucher_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();
		List idList = ContractClientUtils.getSelectedIdValues(
				getBillListTable(), getKeyFieldName());
		int type = getSplit();
		int size = idList.size();
		Set ids = FDCHelper.list2Set(idList);
		ISettlementCostSplit split = SettlementCostSplitFactory.getRemoteInstance();
		SettlementCostSplitInfo splitInfo = new SettlementCostSplitInfo();
		FilterInfo filterSplit = new FilterInfo();
		filterSplit.getFilterItems().add(new FilterItemInfo("id", ids,CompareType.INCLUDE));
		filterSplit.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.INVALID));
		if (getBizInterface().exists(filterSplit)) {
			MsgBox.showWarning(this, "存在已经作废的记录，不能生成凭证！");
			SysUtil.abort();
		}
				
		for (int i = 0; i < size; i++) {// 检查
			String id = idList.get(i).toString();

			IObjectPK pk = new ObjectUuidPK(BOSUuid.read(id));
			if (!getBizInterface().exists(pk)) {
				MsgBox.showWarning(this, "存在未拆分的结算单，不能生成凭证！");
				SysUtil.abort();
			}
			else{

				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("id");
				selector.add("settlementBill.id");
				selector.add("settlementBill.isFinalSettle");
				selector.add("contractBill.id");		
				selector.add("contractBill.conSplitExecState");
				selector.add("settlementBill.billStatus");
				selector.add("settlementBill.state");
				selector.add("settlementBill.fiVouchered");
				if (type == 1) {
					splitInfo = split.getSettlementCostSplitInfo(pk,selector);
					if (splitInfo.getContractBill().getConSplitExecState().equals(
							ConSplitExecStateEnum.INVALID)) {
						MsgBox.showWarning(this,
								"合同已经进入待处理流程，请先到待处理合同对此合同进行凭证处理！");
						SysUtil.abort();
					}
					//因为界面上显示的不可能是状态在"提交"之前的单据，所以不需要考虑单据的"保存"  状态 by Cassiel_peng
					if(splitInfo.getSettlementBill().getState().equals(FDCBillStateEnum.SUBMITTED)){
						MsgBox.showWarning("该结算单尚未审批，不能生成凭证！");
						SysUtil.abort();
					}
					if (splitInfo.getSettlementBill().getIsFinalSettle().equals(BooleanEnum.FALSE)) {
						MsgBox.showWarning("存在未最终结算的结算单，不能生成凭证！");
						SysUtil.abort();
					} else if (splitInfo.isFivouchered()) {
						MsgBox.showWarning("对应的付款单已经生成凭证，不能生成凭证！");
						SysUtil.abort();
					} 
				} 
				else{
					
				}
			}
		}
		
		if (type == 1) {
			SettlementCostSplitFactory.getRemoteInstance().traceData(idList);
		} 
		super.actionVoucher_actionPerformed(e);
    }
    protected boolean checkBeforeVoucherCheck(int i) throws Exception {
		boolean isAddNew = false;
		String keyValue = getSelectedKeyValue();

		if (keyValue == null || keyValue.equals(splitBillNullID.toString())) {
			isAddNew = true;
		} else {
			// 界面上显示有，但数据库内已经删除时的情况
			IObjectPK pk = new ObjectUuidPK(BOSUuid.read(keyValue));
			if (!getBizInterface().exists(pk)) {
				isAddNew = true;
				int[] selectedRows = KDTableUtil
						.getSelectedRows(getMainTable());
				getMainTable().getCell(selectedRows[0],
						getSplitStateFieldName()).setValue(
						CostSplitStateEnum.NOSPLIT.toString());
			}
		}
		return isAddNew;
	}
    protected boolean checkBeforeVoucher() throws Exception {
		int rowNumber = FMClientHelper.getSelectedRow(tblMain);
		// IRow row = tblMain.getRow(rowNumber);
		boolean isAddNew = checkBeforeVoucherCheck(rowNumber);
		if (isAddNew) {
			// 到数据库内进行检查
			String billId = getMainTable().getCell(rowNumber, "id").getValue().toString();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("settlementBill.id", billId));
			filter.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
			if (getRemoteInterface().exists(filter)) {
				EntityViewInfo view = new EntityViewInfo();
				view.setFilter(filter);
				String costBillID = getRemoteInterface().getCollection(view).get(0).getId().toString();
				getMainTable().getCell(rowNumber, getKeyFieldName()).setValue(costBillID);
				isAddNew = false;
			}
		}

		return isAddNew;
	}
    
    public void actionDelVoucher_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		boolean isAddNew = checkBeforeVoucher();
		if (isAddNew) {
			MsgBox.showWarning("结算单未拆分，不能删除凭证！");
			SysUtil.abort();
		} else {
			List idList = ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());
			int size = idList.size();
			for (int i = 0; i < size; i++) {// 凭证检查
				// String id=getSelectedKeyValue();
				String id = idList.get(i).toString();

				// FilterInfo filter = new FilterInfo();
				// filter.getFilterItems().add(new FilterItemInfo("id", id));
				// filter.getFilterItems().add(
				// new FilterItemInfo(VOUCHERFLAG, Boolean.TRUE));
				// filter.getFilterItems().add(
				// new FilterItemInfo("paymentBill.curProject.projectStatus.id",
				// ProjectStatusInfo.flowID));
				// if (getBizInterface().exists(filter)) {
				// MsgBox.showWarning(this, "工程项目已经流失，不能执行删除凭证操作！");
				// SysUtil.abort();
				// }

				FilterInfo filterSplit = new FilterInfo();
				filterSplit.getFilterItems().add(new FilterItemInfo("id", id));
				filterSplit.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID));
				if (getBizInterface().exists(filterSplit)) {
					MsgBox.showWarning(this, "存在已经作废的记录，不能执行删除凭证操作！");
					SysUtil.abort();
				}
			}
		}
		super.actionDelVoucher_actionPerformed(e);
	}
    /**
	 * 判断选择行是不是无文本合同，选择多行返回false
	 * 
	 * @author sxhong Date 2006-12-1
	 * @param table
	 * @return isConWithoutTxt
	 */
	private static final BOSObjectType withoutTxtConBosType = new ContractWithoutTextInfo()
			.getBOSType();

	private boolean isConWithoutTxt(KDTable table) {
		boolean isConWithoutTxt = false;
		int selectRows[] = KDTableUtil.getSelectedRows(table);
		if (selectRows.length == 1) {
			Object obj = table.getCell(selectRows[0], "contractId").getValue();
			if (obj != null) {
				String contractId = obj.toString();
				isConWithoutTxt = BOSUuid.read(contractId).getType().equals(
						withoutTxtConBosType);
			}
		}
		return isConWithoutTxt;
	}
	
    private int getSplit() {
		int cost = 0;
		int noCost = 0;
		KDTable table = getMainTable();
		int selectRows[] = KDTableUtil.getSelectedRows(table);
		int size = selectRows.length;
		for (int i = 0; i < size; i++) {
			// if(selectRows.length==1){
			Object costSplit = getMainTable().getCell(selectRows[0], "isCostSplit")
			.getValue();
			boolean isCostSplit = false;
			if (costSplit instanceof Boolean) {
				isCostSplit = ((Boolean) costSplit).booleanValue();
			}

			if(isCostSplit){
				cost++;
			}
			else{
				noCost++;
			}
		}
		if (cost == size) {
			return 1;
		} else if (noCost == size) {
			return 3;
		} else if (size > 0) {
			MsgBox.showWarning("不能同时处理成本拆分和非成本拆分，请重新选择！");
			SysUtil.abort();
			return 0;
		} else {
			SysUtil.abort();
			return 0;
		}
	}

    protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		super.tblMain_tableSelectChanged(e);
		if(this.isFinacialModel()){
			this.actionViewInvalid.setVisible(true);
		}else{
			this.actionViewInvalid.setVisible(false);
		}
	}
}