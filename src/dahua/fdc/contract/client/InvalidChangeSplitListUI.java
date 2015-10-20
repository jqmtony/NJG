/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.contract.ConChangeNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitFactory;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class InvalidChangeSplitListUI extends AbstractInvalidChangeSplitListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(InvalidChangeSplitListUI.class);

	private BOSUuid splitBillNullID = BOSUuid.create("null");

	/**
	 * output class constructor
	 */
	public InvalidChangeSplitListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected String getBillStatePropertyName() {
		return "costSplit.state";
	}

	private boolean isCostSplit() {
		boolean isCostSplit = false;
		// 非成本科目拆分
		int[] selectRows = KDTableUtil.getSelectedRows(getMainTable());
		if (selectRows.length < 1) {
			return false;
		}
		Object costSplit = getMainTable().getCell(selectRows[0], "isCostSplit")
				.getValue();
		if (costSplit instanceof Boolean) {
			isCostSplit = ((Boolean) costSplit).booleanValue();
		}
		return isCostSplit;
	}

	protected ICoreBase getRemoteInterface() throws BOSException {
		if (isCostSplit()) {
			return ConChangeSplitFactory.getRemoteInstance();
		} else {
			return ConChangeNoCostSplitFactory.getRemoteInstance();
		}
	}

	protected void audit(List ids) throws Exception {

	}

	protected void unAudit(List ids) throws Exception {

	}

	protected ICoreBase getBizInterface() throws Exception {
		return getRemoteInterface();
	}

	protected String getKeyFieldName() {
		// TODO 自动生成方法存根
		// return super.getKeyFieldName();

		return "costSplit.id";
	}

	protected String getEditUIName() {
		if (isCostSplit()) {
			return ConChangeSplitEditUI.class.getName();
		} else {
			return ConChangeNoCostEditUI.class.getName();
		}
	}

	protected FilterInfo getTreeSelectChangeFilter() {
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		filterItems.add(new FilterItemInfo(getCurProjectPath() + ".isEnabled",
				Boolean.TRUE));
		return filter;
	}

	protected void treeSelectChange() throws Exception {

		FilterInfo filter = getTreeSelectChangeFilter();
		FilterItemCollection filterItems = filter.getFilterItems();

		/*
		 * 工程项目树
		 */
		if (getProjSelectedTreeNode() != null
				&& getProjSelectedTreeNode().getUserObject() instanceof CoreBaseInfo) {

			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) getProjSelectedTreeNode()
					.getUserObject();

			// 选择的是成本中心，取该成本中心及下级成本中心（如果有）下的所有合同
			if (projTreeNodeInfo instanceof OrgStructureInfo) {
				BOSUuid id = ((OrgStructureInfo) projTreeNodeInfo).getUnit()
						.getId();

				Set idSet = FDCClientUtils.genOrgUnitIdSet(id);
				filterItems.add(new FilterItemInfo("orgUnit.id", idSet,
						CompareType.INCLUDE));
			}
			// 选择的是项目，取该项目及下级项目（如果有）下的所有合同
			else if (projTreeNodeInfo instanceof CurProjectInfo) {
				BOSUuid id = projTreeNodeInfo.getId();
				Set idSet = FDCClientUtils.genProjectIdSet(id);
				filterItems.add(new FilterItemInfo(getCurProjectPath() + ".id",
						idSet, CompareType.INCLUDE));
			}
		}

		filterItems.add(new FilterItemInfo(getCurProjectPath() + ".isEnabled",
				Boolean.TRUE));
		filterItems.add(new FilterItemInfo("costSplit.state",
				FDCBillStateEnum.INVALID_VALUE, CompareType.EQUALS));
		// filter=new FilterInfo();
		mainQuery.setFilter(filter);
		execQuery();

		if (getMainTable().getRowCount() > 0) {
			getMainTable().getSelectManager().select(0, 0);
		}
	}

	protected String getCurProjectPath() {
		// TODO 自动生成方法存根
		// return super.getCurProjectPath();

		return "contractBill.curProject";

	}

	protected void treeProject_valueChanged(
			javax.swing.event.TreeSelectionEvent e) throws Exception {
		treeSelectChange();
		setSplitStateColor();
	}

	protected void getRowSetBeforeFillTable(IRowSet rowSet) {
		// TODO 自动生成方法存根
		super.getRowSetBeforeFillTable(rowSet);
		String state = null;
		String splitState = null;
		try {
			rowSet.beforeFirst();

			while (rowSet.next()) {
				state = rowSet.getString("costSplit.state");
				splitState = rowSet.getString(getSplitStateFieldName());
				if (splitState == null) {
					// rowSet.updateString("costSplit.splitState",CostSplitState.NOSPLIT.toString());
					// rowSet.updateObject("costSplit.id",splitBillNullID);
					rowSet.updateString(getSplitStateFieldName(),
							CostSplitStateEnum.NOSPLIT.toString());
					rowSet.updateObject(getKeyFieldName(), splitBillNullID);
				} else if (splitState.equals(CostSplitStateEnum.ALLSPLIT_VALUE)) {
					rowSet.updateString(getSplitStateFieldName(),
							CostSplitStateEnum.ALLSPLIT.toString());
				} else if (splitState
						.equals(CostSplitStateEnum.PARTSPLIT_VALUE)) {
					rowSet.updateString(getSplitStateFieldName(),
							CostSplitStateEnum.PARTSPLIT.toString());
				}
				if (state == null) {
					// 不处理
				} else if (state.equals(FDCBillStateEnum.INVALID_VALUE)) {
					rowSet.updateString("costSplit.state",
							FDCBillStateEnum.INVALID.toString());
				}
			}
			rowSet.beforeFirst();

		} catch (SQLException e) {
			handUIExceptionAndAbort(e);
		} catch (UuidException e) {
			handUIExceptionAndAbort(e);
		}
	}

	public void onLoad() throws Exception {
		super.onLoad();
		actionQuery.setVisible(true);
		actionQuery.setEnabled(true);
		actionWorkFlowG.setVisible(false);
		setSplitStateColor();
	}

	protected String getSplitStateFieldName() {
		return "costSplit.splitState";
	}

	public void refreshList() throws Exception {
		// TODO 自动生成方法存根
		super.refreshList();

		setSplitStateColor();
	}

	public void refreshListForOrder() throws Exception {
		// TODO 自动生成方法存根
		super.refreshListForOrder();

		setSplitStateColor();
	}

	public void afterActionPerformed(ActionEvent e) {
		super.afterActionPerformed(e);
		if (e.getSource() == btnRefresh || e.getSource() == menuItemRefresh) {
			setSplitStateColor();
		}
	}

	protected String getCostBillStateFieldName() {
		return "state";
	}

	protected void setSplitStateColor() {

		// 完全拆分合同（绿色）、部分拆分合同（黄色）、已审批未拆分合同（桔色）、未审批合同（蓝色）、未提交合同（白色）
		String splitState;
		String costBillState;

		// IColumn col = tblMain.getColumn("costSplit.splitState")
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			IRow row = tblMain.getRow(i);

			// 成本单据状态
			// ICell cell = row.getCell("state");
			ICell cell = row.getCell(getCostBillStateFieldName());
			if (cell.getValue() != null) {
				costBillState = cell.getValue().toString();
			} else {
				costBillState = "";
			}

			// 成本拆分状态
			cell = row.getCell(getSplitStateFieldName());
			if (cell.getValue() == null
					|| cell.getValue().toString().equals("")) {
				splitState = CostSplitStateEnum.NOSPLIT.toString();
				cell.setValue(splitState);
			} else {
				splitState = cell.getValue().toString();
			}
			if (splitState.equals(CostSplitStateEnum.ALLSPLIT.toString())) {
				row.getStyleAttributes().setBackground(
						FDCSplitClientHelper.COLOR_ALLSPLIT);
			} else if (splitState.equals(CostSplitStateEnum.PARTSPLIT
					.toString())) {
				row.getStyleAttributes().setBackground(
						FDCSplitClientHelper.COLOR_PARTSPLIT);
			} else {
				row.getStyleAttributes().setBackground(
						FDCSplitClientHelper.COLOR_NOSPLIT);
			}

		}

	}

	protected void updateButtonStatus() {
		super.updateButtonStatus();
		actionEdit.setEnabled(false);
		actionAddNew.setEnabled(false);
		actionRemove.setEnabled(false);
	}

	protected void initTable() {

	}
}