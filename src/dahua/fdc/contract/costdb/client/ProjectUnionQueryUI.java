/**
 * output package name
 */
package com.kingdee.eas.fdc.costdb.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ICurProject;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;

/**
 * 描述:项目汇总查询
 * @author jackwang  date:2007-6-8 <p>
 * @version EAS5.1
 */
public class ProjectUnionQueryUI extends AbstractProjectUnionQueryUI {
	private static final Logger logger = CoreUIObject.getLogger(ProjectUnionQueryUI.class);

	private CommonQueryDialog commonQueryDialog = null;

	private CustomerQueryPanel filterUI = null;

	private ProjectUnionFilterUI filterPanel = null;

	private static final String PROJECT_IDS = "projectIds";

	private static final String PROJECT_TYPE = "projectType";

	private static final String START_DATE = null;
	
	private Map acctMap = new HashMap();

	private Map dccMap = new HashMap();//

	private List al = new ArrayList();

	/**
	 * output class constructor
	 */
	public ProjectUnionQueryUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		//将表头值设为相同值
		getMainTable().checkParsed(true);
		IRow headRow0 = getMainTable().getHeadRow(0);
		IRow headRow1 = getMainTable().addHeadRow();
		headRow1.getCell(0).setValue(headRow0.getCell(0).getValue());
		headRow1.getCell(1).setValue(headRow0.getCell(1).getValue());
		headRow1.getCell(2).setValue(headRow0.getCell(2).getValue());
		
		super.onLoad();
//		actionView.setEnabled(true);
		initControl();
		FDCHelper.formatTableNumber(getMainTable(), "avg");
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				int acctNameIndex=tblMain.getColumn("costAccountName").getColumnIndex()+1;
				tblMain.getViewManager().freeze(0, acctNameIndex);
//				FDCTableHelper.setColumnMoveable(tblMain, true);
		}});
		FDCTableHelper.disableExportByPerimission(this, this.getTableForCommon(), "ActionPrint");
	}

	private void refreshTable() throws BOSException {//
		initDynamicTable();
		loadDatas();
		gatherDatas();
		setAvg(tblMain);
		//		addGatherRow();			
	}

	private void initDynamicTable() throws BOSException {

		this.tblMain.removeRows();
		//jianxing_zhou 2007-8-23
 		//this.tblMain.removeRows(false);
		// end by jianxing_zhou
		tblMain.getStyleAttributes().setLocked(true);
		tblMain.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
		tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.MULTIPLE_ROW_SELECT);
		//删除上次刷新列
		for (int i = this.tblMain.getColumnCount()-1; i >=0; i--) {
			if(tblMain.getColumnKey(i).equals("avg")){
				break;
			}
			this.tblMain.removeColumn(i);
		}
		//清空缓存
		this.dccMap.clear();
		this.acctMap.clear();
		this.al.clear();
		int colIndex = 3;
		String key;
		ICurProject icp = CurProjectFactory.getRemoteInstance();
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
//		if (this.objParam.get(PROJECT_TYPE) != null) {
//			filter.getFilterItems().add(new FilterItemInfo("projectType.id", this.objParam.get(PROJECT_TYPE).toString()));
//		}
		if (this.objParam.get("projectSet") != null) {
			filter.getFilterItems().add(new FilterItemInfo("id", (Set) this.objParam.get("projectSet"), CompareType.INCLUDE));
		}
		evi.setFilter(filter);
		SorterItemInfo sorter = new SorterItemInfo("longNumber");
		sorter.setSortType(SortType.ASCEND);
		evi.getSorter().add(sorter);
		evi.getSelector().add(new SelectorItemInfo("*"));
		evi.getSelector().add(new SelectorItemInfo("projectType.id"));		
		CurProjectCollection cpc = icp.getCurProjectCollection(evi);
		if (cpc.size() != 0) {
			for (int i = 0; i < cpc.size(); i++) {
				//增加表头“全项目”和“编制时间”
				IColumn col = tblMain.addColumn();
				int idx = col.getColumnIndex();
				getMainTable().getHeadRow(1).getCell(idx).setValue("全项目");
				
				IColumn col2=tblMain.addColumn();
				getMainTable().getHeadRow(1).getCell(col2.getColumnIndex()).setValue("编制时间");
				
				key = cpc.get(i).getId().toString();// 总成本
				col.setKey(key);
				col2.setKey(key+"createTime");
				col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
				col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
				tblMain.getHeadRow(0).getCell(key).setValue(cpc.get(i).getDisplayName());
				tblMain.getHeadRow(0).getCell(key+"createTime").setValue(cpc.get(i).getDisplayName());
				
//				tblMain.getHeadMergeManager().mergeBlock(0, idx, 0, idx+1);
			}
		}
		getMainTable().getHeadMergeManager().mergeBlock(0, 0, 1, tblMain.getColumnCount()-1, KDTMergeManager.FREE_MERGE);
		initCache(cpc);//		初始化cache	
		
		
	}
	private void gatherDatas() {
		KDTable table = this.tblMain;
		List zeroLeverRowList = new ArrayList();
		List amountColumns = new ArrayList();
		for (int j = 3; j < table.getColumnCount(); j++) {
			//过滤编制时间
			if(tblMain.getColumn(j).getKey().endsWith("createTime")){
				continue;
			}
			amountColumns.add(tblMain.getColumn(j).getKey());
		}

		for (int i = 0; i < table.getRowCount(); i++) {

			IRow row = table.getRow(i);
			if (row.getTreeLevel() == 0) {
				zeroLeverRowList.add(row);
			}
			if (row.getUserObject() == null) {// 非叶结点
				// 设置汇总行
				int level = row.getTreeLevel();
				List rowList = new ArrayList();
				for (int j = i + 1; j < table.getRowCount(); j++) {
					IRow rowAfter = table.getRow(j);
					if (rowAfter.getTreeLevel() <= level) {
						break;
					}
					if (rowAfter.getUserObject() != null) {
						rowList.add(rowAfter);
					}
				}
				for (int k = 0; k < amountColumns.size(); k++) {
					String colName = (String) amountColumns.get(k);
					BigDecimal amount = FMConstants.ZERO;
					boolean hasData = false;
					for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) rowList.get(rowIndex);
						if (rowAdd.getCell(colName).getStyleAttributes().getFontColor().equals(Color.RED)) {
						}
						Object value = rowAdd.getCell(colName).getValue();
						if (value != null) {
							if (value instanceof BigDecimal) {
								amount = amount.add((BigDecimal) value);
							} else if (value instanceof Integer) {
								amount = amount.add(new BigDecimal(((Integer) value).toString()));
							}
							hasData = true;
						}
					}
					if (hasData) {
						row.getCell(colName).setValue(amount);
					}
				}
			} else {
				// System.out.println(row.getCell(0).getValue());
				// System.out.println(((CostAccountInfo)row.getUserObject()).getName());

			}

		}
	}
	private void initCache(CurProjectCollection cpc) throws BOSException {
		if (this.objParam.get(PROJECT_IDS) != null) {
			
			//get start-date and end-date according to the params
			CustomerParams customerParams = getFilterUI().getCustomerParams();
			FDCCustomerParams para = new FDCCustomerParams(customerParams);
			
			Date startDate = (Date)para.getDate(ProjectUnionFilterUI.UNION_START_DATE);
			Date endDate   = (Date)para.getDate(ProjectUnionFilterUI.UNION_END_DATE);
			
			dccMap = ProjectDFRptHelper.createFullProjMap((String[]) objParam.get(PROJECT_IDS),startDate, endDate);
		}
		
		Set hs = new HashSet();
		for (int i = 0; i < cpc.size(); i++) {
			String id = cpc.get(i).getId().toString();
			hs.add(id);
			al.add(id);
		}
//		IDynamicCost idc = DynamicCostFactory.getRemoteInstance();
//		EntityViewInfo evi = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("account.curProject.id", hs, CompareType.INCLUDE));
//		evi.setFilter(filter);
//		evi.getSelector().add(new SelectorItemInfo("*"));
//		evi.getSelector().add(new SelectorItemInfo("account.longNumber"));
//		evi.getSelector().add(new SelectorItemInfo("account.curProject.id"));
//		try{
//			DynamicCostCollection dcc = idc.getDynamicCostCollection(evi);
//	
//			if (dcc.size() != 0) {
//				for (int i = 0; i < dcc.size(); i++) {
//					String key1 = dcc.get(i).getAccount().getCurProject().getId().toString();
//					if (dccMap.containsKey(key1)) {
//						Map tmp = (HashMap) dccMap.get(key1);
//						tmp.put(dcc.get(i).getAccount().getLongNumber(), dcc.get(i).getAdjustSumAmount());
//						dccMap.put(key1, tmp);
//					} else {
//						Map tmp = new HashMap();
//						tmp.put(dcc.get(i).getAccount().getLongNumber(), dcc.get(i).getAdjustSumAmount());
//						dccMap.put(key1, tmp);
//					}
//				}
//			}
//		}catch(Exception e){
//			
//		}
	}

	/*
	 * 科目树构造
	 */
	private void initAcct(FilterInfo acctFilter) throws BOSException {
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add(new SelectorItemInfo("longNumber"));
		sel.add(new SelectorItemInfo("fullOrgUnit.longNumber"));
		sel.add(new SelectorItemInfo("curProject.longNumber"));
		sel.add(new SelectorItemInfo("curProject.fullOrgUnit"));
		sel.add(new SelectorItemInfo("curProject.fullOrgUnit.longNumber"));
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().addObjectCollection(sel);
		view.setFilter(acctFilter);
		CostAccountCollection accts = CostAccountFactory.getRemoteInstance().getCostAccountCollection(view);
		for (int i = 0; i < accts.size(); i++) {
			CostAccountInfo info = accts.get(i);
			this.acctMap.put(info.getId().toString(), info);
		}
	}

	/*
	 * 加载数据
	 */
	private void loadDatas() {
		//		BOSObjectType bosType = this.project.getId().getType();
		FilterInfo acctFilter = new FilterInfo();
		//		if (new CurProjectInfo().getBOSType().equals(bosType)) {
		acctFilter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", OrgConstants.DEF_CU_ID));
		//		}
		acctFilter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
		acctFilter.getFilterItems().add(new FilterItemInfo("isEnterDB", new Integer(1)));
		TreeModel costAcctTree;
		try {
			// 根据当前指定项目条件构造科目树
			costAcctTree = FDCClientHelper.createDataTree(CostAccountFactory.getRemoteInstance(), acctFilter);
			this.initAcct(acctFilter);

			DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) costAcctTree.getRoot();
			Enumeration childrens = root.depthFirstEnumeration();
			int maxLevel = 0;
			while (childrens.hasMoreElements()) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) childrens.nextElement();
				if (node.getUserObject() != null && node.getLevel() > maxLevel) {
					maxLevel = node.getLevel();
				}
			}
			this.tblMain.getTreeColumn().setDepth(maxLevel);
			// /////////////////////////////////////
			for (int i = 0; i < root.getChildCount(); i++) {
				fillNode((DefaultMutableTreeNode) root.getChildAt(i));
			}
		} catch (Exception e) {
		}
	}

	/*
	 * 填充各科目节点数据
	 */
	private void fillNode(DefaultMutableTreeNode node) throws BOSException, SQLException, EASBizException {
		CostAccountInfo costAcct = (CostAccountInfo) node.getUserObject();
		if (costAcct == null) {
			return;
		}
		
//		String acctId = costAcct.getId().toString();
		String accLn = costAcct.getLongNumber();
		IRow row = tblMain.addRow();
		row.setTreeLevel(node.getLevel() - 1);
		row.getCell("costAccount").setValue(costAcct.getLongNumber().replace('!', '.'));
		row.getCell("costAccountName").setValue(costAcct.getName());
		
		if (node.isLeaf()) {
			row.setUserObject(costAcct);
			///
			if (this.dccMap.size() != 0) {
				for (int i = 0; i < al.size(); i++) {
					String key = al.get(i).toString();
					String key1 = al.get(i).toString()+"_"+accLn;
					String keyDate = key+"createTime";
					
					if (dccMap.containsKey(key1)) {
	//					Map tmp = (HashMap) this.dccMap.get(key);
						row.getCell(key).setValue(dccMap.get(key1));
					}
					//叶子结点填充编制时间
					if (dccMap.containsKey(keyDate)){
						row.getCell(keyDate).setValue(dccMap.get(keyDate));
					}
				}
			}
			
		}else{
			//非叶子结点填充编制时间
			for (int i = 0; i < al.size(); i++) {
				String key = al.get(i).toString();
				String keyDate = key+"createTime";
				if (dccMap.containsKey(keyDate)){
					row.getCell(keyDate).setValue(dccMap.get(keyDate));
				}
			}
			for (int i = 0; i < node.getChildCount(); i++) {
				this.fillNode((DefaultMutableTreeNode) node.getChildAt(i));
			}
		}

		
	}

	protected void tblMain_doRequestRowSetForHasQueryPK(RequestRowSetEvent e) {

	}

	/**
	 * output loadFields method
	 */
	public void loadFields() {
		// dataBinder.loadFields();
	}

	private void initControl() {
		this.btnAddNew.setVisible(false);
		this.btnEdit.setVisible(false);
		this.btnView.setVisible(false);
		this.btnRemove.setVisible(false);
		this.btnAttachment.setVisible(false);
		this.btnLocate.setVisible(false);
		this.menuItemAddNew.setVisible(false);
		this.menuItemEdit.setVisible(false);
		this.menuItemView.setVisible(false);
		this.menuItemRemove.setVisible(false);
		this.MenuItemAttachment.setVisible(false);
		this.menuItemLocate.setVisible(false);
		this.menuEdit.setVisible(false);
//		this.menuView.setVisible(false);

	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		//        super.tblMain_tableClicked(e);
	}

	/**
	 * output menuItemImportData_actionPerformed method
	 */
	protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		super.menuItemImportData_actionPerformed(e);
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
	 * output actionCalculator_actionPerformed
	 */
	public void actionCalculator_actionPerformed(ActionEvent e) throws Exception {
		super.actionCalculator_actionPerformed(e);
	}

	/**
	 * output actionExport_actionPerformed
	 */
	public void actionExport_actionPerformed(ActionEvent e) throws Exception {
		super.actionExport_actionPerformed(e);
	}

	/**
	 * output actionExportSelected_actionPerformed
	 */
	public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception {
		super.actionExportSelected_actionPerformed(e);
	}

	/**
	 * output actionRegProduct_actionPerformed
	 */
	public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception {
		super.actionRegProduct_actionPerformed(e);
	}

	/**
	 * output actionPersonalSite_actionPerformed
	 */
	public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception {
		super.actionPersonalSite_actionPerformed(e);
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
	}

	
	 
	// 覆盖此方法,屏蔽回车键产生异常中断 add by jianxing_zhou 2007-8-24
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		//super.actionView_actionPerformed(e);
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
	}

	/**
	 * output actionRefresh_actionPerformed
	 */
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		//        super.actionRefresh_actionPerformed(e);
		this.refreshTable();
	}

	/**
	 * output actionPrint_actionPerformed
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrint_actionPerformed(e);
	}

	/**
	 * output actionPrintPreview_actionPerformed
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrintPreview_actionPerformed(e);
	}

	/**
	 * output actionLocate_actionPerformed
	 */
	public void actionLocate_actionPerformed(ActionEvent e) throws Exception {
		super.actionLocate_actionPerformed(e);
	}

	/**
	 * output actionImportData_actionPerformed
	 */
	public void actionImportData_actionPerformed(ActionEvent e) throws Exception {
		super.actionImportData_actionPerformed(e);
	}

	/**
	 * output actionAttachment_actionPerformed
	 */
	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception {
		super.actionAttachment_actionPerformed(e);
	}

	/**
	 * output actionExportData_actionPerformed
	 */
	public void actionExportData_actionPerformed(ActionEvent e) throws Exception {
		super.actionExportData_actionPerformed(e);
	}

	/**
	 * output actionToExcel_actionPerformed
	 */
	public void actionToExcel_actionPerformed(ActionEvent e) throws Exception {
		super.actionToExcel_actionPerformed(e);
	}

	/**
	 * output actionStartWorkFlow_actionPerformed
	 */
	public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception {
		super.actionStartWorkFlow_actionPerformed(e);
	}

	/**
	 * output actionPublishReport_actionPerformed
	 */
	public void actionPublishReport_actionPerformed(ActionEvent e) throws Exception {
		super.actionPublishReport_actionPerformed(e);
	}

	/**
	 * output actionCancel_actionPerformed
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		super.actionCancel_actionPerformed(e);
	}

	/**
	 * output actionCancelCancel_actionPerformed
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception {
		super.actionCancelCancel_actionPerformed(e);
	}

	///////////////////////////////////////////////
	/**
	 * output actionQuery_actionPerformed
	 */
	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {

		openConditionDialog((ProjectUnionFilterUI)getFilterUI());
	}

	private HashMap objParam = new HashMap();

	protected void openConditionDialog(ProjectUnionFilterUI myPanel) throws Exception {
		if (this.getConditionDialog().show()) {
			objParam.clear();
			objParam.putAll(((ProjectUnionFilterUI)getFilterUI()).getResult());
			//			if (isFirstIn) {
			//				initStaticTable();// 初始化静态table信息
			//				isFirstIn = false;
			//			}
			this.refreshTable();
		} else {
			SysUtil.abort();
		}
	}

	public CommonQueryDialog getConditionDialog() throws Exception {
//		if (this.commonQueryDialog == null) {
//			commonQueryDialog = new CommonQueryDialog();
//			commonQueryDialog.setOwner(this);
//
//			MetaDataPK mainQueryPK = new MetaDataPK("com.kingdee.eas.basedata.assistant.app", "ProjectQuery");
//			commonQueryDialog.setQueryObjectPK(mainQueryPK);
//			commonQueryDialog.setShowFilter(false);
//			commonQueryDialog.setShowSorter(false);
//			commonQueryDialog.setWidth(429);
//			commonQueryDialog.setHeight(356);
//			commonQueryDialog.addUserPanel(getFilterPanel());
//			commonQueryDialog.setTitle(getFilterPanel().getUITitle());
//			commonQueryDialog.setParentUIClassName(this.getClass().getName());// com.kingdee.eas.fdc.aimcost.client.DynCostSnapShotQueryUI
//			// commonQueryDialog.setDisVisiableDefaultView(false);
//			commonQueryDialog.setTitle("项目汇总查询");
//			commonQueryDialog.setDisVisiableDefaultView(true);// .isShowTable();
//		}
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = new CommonQueryDialog();
        if (this.getUIWindow() == null)
        {
        	commonQueryDialog.setOwner((Component) getUIContext().get(UIContext.OWNERWINDOW));
        }
        else
        {
        	commonQueryDialog.setOwner(this);
        }
        commonQueryDialog.setUiObject(this);
        commonQueryDialog.setParentUIClassName(this.getClass().getName());
        commonQueryDialog.setTitle(this.getUITitle() + " - " + EASResource.getString(FrameWorkClientUtils.strResource + "Query_Filter"));        
		MetaDataPK mainQueryPK = new MetaDataPK("com.kingdee.eas.basedata.assistant.app", "ProjectQuery");
		commonQueryDialog.setQueryObjectPK(mainQueryPK);
		commonQueryDialog.setShowFilter(false);
		commonQueryDialog.setShowSorter(false);
		commonQueryDialog.setWidth(429);
		commonQueryDialog.setHeight(356);
		commonQueryDialog.setDisVisiableDefaultView(true);		
		commonQueryDialog.getCommonQueryParam().setUiObject(null);
		try {
			commonQueryDialog.addUserPanel(getFilterUI());
		} catch (Exception e) {
		}		
		// conditionDialog.set
		return commonQueryDialog;
	}
	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = new CommonQueryDialog();
        if (this.getUIWindow() == null)
        {
        	commonQueryDialog.setOwner((Component) getUIContext().get(UIContext.OWNERWINDOW));
        }
        else
        {
        	commonQueryDialog.setOwner(this);
        }
        commonQueryDialog.setUiObject(this);
        commonQueryDialog.setParentUIClassName(this.getClass().getName());
        commonQueryDialog.setTitle(this.getUITitle() + " - " + EASResource.getString(FrameWorkClientUtils.strResource + "Query_Filter"));        
//		MetaDataPK mainQueryPK = new MetaDataPK("com.kingdee.eas.basedata.assistant.app", "ProjectQuery");
		commonQueryDialog.setQueryObjectPK(this.mainQueryPK);
		commonQueryDialog.setShowFilter(false);
		commonQueryDialog.setShowSorter(false);
		commonQueryDialog.setWidth(429);
		commonQueryDialog.setHeight(356);
		commonQueryDialog.setDisVisiableDefaultView(true);		
		try {
			commonQueryDialog.addUserPanel(getFilterUI());
		} catch (Exception e) {
		}		
		return commonQueryDialog;
	}
//	public ProjectUnionFilterUI getFilterPanel() throws Exception {
//		if (this.filterPanel == null) {
//			filterPanel = new ProjectUnionFilterUI();
//		}
//		return filterPanel;
//	}

	protected String getEditUIName() {
		// TODO 自动生成方法存根
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		// TODO 自动生成方法存根
		return null;
	}

	// //////////////////////////////////////////
	protected EntityViewInfo getInitDefaultSolution() {

		EntityViewInfo entityViewInfo = new EntityViewInfo();
		try {
			this.getFilterUI().onLoad();
		} catch (Exception e) {
			e.printStackTrace();
		}
		entityViewInfo.setFilter(this.getFilterUI().getFilterInfo());
		return entityViewInfo;
	}

	protected boolean isAllowDefaultSolutionNull() {
		return false;
	}



	private CustomerQueryPanel getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new ProjectUnionFilterUI();
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
	}

	boolean hasCurrency = false;

	/**
	 * 
	 * 初始化默认过滤条件
	 * 
	 * @return 如果重载了（即做了初始化动作），请返回true;默认返回false;
	 */
	protected boolean initDefaultFilter() {
		return true;
	}

	protected String getKeyFieldName() {
		return "costAccount";
	}
	
	protected boolean isIgnoreCUFilter() {
		// TODO 自动生成方法存根
		return true;
	}
	
	private void setAvg(KDTable table){
		IRow row=null;
		List fullPrjIndexs=new ArrayList();
		for(int i=3;i<table.getColumnCount();i++){
			String key = table.getColumnKey(i);
			if(key!=null&&key.indexOf("_")==-1&&!key.endsWith("createTime")){
				fullPrjIndexs.add(key);
			}
		}
		if(fullPrjIndexs.size()<=0) return;
		BigDecimal count=new BigDecimal(fullPrjIndexs.size()+"");
		for(int i=0;i<table.getRowCount();i++){
			row=table.getRow(i);

			BigDecimal sum=FDCHelper.ZERO;
			for(Iterator iter=fullPrjIndexs.iterator();iter.hasNext();){
				String key=(String)iter.next();
				BigDecimal amt=FDCHelper.toBigDecimal(row.getCell(key).getValue());
				sum=sum.add(amt);
			}
			if(sum.compareTo(FDCHelper.ZERO)==0){
				row.getCell("avg").setValue(null);
			}else{
				row.getCell("avg").setValue(sum.divide(count, 2, BigDecimal.ROUND_HALF_UP));
			}
		
		}
	}
}