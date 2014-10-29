/**
 * output package name
 */
package com.kingdee.eas.port.markesupplier.subill.client;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.QueryInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.base.attachment.util.UICreator;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.framework.client.DListClientControlStrategy;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.master.cssp.client.F7SupplierSimpleSelector;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.basedata.org.PurchaseOrgUnitFactory;
import com.kingdee.eas.basedata.org.PurchaseOrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.cp.ba.client.LongTimeDialog;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.tree.DefaultLNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ITreeBuilder;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;
import com.kingdee.eas.port.markesupplier.subase.SupplierInvoiceTypeTreeCollection;
import com.kingdee.eas.port.markesupplier.subase.SupplierInvoiceTypeTreeFactory;
import com.kingdee.eas.port.markesupplier.subase.SupplierInvoiceTypeTreeInfo;
import com.kingdee.eas.port.markesupplier.subase.SupplierState;
import com.kingdee.eas.port.markesupplier.subase.SupplierTypeInfo;
import com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStock;
import com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockFactory;
import com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class MarketSupplierStockListUI extends AbstractMarketSupplierStockListUI
{
    private static final Logger logger = CoreUIObject.getLogger(MarketSupplierStockListUI.class);
    
    /**
     * output class constructor
     */
    public MarketSupplierStockListUI() throws Exception
    {
        super();
    }

    protected MetaDataPK getQueryPKFromEntity()throws Exception{
		String queryName = "com.kingdee.eas.basedata.master.cssp.app.F7SupplierQueryForDefaultAssign";
		MetaDataPK queryPK = new MetaDataPK(queryName);
		return queryPK;
	}
	QueryInfo qiQuery=null;
	protected QueryInfo getQueryInfoFromQueryPK()throws Exception{
		if(qiQuery == null)
			qiQuery = MetaDataLoaderFactory.getRemoteMetaDataLoader().getQuery(getQueryPKFromEntity());
		return qiQuery;
	}
	String sqlQuery=null;
	protected String getQuerySqlFromQueryPK()throws Exception{
		 if(sqlQuery == null){
			 IQueryExecutor qe = QueryExecutorFactory.getRemoteInstance(getQueryPKFromEntity());
			 sqlQuery = qe.getSQL();
		 }
		 return sqlQuery;
	}
	protected DListClientControlStrategy cStrategy;
    public void actionBathAssign_actionPerformed(ActionEvent e)
    		throws Exception {
    	checkSelected();
		Set supplierId=new HashSet();
		supplierId.add("999999999999");
		ArrayList id = getSelectedIdValues();
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("sysSupplier.id");
		for(int i = 0; i < id.size(); i++){
			MarketSupplierStockInfo info=MarketSupplierStockFactory.getRemoteInstance().getMarketSupplierStockInfo(new ObjectUuidPK(id.get(i).toString()),sel);
			if(info.getSysSupplier()!=null)
				supplierId.add(info.getSysSupplier().getId().toString());
		}
		
		UIContext uictx = new UIContext(this);
		uictx.put("QueryPK", getQueryPKFromEntity());
		uictx.put("pkQueryOnlySltID", null);
		uictx.put("QueryInfo", getQueryInfoFromQueryPK());
		uictx.put("QuerySql", getQuerySqlFromQueryPK());
		uictx.put("QuerySqlOnlySltID", null);
		uictx.put("BizInterface", SupplierFactory.getRemoteInstanceWithObjectContext(getMainOrgContext()));
		uictx.put("CurrentCtrlUnit", SysContext.getSysContext().getCurrentCtrlUnit());
		uictx.put("isSubordinateAssign", true);
		uictx.put("isReferDataBaseDChkBoxVisble", true);
		uictx.put("bosType", new SupplierInfo().getBOSType().toString());
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",supplierId,CompareType.INCLUDE));
		uictx.put("defaultDataFilter", filter);
		String uimode = UIFactoryName.MODEL;
		UICreator.create(com.kingdee.eas.basedata.master.cssp.client.SupplierAssignmentUI.class.getName(), uimode, uictx);
	}
    
    protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		DefaultKingdeeTreeNode TypeNode = this.getSelectedTreeNode(supplierTypeTree);
    	DefaultKingdeeTreeNode OrgNode = this.getSelectedTreeNode(orgTree);
		if(TypeNode!=null&&TypeNode.getUserObject()!=null){
			if(TypeNode.getUserObject() instanceof SupplierInvoiceTypeTreeInfo){
				uiContext.put("type", TypeNode.getUserObject());
			}else{
				uiContext.put("type", null);
			}
		}
		if(OrgNode!=null&&OrgNode.getUserObject()!=null){
			if(OrgNode.getUserObject() instanceof OrgStructureInfo){
				uiContext.put("org", OrgNode.getUserObject());
			}else{
				uiContext.put("org", null);
			}
		}
	}
    
    protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}
    
    protected DefaultKingdeeTreeNode getSelectedTreeNode(KDTree selectTree) {
		if (selectTree.getLastSelectedPathComponent() != null) {
			DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode) selectTree.getLastSelectedPathComponent();
			return treeNode;
		}
		return null;
	}
    
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();
    	List idList = this.getSelectedIdValues();
    	for (int i = 0; i < idList.size(); i++) {
    		MarketSupplierStockInfo info=MarketSupplierStockFactory.getRemoteInstance().getMarketSupplierStockInfo(new ObjectUuidPK(idList.get(i).toString()));
    		if(info.getState().equals(SupplierState.audit) ){
				MsgBox.showWarning(this,"�����Ѻ�׼�������޸ģ�");
				SysUtil.abort();
			}
		}
        super.actionEdit_actionPerformed(e);
    }
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();
    	List idList = this.getSelectedIdValues();
    	for (int i = 0; i < idList.size(); i++) {
    		MarketSupplierStockInfo info=MarketSupplierStockFactory.getRemoteInstance().getMarketSupplierStockInfo(new ObjectUuidPK(idList.get(i).toString()));
			if(info.getState().equals(SupplierState.audit) ){
				MsgBox.showWarning(this,"�����Ѻ�׼������ɾ����");
				SysUtil.abort();
			}
		}
        super.actionRemove_actionPerformed(e);
    }
    public void onLoad() throws Exception {
		super.onLoad();
		buildOrgTree();
		buildInviteTypeTree();
		
		this.btnMultiSubmit.setIcon(EASResource.getIcon("imgTbtn_submit"));
		this.btnAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
		this.btnunaudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
		this.btnBathAssign.setIcon(EASResource.getIcon("imgTbtn_distribute"));
		this.btnEditLevel.setIcon(EASResource.getIcon("imgTbtn_rename"));
		this.btnMultiSubmit.setEnabled(true);
		this.btnAudit.setEnabled(true);
		this.btnunaudit.setEnabled(true);
		this.btnBathAssign.setEnabled(true);
		this.btnEditLevel.setEnabled(true);
		this.actionCreateTo.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCreateTo.setVisible(false);
//		this.actionAuditResult.setVisible(false);//��������鿴
		this.actionMultiapprove.setVisible(false);//�༶����
//		this.actionNextPerson.setVisible(false);//��һ��������
		this.actionTraceDown.setVisible(false);//����ͼ
//		this.actionWorkFlowG.setVisible(false);//����ͼ
		
		this.btnEditLevel.setText("���������ݹ�Ӧ��");
		this.btnEditLevel.setToolTipText("���������ݹ�Ӧ��");
		
		
        this.orgTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                try {
                    orgTree_valueChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDScrollPane2
        // supplierTypeTree
        this.supplierTypeTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                try {
                    kDSupplierTypeTree_valueChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
    }
    
	/**
	 * �ɹ������
	 */
	protected void kDSupplierTypeTree_valueChanged(TreeSelectionEvent e)
			throws Exception {
		DefaultKingdeeTreeNode TypeNode = this.getSelectedTreeNode(supplierTypeTree);
    	if(TypeNode != null && TypeNode.getUserObject() != null){
    		//����������title
    		kDSupplierCont.setTitle(TypeNode.getText());
    	}
    	this.refresh(null);
	}
	protected void buildOrgTree() throws Exception{
		OrgUnitInfo cuInfo = SysContext.getSysContext().getCurrentOrgUnit();
		if (!cuInfo.isIsPurchaseOrgUnit()) {
			MsgBox.showInfo(this, "�ǲɹ���֯���ܲ���");
			SysUtil.abort();
		}
		TreeModel orgTreeModel = NewOrgUtils.getTreeModel(OrgViewType.PURCHASE,"", cuInfo.getId().toString(), null, getActionPK(actionOnLoad));
		this.orgTree.setModel(orgTreeModel);
		this.orgTree.setSelectionRow(0);
	}
	protected void refresh(ActionEvent e) throws Exception{
		this.tblMain.removeRows();
	}
	
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList arrayid = getSelectedIdValues();
		IMarketSupplierStock imarketSupplier = (IMarketSupplierStock)getBizInterface();
		StringBuffer msg = new StringBuffer();
		for (int i = 0; i < arrayid.size(); i++) 
		{
			String id = (String) arrayid.get(i);
			MarketSupplierStockInfo Info = imarketSupplier.getMarketSupplierStockInfo(new ObjectUuidPK(id));
			if(!Info.getState().equals(SupplierState.submit))
			{
				if(!"".equals(msg.toString().trim()))
					msg.append(","+Info.getNumber());
				else
					msg.append(Info.getNumber());
			}
			else
			{
				imarketSupplier.audit(Info);
			}
		}
		if(!"".equals(msg.toString().trim()))
			MsgBox.showWarning("�������ݺ�׼ʧ��,���:\n"+msg.toString());
		else
			MsgBox.showInfo("�����ɹ���");
		refresh(e);
	}
	
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList arrayid = getSelectedIdValues();
		IMarketSupplierStock imarketSupplier = (IMarketSupplierStock)getBizInterface();
		StringBuffer msg = new StringBuffer();
		for (int i = 0; i < arrayid.size(); i++) 
		{
			String id = (String) arrayid.get(i);
			MarketSupplierStockInfo Info = imarketSupplier.getMarketSupplierStockInfo(new ObjectUuidPK(id));
			if(!Info.getState().equals(SupplierState.audit))
			{
				if(!"".equals(msg.toString().trim()))
					msg.append(","+Info.getNumber());
				else
					msg.append(Info.getNumber());
			}
			else
			{
				imarketSupplier.unAudit(Info);
			}
		}
		if(!"".equals(msg.toString().trim()))
			MsgBox.showWarning("�������ݷ���׼ʧ��,���:\n"+msg.toString());
		else
			MsgBox.showInfo("�����ɹ���");
		refresh(e);
	}
	
	/**
	 * �õ�action��PK
	 *
	 * @param action
	 * @return
	 */
	public static IMetaDataPK getActionPK(ItemAction action) {
		if (action == null) {
			return null;
		}
		String actoinName = action.getClass().getName();
		if (actoinName.indexOf("$") >= 0) {
			actoinName = actoinName.substring(actoinName.indexOf("$") + 1);
		}

		return new MetaDataPK(actoinName);
	}
	/**
	 * ��֯��
	 */
	protected void orgTree_valueChanged(TreeSelectionEvent e) throws Exception {
		this.refresh(null);
	}
	
	protected void buildInviteTypeTree() throws Exception {
		FilterInfo filter = new FilterInfo();
		TreeModel model = createDataTree(SupplierInvoiceTypeTreeFactory.getRemoteInstance(), filter, "��Ӧ�����");
		this.supplierTypeTree.setModel(model);
		this.supplierTypeTree.setSelectionRow(0);
	}
	
	/**
	 * ����ͨ����,������ڵ�����
	 */
	public static TreeModel createDataTree(ITreeBase iTree, FilterInfo filter,
			String rootName) throws Exception {
		KDTree tree = new KDTree();
		if (rootName == null) {
			rootName = MetaDataLoaderFactory.getRemoteMetaDataLoader()
					.getBusinessObject(iTree.getType()).getAlias();
		}
		KDTreeNode root = new KDTreeNode(rootName);
		((DefaultTreeModel) tree.getModel()).setRoot(root);
		ITreeBuilder treeBuilder = TreeBuilderFactory.createTreeBuilder(
				new DefaultLNTreeNodeCtrl(iTree), 10, 10, filter);
		treeBuilder.buildTree(tree);
		return tree.getModel();
	}
	
	private FilterInfo getTreeFilter() throws Exception {
		FilterInfo filter = new FilterInfo();
    	FilterItemCollection filterItems = filter.getFilterItems();
    	DefaultKingdeeTreeNode TypeNode = this.getSelectedTreeNode(supplierTypeTree);
    	DefaultKingdeeTreeNode orgNode = this.getSelectedTreeNode(orgTree);
    	Object TypeInfo = null;
    	String orgName = null;
    	//�Ƿ�ѡ��
    	if(TypeNode != null && TypeNode.getUserObject() != null){
    		TypeInfo = TypeNode.getUserObject();
    	}
    	if (TypeInfo instanceof SupplierTypeInfo) {
		    String longNumber = ((SupplierInvoiceTypeTreeInfo)TypeInfo).getLongNumber();
			filterItems.add(new FilterItemInfo("inviteType.longNumber", longNumber+"%",CompareType.LIKE));
		}
    	if(orgNode.getUserObject() instanceof OrgStructureInfo){
    		SelectorItemCollection sel=new SelectorItemCollection();
    		sel.add("longNumber");
    		PurchaseOrgUnitInfo org=PurchaseOrgUnitFactory.getRemoteInstance().getPurchaseOrgUnitInfo(new ObjectUuidPK(((OrgStructureInfo)orgNode.getUserObject()).getUnit().getId()),sel);
    		String longNumber=org.getLongNumber();
    		filterItems.add(new FilterItemInfo("purchaseOrgUnit.longNumber", longNumber+"%",CompareType.LIKE));
    	}
    	return filter;
	}
	
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,EntityViewInfo viewInfo) {
		try {
			FilterInfo filter = getTreeFilter();
			if(filter==null){
				filter = new FilterInfo();
			}
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			if (viewInfo.getFilter() != null)
			{
				viewInfo.getFilter().mergeFilter(filter, "and");
				
			} else
			{
				viewInfo.setFilter(filter);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	/**
	 * �����ύ
	 * @param e
	 * @throws Exception
	 */
	public void actionMultiSubmit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		Window win = SwingUtilities.getWindowAncestor(this);
		LongTimeDialog dialog = null;
		if(win instanceof Frame)
			dialog = new LongTimeDialog((Frame)win);
		else
			if(win instanceof Dialog)
				dialog = new LongTimeDialog((Dialog)win);
		dialog.setLongTimeTask(new ILongTimeTask() {
			public Object exec()throws Exception{
				ArrayList id = getSelectedIdValues();
				for(int i = 0; i < id.size(); i++){
					UIContext uiContext = new UIContext(this);
    			    uiContext.put("ID", id.get(i).toString());
    			    MarketSupplierStockEditUI ui=(MarketSupplierStockEditUI) UIFactoryHelper.initUIObject(MarketSupplierStockEditUI.class.getName(), uiContext, null,OprtState.EDIT);
    			    SupplierState state = MarketSupplierStockFactory.getRemoteInstance().getMarketSupplierStockInfo(new ObjectUuidPK(((MarketSupplierStockInfo)ui.getEditData()).getId())).getState();
    			    checkBillInWorkflow(ui, ui.getEditData().getId().toString());
    				
    			    if(state==null||!(SupplierState.Save.equals(state)||SupplierState.submit.equals(state))){
    			    	MsgBox.showWarning("���Ǳ�������ύ״̬�����ܽ����ύ������");
    					SysUtil.abort();
    			    }
    			    ui.verifyInputForSubmit();
    			    ui.runSubmit();
    			    ui.destroyWindow();
    		    }
                return new Boolean(true);
            }
            public void afterExec(Object result)throws Exception{
            	MsgBox.showWarning("�����ɹ���");
            }
       }
	);
		dialog.show();
		this.refresh(null);
	}
	
	public void actionEditLevel_actionPerformed(ActionEvent e) throws Exception {
//		checkSelected();
//		ArrayList id = getSelectedIdValues();
//		UIContext uiContext = new UIContext(this);
//		uiContext.put("id", id);
//		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(SupplierLevelUI.class.getName(), uiContext, null, OprtState.VIEW);
//		uiWindow.show();
//		
//		this.refresh(null);
		
		KDBizPromptBox suppBox = new KDBizPromptBox();
		suppBox.setQueryInfo("com.kingdee.eas.port.pm.invite.app.JudgesExamineQuery");
		F7SupplierSimpleSelector f7supplier = new F7SupplierSimpleSelector(this,suppBox);
		suppBox.setSelector(f7supplier);
		suppBox.setDataBySelector();
		
		SupplierInfo obj = (SupplierInfo)suppBox.getData();
		
		if(obj==null)
			return;
		
		SupplierInfo supplierInfo = SupplierFactory.getRemoteInstance().getSupplierInfo(new ObjectUuidPK(obj.getId()));
		
		
		PurchaseOrgUnitInfo org = SysContext.getSysContext().getCurrentPurchaseUnit();
		
		String oql = "select id where supplierName='"+supplierInfo.getName()+"' and PurchaseOrgUnit.id='"+org.getId()+"'";
		if(MarketSupplierStockFactory.getRemoteInstance().exists(oql))
		{
			MsgBox.showWarning("��ǰ��Ӧ�����ڡ�"+org.getName()+"�����ڣ�����Ҫ���룡");
		}
		
		SupplierInvoiceTypeTreeCollection treeInfo = SupplierInvoiceTypeTreeFactory.getRemoteInstance().getSupplierInvoiceTypeTreeCollection();
		ICodingRuleManager codingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
		
		MarketSupplierStockInfo Info = new MarketSupplierStockInfo();
		Info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		Info.setNumber(codingRuleManager.getNumber(Info, OrgConstants.DEF_CU_ID));
		Info.setInviteType(treeInfo.get(0));
		Info.setPurchaseOrgUnit(org);
		Info.setSupplierName(supplierInfo.getName());
		Info.setId(BOSUuid.create(Info.getBOSType()));
		
		if(supplierInfo.getProvince()!=null)
			Info.setProvince(supplierInfo.getProvince().getName());
		if(supplierInfo.getCity()!=null)
			Info.setProvince(supplierInfo.getCity().getName());
		if(supplierInfo.getAddress()!=null)
			Info.setAddress(supplierInfo.getAddress());
		
		Info.setState(SupplierState.audit);
		
		Info.setEnterpriseMaster(supplierInfo.getArtificialPerson());
		Info.setBusinessNum(supplierInfo.getBusiLicence());
		Info.setBizRegisterNo(supplierInfo.getBizRegisterNo());
		Info.setSysSupplier(supplierInfo);
		Info.setAuditDate(SysUtil.getAppServerTime(null));
		Info.setAuditor(SysContext.getSysContext().getCurrentUserInfo());
		
		MarketSupplierStockFactory.getRemoteInstance().submit(Info);
		
		Info.setState(SupplierState.audit);
		MarketSupplierStockFactory.getRemoteInstance().update(new ObjectUuidPK(Info.getId()), Info);
		
		refresh(null);
		MsgBox.showInfo("����ɹ���\n ��Ӧ������:"+supplierInfo.getName()+" ��֯��"+org.getName()+"");
	}
	
	/**
	 *
	 * ��������鵥���Ƿ��ڹ�������
	 *
	 * @param ui
	 *            ��ǰUI����ʾ��Ϣʱ��
	 * @param id
	 *            ����ID
	 * @author:liupd ����ʱ�䣺2006-9-29
	 *               <p>
	 */
	public static void checkBillInWorkflow(CoreUIObject ui, String id) {

		ProcessInstInfo instInfo = null;
		ProcessInstInfo[] procInsts = null;
		try {
			IEnactmentService service2 = EnactmentServiceFactory
					.createRemoteEnactService();
			procInsts = service2.getProcessInstanceByHoldedObjectId(id);
		} catch (BOSException e) {
			ExceptionHandler.handle(e);
		}

		for (int i = 0, n = procInsts.length; i < n; i++) {
			// modify by gongyin,���̹���ʱҲ��ʾ����ͼ
			if ("open.running".equals(procInsts[i].getState())
					|| "open.not_running.suspended".equals(procInsts[i]
							.getState())) {
				instInfo = procInsts[i];
			}
		}
		if (instInfo != null) {
			MsgBox.showWarning(ui, EASResource
					.getString(FrameWorkClientUtils.strResource
							+ "Msg_BindWfInstance"));
			SysUtil.abort();
		}
	}
    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockInfo objectValue = new com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockInfo();
        objectValue.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
        return objectValue;
    }

}