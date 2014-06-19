/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.KeyStroke;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDTreeView;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.assistant.ProjectInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.config.TablePreferencesHelper;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.eas.xr.helper.common.PortProjectTreeBuilder;

/**
 * 
 * ����:��߹�����Ŀ�����ұߵ��ݵ�ListUI����
 * 
 * @author liupd date:2006-9-18
 *         <p>
 * @version EAS5.1.3
 */
public abstract class ProjectTreeListBaseUI extends
		AbstractProjectTreeListBaseUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ProjectTreeListBaseUI.class);
	protected Set authorizedOrgs = null;
	/**
	 * output class constructor
	 */
	public ProjectTreeListBaseUI() throws Exception {
		super();
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeAddNew(e);
//		FDCClientUtils.checkSelectProj(this, getProjSelectedTreeNode());
		super.actionAddNew_actionPerformed(e);
	}

	protected void checkBeforeAddNew(ActionEvent e) {
		if(!e.getActionCommand().equals("FDCCostSplit")){
			//��ֵ��ݲ���������ʱ����ϸ���
			FDCClientUtils.checkSelectProj(this, getProjSelectedTreeNode());
		}
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeEdit(e);
		super.actionEdit_actionPerformed(e);
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeRemove();
		super.actionRemove_actionPerformed(e);
	}

    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQuery_actionPerformed(e);
    }
	/**
	 * ɾ��������ҵ������ء�
	 * 
	 * @throws Exception
	 * @throws EASBizException
	 */
	protected void Remove() throws Exception {
		removeBill();
	}

	/**
	 * ����ɾ��
	 * 
	 * @throws Exception
	 * @throws EASBizException
	 */
	protected void removeBill() throws Exception {
		int[] selectRows = KDTableUtil.getSelectedRows(getMainTable());
		IObjectPK[] arrayPK = new ObjectUuidPK[selectRows.length];

		for (int i = 0; i < selectRows.length; i++) {
			String id = (String) getMainTable().getCell(selectRows[i],
					getKeyFieldName()).getValue();
			checkRef(id);
			arrayPK[i] = new ObjectUuidPK(id);
			
            try
            {
                this.setOprtState("REMOVE");
                this.pubFireVOChangeListener(arrayPK[i].toString());
            }
            catch (Throwable ex)
            {
            	this.handUIException(ex);
                SysUtil.abort();
            }
		}
		getRemoteInterface().delete(arrayPK);

		showOprtOKMsgAndRefresh();
	}

	/**
	 * output getEditUIName method
	 */
	protected String getEditUIName() {
		return null;
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception {
		return getRemoteInterface();
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		return null;
	}

	/**
	 * 
	 * ������Ϊ��ǰ���ݵ��������༭���鿴׼��Context
	 * 
	 * @author:liupd
	 * @see com.kingdee.eas.framework.client.CoreBillListUI#prepareUIContext(com.kingdee.eas.common.client.UIContext,
	 *      java.awt.event.ActionEvent)
	 */
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
//		ItemAction act = getActionFromActionEvent(e);
//
//		//Ҫ��������Ŀ��Ҷ�ӽڵ��Id���ϣ����ڱ༭���湤����Ŀ����F7ѡ�����
//		if (act.equals(actionAddNew)) {
//			/*
//			 * �ѵ�ǰѡ�еĹ�����Ŀ����EditUI
//			 */
			
			Object userObject2 = getProjSelectedTreeNode().getUserObject();
			if(userObject2 instanceof ProjectInfo){
				BOSUuid projId = ((ProjectInfo) userObject2).getId();
				uiContext.put("projectId", projId);
			}
//		}
		
		Set leafNodesIdSet = new HashSet();
		TreeNode projRoot = (TreeNode) treeProject.getModel().getRoot();
		FDCClientUtils.genProjLeafNodesIdSet(projRoot, leafNodesIdSet);
		uiContext.put("projLeafNodesIdSet", leafNodesIdSet);
	}
	
	/**
	 * 
	 * ��������ʼ�����
	 * @author:liupd
	 * ����ʱ�䣺2006-8-3 <p>
	 */
	protected void initTable() {		
		super.initTable();
		
	}
	
	

	public void onLoad() throws Exception {
		
		super.onLoad();
//		KDTreeView view=new KDTreeView(treeProject);
//		kDScrollPane1.getViewport().add(view, null);
		KDTreeView treeView=new KDTreeView();
		treeView.setTree(treeProject);
		treeView.setShowButton(false);
		treeView.setTitle("������Ŀ");
//        kDSplitPane1.add(tblMain, "right");
        kDSplitPane1.add(treeView, "left");
		treeView.setShowControlPanel(true);
		buildProjectTree();

		//��ʼ�����
		initTable();
		
		freezeTableColumn();

		updateButtonStatus();

		// ȥ����ѯ����
		actionQuery.setEnabled(false);
		actionQuery.setVisible(false);
		// ȥ���༶����
//		actionMultiapprove.setEnabled(false);
//		actionMultiapprove.setVisible(false);
//		// ȥ����һ��������
//		actionNextPerson.setEnabled(false);
//		actionNextPerson.setVisible(false);
//
		actionAuditResult.setEnabled(true);
		actionAuditResult.setVisible(true);
		menuItemAuditResult.setVisible(true);

		/*
		 * ����ѡ�и����
		 */
//		treeProject.setSelectionRow(0);
//		treeProject.expandRow(0);
		
		//60��
		tHelper = new TablePreferencesHelper(this);

		//53���ô���
//		tHelper = new TableCoreuiPreferenceHelper(this);
	}

	
	/**
	 * private�����޸�Ϊpublic��������������޸Ĺ�����Ŀ���Ĺ�����ʽ
	 * ԭ�򣺽��ȹ����й�����Ŀ�Ĺ���ȡ��ǰ��֯�ϼ�������֯�µ����й�����Ŀ��Ĭ��ȡ�ĵ�ǰ��֯�µĹ�����Ŀ
	 * @throws Exception
	 */
	public void buildProjectTree() throws Exception {

		PortProjectTreeBuilder projectTreeBuilder = new PortProjectTreeBuilder();

		projectTreeBuilder.build(this, treeProject, actionOnLoad);

		treeProject.setShowsRootHandles(true);
	}

	protected void updateButtonStatus() {

		super.updateButtonStatus();

		// ���������ɱ����ģ���������ɾ����
		if (!SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit()) {
			actionAddNew.setEnabled(false);
			actionEdit.setEnabled(false);
			actionRemove.setEnabled(false);
			actionAddNew.setVisible(false);
			actionEdit.setVisible(false);
			actionRemove.setVisible(false);
			menuEdit.setVisible(false);
		}
	}

	// ���ݶ���仯ʱ��ˢ�½���״̬
	protected void setActionState() {
		super.setActionState();
		updateButtonStatus();
	}

	/**
	 * ���ݱ༭����Ĭ�����¿����ڷ�ʽ��
	 * 
	 * @return
	 */
	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}

	/**
	 * 
	 * ��������ʾ�����ɹ�
	 * 
	 * @author:liupd ����ʱ�䣺2006-8-1
	 *               <p>
	 */
	protected void showOprtOKMsgAndRefresh() throws Exception {
		FDCClientUtils.showOprtOK(this);
		refreshList();
	}

	/**
	 * 
	 * ��������״̬���򣨰��ݴ桢���ύ�������У���������˳������
	 * ���Ƶ�execQuery�У�add by sxhong
	 * @author:liupd
	 * @see com.kingdee.eas.framework.client.ListUI#beforeExcutQuery(com.kingdee.bos.metadata.entity.EntityViewInfo)
	 */
	protected void beforeExcutQuery(EntityViewInfo ev) {
		super.beforeExcutQuery(ev);
	}
	/**
	 * ����getSorter����������������״̬
	 * @author sxhong  		Date 2006-11-14
	 * @see com.kingdee.eas.framework.client.ListUI#execQuery()
	 */
	protected void execQuery()
	{
		//��������״̬
		EntityViewInfo ev=getMainQuery();
					
		//��ѯʱ��ʹ��Ĭ������
		if(ev.getSorter().size()==0||
				(ev.getSorter().size()==1&&ev.getSorter().contains(new SorterItemInfo("id")))){
			SorterItemCollection sorters=getSorter();
			if(sorters!=null){
				int i=0;
				for(Iterator iter=sorters.iterator();iter.hasNext();){
					SorterItemInfo sorter = (SorterItemInfo)iter.next();
					if(!ev.getSorter().contains(sorter)){
						ev.getSorter().addObject(i++, sorter); //��֤�����������
					}
				}
			}

		}
		
		super.execQuery();
	}
	
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo)
    {
    	viewInfo = (EntityViewInfo) mainQuery.clone();
		//�ϲ���ѯ����
		try {
			FilterInfo filter = getTreeFilter();
			if(viewInfo.getFilter()!=null){
				viewInfo.getFilter().mergeFilter(filter,"and");
			}else{
				viewInfo.setFilter(filter);
			}
			filterByBillState(viewInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}

    	return super.getQueryExecutor(queryPK,viewInfo);
    }
    
    protected boolean isIgnoreCUFilter()
    {
        return true;
    }
    
	protected void treeProject_valueChanged(TreeSelectionEvent e)
			throws Exception {

		super.treeProject_valueChanged(e);
		treeSelectChange();
	}

	/**
	 * 
	 * �����������ѡ��ı䣬���¹�������ִ�в�ѯ
	 * 
	 * @author:liupd ����ʱ�䣺2006-7-25
	 *               <p>
	 */
	protected void treeSelectChange() throws Exception {

		execQuery();

		if (getMainTable().getRowCount() > 0) {
			getMainTable().getSelectManager().select(0, 0);
		}
	}
	
	//����������
	protected FilterInfo getTreeFilter() throws Exception{
		
		FilterInfo filter = getTreeSelectChangeFilter();
		FilterItemCollection filterItems = filter.getFilterItems();
		//��õ�ǰ�û��µ���֯��Χids
		authorizedOrgs = (Set)ActionCache.get("ContractListBaseUIHandler.authorizedOrgs");
		if(authorizedOrgs==null){
			authorizedOrgs = new HashSet();
			Map orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(
					 new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId()),
			            OrgType.CostCenter, 
			            null,  null, null);
			if(orgs!=null){
				Set orgSet = orgs.keySet();
				Iterator it = orgSet.iterator();
				while(it.hasNext()){
					authorizedOrgs.add(it.next());
				}
			}
		}
		/*
		 * ������Ŀ��
		 */
		if (getProjSelectedTreeNode() != null
				&& getProjSelectedTreeNode().getUserObject() instanceof CoreBaseInfo) {

			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) getProjSelectedTreeNode()
					.getUserObject();
//			 ѡ����ǳɱ����ģ�ȡ�óɱ����ļ��¼��ɱ����ģ�����У��µ����к�ͬ
			if (projTreeNodeInfo instanceof OrgStructureInfo) {
				BOSUuid id = ((OrgStructureInfo)projTreeNodeInfo).getUnit().getId();
				
				String orgUnitLongNumber = null;
				if(orgUnit!=null && id.toString().equals(orgUnit.getId().toString())){					
					orgUnitLongNumber = orgUnit.getLongNumber();
				}else{
					FullOrgUnitInfo orgUnitInfo = FullOrgUnitFactory.getRemoteInstance()
					.getFullOrgUnitInfo(new ObjectUuidPK(id));
					orgUnitLongNumber = orgUnitInfo.getLongNumber();
				}
				
				filterItems.add(
						new FilterItemInfo("orgUnit.longNumber", orgUnitLongNumber + "%",CompareType.LIKE));

				filterItems.add(new FilterItemInfo("orgUnit.isCostOrgUnit", Boolean.TRUE));
				
//				Set idSet = FDCClientUtils.genOrgUnitIdSet(id);
//				filterItems.add(new FilterItemInfo("orgUnit.id", idSet,
//						CompareType.INCLUDE));
//			
//				filterItems.add(new FilterItemInfo("curProject.fullOrgUnit.id", idSet,
//						CompareType.INCLUDE));
				filterItems.add(new FilterItemInfo("orgUnit.id", authorizedOrgs,
						CompareType.INCLUDE));
			}
//			if (projTreeNodeInfo instanceof OrgStructureInfo) {
//				filterItems.add(new FilterItemInfo("orgUnit.id", authorizedOrgs,
//						CompareType.INCLUDE));
//				filterItems.add(new FilterItemInfo("curProject.fullOrgUnit.id", authorizedOrgs,
//						CompareType.INCLUDE));
//			}
			// ѡ�������Ŀ��ȡ����Ŀ���¼���Ŀ������У��µ����к�ͬ
			else if (projTreeNodeInfo instanceof CurProjectInfo) {
				BOSUuid id = projTreeNodeInfo.getId();
				Set idSet = FDCClientUtils.genProjectIdSet(id);
				filterItems.add(new FilterItemInfo("curProject.id", idSet,
						CompareType.INCLUDE));
			}

		}
		
		return filter;
	}

	public DefaultKingdeeTreeNode getProjSelectedTreeNode() {
		return (DefaultKingdeeTreeNode) treeProject
				.getLastSelectedPathComponent();
	}

	/**
	 * 
	 * �����������ͬ����
	 * 
	 * @author:liupd ����ʱ�䣺2006-7-25
	 *               <p>
	 */
	protected void freezeTableColumn() {

	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		checkBillState(getStateForAudit(), "selectRightRowForAudit");
		audit(ContractClientUtils.getSelectedIdValues(getMainTable(),
				getKeyFieldName()));
		showOprtOKMsgAndRefresh();
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		checkBillState(getStateForUnAudit(), "selectRightRowForUnAudit");
		List selectedIdValues = ContractClientUtils.getSelectedIdValues(
				getMainTable(), getKeyFieldName());
		// �������
		for (Iterator iter = selectedIdValues.iterator(); iter.hasNext();) {
			String id = (String) iter.next();
			checkRef(id);
		}

		unAudit(selectedIdValues);
		showOprtOKMsgAndRefresh();
	}

	/**
	 * 
	 * ��������鵥��״̬
	 * 
	 * @param state
	 *            ״̬
	 * @param res
	 *            ��ʾ��Ϣ��Դ����
	 * @throws BOSException
	 * @author:liupd ����ʱ�䣺2006-7-27
	 *               <p>
	 */
	protected void checkBillState(String state, String res) throws Exception {
		List idList = ContractClientUtils.getSelectedIdValues(getMainTable(),
				getKeyFieldName());

		Set idSet = ContractClientUtils.listToSet(idList);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("state");
		CoreBaseCollection coll = getRemoteInterface().getCollection(view);

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			CoreBaseInfo element = (CoreBaseInfo) iter.next();

			// ��鵥���Ƿ��ڹ�������
			FDCClientUtils
					.checkBillInWorkflow(this, element.getId().toString());

			if (!element.getString(getBillStatePropertyName()).equals(state)) {
				MsgBox.showWarning(this, ContractClientUtils.getRes(res));
				abort();
			}

		}
	}

	/**
	 * 
	 * ��������˲����ĵ���ǰ��״̬
	 * 
	 * @return
	 * @author:liupd ����ʱ�䣺2006-8-1
	 *               <p>
	 */
	protected String getStateForAudit() {
		return FDCBillStateEnum.SUBMITTED_VALUE;
	}

	/**
	 * 
	 * ����������˲����ĵ���ǰ��״̬
	 * 
	 * @return
	 * @author:liupd ����ʱ�䣺2006-8-1
	 *               <p>
	 */
	protected String getStateForUnAudit() {
		return FDCBillStateEnum.AUDITTED_VALUE;
	}

	/**
	 * 
	 * ����������״̬�������ƣ������ṩȱʡʵ��
	 * 
	 * @return
	 * @author:liupd ����ʱ�䣺2006-8-26
	 *               <p>
	 */
	protected String getBillStatePropertyName() {
		return "state";
	}

	/**
	 * 
	 * ����������Զ�̽ӿڣ��������ʵ�֣�
	 * 
	 * @return
	 * @throws BOSException
	 * @author:liupd ����ʱ�䣺2006-8-1
	 *               <p>
	 */
	protected abstract ICoreBase getRemoteInterface() throws BOSException;

	/**
	 * 
	 * ���������ͨ�����������ʵ�֣����÷������˴���˱�־�ķ������ɣ�
	 * 
	 * @param ids
	 * @throws Exception
	 * @author:liupd ����ʱ�䣺2006-8-1
	 *               <p>
	 */
	protected abstract void audit(List ids) throws Exception;

	/**
	 * 
	 * ����������ˣ��������ʵ�֣����÷������˴���˱�־�ķ������ɣ�
	 * 
	 * @param ids
	 * @throws Exception
	 * @author:liupd ����ʱ�䣺2006-8-1
	 *               <p>
	 */
	protected abstract void unAudit(List ids) throws Exception;

	/**
	 * 
	 * �������޸�ǰ���
	 * 
	 * @author:liupd ����ʱ�䣺2006-8-26
	 *               <p>
	 * @throws Exception
	 */
	protected void checkBeforeEdit(ActionEvent e) throws Exception {
		checkSelected();

		CoreBaseInfo billInfo = getRemoteInterface().getValue(
				new ObjectUuidPK(getSelectedKeyValue()));
		String billState = billInfo.getString(getBillStatePropertyName());
		String[] states = getBillStateForEditOrRemove();
		boolean pass = false;
		for (int i = 0; i < states.length; i++) {
			if (billState.equals(states[i])) {
				pass = true;
			}
		}
		if (!pass) {
			MsgBox.showWarning(this, ContractClientUtils.getRes("cantEdit"));
			SysUtil.abort();
		}
	}

	/**
	 * 
	 * ���������ݿ��޸ġ�ɾ����״̬
	 * 
	 * @return
	 * @author:liupd ����ʱ�䣺2006-8-26
	 *               <p>
	 */
	protected String[] getBillStateForEditOrRemove() {
		return new String[] { FDCBillStateEnum.SAVED_VALUE,
				FDCBillStateEnum.SUBMITTED_VALUE };
	}

	/**
	 * 
	 * ��������������ɾ��ǰ���
	 * 
	 * @author:liupd ����ʱ�䣺2006-8-26
	 *               <p>
	 * @throws Exception
	 */
	protected void checkBeforeRemove() throws Exception {
		checkSelected();

		List idList = ContractClientUtils.getSelectedIdValues(getMainTable(),
				getKeyFieldName());

		Set idSet = ContractClientUtils.listToSet(idList);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add(getBillStatePropertyName());
		CoreBaseCollection coll = getRemoteInterface().getCollection(view);

		String[] states = getBillStateForEditOrRemove();

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			CoreBillBaseInfo element = (CoreBillBaseInfo) iter.next();
			String billState = element.getString(getBillStatePropertyName());
			boolean pass = false;
			for (int i = 0; i < states.length; i++) {
				if (billState.equals(states[i])) {
					pass = true;
				}
			}
			if (!pass) {
				MsgBox.showWarning(this, ContractClientUtils
						.getRes("cantRemove"));
				SysUtil.abort();
			}
		}
	}

	/**
	 * 
	 * ����������ߵ���ѡ��仯ʱ��ȱʡ�������ṩĬ��ʵ�֣���ͬ״̬Ϊ��ˣ�������Ը��ǣ����û��������ҲҪ����һ��new
	 * FilterInfo()������ֱ�ӷ���null��
	 * 
	 * @return
	 * @author:liupd ����ʱ�䣺2006-9-5
	 *               <p>
	 */
	protected FilterInfo getTreeSelectChangeFilter() {
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
//		filterItems.add(new FilterItemInfo("curProject.isEnabled", Boolean.TRUE));
		return filter;
	}

	protected void initWorkButton() {
		super.initWorkButton();
		btnAudit.setIcon(FDCClientHelper.ICON_AUDIT);
		btnUnAudit.setIcon(FDCClientHelper.ICON_UNAUDIT);
		menuItemAudit.setIcon(FDCClientHelper.ICON_AUDIT);
		menuItemUnAudit.setIcon(FDCClientHelper.ICON_UNAUDIT);
		menuItemAudit.setAccelerator(KeyStroke.getKeyStroke("ctrl U"));
		actionAudit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl U"));
		menuItemAudit.setText(menuItemAudit.getText().replaceAll("\\(A\\)", "")+"(A)");
		menuItemAudit.setMnemonic('A');
		
		actionAudit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift U"));
		menuItemUnAudit.setText(menuItemUnAudit.getText().replaceAll("\\(U\\)", "")+"(U)");
		menuItemUnAudit.setAccelerator(KeyStroke.getKeyStroke("ctrl shift U"));
		menuItemUnAudit.setMnemonic('U');
	}

	/**
	 * 
	 * ����������Ƿ��й�������(ɾ��ǰ����)
	 * 
	 * @author:liupd ����ʱ�䣺2006-8-26
	 *               <p>
	 */
	protected void checkRef(String id) {

	}
	
	/**
	 * ������������ɸ���,���ں�ͬ��ֵ���Ҫ ��execQuery�е���
	 * @author sxhong  		Date 2006-11-10
	 * @return
	 */
	protected SorterItemCollection getSorter(){
		SorterItemCollection sorter=new SorterItemCollection();
		sorter.add(new SorterItemInfo("state"));
		return sorter;
	}
	
	protected void filterByBillState(EntityViewInfo ev) {
		
	}
	/**
	 * �õ���ǰѡ��Ķ��󹤳���Ŀ,��֯ID,��Null
	 * @return ��ǰѡ��Ķ��󹤳���Ŀ,��֯ID,��Null
	 */
	protected Object getSelectObj() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeProject.getLastSelectedPathComponent();
		if (node == null || node.getUserObject() == null || OrgViewUtils.isTreeNodeDisable(node)) {
			return null;
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			return projectInfo;
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
			if (oui.getUnit() == null) {
				return null;
			}
			FullOrgUnitInfo info = oui.getUnit();
			return info;
		}
		return null;
	}
	
	protected String getSelectObjId() {
		Object obj=getSelectObj();
		if(obj!=null){
			return ((IObjectValue)obj).getString("id");
		}
		return null;
	}
	/**
	 * @author yong_zhou
	 * @return
	 */
	protected Set getSelectObjLeafIds() {
		Set idSet = new HashSet();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) this.treeProject.getLastSelectedPathComponent();
		if (node == null || node.getUserObject() == null || OrgViewUtils.isTreeNodeDisable(node)) {
			return idSet;
		}
		getNodeIds(node,idSet);
		return idSet;
	}
	protected void getNodeIds(DefaultKingdeeTreeNode node,Set idSet){
		if (node.isLeaf()/* && node.getUserObject() instanceof CurProjectInfo*/){
//			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
//			idSet.add(projectInfo.getId().toString());
			if(node.getUserObject() instanceof CurProjectInfo){
				CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
				idSet.add(projectInfo.getId().toString());
			}
			else if(node.getUserObject() instanceof FullOrgUnitInfo){
				FullOrgUnitInfo orgUnitInfo=(FullOrgUnitInfo)node.getUserObject();
				idSet.add(orgUnitInfo.getId().toString());
			}
			else if(node.getUserObject() instanceof OrgStructureInfo){
				OrgStructureInfo orgUnitInfo=(OrgStructureInfo)node.getUserObject();
				idSet.add(orgUnitInfo.getId().toString());
			}
		}else if(!node.isLeaf()){
			for(int i=0;i<node.getChildCount();i++){
				DefaultKingdeeTreeNode child = (DefaultKingdeeTreeNode)node.getChildAt(i);
				getNodeIds(child,idSet);
			}
		}
	}
	

	protected CurProjectInfo getSelectProject() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeProject.getLastSelectedPathComponent();
		if (node == null || node.getUserObject() == null || OrgViewUtils.isTreeNodeDisable(node)) {
			return null;
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			return projectInfo;
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			return null;
		}
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	protected boolean isSelectLeafPrj(){
		Object obj=getSelectObj();
		if(obj!=null && obj instanceof CurProjectInfo &&((CurProjectInfo)obj).isIsLeaf()){
			return true;
		}
		return false;
	}
}