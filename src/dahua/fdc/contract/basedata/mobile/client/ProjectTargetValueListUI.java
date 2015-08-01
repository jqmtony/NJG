/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.mobile.client;

import java.awt.event.ActionEvent;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.client.FdcOrgTreeHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.basedata.mobile.ProjectTargetValueBillFactory;
import com.kingdee.eas.fdc.basedata.util.FdcCollectionUtil;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ProjectTargetValueListUI extends AbstractProjectTargetValueListUI {

	private static final Logger logger = CoreUIObject.getLogger(ProjectTargetValueListUI.class);

	protected static final String COL_ID = "id";
	protected static final String COL_ORG_UNIT_ID = "orgUnit.id";
	protected static final String COL_ORG_UNIT_NAME = "orgUnit.name";
	protected static final String COL_CUR_PROJECT_ID = "curProject.id";
	protected static final String COL_CUR_PROJECT_NAME = "curProject.name";
	protected static final String COL_YEAR = "year";
	protected static final String COL_MONTH = "month";
	// protected static final String COL_SEQ_NUM = "seqNum";
	protected static final String COL_NUMBER = "number";
	protected static final String COL_NAME = "name";
	// protected static final String COL_IS_SEQ_EXE = "isSeqExe";
	protected static final String COL_STATE = "state";
	protected static final String COL_DESCRIPTION = "description";
	protected static final String COL_CREATOR_NAME = "creator.name";
	protected static final String COL_CREATE_TIME = "createTime";
	protected static final String COL_LAST_UPDATE_USER_NAME = "lastUpdateUser.name";
	protected static final String COL_LAST_UPDATE_TIME = "lastUpdateTime";
	protected static final String COL_AUDITOR_NAME = "auditor.name";
	protected static final String COL_AUDIT_TIME = "auditTime";
	
	// ��ȡ��Ȩ�޵���֯
	protected Set authorizedOrgs = null;

	/**
	 * output class constructor
	 */
	public ProjectTargetValueListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * ������
	 * 
	 * @return
	 * @throws Exception
	 * @author rd_skyiter_wang
	 * @createDate 2014-10-29
	 * @see com.kingdee.eas.fdc.basedata.mobile.client.FdcMobileListUI#getBizInterface()
	 */
	@Override
	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return ProjectTargetValueBillFactory.getRemoteInstance();
	}

	/**
	 * ������
	 * 
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-10-29
	 * @see com.kingdee.eas.fdc.basedata.mobile.client.FdcMobileListUI#getEditUIName()
	 */
	@Override
	protected String getEditUIName() {
		// TODO Auto-generated method stub
		return ProjectTargetValueEditUI.class.getName();
	}

	/**
	 * ������
	 * 
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-10-31
	 * @see com.kingdee.eas.fdc.basedata.mobile.client.FdcMobileListUI#getEditUIModal()
	 */
	@Override
	protected String getEditUIModal() {
		// TODO Auto-generated method stub
		return super.getEditUIModal();
	}

	/**
	 * ������
	 * 
	 * @throws Exception
	 * @author rd_skyiter_wang
	 * @createDate 2014-10-29
	 * @see com.kingdee.eas.fdc.basedata.mobile.client.FdcMobileListUI#onLoad()
	 */
	@Override
	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
		
		// ������Ŀ���Ĺ���
		buildProjectTree();
	}

	@Override
	protected void initTable() {
		// TODO Auto-generated method stub
		super.initTable();

		// //////////////////////////////////////////////////////////////////

		String[] tblMainTableFormateNumberCols = new String[] { "year", "month" };
		// ��ʾ����
		FDCTableHelper.formatTableNumber2(getMainTable(), tblMainTableFormateNumberCols, 0);
	}

	/**
	 * ������
	 * 
	 * @author rd_skyiter_wang
	 * @createDate 2014-10-29
	 * @see com.kingdee.eas.fdc.basedata.mobile.client.FdcMobileListUI#initWorkButton()
	 */
	@Override
	protected void initWorkButton() {
		// TODO Auto-generated method stub
		super.initWorkButton();

		// //////////////////////////////////////////////////////////////////

		// BT874909: ��Ŀָ��ֵ��Ҫ����˹��� by skyiter_wang 2015-04-24
		FDCClientHelper.setActionEnV(actionAudit, true);
		FDCClientHelper.setActionEnV(actionUnAudit, true);
		FDCClientHelper.setActionEnV(actionAuditResult, true);
		FDCClientHelper.setActionEnV(actionMultiapprove, true);
		FDCClientHelper.setActionEnV(actionWorkFlowG, true);
		// FDCClientHelper.setActionEnV(actionWorkflowList, true);
		FDCClientHelper.setActionEnV(actionNextPerson, true);

		// //////////////////////////////////////////////////////////////////
	}

	/**
	 * ������
	 * 
	 * @throws Exception
	 * @author rd_skyiter_wang
	 * @createDate 2014-10-31
	 * @see com.kingdee.eas.fdc.basedata.mobile.client.FdcMobileListUI#afterOnLoad()
	 */
	protected void afterOnLoad() throws Exception {
		super.afterOnLoad();
	}

	/**
	 * �������
	 */
	public void resetComponent() throws Exception {

		// Ĭ�ϡ������༭��ģʽ
		isEditting = false;
		isSuperEditting = true;

		// /////////////////////////////////////////////////////////////////////

		super.resetComponent();

		// /////////////////////////////////////////////////////////////////////

		KDTable tbl = getMainTable();

		// tbl.getColumn(COL_SEQ_NUM).getStyleAttributes().setHided((!(isShowMore || isEditting ||
		// isSuperEditting)));
		// tbl.getColumn(COL_CREATOR_NAME).getStyleAttributes().setHided(!isShowMore);
		// tbl.getColumn(COL_CREATE_TIME).getStyleAttributes().setHided(!isShowMore);
		// tbl.getColumn(COL_LAST_UPDATE_USER_NAME).getStyleAttributes().setHided(!isShowMore);
		// tbl.getColumn(COL_LAST_UPDATE_TIME).getStyleAttributes().setHided(!isShowMore);

		tbl.getColumn(COL_STATE).getStyleAttributes().setHided(!isSuperEditting);
		tbl.getColumn(COL_CREATOR_NAME).getStyleAttributes().setHided(!isSuperEditting);
		tbl.getColumn(COL_CREATOR_NAME).getStyleAttributes().setHided(false);
		tbl.getColumn(COL_CREATE_TIME).getStyleAttributes().setHided(false);
		tbl.getColumn(COL_LAST_UPDATE_USER_NAME).getStyleAttributes().setHided(false);
		tbl.getColumn(COL_LAST_UPDATE_TIME).getStyleAttributes().setHided(false);
		tbl.getColumn(COL_AUDITOR_NAME).getStyleAttributes().setHided(!isSuperEditting);
		tbl.getColumn(COL_AUDIT_TIME).getStyleAttributes().setHided(!isSuperEditting);

		// /////////////////////////////////////////////////////////////////////

		if (false) {
			// ֻ�г����༭״̬�£����ܵ���
			FDCClientHelper.setActionEnV(actionExportBillSql, isSuperEditting);
			FDCClientHelper.setActionEnV(actionExportEntrySql, isSuperEditting);
			// FDCClientHelper.setActionEnV(actionExportItemSql, isSuperEditting);
			FDCClientHelper.setActionEnV(actionExportSql, isSuperEditting);
		}

		// /////////////////////////////////////////////////////////////////////
	}
	
	// //////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////

	@Override
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		// TODO Auto-generated method stub
		super.prepareUIContext(uiContext, e);

		// ItemAction act = getActionFromActionEvent(e);
		// if (act.equals(actionAddNew)) {
		// // ����
		// } else if (act.equals(actionEdit) || act.equals(actionView)) {
		// uiContext.put(UIContext.ID, getSelectedKeyValue());
		// }

		Object userObject = FdcOrgTreeHelper.getSelectedNodeObj(treeProject);
		if (userObject instanceof CurProjectInfo) {
			BOSUuid projectId = ((CurProjectInfo) userObject).getId();
			uiContext.put("projectId", projectId);
		} else {
			String projectIdStr = FDCClientHelper.getSelectedKeyValue(getMainTable(), COL_CUR_PROJECT_ID);
			if (null != projectIdStr) {
				BOSUuid projectId = BOSUuid.read(projectIdStr);
				uiContext.put("projectId", projectId);
			}
		}
	}

	// //////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////

	/**
	 * ��Ŀ���ı��¼�
	 * 
	 * @see com.kingdee.eas.fdc.basedata.mobile.client.AbstractProjectTargetValueListUI#treeProject_valueChanged(javax.swing.event.TreeSelectionEvent)
	 */
	protected void treeProject_valueChanged(TreeSelectionEvent e) throws Exception {
		super.treeProject_valueChanged(e);
		
		treeSelectChange();
	}

	/**
	 * �����������ѡ��ı䣬���¹�������ִ�в�ѯ
	 * 
	 * @throws Exception
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-3
	 */
	protected void treeSelectChange() throws Exception {
		Object nodeObject = FdcOrgTreeHelper.getSelectedNodeObj(treeProject);
		FilterInfo filter = getTreeSelectFilter(nodeObject);
		mainQuery.setFilter(filter);
		
		long startTime = System.currentTimeMillis();
		execQuery();
		long endTime = System.currentTimeMillis();
		logger.info("ִ��exeQuery()����ʱ��Ϊ��" + (endTime - startTime));
	}

	/**
	 * ������ѡ�����ڵ���ѡ���¼�
	 * 
	 * @param projectNode
	 * @return
	 * @throws Exception
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-3
	 */
	protected FilterInfo getTreeSelectFilter(Object projectNode) throws Exception {
		FilterInfo filter = getTreeSelectChangeDefaultFilter();

		if (projectNode instanceof CoreBaseInfo) {
			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) projectNode;

			BOSUuid id = null;
			// ѡ�������֯��Ԫ
			if (projTreeNodeInfo instanceof OrgStructureInfo || projTreeNodeInfo instanceof FullOrgUnitInfo) {
				if (projTreeNodeInfo instanceof OrgStructureInfo) {
					id = ((OrgStructureInfo) projTreeNodeInfo).getUnit().getId();
				} else {
					id = ((FullOrgUnitInfo) projTreeNodeInfo).getId();
				}

				FullOrgUnitInfo orgUnitInfo = null;
				String orgUnitLongNumber = null;
				if (orgUnit != null && id.toString().equals(orgUnit.getId().toString())) {
					orgUnitInfo = orgUnit;
				} else {
					orgUnitInfo = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(id));
				}
				orgUnitLongNumber = orgUnitInfo.getLongNumber();

				// ȡ�ý�ɫ��֯��Χ
				UserInfo currentUserInfo = SysContext.getSysContext().getCurrentUserInfo();
				authorizedOrgs = FdcOrgTreeHelper.getAuthorizedOrgs(orgUnitInfo, currentUserInfo);

				FilterInfo f = new FilterInfo();
				FilterItemCollection filterItems = f.getFilterItems();
				if (FdcCollectionUtil.isNotEmpty(authorizedOrgs)) {
					filterItems.add(new FilterItemInfo("orgUnit.id", authorizedOrgs, CompareType.INCLUDE));
					filterItems
							.add(new FilterItemInfo("orgUnit.longNumber", orgUnitLongNumber + "%", CompareType.LIKE));

					f.setMaskString("#0 and #1");
				} else {
					filterItems
							.add(new FilterItemInfo("orgUnit.longNumber", orgUnitLongNumber + "%", CompareType.LIKE));
				}

				if (filter != null) {
					filter.mergeFilter(f, "and");
				}
			}
			// ѡ�������Ŀ
			else if (projTreeNodeInfo instanceof CurProjectInfo) {
				id = projTreeNodeInfo.getId();
				Set idSet = FDCClientUtils.genProjectIdSet(id);
				
				if (filter != null) {
					FilterItemCollection filterItems = filter.getFilterItems();
					filterItems.add(new FilterItemInfo("curProject.id", idSet, CompareType.INCLUDE));
				}
			}
		}

		return filter;
	}

	/**
	 * ����������ߵ���ѡ��仯ʱ��ȱʡ����
	 * 
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-3
	 */
	protected FilterInfo getTreeSelectChangeDefaultFilter() {
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		filterItems.add(new FilterItemInfo("curProject.isEnabled", Boolean.TRUE));
		
		return filter;
	}

	/**
	 * ������������Ŀ��
	 * 
	 * @throws Exception
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-3
	 */
	public void buildProjectTree() throws Exception {
		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();
		projectTreeBuilder.build(this, treeProject, actionOnLoad);
		
		// Ĭ�ϣ�ѡ�и����
		treeProject.setSelectionRow(0);
		// Ĭ�ϣ�չ����һ��
		treeProject.expandRow(0);
	}
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.kingdee.eas.fdc.basedata.mobile.client.FdcMobileListUI#actionAddNew_actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		Object nodeObj = FdcOrgTreeHelper.getSelectedNodeObj(treeProject);
		if (!(nodeObj instanceof CurProjectInfo)) {
			MsgBox.showWarning(this, "��ѡ�񹤳���Ŀ�ڵ�");
			SysUtil.abort();
		}

		super.actionAddNew_actionPerformed(e);
	}
	
	// //////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////

	/**
	 * ������ȡ�õ�������SQL��Ӧ�����ݿ������
	 * 
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-14
	 */
	protected String getExportBillTableName() {
		return "T_FDC_ProjectTargetVBill";
	}

	/**
	 * ������ȡ�õ�����¼SQL��Ӧ�����ݿ������
	 * 
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-14
	 */
	protected String getExportEntryTableName() {
		return "T_FDC_ProjectTargetVEntry";
	}

	/**
	 * ������ȡ�õ����嵥SQL��Ӧ�����ݿ������
	 * 
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-14
	 */
	protected String getExportItemTableName() {
		return null;
		// return "T_FDC_ProjectTargetVItem";
	}

	// //////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////
	
}