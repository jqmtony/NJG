/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.NodeClickListener;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.AcctAccreditHelper;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ICostAccount;
import com.kingdee.eas.fdc.basedata.ICurProject;
import com.kingdee.eas.fdc.basedata.ProjectStatusInfo;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUCollection;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUFactory;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.client.ConChangeSplitEditUI;
import com.kingdee.eas.fdc.contract.client.ContractCostSplitEditUI;
import com.kingdee.eas.fdc.finance.client.PaymentSplitEditUI;
import com.kingdee.eas.fdc.finance.client.PaymentSplitWithoutTxtConEditUI;
import com.kingdee.eas.fdc.finance.client.WorkLoadSplitEditUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 成本科目引入
 */
public class CostSplitAcctUI extends AbstractCostSplitAcctUI {
	private static final Logger logger = CoreUIObject.getLogger(CostSplitAcctUI.class);
	private static final Color canntSelectColor = new Color(0xFEFED3);
	private CurProjectInfo addressProject = null;

	private FilterInfo filter = null;
	
	private boolean isFinacial = false;// 判断财务一体化是否启用状态值
	
	private CostAccountCollection cac = new CostAccountCollection();
    
    private boolean isOk = false;		//add by jelon
    private CostAccountCollection cacheCostAccoutCollection = new CostAccountCollection();
    private CurProjectInfo curProject = null;
	private Map parentMap = null;
	//项目ID集合
	private Set curProjectCollection = new HashSet();
	/**
	 * output class constructor
	 */
	public CostSplitAcctUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
//		tblMain.checkParsed();// table解析!
//		this.tblMain.getDataRequestManager().addDataFillListener(new KDTDataFillListener() {
//			public void afterDataFill(KDTDataRequestEvent e) {
//				// do something
//				String strTemp = "";
//				for (int i = e.getFirstRow(); i <= e.getLastRow(); i++) {
//					strTemp = tblMain.getRow(i).getCell(1).getValue().toString();
//					strTemp = strTemp.replace('!', '.');
//					tblMain.getRow(i).getCell(1).setValue(strTemp);
//					tblMain.getRow(i).getCell(0).setValue(strTemp);
//					tblMain.getRow(i).getCell(2).setValue(strTemp);
//				}
//			}
//		});
		super.onLoad();
		initFinacial();
		// 启用复杂模式成本财务一体化的处理
		onFinacial();
		this.btnAllSelect.setIcon(EASResource.getIcon("imgTbtn_selectall"));
//		this.btnAllSelect.setText("全选");
		this.btnNoneSelect.setIcon(EASResource.getIcon("imgTbtn_deleteall"));
//		this.btnNoneSelect.setText("全清");
		if (getUIContext().get("address") != null) {
			if (getUIContext().get("address") instanceof FullOrgUnitInfo) {
				
			} else if (getUIContext().get("address") instanceof CurProjectInfo) {
				addressProject = (CurProjectInfo) getUIContext().get("address");
			}
		}
		//获取合同拆分和变更拆分的项目树
		if (getUIContext().get("Owner") != null) {
			String className = getUIContext().get("Owner").getClass().getName();
			if (className.equals(WorkLoadSplitEditUI.class.getName())
					|| className.equals(PaymentSplitEditUI.class.getName())) {
				getCurProjectInfos();
			}
		}
		buildProjectTree();
		
		
		this.treeMain.setSelectionRow(0);
//		loadDatas(filter);
	}
	public void onShow() throws Exception {
		super.onShow();
		selectCurOrgTreeNode();
		this.buiderLevelButtom(curProject);
	}
	/**
	 * 初始时选中当前结点
	 * @author sxhong  		Date 2007-3-6
	 * @throws BOSException
	 */
	private void selectCurOrgTreeNode() throws BOSException {
		final OrgUnitInfo currentOrgUnit = SysContext.getSysContext().getCurrentOrgUnit();
		final CompanyOrgUnitInfo currentFIUnit = SysContext.getSysContext().getCurrentFIUnit();
		
		CurProjectInfo curProj=(CurProjectInfo)getUIContext().get("curProject");
		if(curProj==null){
			if(currentOrgUnit.isIsCostOrgUnit()){
				EntityViewInfo view=new EntityViewInfo();
				FilterInfo myfilter=new FilterInfo();
				myfilter.appendFilterItem("costCenterOU.id", currentOrgUnit.getId().toString());
				view.setFilter(myfilter);
				final ProjectWithCostCenterOUCollection projectWithCostCenterOUCollection = ProjectWithCostCenterOUFactory.getRemoteInstance().getProjectWithCostCenterOUCollection(view);
				if(projectWithCostCenterOUCollection.size()==1){
					curProj=projectWithCostCenterOUCollection.get(0).getCurProject();
				}
				
			}
		}
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode)treeMain.getModel().getRoot();
		DefaultKingdeeTreeNode node=root;
		while(node!=null){
			if(node.getUserObject() instanceof OrgStructureInfo){
				final OrgStructureInfo info = (OrgStructureInfo)node.getUserObject();
				
				//删除其它组织下的树
				if(info.getUnit().getId().equals(currentFIUnit.getId())){
					deleteOtherOrgNode(node);
				}
//				
//				if(info.getUnit().getId().equals(currentOrgUnit.getId())){
//					treeMain.setSelectionNode(node);
//					int row=treeMain.getSelectionRows()[0];
//					treeMain.expandRow(row);
//					break;
//				}
			}else if(curProj!=null&&node.getUserObject() instanceof CurProjectInfo){
				CurProjectInfo info =(CurProjectInfo) node.getUserObject();
				if(info.getId().equals(curProj.getId())){
					treeMain.setSelectionNode(node);
					int row=treeMain.getSelectionRows()[0];
					treeMain.expandRow(row);
					curProject = info;
					break;
				}
			}
			node=(DefaultKingdeeTreeNode)node.getNextNode();
		}
	
	}
	
	private void deleteOtherOrgNode(DefaultKingdeeTreeNode node){
		DefaultKingdeeTreeNode curNode=node;
		DefaultKingdeeTreeNode parentNode=(DefaultKingdeeTreeNode)curNode.getParent();
		while(parentNode!=null){
			treeMain.removeAllChildrenFromParent(parentNode);
			treeMain.insertNodeInto(curNode, parentNode, 0);
//			parentNode.removeAllChildren();
//			parentNode.add(node);
			curNode=parentNode;
			parentNode=(DefaultKingdeeTreeNode)curNode.getParent();
		}
	}
	private void buildProjectTree() throws Exception {
		//不显示已经禁用的工程项目（考虑是否组织隔离？）	jelon 12/30/2006
		Boolean isPreSplit = Boolean.valueOf(false);
		if(getUIContext().get("isPreSplit") != null)
		{
			isPreSplit = (Boolean)getUIContext().get("isPreSplit");
		}
		
		if(!isPreSplit.booleanValue())
		{
			ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder(false, true,
					curProjectCollection);
			//组织隔离
			//		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder(false, false);

			treeMain.setShowsRootHandles(true);
			projectTreeBuilder.build(this, treeMain, actionOnLoad);
		}
		else
		{
			ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder(false, false,
					curProjectCollection);

			treeMain.setShowsRootHandles(true);
			projectTreeBuilder.build(this, treeMain, actionOnLoad);
		}

	}

	private void addSelector(EntityViewInfo evInfo) {
		evInfo.getSelector().add("id");
		evInfo.getSelector().add("longNumber");
		evInfo.getSelector().add("name");
		//add begin jelon
		SelectorItemCollection selectors=evInfo.getSelector();
        //selectors.add("id");
		//selectors.add("name");
		selectors.add("number");
		//selectors.add("longNumber");		
		selectors.add("displayName");	
		selectors.add("level");;	
		selectors.add("isLeaf");
		// 可拆分成本
		selectors.add("isSplit");
		
		selectors.add("codingNumber");
		selectors.add("curProject");
		selectors.add("curProject.id");
		selectors.add("curProject.number");
		selectors.add("curProject.longNumber");
		selectors.add("curProject.name");
		selectors.add("curProject.displayName");
		selectors.add("curProject.level");
		selectors.add("curProject.isLeaf");

		 selectors.add("parent.id");
		 selectors.add("parent.name");
		//add end jelon
	}

	private void loadDatas(FilterInfo filter) throws BOSException {
		this.tblMain.removeRows();
		ICostAccount iCostAccount = CostAccountFactory.getRemoteInstance();
		EntityViewInfo evInfo = new EntityViewInfo();
		
		addSelector(evInfo);
		
		DefaultKingdeeTreeNode treeNode = getProjSelectedTreeNode();
		if(treeNode.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) treeNode.getUserObject();
			AcctAccreditHelper.handAcctAccreditFilter(null, projectInfo.getId().toString(), filter);
		}
	
		evInfo.setFilter(filter);

		SorterItemInfo sii = new SorterItemInfo();
		sii.setPropertyName("longNumber");
		sii.setSortType(SortType.ASCEND);
		evInfo.getSorter().add(sii);
		CostAccountCollection costAccountCollection = iCostAccount.getCostAccountCollection(evInfo);
		
		/**************************** 工程量成本拆分选择成本科目引用合同拆分和变更拆分的科目 *******************************/
		 if (getUIContext().get("Owner") != null) {
			String className = getUIContext().get("Owner").getClass().getName();
			if (className.equals(WorkLoadSplitEditUI.class.getName())
					|| className.equals(PaymentSplitEditUI.class.getName())) {
				costAccountCollection = getContractSlpitAndChangeSplitCostAccounts();
			}
		}
		/**************************** 工程量成本拆分选择成本科目引用合同拆分和变更拆分的科目 *******************************/
		
		tblMain.checkParsed();// table解析!
		tblMain.getColumn("select").setWidth(50);
		tblMain.getColumn("number").setWidth(211);
		tblMain.getColumn("name").setWidth(130);
		tblMain.getColumn("id").getStyleAttributes().setHided(true);
		CellTreeNode node=null;
		
		/*** 可拆分成本科目逻辑增加 begin ***/
		parentMap = new HashMap();
		String pId = "";
		String Id = "";
		CostAccountInfo costAccountInfo = null;
		for (int i = 0; i < costAccountCollection.size(); i++) {
		    costAccountInfo = costAccountCollection.get(i);
			if (costAccountInfo.getParent() != null) {
				Id = costAccountInfo.getId().toString();
				pId = costAccountInfo.getParent().getId().toString();
				
				if (!Id.equals(pId)) {
					parentMap.put(pId, costAccountInfo.getParent().getName());
				}
			}
		}

		/*** 可拆分成本科目逻辑增加 end ***/
		
		if (costAccountCollection.size() != 0) {
			IRow row;
			for (int i = 0; i < costAccountCollection.size(); i++) {
				row = this.tblMain.addRow();
				node=new CellTreeNode();
				row.getCell("select").setValue(Boolean.valueOf(false));
			
//				row.getCell("number").setValue(costAccountCollection.get(i).getLongNumber().replace('!', '.'));
				costAccountInfo = costAccountCollection.get(i);
				
				row.getCell("name").setValue(costAccountInfo.getName(SysContext.getSysContext().getLocale()));
				row.getCell("id").setValue(costAccountInfo.getId().toString());
				row.setUserObject(costAccountInfo);
				// row.getCell("isenabled").setValue(String.valueOf(costAccountCollection.get(j).getAsstAccount().getName()));
				
				node.setValue(costAccountInfo.getLongNumber().replace('!', '.'));
				final int level = costAccountInfo.getLevel() - 1;
				final boolean isLeaf = costAccountInfo.isIsLeaf();
				node.setTreeLevel(level);
				// 获得当前成本科目ID
				String id = costAccountInfo.getId().toString();
				// 增加判断，判断map里面是否存有为父节点的成本科目
				if (isLeaf || !parentMap.containsKey(id)) {		
					node.setHasChildren(false);
					if (level > 1) {
						row.getStyleAttributes().setHided(true);
					}
				}else{

					if(level<=1){
						if(level==0){
							node.setCollapse(false);
						}else{
							node.setCollapse(true);
						}
					}else{
						node.setCollapse(true);//是否只隐藏根结点
						row.getStyleAttributes().setHided(true);
					}
					

					node.addClickListener(new NodeClickListener(){
						public void doClick(CellTreeNode source, ICell cell,
								int type) {
							// source.getTreeLevel();
							tblMain.revalidate();
							
						}
					});
					node.setHasChildren(true);
					
					row.getStyleAttributes().setBackground(canntSelectColor);
				    
				}
			
				row.getCell("number").setValue(node);

				row.setTreeLevel(level);
			}

		}
//		tblMain.setPreferredSize(new Dimension(0,0));

	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output actionPageSetup_actionPerformed
	 */
	public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception {
		super.actionPageSetup_actionPerformed(e);
	}

	/**
	 * output actionExitCurrent_actionPerformed
	 */
	public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception {
		super.actionExitCurrent_actionPerformed(e);
	}

	/**
	 * output actionHelp_actionPerformed
	 */
	public void actionHelp_actionPerformed(ActionEvent e) throws Exception {
		super.actionHelp_actionPerformed(e);
	}

	/**
	 * output actionAbout_actionPerformed
	 */
	public void actionAbout_actionPerformed(ActionEvent e) throws Exception {
		super.actionAbout_actionPerformed(e);
	}

	/**
	 * output actionOnLoad_actionPerformed
	 */
	public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception {
		super.actionOnLoad_actionPerformed(e);
	}

	/**
	 * output actionSendMessage_actionPerformed
	 */
	public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception {
		super.actionSendMessage_actionPerformed(e);
	}

	/**
	 * output actionSave_actionPerformed method
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		IRow row;
		cac.clear();
		boolean flag = false;
		for (int i = 0, count = tblMain.getRowCount(); i < count; i++) {
			row = tblMain.getRow(i);
			if (((Boolean) row.getCell("select").getValue()).booleanValue()) {
				CostAccountInfo cai = (CostAccountInfo) row.getUserObject();
				cac.add(cai);
				flag = true;
			}
		}
		if (flag) {
			CostAccountFactory.getRemoteInstance().importDatas(cac, this.addressProject.getId());
			this.showResultMessage(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE,"Import_success"));
		}
	}

	/**
	 * output actionCancel_actionPerformed method
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		this.getUIWindow().close();
	}

	/**
	 * output actionAllSelect_actionPerformed method
	 */
	public void actionAllSelect_actionPerformed(ActionEvent e) throws Exception {
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			this.tblMain.getRow(i).getCell("select").setValue(Boolean.valueOf(true));
		}
	}

	/**
	 * output actionNoneSelect_actionPerformed method
	 */
	public void actionNoneSelect_actionPerformed(ActionEvent e) throws Exception {
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			this.tblMain.getRow(i).getCell("select").setValue(Boolean.valueOf(false));
		}
	}

	/**
	 * output treeMain_valueChanged method
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		treeSelectChange();
	}

	/**
	 * 
	 * 描述：左边树选择改变，重新构造条件执行查询
	 * 
	 * @author:liupd 创建时间：2006-7-25
	 *               <p>
	 */
	private void treeSelectChange() throws Exception {
		/*
		 * 工程项目树
		 */
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		//缓存已经选择了的成本科目
		cacheCostAccoutCollection.addCollection(getSelectCostAccounts());
		
//		if (OrgViewUtils.isTreeNodeDisable(node)) {
//			return;
//		}
		
		//招标立项预先拆分使用
		Boolean isPreSplit = Boolean.valueOf(false);
		if(getUIContext().get("isPreSplit") != null)
		{
			isPreSplit = (Boolean)getUIContext().get("isPreSplit");
		}
		Boolean isLeafProject = Boolean.valueOf(false) ;
		
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			
			if(isPreSplit.booleanValue())
			{
				isLeafProject = Boolean.valueOf(projectInfo.isIsLeaf());
			}
			buiderLevelButtom(projectInfo);
			filterItems.add(new FilterItemInfo("curProject.id", projectInfo.getId().toString(), CompareType.EQUALS));	
			//2009-05-22 需求确认 简单模式也不能拆分到已关闭、流失和已中转项目
//			if(isFinacial){
				//拆分数据不能拆分到已关闭、流失和已中转项目
				Set set=new HashSet();
				set.add(ProjectStatusInfo.closeID);
				set.add(ProjectStatusInfo.flowID);
				set.add(ProjectStatusInfo.transferID);
				filterItems.add(new FilterItemInfo("curProject.projectStatus.id",set,CompareType.NOTINCLUDE));
//			}
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
									
			OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
			if (oui == null || oui.getUnit() == null)
				return;		
			FullOrgUnitInfo info = oui.getUnit();
			
			if(isPreSplit.booleanValue())
			{
				isLeafProject = Boolean.valueOf(info.isIsLeaf());
			}
			buiderLevelButtom(info);
			//TODO 暂不允许选择公司的科目	jelon
			//filterItems.add(new FilterItemInfo("fullOrgUnit.id", info.getId().toString(), CompareType.EQUALS));		
			filterItems.add(new FilterItemInfo("fullOrgUnit.id", BOSUuid.create("null"), CompareType.EQUALS));
			
			
		}
		
		
//		if (getProjSelectedTreeNode() != null && getProjSelectedTreeNode().getUserObject() instanceof TreeBaseInfo) {
//			TreeBaseInfo projTreeNodeInfo = (TreeBaseInfo) getProjSelectedTreeNode().getUserObject();
//			BOSUuid id = projTreeNodeInfo.getId();
//			// 选择的是成本中心，取该成本中心及下级成本中心（如果有）下的所有合同
//			if (projTreeNodeInfo instanceof FullOrgUnitInfo) {
//				// Set idSet = ContractClientUtils.genOrgUnitIdSet(id);
//				// filterItems.add(new FilterItemInfo("fullOrgUnit.id", idSet,
//				// CompareType.INCLUDE));
//				filterItems.add(new FilterItemInfo("fullOrgUnit.id", id, CompareType.EQUALS));				
//			}
//			// 选择的是项目，取该项目下的所有源成本科目
//			else if (projTreeNodeInfo instanceof CurProjectInfo) {
//				// Set idSet = ContractClientUtils.genProjectIdSet(id);
//				// filterItems.add(new FilterItemInfo("curProject.id", idSet,
//				// CompareType.INCLUDE));
//				filterItems.add(new FilterItemInfo("curProject.id", id, CompareType.EQUALS));
//			}
//		}else{
//			return;
//		}
		
		
		//filterItems.add(new FilterItemInfo("isSource", Boolean.valueOf(true), CompareType.EQUALS));
		filterItems.add(new FilterItemInfo("isEnabled", Boolean.valueOf(true), CompareType.EQUALS));
		this.filter = filter;
		
		if(isPreSplit.booleanValue() && !isLeafProject.booleanValue())
		{
			tblMain.removeRows();
			return ;
		}
		
		/*** 增加只查看可拆分科目逻辑 begin （工程项目树改变时） ***/
		// 获得是否选中只查看可拆分科目
		boolean isSelectIsSplit = this.isSplitAcct.isSelected();
		Boolean isFinacial = (Boolean) getUIContext().get("isFinacial");
		if (isFinacial != null && isFinacial.booleanValue()) {
			isSelectIsSplit = false;
		}
		// 如果选中则过滤掉不可拆分的科目
		if (isSelectIsSplit) {
			filter.getFilterItems().add(new FilterItemInfo("isSplit", new Integer(1)));
			this.loadDatas(filter);
		} else {
			filter.remove("isSplit");
			//			this.isSplitAcct.setVisible(false);
			//			this.isSplitAcct.setEnabled(false);
			this.isSplitAcct.setSelected(false);
			this.loadDatas(filter);
		}
		// 复杂时，始终明细科目
		/*
		 * if (contractIsInvalid()) { // 如果合同作废过 filter.remove("isSplit");
		 * this.isSplitAcct.setEnabled(false);
		 * this.isSplitAcct.setSelected(false); this.loadDatas(filter); }
		 */
		
		/*** 增加只查看可拆分科目逻辑 end （工程项目树改变时） ***/
		
		for (int i = 0, count = tblMain.getRowCount(); cacheCostAccoutCollection.size()>0&&i < count; i++) {
			IRow row = tblMain.getRow(i);
			CostAccountInfo cai = (CostAccountInfo) row.getUserObject();
			if(cacheCostAccoutCollection.contains(cai)){
//                  row.getCell("select").setValue(Boolean.TRUE);
				select(i, false, true);
			}
		}
		if (this.tblMain.getRowCount() > 0) {
			tblMain.getSelectManager().select(0, 0);
		}
	}

	public DefaultKingdeeTreeNode getProjSelectedTreeNode() {
		return (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
	}
	protected void showResultMessage(String message) {
		setMessageText(message);
		showMessage();
	}
	
	
	
	
	//from here to end 		add by jelon
    protected void btnCancel2_actionPerformed(ActionEvent e) throws Exception {
    	setConfirm(false);
    	disposeUIWindow();
    	cacheCostAccoutCollection.clear();
    }
    
    protected void btnConfirm_actionPerformed(ActionEvent e) throws Exception {
    	confirm();
    }
    
	private void confirm() throws Exception {
		//checkSelected();
    	//getData();
    	setConfirm(true);
	}

    public CostAccountCollection getData() throws Exception {

//		IRow row;
		cac.clear();
		cac.addCollection(getSelectCostAccounts());
		cac.addCollection(cacheCostAccoutCollection);
		cacheCostAccoutCollection.clear();
/*		boolean flag = false;
		for (int i = 0, count = tblMain.getRowCount(); i < count; i++) {
			row = tblMain.getRow(i);
			if (((Boolean) row.getCell("select").getValue()).booleanValue()) {
				CostAccountInfo cai = (CostAccountInfo) row.getUserObject();
				
				//只返回明细科目
				if(cai.isIsLeaf()){
					cac.add(cai);
				}
				
				flag = true;
			}
		}*/
		
		/*
		if (flag) {
			CostAccountFactory.getRemoteInstance().importDatas(cac, this.addressProject.getId());
			this.showResultMessage(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE,"Import_success"));
		}
		*/
		
		/*
        SelectorItemCollection selectors = new SelectorItemCollection();
        selectors.add("id");
		selectors.add("name");
		selectors.add("number");
		selectors.add("longNumber");		
		selectors.add("displayName");	
		selectors.add("level");;	
		selectors.add("isLeaf");
		
		selectors.add("curProject");
		selectors.add("curProject.id");
		selectors.add("curProject.number");
		selectors.add("curProject.longNumber");
		selectors.add("curProject.name");
		selectors.add("curProject.displayName");
		selectors.add("curProject.level");
		selectors.add("curProject.isLeaf");
		
		String id = getSelectedKeyValue();
		CostAccountInfo costAccountInfo = CostAccountFactory.getRemoteInstance().getCostAccountInfo(
				new ObjectUuidPK(id), selectors);
		*/
		
    	/*if(!costAccountInfo.isIsLeaf()) {
    		MsgBox.showWarning(this, FDCClientUtils.getRes("selectLeaf"));
    		SysUtil.abort();
    	}*/
		
//		disposeUIWindow();
		
		return cac;
	}

    public boolean isOk() {
    	return isOk;
    }

	public void setConfirm(boolean isOk) {
		this.isOk = isOk;
		if (isOk) {
			if (getUIContext().get("isMeasureSplit") != null) {
				CostAccountCollection accts = null;
				try {
					accts = getData();
					if (accts != null && accts.size() > 0) {
						for (Iterator iter = accts.iterator(); iter.hasNext();) {
							CostAccountInfo info = (CostAccountInfo) iter.next();
							if (info.getCurProject() != null && !info.getCurProject().isIsLeaf()) {
								FDCMsgBox.showError(this, "量价合同只能选择明细项目的科目！");
								SysUtil.abort();
							}
						}
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					handUIExceptionAndAbort(e);
				}
			}
		}
		disposeUIWindow();
	}

	/* （非 Javadoc）
	 * @see com.kingdee.eas.framework.client.AbstractCoreUI#getSelectors()
	 */
	public SelectorItemCollection getSelectors() {
		// TODO 自动生成方法存根
		return super.getSelectors();
	}

	/* （非 Javadoc）
	 * @see com.kingdee.eas.fdc.contract.client.AbstractCostSplitAcctUI#tblMain_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent)
	 */
	protected void tblMain_editStopped(KDTEditEvent e) throws Exception {
		// TODO 自动生成方法存根
		super.tblMain_editStopped(e);
	}

	/* （非 Javadoc）
	 * @see com.kingdee.eas.fdc.contract.client.AbstractCostSplitAcctUI#tblMain_editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent)
	 */
	protected void tblMain_editValueChanged(KDTEditEvent e) throws Exception {
		// TODO 自动生成方法存根
		super.tblMain_editValueChanged(e);
		
		if(e.getColIndex()==tblMain.getColumnIndex("select")){
			Boolean old=(Boolean)e.getOldValue();
			Boolean now=(Boolean)e.getValue();
			select(e.getRowIndex(),old.booleanValue(),now.booleanValue());
			
			/*
			
			int idx=e.getRowIndex();
			CostAccountInfo acct=(CostAccountInfo)tblMain.getRow(idx).getUserObject();
			int level=acct.getLevel();
			Boolean selected=(Boolean)e.getValue();

			IRow row=null;
			boolean isSelected=false;
			
			
			if(acct.isIsLeaf()){		//明细科目
				isSelected=false;
				
				
				if(selected.equals(Boolean.TRUE)){	//选择
					isSelected=true;
					
					//设置上级科目的选择状态
					for(int i=idx-1; i>=0; i--){
						row=tblMain.getRow(i);
						acct=(CostAccountInfo)row.getUserObject();
						
						if(acct.getLevel()==level-1){
							row.getCell("select").setValue(Boolean.TRUE);

							if((level-1)==1){
								break;
							}
							level-=1;
						}
					}
					
				}else{		//取消选择
					
					//检查当前科目后面的本级科目是否有选择
					for(int i=idx+1; i<tblMain.getRowCount(); i++){
						row=tblMain.getRow(i);
						acct=(CostAccountInfo)row.getUserObject();
						if(acct.getLevel()==level){
							if(row.getCell("select").getValue().equals(Boolean.TRUE)){
								isSelected=true;
								break;
							}
						}else{
							break;
						}
					}
					
					//检查当前科目前面的本级科目是否有选择
					if(!isSelected){
						for(int i=idx-1; i>=0; i--){
							row=tblMain.getRow(i);
							acct=(CostAccountInfo)row.getUserObject();
							if(acct.getLevel()==level){
								if(row.getCell("select").getValue().equals(Boolean.TRUE)){
									isSelected=true;
								}
							}else{
								break;
							}
						}						
					}
					
					

					//检查同级科目是否有选择，如果有选择则取消对上级科目的操作
					boolean isFound=false;
					
					boolean isTrue=true;
					while(isTrue){
						isTrue=false;
						
						isFound=false;
						
						//检查当前科目后面的本级科目是否有选择
						for(int i=idx+1; i<tblMain.getRowCount(); i++){
							row=tblMain.getRow(i);
							acct=(CostAccountInfo)row.getUserObject();
							if(acct.getLevel()==level){
								if(row.getCell("select").getValue().equals(Boolean.TRUE)){
									isFound=true;
									break;
								}
							}else if(acct.getLevel()>level){
								continue;
							}else{
								break;
							}
						}
						
						//检查当前科目前面的本级科目是否有选择
						if(!isFound){
							for(int i=idx-1; i>=0; i--){
								row=tblMain.getRow(i);
								acct=(CostAccountInfo)row.getUserObject();
								if(acct.getLevel()==level){
									if(row.getCell("select").getValue().equals(Boolean.TRUE)){
										isFound=true;
									}
								}else if(acct.getLevel()>level){
									continue;
								}else{
									break;
								}
							}						
						}
						

						if(!isFound){							
							level--;
							
							for(int i=idx-1; i>=0; i--){
								row=tblMain.getRow(i);
								acct=(CostAccountInfo)row.getUserObject();
								
								if(acct.getLevel()==level){
									row.getCell("select").setValue(Boolean.FALSE);
									
									isTrue=true;									
									break;
								}else if(acct.getLevel()>level){
									continue;
								}else{
									break;
								}
							}

						}
					}

					
				}
				
				
				//设置上级科目的选择状态
				for(int i=idx-1; i>=0; i--){
					row=tblMain.getRow(i);
					acct=(CostAccountInfo)row.getUserObject();
					
					if(acct.getLevel()==level-1){
						if(isTrue){
							row.getCell("select").setValue(Boolean.TRUE);
						}else{
							row.getCell("select").setValue(Boolean.FALSE);
						}

						if((level-1)==1){
							break;
						}
						level-=1;
					}
				}
				
			}else{		//非明细科目
				
				//boolean isTrue=(e.getValue().equals(Boolean.TRUE));
				//下级
				for(int i=e.getRowIndex()+1; i<tblMain.getRowCount(); i++){
					row=tblMain.getRow(i);
					acct=(CostAccountInfo)row.getUserObject();
					if(acct.getLevel()>level){
						row.getCell("select").setValue(selected);
					}else{
						break;
					}
				}
				
				//上级
				int temp=level;
				for(int i=e.getRowIndex()-1; i>0; i--){
					row=tblMain.getRow(i);
					acct=(CostAccountInfo)row.getUserObject();
					if(acct.getLevel()<level){
						if(acct.getLevel()+1==temp){
							row.getCell("select").setValue(selected);
						}
						temp--;
					}else if(acct.getLevel()==1||acct.getLevel()>=level){
						break;
					}
				}
				
			}
			
		*/}
	}

	/* （非 Javadoc）
	 * @see com.kingdee.eas.fdc.contract.client.AbstractCostSplitAcctUI#tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent)
	 */
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		// TODO 自动生成方法存根
		super.tblMain_tableClicked(e);
	}

    
    public boolean destroyWindow() {
    	setConfirm(false);
    	return super.destroyWindow();
    }
	
    public void select(int row, boolean old, boolean now){
    	if(old==now) return;
    	tblMain.getCell(row, "select").setValue(Boolean.valueOf(now));
		CostAccountInfo acctSelect=(CostAccountInfo)tblMain.getRow(row).getUserObject();
		if (old) {
			// 从CostAccout对象缓存中删除对应的对象
			cacheCostAccoutCollection.remove(acctSelect);
		}
		CostAccountInfo acct=null;
		int level=acctSelect.getLevel();
		//下级
    	for(int i=row+1;i<tblMain.getRowCount();i++){
    		acct = (CostAccountInfo)tblMain.getRow(i).getUserObject();
    		if(acct.getLevel()>level){
    			tblMain.getCell(i, "select").setValue(Boolean.valueOf(now));
    			// 从CostAccout对象缓存中删除对应的对象
    			cacheCostAccoutCollection.remove(acct);
    		}else{
    			break;
    		}
    	}
    	
    	//上级
    	int parentLevel=level-1;
    	if(now){
        	for(int i=row-1;i>=0;i--){
        		if(parentLevel==0){
        			break;
        		}
        		acct = (CostAccountInfo)tblMain.getRow(i).getUserObject();
        		if(acct.getLevel()==parentLevel){
        			ICell cell = tblMain.getCell(i, "select");
        			if(cell.getValue()!=Boolean.TRUE) {
						cell.setValue(Boolean.TRUE);
						parentLevel--;
					}else{
						break;
					}

        		}
        	}
    	}else{
    		
    		//不选择,检查同级是否有选择的
    		boolean hasSelected=false;
    		//下面的行
        	for(int i=row+1;i<tblMain.getRowCount();i++){
        		acct = (CostAccountInfo)tblMain.getRow(i).getUserObject();
        		if(acct.getLevel()==level){
        			ICell cell = tblMain.getCell(i, "select");
        			if(cell.getValue()==Boolean.TRUE) {
        				hasSelected=true;
        				break;
        			}else if(acct.getLevel()<level){
        				break;
        			}
        		}
        	}
    		//上面的行
        	
        	if(!hasSelected){
            	for(int i=row-1;i>=0;i--){
            		acct = (CostAccountInfo)tblMain.getRow(i).getUserObject();
            		if(acct.getLevel()==level){
            			ICell cell = tblMain.getCell(i, "select");
            			if(cell.getValue()==Boolean.TRUE) {
            				hasSelected=true;
            				break;
    					}
            		}else if(acct.getLevel()<level){
            			row=i;
            			break;
            		}
            	}
        	}
        	
        	if(!hasSelected){

    			//设置父级
    			parentLevel=level-1;
            	for(int j=row;j>=0;j--){
            		if(parentLevel==0){
            			break;
            		}
            		acct = (CostAccountInfo)tblMain.getRow(j).getUserObject();
            		if(acct.getLevel()==parentLevel){
            			ICell cell = tblMain.getCell(j, "select");
						cell.setValue(Boolean.FALSE);
						// 从CostAccout对象缓存中删除对应的对象
						cacheCostAccoutCollection.remove(acct);
						parentLevel--;
            		}
            	}
    		
        	}
    	}
		
    }
	
    public CostAccountCollection getSelectCostAccounts(){
    	CostAccountCollection c=new CostAccountCollection();
		for (int i = 0, count = tblMain.getRowCount(); i < count; i++) {
			IRow row = tblMain.getRow(i);
			if (((Boolean) row.getCell("select").getValue()).booleanValue()) {
				CostAccountInfo cai = (CostAccountInfo) row.getUserObject();
				
				if (isFinacial) {
					String className = "";
					if (getUIContext().get("Owner") != null) {
						className = getUIContext().get("Owner").getClass().getName();
						if (className.equals(WorkLoadSplitEditUI.class.getName())
								|| className.equals(PaymentSplitEditUI.class.getName())
								|| className
										.equals(PaymentSplitWithoutTxtConEditUI.class.getName())) {
							// 只返回明细科目
							if (cai.isIsLeaf()) {
								c.add(cai);
							} 
						} else {
							// 只返回明细科目
							if (cai.isIsLeaf() || !parentMap.containsKey(cai.getId().toString())) {
								c.add(cai);
							} 
						}
					}
				} else {
					// 只返回明细科目
					if (cai.isIsLeaf() || !parentMap.containsKey(cai.getId().toString())) {
						c.add(cai);
					} 
				}
			}
		}
		return c;
    }
	
    
    /**
	 * @description 判断是否启用财务一体化复杂模式
	 * @author 何鹏
	 * @createDate 2011-8-8
	 * @throws Exception
	 * @version EAS7.0
	 * @see
	 */
    protected void initFinacial()throws Exception{
		if(FDCUtils.IsFinacial(null, SysContext.getSysContext().getCurrentFIUnit().getId().toString())){
			isFinacial=true;
		}
    }

	/**
	 * 只查看可拆分科目 （工程项目不改变时）
	 */
    public void actionIsSplitAcct_actionPerformed(ActionEvent e) throws Exception {
    	// 获得是否选中只查看可拆分科目
    	boolean isSelected = this.isSplitAcct.isSelected();
    	// 如果选中则过滤掉不可拆分的科目
		if (isSelected) {
			filter.getFilterItems().add(new FilterItemInfo("isSplit", new Integer(1)));
		} else {// 否则全部显示
			filter.remove("isSplit");
		}
		treeSelectChange();
	}
    
    /**
	 * 获得当前成本科目的最大级次
	 * 
	 * @return 最大级次
	 */
    private int getMaxLevel(Object curProject) {
		// 获得数据库操作对象
		FDCSQLBuilder builder = new FDCSQLBuilder();
    	// 申明级次
    	int level = 0;
    	// 查询数据库sql
		String sql = null;
		// 根据项目生成级次
		if (curProject instanceof CurProjectInfo) {
			sql = "select max(FLevel) from T_FDC_CostAccount where FCurProject = ?";
			builder.appendSql(sql);
			builder.addParam(((CurProjectInfo) curProject).getId().toString());
		} else if (curProject instanceof FullOrgUnitInfo) {// 根据组织生成级次
			sql = "select max(FLevel) from T_FDC_CostAccount where FFullorgunit = ?";
			builder.appendSql(sql);
			builder.addParam(((FullOrgUnitInfo) curProject).getId().toString());
		} else {// 根据所有科目生成级次
			sql = "select max(FLevel) from T_FDC_CostAccount";
			builder.appendSql(sql);
		}
		try {
			// 获得返回的最大级次
			IRowSet set = builder.executeQuery();
			while (set.next()) {
				level = set.getInt(1);
			}
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		} 
		return level;
	}

	/**
	 * 动态创建级次按钮
	 * 
	 * @throws Exception
	 */
    private void buiderLevelButtom(Object curProject) throws Exception {
    	// 获取最大级次
		int level = this.getMaxLevel(curProject);
		this.levelPanel.removeAll();
		// 申明级次按钮的X坐标值
		int x = 0;
		// 便利生成级次按钮
		for (int i = 0; i < level; i++) {
			// 创建一个级次按钮
			KDWorkButton levelBtn = new KDWorkButton();
			// 设置按钮的级次
			levelBtn.setText(Integer.toString(i + 1));
			// 设置按钮字体
			levelBtn.setFont(new Font("Dialog", 0, 9));
			// 设置按钮大小
			levelBtn.setSize(14, 16);
			// 设置每个按钮的位置
			levelBtn.setLocation(x, 0);
			// 为每个按钮添加点击事件
			levelBtn.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e) {
					try {
						// 调用级次按钮事件
						actionClickLevel(e);
					} catch (Exception e1) {
						handUIExceptionAndAbort(e1);
					}
				}
				
			});
			// 将按钮添加到页面中
			this.levelPanel.add(levelBtn);
			// 每次循环改变按钮的X坐标值
			this.levelPanel.repaint();
			x = x + 14;
		}
	}

	/**
	 * 每个级次按钮的点击事件
	 * 
	 * @param e
	 *            点击的按钮事件
	 * @throws Exception
	 */
    public void actionClickLevel(ActionEvent e) throws Exception {
    	// 从事件中获取当前点击的按钮
    	KDWorkButton btn = (KDWorkButton) e.getSource();
    	// 得到点击的按钮级次值
    	int sth = Integer.parseInt(btn.getText());
    	// 根据获得的按钮级次值进行不同的过滤
		changeTreeView(sth);
	}

    /**
	 * 级次点击按钮
	 * 
	 * @param level
	 *            当前点击的级次
	 */
    private void changeTreeView(int level) {
    	IRow row;
		int rowLevel;
		level = level - 1;
		CellTreeNode node;
		// 遍历整个表格
    	for (int i = 0; i < tblMain.getRowCount(); i++) {
    		row = tblMain.getRow(i);
			rowLevel = row.getTreeLevel();
			node = (CellTreeNode) row.getCell("number").getValue();
			// 点击级次等于当前遍历的级次时，则展开树
			if (level == rowLevel) {
				row.getStyleAttributes().setHided(false);
				node.setCollapse(true);
			} else {// 否则隐藏树
				row.getStyleAttributes().setHided(true);
			}
			// 点击级次大于当前遍历级次
			if (level > rowLevel) {
				row.getStyleAttributes().setHided(false);
				node.setCollapse(false);
			}
			// 点击级次小于当前遍历级次
			if (level < rowLevel) {
			
				row.getStyleAttributes().setHided(true);
				node.setCollapse(true);
			
			}
		}
	}
    
    
	/**
	 * 
	 * @description 启用复杂模式成本财务一体化的处理
	 * @author 何鹏
	 * @createDate 2011-8-8
	 * @version EAS7.0
	 * @see
	 */
    public void onFinacial() {
    	// 判断是否启用复杂模式成本财务一体化
		String className = "";
		if (getUIContext().get("Owner") != null) {
			// 获取点击成本科目按钮所在界面的className
			className = getUIContext().get("Owner").getClass().getName();
			// 比较是否是工程量拆分界面的className
			if (className.equals(WorkLoadSplitEditUI.class.getName())
					|| className.equals(PaymentSplitEditUI.class.getName())
					|| className.equals(PaymentSplitWithoutTxtConEditUI.class.getName())) {
			}
		}
		// 判断是否启用复杂模式成本财务一体化
		if (isFinacial) {
			// 将只查询可拆分科目设为不可用和默认不选中状态
			this.isSplitAcct.setSelected(false);
			this.isSplitAcct.setEnabled(false);
		} 
    }

	/**
	 * 查询合同是否作废过
	 */
	public boolean contractIsInvalid() {
		// 获得SQL
		String sql = isInvalid();
		if (sql.equals("") || sql == null) {
			return false;
		}
		// 初始是否作废过
		boolean isInvalid = false;
		// 是否作废集合 用于反复作废合同产生的多条数据
		List isInvalidList = new ArrayList();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(sql);
		try {
			IRowSet set = builder.executeQuery();
			// 便利数据库返回的结果并将结果加入集合
			while (set.next()) {
				isInvalid = set.getBoolean(1);
				isInvalidList.add(Boolean.valueOf(isInvalid));
			}
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
		// 便利结果
		for (int i = 0; i < isInvalidList.size(); i++) {
			boolean temp = ((Boolean) isInvalidList.get(i)).booleanValue();
			// 为真，是被作废过
			if (temp) {
				isInvalid = true;
				break;
			}
		}
		return isInvalid;
	}
	
	/**
	 * 查询合同是否作废过SQL语句拼装
	 * 
	 * @return sql 语句
	 */
	private String isInvalid() {
		// 获得打开页面的名字
		String className = this.getUIContext().get("Owner").getClass().getName();
		// 取得合同的编码（变更拆分指令单编码）
		String costNumber = null;
		String sql = "";
		if (getUIContext().get("txtCostBillNumber") != null) {
			costNumber = getUIContext().get("txtCostBillNumber").toString();
			// 变更拆分的sql拼装
			if (className.equals(ConChangeSplitEditUI.class.getName())) {
				sql = "select FISinvalid from T_CON_ConChangeSplit where fcontractchangeid in (select fid from T_CON_ContractChangeBill where fnumber = '"
					+ costNumber + "')";
			} else if (className.equals(ContractCostSplitEditUI.class.getName())) {// 合同拆分sql拼装
				sql = "select FIsInvalid from T_CON_ContractCostSplit where FContractBillID in (select fid from T_CON_ContractBill where fnumber = '"
					+ costNumber + "') ";
			}
		}
		return sql;
	}

	/**
	 * 
	 * @description 获取合同拆分和变更拆分的科目
	 * @author 何鹏
	 * @createDate 2011-8-22
	 * @return
	 * @version EAS7.0
	 * @see
	 */
	private CostAccountCollection getContractSlpitAndChangeSplitCostAccounts() {
		// 科目的长编码集合
		List longNumbers = new ArrayList();
		// 科目集合
		CostAccountCollection costAccountCollection = new CostAccountCollection();
		try {
			// 获取合同ID
			String contractBillId = getUIContext().get("contractBillId") != null ? getUIContext()
					.get("contractBillId").toString() : "";
			// 通过合同ID查询合同拆分和变更拆分关联的科目长编码
			StringBuffer sql = new StringBuffer();
			sql.append(" select FLONGNUMBER,FCURPROJECT from T_FDC_COSTACCOUNT tfc ");
			sql
					.append(" left join T_CON_ContractCostSplitEntry tcse on tcse.FCOSTACCOUNTID=tfc.FID ");
			sql.append(" left join T_CON_CONTRACTCOSTSPLIT tcs on tcs.FID=tcse.FPARENTID ");
			sql
					.append(" left join T_CON_CONCHANGESPLITENTRY tccse on tccse.FCOSTACCOUNTID=tfc.FID ");
			sql.append(" left join T_CON_CONCHANGESPLIT tcc on tcc.FID=tccse.FPARENTID ");
			sql.append(" where tcs.FCONTRACTBILLID='" + contractBillId
					+ "' or tcc.FCONTRACTBILLID='" + contractBillId
							+ "' order by flongnumber asc");
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql(sql.toString());
			builder.execute();
			IRowSet set = builder.executeQuery();
			// 遍历数据库返回的结果并将结果加入集合
			while (set.next()) {
				// 科目长编码
				String longNumber = set.getString(1);
				longNumbers.add(longNumber);
			}
			// 通过科目长编码查询出科目的信息以及科目的父科目
			this.addCostAccount(longNumbers, costAccountCollection);
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
	
		return costAccountCollection;
	}

	/**
	 * 通过长编码获取科目
	 * 
	 * @description
	 * @author 何鹏
	 * @createDate 2011-8-22
	 * @param longNumbers
	 * @param costAccountInfos
	 * @throws BOSException
	 * @throws EASBizException
	 * @version EAS7.0
	 * @see
	 */
	public void addCostAccount(List longNumbers, CostAccountCollection costAccountCollection)
			throws BOSException, EASBizException {
		// 获取远程接口
		ICostAccount iCostAccount = CostAccountFactory.getRemoteInstance();
		DefaultKingdeeTreeNode treeNode = getProjSelectedTreeNode();
		CurProjectInfo projectInfo = null;
		// 获取当前工程树项目
		if(treeNode.getUserObject() instanceof CurProjectInfo) {
			projectInfo = (CurProjectInfo) treeNode.getUserObject();
		}
		if (projectInfo != null) {
			if (longNumbers.size() > 0) {
				for (int i = 0; i < longNumbers.size(); i++) {
					String longNumber = (String) longNumbers.get(i);
					// 分隔科目的长编码
					String[] tempLongNumbers = longNumber.split("!");
					String sp = "";
					String number = "";
					// 遍历科目的长编码
					for (int j = 0; j < tempLongNumbers.length; j++) {
						number += sp + tempLongNumbers[j];
						sp = "!";
						// 封装查询 条件
						EntityViewInfo evInfo = new EntityViewInfo();
						FilterInfo filterInfo = new FilterInfo();
						
						// 判断是否选中只查询可拆分科目选框
						if (isSplitAcct.isSelected()) {
							filterInfo.getFilterItems().add(
									new FilterItemInfo("isSplit", new Integer(1)));
						} else {
							filterInfo.remove("isSplit");
						}
						 filterInfo.getFilterItems().add(
								new FilterItemInfo("longnumber", number, CompareType.EQUALS));
					
						filterInfo.getFilterItems().add(
								new FilterItemInfo("curProject.id", projectInfo.getId().toString(),
										CompareType.EQUALS));
						 
						evInfo.setFilter(filterInfo);
						CostAccountInfo costAccountInfo = iCostAccount.getCostAccountCollection(
								evInfo).get(0);
						if (costAccountInfo != null) {
							if (longNumbers.contains(costAccountInfo.getLongNumber())
									&& !costAccountInfo.isIsLeaf()) {
								this.getCostAccountCollection(projectInfo, iCostAccount, evInfo,
										filterInfo, costAccountInfo, costAccountCollection);

							} else {
								costAccountCollection.add(costAccountInfo);
							}
						}
						
					}
				}
			}
		}
	}
	/**
	 * 
	 * @description
	 * @author 何鹏
	 * @createDate 2011-9-9
	 * @param projectInfo
	 * @param iCostAccount
	 * @param evInfo
	 * @param filterInfo
	 * @param costAccountInfo
	 * @param costAccountCollection
	 * @throws BOSException
	 * @version EAS7.0
	 * @see
	 */
	public void getCostAccountCollection(CurProjectInfo projectInfo, ICostAccount iCostAccount,
			EntityViewInfo evInfo,
			FilterInfo filterInfo, CostAccountInfo costAccountInfo,
			CostAccountCollection costAccountCollection) throws BOSException {
		if (costAccountInfo != null && costAccountInfo.getLevel() > 1) {
			// 封装查询 条件
			evInfo = new EntityViewInfo();
			filterInfo = new FilterInfo();
			addSelector(evInfo);
			// 判断是否选中只查询可拆分科目选框
			if (isSplitAcct.isSelected()) {
				filterInfo.getFilterItems().add(new FilterItemInfo("isSplit", new Integer(1)));
			} else {
				filterInfo.remove("isSplit");
			}
						
			filterInfo.getFilterItems().add(
						new FilterItemInfo("longnumber", costAccountInfo.getLongNumber() + "%",
							CompareType.LIKE));

			filterInfo.getFilterItems().add(
					new FilterItemInfo("curProject.id", projectInfo.getId().toString(),
							CompareType.EQUALS));
			// 排序
			SorterItemCollection sorter = new SorterItemCollection();
			SorterItemInfo seq = new SorterItemInfo("longnumber");
			seq.setSortType(SortType.ASCEND);
			sorter.add(seq);
			evInfo.setSorter(sorter);
			evInfo.setFilter(filterInfo);
			CostAccountCollection costAccounts = iCostAccount.getCostAccountCollection(evInfo);
			for (int z = 0; z < costAccounts.size(); z++) {
				CostAccountInfo costAccount = costAccounts.get(z);
				costAccountCollection.add(costAccount);
			}
		} else {
			if (costAccountInfo != null) {
				costAccountCollection.add(costAccountInfo);
			}
		}
	}
	
	/**
	 * 获取合同拆分和变更拆分的项目
	 * 
	 * @description
	 * @author 何鹏
	 * @createDate 2011-8-23
	 * @version EAS7.0
	 * @see
	 */
	private void getCurProjectInfos() {
		List allProjects = new ArrayList();

		try {
			String contractBillId = getUIContext().get("contractBillId") != null ? getUIContext()
					.get("contractBillId").toString() : "";
			StringBuffer sql = new StringBuffer();
			sql.append(" select tfp.FLONGNUMBER from T_FDC_CURPROJECT tfp");
			sql.append(" left join T_FDC_COSTACCOUNT tfc on tfc.FCURPROJECT=tfp.fid ");
			sql
					.append(" left join T_CON_ContractCostSplitEntry tcse on tcse.FCOSTACCOUNTID=tfc.FID ");
			sql.append(" left join T_CON_CONTRACTCOSTSPLIT tcs on tcs.FID=tcse.FPARENTID ");
			sql
					.append(" left join T_CON_CONCHANGESPLITENTRY tccse on tccse.FCOSTACCOUNTID=tfc.FID ");
			sql.append(" left join T_CON_CONCHANGESPLIT tcc on tcc.FID=tccse.FPARENTID ");
			sql.append(" where tcs.FCONTRACTBILLID='" + contractBillId
					+ "' or tcc.FCONTRACTBILLID='" + contractBillId + "' ");
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql(sql.toString());

			builder.execute();
			IRowSet set = builder.executeQuery();
			// 遍历数据库返回的结果并将结果加入集合
			while (set.next()) {
				String curproject = set.getString(1);
				allProjects.add(curproject);
			}
			this.addCurproject(allProjects);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			handUIExceptionAndAbort(e);
		}
	}

	/**
	 * 通过项目长编码获取项目树
	 * 
	 * @description
	 * @author 何鹏
	 * @createDate 2011-8-23
	 * @param allProjects
	 * @throws BOSException
	 * @throws EASBizException
	 * @version EAS7.0
	 * @see
	 */
	public void addCurproject(List allProjects)
			throws BOSException, EASBizException {
		    ICurProject iCurProject = CurProjectFactory.getRemoteInstance();

		if (allProjects.size() > 0) {
			for (int i = 0; i < allProjects.size(); i++) {
				String longNumber = (String) allProjects.get(i);
				// 分隔项目的长编码
					String[] tempLongNumbers = longNumber.split("!");
					String sp = "";
					String number = "";
					// 遍历项目的长编码
					for (int j = 0; j < tempLongNumbers.length; j++) {
						number += sp + tempLongNumbers[j];
						sp = "!";
						EntityViewInfo evInfo = new EntityViewInfo();
						FilterInfo filterInfo = new FilterInfo();
						filterInfo.getFilterItems().add(new FilterItemInfo("longnumber", number));
					// filterInfo.getFilterItems().add(
					// new FilterItemInfo("longnumber", longNumber,
					// CompareType.LIKE));
						evInfo.setFilter(filterInfo);
						CurProjectInfo curProjectInfo = iCurProject.getCurProjectCollection(evInfo)
							.get(0);
					if (curProjectInfo != null) {
							curProjectCollection.add(curProjectInfo.getId().toString());
						}
					}
				}
		}
	}
	
	/**
	 * 用于判断拆分事件
	 * 
	 * @return
	 */
	public Map getParentIdMap() {
		return this.parentMap;
	}
}