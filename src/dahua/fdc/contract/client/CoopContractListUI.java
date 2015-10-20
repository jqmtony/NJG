/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.ContractSourceInfo;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.client.tree.DefaultLNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ILNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ITreeBuilder;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;

/**
 * output class name
 */
public class CoopContractListUI extends AbstractCoopContractListUI
{
    private static final Logger logger = CoreUIObject.getLogger(CoopContractListUI.class);
    
    /**
     * output class constructor
     */
    public CoopContractListUI() throws Exception
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

    protected void initButtonStatus () {
    	this.setUITitle("战略合同查询");
    	this.actionAddNew.setVisible(false);
		this.actionView.setEnabled(true);
		this.actionEdit.setVisible(false);
		this.actionQuery.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionCopyTo.setVisible(false);
		this.actionRemove.setVisible(false);
		
		this.menuBiz.setVisible(false);
		
	}
    public void onLoad() throws Exception {
    	// TODO Auto-generated method stub
    	super.onLoad();
    	initProejctTree();
    	initTypeTree();
		initButtonStatus();
		initTableStyle();
		projectTree.setShowsRootHandles(true);
		projectTree.setSelectionRow(0);
		
		kDSplitPane1.setDividerLocation(280);
		leftSplitPane.setDividerLocation(300);
		rightSplitPanel.setDividerLocation(300);
		
//		this.conProject.remove(projectTree);
//		KDScrollPane sp = new KDScrollPane();
//		sp.add(projectTree);
//		conProject.add(sp,java.awt.BorderLayout.CENTER);
		
//		
//		conProject.remove(projectTree);
//		TreeView view = new TreeView(projectTree);
//		conProject.add(view);
    }
    
    protected void initTableStyle(){
    	this.tblDetail.getStyleAttributes().setLocked(true);
    	this.tblDetail.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
    }
    
    protected void initProejctTree(){
    	ProjectTreeBuilder builder = new ProjectTreeBuilder();
    	try {
			builder.build(this, projectTree, actionOnLoad);
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
		
    }
    
    
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
    		EntityViewInfo viewInfo) {
    	// TODO Auto-generated method stub
    	if(viewInfo != null)
    	viewInfo.setFilter(getMainFilter());
    	return super.getQueryExecutor(queryPK, viewInfo);
    }
    
    
    
    public FilterInfo getMainFilter(){
    	FilterInfo filter = new FilterInfo();
    	if(getAllSelectedProjectIds() != null && getAllSelectedProjectIds().size()>0){
    		filter.getFilterItems().add(new FilterItemInfo("curProject.id",getAllSelectedProjectIds(),CompareType.INCLUDE));
    	}
    	if(getAllSelectedTypeIds().size() > 0 ){
    		filter.getFilterItems().add(new FilterItemInfo("contractType.id",getAllSelectedTypeIds(),CompareType.INCLUDE));
    	}
       filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
       filter.getFilterItems().add(new FilterItemInfo("contractSourceId.id",ContractSourceInfo.COOP_VALUE));
       filter.getFilterItems().add(new FilterItemInfo("isSubContract",Boolean.FALSE));
//       filter.getFilterItems().add(new FilterItemInfo("cu.longnumber",SysContext.getSysContext().getCurrentCtrlUnit().getLongNumber()+"%",CompareType.LIKE));
   	   filter.getFilterItems().add(new FilterItemInfo("orgUnit.longnumber",SysContext.getSysContext().getCurrentOrgUnit().getLongNumber()+"%",CompareType.LIKE));
    	return filter;
    }
    
    
    public Set getAllSelectedProjectIds(){
    	DefaultKingdeeTreeNode node = getSelectNode(projectTree);
    	Set idSet = new HashSet();
    	if(node != null){
    		getProjectIdSet(idSet, node);
    	}
    	
    	return idSet;
    }
    
    
    public DefaultKingdeeTreeNode getSelectNode(KDTree tree){
    	DefaultKingdeeTreeNode node = null;
    	node = (DefaultKingdeeTreeNode) tree.getLastSelectedPathComponent();
    	return node;
    }
    
    
    public void getProjectIdSet(Set idSet,DefaultKingdeeTreeNode node){
//    	if(node != null && node.isLeaf()){
//    		if(node.getUserObject() instanceof CurProjectInfo){
//    			CurProjectInfo info = (CurProjectInfo) node.getUserObject();
//        		set.add(info.getId().toString());
//    		}
//    	}else{
//    		for(int i=0;i<node.getChildCount();i++){
//    			getProjectIdSet(set,(DefaultKingdeeTreeNode)node.getChildAt(i));
//    		}
//    	}
    	 if(node.isLeaf()){
  		   if(node.getUserObject() instanceof CurProjectInfo){
  			   idSet.add(((CurProjectInfo)node.getUserObject()).getId().toString());
  		   }
  		   if(node.getUserObject() instanceof OrgStructureInfo){
  			   idSet.add(((OrgStructureInfo)node.getUserObject()).getId().toString());
  		   }
  		   if(node.getUserObject() instanceof FullOrgUnitInfo){
  			   idSet.add(((FullOrgUnitInfo)node.getUserObject()).getId().toString());
  		   }
  	   }else{
  		   for(int i = 0 ; i<node.getChildCount();i++){
  			   DefaultKingdeeTreeNode childNode = (DefaultKingdeeTreeNode) node.getChildAt(i);
  			  getProjectIdSet(idSet,childNode);
  		   }
  	   }
    }
    
    protected void initTypeTree(){
    	try {
			buildContractTypeTree();
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
    }
    
    protected KDTree getContractTypeTree(){
    	return this.typeTree;
    }
    
    private ITreeBase getTreeInterface() {

		ITreeBase treeBase = null;
		try {
			treeBase = ContractTypeFactory.getRemoteInstance();
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}

		return treeBase;
	}
    
    protected ILNTreeNodeCtrl getLNTreeNodeCtrl() throws Exception {
		return new DefaultLNTreeNodeCtrl(getTreeInterface());
	}
    
    protected int getTreeInitialLevel() {
		return TreeBuilderFactory.DEFAULT_INITIAL_LEVEL;
	}

	protected int getTreeExpandLevel() {
		return TreeBuilderFactory.DEFAULT_EXPAND_LEVEL;
	}
	
	protected FilterInfo getDefaultFilterForTree() {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		return filter;
	}

	/**
	 * 定义根节点显示名称，默认返回null，即没有根结点（所有结点来自于数据） 继承类可以重载，添加显示结点
	 */
	protected String getRootName() {
		return ContractClientUtils.getRes("allContractType");
	}

	
	protected Object getRootObject() {
		return getRootName();
	}
	
	protected boolean containConWithoutTxt(){
		return false;
	}
	
    protected void buildContractTypeTree() throws Exception {
		KDTree treeMain = getContractTypeTree();
		TreeSelectionListener treeSelectionListener = null;
		TreeSelectionListener[] listeners = treeMain.getTreeSelectionListeners();
		if (listeners.length > 0) {
			 treeSelectionListener = listeners[0];
			treeMain.removeTreeSelectionListener(treeSelectionListener);
		}

		 ITreeBuilder treeBuilder = TreeBuilderFactory.createTreeBuilder(getLNTreeNodeCtrl(),
				getTreeInitialLevel(), getTreeExpandLevel(), this
						.getDefaultFilterForTree());

		if (getRootName() != null) {
			KDTreeNode rootNode = new KDTreeNode(getRootObject());
			((DefaultTreeModel) treeMain.getModel()).setRoot(rootNode);
			
		} else {
			((DefaultTreeModel) treeMain.getModel()).setRoot(null);
		}
		
		treeBuilder.buildTree(treeMain);
		if(containConWithoutTxt()){
			DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) treeMain.getModel().getRoot();
			KDTreeNode node = new KDTreeNode("allContract");
			node.setUserObject("allContract");
			node.setText(getRootName());
			root.setText("合同");
			node.add(root);
			((DefaultTreeModel) treeMain.getModel()).setRoot(node);
			
		}
		
		treeMain.addTreeSelectionListener(treeSelectionListener);
		treeMain.setShowPopMenuDefaultItem(false);

	}
    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
//        super.tblMain_tableClicked(e);
    	if(e.getClickCount() == 2){
    		return ;
    	}
    }

    /**
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
//        super.tblMain_tableSelectChanged(e);
    	tblDetail.removeRows();
    	checkSelected();
    	String id = tblMain.getCell(tblMain.getSelectManager().getActiveRowIndex(), "id").getValue().toString();
    	EntityViewInfo view  = new EntityViewInfo();
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add(new SelectorItemInfo("*"));
    	sic.add(new SelectorItemInfo("mainContract.*"));
    	sic.add(new SelectorItemInfo("contractType.*"));
    	sic.add(new SelectorItemInfo("contractSourceId.*"));
    	sic.add(new SelectorItemInfo("landDeveloper.*"));
    	sic.add(new SelectorItemInfo("partB.*"));
    	sic.add(new SelectorItemInfo("partC.*"));
    	sic.add(new SelectorItemInfo("period.*"));
    	view.setSelector(sic);
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("mainContract.id",id));
    	view.setFilter(filter);
    	
    	ContractBillCollection cols = ContractBillFactory.getRemoteInstance().getContractBillCollection(view);
    	if(cols.size()>0){
    		fillDetailTable(cols);
    	}else{
    		this.tblDetail.removeRows();
    	}
    }
    
    public void fillDetailTable(ContractBillCollection cols){
    	IRow row = null;
    	ContractBillInfo info  = null;
    	for(Iterator it = cols.iterator();it.hasNext();){
    		row = tblDetail.addRow();
    		info = (ContractBillInfo) it.next();
    		row.getCell("bookDate").setValue(info.getBookedDate());
    		row.getCell("id").setValue(info.getId());
    		row.getCell("hasSetted").setValue(Boolean.valueOf(info.isHasSettled()));
    		row.getCell("period.number").setValue(new Integer(info.getPeriod().getNumber()));
    		row.getCell("number").setValue(info.getNumber());
    		row.getCell("state").setValue(info.getState());
    		row.getCell("contractType.name").setValue(info.getContractType().getName());
    		row.getCell("name").setValue(info.getName());
    		row.getCell("originalAmount").setValue(info.getOriginalAmount());
    		row.getCell("amount").setValue(info.getAmount());
    		row.getCell("partB.name").setValue(info.getPartB().getName());
    		row.getCell("contractSource.name").setValue(info.getContractSourceId().getName());
    		row.getCell("signDate").setValue(info.getSignDate());
    		row.getCell("landDeveloper.name").setValue(info.getLandDeveloper().getName());
    		row.getCell("partC.name").setValue(info.getPartC()==null ?null:info.getPartC().getName());
    		row.getCell("costProperty").setValue(info.getCostProperty());
    		row.getCell("contractProperty").setValue(info.getContractPropert());
    		
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
     * output projectTree_valueChanged method
     */
    protected void projectTree_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
//        super.projectTree_valueChanged(e);
    	tblMain.removeRows();
    	tblDetail.removeRows();
    }

    /**
     * output typeTree_valueChanged method
     */
    protected void typeTree_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
//        super.typeTree_valueChanged(e);
    	   tblMain.removeRows();
    	   tblDetail.removeRows();
    }
    
    
    
    protected void getTypeIdSet(Set set,DefaultKingdeeTreeNode node){
    	if(node != null){
    		if(node.isLeaf()){
    			if(node.getUserObject() instanceof ContractTypeInfo){
    				ContractTypeInfo info = (ContractTypeInfo) node.getUserObject();
        			set.add(info.getId().toString());
    			}
    		}else{
    			for(int i=0;i<node.getChildCount();i++){
    				if(node.getUserObject() instanceof ContractTypeInfo){
    					set.add(((ContractTypeInfo)node.getUserObject()).getId().toString());
    				}
    				getTypeIdSet(set,(DefaultKingdeeTreeNode) node.getChildAt(i));
    			}
    		}
    	}
    }
    
    protected Set getAllSelectedTypeIds(){
    	Set set = new HashSet();
    	DefaultKingdeeTreeNode node = getSelectNode(typeTree);
    	if(node != null){
    		 getTypeIdSet(set, node);
    	}
    	return set;
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
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
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
    
    
    protected void tblDetail_tableClicked(KDTMouseEvent e) throws Exception {
    	// TODO Auto-generated method stub
        // super.tblDetail_tableClicked(e);
    	if(e.getClickCount() == 2){
    		String id = this.tblDetail.getCell(this.tblDetail.getSelectManager().getActiveRowIndex(), "id").getValue().toString();
    		viewContract(id);
    	}
    }
    
    protected void viewContract(String contractId){
    	UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.OWNER, this);
		uiContext.put(UIContext.ID, contractId);

		IUIFactory uiFactory;
		try {
			uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB);
			IUIWindow uiWindow = uiFactory.create(ContractFullInfoUI.class.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();
		} catch (UIException e) {
			handUIExceptionAndAbort(e);
		} 
    }
    
    protected ICoreBase getBizInterface() throws Exception {
    	// TODO Auto-generated method stub
    	return ContractBillFactory.getRemoteInstance();
    }
    
    
    
   protected String getSelectedKeyValue() {
	// TODO Auto-generated method stub
	return tblMain.getCell(this.tblMain.getSelectManager().getActiveRowIndex(), "id").getValue().toString();
}
   
   protected String getEditUIName() {
	// TODO Auto-generated method stub
	return ContractBillEditUI.class.getName();
}
   
   protected String getEditUIModal() {
	// TODO Auto-generated method stub
	return UIFactoryName.NEWTAB;
}
}