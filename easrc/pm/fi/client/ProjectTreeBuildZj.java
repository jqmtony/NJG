package com.kingdee.eas.port.pm.fi.client;

import java.awt.Color;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.assistant.ProjectCollection;
import com.kingdee.eas.basedata.assistant.ProjectFactory;
import com.kingdee.eas.basedata.assistant.ProjectInfo;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.framework.client.tree.DefaultLNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ILNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ITreeBuilder;
import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

public class ProjectTreeBuildZj {

	// parent UI
	private CoreUIObject ui;
	// ��������Ŀ��
	private KDTree projTree = new KDTree();

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

	/** �Ƿ񿪷���Ŀ */
	private boolean isDevPrjFilter = true;
	
	// �Ƿ�ȫ��
	private boolean isWholeAgeStage=false;

	/**
	 * 
	 * ���������ݵ�ǰ��֯���칤����Ŀ��������Ԫ��������Ŀ��
	 * 
	 * @param uiҪ���������б����ʵ��
	 * @param projectTree Ҫ�����KDTree
	 * @param actionOnload �б�����ActionOnLoad����this.actionOnLoad����
	 * @throws Exception
	 * @author:liupd ����ʱ�䣺2006-7-20
	 *               <p>
	 */
	public void build(CoreUI ui, KDTree projectTree, ItemAction actionOnLoad)throws Exception {
		final KDTree prjTree=projectTree;
		prjTree.addTreeWillExpandListener(new TreeWillExpandListener(){
			public void treeWillCollapse(TreeExpansionEvent event)
					throws ExpandVetoException {
			}
			public void treeWillExpand(TreeExpansionEvent event)
					throws ExpandVetoException {				
				updateTreeNodeColor(prjTree);
			}
			
		});
		build(ui, prjTree, actionOnLoad, null);
	}
	
	public void updateTreeNodeColor(KDTree projectTree){
		DefaultKingdeeTreeNode root=(DefaultKingdeeTreeNode)projectTree.getModel().getRoot();
		visitAllNodes(root);
	}
	public void visitAllNodes(DefaultKingdeeTreeNode node) {	   
	       if (node.getChildCount() >= 0) {
	           for (Enumeration e=node.children(); e.hasMoreElements(); ) {
	        	   DefaultKingdeeTreeNode n = (DefaultKingdeeTreeNode)e.nextElement();
	        	   if(n.getUserObject() instanceof ProjectInfo){
	        		   ProjectInfo info=(ProjectInfo)n.getUserObject();
	        		   n.setTextColor(Color.RED);
        			   n.setCustomIcon(EASResource.getIcon("imgTbtn_assistantaccount"));
	        	   }
	               visitAllNodes(n);
	           }
	       }
	   }


	public void build(CoreUI ui, KDTree projectTree, ItemAction actionOnLoad,String curOrgId) throws Exception {
		this.ui = ui;
		if (curOrgId == null && !noOrgIsolation) {
			curOrgId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
		}
		if(SysContext.getSysContext().getCurrentOrgUnit().getId().toString().equals("6vYAAAAAAQvM567U"))
			curOrgId = OrgConstants.DEF_CU_ID;
		
		TreeModel orgTreeModel = NewOrgUtils.getTreeModel(OrgViewType.CTRLUNIT,"", curOrgId, null, getActionPK(actionOnLoad));
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) orgTreeModel.getRoot();
		if (root.getUserObject() != null) {
			// �������֯���ǹ���Ԫ����ʾ
			OrgStructureInfo orgStru = (OrgStructureInfo) root.getUserObject();
			if (!orgStru.getUnit().isIsCU()) {
				MsgBox.showWarning("��֯���ǹ���Ԫ��");
				SysUtil.abort();
			}
			buildByOrgTree(projectTree, orgTreeModel);
		} else {
			buildByCurOrg(projectTree);
		}

	}
	/**
	 * 
	 * ��������ǰ��֯�ǹ���Ԫ
	 * 
	 * @param tree
	 * @throws Exception
	 * @author:liupd ����ʱ�䣺2006-9-14
	 *               <p>
	 */
	public void buildByCurOrg(KDTree orgTree) throws Exception {
		FilterInfo projFilter = null;
		//��������������ڼ��Ź�˾���Ǽ���CU������ʾ��ǰ��֯������Ŀ����ʾ������˾�����ʽ���Ŀ
		if(SysContext.getSysContext().getCurrentOrgUnit().getId().toString().equals("6vYAAAAAAQvM567U"))
		{
			FilterInfo filter = new FilterInfo();
			String orgUnitId = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
			filter.getFilterItems().add(new FilterItemInfo("company.id", orgUnitId));
			filter.getFilterItems().add(new FilterItemInfo("NJGyearInvest.fundSource.number", "ZJLY001"));
			if (projectTypeId != null) {
				filter.getFilterItems().add(new FilterItemInfo("projectType.id", projectTypeId));
				filter.setMaskString("#0 or (#1 and #2)");
			}
			else
			{
				filter.setMaskString("#0 or #1");
			}
		}
		else
		{
			projFilter = getProjectFilterByCurOrg();
			if (projectTypeId != null) {
				projFilter.getFilterItems().add(new FilterItemInfo("projectType.id", projectTypeId));
			}
		}
		
		
		buildProjTree(projFilter);
		TreeNode root = (TreeNode) projTree.getModel().getRoot();
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
	public FilterInfo getProjectFilterByCurOrg() throws BOSException , EASBizException {
		FilterInfo projFilter = new FilterInfo();
		String orgUnitId = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
		projFilter.getFilterItems().add(new FilterItemInfo("company.id", orgUnitId));
		return projFilter;
	}
	/**
	 * 
	 * ��������ǰ��֯�ǹ���Ԫ
	 * 
	 * @param orgTree
	 * @param orgTreeModel
	 * @throws Exception
	 * @author:liupd ����ʱ�䣺2006-9-14
	 * 
	 *               <p>
	 */
	public void buildByOrgTree(KDTree orgTree, TreeModel orgTreeModel)throws Exception {
		orgTree.setModel(orgTreeModel);
		authorizedOrgs = (Set) ActionCache.get("ContractListBaseUIHandler.authorizedOrgs");
		if (authorizedOrgs == null) {
			authorizedOrgs = new HashSet();
			Map orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(
					new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId()), OrgType.ControlUnit,null, null, null);
			if (orgs != null) {
				Set orgSet = orgs.keySet();
				Iterator it = orgSet.iterator();
				while (it.hasNext()) {
					authorizedOrgs.add(it.next());
				}
			}
		}
		TreeNode root = (TreeNode) orgTreeModel.getRoot();
		Set leafNodesIdSet = new HashSet();
		genLeafNodesIdSet(root, leafNodesIdSet);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("company", leafNodesIdSet,CompareType.INCLUDE));
		if (this.projectTypeId != null) {
			filter.getFilterItems().add(new FilterItemInfo("projectType.id", projectTypeId));
		}

		// ����������ʱ ���ӶԵ�ǰ�û���סȨ�޵Ĺ���
		FilterInfo authorizedOrgsFilter = new FilterInfo();
		if (authorizedOrgs == null || authorizedOrgs.size() == 0) {
			// û����Ȩ��ֻ��ʾû�����ĵ�����
			authorizedOrgsFilter.getFilterItems().add(new FilterItemInfo("company.id", null));
		} else {
			authorizedOrgsFilter.getFilterItems().add(new FilterItemInfo("company.id", authorizedOrgs,CompareType.INCLUDE));
		}
		filter.mergeFilter(authorizedOrgsFilter, "and");
		buildProjTree(filter);
		root = (TreeNode) projTree.getModel().getRoot();
		TreeNode orgRoot = (TreeNode) orgTreeModel.getRoot();
		if (orgRoot.getChildCount() == 0) {
			for (int i = 0; i < root.getChildCount(); i++) {
				DefaultKingdeeTreeNode projNode = (DefaultKingdeeTreeNode) root.getChildAt(i);
				orgTree.addNodeInto((MutableTreeNode) projNode,(MutableTreeNode) orgRoot);
				i--;
			}
		} else {
			for (int i = 0; i < root.getChildCount(); i++) {
				DefaultKingdeeTreeNode projNode = (DefaultKingdeeTreeNode) root.getChildAt(i);
				ProjectInfo projectInfo = (ProjectInfo) projNode.getUserObject();
				String orgId = projectInfo.getCompany().getId().toString();
				searchNodeByOrgId(orgRoot, orgId, orgTree, projNode,leafNodesIdSet);
				if (getIt) {
					i--;
				}
				getIt = false;
			}

		}
	}
	
	/**
	 *
	 * ������������֯����Ҷ�ӽڵ�
	 *
	 * @param node
	 * @param leafNodesIdSet
	 * @author:liupd ����ʱ�䣺2006-7-20
	 *               <p>
	 */
	public static void genLeafNodesIdSet(TreeNode node, Set leafNodesIdSet) {

		int count = node.getChildCount();
		// ���û���¼��ڵ㣬˵����ǰ��֯��ʵ�壬�ѵ�ǰ��֯id���ؼ���
		if (count == 0) {
			OrgStructureInfo orgStructureInfo = ((OrgStructureInfo) ((DefaultKingdeeTreeNode) node).getUserObject());
			String oid = orgStructureInfo.getUnit().getId().toString();
			leafNodesIdSet.add(oid);
			return;
		}
		DefaultKingdeeTreeNode treeNode = null;
		for (int i = 0; i < count; i++) {
			treeNode = (DefaultKingdeeTreeNode) node.getChildAt(i);
			if (treeNode.isLeaf()) {
				String id = ((OrgStructureInfo) treeNode.getUserObject()).getUnit().getId().toString();
				leafNodesIdSet.add(id);
			} else {
				genLeafNodesIdSet(treeNode, leafNodesIdSet);
			}
		}

	}
	/**
	 *
	 * ������������֯���Ľڵ�
	 *
	 * @param node
	 * @param leafNodesIdSet
	 * @author:liupd ����ʱ�䣺2006-7-20
	 *               <p>
	 */
	public static void genNodesIdSet(TreeNode node, Set leafNodesIdSet) {
		int count = node.getChildCount();
		// ���û���¼��ڵ㣬˵����ǰ��֯��ʵ�壬�ѵ�ǰ��֯id���ؼ���
		//����������һ��CU�¶��������֯���������˲��ܱ������node
		OrgStructureInfo orgStructureInfo = ((OrgStructureInfo) ((DefaultKingdeeTreeNode) node).getUserObject());
		String oid = orgStructureInfo.getUnit().getId().toString();
		leafNodesIdSet.add(oid);
		DefaultKingdeeTreeNode treeNode = null;
		for (int i = 0; i < count; i++) {
			treeNode = (DefaultKingdeeTreeNode) node.getChildAt(i);
			String id = ((OrgStructureInfo) treeNode.getUserObject()).getUnit().getId().toString();
			leafNodesIdSet.add(id);
			if (!treeNode.isLeaf()) {
				genNodesIdSet(treeNode, leafNodesIdSet);
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
	private void searchNodeByOrgId(TreeNode orgNode, String orgId,
			KDTree orgTree, TreeNode projNode, Set leafNodesIdSet) {
		if (getIt) {
			return;
		}
		/*
		 * ���Ӷ���֯�����ڵ�����������⵱ʵ�������֯��������ʵ�������֯ʱ���޷���ʾ�ϼ�ʵ�������֯�µ���Ŀ�����07.06.08
		 */
		Object obj = ((DefaultKingdeeTreeNode) orgNode).getUserObject();
		String orgRootId = ((OrgStructureInfo) obj).getUnit().getId().toString();
		if (orgRootId.equals(orgId)) {
			orgTree.addNodeInto((MutableTreeNode) projNode,(MutableTreeNode) orgNode);
			getIt = true;
			return;
		}

		int count = orgNode.getChildCount();
		DefaultKingdeeTreeNode treeNode = null;
		for (int i = 0; i < count; i++) {
			treeNode = (DefaultKingdeeTreeNode) orgNode.getChildAt(i);
			Object userObj = treeNode.getUserObject();
			if (userObj instanceof OrgStructureInfo
					&& leafNodesIdSet.contains(((OrgStructureInfo) userObj).getUnit().getId().toString())) {
				String orgId2 = ((OrgStructureInfo) userObj).getUnit().getId().toString();
				if (orgId2.equals(orgId)) {
					orgTree.addNodeInto((MutableTreeNode) projNode,(MutableTreeNode) treeNode);
					getIt = true;
					break;
				}
			} else {
				searchNodeByOrgId(treeNode, orgId, orgTree, projNode,leafNodesIdSet);
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
		KDTree treeMain = projTree;
		TreeSelectionListener[] listeners = treeMain.getTreeSelectionListeners();
		if (listeners.length > 0) {
			treeSelectionListener = listeners[0];
			treeMain.removeTreeSelectionListener(treeSelectionListener);
		}

		//��������������ڼ��Ź�˾���Ǽ���CU������ʾ��ǰ��֯������Ŀ����ʾ������˾�����ʽ���Ŀ
		if(SysContext.getSysContext().getCurrentOrgUnit().getId().toString().equals("6vYAAAAAAQvM567U"))
		{
			filter = new FilterInfo();
		    StringBuffer sqlbf = new StringBuffer();
		    sqlbf.append(" select a.fid from T_BD_Project a left join  CT_INV_YearInvestPlan b on a.CFNJGYearInvestID=b.fid");
			sqlbf.append(" left join CT_BAS_FundSource c on b.CFFundSourceID = c.fid where ((c.fnumber='ZJLY001' and a.FCompanyID<>'6vYAAAAAAQvM567U')");
			sqlbf.append(" OR a.FCompanyID='6vYAAAAAAQvM567U')");
			//���ǵ�����¼���Ŀ���ʽ���Ŀ ������������ʾ�����¼�������Ҳ ��ѯ��������ĿID
			sqlbf.append(" union all select a.fparentid from T_BD_Project a left join  CT_INV_YearInvestPlan b on a.CFNJGYearInvestID=b.fid");
			sqlbf.append(" left join CT_BAS_FundSource c on b.CFFundSourceID = c.fid where a.fparentid is not null and ((c.fnumber='ZJLY001' and a.FCompanyID<>'6vYAAAAAAQvM567U')");
			sqlbf.append(" OR a.FCompanyID='6vYAAAAAAQvM567U')");
			filter.getFilterItems().add(new FilterItemInfo("id",sqlbf.toString(),CompareType.INNER));
		}
		
		treeBuilder = TreeBuilderFactory.createTreeBuilder(getProjLNTreeNodeCtrl(), getTreeInitialLevel(), getTreeExpandLevel(), filter);
		treeBuilder.buildTree(treeMain);
//		TreeModel treeModel = null;
//		treeModel = new KingdeeTreeModel((TreeNode)((DefaultKingdeeTreeNode) treeMain.getModel().getRoot()).clone());
//		treeMain.setModel(treeModel);
		treeMain.addTreeSelectionListener(treeSelectionListener);
		treeMain.setShowPopMenuDefaultItem(false);
	}

	/**
	 * 
	 * ������һ��������ʵ���������޷�ʹ����ģʽȡ������ܲ�֧�֣������ֱ�Ӱѳ�ʼ������һ���Ƚϴ�������Ϳ����ƹ���ģʽȡ��
	 * ���������Ժ���ʱ��дһ�׻���������������ʵ��Ĺ������Ŀ��
	 * 
	 * @return
	 * @author:liupd ����ʱ�䣺2006-8-29
	 */
	private int getTreeInitialLevel() {
		return 50;
	}

	private int getTreeExpandLevel() {
		return 5;
	}

	private ILNTreeNodeCtrl getProjLNTreeNodeCtrl() throws Exception {
		return new DefaultLNTreeNodeCtrl(getProjTreeInterface());
	}

	private ITreeBase getProjTreeInterface() throws Exception {
		ITreeBase treeBase = ProjectFactory.getRemoteInstance();
		return treeBase;
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
	public void genNodeMap(TreeNode root, Map map) {
		int count = root.getChildCount();
		DefaultKingdeeTreeNode treeNode = null;
		for (int i = 0; i < count; i++) {
			treeNode = (DefaultKingdeeTreeNode) root.getChildAt(i);
			Object userObj = treeNode.getUserObject();

			String id = null;
			if (userObj instanceof OrgStructureInfo) {
				id = ((OrgStructureInfo) userObj).getUnit().getId().toString();
			} else if (userObj instanceof ProjectInfo) {
				id = ((ProjectInfo) userObj).getId().toString();
			}

			if (id != null) {
				map.put(id, treeNode);
			}
			genNodeMap(treeNode, map);
		}

	}

	// ����projectTree���ҵ�unitIdΪRoot������
	public static DefaultKingdeeTreeNode getDefaultKingdeeTreeNode(DefaultKingdeeTreeNode root, String unitId) {
		if (unitId == null) {
			return root;
		} else {
			Object obj = ((DefaultKingdeeTreeNode) root).getUserObject();
			if (!(obj instanceof OrgStructureInfo)) {
				return null;
			}
			String orgRootId = ((OrgStructureInfo) obj).getUnit().getId().toString();
			if (!orgRootId.equals(unitId)) {
				int count = root.getChildCount();
				DefaultKingdeeTreeNode treeNode = null;
				for (int i = 0; i < count; i++) {
					treeNode = (DefaultKingdeeTreeNode) root.getChildAt(i);
					obj = ((DefaultKingdeeTreeNode) treeNode).getUserObject();
					if (!(obj instanceof OrgStructureInfo)) {
						continue;
					}
					orgRootId = ((OrgStructureInfo) obj).getUnit().getId().toString();
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


}
