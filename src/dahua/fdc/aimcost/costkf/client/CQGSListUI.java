/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.costkf.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
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
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.aimcost.ForecastChangeVisFactory;
import com.kingdee.eas.fdc.aimcost.costkf.CQGSFactory;
import com.kingdee.eas.fdc.aimcost.costkf.CQGSInfo;
import com.kingdee.eas.fdc.aimcost.costkf.ICQGS;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class CQGSListUI extends AbstractCQGSListUI
{
    private static final Logger logger = CoreUIObject.getLogger(CQGSListUI.class);
	private final static String CANTUNAUDIT = "cantUnAudit";
	private final static String CANTAUDIT = "cantAudit";
    
    /**
     * output class constructor  
     */
    public CQGSListUI() throws Exception
    {
        super();
    } 
    public void onLoad() throws Exception {
    	super.onLoad();
    	btnAudit.setIcon(EASResource.getIcon("imgTbtn_auditing"));
    	btnUnAudit.setIcon(EASResource.getIcon("imgTbtn_fauditing"));
    	btnrevise.setIcon(EASResource.getIcon("imgTbtn_emend"));
    	this.initTree();
    }
    /**
	 * ������Ŀ��
	 * 
	 * @throws Exception
	 */
    protected void initTree() throws Exception {
		ProjectTreeBuilder treeBuilder = new ProjectTreeBuilder();
		treeBuilder.build(this, this.treeMain, this.actionOnLoad);
		this.treeMain.expandAllNodes(true,(TreeNode) ((TreeModel) this.treeMain.getModel()).getRoot());//Ĭ��չ���������ڵ�
	}
    
    
    
    @Override
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.treeMain_valueChanged(e);
		this.tblMain.removeRows();// ��껻ѡ��ɾ��������
	}
    
    
    
    
    
	@Override  //�޶�     �ж�  �Ƿ����£��Ƿ����������Ƿ�汾�����
	public void actionRevise_actionPerformed(ActionEvent e) throws Exception {
		super.actionRevise_actionPerformed(e);
		checkSelected();//�ж��Ƿ�ѡ��
		String id = getSelectedKeyValue();
		
		ICQGS remoteInstance = CQGSFactory.getRemoteInstance();
		CQGSInfo cqgsinfo = remoteInstance.getCQGSInfo(new ObjectUuidPK(id),getSelectors());   //getSelectors �޶��󣬲��㱣�棬����ʾF7�ֶε�ֵ
		if(!cqgsinfo.isLasted()){
			MsgBox.showWarning("�������°治���޶�");
			abort();
		}
		if(!cqgsinfo.getState().equals(FDCBillStateEnum.AUDITTED)){
			MsgBox.showWarning("�������޶�");
			abort();
		}
		//���ˣ����İ汾��
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filInfo = new FilterInfo();
		view.setFilter(filInfo);
		filInfo.getFilterItems().add(new FilterItemInfo("ProjectName.id",cqgsinfo.getProjectName().getId()));
		filInfo.getFilterItems().add(new FilterItemInfo("Version",cqgsinfo.getVersion(),CompareType.GREATER));
		
		if(remoteInstance.exists(filInfo)){
			MsgBox.showWarning("��ѡ�����°��޶�");
			abort();
		}
		
		
		
		UIContext uiContext = new UIContext(this);
		uiContext.put("CQGSInfo", cqgsinfo);
		uiContext.put("lasted", true);
		IUIWindow ui = UIFactory.createUIFactory(getEditUIModal()).create(getEditUIName(), uiContext, null,	OprtState.ADDNEW);
		ui.show();
	}
	
    public SelectorItemCollection getSelectors()//getSelectors �޶��󣬲��㱣�棬����ʾF7�ֶε�ֵ��abstract��copy����
    {
        SelectorItemCollection sic = new SelectorItemCollection();
		String selectorAll = System.getProperty("selector.all");
		if(StringUtils.isEmpty(selectorAll)){
			selectorAll = "true";
		}
        sic.add(new SelectorItemInfo("lasted"));
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
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("lastUpdateUser.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("lastUpdateUser.id"));
        	sic.add(new SelectorItemInfo("lastUpdateUser.number"));
        	sic.add(new SelectorItemInfo("lastUpdateUser.name"));
		}
        sic.add(new SelectorItemInfo("lastUpdateTime"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("description"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("auditor.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("auditor.id"));
        	sic.add(new SelectorItemInfo("auditor.number"));
        	sic.add(new SelectorItemInfo("auditor.name"));
		}
        sic.add(new SelectorItemInfo("company"));
        sic.add(new SelectorItemInfo("startdate"));
        sic.add(new SelectorItemInfo("enddate"));
        sic.add(new SelectorItemInfo("redarea"));
        sic.add(new SelectorItemInfo("VolumeRatio"));
        sic.add(new SelectorItemInfo("TotalArea"));
        sic.add(new SelectorItemInfo("State"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("ProjectName.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("ProjectName.id"));
        	sic.add(new SelectorItemInfo("ProjectName.number"));
        	sic.add(new SelectorItemInfo("ProjectName.name"));
		}
    	sic.add(new SelectorItemInfo("entrys.id"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entrys.*"));
		}
		else{
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entrys.BuildingName.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entrys.BuildingName.id"));
			sic.add(new SelectorItemInfo("entrys.BuildingName.name"));
        	sic.add(new SelectorItemInfo("entrys.BuildingName.number"));
		}
    	sic.add(new SelectorItemInfo("entrys.BuidlingArea"));
    	sic.add(new SelectorItemInfo("entrys.SaleArea"));
    	sic.add(new SelectorItemInfo("entrys.BuildingFloorArea"));
    	sic.add(new SelectorItemInfo("entrys.Use"));
    	sic.add(new SelectorItemInfo("entrys.PropertyRight"));
        sic.add(new SelectorItemInfo("Version"));
        return sic;
    }        
    	
	private void checkBeforeAuditOrUnAudit(CQGSInfo cqgsinfo , FDCBillStateEnum aduit,String Waring ) {
		//��鵥���Ƿ��������
		FDCClientUtils.checkBillInWorkflow(this, cqgsinfo.getId().toString());
		boolean b = cqgsinfo!=null&&cqgsinfo.getState()!=null&&cqgsinfo.getState().equals(aduit);
		if(!b){
			MsgBox.showWarning(this, FDCClientUtils.getRes(Waring));
			SysUtil.abort();
		}
	} 
	
	public void actionAduit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();//�ж��Ƿ�ѡ��
		String id = getSelectedKeyValue();
		ICQGS remoteInstance = CQGSFactory.getRemoteInstance();
		CQGSInfo cqgsinfo = remoteInstance.getCQGSInfo(new ObjectUuidPK(id));
//		if(!cqgsinfo.isLasted()){
//			MsgBox.showWarning("�������°棬�������");
//			abort();
//		}
		if(cqgsinfo.getState().equals(FDCBillStateEnum.SAVED)){
			MsgBox.showWarning("�����ύ�������");
			abort();
		}
		checkBeforeAuditOrUnAudit(cqgsinfo, FDCBillStateEnum.SUBMITTED, CANTAUDIT);
		super.actionAduit_actionPerformed(e);
		FDCClientUtils.showOprtOK(this);
		refresh(null);

	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();//�ж��Ƿ�ѡ��
		String id = getSelectedKeyValue();
		ICQGS remoteInstance = CQGSFactory.getRemoteInstance();
		CQGSInfo cqgsinfo = remoteInstance.getCQGSInfo(new ObjectUuidPK(id));
		checkBeforeAuditOrUnAudit(cqgsinfo, FDCBillStateEnum.AUDITTED, CANTUNAUDIT);
		super.actionUnAudit_actionPerformed(e);
		refresh(null);
	}
//	list������˸���getQueryExecutor����
	protected IQueryExecutor getQueryExecutor(IMetaDataPK arg0,EntityViewInfo arg1) {
		EntityViewInfo view = (EntityViewInfo)arg1.clone(); //��¡EntityViewInfo���Է�list��ѯ��������
		FilterInfo filter = new FilterInfo();
	    FilterItemCollection filterItems = filter.getFilterItems();
	    DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();//���ѡ�еĽڵ�
    	if (node != null) {//ѡ�����Ľڵ㲻Ϊ�գ�
    		String allSpIdStr = FDCTreeHelper.getStringFromSet(getAllObjectIdMap(node).keySet());//��������setת�����ַ���
    		if(!allSpIdStr.equals(""))
    			filterItems.add(new FilterItemInfo("ProjectName.id", allSpIdStr, CompareType.INNER));
    	}else{
    		filterItems.add(new FilterItemInfo("ProjectName.id", "99", CompareType.EQUALS));
    	}
    	
    	if(view.getFilter()==null)//��������Ϊ��
    		view.setFilter(filter);
    	else{
    		try {//����������Ϊ�գ������õĹ�������ƴ�ӽ�ȥ
				view.getFilter().mergeFilter(filter, "and");
			} catch (BOSException e) {
				e.printStackTrace();
			}
    	}
		return super.getQueryExecutor(arg0, view);
	}
	
	/**
	 * ����CU����
	 */
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	


    /**
     * ��ȡroot�ڵ������й�����ĿID
     * @param treeNode
     * @return
     */
	public static Map getAllObjectIdMap(TreeNode treeNode) {
		Map idMap = new HashMap();
		if (treeNode != null) {
			fillTreeNodeIdMap(idMap, treeNode);
		}
		return idMap;
	}
	
	private static void fillTreeNodeIdMap(Map idMap, TreeNode treeNode) {//�ݹ�ڵ㣬ȫ���ڵ�
		DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) treeNode;
		
		if (thisNode.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo objectInfo = (CurProjectInfo) thisNode.getUserObject();
			idMap.put(objectInfo.getId().toString(), thisNode);
		}
		int childCount = treeNode.getChildCount();
		while (childCount > 0) {
			fillTreeNodeIdMap(idMap, treeNode.getChildAt(childCount - 1));
			childCount--;
		}
	}
    protected void prepareUIContext(UIContext uiContext, ActionEvent e) {//��ǰ���˽���  uicontext ����֮��Ĵ�ֵ
		super.prepareUIContext(uiContext, e);
		ItemAction act = getActionFromActionEvent(e);
		if (act.equals(actionAddNew)) {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();//���ѡ�еĽڵ�
			if(node!=null&&node.getUserObject() instanceof  CurProjectInfo){//���ѡ�еĽڵ㲻Ϊ�գ�������ǰ��Ŀ
				uiContext.put("project", node.getUserObject());
			}else{
				uiContext.put("project", null);
			}
			
			//
		}
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
    	//�жϵ�ǰ��û�д������Ĵ���
		IUIWindow win = FDCClientUtils.findUIWindow(this.getEditUIName(), this.getUIContext(), dataObjects, OprtState.ADDNEW);
		if (null != win) {
			//��ʾ����
			win.show();
			//��ʾ����ͼ�رգ���������Ĵ������������ݾͻ���ʾ�û������û�о�ֱ�ӹص�����һ���µ�
			win.close();
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
    	checkSelected(); //����Ƿ�ѡ��
    	String id = getSelectedKeyValue();
    	CQGSInfo cqgsinfo = CQGSFactory.getRemoteInstance().getCQGSInfo(new ObjectUuidPK(id));
		if(cqgsinfo.getState().equals(FDCBillStateEnum.AUDITTED)){
			MsgBox.showWarning("����˲����޸�");
			abort();
		}
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {	
    	checkSelected(); //����Ƿ�ѡ��
    	String id = getSelectedKeyValue();
    	CQGSInfo cqgsinfo = CQGSFactory.getRemoteInstance().getCQGSInfo(new ObjectUuidPK(id));
		if(cqgsinfo.getState().equals(FDCBillStateEnum.AUDITTED)){
			MsgBox.showWarning("����˲���ɾ��");
			abort();
		}
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
        return com.kingdee.eas.fdc.aimcost.costkf.CQGSFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.aimcost.costkf.CQGSInfo objectValue = new com.kingdee.eas.fdc.aimcost.costkf.CQGSInfo();
		
        return objectValue;
    }

}