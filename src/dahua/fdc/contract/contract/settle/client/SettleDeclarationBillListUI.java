/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.settle.client;

import java.awt.Component;
import java.awt.event.*;
import java.sql.ResultSetMetaData;
import java.util.HashSet;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultTreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.base.commonquery.client.IPromptBoxFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.client.ContractBillEditUI;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.merch.common.KDTableHelper;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.tree.DefaultLNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ILNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ITreeBuilder;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class SettleDeclarationBillListUI extends AbstractSettleDeclarationBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(SettleDeclarationBillListUI.class);
    private FilterInfo mainFilterInfo = null;
    private int selectInvit = 0;
	protected FullOrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentAdminUnit().castToFullOrgUnitInfo();
    
    private int selectOrderIndex = 0;
    
    protected Set authorizedOrgs; 
    
    /**
     * output class constructor
     */
    public SettleDeclarationBillListUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
//    	tblMain.getDataRequestManager().setDataRequestMode(1);
    	
//    	tblMain.addKDTDataFillListener(new KDTDataFillListener() {
//    		public void afterDataFill(KDTDataRequestEvent e)
//    		{
//    			tblMain_afterDataFill(e);
//    		}
//    	});
    	super.onLoad();
    	initUI();
    	
    	buildProjectTree();
    	buildContractType();
    	this.tblMain.getGroupManager().setGroup(false);//设置是否分组
    	
    	
    	String fields[] = new String[kDTable1.getColumnCount()];
    	for(int i = 0; i < kDTable1.getColumnCount(); i++)
    	fields[i] = kDTable1.getColumnKey(i);
    	KDTableHelper.setSortedColumn(kDTable1, fields);
    	
//    	showQueryDate(this.kDTable1, "com.kingdee.eas.fdc.contract.app.ContractBillQuery",null);
    	
    }
    
    protected void initUI() throws Exception{
    	btnInTrial.setEnabled(true);
    	btnApproved.setEnabled(true);
    }
    
    public void buildProjectTree()throws Exception {//左，树
	    ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();
	    projectTreeBuilder.build(this, treeOrg, actionOnLoad);
	    treeOrg.setSelectionRow(0);
	}
    
    public void buildContractType()throws Exception {//左树
		KDTree treeMain = getContractTypeTree();

		ITreeBuilder treeBuilder = TreeBuilderFactory.createTreeBuilder(getLNTreeNodeCtrl(),
				getTreeInitialLevel(), getTreeExpandLevel());

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
		
		treeMain.setShowPopMenuDefaultItem(false);
	}
    
    private KDTree getContractTypeTree() {
		return kDTree1;
	}
    
    protected ILNTreeNodeCtrl getLNTreeNodeCtrl() throws Exception {
		return new DefaultLNTreeNodeCtrl(getTreeInterface());
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
    
    protected int getTreeInitialLevel() {
		return TreeBuilderFactory.DEFAULT_INITIAL_LEVEL;
	}

	protected int getTreeExpandLevel() {
		return TreeBuilderFactory.DEFAULT_EXPAND_LEVEL;
	}
	protected Object getRootObject() {
		return getRootName();
	}
	
	protected String getRootName() {
		return ContractClientUtils.getRes("allContractType");
	}
	
	protected boolean containConWithoutTxt(){
		return false;
	}
    
	
    public static int showQueryDate(KDTable tblMain,String queryName,EntityViewInfo evi) throws Exception
    {
    	tblMain.setEnabled(false);
    	tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
    	IMetaDataPK queryPK = new MetaDataPK(queryName);
    	IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(queryPK );
    	exec.option().isAutoIgnoreZero = true;
    	exec.option().isAutoTranslateBoolean = true;
    	exec.option().isAutoTranslateEnum = true; 
        exec.setObjectView(evi);
    	IRowSet rowSet = exec.executeQuery();
    	IRow row = null;
    	tblMain.removeRows();
    	int maxLevel = 0;
        for(int i=0; rowSet.next(); i++)
        {
        	row = tblMain.addRow();
        	ResultSetMetaData o_dbMd = rowSet.getMetaData();
        	int cols = o_dbMd.getColumnCount(); //取得query的字段数
        	Object[] colname = new Object[cols]; //取得query的字段名称
            for (int j = 1; j < cols+1; j++) {
              colname[j-1] = o_dbMd.getColumnName(j);
              if(colname[j-1]!=null && row.getCell(colname[j-1].toString())!=null
            		  && rowSet.getObject(colname[j-1].toString())!=null)
            		  row.getCell(colname[j-1].toString()).setValue(rowSet.getObject(colname[j-1].toString()));
            }
        }
        return maxLevel+1;
    }
    
    protected KDTable getBillListTable() {
        return kDTable1;
    }
    
    protected boolean isHasBillTable() {
        return true;
    }
    
    
    
    protected void kDTable1_tableSelectChanged(KDTSelectEvent e) throws Exception {
    	super.kDTable1_tableSelectChanged(e);
    	tblMain.removeRows();
    }
    
    protected void kDTable1_tableClicked(KDTMouseEvent e) throws Exception {
    	super.kDTable1_tableClicked(e);
    	if(e.getRowIndex()==-1||e.getClickCount()!=2)
    		return;
        String billId = UIRuleUtil.getString(this.kDTable1.getCell(e.getRowIndex(), "id").getValue());
        IUIWindow uiWindow = null;
		UIContext context = new UIContext(this);
		context.put("ID",billId);
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ContractBillEditUI.class.getName(), context, null, OprtState.VIEW);
		uiWindow.show();
    	
    }
    
    
    protected void treeOrg_valueChanged(TreeSelectionEvent e) throws Exception {
    	super.treeOrg_valueChanged(e);
    	refList();

    }
    
    protected void kDTree1_valueChanged(TreeSelectionEvent e) throws Exception {//合同类型
    	super.kDTree1_valueChanged(e);
    	refList();
    }
    
    protected void execQuery() {
    	super.execQuery();
    	
    	try {
			refList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.kDTable1.getSelectManager().select(selectInvit, selectInvit);
    }
    
    private void refList() throws Exception{
    	DefaultKingdeeTreeNode projectNode  = getProjSelectedTreeNode();
    	DefaultKingdeeTreeNode  typeNode  =	getTypeSelectedTreeNode() ;
    	
    	Object project  = null;
    	if(projectNode!=null)
    		project = projectNode.getUserObject();
    	
    	Object type  = null;
    	if(typeNode!=null)
    		type = typeNode.getUserObject();
    	
    	FilterInfo treeSelectFilter = getTreeSelectFilter(project,type, false);
    	
    	EntityViewInfo view = new EntityViewInfo();
    	view.setFilter(treeSelectFilter);
    	if(mainFilterInfo!=null)
    	{
    		if(view.getFilter()!=null)
    		{
    			view.getFilter().mergeFilter(mainFilterInfo, "and");
    		}
    		else
    		{
    			view.setFilter(mainFilterInfo);
    		}
    	}
    	showQueryDate(this.kDTable1, "com.kingdee.eas.fdc.contract.app.ContractBillQuery",view);
    	
    	if(this.kDTable1.getRowCount() > 0)
    	{
    		this.kDTable1.getSelectManager().select(0, 0);
    		btnAddNew.setEnabled(true);
    	} 
    	else
    		btnAddNew.setEnabled(false);
    	this.tblMain.removeRows();
    }
    
    /**
     * 根据选中项目树节点，获取过滤filter对象显示合同单据
     * */
    protected FilterInfo getTreeSelectFilter(Object projectNode, Object typeNode, boolean containConWithoutTxt)throws Exception
    {
		FilterInfo filter = getTreeSelectChangeFilter();
		FilterItemCollection filterItems = filter.getFilterItems();
		
		/*
		 * 工程项目树
		 */
		if (projectNode != null 	&& projectNode instanceof CoreBaseInfo) {

			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) projectNode;
			BOSUuid id = null;
			// 选择的是成本中心，取该成本中心及下级成本中心（如果有）下的所有合同
			if (projTreeNodeInfo instanceof OrgStructureInfo || projTreeNodeInfo instanceof FullOrgUnitInfo) {
				
				if (projTreeNodeInfo instanceof OrgStructureInfo) {
					id = ((OrgStructureInfo)projTreeNodeInfo).getUnit().getId();	
				}else{
					id = ((FullOrgUnitInfo)projTreeNodeInfo).getId();
				}				
				
				String orgUnitLongNumber = null;
				if(orgUnit!=null && id.toString().equals(orgUnit.getId().toString())){					
					orgUnitLongNumber = orgUnit.getLongNumber();
				}else{
					FullOrgUnitInfo orgUnitInfo = FullOrgUnitFactory.getRemoteInstance()
					.getFullOrgUnitInfo(new ObjectUuidPK(id));
					orgUnitLongNumber = orgUnitInfo.getLongNumber();
				}
				
				FilterInfo f = new FilterInfo();

				f.getFilterItems().add(new FilterItemInfo("orgUnit.isCostOrgUnit", Boolean.TRUE));//TODO
//				f.getFilterItems().add(new FilterItemInfo("orgUnit.id", authorizedOrgs,CompareType.INCLUDE));
				f.getFilterItems().add(new FilterItemInfo("orgUnit.longNumber", orgUnitLongNumber + "%", CompareType.LIKE));
				
				f.setMaskString("#0 and #1");
				
				if(filter!=null){
					filter.mergeFilter(f,"and");
				}
			}
			// 选择的是项目，取该项目及下级项目（如果有）下的所有合同
			else if (projTreeNodeInfo instanceof CurProjectInfo) {
				id = projTreeNodeInfo.getId();
				Set idSet = FDCClientUtils.genProjectIdSet(id);
				filterItems.add(new FilterItemInfo("curProject.id", idSet,CompareType.INCLUDE));
			}

		}

		FilterInfo typefilter =  new FilterInfo();
		FilterItemCollection typefilterItems = typefilter.getFilterItems();	
		/*
		 * 合同类型树
		 */
		if (typeNode != null&& typeNode instanceof TreeBaseInfo) {
			TreeBaseInfo typeTreeNodeInfo = (TreeBaseInfo)typeNode;
			BOSUuid id = typeTreeNodeInfo.getId();
			Set idSet = FDCClientUtils.genContractTypeIdSet(id);
			typefilterItems.add(new FilterItemInfo("contractType.id", idSet,CompareType.INCLUDE));
		}else if(containConWithoutTxt && typeNode != null &&typeNode.equals("allContract")){
			//如果包含无文本合同，查询所有时，让它查不到合同
			typefilterItems.add(new FilterItemInfo("contractType.id", "allContract"));
		}
		
		if (filter != null && typefilter != null) {
			filter.mergeFilter(typefilter, "and");
		}

		setIsAmtWithoutCostFilter(filter);
		
		return filter;
	}
    
	protected void setIsAmtWithoutCostFilter(FilterInfo filter) throws BOSException {
		// 以下代码默认不显示非独立计算的补充合同；在子类重载
		// 三方合同处理
		FilterInfo f = new FilterInfo();
		f.appendFilterItem("isAmtWithoutCost", null);
		f.appendFilterItem("isAmtWithoutCost", Boolean.FALSE);
		f.setMaskString("#0 or #1");
		filter.mergeFilter(f, "and");

	}
    
    protected FilterInfo getTreeSelectChangeFilter() {
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		Set set = getContractBillStateSet();
		filterItems.add(new FilterItemInfo("state", "4AUDITTED", CompareType.EQUALS));
		filterItems.add(new FilterItemInfo("curProject.isEnabled", Boolean.TRUE));
		return filter;
	}
    
    protected Set getContractBillStateSet() {
		Set set=new HashSet();
		set.add(FDCBillStateEnum.AUDITTED_VALUE);
		return set;
	}
    
    
    
    
    protected void prepareUIContext(UIContext uiContext, ActionEvent e) {//提前过滤界面
		super.prepareUIContext(uiContext, e);
		ItemAction act = getActionFromActionEvent(e);
		selectOrderIndex = this.kDTable1.getSelectManager().getActiveRowIndex();
		if (act.equals(actionAddNew)) {
			
			int selectIndex = this.kDTable1.getSelectManager().getActiveRowIndex();
			if(selectIndex==-1)
			{
				MsgBox.showWarning("请先选择合同列表！");
				SysUtil.abort();
			}
			String orderId = this.kDTable1.getCell(selectIndex, "id").getValue().toString();
			
			try {//查询需要的字段
//				if(PriceDataFactory.getRemoteInstance().exists("where strategicPact.id='"+orderId+"'"))
//				{
//					MsgBox.showWarning("已经存在当前所选战略协议的 价格库，不能新增！");
//					SysUtil.abort();
//				}
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add(new SelectorItemInfo("number"));
		        sic.add(new SelectorItemInfo("id"));
		        sic.add(new SelectorItemInfo("name"));
				uiContext.put("orderInfo", ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(orderId),sic));
			} catch (EASBizException e1) {
				e1.printStackTrace();
			} catch (BOSException e1) {
				e1.printStackTrace();
			}
		}
    }
    
    protected CommonQueryDialog initCommonQueryDialog() {//初始化查询功能
    	CommonQueryDialog dialog = new CommonQueryDialog();
        if(getUIWindow() == null)
            dialog.setOwner((Component)getUIContext().get("OwnerWindow"));
        else
            dialog.setOwner(this);
        dialog.setUiObject(this);
        dialog.setParentUIClassName(getMetaDataPK().getFullName());
        dialog.setQueryObjectPK(new MetaDataPK("com.kingdee.eas.fdc.contract.app", "ContractBillQuery"));
        dialog.setTitle(getUITitle() + " - " + EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Query_Filter"));
        if(orgContextManager != null)
        {
            if(orgContextManager.getOwner() != null && (orgContextManager.getOwner() instanceof CustomerQueryPanel))
                dialog.addUserPanel((CustomerQueryPanel)orgContextManager.getOwner());
            orgContextManager.init(getUIContext());
        }
        IPromptBoxFactory promptBoxFactory = getPromptBoxFactory();
        if(promptBoxFactory != null)
            dialog.setPromptBoxFactory(promptBoxFactory);
        dialog.setMaxReturnCountVisible(true);
        dialog.setVisibleTableCols(getQueryShowField());
        return dialog;
    }
    
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,EntityViewInfo viewInfo) {//选中战略协议过滤出价格库
    	EntityViewInfo colView = (EntityViewInfo)viewInfo.clone();
    	mainFilterInfo = colView.getFilter();
    	
    	
    	int selectIndex = this.kDTable1.getSelectManager().getActiveRowIndex();
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filInfo = new FilterInfo();
		if(selectIndex!=-1)
		{
			String orderId = this.kDTable1.getCell(selectIndex, "id").getValue().toString();
			filInfo.getFilterItems().add(new FilterItemInfo("contractNumber.id",orderId));
		}
		else
		{
			filInfo.getFilterItems().add(new FilterItemInfo("id","999"));
		}
    	
    	view.setFilter(filInfo);
    	return super.getQueryExecutor(queryPK, view);
    }
    
    
    
    
    
    
    
    
    public DefaultKingdeeTreeNode getProjSelectedTreeNode() {
        return (DefaultKingdeeTreeNode)treeOrg.getLastSelectedPathComponent();
    }
    public DefaultKingdeeTreeNode getTypeSelectedTreeNode() {
		return (DefaultKingdeeTreeNode) kDTree1.getLastSelectedPathComponent();
	}
    
    
    
    
    protected String getEditUIModal() {//打开窗口类型
    	return UIFactoryName.NEWTAB;
    }                                                               
    
    protected boolean isIgnoreCUFilter() {//去除CU过滤
    	return true;
    }
    

    public void storeFields()
    {
        super.storeFields();
    }
    
    
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
    }

    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
        super.tblMain_tableSelectChanged(e);
    }

    protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.menuItemImportData_actionPerformed(e);
    }
    
    public void actionInTrial_actionPerformed(ActionEvent e) throws Exception {//送审
    	super.actionInTrial_actionPerformed(e);
    }
    
    public void actionApproved_actionPerformed(ActionEvent e) throws Exception {//审定
    	super.actionApproved_actionPerformed(e);
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
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.contract.settle.SettleDeclarationBillFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.contract.settle.SettleDeclarationBillInfo objectValue = new com.kingdee.eas.fdc.contract.settle.SettleDeclarationBillInfo();
		
        return objectValue;
    }

}