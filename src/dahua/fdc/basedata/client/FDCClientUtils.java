/*
 * @(#)FDCClientUtils.java
 *
 * 金蝶国际软件集团有限公司版权所有
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.Component;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.ctrl.swing.KDToolBar;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.base.permission.PermissionServiceException;
import com.kingdee.eas.base.uiframe.client.NewMainFrame;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.base.uiframe.client.ui.IMainUIObject;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.SystemStatusCtrolUtils;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.UsedStatusEnum;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.client.f7.AdminF7;
import com.kingdee.eas.basedata.person.client.PersonF7UI;
import com.kingdee.eas.basedata.person.client.PersonPromptBox;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.ContractTypeCollection;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ProjectFacadeFactory;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUCollection;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUFactory;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUInfo;
import com.kingdee.eas.fdc.basedata.TargetTypeCollection;
import com.kingdee.eas.fdc.basedata.TargetTypeFactory;
import com.kingdee.eas.fdc.basedata.TargetTypeInfo;
import com.kingdee.eas.fdc.basedata.util.FdcStringUtil;
import com.kingdee.eas.fdc.basedata.util.FdcWfUtil;
import com.kingdee.eas.fdc.contract.ContractBillEntryFactory;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fi.gl.GlWebServiceUtil;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.SystemEnum;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.scm.common.client.GeneralKDPromptSelectorAdaptor;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;
import com.kingdee.util.enums.StringEnum;

/**
 *
 * 描述:房地产系统客户端工具类
 *
 * @author liupd date:2006-8-15
 *         <p>
 * @version EAS5.1.3
 */
public class FDCClientUtils {

	public final static String RESOURCE = "com.kingdee.eas.fdc.basedata.client.FdcResource";
	private static final Logger logger = Logger.getLogger(FDCClientUtils.class);

	public static Map filterMap = new HashMap();
	/**
	 *
	 * 描述：获取多语言资源
	 *
	 * @param resName
	 *            资源项名称
	 * @return
	 * @author:liupd 创建时间：2006-8-15
	 *               <p>
	 */
	public static String getRes(String resName) {
		return EASResource.getString(RESOURCE, resName);
	}

	/**
	 *
	 * 描述：清除ToolBar上多余的分割线
	 *
	 * @param toolBar
	 * @author:liupd 创建时间：2005-9-5
	 *               <p>
	 */
	public static void clearSeparatorOnToolBar(KDToolBar toolBar) {
		int size = toolBar.getComponentCount();
		Component c = null;
		boolean isSeparator = false;
		int lastVisibleBtnPosition = 0;
		for (int i = 0; i < size; i++) {
			c = toolBar.getComponentAtIndex(i);
			if (c.isVisible()) {
				if (c instanceof javax.swing.JToolBar.Separator) {
					if (isSeparator) {
						c.setVisible(false);
					}
					isSeparator = true;
				} else {
					isSeparator = false;
					lastVisibleBtnPosition = i;
				}
			}
		}

		for (int i = lastVisibleBtnPosition; i < size; i++) {
			c = toolBar.getComponentAtIndex(i);
			if (c instanceof javax.swing.JToolBar.Separator) {
				c.setVisible(false);
			}
		}

	}

	/**
	 *
	 * 描述：检查工程项目是否被单据(合同或无文本合同)引用，如果已被引用，则提示“您选择的工程项目已经被引用，不能新增下级”
	 *
	 * @param ui
	 * @param projId
	 * @throws Exception
	 * @author:liupd 创建时间：2006-9-26
	 *               <p>
	 */
	public static void checkProjUsed(CoreUIObject ui, String projId)
			throws Exception {
		Map usedMap = ProjectFacadeFactory.getRemoteInstance().canAddNew(projId); 
		if (usedMap.containsKey("HASUSED")&&((Boolean)(usedMap.get("HASUSED"))).booleanValue()) {
			if (usedMap.containsKey("CONTRACTNUMBERS") || usedMap.containsKey("CONTRACTCHANGENUMBERS")
					|| usedMap.containsKey("CONNOTEXTNUMBERS")) {
				String headMsg = "存在从上级工程项目拆分到本工程项目的合同拆分、变更拆分或者无文本合同付款拆分，不允许新增下级工程项目。";
				StringBuffer detailMsg = new StringBuffer();
				
				if(usedMap.containsKey("CONTRACTNUMBERS")){
					detailMsg.append("合同编码为：");
					detailMsg.append(usedMap.get("CONTRACTNUMBERS"));
					detailMsg.append("\n");
				}
				if(usedMap.containsKey("CONTRACTCHANGENUMBERS")){
					detailMsg.append("变更编码为：");
					detailMsg.append(usedMap.get("CONTRACTCHANGENUMBERS"));
					detailMsg.append("\n");
				}
				if(usedMap.containsKey("CONNOTEXTNUMBERS")){
					detailMsg.append("无文本编码为：");
					detailMsg.append(usedMap.get("CONNOTEXTNUMBERS"));
					detailMsg.append("\n");
				}
				FDCMsgBox.showDetailAndOK(ui, headMsg, detailMsg.toString(), 0);
				SysUtil.abort();
			}else{
				String warning = getRes("projUsed");
				String errorDetail = "工程项目可能被合同、无文本合同、可研成本、指标值或目标成本引用。";
				FDCMsgBox.showDetailAndOK(ui, warning, errorDetail, 0);
				SysUtil.abort();
			}
		}
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * 描述：检查单据是否在工作流中
	 * 
	 * @param ui
	 *            当前UI，显示消息时用
	 * @param id
	 *            单据ID
	 * @author rd_skyiter_wang
	 * @createDate 2014-12-3
	 */
	public static void checkBillInWorkflow(CoreUIObject ui, String id) {
		boolean flag = false;
		try {
			// 判断单据是否在工作流中
			flag = FdcWfUtil.isBillInWorkflow(id);
		} catch (BOSException e) {
			// ExceptionHandler.handle(e);
			ui.handUIException(e);
			SysUtil.abort();
		}

		if (flag) {
			MsgBox.showWarning(ui, EASResource
					.getString(FrameWorkClientUtils.strResource
							+ "Msg_BindWfInstance"));
			SysUtil.abort();
		}
	}

	/**
	 * 描述：检查单据是否在工作流中
	 * 
	 * @param ui
	 *            当前UI，显示消息时用
	 * @param id
	 *            单据ID
	 * @param infoMsg
	 *            提示信息
	 * @author rd_skyiter_wang
	 * @createDate 2014-12-3
	 */
	public static void checkBillInWorkflow(CoreUIObject ui, String id, String infoMsg) {
		boolean flag = false;
		try {
			// 判断单据是否在工作流中
			flag = FdcWfUtil.isBillInWorkflow(id);
		} catch (BOSException e) {
			// ExceptionHandler.handle(e);
			ui.handUIException(e);
			SysUtil.abort();
		}

		if (flag) {
			MsgBox.showWarning(ui, infoMsg);
			SysUtil.abort();
		}
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 *
	 * 描述：生成组织树的叶子节点
	 *
	 * @param node
	 * @param leafNodesIdSet
	 * @author:liupd 创建时间：2006-7-20
	 *               <p>
	 */
	public static void genLeafNodesIdSet(TreeNode node, Set leafNodesIdSet) {

		int count = node.getChildCount();
		// 如果没有下级节点，说明当前组织是实体，把当前组织id返回即可
		if (count == 0) {

			OrgStructureInfo orgStructureInfo = ((OrgStructureInfo) ((DefaultKingdeeTreeNode) node)
					.getUserObject());

			String oid = orgStructureInfo.getUnit().getId().toString();
			leafNodesIdSet.add(oid);
			return;
		}
		DefaultKingdeeTreeNode treeNode = null;
		for (int i = 0; i < count; i++) {
			treeNode = (DefaultKingdeeTreeNode) node.getChildAt(i);
			if (treeNode.isLeaf()) {
				String id = ((OrgStructureInfo) treeNode.getUserObject())
						.getUnit().getId().toString();
				leafNodesIdSet.add(id);
			} else {
				genLeafNodesIdSet(treeNode, leafNodesIdSet);
			}
		}

	}

	/**
	 *
	 * 描述：生成组织树的节点
	 *
	 * @param node
	 * @param leafNodesIdSet
	 * @author:liupd 创建时间：2006-7-20
	 *               <p>
	 */
	public static void genNodesIdSet(TreeNode node, Set leafNodesIdSet) {

		int count = node.getChildCount();
		// 如果没有下级节点，说明当前组织是实体，把当前组织id返回即可
		//存在这样的一个CU下多个财务组织的情况，因此不能必须添加node
//		if (count == 0) {

			OrgStructureInfo orgStructureInfo = ((OrgStructureInfo) ((DefaultKingdeeTreeNode) node)
					.getUserObject());

			String oid = orgStructureInfo.getUnit().getId().toString();
			leafNodesIdSet.add(oid);
//			return;
//		}
		DefaultKingdeeTreeNode treeNode = null;
		for (int i = 0; i < count; i++) {
			treeNode = (DefaultKingdeeTreeNode) node.getChildAt(i);
			String id = ((OrgStructureInfo) treeNode.getUserObject()).getUnit()
					.getId().toString();
			leafNodesIdSet.add(id);
			if (!treeNode.isLeaf()) {
				genNodesIdSet(treeNode, leafNodesIdSet);
			}
		}

	}

	/**
	 *
	 * 描述：生成树的节点
	 *
	 * @param node
	 * @param leafNodesIdSet
	 * @author:liupd 创建时间：2006-7-20
	 *               <p>
	 */
	public static void genNodesIdSet2(TreeNode node, Set leafNodesIdSet) {

		int count = node.getChildCount();
		if (count == 0) {

			CoreBaseInfo info = ((CoreBaseInfo) ((DefaultKingdeeTreeNode) node)
					.getUserObject());

			String oid = info.getId().toString();
			leafNodesIdSet.add(oid);
			return;
		}
		DefaultKingdeeTreeNode treeNode = null;
		for (int i = 0; i < count; i++) {
			treeNode = (DefaultKingdeeTreeNode) node.getChildAt(i);
			String id = ((CoreBaseInfo) treeNode.getUserObject()).getId()
					.toString();
			leafNodesIdSet.add(id);
			if (!treeNode.isLeaf()) {
				genNodesIdSet2(treeNode, leafNodesIdSet);
			}
		}

	}

	/**
	 *
	 * 描述：生成工程项目树的叶子节点
	 *
	 * @param node
	 * @param leafNodesIdSet
	 * @author:liupd 创建时间：2006-7-20
	 *               <p>
	 */
	public static void genProjLeafNodesIdSet(TreeNode node, Set leafNodesIdSet) {

		int count = node.getChildCount();
		// 如果没有下级工程项目，说明是叶子节点，直接返回它的id即可
		if (count == 0) {
			if(((DefaultKingdeeTreeNode) node).getUserObject() instanceof CurProjectInfo){
				CurProjectInfo curProjectInfo = ((CurProjectInfo) ((DefaultKingdeeTreeNode) node)
						.getUserObject());
				String oid = curProjectInfo.getId().toString();
				leafNodesIdSet.add(oid);
			}else{
				FDCMsgBox.showWarning(FDCClientHelper.getCurrentActiveWindow(), "该结点不是工程项目");
				SysUtil.abort();
			}

			return;
		}
		DefaultKingdeeTreeNode treeNode = null;
		for (int i = 0; i < count; i++) {
			treeNode = (DefaultKingdeeTreeNode) node.getChildAt(i);
			if (treeNode.isLeaf()) {
				if (!(treeNode.getUserObject() instanceof CurProjectInfo))
					continue;
				String id = ((CurProjectInfo) treeNode.getUserObject()).getId()
						.toString();
				leafNodesIdSet.add(id);
			} else {
				genProjLeafNodesIdSet(treeNode, leafNodesIdSet);
			}
		}

	}

	/**
	 *
	 * 描述：获取编码规则是否支持修改 <p>
	 * 有些大单据对象有很多分录（郡原的付款拆分，最多曾见到过18865行分录），如果整个单据对象序列化会引发OutOfMemory异常。
	 * 由于单据的分录对编码规则不影响，不需要序列化传到服务端，故序列化单据对象到服务端之前先拿掉分录，然后将分录还原 edited by Owen_wen 2013-03-11
	 * @param info
	 *            当前单据的Info
	 * @param orgUnitId
	 *            当前组织id
	 * @return 支持修改 － true， 不支持修改 － false，如果没有定义编码规则则返回true
	 * @author:liupd 创建时间：2006-9-29
	 */
	public static boolean isAllowModifyNumber(IObjectValue info, String orgUnitId) {
		boolean result = false;

		result = isAllowModifyNumber(info, orgUnitId, null);

		return result;
	}

	/**
	 * 
	 * 描述：获取编码规则是否支持修改
	 * <p>
	 * 有些大单据对象有很多分录（郡原的付款拆分，最多曾见到过18865行分录），如果整个单据对象序列化会引发OutOfMemory异常。
	 * 由于单据的分录对编码规则不影响，不需要序列化传到服务端，故序列化单据对象到服务端之前先拿掉分录，然后将分录还原 edited by Owen_wen 2013-03-11
	 * 
	 * @param info
	 *            当前单据的Info
	 * @param orgUnitId
	 *            当前组织id
	 * @param bindingProperty
	 *            绑定属性
	 * @return 支持修改 － true， 不支持修改 － false，如果没有定义编码规则则返回true
	 * @author:liupd 创建时间：2006-9-29
	 */
	public static boolean isAllowModifyNumber(IObjectValue info, String orgUnitId, String bindingProperty) {
		boolean result = false;


		try {
			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();

			result = isAllowModifyNumber(iCodingRuleManager, info, orgUnitId, bindingProperty);
		} catch (Exception e) {
			// @AbortException
			ExceptionHandler.handle(e);
		}

		return result;
	}

	/**
	 * 
	 * 描述：获取编码规则是否支持修改
	 * <p>
	 * 有些大单据对象有很多分录（郡原的付款拆分，最多曾见到过18865行分录），如果整个单据对象序列化会引发OutOfMemory异常。
	 * 由于单据的分录对编码规则不影响，不需要序列化传到服务端，故序列化单据对象到服务端之前先拿掉分录，然后将分录还原 edited by Owen_wen 2013-03-11
	 * 
	 * @param iCodingRuleManager
	 *            编码规则Manager
	 * @param orgUnitId
	 *            当前组织id
	 * @param orgUnitId
	 *            当前组织id
	 * @param bindingProperty
	 *            绑定属性
	 * @return 支持修改 － true， 不支持修改 － false，如果没有定义编码规则则返回true
	 * @author:liupd 创建时间：2006-9-29
	 */
	public static boolean isAllowModifyNumber(ICodingRuleManager iCodingRuleManager, IObjectValue info,
			String orgUnitId, String bindingProperty) {
		boolean result = false;

		// 1. 先把所有分录取出来，判断编码规则不需要分录
		Map collectionMap = new HashMap(); // 分录key-value对象
		for (Enumeration e = info.keys(); e.hasMoreElements();) {
			Object key = e.nextElement();
			Object value = info.get((String) key);
			if (value instanceof IObjectCollection) {
				collectionMap.put(key, value);
				info.remove((String) key);// 先拿掉分录
			}
		}

		try {
			if (FdcStringUtil.isNotBlank(bindingProperty)
					&& iCodingRuleManager.isExist(info, orgUnitId, bindingProperty)) {
				return iCodingRuleManager.isModifiable(info, orgUnitId, bindingProperty);
			} else if (iCodingRuleManager.isExist(info, orgUnitId)) {
				return iCodingRuleManager.isModifiable(info, orgUnitId);
			} else {
				return true;
			}
		} catch (Exception e) {
			// @AbortException
			ExceptionHandler.handle(e);
		} finally {
			// 2. 再把所有分录还原
			for (Iterator it = collectionMap.keySet().iterator(); it.hasNext();) {
				String key = (String) it.next();
				info.put(key, collectionMap.get(key));// 再补上分录
			}
		}

		return result;
	}



	/**
	 *
	 * 描述：检查是否选择了项目工程，并选择了明细节点
	 *
	 * @author:liupd 创建时间：2006-7-27
	 *               <p>
	 */
	public static void checkSelectProj(CoreUIObject ui,
			DefaultKingdeeTreeNode selectTreeNode) {

		// 请选择工程项目
		if (selectTreeNode == null) {
			MsgBox.showWarning(ui, ContractClientUtils.getRes("selectProjPls"));
			SysUtil.abort();
		}

		// 请选择工程项目节点（非公司，如果没有工程项目，请先增加工程项目）
		if (!(selectTreeNode.getUserObject() instanceof CurProjectInfo)) {
			MsgBox
					.showWarning(ui, ContractClientUtils
							.getRes("selectProjPls2"));
			SysUtil.abort();
		}

		// 请选择工程项目明细节点
		if (!selectTreeNode.isLeaf()) {
			MsgBox.showWarning(ui, ContractClientUtils
					.getRes("selectProjLeafPls"));
			SysUtil.abort();
		}

	}

	/**
	 * 检查工程项目有没有对应到成本中心
	 *
	 * @param ui
	 * @param selectTreeNode
	 */
	public static void checkProjWithCostOrg(CoreUIObject ui, DefaultKingdeeTreeNode selectTreeNode) {
		CurProjectInfo curProj = (CurProjectInfo) selectTreeNode.getUserObject();

		boolean exists = curProj.getCostCenter()!=null;
		if (!exists) {
			MsgBox.showWarning(ui, "您所选择的工程项目没有对应到成本中心，无法继续，请先设置二者的对应关系");
			SysUtil.abort();
		}
		
		FDCSQLBuilder builder=new FDCSQLBuilder();
		builder.appendSql("select 1 from T_Org_costCenter where fid in (select fcostCenterId from T_FDC_CurProject where fid=?) and fisBizUnit=1");
		builder.addParam(curProj.getId().toString());
		try{
			if(!builder.isExist()){
				String error="工程项目对应的成本中心不是实体成本中心";
				String errorDetail="可能是以下原因导致：\n1.没有设置工程项目与成本中心的对应关系\n2.因为上级工程项目对应了虚体成本而下级成本中心没有设置对应关系而导致工程项目对应的成本中心是虚体成本中心";
				FDCMsgBox.showDetailAndOK(ui, error, errorDetail, 1);
				SysUtil.abort();
			}
		}catch(BOSException e){
			// @AbortException
			ui.handUIException(e);
		}
	}

	/**
	 * 检查工程项目是否对应到成本中心
	 * @param projId
	 * @return 
	 */
	public static boolean checkProjWithCostOrg(String projId){
		boolean exists = false;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("curProject.id", projId));
		try {
			exists = ProjectWithCostCenterOUFactory.getRemoteInstance().exists(filter);
		} catch (Exception e) {
			// @AbortException
			ExceptionHandler.handle(e);
		}
		return exists;
	}
	
	//获得顶级工程项目的Id
	private static String getTopProjId(String projId) {
		CurProjectInfo curProjectInfo = null;
		try {
			curProjectInfo = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectStringPK(projId));
		} catch (Exception e1) {
			// @AbortException
			ExceptionHandler.handle(e1);
		}

		if(curProjectInfo.getParent() == null) return projId;

		String longNumber = curProjectInfo.getLongNumber();
		String pln = longNumber.substring(0, longNumber.indexOf("!"));
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo fi = new FilterInfo();
		fi.getFilterItems().add(new FilterItemInfo("longNumber", pln));
		
		// 如只用longNumber过滤，得到的不只一个顶级项目Id，所以还要加上CU过滤。 Added By Owen_wen 2011-01-21
		String controlUnitId = curProjectInfo.getCU().getId().toString();
		fi.getFilterItems().add(new FilterItemInfo("CU", controlUnitId));
		
		view.setFilter(fi);
		CurProjectCollection coll = null;
		try {
			coll = CurProjectFactory.getRemoteInstance().getCurProjectCollection(view);
		} catch (BOSException e1) {
			// @AbortException
			ExceptionHandler.handle(e1);
		}
		String pProjId = null;
		if (coll != null && coll.size() > 0) {
			CurProjectInfo pProjInfo = coll.get(0);
			pProjId = pProjInfo.getId().toString();
		}
		return pProjId;
	}

	/**
	 * 描述：若工程设置了与成本中心对应关系，则取成本中心对应的组织；否则取上级成本中心对应的组织
	 * @param projId
	 * @return
	 */
	public static FullOrgUnitInfo getCostOrgByProj(String projId) {
		String pProjId = checkProjWithCostOrg(projId) ? projId : getTopProjId(projId);
		FullOrgUnitInfo orgInfo = null;
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("curProject.id", pProjId));
		view.setFilter(filter);
		view.getSelector().add("costCenterOU.*");

		ProjectWithCostCenterOUCollection pwcc = null;
		try {
			pwcc = ProjectWithCostCenterOUFactory.getRemoteInstance().getProjectWithCostCenterOUCollection(view);
			if (pwcc == null) {
				logger.info("CurProject's id is: " + projId);
				FDCMsgBox.showError("工程项目没有对应成本中心，请先设置工程项目与成本中心对应关系。");
				SysUtil.abort();
			}
		} catch (BOSException e) {
			// @AbortException
			ExceptionHandler.handle(e);
			if (e instanceof PermissionServiceException) {
				FDCMsgBox.showInfo("可能没有“工程项目与成本中心对应关系”的查看权限，请检查是否分配了该权限。");
			}
			SysUtil.abort();
		}

		if(pwcc.size() > 0) {
			ProjectWithCostCenterOUInfo info = pwcc.get(0);
			orgInfo = info.getCostCenterOU().castToFullOrgUnitInfo();
		}

		return orgInfo;
	}

	/**
	 *
	 * 描述：去掉没有财务组织属性的节点
	 *
	 * @param node
	 * @author:liupd 创建时间：2006-9-25
	 *               <p>
	 */
	public static void removeNoneCompanyNode(DefaultKingdeeTreeNode node) {

		for (int i = 0; i < node.getChildCount(); i++) {
			DefaultKingdeeTreeNode curNode = (DefaultKingdeeTreeNode) node
					.getChildAt(i);
			OrgStructureInfo orgStru = (OrgStructureInfo) curNode
					.getUserObject();
			if (!orgStru.getUnit().isIsCompanyOrgUnit()) {
				node.remove(i);
				i--;
				continue;
			}
			if (curNode.getChildCount() > 0) {
				removeNoneCompanyNode(curNode);
			}
		}

	}

	public static void showOprtOK(Component comp) {
		MsgBox.showWarning(comp, ContractClientUtils.getRes("oprateOK"));
	}

	public static void showOprtFailed(Component comp) {
		MsgBox.showWarning(comp, getRes("oprateFailed"));
	}

	/**
	 *
	 * 描述：根据组织id获取其所有下级组织id的集合
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 * @author:liupd 创建时间：2006-7-26
	 *               <p>
	 */
	public static Set genOrgUnitIdSet(BOSUuid id) throws Exception {
		Set idSet = new HashSet();
		FullOrgUnitInfo orgUnitInfo = FullOrgUnitFactory.getRemoteInstance()
				.getFullOrgUnitInfo(new ObjectUuidPK(id));
		String orgUnitLongNumber = orgUnitInfo.getLongNumber();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("longNumber", orgUnitLongNumber + "%",
						CompareType.LIKE));
		filter.getFilterItems().add(
				new FilterItemInfo("longNumber", orgUnitLongNumber));
		filter.getFilterItems().add(
				new FilterItemInfo("isCostOrgUnit", Boolean.TRUE));
		filter.setMaskString("(#0 or #1) and #2");
		view.setFilter(filter);
		view.getSelector().add("id");

		FullOrgUnitCollection fullOrgUnitCollection = FullOrgUnitFactory
				.getRemoteInstance().getFullOrgUnitCollection(view);

		for (Iterator iter = fullOrgUnitCollection.iterator(); iter.hasNext();) {
			FullOrgUnitInfo element = (FullOrgUnitInfo) iter.next();
			idSet.add(element.getId().toString());
		}

		return idSet;
	}
	/**
	 *
	 * 描述：根据当前财务组织，取下级的所有实体财务组织
	 * 通过长编码来匹配
	 * 后续优化为sql
	 */
	public static Map getChildrenCompanyOrgUnitInfos(BOSUuid companyID) throws Exception {
		Map dataMap = new HashMap();
//		Set companyOrgUnitIdSet = new HashSet();
		
		CompanyOrgUnitInfo orgUnitInfo = CompanyOrgUnitFactory.getRemoteInstance()
				.getCompanyOrgUnitInfo(new ObjectUuidPK(companyID));
		String orgUnitLongNumber = orgUnitInfo.getLongNumber();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select fid,fname_l2 from t_org_company ");
		builder.appendSql("where flongnumber like ");
		builder.appendParam(orgUnitLongNumber+"%");
		builder.appendSql(" and ");
		builder.appendParam("fisbizunit", Boolean.TRUE);
		builder.appendSql(" and ");
		builder.appendParam("fid", FDCUtils.getAuthorizedOrgs(null).toArray());
		IRowSet rs = builder.executeQuery();
		CompanyOrgUnitInfo info = null;
		while(rs.next()){
			info = new CompanyOrgUnitInfo();
			String id = rs.getString("fid");
			String name=rs.getString("fname_l2");
			info.setId(BOSUuid.read(id));
			info.setName(name);
			dataMap.put(id, info);
		}
		
		return dataMap;
	}
	/**
	 *
	 * 描述：根据财务组织，取所有明细工程项目
	 * 
	 * @param idSet:实体财务组织ID集合
	 */
	public static Map getPrjInfosByCompany(Set idSet) throws Exception {
		Map retMap = new HashMap();
		Set prjIdSet = new HashSet();
		CurProjectCollection projects = new CurProjectCollection();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select unit.flongnumber,prj.flongnumber,prj.fid,prj.fdisplayname_l2,prj.ffullorgunit from t_fdc_curproject prj ");
		builder.appendSql("inner join t_org_baseunit unit on unit.fid=prj.ffullorgunit ");
		builder.appendSql("where ");
		builder.appendParam("prj.ffullOrgUnit", idSet.toArray());
		builder.appendSql("and ");
		builder.appendParam("prj.fisDevPrj",Boolean.valueOf(true));
		builder.appendSql("and ");
		builder.appendParam("prj.fisLeaf", Boolean.valueOf(true));
		builder.appendSql("and ");
		builder.appendParam("prj.fisEnabled", Boolean.valueOf(true));
		builder.appendSql("order by unit.flongnumber,prj.flongnumber");
		
		IRowSet rs = builder.executeQuery();
		CurProjectInfo info = null;
		FullOrgUnitInfo fullOrgUnit = null;
		while(rs.next()){
			info = new CurProjectInfo();
			fullOrgUnit = new FullOrgUnitInfo();
			String id=rs.getString("fid");
			String name = rs.getString("fdisplayname_l2");
			String orgId = rs.getString("ffullorgunit");
			info.setId(BOSUuid.read(id));
			info.setDisplayName(name);
			fullOrgUnit.setId(BOSUuid.read(orgId));
			info.setFullOrgUnit(fullOrgUnit);
			
			prjIdSet.add(id);
			projects.add(info);
		}
		retMap.put("prjIdSet", prjIdSet);
		retMap.put("projects", projects);
		return retMap;
	}
	/**
	 *
	 * 描述：根据项目Id获取其所有下级项目id集合
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 * @author:liupd 创建时间：2006-7-26
	 *               <p>
	 */
	public static Set genProjectIdSet(BOSUuid id) throws Exception {
		Set idSet = new HashSet();
		CurProjectInfo curProjectInfo = CurProjectFactory.getRemoteInstance()
				.getCurProjectInfo(new ObjectUuidPK(id));
		String longNumber = curProjectInfo.getLongNumber();

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("longNumber", longNumber + "!%",
						CompareType.LIKE));
		filter.getFilterItems().add(
				new FilterItemInfo("longNumber", longNumber));
		filter.getFilterItems().add(
				new FilterItemInfo("fullOrgUnit.id", curProjectInfo
						.getFullOrgUnit().getId().toString()));
		filter.setMaskString("(#0 or #1) and #2");
		view.setFilter(filter);

		CurProjectCollection curProjectCollection = CurProjectFactory
				.getRemoteInstance().getCurProjectCollection(view);

		for (Iterator iter = curProjectCollection.iterator(); iter.hasNext();) {
			CurProjectInfo element = (CurProjectInfo) iter.next();
			idSet.add(element.getId().toString());
		}
		return idSet;
	}

	/**
	 * 描述：根据当前工程项目ID 获取下级工程项目id 集合
	 * @param id
	 * @param isOnlyLeaf 是否包括下级非明细项目
	 * @return
	 * @throws Exception
	 */
	public static Set genProjectIdSet(BOSUuid id,boolean isOnlyLeaf) throws Exception {
		Set idSet = new HashSet();
		CurProjectInfo curProjectInfo = CurProjectFactory.getRemoteInstance()
				.getCurProjectInfo(new ObjectUuidPK(id));
		String longNumber = curProjectInfo.getLongNumber();

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("longNumber", longNumber + "!%",
						CompareType.LIKE));
		filter.getFilterItems().add(
				new FilterItemInfo("longNumber", longNumber));
		filter.getFilterItems().add(
				new FilterItemInfo("fullOrgUnit.id", curProjectInfo
						.getFullOrgUnit().getId().toString()));
		if(isOnlyLeaf){
			filter.getFilterItems().add(
					new FilterItemInfo("isLeaf", Boolean.TRUE));
			filter.setMaskString("(#0 or #1) and #2 and #3");
		}else{
			filter.setMaskString("(#0 or #1) and #2");
		}
		view.setFilter(filter);

		CurProjectCollection curProjectCollection = CurProjectFactory
				.getRemoteInstance().getCurProjectCollection(view);

		for (Iterator iter = curProjectCollection.iterator(); iter.hasNext();) {
			CurProjectInfo element = (CurProjectInfo) iter.next();
			idSet.add(element.getId().toString());
		}
		return idSet;
	}

	/**
	 *
	 * 描述：根据合同类型ID获取其所有下级Id集合
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 * @author:liupd 创建时间：2006-7-26
	 *               <p>
	 */
	public static Set genContractTypeIdSet(BOSUuid id) throws Exception {

		Set idSet = new HashSet();
		ContractTypeInfo contractTypeInfo = ContractTypeFactory
				.getRemoteInstance().getContractTypeInfo(new ObjectUuidPK(id));
		String longNumber = contractTypeInfo.getLongNumber();

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("longNumber", longNumber + "!%",
						CompareType.LIKE));
		filter.getFilterItems().add(
				new FilterItemInfo("longNumber", longNumber));
		filter.setMaskString("#0 or #1");
		view.setFilter(filter);

		ContractTypeCollection contractTypeCollection = ContractTypeFactory
				.getRemoteInstance().getContractTypeCollection(view);

		for (Iterator iter = contractTypeCollection.iterator(); iter.hasNext();) {
			ContractTypeInfo element = (ContractTypeInfo) iter.next();
			idSet.add(element.getId().toString());
		}

		idSet.add(id.toString());

		return idSet;
	}

	/**
	 *
	 * 描述：根据指标类型ID获取其所有下级Id集合
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 * @author:liupd 创建时间：2006-7-26
	 *               <p>
	 */
	public static Set genTargetTypeIdSet(BOSUuid id) throws Exception {

		Set idSet = new HashSet();
		TargetTypeInfo TargetTypeInfo = TargetTypeFactory.getRemoteInstance()
				.getTargetTypeInfo(new ObjectUuidPK(id));
		String longNumber = TargetTypeInfo.getLongNumber();

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		//下级
		filter.getFilterItems().add(
				new FilterItemInfo("longNumber", longNumber + "!%",
						CompareType.LIKE));
		//本级
		filter.getFilterItems().add(
				new FilterItemInfo("longNumber", longNumber));
		filter.setMaskString("#0 or #1");
		view.setFilter(filter);

		TargetTypeCollection TargetTypeCollection = TargetTypeFactory
				.getRemoteInstance().getTargetTypeCollection(view);

		for (Iterator iter = TargetTypeCollection.iterator(); iter.hasNext();) {
			TargetTypeInfo element = (TargetTypeInfo) iter.next();
			idSet.add(element.getId().toString());
		}
		return idSet;
	}

	/**
	 * 检查合同详细信息定义是否被使用
	 *
	 * @param id
	 *            合同详细信息定义ID
	 * @return true - 已被引用, false - 未被引用
	 */
	public boolean isContractDetailDefUsed(String id) {
		boolean b = false;

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("detailDefID", id));
		try {
			b = ContractBillEntryFactory.getRemoteInstance().exists(filter);
		} catch (Exception e) {
			// @AbortException
			logger.error(e.getMessage(), e);
			ExceptionHandler.handle(e);
		}

		return b;
	}

	public static CurProjectInfo getProjectInfoForDisp(String projId) {
		CurProjectInfo curProjectInfo = null;
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add(new SelectorItemInfo("displayName"));
		selectors.add(new SelectorItemInfo("CU.id"));
		try {
			curProjectInfo = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectStringPK(projId), selectors);
		} catch (Exception e) {
			// @AbortException
			logger.error(e.getMessage(), e);
			ExceptionHandler.handle(e);
		}
		return curProjectInfo;
	}

	/**
	 * 格式化表尾合计行的数据格式
	 * @param table 表格
	 * @param colNames 要格式化的列Key数组
	 */
	public static void fmtFootNumber(KDTable table, String[] colNames) {
		if(table.getFootManager()==null || table.getFootManager().getFoot() == null) {
//			table.setFootManager(new KDTFootManager(table));
//			table.addFootRow(0);
//			if(table.getFootManager().getFoot() == null){
				return;

//			}
		}
		for (int i = 0; i < colNames.length; i++) {
			table.getFootRow(0).getCell(colNames[i]).getStyleAttributes().setNumberFormat(
					FDCHelper.strDataFormat);
		}

	}

	/**
	 * 供应商核准的条件
	 *
	 * @return
	 */
	public static EntityViewInfo getApprovedSupplierView() {
		EntityViewInfo v = new EntityViewInfo();
		FilterInfo f = new FilterInfo();
		f.getFilterItems().add(
				new FilterItemInfo("usedStatus", new Integer(
						UsedStatusEnum.APPROVED_VALUE)));
		v.setFilter(f);
		return v;
	}

	/**
	 * 设置责任部门F7,只能选择明细节点
	 *
	 * @param bizPromptBox
	 * @param ui
	 */
	public static void setRespDeptF7(KDBizPromptBox bizPromptBox,
			CoreUIObject ui) {
		setRespDeptF7(bizPromptBox, ui, null);
	}
	public static void setRespDeptF7(KDBizPromptBox bizPromptBox,
			CoreUIObject ui, String cuId) {
		setRespDeptF7(bizPromptBox, ui, cuId, true);
	}
	/**
	 * 设置责任部门F7,只能选择明细节点
	 *
	 * @param bizPromptBox
	 * @param ui
	 */
	public static void setRespDeptF7(KDBizPromptBox bizPromptBox,
			CoreUIObject ui, String cuId,boolean isMustSelectLeaf) {
		bizPromptBox
				.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminOrgUnitQuery");

		EntityViewInfo view = new EntityViewInfo();

		SorterItemCollection sorc = view.getSorter();
		SorterItemInfo sort = new SorterItemInfo("number");
		sorc.add(sort);

//		String longNumber = SysContext.getSysContext().getCurrentFIUnit()
//				.getLongNumber();
		FilterInfo filter = new FilterInfo();

		FilterItemCollection fic = filter.getFilterItems();
		fic.add(new FilterItemInfo("isFreeze", new Integer(0)));
		fic.add(new FilterItemInfo("isSealUp", new Integer(0)));

		if(cuId != null){
			fic.add(new FilterItemInfo("CU.id", cuId));
		}

		//用户组织范围内的组织才能选择
		try {
			Set authorizedOrgs = new HashSet();
			Map orgs = (Map)ActionCache.get("FDCBillEditUIHandler.authorizedOrgs");
			if(orgs==null){
				orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(
						 new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId()),
			            OrgType.Admin,  null,  null, null);
			}
			if(orgs!=null){
				Set orgSet = orgs.keySet();
				Iterator it = orgSet.iterator();
				while(it.hasNext()){
					authorizedOrgs.add(it.next());
				}
			}
            FilterInfo filterID = new FilterInfo();
            filterID.getFilterItems().add(new FilterItemInfo("id", authorizedOrgs,CompareType.INCLUDE));

            filter.mergeFilter(filterID, "and");

		} catch (Exception e) {
			// @AbortException
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}

		view.setFilter(filter);
		bizPromptBox.setEntityViewInfo(view);

		AdminF7 f7 = new AdminF7(ui);
		f7.showCheckBoxOfShowingAllOUs();
		f7.setIsCUFilter(false);
		f7.setRootUnitID(cuId);
//
		if(cuId != null) f7.setCurrentCUID(cuId);

		bizPromptBox.setSelector(f7);
		SelectorItemCollection sic = bizPromptBox.getSelectorCollection();
		if (sic == null) {
			sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("number"));
			sic.add(new SelectorItemInfo("name"));
			bizPromptBox.setSelectorCollection(sic);
		}
		sic.add(new SelectorItemInfo("isLeaf"));
		sic.add(new SelectorItemInfo("displayName"));

		// 只能选择明细的，增加监听交验

		if (bizPromptBox.getClientProperty("RespDeptDataChangeLisenter") == null) {
			RespDeptDataChangeLisenter ls = new RespDeptDataChangeLisenter(ui,isMustSelectLeaf);
			bizPromptBox.addDataChangeListener(ls);
			bizPromptBox.putClientProperty("RespDeptDataChangeLisenter", ls);
		}

		//通用F7是可以做到隔离的,只要bizPromptBox.setEntityViewInfo(view)正确 JinXP  20071217
		/*
//		防止输入编码调出通用F7来选择，那样无法做到隔离
		bizPromptBox.setQueryInfo(null);
		*/
	}

	/**
	 * 检查当前组织是否成本中心组织
	 *
	 * @param ui
	 */
	public static void checkCurrentCostCenterOrg(CoreUIObject ui) {
		CostCenterOrgUnitInfo currentCostOrg = SysContext.getSysContext()
				.getCurrentCostUnit();

		if (currentCostOrg == null) {
			MsgBox.showWarning(ui, getRes("notCostCenter"));
			SysUtil.abort();
		}
	}

	/**
	 * 检查当前组织是否成本中心组织
	 *
	 * @param ui
	 */
	public static void checkCurrentBizCostCenterOrg(CoreUIObject ui) {
		checkCurrentCostCenterOrg(ui);
		CostCenterOrgUnitInfo currentCostOrg = SysContext.getSysContext()
				.getCurrentCostUnit();

		if (!currentCostOrg.isIsBizUnit()) {
			MsgBox.showWarning(ui, "该功能只能在实体成本中心使用");
			SysUtil.abort();
		}
	}

	public static void initSupplierStockF7(CoreUIObject owner, KDBizPromptBox box) {
		initSupplierStockF7(owner, box, null);
	}
	public static void initSupplierStockF7(CoreUIObject owner, KDBizPromptBox box, String cuId) {
		box.setHasCUDefaultFilter(false);
		box.getQueryAgent().setDefaultFilterInfo(null);
		box.setDisplayFormat("$name$");
		box.setCommitFormat("$number$");
		box.setQueryInfo("com.kingdee.eas.fdc.invite.supplier.app.F7SupplierStockQuery");
	}
	// /**
	// * 初始化供应商F7，（只显示已核准的，消除重复记录）
	// * @param box
	// */
	// public static void initSupplierF7(KDBizPromptBox box) {
	// box.setQueryInfo(FDCConstants.SUPPLIER_F7_QUERY);
	// box.setDefaultF7UIName("com.kingdee.eas.basedata.master.cssp.client.F7SupplierTreeDetailListUI");
	// box.setEntityViewInfo(getApprovedSupplierView());
	// }

	public static void initSupplierF7(CoreUIObject owner, KDBizPromptBox box) {
		initSupplierF7(owner, box, null);
	}
	/**
	 * 初始化供应商F7，（只显示已核准的，消除重复记录）
	 *
	 * @param box
	 */
	public static void initSupplierF7(CoreUIObject owner, KDBizPromptBox box, String cuId) {

		GeneralKDPromptSelectorAdaptor selectorLisenterSupplier = new GeneralKDPromptSelectorAdaptor(
				box,
				"com.kingdee.eas.basedata.master.cssp.client.F7SupplierTreeDetailListUI",
				owner, CSSPGroupInfo.getBosType(), "com.kingdee.eas.basedata.master.cssp.app.F7SupplierQuery",
				"browseGroup.id", "com.kingdee.eas.basedata.master.cssp.app.F7SupplierQuery",
				"com.kingdee.eas.basedata.master.cssp.app.F7SupplierQueryWithDefaultStandard");
		if(cuId != null) {
			selectorLisenterSupplier.setCUId(cuId);

			CtrlUnitInfo ctrl = new CtrlUnitInfo();
			ctrl.setId(BOSUuid.read(cuId));
			box.setCurrentCtrlUnit(ctrl);
		}
		box.setSelector(selectorLisenterSupplier);
		box.setHasCUDefaultFilter(false);
		box.getQueryAgent().setDefaultFilterInfo(null);


		box.setEntityViewInfo(getApproved4SupplierF7Filter(cuId));


		//防止输入编码调出通用F7来选择，那样无法做到隔离
		//getApproved4SupplierF7Filter(String cuId) 设置过滤正确条件也可以做到隔离
		box.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQueryWithDefaultStandard");
	}

	public static EntityViewInfo getApproved4SupplierF7Filter(String cuId) {
		EntityViewInfo view = new EntityViewInfo();

		SorterItemCollection sorc = view.getSorter();
		SorterItemInfo sort = new SorterItemInfo("number");
		sorc.add(sort);

		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(
				new FilterItemInfo("usedStatus", String
						.valueOf(UsedStatusEnum.APPROVED.getValue()),
						CompareType.EQUALS));
		view.setFilter(filterInfo);

        // 客户是分配类的
        FilterInfo Dfilter = null;
        try {
        	if (!StringUtils.isEmpty(cuId)) {
				if (filterMap.containsKey(cuId)) {
					Dfilter = (FilterInfo) filterMap.get(cuId);
				} else {
					Dfilter = (FilterInfo)ActionCache.get("FDCBillEditUIHandler.dfilter");
					if(Dfilter==null){
						Dfilter = GlWebServiceUtil.getDFilterInfo(SupplierFactory
								.getRemoteInstance(), cuId);
					}
					filterMap.put(cuId, Dfilter);
				}
			}
            if (Dfilter != null) {
            	filterInfo.mergeFilter(Dfilter, "and");
            }
        } catch (Exception e) {
        	// @AbortException
            e.printStackTrace();
        }

		return view;
	}

	/**
	 * 描述：根据当前OU（假设是成本中心）来获取对应的工程项目。
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	public static Set getProjIdsOfCurCostOrg() throws BOSException, EASBizException {
		Set ids = new HashSet();
		String curCostId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("costCenterOU.id", curCostId));
		view.setFilter(filter);
		ProjectWithCostCenterOUCollection relations = null;

		relations = ProjectWithCostCenterOUFactory.getRemoteInstance().getProjectWithCostCenterOUCollection(view);

		if (relations == null || relations.size() == 0) {
			return null;
		}
		String projId = relations.get(0).getCurProject().getId().toString();
		ObjectUuidPK projUuidPK = new ObjectUuidPK(projId);
		if (!CurProjectFactory.getRemoteInstance().exists(projUuidPK)) {
			return null;
		}
		CurProjectInfo curProjectInfo = null;
		curProjectInfo = CurProjectFactory.getRemoteInstance().getCurProjectInfo(projUuidPK);

		String projNumber = curProjectInfo.getLongNumber();
		FilterInfo projFilter = new FilterInfo();
		projFilter.getFilterItems().add(new FilterItemInfo("longnumber", projNumber + "!%", CompareType.LIKE));
		projFilter.getFilterItems().add(new FilterItemInfo("longnumber", projNumber));

		String orgUnitId = SysContext.getSysContext().getCurrentFIUnit().getId().toString();

		projFilter.getFilterItems().add(new FilterItemInfo("fullOrgUnit", orgUnitId));

		projFilter.setMaskString("(#0 or #1) and #2");

		view = new EntityViewInfo();
		view.setFilter(projFilter);
		view.getSelector().add("id");

		CurProjectCollection projs = new CurProjectCollection();
		projs = CurProjectFactory.getRemoteInstance().getCurProjectCollection(view);
		for (Iterator iter = projs.iterator(); iter.hasNext();) {
			CurProjectInfo element = (CurProjectInfo) iter.next();
			if (element == null)
				continue;
			String id = element.getId().toString();
			ids.add(id);
		}
		return ids;
	}

	public static Set getProjIdsOfCostOrg(FullOrgUnitInfo orgUnit,boolean isOnlyLeaf) throws EASBizException, BOSException {
		Set ids = new HashSet();
		String curCostId = orgUnit.getId().toString();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("costCenterOU.id", curCostId));
		view.setFilter(filter);
		ProjectWithCostCenterOUCollection relations = null;
		relations = ProjectWithCostCenterOUFactory.getRemoteInstance().getProjectWithCostCenterOUCollection(view);
		
		if (relations == null || relations.size() == 0) {
			return null;
		}
		String projId = relations.get(0).getCurProject().getId().toString();
		ObjectUuidPK projUuidPK = new ObjectUuidPK(projId);
		CurProjectInfo curProjectInfo = null;
			if (!CurProjectFactory.getRemoteInstance().exists(projUuidPK)) {
				return null;
			}
			curProjectInfo = CurProjectFactory.getRemoteInstance().getCurProjectInfo(projUuidPK);

		String projNumber = curProjectInfo.getLongNumber();
		FilterInfo projFilter = new FilterInfo();
		projFilter.getFilterItems().add(
				new FilterItemInfo("longnumber", projNumber + "!%",
						CompareType.LIKE));
		projFilter.getFilterItems().add(
				new FilterItemInfo("longnumber", projNumber));

		String orgUnitId = null;
		if(orgUnit.getPartFI()==null||orgUnit.getPartFI().getId()==null){
			SelectorItemCollection selector=new SelectorItemCollection();
			selector.add("partFI.id");
			FullOrgUnitInfo fullOrgUnitInfo = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(orgUnit.getId()),selector);
			//判断fullOrgUnitInfo中是否包含partFI的值 避免NullPointer
			if(fullOrgUnitInfo.getPartFI()!=null){		
				
				orgUnitId=fullOrgUnitInfo.getPartFI().getId().toString();
			}
		}else{
			orgUnitId=orgUnit.getPartFI().getId().toString();
		}
		projFilter.getFilterItems().add(
				new FilterItemInfo("fullOrgUnit", orgUnitId));
		if(isOnlyLeaf){
			projFilter.getFilterItems().add(
					new FilterItemInfo("isLeaf", Boolean.TRUE));
			projFilter.setMaskString("(#0 or #1) and #2 and #3");
		}else{
			projFilter.setMaskString("(#0 or #1) and #2");
		}
		view = new EntityViewInfo();
		view.setFilter(projFilter);
		view.getSelector().add("id");

		CurProjectCollection projs = new CurProjectCollection();
		projs = CurProjectFactory.getRemoteInstance().getCurProjectCollection(view);

		for (Iterator iter = projs.iterator(); iter.hasNext();) {
			CurProjectInfo element = (CurProjectInfo) iter.next();
			if (element == null)
				continue;
			String id = element.getId().toString();
			ids.add(id);
		}

		return ids;
	}

	/**
	 * 设置人员
	 * @param box
	 * @param owner
	 * @param cuId
	 */
	public static void setPersonF7(KDPromptBox box, CoreUIObject owner, String cuId) {
		setPersonF7(box,owner,cuId,true);
	}
	public static void setPersonF7(KDPromptBox box, CoreUIObject owner, String cuId,boolean isSingle) {
		HashMap param = new HashMap();

		if(cuId!=null){
			param.put(PersonF7UI.CU_ID, cuId);
		}else{
			param.put(PersonF7UI.ALL_ADMIN, "YES");
		}

        ((KDBizPromptBox)box).setHasCUDefaultFilter(false);
        PersonPromptBox pmt = new PersonPromptBox(owner,param);
        pmt.setIsSingleSelect(isSingle);
//        map.put(PersonF7UI.ALL_ADMIN, "YES");

        box.setSelector(pmt);

        //通用F7是可以做到隔离的,只要bizPromptBox.setEntityViewInfo(view)正确 JinXP  20071217
        if (cuId!=null) {

            EntityViewInfo view = new EntityViewInfo();

    		SorterItemCollection sorc = view.getSorter();
    		SorterItemInfo sort = new SorterItemInfo("number");
    		sorc.add(sort);

            FilterInfo filter = new FilterInfo();
            view.setFilter(filter);
            FilterItemCollection fic = filter.getFilterItems();
            fic.add(new FilterItemInfo("CU2.id", cuId));
            fic.add(new FilterItemInfo("CU2.id", null,CompareType.NOTEQUALS ));
            fic.add(new FilterItemInfo("CU.id", cuId));
            filter.setMaskString("(#0 and #1) or #2 ");

            ((KDBizPromptBox)box).setEntityViewInfo(view);
        }else{
            //防止输入编码调出通用F7来选择，那样无法做到隔离
//            ((KDBizPromptBox)box).setQueryInfo(null);
        }
	}

	public static PeriodInfo getGLCurCompanyCurPeriod(){
		CompanyOrgUnitInfo curC = SysContext.getSysContext().getCurrentFIUnit();
		PeriodInfo curP = null;
		try {
			curP = SystemStatusCtrolUtils.getCurrentPeriod(null, SystemEnum.GENERALLEDGER, curC);
		} catch (Exception e) {
			// @AbortException
			ExceptionHandler.handle(e);
		}

		return curP;
	}
	

	/**
	 * 判断是否是已经锁定而抛出的异常
	 * @param e
	 * @return
	 */
	public static boolean hasMutexed(Throwable e){
		boolean hasMutex=false;
		if(e instanceof EASBizException){
			EASBizException e2=(EASBizException)e;
			String error=EASResource.getString(FrameWorkClientUtils.strResource+ "Error_ObjectUpdateLock_Request");
			if(e2.getMessage()!=null&&e2.getMessage().equals(error)){
				hasMutex=true;
			}
		}
		return hasMutex;
	}
	
	/**
	 * 申请数据锁
	 * @param ui
	 * @param idList
	 * @param oprtState
	 * @throws Throwable
	 */
	public static void requestDataObjectLock(CoreUI ui,List idList,String oprtState) throws Throwable{
		if(ui==null||idList==null||oprtState==null){
			return;
		}
		String oldOprtState=ui.getOprtState();
		try {
			ui.setOprtState(oprtState);
			for (Iterator iter = idList.iterator(); iter.hasNext();) {
				String id = iter.next().toString();
				ui.pubFireVOChangeListener(id);
			}
		}finally{
			ui.setOprtState(oldOprtState);
		}
	}
	
	/**
	 * 释放数据锁
	 * @param ui
	 * @param idList
	 * @throws Throwable
	 */
	public static void releaseDataObjectLock(CoreUI ui,List idList) throws Throwable{
		String oprtState="RELEASEALL";
		requestDataObjectLock(ui,idList,oprtState);
	}
	
	/**
	 *	获取所需的SQL语句中的ID（加上单引号）
	 *  修改null判断，以免依法nullpointer异常 
	 * @param idSet
	 * @update renliang
	 * @author liang_ren969
	 * 
	 */
	public static Set getSQLIdSet(Set idSet){
		Set returnSet = new HashSet();
		//if(idSet.size()<1 || idSet==null) return returnSet;
		/**
		 * 以上代码很很可能抛出NullPointerException
		 */
		//update by renliang
		if(idSet == null || idSet.size()<1){
			return returnSet;
		}
		Iterator ite = idSet.iterator();
		while(ite.hasNext()){
			returnSet.add("'"+ite.next().toString()+"'");
		}
		return returnSet;
	}
	
	/**
	 * 根据枚举类型,对Collection进行过滤
	 * @param 传入最后要传出的集合
	 * @param collString null 为得到与原集合相同类型的col,非null为从colls的info取出来的info的集合.
	 * by tim_gao 
	 */
	public static AbstractObjectCollection filterCollectionByEnum(AbstractObjectCollection tempCol,AbstractObjectCollection colls,
			StringEnum enumString ,String filter,String collString){
		if(colls!=null&&colls.size()>0){
			for(Iterator it = colls.iterator();it.hasNext();){
				IObjectValue tmpObj = null;
				IObjectValue tmp = (IObjectValue) it.next();
				
				if(collString == null){//同类型的coll
					if(enumString!=null){
						if(enumString.getValue().equals(tmp.get(filter))){
							tmpObj = tmp;
						}
					}else{
							tmpObj =tmp;
					}
					
				}else{//不同类型的coll要从info中取得
					if(enumString!=null){
						if(enumString.getValue().equals(tmp.get(filter))){
							tmpObj =(IObjectValue) tmp.get(collString);
						}
					}else{
							tmpObj =(IObjectValue) tmp.get(collString);
					
					}
				}
				if(tmpObj!=null){
					tempCol.addObject(tmpObj);
				}
			}
		}
		return tempCol;
	}
	
	
	/**
	 * 根据枚举类型,对Collection进行过滤
	 * @param 传入最后要传出的数组
	 * @param collString null 为得到与原集合相同类型的col,非null为从colls的info取出来的info的集合.
	 * by tim_gao 
	 */
	public static Object[] filterCollectionByEnum(AbstractObjectCollection colls,
			StringEnum enumString ,String filter,String collString){
		int max =0;
		List list = new ArrayList();
		if(colls!=null&&colls.size()>0){
			for(Iterator it = colls.iterator();it.hasNext();){
				IObjectValue tmpObj = null;
				IObjectValue tmp = (IObjectValue) it.next();
				
				if(collString == null){//同类型的coll
					if(enumString!=null){
						if(enumString.getValue().equals(tmp.get(filter))){
							tmpObj = tmp;
						}
					}else{
							tmpObj =tmp;
					}
					
				}else{//不同类型的coll要从info中取得
					if(enumString!=null){
						if(enumString.getValue().equals(tmp.get(filter))){
							tmpObj =(IObjectValue) tmp.get(collString);
						}
					}else{
							tmpObj =(IObjectValue) tmp.get(collString);
					
					}
				}
				if(tmpObj!=null){
					max++;
					list.add(tmpObj);
				}
			}
		}
		Object[] ar = new Object[max];
		for(int i = 0 ; i < max ; i ++){
			ar[i]=list.get(i);
		}
		return ar;
	}
	
	
	/**
	 * 描述：根据指定参数查找窗口
	 * @param editUIName
	 * @param uiContext
	 * @param dataObjects
	 * @param oprt
	 * @return 
	 * @throws UIException
	 * @Author：jian_cao
	 * @CreateTime：2012-8-30
	 */
	public static IUIWindow findUIWindow(String editUIName, Map uiContext, Map dataObjects, String oprt) throws UIException {
		Frame frame = UIFactoryHelper.getMainFrame(uiContext);
		IMainUIObject mainUI = null;
		if (frame instanceof NewMainFrame) {
			mainUI = ((NewMainFrame) frame).getMainUI();
		} else {
			mainUI = UIFactoryHelper.getMainUIObject(uiContext);
		}
		IUIObject uiObject = mainUI.getUiManager().findUIObject(editUIName, uiContext, dataObjects, oprt);
		return (uiObject == null ? null : uiObject.getUIWindow());
	}
	
	/**
	 * 描述：初始化工程项目F7
	 * @throws Exception
	 * @Author：jian_cao
	 * @CreateTime：2012-9-21
	 */
	public static void initCurProjectF7(KDTable kdtEntrys) {

		KDBizPromptBox kdtEntrys_materialNumber_PromptBox = new KDBizPromptBox();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		//设置过滤条件为当前组织
		filter.appendFilterItem("CU.id", SysContext.getSysContext().getCurrentCtrlUnit().getId().toString());
		view.setFilter(filter);
		kdtEntrys_materialNumber_PromptBox.setEntityViewInfo(view);
		kdtEntrys_materialNumber_PromptBox.setQueryInfo("com.kingdee.eas.fdc.basedata.app.CurProjectQuery");
		KDTDefaultCellEditor kdtEntrys_materialNumber_CellEditor = new KDTDefaultCellEditor(kdtEntrys_materialNumber_PromptBox);
		kdtEntrys.getColumn("curProject").setEditor(kdtEntrys_materialNumber_CellEditor);
	}
}
