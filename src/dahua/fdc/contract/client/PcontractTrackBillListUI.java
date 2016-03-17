/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.IPcontractTrackBill;
import com.kingdee.eas.fdc.contract.PcontractTrackBillFactory;
import com.kingdee.eas.fdc.contract.PcontractTrackBillInfo;
import com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointInfo;
import com.kingdee.eas.framework.batchHandler.UtilRequest;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class PcontractTrackBillListUI extends AbstractPcontractTrackBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(PcontractTrackBillListUI.class);
    private static final String TREE_SELECTED_OBJ = "treeSelectedObj";
	private static final String ORG_OBJ = "treeOrg";
	private Set ids = new HashSet();
    /**
     * output class constructor
     */
    public PcontractTrackBillListUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    
    public void onLoad() throws Exception {
    	// TODO Auto-generated method stub
    	super.onLoad();
    	treeMain.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                try {
                    treeMain_valueChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
    	buildProjectTree();
    }
    
    public void onShow() throws Exception {
    	// TODO Auto-generated method stub
    	super.onShow();
    }
    
    public void buildProjectTree() throws Exception {
		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();
		projectTreeBuilder.build(this, treeMain, actionOnLoad);
		if (treeMain.getRowCount() > 0) {
			treeMain.setSelectionRow(0);
			treeMain.expandPath(treeMain.getSelectionPath());
		}
	}

    protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
    	DefaultKingdeeTreeNode projectNode = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
    	if(isSelectedProjectNode()) {
			getUIContext().put(TREE_SELECTED_OBJ,projectNode.getUserObject());
			//根据选中的工程项目展示列表 mainQuery
			refreshList();
			if(tblMain.getRowCount3() > 0)
				actionAddNew.setEnabled(false);
			else
				actionAddNew.setEnabled(true);
    	}else{
    		getUIContext().put(TREE_SELECTED_OBJ,null);
    		getUIContext().put(ORG_OBJ,getTreeNode(e.getNewLeadSelectionPath()));
    		//展示所有列表
    		refreshList();
    		actionAddNew.setEnabled(false);
    	}
    }
    
    private boolean isSelectedProjectNode() {
		boolean result = false;
		DefaultKingdeeTreeNode treeNode = getTreeNode(treeMain.getSelectionPath());
		if (treeNode != null && treeNode.isLeaf()) {
			if (treeNode.getUserObject() instanceof CurProjectInfo) {
				result = true;
			}
		}
		return result;
	}
    
    public static DefaultKingdeeTreeNode getTreeNode(TreePath path) {
		return path == null ? null : (DefaultKingdeeTreeNode) path.getLastPathComponent();
	}
    
    protected IQueryExecutor getQueryExecutor(IMetaDataPK arg0,EntityViewInfo evi) {
    	EntityViewInfo newviewInfo = (EntityViewInfo)evi.clone();
    	FilterInfo filter = new FilterInfo();
    	if(getUIContext().get(TREE_SELECTED_OBJ) != null){
    		String pid = ((CurProjectInfo)getUIContext().get(TREE_SELECTED_OBJ)).getId().toString();
    		filter.getFilterItems().add(new FilterItemInfo("curProject.id",pid));
    	}else if(getUIContext().get(ORG_OBJ) != null){
    		ids.clear();
    		getChildProject((DefaultKingdeeTreeNode)getUIContext().get(ORG_OBJ),ids);
    		if(ids.size() > 0)
    			filter.getFilterItems().add(new FilterItemInfo("curProject.id",ids,CompareType.INCLUDE));
    		else
    			filter.getFilterItems().add(new FilterItemInfo("curProject.id","999"));
    	}
    	if(newviewInfo.getFilter()!=null){
    		try {
				newviewInfo.getFilter().mergeFilter(filter,"and");
			} catch (BOSException e) {
				handUIException(e);
			}
    	}else{
    		newviewInfo.setFilter(filter);
    	}
    	return super.getQueryExecutor(arg0, newviewInfo);
    }
    
    protected void getChildProject(DefaultKingdeeTreeNode treeNode, Set ids) {
		if (treeNode != null && treeNode.isLeaf()) {
			Object userObject = treeNode.getUserObject();
			if (userObject != null && userObject instanceof CurProjectInfo) {
				ids.add(((CurProjectInfo) userObject).getId().toString());
			}
			return;
		} else {
			if (treeNode == null) {
				return;
			}
			Object userObject = treeNode.getUserObject();
			if (userObject == null) {
				return;
			}
			if (userObject != null && userObject instanceof CurProjectInfo) {
				ids.add(((CurProjectInfo) userObject).getId().toString());
			}
			for (int i = 0; i < treeNode.getChildCount(); i++) {
				DefaultKingdeeTreeNode childNode = (DefaultKingdeeTreeNode) treeNode.getChildAt(i);
				getChildProject(childNode, ids);
			}
		}
	}
    
    /**
     * output actionAudit_actionPerformed
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
    	if(getSelectedKeyValue() == null){
    		MsgBox.showInfo("请选择一条记录！");
    		return;
    	}
//        super.actionAudit_actionPerformed(e);
    	IPcontractTrackBill  ibp = PcontractTrackBillFactory.getRemoteInstance();
    	PcontractTrackBillInfo info = ibp.getPcontractTrackBillInfo(new ObjectUuidPK(getSelectedKeyValue()));
    	if(!FDCBillStateEnum.SUBMITTED.equals(info.getTrackBillStatus())){
    		MsgBox.showInfo("已提交的单据才能进行审核操作！");
    		return;
    	}
    	ibp.audit(info);
        refreshList();
        MsgBox.showInfo("审核成功！");
    }

    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
//        super.tblMain_tableSelectChanged(e);
    	PcontractTrackBillInfo bainfo = (PcontractTrackBillInfo)getBizInterface().getValue(new ObjectUuidPK(getSelectedKeyValue()));
    	if(FDCBillStateEnum.AUDITTED.equals(bainfo.getTrackBillStatus())){
    		btnEdit.setEnabled(false);
    		btnRemove.setEnabled(false);
    		btnAudit.setEnabled(false);
    		btnFix.setEnabled(true);
    		btnUnAudit.setEnabled(true);
    	}else{
    		btnEdit.setEnabled(true);
    		btnRemove.setEnabled(true);
    		btnAudit.setEnabled(true);
    		btnFix.setEnabled(false);
    		btnUnAudit.setEnabled(false);
    	}
    }
    
    /**
     * output actionUnaudit_actionPerformed
     */
    public void actionUnaudit_actionPerformed(ActionEvent e) throws Exception
    {
    	if(getSelectedKeyValue() == null){
    		MsgBox.showInfo("请选择一条记录！");
    		return;
    	}
    	IPcontractTrackBill  ibp = PcontractTrackBillFactory.getRemoteInstance();
    	PcontractTrackBillInfo info = ibp.getPcontractTrackBillInfo(new ObjectUuidPK(getSelectedKeyValue()));
    	if(!info.isIsNew()){
    		MsgBox.showInfo("非最新版本不能反审核！");
    		return;
    	}
    	ibp.unaudit(info);
        refreshList();
        MsgBox.showInfo("反审核成功！");
    }

    /**
     * output actionFix_actionPerformed
     */
    public void actionFix_actionPerformed(ActionEvent e) throws Exception
    {
    	if(getSelectedKeyValue() == null){
    		MsgBox.showInfo("请选择一条记录！");
    		return;
    	}
    	PcontractTrackBillInfo info=PcontractTrackBillFactory.getRemoteInstance().getPcontractTrackBillInfo(new ObjectUuidPK(getSelectedKeyValue()),getBPSelectors());
    	if(!FDCBillStateEnum.AUDITTED.equals(info.getTrackBillStatus())){
    		MsgBox.showInfo("非审批单据不能修订！");
    		return;
    	}
    	if(!info.isIsNew()){
    		MsgBox.showInfo("非最新版本不能修订！");
    		return;
    	}
    	UIContext uiContext = new UIContext(this);
		uiContext.put("info", info);
//		prepareUIContext(uiContext, new ActionEvent(btnAddNew, 1001, btnAddNew.getActionCommand()));
//		getUIContext().putAll(uiContext);
		
		IUIWindow ui = UIFactory.createUIFactory(getEditUIModal()).create(getEditUIName(), uiContext, null,	OprtState.ADDNEW);
		ui.show();
		Window win = SwingUtilities.getWindowAncestor((Component)ui.getUIObject());
        if(!win.isActive() && (win instanceof JFrame) && ((JFrame)win).getExtendedState() == 1)
            ((JFrame)win).setExtendedState(0);
        if(ui != null && isDoRefresh(ui)){
            if(UtilRequest.isPrepare("ActionRefresh", this))
                prepareRefresh(null).callHandler();
            refresh(e);
            setPreSelecteRow();
        }
    }
    
    public SelectorItemCollection getBPSelectors(){
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("entrys.id"));
        sic.add(new SelectorItemInfo("entrys.pcid"));
    	sic.add(new SelectorItemInfo("entrys.level"));
    	sic.add(new SelectorItemInfo("entrys.longNumber"));
    	sic.add(new SelectorItemInfo("entrys.headNumber"));
    	sic.add(new SelectorItemInfo("entrys.hyType"));
    	sic.add(new SelectorItemInfo("entrys.planAmount"));
    	sic.add(new SelectorItemInfo("entrys.changeRate"));
    	sic.add(new SelectorItemInfo("entrys.contralAmount"));
    	sic.add(new SelectorItemInfo("entrys.sgtDate"));
    	sic.add(new SelectorItemInfo("entrys.sgtRealDate"));
    	sic.add(new SelectorItemInfo("entrys.sgtOverdue"));
    	sic.add(new SelectorItemInfo("entrys.sgtPlanDate"));
    	sic.add(new SelectorItemInfo("entrys.csDate"));
    	sic.add(new SelectorItemInfo("entrys.csRealDate"));
    	sic.add(new SelectorItemInfo("entrys.csOverdue"));
    	sic.add(new SelectorItemInfo("entrys.csPlanDate"));
    	sic.add(new SelectorItemInfo("entrys.startDate"));
    	sic.add(new SelectorItemInfo("entrys.startRealDate"));
    	sic.add(new SelectorItemInfo("entrys.startOverdue"));
    	sic.add(new SelectorItemInfo("entrys.startPlanDate"));
    	sic.add(new SelectorItemInfo("entrys.endDate"));
    	sic.add(new SelectorItemInfo("entrys.endRealDate"));
    	sic.add(new SelectorItemInfo("entrys.endOverdue"));
    	sic.add(new SelectorItemInfo("entrys.endPlanDate"));
    	sic.add(new SelectorItemInfo("entrys.csendDate"));
    	sic.add(new SelectorItemInfo("entrys.csendRealDate"));
    	sic.add(new SelectorItemInfo("entrys.csendOverdue"));
    	sic.add(new SelectorItemInfo("entrys.csendPlanDate"));
        sic.add(new SelectorItemInfo("isNew"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("curProject.id"));
    	sic.add(new SelectorItemInfo("curProject.number"));
    	sic.add(new SelectorItemInfo("curProject.name"));
    	sic.add(new SelectorItemInfo("version"));
        sic.add(new SelectorItemInfo("trackBillStatus"));
        return sic;
    }
    
    protected String getEditUIModal() {
    	return UIFactoryName.NEWTAB;
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.contract.PcontractTrackBillFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.contract.PcontractTrackBillInfo objectValue = new com.kingdee.eas.fdc.contract.PcontractTrackBillInfo();
		
        return objectValue;
    }

}