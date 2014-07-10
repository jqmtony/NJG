/*
 * @(#)FDCClientUtils.java
 *
 * �����������������޹�˾��Ȩ����
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.Component;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.TreeNode;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.ctrl.swing.KDToolBar;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
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
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.ProjectCollection;
import com.kingdee.eas.basedata.assistant.ProjectFactory;
import com.kingdee.eas.basedata.assistant.ProjectInfo;
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
import com.kingdee.eas.fi.gl.GlWebServiceUtil;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.SystemEnum;
import com.kingdee.eas.framework.bireport.bimanager.ws.paramCtx.function.CompanyInfoparamFactory;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.port.pm.contract.ContractBillEntryFactory;
import com.kingdee.eas.port.pm.utils.ContractClientUtils;
import com.kingdee.eas.port.pm.utils.FDCUtils;
import com.kingdee.eas.scm.common.client.GeneralKDPromptSelectorAdaptor;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;

/**
 *
 * ����:���ز�ϵͳ�ͻ��˹�����
 *
 * @author liupd date:2006-8-15
 *         <p>
 * @version EAS5.1.3
 */
public class FDCClientUtils {

	public final static String RESOURCE = "com.kingdee.eas.fdc.basedata.client.FdcResource";

	public static Map filterMap = new HashMap();
	/**
	 *
	 * ��������ȡ��������Դ
	 *
	 * @param resName
	 *            ��Դ������
	 * @return
	 * @author:liupd ����ʱ�䣺2006-8-15
	 *               <p>
	 */
	public static String getRes(String resName) {
		return EASResource.getString(RESOURCE, resName);
	}

	/**
	 *
	 * ���������ToolBar�϶���ķָ���
	 *
	 * @param toolBar
	 * @author:liupd ����ʱ�䣺2005-9-5
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
	 * ��������鹤����Ŀ�Ƿ񱻵���(��ͬ�����ı���ͬ)���ã�����ѱ����ã�����ʾ����ѡ��Ĺ�����Ŀ�Ѿ������ã����������¼���
	 *
	 * @param ui
	 * @param projId
	 * @throws Exception
	 * @author:liupd ����ʱ�䣺2006-9-26
	 *               <p>
	 */
	public static void checkProjUsed(CoreUIObject ui, String projId)
			throws Exception {
		Map usedMap = ProjectFacadeFactory.getRemoteInstance().canAddNew(projId); 
		if (usedMap.containsKey("HASUSED")&&((Boolean)(usedMap.get("HASUSED"))).booleanValue()) {
			if(usedMap.containsKey("CONTRACTNUMBERS")||usedMap.containsKey("CONTRACTCHANGENUMBERS")){
				String headMsg = "���ڴ��ϼ�������Ŀ��ֵ���������Ŀ�ĺ�ͬ��֡������ֻ������ı���ͬ�����֣������������¼�������Ŀ��";
				StringBuffer detailMsg = new StringBuffer();
				
				if(usedMap.containsKey("CONTRACTNUMBERS")){
					detailMsg.append("��ͬ����Ϊ��");
					detailMsg.append(usedMap.get("CONTRACTNUMBERS"));
					detailMsg.append("\n");
				}
				if(usedMap.containsKey("CONTRACTCHANGENUMBERS")){
					detailMsg.append("�������Ϊ��");
					detailMsg.append(usedMap.get("CONTRACTCHANGENUMBERS"));
					detailMsg.append("\n");
				}
				if(usedMap.containsKey("CONNOTEXTNUMBERS")){
					detailMsg.append("���ı�����Ϊ��");
					detailMsg.append(usedMap.get("CONNOTEXTNUMBERS"));
					detailMsg.append("\n");
				}
				FDCMsgBox.showDetailAndOK(ui, headMsg, detailMsg.toString(), 0);
//				MsgBox.showError(ui,headMsg,detailMsg.toString());
				SysUtil.abort();
			}else{
				String warning = getRes("projUsed");
				MsgBox.showInfo(ui, warning);
				SysUtil.abort();
			}
		}
	}

	/**
	 *
	 * ��������鵥���Ƿ��ڹ�������
	 *
	 * @param ui
	 *            ��ǰUI����ʾ��Ϣʱ��
	 * @param id
	 *            ����ID
	 * @author:liupd ����ʱ�䣺2006-9-29
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
			// modify by gongyin,���̹���ʱҲ��ʾ����ͼ
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
	 * ����ڹ������У���infoMsg������ʾ��Ϣ
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
	 * �������������Ľڵ�
	 *
	 * @param node
	 * @param leafNodesIdSet
	 * @author:liupd ����ʱ�䣺2006-7-20
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
	 * ���������ɹ�����Ŀ����Ҷ�ӽڵ�
	 *
	 * @param node
	 * @param leafNodesIdSet
	 * @author:liupd ����ʱ�䣺2006-7-20
	 *               <p>
	 */
	public static void genProjLeafNodesIdSet(TreeNode node, Set leafNodesIdSet) {

		int count = node.getChildCount();
		// ���û���¼�������Ŀ��˵����Ҷ�ӽڵ㣬ֱ�ӷ�������id����
		if (count == 0) {
			if(((DefaultKingdeeTreeNode) node).getUserObject() instanceof ProjectInfo){
				ProjectInfo ProjectInfo = ((ProjectInfo) ((DefaultKingdeeTreeNode) node)
						.getUserObject());
				String oid = ProjectInfo.getId().toString();
				leafNodesIdSet.add(oid);
			}else{
				FDCMsgBox.showWarning(FDCClientHelper.getCurrentActiveWindow(), "�ý�㲻�ǹ�����Ŀ");
				SysUtil.abort();
			}

			return;
		}
		DefaultKingdeeTreeNode treeNode = null;
		for (int i = 0; i < count; i++) {
			treeNode = (DefaultKingdeeTreeNode) node.getChildAt(i);
			if (treeNode.isLeaf()) {
				if (!(treeNode.getUserObject() instanceof ProjectInfo))
					continue;
				String id = ((ProjectInfo) treeNode.getUserObject()).getId()
						.toString();
				leafNodesIdSet.add(id);
			} else {
				genProjLeafNodesIdSet(treeNode, leafNodesIdSet);
			}
		}

	}

	/**
	 *
	 * ��������ȡ��������Ƿ�֧���޸ģ�֧���޸� �� true�� ��֧���޸� �� false�����û�ж����������򷵻�true
	 *
	 * @param info
	 *            ��ǰ���ݵ�Info
	 * @param orgUnitId
	 *            ��ǰ��֯id
	 * @return
	 * @author:liupd ����ʱ�䣺2006-9-29
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
	 * ����������Ƿ�ѡ������Ŀ���̣���ѡ������ϸ�ڵ�
	 *
	 * @author:liupd ����ʱ�䣺2006-7-27
	 *               <p>
	 */
	public static void checkSelectProj(CoreUIObject ui,
			DefaultKingdeeTreeNode selectTreeNode) {

		// ��ѡ�񹤳���Ŀ
		if (selectTreeNode == null) {
			MsgBox.showWarning(ui, ContractClientUtils.getRes("selectProjPls"));
			SysUtil.abort();
		}

		// ��ѡ�񹤳���Ŀ�ڵ㣨�ǹ�˾�����û�й�����Ŀ���������ӹ�����Ŀ��
		if (!(selectTreeNode.getUserObject() instanceof ProjectInfo)) {
			MsgBox.showWarning(ui, ContractClientUtils.getRes("selectProjPls2"));
			SysUtil.abort();
		}

		// ��ѡ�񹤳���Ŀ��ϸ�ڵ�
		if (!selectTreeNode.isLeaf()) {
			MsgBox.showWarning(ui, ContractClientUtils.getRes("selectProjLeafPls"));
			SysUtil.abort();
		}

	}

	/**
	 * ��鹤����Ŀ��û�ж�Ӧ���ɱ�����
	 *
	 * @param ui
	 * @param selectTreeNode
	 */
	public static void checkProjWithCostOrg(CoreUIObject ui,
			DefaultKingdeeTreeNode selectTreeNode) {
//		ProjectInfo curProj = (ProjectInfo) selectTreeNode.getUserObject();
//		FDCSQLBuilder builder=new FDCSQLBuilder();
//		builder.appendSql("select 1 from T_Org_costCenter where fid in (select fcostCenterId from T_FDC_Project where fid=?) and fisBizUnit=1");
//		builder.addParam(curProj.getId().toString());
//		try{
//			if(!builder.isExist()){
//				String error="������Ŀ��Ӧ�ĳɱ����Ĳ���ʵ��ɱ�����";
//				String errorDetail="����������ԭ���£�\n1.û�����ù�����Ŀ��ɱ����ĵĶ�Ӧ��ϵ\n2.��Ϊ�ϼ�������Ŀ��Ӧ������ɱ����¼��ɱ�����û�����ö�Ӧ��ϵ�����¹�����Ŀ��Ӧ�ĳɱ�����������ɱ�����";
//				FDCMsgBox.showDetailAndOK(ui, error, errorDetail, 1);
//				SysUtil.abort();
//			}
//		}catch(BOSException e){
//			ui.handUIException(e);
//		}
	}

	/**
	 * ��鹤����Ŀ�Ƿ��Ӧ���ɱ�����
	 * @param projId
	 * @return 
	 */
	public static boolean checkProjWithCostOrg(String projId){
		boolean exists = false;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems()
				.add(new FilterItemInfo("Project.id", projId));
		try {
			exists = ProjectWithCostCenterOUFactory.getRemoteInstance().exists(
					filter);
		} catch (EASBizException e) {
			ExceptionHandler.handle(e);
		} catch (BOSException e) {
			ExceptionHandler.handle(e);
		}
		return exists;
	}
	
	//��ö���������Ŀ��Id
	private static String getTopProjId(String projId) {
		ProjectInfo ProjectInfo = null;
		try {
			ProjectInfo = ProjectFactory.getRemoteInstance().getProjectInfo(new ObjectStringPK(projId));
		} catch (EASBizException e1) {
			ExceptionHandler.handle(e1);
		} catch (BOSException e1) {
			ExceptionHandler.handle(e1);
		}

		if(ProjectInfo.getParent() == null) return projId;

		String longNumber = ProjectInfo.getLongNumber();
		String pln = longNumber.substring(0, longNumber.indexOf("!"));
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo fi = new FilterInfo();
		fi.getFilterItems().add(new FilterItemInfo("longNumber", pln));
		
		// ��ֻ��longNumber���ˣ��õ��Ĳ�ֻһ��������ĿId�����Ի�Ҫ����CU���ˡ� Added By Owen_wen 2011-01-21
		String controlUnitId = ProjectInfo.getCU().getId().toString();
		fi.getFilterItems().add(new FilterItemInfo("CU", controlUnitId));
		
		view.setFilter(fi);
		ProjectCollection coll = null;
		try {
			coll = ProjectFactory.getRemoteInstance().getProjectCollection(view);
		} catch (BOSException e1) {
			ExceptionHandler.handle(e1);
		}
		String pProjId = null;
		if(coll.size() > 0) {
			ProjectInfo pProjInfo = coll.get(0);
			pProjId = pProjInfo.getId().toString();
		}
		return pProjId;
	}

	/**
	 * ��������������������ɱ����Ķ�Ӧ��ϵ����ȡ�ɱ����Ķ�Ӧ����֯������ȡ�ϼ��ɱ����Ķ�Ӧ����֯
	 * @param projId
	 * @return
	 */
	public static FullOrgUnitInfo getCostOrgByProj(String projId) {
		FullOrgUnitInfo orgInfo = null;
		ProjectInfo info = null;
		try {
			info = ProjectFactory.getRemoteInstance().getProjectInfo(new ObjectUuidPK(projId));
			if(info!=null) {
				orgInfo = info.getCompany().castToFullOrgUnitInfo();
				orgInfo = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(orgInfo.getId()));
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return orgInfo;
	}

	/**
	 *
	 * ������ȥ��û�в�����֯���ԵĽڵ�
	 *
	 * @param node
	 * @author:liupd ����ʱ�䣺2006-9-25
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
	 * ������������֯id��ȡ�������¼���֯id�ļ���
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 * @author:liupd ����ʱ�䣺2006-7-26
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
	 * ���������ݵ�ǰ������֯��ȡ�¼�������ʵ�������֯
	 * ͨ����������ƥ��
	 * �����Ż�Ϊsql
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
	 * ���������ݲ�����֯��ȡ������ϸ������Ŀ
	 * 
	 * @param idSet:ʵ�������֯ID����
	 */
	public static Map getPrjInfosByCompany(Set idSet) throws Exception {
		Map retMap = new HashMap();
		Set prjIdSet = new HashSet();
		ProjectCollection projects = new ProjectCollection();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select unit.flongnumber,prj.flongnumber,prj.fid,prj.fdisplayname_l2,prj.ffullorgunit from t_fdc_Project prj ");
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
		ProjectInfo info = null;
		CompanyOrgUnitInfo fullOrgUnit = null;
		while(rs.next()){
			info = new ProjectInfo();
			fullOrgUnit = new CompanyOrgUnitInfo();
			String id=rs.getString("fid");
			String name = rs.getString("fdisplayname_l2");
			String orgId = rs.getString("ffullorgunit");
			info.setId(BOSUuid.read(id));
			info.setDisplayName(name);
			fullOrgUnit.setId(BOSUuid.read(orgId));
			info.setCompany(fullOrgUnit);
			
			prjIdSet.add(id);
			projects.add(info);
		}
		retMap.put("prjIdSet", prjIdSet);
		retMap.put("projects", projects);
		return retMap;
	}
	/**
	 *
	 * ������������ĿId��ȡ�������¼���Ŀid����
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 * @author:liupd ����ʱ�䣺2006-7-26
	 *               <p>
	 */
	public static Set genProjectIdSet(BOSUuid id) throws Exception {
		Set idSet = new HashSet();
		ProjectInfo ProjectInfo = ProjectFactory.getRemoteInstance()
				.getProjectInfo(new ObjectUuidPK(id));
		String longNumber = ProjectInfo.getLongNumber();

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("longNumber", longNumber + "!%",
						CompareType.LIKE));
		filter.getFilterItems().add(
				new FilterItemInfo("longNumber", longNumber));
		filter.getFilterItems().add(
				new FilterItemInfo("company.id", ProjectInfo
						.getCompany().getId().toString()));
		filter.setMaskString("(#0 or #1) and #2");
		view.setFilter(filter);

		ProjectCollection ProjectCollection = ProjectFactory
				.getRemoteInstance().getProjectCollection(view);

		for (Iterator iter = ProjectCollection.iterator(); iter.hasNext();) {
			ProjectInfo element = (ProjectInfo) iter.next();
			idSet.add(element.getId().toString());
		}
		return idSet;
	}

	/**
	 * ���������ݵ�ǰ������ĿID ��ȡ�¼�������Ŀid ����
	 * @param id
	 * @param isOnlyLeaf �Ƿ�����¼�����ϸ��Ŀ
	 * @return
	 * @throws Exception
	 */
	public static Set genProjectIdSet(BOSUuid id,boolean isOnlyLeaf) throws Exception {
		Set idSet = new HashSet();
		ProjectInfo ProjectInfo = ProjectFactory.getRemoteInstance()
				.getProjectInfo(new ObjectUuidPK(id));
		String longNumber = ProjectInfo.getLongNumber();

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("longNumber", longNumber + "!%",
						CompareType.LIKE));
		filter.getFilterItems().add(
				new FilterItemInfo("longNumber", longNumber));
		filter.getFilterItems().add(
				new FilterItemInfo("company.id", ProjectInfo
						.getCompany().getId().toString()));
		if(isOnlyLeaf){
			filter.getFilterItems().add(
					new FilterItemInfo("isLeaf", Boolean.TRUE));
			filter.setMaskString("(#0 or #1) and #2 and #3");
		}else{
			filter.setMaskString("(#0 or #1) and #2");
		}
		view.setFilter(filter);

		ProjectCollection ProjectCollection = ProjectFactory
				.getRemoteInstance().getProjectCollection(view);

		for (Iterator iter = ProjectCollection.iterator(); iter.hasNext();) {
			ProjectInfo element = (ProjectInfo) iter.next();
			idSet.add(element.getId().toString());
		}
		return idSet;
	}

	/**
	 *
	 * ���������ݺ�ͬ����ID��ȡ�������¼�Id����
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 * @author:liupd ����ʱ�䣺2006-7-26
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
	 * ����������ָ������ID��ȡ�������¼�Id����
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 * @author:liupd ����ʱ�䣺2006-7-26
	 *               <p>
	 */
	public static Set genTargetTypeIdSet(BOSUuid id) throws Exception {

		Set idSet = new HashSet();
		TargetTypeInfo TargetTypeInfo = TargetTypeFactory.getRemoteInstance()
				.getTargetTypeInfo(new ObjectUuidPK(id));
		String longNumber = TargetTypeInfo.getLongNumber();

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		//�¼�
		filter.getFilterItems().add(
				new FilterItemInfo("longNumber", longNumber + "!%",
						CompareType.LIKE));
		//����
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
	 * ����ͬ��ϸ��Ϣ�����Ƿ�ʹ��
	 *
	 * @param id
	 *            ��ͬ��ϸ��Ϣ����ID
	 * @return true - �ѱ�����, false - δ������
	 */
	public boolean isContractDetailDefUsed(String id) {
		boolean b = false;

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("detailDefID", id));
		try {
			b = ContractBillEntryFactory.getRemoteInstance().exists(filter);
		} catch (Exception e) {
			ExceptionHandler.handle(e);
		}

		return b;
	}

	public static ProjectInfo getProjectInfoForDisp(String projId) {
		ProjectInfo ProjectInfo = null;
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add(new SelectorItemInfo("displayName"));
		selectors.add(new SelectorItemInfo("CU.id"));
		try {
			ProjectInfo = ProjectFactory.getRemoteInstance().getProjectInfo(new ObjectStringPK(projId), selectors);
		} catch (EASBizException e) {
			ExceptionHandler.handle(e);
		} catch (BOSException e) {
			ExceptionHandler.handle(e);
		}
		return ProjectInfo;
	}



	/**
	 * ��ʽ����β�ϼ��е����ݸ�ʽ
	 * @param table ���
	 * @param colNames Ҫ��ʽ������Key����
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
	 * ��Ӧ�̺�׼������
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
	 * �������β���F7,ֻ��ѡ����ϸ�ڵ�
	 *
	 * @param bizPromptBox
	 * @param ui
	 */
	public static void setRespDeptF7(KDBizPromptBox bizPromptBox,
			CoreUIObject ui) {
		setRespDeptF7(bizPromptBox, ui, null);
	}
	/**
	 * �������β���F7,ֻ��ѡ����ϸ�ڵ�
	 *
	 * @param bizPromptBox
	 * @param ui
	 */
	public static void setRespDeptF7(KDBizPromptBox bizPromptBox,
			CoreUIObject ui, String cuId) {
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

		//�û���֯��Χ�ڵ���֯����ѡ��
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
			e.printStackTrace();
		}

		view.setFilter(filter);
		bizPromptBox.setEntityViewInfo(view);

		AdminF7 f7 = new AdminF7(ui);
		f7.showCheckBoxOfShowingAllOUs();
		f7.setIsCUFilter(true);
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

		// ֻ��ѡ����ϸ�ģ����Ӽ�������

		if (bizPromptBox.getClientProperty("RespDeptDataChangeLisenter") == null) {
			RespDeptDataChangeLisenter ls = new RespDeptDataChangeLisenter(ui);
			bizPromptBox.addDataChangeListener(ls);
			bizPromptBox.putClientProperty("RespDeptDataChangeLisenter", ls);
		}

		//ͨ��F7�ǿ������������,ֻҪbizPromptBox.setEntityViewInfo(view)��ȷ JinXP  20071217
		/*
//		��ֹ����������ͨ��F7��ѡ�������޷���������
		bizPromptBox.setQueryInfo(null);
		*/




	}
	
	public static void setRespDeptF7(KDBizPromptBox bizPromptBox, CoreUIObject ui, String cuId, boolean isMustSelectLeaf)
	  {
	    bizPromptBox.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminOrgUnitQuery");

	    EntityViewInfo view = new EntityViewInfo();

	    SorterItemCollection sorc = view.getSorter();
	    SorterItemInfo sort = new SorterItemInfo("number");
	    sorc.add(sort);

	    FilterInfo filter = new FilterInfo();

	    FilterItemCollection fic = filter.getFilterItems();
	    fic.add(new FilterItemInfo("isFreeze", new Integer(0)));
	    fic.add(new FilterItemInfo("isSealUp", new Integer(0)));

	    if (cuId != null) {
	      fic.add(new FilterItemInfo("CU.id", cuId));
	    }

	    try
	    {
	      Set authorizedOrgs = new HashSet();
	      Map orgs = (Map)ActionCache.get("FDCBillEditUIHandler.authorizedOrgs");
	      if (orgs == null) {
	        orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId()), OrgType.Admin, null, null, null);
	      }

	      if (orgs != null) {
	        Set orgSet = orgs.keySet();
	        Iterator it = orgSet.iterator();
	        while (it.hasNext()) {
	          authorizedOrgs.add(it.next());
	        }
	      }
	      FilterInfo filterID = new FilterInfo();
	      filterID.getFilterItems().add(new FilterItemInfo("id", authorizedOrgs, CompareType.INCLUDE));

	      filter.mergeFilter(filterID, "and");
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }

	    view.setFilter(filter);
	    bizPromptBox.setEntityViewInfo(view);

	    AdminF7 f7 = new AdminF7(ui);
	    f7.showCheckBoxOfShowingAllOUs();
	    f7.setIsCUFilter(true);
	    f7.setRootUnitID(cuId);

	    if (cuId != null) f7.setCurrentCUID(cuId);

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

	    if (bizPromptBox.getClientProperty("RespDeptDataChangeLisenter") == null) {
	      RespDeptDataChangeLisenter ls = new RespDeptDataChangeLisenter(ui, isMustSelectLeaf);
	      bizPromptBox.addDataChangeListener(ls);
	      bizPromptBox.putClientProperty("RespDeptDataChangeLisenter", ls);
	    }
	  }

	/**
	 * ��鵱ǰ��֯�Ƿ�ɱ�������֯
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
	 * ��鵱ǰ��֯�Ƿ�ɱ�������֯
	 *
	 * @param ui
	 */
	public static void checkCurrentBizCostCenterOrg(CoreUIObject ui) {
		checkCurrentCostCenterOrg(ui);
		CostCenterOrgUnitInfo currentCostOrg = SysContext.getSysContext()
				.getCurrentCostUnit();

		if (!currentCostOrg.isIsBizUnit()) {
			MsgBox.showWarning(ui, "�ù���ֻ����ʵ��ɱ�����ʹ��");
			SysUtil.abort();
		}
	}

	// /**
	// * ��ʼ����Ӧ��F7����ֻ��ʾ�Ѻ�׼�ģ������ظ���¼��
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
	 * ��ʼ����Ӧ��F7����ֻ��ʾ�Ѻ�׼�ģ������ظ���¼��
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


		//��ֹ����������ͨ��F7��ѡ�������޷���������
		//getApproved4SupplierF7Filter(String cuId) ���ù�����ȷ����Ҳ������������
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

        // �ͻ��Ƿ������
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

	public static Set getProjIdsOfCurCostOrg() {
		Set ids = new HashSet();
		String curCostId = SysContext.getSysContext().getCurrentOrgUnit()
				.getId().toString();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("costCenterOU.id", curCostId));
		view.setFilter(filter);
		ProjectWithCostCenterOUCollection relations = null;
		try {
			relations = ProjectWithCostCenterOUFactory.getRemoteInstance()
					.getProjectWithCostCenterOUCollection(view);
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (relations == null || relations.size() == 0) {
			return null;
		}
		String projId = relations.get(0).getCurProject().getId().toString();
		ObjectUuidPK projUuidPK = new ObjectUuidPK(projId);
		try {
			if (!ProjectFactory.getRemoteInstance().exists(projUuidPK)) {
				return null;
			}
		} catch (Exception e) {
			ExceptionHandler.handle(e);
		}
		ProjectInfo ProjectInfo = null;
		try {
			ProjectInfo = ProjectFactory.getRemoteInstance()
					.getProjectInfo(projUuidPK);
		} catch (Exception e) {
			ExceptionHandler.handle(e);
		}

		String projNumber = ProjectInfo.getLongNumber();
		// Filter
		FilterInfo projFilter = new FilterInfo();
		projFilter.getFilterItems().add(
				new FilterItemInfo("longnumber", projNumber + "!%",
						CompareType.LIKE));
		projFilter.getFilterItems().add(
				new FilterItemInfo("longnumber", projNumber));

		String orgUnitId = SysContext.getSysContext().getCurrentFIUnit()
				.getId().toString();

		projFilter.getFilterItems().add(
				new FilterItemInfo("fullOrgUnit", orgUnitId));

		projFilter.setMaskString("(#0 or #1) and #2");

		view = new EntityViewInfo();
		view.setFilter(projFilter);
		view.getSelector().add("id");

		ProjectCollection projs = new ProjectCollection();
		try {
			projs = ProjectFactory.getRemoteInstance()
					.getProjectCollection(view);
		} catch (Exception e) {
			ExceptionHandler.handle(e);
		}

		for (Iterator iter = projs.iterator(); iter.hasNext();) {
			ProjectInfo element = (ProjectInfo) iter.next();
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
		filter.getFilterItems().add(
				new FilterItemInfo("costCenterOU.id", curCostId));
		view.setFilter(filter);
		ProjectWithCostCenterOUCollection relations = null;
		try {
			relations = ProjectWithCostCenterOUFactory.getRemoteInstance()
					.getProjectWithCostCenterOUCollection(view);
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (relations == null || relations.size() == 0) {
			return null;
		}
		String projId = relations.get(0).getCurProject().getId().toString();
		ObjectUuidPK projUuidPK = new ObjectUuidPK(projId);
		try {
			if (!ProjectFactory.getRemoteInstance().exists(projUuidPK)) {
				return null;
			}
		} catch (Exception e) {
			ExceptionHandler.handle(e);
		}
		ProjectInfo ProjectInfo = null;
		try {
			ProjectInfo = ProjectFactory.getRemoteInstance()
					.getProjectInfo(projUuidPK);
		} catch (Exception e) {
			ExceptionHandler.handle(e);
		}

		String projNumber = ProjectInfo.getLongNumber();
		// Filter
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
			//�ж�fullOrgUnitInfo���Ƿ����partFI��ֵ ����NullPointer
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

		ProjectCollection projs = new ProjectCollection();
		try {
			projs = ProjectFactory.getRemoteInstance()
					.getProjectCollection(view);
		} catch (Exception e) {
			ExceptionHandler.handle(e);
		}

		for (Iterator iter = projs.iterator(); iter.hasNext();) {
			ProjectInfo element = (ProjectInfo) iter.next();
			if (element == null)
				continue;
			String id = element.getId().toString();
			ids.add(id);
		}

		return ids;
	}

	/**
	 * ������Ա
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

        //ͨ��F7�ǿ������������,ֻҪbizPromptBox.setEntityViewInfo(view)��ȷ JinXP  20071217
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
            //��ֹ����������ͨ��F7��ѡ�������޷���������
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
	 * �ж��Ƿ����Ѿ��������׳����쳣
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
	 * ����������
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
	 * �ͷ�������
	 * @param ui
	 * @param idList
	 * @throws Throwable
	 */
	public static void releaseDataObjectLock(CoreUI ui,List idList) throws Throwable{
		String oprtState="RELEASEALL";
		requestDataObjectLock(ui,idList,oprtState);
	}
	
	/**
	 *	��ȡ�����SQL����е�ID�����ϵ����ţ�
	 *  �޸�null�жϣ���������nullpointer�쳣 
	 * @param idSet
	 * @update renliang
	 * @author liang_ren969
	 * 
	 */
	public static Set getSQLIdSet(Set idSet){
		Set returnSet = new HashSet();
		//if(idSet.size()<1 || idSet==null) return returnSet;
		/**
		 * ���ϴ���ܺܿ����׳�NullPointerException
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
