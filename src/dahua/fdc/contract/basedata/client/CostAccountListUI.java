/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.util.render.IBasicRender;
import com.kingdee.bos.ctrl.kdf.util.render.SimpleTextRender;
import com.kingdee.bos.ctrl.swing.KDOptionPane;
import com.kingdee.bos.ctrl.swing.KDSeparator;
import com.kingdee.bos.ctrl.swing.event.TreePopupMenuEvent;
import com.kingdee.bos.ctrl.swing.event.TreePopupMenuListener;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.query.ISQLExecutor;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.uiframe.client.UIModelDialogFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.aimcost.CostEntryFactory;
import com.kingdee.eas.fdc.basedata.AccountStageCollection;
import com.kingdee.eas.fdc.basedata.AccountStageInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFacadeFactory;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ICostAccount;
import com.kingdee.eas.fdc.basedata.ICostAccountFacade;
import com.kingdee.eas.fdc.basedata.IMeasureStage;
import com.kingdee.eas.fdc.basedata.MeasureStageCollection;
import com.kingdee.eas.fdc.basedata.MeasureStageFactory;
import com.kingdee.eas.fdc.basedata.MeasureStageInfo;
import com.kingdee.eas.fdc.basedata.util.CostAccountHelper;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.framework.config.TablePreferencesHelper;
import com.kingdee.eas.framework.util.StringUtility;
import com.kingdee.eas.framework.util.UIConfigUtility;
import com.kingdee.eas.tools.datatask.DatataskParameter;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 描述:成本科目叙事簿
 * 
 * @author jackwang date:2006-9-6
 *         <p>
 * @version EAS5.1
 */

public class CostAccountListUI extends AbstractCostAccountListUI {
	private static final Logger logger = CoreUIObject.getLogger(CostAccountListUI.class);

	private CostAccountTreeRender treeRender;

	private boolean isSetTreeDispalyStyle = true;

	private CurProjectInfo project = null;

	private FullOrgUnitInfo orgUnit = null;

	MeasureStageCollection mc = null;
	private static final String COLKEY_PER = "k1" ; 
	/**
	 * output class constructor
	 */
	public CostAccountListUI() throws Exception {
		super();
		
		this.btnSave.setEnabled(true);
	}

	/**
	 * 隐藏不要的列
	 */
	protected void hiddenFields(){
		this.tblMain.getColumn("isSource").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("isEnterDB").getStyleAttributes().setHided(true);
		// 在onshow后调用隐藏，右键恢复默认可以显示出来by hpw
		tblMain.getColumn("isSplit").getStyleAttributes().setHided(true);
		tblMain.getColumn("isSplit").setWidth(100);
	}
	
	boolean isUIShow = false ;
	
	// 拆分阶段列
	protected void showFields() throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		FilterInfo filter = new FilterInfo();
		//显示启用的
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
		SorterItemCollection sic = new SorterItemCollection();
		SorterItemInfo sortInfo = new SorterItemInfo("number");
		sortInfo.setSortType(SortType.ASCEND);
		sic.add(sortInfo);

		view.setFilter(filter);
		view.setSorter(sic);

		IMeasureStage iMeasureSrv = MeasureStageFactory.getRemoteInstance();
		mc = iMeasureSrv.getMeasureStageCollection(view);

		if (mc != null && !mc.isEmpty()) {
			MeasureStageInfo mcInfo = null;
			for (int i = 0; i < mc.size(); i++) {
				mcInfo = mc.get(i);
				IColumn col = tblMain.addColumn();
				col.setKey(COLKEY_PER + mcInfo.getNumber());
				col.setWidth(100);
				col.getStyleAttributes().setHided(true);
			}
			
			for (int i = 0; i < mc.size(); i++) {
				mcInfo = mc.get(i);
				tblMain.getHeadRow(0).getCell(COLKEY_PER + mcInfo.getNumber()).setValue(mcInfo.getName());
				tblMain.getHeadRow(0).getCell(COLKEY_PER + mcInfo.getNumber()).setUserObject(mcInfo);
			}
		}
	}
	
	protected void hiddenButton(){
		btnAssignToOrg.setVisible(false);
		btnProjectAttachment.setVisible(false);
	}
	
	public void onShow() throws Exception {

		super.onShow();
		
		appendFunToCostAccount();
		
		hiddenFields();
		
		hiddenButton();
	}
	
	public void init() throws Exception{
		
		FDCTableHelper.setColumnMoveable(tblMain, true);
		this.chkIncludeChild.setSelected(true);
		this.chkIncludeChild.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				isSetTreeDispalyStyle = chkIncludeChild.isSelected();
			}
		});
		treeRender = new CostAccountTreeRender();
		tblMain.getColumn("number").setRenderer(treeRender);

		//R111027-0082暂时先固定 by hpw
		tblMain.getColumn("number").setMoveable(false);
		tblMain.getColumn("name").setMoveable(false);
		
		buildProjectTree();
		//按钮
		this.btnAssign.setIcon(EASResource.getIcon("imgTbtn_distribute"));// 分配按钮		
		this.btnDisAssign.setIcon(EASResource.getIcon("imgTbtn_undistribute"));// 反分配按钮
		this.btnProjectAttachment.setIcon(EASResource.getIcon("imgTbtn_createcredence"));// 工程附件按钮
		this.btnCostAccountImport.setIcon(EASResource.getIcon("imgTbtn_citetree"));//引入按钮
		this.btnTemplateImport.setIcon(EASResource.getIcon("imgTbtn_importcyclostyle"));//引入按钮
		this.btnSave.setIcon(EASResource.getIcon("imgTbtn_save"));//引入按钮
		
		//菜单
		this.menuItemAssign.setIcon(EASResource.getIcon("imgTbtn_distribute"));
		this.menuItemDisAssign.setIcon(EASResource.getIcon("imgTbtn_undistribute"));
		this.menuItemProjectAttachment.setIcon(EASResource.getIcon("imgTbtn_createcredence"));
		this.menuItemImport.setIcon(EASResource.getIcon("imgTbtn_citetree"));
		this.menuItemTemplateImport.setIcon(EASResource.getIcon("imgTbtn_importcyclostyle"));
		//清除
		this.menuItemAssignToOrg.setVisible(false);
		
		actionEnterDB.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_staruse"));
		actionCancelEnterDB.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_forbid"));

		this.btnProjectAttachment.setEnabled(true);
		this.menuItemProjectAttachment.setEnabled(true);
		if (OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString())) {
			this.menuItemTemplateImport.setVisible(true);
			this.menuItemTemplateImport.setEnabled(true);

			this.btnTemplateImport.setVisible(true);
			this.btnTemplateImport.setEnabled(true);
			
			actionEnterDB.setEnabled(true);
			actionCancelEnterDB.setEnabled(true);
		} else {
			this.menuItemTemplateImport.setVisible(false);
			this.btnTemplateImport.setVisible(false);
			actionEnterDB.setVisible(false);
			actionEnterDB.setEnabled(false);
			actionCancelEnterDB.setVisible(false);
			actionCancelEnterDB.setEnabled(false);
			tblMain.getColumn("isEnterDB").getStyleAttributes().setHided(true);
		}
//		this.treeMain.setSelectionRow(0);
		if (treeMain.getRowCount() >= 1) {
			treeMain.setSelectionRow(0);
		}
		tblMain.getColumn("number").setWidth(160);
		tblMain.getColumn("name").setWidth(140);
		tblMain.getColumn("description").setWidth(100);
		
		tblMain.getColumn("isEnabled").setWidth(50);
		tblMain.getColumn("assigned").setWidth(50);
		tblMain.getColumn("isCostAccount").setWidth(50);
//		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
//		this.tblMain.getSelectManager().setSelectMode(2);// 行选
		
		actionQuery.setEnabled(false);
		actionQuery.setVisible(false);
		//集团的科目导入功能
		
		if(OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentOrgUnit().getId().toString())){
			actionImportData.setVisible(true);
			actionImportData.setEnabled(true);
			btnTemplateImport.setVisible(true);
		}else{
			actionImportData.setVisible(false);
			actionImportData.setEnabled(false);
			btnTemplateImport.setVisible(false);
		}
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		
		//仅允许财务组织的逐级分配
		if(SysContext.getSysContext().getCurrentOrgUnit().isIsCompanyOrgUnit()){
			actionAssignToOrg.setEnabled(true);
		}else{
			actionAssignToOrg.setEnabled(false);
		}
		//vincent_zhu mondify 
		//实体成本中心不能使用分配按钮
		if (SysContext.getSysContext().getCurrentFIUnit() != null && !SysContext.getSysContext().getCurrentFIUnit().isIsBizUnit()) {

			if (SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit()) {
				btnAssign.setEnabled(false);
				actionAssign.setEnabled(false);
			} else {
				btnAssign.setEnabled(true);
				actionAssign.setEnabled(true);
			}
		}
		//End
		
		//反分配在客户现场出现了不可修复的数据错误且对性能有较大的影响,暂时不提供此功能 by sxhong 2007-12-04
		actionDisAssign.setEnabled(false);
		actionDisAssign.setVisible(false);
		//设置可以保存当前样式
		tHelper = new TablePreferencesHelper(this);
	}
	
	
	public void onLoad() throws Exception {
		
		super.onLoad();
		
		init();
		
		showFields();
		
		this.isUIShow = true ;
		
		//工具栏及菜单栏上“工程附件”。此按钮无用，建议隐藏掉。
		actionProjectAttachment.setVisible(false);
		btnCostAccountImport.setVisible(false);
		menuItemImport.setVisible(false);
	}

	private void appendFunToCostAccount() throws BOSException {
		createLevelTree();
		buildCustomStage();
	}

	/**
	 * 初始化时构造Tree，几乎不用重载
	 */
	protected void initTree() throws Exception {

	}

	private Map nodes = new HashMap();
	
	private Set tbIds = new HashSet();
	
	protected void buildCustomStage() throws BOSException {
		if (tbIds.isEmpty())
			return;
		
		if(nodes != null &&  !nodes.isEmpty()){
			nodes.clear();
		}
		
		Map colorMap = CostAccountHelper.BytesToObject();
		Map costAccountEntry = new HashMap();
		
		
		EntityViewInfo viewInfo = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("stageEntrys.id"));
		sic.add(new SelectorItemInfo("stageEntrys.value"));
		sic.add(new SelectorItemInfo("stageEntrys.measureStage.id"));
		sic.add(new SelectorItemInfo("stageEntrys.measureStage.number"));
		sic.add(new SelectorItemInfo("stageEntrys.measureStage.name"));
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("name"));
		viewInfo.setSelector(sic);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", tbIds, CompareType.INCLUDE));
		viewInfo.setFilter(filter);

		ICostAccount costActSrv = CostAccountFactory.getRemoteInstance();
		CostAccountCollection cac = costActSrv.getCostAccountCollection(viewInfo);
		CostAccountInfo caInfo = null;
		for (int i = 0, size = cac.size(); i < size; i++) {
			caInfo = cac.get(i);
			AccountStageCollection acctStageColl = caInfo.getStageEntrys();
			if (acctStageColl == null) {
				continue;
			}
			AccountStageInfo acctStageInfo = null;
			Map entrys = new HashMap();
			for (int j = 0; j < acctStageColl.size(); j++) {
				acctStageInfo = acctStageColl.get(j);
				if (acctStageInfo.getMeasureStage() != null) {
					entrys.put(acctStageInfo.getMeasureStage().getNumber(), acctStageInfo);
				}
			}
			costAccountEntry.put(caInfo.getId().toString(), entrys);
		}

		int colCount = this.tblMain.getColumnCount();
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			IRow row = tblMain.getRow(i);
			String id = row.getCell("id").getValue().toString();
			buildGridColor(colorMap, row);
			buildCostAccountStage(id, costAccountEntry, colCount, i);
			nodes.put(id, row);
		}
		this.tblMain.reLayoutAndPaint();
	}
	
	private Set costAccounts = new HashSet();
	private Set beDeleteList = new HashSet();

	/**
	 * 对非明细的处理
	 * @param selectedId
	 * @param id
	 * @param isCanDelete
	 */
	private void getParentNodes(String selectedId,String id ,boolean isCanDelete){
		
		IRow row = (IRow) nodes.get(id);
		
		String parentId = row.getCell("parent.id").getValue().toString();
		IRow parentRow = (IRow) nodes.get(parentId);
		
		//如果子节点有一个不能被删除则父节点也不能设置删除
		boolean canDel  = false ;
		if(!isCanDelete){
			parentRow.setUserObject(Boolean.valueOf(false));
			canDel  = false ;
			
		}else{//如果子节点能删除则看这个节点的兄弟节点是否都能被删除
			
			//如果直接子节点下所有的都能删除
			boolean isAllSubNodeCanDelete = checkNextLevelSubNodes(parentRow);
			
			if(isAllSubNodeCanDelete){
				canDel = checkedValidatedData(parentRow);
				if(canDel){//如果父节点能被删除
					parentRow.setUserObject(Boolean.valueOf(true));
					beDeleteList.add(parentId);
				}else{
					parentRow.setUserObject(Boolean.valueOf(false));
					canDel  = false ;
				}
			}else{
				parentRow.setUserObject(Boolean.valueOf(false));
				canDel  = false ;
			}
		}
		
		if(row.getCell("parent.id").getValue() == null 
				|| selectedId.equals(row.getCell("parent.id").getValue().toString())){
			return ;
		}
		
		getParentNodes(selectedId,parentId,canDel);
	}
	
	/**
	 * @param id
	 * @param rowSets
	 */
	private void recursionParentNodes(String id ,Set rowSets){
		
		IRow row = (IRow) nodes.get(id);
		if(row==null){
			return;
		}
		rowSets.add(row);
		if(row.getCell("parent.id").getValue() == null){
			return ;
		}
		String parentId = row.getCell("parent.id").getValue().toString();
		if (parentId.equals(row.getCell("id").getValue().toString()))
			return;
		recursionParentNodes(parentId,rowSets);
	}
	
	
	private void recursionSubNodes(IRow row ,Set sets){
		NumberExpandInfo costAccountNumberExpandInfo = (NumberExpandInfo) row.getCell("number").getValue();
		String costAccountNumber = costAccountNumberExpandInfo.getNumber();
		String costAccountId = (String) row.getCell("id").getValue();
		
		int i = row.getRowIndex() + 1;
		IRow child;
		for (int count = tblMain.getRowCount(); i < count; i++) {
			child = tblMain.getRow(i);
			
			NumberExpandInfo childNumberExpandInfo = (NumberExpandInfo) child.getCell("number").getValue();
			String childNumber = childNumberExpandInfo.getNumber();

			if (childNumber.startsWith(costAccountNumber)) {
				String parentId = (String) child.getCell("parent.id").getValue();
				sets.add(child);
				if (parentId != null && parentId.equals(costAccountId)) {
					sets.add(child);
					recursionSubNodes(child,sets);
				} else {
					continue;
				}
			} else {
				break;
			}
		}
	}
	
	private void getAllDetailsSubNodes(IRow row ,Set sets){
		NumberExpandInfo costAccountNumberExpandInfo = (NumberExpandInfo) row.getCell("number").getValue();
		String costAccountNumber = costAccountNumberExpandInfo.getNumber();
		String costAccountId = (String) row.getCell("id").getValue();
		
		int i = row.getRowIndex() + 1;
		IRow child;
		for (int count = tblMain.getRowCount(); i < count; i++) {
			child = tblMain.getRow(i);
			
			NumberExpandInfo childNumberExpandInfo = (NumberExpandInfo) child.getCell("number").getValue();
			String childNumber = childNumberExpandInfo.getNumber();

			if (childNumber.startsWith(costAccountNumber)) {
				String parentId = (String) child.getCell("parent.id").getValue();
				if (parentId != null && parentId.equals(costAccountId)) {
					if(((Boolean)child.getCell("isLeaf").getValue()).booleanValue()){
						sets.add(child);
					}
					getAllDetailsSubNodes(child,sets);
				} else {
					continue;
				}
			} else {
				break;
			}
		}
	}
	

	private void sourceStage(int rowIndex,int colIndex,boolean isSelected){
		String key = this.tblMain.getColumnKey(colIndex);
		
		if(key != null){
			if(!key.startsWith(COLKEY_PER)){
				return ;
			}
			this.tblMain.getCell(rowIndex, colIndex).setValue(Boolean.valueOf(isSelected));
			if(isSelected){
				colIndex = colIndex +1 ;
				
			}else{
				colIndex = colIndex -1 ;
			}
			sourceStage(rowIndex,colIndex,isSelected);
		}
	}
	
	
	/**
	 * @param colCount
	 * @param i
	 */
	private void buildCostAccountStage(String id , Map costAccountEntry, int colCount, int i) {
		
		Map entry =  (Map) costAccountEntry.get(id);		
		for(int j=0;j<colCount;j++){
			IColumn col = this.tblMain.getColumn(j);
			if(col.getKey().startsWith(COLKEY_PER)){
				if(entry == null || entry.isEmpty()){
					this.tblMain.getCell(i,j).setValue(Boolean.TRUE);
				}else{
					AccountStageInfo stageInfo = (AccountStageInfo) entry.get(col.getKey().substring(2));
					if(stageInfo != null){
						this.tblMain.getCell(i,j).setValue(Boolean.valueOf(stageInfo.isValue()));
						this.tblMain.getCell(i,j).setUserObject(stageInfo.getId().toString());
					}else{
						this.tblMain.getCell(i,j).setValue(Boolean.TRUE);
					}
				}
			}
		}
	}

	/**
	 * @param colorMap
	 * @param row
	 */
	private void buildGridColor(Map colorMap, IRow row) {
		if(!colorMap.isEmpty()){
			int level = Integer.parseInt(row.getCell("level").getValue().toString());
			if(((Boolean)row.getCell("isLeaf").getValue()).booleanValue()){
				row.getStyleAttributes().setBackground(Color.WHITE);
			}else{
				Color color =  (Color)colorMap.get(String.valueOf(level));
				if(color == null){
					row.getStyleAttributes().setBackground(Color.WHITE);
				}else{
					row.getStyleAttributes().setBackground(color);
				}
			}
		}
	}
	
    /**
     * 
     */
    protected void createLevelTree(){
    	if(this.tblMain.getRowCount()<1){
    		return ;
    	}
    	tbIds = new HashSet();
    	
		int maxLevel = 0;
		int[] levelArray = new int[this.tblMain.getRowCount()];

		for (int i = 0; i < tblMain.getRowCount(); i++) {
			IRow row = tblMain.getRow(i);
			
			tbIds.add(row.getCell("id").getValue().toString());
			
			int level = Integer.parseInt(row.getCell("level").getValue().toString());
			levelArray[i] = level;
			row.setTreeLevel(level - 1);
		}
		
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			maxLevel = Math.max(levelArray[i], maxLevel);
		}
		tblMain.getTreeColumn().setDepth(maxLevel);
		
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);		
    }
	
	protected void buildProjectTree() throws Exception {
		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder(false);
		projectTreeBuilder.setDevPrjFilter(false);
		treeMain.setShowsRootHandles(true);
		projectTreeBuilder.build(this, treeMain, actionOnLoad);
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		int columnIndex = e.getColIndex();
		int rowIndex = e.getRowIndex();
		if (e.getClickCount() == 1) {
			// 选中第二列(number)时
			if (columnIndex == 0) {
				IRow row = tblMain.getRow(rowIndex);
				if (row == null)
					return;
				Object obj = row.getCell("number").getValue();
				if (obj instanceof NumberExpandInfo) {
					NumberExpandInfo numberExpandInfo = (NumberExpandInfo) row.getCell("number").getValue();
					if (treeRender.inRect(numberExpandInfo, e.getX(), e.getY())) {
						setTreeDisplayStyle(row);
					}
				}
				return;
			}else{
			}
		}else{
			if(columnIndex == -1){
				return ;
			}
			if(tblMain.getColumn(columnIndex).getKey().startsWith(COLKEY_PER)){
				Boolean flag = (Boolean) this.tblMain.getCell(rowIndex, columnIndex).getValue();
				if(flag.equals(Boolean.TRUE)){
					IRow row = tblMain.getRow(rowIndex);
					tblMain.getCell(rowIndex, columnIndex).setValue(Boolean.FALSE);
					
					//父级科目取消，其子级科目自动取消
					Set rowsets = new HashSet();
					
					recursionSubNodes(row,rowsets);
					Iterator iter = rowsets.iterator();
					while(iter.hasNext()){
						IRow rowCtl = (IRow) iter.next();
						rowCtl.getCell(columnIndex).setValue(Boolean.FALSE);
						
						sourceStage(rowCtl.getRowIndex(),columnIndex,false);
					}
					sourceStage(rowIndex,columnIndex,false);
					
				}else{
					tblMain.getCell(rowIndex, columnIndex).setValue(Boolean.TRUE);
					
					Set rows = new HashSet();
					String id = tblMain.getCell(rowIndex, "id").getValue().toString();
					recursionParentNodes(id,rows);
					//设置父级节点，子级科目勾选，其父级科目自动勾选
					Iterator iter = rows.iterator();
					while(iter.hasNext()){
						IRow rowCtl = (IRow) iter.next();
						rowCtl.getCell(columnIndex).setValue(Boolean.TRUE);
						
						sourceStage(rowCtl.getRowIndex(),columnIndex,true);
					}
					//设置子级节点，父级科目勾选，其子级科目自动不勾选
				}
				isModify = true;
				return ;
			}
		}
		// 非空判断
		if (columnIndex < 0) {
			return;
		}
		if (e.getClickCount() == 2) {
			IRow curRow = tblMain.getRow(rowIndex);
			// 当前组织是否可勾选
			boolean isCanSelect = isCanSelected(curRow);
				// 可拆分科目勾选判断
			if (tblMain.getColumn(columnIndex).getKey().equals("isSplit")) {
				
				if (!((Boolean) tblMain.getRow(rowIndex).getCell("isSource").getValue()).booleanValue()) {
					return;
				}
				
				// 是否勾选
				Boolean flag = (Boolean) this.tblMain.getCell(rowIndex, columnIndex).getValue();
				// 勾选
				if (isCanSelect && flag.equals(Boolean.TRUE)) {
					IRow row = tblMain.getRow(rowIndex);
					tblMain.getCell(rowIndex, columnIndex).setValue(Boolean.FALSE);
					/**************** 不取消父级之前逻辑 begin ******************/
					Set rowsets = new HashSet();
					// 可拆分科目兄弟节点状态判断
					brothersRow(row, rowsets);
					Iterator it = rowsets.iterator();
					IRow rowCtl = null;
					while (it.hasNext()) {
						rowCtl = (IRow) it.next();
						rowCtl.getCell(columnIndex).setValue(Boolean.FALSE);
						// 兄弟节点取消的时候也取消其的子节点
						cancelChildRow(rowCtl);
					}

					// 父级科目取消，其子级科目自动取消
					recursionSubNodes(row, rowsets);
					Iterator iter = rowsets.iterator();
					while (iter.hasNext()) {
						rowCtl = (IRow) iter.next();
						rowCtl.getCell(columnIndex).setValue(Boolean.FALSE);
					}
					/**************** 不取消父级之前逻辑 end ******************/

					// /**************** 取消父级之后逻辑 begin ******************/
					// IRow rowCtl = null;
					// for (int i = 0; i < tblMain.getRowCount(); i++) {
					// rowCtl = tblMain.getRow(i);
					// rowCtl.getCell("isSplit").setValue(Boolean.FALSE);
					// }
					// /**************** 取消父级之后逻辑 end ******************/
				} else if (isCanSelect) {// 不勾选
					tblMain.getCell(rowIndex, columnIndex).setValue(Boolean.TRUE);

					Set rows = new HashSet();
					String id = tblMain.getCell(rowIndex, "id").getValue().toString();
					recursionParentNodes(id, rows);
					// 设置父级节点，子级科目勾选，其父级科目自动勾选
					Iterator iter = rows.iterator();
					while (iter.hasNext()) {
						IRow rowCtl = (IRow) iter.next();
						rowCtl.getCell(columnIndex).setValue(Boolean.TRUE);

					}

					// IRow tmpRow = tblMain.getRow(rowIndex);
					// int level = tmpRow.getTreeLevel();
					// IRow curRow = null;
					// for (int i = 0; i < tblMain.getRowCount(); i++) {
					// curRow = tblMain.getRow(i);
					// int curLevel = curRow.getTreeLevel();
					// if (curLevel > level) {
					// curRow.getCell(columnIndex).setValue(Boolean.TRUE);
					// }
					// }

					// 可拆分科目兄弟节点状态判断
					Set rowsets = new HashSet();
					IRow row = tblMain.getRow(rowIndex);
					brothersRow(row, rowsets);
					Iterator it = rowsets.iterator();
					while (it.hasNext()) {
						IRow rowCtl = (IRow) it.next();
						rowCtl.getCell(columnIndex).setValue(Boolean.TRUE);
					}
				}
				isModify = true;
				return;
			}
			
		} 
		super.tblMain_tableClicked(e);
	}


	public DefaultKingdeeTreeNode getSelectedTreeNode1() {
		return (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
	}

	public KDTreeNode getSelectedTreeNode() {
		return null;
	}

	//检查是否已被引用过
	private boolean checkAcctStageIsRef() throws Exception {
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		String objectId = null;
		Object obj = node.getUserObject();
		if(obj instanceof OrgStructureInfo) {
		}else if(obj instanceof CurProjectInfo && !((CurProjectInfo)obj).isIsLeaf()){
		}else if(obj instanceof CurProjectInfo && ((CurProjectInfo)obj).isIsLeaf()){
			objectId = ((CurProjectInfo)obj).getId().toString();
		}
		if(objectId==null){
			return false;
		}
		Map idMap = new HashMap();
		
		for(int i=0;i<tblMain.getRowCount();i++){
			IRow row = tblMain.getRow(i);
			for(int j=0;j<tblMain.getColumnCount();j++){
				String key = this.tblMain.getColumnKey(j);
				if(key != null){
					if(key.startsWith(COLKEY_PER)){
						String id = row.getCell("id").getValue().toString();
						Boolean isSelect = (Boolean)row.getCell(key).getValue();
						if(!isSelect.booleanValue()){
							idMap.put(id, Boolean.TRUE);
							
						}
					}
				}
			}
		}
		if(idMap.size()==0){
			return false;
		}
		Set idSet = new HashSet(idMap.keySet());
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select distinct entry.fcostaccountid from t_aim_measureentry entry ");
		builder.appendSql("inner join t_aim_measurecost head on head.fid=entry.fheadid ");
		builder.appendSql("where ");
		builder.appendParam("entry.fcostaccountid", idSet.toArray());
		builder.appendSql(" and head.fprojectid=? ");
		builder.addParam(objectId);
		
		builder.appendSql(" union all ");
		builder.appendSql("select distinct entry.fcostaccountid from t_aim_costentry entry ");
		builder.appendSql("inner join t_aim_aimcost head on head.fid=entry.fheadid ");
		builder.appendSql("where ");
		builder.appendParam("entry.fcostaccountid", idSet.toArray());
		builder.appendSql(" and head.FOrgOrProId=? ");
		builder.addParam(objectId);
		
		// 拆分,含跨项目分期
		builder.appendSql(" union all ");
		builder.appendSql("select distinct entry.fcostaccountid from T_AIM_CostSplitEntry entry ");
		builder.appendSql("inner join T_AIM_CostSplit head on head.fid=entry.fparentid ");
		builder.appendSql("where ");
		builder.appendParam("entry.fcostaccountid", idSet.toArray());
		
		IRowSet rs = builder.executeQuery();
		Map acctMap = new HashMap();
		while(rs.next()){
			acctMap.put(rs.getString("fcostaccountid"), Boolean.TRUE);
		}
		if(acctMap.size()==0){
			return false;
		}
		
		Map difMap = FDCHelper.getDifMap(acctMap, acctMap);
		Map errorDetailMap = new HashMap();
		if(difMap.size()>0){
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id",new HashSet(acctMap.keySet()),CompareType.INCLUDE));
			view.setFilter(filter);
			view.getSelector().add("longNumber");
			view.getSelector().add("name");
			CostAccountCollection accts = CostAccountFactory.getRemoteInstance().getCostAccountCollection(view);
			Object[][] data =new Object[accts.size()][2]; 
			errorDetailMap.put("head", new String[]{"科目编码","科目名称"});
			errorDetailMap.put("data", data);
			errorDetailMap.put("format", new Object[]{"","no",""});
			int i=0;
			for(Iterator iter=accts.iterator();iter.hasNext();i++){
				CostAccountInfo info=(CostAccountInfo)iter.next();
				data[i][0]=info.getLongNumber().replace('!', '.');
				data[i][1]=info.getName();
			}
			FDCMsgBox.showTableDetailAndOK(this, "科目已被数据引用，不能执行此操作!", errorDetailMap, 0);
			return true;
		}
		return false;
	}
	
	public void actionAccountSave_actionPerformed(ActionEvent e) throws Exception {
		
		
		//TODO
		String deleteStageSQL = " delete from T_FDC_Accountstage  where  fcostaccountid = ? " ;
		String insertStageSQL = " insert into t_fdc_accountstage (fid,fcostaccountid,fmeasurestageid,fvalue) values (?,?,?,?) "; 
		
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
		
		List paramsList = new ArrayList();
		Set idSet = new HashSet();
		for(int i=0;i<tblMain.getRowCount();i++){
			IRow row = tblMain.getRow(i);
			
			List deleteParams = new ArrayList();
			String id = row.getCell("id").getValue().toString();
			deleteParams.add(id);
			idSet.add(id);
			paramsList.add(deleteParams);
		}
		sqlBuilder.executeBatch(deleteStageSQL, paramsList);
		
		List updateParams4StageList  = new ArrayList(); 
		
		for(int i=0;i<tblMain.getRowCount();i++){
			IRow row = tblMain.getRow(i);
			
			for(int j=0;j<tblMain.getColumnCount();j++){
				String key = this.tblMain.getColumnKey(j);
				if(key != null){
					if(key.startsWith(COLKEY_PER)){
						List params = new ArrayList();

						params.add(BOSUuid.create("476BD8C3").toString());
						params.add(row.getCell("id").getValue().toString());
						
						ICell cell = row.getCell(j);
						MeasureStageInfo msInfo = (MeasureStageInfo)
											tblMain.getHeadRow(0).getCell(key).getUserObject();
						if(cell != null ){
							Boolean isChecked = (Boolean) cell.getValue();
							String acctStageId = msInfo.getId().toString();
							int value = 0 ;
							if(isChecked.booleanValue()){
								value = 1 ;
							}else{
								value = 0 ;
							}
							params.add(acctStageId);
							params.add(new Integer(value));
							
							updateParams4StageList.add(params);
						}
					}
				}
			}
		}
		if(checkAcctStageIsRef()){
			this.actionRefresh_actionPerformed(e);
			this.abort();
		}
		sqlBuilder.executeBatch(insertStageSQL, updateParams4StageList);
		// 保存可拆分科目状态
		this.saveAccountIsSplit();
		MsgBox.showInfo(this, "保存成功！");
		this.actionRefresh_actionPerformed(e);
	}

	/**
	 * 判断当前行是否有子结点，且测算阶段被勾选中的
	 * 
	 * 有子结点则返回当前第一个子结点下标
	 * 
	 * 无子结点则返回当前行下标
	 * 
	 * @param index
	 * @return
	 */
	private int isHasLeaf(int index, String colKey) {
		int currentLevel = new Integer(tblMain.getRow(index).getCell("level").getValue().toString()).intValue();
		for (int i = index + 1; i < tblMain.getRowCount(); i++) {
			int level = new Integer(tblMain.getRow(i).getCell("level").getValue().toString()).intValue();
			Boolean isChoose = (Boolean) tblMain.getRow(i).getCell(colKey).getValue();
			if (level == currentLevel + 1) {
				if (isChoose.booleanValue()) {
					return i;
				}
			}
		}
		return index;
	}

	protected void getRowSetBeforeFillTable(IRowSet rowSet) {
    	super.getRowSetBeforeFillTable(rowSet);
    }
	
	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception {
		boolean isSelectCurOrg = false;
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		CostCenterOrgUnitInfo currentCostUnit = SysContext.getSysContext().getCurrentCostUnit();
		CurProjectInfo curProject = null;
		if(!currentCostUnit.isIsBizUnit()) {
			Object userObject2 = node.getUserObject();
			if(userObject2 instanceof OrgStructureInfo) {
				String id = ((OrgStructureInfo)userObject2).getUnit().getId().toString();
				if(id.equals(currentCostUnit.getId().toString())) {
					isSelectCurOrg = true;
				}
			}else if(userObject2 instanceof CurProjectInfo){
				curProject = (CurProjectInfo)userObject2;
			}
		}
		else {
			isSelectCurOrg = true;
		}
		
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex != -1) {
			if (this.tblMain.getRow(rowIndex).getCell("isEnabled") != null) {
				boolean status = ((Boolean) this.tblMain.getRow(rowIndex).getCell("isEnabled").getValue()).booleanValue();
				// 随着每一行的isEnabled的值改变，两个WBT的状态也改变
				changeWBTEnabeld(status);
				// 刷新编辑按钮状态
			}
			// 不是集团;虚体也支持新增12.6.6
			if ((SysContext.getSysContext().getCurrentFIUnit() != null && (!SysContext.getSysContext().getCurrentFIUnit().isIsOnlyUnion()))
					|| (SysContext.getSysContext().getCurrentCostUnit() != null)) {
				if (this.tblMain.getRow(rowIndex).getCell("isSource") != null) {
					boolean statusSource = ((Boolean) this.tblMain.getRow(rowIndex).getCell("isSource").getValue()).booleanValue();
					if (statusSource) {// 源，已分配的源不允许删除(允许修改)
						// this.btnRemove.setEnabled(false);
						// this.menuItemRemove.setEnabled(false);
						// this.btnEdit.setEnabled(false);
						// this.menuItemEdit.setEnabled(false);//
						if (this.tblMain.getRow(rowIndex).getCell("assigned") != null
								 && isSelectCurOrg) {
							boolean statusAssign = ((Boolean) this.tblMain.getRow(rowIndex).getCell("assigned").getValue()).booleanValue();
							if (statusAssign) {
								this.btnRemove.setEnabled(false);
								this.btnEdit.setEnabled(true);
								this.btnAddNew.setEnabled(true);
								// this.btnCancel.setEnabled(false);
								menuItemCancel.setEnabled(false);
								this.menuItemRemove.setEnabled(false);
								this.menuItemEdit.setEnabled(true);
								this.menuItemAddNew.setEnabled(true);
							} else {
								this.btnAddNew.setEnabled(true);
								this.btnEdit.setEnabled(true);
								this.btnRemove.setEnabled(true);
								//								this.btnCancel.setEnabled(true);
								//								this.btnCancelCancel.setEnabled(false);
								this.menuItemAddNew.setEnabled(true);
								this.menuItemEdit.setEnabled(true);
								this.menuItemRemove.setEnabled(true);

							}
						} else {
							boolean statusAssign = ((Boolean) this.tblMain.getRow(rowIndex)
									.getCell("assigned").getValue()).booleanValue();
							if (statusAssign) {
								this.btnCancel.setEnabled(false);
								menuItemCancel.setEnabled(false);
							} 
							
							//引入进来的设定为源，引入进来的科目支持删除
							btnRemove.setEnabled(true);
							actionRemove.setEnabled(true);
						}
						
					} else {// 非源(分配下来的),不允许修改删除,不允许禁用
						this.btnRemove.setEnabled(false);
						this.btnEdit.setEnabled(false);

						// this.btnCancel.setEnabled(false);
						this.menuItemRemove.setEnabled(false);
						this.menuItemEdit.setEnabled(false);
						// this.menuItemCancel.setEnabled(false);

						// 如果选中的不是非源的叶结点,不允许新增
						
						//addd by zkyan 为什么工程项目's 是开发项目时就不能新增呢，求解？？？？先去掉
						if (((Boolean) this.tblMain.getRow(rowIndex).getCell("isLeaf")
								.getValue()).booleanValue()) {
							//																 && (isSelectCurOrg || (curProject != null && curProject.isIsLeaf() && !curProject.isIsDevPrj()))) {
							//								&& (isSelectCurOrg || (curProject != null && curProject.isIsLeaf()))) {

							//可研，明细项目允许
							// 如果选中的是叶节点,但是该叶节点所在组织
								this.btnAddNew.setEnabled(true);
								this.menuItemAddNew.setEnabled(true);
						} else {//如果选中的不是叶结点，要判断其下级节电是否是源
							if (this.tblMain.getRow(rowIndex + 1) != null) {
								if (((Boolean) this.tblMain.getRow(rowIndex + 1).getCell(
										"isSource").getValue()).booleanValue()) {
//										 && isSelectCurOrg) {
									//下级为源
									this.btnAddNew.setEnabled(true);
									this.menuItemAddNew.setEnabled(true);
								} else {
									//非源
									this.btnAddNew.setEnabled(false);
									this.menuItemAddNew.setEnabled(false);
								}
							}
							//							boolean isCanAdd = ((Boolean) this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("isCanAdd").getValue()).booleanValue();
							//							// 分配为可新增下级
							//							if (isCanAdd) {
							//								this.btnAddNew.setEnabled(true);
							//								this.menuItemAddNew.setEnabled(true);
							//							}
						}
						// disabledWBT();
					}
				}

			} else {// 如果当前的组织是集团，并且当前选中的数据其节点不是集团，那么不允许编辑
				if (this.tblMain.getRow(rowIndex).getCell("fullOrgUnit.id").getValue() != null) {
					String ouid = this.tblMain.getRow(rowIndex).getCell("fullOrgUnit.id").getValue().toString();
					if (!OrgConstants.DEF_CU_ID.equals(ouid)) {
						this.btnAddNew.setEnabled(false);
						this.btnEdit.setEnabled(false);
						this.btnRemove.setEnabled(false);
						this.btnCostAccountImport.setEnabled(false);
						this.btnAssign.setEnabled(false);
						actionAssign.setEnabled(false);
						this.btnDisAssign.setEnabled(false);

						this.menuItemAddNew.setEnabled(false);
						this.menuItemEdit.setEnabled(false);
						this.menuItemRemove.setEnabled(false);
						this.menuItemImport.setEnabled(false);
						this.menuItemAssign.setEnabled(false);
						this.menuItemDisAssign.setEnabled(false);

					} else {
						this.btnAddNew.setEnabled(true);
						this.btnEdit.setEnabled(true);
						this.btnCostAccountImport.setEnabled(true);
						this.btnAssign.setEnabled(true);
						actionAssign.setEnabled(true);
						this.btnDisAssign.setEnabled(true);

						this.menuItemImport.setEnabled(true);
						this.menuItemAddNew.setEnabled(true);
						this.menuItemEdit.setEnabled(true);
						this.menuItemRemove.setEnabled(true);
						this.menuItemAssign.setEnabled(true);
						this.menuItemDisAssign.setEnabled(true);
						// 已分配的不允许删除
						if (this.tblMain.getRow(rowIndex).getCell("assigned") != null) {
							boolean status = ((Boolean) this.tblMain.getRow(rowIndex).getCell("assigned").getValue()).booleanValue();
							if (status) {
								// this.actionCancel.setEnabled(false);
								this.btnRemove.setEnabled(false);
								this.menuItemRemove.setEnabled(false);
							} else {
								this.btnRemove.setEnabled(true);
								this.menuItemRemove.setEnabled(true);
							}
						}

					}
				} else {
					this.btnAddNew.setEnabled(false);
					this.btnEdit.setEnabled(false);
					this.btnRemove.setEnabled(false);
					this.btnCostAccountImport.setEnabled(false);
					this.btnAssign.setEnabled(false);
					actionAssign.setEnabled(false);
					this.btnDisAssign.setEnabled(false);

					this.menuItemAddNew.setEnabled(false);
					this.menuItemEdit.setEnabled(false);
					this.menuItemRemove.setEnabled(false);
					this.menuItemImport.setEnabled(false);
					this.menuItemAssign.setEnabled(false);
					this.menuItemDisAssign.setEnabled(false);
				}
				if(!isSelectCurOrg) {
					disabledWBT();
				}
				
			}

		} else {
			disabledWBT();
		}

	}

	// 递归调用选中节点。
	private void getSelectNode(DefaultKingdeeTreeNode selectNode) throws Exception {
		if (selectNode.getChildCount() == 0) {
			treeMain.setSelectionNode(selectNode);
		} else {
			for (int j = 0; j < selectNode.getChildCount(); j++) {
				DefaultKingdeeTreeNode tempNode = (DefaultKingdeeTreeNode) selectNode.getChildAt(j);
				if (tempNode.getUserObject() instanceof OrgStructureInfo) {
					getSelectNode(tempNode);
				} else {
					TreeBaseInfo tempTree = (TreeBaseInfo) tempNode.getUserObject();
					TreeBaseInfo node = (TreeBaseInfo) selectNode.getUserObject();
					if (StringUtility.isMatch(tempTree.getId().toString(), node.getId().toString(), true)) {
						treeMain.setSelectionNode((DefaultKingdeeTreeNode) tempNode);
						break;
					} else {
						getSelectNode(tempNode);
					}
				}
			}
		}
	}

	/**
	 * 随着每一行的isEnabled的值改变，两个btn的状态也改变
	 * 
	 * @param isEnabled
	 *            boolean
	 */
	private void changeWBTEnabeld(boolean isEnabled) {
		this.actionCancel.setEnabled(isEnabled);// 禁用按钮
		btnCancel.setEnabled(isEnabled);
		menuItemCancel.setEnabled(isEnabled);
		this.actionCancelCancel.setEnabled(!isEnabled);// 启用按钮
		this.btnCancelCancel.setEnabled(!isEnabled);// 启用按钮
		this.menuItemCancelCancel.setEnabled(!isEnabled);// 启用按钮
	}

	/**
	 * 把启用/禁止按钮disabled
	 */
	private void disabledWBT() {
		//任何组织下，启用、禁用按钮可都用。

		//		this.actionCancel.setEnabled(false);// 禁用按钮
		//		this.actionCancelCancel.setEnabled(false);// 启用按钮
	}

	/**
	 * 树形的CU过滤处理。
	 */
	protected FilterInfo getDefaultFilterForTree() {
		return super.getDefaultFilterForTree();
	}

	/**
	 * 默认进行当前CU的过滤。子类可重载。
	 */
	protected FilterInfo getDefaultFilterForQuery() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return null;
		}
		if (OrgViewUtils.isTreeNodeDisable(node)) {
			return null;
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.getFilterItems().add(new FilterItemInfo("curProject.id", projectInfo.getId().toString(), CompareType.EQUALS));
			this.mainQuery.setFilter(filterInfo);
			tblMain.removeRows();// 触发新查询
		} else if (node.getUserObject() instanceof OrgStructureInfo) {

			OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
			if (oui == null || oui.getUnit() == null)
				return null;
			FullOrgUnitInfo info = oui.getUnit();
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", info.getId().toString(), CompareType.EQUALS));
			// filterInfo.setMaskString(" #0 ");
			this.mainQuery.setFilter(filterInfo);
			// execQuery();
			tblMain.removeRows();// 触发新查询

		}
		return this.mainQuery.getFilter();
	}

	/**
	 * output treeMain_valueChanged method
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		//		if (OrgViewUtils.isTreeNodeDisable(node)) {
		//			return;
		//		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			project = projectInfo;
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.getFilterItems().add(new FilterItemInfo("curProject.id", projectInfo.getId().toString(), CompareType.EQUALS));
			this.mainQuery.setFilter(filterInfo);
			tblMain.removeRows();// 触发新查询
			// 如果当前的组织是集团，并且当前选中的节点是工程项目,不允许新增
			if ((SysContext.getSysContext().getCurrentFIUnit() != null && (!SysContext.getSysContext().getCurrentFIUnit().isIsOnlyUnion()))
					|| (SysContext.getSysContext().getCurrentCostUnit() != null)
					&& SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit()) {
				// 不是集团,不能新增非明细节点
				if (this.tblMain.getSelectManager().getActiveRowIndex() == -1) {
					this.btnAddNew.setEnabled(false);
					this.btnEdit.setEnabled(false);
					this.btnRemove.setEnabled(false);
					// this.btnCostAccountImport.setEnabled(false);
					// this.btnAssign.setEnabled(false);
					// this.btnDisAssign.setEnabled(false);
					this.menuItemAddNew.setEnabled(false);
					this.menuItemEdit.setEnabled(false);
					this.menuItemRemove.setEnabled(false);
				}
				if(node.isLeaf()&&!projectInfo.isIsDevPrj()){
					this.btnAddNew.setEnabled(true);
					this.btnEdit.setEnabled(true);
					this.btnRemove.setEnabled(true);
					this.menuItemAddNew.setEnabled(true);
					this.menuItemEdit.setEnabled(true);
					this.menuItemRemove.setEnabled(true);
				}
				//

			} else {// 是集团
				this.btnAddNew.setEnabled(false);
				this.btnEdit.setEnabled(false);
				this.btnRemove.setEnabled(false);
				this.btnCostAccountImport.setEnabled(false);
				this.btnCostAccountImport.setVisible(false);
				
				this.btnAssign.setEnabled(false);
				actionAssign.setEnabled(false);
				this.btnDisAssign.setEnabled(false);
				this.menuItemAddNew.setEnabled(false);
				this.menuItemEdit.setEnabled(false);
				this.menuItemRemove.setEnabled(false);
				
				this.menuItemImport.setEnabled(false);
				this.menuItemImport.setVisible(false);
				
				
			}
			//
			if (node.isLeaf()) {
				actionAssign.setEnabled(false);
				actionDisAssign.setEnabled(false);
			} else {
				actionAssign.setEnabled(true);
				actionDisAssign.setEnabled(true);
			}
		} else if (node.getUserObject() instanceof OrgStructureInfo) {

			OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
			if (oui == null || oui.getUnit() == null)
				return;
			FullOrgUnitInfo info = oui.getUnit();
			orgUnit = info;
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", info.getId().toString(), CompareType.EQUALS));
			this.mainQuery.setFilter(filterInfo);
			tblMain.removeRows();// 触发新查询
			// 如果当前的组织是集团，并且当前选中的节点不是集团，那么不允许新增
			if ((SysContext.getSysContext().getCurrentFIUnit() != null && (!SysContext.getSysContext().getCurrentFIUnit().isIsOnlyUnion()))
					|| (SysContext.getSysContext().getCurrentCostUnit() != null && SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit())) {
				// 不是集团,不能新增非明细节点
				if (this.tblMain.getSelectManager().getActiveRowIndex() == -1) {
					this.btnAddNew.setEnabled(false);
					this.btnEdit.setEnabled(false);
					this.btnRemove.setEnabled(false);
					// this.btnCostAccountImport.setEnabled(false);
					// this.btnAssign.setEnabled(false);
					// this.btnDisAssign.setEnabled(false);
					this.menuItemAddNew.setEnabled(false);
					this.menuItemEdit.setEnabled(false);
					this.menuItemRemove.setEnabled(false);
				}

			} else {// 是虚体,包括集团
				if (!OrgConstants.DEF_CU_ID.equals(info.getId().toString())) {
					this.btnAddNew.setEnabled(false);
					this.btnEdit.setEnabled(false);
					this.btnRemove.setEnabled(false);
					this.btnCostAccountImport.setEnabled(false);
					this.btnCostAccountImport.setVisible(false);
					this.btnAssign.setEnabled(false);
					actionAssign.setEnabled(false);
					this.btnDisAssign.setEnabled(false);
					this.menuItemAddNew.setEnabled(false);
					this.menuItemEdit.setEnabled(false);
					this.menuItemRemove.setEnabled(false);
					
					this.menuItemImport.setEnabled(false);
					this.menuItemImport.setVisible(false);
					//					this.btnCancel.setVisible(false);
					//					this.menuItemCancel.setVisible(false);
					//					this.btnCancelCancel.setVisible(false);
					//					this.menuItemCancelCancel.setVisible(false);

				} else {
					this.btnAddNew.setEnabled(true);
					this.btnEdit.setEnabled(true);
					this.btnRemove.setEnabled(true);
					
					
					this.btnCostAccountImport.setEnabled(false);
					this.btnCostAccountImport.setVisible(false);
					
					
					this.btnAssign.setEnabled(true);
					actionAssign.setEnabled(true);
					this.btnDisAssign.setEnabled(true);
					this.menuItemAddNew.setEnabled(true);
					this.menuItemEdit.setEnabled(true);
					this.menuItemRemove.setEnabled(true);
					
					this.menuItemImport.setEnabled(true);
					this.menuItemImport.setVisible(true);
					//					this.btnCancel.setVisible(true);
					//					this.btnCancelCancel.setVisible(true);
					//					this.menuItemCancel.setVisible(true);
					//					this.menuItemCancelCancel.setVisible(true);
				}
			}
		}
		if (node.isLeaf()) {
			this.btnAssign.setEnabled(false);
			actionAssign.setEnabled(false);
			this.btnDisAssign.setEnabled(false);
		} else {
			this.btnAssign.setEnabled(true);
			actionAssign.setEnabled(true);
			this.btnDisAssign.setEnabled(true);
		}
		
		//当前组织是集团且选择的是集团结点，可以操作是否进行成本数据库
		if(OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentOrgUnit().getId().toString())
				&&(node.getUserObject() instanceof OrgStructureInfo)
				&&node.getLevel()==0){
			actionEnterDB.setEnabled(true);
			actionEnterDB.setVisible(true);
			actionCancelEnterDB.setEnabled(true);
			actionCancelEnterDB.setVisible(true);
			tblMain.getColumn("isEnterDB").getStyleAttributes().setHided(false);
		}else{
			actionEnterDB.setEnabled(false);
			actionEnterDB.setVisible(false);
			actionCancelEnterDB.setEnabled(false);
			actionCancelEnterDB.setVisible(false);
			tblMain.getColumn("isEnterDB").getStyleAttributes().setHided(true);
		}
		//TODO
		//vincent_zhu mondify 
		//实体成本中心不能使用分配按钮
		if (SysContext.getSysContext().getCurrentFIUnit() != null && !SysContext.getSysContext().getCurrentFIUnit().isIsBizUnit()) {
		
		if(SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit()){
			btnAssign.setEnabled(false);
			actionAssign.setEnabled(false);
		}else{
			if (node.isLeaf()) {
					this.btnAssign.setEnabled(false);
					actionAssign.setEnabled(false);
					this.btnDisAssign.setEnabled(false);
				} else {
					this.btnAssign.setEnabled(true);
					actionAssign.setEnabled(true);
					this.btnDisAssign.setEnabled(true);
				}
		}
		}
		this.appendFunToCostAccount();
		//End
		//无论进入什么组织，在左边项目树中选择 非明细项目 时，工具栏中“引入”按钮均不显示
		//无论进入什么组织，在左边项目树中选择 明细项目 时，工具栏中“引入”按钮显示
		if (node.getUserObject() != null) {
			if (node.getUserObject() instanceof CurProjectInfo && node.isLeaf()) {
				btnCostAccountImport.setEnabled(true);
				btnCostAccountImport.setVisible(true);
				menuItemImport.setEnabled(true);
				menuItemImport.setVisible(true);
			} else {
				btnCostAccountImport.setEnabled(false);
				btnCostAccountImport.setVisible(false);
				menuItemImport.setEnabled(false);
				menuItemImport.setVisible(false);
			}
		}
	}

	protected void showResultMessage(String message) {
		setMessageText(message);
		showMessage();
	}

	
	public void refreshList() throws Exception {
		super.refreshList();
		
		this.appendFunToCostAccount();
	}
	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		int selectRow=getMainTable().getSelectManager().getActiveRowIndex();
		
		IRow row1 = this.tblMain.getRow(selectRow);
		if(row1!=null){
			String rowId= row1.getCell("id").getValue().toString();
			
			//
			CostAccountInfo caInfo= (CostAccountInfo)CostAccountFactory.getRemoteInstance()
					.getValue("select * where id= '"+ rowId + "'");
			//获取集团是否充许公司或项目新增
			boolean isCanAdd=caInfo.isIsCanAdd();
			//判断是不是公司或项目
			if((SysContext.getSysContext().getCurrentFIUnit() != null && (!SysContext.getSysContext().getCurrentFIUnit().isIsOnlyUnion()))
					|| (SysContext.getSysContext().getCurrentCostUnit() != null && SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit())){
				//集团不充许新增的情况下


				if(!isCanAdd){
					MsgBox.showWarning(this, "集团管控科目，不允许新增下级成本科目！");
					return;				
				}
			}
			
		
		}

		// 上级获取
		int rowNo = tblMain.getSelectManager().getActiveRowIndex();
		if (rowNo != -1) {// 选中了上级
			IRow row = this.tblMain.getRow(rowNo);
			this.getUIContext().put("upId", row.getCell("id").getValue().toString());
		} else {
			// 如果没有选中
			// 节点获取
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			if (node == null) {
				MsgBox.showWarning(EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "CostAccount_Add_SelectNode"));
				return;
			} else {
				if ((SysContext.getSysContext().getCurrentFIUnit() != null && (!SysContext.getSysContext().getCurrentFIUnit().isIsOnlyUnion()))
						|| (SysContext.getSysContext().getCurrentCostUnit() != null && SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit())) {
					MsgBox.showWarning(EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "CostAccount_Add_OnlyDetail"));
					return;
				}
			}
			if (node.getUserObject() != null) {
				if (node.getUserObject() instanceof CurProjectInfo) {
					CurProjectInfo info = (CurProjectInfo) node.getUserObject();
					this.getUIContext().put("source", info);
				} else if (node.getUserObject() instanceof OrgStructureInfo) {
					OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
					if (oui == null || oui.getUnit() == null)
						return;
					FullOrgUnitInfo info = oui.getUnit();
					this.getUIContext().put("source", info);
				}
			}
			this.getUIContext().put("upId", null);
		}
		//		判断是否被拆分引用
		if (this.getUIContext().get("upId") != null) {
			String id = this.getUIContext().get("upId").toString();
			if (ContractCostSplitEntryFactory.getRemoteInstance().exists("select id where costAccount.id = '" + id + "'")
					|| SettlementCostSplitEntryFactory.getRemoteInstance().exists("select id where costAccount.id = '" + id + "'")
					|| ConChangeSplitEntryFactory.getRemoteInstance().exists("select id where costAccount.id = '" + id + "'")
					|| PaymentSplitEntryFactory.getRemoteInstance().exists("select id where costAccount.id = '" + id + "'")) {
				if (!MsgBox.isYes(MsgBox.showConfirm2(this, EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "CostAccountSplit")))) {
					// 不继续
					return;
				}
			}
		}
		//判断是否被目标成本引用
		if (this.getUIContext().get("upId") != null) {
			String id = this.getUIContext().get("upId").toString();
			if (CostEntryFactory.getRemoteInstance().exists("select id where costAccount.id = '" + id + "'")) {
//			if(true){
				MsgBox.showWarning(this, "科目被目标成本引用，不能新增下级！");//候艳要求加此约束
				SysUtil.abort();
			}
		}
		super.actionAddNew_actionPerformed(e);
	}

    /**
     * 响应删除按钮
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {	
    	checkSelected();
    	
    	Set subNodesSet = new HashSet();
    	
    	if (confirmRemove()){
    		
    		if(costAccounts != null && !costAccounts.isEmpty()){
    			costAccounts.clear();
    		}
    		
    		if(beDeleteList != null && !beDeleteList.isEmpty()){
    			beDeleteList.clear();
    		}
    		
    		ICostAccount service = CostAccountFactory.getRemoteInstance();
    		//如果要删除明细科目(满足条件)
    		//删除非明细科目(	明细子节点都满足删除条件时，删除父节点的科目将直接删除下面的子节点科目。如果有不满足条件的子节点科目时，给出提示信息)
        	IRow row = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex());
        	
        	boolean isRoot = false ;
        	if("1".equals(row.getCell("level").getValue().toString())){
        		isRoot = true ;
        	}
        	
        	StringBuffer refSQL = new StringBuffer();
    		
        	refSQL.append(" select a.fid billId from T_FDC_CostAccount a " );
    		refSQL.append(" inner join T_CON_ContractCostSplitEntry b " );
    		refSQL.append(" on a.fid = b.fcostaccountid " );
    		refSQL.append(" union " );
    		
    		refSQL.append(" select distinct a.fid billId from T_FDC_CostAccount a " );
    		refSQL.append(" inner join T_CON_ConChangeSplitEntry c " );
    		refSQL.append(" on a.fid = c.fcostaccountid " );
    		refSQL.append(" union " );
    		
    		refSQL.append(" select distinct a.fid billId from T_FDC_CostAccount a " );
    		refSQL.append(" inner join T_AIM_MeasureEntry d " );
    		refSQL.append(" on a.fid = d.fcostaccountid " );
    		refSQL.append(" union " );
    		
    		refSQL.append(" select t0.fid from t_fdc_costaccount t0  " );
    		refSQL.append(" inner join  T_AIM_DynCostCtrlEntryItems  t1  " );
    		refSQL.append(" on t0.fid = t1.fcostaccountid " );
    		refSQL.append(" union " );
    		
    		refSQL.append(" select t0.fid from t_fdc_costaccount t0  " );
    		refSQL.append(" inner join  T_AIM_AimCostCtrlCostActItems  t1  " );
    		refSQL.append(" on t0.fid = t1.fcostaccountid " );
    		refSQL.append(" union " );
        	
    		refSQL.append(" select distinct ca.fid billId from t_fdc_costaccount ca ");
			refSQL.append(" inner join T_FDC_CostAccountWithAccount t2 ");
			refSQL.append(" on ca.fid = t2.fcostaccountid");
    		
    		//还要判断是否被引用了
    		IRowSet rowSetList = getRowSetByPureSQL(refSQL.toString());
    		
    		while(rowSetList.next()){
    			costAccounts.add(rowSetList.getString("billId"));
    			
    		}
    		
    		FilterInfo filter = new FilterInfo();
    		//判断它不是叶子结点(即不是明细科目)
    		boolean isLeaf = ((Boolean)row.getCell("isLeaf").getValue()).booleanValue();
    		Set beSetToLeafNode = new HashSet();

    		if(isLeaf){
    			
    			if(checkedValidatedData(row)){
    				beDeleteList.add(row.getCell("id").getValue().toString());
    				filter.getFilterItems().add(new FilterItemInfo("id",beDeleteList,CompareType.INCLUDE));
    				
    				IRow maxRowParent = null ;
    				if(isRoot){
        				maxRowParent = row;
        				
        			}else{
        				Set subSet = new HashSet();
        				maxRowParent = (IRow) nodes.get(row.getCell("parent.id").getValue().toString());
        				
        				if(maxRowParent != null){
        					getNextLevelSubNodes(maxRowParent,subSet);
        					if(subSet.size()==1){
        						beSetToLeafNode.add(maxRowParent.getCell("id").getValue().toString());
        					}
        				}
        			}
    				
    			}else{
    				//MsgBox.showWarning(this, "该成本科目不能被删除！");
    				return ;
    			}
    			
    		}else{
    			
    			//得取当前选中的节点所有最明细
    			getAllDetailsSubNodes(row, subNodesSet);
    			
    			Iterator iter = subNodesSet.iterator();
    			String rowId = row.getCell("id").getValue().toString();
    			
    			while(iter.hasNext()){
    				IRow rowElem = (IRow) iter.next();
    				String id = rowElem.getCell("id").getValue().toString();
    				
    				boolean isBeDelete =checkedValidatedData(rowElem); 
    				
    				if(isBeDelete){
    					beDeleteList.add(id);
    				}
    				getParentNodes(rowId,id,isBeDelete);
    				
    			}
    			
    			filter.getFilterItems().add(new FilterItemInfo("id",beDeleteList,CompareType.INCLUDE));
    			
    			IRow maxRowParent = null ;
    			
    			if(isRoot){
    				maxRowParent = row;
    				
    			}else{
    				if(beDeleteList != null && !beDeleteList.isEmpty()){
    					//
    					String maxParentSQL = 
    						"select flevel ,fid bId ,fisleaf isleaf from t_fdc_costaccount" +
    						" where fid in " + CostAccountHelper.SetConvertToString(beDeleteList) +
    						" order by flevel" ;  
    					
    					IRowSet updateIds = getRowSetByPureSQL(maxParentSQL);
    					
    					String maxNodeId = "" ;
    					
    					updateIds.beforeFirst();
    					while(updateIds.next()){
    						maxNodeId = updateIds.getString("bId");
    						IRow maxDelRow = (IRow) nodes.get(maxNodeId);
    						maxRowParent = (IRow) nodes.get(maxDelRow.getCell("parent.id").getValue().toString());

    						if(maxRowParent != null){
    							Set subSet = new HashSet();
    							
    							getNextLevelSubNodes(maxRowParent,subSet);
    							
    							for(Iterator iteror = subSet.iterator();iteror.hasNext();){
    								iteror.next();
    							}
    							
    							if(subSet.size() == 1){
    								beSetToLeafNode.add(maxRowParent.getCell("id").getValue().toString());
    							}
    						}
    					}
    					updateIds.beforeFirst();
    					
    				}
		    	}
    		}
			
    		ICostAccountFacade costActService = CostAccountFacadeFactory.getRemoteInstance();
    		
    		if(!beDeleteList.isEmpty()){
    			service.delete(filter);
    		}
    		
    		if(!isRoot){
	    		if(!beSetToLeafNode.isEmpty()){
	    			costActService.updateToLeafNode(beSetToLeafNode);
	    		}
    		}
    		
        	this.refreshList();
        }
    	
    }

	private IRowSet getRowSetByPureSQL(String sql) throws BOSException {
		ISQLExecutor exe = SQLExecutorFactory.getRemoteInstance(sql);
		
		IRowSet rowSetList = exe.executeSQL();
		return rowSetList;
	}
    
    private boolean checkedValidatedData(IRow row){
    	
		boolean isEnable = ((Boolean)row.getCell("isEnabled").getValue()).booleanValue();
		boolean assigned = ((Boolean)row.getCell("assigned").getValue()).booleanValue();
		
		if(isEnable){
			MsgBox.showWarning(this, "此科目为已启用的成本科目，不能删除，只有先禁用此科目后，才能进行删除操作！");
		}else if(assigned){
			MsgBox.showWarning(this, "此科目为引用的成本科目，不能删除！");
		}
		
		boolean isSource= false;
//		boolean isSource=((Boolean)row.getCell("isSource").getValue()).booleanValue();
    	
		String rowId = row.getCell("id").getValue().toString();
    	if(!isEnable && !assigned && !isSource && !costAccounts.contains(rowId)){
    		return true ;
    	}
		return false  ;
    }

	/**
	 * 对新增，准备UI参数的时候，需要传入父结点参数
	 */
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
	}

	/**
	 * 对新增，准备UI参数的时候，需要传入父结点参数
	 */
	protected void prepareUIContext1(UIContext uiContext, ActionEvent e) {
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		// this.checkSelected();
		checkSelected();
		int selectRow=getMainTable().getSelectManager().getActiveRowIndex();
		super.actionEdit_actionPerformed(e);
		getMainTable().getSelectManager().select(selectRow, 0);
		getMainTable().scrollToVisible(selectRow, 0);
	}

	/**
	 * output actionView_actionPerformed
	 */
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		super.actionView_actionPerformed(e);
	}

	protected void refresh(ActionEvent e) throws Exception {
		//		super.refresh(e);
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		CacheServiceFactory.getInstance().discardQuery(this.mainQueryPK);
		execQuery();
		
		this.appendFunToCostAccount();
	}

	/**
	 * output actionQuery_actionPerformed
	 */
	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		/**
		 * 解决manQuery缓存问题
		 */
		this.mainQuery = new EntityViewInfo();
		mainQuery.setFilter(getDefaultFilterForQuery());
		super.actionQuery_actionPerformed(e);
		this.appendFunToCostAccount();
	}

	protected String getQueryFieldName() {
		return null;
	}

	protected String getGroupEditUIName() {
		return null;
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return CostAccountEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return CostAccountFactory.getRemoteInstance();
	}

	private class CostAccountTreeRender implements IBasicRender {

		private int TABSIZE = 8;

		private int ICONSIZE = 10;

		private int margin = 2;

		protected SimpleTextRender simpleRender = new SimpleTextRender();

		protected HashMap costAccountIdToPos = new HashMap();

		protected void drawExpanded(Graphics g, int x, int y) {
			g.drawRect(x, y, ICONSIZE, ICONSIZE);
			int lineSize = ICONSIZE - 2 * margin;
			g.drawLine(x + margin, y + ICONSIZE / 2, x + margin + lineSize, y + ICONSIZE / 2);
		}

		protected void drawCollapsed(Graphics g, int x, int y) {
			g.drawRect(x, y, ICONSIZE, ICONSIZE);
			int lineSize = ICONSIZE - 2 * margin;
			g.drawLine(x + margin, y + ICONSIZE / 2, x + margin + lineSize, y + ICONSIZE / 2);
			g.drawLine(x + ICONSIZE / 2, y + margin, x + ICONSIZE / 2, y + margin + lineSize);
		}
		
		protected void drawLeaf(Graphics g, int x, int y) {
			int lineSize = ICONSIZE - 2 * margin;

		}

		public boolean inRect(NumberExpandInfo numberExpandInfo, int x, int y) {
			String costAccountId = numberExpandInfo.getcostAccountId();
			java.awt.Rectangle rec = (java.awt.Rectangle) costAccountIdToPos.get(costAccountId);
			if (rec != null) {
				return rec.getX() < x && (rec.getX() + rec.getWidth()) > x;
			}
			return false;
		}

		public void draw(Graphics graphics, Shape clip, Object obj, com.kingdee.bos.ctrl.kdf.util.style.Style style) {
			if (obj instanceof NumberExpandInfo) {
				NumberExpandInfo numberExpandInfo = (NumberExpandInfo) obj;
				int ident = numberExpandInfo.getLevel() * TABSIZE;
				java.awt.Rectangle rect = clip.getBounds();
				int x = rect.x + ident;
				int y = rect.y + (rect.height - ICONSIZE) / 2;
				Rectangle iconRect = new Rectangle(x, y, ICONSIZE, ICONSIZE);
				costAccountIdToPos.put(numberExpandInfo.getcostAccountId(), iconRect);

				simpleRender.draw(graphics, (Shape) new Rectangle(x + ICONSIZE + TABSIZE, rect.y, rect.width - x - ICONSIZE - TABSIZE, rect.height), numberExpandInfo.getNumber(), style);
				paintIcon(graphics, numberExpandInfo, iconRect);
				
			} else if (obj != null) {
				simpleRender.draw(graphics, clip, obj.toString(), style);
				
			}
		}

		private void paintIcon(Graphics graphics, NumberExpandInfo numberExpandInfo, java.awt.Rectangle iconRect) {
			if (numberExpandInfo.isLeaf()) {
				drawLeaf(graphics, iconRect.x, iconRect.y);
			} else if (numberExpandInfo.isExpandStatus()) {
				drawExpanded(graphics, iconRect.x, iconRect.y);
			} else if (!numberExpandInfo.isExpandStatus()) {
				drawCollapsed(graphics, iconRect.x, iconRect.y);
			}
		}
	}

	private class NumberExpandInfo {
		private String costAccountId;

		private String number;

		private boolean isExpandStatus;

		// private String signNumber;
		private int level;

		private boolean isLeaf;

		/**
		 * @return 返回 isExpandStatus。
		 */
		public boolean isExpandStatus() {
			return isExpandStatus;
		}

		/**
		 * @param isExpandStatus
		 *            要设置的 isExpandStatus。
		 */
		public void setExpandStatus(boolean isExpandStatus) {
			this.isExpandStatus = isExpandStatus;
		}

		/**
		 * @return 返回 number。
		 */
		public String getNumber() {
			return number;
		}

		/**
		 * @param number
		 *            要设置的 number。
		 */
		public void setNumber(String number) {
			this.number = number;
		}

		public String toString() {
			return number;
		}

		public int getLevel() {
			return level;
		}

		public void setLevel(int level) {
			this.level = level;
		}

		public boolean isLeaf() {
			return isLeaf;
		}

		public void setLeaf(boolean isLeaf) {
			this.isLeaf = isLeaf;
		}

		public String getcostAccountId() {
			return costAccountId;
		}

		public void setcostAccountId(String costAccountId) {
			this.costAccountId = costAccountId;
		}
	}

	protected IObjectPK getSelectedTreeKeyValue() {
		return null;
	}

	// 是否需要进行表格排序。因为实现了分级显示，所以不能排序
	protected boolean isOrderForClickTableHead() {
		return false;
	}

	/**
	 * 
	 * 描述：覆盖超类行为，右键菜单没有新增、删除
	 */
	protected void initPopmenu() {
		/* 增加显示右键菜单的代码 */
		// 2004-9-10 by Jerry
		final JPopupMenu menu = treeMain.getPopupMenu();
		Object[] ls = treeMain.getListeners(TreePopupMenuListener.class);

		if (ls == null || ls.length == 0) {
			treeMain.addTreePopupMenu(new TreePopupMenuListener() {
				public boolean popMenu(TreePopupMenuEvent event) {
					return true;
				}
			});
			menu.add(new KDSeparator());
			JMenuItem itemEdit = new JMenuItem(actionGroupEdit);
			itemEdit.setIcon(EASResource.getIcon("imgTbtn_edit"));
			menu.add(itemEdit);
		}
	}

	/**
	 * 
	 * 描述：设置行的显示格式，被tblMain_tableClicked调用
	 * @throws BOSException 
	 * 
	 */
	private void setTreeDisplayStyle(IRow row) throws BOSException {
		boolean isCostAccountLeaf = ((Boolean) row.getCell("isLeaf").getValue()).booleanValue();
		if (isCostAccountLeaf) {
			return;
		} else {
			expandCostAccount(row);
		}
	}

	/**
	 * 
	 * 描述：用于非叶子结点时，显示下级 1.设置本行信息 2.设置下级信息
	 * @throws BOSException 
	 */
	private void expandCostAccount(IRow row) throws BOSException {
		NumberExpandInfo costAccountNumberExpandInfo = (NumberExpandInfo) row.getCell("number").getValue();
		String costAccountNumber = costAccountNumberExpandInfo.getNumber();
		boolean isExpandStatus = costAccountNumberExpandInfo.isExpandStatus();
		String costAccountId = (String) row.getCell("id").getValue();
		if (isExpandStatus) {
			costAccountNumberExpandInfo.setExpandStatus(false);

			int i = row.getRowIndex() + 1;
			tblMain.setRefresh(false);
			IRow child;
			for (int count = tblMain.getRowCount(); i < count; i++) {
				child = tblMain.getRow(i);
				if (child.getCell("number").getValue() instanceof NumberExpandInfo) {
					NumberExpandInfo childNumberExpandInfo = (NumberExpandInfo) child.getCell("number").getValue();
					String childNumber = childNumberExpandInfo.getNumber();
					if (childNumber.startsWith(costAccountNumber)) {
						child.getStyleAttributes().setHided(true);
					} else {
						break;
					}
				}
			}
			tblMain.setRefresh(true);
			tblMain.reLayoutAndPaint();
		} else {
			costAccountNumberExpandInfo.setExpandStatus(true);
			int i = row.getRowIndex() + 1;
			boolean hasChildrenDataGotten = false;
			String curProjectId, fullOrgUnitId;

			if (i < tblMain.getRowCount()) {
				IRow next = tblMain.getRow(i);
				if (next.getCell("number").getValue() instanceof NumberExpandInfo) {
					NumberExpandInfo nextNumberExpandInfo = (NumberExpandInfo) next.getCell("number").getValue();
					if (nextNumberExpandInfo.getNumber().startsWith(costAccountNumber)) {
						hasChildrenDataGotten = true;
					}
				}
			} else {
				hasChildrenDataGotten = true;
			}
			if (!hasChildrenDataGotten) {
				this.setCursorOfWair();
				EntityViewInfo childQuery = new EntityViewInfo();
				FilterInfo childFilter = new FilterInfo();
				childQuery.setFilter(childFilter);
				childFilter.getFilterItems().add(new FilterItemInfo("parent.id", costAccountId));
				if (row.getCell("curProject.id").getValue() != null) {
					curProjectId = row.getCell("curProject.id").getValue().toString();
					childFilter.getFilterItems().add(new FilterItemInfo("curProject.id", curProjectId));
				} else {
					fullOrgUnitId = row.getCell("fullOrgUnit.id").getValue().toString();
					childFilter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", fullOrgUnitId));
				}
				// childFilter.setMaskString("#0" AND "#1");
				IRowSet rowset = null;
				try {
					rowset = getQueryExecutor(this.mainQueryPK, childQuery).executeQuery();
					if (rowset != null && rowset.size() > 0) {
						IRow child;
						int start = row.getRowIndex() + 1;
						while (rowset.next()) {
							child = tblMain.addRow(start);
							start++;
							for (int col = 0, colcount = tblMain.getColumnCount(); col < colcount; col++) {
								IColumn column = tblMain.getColumn(col);
								String field = column.getFieldName();
								if (field == null || field.length() < 1) {
									continue;
								}
								Object value = rowset.getObject(field);
								if (field.equals("number")) {
									NumberExpandInfo childNumberExpandInfo = new NumberExpandInfo();
									childNumberExpandInfo.setcostAccountId(rowset.getString("id"));
									childNumberExpandInfo.setLevel(rowset.getInt("level"));
									childNumberExpandInfo.setLeaf(rowset.getBoolean("isLeaf"));
									childNumberExpandInfo.setNumber(rowset.getString("number"));
									childNumberExpandInfo.setExpandStatus(false);
									value = childNumberExpandInfo;
								}
								child.getCell(col).setValue(value);
							}
						}
						tblMain.setRowCount(tblMain.getRowCount() + rowset.size());
					}
				} catch (Exception e) {
					handUIExceptionAndAbort(e);
				} finally {
					this.setCursorOfDefault();
				}
			}

			IRow child;
			for (int count = tblMain.getRowCount(); i < count; i++) {
				child = tblMain.getRow(i);
				if (child.getCell("number").getValue() instanceof NumberExpandInfo) {
					NumberExpandInfo childNumberExpandInfo = (NumberExpandInfo) child.getCell("number").getValue();
					String childNumber = childNumberExpandInfo.getNumber();

					if (childNumber.startsWith(costAccountNumber)) {
						String parentId = (String) child.getCell("parent.id").getValue();
						if (parentId != null && parentId.equals(costAccountId)) {
							// 当下级结点还有下级时，分级显示下级
							expandChildCostAccount(child);
						} else {
							continue;
						}
					} else {
						break;
					}
				}
			}

		}
		this.appendFunToCostAccount();		
	}

	/**
	 * 
	 * 描述：递归方法，用于下级结点还有下级时，分级显示
	 * 
	 */
	private void expandChildCostAccount(IRow row) {
		NumberExpandInfo costAccountNumberExpandInfo = (NumberExpandInfo) row.getCell("number").getValue();
		String costAccountNumber = costAccountNumberExpandInfo.getNumber();
		boolean isExpandStatus = costAccountNumberExpandInfo.isExpandStatus();
		String costAccountId = (String) row.getCell("id").getValue();
		row.getStyleAttributes().setHided(false);
		if (isExpandStatus) {
			int i = row.getRowIndex() + 1;
			IRow child;
			for (int count = tblMain.getRowCount(); i < count; i++) {
				child = tblMain.getRow(i);
				NumberExpandInfo childNumberExpandInfo = (NumberExpandInfo) child.getCell("number").getValue();
				String childNumber = childNumberExpandInfo.getNumber();

				if (childNumber.startsWith(costAccountNumber)) {
					String parentId = (String) child.getCell("parent.id").getValue();
					if (parentId != null && parentId.equals(costAccountId)) {
						expandChildCostAccount(child);
					} else {
						continue;
					}
				} else {
					break;
				}
			}
		}
	}

	/**
	 * 
	 * 描述：添加tblMain的监听器
	 * 
	 */
	protected void initListener() {
		super.initListener();
		tblMain.addKDTDataFillListener(new KDTDataFillListener() {
			public void afterDataFill(KDTDataRequestEvent e) {
				int start = e.getFirstRow();
				int end = e.getLastRow();
				setTreeDisplayStyle(start, end + 1);
			}
		});
	}

	/**
	 * 
	 * 描述：tblMain显示树状格式
	 * 
	 */
	private void setTreeDisplayStyle(int start, int end) {
		IRow row;
		for (int i = start, count = end; i < count; i++) {
			row = tblMain.getRow(i);
			String id = (String) row.getCell("id").getValue();
			int level = ((Integer) row.getCell("level").getValue()).intValue();
			Object numberValue = row.getCell("number").getValue();
			if (!(numberValue instanceof String)) {
				return;
			}
			String costAccountNumber = (String) numberValue;
			boolean isCostAccountLeaf = ((Boolean) row.getCell("isLeaf").getValue()).booleanValue();
			NumberExpandInfo costAccountNumberExpandInfo = new NumberExpandInfo();
			costAccountNumberExpandInfo.setcostAccountId(id);
			costAccountNumberExpandInfo.setLevel(level);
			costAccountNumberExpandInfo.setLeaf(isCostAccountLeaf);
			costAccountNumberExpandInfo.setNumber(costAccountNumber);
			costAccountNumberExpandInfo.setExpandStatus(true);
			row.getCell("number").setValue(costAccountNumberExpandInfo);
			if (!isSetTreeDispalyStyle) {
				if (level == 1) {
					row.getStyleAttributes().setHided(false);
					costAccountNumberExpandInfo.setExpandStatus(false);
				} else {
					row.getStyleAttributes().setHided(true);
				}
			} else
				costAccountNumberExpandInfo.setExpandStatus(true);
		}
	}

	/**
	 * 启用
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception {
		// super.actionCancelCancel_actionPerformed(e);

		checkSelected();
		int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(activeRowIndex);
		// if (row == null)
		// return;
		String id = row.getCell("id").getValue().toString().trim();
		ICostAccount iCostAccount = CostAccountFactory.getRemoteInstance();
		IObjectPK pk = new ObjectStringPK(id);
		
		CostAccountInfo costAccountInfo = iCostAccount.getCostAccountInfo(pk);
		if (costAccountInfo.getSrcCostAccountId() != null) {
			CostAccountInfo accountInfo = CostAccountFactory.getRemoteInstance().getCostAccountInfo(
					"select isenabled where id='" + costAccountInfo.getSrcCostAccountId() + "'");

			/*
			 * if (!accountInfo.isIsEnabled() && !costAccountInfo.isIsEnabled())
			 * { MsgBox.showWarning(this, "上级分配下来的已禁用的科目，下级不能启用");
			 * SysUtil.abort(); }
			 */
		}
		if (iCostAccount.enable(pk)) {
			setMessageText(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK"));
			showMessage();
			//不进行查询,直接在界面进行更新即可
			//tblMain.getRow(activeRowIndex).getCell("isEnabled").setValue(Boolean.TRUE);
			//tblMain_tableSelectChanged(null);
			tblMain.removeRows();// 触发新查询
//			this.btnCancel.setEnabled(false);
//			this.menuItemCancel.setEnabled(false);
//			this.btnCancelCancel.setEnabled(true);
//			this.menuItemCancelCancel.setEnabled(true);
			
			tblMain.getSelectManager().select(activeRowIndex, 0);
			tblMain.scrollToVisible(activeRowIndex, 0);
		}
		
		this.appendFunToCostAccount();
	}

	/**
	 * 禁用
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		// super.actionCancel_actionPerformed(e);
		checkSelected();
		int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(activeRowIndex);
		if (row == null)
			return;
		String id = row.getCell("id").getValue().toString().trim();
		ICostAccount iCostAccount = CostAccountFactory.getRemoteInstance();
		IObjectPK pk = new ObjectStringPK(id);
		if (iCostAccount.disable(pk)) {
			setMessageText(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK"));
			showMessage();
			//不进行查询,直接在界面进行更新即可
			//tblMain.getRow(activeRowIndex).getCell("isEnabled").setValue(Boolean.FALSE);
			//tblMain_tableSelectChanged(null);
			
			tblMain.removeRows();// 触发新查询
//			
//			this.btnCancelCancel.setEnabled(false);
//			this.menuItemCancelCancel.setEnabled(false);
//			this.btnCancel.setEnabled(true);
//			this.menuItemCancel.setEnabled(true);
			tblMain.getSelectManager().select(activeRowIndex, 0);
			tblMain.scrollToVisible(activeRowIndex, 0);
		}
		this.appendFunToCostAccount();
		
	}

	/**
	 * 分配
	 */
	public void actionAssign_actionPerformed(ActionEvent e) throws Exception {

		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		
		Map uiContext = this.getUIContext();
		
		uiContext.put("orgTree", this.treeMain);
		
		IUIWindow assignUI = UIFactory.createUIFactory().create(
								CostAccountNewAssignUI.class.getName(), uiContext);
		assignUI.show();
		
//		if (node == null) {// 应该提示"请选中欲分配的节点"
//			MsgBox.showWarning(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "CostAccount_Assign_Selected"));
//			return;
//		}
		this.actionRefresh_actionPerformed(e);
	}

	/**
	 * 原来的分配功能实现 现在暂时关闭
	 * vincen_zhu close
	 * @param node
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void setCostAccountAssigedState(DefaultKingdeeTreeNode node) throws BOSException, EASBizException {
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			
//			FilterInfo filter = new FilterInfo();
//			filter.getFilterItems().add(new FilterItemInfo("curProject.id", projectInfo.getId().toString()));
//			filter.getFilterItems().add(new FilterItemInfo("assigned", Boolean.TRUE));
//			boolean ass = CostAccountFactory.getRemoteInstance().exists(filter);
//			if(ass) {
				if (!MsgBox.isYes(MsgBox.showConfirm2(this, "要分配的科目的上级科目如果已经有数据，则无法分配，这样会导致该科目上级汇总数不正确，是否继续？"))) {
					// 提示未启用的成本科目将不会分配下去
					return;
				}
//			}
			
			ICostAccountFacade iCostAccountFacade = CostAccountFacadeFactory.getRemoteInstance();
			List errorList = iCostAccountFacade.assignProjsCostAccount(new ObjectStringPK(projectInfo.getId().toString()));
			
			showErrorInfo(errorList);
			tblMain.removeRows();// 触发新查询
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
			if (oui == null || oui.getUnit() == null)
				return;
			FullOrgUnitInfo info = oui.getUnit();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", info.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("assigned", Boolean.TRUE));

			// 组织之间的分配
			if ((node.getChildCount() > 0) && (((DefaultKingdeeTreeNode) node.getChildAt(0)).getUserObject() instanceof OrgStructureInfo)) {
				ICostAccountFacade iCostAccountFacade = CostAccountFacadeFactory.getRemoteInstance();
				iCostAccountFacade.assignOrgsCostAccount(new ObjectStringPK(info.getId().toString()));
				tblMain.removeRows();// 触发新查询
			} else {
				// 若选择结点为组织与工程项目临界点,则为组织与工程项目之间的分配
				ICostAccountFacade iCostAccountFacade = CostAccountFacadeFactory.getRemoteInstance();
				List errorlist = iCostAccountFacade.assignOrgProject(new ObjectStringPK(info.getId().toString()));
				showErrorInfo(errorlist);
				tblMain.removeRows();// 触发新查询
			}
		}
	}

	private void showErrorInfo(List errorlist) {
		if(errorlist == null || errorlist.size() == 0) return;
		StringBuffer errorStr = new StringBuffer();
		String sep = "\n";
		for (Iterator iter = errorlist.iterator(); iter.hasNext();) {
			String element = (String) iter.next();
			errorStr.append(element);
			errorStr.append(sep);
		}
		String title = "部分科目由于其上级已经有数据，未分配成功，其余已经分配成功！未分配成功的科目请查看详细信息";
		
		String error = errorStr.toString();
		error = error.replace('!', '.');
		MsgBox.showDetailAndOK(this, title, error, 1);
	}

	// /**
	// * 反分配
	// */
	// public void actionDisAssign_actionPerformed(ActionEvent e) throws
	// Exception
	// {
	// DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)
	// treeMain.getLastSelectedPathComponent();
	// if (node == null) {//应该提示"请选中欲反分配的节点"
	// return;
	// }
	// if (node.getUserObject() instanceof CurProjectInfo) {
	// CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
	// ICostAccountFacade iCostAccountFacade =
	// CostAccountFacadeFactory.getRemoteInstance();
	// iCostAccountFacade.disAssignProjsCostAccount(new
	// ObjectStringPK(projectInfo.getId().toString()));
	// tblMain.removeRows();// 触发新查询
	// } else if (node.getUserObject() instanceof OrgStructureInfo) {
	// //组织之间的反分配
	// if
	// ((node.getChildCount()>0)&&(((DefaultKingdeeTreeNode)node.getChildAt(0)).getUserObject()
	// instanceof OrgStructureInfo)) {
	// OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
	// if (oui == null || oui.getUnit() == null)
	// return;
	// FullOrgUnitInfo info = oui.getUnit();
	// ICostAccountFacade iCostAccountFacade =
	// CostAccountFacadeFactory.getRemoteInstance();
	// iCostAccountFacade.disAssignOrgsCostAccount(new
	// ObjectStringPK(info.getId().toString()));
	// tblMain.removeRows();// 触发新查询
	// }else{
	// //若选择结点为组织与工程项目临界点,则为组织与工程项目之间的反分配
	// OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
	// if (oui == null || oui.getUnit() == null)
	// return;
	// FullOrgUnitInfo info = oui.getUnit();
	// ICostAccountFacade iCostAccountFacade =
	// CostAccountFacadeFactory.getRemoteInstance();
	// iCostAccountFacade.disAssignOrgProject(new
	// ObjectStringPK(info.getId().toString()));
	// tblMain.removeRows();// 触发新查询
	// }
	// }
	// }
	/**
	 * 反分配
	 */
	public void actionDisAssign_actionPerformed(ActionEvent e) throws Exception {
//		MsgBox.showWarning(this, "");
		if (this.tblMain.getSelectManager().getActiveRowIndex() == -1) {
			// 请先指定项
			MsgBox.showWarning(EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "CostAccount_DisAssign_CheckSelected"));
			return;
		}
		CostAccountCollection cacSelect = new CostAccountCollection();
		CostAccountInfo caiSelect;
		KDTSelectBlock selectBlock = null;
		ICell cell = null;
		FullOrgUnitInfo foui;
		CurProjectInfo cpi;
		for (int i = 0; i < tblMain.getSelectManager().size(); i++) {
			caiSelect = new CostAccountInfo();
			selectBlock = tblMain.getSelectManager().get(i);
			for (int j = selectBlock.getTop(); j <= selectBlock.getBottom(); j++) {
				IRow row = this.tblMain.getRow(j);
				// this.verifyNotAccepted(row);//验证是否为已分配项
				cell = row.getCell(getKeyFieldName());
				if (cell == null) {
					MsgBox.showError(EASResource.getString(FrameWorkClientUtils.strResource + "Error_KeyField_Fail"));
					SysUtil.abort();
				}
				Object keyValue = cell.getValue();

				BOSUuid id = BOSUuid.read((String) keyValue);
				caiSelect.setId(id);
				caiSelect.setLongNumber(row.getCell("longNumber").getValue().toString());
				if (row.getCell("fullOrgUnit.id").getValue() != null) {
					foui = new FullOrgUnitInfo();
					foui.setId(BOSUuid.read(row.getCell("fullOrgUnit.id").getValue().toString()));
					foui.setLongNumber(row.getCell("fullOrgUnit.longNumber").getValue().toString());
					caiSelect.setFullOrgUnit(foui);
				} else if (row.getCell("curProject.id").getValue() != null) {
					cpi = new CurProjectInfo();
					cpi.setId(BOSUuid.read(row.getCell("curProject.id").getValue().toString()));
					cpi.setLongNumber(row.getCell("curProject.longNumber").getValue().toString());
					caiSelect.setCurProject(cpi);
				}
				if (((Boolean) (row.getCell("isSource").getValue())).booleanValue()) {
					caiSelect.setIsSource(true);
				} else {
					caiSelect.setIsSource(false);
					if (row.getCell("srcCostAccountId").getValue() != null) {
						caiSelect.setSrcCostAccountId(row.getCell("srcCostAccountId").getValue().toString());
					}
				}
				caiSelect.setName(row.getCell("name").getValue().toString(), SysContext.getSysContext().getLocale());
				cacSelect.add(caiSelect);
			}
		}

		ICostAccountFacade iCostAccountFacade = CostAccountFacadeFactory.getRemoteInstance();

		ArrayList al = iCostAccountFacade.disAssignSelectOrgProject(cacSelect);
		if (al.size() != 0) {
			ArrayList alOrgAndProject = new ArrayList();
			for (int i = 0; i < al.size(); i++) {
				alOrgAndProject.addAll(i, (ArrayList) al.get(i));
			}
			MsgBox.showDetailAndOK(this, "详细信息列出了无法反分配的明细情况!", alOrgAndProject.toString(), 1);

		}
		actionRefresh_actionPerformed(e);
	}

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		super.actionRefresh_actionPerformed(e);
		
		this.appendFunToCostAccount();
	}
	
	
	/**
	 * 模板导入
	 */
	public void actionTemplateImport_actionPerformed(ActionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		boolean checkGroupNodeSelected = true;
		if (node != null) {
			// 提示选中节点
			if (node.getUserObject() != null) {
				if (node.getUserObject() instanceof CurProjectInfo) {
					checkGroupNodeSelected = false;
				} else if (node.getUserObject() instanceof OrgStructureInfo) {
					OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
					if (oui == null || oui.getUnit() == null) {
						checkGroupNodeSelected = false;
					} else {
						FullOrgUnitInfo info = oui.getUnit();
						if (!OrgConstants.DEF_CU_ID.equals(info.getId().toString())) {
							checkGroupNodeSelected = false;
						}
					}
				}
			} else {
				checkGroupNodeSelected = false;
			}
		} else {
			checkGroupNodeSelected = false;
		}
		if (!checkGroupNodeSelected) {
			MsgBox.showWarning(EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "CostAccount_TemplateImport_CheckNodeSelected"));
			return;
		}
		HashMap map = new HashMap();
		map.put("Owner", this); // 必须。被启动UI的父UI对象
		UIContext uiContext = new UIContext(this);
		// 供子类定义要传递到EditUI中扩展的属性

		prepareUIContext(uiContext, e);
		IUIFactory uiFactory = null;
		uiFactory = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory"); // 以模态对话框方式启动
		IUIWindow uiWindow = uiFactory.create("com.kingdee.eas.fdc.basedata.client.CostAccountTemplateDataImportUI", /* 被启动对象的类名称 */
		uiContext, null, OprtState.EDIT);
		uiWindow.show();
		actionRefresh_actionPerformed(e);

	}

	/**
	 * 成本科目引入
	 */
	public void actionImport_actionPerformed(ActionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			// 提示选中节点
			// MsgBox.showWarning("请指定需要引入的节点!");//ProjectImport_CheckNodeSelected
			MsgBox.showWarning(EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "ProjectImport_CheckNodeSelected"));
			return;
		}
		
		
		// if (OrgViewUtils.isTreeNodeDisable(node)) {
		// return;
		// }
		HashMap map = new HashMap();
		map.put("Owner", this); // 必须。被启动UI的父UI对象
		UIContext uiContext = new UIContext(this);
		// 供子类定义要传递到EditUI中扩展的属性
		if (node.getUserObject() != null) {
			if (node.getUserObject() instanceof CurProjectInfo) {
				if (!node.isLeaf()) {
					project = null;
					MsgBox.showWarning(this, ContractClientUtils
							.getRes("selectProjLeafPls"));
					SysUtil.abort();
				}
				CurProjectInfo info = (CurProjectInfo) node.getUserObject();
				uiContext.put("address", info);
				uiContext.put("isOrgFilter", Boolean.valueOf(true));
			} else if (node.getUserObject() instanceof OrgStructureInfo) {
				// if
				// ((node.getChildCount()>0)&&(((DefaultKingdeeTreeNode)node.getChildAt(0)).getUserObject()
				// instanceof OrgStructureInfo)) {// 选择结点不适合新增,灰掉新增按钮
				// return;
				// }
				// 需求规定不存在这种情况,提示只能对工程项目进行数据引入
				throw new FDCBasedataException(FDCBasedataException.SELECTLEAFPROJECT);
				// OrgStructureInfo oui = (OrgStructureInfo)
				// node.getUserObject();
				// if (oui == null || oui.getUnit() == null)
				// return;
				// FullOrgUnitInfo info = oui.getUnit();
				// uiContext.put("address", info);
			}
		} else {
			return;
		}
		prepareUIContext(uiContext, e);
		IUIFactory uiFactory = null;
		uiFactory = UIFactory.createUIFactory(UIModelDialogFactory.class.getName()); // 以模态对话框方式启动
		IUIWindow uiWindow = uiFactory.create(CostAccountImportUI.class.getName(), uiContext, null, OprtState.EDIT);
		uiWindow.show();
		actionRefresh_actionPerformed(e);
	}

	/**
	 * output actionProjectAttachment_actionPerformed method
	 */
	public void actionProjectAttachment_actionPerformed(ActionEvent e) throws Exception {
		// this.checkSelected();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		CurProjectInfo cpi = null;
		if (node == null) {
			return;
		}
		if (OrgViewUtils.isTreeNodeDisable(node)) {
			return;
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			cpi = (CurProjectInfo) node.getUserObject();
		} else {
			//			 请先指定项
			MsgBox.showWarning(EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "Project_CheckSelected"));
			// MsgBox.showWarning("请指定工程项目!");//Project_CheckSelected
			return;
		}
		//		if (this.tblMain.getSelectManager().getActiveRowIndex() == -1) {
		//			
		//		}
		super.actionProjectAttachment_actionPerformed(e);
		AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
		String contractId = this.getSelectedKeyValue();
		// ContractBillInfo contract = ContractBillFactory
		// .getRemoteInstance()
		// .getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId)));
		//		IRow row = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex());

		acm.showAttachmentListUIByBoID(cpi.getId().toString(), this);
	}
    protected String getEditUIModal()
    {
        // return UIFactoryName.MODEL;
        // return UIFactoryName.NEWWIN;
        // 2006-4-29 胡博要求加入根据配置项来读取UI打开方式。
        String openModel = UIConfigUtility.getOpenModel();
        if (openModel != null)
        {
            return openModel;
        }
        else
        {
            return UIFactoryName.MODEL;
        }
    }
    
    protected ArrayList getImportParam()
    {
    	DatataskParameter param = new DatataskParameter();
		String solutionName = "eas.fdc.fdcbasedata.fdccostaccount";
		param.solutionName = solutionName;
		param.alias = "房地产成本科目";
		ArrayList paramList = new ArrayList();
		paramList.add(param);
		return paramList;
    }
    public void actionEnterDB_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected();
    	
    	int index = getMainTable().getSelectManager().getActiveRowIndex();
    	CostAccountInfo acct=new CostAccountInfo();
    	Object obj = getMainTable().getCell(index, "isEnterDB").getValue();
    	Object objID = getMainTable().getCell(index, "id").getValue();
    	Object longNumber = getMainTable().getCell(index, "longNumber").getValue();
    	if(obj==null||obj.equals(Boolean.TRUE)||objID==null||longNumber==null){
    		return;
    	}
    	
    	acct.setId(BOSUuid.read(objID.toString()));
    	acct.setLongNumber(longNumber.toString().trim().replaceAll("\\.", "!"));
    	acct.setIsEnabled(false);
    	CostAccountFacadeFactory.getRemoteInstance().handleEnterDB(acct, true);
    	this.refreshList();
    }
    public void actionCancelEnterDB_actionPerformed(ActionEvent e)
    		throws Exception {
    	checkSelected();
    	int index = getMainTable().getSelectManager().getActiveRowIndex();
    	CostAccountInfo acct=new CostAccountInfo();
    	Object obj = getMainTable().getCell(index, "isEnterDB").getValue();
    	Object objID = getMainTable().getCell(index, "id").getValue();
    	Object longNumber = getMainTable().getCell(index, "longNumber").getValue();
    	if(obj==null||obj.equals(Boolean.FALSE)||objID==null||longNumber==null){
    		return;
    	}
    	
    	acct.setId(BOSUuid.read(objID.toString()));
    	acct.setLongNumber(longNumber.toString().trim().replaceAll("\\.", "!"));
    	acct.setIsEnterDB(true);
    	CostAccountFacadeFactory.getRemoteInstance().handleEnterDB(acct, false);
    	this.refreshList();
    }

	public void actionAssignToOrg_actionPerformed(ActionEvent e) throws Exception {
		//提示未启用的成本科目将不会分配下去
		if (!MsgBox.isYes(MsgBox.showConfirm2(this, EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "CostAccountAssign_Note")))) {
			return;
		}
		
		if (!MsgBox.isYes(MsgBox.showConfirm2(this, "要分配的科目的上级科目如果已经有数据，则无法分配(仅有数据的无法分配)，这样会导致该科目上级汇总数不正确，是否继续？"))) {
			return;
		}
	
		UIContext uiContext = new UIContext(this);
		IUIWindow assignUI = UIFactory.createUIFactory().create(CostAccountAssignUI.class.getName(), uiContext);
		assignUI.show();
		
		tblMain.removeRows();// 触发新查询
	}
	
	protected void chkIncludeChild_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
		setIsIncludeChild(this.chkIncludeChild.isSelected());
        if (this.treeMain.getRowCount() == 0)
        {
            return;
        }
        treeMain_valueChanged(null);
       
    }

	
	protected void tblMain_doRequestRowSet(final RequestRowSetEvent e){
		super.tblMain_doRequestRowSet(e);
	}
	
	/**
	 * 
	 * @param row
	 * @param sets
	 */
	private void getNextLevelSubNodes(IRow row ,Set sets ){
		NumberExpandInfo costAccountNumberExpandInfo = (NumberExpandInfo) row.getCell("number").getValue();
		String costAccountNumber = costAccountNumberExpandInfo.getNumber();
		String costAccountId = (String) row.getCell("id").getValue();
		
		int i = row.getRowIndex()+1;
		IRow child;
		for (int count = tblMain.getRowCount(); i < count; i++) {
			child = tblMain.getRow(i);
			
			NumberExpandInfo childNumberExpandInfo = (NumberExpandInfo) child.getCell("number").getValue();
			String childNumber = childNumberExpandInfo.getNumber();

			if (childNumber.lastIndexOf(".") != -1 && 
					childNumber.substring(0, childNumber.lastIndexOf(".")).equals(costAccountNumber)) {
				
				String parentId = (String) child.getCell("parent.id").getValue();
				if (parentId != null && parentId.equals(costAccountId)) {
					sets.add(child);
				} else {
					continue;
				}
			} else {
				//break;
			}
		}
	}
	
	/**
	 * 判断直接下级节点
	 * @param row
	 * @return
	 */
	private boolean checkNextLevelSubNodes(IRow row ){
		NumberExpandInfo costAccountNumberExpandInfo = (NumberExpandInfo) row.getCell("number").getValue();
		String costAccountNumber = costAccountNumberExpandInfo.getNumber();
		String costAccountId = (String) row.getCell("id").getValue();
		
		int i = row.getRowIndex()+1;
		IRow child;
		for (int count = tblMain.getRowCount(); i < count; i++) {
			child = tblMain.getRow(i);
			
			NumberExpandInfo childNumberExpandInfo = (NumberExpandInfo) child.getCell("number").getValue();
			String childNumber = childNumberExpandInfo.getNumber();

			if (childNumber.lastIndexOf(".") != -1 &&  
					childNumber.substring(0, childNumber.lastIndexOf(".")).equals(costAccountNumber)) {
				String parentId = (String) child.getCell("parent.id").getValue();
				if (parentId != null && parentId.equals(costAccountId)) {
					if(!checkedValidatedData(child)){
						return false;  
					}
				} else {
					continue;
				}
			} else {
				break;
			}
		}
		return true ;
	}
	/**
	 * 可拆拆分科目保存
	 * 
	 * @author beyondsoft 向晓帆
	 */
	private void saveAccountIsSplit() throws Exception {
		// 可拆分集合
		List splitId = new ArrayList();
		// 不可拆分集合
		List unSplitId = new ArrayList();
		// 得到所有的行
		int rowCount = tblMain.getRowCount();
		// 遍历所有行
		for (int i = 0; i < rowCount; i++) {
			// 得到一行并且得到是否可拆分
			IRow row = tblMain.getRow(i);
			String isSplit = row.getCell("isSplit").getValue().toString();
			// 获得不可拆分的科目ID
			if (isSplit.equals("false")) {
				String id = row.getCell("id").getValue().toString();
				// 将不可拆分科目ID放入集合
				unSplitId.add(id);
			} else {// 获得可拆分的科目ID
				String id = row.getCell("id").getValue().toString();
				// 将可拆分科目ID放入集合
				splitId.add(id);
			}
		}
		// 检查可拆分字段引用
		checkIsSplitRef(unSplitId);
		
		// 修改不可拆分的科目
		try {
			CostAccountFactory.getRemoteInstance().saveIsSplitCostAccount(unSplitId, false);
		// 修改可拆分的科目
			CostAccountFactory.getRemoteInstance().saveIsSplitCostAccount(splitId, true);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
	}

	private boolean checkIsSplitRef(List unSplitId) throws Exception {
		if (unSplitId.size() == 0)
			return false;
		FDCSQLBuilder builder = new FDCSQLBuilder();
		// 拆分,含跨项目分期
		builder.appendSql("select distinct entry.fcostaccountid from T_AIM_CostSplitEntry entry ");
		builder.appendSql("inner join T_AIM_CostSplit head on head.fid=entry.fparentid ");
		builder.appendSql("where ");
		builder.appendParam("entry.fcostaccountid", unSplitId.toArray());
		
		IRowSet rs = builder.executeQuery();
		Map acctMap = new HashMap();
		while (rs.next()) {
			acctMap.put(rs.getString("fcostaccountid"), Boolean.TRUE);
		}
		if (acctMap.size() == 0) {
			return false;
		}
		
		Map difMap = FDCHelper.getDifMap(acctMap, acctMap);
		Map errorDetailMap = new HashMap();
		if (difMap.size() > 0) {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", new HashSet(acctMap.keySet()), CompareType.INCLUDE));
			view.setFilter(filter);
			view.getSelector().add("longNumber");
			view.getSelector().add("name");
			CostAccountCollection accts = CostAccountFactory.getRemoteInstance().getCostAccountCollection(view);
			Object[][] data = new Object[accts.size()][2];
			errorDetailMap.put("head", new String[] { "科目编码", "科目名称" });
			errorDetailMap.put("data", data);
			errorDetailMap.put("format", new Object[] { "", "no", "" });
			int i = 0;
			for (Iterator iter = accts.iterator(); iter.hasNext(); i++) {
				CostAccountInfo info = (CostAccountInfo) iter.next();
				data[i][0] = info.getLongNumber().replace('!', '.');
				data[i][1] = info.getName();
			}
			FDCMsgBox.showTableDetailAndOK(this, "可拆分科目已被数据引用，不能执行此操作!", errorDetailMap, 0);
			this.abort();
			return true;
		}
		
		
		return false;
	}

	/**
	 * 兄弟节点选中状态
	 * 
	 * @param row
	 *            当前选中行
	 * @param sets
	 *            兄弟节点集合
	 * @throws SQLException 
	 * @throws BOSException 
	 */
	private void brothersRow(IRow row, Set sets) throws BOSException, SQLException {
		
		// 当前行的索引
		int rowIndex = row.getRowIndex();
		// 便利当前行之前的同级行
		for (int i = rowIndex; i >= 0; i--) {
			IRow tmpRow = tblMain.getRow(i);
			int tmpLevel = tmpRow.getTreeLevel();
			int rowLevel = row.getTreeLevel();
			// 如果是同级加入集合
			if (tmpLevel == rowLevel && isCanSelected(tmpRow)) {
				sets.add(tmpRow);
			} else if (tmpLevel < rowLevel) {// 如果大于当前行级别则跳出循环
				break;
			}
		}

		// 便利当前行一下的同级行
		for (int i = rowIndex; i < tblMain.getRowCount(); i++) {
			// 从当前行下一行开始取
			IRow tmpRow = tblMain.getRow(i + 1);
			// 如果当前行下一行有数据
			if (tmpRow != null) {
				int tmpLevel = tmpRow.getTreeLevel();
				int rowLevel = row.getTreeLevel();
				// 如果级别相等加入集合
				if (tmpLevel == rowLevel && isCanSelected(tmpRow)) {
					sets.add(tmpRow);
				} else if (tmpLevel < rowLevel) {// 如果大于当前行级别则跳出循环
					break;
				}
			}
		}
		
	}

	/**
	 * 取消兄弟节点取消兄弟节点的子节点
	 * 
	 * @param row
	 *            当前兄弟节点行
	 */
	private void cancelChildRow(IRow row) {
		// 当前行的级别编码
		String number = row.getCell("number").getValue().toString();
		// 便利所有行
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			// 得到临时便利行和临时行的级别编码
			IRow tmpRow = tblMain.getRow(i);
			String tmpNumber = tmpRow.getCell("number").getValue().toString();
			// 如果临时便利行的级别编码是从当前行开始的则是子节点，设置字节点的值为false
			if (tmpNumber.startsWith(number)) {
				tmpRow.getCell("isSplit").setValue(Boolean.FALSE);
			}
		}
	}

	/**
	 * 得到当前组织父组织分配的科目
	 * 
	 * @return 当前组织父组织分配的科目集合，如果为空，则当前组织为最高组织
	 * @throws BOSException 
	 * @throws SQLException 
	 */
	private boolean isCanSelected(IRow curRow) throws BOSException, SQLException {
		if (SysContext.getSysContext().getCurrentOrgUnit().getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			return true;
		}
		FDCSQLBuilder builder = new FDCSQLBuilder();
		boolean isSplit = false;

		/**
		 * 非集团也可以选择:集团科目长编码为本科目的上级科目长编码，且可拆分选择时，本科目可以选择 by hpw
		 */
		builder.clear();
		builder.appendSql("select a.fissplit from t_fdc_costaccount a ");
		builder.appendSql("inner join t_fdc_costaccount p on p.flongnumber=a.flongnumber ");
		builder.appendSql("inner join t_fdc_costaccount b on b.fparentid=p.fid ");
		builder.appendSql("where b.fid=? and a.ffullorgunit=?");
		builder.addParam(curRow.getCell("id").getValue().toString());
		builder.addParam(OrgConstants.DEF_CU_ID);
		logger.info(builder.getTestSql());
		IRowSet set = builder.executeQuery();
		while (set.next()) {
			isSplit = set.getBoolean("fissplit");
		}
		return isSplit;
	}
	
	// 关闭检查：可拆分、科目阶段 by hpw
	public boolean destroyWindow() {
		if(isModify){
			int result = MsgBox.showConfirm3(this, EASResource.getString(FrameWorkClientUtils.strResource + "Confirm_Save_Exit"));

			if (result == KDOptionPane.YES_OPTION) {
				btnSave.doClick();
			} else if (result == KDOptionPane.NO_OPTION) {
				return true;
			} else {
				return false;
			}

		}
		return super.destroyWindow();
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * 描述：是否支持EAS高级统计(EAS800新增的功能)
	 * 
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2015-4-1
	 */
	// @Override
	protected boolean isSupportEASPivot() {
		// return super.isSupportEASPivot();
		return false;
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

}
