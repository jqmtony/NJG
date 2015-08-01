/**
 * output package name
 */
package com.kingdee.eas.fdc.costdb.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.chart.ChartType;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.aimcost.AimCostCollection;
import com.kingdee.eas.fdc.aimcost.AimCostFactory;
import com.kingdee.eas.fdc.aimcost.CostEntryCollection;
import com.kingdee.eas.fdc.aimcost.CostEntryInfo;
import com.kingdee.eas.fdc.aimcost.DynamicCostCollection;
import com.kingdee.eas.fdc.aimcost.DynamicCostFactory;
import com.kingdee.eas.fdc.aimcost.DynamicCostInfo;
import com.kingdee.eas.fdc.aimcost.HappenDataGetter;
import com.kingdee.eas.fdc.aimcost.MeasureEntryInfo;
import com.kingdee.eas.fdc.aimcost.client.AimCostHandler;
import com.kingdee.eas.fdc.aimcost.client.ChartUI;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.ChangeTypeCollection;
import com.kingdee.eas.fdc.basedata.ChangeTypeFactory;
import com.kingdee.eas.fdc.basedata.ChangeTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.basedata.client.ChartData;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.costdb.DBDynCostSnapShotCollection;
import com.kingdee.eas.fdc.costdb.DBDynCostSnapShotFactory;
import com.kingdee.eas.fdc.costdb.DBDynCostSnapShotInfo;
import com.kingdee.eas.fdc.costdb.DBDynCostSnapShotProTypEntryCollection;
import com.kingdee.eas.fdc.costdb.DBDynCostSnapShotSettEntryCollection;
import com.kingdee.eas.fdc.costdb.IDBDynCostSnapShot;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 描述:动态成本导入
 * 
 * @author jackwang date:2007-4-12
 *         <p>
 * @version EAS5.2
 */
public class DynamicCostImportUI extends AbstractDynamicCostImportUI {
	private static final String SETTLE_ADJUST = "settleAdjust";

	private static final String BUILD_PART = "buildPart";

	private static final String SELL_PART = "sellPart";

	private static final String DIFF = "diff";

	private static final String DYNAMIC_COST = "dynamicCost";

	private static final String AIM_COST = "aimCost";

	private static final String INTENDING_HAPPEN = "intendingHappen";

	private static final String HAS_HAPPEN = "hasHappen";

	private static final String NO_TEXT = "noText";

	private static final String SETTLE = "Settle";

	private static final String NO_SETTLE = "NoSettle";

	private static final String SUB_TOTAL_SETTLE = "subTotalSettle";

	private static final String SUB_TOTAL_NO_SETTLE = "subTotalNoSettle";

	private static final String ASSIGN_AMOUNT_SETTLE = "assignAmountSettle";

	private static final String ASSIGN_AMOUNT_NO_SETTLE = "assignAmountNoSettle";

	private ChangeTypeCollection changeTypes;

	private ProductTypeCollection products;

	private Map acctMap = new HashMap();

	private Map dynamicCostMap = new HashMap();

	private Map aimCostMap = new HashMap();

	private HappenDataGetter happenGetter;

	private BigDecimal sellArea = null;

	private BigDecimal buildArea = null;

	/**
	 * output class constructor
	 */
	public DynamicCostImportUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		loadAccountTableData();
		this.btnImportData.setIcon(EASResource.getIcon("imgTbtn_input"));
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		view.getSorter().add(new SorterItemInfo("number"));
		this.changeTypes = ChangeTypeFactory.getRemoteInstance().getChangeTypeCollection(view);
		this.products = ProductTypeFactory.getRemoteInstance().getProductTypeCollection(view);
		super.onLoad();
		initControl();
		btnSave.setVisible(false);
		btnChart.setVisible(false);
		this.menuItemSave.setVisible(false);
		this.menuItemSubmit.setVisible(false);
	}

	// 数据对象变化时，刷新界面状态
	protected void setActionState() {

	}

	/**
	 * 屏蔽回车键
	 */
	public void actionView_actionPerformed(ActionEvent e) throws Exception {

	}

	protected ITreeBase getTreeInterface() throws Exception {
		return CurProjectFactory.getRemoteInstance();
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		// super.tblMain_tableClicked(e);
	}

	protected void tblMain_doRequestRowSet(RequestRowSetEvent e) {
	}

	protected String getEditUIName() {
		return null;
	}

	/**
	 * show出应用条件属性窗口（即，绑定属性窗口）
	 * 
	 * @throws Exception
	 */
	private void showSelectImportWindows() throws Exception {
		HashMap hm = new UIContext(this);
		IUIFactory uiFactory = null;
		uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		IUIWindow uiWindow = uiFactory.create("com.kingdee.eas.fdc.costdb.client.DynamicCostImportSourceUI", hm, null, null);
		uiWindow.show();
	}

	// 初始化下拉列表。
	public void loadAccountTableData() throws Exception {
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		IDBDynCostSnapShot iDBDynCostSnapShot = DBDynCostSnapShotFactory.getRemoteInstance();
		EntityViewInfo evi = new EntityViewInfo();
		evi.getSelector().add(new SelectorItemInfo("snapShotDate"));
		SorterItemInfo sorter = new SorterItemInfo("snapShotDate");
		sorter.setSortType(SortType.DESCEND);
		evi.getSorter().add(sorter);
		DBDynCostSnapShotCollection dcssc = iDBDynCostSnapShot.getDBDynCostSnapShotCollection(evi);
		Iterator ter = dcssc.iterator();
		ArrayList al = new ArrayList();
		Iterator date;
		String str;
		while (ter.hasNext()) {
			Object o = ((DBDynCostSnapShotInfo) ter.next()).getSnapShotDate();
			str = o.toString().substring(0, 10);
			if (!al.contains(str)) {
				al.add(str);
				model.addElement(str);
			}
		}
		this.kdcSaveDate.setModel(model);
		if (kdcSaveDate.getItemCount() != 0) {
			kdcSaveDate.setSelectedIndex(0);
			Object o = this.kdcSaveDate.getSelectedItem();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			if (o != null && o.toString().length() > 0) {
				o = df.parse(o.toString());
				if (o != null && o instanceof Date) {
					this.selectDate = (Date) o;
				}
			}
		}
	}

	public void actionImportData_actionPerformed(ActionEvent e) throws Exception {
		this.showSelectImportWindows();
		actionRefresh_actionPerformed(e);
	}

	private Date selectDate = new Date();

	protected void kdcSaveDate_itemStateChanged(ItemEvent e) throws Exception {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			Calendar worldTour = Calendar.getInstance();
			Date d = worldTour.getTime();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Object o = this.kdcSaveDate.getSelectedItem();
			if (o != null && o.toString().length() > 0) {
				o = df.parse(o.toString());
				if (o != null && o instanceof Date) {
					d = (Date) o;
				}
			}
			worldTour.setTime(d);
			String selectDate = df.format(d);
			selectDate = selectDate + " 00:00:00";
			this.selectDate = df.parse(selectDate);
			this.fillMainTable(getSelectObjId());
		}
	}

	protected void initTree() throws Exception {
		this.initMainTable();
		// ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder(false,
		// false, true);
		// treeMain.setShowsRootHandles(true);
		// projectTreeBuilder.build(this, treeMain, actionOnLoad);
		ProjectTreeBuilder treeBuilder = new ProjectTreeBuilder();
		treeBuilder.build(this, this.treeMain, this.actionOnLoad);
		this.treeMain.expandAllNodes(true, (TreeNode) ((TreeModel) this.treeMain.getModel()).getRoot());
	}

	/**
	 * 设置表格属性
	 * 
	 * @throws BOSException
	 */
	private void initMainTable() throws BOSException {
		tblMain.getStyleAttributes().setLocked(true);
		tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
		tblMain.getViewManager().setFreezeView(-1, 2);
		tblMain.getColumn(AIM_COST).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn(HAS_HAPPEN).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn(INTENDING_HAPPEN).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn(DYNAMIC_COST).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn(NO_TEXT).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn(DIFF).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn(SELL_PART).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn(BUILD_PART).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);

		tblMain.getColumn(AIM_COST).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn(HAS_HAPPEN).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn(INTENDING_HAPPEN).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn(DYNAMIC_COST).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn(NO_TEXT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn(DIFF).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn(SELL_PART).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn(BUILD_PART).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		// /////////////////
		// tblMain.getColumn("aimSaleUnitAmt").getStyleAttributes().setHided(true);//.setHorizontalAlign(HorizontalAlignment.RIGHT);
		// tblMain.getColumn("aimCostAmt").getStyleAttributes().setHided(true);//.setHorizontalAlign(HorizontalAlignment.RIGHT);
		// tblMain.getColumn("hasHappenAmt").getStyleAttributes().setHided(true);//.setHorizontalAlign(HorizontalAlignment.RIGHT);
		// tblMain.getColumn("notHappenAmt").getStyleAttributes().setHided(true);//.setHorizontalAlign(HorizontalAlignment.RIGHT);
		// tblMain.getColumn("dynSaleUnitAmt").getStyleAttributes().setHided(true);//.setHorizontalAlign(HorizontalAlignment.RIGHT);
		// tblMain.getColumn("dynCostAmt").getStyleAttributes().setHided(true);//.setHorizontalAlign(HorizontalAlignment.RIGHT);

		// tblMain.getColumn("aimSaleUnitAmt").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		// tblMain.getColumn("aimCostAmt").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		// tblMain.getColumn("hasHappenAmt").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		// tblMain.getColumn("notHappenAmt").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		// tblMain.getColumn("dynSaleUnitAmt").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		// tblMain.getColumn("dynCostAmt").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		// ////////////

		tblMain.addHeadRow(1, (IRow) tblMain.getHeadRow(0).clone());
		for (int i = 0; i < tblMain.getColumnCount(); i++) {
			tblMain.getHeadMergeManager().mergeBlock(0, i, 1, i);
		}
		int colIndex = 2;
		//
		IColumn col = tblMain.addColumn(colIndex++);
		String key = ASSIGN_AMOUNT_NO_SETTLE;
		col.setKey(key);
		col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getHeadRow(0).getCell(key).setValue(AimCostHandler.getResource("NoSettleContract"));
		tblMain.getHeadRow(1).getCell(key).setValue(AimCostHandler.getResource("AssignAmount"));
		for (int i = 0; i < this.changeTypes.size(); i++) {
			ChangeTypeInfo changeTypeInfo = this.changeTypes.get(i);
			key = changeTypeInfo.getId().toString() + NO_SETTLE;
			col = tblMain.addColumn(colIndex++);
			col.setKey(key);
			tblMain.getHeadRow(0).getCell(key).setValue(AimCostHandler.getResource("NoSettleContract"));
			tblMain.getHeadRow(1).getCell(key).setValue(changeTypeInfo.getName());
			col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
			col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		}
		//
		col = tblMain.addColumn(colIndex++);
		key = SUB_TOTAL_NO_SETTLE;
		col.setKey(key);
		col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getHeadRow(0).getCell(key).setValue(AimCostHandler.getResource("NoSettleContract"));
		tblMain.getHeadRow(1).getCell(key).setValue(AimCostHandler.getResource("ContractSubTotal"));
		tblMain.getHeadMergeManager().mergeBlock(0, 2, 0, colIndex - 1);
		// ////////////--------------------------------
		col = tblMain.addColumn(colIndex++);
		key = ASSIGN_AMOUNT_SETTLE;
		col.setKey(key);
		col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getHeadRow(0).getCell(key).setValue(AimCostHandler.getResource("SettleContract"));
		tblMain.getHeadRow(1).getCell(key).setValue(AimCostHandler.getResource("AssignAmount"));
		for (int i = 0; i < this.changeTypes.size(); i++) {
			ChangeTypeInfo changeTypeInfo = this.changeTypes.get(i);
			key = changeTypeInfo.getId().toString() + SETTLE;
			col = tblMain.addColumn(colIndex++);
			col.setKey(key);
			tblMain.getHeadRow(0).getCell(key).setValue(AimCostHandler.getResource("SettleContract"));
			tblMain.getHeadRow(1).getCell(key).setValue(changeTypeInfo.getName());
			col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
			col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		}
		//
		col = tblMain.addColumn(colIndex++);
		key = SETTLE_ADJUST;
		col.setKey(key);
		col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getHeadRow(0).getCell(key).setValue(AimCostHandler.getResource("SettleContract"));
		tblMain.getHeadRow(1).getCell(key).setValue(AimCostHandler.getResource("SettleAdjust"));
		//
		col = tblMain.addColumn(colIndex++);
		key = SUB_TOTAL_SETTLE;
		col.setKey(key);
		col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getHeadRow(0).getCell(key).setValue(AimCostHandler.getResource("SettleContract"));
		tblMain.getHeadRow(1).getCell(key).setValue(AimCostHandler.getResource("ContractSubTotal"));
		tblMain.getHeadMergeManager().mergeBlock(0, this.changeTypes.size() + 4, 0, colIndex - 1);
		// ///////////////////////////////////////////////////////////////////////////////////////jack
		// modified070412
		colIndex = colIndex + 9;
		for (int i = 0; i < this.products.size(); i++) {
			key = products.get(i).getId().toString() + "Aim" + "SellPart";
			col = tblMain.addColumn(colIndex++);
			col.setKey(key);
			tblMain.getHeadRow(0).getCell(key).setValue(products.get(i).getName());
			tblMain.getHeadRow(1).getCell(key).setValue(AimCostHandler.getResource("Aim") + AimCostHandler.getResource("SellPart"));
			col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
			col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			//
			key = products.get(i).getId().toString() + "Aim";
			col = tblMain.addColumn(colIndex++);
			col.setKey(key);
			tblMain.getHeadRow(0).getCell(key).setValue(products.get(i).getName());
			tblMain.getHeadRow(1).getCell(key).setValue(AimCostHandler.getResource("Aim"));
			col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
			col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			//
			key = products.get(i).getId().toString() + "HasHappen";
			col = tblMain.addColumn(colIndex++);
			col.setKey(key);
			tblMain.getHeadRow(0).getCell(key).setValue(products.get(i).getName());
			tblMain.getHeadRow(1).getCell(key).setValue(AimCostHandler.getResource("HasHappen"));
			col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
			col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			//
			key = products.get(i).getId().toString() + "NoHappen";
			col = tblMain.addColumn(colIndex++);
			col.setKey(key);
			tblMain.getHeadRow(0).getCell(key).setValue(products.get(i).getName());
			tblMain.getHeadRow(1).getCell(key).setValue(AimCostHandler.getResource("NoHappen"));
			col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
			col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			//
			key = products.get(i).getId().toString() + "SellPart";
			col = tblMain.addColumn(colIndex++);
			col.setKey(key);
			tblMain.getHeadRow(0).getCell(key).setValue(products.get(i).getName());
			tblMain.getHeadRow(1).getCell(key).setValue(AimCostHandler.getResource("SellPart"));
			col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
			col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			//
			key = products.get(i).getId().toString() + "Dynamic";
			col = tblMain.addColumn(colIndex++);
			col.setKey(key);
			tblMain.getHeadRow(0).getCell(key).setValue(products.get(i).getName());
			tblMain.getHeadRow(1).getCell(key).setValue(AimCostHandler.getResource("Dynamic"));
			col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
			col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);

			tblMain.getHeadMergeManager().mergeBlock(0, colIndex - 6, 0, colIndex - 1);
		}

	}

	/**
	 * 获取query中的主键列名称，返回供编辑/删除时获取主键用，默认值为"id"，继承类可以重载
	 * 
	 */
	protected String getKeyFieldName() {
		return "acctNumber";
	}

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		loadAccountTableData();
		this.fillMainTable(getSelectObjId());
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		String selectObjId = getSelectObjId();
		if (selectObjId == null) {
			return;
		}
		fillMainTable(selectObjId);
	}

	private String getSelectObjId() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node.getUserObject() == null || OrgViewUtils.isTreeNodeDisable(node)) {
			return null;
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			return projectInfo.getId().toString();
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
			if (oui.getUnit() == null) {

			}
			FullOrgUnitInfo info = oui.getUnit();
			return info.getId().toString();
		}
		return null;
	}

	public void fillMainTable(String objectId) throws Exception {
		tblMain.removeRows();
		BOSObjectType bosType = BOSUuid.read(objectId).getType();
		FilterInfo acctFilter = new FilterInfo();
		if (new CurProjectInfo().getBOSType().equals(bosType)) {
			acctFilter.getFilterItems().add(new FilterItemInfo("curProject.id", objectId));
		} else {
			acctFilter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", objectId));
		}
		acctFilter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
		TreeModel costAcctTree = FDCClientHelper.createDataTree(CostAccountFactory.getRemoteInstance(), acctFilter);
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
		// setApportionData();
		// initAimCostData(objectId);
		// initDynamicCostData(objectId);
		// this.happenGetter = new HappenDataGetter(objectId, true, true);
		// 根据选择的节点获取保存内容
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("project.id", objectId));// ////////////////工程项目节点
		filter.getFilterItems().add(new FilterItemInfo("snapShotDate", this.selectDate));// ///////快照日期
		evi.setFilter(filter);
		evi.getSelector().add(new SelectorItemInfo("*"));
		evi.getSelector().add(new SelectorItemInfo("project.*"));
		evi.getSelector().add(new SelectorItemInfo("settEntries.*"));// 结算分录
		evi.getSelector().add(new SelectorItemInfo("settEntries.changeType.*"));
		evi.getSelector().add(new SelectorItemInfo("approtionType.name"));
		evi.getSelector().add(new SelectorItemInfo("proTypEntries.*"));// 产品分录
		evi.getSelector().add(new SelectorItemInfo("proTypEntries.productType.*"));
		// evi.getSelector().add(new SelectorItemInfo("proTypEntries.*"));
		IDBDynCostSnapShot iDBDynCostSnapShot = DBDynCostSnapShotFactory.getRemoteInstance();
		this.dbdcssc = iDBDynCostSnapShot.getDBDynCostSnapShotCollection(evi);
		if (dbdcssc.size() != 0) {
			for (int i = 0; i < dbdcssc.size(); i++) {
				exportMap.put(dbdcssc.get(i).getCostAccount().getId().toString(), dbdcssc.get(i));
			}
			for (int i = 0; i < root.getChildCount(); i++) {
				fillNode((DefaultMutableTreeNode) root.getChildAt(i));
			}
		}
		 this.setUnionData();
	}

	Map exportMap = new HashMap();

	DBDynCostSnapShotCollection dbdcssc = new DBDynCostSnapShotCollection();

	private void addTotalRows() {

	}

	private void setApportionData() throws BOSException, SQLException, EASBizException {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node.getUserObject() == null || OrgViewUtils.isTreeNodeDisable(node)) {
			return;
		}
		String selectObjId = this.getSelectObjId();
		if (selectObjId == null) {
			return;
		}
		buildArea = FDCHelper.getApportionValue(selectObjId, ApportionTypeInfo.buildAreaType, ProjectStageEnum.DYNCOST);
		sellArea = FDCHelper.getApportionValue(selectObjId, ApportionTypeInfo.sellAreaType, ProjectStageEnum.DYNCOST);
	}

	/**
	 * 设置父科目汇总数
	 * 
	 * @param table
	 * @param amountColumns
	 */
	public void setUnionData() {
		KDTable table = this.tblMain;
		List amountColumns = new ArrayList();
		for (int i = 2; i < table.getColumnCount(); i++) {
			amountColumns.add(table.getColumn(i).getKey());
		}
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			if (row.getUserObject() == null) {
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
			}
		}
	}

	protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception {

	}

	protected void initWorkButton() {
		super.initWorkButton();
		this.btnChart.setIcon(EASResource.getIcon("imgTbtn_planchart"));
		this.menuItemChart.setIcon(EASResource.getIcon("imgTbtn_planchart"));
		menuItemSave.setIcon(FDCClientHelper.ICON_SAVE);
		btnSave.setIcon(FDCClientHelper.ICON_SAVE);
	}

	public void showChart(int type) {
		ChartData data = new ChartData();
		if (type == 1) {
			List colKeys = new ArrayList();
			colKeys.add(AIM_COST);
			colKeys.add(DYNAMIC_COST);
			String[] serials = new String[colKeys.size()];
			for (int i = 0; i < colKeys.size(); i++) {
				serials[i] = (String) tblMain.getHeadRow(0).getCell((String) colKeys.get(i)).getValue();
			}
			data.setSeriesKeys(serials);
			List rows = new ArrayList();
			for (int i = 0; i < tblMain.getSelectManager().size(); i++) {
				KDTSelectBlock selectBlock = tblMain.getSelectManager().get(i);
				for (int j = selectBlock.getTop(); j <= selectBlock.getBottom(); j++) {
					IRow row = this.tblMain.getRow(j);
					rows.add(row);
				}
			}

			for (int i = 0; i < rows.size(); i++) {
				fillChartDataByRow(data, (IRow) rows.get(i), colKeys);
			}
			data.setChartType(ChartType.CT_COLUMNCLUSTERED3D);
		} else if (type == 2) {
			String noText = (String) tblMain.getHeadRow(0).getCell(NO_TEXT).getValue();
			String intendingHappen = (String) tblMain.getHeadRow(0).getCell(INTENDING_HAPPEN).getValue();
			String[] serials = new String[] { "合同性成本", noText, intendingHappen };
			data.setSeriesKeys(serials);

			List rows = new ArrayList();
			for (int i = 0; i < tblMain.getSelectManager().size(); i++) {
				KDTSelectBlock selectBlock = tblMain.getSelectManager().get(i);
				for (int j = selectBlock.getTop(); j <= selectBlock.getBottom(); j++) {
					IRow row = this.tblMain.getRow(j);
					rows.add(row);
				}
			}
			for (int i = 0; i < rows.size(); i++) {
				IRow row = (IRow) rows.get(i);
				BigDecimal[] values = new BigDecimal[serials.length];
				BigDecimal subNoSettle = (BigDecimal) row.getCell(SUB_TOTAL_NO_SETTLE).getValue();
				BigDecimal subSettle = (BigDecimal) row.getCell(SUB_TOTAL_SETTLE).getValue();
				if (subNoSettle == null) {
					subNoSettle = FDCHelper.ZERO;
				}
				if (subSettle == null) {
					subSettle = FDCHelper.ZERO;
				}
				values[0] = subNoSettle.add(subSettle);
				values[1] = (BigDecimal) row.getCell(NO_TEXT).getValue();
				values[2] = (BigDecimal) row.getCell(INTENDING_HAPPEN).getValue();
				data.addGroupData(row.getCell("acctName").getValue().toString(), values);
			}
			data.setChartType(ChartType.CT_MULTIPIE);
		}
		DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		// colKeys.add("intendingHappen");

		data.setTitle(proNode.getText());

		try {
			ChartUI.showChart(this, data);
		} catch (UIException e) {
			e.printStackTrace();
		}
	}

	private void fillChartDataByRow(ChartData data, IRow row, List colKeys) {
		BigDecimal[] values = new BigDecimal[colKeys.size()];
		for (int k = 0; k < colKeys.size(); k++) {
			values[k] = (BigDecimal) row.getCell((String) colKeys.get(k)).getValue();
		}
		data.addGroupData(row.getCell("acctName").getValue().toString(), values);
	}

	private void initControl() {
		this.btnAddNew.setVisible(false);
		this.btnEdit.setVisible(false);
		this.btnRemove.setVisible(false);
		this.btnView.setVisible(false);
		this.btnQuery.setVisible(false);
		this.btnLocate.setVisible(false);
		this.menuItemAddNew.setVisible(false);
		this.menuItemEdit.setVisible(false);
		this.menuItemRemove.setVisible(false);
		this.menuItemView.setVisible(false);
		this.menuItemQuery.setVisible(false);
		this.menuItemLocate.setVisible(false);
		this.menuEdit.setVisible(false);
		this.menuView.setVisible(false);
		this.menuItemImportData.setVisible(true);
		this.treeMain.setSelectionRow(0);
		// KDMenuItem menuItem1 = new KDMenuItem();
		// menuItem1.setAction(new ItemAction() {
		// public void actionPerformed(ActionEvent e) {
		// showChart(1);
		// }
		// });
		// menuItem1.setText("全项目成本差额图");
		// this.btnChart.addAssistMenuItem(menuItem1);
		//
		// KDMenuItem menuItem2 = new KDMenuItem();
		// menuItem2.setAction(new ItemAction() {
		// public void actionPerformed(ActionEvent e) {
		// showChart(2);
		// }
		// });
		// menuItem2.setText("动态成本构成图");
		// this.btnChart.addAssistMenuItem(menuItem2);
		// FDCClientHelper.addSqlMenu(this, this.menuEdit);
	}

	private void fillNode(DefaultMutableTreeNode node) throws BOSException, SQLException, EASBizException {

		DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		// if (proNode.getUserObject() instanceof CurProjectInfo) {
		// if (!proNode.isLeaf()) {
		// if (!(((DefaultKingdeeTreeNode)
		// proNode.getChildAt(0)).getUserObject() instanceof ProductTypeInfo)) {
		// return;
		// }
		// }
		// }
		if (!proNode.isLeaf()) {// 只填充明细节点
			return;
		}
		CostAccountInfo costAcct = (CostAccountInfo) node.getUserObject();
		if (costAcct == null) {
			return;
		}
		String acctId = costAcct.getId().toString();
		IRow row = tblMain.addRow();
		if(costAcct.isIsLeaf()){
			row.setUserObject(costAcct);
		}
		row.setTreeLevel(node.getLevel() - 1);
		row.getCell("acctNumber").setValue(costAcct.getLongNumber().replace('!', '.'));
		row.getCell("acctName").setValue(costAcct.getName());
		// ////////////////////////////
		if (this.dbdcssc.size() != 0) {
			DBDynCostSnapShotInfo dbd = (DBDynCostSnapShotInfo) exportMap.get(acctId);
			if (dbd != null) {
				row.getCell(AIM_COST).setValue(dbd.getAimCostAmt());
				row.getCell(NO_TEXT).setValue(dbd.getUnContractCostAmt());
				row.getCell(HAS_HAPPEN).setValue(dbd.getSoFarHasAmt());
				row.getCell(INTENDING_HAPPEN).setValue(dbd.getSoFarToAmt());
				row.getCell(AIM_COST).setValue(dbd.getAimCostAmt());
				row.getCell(DYNAMIC_COST).setValue(dbd.getDynCostAmt());
				row.getCell(DIFF).setValue(dbd.getDiffAmt());
				row.getCell(SELL_PART).setValue(dbd.getSalableAmt());
				row.getCell(BUILD_PART).setValue(dbd.getConstrAmt());
				
		
				
				row.getCell(ASSIGN_AMOUNT_NO_SETTLE).setValue(dbd.getUnSettSignAmt());// 未结算签约金额
				row.getCell(ASSIGN_AMOUNT_SETTLE).setValue(dbd.getSettSignAmt());// 已结算签约金额
				BigDecimal subTotalNoSett=FDCHelper.ZERO;
				BigDecimal subTotalSett=FDCHelper.ZERO;
				if(dbd.getUnSettSignAmt()!=null){
					subTotalNoSett=subTotalNoSett.add(dbd.getUnSettSignAmt());
				}
				if(dbd.getSettSignAmt()!=null){
					subTotalSett=subTotalSett.add(dbd.getSettSignAmt());
				}
				DBDynCostSnapShotSettEntryCollection dbde = dbd.getSettEntries();// 结算分录
				if (dbde != null && dbde.size() != 0) {
					for (int i = 0; i < changeTypes.size(); i++) {
						BigDecimal changeAmount = null;
						for (int j = 0; j < dbde.size(); j++) {
							if ((dbde.get(j).getChangeType() != null) && (changeTypes.get(i).getId().toString().equals(dbde.get(j).getChangeType().getId().toString()))) {
								changeAmount = dbde.get(j).getUnSettleAmt();
								row.getCell(changeTypes.get(i).getId().toString() + NO_SETTLE).setValue(changeAmount);// 未结算变更金额
								if(changeAmount!=null){
									subTotalNoSett=subTotalNoSett.add(changeAmount);
								}
								changeAmount = dbde.get(j).getSettleAmt();
								row.getCell(changeTypes.get(i).getId().toString() + SETTLE).setValue(changeAmount);// 已结算变更金额
								if(changeAmount!=null){
									subTotalSett=subTotalSett.add(changeAmount);
								}
							}
						}
					}
				}
				if(subTotalNoSett.compareTo(FDCHelper.ZERO)!=0){
					row.getCell(SUB_TOTAL_NO_SETTLE).setValue(subTotalNoSett);
				}
				if(dbd.getSettAdjustAmt()!=null){
					subTotalSett=subTotalSett.add(dbd.getSettAdjustAmt());
				}
				if(subTotalSett.compareTo(FDCHelper.ZERO)!=0){
				row.getCell(SUB_TOTAL_SETTLE).setValue(subTotalSett);
				}
				row.getCell(SETTLE_ADJUST).setValue(dbd.getSettAdjustAmt());// 结算调整金额
				if (dbd.getApprotionType() != null)
					row.getCell("apportionType").setValue(dbd.getApprotionType().getName());
				DBDynCostSnapShotProTypEntryCollection dbdp = dbd.getProTypEntries();// 产品分录
				if (dbdp != null && dbdp.size() != 0) {
					for (int i = 0; i < this.products.size(); i++) {
						for (int j = 0; j < dbdp.size(); j++) {
							if (dbdp.get(j).getProductType().getId().toString().equals(this.products.get(i).getId().toString())) {
								if (dbdp.get(j) != null) {
									row.getCell(products.get(i).getId().toString() + "Aim" + "SellPart").setValue(dbdp.get(j).getAimSaleUnitAmt());
									row.getCell(products.get(i).getId().toString() + "Aim").setValue(dbdp.get(j).getAimCostAmt());
									row.getCell(products.get(i).getId().toString() + "HasHappen").setValue(dbdp.get(j).getHasHappenAmt());
									row.getCell(products.get(i).getId().toString() + "NoHappen").setValue(dbdp.get(j).getNotHappenAmt());
									row.getCell(products.get(i).getId().toString() + "SellPart").setValue(dbdp.get(j).getDynSaleUnitAmt());
									row.getCell(products.get(i).getId().toString() + "Dynamic").setValue(dbdp.get(j).getDynCostAmt());
								}
							}
						}
					}
				}
			}
		}
		for (int i = 0; i < node.getChildCount(); i++) {
			this.fillNode((DefaultMutableTreeNode) node.getChildAt(i));
		}
	}

	private BigDecimal getSumAdjustCol(String acctId) throws BOSException, SQLException {
		StringBuffer innerSql = new StringBuffer();
		innerSql.append("select fid from " + FDCHelper.getFullAcctSql() + " where ");
		String fullNumber = "";
		CostAccountInfo acct = (CostAccountInfo) this.acctMap.get(acctId);
		if (acct.getCurProject() != null) {
			fullNumber = acct.getCurProject().getFullOrgUnit().getLongNumber() + "!" + acct.getCurProject().getLongNumber();
		} else {
			fullNumber = acct.getFullOrgUnit().getLongNumber();
		}
		String longNumber = acct.getLongNumber();
		innerSql.append(" (FLongNumber ='").append(longNumber).append("'").append(" or FLongNumber like '").append(longNumber).append("!%' ").append(" or FLongNumber like '%!").append(longNumber)
				.append("!%') ");
		innerSql.append("and (FullNumber =	'").append(fullNumber).append("'").append(" or FullNumber like '").append(fullNumber).append("!%' ").append(" or FullNumber like '%!").append(fullNumber)
				.append("!%') ");

		String sql = "select sum(FAdjustSumAmount) sumAmount from T_AIM_DynamicCost where FAccountId in (" + innerSql.toString() + ")";
		IRowSet rs = SQLExecutorFactory.getRemoteInstance(sql).executeSQL();
		BigDecimal adjustAmount = null;
		while (rs.next()) {
			adjustAmount = rs.getBigDecimal("sumAmount");
		}
		if (adjustAmount != null && adjustAmount.compareTo(FDCHelper.ZERO) == 0) {
			return null;
		}
		return adjustAmount;
	}

	private void initDynamicCostData(String objectId) throws BOSException {
		dynamicCostMap.clear();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		BOSObjectType bosType = BOSUuid.read(objectId).getType();
		if (new CurProjectInfo().getBOSType().equals(bosType)) {
			filter.getFilterItems().add(new FilterItemInfo("account.curProject.id", objectId));
		} else {
			filter.getFilterItems().add(new FilterItemInfo("account.fullOrgUnit.id", objectId));
		}
		view.getSelector().add(new SelectorItemInfo("*"));
		DynamicCostCollection DynamicCostCollection = DynamicCostFactory.getRemoteInstance().getDynamicCostCollection(view);
		for (int i = 0; i < DynamicCostCollection.size(); i++) {
			DynamicCostInfo info = DynamicCostCollection.get(i);
			CostAccountInfo acct = info.getAccount();
			dynamicCostMap.put(acct.getId().toString(), info.getAdjustSumAmount());
		}
	}

	private void initAimCostData(String objectId) throws BOSException, SQLException {
		aimCostMap.clear();
		EntityViewInfo aimView = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		aimView.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("orgOrProId", objectId));
		filter.getFilterItems().add(new FilterItemInfo("isLastVersion", new Integer(1)));
		aimView.getSelector().add(new SelectorItemInfo("*"));
		aimView.getSelector().add(new SelectorItemInfo("costEntry.*"));
		AimCostCollection aimCostCollection = AimCostFactory.getRemoteInstance().getAimCostCollection(aimView);
		if (aimCostCollection.size() >= 1) {
			CostEntryCollection costEntrys = aimCostCollection.get(0).getCostEntry();
			for (int i = 0; i < costEntrys.size(); i++) {
				CostEntryInfo entry = costEntrys.get(i);
				CostAccountInfo costAccount = entry.getCostAccount();
				BigDecimal value = entry.getCostAmount();
				if (value == null) {
					value = FDCHelper.ZERO;
				}
				if (aimCostMap.containsKey(costAccount.getId().toString())) {
					BigDecimal sum = (BigDecimal) aimCostMap.get(costAccount.getId().toString());
					aimCostMap.put(costAccount.getId().toString(), sum.add(value));

				} else {
					aimCostMap.put(costAccount.getId().toString(), value);
				}
			}
		}
	}

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

	public void actionChart_actionPerformed(ActionEvent e) throws Exception {
		this.showChart(1);
	}

	/**
	 * 保存全项目动态成本表和各产品类型动态成本表的数据到成本数据库(动态成本快照)
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
	}

	private BigDecimal setScale(BigDecimal value) {
		value = value == null ? null : value.setScale(2, BigDecimal.ROUND_HALF_UP);
		return value;
	}
}