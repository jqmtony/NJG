/*
 * @(#)FDCClientUtils.java
 *
 * 金蝶国际软件集团有限公司版权所有
 */
package com.kingdee.eas.port.markesupplier.uitl;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.TreeNode;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.ctrl.swing.KDToolBar;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.permission.OrgRangeCollection;
import com.kingdee.eas.base.permission.OrgRangeInfo;
import com.kingdee.eas.base.permission.OrgRangeType;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.permission.util.PermissionRangeHelper;
import com.kingdee.eas.base.permission.util.ToolUtils;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.SystemStatusCtrolUtils;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.UsedStatusEnum;
import com.kingdee.eas.basedata.org.AdminOrgUnitCollection;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.IAdminOrgUnit;
import com.kingdee.eas.basedata.org.ICompanyOrgUnit;
import com.kingdee.eas.basedata.org.IPositionMember;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.PositionInfo;
import com.kingdee.eas.basedata.org.PositionMemberCollection;
import com.kingdee.eas.basedata.org.PositionMemberFactory;
import com.kingdee.eas.basedata.person.IPerson;
import com.kingdee.eas.basedata.person.PersonFactory;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.basedata.person.client.PersonF7UI;
import com.kingdee.eas.basedata.person.client.PersonPromptBox;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fi.gl.GlWebServiceUtil;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.SystemEnum;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.multiDetail.DetailPanel;
import com.kingdee.eas.scm.common.SCMBillCommonFacadeFactory;
import com.kingdee.eas.scm.common.client.GeneralKDPromptSelectorAdaptor;
import com.kingdee.eas.scm.im.inv.client.InvClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

/**
 *
 * 描述:房地产系统客户端工具类
 *
 * @author liupd date:2006-8-15
 *         <p>
 * @version EAS5.1.3
 */
public class ClientUtils {

	public final static String RESOURCE = "com.kingdee.eas.fdc.basedata.client.FdcResource";

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
	 * 设置单据上所有控件不可编辑 2009-10-09 by wp
	 */
	public static void setViewCompState(CoreUIObject ui) {
		Component[] components = ui.getComponents();
		Component component = null;
		for (int i = 0, size = components.length; i < size; i++) {
			component = components[i];
			if (component instanceof DetailPanel) {
				DetailPanel dp = (DetailPanel) component;
				Component[] dpcomponents = null;
				Component dpcomponent = null;
				dpcomponents = dp.getComponents();
				for (int j = 0, dpsize = dpcomponents.length; j < dpsize; j++) {
					dpcomponent = dpcomponents[j];
					KDPanel panel = (KDPanel) dpcomponent;
					Component[] pcomponents = null;
					Component pcomponent = null;
					pcomponents = panel.getComponents();
					for (int k = 0, psize = pcomponents.length; k < psize; k++) {
						pcomponent = pcomponents[k];
						if ("btnAddEntryLine".equals(pcomponent.getName())
								|| "btnInsertEntryLine".equals(pcomponent
										.getName())
								|| "btnRemoveEntryLine".equals(pcomponent
										.getName())
								|| "btnAddnewLine".equals(pcomponent.getName())
								|| "btnInsertLine".equals(pcomponent.getName())
								|| "btnRemoveLines"
										.equals(pcomponent.getName())
								|| "btnAddSwapLine"
										.equals(pcomponent.getName())
								|| "btnInsertSwapLine".equals(pcomponent
										.getName())
								|| "btnRemoveSwapLine".equals(pcomponent
										.getName())) {
							pcomponent.setEnabled(false);
						}
					}
				}
			} else {
				component.setEnabled(false);
			}
		}
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
	 * 描述：检查单据是否在工作流中
	 *
	 * @param ui
	 *            当前UI，显示消息时用
	 * @param id
	 *            单据ID
	 * @author:liupd 创建时间：2006-9-29
	 *               <p>
	 */
	public static void checkBillInWorkflow(CoreUIObject ui, String id) {

		ProcessInstInfo instInfo = null;
		ProcessInstInfo[] procInsts = null;
		try {
			IEnactmentService service2 = EnactmentServiceFactory
					.createRemoteEnactService();
			procInsts = service2.getProcessInstanceByHoldedObjectId(id);
		} catch (BOSException e) {
			ExceptionHandler.handle(e);
		}

		for (int i = 0, n = procInsts.length; i < n; i++) {
			// modify by gongyin,流程挂起时也显示流程图
			if ("open.running".equals(procInsts[i].getState())
					|| "open.not_running.suspended".equals(procInsts[i]
							.getState())) {
				instInfo = procInsts[i];
			}
		}
		if (instInfo != null) {
			MsgBox.showWarning(ui, EASResource
					.getString(FrameWorkClientUtils.strResource
							+ "Msg_BindWfInstance"));
			SysUtil.abort();
		}
	}

	/**
	 * 如果在工作流中，用infoMsg给出提示信息
	 * 
	 * @param ui
	 * @param id
	 * @param infoMsg
	 * @author owen_wen 2011-08-18
	 */
	public static void checkBillInWorkflow(CoreUIObject ui, String id, String infoMsg) {
		ProcessInstInfo instInfo = null;
		ProcessInstInfo[] procInsts = null;
		try {
			IEnactmentService service2 = EnactmentServiceFactory.createRemoteEnactService();
			procInsts = service2.getProcessInstanceByHoldedObjectId(id);
		} catch (BOSException e) {
			ExceptionHandler.handle(e);
		}

		for (int i = 0, n = procInsts.length; i < n; i++) {
			if ("open.running".equals(procInsts[i].getState()) || "open.not_running.suspended".equals(procInsts[i].getState())) {
				instInfo = procInsts[i];
			}
		}
		if (instInfo != null) {
			MsgBox.showWarning(ui, infoMsg);
			SysUtil.abort();
		}
	}

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
	 * 描述：获取编码规则是否支持修改，支持修改 － true， 不支持修改 － false，如果没有定义编码规则则返回true
	 *
	 * @param info
	 *            当前单据的Info
	 * @param orgUnitId
	 *            当前组织id
	 * @return
	 * @author:liupd 创建时间：2006-9-29
	 *               <p>
	 */
	public static boolean isAllowModifyNumber(IObjectValue info,
			String orgUnitId) {
		boolean result = false;
		try {
			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
					.getRemoteInstance();
			if (iCodingRuleManager.isExist(info, orgUnitId)) {
				return iCodingRuleManager.isModifiable(info, orgUnitId);
			} else {
				return true;
			}

		} catch (Exception e) {
			ExceptionHandler.handle(e);
		}

		return result;
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
            e.printStackTrace();
        }

		return view;
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
			curP = SystemStatusCtrolUtils.getCurrentPeriod(null,	SystemEnum.GENERALLEDGER, curC);
		} catch (EASBizException e) {
			ExceptionHandler.handle(e);
		} catch (BOSException e) {
			ExceptionHandler.handle(e);
		}

		return curP;
	}
	
	/**
	* 初始化申请人F7
	* 
	* @param coreui
	*            所属UI
	* @param prmtPerson
	*            申请人F7控件
	* @param hasCUFilter
	*            是否CU隔离
	* @throws BOSException
	* @throws Exception
	*/
	public static void initPersonF7(CoreUIObject coreui,KDBizPromptBox prmtPerson, boolean hasCUFilter)throws BOSException, Exception {
		prmtPerson.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");
		HashMap map = new HashMap();
		map.put("All_Admins", "YES");
		map.put("DEFAULT_SHOW_ALL", "AAA");
		
		PersonPromptBox select = new PersonPromptBox(coreui, map);
		prmtPerson.setSelector(select);
		prmtPerson.setHasCUDefaultFilter(hasCUFilter);
		prmtPerson.setDisplayFormat("$name$");
		prmtPerson.setEditFormat("$number$");
		prmtPerson.setCommitFormat("$number$");
		FilterInfo filter = new FilterInfo();
		PersonInfo person = SysContext.getSysContext().getCurrentUserInfo().getPerson();
		filter = new FilterInfo();
		EntityViewInfo viewInfo = new EntityViewInfo();
		if(person!=null)
			filter.getFilterItems().add(new FilterItemInfo("id", person.getId().toString()));
		viewInfo.setFilter(filter);
		prmtPerson.setEntityViewInfo(viewInfo);
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
	
	public static String getOprtState(String oprtState){
		String state = null;
		if (oprtState.equals(OprtState.ADDNEW)) {
			state = "新增";
		}else if (oprtState.equals(OprtState.EDIT)) {
			state = "修改";
		}else if (oprtState.equals(OprtState.VIEW)) {
			state = "查看";
		}
		return state;
	}
	
	public static PersonInfo getUser(){
		UserInfo userInfo = SysContext.getSysContext().getCurrentUserInfo();
		PersonInfo perInfo = userInfo.getPerson();
		return perInfo;
	}
	
	public static AdminOrgUnitInfo getDefaultOrgUnit() throws BOSException, EASBizException
	{
		UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
		IPerson iPerson = PersonFactory.getRemoteInstance();
		AdminOrgUnitInfo primaryAdmin = null;
		CompanyOrgUnitInfo tempCompany = null;
		CompanyOrgUnitInfo cou = null;
		if(user.getPerson() != null && user.getPerson().getId() != null)
	    {
			primaryAdmin = iPerson.getPrimaryAdminOrgUnit(user.getPerson().getId());
			UserInfo userMG = ToolUtils.getUserAllInfo(null, SysContext.getSysContext().getCurrentUserInfo().getId().toString());
			OrgRangeCollection userRangeCollection = userMG.getOrgRange();
			FilterInfo filter = new FilterInfo();
			AdminOrgUnitInfo adminOrgUnitInfo = null;
			AdminOrgUnitInfo admTempInfo = null;
			OrgRangeInfo orgRageInfo = null;
			EntityViewInfo viewInfo = new EntityViewInfo();
			FullOrgUnitInfo fullInfo = null;
			IAdminOrgUnit iAdmin = AdminOrgUnitFactory.getRemoteInstance();
			HashSet set = new HashSet();
			boolean isHasPermission = false;
			if(userRangeCollection.size() > 0)
	        {
				for(int i = 0; i < userRangeCollection.size(); i++)
	            {
					orgRageInfo = userRangeCollection.get(i);
					if(orgRageInfo.getType().equals(OrgRangeType.ADMIN_ORG_TYPE))
	                {
						fullInfo = orgRageInfo.getOrg();
						set.add(fullInfo.getId().toString());
	                }
	            }
	
				filter.getFilterItems().add(new FilterItemInfo("ID", set, CompareType.INCLUDE));
				viewInfo.setFilter(filter);
				AdminOrgUnitCollection aouCol = iAdmin.getAdminOrgUnitCollection(viewInfo);
				int i = 0;
				int size = aouCol.size();
				do
	            {
					if(i >= size)
						break;
					adminOrgUnitInfo = aouCol.get(i);
					if(adminOrgUnitInfo.getId() != null && primaryAdmin != null && primaryAdmin.getId().toString().equals(adminOrgUnitInfo.getId().toString()))
	                {
						admTempInfo = adminOrgUnitInfo;
						isHasPermission = true;
						break;
	                }
					i++;
	            } while(true);
	        }
			if(primaryAdmin != null)
				if(isHasPermission)
	            {
					tempCompany = InvClientUtils.getCompanyInfo(primaryAdmin);
					if(tempCompany != null)
	                {
						if(cou == null)
							cou = tempCompany;
						return primaryAdmin;
	                }
	            } else
	            {
	            	tempCompany = InvClientUtils.getCompanyInfo(admTempInfo);
	            	if(tempCompany != null)
	                {
	            		if(cou == null)
	            			cou = tempCompany;
	            		return admTempInfo;
	                }
	            }
	    }
		if(tempCompany == null)
	    {
			String userID = SysContext.getSysContext().getCurrentUserInfo().getId().toString();
			ArrayList adminList = null;
			adminList = (ArrayList)PermissionRangeHelper.getUserOrgId(null, new ObjectUuidPK(BOSUuid.read(userID)), OrgRangeType.ADMIN_ORG_TYPE);
			AdminOrgUnitInfo adminInfo = SysContext.getSysContext().getCurrentAdminUnit();
			if(adminList != null && adminList.size() > 0)
	        {
				if(adminInfo != null && adminList.contains(adminInfo.getId().toString()))
	            {
					tempCompany = InvClientUtils.getCompanyInfo(adminInfo);
					if(tempCompany != null)
	                {
						cou = tempCompany;
						return adminInfo;
	                }
	            }
				if(tempCompany == null)
	            {
					for(int i = 0; i < adminList.size(); i++)
	                {
						Map couMap = SCMBillCommonFacadeFactory.getRemoteInstance().getCompanyInfos((String[])adminList.toArray(new String[0]), OrgType.Admin, OrgType.Company);
						for(Iterator iter = couMap.keySet().iterator(); iter.hasNext();)
	                    {
							String key = (String)iter.next();
							if(couMap.get(key) != null)
	                        {
								cou = (CompanyOrgUnitInfo)couMap.get(key);
								return AdminOrgUnitFactory.getRemoteInstance().getAdminOrgUnitInfo(new ObjectUuidPK(key));
	                        }
	                    }
	
	                }
	
	            }
	        }
	    }
		if(tempCompany == null)
			return null;
		else
			return null;
	}
	
	/**
	 * 由部门得到财务组织
	 */
	public static CompanyOrgUnitInfo getComOrgByAdminOrg(Context ctx,
			AdminOrgUnitInfo adminOrgUnitInfo) throws EASBizException,
			BOSException {
		CompanyOrgUnitInfo costCenterOrgUnitInfo = null;
		AdminOrgUnitInfo parentCost = null;
		parentCost = adminOrgUnitInfo;
		do {
			if (parentCost == null)
				break;
			if (parentCost.isIsCompanyOrgUnit()) {
				String id = parentCost.getId().toString();
				if (id == null)
					continue;
				ICompanyOrgUnit iCompanyOrgUnit = null;
				if(ctx!=null)
					iCompanyOrgUnit = CompanyOrgUnitFactory.getLocalInstance(ctx);
				else
					iCompanyOrgUnit = CompanyOrgUnitFactory.getRemoteInstance();
				costCenterOrgUnitInfo = (CompanyOrgUnitInfo) iCompanyOrgUnit.getValue(new ObjectUuidPK(id));
				if(costCenterOrgUnitInfo.isIsBizUnit()){
                	break;
                }
			}
			parentCost = parentCost.getParent();
			if (parentCost != null) {
				String id = parentCost.getId().toString();
				if (id != null) {
					IAdminOrgUnit iAdmin = null;
					if (ctx!=null) 
						iAdmin = AdminOrgUnitFactory.getLocalInstance(ctx);
					else
						iAdmin = AdminOrgUnitFactory.getRemoteInstance();
					parentCost = (AdminOrgUnitInfo) iAdmin.getValue(new ObjectUuidPK(id));
				}
			}
		} while (true);
		return costCenterOrgUnitInfo;
	}
	
	 /**
	 *  获得职员_任职情况 主要职位的部门信息
	 * @return
	 */
	public static AdminOrgUnitInfo getPosiMemByDeptUser(PersonInfo person) {
		
		AdminOrgUnitInfo workDept = null;
		PositionMemberCollection positMems = getPositionMemberByUser(person);
		
		if (positMems == null)	
			return workDept;
		
		for (int i = 0; i < positMems.size(); i++) {
			if (positMems.get(i).isIsPrimary()) {
				workDept = positMems.get(i).getPosition().getAdminOrgUnit();
				return workDept;
			}
		}
		return workDept;
	}
	
    /**
	 * 根据personId 获取员工职位
	 * */
	public  PositionInfo getPositionByPersonId( String personId)
	    throws BOSException, EASBizException
	{
	    PositionInfo info = null;
	    String oql = "select position.* where isPrimary = 1 and person.id = '" + personId + "'";
	    IPositionMember ipp = null;
	    ipp = PositionMemberFactory.getRemoteInstance();
	    PositionMemberCollection c = ipp.getPositionMemberCollection(oql);
	    if(c.size() > 0)
	        info = c.get(0).getPosition();
	    return info;
	}
	/**
	 * 根据用户 获得职员_任职情况 职位列表
	 * 
	 * @param person
	 * 
	 * @return
	 */
	public static  PositionMemberCollection getPositionMemberByUser(PersonInfo person) {
		
		try {
			String oql = "select *, person.*, position.adminOrgUnit.* where person.id = '" + person.getId() + "'" ;
			return PositionMemberFactory.getRemoteInstance().getPositionMemberCollection(oql);
		} catch (Exception e1) {
		}
		return null;
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
}
