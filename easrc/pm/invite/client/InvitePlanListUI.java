/**
 * output package name
 */
package com.kingdee.eas.port.pm.invite.client;

import java.awt.event.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.common.CtrlUtil.Str;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.assistant.IProject;
import com.kingdee.eas.basedata.assistant.ProjectCollection;
import com.kingdee.eas.basedata.assistant.ProjectFactory;
import com.kingdee.eas.basedata.assistant.ProjectInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitCollection;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.IOrgStructure;
import com.kingdee.eas.basedata.org.OrgStructureCollection;
import com.kingdee.eas.basedata.org.OrgStructureFactory;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.port.pm.base.JudgesTreeInfo;
import com.kingdee.eas.port.pm.invite.util.SLProjectTreeBuilder;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.xr.helper.common.PortProjectTreeBuilder;

/**
 * output class name
 */
public class InvitePlanListUI extends AbstractInvitePlanListUI
{
    private static final Logger logger = CoreUIObject.getLogger(InvitePlanListUI.class);
    private Object info = null;
    /**
     * output class constructor
     */
    public InvitePlanListUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
//    	SLProjectTreeBuilder projectBuilder = new SLProjectTreeBuilder();
//    	projectBuilder.build(this, kDTree1, actionOnLoad);
//    	PortProjectTreeBuilder projectTreeBuilder = new PortProjectTreeBuilder();
//		projectTreeBuilder.build(this, kDTree1, actionOnLoad);
    	
    	buildProjectTree();
    	if(this.kDTree1.getRowCount()>0){
    		this.kDTree1.setSelectionRow(0);
    		this.kDTree1.expandAllNodes(true, (TreeNode) this.kDTree1.getModel().getRoot());
    	}
    }

    protected Set authorizedOrgs = null;
    public void buildProjectTree() throws Exception {

    	PortProjectTreeBuilder projectTreeBuilder = new PortProjectTreeBuilder();

		projectTreeBuilder.build(this, kDTree1, actionOnLoad);
		
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
    @Override
    protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
    	// TODO Auto-generated method stub
    	super.prepareUIContext(uiContext, e);
    	if(getActionFromActionEvent(e).equals(actionAddNew)) {
			DefaultKingdeeTreeNode treeNote = (DefaultKingdeeTreeNode)this.kDTree1.getLastSelectedPathComponent();
			if (treeNote.getUserObject() instanceof ProjectInfo) {
				getUIContext().put("treeInfo", treeNote.getUserObject());
			} else {
				MsgBox.showWarning("非子节点无法新增！");
				SysUtil.abort();
			}
		}
    }
    protected String getEditUIModal() {
    	return UIFactoryName.MODEL;
    }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    protected void kDTree1_valueChanged(TreeSelectionEvent e) throws Exception {
    	super.kDTree1_valueChanged(e);
    	refresh(null);
    }
    
    /**
     * 获取当前项目以及子项目的单据id
     */
    protected Set<String> getProjectIdSet(ProjectInfo project) throws Exception {
    	Set<String> idset = new HashSet<String>();
		idset.add(project.getId().toString());
		EntityViewInfo entityView = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("longNumber", project.getLongNumber() + "%", CompareType.LIKE));
		entityView.setFilter(filter);
		IProject iproject = ProjectFactory.getRemoteInstance();
		ProjectCollection projectColl = iproject.getProjectCollection(entityView);
		for(int i = 0; i < projectColl.size(); i++) {
			ProjectInfo info = projectColl.get(i);
			idset.add(info.getId().toString());
		}	
		return idset;
    }

    protected Set<String> getProjectIdSet(OrgStructureInfo orgInfo) throws Exception {
    	String longNumber = orgInfo.getLongNumber();
    	EntityViewInfo entityViewInfo = new EntityViewInfo();
    	FilterInfo filterInfo = new FilterInfo();
    	entityViewInfo.setFilter(filterInfo);
    	filterInfo.getFilterItems().add(new FilterItemInfo("longNumber", longNumber+"%", CompareType.LIKE));
    	CompanyOrgUnitCollection companyColl = CompanyOrgUnitFactory.getRemoteInstance().getCompanyOrgUnitCollection(entityViewInfo);
    	Set<String> companyId = new HashSet<String>();
    	for(int i = 0; i < companyColl.size(); i++) {
    		CompanyOrgUnitInfo companyInfo = companyColl.get(i);
    		companyId.add(companyInfo.getId().toString());
    	}
    	EntityViewInfo entityView = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("company.id", companyId, CompareType.INCLUDE));
    	entityView.setFilter(filter);
    	ProjectCollection projectColl = ProjectFactory.getRemoteInstance().getProjectCollection(entityView);
    	Set<String> idSet = new HashSet<String>();
    	for(int i = 0; i < projectColl.size(); i++){
    		ProjectInfo info = projectColl.get(i);
    		idSet.add(info.getId().toString());
    	}
    	return idSet;
    }
    protected IQueryExecutor getQueryExecutor(IMetaDataPK arg0,EntityViewInfo entityViewInfo) {
    	EntityViewInfo newEntityViewInfo = (EntityViewInfo) entityViewInfo.clone();
    	FilterInfo filterInfo = new FilterInfo();
    	DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)this.kDTree1.getLastSelectedPathComponent();
    	if(treeNode!=null)
    	{
    		if(treeNode !=null && treeNode.getUserObject() instanceof OrgStructureInfo){
    			OrgStructureInfo orgInfo = (OrgStructureInfo) treeNode.getUserObject();
//    			try {
//					Set<String> idset = getProjectIdSet(orgInfo);
					filterInfo.getFilterItems().add(new FilterItemInfo("project.Company.longnumber", orgInfo.getLongNumber()+"%", CompareType.LIKE));
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
    		} else if(treeNode.getUserObject() instanceof ProjectInfo) {
    			ProjectInfo project = (ProjectInfo) treeNode.getUserObject();
//    			try {
//					Set<String> idset = getProjectIdSet(project);
//					filterInfo.getFilterItems().add(new FilterItemInfo("project.id", idset, CompareType.INCLUDE));
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
    			filterInfo.getFilterItems().add(new FilterItemInfo("project.longNumber", project.getLongNumber() + "%", CompareType.LIKE));
    		} else {
    			filterInfo.getFilterItems().add(new FilterItemInfo("id", "null"));
			}
    		
    		try 
        	{
    			if(entityViewInfo.getFilter()!=null)
    			{
    				newEntityViewInfo.getFilter().mergeFilter(filterInfo, "and");
    			}
    			else
    			{
    				newEntityViewInfo.setFilter(filterInfo);
    			}
    		} 
        	catch (BOSException e) {
    			e.printStackTrace();
    		}
    	}
    	return super.getQueryExecutor(arg0, newEntityViewInfo);
    }
    
    /**
     * output actionAudit_actionPerformed
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
//    	UIContext context = new UIContext(this);
//    	context.put("id", "");
//    	UIFactory.createUIFactory(UIFactoryName.MODEL).create(InvitePlanEditUI.class.getName(), context, null, OprtState.ADDNEW).show();
        super.actionAudit_actionPerformed(e);
    }
    
    protected boolean isIgnoreCUFilter() {
    	return true;
    }

    /**
     * output actionUnAudit_actionPerformed
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionUnAudit_actionPerformed(e);
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.pm.invite.InvitePlanFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.port.pm.invite.InvitePlanInfo objectValue = new com.kingdee.eas.port.pm.invite.InvitePlanInfo();
		
        return objectValue;
    }

}