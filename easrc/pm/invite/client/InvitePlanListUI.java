/**
 * output package name
 */
package com.kingdee.eas.port.pm.invite.client;

import java.awt.event.*;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.basedata.assistant.ProjectInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.port.pm.invite.util.SLProjectTreeBuilder;

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
    	
    	
    	SLProjectTreeBuilder projectBuilder = new SLProjectTreeBuilder();
    	projectBuilder.build(this, kDTree1, actionOnLoad);
    	
    	if(this.kDTree1.getRowCount()>0){
    		this.kDTree1.setSelectionRow(0);
    		this.kDTree1.expandAllNodes(true, (TreeNode) this.kDTree1.getModel().getRoot());
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
    	DefaultKingdeeTreeNode treeNote = (DefaultKingdeeTreeNode) this.kDTree1.getLastSelectedPathComponent();
    	if(treeNote==null){}
    	if(treeNote.getUserObject() instanceof FullOrgUnitInfo)
    	{
    		info = (FullOrgUnitInfo)treeNote.getUserObject();
    	}
    	else if(treeNote.getUserObject() instanceof ProjectInfo)
    	{
    		info = (ProjectInfo)treeNote.getUserObject();
    	}
    	else
    	{
    		
    	}
    	refreshList();
    }
    
    
    protected IQueryExecutor getQueryExecutor(IMetaDataPK arg0,EntityViewInfo entityViewInfo) {
    	EntityViewInfo newEntityViewInfo = (EntityViewInfo) entityViewInfo.clone();
    	FilterInfo filterInfo = new FilterInfo();
    	if(info != null) {
    		if(info instanceof FullOrgUnitInfo) {
    			
    		} else if(info instanceof ProjectInfo) {
    			filterInfo.getFilterItems().add(new FilterItemInfo("id", ((ProjectInfo) info).getId(), CompareType.EQUALS));
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