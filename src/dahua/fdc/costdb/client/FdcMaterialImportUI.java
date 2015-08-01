/**
 * output package name
 */
package com.kingdee.eas.fdc.costdb.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.costdb.FdcMaterialFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.config.TablePreferencesHelper;
import com.kingdee.eas.tools.datatask.DatataskMode;
import com.kingdee.eas.tools.datatask.DatataskParameter;
import com.kingdee.eas.tools.datatask.client.DatataskCaller;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class FdcMaterialImportUI extends AbstractFdcMaterialImportUI
{
    private static final Logger logger = CoreUIObject.getLogger(FdcMaterialImportUI.class);
    public static final String resourcePath = "com.kingdee.eas.fdc.costdb.CostDBResource";
    /**
     * output class constructor
     */
    public FdcMaterialImportUI() throws Exception
    {
        super();
    }
	public void onLoad() throws Exception {
		this.tblMain.getDataRequestManager().addDataFillListener(new KDTDataFillListener() {
			public void afterDataFill(KDTDataRequestEvent e) {
				// do something
				String strTemp = "";
				for (int i = e.getFirstRow(); i <= e.getLastRow(); i++) {
					if( tblMain.getRow(i).getCell("project.longNumber")!=null){
						strTemp = tblMain.getRow(i).getCell("project.longNumber").getValue().toString();
						strTemp = strTemp.replace('!', '.');
						tblMain.getRow(i).getCell("project.longNumber").setValue(strTemp);
					}
				}
			}
		});
		super.onLoad();
		FDCTableHelper.disableExportByPerimission(this, this.getTableForCommon(), "ActionPrint");
		//?权限
		this.menuItemImportData.setVisible(true);		
		this.actionImportData.setVisible(true);
		this.actionImportData.setEnabled(true);//
		initStatus();
//		if (SysContext.getSysContext().getCurrentCostUnit()!=null&&SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit()) {
////			this.actionSubmit.setEnabled(false);
////			this.actionAddRow.setEnabled(false);
////			this.actionDeleteRow.setEnabled(false);
////			this.actionRecense.setEnabled(false);
////			this.actionExpression.setEnabled(false);
////			this.actionRevert.setEnabled(false);
////			this.actionAudit.setEnabled(false);
////			this.actionUnAudit.setEnabled(false);
//			this.menuItemImportData.setEnabled(true);
//		}else{
//			this.menuItemImportData.setEnabled(false);
//		}
		this.menuItemImportData.setEnabled(true);
		buildProjectTree();
		this.treeMain.setSelectionRow(0);
		this.btnImportData.setIcon(EASResource.getIcon("imgTbtn_input"));
		//设置保存当前样式
		tHelper = new TablePreferencesHelper(this);
	}
	protected void buildProjectTree() throws Exception {
		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder(false);
		treeMain.setShowsRootHandles(true);
		projectTreeBuilder.build(this, treeMain, actionOnLoad);
	}
	private void initStatus(){
		this.btnAddNew.setVisible(false);
		this.btnRemove.setVisible(false);
		this.btnEdit.setVisible(false);
		this.btnView.setVisible(false);
//		this.btnRefresh.setVisible(false);
		this.btnQuery.setVisible(false);
		this.btnCancel.setVisible(false);
		this.btnCancelCancel.setVisible(false);
		this.btnAttachment.setVisible(false);
		this.btnLocate.setVisible(false);
		this.menuItemAddNew.setVisible(false);
		this.menuItemRemove.setVisible(false);
		this.menuItemEdit.setVisible(false);
		this.menuItemView.setVisible(false);
		this.menuItemRefresh.setVisible(false);		
		this.menuItemCancel.setVisible(false);
		this.menuItemCancelCancel.setVisible(false);
		this.MenuItemAttachment.setVisible(false);
		this.menuBiz.setVisible(false);
		this.menuEdit.setVisible(false);
		this.menuView.setVisible(false);
		
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
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
//        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionView_actionPerformed
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
//        super.actionView_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
//        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
//        super.actionRemove_actionPerformed(e);
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

	protected String getEditUIName() {
		// TODO 自动生成方法存根
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		// TODO 自动生成方法存根
		return FdcMaterialFactory.getRemoteInstance();
	}
	/*
	 * 导入参数设置
	 * jackwang 2006/11/16
	 */
    protected ArrayList getImportParam() {
        DatataskParameter param = new DatataskParameter();
//        //当前节点
//        Hashtable hs = new Hashtable();
//        String orgOrProId = this.getSelectObjId();
//        hs.put("orgOrProId",orgOrProId);
//        //
//		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
//		if (node == null || node.getUserObject() == null
//				|| OrgViewUtils.isTreeNodeDisable(node)) {		
//			if (node.getUserObject() instanceof CurProjectInfo) {
//				CurProjectInfo curProjectInfo = (CurProjectInfo) node.getUserObject();
//				hs.put("curProjectInfo",curProjectInfo);
//			} else if (node.getUserObject() instanceof OrgStructureInfo) {
//				OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
//				if (oui.getUnit() == null) {
//			
//				}
//				FullOrgUnitInfo fullOrgUnitInfo = oui.getUnit();
//				hs.put("fullOrgUnitInfo",fullOrgUnitInfo);
//			}
//		}
//		//当前最新版本号
//        AimCostVersionHandler versionHandler;
//        String lastVerisonNumber = "1.0";
//		try {
//			versionHandler = new AimCostVersionHandler(orgOrProId);
//			lastVerisonNumber = versionHandler.getLastVersion();
//		} catch (BOSException e) {
//			e.printStackTrace();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		hs.put("lastVerisonNumber",lastVerisonNumber);
//		//最新headID
//		String lastFid = null;
//		String sql = "select top 1 FID from T_Aim_AimCost "
//			+ "where FOrgOrProId='" + orgOrProId + "'"
//			+ "order by to_number(FVersionNumber) desc";
//		IRowSet rs;
//		try {
//			rs = SQLExecutorFactory.getRemoteInstance(sql).executeSQL();
//			if(rs.next()) {
//				lastFid = rs.getString("FID");
//				hs.put("lastFid",lastFid);
//			}
//		} catch (BOSException e) {
//			e.printStackTrace();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}		
//        param.setContextParam(hs);//
//      当前节点
		Hashtable hs = new Hashtable();
		CurProjectInfo projectNode = this.getSelectProjectNode();
		if(projectNode==null){
			MsgBox.showInfo(EASResource.getString("com.kingdee.eas.fdc.costdb.CostDBResource", "Import_FdcMaterial_ProjectNodeSelect"));
			SysUtil.abort();
		}
		hs.put("projectNode", projectNode);
		param.setContextParam(hs);//
        String solutionName = "eas.fdc.costmanager.costdb.CostDBResource";
        param.solutionName = solutionName;      
        param.alias = EASResource.getString(resourcePath, "FdcMaterial");
        ArrayList paramList = new ArrayList();
        paramList.add(param);
        return paramList;
    }
    private CurProjectInfo getSelectProjectNode(){
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			return projectInfo;
		}
		return null;
    }
	public void actionImportData_actionPerformed(ActionEvent e) throws Exception {
    	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
    	if (node == null) {
			// 提示选中节点
			// MsgBox.showWarning("请指定需要引入的节点!");//ProjectImport_CheckNodeSelected
    		MsgBox.showInfo(EASResource.getString("com.kingdee.eas.fdc.costdb.CostDBResource", "Import_FdcMaterial_ProjectNodeSelect"));
			return;
		}
//		super.actionImportData_actionPerformed(e);
        DatataskCaller task = new DatataskCaller();
        task.setParentComponent(this);
        if (getImportParam() != null)
        {
            task.invoke(getImportParam(), DatataskMode.ImpMode,true,true);
        }
        actionRefresh_actionPerformed(e);
	}

    // 默认进行当前CU的过滤。子类可重载。
    protected FilterInfo getDefaultFilterForQuery() {
    	return null;
    }
    /**
     * output treeMain_valueChanged method
     */
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.getFilterItems().add(new FilterItemInfo("project.id", projectInfo.getId().toString(), CompareType.EQUALS));
			this.mainQuery.setFilter(filterInfo);
			tblMain.removeRows();// 触发新查询
			if(projectInfo.isIsLeaf()){
				this.menuItemImportData.setEnabled(true);
			}else{
				this.menuItemImportData.setEnabled(false);
			}
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
			if (oui == null || oui.getUnit() == null)
				return;
			FullOrgUnitInfo info = oui.getUnit();
			HashSet lnUps = new HashSet();
			//获取选中组合及下级组织集合
			getLeafNodesIdSet(node, lnUps);
			lnUps.add(info.getId().toString());
			//获取该集合下的工程项目
			CurProjectCollection cpc = new CurProjectCollection();
			FilterInfo filter = new FilterInfo();
			if (lnUps.size() != 0) {
				filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", lnUps, CompareType.INCLUDE));
				// filterInfo.setMaskString("(#0 or #1 or #2) AND #3");
				EntityViewInfo evi = new EntityViewInfo();
				evi.setFilter(filter);
				cpc = CurProjectFactory.getRemoteInstance().getCurProjectCollection(evi) ;
			}
			HashSet projectlnUps = new HashSet();
			if(cpc!=null&&cpc.size()!=0){				
				for(int i = 0;i<cpc.size();i++){
					projectlnUps.add(cpc.get(i).getId().toString());
				}
			}
			FilterInfo filterInfo = new FilterInfo();
			if(projectlnUps.size()!=0){
				filterInfo.getFilterItems().add(new FilterItemInfo("project.id", projectlnUps, CompareType.INCLUDE));			
//				filterInfo.setMaskString(" #0 ");
				this.mainQuery.setFilter(filterInfo);
				tblMain.removeRows();// 触发新查询
			
			}
			this.menuItemImportData.setEnabled(false);
		}
	}
	/**
	 * 
	 * 描述：生成组织树节点集合
	 * 
	 * @param node
	 * @param leafNodesIdSet
	 * @author:jackwang
	 *            <p>
	 */
	public static void getLeafNodesIdSet(DefaultKingdeeTreeNode node, Set leafNodesIdSet) {

		int count = node.getChildCount();
		// 如果没有下级节点，说明当前组织是实体，把当前组织id返回即可
		if (count == 0) {

			OrgStructureInfo orgStructureInfo = ((OrgStructureInfo) ((DefaultKingdeeTreeNode) node).getUserObject());

			String oid = orgStructureInfo.getUnit().getId().toString();
			leafNodesIdSet.add(oid);
			return;
		}
		DefaultKingdeeTreeNode treeNode = null;
		for (int i = 0; i < count; i++) {
			treeNode = (DefaultKingdeeTreeNode) node.getChildAt(i);
			if (treeNode.getUserObject() instanceof OrgStructureInfo) {
				if (treeNode.isLeaf()) {
					String id = ((OrgStructureInfo) treeNode.getUserObject()).getUnit().getId().toString();
					leafNodesIdSet.add(id);
				} else {
					getLeafNodesIdSet(treeNode, leafNodesIdSet);
				}
			} else {
				OrgStructureInfo orgStructureInfo = ((OrgStructureInfo) ((DefaultKingdeeTreeNode) node).getUserObject());
				String oid = orgStructureInfo.getUnit().getId().toString();
				leafNodesIdSet.add(oid);

			}
		}

	}
}