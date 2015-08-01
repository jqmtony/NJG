/*
 * @(#)FdcTreeBuilder.java
 * 
 * 金蝶国际软件集团有限公司版权所有
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.Color;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.tree.KingdeeTreeModel;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitCollection;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesCollection;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesFactory;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUCollection;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUFactory;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUInfo;
import com.kingdee.eas.fdc.basedata.util.FdcCollectionUtil;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.framework.client.tree.DefaultLNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ILNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ITreeBuilder;
import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 
 * 描述:根据当前组织构造工程项目树，实际上是： 同时具备财务组织和成本中心属性的组织树 ＋ 工程项目树
 * 
 * @author liupd date:2006-7-20
 *         <p>
 * @version EAS5.1.3
 */
public class FdcTreeBuilder {

	static final Logger logger = CoreUIObject.getLogger(FdcTreeBuilder.class);

	// parent UI
	private CoreUIObject ui;
	// 纯工程项目树
	private KDTree projTree = new KDTree();

	// internal var, a flag
	private boolean getIt = false;

	// 是否包含已禁用的工程项目
	private boolean isIncludeDisabled;

	// 不进行组织隔离？
	private boolean noOrgIsolation;

	// 是否包含产品类型
	private boolean isIncludeProductType;

	private String projectTypeId;

	// 获取有权限的组织
	protected Set authorizedOrgs = null;

	// 是否显示明细项目
	private boolean isViewLevf = true;

	/** 是否开发项目 */
	private boolean isDevPrjFilter = true;
	private Set projectIdSet = new HashSet();// 项目工程树ID合集

	/**
	 * 
	 * 描述：根据当前组织构造工程项目树（财务组织＋工程项目）
	 * 
	 * @param ui
	 *            要构造树的列表界面实例
	 * @param projectTree
	 *            要构造的KDTree
	 * @param actionOnload
	 *            列表界面的ActionOnLoad，用this.actionOnLoad调用
	 * @throws Exception
	 * @author:liupd 创建时间：2006-7-20
	 *               <p>
	 */
	public void build(CoreUI ui, KDTree projectTree, ItemAction actionOnLoad) throws Exception {
		build(ui, projectTree, actionOnLoad, null);
	}

	public void updateTreeNodeColor(KDTree projectTree) {
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) FdcOrgTreeHelper.getRoot(projectTree.getModel());
		visitAllNodes(root);
	}

	public void visitAllNodes(DefaultKingdeeTreeNode node) {
		if (node.getChildCount() >= 0) {
			for (Enumeration e = node.children(); e.hasMoreElements();) {
				DefaultKingdeeTreeNode n = (DefaultKingdeeTreeNode) e.nextElement();
				if (n.getUserObject() instanceof CurProjectInfo) {
					CurProjectInfo info = (CurProjectInfo) n.getUserObject();
					// Boolean.parseBoolean(info.get("hasAimCost").toString());
					if (info.get("hasAimCost") != null) {// NP
						boolean b = Boolean.valueOf(info.get("hasAimCost").toString()).booleanValue();
						if (!b && n.isLeaf()) {
							n.setTextColor(Color.RED);
						}
					}
				}
				visitAllNodes(n);
			}
		}
	}

	public void build(CoreUI ui, KDTree projectTree, ItemAction actionOnLoad, OrgUnitInfo currentOrgUnitInfo) throws Exception {
		long startTime;
		long endTime;
		long exeTime;

		long startTime_total;
		long endTime_total;
		long exeTime_total;

		startTime = System.currentTimeMillis();
		startTime_total = System.currentTimeMillis();

		// //////////////////////////////////////////////////////////////////////
		// //////////////////////////////////////////////////////////////////////

		// 添加树监听器
		addTreeListener(projectTree);

		// //////////////////////////////////////////////////////////////////////
		// //////////////////////////////////////////////////////////////////////
		
		this.ui = ui;

		// //////////////////////////////////////////////////////////////////////
		// //////////////////////////////////////////////////////////////////////

		startTime = System.currentTimeMillis();

		if (null == currentOrgUnitInfo) {
			currentOrgUnitInfo = SysContext.getSysContext().getCurrentCostUnit();
		}
		// 检查是否是成本中心
		FdcOrgTreeHelper.checkIsCostCenter(currentOrgUnitInfo, false);
		UserInfo currentUserInfo = SysContext.getSysContext().getCurrentUserInfo();

		// //////////////////////////////////////////////////////////////////////
		// ///

		// 生成所有角色组织范围
		// authorizedOrgs = FdcOrgTreeHelper.genAuthorizedOrgs();
		// 生成角色组织范围
		authorizedOrgs = FdcOrgTreeHelper.getAuthorizedOrgs(currentOrgUnitInfo, currentUserInfo);

		endTime = System.currentTimeMillis();
		exeTime = endTime - startTime;
		logger.info("2.1=================>FdcTreeBuilder.build(), FdcOrgTreeHelper.getAuthorizedOrgs(), exeTime:" + exeTime);

		// //////////////////////////////////////////////////////////////////////
		// ///

		// if (curOrgId == null && !noOrgIsolation) {
		// curOrgId =
		// SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
		// }
		// TreeModel orgTreeModel =
		// NewOrgUtils.getTreeModel(OrgViewType.COSTCENTER, "", curOrgId, null,
		// getActionPK(actionOnLoad));
		// DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode)
		// orgTreeModel.getRoot();

		buildByOrgTree(projectTree, currentOrgUnitInfo);

		endTime = System.currentTimeMillis();
		exeTime = endTime - startTime;
		logger.info("2.2=================>FdcTreeBuilder.build(), buildByOrgTree(), exeTime:" + exeTime);

		// //////////////////////////////////////////////////////////////////
		// //////////////////////////////////////////////////////////////////

		TreeModel orgTreeModel = projectTree.getModel();
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) FdcOrgTreeHelper.getRoot(orgTreeModel);

		if (root.getUserObject() != null) {
			// 检查是否是成本中心
			FdcOrgTreeHelper.checkIsCostCenter(root, false);

			// //////////////////////////////////////////////////////////////////

			startTime = System.currentTimeMillis();

			// /////////////////
			// 去掉没有成本中心组织属性的节点
			FdcOrgTreeHelper.removeNoneCostCenterNode(root);
			// /////////////////

			endTime = System.currentTimeMillis();
			exeTime = endTime - startTime;
			logger.info("2.3=================>FdcTreeBuilder.build(), removeNoneCostCenterNode(), exeTime: " + exeTime);

			startTime = System.currentTimeMillis();

			// /////////////////
			// 构建组织树
			buildByOrgTree(projectTree, orgTreeModel);
			// /////////////////

			endTime = System.currentTimeMillis();
			exeTime = endTime - startTime;

			logger.info("2.4=================>FdcTreeBuilder.build(), buildByOrgTree(projectTree, orgTreeModel), exeTime:" + exeTime);
		} else {
			startTime = System.currentTimeMillis();

			// /////////////////
			// 构建组织树
			buildByCurOrg(projectTree);
			// /////////////////

			endTime = System.currentTimeMillis();
			exeTime = endTime - startTime;

			logger.info("2.3=================>FdcTreeBuilder.build(), buildByCurOrg(projectTree), exeTime:" + exeTime);
		}

		endTime_total = System.currentTimeMillis();
		exeTime_total = endTime_total - startTime_total;

		logger.info("2.5=================>FdcTreeBuilder.build(), exeTime:" + exeTime_total);
	}

	/**
	 * 添加树监听器
	 * 
	 * @param projectTree
	 * @return
	 */
	private KDTree addTreeListener(KDTree projectTree) {
		final KDTree projTree = projectTree;
		projTree.addTreeWillExpandListener(new TreeWillExpandListener() {

			public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {

			}

			public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
				updateTreeNodeColor(projTree);
			}

		});
		return projTree;
	}

	/**
	 * 
	 * 描述：当前组织是成本中心
	 * 
	 * @param tree
	 * @throws Exception
	 * @author:liupd 创建时间：2006-9-14
	 *               <p>
	 */
	public void buildByCurOrg(KDTree orgTree) throws Exception {
		FilterInfo projFilter = getProjectFilterByCurOrg();
		if (projectTypeId != null) {
			projFilter.getFilterItems().add(new FilterItemInfo("projectType.id", projectTypeId));
		}
		buildProjTree(projFilter);

		TreeNode root = (TreeNode) FdcOrgTreeHelper.getRoot(projTree);
		if (root.getChildCount() == 0) {
			MsgBox.showWarning(ui, "无法构造工程项目树，请确认\"工程项目与成本中心的对应关系\"设置是否正确");
			SysUtil.abort();
		}
		TreeModel model = new DefaultTreeModel(root.getChildAt(0));
		orgTree.setModel(model);

	}

	/**
	 * 
	 * @return 构造获取当前组织下的工程项目的Filter
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public FilterInfo getProjectFilterByCurOrg() throws BOSException, EASBizException {
		// 组织ID
		String curCostId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("costCenterOU.id", curCostId));
		view.setFilter(filter);
		// 工程项目与成本中心对应关系
		ProjectWithCostCenterOUCollection relations = ProjectWithCostCenterOUFactory.getRemoteInstance().getProjectWithCostCenterOUCollection(view);
		if (relations == null || relations.size() == 0) {
			MsgBox.showWarning(FDCClientUtils.getRes("noRelativeProject"));
			SysUtil.abort();
		}
		// 项目ID
		String projId = relations.get(0).getCurProject().getId().toString();
		ObjectUuidPK projUuidPK = new ObjectUuidPK(projId);
		// 判断项目是否存在
		if (!CurProjectFactory.getRemoteInstance().exists(projUuidPK)) {
			MsgBox.showWarning(FDCClientUtils.getRes("noRelativeProject"));
			SysUtil.abort();
		}
		// 得到一个项目实体
		CurProjectInfo curProjectInfo = CurProjectFactory.getRemoteInstance().getCurProjectInfo(projUuidPK);
		// 是否启用
		if (!curProjectInfo.isIsEnabled()) {
			MsgBox.showWarning(FDCClientUtils.getRes("curOrgRelProjNotEnab"));
			SysUtil.abort();
		}
		// 项目的长编码
		String projNumber = curProjectInfo.getLongNumber();
		// Filter项目树构建条件
		FilterInfo projFilter = new FilterInfo();
		projFilter.getFilterItems().add(new FilterItemInfo("longnumber", projNumber + "%", CompareType.LIKE));

		String orgUnitId = SysContext.getSysContext().getCurrentFIUnit().getId().toString();
		// 判断合同拆分和变更拆分的项目树合集是否为空
		if (projectIdSet != null && projectIdSet.size() > 0) {
			projFilter.getFilterItems().add(new FilterItemInfo("id", projectIdSet, CompareType.INCLUDE));
		}
		projFilter.getFilterItems().add(new FilterItemInfo("fullOrgUnit", orgUnitId));
		if (!isIncludeDisabled) {
			projFilter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		}
		if (isDevPrjFilter) {
			projFilter.getFilterItems().add(new FilterItemInfo("isDevPrj", Boolean.TRUE));
		}
		// if (!isContainDevPrj) {
		// projFilter.getFilterItems().add(
		// new FilterItemInfo("isDevPrj", Boolean.FALSE));
		// }
		return projFilter;
	}

	public FdcTreeBuilder() {
		this.isIncludeDisabled = false;
		noOrgIsolation = false;
	}

	/**
	 * 
	 * 描述：构造函数
	 * 
	 * @param isViewLevf
	 *            是否显示明细项目，用于目标、动态成本分摊
	 * @param a
	 */
	public FdcTreeBuilder(boolean isViewLevf, String a) {
		this.isIncludeDisabled = false;
		noOrgIsolation = false;

		this.isViewLevf = isViewLevf;
	}

	/**
	 * 
	 * 描述：构造函数
	 * 
	 * @param isIncludeDisabled
	 *            是否包含已禁用的工程项目
	 * @author:liupd 创建时间：2006-9-14
	 *               <p>
	 */
	public FdcTreeBuilder(boolean isIncludeDisabled) {
		this.isIncludeDisabled = isIncludeDisabled;
		noOrgIsolation = false;
	}

	/**
	 * 
	 * 描述：构造函数
	 * 
	 * @param isIncludeDisabled
	 *            是否包含已禁用的工程项目
	 * @param noOrgIsolation
	 *            不进行组织隔离？ true － 不隔离，false － 隔离
	 * @author:liupd 创建时间：2006-9-14
	 *               <p>
	 */
	public FdcTreeBuilder(boolean isIncludeDisabled, boolean noOrgIsolation) {
		this.isIncludeDisabled = isIncludeDisabled;
		this.noOrgIsolation = noOrgIsolation;
	}

	/**
	 * 
	 * 描述：构造函数
	 * 
	 * @param isIncludeDisabled
	 *            是否包含已禁用的工程项目
	 * @param noOrgIsolation
	 *            不进行组织隔离？ true － 不隔离，false － 隔离
	 * @author:liupd 创建时间：2006-9-14
	 *               <p>
	 */
	public FdcTreeBuilder(boolean isIncludeDisabled, boolean noOrgIsolation, Set set) {
		this.isIncludeDisabled = isIncludeDisabled;
		this.noOrgIsolation = noOrgIsolation;
		this.projectIdSet = set;
	}

	/**
	 * 
	 * 描述：构造函数
	 * 
	 * @param isIncludeDisabled
	 *            是否包含已禁用的工程项目
	 * @param noOrgIsolation
	 *            不进行组织隔离？ true － 不隔离，false － 隔离
	 * @param isIncludeProductType
	 *            是否包含产品类型(工程项目下挂产品类型)
	 * @author:liupd 创建时间：2006-9-14
	 *               <p>
	 */
	public FdcTreeBuilder(boolean isIncludeDisabled, boolean noOrgIsolation, boolean isIncludeProductType) {
		this.isIncludeDisabled = isIncludeDisabled;
		this.noOrgIsolation = noOrgIsolation;
		this.isIncludeProductType = isIncludeProductType;
	}

	/**
	 * 
	 * 描述：构造函数
	 * 
	 * @param isIncludeDisabled
	 *            是否包含已禁用的工程项目
	 * @param noOrgIsolation
	 *            不进行组织隔离？ true － 不隔离，false － 隔离
	 * @param isIncludeProductType
	 *            是否包含产品类型(工程项目下挂产品类型)
	 * @author:liupd 创建时间：2006-9-14
	 *               <p>
	 */
	public FdcTreeBuilder(boolean isIncludeDisabled, boolean noOrgIsolation, boolean isIncludeProductType, String projectTypeId) {
		this.isIncludeDisabled = isIncludeDisabled;
		this.noOrgIsolation = noOrgIsolation;
		this.isIncludeProductType = isIncludeProductType;
		this.projectTypeId = projectTypeId;
	}

	/**
	 * 构建组织树
	 * <p>
	 * 对NewOrgUtils.getTreeModel(OrgViewType.COSTCENTER的改造
	 * 
	 * @param orgTree
	 * @throws Exception
	 */
	public void buildByOrgTree(KDTree orgTree, OrgUnitInfo currentOrgUnitInfo) throws Exception {
		long startTime = 0;
		long endTime = 0;
		long exeTime = 0;

		// //////////////////////////////////////////////////////////////////////
		// ///

		FilterInfo filterInfo = new FilterInfo();

		if (null == currentOrgUnitInfo) {
			currentOrgUnitInfo = SysContext.getSysContext().getCurrentCostUnit();
		}
		// 检查是否是成本中心
		FdcOrgTreeHelper.checkIsCostCenter(currentOrgUnitInfo, false);

		filterInfo.getFilterItems().add(new FilterItemInfo("longNumber", currentOrgUnitInfo.getLongNumber()));
		filterInfo.getFilterItems().add(new FilterItemInfo("longNumber", currentOrgUnitInfo.getLongNumber() + "!%", CompareType.LIKE));

		filterInfo.setMaskString("#0 or #1");
		startTime = System.currentTimeMillis();

		KDTree treeMain = orgTree;
		TreeSelectionListener[] listeners = treeMain.getTreeSelectionListeners();
		TreeSelectionListener treeSelectionListener = null;
		if (listeners.length > 0) {
			treeSelectionListener = listeners[0];
			treeMain.removeTreeSelectionListener(treeSelectionListener);
		}

		ITreeBase treeBase = CostCenterOrgUnitFactory.getRemoteInstance();
		ILNTreeNodeCtrl iLNTreeNodeCtrl = new DefaultLNTreeNodeCtrl(treeBase);

		SelectorItemCollection sic = getOrgTreeSic();

		startTime = System.currentTimeMillis();
		ITreeBuilder orgTreeBuilder = TreeBuilderFactory.createTreeBuilder(iLNTreeNodeCtrl, getTreeInitialLevel(), getTreeExpandLevel(),
				filterInfo, sic);
		endTime = System.currentTimeMillis();
		exeTime = endTime - startTime;

		logger.info("5=================>FdcTreeBuilder.buildByOrgTree(), createTreeBuilder, exeTime:" + +exeTime);

		startTime = System.currentTimeMillis();
		orgTreeBuilder.buildTree(orgTree);
		endTime = System.currentTimeMillis();
		exeTime = endTime - startTime;

		logger.info("5=================>FdcTreeBuilder.buildByOrgTree(), orgTreeBuilder.buildTree, exeTime:" + +exeTime);

		// 重新加上树选择事件
		treeMain.addTreeSelectionListener(treeSelectionListener);
		treeMain.setShowPopMenuDefaultItem(false);

		// //////////////////////////////////////////////////////////////////////
		// ///

		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) FdcOrgTreeHelper.getRoot(orgTree);

		startTime = System.currentTimeMillis();
		// 删除没有组织的项目
		FdcOrgTreeHelper.removeOrgWithOutPermision(root, authorizedOrgs);
		endTime = System.currentTimeMillis();
		exeTime = endTime - startTime;

		logger.info("5=================>FdcTreeBuilder.buildByOrgTree(), removeOrgWithNoPermision, exeTime:" + +exeTime);

		// 转换成OrgStructure树
		// coverToOrgStructureTree(orgTree);
	}

	/**
	 * 取得组织树查询项
	 * 
	 * @return
	 */
	private SelectorItemCollection getOrgTreeSic() {
		SelectorItemCollection sic = new SelectorItemCollection();

		// 构建树形结构需要7个字段:id,name,number,longNumber,level,isLeaf,parent.id
		sic.add("id");
		sic.add("name");
		sic.add("number");
		sic.add("longNumber");
		sic.add("level");
		sic.add("isLeaf");
		sic.add("parent.id");

		sic.add("isCompanyOrgUnit");
		sic.add("isCostOrgUnit");

		return sic;
	}

	/**
	 * 
	 * 描述：当前组织是财务组织
	 * 
	 * @param orgTree
	 * @param orgTreeModel
	 * @throws Exception
	 * @author:liupd 创建时间：2006-9-14
	 * 
	 *               <p>
	 */
	/**
	 * @param orgTree
	 * @param orgTreeModel
	 * @throws Exception
	 */
	public void buildByOrgTree(KDTree orgTree, TreeModel orgTreeModel) throws Exception {
		long startTime = 0;
		long endTime = 0;
		long exeTime = 0;

		long startTime_total = 0;
		long endTime_total = 0;
		long exeTime_total = 0;

		startTime_total = System.currentTimeMillis();

		orgTree.setModel(orgTreeModel);
		TreeNode root2 = (TreeNode) FdcOrgTreeHelper.getRoot(orgTreeModel);

		Set leafNodesIdSet = new HashSet();
		// 生成叶子节点集合
		FdcTreeBuilder.genLeafNodesIdSet(root2, leafNodesIdSet);

		int authorizedOrgsSize = FdcCollectionUtil.isNotEmpty(authorizedOrgs) ? authorizedOrgs.size() : 0;
		int leafNodesIdSetSize = FdcCollectionUtil.isNotEmpty(leafNodesIdSet) ? leafNodesIdSet.size() : 0;
		logger.info("5=================>FdcTreeBuilder.buildByOrgTree(), authorizedOrgs.size():" + authorizedOrgsSize);
		logger.info("5=================>FdcTreeBuilder.buildByOrgTree(), leafNodesIdSet.size():" + leafNodesIdSetSize);

		// Filter
		FilterInfo filter = new FilterInfo();

		// // 不加过滤,倒数第2级的组织过滤存在问题
		// if (leafNodesIdSetSize <= 100) {
		// // 小于100个下级节点
		// filter.getFilterItems().add(new FilterItemInfo("costCenter.id",
		// leafNodesIdSet, CompareType.INCLUDE));
		// }
		if (leafNodesIdSetSize <= 100 && root2 instanceof DefaultKingdeeTreeNode) {
			DefaultKingdeeTreeNode root3 = (DefaultKingdeeTreeNode) root2;

			if (FdcOrgTreeHelper.isCostCenterOrgUnitOrOrgStructureNode(root3) && root3.isLeaf()) {
				CostCenterOrgUnitInfo orgUnitInfo = (CostCenterOrgUnitInfo) FdcOrgTreeHelper.getOrgInfo(root3);

				// filter.getFilterItems().add(new
				// FilterItemInfo("costCenter.longNumber",
				// orgUnitInfo.getLongNumber()));
				filter.getFilterItems().add(
						new FilterItemInfo("costCenter.longNumber", orgUnitInfo.getLongNumber() + "!%", CompareType.LIKE));
				// filter.setMaskString("#0 or #1");
			}
		}

		if (!isIncludeDisabled) {
			filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		}

		if (this.projectTypeId != null) {
			filter.getFilterItems().add(new FilterItemInfo("projectType.id", projectTypeId));
		}
		// 判断合同拆分和变更拆分的项目树合集是否为空
		if (projectIdSet != null && projectIdSet.size() > 0) {
			filter.getFilterItems().add(new FilterItemInfo("id", projectIdSet, CompareType.INCLUDE));
		}
		if (isDevPrjFilter) {
			if (isContainDevPrj) {
				filter.getFilterItems().add(new FilterItemInfo("isDevPrj", Boolean.FALSE));
			} else {
				filter.getFilterItems().add(new FilterItemInfo("isDevPrj", Boolean.TRUE));
			}
		}

		if (!isViewLevf) {
			filter.getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.FALSE));
		}

		// 构建工程树时 增加对当前用户租住权限的过滤
		// 如果项目没有成本中心则不做此限制 by sxhong 2008-11-10 16:48:45
		// FilterInfo authorizedOrgsFilter = new FilterInfo();
		// if (this.ui == null || !(this.ui instanceof ProjectListUI)) {
		// // 工程项目序时簿不做过滤
		// if (authorizedOrgs == null || authorizedOrgs.size() == 0) {
		// // 没有授权则只显示没有做的的数据
		// authorizedOrgsFilter.getFilterItems().add(new
		// FilterItemInfo("costCenter.id", null));
		// } else {
		// authorizedOrgsFilter.getFilterItems().add(new
		// FilterItemInfo("costCenter.id", authorizedOrgs,
		// CompareType.INCLUDE));
		// authorizedOrgsFilter.getFilterItems().add(new
		// FilterItemInfo("costCenter.id", null));
		// authorizedOrgsFilter.setMaskString("#0 or #1");
		// }
		// filter.mergeFilter(authorizedOrgsFilter, "and");
		// }

		startTime = System.currentTimeMillis();
		buildProjTree(filter);

		endTime = System.currentTimeMillis();
		exeTime = endTime - startTime;
		logger.info("5=================>FdcTreeBuilder.buildByOrgTree().buildProjTree(filter), exeTime:" + +exeTime);

		TreeNode proRoot = (TreeNode) FdcOrgTreeHelper.getRoot(projTree);
		TreeNode orgRoot = (TreeNode) FdcOrgTreeHelper.getRoot(orgTreeModel);

		logger.info("FdcTreeBuilder.build.searchNodeByOrgId========begin:" + System.currentTimeMillis());

		// 如果是明细组织,没有下级(叶子节点)
		if (orgRoot.getChildCount() == 0) {
			for (int i = 0; i < proRoot.getChildCount(); i++) {
				DefaultKingdeeTreeNode projNode = (DefaultKingdeeTreeNode) proRoot.getChildAt(i);

				orgTree.addNodeInto((MutableTreeNode) projNode, (MutableTreeNode) orgRoot);
				i--;
			}
		}
		// 如果不是明细组织,还有下级(非叶子节点)
		else {
			for (int i = 0; i < proRoot.getChildCount(); i++) {
				DefaultKingdeeTreeNode projNode = (DefaultKingdeeTreeNode) proRoot.getChildAt(i);
				CurProjectInfo projectInfo = (CurProjectInfo) projNode.getUserObject();
				CostCenterOrgUnitInfo costCenterInfo = projectInfo.getCostCenter();
				String orgId = null;

				if (projectInfo.isIsLeaf() && projectInfo.getParent() != null) {
					getIt = false;
					continue;
				} else if (costCenterInfo != null && costCenterInfo.getParent() != null) {
					// 金地修改:为什么挂在成本中心的上一级? by skyiter_wang 发现
					orgId = costCenterInfo.getParent().getId().toString();
				} else if (costCenterInfo != null) {
					orgId = costCenterInfo.getId().toString();

					logger.info("6===========>没有parent成本中心的工程项目: costCenterInfo: " + costCenterInfo.getName() + "，projectInfo: "
							+ projectInfo.getName());
				} else {
					orgId = projectInfo.getFullOrgUnit().getId().toString();
				}

				searchNodeByOrgId(orgRoot, orgId, orgTree, projNode, leafNodesIdSet);

				if (getIt) {
					i--;
				}
				getIt = false;
			}
		}

		logger.info("FdcTreeBuilder.build.searchNodeByOrgId========end:" + System.currentTimeMillis());

		// 删除没有工程项目的组织
		FdcOrgTreeHelper.removeCompanyNoProject((DefaultKingdeeTreeNode) orgRoot);

		// DefaultKingdeeTreeNode kdTreeNode =
		// (DefaultKingdeeTreeNode)(orgTree.getModel().getRoot());
		// if (StringUtils.isBlank(kdTreeNode.getText()) &&
		// kdTreeNode.getChildCount() == 0) {
		// OrgUnitInfo orgUnitInfo =
		// FdcOrgTreeHelper.getOrgInfo((DefaultKingdeeTreeNode)orgRoot);
		//			
		// kdTreeNode.setText(orgUnitInfo.getName());
		// kdTreeNode.add(new DefaultMutableTreeNode((DefaultKingdeeTreeNode)
		// orgRoot));
		// }

		orgTree.updateUI();

		logger.info("FdcTreeBuilder.build.removeCompanyNoProject========end" + System.currentTimeMillis());

		endTime_total = System.currentTimeMillis();
		exeTime_total = endTime_total - startTime_total;
		logger.info("11=================>FdcTreeBuilder.buildByOrgTree(), filter:" + filter);
		logger.info("12=================>FdcTreeBuilder.buildByOrgTree(), exeTime:" + exeTime_total);

		// 打印有项目的组织节点
		// printOrgNodesWithProject(orgTreeModel);
	}

	/**
	 * 生存叶子几点ID列表
	 * 
	 * @param node
	 * @param leafNodesIdSet
	 */
	public static void genLeafNodesIdSet(TreeNode node, Set leafNodesIdSet) {
		int count = node.getChildCount();

		if (count == 0) {
			CostCenterOrgUnitInfo orgStructureInfo = (CostCenterOrgUnitInfo) ((DefaultKingdeeTreeNode) node).getUserObject();

			String oid = orgStructureInfo.getId().toString();
			leafNodesIdSet.add(oid);
			return;
		}
		DefaultKingdeeTreeNode treeNode = null;
		for (int i = 0; i < count; i++) {
			treeNode = (DefaultKingdeeTreeNode) node.getChildAt(i);
			if (treeNode.isLeaf()) {
				String id = ((CostCenterOrgUnitInfo) treeNode.getUserObject()).getId().toString();

				leafNodesIdSet.add(id);
			} else {
				genLeafNodesIdSet(treeNode, leafNodesIdSet);
			}
		}
	}

	/**
	 * 打印有项目的组织节点
	 * 
	 * @param orgTreeModel
	 */
	public static void printOrgNodesWithProject(TreeModel orgTreeModel) {
		// FDCClientUtils.genLeafNodesIdSet(rootWithProject,
		// leafNodesIdSetWithProject);

		TreeNode rootWithProject = (TreeNode) FdcOrgTreeHelper.getRoot(orgTreeModel);
		Map leafNodeOrgMap = new LinkedHashMap();
		Map allNodeOrgMap = new LinkedHashMap();

		logger.info("13=================>FdcTreeBuilder.buildByOrgTree(), 删除没有工程项目后的项目节点, start=================");
		genOrgNode(rootWithProject, leafNodeOrgMap, allNodeOrgMap);
		logger.info("13=================>FdcTreeBuilder.buildByOrgTree(), 叶子节点数量:" + leafNodeOrgMap.size());
		logger.info("13=================>FdcTreeBuilder.buildByOrgTree(), 所有节点数量:" + allNodeOrgMap.size());
		logger.info("13=================>FdcTreeBuilder.buildByOrgTree(), 删除没有工程项目后的项目节点, end=================");
	}

	/**
	 * 生成组织节点
	 * 
	 * 参照FDCClientUtils.genLeafNodesIdSet
	 * 
	 * @param node
	 */
	public static void genOrgNode(TreeNode node, Map leafNodeOrgMap, Map allNodeOrgMap) {
		int count = node.getChildCount();

		Object userObject = null;

		if (count == 0) {
			userObject = ((DefaultKingdeeTreeNode) node).getUserObject();
			if (!(FdcOrgTreeHelper.isCostCenterOrgUnitOrOrgStructureObject(userObject))) {
				return;
			}

			logger.info("13=================> 叶子节点组织:" + userObject);

			allNodeOrgMap.put(userObject, node);
			leafNodeOrgMap.put(userObject, node);

			return;
		}
		DefaultKingdeeTreeNode treeNode = null;
		for (int i = 0; i < count; i++) {
			treeNode = (DefaultKingdeeTreeNode) node.getChildAt(i);

			userObject = ((DefaultKingdeeTreeNode) treeNode).getUserObject();
			if (!(FdcOrgTreeHelper.isCostCenterOrgUnitOrOrgStructureObject(userObject))) {
				continue;
			}

			int tempChildCount = treeNode.getChildCount();
			if (tempChildCount > 0) {

				DefaultKingdeeTreeNode tempTreeNode1 = (DefaultKingdeeTreeNode) treeNode.getChildAt(0);
				Object tempUserObject1 = ((DefaultKingdeeTreeNode) tempTreeNode1).getUserObject();
				if (!(tempUserObject1 instanceof CurProjectInfo)) {
					logger.info("13==========> 非叶子节点组织:" + userObject);

					allNodeOrgMap.put(userObject, treeNode);
					genOrgNode(treeNode, leafNodeOrgMap, allNodeOrgMap);

					continue;
				}

				if (tempChildCount > 1) {
					DefaultKingdeeTreeNode tempTreeNode2 = (DefaultKingdeeTreeNode) treeNode.getChildAt(tempChildCount - 1);
					Object tempUserObject2 = ((DefaultKingdeeTreeNode) tempTreeNode2).getUserObject();
					if (!(tempUserObject2 instanceof CurProjectInfo)) {
						logger.info("13==========> 非叶子节点组织:" + userObject);

						allNodeOrgMap.put(userObject, treeNode);
						genOrgNode(treeNode, leafNodeOrgMap, allNodeOrgMap);

						continue;
					}
				}

				// //////////////////////////////////////////////////////////////

				logger.info("13=================> 叶子节点组织:" + userObject);

				allNodeOrgMap.put(userObject, treeNode);
				leafNodeOrgMap.put(userObject, treeNode);
			}
		}
	}

	/**
	 * 
	 * 描述：根据成本中心组织数构造工程项目树
	 * 
	 * @param orgTree
	 *            在此树上构造工程项目
	 * @param orgTreeModel
	 *            成本中心树Model
	 * @throws Exception
	 * @author:liupd 创建时间：2006-9-14
	 *               <p>
	 */
	public void buildByCostOrgTree(KDTree orgTree, TreeModel orgTreeModel) throws Exception {
		TreeNode root2 = (TreeNode) FdcOrgTreeHelper.getRoot(orgTreeModel);

		Set costLeafNodesIdSet = new HashSet();

		FDCClientUtils.genLeafNodesIdSet(root2, costLeafNodesIdSet);

		FDCClientUtils.removeNoneCompanyNode((DefaultKingdeeTreeNode) root2);

		Set companyNodesIdSet = new HashSet();

		FDCClientUtils.genNodesIdSet(root2, companyNodesIdSet);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", companyNodesIdSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("isBizUnit");
		Set companyIdSet = new HashSet();
		CompanyOrgUnitCollection companyOrgUnitCollection = CompanyOrgUnitFactory.getRemoteInstance().getCompanyOrgUnitCollection(view);
		for (Iterator iter = companyOrgUnitCollection.iterator(); iter.hasNext();) {
			CompanyOrgUnitInfo element = (CompanyOrgUnitInfo) iter.next();
			if (element.isIsBizUnit()) {
				companyIdSet.add(element.getId().toString());
			}

		}

		orgTree.setModel(orgTreeModel);

		view = new EntityViewInfo();
		filter = new FilterInfo();

		filter.getFilterItems().add(new FilterItemInfo("costCenterOU.id", costLeafNodesIdSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("curProject.longNumber");
		ProjectWithCostCenterOUCollection projectWithCostCenterOUCollection = ProjectWithCostCenterOUFactory.getRemoteInstance().getProjectWithCostCenterOUCollection(view);

		if (projectWithCostCenterOUCollection == null || projectWithCostCenterOUCollection.size() == 0) {
			return;
		}
		StringBuffer maskStr = new StringBuffer();
		int idx = 0;
		String mark = "#";
		String or = " OR ";
		maskStr.append("(");
		FilterInfo projFilter = new FilterInfo();
		for (Iterator iter = projectWithCostCenterOUCollection.iterator(); iter.hasNext();) {
			ProjectWithCostCenterOUInfo element = (ProjectWithCostCenterOUInfo) iter.next();
			String longNumber = element.getCurProject().getLongNumber();
			projFilter.getFilterItems().add(new FilterItemInfo("longNumber", longNumber + "%", CompareType.LIKE));
			maskStr.append(mark + idx++ + or);
		}

		String mask = maskStr.toString();
		mask = mask.substring(0, mask.length() - or.length());
		maskStr.setLength(0);
		maskStr.append(mask);
		maskStr.append(")");

		// 当前组织为实体成本中心时, 没有财务组织, 这里将当前财务组织加入
		if (companyIdSet.size() == 1 || companyIdSet.size() == 0) {
			companyIdSet.add(SysContext.getSysContext().getCurrentFIUnit().getId().toString());
		}

		projFilter.getFilterItems().add(new FilterItemInfo("fullOrgUnit", companyIdSet, CompareType.INCLUDE));
		maskStr.append(" and " + mark + idx++);

		if (!isIncludeDisabled) {
			projFilter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
			maskStr.append(" and " + mark + idx++);
		}

		projFilter.setMaskString(maskStr.toString());

		buildProjTree(projFilter);

		TreeNode root = (TreeNode) FdcOrgTreeHelper.getRoot(projTree);

		TreeNode orgRoot = (TreeNode) orgTreeModel.getRoot();

		if (orgRoot.getChildCount() == 0) {
			for (int i = 0; i < root.getChildCount(); i++) {
				DefaultKingdeeTreeNode projNode = (DefaultKingdeeTreeNode) root.getChildAt(i);
				orgTree.addNodeInto((MutableTreeNode) projNode, (MutableTreeNode) orgRoot);
				i--;
			}
		} else {
			for (int i = 0; i < root.getChildCount(); i++) {
				DefaultKingdeeTreeNode projNode = (DefaultKingdeeTreeNode) root.getChildAt(i);
				CurProjectInfo projectInfo = (CurProjectInfo) projNode.getUserObject();
				String orgId = projectInfo.getFullOrgUnit().getId().toString();

				searchNodeByOrgId(orgRoot, orgId, orgTree, projNode, companyIdSet);

				if (getIt) {
					i--;
				}
				getIt = false;
			}

		}
	}

	/**
	 * 
	 * 描述：根据组织ID查找组织树节点, 并将项目节点增加到搜索到的组织节点下面
	 * 
	 * @param result
	 *            查找结果
	 * @param node
	 * @param orgId
	 * @author:liupd 创建时间：2006-7-20
	 *               <p>
	 */
	private void searchNodeByOrgId(TreeNode orgNode, String orgId, KDTree orgTree, TreeNode projNode, Set leafNodesIdSet) {
		if (getIt) {
			return;
		}
		/*
		 * 增加对组织树根节点的搜索，避免当实体财务组织下面又有实体财务组织时，无法显示上级实体财务组织下的项目的情况07.06.08
		 */

		Object obj = ((DefaultKingdeeTreeNode) orgNode).getUserObject();
		if (FdcOrgTreeHelper.isCostCenterOrgUnitOrOrgStructureObject(obj)) {
			String orgRootId = FdcOrgTreeHelper.getOrgId(obj);
			if (orgRootId.equals(orgId)) {
				orgTree.addNodeInto((MutableTreeNode) projNode, (MutableTreeNode) orgNode);
				getIt = true;
				return;
			}
		}

		int count = orgNode.getChildCount();
		DefaultKingdeeTreeNode treeNode = null;
		for (int i = 0; i < count; i++) {
			treeNode = (DefaultKingdeeTreeNode) orgNode.getChildAt(i);
			Object userObj = treeNode.getUserObject();
			if (FdcOrgTreeHelper.isCostCenterOrgUnitOrOrgStructureObject(userObj)) {
				String orgId2 = FdcOrgTreeHelper.getOrgId(userObj);
				if (orgId2.equals(orgId)) {
					orgTree.addNodeInto((MutableTreeNode) projNode, (MutableTreeNode) treeNode);
					getIt = true;
					break;
				} else {
					searchNodeByOrgId(treeNode, orgId, orgTree, projNode, leafNodesIdSet);
				}
			} else {
				searchNodeByOrgId(treeNode, orgId, orgTree, projNode, leafNodesIdSet);
			}

		}

	}

	private IMetaDataPK getActionPK(ItemAction action) {
		if (action == null) {
			return null;
		}
		String actoinName = action.getClass().getName();
		if (actoinName.indexOf("$") >= 0) {
			actoinName = actoinName.substring(actoinName.indexOf("$") + 1);
		}

		return new MetaDataPK(actoinName);
	}

	private TreeSelectionListener treeSelectionListener;

	private ITreeBuilder treeBuilder;

	/**
	 * 构造项目树
	 */
	private void buildProjTree(FilterInfo filter) throws Exception {
		long startTime = 0;
		long endTime = 0;
		long exeTime = 0;

		long startTime_total = 0;
		long endTime_total = 0;
		long exeTime_total = 0;

		startTime_total = System.currentTimeMillis();

		// 暂时移除树监听器
		KDTree treeMain = projTree;
		TreeSelectionListener[] listeners = treeMain.getTreeSelectionListeners();
		if (listeners.length > 0) {
			treeSelectionListener = listeners[0];
			treeMain.removeTreeSelectionListener(treeSelectionListener);
		}

		startTime = System.currentTimeMillis();
		treeBuilder = TreeBuilderFactory.createTreeBuilder(getProjLNTreeNodeCtrl(), getTreeInitialLevel(), getTreeExpandLevel(), filter,
				getProjTreeSic());
		endTime = System.currentTimeMillis();
		exeTime = endTime - startTime;
		logger.info("6===========>FdcTreeBuilder.buildProjTree(), TreeBuilderFactory.createTreeBuilder, exeTime: " + exeTime);
		startTime = System.currentTimeMillis();
		treeBuilder.buildTree(treeMain);
		endTime = System.currentTimeMillis();
		exeTime = endTime - startTime;
		logger.info("7===========>FdcTreeBuilder.buildProjTree(), treeBuilder.buildTree(treeMain), exeTime: " + exeTime);

		TreeModel treeModel = null;
		String paramValue = ParamControlFactory.getRemoteInstance().getParamValue(null, FDCConstants.FDC_PARAM_PROJECTTREESORTBYSORTNO);
		if (Boolean.valueOf(paramValue).booleanValue()) {

			/*
			 * 根据sortNo字段排序
			 */
			treeModel = new KingdeeTreeModel((TreeNode) ((DefaultKingdeeTreeNode) FdcOrgTreeHelper.getRoot(treeMain)).clone());
			this.addNode((DefaultKingdeeTreeNode) treeMain.getModel().getRoot(), (DefaultKingdeeTreeNode) treeModel.getRoot());
		} else {
			treeModel = new KingdeeTreeModel((TreeNode) ((DefaultKingdeeTreeNode) FdcOrgTreeHelper.getRoot(treeMain)));
		}

		// 如果选择了包括产品类型,则要往工程树上加产品类型
		if (isIncludeProductType) {
			DefaultKingdeeTreeNode rootNode = (DefaultKingdeeTreeNode) treeModel.getRoot();
			addProductType(rootNode);
		}

		treeMain.setModel(treeModel);

		// 添加树监听器
		treeMain.addTreeSelectionListener(treeSelectionListener);
		treeMain.setShowPopMenuDefaultItem(false);

		endTime_total = System.currentTimeMillis();
		exeTime_total = endTime_total - startTime_total;
		logger.info("7=================>FdcTreeBuilder.buildProjTree(), filter:" + filter);
		logger.info("8=================>FdcTreeBuilder.buildProjTree(), exeTime:" + exeTime_total);
	}

	/**
	 * 取得项目树查询项
	 * 
	 * @return
	 */
	private SelectorItemCollection getProjTreeSic() {
		SelectorItemCollection sic = new SelectorItemCollection();

		// sic.add("*");

		// 构建树形结构需要7个字段:id,name,number,longNumber,level,isLeaf,parent.id
		sic.add("id");
		sic.add("name");
		sic.add("number");
		sic.add("longNumber");
		sic.add("level");
		sic.add("isLeaf");
		sic.add("parent.id");

		sic.add("costCenter.id");
		sic.add("costCenter.parent.id");
		sic.add("fullOrgUnit.id");

		sic.add("CU.id");

		sic.add("projectType.id");

		return sic;
	}

	/**
	 * 加产品类型
	 * 
	 * @param node
	 * @throws Exception
	 */
	private void addProductType(DefaultKingdeeTreeNode node) throws Exception {
		for (int i = 0; i < node.getChildCount(); i++) {
			DefaultKingdeeTreeNode childAt = (DefaultKingdeeTreeNode) node.getChildAt(i);
			if (childAt.getChildCount() == 0) {
				CurProjectInfo proj = (CurProjectInfo) childAt.getUserObject();
				if (proj != null) {
					String id = proj.getId().toString();
					CurProjProductEntriesCollection col = getProductTypes(id);
					for (Iterator iter = col.iterator(); iter.hasNext();) {
						CurProjProductEntriesInfo element = (CurProjProductEntriesInfo) iter.next();
						DefaultKingdeeTreeNode productTypeNode = new DefaultKingdeeTreeNode(element.getProductType());
						childAt.add(productTypeNode);

					}
				}
			} else {
				addProductType(childAt);
			}
		}
	}

	/**
	 * 根据工程项目找产品类型
	 * 
	 * @param projId
	 * @return
	 * @throws Exception
	 */
	private CurProjProductEntriesCollection getProductTypes(String projId) throws Exception {
		CurProjProductEntriesCollection coll = new CurProjProductEntriesCollection();
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		filterItems.add(new FilterItemInfo("curProject", projId));
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		view.getSelector().add("productType.id");
		view.getSelector().add("productType.number");
		view.getSelector().add("productType.name");
		coll = CurProjProductEntriesFactory.getRemoteInstance().getCurProjProductEntriesCollection(view);
		return coll;
	}

	/**
	 * 排序用
	 * 
	 * @param node
	 * @param newNode
	 */
	private void addNode(DefaultKingdeeTreeNode node, DefaultKingdeeTreeNode newNode) {
		TreeMap map = new TreeMap();
		BigDecimal b = new BigDecimal("0.001");
		for (int i = 0; i < node.getChildCount(); i++) {
			DefaultKingdeeTreeNode childAt = (DefaultKingdeeTreeNode) node.getChildAt(i);
			int index = ((CurProjectInfo) childAt.getUserObject()).getSortNo();
			if (map.containsKey(new BigDecimal(index))) {
				map.put(new BigDecimal(index).add(b), childAt);
				b = b.add(b);
			} else {
				map.put(new BigDecimal(index), childAt);
			}
		}
		Collection collection = map.values();
		for (Iterator iter = collection.iterator(); iter.hasNext();) {
			DefaultKingdeeTreeNode element = (DefaultKingdeeTreeNode) iter.next();
			DefaultKingdeeTreeNode newElement = (DefaultKingdeeTreeNode) element.clone();
			newNode.add(newElement);
			addNode(element, newElement);
		}

	}

	/**
	 * 
	 * 描述：一棵树两个实体的情况下无法使用虚模式取数（框架不支持），因此直接把初始级次设一个比较大的数，就可以绕过虚模式取数
	 * 先这样，以后有时间写一套基于两个（或多个）实体的构造树的框架
	 * 
	 * @return
	 * @author:liupd 创建时间：2006-8-29
	 *               <p>
	 */
	private int getTreeInitialLevel() {
		// return 3;
		return 50;
	}

	private int getTreeExpandLevel() {
		// return 3;
		return 5;
	}

	private ILNTreeNodeCtrl getProjLNTreeNodeCtrl() throws Exception {
		return new DefaultLNTreeNodeCtrl(getProjTreeInterface());
	}

	private ITreeBase getProjTreeInterface() throws Exception {

		ITreeBase treeBase = CurProjectFactory.getRemoteInstance();

		return treeBase;
	}

	/**
	 * 获取整个工程项目树Model
	 * 
	 * @return
	 * @throws Exception
	 */
	public TreeModel getFullProjTreeModel() throws Exception {
		KDTree tree = new KDTree();

		TreeModel orgTreeModel = NewOrgUtils.getTreeModel(OrgViewType.COMPANY, "", null, null, null);
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) orgTreeModel.getRoot();

		if (root.getUserObject() != null) {
			// 如果根组织不是成本中心，提示
			if (!FdcOrgTreeHelper.isCostCenterOrgUnitNode(root)) {
				MsgBox.showWarning(FDCClientUtils.getRes("needCostcenter"));
				SysUtil.abort();
			}
			FdcOrgTreeHelper.removeNoneCostCenterNode(root);
			buildByOrgTree(tree, orgTreeModel);
		} else {
			buildByCurOrg(tree);
		}

		return tree.getModel();
	}

	/**
	 * 
	 * 描述：生成指定节点及下级节点的Map[id, node]
	 * 
	 * @param root
	 * @param map
	 * @author:liupd 创建时间：2006-7-20
	 *               <p>
	 */
	public static void genNodeMap(TreeNode root, Map map) {
		FdcOrgTreeHelper.genNodeMap(root, map);
	}

	// 遍历projectTree，找到unitId为Root的子树
	public static DefaultKingdeeTreeNode getDefaultKingdeeTreeNode(DefaultKingdeeTreeNode root, String unitId) {
		if (unitId == null) {
			return root;
		} else {
			Object obj = ((DefaultKingdeeTreeNode) root).getUserObject();

			if (!(FdcOrgTreeHelper.isCostCenterOrgUnitOrOrgStructureObject(obj))) {
				return null;
			}

			String orgRootId = FdcOrgTreeHelper.getOrgId(obj);
			if (!orgRootId.equals(unitId)) {
				int count = root.getChildCount();
				DefaultKingdeeTreeNode treeNode = null;
				for (int i = 0; i < count; i++) {
					treeNode = (DefaultKingdeeTreeNode) root.getChildAt(i);

					obj = ((DefaultKingdeeTreeNode) treeNode).getUserObject();
					if (!(FdcOrgTreeHelper.isCostCenterOrgUnitOrOrgStructureObject(obj))) {
						continue;
					}
					orgRootId = FdcOrgTreeHelper.getOrgId(obj);

					if (!orgRootId.equals(unitId)) {
						DefaultKingdeeTreeNode value = getDefaultKingdeeTreeNode(treeNode, unitId);
						if (value != null) {
							return value;
						}
					} else {
						return treeNode;
					}
				}

			} else {
				return root;
			}
		}
		return null;

	}

	/**
	 * 描述：仅成本科目序事簿、可研测算序事簿、测算模版三个界面可见开发项目及可研测算项目；其他只能显示开发项目。
	 * 
	 * @author hpw date:2009-8-24
	 * @param isDevPrjFilter
	 * @return
	 */
	public void setDevPrjFilter(boolean isDevPrjFilter) {
		this.isDevPrjFilter = isDevPrjFilter;
	}

	/**
	 * 专用过滤可研成本
	 */
	private boolean isContainDevPrj = false;

	public void setDevPrjFilter(boolean isDevPrjFilter, boolean isContainDevPrj) {
		this.isDevPrjFilter = isDevPrjFilter;
		this.isContainDevPrj = isContainDevPrj;

	}

	// //////////////////////////////////////////////////////////////////////////
	// 金地性能优化 by skyiter_wang 2014-05-06
	// --- end
	// //////////////////////////////////////////////////////////////////////////

}