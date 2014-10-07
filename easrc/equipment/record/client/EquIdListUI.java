/**
 * output package name
 */
package com.kingdee.eas.port.equipment.record.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.io.File;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.common.LanguageManager;
import com.kingdee.bos.ctrl.excel.io.kds.KDSBookToBook;
import com.kingdee.bos.ctrl.excel.model.struct.Sheet;
import com.kingdee.bos.ctrl.kdf.export.ExportManager;
import com.kingdee.bos.ctrl.kdf.export.KDTables2KDSBook;
import com.kingdee.bos.ctrl.kdf.export.KDTables2KDSBookVO;
import com.kingdee.bos.ctrl.kdf.kds.KDSBook;
import com.kingdee.bos.ctrl.kdf.read.POIXlsReader;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTMenuManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.util.SimpleFileFilter;
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
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.permission.client.longtime.LongTimeDialog;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.IAdminOrgUnit;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.custom.fi.AssetCardCollection;
import com.kingdee.eas.custom.fi.AssetCardFactory;
import com.kingdee.eas.custom.fi.AssetCardInfo;
import com.kingdee.eas.fi.fa.basedata.FaCatFactory;
import com.kingdee.eas.fi.fa.basedata.FaCatInfo;
import com.kingdee.eas.fi.newrpt.client.designer.io.WizzardIO;
import com.kingdee.eas.framework.client.tree.DefaultLNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ITreeBuilder;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;
import com.kingdee.eas.port.equipment.base.client.ImportFaCardUI;
import com.kingdee.eas.port.equipment.base.enumbase.sbStatusType;
import com.kingdee.eas.port.equipment.record.EquIdFactory;
import com.kingdee.eas.port.equipment.record.EquIdInfo;
import com.kingdee.eas.port.equipment.record.IEquId;
import com.kingdee.eas.tools.datatask.DatataskParameter;
import com.kingdee.eas.tools.datatask.client.DatataskCaller;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.eas.xr.app.XRBillStatusEnum;

/**
 * output class name
 */
public class EquIdListUI extends AbstractEquIdListUI
{
    private static final Logger logger = CoreUIObject.getLogger(EquIdListUI.class);
    
    private Color a = new Color(238,205,125);
    
    private boolean flse = true;
    
    /**
     * 更新检测日期有不符合的档案号提示信息
     */
    private StringBuffer excelImportMsg = new StringBuffer();
    /**
     * output class constructor
     */
    public EquIdListUI() throws Exception
    {
        super();
    }

    protected String getEditUIModal() {
    	return UIFactoryName.NEWTAB;
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
     * output actionAudit_actionPerformed
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAudit_actionPerformed(e);
    }

    /**
     * output actionUnAudit_actionPerformed
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionUnAudit_actionPerformed(e);
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.equipment.record.EquIdFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.port.equipment.record.EquIdInfo objectValue = new com.kingdee.eas.port.equipment.record.EquIdInfo();
		
        return objectValue;
    }

    protected FilterInfo getDefaultFilterForQuery() {
    	return super.getDefaultFilterForQuery();
    }
    
    //去除CU隔离
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	
	//根据所属组织隔离设备档案
	protected IQueryExecutor getQueryExecutor(IMetaDataPK arg0,EntityViewInfo arg1) {
		EntityViewInfo viewInfo = (EntityViewInfo)arg1.clone();
	    FilterInfo filInfo = new FilterInfo();

	    String id = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
	    DateFormat FORMAT_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	    Date date = null;
	    try {
	      date = SysUtil.getAppServerTime(null);
	    } catch (EASBizException e1) {
	      e1.printStackTrace();
	    }

	    StringBuffer sb = new StringBuffer();
	    sb.append(" select CFEqmNumberID from cT_OPE_EqmIO  ");
	    sb.append(" where CFInOrgUnitID='").append(id).append("'");
	    sb.append(" and CFRentStart<={ts '" + FORMAT_TIME.format(date) + "'}");
	    sb.append(" and CFRentEnd>={ts '" + FORMAT_TIME.format(date) + "'}");
	    sb.append(" and fstatus = '4'");

	    filInfo.getFilterItems().add(new FilterItemInfo("ssOrgUnit.id", id, CompareType.EQUALS));
	    filInfo.getFilterItems().add(new FilterItemInfo("id", sb.toString(), CompareType.INNER));
	    filInfo.setMaskString("#0 or #1");

	    if (viewInfo.getFilter() != null)
	    {
	      try
	      {
	        viewInfo.getFilter().mergeFilter(filInfo, "and");
	      } catch (BOSException e) {
	        e.printStackTrace();
	      }

	    }
	    else
	    {
	      viewInfo.setFilter(filInfo);
	    }
	    if ("00000000-0000-0000-0000-000000000000CCE7AED4".equals(id)){
	      viewInfo = (EntityViewInfo)arg1.clone();
	    }
	    if ("6vYAAAAAAQvM567U".equals(id)){
		      viewInfo = (EntityViewInfo)arg1.clone();
		    }
	    return super.getQueryExecutor(arg0, viewInfo);
	}
	
    public void onLoad() throws Exception {
    	
    	tblMain.addKDTDataFillListener(new KDTDataFillListener() {
            public void afterDataFill(KDTDataRequestEvent e)
            {
                try
                {
                    tblMain_afterDataFill(e);
                }
                catch(Exception exc)
                {
                    handUIException(exc);
                }
            }
        });
    	
    	super.onLoad();
    	this.btnImportFacard.setEnabled(true);
    	this.btnImportFacard.setIcon(EASResource.getIcon("imgTbtn_importcyclostyle"));    
    	this.btnImportCard.setEnabled(true);
    	
    	InitTree();
    }
    
    
    private void InitTree() throws Exception
    {
    	
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("company.id",SysContext.getSysContext().getCurrentFIUnit().getId()));
    	
    	ITreeBuilder treebuild = TreeBuilderFactory.createTreeBuilder(new DefaultLNTreeNodeCtrl(FaCatFactory.getRemoteInstance()), 2147483647, 5, filter);

    	KDTreeNode rootNode = new KDTreeNode("固定资产类别");
    	((DefaultTreeModel)this.kDTree1.getModel()).setRoot(rootNode);
    	
    	treebuild.buildTree(this.kDTree1);
    	
    	this.kDTree1.expandAllNodes(false, (TreeNode) this.kDTree1.getModel().getRoot());
    	this.kDTree1.setSelectionRow(0);
    }
    
    
    /**
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
    	checkSelected();
    	
    	flse = true;
        String id = this.getSelectedKeyValue();
        
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("eqmType.id"));
		sic.add(new SelectorItemInfo("eqmType.number"));
		sic.add(new SelectorItemInfo("eqmType.longNumber"));
		
        EquIdInfo Info = EquIdFactory.getRemoteInstance().getEquIdInfo(new ObjectUuidPK(id),sic);
       if( Info.getEqmType()==null)
    	   return;
       
       DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) this.kDTree1.getModel().getRoot();
       
       String facatId =  Info.getEqmType().getLongNumber();
       this.kDTree1.expandAllNodes(false, (TreeNode) this.kDTree1.getModel().getRoot());
       
       getSelectFaCatNode(root, facatId);
       
       flse = false;
    }
    
    private int inxex = 1;
    
	private void getSelectFaCatNode(DefaultKingdeeTreeNode node,String facatId) {

		for (int i = 0; i < node.getChildCount(); i++) {
			DefaultKingdeeTreeNode curNode = (DefaultKingdeeTreeNode) node.getChildAt(i);
			
			inxex +=1;
			Object obj = curNode.getUserObject();
			if(obj==null )
				continue;
			if( obj instanceof FaCatInfo )
			{
				String lastLongNumber = ((FaCatInfo)obj).getLongNumber();
				
				
				if(lastLongNumber.equals(facatId))
				{
					this.kDTree1.setSelectionNode(curNode);
					break;
				}
			}
			if (curNode.getChildCount() > 0) {
				getSelectFaCatNode(curNode,facatId);
			}
		}
	}
    
    private void tblMain_afterDataFill(KDTDataRequestEvent e){
        for (int i = e.getFirstRow(); i <= e.getLastRow(); i++)
        {
        	 if (UIRuleUtil.isNotNull(this.tblMain.getRow(i).getCell("sbStatus").getValue())) 
        	 {
        		 String status = UIRuleUtil.getString(this.tblMain.getRow(i).getCell("sbStatus").getValue());
        		 
        		 if(status.equals("停用"))
        			 this.tblMain.getRow(i).getStyleAttributes().setBackground(Color.RED);
        	 }
        }
    }
	  
    protected void kDTree1_valueChanged(TreeSelectionEvent e) throws Exception {
    	
	    	DefaultKingdeeTreeNode  typeNode  =	(DefaultKingdeeTreeNode) kDTree1.getLastSelectedPathComponent();
	    	
	    	if(flse)
	    		return;
			
		     FaCatInfo type  = null;
			if(typeNode!=null){
				if(!typeNode.getUserObject().equals("固定资产类别")){
					type =  (FaCatInfo) typeNode.getUserObject();
					 FilterInfo filter = new FilterInfo();
					 filter.getFilterItems().add(new FilterItemInfo("eqmType.longNumber",type.getLongNumber()+"%" ,CompareType.LIKE));
					mainQuery.setFilter(filter);
					execQuery();
				}else{
					 FilterInfo filter = new FilterInfo();
					mainQuery.setFilter(filter);
					execQuery();
				}
			}
	    }
    
    /**
     * zhangjuan
     * 2014.5.15
     * 实现功能：设备档案列表界面的在用按钮，点击此按钮改变单据的设备状态为在用。
     **/
    public void actionInUse_actionPerformed(ActionEvent e) throws Exception {
    	super.actionInUse_actionPerformed(e); 
    	checkSelected();
    	List<String> list = this.getSelectedIdValues();
    	StringBuffer msg = new StringBuffer("");
    	
    	for (int i = 0; i < list.size(); i++)
    	{
    		ObjectUuidPK pk = new ObjectUuidPK(BOSUuid.read(list.get(i)));
    		SelectorItemCollection sc = new SelectorItemCollection();
    		Object o = getBizInterface().getValue(pk, sc);
    		EquIdInfo conInfo = (EquIdInfo)o;
    		
    		if(conInfo.getSbStatus().equals(sbStatusType.inUse)||conInfo.getSbStatus().equals(sbStatusType.discarded)){
    			if(!"".equals(msg.toString().trim()))
    				msg.append(";").append(conInfo.getNumber());
    			else
    				msg.append(conInfo.getNumber());
    			continue;
    		}
    		conInfo.setSbStatus(sbStatusType.inUse);
    		((IEquId)getBizInterface()).update(pk, conInfo);
		}
    	if(!"".equals(msg.toString().trim()))
    		MsgBox.showConfirm3a("部分数据不满足条件,请查看详细信息!", "编码：\n"+msg.toString()+"\n已经在用或者已报废！");
    	else
    		MsgBox.showInfo("在用成功！");
    	 this.refresh(e);
    }
    
    /**
     * zhangjuan
     * 2014.5.15
     * 实现功能：设备档案列表界面的停用按钮，点击此按钮改变单据的设备状态为停用。
     **/
    public void actionOutUse_actionPerformed(ActionEvent e) throws Exception {
    	super.actionOutUse_actionPerformed(e);
    	checkSelected();
    	List<String> list = this.getSelectedIdValues();
    	StringBuffer msg = new StringBuffer("");
    	
    	for (int i = 0; i < list.size(); i++)
    	{
    		ObjectUuidPK pk = new ObjectUuidPK(BOSUuid.read(list.get(i)));
    		SelectorItemCollection sc = new SelectorItemCollection();
    		Object o = getBizInterface().getValue(pk, sc);
    		EquIdInfo conInfo = (EquIdInfo)o;
    		
    		if(conInfo.getSbStatus().equals(sbStatusType.notUse)||conInfo.getSbStatus().equals(sbStatusType.discarded)){
    			if(!"".equals(msg.toString().trim()))
    				msg.append(";").append(conInfo.getNumber());
    			else
    				msg.append(conInfo.getNumber());
    			continue;
    		}
    		conInfo.setSbStatus(sbStatusType.notUse);
    		((IEquId)getBizInterface()).update(pk, conInfo);
		}
    	if(!"".equals(msg.toString().trim()))
    		MsgBox.showConfirm3a("部分数据不满足条件,请查看详细信息!", "编码：\n"+msg.toString()+"\n已经停用或者已报废！");
    	else
    		MsgBox.showInfo("停用成功！");
    	 this.refresh(e);
    }
    
	protected void initWorkButton() {
		super.initWorkButton();
		btninUse.setIcon(EASResource.getIcon("imgTbtn_turnin"));
		btnoutUse.setIcon(EASResource.getIcon("imgTbtn_stopturnin"));
		btnExcel.setIcon(EASResource.getIcon("imgTbtn_input"));
		btnExcelFoced.setIcon(EASResource.getIcon("imgTbtn_dcdwj"));
		btnExcelEqu.setIcon(EASResource.getIcon("imgTbtn_inputoutput"));
		btnBeijian.setIcon(EASResource.getIcon("imgTbtn_inputoutput"));
		btnZhuyao.setIcon(EASResource.getIcon("imgTbtn_inputoutput"));
		btnXiangxi.setIcon(EASResource.getIcon("imgTbtn_inputoutput"));
	}
	
	
	public void actionExcelFoced_actionPerformed(ActionEvent e)throws Exception {
			super.actionExcelFoced_actionPerformed(e);
			btnExportExcel();
    }

	String colName[] = {"设备档案号","到期检测日期"};
	/**
	 * 导出模板
	  **/
	protected void btnExportExcel() throws Exception {
		ExportManager exportM = new ExportManager();
        String path = null;
        File tempFile = File.createTempFile("eastemp",".xls");
        path = tempFile.getCanonicalPath();
        
        KDTable kdtable = new KDTable();
        kdtable.addHeadRow();
        for (int i = 0; i < colName.length; i++)
        {
        	this.addColumn(kdtable, "F"+i,colName[i]);
		}
        
        KDTables2KDSBookVO[] tablesVO = new KDTables2KDSBookVO[1];
        tablesVO[0]=new KDTables2KDSBookVO(kdtable);
		tablesVO[0].setTableName("更新到期检测日期");
        KDSBook book = null;
        book = KDTables2KDSBook.getInstance().exportKDTablesToKDSBook(tablesVO,true,true);
        exportM.exportToExcel(book, path);
		KDFileChooser fileChooser = new KDFileChooser();
		fileChooser.setFileSelectionMode(0);
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setSelectedFile(new File("更新到期检测日期.xls"));
		int result = fileChooser.showSaveDialog(this);
		if (result == KDFileChooser.APPROVE_OPTION){
			File dest = fileChooser.getSelectedFile();
			try{
				File src = new File(path);
				if (dest.exists())
					dest.delete();
				src.renameTo(dest);
				MsgBox.showInfo("导出成功！");
				KDTMenuManager.openFileInExcel(dest.getAbsolutePath());
			}
			catch (Exception e3)
			{
				handUIException(e3);
			}
		}
		tempFile.delete();
	}
	
	private IColumn addColumn(KDTable kdtable,String key,String colName)
	{
		IColumn Icolunmn = kdtable.addColumn();
		Icolunmn.setKey(key);
		kdtable.getHeadRow(0).getCell(key).setValue(colName);
		return Icolunmn;
	}
	
	public void actionExcel_actionPerformed(ActionEvent e) throws Exception {
//		DatataskCaller task = new DatataskCaller();
//        task.setParentComponent(this);
//        DatataskParameter param = new DatataskParameter();
//        String solutionName = "eas.custom.007";
//        param.solutionName = solutionName;
//        ArrayList paramList = new ArrayList();
//        paramList.add(param);   
//        task.invoke(paramList, DatataskMode.UPDATE, true);
		actionImportExcel();
        this.refresh(null);
	}
	

//	
	    public void actionImportExcel()  {
			final String path = showExcelSelectDlg(this);
			if (path == null) {
				return;
			}
			Window win = SwingUtilities.getWindowAncestor(this);
	        LongTimeDialog dialog = null;
	        if(win instanceof Frame){
	        	dialog = new LongTimeDialog((Frame)win);
	        }else if(win instanceof Dialog){
	        	dialog = new LongTimeDialog((Dialog)win);
	        }
	        if(dialog==null){
	        	dialog = new LongTimeDialog(new Frame());
	        }
	        dialog.setLongTimeTask(new ILongTimeTask() {
				public void afterExec(Object arg0) throws Exception {
					Boolean bol=(Boolean)arg0;
					if(bol){
						if(excelImportMsg!=null&&!"".equals(excelImportMsg.toString().trim()))
							MsgBox.showConfirm3a("部分设备档案号不存在，请查看详细信息！", excelImportMsg.toString());
						else
							MsgBox.showInfo("更新成功！");
					}
				}
				public Object exec() throws Exception {
					boolean bol=importExcelToTable(path);
					return bol;
				}
	    	}
		    );
		    dialog.show();
		}
	    
	    
		private boolean importExcelToTable(String fileName) throws Exception {
			KDSBook kdsbook = null;
			try {
				kdsbook = POIXlsReader.parse2(fileName);
			} catch (Exception e) {
				e.printStackTrace();
				MsgBox.showWarning(this,"读EXCEL出错,EXCEl格式不匹配！");
				return false;
			}
			if (kdsbook == null) {
				return false;
			}
			if(KDSBookToBook.traslate(kdsbook).getSheetCount()>1){
				MsgBox.showWarning(this,"读EXCEL出错,EXCEl Sheet数量不匹配！");
				return false;
			}
			Sheet excelSheet = KDSBookToBook.traslate(kdsbook).getSheet(0);
	    	Map e_colNameMap = new HashMap();
			int e_maxRow = excelSheet.getMaxRowIndex();
			int e_maxColumn = excelSheet.getMaxColIndex();
			for (int col = 0; col <= e_maxColumn; col++) {
				String excelColName = excelSheet.getCell(0, col, true).getText();
				e_colNameMap.put(excelColName, new Integer(col));
			}
			for (int i = 0; i < colName.length; i++)
	        {
				Integer colInt = (Integer) e_colNameMap.get(colName[i]);
				if (colInt == null) {
					MsgBox.showWarning(this,"表头结构不一致！表格上的关键列:" + colName[i] + "在EXCEL中没有出现！");
					return false;
				}
			}
			IEquId iEquId = EquIdFactory.getRemoteInstance();
			Calendar ca = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			excelImportMsg = new StringBuffer();
			
			for (int rowIndex = 1; rowIndex <= e_maxRow; rowIndex++) {
				Integer colInt = (Integer) e_colNameMap.get(colName[0]);
				Integer colcityInt = (Integer) e_colNameMap.get(colName[1]);
//				Integer colhongkInt = (Integer) e_colNameMap.get(colName[3]);

				if (colInt == null) {
					continue;
				}
				com.kingdee.bos.ctrl.common.variant.Variant cellRawVal = excelSheet.getCell(rowIndex, colInt.intValue(), true).getValue();
				if (com.kingdee.bos.ctrl.common.variant.Variant.isNull(cellRawVal)) {
					continue;
				}
				String citycellRawVal = excelSheet.getCell(rowIndex, colcityInt.intValue(), true).getDisplayFormula();
//				String hongkcellRawVal = excelSheet.getCell(rowIndex, colhongkInt.intValue(), true).getDisplayFormula();
				String colValue = cellRawVal.toString();
				if(iEquId.exists("where tzdaNumber='"+colValue+"'"))
				{
					EquIdInfo equInfo = iEquId.getEquIdCollection("where tzdaNumber='"+colValue+"'").get(0);
//					BigDecimal portperiod = equInfo.getPortPeriod()!=null?equInfo.getPortPeriod():BigDecimal.ZERO;
					BigDecimal ctyPeriod = equInfo.getCityPeriod()!=null?equInfo.getCityPeriod():BigDecimal.ZERO;
					
					if (UIRuleUtil.isNotNull(citycellRawVal)) {
						ca.setTime(sdf.parse(citycellRawVal));
						equInfo.setTextDate1(ca.getTime());
						ca.add(Calendar.YEAR,ctyPeriod.intValue());
						equInfo.setDayone(ca.getTime());
						
					}
					
//					if (UIRuleUtil.isNotNull(hongkcellRawVal)) {
//						ca.setTime(sdf.parse(hongkcellRawVal));
//						ca.add(Calendar.YEAR,portperiod.intValue());
//						equInfo.setDaytow(ca.getTime());
//					}
					
					iEquId.update(new ObjectUuidPK(equInfo.getId()), equInfo);
				}
				else
				{
//					if(excelImportMsg!=null&&!"".equals(excelImportMsg.toString().trim()))
//					else
//						excelImportMsg.append("设备档案号："+colValue+"\n");
					excelImportMsg.append("设备档案号："+colValue+"；到期检测日期："+citycellRawVal+" 设备档案号不存在！\n");
				}
			}
			return true;
		}
		
		
		
		 public static String showExcelSelectDlg(CoreUIObject ui)
         {
			 KDFileChooser chsFile = new KDFileChooser();
			 String XLS = "xls";
			 String Key_File = "Key_File";
			 SimpleFileFilter Filter_Excel = new SimpleFileFilter(XLS, (new StringBuilder("MS Excel")).append(LanguageManager.getLangMessage(Key_File, WizzardIO.class.getName(), "\u64CD\u4F5C\u5931\u8D25")).toString());
			 chsFile.addChoosableFileFilter(Filter_Excel);
			 int ret = chsFile.showOpenDialog(ui);
			 if(ret != 0)
				 SysUtil.abort();

			 File file = chsFile.getSelectedFile();
			 String fileName = file.getAbsolutePath();
			 return fileName;
         }
		
	
	public void actionImportFacard_actionPerformed(ActionEvent e)throws Exception {
		IUIWindow uiWindow = null;
		UIContext context = new UIContext(this);
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ImportFaCardUI.class.getName(), context, null, OprtState.ADDNEW);
		uiWindow.show(); 
	}

	public void actionImportCard_actionPerformed(ActionEvent e)throws Exception {
		super.actionImportCard_actionPerformed(e);
		
		LongTimeDialog dialog = new LongTimeDialog((JFrame) SwingUtilities.getWindowAncestor(this));    
        dialog.setLongTimeTask(new ILongTimeTask() {    
            public Object exec() throws Exception { 
            	Imp();
                return "";    
           }    
            
            public void afterExec(Object result) throws Exception {    
           }    
        });    
        Component[] cps=dialog.getContentPane().getComponents();    
        for(Component cp:cps){    
            if(cp instanceof JLabel){    
                ((JLabel) cp).setText("引入正在进行、请稍候.......");    
            }    
        }    
        dialog.show();  
        
        refresh(null);
	}
	
	int index = 0;
	
	private void Imp() throws BOSException, EASBizException
	{
		
		String sql = "select a.fid from CT_FI_AssetCard a left join CT_REC_EquId b on b.fsourcebillid=a.fid where b.fid is null";
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filInfo = new FilterInfo();
		filInfo.getFilterItems().add(new FilterItemInfo("id",sql,CompareType.INNER));
		view.setFilter(filInfo);
		IAdminOrgUnit iAdminOrgUnit = AdminOrgUnitFactory.getRemoteInstance();
		IEquId IEquId = EquIdFactory.getRemoteInstance();
		AssetCardCollection assectCollect = AssetCardFactory.getRemoteInstance().getAssetCardCollection(view);
		index = 0;
		for (int i = 0; i < assectCollect.size(); i++) 
		{
			AssetCardInfo cardInfo = assectCollect.get(i);
			if(!IEquId.exists("select id where number='"+cardInfo.getNumber()+"'"))
				ImportEquInfo(IEquId,iAdminOrgUnit,cardInfo);
		}
		if(index!=0)
			MsgBox.showInfo("引入成功，共成功{"+index+"}条！");
		else
			MsgBox.showWarning("没有满足条件的数据，引入失败！");
	}
	
	private void ImportEquInfo(IEquId IEquId,IAdminOrgUnit iAdminOrgUnit,AssetCardInfo info)
	{
		try 
		{
			EquIdInfo equInfo = new EquIdInfo();
			equInfo.setId(BOSUuid.create(equInfo.getBOSType()));
			equInfo.setNumber(info.getNumber());
			equInfo.setName(info.getName());
			equInfo.setSsOrgUnit(iAdminOrgUnit.getAdminOrgUnitInfo(new ObjectUuidPK(info.getFICompany().getId())));
			equInfo.setSourceBillId(info.getId().toString());
			equInfo.setStatus(XRBillStatusEnum.TEMPORARILYSAVED);
			equInfo.setSbStatus(sbStatusType.inUse);
			
			IEquId.addnew(equInfo);
			index+=1;
		} 
		catch (EASBizException e) {
			e.printStackTrace();
		} 
		catch (BOSException e) {
			e.printStackTrace();
		}
	}
	public void actionExcelEqu_actionPerformed(ActionEvent e) throws Exception {
		super.actionExcelEqu_actionPerformed(e);
		String strSolutionName = "eas.equ.001";
		DatataskCaller task = new DatataskCaller();
		task.setParentComponent(this);
		DatataskParameter param = new DatataskParameter();
		param.solutionName = strSolutionName;
		param.alias = btnExcelEqu.getText();
		ArrayList paramList = new ArrayList();
		paramList.add(param);
		task.invoke(paramList, 0, true);
	}
	
	public void actionZhuyao_actionPerformed(ActionEvent e) throws Exception {
		super.actionZhuyao_actionPerformed(e);
		String strSolutionName = "eas.equ.002";
		DatataskCaller task = new DatataskCaller();
		task.setParentComponent(this);
		DatataskParameter param = new DatataskParameter();
		param.solutionName = strSolutionName;
		param.alias = btnZhuyao.getText();
		ArrayList paramList = new ArrayList();
		paramList.add(param);
		task.invoke(paramList, 0, true);
	}
	
	public void actionBeijian_actionPerformed(ActionEvent e) throws Exception {
		super.actionBeijian_actionPerformed(e);
		String strSolutionName = "eas.equ.003";
		DatataskCaller task = new DatataskCaller();
		task.setParentComponent(this);
		DatataskParameter param = new DatataskParameter();
		param.solutionName = strSolutionName;
		param.alias = btnBeijian.getText();
		ArrayList paramList = new ArrayList();
		paramList.add(param);
		task.invoke(paramList, 0, true);
	}
	
	public void actionXiangxi_actionPerformed(ActionEvent e) throws Exception {
		super.actionXiangxi_actionPerformed(e);
		String strSolutionName = "eas.equ.004";
		DatataskCaller task = new DatataskCaller();
		task.setParentComponent(this);
		DatataskParameter param = new DatataskParameter();
		param.solutionName = strSolutionName;
		param.alias = btnXiangxi.getText();
		ArrayList paramList = new ArrayList();
		paramList.add(param);
		task.invoke(paramList, 0, true);
	}
}