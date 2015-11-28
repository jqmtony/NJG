/**
 * output package name
 */
package com.kingdee.eas.fdc.costindexdb.client;

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
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
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
import com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointFactory;
import com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointInfo;
import com.kingdee.eas.fdc.costindexdb.IBaseAndSinglePoint;
import com.kingdee.eas.framework.batchHandler.UtilRequest;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class BaseAndSinglePointListUI extends AbstractBaseAndSinglePointListUI
{
    private static final Logger logger = CoreUIObject.getLogger(BaseAndSinglePointListUI.class);
    // 获取有权限的组织
//	protected Set authorizedOrgs = null;
	private static final String TREE_SELECTED_OBJ = "treeSelectedObj";
	private static final String ORG_OBJ = "treeOrg";
	private Set ids = new HashSet();
    /**
     * output class constructor
     */
    public BaseAndSinglePointListUI() throws Exception
    {
        super();
    }

    @Override
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
    	actionUnAdudit.setVisible(false);
    	tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
    }
    
    public void buildProjectTree() throws Exception {
		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();
		projectTreeBuilder.build(this, treeMain, actionOnLoad);
//		authorizedOrgs = (Set) ActionCache.get("FDCBillListUIHandler.authorizedOrgs");
//		if (authorizedOrgs == null) {
//			authorizedOrgs = new HashSet();
//			Map orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(
//					new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId()), OrgType.CostCenter, null, null, null);
//			if (orgs != null) {
//				Set orgSet = orgs.keySet();
//				Iterator it = orgSet.iterator();
//				while (it.hasNext()) {
//					authorizedOrgs.add(it.next());
//				}
//			}
//		}
		if (treeMain.getRowCount() > 0) {
			treeMain.setSelectionRow(0);
			treeMain.expandPath(treeMain.getSelectionPath());
			
		}
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
    
    @Override
    protected IQueryExecutor getQueryExecutor(IMetaDataPK arg0,EntityViewInfo evi) {
    	EntityViewInfo newviewInfo = (EntityViewInfo)evi.clone();
    	FilterInfo filter = new FilterInfo();
    	if(getUIContext().get(TREE_SELECTED_OBJ) != null){
    		String pid = ((CurProjectInfo)getUIContext().get(TREE_SELECTED_OBJ)).getId().toString();
    		filter.getFilterItems().add(new FilterItemInfo("projectId",pid));
    	}else if(getUIContext().get(ORG_OBJ) != null){
    		ids.clear();
    		getChildProject((DefaultKingdeeTreeNode)getUIContext().get(ORG_OBJ),ids);
    		if(ids.size() > 0)
    			filter.getFilterItems().add(new FilterItemInfo("projectId",ids,CompareType.INCLUDE));
    		else
    			filter.getFilterItems().add(new FilterItemInfo("projectId","999"));
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
    
    public static DefaultKingdeeTreeNode getTreeNode(TreePath path) {
		return path == null ? null : (DefaultKingdeeTreeNode) path.getLastPathComponent();
	}
    
    @Override
    protected boolean isIgnoreCUFilter() {
    	return super.isIgnoreCUFilter();
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
        super.tblMain_tableClicked(e);
    }

    /**
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
//        super.tblMain_tableSelectChanged(e);
    	BaseAndSinglePointInfo bainfo = (BaseAndSinglePointInfo)getBizInterface().getValue(new ObjectUuidPK(getSelectedKeyValue()));
    	if(FDCBillStateEnum.AUDITTED.equals(bainfo.getPointBillStatus())){
    		btnEdit.setEnabled(false);
    		btnRemove.setEnabled(false);
    		btnAudit.setEnabled(false);
    		btnFix.setEnabled(true);
    	}else{
    		btnEdit.setEnabled(true);
    		btnRemove.setEnabled(true);
    		btnAudit.setEnabled(true);
    		btnFix.setEnabled(false);
    	}
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception{
    	if(getUIContext().get(TREE_SELECTED_OBJ) == null){
			MsgBox.showInfo("请选择具体的项目！");
			return;
		}
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionView_actionPerformed
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionView_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
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
    	IBaseAndSinglePoint ibp = BaseAndSinglePointFactory.getRemoteInstance();
    	BaseAndSinglePointInfo info = ibp.getBaseAndSinglePointInfo(new ObjectUuidPK(getSelectedKeyValue()));
    	if(!FDCBillStateEnum.SUBMITTED.equals(info.getPointBillStatus())){
    		MsgBox.showInfo("已提交的单据才能进行审核操作！");
    		return;
    	}
    	ibp.audit(info);
        refreshList();
        MsgBox.showInfo("审核成功！");
    }

    /**
     * output actionUnAdudit_actionPerformed
     */
    public void actionUnAdudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionUnAdudit_actionPerformed(e);
    }

    /**修订功能
     * output actionRefix_actionPerformed
     */
    public void actionRefix_actionPerformed(ActionEvent e) throws Exception
    {
    	if(getSelectedKeyValue() == null){
    		MsgBox.showInfo("请选择一条记录！");
    		return;
    	}
    	BaseAndSinglePointInfo info=BaseAndSinglePointFactory.getRemoteInstance().getBaseAndSinglePointInfo(new ObjectUuidPK(getSelectedKeyValue()),getBPSelectors());
    	if(!FDCBillStateEnum.AUDITTED.equals(info.getPointBillStatus())){
    		MsgBox.showInfo("非审批单据不能修订！");
    		return;
    	}
    	if(!info.isIsLatest()){
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
        sic.add(new SelectorItemInfo("isLatest"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("projectName"));
        sic.add(new SelectorItemInfo("projectId"));
        sic.add(new SelectorItemInfo("version"));
        sic.add(new SelectorItemInfo("pointBillStatus"));
        sic.add(new SelectorItemInfo("beizhu"));
    	sic.add(new SelectorItemInfo("entrys.id"));
    	sic.add(new SelectorItemInfo("entrys.pointName"));
    	sic.add(new SelectorItemInfo("entrys.pointValue"));
	    sic.add(new SelectorItemInfo("entrys.baseUnit.id"));
		sic.add(new SelectorItemInfo("entrys.baseUnit.name"));
        sic.add(new SelectorItemInfo("entrys.baseUnit.number"));
    	sic.add(new SelectorItemInfo("entrys.isCombo"));
	    sic.add(new SelectorItemInfo("entrys.productType.id"));
		sic.add(new SelectorItemInfo("entrys.productType.name"));
        sic.add(new SelectorItemInfo("entrys.productType.number"));
    	sic.add(new SelectorItemInfo("entrys.isModel"));
    	sic.add(new SelectorItemInfo("entrys.buildValue"));
    	sic.add(new SelectorItemInfo("entrys.beizhu"));
	    sic.add(new SelectorItemInfo("entrys.buildNo.id"));
		sic.add(new SelectorItemInfo("entrys.buildNo.name"));
        sic.add(new SelectorItemInfo("entrys.buildNo.number"));
    	sic.add(new SelectorItemInfo("Ecost.id"));
	    sic.add(new SelectorItemInfo("Ecost.costAccount.id"));
		sic.add(new SelectorItemInfo("Ecost.costAccount.name"));
        sic.add(new SelectorItemInfo("Ecost.costAccount.number"));
    	sic.add(new SelectorItemInfo("Ecost.pointName"));
    	sic.add(new SelectorItemInfo("Ecost.pointValue"));
	    sic.add(new SelectorItemInfo("Ecost.baseUnit.id"));
		sic.add(new SelectorItemInfo("Ecost.baseUnit.name"));
        sic.add(new SelectorItemInfo("Ecost.baseUnit.number"));
    	sic.add(new SelectorItemInfo("Ecost.isCombo"));
	    sic.add(new SelectorItemInfo("Ecost.productType.id"));
		sic.add(new SelectorItemInfo("Ecost.productType.name"));
        sic.add(new SelectorItemInfo("Ecost.productType.number"));
    	sic.add(new SelectorItemInfo("Ecost.isModel"));
    	sic.add(new SelectorItemInfo("Ecost.buildValue"));
    	sic.add(new SelectorItemInfo("Ecost.beizhu"));
	    sic.add(new SelectorItemInfo("Ecost.buildNo.id"));
		sic.add(new SelectorItemInfo("Ecost.buildNo.name"));
        sic.add(new SelectorItemInfo("Ecost.buildNo.number"));
        
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
        return com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointInfo objectValue = new com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointInfo();
		
        return objectValue;
    }

}