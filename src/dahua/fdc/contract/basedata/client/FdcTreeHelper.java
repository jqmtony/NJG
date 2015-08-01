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
 * 房地产树 助手
 * 
 * @author 王正
 * @email skyiter@live.com
 */
public class FdcTreeHelper {

	private static final Logger logger = CoreUIObject.getLogger(FdcTreeHelper.class);

	// //////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////

	/**
	 * 构建树
	 * 
	 * @param kdTree
	 *            金蝶树；非空
	 * @param iTree
	 *            树实体接口；非空
	 * @return
	 * @throws Exception
	 */
	public static KDTree buildTree(KDTree kdTree, ITreeBase iTree) throws Exception {
		return buildTree(kdTree, iTree, true);
	}

	/**
	 * 构建树
	 * 
	 * @param kdTree
	 *            金蝶树；非空
	 * @param iTree
	 *            树实体接口；非空
	 * @param isVirtual
	 *            是否虚拟；如果为是，则初始化层级默认为TreeBuilderFactory.DEFAULT_INITIAL_LEVEL，否则默认为Integer .MAX_VALUE
	 * @return
	 * @throws Exception
	 */
	public static KDTree buildTree(KDTree kdTree, ITreeBase iTree, boolean isVirtual) throws Exception {
		int initialLevel = isVirtual ? TreeBuilderFactory.DEFAULT_INITIAL_LEVEL : Integer.MAX_VALUE;
		int expandLevel = TreeBuilderFactory.DEFAULT_EXPAND_LEVEL;

		return buildTree(kdTree, iTree, initialLevel, expandLevel, null, null);
	}

	/**
	 * 构建树
	 * 
	 * @param kdTree
	 *            金蝶树；非空
	 * @param iTree
	 *            树实体接口；非空
	 * @param filter
	 *            过滤器；可空
	 * @return
	 * @throws Exception
	 */
	public static KDTree buildTree(KDTree kdTree, ITreeBase iTree, FilterInfo filter) throws Exception {
		String rootName = null;

		return buildTree(kdTree, iTree, filter, rootName);
	}

	/**
	 * 构建树（带入根节点名称）
	 * 
	 * @param kdTree
	 *            金蝶树；非空
	 * @param iTree
	 *            树实体接口；非空
	 * @param filter
	 *            过滤器；可空
	 * @param rootName
	 *            根节点名称；可空
	 * @return
	 * @throws Exception
	 */
	public static KDTree buildTree(KDTree kdTree, ITreeBase iTree, FilterInfo filter, String rootName) throws Exception {
		int initialLevel = 10;
		int expandLevel = 10;

		return buildTree(kdTree, iTree, initialLevel, expandLevel, filter, rootName);
	}

	/**
	 * 构建树（带入根节点名称）
	 * 
	 * @param kdTree
	 *            金蝶树；非空
	 * @param iTree
	 *            树实体接口；非空
	 * @param initialLevel
	 *            初始化层级；正整数；如果为非负整数，则初始化层级默认为TreeBuilderFactory.DEFAULT_INITIAL_LEVEL
	 * @param expandLevel
	 *            展开层级；正整数；如果为非负整数，则展开层级默认为TreeBuilderFactory.DEFAULT_EXPAND_LEVEL
	 * @param filter
	 *            过滤器；可空
	 * @param rootName
	 *            根节点名称；可空
	 * @return
	 * @throws Exception
	 */
	public static KDTree buildTree(KDTree kdTree, ITreeBase iTree, int initialLevel, int expandLevel,
			FilterInfo filter, String rootName) throws Exception {
		return buildTree(kdTree, iTree, initialLevel, expandLevel, filter, null, rootName);
	}

	/**
	 * 构建树（带入根节点名称）
	 * 
	 * @param kdTree
	 *            金蝶树；非空
	 * @param iTree
	 *            树实体接口；非空
	 * @param initialLevel
	 *            初始化层级；正整数；如果为非负整数，则初始化层级默认为TreeBuilderFactory.DEFAULT_INITIAL_LEVEL
	 * @param expandLevel
	 *            展开层级；正整数；如果为非负整数，则展开层级默认为TreeBuilderFactory.DEFAULT_EXPAND_LEVEL
	 * @param filter
	 *            过滤器；可空
	 * @param selector
	 *            查询器；可空
	 * @param rootName
	 *            根节点名称；可空
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

		// 创建树构造器
		if (FdcTreeHelper.isEmpty(filter) && FdcTreeHelper.isEmpty(selector)) {
			treeBuilder = TreeBuilderFactory.createTreeBuilder(ctrl, initialLevel, expandLevel);
		} else if (FdcTreeHelper.isEmpty(selector)) {
			treeBuilder = TreeBuilderFactory.createTreeBuilder(ctrl, initialLevel, expandLevel, filter);
		} else {
			treeBuilder = TreeBuilderFactory.createTreeBuilder(ctrl, initialLevel, expandLevel, filter, selector);
		}

		// 构建树
		treeBuilder.buildTree(kdTree);

		return kdTree;
	}

	// //////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////

	/**
	 * 创建树
	 * 
	 * @param iTree
	 *            树实体接口；非空
	 * @return
	 * @throws Exception
	 */
	public static KDTree createTree(ITreeBase iTree) throws Exception {
		KDTree kdTree = new KDTree();

		buildTree(kdTree, iTree);

		return kdTree;
	}

	/**
	 * 创建树
	 * 
	 * @param iTree
	 *            树实体接口；非空
	 * @param isVirtual
	 *            是否虚拟；如果为是，则初始化层级默认为TreeBuilderFactory.DEFAULT_INITIAL_LEVEL，否则默认为Integer .MAX_VALUE
	 * @return
	 * @throws Exception
	 */
	public static KDTree createTree(ITreeBase iTree, boolean isVirtual) throws Exception {
		KDTree kdTree = new KDTree();

		buildTree(kdTree, iTree, isVirtual);

		return kdTree;
	}

	/**
	 * 创建树
	 * 
	 * @param iTree
	 *            树实体接口；非空
	 * @param filter
	 *            过滤器；可空
	 * @return
	 * @throws Exception
	 */
	public static KDTree createTree(ITreeBase iTree, FilterInfo filter) throws Exception {
		KDTree kdTree = new KDTree();

		buildTree(kdTree, iTree, filter);

		return kdTree;
	}

	/**
	 * 创建树,带入根节点名称
	 * 
	 * @param iTree
	 *            树实体接口；非空
	 * @param filter
	 *            过滤器；可空
	 * @param rootName
	 *            根节点名称；可空
	 * @return
	 * @throws Exception
	 */
	public static KDTree createTree(ITreeBase iTree, FilterInfo filter, String rootName) throws Exception {
		KDTree kdTree = new KDTree();

		buildTree(kdTree, iTree, filter, rootName);

		return kdTree;
	}

	/**
	 * 创建树,带入根节点名称
	 * 
	 * @param iTree
	 *            树实体接口；非空
	 * @param initialLevel
	 *            初始化层级；正整数；如果为非负整数，则初始化层级默认为TreeBuilderFactory.DEFAULT_INITIAL_LEVEL
	 * @param expandLevel
	 *            展开层级；正整数；如果为非负整数，则展开层级默认为TreeBuilderFactory.DEFAULT_EXPAND_LEVEL
	 * @param filter
	 *            过滤器；可空
	 * @param rootName
	 *            根节点名称；可空
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
	 * 创建树,带入根节点名称
	 * 
	 * @param iTree
	 *            树实体接口；非空
	 * @param initialLevel
	 *            初始化层级；正整数；如果为非负整数，则初始化层级默认为TreeBuilderFactory.DEFAULT_INITIAL_LEVEL
	 * @param expandLevel
	 *            展开层级；正整数；如果为非负整数，则展开层级默认为TreeBuilderFactory.DEFAULT_EXPAND_LEVEL
	 * @param filter
	 *            过滤器；可空
	 * @param selector
	 *            查询器；可空
	 * @param rootName
	 *            根节点名称；可空
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
	 * 是否是空的
	 * 
	 * @param filter
	 * @return
	 */
	private static boolean isEmpty(FilterInfo filter) {
		return (null == filter || filter.getFilterItems().isEmpty());
	}

	/**
	 * 是否是空的
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
	 * 取得选择的节点
	 * 
	 * @param kdTree
	 *            金蝶树；非空
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
	 * 取得选择的节点
	 * 
	 * @param kdTree
	 *            金蝶树；非空
	 * @return
	 */
	public static TreeBaseInfo getSelectedNodeObj(KDTree kdTree) {
		DefaultKingdeeTreeNode node = getSelectedNode(kdTree);

		TreeBaseInfo nodeObject = getNodeObj(node);

		return nodeObject;
	}

	/**
	 * 取得选择的节点用户对象
	 * 
	 * @param node
	 *            默认金蝶树节点；可空
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
	 * 取得选择节点叶子ID
	 * 
	 * @param kdTree
	 *            金蝶树；非空
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
	 * 取得
	 * 
	 * @param node
	 *            默认金蝶树节点；非空
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
