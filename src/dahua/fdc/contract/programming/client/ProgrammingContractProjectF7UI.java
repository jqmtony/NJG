/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.programming.client;

import java.awt.event.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
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
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.NodeClickListener;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProjectStatusInfo;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUCollection;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUFactory;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCColorConstants;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ProgrammingContractProjectF7UI extends AbstractProgrammingContractProjectF7UI
{
    private static final Logger logger = CoreUIObject.getLogger(ProgrammingContractProjectF7UI.class);
    
    private boolean isCancel = true;
    private int rowNum = 0;  
    private CurProjectInfo curProject = null;
	private Map parentMap = null;
	//项目ID集合
	private Set curProjectCollection = new HashSet();
	private EntryTreeSumField sumField = new EntryTreeSumField();
	public boolean isCancel() {
		return isCancel;
	}

	public void setCancel(boolean isCancel) {
		this.isCancel = isCancel;
	}
    
    /**
     * output class constructor
     */
    public ProgrammingContractProjectF7UI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
    	super.onLoad();
    	initTableMain();
    	buildProjectTree();
		this.treeMain.setSelectionRow(0);
		
    	EntityViewInfo viewInfo = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("parent.*");
		viewInfo.setSelector(sic);
		FilterInfo filter = new FilterInfo();
		
		filter.appendFilterItem("programming.isLatest", new Boolean(true));
		filter.appendFilterItem("programming.state", FDCBillStateEnum.AUDITTED);
		
		if (getUIContext().get("projectId") != null) {
			String projectId = getUIContext().get("projectId").toString();
			filter.appendFilterItem("programming.project.id", projectId);
		}
		/*********** add by lihaiou,2013.0922. for bug R130916-0204*************/
		Boolean allowZero = getUIContext().get("allowZero") == null ? Boolean.TRUE : Boolean.valueOf(getUIContext().get("allowZero")
				.toString());
		if (!allowZero.booleanValue()) {
			filter.getFilterItems().add(new FilterItemInfo("amount", new Integer(0), CompareType.GREATER));
		}
		/*************** modify end*********************/
		viewInfo.setFilter(filter);
		SorterItemCollection sort = new SorterItemCollection();
//    	sort.add(new SorterItemInfo("longNumber"));
//    	sort.add(new SorterItemInfo("programming.id"));
    	sort.add(new SorterItemInfo("sortNumber"));
//    	
    	viewInfo.setSorter(sort);
		
		ProgrammingContractCollection  cols = null;
    	try {
			 cols = ProgrammingContractFactory.getRemoteInstance().getProgrammingContractCollection(viewInfo);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
		
    	if(cols != null && cols.size() > 0){ 
    		rowNum = cols.size();
    		getMainTable().setRowCount(rowNum);
    		for(Iterator it = cols.iterator();it.hasNext();){
    			ProgrammingContractInfo info = (ProgrammingContractInfo) it.next();
    			info.setIsLeaf(chargeIsLeaf(info,cols));
    			IRow row = getMainTable().addRow();
     			setRowData(info, row);
    		}
    		//向上汇总
			sumField.caclTotalAmount(getMainTable());
    	}
    	 this.treeMain.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
             public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                 try {
                     treeMain_valueChanged(e);
                 } catch (Exception exc) {
                     handUIException(exc);
                 } finally {
                 }
             }
         });
    }

	private void initTableMain() {
		menuBar.setVisible(false); 
		toolBar.setVisible(false);
		tblMain.getColumn("id").getStyleAttributes().setHided(true);
		tblMain.getColumn("sortNumber").getStyleAttributes().setHided(true);
		tblMain.getColumn("longNumber").getStyleAttributes().setHided(true);
		tblMain.getColumn("amount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn("controlAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn("balance").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn("controlBalance").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		btnConfirm.setEnabled(true);
		btnExit.setEnabled(true);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
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

	private boolean chargeIsLeaf(ProgrammingContractInfo info,ProgrammingContractCollection  cols) {
		String id = info.getId().toString(); 
		for (int i = 0; i < rowNum; i++) {
			ProgrammingContractInfo parent = cols.get(i).getParent();
			if(parent==null) continue;
			if(id.equals(parent.getId().toString())){
				return false;
			}
		}
		return true;
	}
	
	private void buildProjectTree() throws Exception {
		//不显示已经禁用的工程项目（考虑是否组织隔离？）	jelon 12/30/2006
		Boolean isPreSplit = Boolean.valueOf(false);
		if(getUIContext().get("isPreSplit") != null)
		{
			isPreSplit = (Boolean)getUIContext().get("isPreSplit");
		}
		if(!isPreSplit.booleanValue()){
			ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder(false, true,curProjectCollection);
			//组织隔离
			//		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder(false, false);
			treeMain.setShowsRootHandles(true);
			projectTreeBuilder.build(this, treeMain, actionOnLoad);
		}
		else{
			ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder(false, false,curProjectCollection);
			treeMain.setShowsRootHandles(true);
			projectTreeBuilder.build(this, treeMain, actionOnLoad);
		}
	}
	
	private void setRowData(ProgrammingContractInfo info, IRow row) {
		CellTreeNode node = new CellTreeNode();
    	row.setUserObject(info);
    	node.setValue(info.getName());
    	node.setTreeLevel(info.getLevel());
    	row.getCell("id").setValue(info.getId());
    	row.getCell("longNumber").setValue(info.getLongNumber());
    	row.getCell("amount").setValue(info.getAmount());
    	row.getCell("name").setValue(node);
    	row.getCell("controlAmount").setValue(info.getControlAmount());
    	row.getCell("balance").setValue(info.getBalance());
    	row.getCell("controlBalance").setValue(info.getControlBalance());
    	if(info.getProgramming().getProject()!=null){
    		row.getCell("project.isEnabled").setValue(new Boolean(info.getProgramming().getProject().isIsEnabled()));
    		row.getCell("project.id").setValue(info.getProgramming().getProject().getId());//add by warship at 2010/07/26
    	}
    	row.getCell("level").setValue(new Integer(info.getLevel()));
    	row.getCell("number").setValue(info.getNumber());
    	row.getCell("programming.isLatest").setValue(new Boolean(info.getProgramming().isIsLatest()));
    	row.getCell("programming.state").setValue(info.getProgramming().getState());
    	row.getCell("sortNumber").setValue(new Integer(info.getSortNumber()));
    	row.getCell("headNumber").setValue(info.getParent() == null ? "" : info.getParent().getLongNumber());
    	row.getStyleAttributes().setLocked(true);
    	row.getCell("name").getStyleAttributes().setLocked(false);
    	
    	final int level = info.getLevel()-1;
		final boolean isLeaf = info.isIsLeaf();
		node.setTreeLevel(level);
		
		if(isLeaf){
			node.setHasChildren(false);
			if(level>1){
				row.getStyleAttributes().setHided(true);
			}
		}else{
//			row.getStyleAttributes().setBackground(Color.BLACK);
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
					tblMain.revalidate();
					
				}
			});
			node.setHasChildren(true);
			row.getStyleAttributes().setBackground(FDCColorConstants.lockColor);
			row.getStyleAttributes().setLocked(true);
			row.getCell("name").getStyleAttributes().setLocked(false);
		}

		row.getCell("name").setValue(node);
	}
	
	public void onShow() throws Exception {
		super.onShow();
		selectCurOrgTreeNode();
		createLevelTree();
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
	
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		treeSelectChange();
	}
	
	private void treeSelectChange() throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
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
			//TODO 暂不允许选择公司的科目	jelon
			//filterItems.add(new FilterItemInfo("fullOrgUnit.id", info.getId().toString(), CompareType.EQUALS));		
			filterItems.add(new FilterItemInfo("fullOrgUnit.id", BOSUuid.create("null"), CompareType.EQUALS));
			
			
		}
	}
	
	private String setNameIndent(int level) {
		StringBuffer blank = new StringBuffer("");
		for (int i = level; i > 1; i--) {
			blank.append("        ");
		}
		return blank.toString();
	}

	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		Map map = this.getUIContext();
		if (map.get("view") != null) {
			EntityViewInfo entityView = (EntityViewInfo) map.get("view");
			EntityViewInfo viewInfo = this.getMainQuery();
			viewInfo.setFilter(entityView.getFilter());
		}
		super.actionQuery_actionPerformed(e);
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		//modified by shangjing  这里取消了 双击选择  因为有+ -树 影响了操作
		//啥意思？怎么影响操作了？赵伟提bug了，放开了
		if (e.getClickCount() == 2) {
			if (e.getType() == 0) {
				return;
			} else {
				confirm();
				setCancel(false);
			}
		}
	}

	private String getSelectedId() {
		int actRowIdx = tblMain.getSelectManager().getActiveRowIndex();
		if (actRowIdx < 0) {
			return null;
		} else if (tblMain.getCell(actRowIdx, "id").getValue() != null) {
			return tblMain.getCell(actRowIdx, "id").getValue().toString();
		} else {
			return null;
		}
	}
	
	public ProgrammingContractInfo getData() throws Exception {
		ProgrammingContractInfo info = null;
		String id = getSelectedId();
		if (id == null)
			return null;
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("id");
		view.getSelector().add("name");
		view.getSelector().add("longNumber");
		view.getSelector().add("amount");
		view.getSelector().add("number");
		view.getSelector().add("controlAmount");
		view.getSelector().add("balance");
		view.getSelector().add("controlBalance");
		view.getSelector().add("project.id");
		view.getSelector().add("project.isEnabled");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", id));
		view.setFilter(filter);
		info = (ProgrammingContractInfo) ProgrammingContractFactory
				.getRemoteInstance().getProgrammingContractCollection(view)
				.get(0);
		CoreUIObject owner = (CoreUIObject) this.getUIContext().get(UIContext.OWNER);
		if(owner!=null){
			owner.getUIContext().put("selectedValue", info);
		}
		disposeUIWindow();
		return info;
	}

	private void confirm() throws Exception {
		checkSelected();  
		getData();
		setCancel(true);
	}

	public void checkSelected() {
		int rowsCount = tblMain.getBody().getRows().size();
		if (rowsCount == 0
				|| tblMain.getSelectManager().size() == 0
				|| tblMain.getSelectManager().getActiveRowIndex() < 0) {
			MsgBox.showWarning(this, EASResource
					.getString(FrameWorkClientUtils.strResource
							+ "Msg_MustSelected"));
			SysUtil.abort();
		}
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		int level = new Integer(tblMain.getCell(rowIndex, "level").getValue()
				.toString()).intValue();
		if (rowIndex < rowsCount-1) {
			int level_next = new Integer(tblMain.getCell(rowIndex + 1, "level")
					.getValue().toString()).intValue();
			if (level < level_next) {
				MsgBox.showWarning(this, FDCClientUtils.getRes("selectLeaf"));
				SysUtil.abort();
			}
		}
	}

	public void actionConfirm_actionPerformed(ActionEvent e) throws Exception {
		confirm();
		setCancel(false);
	}

	public void actionExit_actionPerformed(ActionEvent e) throws Exception {
		destroyWindow();
		setCancel(true);
	}
	
	private void createLevelTree() {  
		int rowsNum = tblMain.getBody().getRows().size();
    	if(rowsNum<1){
    		return ;
    	}
    	HashSet tbIds = new HashSet();
		int[] levelArray = new int[rowsNum];
		for (int i = 0; i < rowsNum; i++) {
			IRow row = tblMain.getRow(i);
			tbIds.add(row.getCell("id").getValue().toString());
			int level = Integer.parseInt(row.getCell("level").getValue().toString());
			levelArray[i] = level;
			row.setTreeLevel(level - 1);
		}
		int maxLevel = 0;
		for (int i = 0; i < rowsNum; i++) {
			maxLevel  = Math.max(levelArray[i], maxLevel);
		}
		tblMain.getTreeColumn().setDepth(maxLevel);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
	}
	
	@Override
	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getEditUIName() {
		// TODO Auto-generated method stub
		return null;
	}

}