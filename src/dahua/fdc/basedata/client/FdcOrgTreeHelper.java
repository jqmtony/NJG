package com.kingdee.eas.fdc.basedata.client;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.tree.KingdeeTreeModel;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureCollection;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.client.tree.util.OrgTreeHelper;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.util.FdcCollectionUtil;
import com.kingdee.eas.fdc.basedata.util.FdcMapUtil;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 房地产组织树 助手
 * 
 * @author 王正
 * @email skyiter@live.com
 */
public class FdcOrgTreeHelper extends FdcTreeHelper {

	private static final Logger logger = CoreUIObject.getLogger(FdcOrgTreeHelper.class);

	// //////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////

	// 组织Map(有工程项目的的组织)
	public static Map orgMapWithProject = null;

	// 组织Set(有工程项目的的组织)
	public static Set orgSetWithProject = null;

	// 组织ID_Set(有工程项目的的组织)
	public static Set orgIdSetWithProject = null;

	// //////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////

	/**
	 * 取得根节点
	 * 
	 * @param treeModel
	 * @return
	 */
	public static DefaultKingdeeTreeNode getRoot(TreeModel treeModel) {
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) treeModel.getRoot();
		if (null == root) {
			return null;
		}

		if (root.getUserObject() == null && root.getChildCount() == 1) {
			root = (DefaultKingdeeTreeNode) root.getChildAt(0);
		}

		return root;
	}

	/**
	 * 取得根节点
	 * 
	 * @param tree
	 * @return
	 */
	public static DefaultKingdeeTreeNode getRoot(KDTree tree) {
		return getRoot(tree.getModel());
	}

	/**
	 * 转换成OrgStructure树 <br>
	 * 以前的树都是按照OrgStructure构建的
	 * 
	 * @param tree
	 * @throws BOSException
	 */
	public static void coverToOrgStructureTree(KDTree tree) throws BOSException {
		long startTime = 0;
		long endTime = 0;
		long exeTime = 0;

		startTime = System.currentTimeMillis();

		DefaultKingdeeTreeNode root = getRoot(tree);
		Map nodeMap = new LinkedHashMap();
		FdcOrgTreeHelper.genNodeMap(root, nodeMap, true);

		endTime = System.currentTimeMillis();
		exeTime = endTime - startTime;
		logger.info("21===========>FdcOrgTreeHelper.coverToOrgStructureTree(), genNodeMap, exeTime: " + exeTime);

		startTime = System.currentTimeMillis();

		OrgStructureCollection orgStructureCollection = new OrgStructureCollection();
		Set idSet = nodeMap.keySet();
		for (Iterator iterator = idSet.iterator(); iterator.hasNext();) {
			String id = (String) iterator.next();
			KDTreeNode treeNode = (KDTreeNode) nodeMap.get(id);
			Object nodeObject = treeNode.getUserObject();
			if (nodeObject instanceof CostCenterOrgUnitInfo) {
				CostCenterOrgUnitInfo costCenterOrgUnitInfo = (CostCenterOrgUnitInfo) nodeObject;

				OrgStructureInfo orgStructureInfo = FdcOrgTreeHelper.coverToOrgStructureInfo(costCenterOrgUnitInfo);

				CostCenterOrgUnitInfo prentOrgInfo = costCenterOrgUnitInfo.getParent();
				if (null != prentOrgInfo) {
					OrgStructureInfo parentOrgStructureInfo = FdcOrgTreeHelper.coverToOrgStructureInfo(prentOrgInfo);
					orgStructureInfo.setParent(parentOrgStructureInfo);
				}

				orgStructureCollection.add(orgStructureInfo);
			}
		}

		if (null != orgStructureCollection && !orgStructureCollection.isEmpty()) {
			DefaultKingdeeTreeNode defaultKingdeeTreeNode = OrgTreeHelper.buildOUTreeByCol(orgStructureCollection);
			KingdeeTreeModel newModel = new KingdeeTreeModel(defaultKingdeeTreeNode);
			newModel.setRoot(root);
			tree.setModel(newModel);

			tree.updateUI();
		}

		endTime = System.currentTimeMillis();
		exeTime = endTime - startTime;
		logger.info("21===========>FdcOrgTreeHelper.coverToOrgStructureTree(), exeTime: " + exeTime);
	}

	/**
	 * 转换成OrgStructure对象 <br>
	 * 以前的树都是按照OrgStructure构建的
	 * 
	 * @return
	 */
	public static OrgStructureInfo coverToOrgStructureInfo(CostCenterOrgUnitInfo costCenterOrgUnitInfo) {
		// 树形结构需要7个字段:id,name,number,longNumber,isLeaf,level, parent.id

		OrgStructureInfo orgStructureInfo = new OrgStructureInfo();
		orgStructureInfo.setId(costCenterOrgUnitInfo.getId());
		// orgStructureInfo.setName(costCenterOrgUnitInfo.getName());
		orgStructureInfo.setLongNumber(costCenterOrgUnitInfo.getLongNumber());
		orgStructureInfo.setLevel(costCenterOrgUnitInfo.getLevel());
		orgStructureInfo.setIsLeaf(costCenterOrgUnitInfo.isIsLeaf());
		orgStructureInfo.setUnit(costCenterOrgUnitInfo);

		return orgStructureInfo;
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * 描述：生成指定节点及下级节点的Map[id, node]
	 * 
	 * @param root
	 * @param map
	 */
	public static void genNodeMap(TreeNode root, Map map) {
		int count = root.getChildCount();
		DefaultKingdeeTreeNode treeNode = null;
		for (int i = 0; i < count; i++) {
			treeNode = (DefaultKingdeeTreeNode) root.getChildAt(i);
			Object userObj = treeNode.getUserObject();

			String id = null;
			if (userObj instanceof OrgStructureInfo) {
				id = ((OrgStructureInfo) userObj).getUnit().getId().toString();
			} else if (userObj instanceof CostCenterOrgUnitInfo) {
				id = ((CostCenterOrgUnitInfo) userObj).getId().toString();
			} else if (userObj instanceof CurProjectInfo) {
				id = ((CurProjectInfo) userObj).getId().toString();
			}

			if (id != null) {
				map.put(id, treeNode);
			}

			genNodeMap(treeNode, map);
		}

	}

	/**
	 * 生成指定节点及下级节点的Map[id, node]
	 * 
	 * @param root
	 * @param nodeMap
	 * @param isGenSelf
	 */
	public static void genNodeMap(TreeNode root, Map nodeMap, boolean isGenSelf) {
		if (isGenSelf) {
			Object userObj = ((DefaultKingdeeTreeNode) root).getUserObject();
			String id = null;
			if (userObj instanceof OrgStructureInfo) {
				id = ((OrgStructureInfo) userObj).getUnit().getId().toString();
			} else if (userObj instanceof CostCenterOrgUnitInfo) {
				id = ((CostCenterOrgUnitInfo) userObj).getId().toString();
			} else if (userObj instanceof CurProjectInfo) {
				id = ((CurProjectInfo) userObj).getId().toString();
			}
			nodeMap.put(id, root);
		}

		// 生成指定节点及下级节点的Map
		FdcOrgTreeHelper.genNodeMap(root, nodeMap);
	}

	/**
	 * 取得组织对象
	 * 
	 * @param node
	 *            树形节点
	 */
	public static OrgUnitInfo getOrgInfo(DefaultKingdeeTreeNode node) {
		OrgUnitInfo orgUnitInfo = null;

		if (null == node) {
			return orgUnitInfo;
		}

		Object userObject = node.getUserObject();
		orgUnitInfo = getOrgInfo(userObject);

		return orgUnitInfo;
	}

	/**
	 * 取得组织对象
	 * 
	 * @param userObject
	 *            用户对象
	 */
	public static OrgUnitInfo getOrgInfo(Object userObject) {
		OrgUnitInfo orgUnitInfo = null;

		if (userObject instanceof OrgUnitInfo) {
			orgUnitInfo = (OrgUnitInfo) userObject;
		} else if (userObject instanceof OrgStructureInfo) {
			FullOrgUnitInfo fullOrgUnitInfo = ((OrgStructureInfo) userObject).getUnit();

			if (null != fullOrgUnitInfo) {
				orgUnitInfo = fullOrgUnitInfo;
			}
		}

		return orgUnitInfo;
	}

	/**
	 * 取得组织ID
	 * 
	 * @param node
	 *            树形节点
	 */
	public static String getOrgId(DefaultKingdeeTreeNode node) {
		String orgId = null;

		OrgUnitInfo orgUnitInfo = getOrgInfo(node);
		if (null != orgUnitInfo) {
			orgId = orgUnitInfo.getId().toString();
		}

		return orgId;
	}

	/**
	 * 取得组织ID
	 * 
	 * @param node
	 *            用户对象
	 */
	public static String getOrgId(Object userObject) {
		String orgId = null;

		if (userObject instanceof OrgUnitInfo) {
			orgId = ((OrgUnitInfo) userObject).getId().toString();
		} else if (userObject instanceof OrgStructureInfo) {
			FullOrgUnitInfo tempObject = ((OrgStructureInfo) userObject).getUnit();
			if (null != tempObject) {
				orgId = tempObject.getId().toString();
			}
		}

		return orgId;
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * 生成角色所有组织范围
	 * 
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static Set genAuthorizedOrgs() throws BOSException, EASBizException {
		Set authorizedOrgs = new HashSet();

		long startTime;
		long endTime;
		long exeTime;

		startTime = System.currentTimeMillis();
		Set authorizedOrgsCache = (Set) ActionCache.get("FdcOrgTreeHelper.authorizedOrgs");
		endTime = System.currentTimeMillis();

		exeTime = endTime - startTime;
		logger
				.info("4=================>FdcOrgTreeHelper.getAuthorizedOrgs(), ActionCache.get(FdcOrgTreeHelper.authorizedOrgs), exeTime:"
						+ exeTime);

		if (FdcCollectionUtil.isNotEmpty(authorizedOrgsCache)) {
			authorizedOrgs.addAll(authorizedOrgsCache);
		} else {
			startTime = System.currentTimeMillis();

			UserInfo currentUserInfo = SysContext.getSysContext().getCurrentUserInfo();
			Map orgMap = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(
					new ObjectUuidPK(currentUserInfo.getId()), OrgType.CostCenter, null, null, null);

			if (FdcMapUtil.isNotEmpty(orgMap)) {
				authorizedOrgs.addAll(orgMap.keySet());
			}

			endTime = System.currentTimeMillis();
			exeTime = endTime - startTime;
			logger
					.info("4=================>FdcOrgTreeHelper.getAuthorizedOrgs(), PermissionFactory.getRemoteInstance().getAuthorizedOrgs. exeTime:"
							+ exeTime);

		}

		if (FdcCollectionUtil.isNotEmpty(authorizedOrgs)) {
			// 添加动作缓存
			addActionCache("FdcOrgTreeHelper.authorizedOrgs", authorizedOrgs);
		}

		int authorizedOrgsSize = (FdcCollectionUtil.isNotEmpty(authorizedOrgs)) ? authorizedOrgs.size() : 0;
		logger.info("4=================>FdcOrgTreeHelper.getAuthorizedOrgs(), authorizedOrgs.size():"
				+ authorizedOrgsSize);

		return authorizedOrgs;
	}

	/**
	 * 添加动作缓存
	 * 
	 * @param key
	 * @param value
	 */
	public static void addActionCache(String key, Object value) {
		Map res = new HashMap();
		res.put(key, value);

		// 缓存适合于当前用户登陆后变化不多的数据
		ActionCache.put(res);
	}

	/**
	 * 取得角色组织范围
	 * 
	 * @param currentOrgUnitInfo
	 *            当前组织
	 * @param currentUserInfo
	 *            当前用户
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static Set getAuthorizedOrgs(OrgUnitInfo currentOrgUnitInfo, UserInfo currentUserInfo) throws BOSException,
			EASBizException {
		Set authorizedOrgs = new HashSet();

		long startTime;
		long endTime;
		long exeTime;

		startTime = System.currentTimeMillis();

		// ///////////////////////////////////////////////////////

		BOSUuid cuId = null;
		if (currentOrgUnitInfo.isIsCU()) {
			cuId = currentOrgUnitInfo.getId();
		} else if (null == currentOrgUnitInfo.getCU()) {
			SelectorItemCollection selectorItemCollection = new SelectorItemCollection();
			selectorItemCollection.add("id");
			selectorItemCollection.add("name");
			selectorItemCollection.add("parent.id");
			selectorItemCollection.add("parent.name");
			selectorItemCollection.add("cu.id");
			selectorItemCollection.add("cu.name");
			selectorItemCollection.add("isCU");

			BOSUuid id = currentOrgUnitInfo.getId();
			OrgUnitInfo tempCurrentOrgUnitInfo = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(
					new ObjectUuidPK(id), selectorItemCollection);
			
			if (tempCurrentOrgUnitInfo.isIsCU()) {
				cuId = tempCurrentOrgUnitInfo.getId();
			} else {
				cuId = tempCurrentOrgUnitInfo.getCU().getId();
			}
		} else {
			cuId = currentOrgUnitInfo.getCU().getId();
		}

		FullOrgUnitCollection fullOrgUnitCollection = PermissionFactory.getRemoteInstance()
				.getAuthorizedOrgOfIncludeSubCU(new ObjectUuidPK(cuId), new ObjectUuidPK(currentUserInfo.getId()),
						OrgType.CostCenter, null, null, null);

		if (null != fullOrgUnitCollection && !fullOrgUnitCollection.isEmpty()) {
			FullOrgUnitInfo fullOrgUnitInfo = null;
			String orgId = null;
			for (int i = 0, size = fullOrgUnitCollection.size(); i < size; i++) {
				fullOrgUnitInfo = fullOrgUnitCollection.get(i);
				orgId = fullOrgUnitInfo.getId().toString();

				authorizedOrgs.add(orgId);
			}
		}

		endTime = System.currentTimeMillis();
		exeTime = endTime - startTime;

		// ///////////////////////////////////////////////////////

		int authorizedOrgsSize = (FdcCollectionUtil.isNotEmpty(authorizedOrgs)) ? authorizedOrgs.size() : 0;
		logger
				.info("4=================>FdcOrgTreeHelper.getAuthorizedOrgs(currentOrgUnitInfo, currentUserInfo), authorizedOrgs.size():"
						+ authorizedOrgsSize);
		logger
				.info("4=================>FdcOrgTreeHelper.getAuthorizedOrgs(currentOrgUnitInfo, currentUserInfo), exeTime:"
						+ exeTime);

		return authorizedOrgs;
	}

	/**
	 * 取得角色组织范围
	 * 
	 * @param currentOrgUnitInfo
	 *            当前组织
	 * @param currentUserInfo
	 *            当前用户
	 * @param hasProject
	 *            是否有工程项目
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static Set getAuthorizedOrgs(OrgUnitInfo currentOrgUnitInfo, UserInfo currentUserInfo, boolean hasProject)
			throws BOSException, EASBizException {
		long startTime;
		long endTime;
		long exeTime;

		startTime = System.currentTimeMillis();
		Set authorizedOrgs = getAuthorizedOrgs(currentOrgUnitInfo, currentUserInfo);

		if (FdcCollectionUtil.isNotEmpty(authorizedOrgs) && hasProject) {
			// 取得当前及其下级组织ID_Set(有工程项目的的组织)
			Set subOrgIdSetWithProjectOfIncludeSub = getOrgIdSetWithProjectOfIncludeSub(currentOrgUnitInfo);

			authorizedOrgs.retainAll(subOrgIdSetWithProjectOfIncludeSub);
		}

		endTime = System.currentTimeMillis();
		exeTime = endTime - startTime;

		// ///////////////////////////////////////////////////////

		int authorizedOrgsSize = (FdcCollectionUtil.isNotEmpty(authorizedOrgs)) ? authorizedOrgs.size() : 0;
		logger
				.info("4=================>FdcOrgTreeHelper.getAuthorizedOrgs(currentOrgUnitInfo, currentUserInfo, hasProject), authorizedOrgs.size():"
						+ authorizedOrgsSize);
		logger
				.info("4=================>FdcOrgTreeHelper.getAuthorizedOrgs(currentOrgUnitInfo, currentUserInfo, hasProject), exeTime:"
						+ exeTime);

		return authorizedOrgs;
	}

	/**
	 * 生成组织映射(有工程项目的的组织)
	 * <p>
	 * key:组织长编码,value:组织info
	 * 
	 * @return
	 * @throws BOSException
	 */
	public static Map genOrgMapWithProject() throws BOSException {
		if (FdcMapUtil.isNotEmpty(orgMapWithProject)) {
			logger.info("4=================>FdcOrgTreeHelper.genOrgMapWithProject(), hasInit");
			logger.info("4=================>FdcOrgTreeHelper.genOrgMapWithProject(), exeTime:" + 0);

			return orgMapWithProject;
		} else if (null == orgMapWithProject) {
			logger.info("4=================>FdcOrgTreeHelper.genOrgMapWithProject(), notInit");

			orgMapWithProject = new TreeMap();
			orgSetWithProject = new HashSet();
			orgIdSetWithProject = new HashSet();
		}

		long startTime;
		long endTime;
		long exeTime;

		startTime = System.currentTimeMillis();

		// ///////////////////////////////////////////////////////

		EntityViewInfo entityViewInfo;
		SelectorItemCollection selectorItemCollection;
		FilterInfo filterInfo;

		entityViewInfo = new EntityViewInfo();

		selectorItemCollection = new SelectorItemCollection();
		selectorItemCollection.add("id");
		selectorItemCollection.add("name");
		selectorItemCollection.add("longNumber");
		selectorItemCollection.add("fullOrgUnit.id");
		selectorItemCollection.add("fullOrgUnit.name");
		selectorItemCollection.add("fullOrgUnit.longNumber");
		entityViewInfo.setSelector(selectorItemCollection);

		filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new FilterItemInfo("parent.id", null));
		entityViewInfo.setFilter(filterInfo);

		CurProjectCollection curProjectCollection = CurProjectFactory.getRemoteInstance().getCurProjectCollection(
				entityViewInfo);

		CurProjectInfo curProjectInfo = null;
		String orgId = null;
		String orgLongNumber = null;
		FullOrgUnitInfo fullOrgUnitInfo = null;
		for (int i = 0, size = curProjectCollection.size(); i < size; i++) {
			curProjectInfo = curProjectCollection.get(i);
			fullOrgUnitInfo = curProjectInfo.getFullOrgUnit();
			if (null == fullOrgUnitInfo) {
				continue;
			}

			orgId = fullOrgUnitInfo.getId().toString();
			orgLongNumber = fullOrgUnitInfo.getLongNumber();
			if (null == orgLongNumber) {
				continue;
			}

			orgMapWithProject.put(orgLongNumber, fullOrgUnitInfo);
			orgSetWithProject.add(fullOrgUnitInfo);
			orgIdSetWithProject.add(orgId);
		}

		endTime = System.currentTimeMillis();
		exeTime = endTime - startTime;

		// ///////////////////////////////////////////////////////

		logger.info("4=================>FdcOrgTreeHelper.genOrgMapWithProject(), exeTime:" + exeTime);

		return orgMapWithProject;
	}

	/**
	 * 取得当前及其下级组织映射(有工程项目的的组织)
	 * <p>
	 * key:组织长编码,value:组织info
	 * 
	 * @param currentOrgUnitInfo
	 *            当前组织
	 * @return
	 * @throws BOSException
	 */
	public static Map getOrgMapWithProjectOfIncludeSub(OrgUnitInfo currentOrgUnitInfo) throws BOSException {
		Map subOrgMapWithProjectOfIncludeSub = new TreeMap();

		Map orgMapWithProject = genOrgMapWithProject();
		if (FdcMapUtil.isNotEmpty(orgMapWithProject)) {
			String currentLongNumber = currentOrgUnitInfo.getLongNumber();

			Set orgSetWithProject = orgMapWithProject.keySet();
			String longNumber = null;
			FullOrgUnitInfo fullOrgUnitInfo = null;
			for (Iterator iterator = orgSetWithProject.iterator(); iterator.hasNext();) {
				longNumber = (String) iterator.next();

				if (longNumber.equals(currentLongNumber) || longNumber.startsWith(currentLongNumber + "!")) {
					fullOrgUnitInfo = (FullOrgUnitInfo) orgMapWithProject.get(longNumber);

					subOrgMapWithProjectOfIncludeSub.put(longNumber, fullOrgUnitInfo);
				}

			}
		}

		return subOrgMapWithProjectOfIncludeSub;
	}

	/**
	 * 取得当前及其下级组织ID_Set(有工程项目的的组织)
	 * 
	 * @param currentOrgUnitInfo
	 *            当前组织
	 * @return
	 * @throws BOSException
	 */
	public static Set getOrgIdSetWithProjectOfIncludeSub(OrgUnitInfo currentOrgUnitInfo) throws BOSException {
		Set orgIdSetWithProjectOfIncludeSub = new TreeSet();

		Map subOrgMapWithProjectOfIncludeSub = getOrgMapWithProjectOfIncludeSub(currentOrgUnitInfo);
		if (FdcMapUtil.isNotEmpty(subOrgMapWithProjectOfIncludeSub)) {
			String currentLongNumber = currentOrgUnitInfo.getLongNumber();

			Set orgSetWithProject = subOrgMapWithProjectOfIncludeSub.keySet();
			String longNumber = null;
			FullOrgUnitInfo fullOrgUnitInfo = null;
			for (Iterator iterator = orgSetWithProject.iterator(); iterator.hasNext();) {
				longNumber = (String) iterator.next();

				if (longNumber.startsWith(currentLongNumber)) {
					fullOrgUnitInfo = (FullOrgUnitInfo) subOrgMapWithProjectOfIncludeSub.get(longNumber);

					orgIdSetWithProjectOfIncludeSub.add(fullOrgUnitInfo.getId().toString());
				}

			}
		}

		return orgIdSetWithProjectOfIncludeSub;
	}

	/**
	 * 取得 组织映射(有工程项目的的组织)
	 * 
	 * @return
	 * @throws BOSException
	 */
	public static Map getOrgMapWithProject() throws BOSException {
		genOrgMapWithProject();

		return orgMapWithProject;
	}

	/**
	 * 取得 组织Set(有工程项目的的组织)
	 * 
	 * @return
	 * @throws BOSException
	 */
	public static Set getOrgSetWithProject() throws BOSException {
		genOrgMapWithProject();

		return orgSetWithProject;
	}

	/**
	 * 取得 组织ID_Set(有工程项目的的组织)
	 * 
	 * @return
	 * @throws BOSException
	 */
	public static Set getOrgIdSetWithProject() throws BOSException {
		genOrgMapWithProject();

		return orgIdSetWithProject;
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * 是否组织结构节点
	 * 
	 * @param node
	 * @return
	 */
	public static boolean isOrgStructureNode(DefaultKingdeeTreeNode node) {
		boolean flag = false;

		if (null != node) {
			Object nodeObject = node.getUserObject();
			flag = (nodeObject instanceof OrgStructureInfo);
		}

		return flag;
	}

	/**
	 * 是否组织单元节点
	 * 
	 * @param node
	 * @return
	 */
	public static boolean isOrgUnitNode(DefaultKingdeeTreeNode node) {
		boolean flag = false;

		if (null != node) {
			Object nodeObject = node.getUserObject();
			flag = (nodeObject instanceof OrgUnitInfo);
		}

		return flag;
	}

	/**
	 * 是否成本中心节点
	 * 
	 * @param node
	 * @return
	 */
	public static boolean isCostCenterOrgUnitNode(DefaultKingdeeTreeNode node) {
		boolean flag = false;

		if (null != node) {
			Object nodeObject = node.getUserObject();

			flag = (nodeObject instanceof CostCenterOrgUnitInfo);
			if (!flag && (nodeObject instanceof OrgStructureInfo)) {
				FullOrgUnitInfo tempNodeObject = ((OrgStructureInfo) nodeObject).getUnit();

				flag = (null != tempNodeObject) && tempNodeObject.isIsCostOrgUnit();
			}
			// if (!flag && (nodeObject instanceof OrgUnitInfo)) {
			// flag = ((OrgUnitInfo) nodeObject).isIsCostOrgUnit();
			// }
		}

		return flag;
	}

	/**
	 * 是否组织结构或组织单元节点
	 * 
	 * @param node
	 * @return
	 */
	public static boolean isOrgUnitOrOrgStructureNode(DefaultKingdeeTreeNode node) {
		boolean flag = false;

		if (null != node) {
			flag = isOrgUnitNode(node) || isOrgStructureNode(node);
		}

		return flag;
	}

	/**
	 * 是否组织结构或组织单元节点
	 * 
	 * @param node
	 * @return
	 */
	public static boolean isOrgUnitOrOrgStructureObject(Object userObject) {
		boolean flag = false;

		flag = (userObject instanceof OrgUnitInfo) || (userObject instanceof OrgStructureInfo);

		return flag;
	}

	/**
	 * 是否组织结构或成本中心节点
	 * 
	 * @param node
	 * @return
	 */
	public static boolean isCostCenterOrgUnitOrOrgStructureNode(DefaultKingdeeTreeNode node) {
		boolean flag = false;

		if (null != node) {
			flag = isCostCenterOrgUnitNode(node) || isOrgStructureNode(node);
		}

		return flag;
	}

	/**
	 * 是否组织结构或成本中心对象
	 * 
	 * @param node
	 * @return
	 */
	public static boolean isCostCenterOrgUnitOrOrgStructureObject(Object userObject) {
		boolean flag = false;

		flag = (userObject instanceof CostCenterOrgUnitInfo) || (userObject instanceof OrgStructureInfo);

		return flag;
	}

	/**
	 * 是否工程项目节点
	 * 
	 * @param node
	 * @return
	 */
	public static boolean isProjectNode(DefaultKingdeeTreeNode node) {
		boolean flag = false;

		if (null != node) {
			Object nodeObject = node.getUserObject();
			flag = (nodeObject instanceof CurProjectInfo);
		}

		return flag;
	}

	/**
	 * 取得当前项目对象
	 * 
	 * @param node
	 *            树形节点
	 */
	public static CurProjectInfo getCurProjectInfo(DefaultKingdeeTreeNode node) {
		CurProjectInfo curProjectInfo = null;

		if (null == node) {
			return curProjectInfo;
		}

		Object nodeObject = node.getUserObject();
		if (nodeObject instanceof CurProjectInfo) {
			curProjectInfo = (CurProjectInfo) nodeObject;
		}

		return curProjectInfo;
	}

	/**
	 * 取得当前项目对象
	 * 
	 * @param userObject
	 *            用户对象
	 */
	public static CurProjectInfo getCurProjectInfo(Object userObject) {
		CurProjectInfo curProjectInfo = null;

		if (userObject instanceof CurProjectInfo) {
			curProjectInfo = (CurProjectInfo) userObject;
		}

		return curProjectInfo;
	}

	/**
	 * 取得当前项目ID
	 * 
	 * @param node
	 *            树形节点
	 */
	public static String getCurProjectId(DefaultKingdeeTreeNode node) {
		String projectId = null;

		CurProjectInfo orgUnitInfo = getCurProjectInfo(node);
		if (null != orgUnitInfo) {
			projectId = orgUnitInfo.getId().toString();
		}

		return projectId;
	}

	/**
	 * 取得当前项目ID
	 * 
	 * @param node
	 *            用户对象
	 */
	public static String getCurProjectId(Object userObject) {
		String projectId = null;

		if (userObject instanceof CurProjectInfo) {
			projectId = ((CurProjectInfo) userObject).getId().toString();
		}

		return projectId;
	}

	/**
	 * 检查是否是成本中心
	 * 
	 * @param node
	 *            树节点
	 * @param isMustCostCenterInfo
	 *            是否必须是成本中心对象
	 */
	public static void checkIsCostCenter(DefaultKingdeeTreeNode node, boolean isMustCostCenterInfo) {
		Object nodeObject = node.getUserObject();

		checkIsCostCenter(nodeObject, isMustCostCenterInfo);
	}

	/**
	 * 检查是否是成本中心
	 * 
	 * @param userObject
	 *            用户对象
	 * @param isMustCostCenterInfo
	 *            是否必须是成本中心对象
	 */
	public static void checkIsCostCenter(Object userObject, boolean isMustCostCenterInfo) {
		OrgUnitInfo orgUnitInfo = FdcOrgTreeHelper.getOrgInfo(userObject);
		boolean flag = false;
		if (null != orgUnitInfo) {
			flag = (!isMustCostCenterInfo && orgUnitInfo.isIsCostOrgUnit())
					|| (orgUnitInfo instanceof CostCenterOrgUnitInfo);
		}

		// 如果根组织不是成本中心，提示
		if (!flag) {
			MsgBox.showWarning(FDCClientUtils.getRes("needCostcenter"));
			SysUtil.abort();
		}
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * 删除没有组织的项目
	 * 
	 * @param node
	 * @param authorizedOrgIds
	 */
	public static void removeOrgWithOutPermision(DefaultKingdeeTreeNode node, Set authorizedOrgIds) {
		if (node == null) {
			return;
		}

		for (int i = 0; i < node.getChildCount(); i++) {
			DefaultKingdeeTreeNode curNode = (DefaultKingdeeTreeNode) node.getChildAt(i);
			OrgUnitInfo orgUnitInfo = FdcOrgTreeHelper.getOrgInfo(curNode);
			if (!orgUnitInfo.isIsCostOrgUnit() || !authorizedOrgIds.contains(orgUnitInfo.getId().toString())) {
				node.remove(i);
				i--;
				continue;
			}
			if (curNode.getChildCount() > 0) {
				removeOrgWithOutPermision(curNode, authorizedOrgIds);
			}
		}

	}

	/**
	 * 去掉没有成本中心组织属性的节点
	 * 
	 * @param node
	 *            树节点
	 */
	public static void removeNoneCostCenterNode(DefaultKingdeeTreeNode node) {
		if (node == null) {
			return;
		}

		DefaultKingdeeTreeNode childNode = null;
		OrgUnitInfo orgUnitInfo = null;
		boolean flag = false;
		for (int i = 0; i < node.getChildCount(); i++) {
			childNode = (DefaultKingdeeTreeNode) node.getChildAt(i);
			orgUnitInfo = getOrgInfo(childNode);
			flag = false;
			if (null != orgUnitInfo) {
				flag = (orgUnitInfo.isIsCostOrgUnit()) || (orgUnitInfo instanceof CostCenterOrgUnitInfo);
			}

			if (!flag) {
				node.remove(i);
				i--;
				continue;
			}
			if (childNode.getChildCount() > 0) {
				removeNoneCostCenterNode(childNode);
			}
		}
	}

	/**
	 * 删除没有工程项目的组织
	 * 
	 * @param node
	 *            树节点
	 * @return
	 */
	public static boolean removeCompanyNoProject(DefaultKingdeeTreeNode node) {
		if (node == null) {
			return false;
		}

		if (node.isLeaf() && (isCostCenterOrgUnitOrOrgStructureNode(node))) {
			return true;
		}

		DefaultKingdeeTreeNode childNode = null;
		boolean isOrg = false;
		for (int i = 0; i < node.getChildCount(); i++) {
			childNode = (DefaultKingdeeTreeNode) node.getChildAt(i);
			isOrg = removeCompanyNoProject(childNode);
			if (isOrg) {
				node.remove(childNode);
				i--;
			}
		}
		if (isOrg) {
			removeCompanyNoProject((DefaultKingdeeTreeNode) node.getParent());
		}

		return false;
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

}
