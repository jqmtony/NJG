package com.kingdee.eas.fdc.basedata.client;

import java.util.HashSet;
import java.util.Set;

import javax.swing.tree.DefaultTreeModel;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.ICostAccount;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.framework.client.tree.DefaultLNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ILNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ITreeBuilder;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;

/**
 * ���ز��� ����
 * 
 * @author ����
 * @email skyiter@live.com
 */
public class FdcTreeHelper {

	private static final Logger logger = CoreUIObject.getLogger(FdcTreeHelper.class);

	// //////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////

	/**
	 * ������
	 * 
	 * @param kdTree
	 *            ��������ǿ�
	 * @param iTree
	 *            ��ʵ��ӿڣ��ǿ�
	 * @return
	 * @throws Exception
	 */
	public static KDTree buildTree(KDTree kdTree, ITreeBase iTree) throws Exception {
		return buildTree(kdTree, iTree, true);
	}

	/**
	 * ������
	 * 
	 * @param kdTree
	 *            ��������ǿ�
	 * @param iTree
	 *            ��ʵ��ӿڣ��ǿ�
	 * @param isVirtual
	 *            �Ƿ����⣻���Ϊ�ǣ����ʼ���㼶Ĭ��ΪTreeBuilderFactory.DEFAULT_INITIAL_LEVEL������Ĭ��ΪInteger .MAX_VALUE
	 * @return
	 * @throws Exception
	 */
	public static KDTree buildTree(KDTree kdTree, ITreeBase iTree, boolean isVirtual) throws Exception {
		int initialLevel = isVirtual ? TreeBuilderFactory.DEFAULT_INITIAL_LEVEL : Integer.MAX_VALUE;
		int expandLevel = TreeBuilderFactory.DEFAULT_EXPAND_LEVEL;

		return buildTree(kdTree, iTree, initialLevel, expandLevel, null, null);
	}

	/**
	 * ������
	 * 
	 * @param kdTree
	 *            ��������ǿ�
	 * @param iTree
	 *            ��ʵ��ӿڣ��ǿ�
	 * @param filter
	 *            ���������ɿ�
	 * @return
	 * @throws Exception
	 */
	public static KDTree buildTree(KDTree kdTree, ITreeBase iTree, FilterInfo filter) throws Exception {
		String rootName = null;

		return buildTree(kdTree, iTree, filter, rootName);
	}

	/**
	 * ��������������ڵ����ƣ�
	 * 
	 * @param kdTree
	 *            ��������ǿ�
	 * @param iTree
	 *            ��ʵ��ӿڣ��ǿ�
	 * @param filter
	 *            ���������ɿ�
	 * @param rootName
	 *            ���ڵ����ƣ��ɿ�
	 * @return
	 * @throws Exception
	 */
	public static KDTree buildTree(KDTree kdTree, ITreeBase iTree, FilterInfo filter, String rootName) throws Exception {
		int initialLevel = 10;
		int expandLevel = 10;

		return buildTree(kdTree, iTree, initialLevel, expandLevel, filter, rootName);
	}

	/**
	 * ��������������ڵ����ƣ�
	 * 
	 * @param kdTree
	 *            ��������ǿ�
	 * @param iTree
	 *            ��ʵ��ӿڣ��ǿ�
	 * @param initialLevel
	 *            ��ʼ���㼶�������������Ϊ�Ǹ����������ʼ���㼶Ĭ��ΪTreeBuilderFactory.DEFAULT_INITIAL_LEVEL
	 * @param expandLevel
	 *            չ���㼶�������������Ϊ�Ǹ���������չ���㼶Ĭ��ΪTreeBuilderFactory.DEFAULT_EXPAND_LEVEL
	 * @param filter
	 *            ���������ɿ�
	 * @param rootName
	 *            ���ڵ����ƣ��ɿ�
	 * @return
	 * @throws Exception
	 */
	public static KDTree buildTree(KDTree kdTree, ITreeBase iTree, int initialLevel, int expandLevel,
			FilterInfo filter, String rootName) throws Exception {
		return buildTree(kdTree, iTree, initialLevel, expandLevel, filter, null, rootName);
	}

	/**
	 * ��������������ڵ����ƣ�
	 * 
	 * @param kdTree
	 *            ��������ǿ�
	 * @param iTree
	 *            ��ʵ��ӿڣ��ǿ�
	 * @param initialLevel
	 *            ��ʼ���㼶�������������Ϊ�Ǹ����������ʼ���㼶Ĭ��ΪTreeBuilderFactory.DEFAULT_INITIAL_LEVEL
	 * @param expandLevel
	 *            չ���㼶�������������Ϊ�Ǹ���������չ���㼶Ĭ��ΪTreeBuilderFactory.DEFAULT_EXPAND_LEVEL
	 * @param filter
	 *            ���������ɿ�
	 * @param selector
	 *            ��ѯ�����ɿ�
	 * @param rootName
	 *            ���ڵ����ƣ��ɿ�
	 * @return
	 * @throws Exception
	 */
	public static KDTree buildTree(KDTree kdTree, ITreeBase iTree, int initialLevel, int expandLevel,
			FilterInfo filter, SelectorItemCollection selector, String rootName) throws Exception {
		if (initialLevel < 1) {
			initialLevel = TreeBuilderFactory.DEFAULT_INITIAL_LEVEL;
		}
		if (expandLevel < 1) {
			expandLevel = TreeBuilderFactory.DEFAULT_EXPAND_LEVEL;
		}

		if (StringUtils.isBlank(rootName)) {
			rootName = MetaDataLoaderFactory.getRemoteMetaDataLoader().getBusinessObject(iTree.getType()).getAlias();
		}
		if (StringUtils.isBlank(rootName) && (iTree instanceof ICostAccount)) {
			rootName = FDCClientUtils.getRes("costAccount");
		}
		if (StringUtils.isBlank(rootName)) {
			rootName = "root";
		}

		KDTreeNode root = new KDTreeNode(rootName);
		((DefaultTreeModel) kdTree.getModel()).setRoot(root);

		ITreeBuilder treeBuilder = null;
		ILNTreeNodeCtrl ctrl = new DefaultLNTreeNodeCtrl(iTree);

		// ������������
		if (FdcTreeHelper.isEmpty(filter) && FdcTreeHelper.isEmpty(selector)) {
			treeBuilder = TreeBuilderFactory.createTreeBuilder(ctrl, initialLevel, expandLevel);
		} else if (FdcTreeHelper.isEmpty(selector)) {
			treeBuilder = TreeBuilderFactory.createTreeBuilder(ctrl, initialLevel, expandLevel, filter);
		} else {
			treeBuilder = TreeBuilderFactory.createTreeBuilder(ctrl, initialLevel, expandLevel, filter, selector);
		}

		// ������
		treeBuilder.buildTree(kdTree);

		return kdTree;
	}

	// //////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////

	/**
	 * ������
	 * 
	 * @param iTree
	 *            ��ʵ��ӿڣ��ǿ�
	 * @return
	 * @throws Exception
	 */
	public static KDTree createTree(ITreeBase iTree) throws Exception {
		KDTree kdTree = new KDTree();

		buildTree(kdTree, iTree);

		return kdTree;
	}

	/**
	 * ������
	 * 
	 * @param iTree
	 *            ��ʵ��ӿڣ��ǿ�
	 * @param isVirtual
	 *            �Ƿ����⣻���Ϊ�ǣ����ʼ���㼶Ĭ��ΪTreeBuilderFactory.DEFAULT_INITIAL_LEVEL������Ĭ��ΪInteger .MAX_VALUE
	 * @return
	 * @throws Exception
	 */
	public static KDTree createTree(ITreeBase iTree, boolean isVirtual) throws Exception {
		KDTree kdTree = new KDTree();

		buildTree(kdTree, iTree, isVirtual);

		return kdTree;
	}

	/**
	 * ������
	 * 
	 * @param iTree
	 *            ��ʵ��ӿڣ��ǿ�
	 * @param filter
	 *            ���������ɿ�
	 * @return
	 * @throws Exception
	 */
	public static KDTree createTree(ITreeBase iTree, FilterInfo filter) throws Exception {
		KDTree kdTree = new KDTree();

		buildTree(kdTree, iTree, filter);

		return kdTree;
	}

	/**
	 * ������,������ڵ�����
	 * 
	 * @param iTree
	 *            ��ʵ��ӿڣ��ǿ�
	 * @param filter
	 *            ���������ɿ�
	 * @param rootName
	 *            ���ڵ����ƣ��ɿ�
	 * @return
	 * @throws Exception
	 */
	public static KDTree createTree(ITreeBase iTree, FilterInfo filter, String rootName) throws Exception {
		KDTree kdTree = new KDTree();

		buildTree(kdTree, iTree, filter, rootName);

		return kdTree;
	}

	/**
	 * ������,������ڵ�����
	 * 
	 * @param iTree
	 *            ��ʵ��ӿڣ��ǿ�
	 * @param initialLevel
	 *            ��ʼ���㼶�������������Ϊ�Ǹ����������ʼ���㼶Ĭ��ΪTreeBuilderFactory.DEFAULT_INITIAL_LEVEL
	 * @param expandLevel
	 *            չ���㼶�������������Ϊ�Ǹ���������չ���㼶Ĭ��ΪTreeBuilderFactory.DEFAULT_EXPAND_LEVEL
	 * @param filter
	 *            ���������ɿ�
	 * @param rootName
	 *            ���ڵ����ƣ��ɿ�
	 * @return
	 * @throws Exception
	 */
	public static KDTree createTree(ITreeBase iTree, int initialLevel, int expandLevel, FilterInfo filter,
			String rootName) throws Exception {
		KDTree kdTree = new KDTree();

		buildTree(kdTree, iTree, initialLevel, expandLevel, filter, rootName);

		return kdTree;
	}

	/**
	 * ������,������ڵ�����
	 * 
	 * @param iTree
	 *            ��ʵ��ӿڣ��ǿ�
	 * @param initialLevel
	 *            ��ʼ���㼶�������������Ϊ�Ǹ����������ʼ���㼶Ĭ��ΪTreeBuilderFactory.DEFAULT_INITIAL_LEVEL
	 * @param expandLevel
	 *            չ���㼶�������������Ϊ�Ǹ���������չ���㼶Ĭ��ΪTreeBuilderFactory.DEFAULT_EXPAND_LEVEL
	 * @param filter
	 *            ���������ɿ�
	 * @param selector
	 *            ��ѯ�����ɿ�
	 * @param rootName
	 *            ���ڵ����ƣ��ɿ�
	 * @return
	 * @throws Exception
	 */
	public static KDTree createTree(ITreeBase iTree, int initialLevel, int expandLevel, FilterInfo filter,
			SelectorItemCollection selector, String rootName) throws Exception {
		KDTree kdTree = new KDTree();

		buildTree(kdTree, iTree, initialLevel, expandLevel, filter, selector, rootName);

		return kdTree;
	}

	// //////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////

	/**
	 * �Ƿ��ǿյ�
	 * 
	 * @param filter
	 * @return
	 */
	private static boolean isEmpty(FilterInfo filter) {
		return (null == filter || filter.getFilterItems().isEmpty());
	}

	/**
	 * �Ƿ��ǿյ�
	 * 
	 * @param objectCollection
	 * @return
	 */
	private static boolean isEmpty(IObjectCollection objectCollection) {
		return (null == objectCollection || objectCollection.isEmpty());
	}

	// //////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////

	/**
	 * ȡ��ѡ��Ľڵ�
	 * 
	 * @param kdTree
	 *            ��������ǿ�
	 * @return
	 */
	public static DefaultKingdeeTreeNode getSelectedNode(KDTree kdTree) {
		if (null != kdTree) {
			return (DefaultKingdeeTreeNode) kdTree.getLastSelectedPathComponent();
		} else {
			return null;
		}
	}

	/**
	 * ȡ��ѡ��Ľڵ�
	 * 
	 * @param kdTree
	 *            ��������ǿ�
	 * @return
	 */
	public static TreeBaseInfo getSelectedNodeObj(KDTree kdTree) {
		DefaultKingdeeTreeNode node = getSelectedNode(kdTree);

		TreeBaseInfo nodeObject = getNodeObj(node);

		return nodeObject;
	}

	/**
	 * ȡ��ѡ��Ľڵ��û�����
	 * 
	 * @param node
	 *            Ĭ�Ͻ�����ڵ㣻�ɿ�
	 * @return
	 */
	public static TreeBaseInfo getNodeObj(DefaultKingdeeTreeNode node) {
		if (node == null || node.getUserObject() == null || OrgViewUtils.isTreeNodeDisable(node)) {
			return null;
		}

		Object nodeObject = node.getUserObject();
		if (nodeObject instanceof OrgStructureInfo) {
			FullOrgUnitInfo fullOrgUnitInfo = ((OrgStructureInfo) nodeObject).getUnit();

			return fullOrgUnitInfo;
		} else if (nodeObject instanceof OrgUnitInfo) {
			OrgUnitInfo orgUnitInfo = (OrgUnitInfo) nodeObject;

			return orgUnitInfo;
		} else if (nodeObject instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();

			return projectInfo;
		} else if (nodeObject instanceof TreeBaseInfo) {
			TreeBaseInfo treeBaseInfo = (TreeBaseInfo) nodeObject;

			return treeBaseInfo;
		} else {
			return null;
		}
	}

	/**
	 * ȡ��ѡ��ڵ�Ҷ��ID
	 * 
	 * @param kdTree
	 *            ��������ǿ�
	 * @return
	 */
	public static Set getSelectedNodeLeafIds(KDTree kdTree) {
		Set idSet = new HashSet();

		DefaultKingdeeTreeNode node = getSelectedNode(kdTree);
		if (node == null || node.getUserObject() == null || OrgViewUtils.isTreeNodeDisable(node)) {
			return idSet;
		}

		getNodeIds(node, idSet);

		return idSet;
	}

	/**
	 * ȡ��
	 * 
	 * @param node
	 *            Ĭ�Ͻ�����ڵ㣻�ǿ�
	 * @param idSet
	 */
	public static void getNodeIds(DefaultKingdeeTreeNode node, Set idSet) {
		if (node.isLeaf()) {
			TreeBaseInfo nodeObject = getNodeObj(node);
			if (nodeObject instanceof IObjectValue) {
				String idStr = nodeObject.getId().toString();

				idSet.add(idStr);
			}
		} else {
			for (int i = 0; i < node.getChildCount(); i++) {
				DefaultKingdeeTreeNode child = (DefaultKingdeeTreeNode) node.getChildAt(i);
				getNodeIds(child, idSet);
			}
		}
	}

	// //////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////
}
