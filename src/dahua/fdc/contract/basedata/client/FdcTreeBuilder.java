/*
 * @(#)FdcTreeBuilder.java
 * 
 * �����������������޹�˾��Ȩ����
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
 * ����:���ݵ�ǰ��֯���칤����Ŀ����ʵ�����ǣ� ͬʱ�߱�������֯�ͳɱ��������Ե���֯�� �� ������Ŀ��
 * 
 * @author liupd date:2006-7-20
 *         <p>
 * @version EAS5.1.3
 */
public class FdcTreeBuilder {

	static final Logger logger = CoreUIObject.getLogger(FdcTreeBuilder.class);

	// parent UI
	private CoreUIObject ui;
	// ��������Ŀ��
	private KDTree projTree = new KDTree();

	// internal var, a flag
	private boolean getIt = false;

	// �Ƿ�����ѽ��õĹ�����Ŀ
	private boolean isIncludeDisabled;

	// ��������֯���룿
	private boolean noOrgIsolation;

	// �Ƿ������Ʒ����
	private boolean isIncludeProductType;

	private String projectTypeId;

	// ��ȡ��Ȩ�޵���֯
	protected Set authorizedOrgs = null;

	// �Ƿ���ʾ��ϸ��Ŀ
	private boolean isViewLevf = true;

	/** �Ƿ񿪷���Ŀ */
	private boolean isDevPrjFilter = true;
	private Set projectIdSet = new HashSet();// ��Ŀ������ID�ϼ�

	/**
	 * 
	 * ���������ݵ�ǰ��֯���칤����Ŀ����������֯��������Ŀ��
	 * 
	 * @param ui
	 *            Ҫ���������б����ʵ��
	 * @param projectTree
	 *            Ҫ�����KDTree
	 * @param actionOnload
	 *            �б�����ActionOnLoad����this.actionOnLoad����
	 * @throws Exception
	 * @author:liupd ����ʱ�䣺2006-7-20
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

		// �����������
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
		// ����Ƿ��ǳɱ�����
		FdcOrgTreeHelper.checkIsCostCenter(currentOrgUnitInfo, false);
		UserInfo currentUserInfo = SysContext.getSysContext().getCurrentUserInfo();

		// //////////////////////////////////////////////////////////////////////
		// ///

		// �������н�ɫ��֯��Χ
		// authorizedOrgs = FdcOrgTreeHelper.genAuthorizedOrgs();
		// ���ɽ�ɫ��֯��Χ
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
			// ����Ƿ��ǳɱ�����
			FdcOrgTreeHelper.checkIsCostCenter(root, false);

			// //////////////////////////////////////////////////////////////////

			startTime = System.currentTimeMillis();

			// /////////////////
			// ȥ��û�гɱ�������֯���ԵĽڵ�
			FdcOrgTreeHelper.removeNoneCostCenterNode(root);
			// /////////////////

			endTime = System.currentTimeMillis();
			exeTime = endTime - startTime;
			logger.info("2.3=================>FdcTreeBuilder.build(), removeNoneCostCenterNode(), exeTime: " + exeTime);

			startTime = System.currentTimeMillis();

			// /////////////////
			// ������֯��
			buildByOrgTree(projectTree, orgTreeModel);
			// /////////////////

			endTime = System.currentTimeMillis();
			exeTime = endTime - startTime;

			logger.info("2.4=================>FdcTreeBuilder.build(), buildByOrgTree(projectTree, orgTreeModel), exeTime:" + exeTime);
		} else {
			startTime = System.currentTimeMillis();

			// /////////////////
			// ������֯��
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
	 * �����������
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
	 * ��������ǰ��֯�ǳɱ�����
	 * 
	 * @param tree
	 * @throws Exception
	 * @author:liupd ����ʱ�䣺2006-9-14
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
			MsgBox.showWarning(ui, "�޷����칤����Ŀ������ȷ��\"������Ŀ��ɱ����ĵĶ�Ӧ��ϵ\"�����Ƿ���ȷ");
			SysUtil.abort();
		}
		TreeModel model = new DefaultTreeModel(root.getChildAt(0));
		orgTree.setModel(model);

	}

	/**
	 * 
	 * @return �����ȡ��ǰ��֯�µĹ�����Ŀ��Filter
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public FilterInfo getProjectFilterByCurOrg() throws BOSException, EASBizException {
		// ��֯ID
		String curCostId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("costCenterOU.id", curCostId));
		view.setFilter(filter);
		// ������Ŀ��ɱ����Ķ�Ӧ��ϵ
		ProjectWithCostCenterOUCollection relations = ProjectWithCostCenterOUFactory.getRemoteInstance().getProjectWithCostCenterOUCollection(view);
		if (relations == null || relations.size() == 0) {
			MsgBox.showWarning(FDCClientUtils.getRes("noRelativeProject"));
			SysUtil.abort();
		}
		// ��ĿID
		String projId = relations.get(0).getCurProject().getId().toString();
		ObjectUuidPK projUuidPK = new ObjectUuidPK(projId);
		// �ж���Ŀ�Ƿ����
		if (!CurProjectFactory.getRemoteInstance().exists(projUuidPK)) {
			MsgBox.showWarning(FDCClientUtils.getRes("noRelativeProject"));
			SysUtil.abort();
		}
		// �õ�һ����Ŀʵ��
		CurProjectInfo curProjectInfo = CurProjectFactory.getRemoteInstance().getCurProjectInfo(projUuidPK);
		// �Ƿ�����
		if (!curProjectInfo.isIsEnabled()) {
			MsgBox.showWarning(FDCClientUtils.getRes("curOrgRelProjNotEnab"));
			SysUtil.abort();
		}
		// ��Ŀ�ĳ�����
		String projNumber = curProjectInfo.getLongNumber();
		// Filter��Ŀ����������
		FilterInfo projFilter = new FilterInfo();
		projFilter.getFilterItems().add(new FilterItemInfo("longnumber", projNumber + "%", CompareType.LIKE));

		String orgUnitId = SysContext.getSysContext().getCurrentFIUnit().getId().toString();
		// �жϺ�ͬ��ֺͱ����ֵ���Ŀ���ϼ��Ƿ�Ϊ��
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
	 * ���������캯��
	 * 
	 * @param isViewLevf
	 *            �Ƿ���ʾ��ϸ��Ŀ������Ŀ�ꡢ��̬�ɱ���̯
	 * @param a
	 */
	public FdcTreeBuilder(boolean isViewLevf, String a) {
		this.isIncludeDisabled = false;
		noOrgIsolation = false;

		this.isViewLevf = isViewLevf;
	}

	/**
	 * 
	 * ���������캯��
	 * 
	 * @param isIncludeDisabled
	 *            �Ƿ�����ѽ��õĹ�����Ŀ
	 * @author:liupd ����ʱ�䣺2006-9-14
	 *               <p>
	 */
	public FdcTreeBuilder(boolean isIncludeDisabled) {
		this.isIncludeDisabled = isIncludeDisabled;
		noOrgIsolation = false;
	}

	/**
	 * 
	 * ���������캯��
	 * 
	 * @param isIncludeDisabled
	 *            �Ƿ�����ѽ��õĹ�����Ŀ
	 * @param noOrgIsolation
	 *            ��������֯���룿 true �� �����룬false �� ����
	 * @author:liupd ����ʱ�䣺2006-9-14
	 *               <p>
	 */
	public FdcTreeBuilder(boolean isIncludeDisabled, boolean noOrgIsolation) {
		this.isIncludeDisabled = isIncludeDisabled;
		this.noOrgIsolation = noOrgIsolation;
	}

	/**
	 * 
	 * ���������캯��
	 * 
	 * @param isIncludeDisabled
	 *            �Ƿ�����ѽ��õĹ�����Ŀ
	 * @param noOrgIsolation
	 *            ��������֯���룿 true �� �����룬false �� ����
	 * @author:liupd ����ʱ�䣺2006-9-14
	 *               <p>
	 */
	public FdcTreeBuilder(boolean isIncludeDisabled, boolean noOrgIsolation, Set set) {
		this.isIncludeDisabled = isIncludeDisabled;
		this.noOrgIsolation = noOrgIsolation;
		this.projectIdSet = set;
	}

	/**
	 * 
	 * ���������캯��
	 * 
	 * @param isIncludeDisabled
	 *            �Ƿ�����ѽ��õĹ�����Ŀ
	 * @param noOrgIsolation
	 *            ��������֯���룿 true �� �����룬false �� ����
	 * @param isIncludeProductType
	 *            �Ƿ������Ʒ����(������Ŀ�¹Ҳ�Ʒ����)
	 * @author:liupd ����ʱ�䣺2006-9-14
	 *               <p>
	 */
	public FdcTreeBuilder(boolean isIncludeDisabled, boolean noOrgIsolation, boolean isIncludeProductType) {
		this.isIncludeDisabled = isIncludeDisabled;
		this.noOrgIsolation = noOrgIsolation;
		this.isIncludeProductType = isIncludeProductType;
	}

	/**
	 * 
	 * ���������캯��
	 * 
	 * @param isIncludeDisabled
	 *            �Ƿ�����ѽ��õĹ�����Ŀ
	 * @param noOrgIsolation
	 *            ��������֯���룿 true �� �����룬false �� ����
	 * @param isIncludeProductType
	 *            �Ƿ������Ʒ����(������Ŀ�¹Ҳ�Ʒ����)
	 * @author:liupd ����ʱ�䣺2006-9-14
	 *               <p>
	 */
	public FdcTreeBuilder(boolean isIncludeDisabled, boolean noOrgIsolation, boolean isIncludeProductType, String projectTypeId) {
		this.isIncludeDisabled = isIncludeDisabled;
		this.noOrgIsolation = noOrgIsolation;
		this.isIncludeProductType = isIncludeProductType;
		this.projectTypeId = projectTypeId;
	}

	/**
	 * ������֯��
	 * <p>
	 * ��NewOrgUtils.getTreeModel(OrgViewType.COSTCENTER�ĸ���
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
		// ����Ƿ��ǳɱ�����
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

		// ���¼�����ѡ���¼�
		treeMain.addTreeSelectionListener(treeSelectionListener);
		treeMain.setShowPopMenuDefaultItem(false);

		// //////////////////////////////////////////////////////////////////////
		// ///

		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) FdcOrgTreeHelper.getRoot(orgTree);

		startTime = System.currentTimeMillis();
		// ɾ��û����֯����Ŀ
		FdcOrgTreeHelper.removeOrgWithOutPermision(root, authorizedOrgs);
		endTime = System.currentTimeMillis();
		exeTime = endTime - startTime;

		logger.info("5=================>FdcTreeBuilder.buildByOrgTree(), removeOrgWithNoPermision, exeTime:" + +exeTime);

		// ת����OrgStructure��
		// coverToOrgStructureTree(orgTree);
	}

	/**
	 * ȡ����֯����ѯ��
	 * 
	 * @return
	 */
	private SelectorItemCollection getOrgTreeSic() {
		SelectorItemCollection sic = new SelectorItemCollection();

		// �������νṹ��Ҫ7���ֶ�:id,name,number,longNumber,level,isLeaf,parent.id
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
	 * ��������ǰ��֯�ǲ�����֯
	 * 
	 * @param orgTree
	 * @param orgTreeModel
	 * @throws Exception
	 * @author:liupd ����ʱ�䣺2006-9-14
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
		// ����Ҷ�ӽڵ㼯��
		FdcTreeBuilder.genLeafNodesIdSet(root2, leafNodesIdSet);

		int authorizedOrgsSize = FdcCollectionUtil.isNotEmpty(authorizedOrgs) ? authorizedOrgs.size() : 0;
		int leafNodesIdSetSize = FdcCollectionUtil.isNotEmpty(leafNodesIdSet) ? leafNodesIdSet.size() : 0;
		logger.info("5=================>FdcTreeBuilder.buildByOrgTree(), authorizedOrgs.size():" + authorizedOrgsSize);
		logger.info("5=================>FdcTreeBuilder.buildByOrgTree(), leafNodesIdSet.size():" + leafNodesIdSetSize);

		// Filter
		FilterInfo filter = new FilterInfo();

		// // ���ӹ���,������2������֯���˴�������
		// if (leafNodesIdSetSize <= 100) {
		// // С��100���¼��ڵ�
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
		// �жϺ�ͬ��ֺͱ����ֵ���Ŀ���ϼ��Ƿ�Ϊ��
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

		// ����������ʱ ���ӶԵ�ǰ�û���סȨ�޵Ĺ���
		// �����Ŀû�гɱ��������������� by sxhong 2008-11-10 16:48:45
		// FilterInfo authorizedOrgsFilter = new FilterInfo();
		// if (this.ui == null || !(this.ui instanceof ProjectListUI)) {
		// // ������Ŀ��ʱ����������
		// if (authorizedOrgs == null || authorizedOrgs.size() == 0) {
		// // û����Ȩ��ֻ��ʾû�����ĵ�����
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

		// �������ϸ��֯,û���¼�(Ҷ�ӽڵ�)
		if (orgRoot.getChildCount() == 0) {
			for (int i = 0; i < proRoot.getChildCount(); i++) {
				DefaultKingdeeTreeNode projNode = (DefaultKingdeeTreeNode) proRoot.getChildAt(i);

				orgTree.addNodeInto((MutableTreeNode) projNode, (MutableTreeNode) orgRoot);
				i--;
			}
		}
		// ���������ϸ��֯,�����¼�(��Ҷ�ӽڵ�)
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
					// ����޸�:Ϊʲô���ڳɱ����ĵ���һ��? by skyiter_wang ����
					orgId = costCenterInfo.getParent().getId().toString();
				} else if (costCenterInfo != null) {
					orgId = costCenterInfo.getId().toString();

					logger.info("6===========>û��parent�ɱ����ĵĹ�����Ŀ: costCenterInfo: " + costCenterInfo.getName() + "��projectInfo: "
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

		// ɾ��û�й�����Ŀ����֯
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

		// ��ӡ����Ŀ����֯�ڵ�
		// printOrgNodesWithProject(orgTreeModel);
	}

	/**
	 * ����Ҷ�Ӽ���ID�б�
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
	 * ��ӡ����Ŀ����֯�ڵ�
	 * 
	 * @param orgTreeModel
	 */
	public static void printOrgNodesWithProject(TreeModel orgTreeModel) {
		// FDCClientUtils.genLeafNodesIdSet(rootWithProject,
		// leafNodesIdSetWithProject);

		TreeNode rootWithProject = (TreeNode) FdcOrgTreeHelper.getRoot(orgTreeModel);
		Map leafNodeOrgMap = new LinkedHashMap();
		Map allNodeOrgMap = new LinkedHashMap();

		logger.info("13=================>FdcTreeBuilder.buildByOrgTree(), ɾ��û�й�����Ŀ�����Ŀ�ڵ�, start=================");
		genOrgNode(rootWithProject, leafNodeOrgMap, allNodeOrgMap);
		logger.info("13=================>FdcTreeBuilder.buildByOrgTree(), Ҷ�ӽڵ�����:" + leafNodeOrgMap.size());
		logger.info("13=================>FdcTreeBuilder.buildByOrgTree(), ���нڵ�����:" + allNodeOrgMap.size());
		logger.info("13=================>FdcTreeBuilder.buildByOrgTree(), ɾ��û�й�����Ŀ�����Ŀ�ڵ�, end=================");
	}

	/**
	 * ������֯�ڵ�
	 * 
	 * ����FDCClientUtils.genLeafNodesIdSet
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

			logger.info("13=================> Ҷ�ӽڵ���֯:" + userObject);

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
					logger.info("13==========> ��Ҷ�ӽڵ���֯:" + userObject);

					allNodeOrgMap.put(userObject, treeNode);
					genOrgNode(treeNode, leafNodeOrgMap, allNodeOrgMap);

					continue;
				}

				if (tempChildCount > 1) {
					DefaultKingdeeTreeNode tempTreeNode2 = (DefaultKingdeeTreeNode) treeNode.getChildAt(tempChildCount - 1);
					Object tempUserObject2 = ((DefaultKingdeeTreeNode) tempTreeNode2).getUserObject();
					if (!(tempUserObject2 instanceof CurProjectInfo)) {
						logger.info("13==========> ��Ҷ�ӽڵ���֯:" + userObject);

						allNodeOrgMap.put(userObject, treeNode);
						genOrgNode(treeNode, leafNodeOrgMap, allNodeOrgMap);

						continue;
					}
				}

				// //////////////////////////////////////////////////////////////

				logger.info("13=================> Ҷ�ӽڵ���֯:" + userObject);

				allNodeOrgMap.put(userObject, treeNode);
				leafNodeOrgMap.put(userObject, treeNode);
			}
		}
	}

	/**
	 * 
	 * ���������ݳɱ�������֯�����칤����Ŀ��
	 * 
	 * @param orgTree
	 *            �ڴ����Ϲ��칤����Ŀ
	 * @param orgTreeModel
	 *            �ɱ�������Model
	 * @throws Exception
	 * @author:liupd ����ʱ�䣺2006-9-14
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

		// ��ǰ��֯Ϊʵ��ɱ�����ʱ, û�в�����֯, ���ｫ��ǰ������֯����
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
	 * ������������֯ID������֯���ڵ�, ������Ŀ�ڵ����ӵ�����������֯�ڵ�����
	 * 
	 * @param result
	 *            ���ҽ��
	 * @param node
	 * @param orgId
	 * @author:liupd ����ʱ�䣺2006-7-20
	 *               <p>
	 */
	private void searchNodeByOrgId(TreeNode orgNode, String orgId, KDTree orgTree, TreeNode projNode, Set leafNodesIdSet) {
		if (getIt) {
			return;
		}
		/*
		 * ���Ӷ���֯�����ڵ�����������⵱ʵ�������֯��������ʵ�������֯ʱ���޷���ʾ�ϼ�ʵ�������֯�µ���Ŀ�����07.06.08
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
	 * ������Ŀ��
	 */
	private void buildProjTree(FilterInfo filter) throws Exception {
		long startTime = 0;
		long endTime = 0;
		long exeTime = 0;

		long startTime_total = 0;
		long endTime_total = 0;
		long exeTime_total = 0;

		startTime_total = System.currentTimeMillis();

		// ��ʱ�Ƴ���������
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
			 * ����sortNo�ֶ�����
			 */
			treeModel = new KingdeeTreeModel((TreeNode) ((DefaultKingdeeTreeNode) FdcOrgTreeHelper.getRoot(treeMain)).clone());
			this.addNode((DefaultKingdeeTreeNode) treeMain.getModel().getRoot(), (DefaultKingdeeTreeNode) treeModel.getRoot());
		} else {
			treeModel = new KingdeeTreeModel((TreeNode) ((DefaultKingdeeTreeNode) FdcOrgTreeHelper.getRoot(treeMain)));
		}

		// ���ѡ���˰�����Ʒ����,��Ҫ���������ϼӲ�Ʒ����
		if (isIncludeProductType) {
			DefaultKingdeeTreeNode rootNode = (DefaultKingdeeTreeNode) treeModel.getRoot();
			addProductType(rootNode);
		}

		treeMain.setModel(treeModel);

		// �����������
		treeMain.addTreeSelectionListener(treeSelectionListener);
		treeMain.setShowPopMenuDefaultItem(false);

		endTime_total = System.currentTimeMillis();
		exeTime_total = endTime_total - startTime_total;
		logger.info("7=================>FdcTreeBuilder.buildProjTree(), filter:" + filter);
		logger.info("8=================>FdcTreeBuilder.buildProjTree(), exeTime:" + exeTime_total);
	}

	/**
	 * ȡ����Ŀ����ѯ��
	 * 
	 * @return
	 */
	private SelectorItemCollection getProjTreeSic() {
		SelectorItemCollection sic = new SelectorItemCollection();

		// sic.add("*");

		// �������νṹ��Ҫ7���ֶ�:id,name,number,longNumber,level,isLeaf,parent.id
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
	 * �Ӳ�Ʒ����
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
	 * ���ݹ�����Ŀ�Ҳ�Ʒ����
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
	 * ������
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
	 * ������һ��������ʵ���������޷�ʹ����ģʽȡ������ܲ�֧�֣������ֱ�Ӱѳ�ʼ������һ���Ƚϴ�������Ϳ����ƹ���ģʽȡ��
	 * ���������Ժ���ʱ��дһ�׻���������������ʵ��Ĺ������Ŀ��
	 * 
	 * @return
	 * @author:liupd ����ʱ�䣺2006-8-29
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
	 * ��ȡ����������Ŀ��Model
	 * 
	 * @return
	 * @throws Exception
	 */
	public TreeModel getFullProjTreeModel() throws Exception {
		KDTree tree = new KDTree();

		TreeModel orgTreeModel = NewOrgUtils.getTreeModel(OrgViewType.COMPANY, "", null, null, null);
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) orgTreeModel.getRoot();

		if (root.getUserObject() != null) {
			// �������֯���ǳɱ����ģ���ʾ
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
	 * ����������ָ���ڵ㼰�¼��ڵ��Map[id, node]
	 * 
	 * @param root
	 * @param map
	 * @author:liupd ����ʱ�䣺2006-7-20
	 *               <p>
	 */
	public static void genNodeMap(TreeNode root, Map map) {
		FdcOrgTreeHelper.genNodeMap(root, map);
	}

	// ����projectTree���ҵ�unitIdΪRoot������
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
	 * ���������ɱ���Ŀ���²������в������²�������ģ����������ɼ�������Ŀ�����в�����Ŀ������ֻ����ʾ������Ŀ��
	 * 
	 * @author hpw date:2009-8-24
	 * @param isDevPrjFilter
	 * @return
	 */
	public void setDevPrjFilter(boolean isDevPrjFilter) {
		this.isDevPrjFilter = isDevPrjFilter;
	}

	/**
	 * ר�ù��˿��гɱ�
	 */
	private boolean isContainDevPrj = false;

	public void setDevPrjFilter(boolean isDevPrjFilter, boolean isContainDevPrj) {
		this.isDevPrjFilter = isDevPrjFilter;
		this.isContainDevPrj = isContainDevPrj;

	}

	// //////////////////////////////////////////////////////////////////////////
	// ��������Ż� by skyiter_wang 2014-05-06
	// --- end
	// //////////////////////////////////////////////////////////////////////////

}