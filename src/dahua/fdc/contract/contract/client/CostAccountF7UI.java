/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.*;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CostAccountF7UI extends AbstractCostAccountF7UI
{
    private static final Logger logger = CoreUIObject.getLogger(CostAccountF7UI.class);
    private boolean isCancel=true;
    public boolean isCancel() {
		return isCancel;
	}

	public void setCancel(boolean isCancel) {
		this.isCancel = isCancel;
	}

    public CostAccountF7UI() throws Exception
    {
        super();
    }

    public void storeFields()
    {
        super.storeFields();
    }
    
    protected void btnSelected_actionPerformed(ActionEvent e) throws Exception {
    	confirm();
    	setCancel(false);
    }
    
    protected void btnQuit_actionPerformed(ActionEvent e) throws Exception {
    	destroyWindow();
    	setCancel(true);
    }
    
    public void onLoad() throws Exception {
    	menuBar.setVisible(false);
    	toolBar.setVisible(false);
    	super.onLoad();
    	treeMain.expandAllNodes(true, (TreeNode)treeMain.getModel().getRoot());
    }

    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    	if(e.getClickCount() == 2){
        	if(e.getType() == 0){
        		return;
        	}else{
        		confirm();
        		setCancel(false);
        	}
        }
    }
    
    private String getSelectedId(){
    	int actRowIdx = tblMain.getSelectManager().getActiveRowIndex();
    	if(actRowIdx < 0){
    		return null;
    	}else if(tblMain.getCell(actRowIdx,"id").getValue() != null){
    		return tblMain.getCell(actRowIdx,"id").getValue().toString();
    	}else{
    		return null;
    	}
    }
    
    public CostAccountInfo getData() throws Exception{
    	CostAccountInfo info=null;
    	String id = getSelectedId();
    	if(id == null) 
    		return null;
    	EntityViewInfo view = new EntityViewInfo();
    	view.getSelector().add("id");
    	view.getSelector().add("name");
    	view.getSelector().add("longNumber");
    	view.getSelector().add("curProject.id");
    	view.getSelector().add("curProject.displayName");
    	view.getSelector().add("isLeaf");
    	view.getSelector().add("CU.id");
    	view.getSelector().add("curProject.longNumber");
    	view.getSelector().add("isEnabled");
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("id",id));
    	view.setFilter(filter);
    	info=(CostAccountInfo)CostAccountFactory.getRemoteInstance().getCostAccountCollection(view).get(0);
    	if(!info.isIsLeaf()){
    		MsgBox.showWarning(this, FDCClientUtils.getRes("selectLeaf"));
    		SysUtil.abort();
    	}
    	disposeUIWindow();
    	return info;
    }
    
    private void confirm() throws Exception{
    	checkSelected();
    	getData();
    	setCancel(true);
    }
    public void checkSelected() {
        if (tblMain.getRowCount() == 0 
        		||tblMain.getSelectManager().size() == 0
        		||tblMain.getSelectManager().getActiveRowIndex() <0) {
            MsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
            SysUtil.abort();
        }
    }

//    protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
//    	super.treeMain_valueChanged(e);
//    	EntityViewInfo ev=getMainQuery();
//		FilterInfo filter = new FilterInfo();
//		String sql="select FCostAccountID from T_AIM_CostEntry where FHeadID in (select fid from T_AIM_AimCost where FIsLastVersion=1)";
//		filter.getFilterItems().add(
//				new FilterItemInfo("id", sql,CompareType.INNER));
//		try {
//			if(ev.getFilter()!=null){
//				ev.getFilter().mergeFilter(filter,"and");
//			}else{
//				ev.setFilter(filter);
//			}
//		} catch (BOSException ee) {
//			ee.printStackTrace();
//		}
//    }
}