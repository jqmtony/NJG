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
    		String pid = ((CurProjectInfo)getUIContext().get("treeSelectedObj")).getId().toString();
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
     * output menuItemImportData_actionPerformed method
     */
    protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.menuItemImportData_actionPerformed(e);
    }

    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    /**
     * output actionHelp_actionPerformed
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }

    /**
     * output actionAbout_actionPerformed
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionSendMessage_actionPerformed
     */
    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMessage_actionPerformed(e);
    }

    /**
     * output actionCalculator_actionPerformed
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }

    /**
     * output actionExport_actionPerformed
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExport_actionPerformed(e);
    }

    /**
     * output actionExportSelected_actionPerformed
     */
    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelected_actionPerformed(e);
    }

    /**
     * output actionRegProduct_actionPerformed
     */
    public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRegProduct_actionPerformed(e);
    }

    /**
     * output actionPersonalSite_actionPerformed
     */
    public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPersonalSite_actionPerformed(e);
    }

    /**
     * output actionProcductVal_actionPerformed
     */
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }

    /**
     * output actionExportSave_actionPerformed
     */
    public void actionExportSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSave_actionPerformed(e);
    }

    /**
     * output actionExportSelectedSave_actionPerformed
     */
    public void actionExportSelectedSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelectedSave_actionPerformed(e);
    }

    /**
     * output actionKnowStore_actionPerformed
     */
    public void actionKnowStore_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionKnowStore_actionPerformed(e);
    }

    /**
     * output actionAnswer_actionPerformed
     */
    public void actionAnswer_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAnswer_actionPerformed(e);
    }

    /**
     * output actionRemoteAssist_actionPerformed
     */
    public void actionRemoteAssist_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoteAssist_actionPerformed(e);
    }

    /**
     * output actionPopupCopy_actionPerformed
     */
    public void actionPopupCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupCopy_actionPerformed(e);
    }

    /**
     * output actionHTMLForMail_actionPerformed
     */
    public void actionHTMLForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForMail_actionPerformed(e);
    }

    /**
     * output actionExcelForMail_actionPerformed
     */
    public void actionExcelForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForMail_actionPerformed(e);
    }

    /**
     * output actionHTMLForRpt_actionPerformed
     */
    public void actionHTMLForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForRpt_actionPerformed(e);
    }

    /**
     * output actionExcelForRpt_actionPerformed
     */
    public void actionExcelForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForRpt_actionPerformed(e);
    }

    /**
     * output actionLinkForRpt_actionPerformed
     */
    public void actionLinkForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLinkForRpt_actionPerformed(e);
    }

    /**
     * output actionPopupPaste_actionPerformed
     */
    public void actionPopupPaste_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupPaste_actionPerformed(e);
    }

    /**
     * output actionToolBarCustom_actionPerformed
     */
    public void actionToolBarCustom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionToolBarCustom_actionPerformed(e);
    }

    /**
     * output actionCloudFeed_actionPerformed
     */
    public void actionCloudFeed_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudFeed_actionPerformed(e);
    }

    /**
     * output actionCloudShare_actionPerformed
     */
    public void actionCloudShare_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudShare_actionPerformed(e);
    }

    /**
     * output actionCloudScreen_actionPerformed
     */
    public void actionCloudScreen_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudScreen_actionPerformed(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
	    {if(getUIContext().get(TREE_SELECTED_OBJ) == null){
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
     * output actionRefresh_actionPerformed
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRefresh_actionPerformed(e);
    }

    /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }

    /**
     * output actionLocate_actionPerformed
     */
    public void actionLocate_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLocate_actionPerformed(e);
    }

    /**
     * output actionQuery_actionPerformed
     */
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQuery_actionPerformed(e);
    }

    /**
     * output actionImportData_actionPerformed
     */
    public void actionImportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionImportData_actionPerformed(e);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }

    /**
     * output actionExportData_actionPerformed
     */
    public void actionExportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportData_actionPerformed(e);
    }

    /**
     * output actionToExcel_actionPerformed
     */
    public void actionToExcel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionToExcel_actionPerformed(e);
    }

    /**
     * output actionStartWorkFlow_actionPerformed
     */
    public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionStartWorkFlow_actionPerformed(e);
    }

    /**
     * output actionPublishReport_actionPerformed
     */
    public void actionPublishReport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPublishReport_actionPerformed(e);
    }

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelCancel_actionPerformed(e);
    }

    /**
     * output actionQueryScheme_actionPerformed
     */
    public void actionQueryScheme_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQueryScheme_actionPerformed(e);
    }

    /**
     * output actionCreateTo_actionPerformed
     */
    public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateTo_actionPerformed(e);
    }

    /**
     * output actionCopyTo_actionPerformed
     */
    public void actionCopyTo_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopyTo_actionPerformed(e);
    }

    /**
     * output actionTraceUp_actionPerformed
     */
    public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceUp_actionPerformed(e);
    }

    /**
     * output actionTraceDown_actionPerformed
     */
    public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceDown_actionPerformed(e);
    }

    /**
     * output actionVoucher_actionPerformed
     */
    public void actionVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionVoucher_actionPerformed(e);
    }

    /**
     * output actionDelVoucher_actionPerformed
     */
    public void actionDelVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionDelVoucher_actionPerformed(e);
    }

    /**
     * output actionAuditResult_actionPerformed
     */
    public void actionAuditResult_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAuditResult_actionPerformed(e);
    }

    /**
     * output actionViewDoProccess_actionPerformed
     */
    public void actionViewDoProccess_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewDoProccess_actionPerformed(e);
    }

    /**
     * output actionMultiapprove_actionPerformed
     */
    public void actionMultiapprove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMultiapprove_actionPerformed(e);
    }

    /**
     * output actionNextPerson_actionPerformed
     */
    public void actionNextPerson_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNextPerson_actionPerformed(e);
    }

    /**
     * output actionWorkFlowG_actionPerformed
     */
    public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkFlowG_actionPerformed(e);
    }

    /**
     * output actionSendSmsMessage_actionPerformed
     */
    public void actionSendSmsMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendSmsMessage_actionPerformed(e);
    }

    /**
     * output actionSignature_actionPerformed
     */
    public void actionSignature_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSignature_actionPerformed(e);
    }

    /**
     * output actionWorkflowList_actionPerformed
     */
    public void actionWorkflowList_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkflowList_actionPerformed(e);
    }

    /**
     * output actoinViewSignature_actionPerformed
     */
    public void actoinViewSignature_actionPerformed(ActionEvent e) throws Exception
    {
        super.actoinViewSignature_actionPerformed(e);
    }

    /**
     * output actionNumberSign_actionPerformed
     */
    public void actionNumberSign_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNumberSign_actionPerformed(e);
    }

    /**
     * output actionTDPrint_actionPerformed
     */
    public void actionTDPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTDPrint_actionPerformed(e);
    }

    /**
     * output actionTDPrintPreview_actionPerformed
     */
    public void actionTDPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTDPrintPreview_actionPerformed(e);
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