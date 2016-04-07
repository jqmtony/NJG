/**
 * output package name
 */
package com.kingdee.eas.fdc.gcftbiaoa.client;

import java.awt.Component;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.ResultSetMetaData;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultTreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
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
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.base.commonquery.client.IPromptBoxFactory;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.aimcost.client.ForecastChangeVisEditUI;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.IContractBill;
import com.kingdee.eas.fdc.contract.client.ContractBillEditUI;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.gcftbiaoa.ConfirquantitesFactory;
import com.kingdee.eas.fdc.gcftbiaoa.ConfirquantitesInfo;
import com.kingdee.eas.fdc.gcftbiaoa.IConfirquantites;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.tree.DefaultLNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ILNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ITreeBuilder;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class ConfirquantitesListUI extends AbstractConfirquantitesListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ConfirquantitesListUI.class);
    //获取有权限的组织
	protected Set authorizedOrgs = null;
	protected FullOrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentAdminUnit().castToFullOrgUnitInfo();
	
    private FilterInfo mainFilterInfo = null;
    private IContractBill IContractBill = ContractBillFactory.getRemoteInstance();
    private IConfirquantites IForecastChangeVis = ConfirquantitesFactory.getRemoteInstance();
    
	private final static String CANTAUDIT = "cantAudit";

	private static final String CANTEDIT = "cantEdit";


	private final static String CANTUNAUDIT = "cantUnAudit";

	private final static String CANTAUDITEDITSTATE = "cantAuditEditState";

	private final static String CANTUNAUDITEDITSTATE = "cantUnAuditEditState";
    
    private int selectInvit = 0;
    /**
     * output class constructor
     */
    public ConfirquantitesListUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	buildProjectTree();
    	buildContractTypeTree();
    	tblMain.getColumn("version").getStyleAttributes().setHided(true);
    	actionModify.setEnabled(false);
    	
    	initButtonStatus();
    	initTable();
    }
    
    
	private void initButtonStatus() {
		
	}

	private void initTable(){
    	this.kDTree1.setSelectionRow(0);
    	this.kDTree1.expandRow(0);
    	this.kDTree2.setSelectionRow(0);
    	this.kDTree2.expandRow(0);
    	this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
    	this.kDTable1.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                	kDTable1_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
    	
    	this.kDTable1.addKDTSelectListener(new KDTSelectListener(){

			public void tableSelectChanged(KDTSelectEvent e) {
				try {
					kDTable1_tableSelectChanged(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
    	});
    }
	
    
    protected void initWorkButton() {
    	super.initWorkButton();
    	this.actionModify.setEnabled(true);
		this.btnAudit.setIcon(FDCClientHelper.ICON_AUDIT);
		this.btnUnAudit.setIcon(FDCClientHelper.ICON_UNAUDIT);
		this.btnModify.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_duizsetting"));
		
		
		ForecastChangeVisEditUI.setEnableAcion(new ItemAction[]{actionCreateTo,actionTraceDown,actionTraceUp,actionPrint,actionPrintPreview});
    }
    
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
    	ConfirquantitesInfo info = getSelectInfo();
    	if(!info.isIsLast()){
    		FDCMsgBox.showInfo("不是最新版，不能反审批！");
			this.abort();
    	}
    	checkBeforeAuditOrUnAudit(info,FDCBillStateEnum.AUDITTED, CANTUNAUDIT);
    	super.actionUnAudit_actionPerformed(e);
    	FDCClientUtils.showOprtOK(this);
		setSaveActionStatus(info);
    }
    
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
    	ConfirquantitesInfo info = getSelectInfo();
		checkBeforeAuditOrUnAudit(info,FDCBillStateEnum.SUBMITTED, CANTAUDIT);
    	super.actionAudit_actionPerformed(e);
    	FDCClientUtils.showOprtOK(this);
		setSaveActionStatus(info);
    }
    
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
    	ConfirquantitesInfo info = getSelectInfo();
    	checkBeforeEditOrRemove(info,CANTEDIT);
    	super.actionEdit_actionPerformed(e);
    	setSaveActionStatus(info);
    }
    
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
    	ConfirquantitesInfo info = getSelectInfo();
    	checkBeforeEditOrRemove(info,CANTEDIT);
    	super.actionRemove_actionPerformed(e);
    }
    
	private void setSaveActionStatus(ConfirquantitesInfo info) {
		try {
			refresh(null);
			this.kDTable1.getSelectManager().select(selectInvit, selectInvit);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ConfirquantitesInfo getSelectInfo() throws EASBizException, BOSException{
    	checkSelected();
    	String id = this.getSelectedKeyValue();
    	
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add("id");
    	sic.add("status");
    	sic.add("isLast");
    	return IForecastChangeVis.getConfirquantitesInfo(new ObjectUuidPK(id),sic);
    }
    
	protected void checkBeforeEditOrRemove(ConfirquantitesInfo info,String warning) throws BOSException {
		FDCClientUtils.checkBillInWorkflow(this,info.getId().toString());
		FDCBillStateEnum state = info.getStatus();
		if (state != null&& (state == FDCBillStateEnum.AUDITTING || state == FDCBillStateEnum.AUDITTED || state == FDCBillStateEnum.CANCEL )) {
			MsgBox.showWarning(this, ContractClientUtils.getRes(warning));
			SysUtil.abort();
		}
	}
	
	public void checkBeforeAuditOrUnAudit(ConfirquantitesInfo info,FDCBillStateEnum state, String warning) throws Exception{
		//检查单据是否在工作流中
		FDCClientUtils.checkBillInWorkflow(this,info.getId().toString());

		boolean b = info != null
				&& info.getStatus() != null
				&& info.getStatus().equals(state);
		if (!b) {
			MsgBox.showWarning(this, FDCClientUtils.getRes(warning));
			SysUtil.abort();
		}

	}
    
    protected void kDTree1_valueChanged(TreeSelectionEvent e) throws Exception {
    	super.kDTree1_valueChanged(e);
    	refList();
    }
    
    protected void kDTree2_valueChanged(TreeSelectionEvent e) throws Exception {
    	super.kDTree2_valueChanged(e);
    	refList();
    }
    
	public void buildProjectTree() throws Exception {
		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();
		projectTreeBuilder.build(this, kDTree2, actionOnLoad);
		
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
	
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,EntityViewInfo viewInfo) {
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
    	
    	if(view.getSorter()!=null&&view.getSorter().size()<2){
			viewInfo.getSorter().clear();
			SorterItemInfo version=new SorterItemInfo("version"); //createTime
			version.setSortType(SortType.DESCEND);
			view.getSorter().add(version);
		}
    	IQueryExecutor exec = super.getQueryExecutor(queryPK, view);
    	exec.option().isAutoTranslateBoolean = true;
        exec.option().isAutoTranslateEnum = true;
		return exec;
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
    
	public DefaultKingdeeTreeNode getProjSelectedTreeNode() {
		return (DefaultKingdeeTreeNode) kDTree2.getLastSelectedPathComponent();
	}

	public DefaultKingdeeTreeNode getTypeSelectedTreeNode() {
		return (DefaultKingdeeTreeNode) kDTree1.getLastSelectedPathComponent();
	}
	
	protected void tblMain_tableSelectChanged(KDTSelectEvent e)throws Exception {
		super.tblMain_tableSelectChanged(e);
	}
	
	protected void tblMain_tableClicked(KDTMouseEvent arg0) throws Exception {
		super.tblMain_tableClicked(arg0);
	}
	
	protected void kDTable1_tableClicked(KDTMouseEvent e) throws Exception {
    	if(e.getRowIndex()==-1||e.getClickCount()!=2)
    		return;
        String billId = UIRuleUtil.getString(this.kDTable1.getCell(e.getRowIndex(), "id").getValue());
        IUIWindow uiWindow = null;
		UIContext context = new UIContext(this);
		context.put("ID",billId);
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ContractBillEditUI.class.getName(), context, null, OprtState.VIEW);
		uiWindow.show();
	}
	
	protected void kDTable1_tableSelectChanged(KDTSelectEvent e)throws Exception {
		tblMain.removeRows();
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
    
    protected void prepareUIContext(UIContext uiContext, ActionEvent e) {//提前过滤界面
		super.prepareUIContext(uiContext, e);
		ItemAction act = getActionFromActionEvent(e);
		if (act.equals(actionAddNew)) {
			
			int selectIndex = this.kDTable1.getSelectManager().getActiveRowIndex();
			if(selectIndex==-1)
			{
				MsgBox.showWarning("请先选择合同！");
				SysUtil.abort();
			}
			String orderId = this.kDTable1.getCell(selectIndex, "id").getValue().toString();
			
			try {
//				if(ConfirquantitesFactory.getRemoteInstance().exists("select id where contractNumber.id='"+orderId+"'"))
//				{
//					MsgBox.showWarning("已存在工程量确认单，不允许新增！");SysUtil.abort();
//				}
//				
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add("*");
				sic.add("curProject.*");
				sic.add("partB.*");
				uiContext.put("contractInfo", IContractBill.getContractBillInfo(new ObjectUuidPK(orderId),sic));
			} catch (EASBizException e1) {
				e1.printStackTrace();
			} catch (BOSException e1) {  
				e1.printStackTrace();
			}
		}
		
		selectInvit = this.kDTable1.getSelectManager().getActiveRowIndex();
    }
	
    
    protected String getEditUIModal() {//打开窗口类型
    	return UIFactoryName.NEWTAB;
    }                                                               
    
    protected boolean isIgnoreCUFilter() {//去除CU过滤
    	return true;
    }
	
	protected FilterInfo getTreeSelectFilter(Object projectNode,Object  typeNode,boolean containConWithoutTxt) throws Exception {
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

				f.getFilterItems().add(new FilterItemInfo("orgUnit.isCostOrgUnit", Boolean.TRUE));
				f.getFilterItems().add(new FilterItemInfo("orgUnit.id", authorizedOrgs,CompareType.INCLUDE));
				f.getFilterItems().add(new FilterItemInfo("orgUnit.longNumber", orgUnitLongNumber + "%", CompareType.LIKE));
				
				f.setMaskString("#0 and #1 and #2");
				
				if(filter!=null){
					filter.mergeFilter(f,"and");
				}
			}
			// 选择的是项目，取该项目及下级项目（如果有）下的所有合同
			else if (projTreeNodeInfo instanceof CurProjectInfo) {
				id = projTreeNodeInfo.getId();
				Set idSet = FDCClientUtils.genProjectIdSet(id);
				filterItems.add(new FilterItemInfo("curProject.id", idSet,
						CompareType.INCLUDE));
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
	
	protected void buildContractTypeTree() throws Exception {
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
	
	protected boolean containConWithoutTxt(){
		return false;
	}
	
	protected Object getRootObject() {
		return getRootName();
	}
	
	protected String getRootName() {
		return ContractClientUtils.getRes("allContractType");
	}
	
	protected int getTreeInitialLevel() {
		return TreeBuilderFactory.DEFAULT_INITIAL_LEVEL;
	}

	protected int getTreeExpandLevel() {
		return TreeBuilderFactory.DEFAULT_EXPAND_LEVEL;
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
	
	private KDTree getContractTypeTree() {
		return kDTree1;
	}
    
//	public void actionModify_actionPerformed(ActionEvent e) throws Exception {
//		super.actionModify_actionPerformed(e);
//		checkSelected();
//		ConfirquantitesInfo info = getSelectorsForBillInfo(this.getSelectedKeyValue());
//		if(!info.getStatus().equals(FDCBillStateEnum.AUDITTED)){
//			FDCMsgBox.showWarning("单据未审批，不允许修订！");
//			return;
//		}
//		if(!info.isIsLast()){
//			FDCMsgBox.showWarning("不是最新版，不允许修订！");
//			return;
//		}
//		FilterInfo filter=new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("contractNumber.id",info.getContractNumber().getId()));
//		filter.getFilterItems().add(new FilterItemInfo("version",info.getVersion(),CompareType.GREATER));
//		if(ConfirquantitesFactory.getRemoteInstance().exists(filter)){
//			FDCMsgBox.showWarning(this,"单据已修订！");
//			return;
//		}
//		
//		UIContext uiContext = new UIContext(this);
//		uiContext.put("ForInfo", info);
//		uiContext.put("IsModify", true);
//		uiContext.put("verson", FDCHelper.add(info.getVersion(), BigDecimal.ONE));
//		
//		IUIWindow ui = UIFactory.createUIFactory(getEditUIModal()).create(getEditUIName(), uiContext, null,	OprtState.ADDNEW);
//		ui.show();
//	}
	
   public ConfirquantitesInfo getSelectorsForBillInfo(String id) throws EASBizException, BOSException {
    	SelectorItemCollection sic = new SelectorItemCollection();
    	String selectorAll = System.getProperty("selector.all");
		if(StringUtils.isEmpty(selectorAll)){
			selectorAll = "true";
		}
        sic.add(new SelectorItemInfo("isLast"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("creator.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("creator.id"));
        	sic.add(new SelectorItemInfo("creator.number"));
        	sic.add(new SelectorItemInfo("creator.name"));
		}
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("bizDate"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("auditor.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("auditor.id"));
        	sic.add(new SelectorItemInfo("auditor.number"));
        	sic.add(new SelectorItemInfo("auditor.name"));
		}
        sic.add(new SelectorItemInfo("version"));
        sic.add(new SelectorItemInfo("remake"));
        sic.add(new SelectorItemInfo("auditDate"));
        sic.add(new SelectorItemInfo("status"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("contractNumber.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("contractNumber.id"));
        	sic.add(new SelectorItemInfo("contractNumber.number"));
        	sic.add(new SelectorItemInfo("contractNumber.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("projrct.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("projrct.id"));
        	sic.add(new SelectorItemInfo("projrct.number"));
        	sic.add(new SelectorItemInfo("projrct.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("contractorUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("contractorUnit.id"));
        	sic.add(new SelectorItemInfo("contractorUnit.number"));
        	sic.add(new SelectorItemInfo("contractorUnit.name"));
		}
        sic.add(new SelectorItemInfo("endTime"));
        sic.add(new SelectorItemInfo("engineeringAudit"));
        sic.add(new SelectorItemInfo("costAudit"));
        sic.add(new SelectorItemInfo("projectFirstAudit"));
        sic.add(new SelectorItemInfo("working"));
        sic.add(new SelectorItemInfo("contractorPerosn"));
        sic.add(new SelectorItemInfo("engineeringPerosn"));
        sic.add(new SelectorItemInfo("costPerosn"));
        sic.add(new SelectorItemInfo("firstPerosn"));
    	return ConfirquantitesFactory.getRemoteInstance().getConfirquantitesInfo(new ObjectUuidPK(id),sic);
    }


    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.gcftbiaoa.ConfirquantitesFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.gcftbiaoa.ConfirquantitesInfo objectValue = new com.kingdee.eas.fdc.gcftbiaoa.ConfirquantitesInfo();
		
        return objectValue;
    }

}