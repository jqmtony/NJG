package com.kingdee.eas.port.pm.invite.util;

import java.awt.Color;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.basedata.assistant.Project;
import com.kingdee.eas.basedata.assistant.ProjectCollection;
import com.kingdee.eas.basedata.assistant.ProjectFactory;
import com.kingdee.eas.basedata.assistant.ProjectInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitCollection;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.framework.client.tree.DefaultLNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ITreeBuilder;
import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

public class SLProjectTreeBuilder {
	private CoreUIObject ui;
	private KDTree projTree = new KDTree();
	private boolean noOrgIsolation;
	protected Set authorizedOrgs = null;
	private ITreeBuilder treeBuilder;

	public void build(CoreUI ui, KDTree projectTree, ItemAction actionOnLoad)
			throws Exception {
		KDTree prjTree = projectTree;
		CompanyOrgUnitInfo fiOrg = SysContext.getSysContext()
				.getCurrentFIUnit();
		if (fiOrg == null) {
			MsgBox.showWarning("请切财务组织！");
			SysUtil.abort();
		}
		build(ui, prjTree, actionOnLoad, SysContext.getSysContext()
				.getCurrentAdminUnit().getId().toString());
	}

	public void build(CoreUI ui, KDTree projectTree, ItemAction actionOnLoad,
			String curOrgId) throws Exception {
		this.ui = ui;

		buildProjectTree(projectTree, buildOrgTree(actionOnLoad, curOrgId));

		setCustomerIcon((TreeNode) projectTree.getModel().getRoot());
	}

	private static void setCustomerIcon(TreeNode treeNode) {
		DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) treeNode;
		if ((thisNode.getUserObject() instanceof ProjectInfo))
			thisNode.setCustomIcon(EASResource.getIcon("imgTbtn_assistantaccount"));
		int childCount = treeNode.getChildCount();
		while (childCount > 0) {
			setCustomerIcon(treeNode.getChildAt(childCount - 1));
			childCount--;
		}
	}

	private TreeModel buildOrgTree(ItemAction actionOnLoad, String curOrgId)
			throws Exception {
		OrgUnitInfo curOrgInfo = SysContext.getSysContext().getCurrentOrgUnit();
		if ((curOrgId == null) && (!this.noOrgIsolation)) {
			curOrgId = curOrgInfo.getId().toString();
		}
		this.authorizedOrgs = new HashSet();
		String oql = "select id where longnumber like'%"
				+ curOrgInfo.getLongNumber() + "%'";
		FullOrgUnitCollection coll = FullOrgUnitFactory.getRemoteInstance()
				.getFullOrgUnitCollection(oql);
		for (int i = 0; i < coll.size(); i++) {
			this.authorizedOrgs.add(coll.get(i).getId().toString());
		}
		DefaultKingdeeTreeNode root = new DefaultKingdeeTreeNode(curOrgInfo);
		root.removeAllChildren();
		root.removeFromParent();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("isleaf", Integer.valueOf(0)));
		filter.getFilterItems().add(
				new FilterItemInfo("isCompanyOrgUnit", Integer.valueOf(1)));
		filter.getFilterItems().add(
				new FilterItemInfo("isOUSealUp", Integer.valueOf(0)));
//		filter.getFilterItems().add(
//				new FilterItemInfo("isBizUnit", Integer.valueOf(1)));
		if ((this.authorizedOrgs == null) || (this.authorizedOrgs.size() == 0))
			filter.getFilterItems().add(new FilterItemInfo("id", null));
		else {
			filter.getFilterItems().add(
					new FilterItemInfo("id", this.authorizedOrgs,
							CompareType.INCLUDE));
		}
		ITreeBuilder treeBuilder = null;
		treeBuilder = TreeBuilderFactory.createTreeBuilder(
				new DefaultLNTreeNodeCtrl(FullOrgUnitFactory
						.getRemoteInstance()), 2147483647, 5, filter);
		KDTree tree = treeBuilder.buildTree(null);
		KDTree newKDTree = new KDTree((DefaultKingdeeTreeNode) root.clone());
		cloneTree((DefaultKingdeeTreeNode) newKDTree.getModel().getRoot(),
				(DefaultKingdeeTreeNode) tree.getModel().getRoot());
		return newKDTree.getModel();
	}

	public static AdminOrgUnitCollection getChildByParent(
			AdminOrgUnitInfo parent) throws BOSException {
		AdminOrgUnitCollection resutl = new AdminOrgUnitCollection();
		resutl.add(parent);
		EntityViewInfo evInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		evInfo.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("parent", parent.getId().toString()));
		AdminOrgUnitCollection collection = AdminOrgUnitFactory
				.getRemoteInstance().getAdminOrgUnitCollection(evInfo);
		int i = collection.size();
		do {
			AdminOrgUnitInfo adminOrgUnitInfo = collection.get(i);
			if (!adminOrgUnitInfo.isIsLeaf())
				resutl.addCollection(getChildByParent(adminOrgUnitInfo));
			i--;
		} while (i >= 0);

		resutl.addCollection(collection);
		return resutl;
	}

	private void buildProjectTree(KDTree projectTree, TreeModel newTreeModel)
			throws Exception {
		projectTree.setModel(newTreeModel);
		buildProjTree();// by 2014 05 07
		//    
		Map idNode = (Map) this.projTree.getUserObject();
		Map orgMap = new HashMap();
		genNodeMap((DefaultKingdeeTreeNode) newTreeModel.getRoot(), orgMap);
		//
		for (Iterator it = idNode.keySet().iterator(); it.hasNext();) {
			String id = (String) it.next();
			DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) idNode
					.get(id);
			ProjectInfo info = (ProjectInfo) proNode.getUserObject();
			String orgId = info.getCompany().getId().toString();
			DefaultKingdeeTreeNode orgNode = (DefaultKingdeeTreeNode) orgMap
					.get(orgId);
			proNode.setText(info.getName());
			if (orgNode != null)
				orgNode.add(proNode);
		}
	}

	public SLProjectTreeBuilder() {
		this.noOrgIsolation = false;
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

	private void buildProjTree() throws Exception {
		KDTree ProjecTree = this.projTree;
		Map idProjectNodes = new HashMap();

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filInfo = new FilterInfo();
		filInfo.getFilterItems().add(new FilterItemInfo("level", "1"));
		view.setFilter(filInfo);

		ProjectCollection projectColl = ProjectFactory.getRemoteInstance()
				.getProjectCollection(view);
		for (int i = 0; i < projectColl.size(); i++) {
			ProjectInfo info = projectColl.get(i);
			DefaultKingdeeTreeNode node = new DefaultKingdeeTreeNode();
			node.setUserObject(info);
			idProjectNodes.put(info.getId().toString(), node);
		}

		ProjecTree.setUserObject(idProjectNodes);
	}

	private void cloneTree(DefaultKingdeeTreeNode newNode,
			DefaultKingdeeTreeNode oriNode) {
		for (int i = 0; i < oriNode.getChildCount(); i++) {
			DefaultKingdeeTreeNode oriChild = (DefaultKingdeeTreeNode) oriNode
					.getChildAt(i);
			DefaultKingdeeTreeNode child = (DefaultKingdeeTreeNode) oriChild
					.clone();
			newNode.add(child);
			cloneTree(child, oriChild);
		}
	}

	private int getTreeInitialLevel() {
		return 50;
	}

	private int getTreeExpandLevel() {
		return 5;
	}

	public void genNodeMap(TreeNode root, Map map) {
		int count = root.getChildCount();
		DefaultKingdeeTreeNode treeNode = null;
		for (int i = 0; i < count; i++) {
			treeNode = (DefaultKingdeeTreeNode) root.getChildAt(i);
			Object userObj = treeNode.getUserObject();
			String id = null;
			if ((userObj instanceof FullOrgUnitInfo)) {
				id = ((FullOrgUnitInfo) userObj).getId().toString();
			}

			if (id != null) {
				map.put(id, treeNode);
			}

			genNodeMap(treeNode, map);
		}
	}

	public static DefaultKingdeeTreeNode getDefaultKingdeeTreeNode(
			DefaultKingdeeTreeNode root, String unitId) {
		if (unitId == null) {
			return root;
		}

		Object obj = root.getUserObject();

		if (!(obj instanceof OrgStructureInfo)) {
			return null;
		}

		String orgRootId = ((OrgStructureInfo) obj).getUnit().getId()
				.toString();
		if (!orgRootId.equals(unitId)) {
			int count = root.getChildCount();
			DefaultKingdeeTreeNode treeNode = null;
			for (int i = 0; i < count; i++) {
				treeNode = (DefaultKingdeeTreeNode) root.getChildAt(i);

				obj = treeNode.getUserObject();
				if (!(obj instanceof OrgStructureInfo)) {
					continue;
				}
				orgRootId = ((OrgStructureInfo) obj).getUnit().getId()
						.toString();

				if (!orgRootId.equals(unitId)) {
					DefaultKingdeeTreeNode value = getDefaultKingdeeTreeNode(
							treeNode, unitId);
					if (value != null)
						return value;
				} else {
					return treeNode;
				}
			}
		} else {
			return root;
		}

		return null;
	}
}