/**
 * output package name
 */
package com.kingdee.eas.port.pm.invest.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultTreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.eas.basedata.assistant.IProject;
import com.kingdee.eas.basedata.assistant.ProjectFactory;
import com.kingdee.eas.basedata.assistant.ProjectInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.xr.helper.common.PortProjectTreeBuilder;

/**
 * output class name
 */
public class ProjectTreeSelectorUI extends AbstractProjectTreeSelectorUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProjectTreeSelectorUI.class);
    
    /**
     * output class constructor
     */
    public ProjectTreeSelectorUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	
    	PortProjectTreeBuilder projectTreeBuilder = new PortProjectTreeBuilder();

		projectTreeBuilder.build(this, kDTree1, actionOnLoad);
		
		if(this.kDTree1.getRowCount() > 0)
		{
			this.kDTree1.setSelectionRow(0);
			this.kDTree1.expandAllNodes(true,(DefaultKingdeeTreeNode) this.kDTree1.getModel().getRoot());
		}
		
		this.toolBar.removeAll();
		this.toolBar.add(this.btnAddNew);
		this.toolBar.add(this.btnQuery);
		this.btnAddNew.setVisible(false);
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    	checkSelected();
    	if(e.getClickCount()==2)
    	{
    		String proId = this.getSelectedKeyValue();
    		ArrayList<String> rrlist = this.getSelectedIdValues();
    		
    		IProject IProject = ProjectFactory.getRemoteInstance();
    		KDBizPromptBox prmtCon = (KDBizPromptBox)getUIContext().get("prmtPro");
    		
    		if(getUIContext().get("flses")==null||!UIRuleUtil.getBooleanValue(getUIContext().get("flses")))
    		{
        		if(prmtCon!=null)
        		{
        			this.getUIWindow().close();
        			prmtCon.setValue(IProject.getProjectInfo(new ObjectUuidPK(proId)));
        		}
    		}
    		else
    		{
    			if(prmtCon!=null)
        		{
        			prmtCon.setEnabledMultiSelection(true);
        			Object obj[] = new Object[rrlist.size()];
        			for (int i = 0; i < rrlist.size(); i++) 
        			{
        				obj[i] = IProject.getProjectInfo(new ObjectUuidPK(rrlist.get(i)));
					}
        			this.getUIWindow().close();
        			prmtCon.setValue(obj);
        		}
    			
    		}
    	}
    }
    
    protected void kDTree1_valueChanged(TreeSelectionEvent e) throws Exception {
    	refresh(null);
    }
    
    protected IQueryExecutor getQueryExecutor(IMetaDataPK arg0,EntityViewInfo arg1) {
    	
    	EntityViewInfo viewInfo = (EntityViewInfo)arg1.clone();
    	FilterInfo filInfo = null;
    	if(getUIContext().get("filInfo")!=null)
    	{
    		filInfo = (FilterInfo) getUIContext().get("filInfo");
    	}
    	else
    	{
    		filInfo = new FilterInfo();
    	}
    	DefaultKingdeeTreeNode selectNode = (DefaultKingdeeTreeNode)((DefaultTreeModel)kDTree1.getModel()).getRoot();
    	if(selectNode!=null)
    	{
    		if(selectNode.getUserObject() instanceof ProjectInfo)
    		{
    			ProjectInfo info = (ProjectInfo)selectNode.getUserObject();
    			filInfo.getFilterItems().add(new FilterItemInfo("longNumber",info.getLongNumber()+"%",CompareType.LIKE));
    		}
    	}
    	if(viewInfo.getFilter()!=null)
    	{
    		try {
				viewInfo.getFilter().mergeFilter(filInfo, "and");
			} catch (BOSException e) {
				e.printStackTrace();
			}
    	}
    	else
    	{
    		viewInfo.setFilter(filInfo);
    	}
    	return super.getQueryExecutor(arg0, viewInfo);
    }
    
    protected void btnCancal_actionPerformed(ActionEvent e) throws Exception {
    	this.getUIWindow().close();
    }
    
    protected void btnYes_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected();
    	
    	String proId = this.getSelectedKeyValue();
		ArrayList<String> rrlist = this.getSelectedIdValues();
		
		IProject IProject = ProjectFactory.getRemoteInstance();
		KDBizPromptBox prmtCon = (KDBizPromptBox)getUIContext().get("prmtPro");
		
		if(getUIContext().get("flses")==null||!UIRuleUtil.getBooleanValue(getUIContext().get("flses")))
		{
    		if(prmtCon!=null)
    		{
    			this.getUIWindow().close();
    			prmtCon.setValue(IProject.getProjectInfo(new ObjectUuidPK(proId)));
    		}
		}
		else
		{
			if(prmtCon!=null)
    		{
    			prmtCon.setEnabledMultiSelection(true);
    			Object obj[] = new Object[rrlist.size()];
    			for (int i = 0; i < rrlist.size(); i++) 
    			{
    				obj[i] = IProject.getProjectInfo(new ObjectUuidPK(rrlist.get(i)));
				}
    			this.getUIWindow().close();
    			prmtCon.setValue(obj);
    		}
			
		}
    }
    
    protected String getEntityBOSType() throws Exception {
    	return "DADE05EE";
    }

}