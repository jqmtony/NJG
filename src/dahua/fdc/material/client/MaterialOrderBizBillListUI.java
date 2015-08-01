/**
 * output package name
 */
package com.kingdee.eas.fdc.material.client;


import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDTreeView;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.material.MaterialOrderBizBillFactory;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * ���϶����� �б����
 */
public class MaterialOrderBizBillListUI extends AbstractMaterialOrderBizBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(MaterialOrderBizBillListUI.class);
  //��ȡ��Ȩ�޵���֯
	protected Set authorizedOrgs = null;
    /**
     * output class constructor
     */
    public MaterialOrderBizBillListUI() throws Exception
    {
        super();
    }
    
	protected String getEditUIName() {
		return com.kingdee.eas.fdc.material.client.MaterialOrderBizBillEditUI.class.getName();
	}

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
    	///FDCClientUtils.checkSelectProj(this, getProjSelectedTreeNode());
		//FDCClientUtils.checkProjWithCostOrg(this, getProjSelectedTreeNode());
        super.actionAddNew_actionPerformed(e);
    }
   
    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected(tblMain);
    	checkIsExistEditRows();
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected(tblMain);
    	checkIsExistEditRows();
        super.actionRemove_actionPerformed(e);
    }   
   

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.material.MaterialOrderBizBillFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.material.MaterialOrderBizBillInfo objectValue = new com.kingdee.eas.fdc.material.MaterialOrderBizBillInfo();
		
        return objectValue;
    }

	protected String getEditUIModal() {
		 return UIFactoryName.NEWTAB;

	}

    public void onLoad() throws Exception {
		super.onLoad();
		// setMergeColumn();
		setComponentState();
		//������Ŀ���Ĺ���
		buildProjectTree();
		treeProject.setShowsRootHandles(true);
		
		//����KDtreeView
		KDTreeView treeView = new KDTreeView();
		treeView.setTree(treeProject);
		treeView.setShowButton(true);
		treeView.setTitle("������Ŀ");
		SplitPane.add(treeView, "left");
		treeView.setShowControlPanel(true);
	}

    private void buildProjectTree() throws Exception {
		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();
		projectTreeBuilder.build(this, treeProject, actionOnLoad);
		authorizedOrgs = (Set)ActionCache.get("FDCBillListUIHandler.authorizedOrgs");
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
	}
    
    protected void setComponentState()
	{
    	btnTraceUp.setVisible(false);
		btnTraceDown.setVisible(false);
		btnCreateTo.setVisible(false);
		btnCreateTo.setVisible(false);
		btnAudit.setEnabled(true);
		btnUnAudit.setEnabled(true);
	}
    
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected(tblMain);
		checkIsExistAuditedRows();
		audit(getSelectedIdValues(tblMain,"id"));
		super.actionAudit_actionPerformed(e);
		showOprtOKMsgAndRefresh();
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected(tblMain);
		checkIsExistUnAuditRows();
		unAudit(getSelectedIdValues(tblMain,"id"));
		super.actionUnAudit_actionPerformed(e);
		
		showOprtOKMsgAndRefresh();
	}    
	
	protected void audit(List ids) throws Exception {
		MaterialOrderBizBillFactory.getRemoteInstance().audit(ids);
	}
	
	protected void unAudit(List ids) throws Exception {
		MaterialOrderBizBillFactory.getRemoteInstance().unAudit(ids);
	}
	
	public  List getSelectedIdValues(KDTable table, String keyFieldName) {
		List ids = new ArrayList();

		int[] selectedRows = KDTableUtil.getSelectedRows(table);
		for (int i = 0; i < selectedRows.length; i++) {
			if(table.getCell(selectedRows[i], keyFieldName)==null){
				return null;
			}
			String id = (String) table.getCell(selectedRows[i], keyFieldName).getValue();
			ids.add(id);
		}
		return ids;
	}
	
	/**
	 * 
	 * ��������ʾ�����ɹ�
	 * @author:liupd
	 * ����ʱ�䣺2006-8-1 <p>
	 */
	protected void showOprtOKMsgAndRefresh() throws Exception {
		FDCClientUtils.showOprtOK(this);
		refreshList();
	}
    
	/**
	 * 
	 * ��������鵱ǰ���ݵ�Table�Ƿ�ѡ����
	 * @author:liupd
	 * @see com.kingdee.eas.framework.client.ListUI#checkSelected()
	 */
	public void checkSelected(KDTable table) {
		if (table.getRowCount()==0 || table.getSelectManager().size() == 0) {
	        MsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource+ "Msg_MustSelected"));
	        SysUtil.abort();
	    }
		
	}
   protected void checkIsExistAuditedRows(){
	   int[] selectedRows = KDTableUtil.getSelectedRows(tblMain);
		for (int i = 0; i < selectedRows.length; i++) {
			if(tblMain.getCell(selectedRows[i], "state")!=null){
				Object objState = tblMain.getCell(selectedRows[i], "state").getValue();
				if(objState == null || !"���ύ".equals(objState.toString())){
					MsgBox.showInfo("���ڲ��������������ļ�¼��������ѡ�񣬱�֤��ѡ�ļ�¼�����ύ״̬");	 
					SysUtil.abort();
				}
			}
		}	  
   }
   
   protected void checkIsExistEditRows(){
	   int[] selectedRows = KDTableUtil.getSelectedRows(tblMain);
		for (int i = 0; i < selectedRows.length; i++) {
			if(tblMain.getCell(selectedRows[i], "state")!=null){
				Object objState = tblMain.getCell(selectedRows[i], "state").getValue();
				if(objState !=null && "������".equals(objState.toString())){
					MsgBox.showInfo("�������޸Ļ�ɾ���������ĵ���!");	 
					SysUtil.abort();
				}
			}
		}
	  
   }
   
   protected void checkIsExistUnAuditRows(){
	   int[] selectedRows = KDTableUtil.getSelectedRows(tblMain);
		for (int i = 0; i < selectedRows.length; i++) {
			if(tblMain.getCell(selectedRows[i], "state")!=null){
				Object objState = tblMain.getCell(selectedRows[i], "state").getValue();
				if( objState!=null &&  !"������".equals(objState.toString())){
					MsgBox.showInfo("����δ�����ļ�¼�����޸�ѡ��!");	 
					SysUtil.abort();
				}
			}
		}
   }

	protected void treeProject_valueChanged(TreeSelectionEvent e) throws Exception {
		super.treeProject_valueChanged(e);
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeProject.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		this.execQuery();
		this.refresh(null);
	}
   
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		try {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeProject.getLastSelectedPathComponent();
			FilterInfo filter = new FilterInfo();
			FilterItemCollection filterItems = filter.getFilterItems();
			/*
			 * ������Ŀ��
			 */
			
			if (node == null) {
				node = (DefaultKingdeeTreeNode) treeProject.getModel().getRoot();
			}
			if (node != null) {
				BOSUuid id = null;
				// ѡ����ǳɱ����ģ�ȡ�óɱ����ļ��¼��ɱ����ģ�����У��µ����к�ͬ
				if (node.getUserObject() instanceof OrgStructureInfo || node.getUserObject() instanceof FullOrgUnitInfo) {
					
					if (node.getUserObject() instanceof OrgStructureInfo) {
						id = ((OrgStructureInfo)node.getUserObject()).getUnit().getId();	
					}else{
						id = ((FullOrgUnitInfo)node.getUserObject()).getId();
					}				
					
					String orgUnitLongNumber = null;
					if(orgUnit!=null && id.toString().equals(orgUnit.getId().toString())){					
						orgUnitLongNumber = orgUnit.getLongNumber();
					}else{
						FullOrgUnitInfo orgUnitInfo = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(id));
						orgUnitLongNumber = orgUnitInfo.getLongNumber();
					}
					
					FilterInfo f = new FilterInfo();
					f.getFilterItems().add(new FilterItemInfo("orgUnit.longNumber", orgUnitLongNumber + "%",CompareType.LIKE));
	
					f.getFilterItems().add(new FilterItemInfo("orgUnit.isCostOrgUnit", Boolean.TRUE));
					f.getFilterItems().add(new FilterItemInfo("orgUnit.id", authorizedOrgs,CompareType.INCLUDE));
					
					f.setMaskString("#0 and #1 and #2");
					
					if(filter!=null){
						filter.mergeFilter(f,"and");
					}
				}else if (node.getUserObject() instanceof CurProjectInfo) {// ѡ�������Ŀ��ȡ����Ŀ���¼���Ŀ������У��µ����к�ͬ
					CurProjectInfo info= (CurProjectInfo)node.getUserObject();
					id = info.getId();
					Set idSet = FDCClientUtils.genProjectIdSet(id);
					filterItems.add(new FilterItemInfo("entrys.curProject.id", idSet, CompareType.INCLUDE));
				}
	
			}
			
			 viewInfo.setFilter(filter);
			
		} catch (Exception e) {
			this.handleException(e);
		}
		
		return super.getQueryExecutor(queryPK, viewInfo);
	}

	/**
	 * ��ӿ��ٶ�λ�ֶΣ����ݱ�ţ��������ڡ���Ӧ��
	 * 
	 * @author owen_wen 2010-09-07
	 * 
	 */
	protected String[] getLocateNames() {
		return new String[] { "number", "bizDate", "suppliers.name", };
	}
}