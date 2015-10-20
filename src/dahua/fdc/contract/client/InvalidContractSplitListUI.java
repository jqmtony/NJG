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
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.KDTableUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class InvalidContractSplitListUI extends
		AbstractInvalidContractSplitListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(InvalidContractSplitListUI.class);

	private BOSUuid splitBillNullID = BOSUuid.create("null");

	/**
	 * output class constructor
	 */
	public InvalidContractSplitListUI() throws Exception {
		super();
	}

	public void afterActionPerformed(ActionEvent e) {
		super.afterActionPerformed(e);
		if (e.getSource() == btnRefresh || e.getSource() == menuItemRefresh) {
			setSplitStateColor();
		}
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected String getBillStatePropertyName() {
		// TODO �Զ����ɷ������
		return "costSplit.state";
	}

	public void refreshList() throws Exception {
		// TODO �Զ����ɷ������
		super.refreshList();

		setSplitStateColor();
	}

	public void refreshListForOrder() throws Exception {
		// TODO �Զ����ɷ������
		super.refreshListForOrder();

		setSplitStateColor();
	}

	protected String getCostBillStateFieldName() {
		return "state";
	}

	protected void setSplitStateColor() {

		// ��ȫ��ֺ�ͬ����ɫ�������ֲ�ֺ�ͬ����ɫ����������δ��ֺ�ͬ����ɫ����δ������ͬ����ɫ����δ�ύ��ͬ����ɫ��
		String splitState;
		String costBillState;

		// IColumn col = tblMain.getColumn("costSplit.splitState")
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			IRow row = tblMain.getRow(i);

			// �ɱ�����״̬
			// ICell cell = row.getCell("state");
			ICell cell = row.getCell(getCostBillStateFieldName());
			if (cell.getValue() != null) {
				costBillState = cell.getValue().toString();
			} else {
				costBillState = "";
			}

			// �ɱ����״̬
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

	protected ICoreBase getRemoteInterface() throws BOSException {
		if (isCostSplit()) {
			return com.kingdee.eas.fdc.contract.ContractCostSplitFactory
					.getRemoteInstance();
		} else {
			return com.kingdee.eas.fdc.contract.ConNoCostSplitFactory
					.getRemoteInstance();
		}
	}

	private boolean isCostSplit() {
		boolean isCostSplit = false;
		// �ǳɱ���Ŀ���
		int[] selectRows = KDTableUtil.getSelectedRows(getMainTable());
		if (selectRows.length < 1) {
			return false;
		}
		/*
		 * String
		 * bill=getMainTable().getCell(selectRows[0],"id").getValue().toString();
		 * if (bill!= null) { try{ ContractBillInfo con =
		 * ContractBillFactory.getRemoteInstance().getContractBillInfo(new
		 * ObjectUuidPK(bill)); if(con.isIsCoseSplit()){ isCostSplit=true; }
		 * }catch (Exception e){ handUIException(e); } }
		 */
		Object costSplit = getMainTable().getCell(selectRows[0], "isCostSplit")
				.getValue();
		if (costSplit instanceof Boolean) {
			isCostSplit = ((Boolean) costSplit).booleanValue();
		}
		return isCostSplit;
	}

	protected void audit(List ids) throws Exception {
		// TODO �Զ����ɷ������

	}

	protected void unAudit(List ids) throws Exception {
		// TODO �Զ����ɷ������

	}

	protected ICoreBase getBizInterface() throws Exception {
		return getRemoteInterface();
	}

	protected void treeSelectChange() throws Exception {

		FilterInfo filter = getTreeSelectChangeFilter();
		FilterItemCollection filterItems = filter.getFilterItems();

		/*
		 * ������Ŀ��
		 */
		if (getProjSelectedTreeNode() != null
				&& getProjSelectedTreeNode().getUserObject() instanceof CoreBaseInfo) {

			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) getProjSelectedTreeNode()
					.getUserObject();

			// ѡ����ǳɱ����ģ�ȡ�óɱ����ļ��¼��ɱ����ģ�����У��µ����к�ͬ
			if (projTreeNodeInfo instanceof OrgStructureInfo) {
				BOSUuid id = ((OrgStructureInfo) projTreeNodeInfo).getUnit()
						.getId();

				Set idSet = FDCClientUtils.genOrgUnitIdSet(id);
				filterItems.add(new FilterItemInfo("orgUnit.id", idSet,
						CompareType.INCLUDE));
			}
			// ѡ�������Ŀ��ȡ����Ŀ���¼���Ŀ������У��µ����к�ͬ
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
		// TODO �Զ����ɷ������
		// return super.getCurProjectPath();

		return "curProject";

	}

	protected void treeProject_valueChanged(
			javax.swing.event.TreeSelectionEvent e) throws Exception {
		treeSelectChange();
		setSplitStateColor();
	}

	protected String getKeyFieldName() {
		// TODO �Զ����ɷ������
		// return super.getKeyFieldName();

		return "costSplit.id";
	}

	protected String getEditUIName() {
		if (isCostSplit()) {
			return com.kingdee.eas.fdc.contract.client.ContractCostSplitEditUI.class
					.getName();
		} else {
			return com.kingdee.eas.fdc.contract.client.ConNoCostSplitEditUI.class
					.getName();
		}
	}

	protected void getRowSetBeforeFillTable(IRowSet rowSet) {
		// TODO �Զ����ɷ������
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
					// ������
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
		setSplitStateColor();
	}

	protected String getSplitStateFieldName() {
		return "costSplit.splitState";
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