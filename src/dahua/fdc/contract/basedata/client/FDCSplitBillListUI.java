/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.Action;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDLabel;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.QueryInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.query.util.QueryUtil;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.DefaultPromptBoxFactory;
import com.kingdee.eas.base.commonquery.client.IPromptBoxFactory;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.PayReqUtils;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.SettNoCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.client.ConChangeSplitListUI;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.contract.client.ContractCostSplitListUI;
import com.kingdee.eas.fdc.contract.client.SettlementCostSplitListUI;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitEntryFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryFactory;
import com.kingdee.eas.fdc.finance.client.PaymentSplitListUI;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.config.client.ConfigServiceUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class FDCSplitBillListUI extends AbstractFDCSplitBillListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(FDCSplitBillListUI.class);

	protected static final BOSUuid splitBillNullID = BOSUuid.create("null");

	/**
	 * 是否刷新MainTable
	 */
	private boolean isNeedRefresh = true;
	
	//获取有权限的组织
	protected Set authorizedOrgs = null;
	
	/** 一体化参数值 */
	protected Map initParam = new HashMap();
	
	//是否允许拆分提交状态的单据   by Cassiel_peng
	protected static boolean canSplitSubmit(){
		boolean canSplitSubmitBill=false;
		try {
			canSplitSubmitBill= FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_SPLITSUBMIT);
		} catch (Exception e) {
			// @AbortException
			logger.error(e.getMessage(), e);
		}
		return canSplitSubmitBill;
	}
	 
	/**
	 * output class constructor
	 */
	public FDCSplitBillListUI() throws Exception {
		super();
	}
	
	protected void refresh(ActionEvent e) throws Exception {
		if (isNeedRefresh) {
			super.refresh(e);
		}
	}
	protected CommonQueryDialog initCommonQueryDialog() {
		CommonQueryDialog commonDialog = super.initCommonQueryDialog();
		IPromptBoxFactory factory = new DefaultPromptBoxFactory() {
			public KDPromptBox create(String queryName,
					EntityObjectInfo entity, String propertyName) {
				return super.create(queryName, entity, propertyName);
			}

			public KDPromptBox create(String queryName, QueryInfo mainQuery,
					String queryFieldName) {
				final KDBizPromptBox f7 = (KDBizPromptBox) super.create(
						queryName, mainQuery, queryFieldName);
				if (queryName
						.equalsIgnoreCase("com.kingdee.eas.fdc.contract.app.ContractBillF7Query")) {
					f7.addSelectorListener(new SelectorListener() {
						public void willShow(SelectorEvent e) {
							f7.getQueryAgent().resetRuntimeEntityView();
							EntityViewInfo view = new EntityViewInfo();
							FilterInfo filter = new FilterInfo();
							FilterItemCollection filterItems = filter
									.getFilterItems();
							if (getProjSelectedTreeNode() != null
									&& getProjSelectedTreeNode()
											.getUserObject() instanceof CoreBaseInfo) {

								CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) getProjSelectedTreeNode()
										.getUserObject();

								// 选择的是成本中心，取该成本中心及下级成本中心（如果有）下的所有合同
								if (projTreeNodeInfo instanceof OrgStructureInfo) {
									BOSUuid id = ((OrgStructureInfo) projTreeNodeInfo).getUnit().getId();

									Set idSet = null;
									try {
										idSet = FDCClientUtils.genOrgUnitIdSet(id);
									} catch (Exception exe) {
										handUIExceptionAndAbort(exe);
									}
									if (idSet != null && idSet.size() > 0) {
										filterItems.add(new FilterItemInfo("orgUnit.id", idSet, CompareType.INCLUDE));
									}
								}
								// 选择的是项目，取该项目及下级项目（如果有）下的所有合同
								else if (projTreeNodeInfo instanceof CurProjectInfo) {
									BOSUuid id = projTreeNodeInfo.getId();
									Set idSet = null;
									try {
										idSet = FDCClientUtils.genProjectIdSet(id);
									} catch (Exception exe) {
										handUIExceptionAndAbort(exe);
									}
									if (idSet != null && idSet.size() > 0) {
										filterItems.add(new FilterItemInfo("curProject.id", idSet, CompareType.INCLUDE));
									}
								}
							}
							filterItems.add(new FilterItemInfo("curProject.isEnabled", Boolean.TRUE));
							view.setFilter(filter);
							f7.setEntityViewInfo(view);
						}
					});

				}
				return f7;
			}
		};
		commonDialog.setPromptBoxFactory(factory);
		return commonDialog;
	}

	/**
	 * 用于显示一些被隐藏的按钮，不知道什么原因在initWorkButton内设置无效，<b>
	 * 所以在这设置了;;//ProjectListBaseUI在onLoad()中设置了
	 * 
	 * @author sxhong Date 2006-11-20
	 * @throws Exception
	 * @see com.kingdee.eas.framework.client.ListUI#onShow()
	 */
	public void onShow() throws Exception {
		tblMain.setColumnMoveable(true);
		super.onShow();
		actionQuery.setEnabled(true);
		actionQuery.setVisible(true);
		drawColorPanel();
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		if (e.getClickCount() == 2 && e.getType() == KDTStyleConstants.BODY_ROW) {
			// 双击打开拆分界面
			ActionEvent evt = new ActionEvent(btnView, 0, "Double Clicked");
			ItemAction actView = getActionFromActionEvent(evt);
			actView.actionPerformed(evt);

		} else
			super.tblMain_tableClicked(e);
		// checkSelected();

	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		checkSelected();
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		String projectID = (String)getMainTable().getCell(selectRows[0],"curProject.id").getValue();
		this.getUIContext().put("projectId", BOSUuid.read(projectID));
		isFinacial = this.isFinacialModel(projectID);
		isAdjustVourcherMode = this.isAdjustVourcherModel(projectID);
		isSimpleFinacialMode = this.isSimpleFinacialMode(projectID);
		isSimpleFinacialExtendMode = this.isSimpleFinacialExtendMode(projectID);
		isWorkLoadSeparate = this.isWorkLoadSeparate(projectID); 
		super.tblMain_tableSelectChanged(e);
	}

	/**
	 * output treeProject_valueChanged method
	 */
	protected void treeProject_valueChanged(
			javax.swing.event.TreeSelectionEvent e) throws Exception {
		treeSelectChange();
	}

	/**
	 * 构建过滤条件，执行查询
	 * 
	 * @author sxhong Date 2006-11-10
	 * @throws Exception
	 */
	protected void treeSelectChange() throws Exception {
		execQuery();

		if (getMainTable().getRowCount() > 0) {
			getMainTable().getSelectManager().select(0, 0);
		}
	}

	protected FilterInfo getTreeFilter() throws Exception {

		FilterInfo filter = getTreeSelectChangeFilter();
		FilterItemCollection filterItems = filter.getFilterItems();
		//获得当前用户下的组织范围ids
		authorizedOrgs = new HashSet();
		Map orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(
				 new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId()),
		            OrgType.CostCenter, 
		            null,  null, null);
		if(orgs!=null){
			Set orgSet = orgs.keySet();
			Iterator it = orgSet.iterator();
			while(it.hasNext()){
				authorizedOrgs.add(it.next());
			}
		}
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
				filterItems.add(new FilterItemInfo("orgUnit.id", authorizedOrgs,
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
		// filter=new FilterInfo();
		return filter;
	}

	protected ICoreBase getRemoteInterface() throws BOSException {
		return null;
	}

	protected void audit(List ids) throws Exception {
		((IFDCBill) getRemoteInterface()).audit(ids);
	}

	protected void unAudit(List ids) throws Exception {
		((IFDCBill) getRemoteInterface()).unAudit(ids);

	}

	protected SorterItemCollection getSorter() {
		SorterItemCollection sorter = new SorterItemCollection();
		sorter.add(new SorterItemInfo(getSplitStateFieldName()));
		sorter.add(new SorterItemInfo(getCostBillStateFieldName()));
		return sorter;
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkbeforeAudit();
		List idList = ContractClientUtils.getSelectedIdValues(getMainTable(),
				getKeyFieldName());
		boolean hasMutex = false;
		try {
			FDCClientUtils.requestDataObjectLock(this, idList, "Audit");
			super.actionAudit_actionPerformed(e);

		} catch (Throwable e1) {
			// @AbortException
			this.handUIException(e1);
			hasMutex = FDCClientUtils.hasMutexed(e1);
		} finally {
			if (!hasMutex) {
				try {
					FDCClientUtils.releaseDataObjectLock(this, idList);
				} catch (Throwable e1) {
					// @AbortException
					this.handUIException(e1);
				}
			}
		}
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkbeforeAudit();
		super.actionUnAudit_actionPerformed(e);
	}

	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();

		boolean isNotSplit = false;
		String keyValue = getSelectedKeyValue();

		if (keyValue == null || keyValue.equals(splitBillNullID.toString())) {
			isNotSplit = true;
		} else {
			// 界面上显示有，但数据库内已经删除时的情况
			IObjectPK pk = new ObjectUuidPK(BOSUuid.read(keyValue));
			// 暂时去掉这个操作
			if (!getBizInterface().exists(pk)) {
				isNotSplit = true;
				int[] selectedRows = KDTableUtil
						.getSelectedRows(getMainTable());
				getMainTable().getCell(selectedRows[0],
						getSplitStateFieldName()).setValue(
						CostSplitStateEnum.NOSPLIT.toString());
			}
		}

		if (isNotSplit) {
			MsgBox.showWarning(this, FDCSplitClientHelper.getRes("noView"));
			SysUtil.abort();
		} else {
			super.actionView_actionPerformed(e);
		}
	}

	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		// super.actionAddLine_actionPerformed(e);
		// 空方法禁用实现
	}

	public void actionInsertLine_actionPerformed(ActionEvent e)
			throws Exception {
		// super.actionInsertLine_actionPerformed(e);
	}

	protected void checkbeforeAudit() throws Exception {
		checkSelected();
		List idList = ContractClientUtils.getSelectedIdValues(getMainTable(),// "id");
				getKeyFieldName());

		Set idSet = ContractClientUtils.listToSet(idList);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		if (idSet.size() > 0) {
			filter.getFilterItems().add(
					new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		}
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("splitState");
		CoreBaseCollection coll = getRemoteInterface().getCollection(view);

		if (!coll.iterator().hasNext()) {
			MsgBox.showWarning(this, FDCSplitClientHelper.getRes("noView"));
			SysUtil.abort();
		}

		if (coll.size() < idList.size()) {
			MsgBox.showWarning(this, FDCSplitClientHelper
					.getRes("existNoSplitRecord"));
			SysUtil.abort();
		}

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			CoreBaseInfo element = (CoreBaseInfo) iter.next();

			// 检查单据是否在工作流中
			FDCClientUtils
					.checkBillInWorkflow(this, element.getId().toString());
			if (((element.getString("splitState"))
					.equals(CostSplitStateEnum.NOSPLIT_VALUE))) {
				MsgBox.showWarning(this, FDCSplitClientHelper
						.getRes("partSplited"));
				SysUtil.abort();
			}
		}
	}

	protected String getSelectedKeyValue() {

		String keyValue;
		keyValue = super.getSelectedKeyValue();

		if (keyValue == null || keyValue.equals(splitBillNullID.toString())) {
			String costBillID = getSelectedCostBillID();
			if (costBillID == null) {
				return null;
			}
		}

		return keyValue;
	}

	/**
	 * 原始成本单据ID
	 * 
	 * @author jelon Date 2006-11-11
	 * @throws
	 */
	protected String getSelectedCostBillID() {
		int selectIndex = -1;

		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);

		if (selectRows.length > 0) {
			int rowIndex = selectRows[0];
			IRow row = tblMain.getRow(rowIndex);
			if (row == null) {
				return null;
			}

			selectIndex = rowIndex;
			String key = getCostBillIdFieldName();
			if (null == key) {
				return null;
			}

			ICell cell = row.getCell(key);
			if (cell == null) {
				return null;
			}
			// if (cell == null) {
			// MsgBox.showError(EASResource
			// .getString(FrameWorkClientUtils.strResource
			// + "Error_KeyField_Fail"));
			// SysUtil.abort();
			// }

			Object keyValue = cell.getValue();

			if (keyValue != null) {
				return keyValue.toString();
			}
		}

		return null;
	}

	protected String getKeyFieldName() {
		return "costSplit.id";
	}

	protected void beforeExcutQuery(EntityViewInfo ev) {
		if (ev.getFilter() != null) {
			FilterItemCollection items = ev.getFilter().getFilterItems();
			FilterItemInfo item = new FilterItemInfo("costSplit.splitState",
					CostSplitStateEnum.NOSPLIT_VALUE);
			FilterItemInfo item2 = new FilterItemInfo("costSplit.splitState",
					CostSplitStateEnum.ALLSPLIT_VALUE, CompareType.NOTEQUALS);
			FilterItemInfo item3 = new FilterItemInfo("costSplit.splitState",
					CostSplitStateEnum.PARTSPLIT_VALUE, CompareType.NOTEQUALS);
			if (items.contains(item2)) {
				FilterItemInfo replaceItem = new FilterItemInfo(
						"costSplit.splitState", null);
				FilterItemInfo replaceItem2 = new FilterItemInfo(
						"costSplit.splitState",
						CostSplitStateEnum.PARTSPLIT_VALUE);
				if (items.size() == 1) {
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(replaceItem);
					filter.getFilterItems().add(replaceItem2);
					filter.setMaskString("#0 or #1");
					ev.setFilter(filter);
				} else {
					String oldMaskString = QueryUtil.processMaskString(ev
							.getFilter().getFilterItems().size(), ev
							.getFilter().getMaskString());
					int i = items.indexOf(item2);
					for (int j = items.size() - 1; j > i; j--) {
						oldMaskString = oldMaskString.replaceAll("#" + j, "#"
								+ (j + 1));
					}
					oldMaskString = oldMaskString.replaceAll("#" + i, "(#" + i + " or #"
							+ (i + 1) + ")");
					items.remove(item2);
					items.addObject(i, replaceItem2);
					items.addObject(i, replaceItem);
				}

			}
			if (items.contains(item3)) {
				FilterItemInfo replaceItem = new FilterItemInfo(
						"costSplit.splitState", null);
				FilterItemInfo replaceItem2 = new FilterItemInfo(
						"costSplit.splitState",
						CostSplitStateEnum.ALLSPLIT_VALUE);
				if (items.size() == 1) {
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(replaceItem);
					filter.getFilterItems().add(replaceItem2);
					filter.setMaskString("#0 or #1");
					ev.setFilter(filter);
				} else {
					String oldMaskString = QueryUtil.processMaskString(ev
							.getFilter().getFilterItems().size(), ev
							.getFilter().getMaskString());
					int i = items.indexOf(item2);
					for (int j = items.size() - 1; j > i; j--) {
						oldMaskString = oldMaskString.replaceAll("#" + j, "#"
								+ (j + 1));
					}
					oldMaskString = oldMaskString.replaceAll("#" + i, "(#" + i + " or #"
							+ (i + 1) + ")");
					items.remove(item2);
					items.addObject(i, replaceItem2);
					items.addObject(i, replaceItem);
				}

			}
		}
		super.beforeExcutQuery(ev);
	}

	protected void getRowSetBeforeFillTable(IRowSet rowSet) {

		String splitState = null;
		String state = null;
		String amountField = "amount";
		 
		if (this instanceof SettlementCostSplitListUI) {
			amountField = "settlePrice";
		}
		try {
			rowSet.beforeFirst();

			while (rowSet.next()) {
				// splitState=rowSet.getString("costSplit.splitState");
				splitState = rowSet.getString(getSplitStateFieldName());
				state = rowSet.getString("costSplit.state");
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
				}else if (splitState
						.equals(CostSplitStateEnum.NOSPLIT_VALUE)) {
					rowSet.updateString(getSplitStateFieldName(),
							CostSplitStateEnum.NOSPLIT.toString());
					rowSet.updateObject(getKeyFieldName(), splitBillNullID);
					
				}
				if(rowSet.getObject(getKeyFieldName())==null){
					rowSet.updateObject(getKeyFieldName(), splitBillNullID);
				}
				if (state == null) {
					// 不处理
				} else if (state.equals(FDCBillStateEnum.SAVED_VALUE)) {
					rowSet.updateString("costSplit.state",
							FDCBillStateEnum.SAVED.toString());
				} else if (state.equals(FDCBillStateEnum.SUBMITTED_VALUE)) {
					rowSet.updateString("costSplit.state",
							FDCBillStateEnum.SUBMITTED.toString());
				} else if (state.equals(FDCBillStateEnum.AUDITTED_VALUE)) {
					rowSet.updateString("costSplit.state",
							FDCBillStateEnum.AUDITTED.toString());
				} else if (state.equals(FDCBillStateEnum.AUDITTING_VALUE)) {
					rowSet.updateString("costSplit.state",
							FDCBillStateEnum.AUDITTING.toString());
				}
				
				if (amountField != null) {
					BigDecimal amount = rowSet.getBigDecimal(amountField);
					if (amount == null || amount.compareTo(FDCHelper.ZERO) == 0) {
						// 零金额合同显示为完全拆分 万科不需要了,恶心的万科  by sxhong 2008-05-24 15:09:03
						rowSet.updateBigDecimal(amountField, FDCHelper.ZERO);
						/*rowSet.updateString(getSplitStateFieldName(),
								CostSplitStateEnum.ALLSPLIT.toString());*/
					}
				}
			}

			rowSet.beforeFirst();

		} catch (SQLException e) {
			handUIExceptionAndAbort(e);
		} 
	}

	/**
	 * 在tblMain_tableAfterDataFill(KDTDataRequestEvent) 内调用
	 */
	protected void setSplitStateColor(int start, int end) {
		// 完全拆分合同（绿色）、部分拆分合同（黄色）、已审批未拆分合同（桔色）、未审批合同（蓝色）、未提交合同（白色）
		String splitState;
		String costBillState;

		// IColumn col = tblMain.getColumn("costSplit.splitState")
		for (int i = start; i <= end; i++) {
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

			/*
			 * if(splitState=="完全拆分"){
			 * row.getStyleAttributes().setBackground(new Color(0xD2E3CA)); }
			 * else if(splitState=="部份拆分"){
			 * row.getStyleAttributes().setBackground(new Color(0xF6F6BF)); }
			 * else if(contrState=="已审批"){
			 * row.getStyleAttributes().setBackground(new Color(0xE9E2B8)); }
			 * else if(contrState=="已提交"||contrState=="审批中"){
			 * row.getStyleAttributes().setBackground(new Color(0xF5F5E6)); }
			 * else if(contrState=="保存"){ ; }
			 */

			if (splitState.equals(CostSplitStateEnum.ALLSPLIT.toString())) {
				row.getStyleAttributes().setBackground(
						FDCSplitClientHelper.COLOR_ALLSPLIT);
			} else if (splitState.equals(CostSplitStateEnum.PARTSPLIT
					.toString())) {
				row.getStyleAttributes().setBackground(
						FDCSplitClientHelper.COLOR_PARTSPLIT);
			}
			/*
			 * else
			 * if(costBillState.equals(FDCBillStateEnum.AUDITTED.toString())){
			 * row.getStyleAttributes().setBackground(FDCSplitClientHelper.COLOR_AUDITTED); }
			 * else
			 * if(costBillState.equals(FDCBillStateEnum.SUBMITTED.toString())
			 * ||costBillState.equals(FDCBillStateEnum.AUDITTING.toString())){
			 * row.getStyleAttributes().setBackground(FDCSplitClientHelper.COLOR_UNAUDITTED); }
			 */
			/*
			 * else if(costBillState.equals(FDCBillStateEnum.SAVED.toString())){ }
			 */else {
				row.getStyleAttributes().setBackground(
						FDCSplitClientHelper.COLOR_NOSPLIT);
			}

		}

	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add(new SelectorItemInfo(getCostBillStateFieldName()));
		return sic;
	}

	protected void checkBeforeRemove() throws Exception {
		checkSelected();
		{// 审批检查
			String id = getSelectedKeyValue();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", id));
			filter.getFilterItems()
					.add(
							new FilterItemInfo("state",
									FDCBillStateEnum.AUDITTED_VALUE));
			if (getBizInterface().exists(filter)) {
				MsgBox.showWarning(FDCSplitClientHelper.getRes("listRemove"));
				SysUtil.abort();
			}
		}
		boolean isNotSplit = false;
		// 避免不一致的情况的出现，从数据库内取值判断
		List idList = new ArrayList();

		int[] selectedRows = KDTableUtil.getSelectedRows(getMainTable());

		for (int i = 0; i < selectedRows.length; i++) {
			String id = (String) getMainTable().getCell(selectedRows[i],
					getKeyFieldName()).getValue();
			// 只做表面检查，不然性能太差了
			if (id == null || id.equals(splitBillNullID.toString())) {
				isNotSplit = true;
				break;
			}
			idList.add(id);
		}
		if (isNotSplit && idList.size() > 0) {
			Set idSet = ContractClientUtils.listToSet(idList);
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			if (idSet.size() > 0) {
				filter.getFilterItems().add(
						new FilterItemInfo("id", idSet, CompareType.INCLUDE));
			}
			view.setFilter(filter);
			CoreBaseCollection collection = getRemoteInterface().getCollection(
					view);

			if (collection.size() < 1 && idSet.size() > collection.size()) {
				isNotSplit = true;
			}
		}
		if (isNotSplit) {
			MsgBox.showWarning(this, FDCSplitClientHelper.getRes("noView"));
			SysUtil.abort();
		}
	}


	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);

		ItemAction act = getActionFromActionEvent(e);

		if (act.equals(actionAddNew)) {
			uiContext.put("costBillID", getSelectedCostBillID());
		} else if (act.equals(actionEdit) || act.equals(actionView)) {
			uiContext.put("costBillID", getSelectedCostBillID());
			uiContext.put(UIContext.ID, getSelectedKeyValue());
		}
		
		prepareUIContext2(uiContext);
	}

	

	public void actionCostSplit_actionPerformed(ActionEvent e) throws Exception {
		
		// Added by Jian_cao 
		IUIWindow win = FDCClientUtils.findUIWindow(this.getEditUIName(), this.getUIContext(), dataObjects, OprtState.ADDNEW);
		if (null != win) {
			win.show();
			//是否刷新tblMain设置为【不刷新】
			isNeedRefresh = false;
			//关闭已经打开的拆分界面 （关闭的时候会自动刷新tblMain，那么tblMain选择的行就会丢失，所以在这里做个标识来控制）
			win.close();
			isNeedRefresh = true;
		}
		
		checkSelected();

		boolean hasMutex = false;
		List idList =ContractClientUtils.getSelectedIdValues(getBillListTable(), getSelectedKeyValue());
		try{
				
			FDCClientUtils.requestDataObjectLock(this, idList, "Audit");
			String id = getSelectedKeyValue();

			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", id));
			filter.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
			if (getBizInterface().exists(filter)) {
				MsgBox.showWarning(FDCSplitClientHelper.getRes("listSplit"));
				SysUtil.abort();
			}
//			 filter = new FilterInfo();
//			filter.getFilterItems().add(new FilterItemInfo("id", id));
//			if (getBizInterface().exists(filter)) {
//				MsgBox.showWarning("已拆分单据不能进行再次拆分");
//				SysUtil.abort();
//			}
			checkSplit();
			boolean isAddNew = checkBeforeSplit();
			UIContext uiContext = new UIContext(this);
			prepareUIContext2(uiContext);
			
			if (isAddNew) {
			
				ActionEvent evt = new ActionEvent(btnAddNew, 0, "FDCCostSplit");
				ItemAction actAddNew = getActionFromActionEvent(evt);
				actAddNew.actionPerformed(evt);
				// btnAddNew.doClick();
			} else {
				String costBillID = getSelectedKeyValue();
				//量价合同请用量价拆分打开
				if(this instanceof ContractCostSplitListUI){
					FilterInfo myFilter=new FilterInfo();
					myFilter.appendFilterItem("id", getSelectedCostBillID());
					myFilter.appendFilterItem("isMeasureContract", Boolean.TRUE);
					if(ContractBillFactory.getRemoteInstance().exists(myFilter)){
						FDCMsgBox.showWarning(this, "已拆分的量价拆分请用量价拆分进行再次拆分");
						SysUtil.abort();
					}
				}
				
				uiContext.put(UIContext.ID, costBillID);
				//把所选择节点信息存入uiContext 传给FDCSplitBillEditUI
				DefaultKingdeeTreeNode node = getProjSelectedTreeNode();
				uiContext.put("node",node);
				IUIWindow uiWin = null;
				
				// 根据配置项决定UI打开方式 jelon 12/30/2006
				/*
				 * uiWin =
				 * UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(getEditUIName(),
				 * uiContext , null , OprtState.EDIT);
				 */
				uiWin = UIFactory.createUIFactory(getEditUIModal()).create(
						getEditUIName(), uiContext, null, OprtState.EDIT);
	
				uiWin.show();
			}
		}
		catch (Throwable e1) {
			// @AbortException
			this.handUIException(e1);
			hasMutex = FDCClientUtils.hasMutexed(e1);
		}
		finally
		{
			if (!hasMutex) {
				try {
					FDCClientUtils.releaseDataObjectLock(this, idList);
				} catch (Throwable e1) {
					// @AbortException
					this.handUIException(e1);
				}
			}	
		}
	}

	/**
	 * 描述：
	 * 
	 * @param uiContext
	 * @author rd_skyiter_wang
	 * @createDate 2014-12-30
	 */
	private void prepareUIContext2(UIContext uiContext) {
		uiContext.put("costBillID", getSelectedCostBillID());
		
		// 获取选中行的index
		int selectedRowIndex = this.getMainTable().getSelectManager().getActiveRowIndex();
		// 获取选中行的合同ID,将其放入上下文
		if (selectedRowIndex >= 0) {
			if (this.getMainTable().getRow(selectedRowIndex).getCell("contractBill.id") != null) {
				String contractBillId = this.getMainTable().getRow(selectedRowIndex).getCell(
						"contractBill.id").getValue().toString();

				uiContext.put("contractBillId", contractBillId);
				getUIContext().put("contractBillId", contractBillId);
			} else if (this.getMainTable().getRow(selectedRowIndex).getCell("contractId") != null) {
				String contractBillId = this.getMainTable().getRow(selectedRowIndex).getCell(
						"contractId").getValue().toString();

				uiContext.put("contractBillId", contractBillId);
				getUIContext().put("contractBillId", contractBillId);
			}
		}
	}

	protected void checkSplit() throws Exception {
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		if (selectRows.length > 1) {
			MsgBox.showWarning(this, FDCSplitClientHelper
					.getRes("multiRowSelected"));
			SysUtil.abort();
		}
		String billId = getMainTable().getCell(selectRows[0],
				getCostBillIdFieldName()).getValue().toString();
		boolean existCostSplitColumn=getMainTable().getColumnIndex("isCostSplit")!=-1;
		boolean isCostSplit=false;
		if(existCostSplitColumn){
			Boolean temp=(Boolean)getMainTable().getCell(selectRows[0],"isCostSplit").getValue();
			if(temp!=null){
				isCostSplit=temp.booleanValue();
			}
		}
		FilterInfo filtertemp = new FilterInfo();
		filtertemp.getFilterItems().add(
				new FilterItemInfo("costBillId", billId));
		// 作废单据不算
		filtertemp.getFilterItems().add(
				new FilterItemInfo("parent.state",
						FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));

		
		boolean existSettlecoll = false;
		boolean existcollPay = false;
		boolean existcollNoCost = false;
		boolean existcollPayNoCost = false;
		if (existCostSplitColumn && isCostSplit) {
			existSettlecoll = SettlementCostSplitEntryFactory.getRemoteInstance().exists(filtertemp);
			existcollPay = PaymentSplitEntryFactory.getRemoteInstance().exists(filtertemp);
		}
		if (existCostSplitColumn && !isCostSplit) {
			existcollNoCost = SettNoCostSplitEntryFactory.getRemoteInstance().exists(filtertemp);
			existcollPayNoCost = PaymentNoCostSplitEntryFactory.getRemoteInstance().exists(filtertemp);
		}
		if (existSettlecoll || existcollNoCost) {
			MsgBox.showError(this, FDCSplitClientHelper.getRes("impBySett"));
			SysUtil.abort();
		}
		if (existcollPay || existcollPayNoCost) {
			MsgBox.showError(this, FDCSplitClientHelper.getRes("impByPay"));
			SysUtil.abort();
		}
	}

	protected String getSplitStateFieldName() {
		return "costSplit.splitState";
	}

	protected String getCostBillIdFieldName() {
		return "id";
	}

	protected String getCostBillStateFieldName() {
		return "state";
	}

	protected String getCostBillFieldName() {
		return "costBill";
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.kingdee.eas.fdc.basedata.client.ProjectTreeListBaseUI#initWorkButton()
	 */
	protected void initWorkButton() {
		// TODO 自动生成方法存根
		super.initWorkButton();
		actionUnAudit.setEnabled(true);
		actionAudit.setEnabled(true);

		// 修改图标
		/*
		 * actionCostSplit.putValue(Action.SMALL_ICON,
		 * EASResource.getIcon("imgTbtn_disassemble"));
		 * this.btnCostSplit.setIcon(EASResource.getIcon("imgTbtn_disassemble"));
		 */
		actionCostSplit.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_split"));

		this.btnProjectAttachment.setIcon(EASResource
				.getIcon("imgTbtn_createcredence"));

		menuItemCostSplit.setAccelerator(KeyStroke.getKeyStroke("alt shift S"));
		// TODO 审核、反审核
		/*
		 * btnAudit.setVisible(false); btnUnAudit.setVisible(false);
		 */
		
		btnAuditResult.setVisible(false);
		btnAuditResult.setEnabled(false);
		btnMultiapprove.setVisible(false);
		btnMultiapprove.setEnabled(false);
		btnNextPerson.setVisible(false);
		btnNextPerson.setEnabled(false);
		actionNextPerson.setVisible(false);
		actionNextPerson.setEnabled(false);
		actionMultiapprove.setVisible(false);
		actionMultiapprove.setEnabled(false);
		actionAuditResult.setVisible(false);
		actionAuditResult.setEnabled(false);
		
		//工作流菜单暂时隐藏
		actionWorkFlowG.setVisible(false);
		actionWorkFlowG.setEnabled(false);
		actionWorkflowList.setVisible(false);
		actionWorkflowList.setEnabled(false);
		actionMultiapprove.setVisible(false);
		actionMultiapprove.setEnabled(false);
		actionNextPerson.setVisible(false);
		actionNextPerson.setEnabled(false);
		actionAuditResult.setVisible(false);
		actionAuditResult.setEnabled(false);
	}

	/**
	 * 返回工程项目在ListUI Query中对应的主实体的工程项目关联属性的路径，子类必须实现 在treeSelectChange()内调用
	 * 
	 * @author sxhong Date 2006-11-13
	 * @return
	 */
	protected String getCurProjectPath() {
		return null;
	}

	/**
	 * 描述：当左边的树选择变化时的缺省条件（提供默认实现，合同状态为审核，子类可以覆盖，如果没有条件，也要返回一个new)
	 * 
	 * @author sxhong Date 2006-11-13
	 * @return
	 * @see com.kingdee.eas.fdc.basedata.client.ProjectTreeListBaseUI#getTreeSelectChangeFilter()
	 */
	protected FilterInfo getTreeSelectChangeFilter() {
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		filterItems.add(new FilterItemInfo(getCurProjectPath() + ".isEnabled",
				Boolean.TRUE));
		return filter;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.kingdee.eas.fdc.basedata.client.ProjectTreeListBaseUI#getBillStateForEditOrRemove()
	 */
	protected String[] getBillStateForEditOrRemove() {
		// TODO 自动生成方法存根
		return super.getBillStateForEditOrRemove();
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.kingdee.eas.fdc.basedata.client.ProjectTreeListBaseUI#getStateForAudit()
	 */
	protected String getStateForAudit() {
		// TODO 自动生成方法存根
		// return super.getStateForAudit();
		return FDCBillStateEnum.SAVED_VALUE;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.kingdee.eas.fdc.basedata.client.ProjectTreeListBaseUI#getStateForUnAudit()
	 */
	protected String getStateForUnAudit() {
		// TODO 自动生成方法存根
		return super.getStateForUnAudit();
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillListUI#actionImportData_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionImportData_actionPerformed(ActionEvent e)
			throws Exception {
		// TODO 自动生成方法存根
		super.actionImportData_actionPerformed(e);
	}

	/**
	 * 在点拆分及双击的时候查看，以确定是新增拆分单据还是修改拆分单据<br>
	 * 默认实现只从序时薄的字段进行判断，子类要覆盖以提供完整的逻辑
	 * 
	 * @author sxhong Date 2006-11-16
	 * @return isAddNew true 新增单据 false 修改单据
	 */
	protected boolean checkBeforeSplit() throws Exception {
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

	/**
	 * 绘制颜色板
	 * 
	 * @author sxhong Date 2006-11-23
	 */
	protected void drawColorPanel() {
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		flowLayout.setHgap(10);
		// 将每个颜色放到一个Lable内
		colorPanel.setLayout(flowLayout);
		// drawALogo("",null);//调整距离用
		drawALogo(FDCSplitClientHelper.getRes("allSplitState"),
				FDCSplitClientHelper.COLOR_ALLSPLIT);
		if (this instanceof ContractCostSplitListUI) {
			drawALogo(FDCSplitClientHelper.getRes("partSplitState"),
					FDCSplitClientHelper.COLOR_PARTSPLIT);
		}
		// drawALogo(FDCSplitClientHelper.getRes("auditNotSplit"),FDCSplitClientHelper.COLOR_AUDITTED);
		// drawALogo(FDCSplitClientHelper.getRes("notAudit"),FDCSplitClientHelper.COLOR_UNAUDITTED);
		drawALogo(FDCSplitClientHelper.getRes("notSubmmit"),
				FDCSplitClientHelper.COLOR_NOSPLIT);

	}

	/**
	 * 绘制颜色板内的一个Logo
	 * 
	 * @author sxhong Date 2006-11-23
	 * @param name
	 * @param color
	 */
	protected void drawALogo(String name, Color color) {
		if (color == null) {
			color = colorPanel.getBackground();
		}
		KDLabel lable = new KDLabel(name);
		KDLabel colorLable = new KDLabel();
		Dimension d = new Dimension(40, 10);
		colorLable.setPreferredSize(d);
		colorLable.setOpaque(true);
		colorLable.setBackground(color);
		colorPanel.add(lable);
		colorPanel.add(colorLable);

	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		// checkSplitState();
		checkBeforeRemove();
		checkImp();
		super.actionRemove_actionPerformed(e);
	}

	protected void checkImp() throws Exception {
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		int i = 0;
		for (i = 0; i < selectRows.length; i++) {
			String billId = getMainTable().getCell(selectRows[i],
					getCostBillIdFieldName()).getValue().toString();
			// EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();

			// 如果合同拆分删除还要判断是否已经生成过付款申请单
			if (PayReqUtils.isContractBill(billId)) {
				filter.getFilterItems().add(
						new FilterItemInfo("contractId", billId));

				// 该合同已经存在付款申请单，不能进行此操作
				boolean hasPayRequest = PayRequestBillFactory
						.getRemoteInstance().exists(filter);
				//最新需求，存在付款申请单也能删除
//				if (hasPayRequest) {
//					MsgBox.showError(this, FDCSplitClientHelper
//							.getRes("hasPayRequest"));
//					SysUtil.abort();
//				}
			}

			filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("costBillId", billId));
			filter.getFilterItems().add(
					new FilterItemInfo("parent.state",
							FDCBillStateEnum.INVALID_VALUE,
							CompareType.NOTEQUALS));

			// view.setFilter(filter);
			// AbstractObjectCollection coll = null;
			// AbstractObjectCollection collPay = null;
			// coll = SettlementCostSplitEntryFactory.getRemoteInstance()
			// .getSettlementCostSplitEntryCollection(view);
			// collPay = PaymentSplitEntryFactory.getRemoteInstance()
			// .getPaymentSplitEntryCollection(view);

			boolean hasSettlementCostSplit = SettlementCostSplitEntryFactory
					.getRemoteInstance().exists(filter);
			if (hasSettlementCostSplit) {
				MsgBox
						.showError(this, FDCSplitClientHelper
								.getRes("impBySett"));
				SysUtil.abort();
			}

			boolean hasPaymentSplit = PaymentSplitEntryFactory
					.getRemoteInstance().exists(filter);
			if (hasPaymentSplit) {
				if(isWorkLoadSeparate()){
					MsgBox.showError(this, FDCSplitClientHelper.getRes("impByPay"));
					SysUtil.abort();
					
				}else{
					MsgBox.showError(this, "拆分已经被付款拆分引用，不能进行此操作!");
					SysUtil.abort();
				}
			}

			// PaymentNoCostSplitEntryCollection collection =
			// PaymentNoCostSplitEntryFactory
			// .getRemoteInstance().getPaymentNoCostSplitEntryCollection(
			// view);

			boolean hasPaymentNoCostSplit = PaymentNoCostSplitEntryFactory
					.getRemoteInstance().exists(filter);
			if(hasPaymentNoCostSplit){
				if(isWorkLoadSeparate()){
					MsgBox.showError(this, FDCSplitClientHelper.getRes("impByPay"));
					SysUtil.abort();
				}else{
					MsgBox.showError(this, "拆分已经被付款拆分引用，不能进行此操作!");
					SysUtil.abort();
				}
			}
		}
	}

	protected void checkSplitState() throws Exception {
		checkSelected();
		boolean isNotSplit = false;
		String keyValue = getSelectedKeyValue();
		if (keyValue == null || keyValue.equals(splitBillNullID.toString())) {
			isNotSplit = true;
		}
		if (isNotSplit) {
			MsgBox.showWarning(this, FDCSplitClientHelper.getRes("noView"));
			SysUtil.abort();
		}
	}

	protected void filterByBillState(EntityViewInfo ev) {
		FilterInfo newFilter = new FilterInfo();
		// 不显示作废的拆分单
		// newFilter.getFilterItems().add(new FilterItemInfo("costSplit.state",
		// FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));

		Set set = new HashSet(3);
		set.add(FDCBillStateEnum.SAVED_VALUE);
		//R111215-0464 过滤出已审批和终止状态单据 added by andy_liu 2012-6-28
		//set.add(FDCBillStateEnum.CANCEL_VALUE);
		//过滤掉作废状态单据  added by andy_liu 2012-8-27
		set.add(FDCBillStateEnum.INVALID_VALUE);
		
		if(!canSplitSubmit()){//如果不允许拆分提交状态的单据 by Cassiel_peng
				set.add(FDCBillStateEnum.SUBMITTED_VALUE);
				set.add(FDCBillStateEnum.AUDITTING_VALUE);
		}
		if (this instanceof ContractCostSplitListUI) {
			newFilter.getFilterItems().add(
					new FilterItemInfo("state", set, CompareType.NOTINCLUDE));
			newFilter.appendFilterItem("isAmtWithoutCost", Boolean.FALSE);
		} else if (this instanceof ConChangeSplitListUI) {
			newFilter.getFilterItems().add(
					new FilterItemInfo("state", set, CompareType.NOTINCLUDE));
			newFilter.appendFilterItem("contractBill.isAmtWithoutCost",
					Boolean.FALSE);

		} else if (this instanceof PaymentSplitListUI) {
			
			//付款拆分的单据状态与"合同拆分"、"变更拆分"、"结算拆分"引用的对象不一样   by Cassiel_peng
			if(!canSplitSubmit()){
				newFilter.getFilterItems().add(new FilterItemInfo("billStatus",new Integer(
											com.kingdee.eas.fi.cas.BillStatusEnum.AUDITED_VALUE),
									CompareType.GREATER_EQUALS));
			}else{
				newFilter.getFilterItems().add(new FilterItemInfo("billStatus",new Integer(
											com.kingdee.eas.fi.cas.BillStatusEnum.SUBMIT_VALUE),
									CompareType.GREATER_EQUALS));
			}
			if(checkpaymentallsplit()){
				newFilter.getFilterItems().clear();
				newFilter.getFilterItems().add(
						new FilterItemInfo("billStatus", new Integer(com.kingdee.eas.fi.cas.BillStatusEnum.SAVE_VALUE),
								CompareType.GREATER_EQUALS));
			}
		} else if (this instanceof SettlementCostSplitListUI) {
			newFilter.getFilterItems().add(
					new FilterItemInfo("state", set, CompareType.NOTINCLUDE));
			newFilter.appendFilterItem("contractBill.isAmtWithoutCost",
					Boolean.FALSE);

		}

		try {
			if (ev.getFilter() == null) {
				ev.setFilter(newFilter);
			} else {
				ev.getFilter().mergeFilter(newFilter, "and");
			}
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}

	/**
	 * 
	 * 描述：检查单据状态
	 * 
	 * @param state
	 *            状态
	 * @param res
	 *            提示信息资源名称
	 * @throws BOSException
	 * @author:liupd 创建时间：2006-7-27
	 *               <p>
	 */
	protected void checkBillState(String state, String res) throws Exception {
		List idList = ContractClientUtils.getSelectedIdValues(getMainTable(),
				getKeyFieldName());

		Set idSet = ContractClientUtils.listToSet(idList);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("state");
		CoreBaseCollection coll = getRemoteInterface().getCollection(view);

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			CoreBaseInfo element = (CoreBaseInfo) iter.next();

			// 检查单据是否在工作流中
			FDCClientUtils
					.checkBillInWorkflow(this, element.getId().toString());

//			if (!element.getString(getBillStatePropertyName()).equals(state)) {
			//				MsgBox.showWarning(this, FDCSplitClientHelper.getRes(res));
			//				abort();
			//			}

		}
	}

	public void actionAttachment_actionPerformed(ActionEvent e)
			throws Exception {
		// super.actionAttachment_actionPerformed(e);
		boolean isEdit = false;
		AttachmentClientManager acm = AttachmentManagerFactory
				.getClientManager();
		String boID = this.getSelectedKeyValue();
		checkSelected();
		if (boID == null) {
			return;
		}

		int rowIdx = getMainTable().getSelectManager().getActiveRowIndex();
		ICell cell = getMainTable().getCell(rowIdx, "costSplit.state");
		Object obj = cell.getValue();
		if (obj == null)
			return;// 未拆分的没有附件管理
		if (obj.equals(FDCBillStateEnum.SAVED.toString())
				|| obj.equals(FDCBillStateEnum.SUBMITTED.toString())
				|| obj.equals(FDCBillStateEnum.AUDITTING.toString())) {
			isEdit = true;
		} else {
			isEdit = false;
		}

		acm.showAttachmentListUIByBoID(boID, this, isEdit); // boID 是 与附件关联的
		// 业务对象的 ID

	}

	public void onLoad() throws Exception {
		//不支持界面配置
		ConfigServiceUtils.saveUserConfigData("HeadView", this, null);
		tblMain.addKDTDataFillListener(new KDTDataFillListener() {
			public void afterDataFill(KDTDataRequestEvent e) {
				tblMain_tableAfterDataFill(e);
			}
		});
		super.onLoad();
	}
	
	protected void tblMain_tableAfterDataFill(KDTDataRequestEvent e) {
		int start = e.getFirstRow();
		int end = e.getLastRow();
		setSplitStateColor(start, end);

	}

	protected void updateButtonStatus() {
		super.updateButtonStatus();
		// 做快捷键屏蔽 by sxhong
		actionAddNew.setEnabled(false);
	}

	protected void initTable() {

	}
	
	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		super.actionQuery_actionPerformed(e);
	}
	
	protected boolean isAdjustVourcherMode=false;
	protected boolean isSimpleFinacialMode=false;
	protected boolean isSimpleFinacialExtendMode=false;
	/**
	 * 工程量与付款分离
	 */
	protected boolean isWorkLoadSeparate=false;
	protected  void fetchInitData() throws Exception{		
		super.fetchInitData();
		String compayId = company.getId().toString();
		HashMap paramMap = FDCUtils.getDefaultFDCParam(null, compayId);
		isAdjustVourcherMode=FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_ADJUSTVOURCHER);
		isSimpleFinacialMode = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_SIMPLEFINACIAL);
		isSimpleFinacialExtendMode = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_SIMPLEFINACIALEXTEND);
		isFinacial = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_FINACIAL);
//		isWorkLoadSeparate = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT);
		isMoreSetter=FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_MORESETTER);
		//成本级参数
		String orgUnitId = orgUnit.getId().toString();
		checkPaymentAllSplit = FDCUtils.getBooleanValue4FDCParamByKey(null, orgUnitId, FDCConstants.FDC_PARAM_CHECKPAYMENTALLSPLIT);
	}
	
	protected boolean checkPaymentAllSplit = false;
	protected boolean checkpaymentallsplit(){
		return checkPaymentAllSplit;
	}
	/**
	 * 是否启用了调整凭证模式
	 * @return
	 */
	protected boolean isAdjustVourcherModel(){
		return isAdjustVourcherMode;
	}

	protected boolean isSimpleFinacialExtendMode() {
		return isSimpleFinacialExtendMode;
	}

	protected boolean isSimpleFinacialMode() {
		return isSimpleFinacialMode;
	}
	
	protected boolean isWorkLoadSeparate(){
		//modified by ken_liu..
		/*R140612-0098  工程量拆分，以前isWorkLoadSeparate参数的获取是在super.onload里执行的，super.onload()放在参数判断之前。
		但因license的扣减在coreUI里执行。。又要把参数判断放在onload之后，所以在引用判断的时候重新获取，有好有坏，先这样吧*/
		String companyID = SysContext.getSysContext().getCurrentFIUnit().getId().toString();
		try {
			isWorkLoadSeparate = FDCUtils.getBooleanValue4FDCParamByKey(null, companyID, FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT);
		} catch (Exception e) {
			this.handUIExceptionAndAbort(e);
		} 
		return isWorkLoadSeparate;
	}
	
	protected boolean isFinacialModel() {
		return isFinacial;
	}
	/**
	 * 多次结算
	 */
	private boolean isMoreSetter = false;
	protected boolean isMoreSetter(){
		return isMoreSetter;
	}
	protected boolean isFinacialModel(String projectID) {
		if (initParam.get(FDCConstants.FDC_PARAM_FINACIAL + projectID) != null) {
			return Boolean.valueOf(
					initParam.get(FDCConstants.FDC_PARAM_FINACIAL + projectID)
							.toString()).booleanValue();
		}
		return false;
	}

	protected boolean isAdjustVourcherModel(String projectID) {
		if (initParam.get(FDCConstants.FDC_PARAM_ADJUSTVOURCHER + projectID) != null) {
			return Boolean.valueOf(
					initParam.get(
							FDCConstants.FDC_PARAM_ADJUSTVOURCHER + projectID)
							.toString()).booleanValue();
		}
		return false;
	}

	protected boolean isSimpleFinacialExtendMode(String projectID) {
		if (initParam.get(FDCConstants.FDC_PARAM_SIMPLEFINACIALEXTEND
				+ projectID) != null) {
			return Boolean.valueOf(
					initParam.get(
							FDCConstants.FDC_PARAM_SIMPLEFINACIALEXTEND
									+ projectID).toString()).booleanValue();
		}
		return false;
	}

	protected boolean isSimpleFinacialMode(String projectID) {
		if (initParam.get(FDCConstants.FDC_PARAM_SIMPLEFINACIAL + projectID) != null) {
			return Boolean.valueOf(
					initParam.get(
							FDCConstants.FDC_PARAM_SIMPLEFINACIAL + projectID)
							.toString()).booleanValue();
		}
		return false;
	}

	protected boolean isWorkLoadSeparate(String projectID) {
		if (initParam.get(FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT
				+ projectID) != null) {
			return Boolean.valueOf(
					initParam.get(
							FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT
									+ projectID).toString()).booleanValue();
		}
		return false;
	}
}