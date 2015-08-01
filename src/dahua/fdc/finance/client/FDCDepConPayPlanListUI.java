/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.UIManager;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDTreeView;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.BizEnumValueDTO;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.INewOrgF7Facade;
import com.kingdee.eas.basedata.org.IOrgTree;
import com.kingdee.eas.basedata.org.NewOrgF7FacadeFactory;
import com.kingdee.eas.basedata.org.OrgAssistInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgStructureCollection;
import com.kingdee.eas.basedata.org.OrgStructureFactory;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgTreeFactory;
import com.kingdee.eas.basedata.org.OrgTreeInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.helper.OrgTypeInfoMgr;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientUtils;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanBillFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.batchHandler.UtilRequest;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 合同月度滚动付款计划
 */
public class FDCDepConPayPlanListUI extends AbstractFDCDepConPayPlanListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(FDCDepConPayPlanListUI.class);
	String flag_state = "";
	
	private OrgTreeInfo orgTree;

	public FDCDepConPayPlanListUI() throws Exception {
		super();
	}

	protected void execQuery() {
		try {
			treeSelectChange();
		} catch (Exception e) {
			handUIException(e);
		}
		super.execQuery();
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			BizEnumValueDTO fde = (BizEnumValueDTO) tblMain.getRow(i).getCell(
					"state").getValue();
			if (fde != null
					&& FDCBillStateEnum.BACK_VALUE.equals(fde.getValue())) {
				tblMain.getRow(i).getStyleAttributes().setFontColor(Color.RED);
			}
		}
	}

	protected SorterItemCollection getSorter() {
		SorterItemCollection sorter = new SorterItemCollection();
		sorter.add(new SorterItemInfo("sort1"));
		sorter.add(new SorterItemInfo("sort2"));
		SorterItemInfo num = new SorterItemInfo("number");
		num.setSortType(SortType.DESCEND);
		sorter.add(num);
		SorterItemInfo ver = new SorterItemInfo("version");
		ver.setSortType(SortType.DESCEND);
		sorter.add(ver);
		return sorter;
	}

	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		checkSelected();
		actionRemove.setEnabled(false);
		actionEdit.setEnabled(false);
		int row = e.getSelectBlock().getEndRow();
		actionAudit.setEnabled(false);
		actionUnAudit.setEnabled(false);
		actionPublish.setEnabled(false);
		actionRevise.setEnabled(false);
		BizEnumValueDTO fde = (BizEnumValueDTO) tblMain.getRow(row).getCell(
				"state").getValue();
		if (fde.getValue().equals(FDCBillStateEnum.SAVED_VALUE)
				|| fde.getValue().equals(FDCBillStateEnum.SUBMITTED_VALUE)) {
			actionEdit.setEnabled(true);
			actionRemove.setEnabled(true);
		}
		if (fde.getValue().equals(FDCBillStateEnum.SUBMITTED_VALUE)) {
			actionAudit.setEnabled(true);
			actionRemove.setEnabled(true);
		}
		if (fde.getValue().equals(FDCBillStateEnum.AUDITTED_VALUE)) {
			actionUnAudit.setEnabled(true);
			actionPublish.setEnabled(true);
		}
		if (fde.getValue().equals(FDCBillStateEnum.BACK_VALUE)) {
			actionRevise.setEnabled(true);
		}
		if (KDTableUtil.getSelectedRowCount(tblMain) > 1) {
			actionRevise.setEnabled(false);
			actionEdit.setEnabled(false);
		}

	}

	protected boolean isOrderByIDForBill() {
		return false;
	}

	protected void treeSelectChange() throws Exception {
		FilterInfo filter = null;
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeProject.getLastSelectedPathComponent();
		if (node != null && node.isLeaf()) {
			actionAddNew.setEnabled(true);
		} else {
			actionAddNew.setEnabled(false);
		}
//		if (node != null && node.getUserObject() instanceof FullOrgUnitInfo) {
		if (node != null && node.getUserObject() instanceof OrgStructureInfo) {
		
			FullOrgUnitInfo fullinfo = ((OrgStructureInfo) node.getUserObject()).getUnit();
			logger.warn("treenode:" + fullinfo.getLongNumber());
			AdminOrgUnitInfo adminInfo = AdminOrgUnitFactory
					.getRemoteInstance().getAdminOrgUnitInfo(
							new ObjectUuidPK(fullinfo.getId()));
			logger.warn("AdminOrgUnit:" + adminInfo.getLongNumber());
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("deptment.id", adminInfo.getId().toString(), CompareType.EQUALS));
			this.mainQuery.setFilter(filter);
		}
		logger.warn(filter);
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) treeProject.getModel().getRoot();
		if (root == node) {
			tblMain.removeRows();
			tblMain.getSelectManager().removeAll();
		}
	}

	public void buildProjectTree() {/*
		AdminOrgUnitInfo currentAdminUnit = SysContext.getSysContext().getCurrentAdminUnit();
		logger.warn("CurrentAdminUnit:" + currentAdminUnit.getLongNumber());
		FullOrgUnitInfo adminOUInfo = null;
		try {
			adminOUInfo = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(currentAdminUnit.getId()));
		} catch (EASBizException e1) {
			e1.printStackTrace();
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
		if (adminOUInfo == null) {
			adminOUInfo = currentAdminUnit.castToFullOrgUnitInfo();
		}
		logger.warn("CastFullOrgUnit:" + adminOUInfo.getLongNumber());
		if (adminOUInfo != null) {
			try {
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("longNumber", adminOUInfo.getLongNumber() + "%", CompareType.LIKE));
				filter.getFilterItems().add(new FilterItemInfo("isFreeze", new Integer(0)));

				CtrlUnitInfo obj = SysContext.getSysContext().getCurrentCtrlUnit();
				logger.warn("CtrlUnit:" + obj.getLongNumber());
				String cuId = obj.getId().toString();
//				if (cuId != null) {
//					filter.getFilterItems().add(new FilterItemInfo("CU.id", cuId));
//				}

				// 用户组织范围内的组织才能选择
				try {
					Set authorizedOrgs = new HashSet();
					Map orgs = (Map) ActionCache.get("FDCBillEditUIHandler.authorizedOrgs");
					if (orgs == null) {
						orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(
								new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId()), OrgType.Admin, null, null, null);
					}
					if (orgs != null) {
						Set orgSet = orgs.keySet();
						Iterator it = orgSet.iterator();
						while (it.hasNext()) {
							authorizedOrgs.add(it.next());
						}
					}
					FilterInfo filterID = new FilterInfo();
					filterID.getFilterItems().add(new FilterItemInfo("id", authorizedOrgs, CompareType.INCLUDE));

					filter.mergeFilter(filterID, "and");

				} catch (Exception e) {
					e.printStackTrace();
				}
				logger.warn("Filter:" + filter);
				ITreeBuilder treeBuilder = TreeBuilderFactory.createTreeBuilder(new DefaultLNTreeNodeCtrl(FullOrgUnitFactory
						.getRemoteInstance()), 10, 10, filter);
				treeBuilder.buildTree(treeProject);
				treeProject.setSelectionNode((DefaultKingdeeTreeNode) treeProject.getModel().getRoot());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	*/
//		  OrgTreeInfo selectInfo = (OrgTreeInfo) this.cbxMain.getSelectedItem();
//	        if (selectInfo == null) {
//	            treeProject.setModel(null);
//	            this.mainQuery = null;
//	            return;
//	        }
	        
		
		KDTreeNode root = null;
		try {
	        OrgAssistInfo	assistInfo = buildNewOrgF7AssistInfo();
			
	    	INewOrgF7Facade facade = NewOrgF7FacadeFactory.getRemoteInstance();
	    	AdminOrgUnitInfo adminUnit = SysContext.getSysContext().getCurrentAdminUnit();
	    	List orgCol = new ArrayList();
	    	if(adminUnit!=null){
	    		if(OrgConstants.DEF_CU_ID.equals(adminUnit.getId().toString())){
//	    		if(false){
	    			orgCol = facade.getDirectChildren(assistInfo, null);
	    		}else{
	    			OrgStructureCollection orgStructureCollection = OrgStructureFactory.getRemoteInstance().getOrgStructureCollection("select * where unit.id ='"+adminUnit.getId().toString()
	    					+"' and tree.id='"+orgTree.getId().toString()+"' and isValid=1 ");
	    			orgStructureCollection.get(0).setUnit(adminUnit);
	    			orgCol.add(orgStructureCollection.get(0));
	    			orgCol.addAll(facade.getDirectChildren(assistInfo, orgStructureCollection.get(0)));
	    		}
	    	}else{
	    		orgCol = facade.getDirectChildren(assistInfo, null);
	    	}
			
	        if (orgCol != null && orgCol.size() > 0) {
				root = new KDTreeNode(orgCol.get(0));
			}else{
				treeProject.setModel(null);
	            this.mainQuery = null;
	            return;
			}
	        orgCol.remove(0);
	        insertNodeIntoRoot(root, orgCol);
	        ((OrgStructureInfo)root.getUserObject()).put("alreadyLoad", true);
			treeProject.setModel(new DefaultTreeModel(root){
				public boolean isLeaf(Object node){
					OrgStructureInfo info = (OrgStructureInfo)((KDTreeNode)node).getUserObject();
	        		if(info == null || info.isIsLeaf()){
	        			return true;
	        		}
	        		return false;
				}
			});
			
			} catch (BOSException e) {
				handUIExceptionAndAbort(e);
			} catch (EASBizException e) {
				handUIExceptionAndAbort(e);
			}
	        
//	        if (isQueryBySortCode) {
//	            NewOrgViewHelper.reBuildBySortCode(treeProject.getModel(), selectInfo);
//	        }
	        
	        treeProject.setRootVisible(true);
	        treeProject.setShowsRootHandles(true);
	        treeProject.setSelectionNode(root);
	
	
	}
	
	private void addTreeListener() {
    	this.treeProject.addTreeWillExpandListener(new TreeWillExpandListener() {
            public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {}
            public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
                DefaultKingdeeTreeNode tmp = (DefaultKingdeeTreeNode)event.getPath().getLastPathComponent();
                if (tmp == null) {
        			return;
        		}
        		try {
                    loadNode(tmp);
                } catch (Exception e) {
                    logger.error("error",e);
                }
            }
        });
	}
	
	  private void loadNode(DefaultKingdeeTreeNode tmp) throws EASBizException, BOSException {
	    	if (tmp != null && tmp.getUserObject() != null) {
	    		OrgStructureInfo unitInfo = (OrgStructureInfo)tmp.getUserObject();
				if (unitInfo.get("alreadyLoad") == null ) {
					OrgAssistInfo assistInfo = buildNewOrgF7AssistInfo();
					INewOrgF7Facade facade = NewOrgF7FacadeFactory.getRemoteInstance();
					List orgCol = facade.getDirectChildren(assistInfo, unitInfo);
					if (null == orgCol || orgCol.size() == 0) {
						tmp.setCustomIcon(UIManager.getIcon("Tree.leafIcon"));
					}
					insertNodeIntoRoot(tmp, orgCol);
					unitInfo.put("alreadyLoad", true);
				}
			}
		}
	
	private void insertNodeIntoRoot(DefaultKingdeeTreeNode root, List orgCol) {
		// 用户组织范围内的组织才能选择
		Set authorizedOrgs = new HashSet();
		try {
			Map orgs = (Map) ActionCache.get("FDCBillEditUIHandler.authorizedOrgs");
			if (orgs == null) {
				orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(
						new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId()), OrgType.Admin, null, null, null);
			}
			if (orgs != null) {
				Set orgSet = orgs.keySet();
				Iterator it = orgSet.iterator();
				while (it.hasNext()) {
					authorizedOrgs.add(it.next());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		for (int i = 0; i < orgCol.size(); i++) {
			OrgStructureInfo info = (OrgStructureInfo)orgCol.get(i);
			if(authorizedOrgs.contains(info.getUnit().getId().toString())){
				KDTreeNode treeNode = new KDTreeNode(info);
				root.add(treeNode);
			}
		}
	}
	
	 private OrgAssistInfo buildNewOrgF7AssistInfo() throws BOSException {
	    	OrgAssistInfo assistInfo = new OrgAssistInfo();
	    	String treeID = orgTree.getId().toString();
				assistInfo.setNeedAddSealUpFilter(false);
				assistInfo.setNeedAddVirtualFilter(true);
			assistInfo.setNeedAddAssistantFilter(true); 
	    	assistInfo.setOrgViewType(OrgTypeInfoMgr.getInstance().getOrgTypeInfo(treeID).getOrgViewType());
	    	assistInfo.setTreeID(treeID);
	    	assistInfo.setStructure(true);
	    	
//	    	assistInfo.setNeedAddOrgRange(true);
	    	
	    	List decorators = new ArrayList();
			decorators.add("com.kingdee.eas.basedata.org.app.helper.StructTreeNode4OUViewAssemblage");
			decorators.add("com.kingdee.eas.basedata.org.app.helper.NormalFilter4F7Decorator");
			decorators.add("com.kingdee.eas.basedata.org.app.helper.OrgRangeFilter4F7Decorator");
			assistInfo.setConstructParam(decorators);
			return assistInfo;
		}
	

	protected void treeProject_valueChanged(TreeSelectionEvent e) throws Exception {
		treeSelectChange();
		this.refresh(null);
	}

	/**
	 * 
	 * 描述：判断成本中心是否封存了
	 * @throws BOSException 
	 * @throws EASBizException 
	 * @Author：jian_cao
	 * @CreateTime：2012-9-1
	 */
	private void costCenterIsSealUp() throws EASBizException, BOSException {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeProject.getLastSelectedPathComponent();
		
		FullOrgUnitInfo info = ((OrgStructureInfo) node.getUserObject()).getUnit();
//		FullOrgUnitInfo info = (FullOrgUnitInfo) node.getUserObject();
		if (!CostCenterOrgUnitFactory.getRemoteInstance().exists(new ObjectUuidPK(info.getId()))) {
			MsgBox.showWarning("请先设置成本中心！");
			SysUtil.abort();
		}
		if (CostCenterOrgUnitFactory.getRemoteInstance().getCostCenterOrgUnitInfo(new ObjectUuidPK(info.getId())).isIsSealUp()) {
			MsgBox.showWarning("该成本中心已经封存不能新增！");
			SysUtil.abort();
		}
	}
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		
		costCenterIsSealUp();
		// FDCClientUtils.checkSelectProj(this, getProjSelectedTreeNode());
		// FDCClientUtils.checkProjWithCostOrg(this, getProjSelectedTreeNode());

		// 如果工程项目对应的财务组织没有做付款计划周期，不允许新增
		// CurProjectInfo curProj = (CurProjectInfo)
		// getProjSelectedTreeNode().getUserObject();
		// String fullOrgId = curProj.getFullOrgUnit().getId().toString();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select FID ,FIsEnabled from  T_FDC_PayPlanCycle where FIsEnabled = 1");
		IRowSet rowSet = builder.executeQuery();
		if (rowSet == null || rowSet.size() == 0) {
			// MsgBox.showWarning("该组织下没做付款计划周期，不能新增！");
			MsgBox.showWarning("没有付款计划周期或没有启用付款计划周期，不能新增！");
			SysUtil.abort();
		}
		super.actionAddNew_actionPerformed(e);
	}

	protected void checkBeforeAddNew(ActionEvent e) {
		// super.checkBeforeAddNew(e);
	}

	/**
	 * @see com.kingdee.eas.framework.client.ListUI#confirmRemove()
	 */
	protected boolean confirmRemove() {
		if (MsgBox.isYes(MsgBox.showConfirm2(this, EASResource.getString(FrameWorkClientUtils.strResource + "Confirm_Delete")))) {
			int actRowIdx = tblMain.getSelectManager().getActiveRowIndex();
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql("select fid as id from T_FNC_FDCDepConPayPlanBill where Fnumber = ? and Fversion =?  ");
			String number = tblMain.getCell(actRowIdx, "number").getValue().toString();
			double versions = Double.parseDouble(tblMain.getCell(actRowIdx, "version").getValue().toString()) - 0.1;
			DecimalFormat myformat = new DecimalFormat("#0.0");
			String version = myformat.format(versions);
			String state = tblMain.getCell(actRowIdx, "state").getValue().toString();
			//如果版本大于等于1.0
			if (versions >= 1.0) {
				//如果当前版本是“提交”和“保存”状态的情况下更新上个版本为打回
				if (FDCBillStateEnum.SUBMITTED.getAlias().equals(state) || FDCBillStateEnum.SAVED.getAlias().equals(state)) {
					builder.addParam(number);
					builder.addParam(version);
					try {
						IRowSet rowSet = builder.executeQuery();
						builder.clear();
						if (rowSet != null && rowSet.size() >= 1) {
							rowSet.next();
							String id = rowSet.getString("id");

							builder.appendSql("update T_FNC_FDCDepConPayPlanBill set  FState='11BACK' where fid =?  ");
							builder.addParam(id);
							builder.execute();
						}
					} catch (Exception e) {
						handUIExceptionAndAbort(e);
					}
				}
			}
			return true;
		} else {
			return false;
		}
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		// FDCClientUtils.checkSelectProj(this, getProjSelectedTreeNode());
		// FDCClientUtils.checkProjWithCostOrg(this, getProjSelectedTreeNode());
		checkSelected();
		super.actionRemove_actionPerformed(e);
	}

	protected void audit(List ids) throws Exception {
		FDCDepConPayPlanBillFactory.getRemoteInstance().audit(ids);
	}

	protected void unAudit(List ids) throws Exception {
		FDCDepConPayPlanBillFactory.getRemoteInstance().unAudit(ids);
	}

	protected String getEditUIName() {
		return FDCDepConPayPlanEditUI.class.getName();
	}

	protected ICoreBase getRemoteInterface() throws BOSException {
		return FDCDepConPayPlanBillFactory.getRemoteInstance();
	}

	protected void updateButtonStatus() {
		// 如果是虚体财务组织，则不能增、删、改
		// if (!SysContext.getSysContext().getCurrentFIUnit().isIsBizUnit()) {
		// actionAddNew.setEnabled(false);
		// actionEdit.setEnabled(false);
		// actionRemove.setEnabled(false);
		// actionAddNew.setVisible(false);
		// actionEdit.setVisible(false);
		// actionRemove.setVisible(false);
		// menuEdit.setVisible(false);
		// }
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		// super.prepareUIContext(uiContext, e);

		Object o = getProjSelectedTreeNode().getUserObject();
		if (o != null && o instanceof CurProjectInfo) {
			CurProjectInfo curProj = (CurProjectInfo) o;
			getUIContext().put("selectedProj", curProj);
		} 
		getUIContext().put("currentAdmit",
				SysContext.getSysContext().getCurrentAdminUnit());
//		if (o != null && o instanceof FullOrgUnitInfo) {
		if (o != null && o instanceof OrgStructureInfo) {
			FullOrgUnitInfo info = ((OrgStructureInfo) o).getUnit();;
			getUIContext().put("department", info);
		}
	}

	Set projectLeafset = null;
	String selectOrgId = null;

	protected FilterInfo getTreeFilter() throws Exception {
		return null;
		// return super.getTreeFilter();
	}

	protected void checkBeforeEdit(ActionEvent e) throws Exception {
		if (!flag_state.equals("revist")) {
			super.checkBeforeEdit(e);
		}
	}

	public void onShow() throws Exception {
		super.onShow();
		this.btnLocate.setVisible(false);
		this.btnAttachment.setVisible(false);
		this.btnPrint.setVisible(false);
		this.btnPrintPreview.setVisible(false);

		this.menuItemLocate.setVisible(false);
		this.MenuItemAttachment.setVisible(false);
		this.menuItemPrint.setVisible(false);
		this.menuItemPrintPreview.setVisible(false);
	}

	public void onLoad() throws Exception {
		if (SysContext.getSysContext().getCurrentAdminUnit() == null) {
			MsgBox.showWarning("只有末级行政组织才能编制合同月度滚动付款计划，请切换组织!");
			abort();
		}
		
		IOrgTree iTree = OrgTreeFactory.getRemoteInstance();
		orgTree = iTree.getOrgTreeInfo("select id,name,type where type = 0 order by id");

		// 如此设置便能保证即使我们切换到了财务组织下边，部门合同付款计划序时簿左边的树上也可以显示出来工程项目
		// OrgUnitInfo currentOrgUnit =
		// SysContext.getSysContext().getCurrentOrgUnit();
		// try {
		//SysContext.getSysContext().setCurrentOrgUnit(SysContext.getSysContext(
		// ).getCurrentFIUnit());
			super.onLoad();
		// } finally {
		// SysContext.getSysContext().setCurrentOrgUnit(currentOrgUnit);
		// }

		//		FDCBaseDataClientUtils.setupUITitle(this, "合同月度滚动付款计划");
			
		
		addTreeListener();
		FDCBaseDataClientUtils.setupUITitle(this, "部门月度付款计划");
		btnPublish.setIcon(EASResource.getIcon("imgTbtn_timingrefer"));
		menuItemPublish.setIcon(EASResource.getIcon("imgTbtn_timingrefer"));
		tbnRevise.setIcon(EASResource.getIcon("imgTbtn_emend"));
		menuItemRevise.setIcon(EASResource.getIcon("imgTbtn_emend"));
		btnBack.setIcon(EASResource.getIcon("imgTbtn_untreadreport"));
		KDTreeView treeView = new KDTreeView();
		treeView.setTree(treeProject);
		treeView.setShowButton(false);
		// 标题为“行政组织”
		treeView.setTitle(EASResource.getString("com.kingdee.eas.basedata.org.client.OrgResource", "ADMINORG_F7TITLE"));
		
		treeView.setShowControlPanel(true);
		kDSplitPane1.add(treeView, "left");

		this.btnBack.setVisible(false);
	}

	public void actionBack_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		List selectedIdValues = ContractClientUtils.getSelectedIdValues(
				getMainTable(), getKeyFieldName());
		FDCDepConPayPlanBillFactory.getRemoteInstance().back(selectedIdValues);
		showOprtOKMsgAndRefresh();
		super.actionBack_actionPerformed(e);
	}

	public void actionPublish_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		if (MsgBox.showConfirm2("是否确定上报？") == MsgBox.CANCEL) {
			return;
		}
		List selectedIdValues = ContractClientUtils.getSelectedIdValues(
				getMainTable(), getKeyFieldName());
		FDCDepConPayPlanBillFactory.getRemoteInstance().publish(
				selectedIdValues);
		showOprtOKMsgAndRefresh();
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int actRowIdx = tblMain.getSelectManager().getActiveRowIndex();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder
				.appendSql("select fid as id from T_FNC_FDCDepConPayPlanBill where Fnumber = ? and Fversion =?  ");
		String number = tblMain.getCell(actRowIdx, "number").getValue()
				.toString();
		double versions = Double.parseDouble(tblMain.getCell(actRowIdx,
				"version").getValue().toString()) - 0.1;
		DecimalFormat myformat = new DecimalFormat("#0.0");
		String version = myformat.format(versions);
		if (versions >= 1.0 && FDCBillStateEnum.SUBMITTED.getAlias().equals(tblMain.getCell(actRowIdx, "state").getValue().toString())) {
			builder.addParam(number);
			builder.addParam(version);
			IRowSet rowSet = builder.executeQuery();
			builder.clear();
			if (rowSet != null && rowSet.size() >= 1) {
				rowSet.next();
				String id = rowSet.getString("id");

				builder
						.appendSql("update T_FNC_FDCDepConPayPlanBill set  FState='12REVISE' where fid =?  ");
				builder.addParam(id);
				builder.execute();
			}
		}

		super.actionAudit_actionPerformed(e);
	}

	public void actionRevise_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		flag_state = "revist";
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		uiContext.put("flag_state", flag_state);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(getEditUIName(), uiContext, null, OprtState.EDIT);
		uiWindow.show();
		if (isDoRefresh(uiWindow)) {
			if (UtilRequest.isPrepare("ActionRefresh", this)) {
				prepareRefresh(null).callHandler();
			}
			setLocatePre(false);
			refresh(e);
			setPreSelecteRow();
			setLocatePre(true);
		}
	}

	// 拆分后不能反审批
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		// List idList = ContractClientUtils.getSelectedIdValues(getMainTable(),
		// getKeyFieldName());
		// FilterInfo filter = new FilterInfo();
		// filter.getFilterItems().add(
		// new FilterItemInfo("splitPayPlanBill.id", FDCHelper
		// .list2Set(idList), CompareType.INCLUDE));
		// boolean isDepSplit =
		// DepConPayPlanSplitBillFactory.getRemoteInstance()
		// .exists(filter);
		// if (isDepSplit) {
		// FDCMsgBox.showWarning(this, "您所选择的单据存在已被拆分的对象!");
		// this.abort();
		// }
		int actRowIdx = tblMain.getSelectManager().getActiveRowIndex();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder
				.appendSql("select fid as id from T_FNC_FDCDepConPayPlanBill where Fnumber = ? and Fversion =?  ");
		String number = tblMain.getCell(actRowIdx, "number").getValue()
				.toString();
		double versions = Double.parseDouble(tblMain.getCell(actRowIdx,
				"version").getValue().toString()) - 0.1;
		DecimalFormat myformat = new DecimalFormat("#0.0");
		String version = myformat.format(versions);
		if (versions >= 1.0
				&& FDCBillStateEnum.AUDITTED.getAlias().equals(tblMain.getCell(actRowIdx, "state").getValue().toString())) {
			builder.addParam(number);
			builder.addParam(version);
			IRowSet rowSet = builder.executeQuery();
			builder.clear();
			if (rowSet != null && rowSet.size() >= 1) {
				rowSet.next();
				String id = rowSet.getString("id");

				builder.appendSql("update T_FNC_FDCDepConPayPlanBill set  FState=? where fid =?  ");
				builder.addParam(FDCBillStateEnum.REVISING_VALUE);
				builder.addParam(id);
				builder.execute();
			}
		}
		super.actionUnAudit_actionPerformed(e);
	}
}