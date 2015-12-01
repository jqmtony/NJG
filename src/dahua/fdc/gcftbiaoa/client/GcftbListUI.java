/**
 * output package name
 */
package com.kingdee.eas.fdc.gcftbiaoa.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.Action;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.aimcost.ForecastChangeVisFactory;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.gcftbiaoa.GcftbFactory;
import com.kingdee.eas.fdc.gcftbiaoa.GcftbInfo;
import com.kingdee.eas.fdc.gcftbiaoa.TreeNodeCollection;
import com.kingdee.eas.fdc.gcftbiaoa.TreeNodeFactory;
import com.kingdee.eas.fdc.gcftbiaoa.TreeNodeInfo;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class GcftbListUI extends AbstractGcftbListUI
{
    private static final Logger logger = CoreUIObject.getLogger(GcftbListUI.class);
    
	private final static String CANTUNAUDIT = "cantUnAudit";
	
	private final static String CANTAUDIT = "cantAudit";
	protected Set authorizedOrgs = null;
	private static final String CANTEDIT = "cantEdit";
	
	private String treeID = null;
	
	private boolean isFirst = false;
    
    /**
     * output class constructor
     */
    public GcftbListUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	buildCompanyTree();
    	initButton();
    	btnModify.setEnabled(true);
    }
    
    private void initButton(){
    	this.actionAddTree.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_evaluatecortrol"));
		this.actionDelTree.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_showparent"));
		this.actionEditTreeName.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_showparent"));
		this.actionAddTree.setEnabled(true);
		this.actionDelTree.setEnabled(true);
		this.actionEditTreeName.setEnabled(true);
		
		KDWorkButton btnAdd = (KDWorkButton)this.kDContainer2.add(actionAddTree);
		btnAdd.setText("新增");
		KDWorkButton btnEdit = (KDWorkButton)this.kDContainer2.add(actionEditTreeName);
		btnEdit.setText("修改");
		KDWorkButton btnDel = (KDWorkButton)this.kDContainer2.add(actionDelTree);
		btnDel.setText("删除");
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
        super.tblMain_tableSelectChanged(e);
    }

    /**
     * output menuItemImportData_actionPerformed method
     */
    protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.menuItemImportData_actionPerformed(e);
    }

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		 GcftbInfo info = getSelectInfo();
			checkBeforeAuditOrUnAudit(info,FDCBillStateEnum.SUBMITTED, CANTAUDIT);
	    	super.actionAudit_actionPerformed(e);
	    	FDCClientUtils.showOprtOK(this);
			setSaveActionStatus(info);
	}
	
	public void actionUnaudit_actionPerformed(ActionEvent e) throws Exception {
		GcftbInfo info = getSelectInfo();
		if(!info.isIsLast()){
			FDCMsgBox.showInfo("不是最新版，不能反审批！");
			this.abort();
		}
		checkBeforeAuditOrUnAudit(info,FDCBillStateEnum.AUDITTED, CANTUNAUDIT);
		super.actionUnaudit_actionPerformed(e);
		FDCClientUtils.showOprtOK(this);
		setSaveActionStatus(info);
	}

	private void setSaveActionStatus(GcftbInfo info) {
		try {
			refresh(null);
//			this.kDTable1.getSelectManager().select(selectInvit, selectInvit);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void checkBeforeAuditOrUnAudit(GcftbInfo info,FDCBillStateEnum auditted, String warning) {
		//检查单据是否在工作流中
		FDCClientUtils.checkBillInWorkflow(this,info.getId().toString());

		boolean b = info != null
				&& info.getStatus() != null
				&& info.getStatus().equals(auditted);
		if (!b) {
			MsgBox.showWarning(this, FDCClientUtils.getRes(warning));
			SysUtil.abort();
		}
		
	}
	

	private GcftbInfo getSelectInfo() throws EASBizException, BOSException {
		checkSelected();
    	String id = this.getSelectedKeyValue();
    	
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add("id");
    	sic.add("status");
    	sic.add("isLast");
    	return  GcftbFactory.getRemoteInstance().getGcftbInfo(new ObjectUuidPK(id),sic);
    
	}

    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
    	 GcftbInfo info = getSelectInfo();
    	checkBeforeEditOrRemove(info,CANTEDIT);
    	super.actionEdit_actionPerformed(e);
    	setSaveActionStatus(info);
    }
    

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
    	 GcftbInfo info = getSelectInfo();
    	checkBeforeEditOrRemove(info,CANTEDIT);
    	super.actionRemove_actionPerformed(e);
    }
	
	private void checkBeforeEditOrRemove(GcftbInfo info, String warning) {
		FDCClientUtils.checkBillInWorkflow(this,info.getId().toString());
		FDCBillStateEnum state = info.getStatus();
		if (state != null
				&& (state == FDCBillStateEnum.AUDITTING || state == FDCBillStateEnum.AUDITTED || state == FDCBillStateEnum.CANCEL )) {
			MsgBox.showWarning(this, ContractClientUtils.getRes(warning));
			SysUtil.abort();
		}
	}



	public void actionModify_actionPerformed(ActionEvent e) throws Exception {
		super.actionModify_actionPerformed(e);
		checkSelected();
		GcftbInfo info = getSelectorsForBillInfo(this.getSelectedKeyValue());
		if (!info.getStatus().equals(FDCBillStateEnum.AUDITTED)) {
			FDCMsgBox.showWarning("单据未审批，不允许修订！");
			return;
		}
		if (!info.isIsLast()) {
			FDCMsgBox.showWarning("不是最新版，不允许修订！");
			return;
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("engineeringProject.id", info.getEngineeringProject().getId()));
		filter.getFilterItems().add(new FilterItemInfo("Bbh", info.getBbh(),CompareType.GREATER));
		if (GcftbFactory.getRemoteInstance().exists(filter)) {
			FDCMsgBox.showWarning(this, "单据已修订！");
			return;
		}

		UIContext uiContext = new UIContext(this);
		uiContext.put("ForInfo", info);
		uiContext.put("IsModify", true);
		uiContext.put("Bbh", Integer.parseInt(info.getBbh())+1);

		IUIWindow ui = UIFactory.createUIFactory(getEditUIModal()).create(
				getEditUIName(), uiContext, null, OprtState.ADDNEW);
		ui.show();
	}
	
	private  GcftbInfo getSelectorsForBillInfo(String selectedKeyValue) throws EASBizException, BOSException {
		SelectorItemCollection sic = new SelectorItemCollection();
		String selectorAll = System.getProperty("selector.all");
		if(StringUtils.isEmpty(selectorAll)){
			selectorAll = "true";
		}
    	sic.add(new SelectorItemInfo("entrys.id"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entrys.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("entrys.startTime"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entrys.facilityName.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entrys.facilityName.id"));
			sic.add(new SelectorItemInfo("entrys.facilityName.name"));
        	sic.add(new SelectorItemInfo("entrys.facilityName.number"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entrys.proptreyRight.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entrys.proptreyRight.id"));
			sic.add(new SelectorItemInfo("entrys.proptreyRight.name"));
        	sic.add(new SelectorItemInfo("entrys.proptreyRight.number"));
		}
    	sic.add(new SelectorItemInfo("entrys.constructionArea"));
    	sic.add(new SelectorItemInfo("entrys.actualStartTine"));
    	sic.add(new SelectorItemInfo("entrys.completionTime"));
    	sic.add(new SelectorItemInfo("entrys.actualCompeltionTime"));
    	sic.add(new SelectorItemInfo("entrys.totalCost"));
    	sic.add(new SelectorItemInfo("entrys.costHasOccurred"));
    	sic.add(new SelectorItemInfo("entrys.allocationIndex"));
    	sic.add(new SelectorItemInfo("entrys.totalAmount"));
    	sic.add(new SelectorItemInfo("entrys.share"));
    	sic.add(new SelectorItemInfo("entrys.sharePrice"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entrys.engineeringProject.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entrys.engineeringProject.id"));
			sic.add(new SelectorItemInfo("entrys.engineeringProject.isEnabled"));
			sic.add(new SelectorItemInfo("entrys.engineeringProject.name"));
        	sic.add(new SelectorItemInfo("entrys.engineeringProject.number"));
		}
        sic.add(new SelectorItemInfo("isLast"));
    	sic.add(new SelectorItemInfo("entrys.Detail.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entrys.Detail.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entrys.Detail.id"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entrys.Detail.benefitProject.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entrys.Detail.benefitProject.id"));
			sic.add(new SelectorItemInfo("entrys.Detail.benefitProject.name"));
        	sic.add(new SelectorItemInfo("entrys.Detail.benefitProject.number"));
		}
    	sic.add(new SelectorItemInfo("entrys.Detail.allocationBase"));
    	sic.add(new SelectorItemInfo("entrys.Detail.shareAmount"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("gsmc"));
        sic.add(new SelectorItemInfo("bbh"));
        sic.add(new SelectorItemInfo("status"));
        sic.add(new SelectorItemInfo("AuditDate"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("engineeringProject.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("engineeringProject.id"));
        	sic.add(new SelectorItemInfo("engineeringProject.number"));
        	sic.add(new SelectorItemInfo("engineeringProject.name"));
		}
		return GcftbFactory.getRemoteInstance().getGcftbInfo(new ObjectUuidPK(selectedKeyValue), sic);
	}
	
	private void buildCompanyTree() throws Exception{
		String curOrgId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
		TreeModel orgTreeModel = NewOrgUtils.getTreeModel(OrgViewType.COMPANY,"", curOrgId, null, getActionPK(actionOnLoad));
		
		Map<String,Set<TreeNode>> zdyNode = new HashMap<String, Set<TreeNode>>();
		
		String oql = "select id,number,name,company.id,company.name,company.numner where company.id is not null";
		TreeNodeCollection treeNodeCollection = TreeNodeFactory.getRemoteInstance().getTreeNodeCollection(oql);
		for (int i = 0; i < treeNodeCollection.size(); i++) {
			TreeNodeInfo treeNodeInfo = treeNodeCollection.get(i);
			KDTreeNode sourceNode = new KDTreeNode(treeNodeInfo);
			sourceNode.setText(treeNodeInfo.getName());

			Set<TreeNode> set = zdyNode.get(treeNodeInfo.getCompany().getId().toString());
			if(set!=null){
				set.add(sourceNode);
			}else{
				set = new HashSet<TreeNode>();
				set.add(sourceNode);
			}
			zdyNode.put(treeNodeInfo.getCompany().getId().toString(), set);
		}
		
		DefaultKingdeeTreeNode rootNode = (DefaultKingdeeTreeNode) orgTreeModel.getRoot();
		addSechNode(rootNode, zdyNode);
		
		this.kDTree1.setModel(orgTreeModel);
		
		isFirst = true;
		if(treeID!=null)
			SelectSechNode((DefaultKingdeeTreeNode) orgTreeModel.getRoot());
	}
	
	private void SelectSechNode(DefaultKingdeeTreeNode node) {
		for (int i = 0; i < node.getChildCount(); i++) {
			DefaultKingdeeTreeNode curNode = (DefaultKingdeeTreeNode) node.getChildAt(i);
			
			Object obj = curNode.getUserObject();
			if(obj==null )
				continue;
			if( obj instanceof TreeNodeInfo ){
				TreeNodeInfo treeNodeInfo = (TreeNodeInfo)curNode.getUserObject();
				
				if(treeNodeInfo.getId().toString().equals(treeID)){
					kDTree1.setSelectionNode(curNode);
					break;
				}
			}
			if (curNode.getChildCount() > 0) {
				SelectSechNode(curNode);
			}
		}
	}
	
	private void addSechNode(DefaultKingdeeTreeNode node,Map<String,Set<TreeNode>> zdyNode) {
		if(node.getChildCount()==0){
			addNode(node, zdyNode);
		}
		for (int i = 0; i < node.getChildCount(); i++) {
			DefaultKingdeeTreeNode curNode = (DefaultKingdeeTreeNode) node.getChildAt(i);
			
			Object obj = curNode.getUserObject();
			if(obj==null )
				continue;
			if( obj instanceof OrgStructureInfo ){
				addNode(curNode, zdyNode);
			}
			if (curNode.getChildCount() > 0) {
				addSechNode(curNode,zdyNode);
			}
		}
	}

	private void addNode(DefaultKingdeeTreeNode node,Map<String,Set<TreeNode>> zdyNode){
		if( node.getUserObject() instanceof OrgStructureInfo ){
			String orgId = ((OrgStructureInfo)node.getUserObject()).getUnit().getId().toString();
			Set<TreeNode> treeNodeSet = zdyNode.get(orgId);
			if(treeNodeSet!=null){
				Iterator<TreeNode> iterator = treeNodeSet.iterator();
				while(iterator.hasNext()){
					node.insert((MutableTreeNode) iterator.next(), 0);
				}
			}
		}
	}
	
	private IMetaDataPK getActionPK(ItemAction action) {
		if (action == null) {
			return null;
		}
		String actoinName = action.getClass().getName();
		if (actoinName.indexOf("$") >= 0) {
			actoinName = actoinName.substring(actoinName.indexOf("$") + 1);
		}

		return new MetaDataPK(actoinName);
	}

	protected void kDTree1_valueChanged(TreeSelectionEvent e) throws Exception {
		super.kDTree1_valueChanged(e);
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)kDTree1.getLastSelectedPathComponent();
		TreeNodeInfo treeNodeInfo = null;
		if (treeNode == null || !(treeNode.getUserObject() instanceof TreeNodeInfo)){
			if(isFirst)
				treeID  = null;
		}else{
			treeNodeInfo = ((TreeNodeInfo)treeNode.getUserObject());
			treeID = treeNodeInfo.getId().toString();
		}
		this.tblMain.removeRows();
		isFirst = false;
		
	}
	
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,EntityViewInfo viewInfo) {
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)kDTree1.getLastSelectedPathComponent();
		TreeNodeInfo treeNodeInfo = null;
		String nodeId = "";
		if (treeNode == null || !(treeNode.getUserObject() instanceof TreeNodeInfo)){
			nodeId  = null;
		}else{
			treeNodeInfo = ((TreeNodeInfo)treeNode.getUserObject());
			nodeId = treeNodeInfo.getId().toString();
		}
		FilterInfo filInfo = new FilterInfo();
		if(nodeId==null)
			filInfo.getFilterItems().add(new FilterItemInfo("engineeringProject.id","9999"));
		else
			filInfo.getFilterItems().add(new FilterItemInfo("engineeringProject.id",treeID));
		
		EntityViewInfo view = (EntityViewInfo)viewInfo.clone();
		if(view.getFilter()!=null){
			try {
				view.getFilter().mergeFilter(filInfo, "and");
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}else{
			view.setFilter(filInfo);
		}
		return super.getQueryExecutor(queryPK, view);
	}
	
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	
	public void actionAddTree_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddTree_actionPerformed(e);
		UIContext uiContext = new UIContext(this); 
		uiContext.put("companyInfo",getSelectCompanyInfoNode());
		UIFactory.createUIFactory(UIFactoryName.MODEL).create(TreeNodeEditUI.class.getName(),uiContext, null , "ADDNEW").show(); 
		buildCompanyTree();
	}
	
	public void actionEditTreeName_actionPerformed(ActionEvent e)throws Exception {
		super.actionEditTreeName_actionPerformed(e);
		TreeNodeInfo ZDYInfoNode = getSelectZDYInfoNode(); 
		UIContext uiContext = new UIContext(this); 
		uiContext.put("ID",ZDYInfoNode.getId());
		UIFactory.createUIFactory(UIFactoryName.MODEL).create(TreeNodeEditUI.class.getName(),uiContext, null , "EDIT").show(); 
		buildCompanyTree();
	}
	
	public void actionDelTree_actionPerformed(ActionEvent e) throws Exception {
		super.actionDelTree_actionPerformed(e);
		TreeNodeInfo ZDYInfoNode = getSelectZDYInfoNode();
		
		FilterInfo filInfo = new FilterInfo();
		filInfo.getFilterItems().add(new FilterItemInfo("engineeringProject.id",ZDYInfoNode.getId()));
		if(GcftbFactory.getRemoteInstance().exists(filInfo)){
			FDCMsgBox.showWarning("已有关联数据，不能删除！");
			SysUtil.abort();
		}
		TreeNodeFactory.getRemoteInstance().delete(new ObjectUuidPK(ZDYInfoNode.getId()));
		
		buildCompanyTree();
	}
	
	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}
	
	private CompanyOrgUnitInfo getSelectCompanyInfoNode() throws EASBizException, BOSException{
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)kDTree1.getLastSelectedPathComponent();
		CompanyOrgUnitInfo companyInfo = null;
		if (treeNode == null){
			FDCMsgBox.showWarning("请先选择末级节点公司！");
			SysUtil.abort();
		}
		
		if(!(treeNode.getUserObject() instanceof OrgStructureInfo)){
			FDCMsgBox.showWarning("请先选择末级节点公司！");
			SysUtil.abort();
		}else{
			OrgStructureInfo stInfo = (OrgStructureInfo) treeNode.getUserObject();
			
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("id");
			sic.add("number");
			sic.add("name");
			sic.add("isLeaf");
			companyInfo = CompanyOrgUnitFactory.getRemoteInstance().getCompanyOrgUnitInfo(new ObjectUuidPK(stInfo.getUnit().getId()),sic);
			if(!companyInfo.isIsLeaf()){
				FDCMsgBox.showWarning("请先选择末级节点公司！");
				SysUtil.abort();
			} 
		}
		return companyInfo;
	}
	
	private TreeNodeInfo getSelectZDYInfoNode(){
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)kDTree1.getLastSelectedPathComponent();
		TreeNodeInfo treeNodeInfo = null;
		if (treeNode == null){
			FDCMsgBox.showWarning("请选择非公司节点！");
			SysUtil.abort();
		}
		
		if(!(treeNode.getUserObject() instanceof TreeNodeInfo)){
			FDCMsgBox.showWarning("请选择非公司节点！");
			SysUtil.abort();
		}else{
			treeNodeInfo = (TreeNodeInfo) treeNode.getUserObject();
		}
		return treeNodeInfo;
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {// 提前过滤界面
		super.prepareUIContext(uiContext, e);
		ItemAction act = getActionFromActionEvent(e);
		if (act.equals(actionAddNew)) {

			DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)kDTree1.getLastSelectedPathComponent();
			TreeNodeInfo treeNodeInfo = null;
			if (treeNode == null){
				FDCMsgBox.showWarning("请选择非公司节点！");
				SysUtil.abort();
			}
			
			if(!(treeNode.getUserObject() instanceof TreeNodeInfo)){
				FDCMsgBox.showWarning("请选择非公司节点！");
				SysUtil.abort();
			}else{
				treeNodeInfo = (TreeNodeInfo) treeNode.getUserObject();
			}
			
			FilterInfo filInfo = new FilterInfo();
			filInfo.getFilterItems().add(new FilterItemInfo("engineeringProject.id",treeNodeInfo.getId()));
			try {
				if(GcftbFactory.getRemoteInstance().exists(filInfo)){
					FDCMsgBox.showWarning("请进行修订操作！");
					SysUtil.abort();
				}
			} catch (EASBizException e1) {
				e1.printStackTrace();
			} catch (BOSException e1) {
				e1.printStackTrace();
			}  
			uiContext.put("treeNodeInfo", treeNodeInfo);
		}
	}

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.gcftbiaoa.GcftbFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.gcftbiaoa.GcftbInfo objectValue = new com.kingdee.eas.fdc.gcftbiaoa.GcftbInfo();
		
        return objectValue;
    }

}