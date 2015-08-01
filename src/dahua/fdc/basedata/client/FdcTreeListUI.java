/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.KeyStroke;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDTreeView;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public abstract class FdcTreeListUI extends AbstractFdcTreeListUI {
	
	private static final Logger logger = CoreUIObject.getLogger(FdcTreeListUI.class);

	protected Set authorizedOrgs = null;

	/**
	 * output class constructor
	 */
	public FdcTreeListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeAddNew(e);

		super.actionAddNew_actionPerformed(e);
	}

	protected void checkBeforeAddNew(ActionEvent e) {
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeEdit(e);

		super.actionEdit_actionPerformed(e);
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeRemove();

		super.actionRemove_actionPerformed(e);
	}

	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		super.actionQuery_actionPerformed(e);
	}

	/**
	 * 删除操作。业务可重载。
	 * 
	 * @throws Exception
	 * @throws EASBizException
	 */
	protected void Remove() throws Exception {
		removeBill();
	}

	/**
	 * 批量删除
	 * 
	 * @throws Exception
	 * @throws EASBizException
	 */
	protected void removeBill() throws Exception {
		int[] selectRows = KDTableUtil.getSelectedRows(getMainTable());
		IObjectPK[] arrayPK = new ObjectUuidPK[selectRows.length];

		for (int i = 0; i < selectRows.length; i++) {
			String id = (String) getMainTable().getCell(selectRows[i], getKeyFieldName()).getValue();
			checkRef(id);
			arrayPK[i] = new ObjectUuidPK(id);

			try {
				this.setOprtState("REMOVE");
				this.pubFireVOChangeListener(arrayPK[i].toString());
			} catch (Throwable ex) {
				this.handUIException(ex);
				SysUtil.abort();
			}
		}
		getRemoteInterface().delete(arrayPK);

		showOprtOKMsgAndRefresh();
	}

	/**
	 * output getEditUIName method
	 */
	protected String getEditUIName() {
		return null;
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception {
		return getRemoteInterface();
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		return null;
	}

	/**
	 * 
	 * 描述：为当前单据的新增、编辑、查看准备Context
	 * 
	 * @author:liupd
	 * @see com.kingdee.eas.framework.client.CoreBillListUI#prepareUIContext(com.kingdee.eas.common.client.UIContext,
	 *      java.awt.event.ActionEvent)
	 */
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		ItemAction act = getActionFromActionEvent(e);

		// //要传树叶子节点的Id集合，用于编辑界面树的F7选择过滤
		if (act.equals(actionAddNew)) {
			// 把当前选中的工程项目传给EditUI
			Object userObject2 = getProjSelectedTreeNode().getUserObject();
			if (userObject2 instanceof CurProjectInfo) {
				BOSUuid projId = ((CurProjectInfo) userObject2).getId();
				uiContext.put("projectId", projId);
			}
		} else {
			try {
				Object selectObj = getBizInterface().getValue(new ObjectUuidPK(getSelectedKeyValue().toString()));
				if (selectObj instanceof FDCBillInfo) {
					FDCBillInfo billInfo = (FDCBillInfo) selectObj;
					if (billInfo.get("curProject") instanceof CurProjectInfo) {
						BOSUuid projId = ((CurProjectInfo) billInfo.get("curProject")).getId();
						uiContext.put("projectId", projId);
					}
				}
			} catch (Exception e1) {
			}
		}
		
		Set leafNodesIdSet = new HashSet();
		TreeNode projRoot = (TreeNode) treeMain.getModel().getRoot();
		FDCClientUtils.genProjLeafNodesIdSet(projRoot, leafNodesIdSet);
		uiContext.put("projLeafNodesIdSet", leafNodesIdSet);
	}

	/**
	 * 
	 * 描述：初始化表格
	 * 
	 * @author:liupd 创建时间：2006-8-3
	 *               <p>
	 */
	protected void initTable() {
		super.initTable();

	}

	public void onLoad() throws Exception {
		super.onLoad();
		
		// 初始化树
		initTree();

		// 初始化表格
		initTable();

		// 冻结表列
		freezeTableColumn();

		// 更新按钮状态
		updateButtonStatus();

		// 初始化表格帮助类
		initTableHelper();

		// ////////////////////////////////////////////////////////////
		// ////////////////////////////////////////////////////////////

		// 设置选中根结点
		treeMain.setSelectionRow(0);
		treeMain.expandRow(0);
	}

	/**
	 * 初始化树
	 * 
	 * @throws Exception
	 */
	protected void initTree() throws Exception {
		KDTreeView treeView = new KDTreeView();
		treeView.setTree(treeMain);
		treeView.setShowButton(false);
		treeView.setTitle("组织");
		kDSplitPane1.add(treeView, "left");
		treeView.setShowControlPanel(true);
		
		buildTree();
	}

	/**
	 * private方法修改为public方法，子类可以修改树的构建方式 原因：进度管理中工程项目的构建取当前组织上级财务组织下的所有工程项目，默认取的当前组织下的工程项目
	 * 
	 * @throws Exception
	 */
	public void buildTree() throws Exception {
		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();

		projectTreeBuilder.build(this, treeMain, actionOnLoad);

		treeMain.setShowsRootHandles(true);
	}

	// 更新按钮状态
	protected void updateButtonStatus() {
		super.updateButtonStatus();

		// /////////////////////////////////////////////////////////////////////////

		// if (!checkAddNewEditRemovebyCostUnitBizUnit()) {
		// return;
		// }
		// 如果是虚体成本中心，则不能增、删、改
		// if (!SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit()) {
		// actionAddNew.setEnabled(false);
		// actionEdit.setEnabled(false);
		// actionRemove.setEnabled(false);
		// actionAddNew.setVisible(false);
		// actionEdit.setVisible(false);
		// actionRemove.setVisible(false);
		// menuEdit.setVisible(false);
		// }
	}

	/**
	 * 初始化表格帮助类
	 */
	protected void initTableHelper() {
		// 60中
		// tHelper = new TablePreferencesHelper(this);

		// 53中用此类
		// tHelper = new TableCoreuiPreferenceHelper(this);
	}

	protected boolean checkAddNewEditRemovebyCostUnitBizUnit() {
		return true;
	}

	// 数据对象变化时，刷新界面状态
	protected void setActionState() {
		super.setActionState();
		updateButtonStatus();
	}

	/**
	 * 单据编辑界面默认是新开窗口方式打开
	 * 
	 * @return
	 */
	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}

	/**
	 * 
	 * 描述：提示操作成功
	 * 
	 * @author:liupd 创建时间：2006-8-1
	 *               <p>
	 */
	protected void showOprtOKMsgAndRefresh() throws Exception {
		FDCClientUtils.showOprtOK(this);
		refreshList();
	}

	/**
	 * 
	 * 描述：按状态排序（按暂存、已提交、审批中，已审批的顺序排序） 已移到execQuery中，add by sxhong
	 * 
	 * @author:liupd
	 * @see com.kingdee.eas.framework.client.ListUI#beforeExcutQuery(com.kingdee.bos.metadata.entity.EntityViewInfo)
	 */
	protected void beforeExcutQuery(EntityViewInfo ev) {
		super.beforeExcutQuery(ev);
	}

	/**
	 * 调用getSorter方法用于设置排序状态
	 * 
	 * @author sxhong Date 2006-11-14
	 * @see com.kingdee.eas.framework.client.ListUI#execQuery()
	 */
	protected void execQuery() {
		// 设置排序状态
		EntityViewInfo ev = getMainQuery();

		// 查询时不使用默认排序
		if (ev.getSorter().size() == 0 || (ev.getSorter().size() == 1 && ev.getSorter().contains(new SorterItemInfo("id")))) {
			SorterItemCollection sorters = getSorter();
			if (sorters != null) {
				int i = 0;
				for (Iterator iter = sorters.iterator(); iter.hasNext();) {
					SorterItemInfo sorter = (SorterItemInfo) iter.next();
					if (!ev.getSorter().contains(sorter)) {
						ev.getSorter().addObject(i++, sorter); // 保证排序的优先性
					}
				}
			}

		}

		super.execQuery();
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		EntityViewInfo tempViewInfo = (EntityViewInfo) viewInfo.clone();
		// 合并查询条件
		try {
			FilterInfo filter = getTreeFilter();
			if (filter != null) {
				if (tempViewInfo.getFilter() != null) {
					tempViewInfo.getFilter().mergeFilter(filter, "and");
				} else {
					tempViewInfo.setFilter(filter);
				}
			}
			
			filterByBillState(tempViewInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		IQueryExecutor queryExecutor = super.getQueryExecutor(queryPK, tempViewInfo);
		
		// 打印查询执行器
		printQueryExecutor(queryExecutor);
		
		return queryExecutor;
    }
    
	/**
	 * 打印查询执行器
	 * 
	 * @param queryExecutor
	 */
	protected void printQueryExecutor(IQueryExecutor queryExecutor) {
		boolean flag = isPrintQueryExecutor(queryExecutor);
		if (!flag) {
			return;
		}

		try {
			String sql = queryExecutor.getSQL();
			EntityViewInfo entityViewInfo = queryExecutor.getObjectView();
			FilterInfo filterInfo = entityViewInfo.getFilter();
			
			logger.info("=================>FdcTreeListUI.getQueryExecutor(), sql:" + sql);
			logger.info("=================>FdcTreeListUI.getQueryExecutor(), filterInfo:" + filterInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 是否打印查询执行器
	 * 
	 * @param queryExecutor
	 */
	protected boolean isPrintQueryExecutor(IQueryExecutor queryExecutor) {
		return false;
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {

		super.treeMain_valueChanged(e);
		treeSelectChange();
	}

	/**
	 * 
	 * 描述：左边树选择改变，重新构造条件执行查询
	 * 
	 * @author:liupd 创建时间：2006-7-25
	 *               <p>
	 */
	protected void treeSelectChange() throws Exception {

		execQuery();

		if (getMainTable().getRowCount() > 0) {
			getMainTable().getSelectManager().select(0, 0);
		}
	}

	// 树过滤条件
	protected FilterInfo getTreeFilter() throws Exception {

		FilterInfo filter = getTreeSelectChangeFilter();
		FilterItemCollection filterItems = filter.getFilterItems();
		// 获得当前用户下的组织范围ids
		authorizedOrgs = (Set) ActionCache.get("ContractListBaseUIHandler.authorizedOrgs");
		if (authorizedOrgs == null) {
			authorizedOrgs = new HashSet();
			Map orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(
					new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId()), OrgType.CostCenter, null, null, null);
			if (orgs != null) {
				Set orgSet = orgs.keySet();
				Iterator it = orgSet.iterator();
				while (it.hasNext()) {
					authorizedOrgs.add(it.next());
				}
			}
		}
		/*
		 * 树
		 */
		if (getProjSelectedTreeNode() != null && getProjSelectedTreeNode().getUserObject() instanceof CoreBaseInfo) {

			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) getProjSelectedTreeNode().getUserObject();
			// 选择的是成本中心，取该成本中心及下级成本中心（如果有）下的所有合同
			if (projTreeNodeInfo instanceof OrgStructureInfo) {
				BOSUuid id = ((OrgStructureInfo) projTreeNodeInfo).getUnit().getId();

				String orgUnitLongNumber = null;
				if (orgUnit != null && id.toString().equals(orgUnit.getId().toString())) {
					orgUnitLongNumber = orgUnit.getLongNumber();
				} else {
					FullOrgUnitInfo orgUnitInfo = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(id));
					orgUnitLongNumber = orgUnitInfo.getLongNumber();
				}

				filterItems.add(new FilterItemInfo("orgUnit.longNumber", orgUnitLongNumber + "%", CompareType.LIKE));

				filterItems.add(new FilterItemInfo("orgUnit.isCostOrgUnit", Boolean.TRUE));

				// Set idSet = FDCClientUtils.genOrgUnitIdSet(id);
				// filterItems.add(new FilterItemInfo("orgUnit.id", idSet,
				// CompareType.INCLUDE));
				//			
				// filterItems.add(new FilterItemInfo("curProject.fullOrgUnit.id", idSet,
				// CompareType.INCLUDE));
				filterItems.add(new FilterItemInfo("orgUnit.id", authorizedOrgs, CompareType.INCLUDE));
			}
			// if (projTreeNodeInfo instanceof OrgStructureInfo) {
			// filterItems.add(new FilterItemInfo("orgUnit.id", authorizedOrgs,
			// CompareType.INCLUDE));
			// filterItems.add(new FilterItemInfo("curProject.fullOrgUnit.id", authorizedOrgs,
			// CompareType.INCLUDE));
			// }
			// 选择的是项目，取该项目及下级项目（如果有）下的所有合同
			else if (projTreeNodeInfo instanceof CurProjectInfo) {
				BOSUuid id = projTreeNodeInfo.getId();
				Set idSet = FDCClientUtils.genProjectIdSet(id);
				
				String curProjectPropertyName = getCurProjectPropertyName();
				String filterPropertyName = curProjectPropertyName + ".id";
				filterItems.add(new FilterItemInfo(filterPropertyName, idSet, CompareType.INCLUDE));
			}

		}

		return filter;
	}
	
	/**
	 * 描述：取得"组织"属性字段名称，子类可以重写
	 * 
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-7-24
	 */
	protected String getOrgPropertyName() {
		return "orgUnit";
	}

	/**
	 * 描述：取得"当前工程项目"属性字段名称，子类可以重写
	 * 
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-7-24
	 */
	protected String getCurProjectPropertyName() {
		return "curProject";
	}

	public DefaultKingdeeTreeNode getProjSelectedTreeNode() {
		return (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
	}

	/**
	 * 
	 * 描述：冻结表列
	 * 
	 * @author:liupd 创建时间：2006-7-25
	 *               <p>
	 */
	protected void freezeTableColumn() {

	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		checkBillState(getStateForAudit(), "selectRightRowForAudit");
		audit(ContractClientUtils.getSelectedIdValues(getMainTable(), getKeyFieldName()));
		showOprtOKMsgAndRefresh();
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		checkBillState(getStateForUnAudit(), "selectRightRowForUnAudit");
		List selectedIdValues = ContractClientUtils.getSelectedIdValues(getMainTable(), getKeyFieldName());
		// 检查引用
		for (Iterator iter = selectedIdValues.iterator(); iter.hasNext();) {
			String id = (String) iter.next();
			checkRef(id);
		}

		unAudit(selectedIdValues);
		showOprtOKMsgAndRefresh();
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
		List idList = ContractClientUtils.getSelectedIdValues(getMainTable(), getKeyFieldName());

		Set idSet = ContractClientUtils.listToSet(idList);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("state");
		CoreBaseCollection coll = getRemoteInterface().getCollection(view);

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			CoreBaseInfo element = (CoreBaseInfo) iter.next();

			// 检查单据是否在工作流中
			FDCClientUtils.checkBillInWorkflow(this, element.getId().toString());

			if (!element.getString(getBillStatePropertyName()).equals(state)) {
				MsgBox.showWarning(this, ContractClientUtils.getRes(res));
				abort();
			}

		}
	}

	/**
	 * 
	 * 描述：审核操作的单据前置状态
	 * 
	 * @return
	 * @author:liupd 创建时间：2006-8-1
	 *               <p>
	 */
	protected String getStateForAudit() {
		return FDCBillStateEnum.SUBMITTED_VALUE;
	}

	/**
	 * 
	 * 描述：反审核操作的单据前置状态
	 * 
	 * @return
	 * @author:liupd 创建时间：2006-8-1
	 *               <p>
	 */
	protected String getStateForUnAudit() {
		return FDCBillStateEnum.AUDITTED_VALUE;
	}

	/**
	 * 
	 * 描述：单据状态属性名称，基类提供缺省实现
	 * 
	 * @return
	 * @author:liupd 创建时间：2006-8-26
	 *               <p>
	 */
	protected String getBillStatePropertyName() {
		return "state";
	}

	/**
	 * 
	 * 描述：返回远程接口（子类必须实现）
	 * 
	 * @return
	 * @throws BOSException
	 * @author:liupd 创建时间：2006-8-1
	 *               <p>
	 */
	protected abstract ICoreBase getRemoteInterface() throws BOSException;

	/**
	 * 
	 * 描述：审核通过（子类必须实现，调用服务器端打审核标志的方法即可）
	 * 
	 * @param ids
	 * @throws Exception
	 * @author:liupd 创建时间：2006-8-1
	 *               <p>
	 */
	protected abstract void audit(List ids) throws Exception;

	/**
	 * 
	 * 描述：反审核（子类必须实现，调用服务器端打反审核标志的方法即可）
	 * 
	 * @param ids
	 * @throws Exception
	 * @author:liupd 创建时间：2006-8-1
	 *               <p>
	 */
	protected abstract void unAudit(List ids) throws Exception;

	/**
	 * 
	 * 描述：修改前检查
	 * 
	 * @author:liupd 创建时间：2006-8-26
	 *               <p>
	 * @throws Exception
	 */
	protected void checkBeforeEdit(ActionEvent e) throws Exception {
		checkSelected();

		CoreBaseInfo billInfo = getRemoteInterface().getValue(new ObjectUuidPK(getSelectedKeyValue()));
		String billState = billInfo.getString(getBillStatePropertyName());
		String[] states = getBillStateForEditOrRemove();
		boolean pass = false;
		for (int i = 0; i < states.length; i++) {
			if (billState.equals(states[i])) {
				pass = true;
			}
		}
		if (!pass) {
			MsgBox.showWarning(this, ContractClientUtils.getRes("cantEdit"));
			SysUtil.abort();
		}
	}

	/**
	 * 
	 * 描述：单据可修改、删除的状态
	 * 
	 * @return
	 * @author:liupd 创建时间：2006-8-26
	 *               <p>
	 */
	protected String[] getBillStateForEditOrRemove() {
		return new String[] { FDCBillStateEnum.SAVED_VALUE, FDCBillStateEnum.SUBMITTED_VALUE };
	}

	/**
	 * 
	 * 描述：（批量）删除前检查
	 * 
	 * @author:liupd 创建时间：2006-8-26
	 *               <p>
	 * @throws Exception
	 */
	protected void checkBeforeRemove() throws Exception {
		checkSelected();

		List idList = ContractClientUtils.getSelectedIdValues(getMainTable(), getKeyFieldName());

		Set idSet = ContractClientUtils.listToSet(idList);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add(getBillStatePropertyName());
		CoreBaseCollection coll = getRemoteInterface().getCollection(view);

		String[] states = getBillStateForEditOrRemove();

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			CoreBillBaseInfo element = (CoreBillBaseInfo) iter.next();
			String billState = element.getString(getBillStatePropertyName());
			boolean pass = false;
			for (int i = 0; i < states.length; i++) {
				if (billState.equals(states[i])) {
					pass = true;
				}
			}
			if (!pass) {
				MsgBox.showWarning(this, ContractClientUtils.getRes("cantRemove"));
				SysUtil.abort();
			}
		}
	}

	/**
	 * 
	 * 描述：当左边的树选择变化时的缺省条件（提供默认实现，合同状态为审核，子类可以覆盖，如果没有条件，也要返回一个new FilterInfo()，不能直接返回null）
	 * 
	 * @return
	 * @author:liupd 创建时间：2006-9-5
	 *               <p>
	 */
	protected FilterInfo getTreeSelectChangeFilter() {
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		
		String curProjectPropertyName = getCurProjectPropertyName();
		String filterPropertyName = curProjectPropertyName + ".isEnabled";
		filterItems.add(new FilterItemInfo(filterPropertyName, Boolean.TRUE));
		
		return filter;
	}

	protected void initWorkButton() {
		super.initWorkButton();
		btnAudit.setIcon(FDCClientHelper.ICON_AUDIT);
		btnUnAudit.setIcon(FDCClientHelper.ICON_UNAUDIT);
		menuItemAudit.setIcon(FDCClientHelper.ICON_AUDIT);
		menuItemUnAudit.setIcon(FDCClientHelper.ICON_UNAUDIT);
		menuItemAudit.setAccelerator(KeyStroke.getKeyStroke("ctrl U"));
		actionAudit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl U"));
		menuItemAudit.setText(menuItemAudit.getText().replaceAll("\\(A\\)", "") + "(A)");
		menuItemAudit.setMnemonic('A');

		actionAudit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift U"));
		menuItemUnAudit.setText(menuItemUnAudit.getText().replaceAll("\\(U\\)", "") + "(U)");
		menuItemUnAudit.setAccelerator(KeyStroke.getKeyStroke("ctrl shift U"));
		menuItemUnAudit.setMnemonic('U');
		
		actionAudit.setEnabled(true);
		actionUnAudit.setEnabled(true);
	}

	/**
	 * 
	 * 描述：检查是否有关联对象(删除前调用)
	 * 
	 * @author:liupd 创建时间：2006-8-26
	 *               <p>
	 */
	protected void checkRef(String id) {

	}

	/**
	 * 用于排序，子类可覆盖,出于合同拆分的需要 在execQuery中调用
	 * 
	 * @author sxhong Date 2006-11-10
	 * @return
	 */
	protected SorterItemCollection getSorter() {
		// SorterItemCollection sorter = new SorterItemCollection();
		// sorter.add(new SorterItemInfo("state"));
		// SorterItemInfo sorterItemInfo = new SorterItemInfo("bookedDate");
		// sorterItemInfo.setSortType(SortType.DESCEND);
		// sorter.add(sorterItemInfo);
		// return sorter;

		return null;
	}

	protected void filterByBillState(EntityViewInfo ev) {

	}

	/**
	 * 得到当前选择的对象工程项目,组织ID,或Null
	 * 
	 * @return 当前选择的对象工程项目,组织ID,或Null
	 */
	protected Object getSelectObj() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null || node.getUserObject() == null || OrgViewUtils.isTreeNodeDisable(node)) {
			return null;
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			return projectInfo;
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
			if (oui.getUnit() == null) {
				return null;
			}
			FullOrgUnitInfo info = oui.getUnit();
			return info;
		}
		return null;
	}

	protected String getSelectObjId() {
		Object obj = getSelectObj();
		if (obj != null) {
			return ((IObjectValue) obj).getString("id");
		}
		return null;
	}

	/**
	 * @author yong_zhou
	 * @return
	 */
	protected Set getSelectObjLeafIds() {
		Set idSet = new HashSet();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) this.treeMain.getLastSelectedPathComponent();
		if (node == null || node.getUserObject() == null || OrgViewUtils.isTreeNodeDisable(node)) {
			return idSet;
		}
		getNodeIds(node, idSet);
		return idSet;
	}

	protected void getNodeIds(DefaultKingdeeTreeNode node, Set idSet) {
		if (node.isLeaf()/* && node.getUserObject() instanceof CurProjectInfo */) {
			// CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			// idSet.add(projectInfo.getId().toString());
			if (node.getUserObject() instanceof CurProjectInfo) {
				CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
				idSet.add(projectInfo.getId().toString());
			} else if (node.getUserObject() instanceof FullOrgUnitInfo) {
				FullOrgUnitInfo orgUnitInfo = (FullOrgUnitInfo) node.getUserObject();
				idSet.add(orgUnitInfo.getId().toString());
			} else if (node.getUserObject() instanceof OrgStructureInfo) {
				OrgStructureInfo orgUnitInfo = (OrgStructureInfo) node.getUserObject();
				idSet.add(orgUnitInfo.getId().toString());
			}
		} else if (!node.isLeaf()) {
			for (int i = 0; i < node.getChildCount(); i++) {
				DefaultKingdeeTreeNode child = (DefaultKingdeeTreeNode) node.getChildAt(i);
				getNodeIds(child, idSet);
			}
		}
	}

	protected CurProjectInfo getSelectNodeInfo() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null || node.getUserObject() == null || OrgViewUtils.isTreeNodeDisable(node)) {
			return null;
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			return projectInfo;
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			return null;
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	protected boolean isSelectLeafNode() {
		Object obj = getSelectObj();
		if (obj != null && obj instanceof CurProjectInfo && ((CurProjectInfo) obj).isIsLeaf()) {
			return true;
		}
		return false;
	}

}