package com.kingdee.eas.fdc.aimcost.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.commons.lang.StringUtils;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.AdjustRecordEntryCollection;
import com.kingdee.eas.fdc.aimcost.AdjustRecordEntryInfo;
import com.kingdee.eas.fdc.aimcost.AimCostSplitDataGetter;
import com.kingdee.eas.fdc.aimcost.CostEntryCollection;
import com.kingdee.eas.fdc.aimcost.CostEntryInfo;
import com.kingdee.eas.fdc.aimcost.DyCostSplitDataGetter;
import com.kingdee.eas.fdc.aimcost.DyProductTypeGetter;
import com.kingdee.eas.fdc.aimcost.DynamicCostFactory;
import com.kingdee.eas.fdc.aimcost.DynamicCostInfo;
import com.kingdee.eas.fdc.aimcost.DynamicCostMap;
import com.kingdee.eas.fdc.aimcost.FDCCostRptFacadeFactory;
import com.kingdee.eas.fdc.aimcost.HappenDataGetter;
import com.kingdee.eas.fdc.aimcost.HappenDataInfo;
import com.kingdee.eas.fdc.aimcost.IntendingCostEntryCollection;
import com.kingdee.eas.fdc.aimcost.IntendingCostEntryInfo;
import com.kingdee.eas.fdc.basedata.AcctAccreditHelper;
import com.kingdee.eas.fdc.basedata.AdjustReasonInfo;
import com.kingdee.eas.fdc.basedata.AdjustTypeCollection;
import com.kingdee.eas.fdc.basedata.AdjustTypeFactory;
import com.kingdee.eas.fdc.basedata.AdjustTypeInfo;
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
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.TimeTools;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;

/**
 * 待发生成本预测 
 */
public class DynamicCostUI extends AbstractDynamicCostUI {

	private AimCostSplitDataGetter aimGetter;

	private DyCostSplitDataGetter dyGetter;

	private HappenDataGetter happenGetter;

	private String objectId = null;

	private CostAccountInfo curAcct = null;

	private DyProductTypeGetter productTypeGetter;
	private BigDecimal nosplitAmt = null;
	private Map acctMap = new HashMap();
	private Map adjustMap = null;

	private boolean canAddNew = false;
	/** 是否允许调整 */
	private boolean isAdjust = false;

	private boolean isCanDelete = false;

	/** 删除的调整记录暂存 */
	private Map adjustDataMap = new HashMap();

	/** 是否级次 */
	private boolean isShowSplitAcct = true;

	/** 当前科目 */
	private CostAccountInfo costaccInfo = null;
	/** 子集集合 */
	private Map acctChidrenMap = new HashMap();

	/** 是否有子节点 */
	private Map isChidrenMap = new HashMap();
	/** 父子节点集合*/
	private Map accChidrenIdMap = new HashMap();
	private Map adjustsMap = null;
	// 是否包含禁用科目
	protected boolean isAll = false;

	public DynamicCostUI() throws Exception {
		super();
	}

	// 数据对象变化时，刷新界面状态
	protected void setActionState() {
	}

	public static final String SETTLE = "Settle";

	public static final String NO_SETTLE = "NoSettle";

	private Map productDyAmount = new HashMap();
	
	
	  /**
	 * 变更类型
	 */
	private ChangeTypeCollection changeTypes;

	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		int curIndex = tblMain.getSelectManager().getActiveRowIndex();
		if (curIndex < 0) {
			return;
		}
		IRow curRow = tblMain.getRow(curIndex);
		this.productDyAmount.put("index", curIndex + "");
		if (e.getClickCount() > 0) {
			setBtnStatus();
			// 点中一行时将产品的动态成本存入缓存
			DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			if (proNode.isLeaf() && this.productTypeGetter != null) {
				String[] productTypeIds = this.productTypeGetter.getProductTypeIds();
				BigDecimal amount = null;
				for (int i = 0; productTypeIds != null && i < productTypeIds.length; i++) {
					String id = productTypeIds[i];
					String key = id + 3;
					amount = (BigDecimal) curRow.getCell(key).getValue();
					productDyAmount.put(key, amount);

				}
			}

		}

		/************ 科目未勾选不能添加删除待发生数据 ****************/
		if (curRow.getUserObject() == null) {
			this.actionAddRow.setEnabled(false);
			this.actionDeleteRow.setEnabled(false);
		}
		/************ 科目未勾选不能添加删除待发生数据 ****************/

		if (e.getClickCount() == 2) {
			int rowIndex = e.getRowIndex();

			// if(getMainTable().getCell(rowIndex, colIndex).getValue() == null)
			// return;

			if (getMainTable().getRow(rowIndex) == null) {
				return;
			}
			Object value = getMainTable().getRow(rowIndex).getUserObject();
			if (value == null)
				return;
			CostAccountInfo acctInfo = (CostAccountInfo) value;

			boolean b = acctInfo.isIsLeaf();
			// 显示出来时有错，暂时不显示非明细工程项目
			if (!b)
				return;
			// boolean prjIsLeaf=acctInfo.getCurProject().isIsLeaf();
			DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			if (proNode == null || !proNode.isLeaf()) {
				return;
			}

			this.setCursorOfWair();
			Map map = new HashMap();

			String acctId = acctInfo.getId().toString();
			map.put("acctId", acctId);

			String projId = getSelectObjId();

			if (projId == null)
				return;

			this.happenGetter = new HappenDataGetter(projId, true, true, false);

			BigDecimal no_set_total = FDCConstants.B0;

			BigDecimal no_set_amt = null;
			HappenDataInfo happenDataInfo = (HappenDataInfo) this.happenGetter.conSplitMap.get(acctId + 0);

			if (happenDataInfo != null) {
				no_set_amt = happenDataInfo.getAmount();

				no_set_total = no_set_total.add(no_set_amt);
			}

			BigDecimal set_amt = null;

			happenDataInfo = (HappenDataInfo) this.happenGetter.conSplitMap.get(acctId + 1);
			if (happenDataInfo != null) {
				set_amt = happenDataInfo.getAmount();
			}

			//变更
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
			view.getSorter().add(new SorterItemInfo("number"));
			view.getSelector().add("id");
			view.getSelector().add("name");
			ChangeTypeCollection changeTypes = ChangeTypeFactory.getRemoteInstance().getChangeTypeCollection(view);
			BigDecimal noSettleChange = FDCHelper.ZERO;
			BigDecimal settleChange = FDCHelper.ZERO;
			for (Iterator iter = changeTypes.iterator(); iter.hasNext();) {
				ChangeTypeInfo info = (ChangeTypeInfo) iter.next();
				String changeId = info.getId().toString();
				happenDataInfo = (HappenDataInfo) this.happenGetter.changeSplitMap.get(acctId + changeId + 1);

				if (happenDataInfo != null) {
					map.put(changeId + "Settle", happenDataInfo.getAmount());
					settleChange = FDCHelper.add(settleChange, happenDataInfo.getAmount());
				}

				happenDataInfo = (HappenDataInfo) this.happenGetter.changeSplitMap.get(acctId + changeId + 0);
				if (happenDataInfo != null) {
					map.put(changeId + "NoSettle", happenDataInfo.getAmount());
					noSettleChange = FDCHelper.add(noSettleChange, happenDataInfo.getAmount());
				}
			}
			no_set_total = FDCHelper.add(no_set_total, noSettleChange);
			BigDecimal set_total = null;
			happenDataInfo = (HappenDataInfo) this.happenGetter.settleSplitMap.get(acctId);
			if (happenDataInfo != null) {
				set_total = happenDataInfo.getAmount();
			}

			BigDecimal no_text = null;
			happenDataInfo = (HappenDataInfo) this.happenGetter.noTextSplitMap.get(acctId);
			if (happenDataInfo != null) {
				no_text = happenDataInfo.getAmount();
			}

			Object will_happen = getMainTable().getRow(rowIndex).getCell("intendingHappen").getValue();

			map.put("no_set_amt", no_set_amt);
			map.put("no_set_total", no_set_total);

			map.put("set_amt", set_amt);
			map.put("set_total", set_total);

			map.put("no_text", no_text);
			map.put("will_happen", will_happen);
			map.put("FullDyDetailInfoTitle", "动态成本信息");

			FullDyDetailInfoUI.showDialogWindows(this, map);
			this.setCursorOfDefault();
		}
		int length = this.tblAdjustRecord.getRowCount();
		productDyAmount.put("rowCount", length + "");
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return CurProjectFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return null;
	}

	protected void initTree() throws Exception {
		this.initMainTable();
		this.initEntryTable(this.tblAdjustRecord);
		this.initEntryTable(this.tblIntendingCost);
		ProjectTreeBuilder treeBuilder = new ProjectTreeBuilder();
		treeBuilder.build(this, this.treeMain, this.actionOnLoad);
		this.treeMain.expandAllNodes(true, (TreeNode) ((TreeModel) this.treeMain.getModel()).getRoot());
	}

	private void initEntryTable(KDTable table) throws BOSException {
		table.checkParsed();
		table.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
		table.getColumn("workload").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getColumn("price").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getColumn("costAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);

		table.getColumn("workload").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		table.getColumn("price").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(8));
		table.getColumn("costAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		// table.getColumn("acctName").getStyleAttributes().setBackground(new
		// Color(0xF0EDD9));
		KDTextField textField = new KDTextField();
		textField.setMaxLength(80);
		ICellEditor txtEditor = new KDTDefaultCellEditor(textField);
		table.getColumn("acctName").setEditor(txtEditor);
		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		// formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		table.getColumn("workload").setEditor(numberEditor);
		textField = new KDTextField();
		textField.setMaxLength(80);
		txtEditor = new KDTDefaultCellEditor(textField);
		table.getColumn("unit").setEditor(txtEditor);

		formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(8);
		// formattedTextField.setNegatived(false);
		formattedTextField.setSupportedEmpty(true);
		numberEditor = new KDTDefaultCellEditor(formattedTextField);
		table.getColumn("price").setEditor(numberEditor);
		FDCHelper.formatTableNumber(table, "price");
		formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		// formattedTextField.setNegatived(false);
		formattedTextField.setRequired(true);
		numberEditor = new KDTDefaultCellEditor(formattedTextField);
		table.getColumn("costAmount").setEditor(numberEditor);
		table.getColumn("costAmount").getStyleAttributes().setBackground(FDCHelper.KDTABLE_SUBTOTAL_BG_COLOR);

		textField = new KDTextField();
		textField.setMaxLength(80);
		txtEditor = new KDTDefaultCellEditor(textField);
		table.getColumn("description").setEditor(txtEditor);
		table.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);

		if (table.getName().equals("tblAdjustRecord")) {
			((KDFormattedTextField) table.getColumn("costAmount").getEditor().getComponent()).setNegatived(true);
			KDDatePicker datePicker = new KDDatePicker();
			KDTDefaultCellEditor dateEditor = new KDTDefaultCellEditor(datePicker);
			table.getColumn("adjustDate").setEditor(dateEditor);
			String formatString = "yyyy-MM-dd";
			table.getColumn("adjustDate").getStyleAttributes().setNumberFormat(formatString);
			table.getColumn("adjustDate").getStyleAttributes().setLocked(true);
			KDComboBox comboAdjustType = new KDComboBox();
			EntityViewInfo adjustTypeView = new EntityViewInfo();
			FilterInfo adjustTypeFilter = new FilterInfo();
			adjustTypeFilter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
			adjustTypeView.setFilter(adjustTypeFilter);
			AdjustTypeCollection adjustTypes = AdjustTypeFactory.getRemoteInstance().getAdjustTypeCollection(adjustTypeView);
			comboAdjustType.addItem(new AdjustTypeInfo());
			for (int i = 0; i < adjustTypes.size(); i++) {
				comboAdjustType.addItem(adjustTypes.get(i));
			}
			comboAdjustType.setSelectedIndex(0);
			ICellEditor f7AdjustTypeEditor = new KDTDefaultCellEditor(comboAdjustType);
			table.getColumn("adjustType").setEditor(f7AdjustTypeEditor);

			KDBizPromptBox f7AdjustReason = new KDBizPromptBox();
			f7AdjustReason.setQueryInfo("com.kingdee.eas.fdc.basedata.app.AdjustReasonQuery");
			EntityViewInfo adjustView = new EntityViewInfo();
			FilterInfo adjustFilter = new FilterInfo();
			adjustFilter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
			adjustView.setFilter(adjustFilter);
			f7AdjustReason.setEntityViewInfo(adjustView);
			f7AdjustReason.setEditable(true);
			f7AdjustReason.setDisplayFormat("$name$");
			f7AdjustReason.setEditFormat("$number$");
			f7AdjustReason.setCommitFormat("$number$");
			ICellEditor f7AdjustReasonEditor = new KDTDefaultCellEditor(f7AdjustReason);
			table.getColumn("adjustReason").setEditor(f7AdjustReasonEditor);
			table.getColumn("objectType").getStyleAttributes().setLocked(true);
		}
		/****************modify by lihaiou, fix for performance ,2013.08.08*********/
		KDBizPromptBox f7Product = new KDBizPromptBox();
		f7Product.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProductTypeQuery");
		//		EntityViewInfo productView = new EntityViewInfo();
		//		FilterInfo productFilter = new FilterInfo();
		//		productFilter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
		//		productFilter.getFilterItems().add(
		//				new FilterItemInfo("id", FDCHelper.getSetByArray(this.productTypeGetter.getProductTypeIds()), CompareType.INCLUDE));
		//		productView.setFilter(productFilter);
		//		f7Product.setEntityViewInfo(productView);
		f7Product.setEditable(true);
		f7Product.setDisplayFormat("$name$");
		f7Product.setEditFormat("$number$");
		f7Product.setCommitFormat("$number$");
		ICellEditor f7Editor = new KDTDefaultCellEditor(f7Product);
		this.tblAdjustRecord.getColumn("product").setEditor(f7Editor);
		this.tblIntendingCost.getColumn("product").setEditor(f7Editor);
		/******************modify end ****************************/
	}

	/**
	 * 设置表格属性
	 * 
	 * @throws BOSException
	 */
	private void initMainTable() throws BOSException {
		tblMain.getStyleAttributes().setLocked(true);
		tblMain.getViewManager().setFreezeView(-1, 2);
		tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		tblMain.getColumn("aimCost").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn("hasHappen").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn("intendingHappen").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn("dynamicCost").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn("aimCost").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn("hasHappen").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn("intendingHappen").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn("dynamicCost").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		tblMain.addHeadRow(1, (IRow) tblMain.getHeadRow(0).clone());
		for (int i = 0; i < tblMain.getColumnCount(); i++) {
			tblMain.getHeadMergeManager().mergeBlock(0, i, 1, i);
		}
	}

	private void setProductTypeColumn() {
		int columnCount = tblMain.getColumnCount();
		for (int i = 0; i < columnCount - 6; i++) {
			tblMain.removeColumn(6);
		}
		// code refactory by lihaiou, 2013.08.07,前面已经判断，这里面再进行判断没有意义
		/*DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		
		if (!proNode.isLeaf()) {
			return;
		}*/
		// modify end
		if (productTypeGetter == null || productTypeGetter.getSortedProductMap().size() == 0) {
			return;
		}

		Map prodcutMap = this.productTypeGetter.getSortedProductMap();
		String[] resName = new String[] { "Aim", "HasHappen", "NoHappen", "Dynamic" };
		int i = 0;
		Set keySet = prodcutMap.keySet();
		for (Iterator iter = keySet.iterator(); iter.hasNext();) {
			String number = (String) iter.next();
			ProductTypeInfo product = (ProductTypeInfo) prodcutMap.get(number);
			// code refactory by lihaiou, 2013.08.07,小于4，让人产生迷惑
			for (int j = 0; j < resName.length; j++) {
				String key = product.getId().toString() + j;
				IColumn col = tblMain.addColumn();
				col.setKey(key);
				col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
				col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
				tblMain.getHeadRow(0).getCell(key).setValue(product.getName());
				tblMain.getHeadRow(1).getCell(key).setValue(AimCostHandler.getResource(resName[j]));
			}
			tblMain.getHeadMergeManager().mergeBlock(0, 6 + i * 4, 0, 6 + 3 + i * 4);
			i++;
		}
		/***********add by lihaiou,modify for performance, 这个一部分需要放在初始化方法中，而不是每次点击都运行这个方法**********/
		//KDBizPromptBox f7Product = new KDBizPromptBox();
		//f7Product.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProductTypeQuery");
		EntityViewInfo productView = new EntityViewInfo();
		FilterInfo productFilter = new FilterInfo();
		productFilter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
		productFilter.getFilterItems().add(
				new FilterItemInfo("id", FDCHelper.getSetByArray(this.productTypeGetter.getProductTypeIds()), CompareType.INCLUDE));
		productView.setFilter(productFilter);
		//		f7Product.setEntityViewInfo(productView);
		//		f7Product.setEditable(true);
		//		f7Product.setDisplayFormat("$name$");
		//		f7Product.setEditFormat("$number$");
		//		f7Product.setCommitFormat("$number$");
		//ICellEditor f7Editor = new KDTDefaultCellEditor(f7Product);
		//this.tblAdjustRecord.getColumn("product").setEditor(f7Editor);
		((KDBizPromptBox) this.tblAdjustRecord.getColumn("product").getEditor().getComponent()).setEntityViewInfo(productView);
		((KDBizPromptBox) this.tblIntendingCost.getColumn("product").getEditor().getComponent()).setEntityViewInfo(productView);
		//this.tblIntendingCost.getColumn("product").setEditor(f7Editor);
		/******************* modify end******************************/
	}

	private boolean isFirstLoad = true;

	//R101103-251待发生成本预测表中“目前待发生明细”需要维护，同时目标成本调整使用调整单
	protected void panelDown_stateChanged(javax.swing.event.ChangeEvent e) throws Exception {
		IRow newRow = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex());
		if (newRow == null) {
			return;
		}
		CostAccountInfo acct = (CostAccountInfo) newRow.getUserObject();
		if (acct == null && this.curAcct == null) {
			return;
		}
		int index = this.tblMain.getSelectManager().getActiveRowIndex();
		if (index == -1) {
			return;
		}
		setBtnStatus();
	}

	private void setBtnStatus() {
		DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		int index = this.tblMain.getSelectManager().getActiveRowIndex();

		IRow row = null;
		CostAccountInfo info = null;
		// 明细控制参数
		boolean b = false;
		// 初始都为false
		this.actionAddRow.setEnabled(false);
		this.actionDeleteRow.setEnabled(false);
		this.actionSubmit.setEnabled(false);
		// 只有当前行为明细才能做添加操作，包括 可拆分时候非明细变为明细
		if (index > -1) {
			row = this.tblMain.getRow(index);
			info = (CostAccountInfo) row.getUserObject();
			// 当前行的对象是否有下级
			if (info != null && ((List) acctChidrenMap.get(info.getId().toString())) != null
					&& ((List) acctChidrenMap.get(info.getId().toString())).size() > 0) {
				b = false;
			} else if (info != null) {
				b = true;
			}
		}

		if (!proNode.isLeaf() || proNode.getChildCount() == 0) {
			this.actionAttachment.setEnabled(true);
		} else {
			return;
		}

		if (canAddNew) {
			this.actionSubmit.setEnabled(false);
			this.actionAddRow.setEnabled(false);
			this.actionDeleteRow.setEnabled(false);
			this.actionRecense.setEnabled(false);
			this.actionRevert.setEnabled(false);
		} else {//受前面参数控制
			if (panelDown.getSelectedComponent() == null) {
				return;
			}
			String ctnName = this.panelDown.getSelectedComponent().getName();
			if ("tblAdjustRecord".equals(ctnName)) {
				if (!isAdjust) {
					this.actionAddRow.setEnabled(false);
					this.actionDeleteRow.setEnabled(false);
					this.actionSubmit.setEnabled(false);
				} else {
					// 明细控制
					if (b) {
						this.actionAddRow.setEnabled(true);
						this.actionDeleteRow.setEnabled(true);
						this.actionSubmit.setEnabled(true);
					}
				}
				if (isCanDelete) {
					// 明细控制
					if (b) {
						actionDeleteRow.setEnabled(true);
						this.actionSubmit.setEnabled(true);
					}
				}
			} else {
				// 明细控制
				if (b) {
					this.actionAddRow.setEnabled(true);
					this.actionDeleteRow.setEnabled(true);
					this.actionSubmit.setEnabled(true);
					if (isCanDelete) {
						actionDeleteRow.setEnabled(true);
						actionSubmit.setEnabled(true);
					} else {
						// actionDeleteRow.setEnabled(false);
					}
				}
			}
		}
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		if (isFirstLoad && (treeMain == null || treeMain.getRowCount() > 1)) {
			isFirstLoad = false;
			return;
		}
		/*
		 * if(treeMain.getUserObject()!=null&&treeMain.getUserObject().equals("old")){
		 * treeMain.setUserObject(null); return; } if(!hasFinish){
		 * MsgBox.showInfo(this,"正在加载数据请稍后再点击"); treeMain.setUserObject("old");
		 * treeMain.setSelectionPath(e.getOldLeadSelectionPath()); return; }
		 * hasFinish=false;
		 */
		final String selectObjId = getSelectObjId();
		if (selectObjId == null) {
			return;
		}
		if (selectObjId.equals(this.objectId)) {
			return;
		}
		DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		// modify by lihaiou, code refacotry ,当选择的是组织单元时，需要进行过滤
		if (!proNode.isLeaf() || proNode.getUserObject() instanceof OrgStructureInfo) {
			tblMain.removeRows();
			this.productTypeGetter = new DyProductTypeGetter(selectObjId);
			setProductTypeColumn();
			this.objectId = selectObjId;
			return;
		}

		tblAdjustRecord.getSelectManager().select(0, 6);
		tblIntendingCost.getSelectManager().select(0, 6);
		if (isEditEntry()) {
			if (MsgBox.showConfirm2(this, AimCostHandler.getResource("NoSave")) == MsgBox.YES) {
				if (!verify()) {
					treeMain.setSelectionPath(e.getOldLeadSelectionPath());
					return;
				}
				CostAccountInfo oldAcct = (CostAccountInfo) tblMain.getRow(tblMain.getSelectManager().getActiveRowIndex()).getUserObject();
				if (oldAcct == null) {
					return;
				}
				try {
					submitByAcct(oldAcct);
				} catch (EASBizException e1) {
					handUIExceptionAndAbort(e1);
				} catch (BOSException e1) {
					handUIExceptionAndAbort(e1);
				}
			}
		}
		fetchData(selectObjId);
		setProductTypeColumn();
		adjustDataMap.clear();
		try {
			fillMainTable();
		} catch (Exception e1) {
			handUIExceptionAndAbort(e1);
		}
		if (tblMain.getRowCount() != 0) {
			tblMain.getSelectManager().select(0, 0);
		}
	}

	public void fillMainTable() throws Exception {
		tblMain.removeRows();
		addTotalRows();
		adjustDataMap = new HashMap();
		this.clearEntrys();
		this.objectId = this.getSelectObjId();

		BOSObjectType bosType = BOSUuid.read(objectId).getType();
		FilterInfo acctFilter = new FilterInfo();
		if (new CurProjectInfo().getBOSType().equals(bosType)) {
			acctFilter.getFilterItems().add(new FilterItemInfo("curProject.id", objectId));
		} else {
			acctFilter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", objectId));
		}
		// 如果选择显示级次
		if (isShowSplitAcct) {
			acctFilter.getFilterItems().add(new FilterItemInfo("isSplit", new Integer(1)));

		}

		// add by lihaiou, 2013.08.07, modify for performance, init all cost
		initAccChildrenMap();
		initAdjustMap();
		String[] productTypeIds = this.productTypeGetter.getProductTypeIds();
		//initisChidrenMap(acctFilter);
		// modify end
		AcctAccreditHelper.handAcctAccreditFilter(null, objectId, acctFilter);
		TreeModel costAcctTree = FDCClientHelper.createDataTree(CostAccountFactory.getRemoteInstance(), acctFilter);
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) costAcctTree.getRoot();

		int maxLevel = root.getDepth();
		this.tblMain.getTreeColumn().setDepth(maxLevel);
		// 使用新的接口
		for (int i = 0; i < root.getChildCount(); i++) {
			fillNode((DefaultMutableTreeNode) root.getChildAt(i), productTypeIds);
		}
		this.setAimUnionData();
		this.setUnionData();
		setTotalData();
		initUserConfig();
	}

	/**
	 * 
	 * 描述：modify for performance, init all cost,不知道initisChidrenMap方法作何用途，成本科目应该只查一次就可以取出当前所需
	 * 不用BOS平台提供的FILTER进行查询，效率比直接用JDBC低
	 * @Author：haiou_li
	 * @CreateTime：2013-8-7
	 */
	private void initAccChildrenMap() {
		// 判断是否是项目id如果是项目id就给用项目查询如果不是就用组织查询
		BOSObjectType bosType = BOSUuid.read(objectId).getType();
		String whereClause = "";
		if (new CurProjectInfo().getBOSType().equals(bosType)) {
			whereClause = " and fcurproject ='" + objectId + "'";
		} else {
			whereClause = " and FFullOrgUnit ='" + objectId + "'";
			whereClause = " and fcurproject is null";
		}

		String sql = "select fid,fisleaf, flongnumber from t_fdc_costaccount where fisenable=1 " + whereClause;
		IRowSet rs = null;
		List collection = new ArrayList();
		try {
			rs = SQLExecutorFactory.getRemoteInstance(sql).executeSQL();

			while (rs.next()) {
				CostAccountInfo info = new CostAccountInfo();
				info.setId(BOSUuid.read(rs.getString("fid")));
				info.setLongNumber(rs.getString("flongnumber"));
				info.setIsLeaf(rs.getString("fisleaf") == "1" ? true : false);
				collection.add(info);
			}
		} catch (BOSException e) {
			handUIException(e);
		} catch (SQLException e) {
			handUIException(e);
		}
		
		Map longNumberMap = new HashMap();
		for (int i = 0; collection.size() > 0 && i < collection.size(); i++) {
			CostAccountInfo info = (CostAccountInfo) collection.get(i);
			String longNumber = (String) longNumberMap.get(info.getId());
			if (StringUtils.isBlank(longNumber)) {
				longNumber = (String) info.getLongNumber();
				longNumberMap.put(info.getId(), longNumber);
			}
			for (int j = 0; j < collection.size(); j++) {
				if (j == i) {
					continue;
				}
				//提高效率getLongNumber()方法的效率较低
				CostAccountInfo accountInfo = (CostAccountInfo) collection.get(j);
				String longNumber2 = (String) longNumberMap.get(accountInfo.getId());
				if (StringUtils.isBlank(longNumber2)) {
					longNumber2 = accountInfo.getLongNumber();
					longNumberMap.put(accountInfo.getId(), longNumber2);
				}
				if (longNumber2.startsWith(longNumber)) {
					List accountList = (List) acctChidrenMap.get(info.getId().toString());
					List accostIDList = (List) accChidrenIdMap.get(info.getId().toString());

					if (accountList == null) {
						accountList = new ArrayList();
						acctChidrenMap.put(info.getId().toString(), accountList);
					}
					if (accostIDList == null) {
						accostIDList = new ArrayList();
						accChidrenIdMap.put(info.getId().toString(), accostIDList);
					}
					accountList.add(accountInfo);
					accostIDList.add(accountInfo.getId().toString());
				}
			}
		}
	}

	/**
	 * @description 根据当前显示的科目将有上级的ID存入缓存，每次刷新都重新装载一次。
	 * @author 陈伟
	 * @createDate 2011-9-5
	 * @param acctFilter
	 * @version EAS7.0
	 * @throws BOSException
	 * @see
	 */
	private void initisChidrenMap(FilterInfo acctFilter) throws BOSException {
		isChidrenMap = new HashMap();

		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("id"));
		view.getSelector().add(new SelectorItemInfo("parent.id"));
		view.getSelector().add(new SelectorItemInfo("longNumber"));
		view.getSelector().add(new SelectorItemInfo("parent.longNumber"));
		view.setFilter(acctFilter);
		CostAccountCollection cion = CostAccountFactory.getRemoteInstance().getCostAccountCollection(view);
		CostAccountInfo info = null;
		for (int i = 0, l = cion.size(); i < l; i++) {
			info = cion.get(i);
			if (info.getParent() == null) {
				continue;
			}
			if (!isChidrenMap.containsKey(info.getParent().getLongNumber())) {
				isChidrenMap.put(info.getParent().getLongNumber(), info.getLongNumber());
			}
		}
	}

	private void addTotalRows() {
		IRow row = tblMain.addRow();
		row.setTreeLevel(0);
		Color totalColor = FDCTableHelper.warnColor;// new Color(0xF0AAA9);
		row.getStyleAttributes().setBackground(totalColor);
		row.getCell("acctNumber").setValue(AimCostHandler.getResource("Total"));
		// AimCostHandler.getResource("ProjectTotal"));
		row = tblMain.addRow();
		row.setTreeLevel(0);
		row.getCell("acctNumber").setValue(AimCostHandler.getResource("SubTotal"));
		row.getStyleAttributes().setBackground(totalColor);
		row.getCell("acctName").setValue(AimCostHandler.getResource("NoSplitTotal"));
		row = tblMain.addRow();
		row.setTreeLevel(0);
		row.getStyleAttributes().setBackground(totalColor);
		row.getCell("acctName").setValue(AimCostHandler.getResource("SplitTotal"));
	}

	public void fillEntryTable() {
		DynamicCostInfo dynamicInfo = this.dyGetter.getDynamicInfo(this.curAcct.getId().toString());
		if (dynamicInfo == null) {
			dynamicInfo = new DynamicCostInfo();
			dynamicInfo.setAccount(curAcct);
			this.dyGetter.updateDynamic(curAcct.getId().toString(), dynamicInfo);
		}
		AdjustRecordEntryCollection adjustEntrys = dynamicInfo.getAdjustEntrys();
		Map adjustMap = new HashMap();
		for (int i = 0; i < adjustEntrys.size(); i++) {
			AdjustRecordEntryInfo adjustEntry = adjustEntrys.get(i);
			if (adjustMap.containsKey(adjustEntry.getAimCostEntryId())) {
				AdjustRecordEntryCollection coll = (AdjustRecordEntryCollection) adjustMap.get(adjustEntry.getAimCostEntryId());
				coll.add(adjustEntry);
			} else {
				AdjustRecordEntryCollection newColl = new AdjustRecordEntryCollection();
				newColl.add(adjustEntry);
				adjustMap.put(adjustEntry.getAimCostEntryId(), newColl);
			}
		}
		this.tblAdjustRecord.setUserObject(adjustEntrys);
		// 添加调整汇总
		IRow sumRow = this.tblAdjustRecord.addRow();
		sumRow.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
		sumRow.getStyleAttributes().setLocked(true);
		sumRow.getCell("objectType").setValue("调整汇总");
		BigDecimal adjustSum = FDCHelper.ZERO;
		CostEntryCollection aimColl = this.aimGetter.getCostEntrys(dynamicInfo.getAccount().getId().toString());
		for (int i = 0; i < aimColl.size(); i++) {
			CostEntryInfo aimEntryInfo = aimColl.get(i);
			addAimCostRow(aimEntryInfo);
			AdjustRecordEntryCollection entrys = (AdjustRecordEntryCollection) adjustMap.get(aimEntryInfo.getId().toString());
			if (entrys != null) {
				for (int j = 0; j < entrys.size(); j++) {
					AdjustRecordEntryInfo entry = entrys.get(j);
					AdjustObjectTypeEnum adjustObject = AdjustObjectTypeEnum.ADJUST;
					addAdjustRow(entry, adjustObject);
					if (entry.getCostAmount() != null) {
						adjustSum = adjustSum.add(entry.getCostAmount());
					}
				}
				adjustMap.remove(aimEntryInfo.getId().toString());
			}
		}
		Collection nullColl = (Collection) adjustMap.values();
		for (Iterator iter = nullColl.iterator(); iter.hasNext();) {
			AdjustRecordEntryCollection adColl = (AdjustRecordEntryCollection) iter.next();
			for (int i = 0; i < adColl.size(); i++) {
				AdjustRecordEntryInfo entry = adColl.get(i);
				AdjustObjectTypeEnum adjustObject = AdjustObjectTypeEnum.NOAIM;
				addAdjustRow(entry, adjustObject);
				if (entry.getCostAmount() != null) {
					adjustSum = adjustSum.add(entry.getCostAmount());
				}
			}
		}
		sumRow.getCell("costAmount").setValue(adjustSum);
		IntendingCostEntryCollection intendingEntrys = dynamicInfo.getIntendingCostEntrys();
		this.tblIntendingCost.setUserObject(intendingEntrys);
		for (int i = 0; i < intendingEntrys.size(); i++) {
			IntendingCostEntryInfo intendingEntry = intendingEntrys.get(i);
			addIntendingRow(intendingEntry);
		}
	}

	private void addIntendingRow(IntendingCostEntryInfo intendingEntry) {
		IRow row = this.tblIntendingCost.addRow();
		row.setUserObject(intendingEntry);
		row.getCell("acctName").setValue(intendingEntry.getCostAcctName());
		BigDecimal workload = intendingEntry.getWorkload();
		row.getCell("workload").setValue(workload);
		row.getCell("unit").setValue(intendingEntry.getUnit());
		BigDecimal price = intendingEntry.getPrice();
		row.getCell("price").setValue(price);
		row.getCell("product").setValue(intendingEntry.getProduct());
		row.getCell("costAmount").setValue(intendingEntry.getCostAmount());
		if (workload != null && price != null) {
			row.getCell("costAmount").getStyleAttributes().setLocked(true);
		}
		row.getCell("description").setValue(intendingEntry.getDescription());
	}

	private void addAimCostRow(CostEntryInfo aimEntryInfo) {
		IRow aimRow = this.tblAdjustRecord.addRow();
		aimRow.getStyleAttributes().setBackground(new Color(0xF0EDD9));
		aimRow.getStyleAttributes().setLocked(true);
		aimRow.getCell("objectType").setValue(AdjustObjectTypeEnum.HASAIM);
		aimRow.getCell("acctName").setValue(aimEntryInfo.getEntryName());
		aimRow.getCell("acctName").setUserObject(aimEntryInfo.getId().toString());
		aimRow.getCell("workload").setValue(aimEntryInfo.getWorkload());
		aimRow.getCell("unit").setValue(aimEntryInfo.getUnit());
		aimRow.getCell("price").setValue(aimEntryInfo.getPrice());
		aimRow.getCell("product").setValue(aimEntryInfo.getProduct());
		aimRow.getCell("adjusterID").setValue(aimEntryInfo.getCreator());
		aimRow.getCell("adjusterName").setValue(aimEntryInfo.getName());
		aimRow.getCell("costAmount").setValue(aimEntryInfo.getCostAmount());
		aimRow.getCell("description").setValue(aimEntryInfo.getDescription());
	}

	private void addAdjustRow(AdjustRecordEntryInfo entry, AdjustObjectTypeEnum adjustObject) {
		IRow row = this.tblAdjustRecord.addRow();
		row.setUserObject(entry);
		if (entry.getId() != null) {
			row.getCell("id").setValue(entry.getId());
			row.getStyleAttributes().setLocked(true);
		}
		row.getCell("objectType").setValue(adjustObject);
		row.getCell("acctName").setValue(entry.getAdjustAcctName());
		row.getCell("adjustType").setValue(entry.getAdjustType());
		row.getCell("adjustReason").setValue(entry.getAdjustReason());
		BigDecimal workload = entry.getWorkload();
		row.getCell("workload").setValue(workload);
		row.getCell("unit").setValue(entry.getUnit());
		BigDecimal price = entry.getPrice();
		row.getCell("price").setValue(price);
		row.getCell("product").setValue(entry.getProduct());
		row.getCell("costAmount").setValue(entry.getCostAmount());
		if (workload != null && price != null) {
			row.getCell("costAmount").getStyleAttributes().setLocked(true);
		}
		row.getCell("adjustDate").setValue(entry.getAdjustDate());

		row.getCell("adjusterID").setValue(entry.getAdjuster());
		row.getCell("adjusterName").setValue(entry.getAdjusterName());

		row.getCell("description").setValue(entry.getDescription());
		row.getCell("fiVouchered").setValue(Boolean.valueOf(entry.isFiVouchered()));
		row.getCell("isBeforeSett").setValue(Boolean.valueOf(entry.isIsBeforeSett()));
	}

	public void setUnionDataChange() {
		KDTable table = this.tblMain;
		List zeroLeverRowList = new ArrayList();
		List amountColumns = new ArrayList();
		amountColumns.add("aimCost");
		amountColumns.add("hasHappen");
		amountColumns.add("intendingHappen");
		amountColumns.add("dynamicCost");
		DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (proNode.isLeaf() && this.productTypeGetter != null) {
			String[] productTypeIds = this.productTypeGetter.getProductTypeIds();
			for (int i = 0; productTypeIds != null && i < productTypeIds.length; i++) {
				String id = productTypeIds[i];
				amountColumns.add(id + 0);
				amountColumns.add(id + 1);
				amountColumns.add(id + 2);
				amountColumns.add(id + 3);
			}
		}
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			if (row.getTreeLevel() == 0) {
				zeroLeverRowList.add(row);
			}
			if (row.getUserObject() == null) {
				// 设置汇总行
				int level = row.getTreeLevel();
				int plevel = 0;
				List rowList = new ArrayList();
				for (int j = i + 1; j < table.getRowCount(); j++) {
					IRow rowAfter = table.getRow(j);
					if (plevel > 0 && plevel <= rowAfter.getTreeLevel()) {
						continue;
					}
					if (rowAfter.getTreeLevel() <= level) {
						break;
					}
					if (rowAfter.getUserObject() != null && row.getCell("aimCost").getUserObject() == null) {
						rowList.add(rowAfter);
					} else if (rowAfter.getCell("aimCost").getUserObject() != null) {
						rowList.add(rowAfter);
						// 成本科目多级改造
						plevel = rowAfter.getTreeLevel() + 1;
					}
				}
				for (int k = 0; k < amountColumns.size(); k++) {
					String colName = (String) amountColumns.get(k);
					BigDecimal amount = FMConstants.ZERO;
					boolean isRed = false;
					for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) rowList.get(rowIndex);
						if (rowAdd.getCell(colName).getStyleAttributes().getFontColor().equals(Color.RED)) {
							isRed = true;
						}
						Object value = rowAdd.getCell(colName).getValue();
						if (value != null) {
							if (value instanceof BigDecimal) {
								amount = amount.add((BigDecimal) value);
							} else if (value instanceof Integer) {
								amount = amount.add(new BigDecimal(((Integer) value).toString()));
							}
						}
					}
					if (isRed) {
						row.getCell(colName).getStyleAttributes().setFontColor(Color.RED);
					} else {
						row.getCell(colName).getStyleAttributes().setFontColor(Color.BLACK);
					}
					if (FDCHelper.toBigDecimal(amount).compareTo(FDCHelper.ZERO) != 0) {
						row.getCell(colName).setValue(amount);
					}
				}
			}
		}

	}

	/**
	 * 设置父科目汇总数
	 * 
	 * @param table
	 * @param amountColumns
	 */
	public void setUnionData() {
		KDTable table = this.tblMain;
		List zeroLeverRowList = new ArrayList();
		List amountColumns = new ArrayList();
		// amountColumns.add("aimCost");//setAimUnionData
		amountColumns.add("hasHappen");
		// amountColumns.add("intendingHappen");
		// amountColumns.add("dynamicCost");
		DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (proNode.isLeaf() && this.productTypeGetter != null) {
			String[] productTypeIds = this.productTypeGetter.getProductTypeIds();
			for (int i = 0; productTypeIds != null && i < productTypeIds.length; i++) {
				String id = productTypeIds[i];
				// amountColumns.add(id + 0);
				amountColumns.add(id + 1);
				amountColumns.add(id + 2);
				// amountColumns.add(id + 3);
			}
		}
		/******************modify by lihaiou,2013.08.08. for performance,改用编码判断是不是顶级节点row.getTreeLevel() 这个方法效率极低***********/
		for (int i = 3; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			
			//CostAccountInfo info = (CostAccountInfo) row.getCell("acctNumber").getUserObject();
			String accNumber = row.getCell("acctNumber").getValue() == null ? "" : row.getCell("acctNumber").getValue().toString();

			if (StringUtils.isBlank(accNumber) || accNumber.indexOf(".") < 0) {
				zeroLeverRowList.add(row);
			}
			if (row.getUserObject() == null) {
				// 设置汇总行
				// add by lihaiou. 2013.08.08.for performance,改用编码判断
				//int level = row.getTreeLevel();
				List rowList = new ArrayList();
				for (int j = i + 1; j < table.getRowCount(); j++) {
					IRow rowAfter = table.getRow(j);
					String accNumber2 = rowAfter.getCell("acctNumber").getValue() == null ? "" : rowAfter.getCell("acctNumber").getValue()
							.toString();
					if (!accNumber2.startsWith(accNumber)) {
						break;
					}
					if (rowAfter.getUserObject() != null) {
						rowList.add(rowAfter);
					}
				}
				/*****************modify end by lihaiou ************************/
				for (int k = 0; k < amountColumns.size(); k++) {
					String colName = (String) amountColumns.get(k);
					BigDecimal amount = FMConstants.ZERO;
					boolean isRed = false;
					for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) rowList.get(rowIndex);
						if (rowAdd.getCell(colName).getStyleAttributes().getFontColor().equals(Color.RED)) {
							isRed = true;
						}
						Object value = rowAdd.getCell(colName).getValue();
						if (value != null) {
							if (value instanceof BigDecimal) {
								amount = amount.add((BigDecimal) value);
							} else if (value instanceof Integer) {
								amount = amount.add(new BigDecimal(value.toString()));
							}
						}
					}
					if (isRed) {
						row.getCell(colName).getStyleAttributes().setFontColor(Color.RED);
					} else {
						row.getCell(colName).getStyleAttributes().setFontColor(Color.BLACK);
					}

					if (FDCHelper.toBigDecimal(amount).compareTo(FDCHelper.ZERO) != 0) {
						row.getCell(colName).setValue(amount);
					}
				}
			}
			row.getCell("intendingHappen").setValue(
					FDCHelper.subtract(row.getCell("dynamicCost").getValue(), row.getCell("hasHappen").getValue()));
			for (int k = 0; k < amountColumns.size(); k++) {
				String colName = (String) amountColumns.get(k);
				BigDecimal amount = FDCHelper.ZERO;
				if (colName.endsWith("2")) {
					amount = FDCHelper.subtract(row.getCell(colName.substring(0, colName.length() - 1) + "3").getValue(), row.getCell(
							colName.substring(0, colName.length() - 1) + "1").getValue());
					row.getCell(colName).setValue(amount);
				}
			}

		}

	}

	/**
	 * 设置父科目汇总数
	 * 
	 * @param table
	 * @param amountColumns
	 */
	public void setAimUnionData() throws Exception {
		KDTable table = this.tblMain;
		List zeroLeverRowList = new ArrayList();
		List amountColumns = new ArrayList();
		amountColumns.add("aimCost");
		amountColumns.add("dynamicCost");
		DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (proNode.isLeaf() && this.productTypeGetter != null) {
			String[] productTypeIds = this.productTypeGetter.getProductTypeIds();
			for (int i = 0; productTypeIds != null && i < productTypeIds.length; i++) {
				String id = productTypeIds[i];
				amountColumns.add(id + 0);
				amountColumns.add(id + 3);
			}
		}
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			if (row.getTreeLevel() == 0) {
				zeroLeverRowList.add(row);
			}
			if (row.getUserObject() == null) {
				// 设置汇总行
				int level = row.getTreeLevel();
				List rowList = new ArrayList();
				// if(row.getCell("aimCost").getUserObject()!=null){
				// rowList.add(row);
				// }
				for (int j = i + 1; j < table.getRowCount(); j++) {
					IRow rowAfter = table.getRow(j);
					if (rowAfter.getTreeLevel() <= level) {
						break;
					}
					if (rowAfter.getUserObject() != null) {
						rowList.add(rowAfter);
					} else if (rowAfter.getCell("aimCost").getUserObject() != null) {
						// 成本科目多级改造
						rowList.add(rowAfter);
					}
				}
				for (int k = 0; k < amountColumns.size(); k++) {
					String colName = (String) amountColumns.get(k);
					BigDecimal amount = FMConstants.ZERO;
					boolean isRed = false;
					for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) rowList.get(rowIndex);
						if (rowAdd.getCell(colName).getStyleAttributes().getFontColor().equals(Color.RED)) {
							isRed = true;
						}
						if (rowAdd.getUserObject() != null) {
							// 处理以前明细的情况，直接相加
							Object value = rowAdd.getCell(colName).getValue();
							if (value != null) {
								if (value instanceof BigDecimal) {
									amount = amount.add((BigDecimal) value);
								} else if (value instanceof Integer) {
									amount = amount.add(new BigDecimal(((Integer) value).toString()));
								}
							}
						} else if (rowAdd.getCell("aimCost").getUserObject() != null) {
							// 处理非明细的情况，不直接取相加，重新算：目标成本+明细的动态调整
							String acctId = (String) rowAdd.getCell("aimCost").getUserObject();
							if (colName.endsWith("dynamicCost")) {
								Object aimCost = rowAdd.getCell(colName).getValue();
								amount = FDCHelper.add(amount, aimCost);
							} else if (colName.endsWith("3")) {
								// 产品上的动态
								AdjustRecordEntryCollection adjusts = null;
								if (proNode.isLeaf()) {
									DynamicCostInfo dynamicCostInfo = this.dyGetter.getDynamicInfo(acctId);
									if (dynamicCostInfo != null) {
										adjusts = dynamicCostInfo.getAdjustEntrys();
									} else {
										adjusts = new AdjustRecordEntryCollection();
									}
								}
								Map productDynamic = this.dyGetter.getDyProductMap(acctId, adjusts, true);
								String productId = colName.substring(0, colName.length() - 1);
								BigDecimal value = (BigDecimal) productDynamic.get(productId);
								if (adjustDataMap != null) {
									BigDecimal adjustAmt = (BigDecimal) adjustDataMap.get(acctId + productId);
									value = FDCHelper.subtract(value, adjustAmt);
								}
								amount = FDCHelper.add(amount, value);
							} else {
								// 非明细目标成本直接加
								Object value = rowAdd.getCell(colName).getValue();
								if (value != null) {
									if (value instanceof BigDecimal) {
										amount = amount.add((BigDecimal) value);
									} else if (value instanceof Integer) {
										amount = amount.add(new BigDecimal(((Integer) value).toString()));
									}
								}
							}

						}
					}
					if (row.getCell("aimCost").getUserObject() != null) {
						/**
						 * 这里相加就导致动态成本翻倍了
						 */
						if (colName.equals("aimCost")) {
							amount = FDCHelper.add(row.getCell(colName).getValue(), amount);
						}
						if (colName.equals("dynamicCost")) {
							amount = FDCHelper.add(row.getCell(colName).getValue(), amount);
						}
						if (colName.endsWith("3")) {
							amount = FDCHelper.add(row.getCell(colName.substring(0, colName.length() - 1) + "0").getValue(), amount);
						}
					}
					if (isRed) {
						row.getCell(colName).getStyleAttributes().setFontColor(Color.RED);
					} else {
						row.getCell(colName).getStyleAttributes().setFontColor(Color.BLACK);
					}
					if (FDCHelper.toBigDecimal(amount).compareTo(FDCHelper.ZERO) != 0) {
						row.getCell(colName).setValue(amount);
					}
				}
			}
		}

	}

	private void fillNode(DefaultMutableTreeNode node, String[] productTypeIds) throws BOSException, SQLException, EASBizException {
		CostAccountInfo costAcct = (CostAccountInfo) node.getUserObject();
		if (costAcct == null) {
			return;
		}
		if (!isAll) {
			if (!costAcct.isIsEnabled()) {
				return;
			}
		}
		IRow row = tblMain.addRow();
		row.setTreeLevel(node.getLevel() - 1);
		row.getCell("acctNumber").setValue(costAcct.getLongNumber().replace('!', '.'));
		//成本科目多级改造
		row.getCell("acctNumber").setUserObject(costAcct);
		row.getCell("acctName").setValue(costAcct.getName());
		DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		costaccInfo = costAcct;

		if (node.isLeaf()) {
			row.setUserObject(costAcct);
			updateAimData(row, costAcct.getId().toString());

			updateHasHappenData(row, costAcct.getId().toString());
			AdjustRecordEntryCollection adjusts = null;
			IntendingCostEntryCollection intendings = null;
			if (proNode.isLeaf()) {
				DynamicCostInfo dynamicCostInfo = this.dyGetter.getDynamicInfo(costAcct.getId().toString());
				if (dynamicCostInfo != null) {
					adjusts = dynamicCostInfo.getAdjustEntrys();
					intendings = dynamicCostInfo.getIntendingCostEntrys();
				} else {
					adjusts = getSumAdjustCol(costAcct);
				}
			}
			if (isShowSplitAcct && !costAcct.isIsLeaf()) {
				adjusts = getSumAdjustCol(costAcct);
			}
			updateDynamicData(row, adjusts);
			updateIntendingData(row, intendings, productTypeIds);
		} else {

			updateAimData(row, costAcct.getId().toString());

			updateHasHappenData(row, costAcct.getId().toString());
			AdjustRecordEntryCollection adjusts = null;
			IntendingCostEntryCollection intendings = null;
			if (proNode.isLeaf()) {
				DynamicCostInfo dynamicCostInfo = this.dyGetter.getDynamicInfo(costAcct.getId().toString());
				if (dynamicCostInfo != null) {
					adjusts = dynamicCostInfo.getAdjustEntrys();
					intendings = dynamicCostInfo.getIntendingCostEntrys();
				}
			} else {
				adjusts = getSumAdjustCol(costAcct);
			}
			updateDynamicData(row, adjusts);
			updateIntendingData(row, intendings, productTypeIds);

		}
		if (!node.isLeaf() || !proNode.isLeaf()) {
			row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
		}
		for (int i = 0; i < node.getChildCount(); i++) {
			this.fillNode((DefaultMutableTreeNode) node.getChildAt(i), productTypeIds);
		}

	}

	/**
	* @description 动态成本, modify by lihaiou, 2013.08.07. modify for performance,<br>
	* <p>性能修改，SQL语句本身执行效率低，同事还循环查数据库<p>
	* <p>一次性查出当前节点的所有项目的成本值<p>
	* @author 陈伟
	* @createDate 2011-9-8
	* @param costAcct
	* @return
	* @throws BOSException
	* @throws SQLException
	* @version EAS7.0
	* @see
	*/
	private AdjustRecordEntryCollection getSumAdjustCol(CostAccountInfo costAcct) throws BOSException, SQLException {
		if (this.adjustMap == null) {
			this.adjustMap = new HashMap();
		}
		if (this.adjustsMap == null) {
			this.adjustsMap = new HashMap();
		}
		if (adjustsMap.containsKey(costAcct.getId().toString())) {
			return (AdjustRecordEntryCollection) adjustsMap.get(costAcct.getId().toString());
		}

		//adjustsMap.put(costAcct.getId().toString(), adjusts);
		return (AdjustRecordEntryCollection) adjustsMap.get(costAcct.getId().toString());
	}

	/**
	 * 描述：对成本数据进行调整一次性进行取数，不进行多次查询
	 * @param costAcct
	 * @throws BOSException
	 * @throws SQLException
	 * @Author：haiou_li
	 * @CreateTime：2013-8-7
	 */
	private void initAdjustMap() throws BOSException, SQLException {
		String fullNumber = "";
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null || node.getUserObject() == null || OrgViewUtils.isTreeNodeDisable(node)) {
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			fullNumber = ((CurProjectInfo) node.getUserObject()).getFullOrgUnit().getLongNumber() + "!"
					+ ((CurProjectInfo) node.getUserObject()).getLongNumber();

		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();

			FullOrgUnitInfo info = oui.getUnit();
			fullNumber = info.getLongNumber();
		}
		AdjustRecordEntryCollection adjusts = new AdjustRecordEntryCollection();
		StringBuffer innerSql = new StringBuffer();
		innerSql.append("select fid from " + FDCHelper.getFullAcctSql() + " where ");

		//		CostAccountInfo acct = (CostAccountInfo) this.acctMap.get(costAcct
		//				.getId().toString());
		//		if (acct.getCurProject() != null) {
		//			fullNumber = acct.getCurProject().getFullOrgUnit().getLongNumber()
		//					+ "!" + acct.getCurProject().getLongNumber();
		//		} else {
		//			fullNumber = acct.getFullOrgUnit().getLongNumber();
		//		}

		//String longNumber = costAcct.getLongNumber();
		// modify by lihaiou,2013.08.07. modify for performance
		//innerSql.append(" (FLongNumber ='").append(longNumber).append("'").append(" or FLongNumber like '").append(longNumber).append("!%' ").append(" or FLongNumber like		 '%!").append(longNumber)
		//.append("!%') and ");

		innerSql.append("(FullNumber =				 '").append(fullNumber).append("'").append(" or FullNumber like '").append(fullNumber).append(
				"!%' ").append(" or FullNumber like '%!").append(fullNumber).append("!%')  And costAcct.isleafProject=1 ");

		String sql = "select FCostAmount,FProductId, FAdjusterID, t_fdc_costaccount.FLongNumber FLongNumber, t_fdc_costaccount.fid fid,t_fdc_costaccount.fparentid fparentid from T_AIM_AdjustRecordEntry inner join T_AIM_DynamicCost "
				+ "on  T_AIM_AdjustRecordEntry.FParentID= T_AIM_DynamicCost.fid "
				+ " left join t_fdc_costaccount on t_fdc_costaccount.fid = T_AIM_DynamicCost.FAccountId where T_AIM_DynamicCost.FAccountId in ("
				+ innerSql.toString() + ")";
		// modify end
		IRowSet rs = SQLExecutorFactory.getRemoteInstance(sql).executeSQL();
		List collection = new ArrayList();
		while (rs.next()) {

			AdjustRecordEntryInfo info = new AdjustRecordEntryInfo();
			info.setCostAmount(rs.getBigDecimal("FCostAmount"));
			//设置FID
			info.setId(BOSUuid.read(rs.getString("fid")));
			if (rs.getString("FProductId") != null) {
				ProductTypeInfo product = new ProductTypeInfo();
				product.setId(BOSUuid.read(rs.getString("FProductId")));
				info.setProduct(product);
			}
			collection.add(info);
		}
		for (int i = 0; i < collection.size(); i++) {
			AdjustRecordEntryInfo info = (AdjustRecordEntryInfo) collection.get(i);
			String fid = info.getId().toString();
			List costAccIds = (List) accChidrenIdMap.get(fid);
			for (int j = 0; j < collection.size(); j++) {
				if (j == i) {
					continue;
				}
				AdjustRecordEntryInfo info2 = (AdjustRecordEntryInfo) collection.get(j);
				String fid2 = info.getId().toString();
				if (costAccIds != null && costAccIds.contains(fid2)) {
					AdjustRecordEntryCollection col = (AdjustRecordEntryCollection) adjustsMap.get(fid);
					if (col == null) {
						col = new AdjustRecordEntryCollection();
						adjustsMap.put(fid, col);
					}
					AdjustRecordEntryInfo info3 = (AdjustRecordEntryInfo) info2.clone();
					info3.setId(null);
					col.add(info3);
				}
			}
		}
	}

	private void updateIntendingData(IRow row, IntendingCostEntryCollection intendingCostEntrys, String[] productTypeIds) {
		BigDecimal intendingSum = FDCHelper.ZERO;
		Map productDynamic = new HashMap();
		if (intendingCostEntrys != null) {
			for (int i = 0; i < intendingCostEntrys.size(); i++) {
				IntendingCostEntryInfo info = intendingCostEntrys.get(i);
				if (info.getCostAmount() != null) {
					if (info.getProduct() != null) {
						String productId = info.getProduct().getId().toString();
						if (productDynamic.containsKey(productId)) {
							BigDecimal value = (BigDecimal) productDynamic.get(productId);
							productDynamic.put(productId, value.add(info.getCostAmount()));
						} else {
							productDynamic.put(productId, info.getCostAmount());
						}
					}
					intendingSum = intendingSum.add(info.getCostAmount());
				}
			}
		}

		BigDecimal dynamicCost = (BigDecimal) row.getCell("dynamicCost").getValue();
		BigDecimal hasHappen = (BigDecimal) row.getCell("hasHappen").getValue();
		BigDecimal intendingHappen = FDCHelper.subtract(dynamicCost, hasHappen);
		if (FDCHelper.toBigDecimal(intendingHappen, 2).compareTo(FDCHelper.ZERO) < 0) {
			row.getCell("intendingHappen").getStyleAttributes().setFontColor(Color.RED);
		} else {
			row.getCell("intendingHappen").getStyleAttributes().setFontColor(Color.BLACK);
		}
		row.getCell("intendingHappen").setValue(intendingHappen);
		// modify by lihaiou,2013.08.07, modify for performance
		/*DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (!proNode.isLeaf()) {
			return;
		}*/
		// modifye end
		/***************下面这句执行效率低下，重复执行很多次，实际上只要有一次就足够，移到fillmaintable方法中***********/
		for (int i = 0; i < productTypeIds.length; i++) {
			String productId = productTypeIds[i];
			// 非空判断 by chenwei
			if (row.getCell(productId + 3) == null) {
				continue;
			}
			dynamicCost = (BigDecimal) row.getCell(productId + 3).getValue();
			hasHappen = (BigDecimal) row.getCell(productId + 1).getValue();

			intendingHappen = FDCHelper.ZERO;
			if (dynamicCost != null) {
				intendingHappen = intendingHappen.add(dynamicCost);
			}
			if (hasHappen != null) {
				intendingHappen = intendingHappen.subtract(hasHappen);
			}
			intendingSum = (BigDecimal) productDynamic.get(productId);
			if (dynamicCost != null || hasHappen != null || intendingSum != null) {
				if (intendingSum == null) {
					intendingSum = FDCHelper.ZERO;
				}

				if (FDCHelper.toBigDecimal(intendingHappen, 2).compareTo(FDCHelper.ZERO) < 0) {
					row.getCell(productId + 2).getStyleAttributes().setFontColor(Color.RED);
				} else {
					row.getCell(productId + 2).getStyleAttributes().setFontColor(Color.BLACK);
				}
				row.getCell(productId + 2).setValue(intendingHappen);
			} else {
				row.getCell(productId + 2).setValue(null);
			}
		}
	}
	
	private void initChangeTypes() throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		//DELETE BY zhiyuan_tang 2010-08-11
		//R100721-063:"待发生成本预测"中的"目前已发生"与"科目合同明细表"中的"目前已发生"数据不一致
		//原因是之前产生过变更数据的变更类型被禁用了，所以取变更类型的时候要把启用和禁用的都取出来
//		filter.getFilterItems().add(
//				new FilterItemInfo("isEnabled", Boolean.TRUE));
		view.getSorter().add(new SorterItemInfo("number"));
		view.getSelector().add("id");
		view.getSelector().add("name");
		this.changeTypes = ChangeTypeFactory.getRemoteInstance()
				.getChangeTypeCollection(view);
	}

	/**
	 * modify by lihaiou，已发生成本取数错误
	 * @param row
	 * @param acctId
	 * @throws BOSException
	 */
	private void updateHasHappenData(IRow row, String acctId) throws BOSException {
		HappenDataInfo happenDataInfo = (HappenDataInfo) this.happenGetter.conSplitMap.get(acctId + 0);
		BigDecimal noSettConAmount = null;
		if (happenDataInfo != null) {
			noSettConAmount = happenDataInfo.getAmount();
		}
		BigDecimal noSettleChangeSumAmount = null;
		for (int i = 0; i < changeTypes.size(); i++) {
			ChangeTypeInfo change = changeTypes.get(i);
			happenDataInfo = (HappenDataInfo) this.happenGetter.changeSplitMap.get(acctId + change.getId().toString() + 0);
			BigDecimal changeAmount = null;
			if (happenDataInfo != null) {
				changeAmount = happenDataInfo.getAmount();
			}
			if (changeAmount != null) {
				if (noSettleChangeSumAmount == null) {
					noSettleChangeSumAmount = FDCHelper.ZERO;
				}
				noSettleChangeSumAmount = noSettleChangeSumAmount.add(changeAmount);
			}
		}
		BigDecimal noSettleTotal = null;
		if (noSettConAmount != null) {
			noSettleTotal = noSettConAmount;
		}
		if (noSettleChangeSumAmount != null) {
			if (noSettleTotal == null) {
				noSettleTotal = noSettleChangeSumAmount;
			} else {
				noSettleTotal = noSettleTotal.add(noSettleChangeSumAmount);
			}
		}
		happenDataInfo = (HappenDataInfo) this.happenGetter.conSplitMap.get(acctId + 1);
		BigDecimal settConAmount = null;
		if (happenDataInfo != null) {
			settConAmount = happenDataInfo.getAmount();
		}
		BigDecimal settleChangeSumAmount = null;
		for (int i = 0; i < changeTypes.size(); i++) {
			ChangeTypeInfo change = changeTypes.get(i);
			happenDataInfo = (HappenDataInfo) this.happenGetter.changeSplitMap.get(acctId + change.getId().toString() + 1);
			BigDecimal changeAmount = null;
			if (happenDataInfo != null) {
				changeAmount = happenDataInfo.getAmount();
			}
			if (changeAmount != null) {
				if (settleChangeSumAmount == null) {
					settleChangeSumAmount = FDCHelper.ZERO;
				}
				settleChangeSumAmount = settleChangeSumAmount.add(changeAmount);
			}
		}
		happenDataInfo = (HappenDataInfo) this.happenGetter.settleSplitMap.get(acctId);
		BigDecimal settleTotal = null;
		if (happenDataInfo != null) {
			settleTotal = happenDataInfo.getAmount();
		}
		BigDecimal settleAdjust = null;
		if (settleTotal != null) {
			settleAdjust = settleTotal;
		}
		if (settConAmount != null) {
			if (settleAdjust == null) {
				settleAdjust = FDCHelper.ZERO;
			}
			settleAdjust = settleAdjust.subtract(settConAmount);
		}
		if (settleChangeSumAmount != null) {
			if (settleAdjust == null) {
				settleAdjust = FDCHelper.ZERO;
			}
			settleAdjust = settleAdjust.subtract(settleChangeSumAmount);
		}
		happenDataInfo = (HappenDataInfo) this.happenGetter.noTextSplitMap.get(acctId);
		BigDecimal noTextAmount = null;
		if (happenDataInfo != null) {
			noTextAmount = happenDataInfo.getAmount();
		}
		BigDecimal hasHappenAmount = null;
		if (noSettleTotal != null) {
			hasHappenAmount = noSettleTotal;
		}
		if (settleTotal != null) {
			if (hasHappenAmount == null) {
				hasHappenAmount = FDCHelper.ZERO;
			}
			hasHappenAmount = hasHappenAmount.add(settleTotal);
		}
		if (noTextAmount != null) {
			if (hasHappenAmount == null) {
				hasHappenAmount = FDCHelper.ZERO;
			}
			hasHappenAmount = hasHappenAmount.add(noTextAmount);
		}
		row.getCell("hasHappen").setValue(hasHappenAmount);
		//		if(FDCHelper.isZero(hasHappen)){
		//			row.getCell("aimCost").setUserObject("hasData");	
		//		}
		// add by lihaiou. code refactory 2013.08.07,此处代码到处都有，没有意义
		/*DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (!proNode.isLeaf()) {
			return;
		}*/
		CostAccountInfo acct = (CostAccountInfo) row.getUserObject();
		if (acct == null) {
			acct = (CostAccountInfo) row.getCell("acctNumber").getUserObject();
		}
		Map happenCalculateData = this.dyGetter.getHasHappenProductMap(acct.getId().toString());
		Set keySet = happenCalculateData.keySet();
		for (Iterator iter = keySet.iterator(); iter.hasNext();) {
			String prodId = (String) iter.next();
			BigDecimal finalAmount = (BigDecimal) happenCalculateData.get(prodId);
			if (row.getCell(prodId + 1) != null) {
				row.getCell(prodId + 1).setValue(finalAmount);
			}
		}
	}

	private void updateAimData(IRow row, String acctId) throws BOSException, SQLException {
		// code refactry lihaiou,2013.08.07.这里判断是否是不是末级节点没有任何意义，一进入treemain_selectchage方法就已经判断是不是末级节点
		//取选中当前节点这条语句执行慢，删除掉
		/*DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();*/
		BigDecimal aimCost = FDCHelper.ZERO;
		//findbugs 不知道怎么改，暂时不改
		//if (proNode.isLeaf()) {
		aimCost = this.aimGetter.getAimCost(acctId);
		//		} else {
		//			aimCost = this.aimGetter.getAimCost(acctId);
		//		}
		row.getCell("aimCost").setValue(aimCost);
		//多级成本管控：用于标识非明细有数据
		if (aimCost.compareTo(FDCHelper.ZERO) != 0) {
			row.getCell("aimCost").setUserObject(acctId);
		}
		/*if (!proNode.isLeaf()) {
			return;
		}*/
		// modify end
		Map productAim = this.aimGetter.getProductMap(acctId);
		Set keySet = productAim.keySet();
		// modify by lihaiou,.2013.08.08. modify for performance ,
		//很明显这个地方之中只设置了一个值，不明白为什么要去循环,只要row.getCell(productId + 0) == null永远不会进行任何操作
		//只有row.getCell(productId + 0) != null时一直进行操作，不停的不覆盖最终只取最后一个ITerator的值，要是中间置空了，就不会执行
		for (Iterator iter = keySet.iterator(); iter.hasNext();) {
			String productId = (String) iter.next();
			BigDecimal value = (BigDecimal) productAim.get(productId);
			if (row.getCell(productId + 0) != null) {

				row.getCell(productId + 0).setValue(value);
			}
		}
	}

	private void updateDynamicData(IRow row, AdjustRecordEntryCollection entrys) {
		if (entrys == null) {
			entrys = new AdjustRecordEntryCollection();
		}
		CostAccountInfo acct = (CostAccountInfo) row.getUserObject();
		if (acct == null) {
			acct = (CostAccountInfo) row.getCell("acctNumber").getUserObject();
		}
		if (acct == null) {
			return;
		}
		BigDecimal aimCost = this.aimGetter.getAimCost(acct.getId().toString());
		// code refactory , modify by lihaiou, fix bug R130719-0193,为什么要在循环里面查数据,	在构建科目树的时候已经将当前项目的所有成本科目查询出来	
		/*CostAccountCollection cion = null;
		try {
			cion = FDCAimcostClientHelper.getCostAccountCollection(this.costaccInfo, this
					.getSelectObjId(), acctChidrenMap);
		} catch (BOSException e1) {
			handUIExceptionAndAbort(e1);
		}*/
		List cion = (List) acctChidrenMap.get(costaccInfo.getId().toString());
		//modify end
		// 将下级集合中的目标成本累计给本级
		CostAccountInfo costinfo = null;
		BigDecimal value = null;
		BigDecimal dyCost = aimCost.add(FDCHelper.ZERO);

		for (int i = 0; i < entrys.size(); i++) {
			AdjustRecordEntryInfo info = entrys.get(i);
			BigDecimal costAmount = info.getCostAmount();
			if (costAmount != null) {

				//当非明细科目，目标没有值，而动态有值时，加入进去
				if (!FDCHelper.isZero(costAmount)) {
					row.getCell("aimCost").setUserObject(acct.getId().toString());
				}
				dyCost = dyCost.add(costAmount);
			}
		}
		//		if(row.getCell("aimCost").getUserObject()!=null){
		//			dyCost=FDCHelper.add(row.getCell("dynamicCost").getValue(), dyCost);
		//		}
		row.getCell("dynamicCost").setValue(dyCost);
		//多级成本管控：用于标识非明细有数据
		//		if(dyCost.compareTo(FDCHelper.ZERO)!=0){
		//			row.getCell("aimCost").setUserObject(acct.getId().toString());
		//		}
		// add by lihaiou, 2013.08.07, code refactory ,此处代码到处判断没有任何意义
		/*DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (!proNode.isLeaf()) {
			return;
		}*/
		// modify end
		try {

			DynamicCostInfo dynamicCostInfo = this.dyGetter.getDynamicInfo(acct.getId().toString());
			if (dynamicCostInfo != null) {
				entrys = dynamicCostInfo.getAdjustEntrys();
			} else {
				entrys = new AdjustRecordEntryCollection(); // getSumAdjustCol(
				// acct);
			}

			// Map productDynamic = this.dyGetter.getDyProductMap(acct.getId()
			// .toString(), entrys);
			//获取最新的产品动态成本信息，包括新录入还未保存的记录
			Map productDynamic = null;
			if (acct.isIsLeaf()) {
				productDynamic = this.dyGetter.getDyProductMap(acct.getId().toString(), entrys, true);
			} else {
				productDynamic = this.dyGetter.getDyProductMap(acct.getId().toString(), entrys, false);
			}

			Set keySet = productDynamic.keySet();
			for (Iterator iter = keySet.iterator(); iter.hasNext();) {
				String productId = (String) iter.next();
				BigDecimal productValue = (BigDecimal) productDynamic.get(productId);

				if (adjustDataMap != null) {
					BigDecimal adjustAmt = (BigDecimal) adjustDataMap.get(acct.getId().toString() + productId);
					value = FDCHelper.subtract(productValue, adjustAmt);
				}
				row.getCell(productId + 3).setValue(value);
			}
			AdjustRecordEntryCollection adjusts = null;
			if (cion != null && cion.size() > 0) {
				BigDecimal values = null;
				for (int i = 0; i < cion.size(); i++) {
					costinfo = (CostAccountInfo) cion.get(i);
					dynamicCostInfo = this.dyGetter.getDynamicInfo(costinfo.getId().toString());
					if (dynamicCostInfo != null) {
						adjusts = dynamicCostInfo.getAdjustEntrys();
					} else {
						adjusts = new AdjustRecordEntryCollection();
					}
					if (costinfo.isIsLeaf()) {
						productDynamic = this.dyGetter.getDyProductMap(costinfo.getId().toString(), adjusts, false);
					} else {
						productDynamic = this.dyGetter.getDyProductMap(costinfo.getId().toString(), adjusts, false);
					}
					keySet = productDynamic.keySet();
					for (Iterator iter = keySet.iterator(); iter.hasNext();) {
						String productId = (String) iter.next();
						BigDecimal productValue = (BigDecimal) productDynamic.get(productId);
						BigDecimal adjustAmt = null;
						if (adjustDataMap != null) {
							adjustAmt = (BigDecimal) adjustDataMap.get(costinfo.getId().toString() + productId);
							adjustAmt = FDCHelper.add((BigDecimal) adjustDataMap.get(acct.getId().toString() + productId), adjustAmt);
						}
						values = FDCHelper.subtract(productValue, adjustAmt);
						values = FDCHelper.add((BigDecimal) row.getCell(productId + 3).getValue(), values);
						row.getCell(productId + 3).setValue(values);
					}
				}

			}
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
	}

	private String getSelectObjId() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null || node.getUserObject() == null || OrgViewUtils.isTreeNodeDisable(node)) {
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

	public void onLoad() throws Exception {
		
		initChangeTypes();
		super.onLoad();
		fetchInitParam();
		initControl();
		FDCClientHelper.addSqlMenu(this, this.menuEdit);

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				int acctNameIndex = tblMain.getColumn("acctName").getColumnIndex() + 1;
				tblMain.getViewManager().freeze(0, acctNameIndex);
				tblMain.setColumnMoveable(true);
			}
		});
		setBtnStatus();
		
		this.isSplitBtn.setIcon(EASResource.getIcon("imgTbtn_execute"));
		this.menuItemCancelCancel.setVisible(false);
		this.menuItemCancel.setVisible(false);
	}

	private void fetchInitParam() throws EASBizException, BOSException {
		// 待发生成本预测数据通过待发生成本预测单进行处理(二次开发)，待发生成本预测功能只允许查询
		canAddNew = FDCUtils.getBooleanValue4FDCParamByKey(null, null, FDCConstants.FDC_PARAM_COSTMEASURE);
		isAdjust = FDCUtils.getBooleanValue4FDCParamByKey(null, null, FDCConstants.FDC_PARAM_AIMCOSTADJUSTINPUT);
		isCanDelete = FDCUtils.getBooleanValue4FDCParamByKey(null, null, FDCConstants.FDC_PARAM_AIMCOSTADJUSTDELETE);
	}

	private void initControl() {
		this.actionAddNew.setVisible(false);
		this.actionAddNew.setEnabled(false);
		this.actionEdit.setVisible(false);
		this.actionEdit.setEnabled(false);
		this.actionRemove.setVisible(false);
		this.actionRemove.setEnabled(false);
		this.actionView.setVisible(false);
		this.actionView.setEnabled(false);
		this.actionQuery.setVisible(false);
		this.actionQuery.setEnabled(false);
		this.actionLocate.setVisible(false);
		this.actionLocate.setEnabled(false);
		this.actionRecense.setVisible(false);
		this.actionRecense.setEnabled(false);
		this.actionAttachment.setVisible(true);

		// 初始都为false
		this.actionAddRow.setEnabled(false);
		this.actionDeleteRow.setEnabled(false);
		this.actionSubmit.setEnabled(false);

		panelDown.removeAll();
		panelDown.add(tblAdjustRecord, resHelper.getString("tblAdjustRecord.constraints"));
		panelDown.add(tblIntendingCost, resHelper.getString("tblIntendingCost.constraints"));

		tblAdjustRecord.getColumn("adjusterName").getStyleAttributes().setLocked(true);
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}

	protected void tblMain_doRequestRowSet(RequestRowSetEvent e) {
	}

	public void actionAddRow_actionPerformed(ActionEvent e) throws Exception {
		if (this.tblMain.getSelectManager().getActiveRowIndex() == -1) {
			return;
		}
		KDTable table = (KDTable) this.panelDown.getSelectedComponent();
		if (table.getName().equals("tblAdjustRecord")) {
			int selectIndex = table.getSelectManager().getActiveRowIndex();
			if (selectIndex == -1) {
				selectIndex = table.getRowCount() - 1;
			}
			if (selectIndex != 0) {
				IRow selectRow = table.getRow(selectIndex);
				IRow row = table.addRow(selectIndex + 1);
				AdjustRecordEntryInfo info = new AdjustRecordEntryInfo();
				String aimEntryId = null;
				if (selectRow.getUserObject() == null) {
					aimEntryId = (String) selectRow.getCell("acctName").getUserObject();
					row.getCell("objectType").setValue(AdjustObjectTypeEnum.ADJUST);
				} else {
					aimEntryId = ((AdjustRecordEntryInfo) selectRow.getUserObject()).getAimCostEntryId();
					row.getCell("objectType").setValue(selectRow.getCell("objectType").getValue());
				}
				info.setAimCostEntryId(aimEntryId);
				row.getCell("acctName").setValue(selectRow.getCell("acctName").getValue());
				row.getCell("product").setValue(selectRow.getCell("product").getValue());
				row.getCell("adjustDate").setValue(new Date());

				//添加调整人
				row.getCell("adjusterID").setValue(SysContext.getSysContext().getCurrentUserInfo());
				// TODO 冗余字段
				row.getCell("adjusterName").setValue(SysContext.getSysContext().getCurrentUserInfo().getName());

				row.getCell("fiVouchered").setValue(Boolean.FALSE);
				row.getCell("isBeforeSett").setValue(Boolean.TRUE);
				info.setId(BOSUuid.create(info.getBOSType()));
				row.setUserObject(info);
			} else {
				IRow row = table.addRow();
				row.getCell("objectType").setValue(AdjustObjectTypeEnum.NOAIM);
				row.getCell("adjustDate").setValue(new Date());

				//添加调整人
				row.getCell("adjusterID").setValue(SysContext.getSysContext().getCurrentUserInfo());
				row.getCell("adjusterName").setValue(SysContext.getSysContext().getCurrentUserInfo().getName());

				row.getCell("fiVouchered").setValue(Boolean.FALSE);
				row.getCell("isBeforeSett").setValue(Boolean.TRUE);
				AdjustRecordEntryInfo info = new AdjustRecordEntryInfo();
				info.setId(BOSUuid.create(info.getBOSType()));
				row.setUserObject(info);
			}
		} else {
			IRow row = table.addRow();
			IntendingCostEntryInfo info = new IntendingCostEntryInfo();
			info.setId(BOSUuid.create(info.getBOSType()));
			row.setUserObject(info);
		}
	}

	public void actionDeleteRow_actionPerformed(ActionEvent e) throws Exception {
		int index = this.tblMain.getSelectManager().getActiveRowIndex();
		if (index == -1) {
			return;
		}
		KDTable table = (KDTable) this.panelDown.getSelectedComponent();
		int rowIndex = table.getSelectManager().getActiveRowIndex();
		if (table.getName().equals("tblAdjustRecord")) {
			IRow row = table.getRow(rowIndex);
			if (row == null) {
				return;
			}
			if (row.getUserObject() == null) {
				return;
			} else if (row.getCell("id").getValue() != null) {
				if (!isCanDelete) {
					return;
				}
				String msg = "删除调整记录将改变科目及产品上的成本数据，是否删除？";
				int ok = MsgBox.showConfirm2(this, msg);
				if (ok != MsgBox.OK) {
					SysUtil.abort();
				}
				IRow mainRow = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex());
				CostAccountInfo acct = (CostAccountInfo) mainRow.getUserObject();
				ProductTypeInfo product = (ProductTypeInfo) row.getCell("product").getValue();

				String productID = null;
				if (product != null) {
					productID = product.getId().toString();
				} else {
					AdjustRecordEntryCollection adjusts = new AdjustRecordEntryCollection();
					AdjustRecordEntryInfo adjust = new AdjustRecordEntryInfo();
					adjust.setCostAmount((BigDecimal) row.getCell("costAmount").getValue());
					//					adjusts.add(adjust);
					Map productDynamic = this.dyGetter.getDyProductMap(acct.getId().toString(), adjusts);
					if (productDynamic.size() == 1) {
						Set keySet = productDynamic.keySet();
						for (Iterator iter = keySet.iterator(); iter.hasNext();) {
							productID = (String) iter.next();
						}
					}
				}
				String key = acct.getId().toString() + productID;
				BigDecimal adjustAmt = (BigDecimal) row.getCell("costAmount").getValue();
				if (adjustDataMap.containsKey(key)) {
					adjustDataMap.put(key, FDCHelper.add(adjustDataMap.get(key), adjustAmt));
				} else {
					adjustDataMap.put(key, adjustAmt);
				}
			}
		}
		table.removeRow(rowIndex);
		this.detailChange();
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		int index = this.tblMain.getSelectManager().getActiveRowIndex();
		if (index == -1) {
			return;
		}
		if (!this.verify()) {
			return;
		}
		IRow selectRow = this.tblMain.getRow(index);
		CostAccountInfo acct = (CostAccountInfo) selectRow.getUserObject();
		if (acct == null) {
			return;
		}
		submitByAcct(acct);
		selectRow.getCell(0).setUserObject("hasSaved");
		this.setMessageText(EASResource.getString(FrameWorkClientUtils.strResource + "Msg_Save_OK"));
		this.showMessage();
		//应万科要求暂时取消，先留待看下一版本是否需要
		//		submitAcctVoucher(acct);
		/*		保存后不加载  by sxhong 2008-04-15 15:28:08	
				this.fillMainTable();
				this.tblMain.getSelectManager().select(index, index);
				this.tblMain.scrollToVisible(index,0);
				clearEntrys();*/
		IRow row = this.tblMain.getRow(index);
		DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (proNode.isLeaf() && row.getUserObject() != null) {
			this.actionAttachment.setEnabled(true);
			this.actionAddRow.setEnabled(true);
			this.actionDeleteRow.setEnabled(true);
			this.actionSubmit.setEnabled(true);
			setBtnStatus();
			this.curAcct = (CostAccountInfo) row.getUserObject();
			//不必要的重载　by sxhong　2008-03-66 10:03:22
			//			this.fillEntryTable();
		}
		adjustDataMap.clear();
		actionRefresh_actionPerformed(e);
	}

	private void submitByAcct(CostAccountInfo acct) throws BOSException, EASBizException {
		DynamicCostInfo info = this.dyGetter.getDynamicInfo(acct.getId().toString());
		if (info == null) {
			info = new DynamicCostInfo();
			info.setId(BOSUuid.create(info.getBOSType()));
			info.setAccount(acct);
			info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
			this.dyGetter.updateDynamic(acct.getId().toString(), info);
		}
		if (info.getId() == null) {
			info.setId(BOSUuid.create(info.getBOSType()));
		}
		info.getAdjustEntrys().clear();
		AdjustRecordEntryCollection adjustCol = getAdjustRecordCollection();
		info.getAdjustEntrys().addCollection(adjustCol);
		BigDecimal adjustAmount = FDCHelper.ZERO;
		for (int i = 0; i < adjustCol.size(); i++) {
			AdjustRecordEntryInfo adjustInfo = adjustCol.get(i);
			adjustAmount = adjustAmount.add(adjustInfo.getCostAmount());
			if (adjustInfo.getId() == null) {
				adjustInfo.setId(BOSUuid.create(adjustInfo.getBOSType()));
			}
			Integer rowNum = (Integer) adjustInfo.get("rows");
			if (rowNum != null) {
				tblAdjustRecord.getRow(rowNum.intValue()).setUserObject(adjustInfo);
			}
		}
		info.setAdjustSumAmount(adjustAmount);

		info.getIntendingCostEntrys().clear();
		IntendingCostEntryCollection intendingCol = getIntendingCollection();
		info.getIntendingCostEntrys().addCollection(intendingCol);
		BigDecimal intendingAmount = FDCHelper.ZERO;
		for (int i = 0; i < intendingCol.size(); i++) {
			IntendingCostEntryInfo intendingInfo = intendingCol.get(i);
			intendingAmount = intendingAmount.add(intendingInfo.getCostAmount());
			if (intendingInfo.getId() == null) {
				intendingInfo.setId(BOSUuid.create(intendingInfo.getBOSType()));
			}
			Integer rowNum = (Integer) intendingInfo.get("rows");
			if (rowNum != null) {
				tblIntendingCost.getRow(rowNum.intValue()).setUserObject(intendingInfo);
			}
		}
		info.setIntendingCostSumAmount(intendingAmount);
		DynamicCostFactory.getRemoteInstance().submit(info);

		// tblAdjustRecord.setUserObject(null);
		// tblIntendingCost.setUserObject(null);
	}

	private IntendingCostEntryCollection getIntendingCollection() {
		IntendingCostEntryCollection intendingCol = new IntendingCostEntryCollection();
		for (int i = 0; i < tblIntendingCost.getRowCount(); i++) {
			IRow row = tblIntendingCost.getRow(i);
			IntendingCostEntryInfo intendingInfo = (IntendingCostEntryInfo) ((IntendingCostEntryInfo) row.getUserObject()).clone();

			intendingInfo.setCostAcctName((String) row.getCell("acctName").getValue());
			intendingInfo.setWorkload((BigDecimal) row.getCell("workload").getValue());
			intendingInfo.setUnit((String) row.getCell("unit").getValue());
			intendingInfo.setPrice((BigDecimal) row.getCell("price").getValue());
			intendingInfo.setProduct((ProductTypeInfo) row.getCell("product").getValue());
			intendingInfo.setDescription((String) row.getCell("description").getValue());
			BigDecimal costAmount = FDCHelper.ZERO;
			if (row.getCell("costAmount").getValue() != null) {
				costAmount = (BigDecimal) row.getCell("costAmount").getValue();
			}
			intendingInfo.setCostAmount(costAmount);
			intendingInfo.put("rows", new Integer(i));
			intendingCol.add(intendingInfo);

		}
		return intendingCol;
	}

	private AdjustRecordEntryCollection getAdjustRecordCollection() {
		AdjustRecordEntryCollection adjustCol = new AdjustRecordEntryCollection();
		for (int i = 0; i < tblAdjustRecord.getRowCount(); i++) {
			IRow row = tblAdjustRecord.getRow(i);
			AdjustRecordEntryInfo adjust = (AdjustRecordEntryInfo) row.getUserObject();
			if (adjust != null) {
				AdjustRecordEntryInfo adjustInfo = (AdjustRecordEntryInfo) adjust.clone();
				adjustInfo.setAdjustAcctName((String) row.getCell("acctName").getValue());
				adjustInfo.setAdjustType((AdjustTypeInfo) row.getCell("adjustType").getValue());
				adjustInfo.setAdjustReason((AdjustReasonInfo) row.getCell("adjustReason").getValue());
				adjustInfo.setWorkload((BigDecimal) row.getCell("workload").getValue());
				adjustInfo.setUnit((String) row.getCell("unit").getValue());
				adjustInfo.setPrice((BigDecimal) row.getCell("price").getValue());
				adjustInfo.setProduct((ProductTypeInfo) row.getCell("product").getValue());
				Date value = (Date) row.getCell("adjustDate").getValue();
				value = DateTimeUtils.truncateDate(value);

				UserInfo adjuster = (UserInfo) row.getCell("adjusterID").getValue();
				if (adjuster == null) {//k3旧数据无此字段，导入出错,增加默认  by hpw 2010.11.9
					adjuster = SysContext.getSysContext().getCurrentUserInfo();
				}
				//添加调整人
				adjustInfo.setAdjuster(adjuster);
				adjustInfo.setAdjusterName(adjuster.getName());

				row.getCell("adjustDate").setValue(value);
				adjustInfo.setAdjustDate(value);
				adjustInfo.setDescription((String) row.getCell("description").getValue());
				adjustInfo.setFiVouchered(((Boolean) row.getCell("fiVouchered").getValue()).booleanValue());
				adjustInfo.setIsBeforeSett(((Boolean) row.getCell("isBeforeSett").getValue()).booleanValue());
				adjustCol.add(adjustInfo);
				BigDecimal costAmount = FDCHelper.ZERO;
				if (row.getCell("costAmount").getValue() != null) {
					costAmount = (BigDecimal) row.getCell("costAmount").getValue();
				}
				adjustInfo.setCostAmount(costAmount.setScale(2, BigDecimal.ROUND_HALF_UP));
				adjustInfo.put("rows", new Integer(i));
			}
		}
		return adjustCol;
	}

	protected void initWorkButton() {
		super.initWorkButton();
		this.btnSubmit.setIcon(EASResource.getIcon("imgTbtn_save"));
		this.btnRecense.setIcon(EASResource.getIcon("imgTbtn_review"));
		this.btnAddRow.setIcon(EASResource.getIcon("imgTbtn_addline"));
		this.btnDeleteRow.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		this.btnExpression.setIcon(EASResource.getIcon("imgTbtn_assistantaccountdetail"));
		this.btnRevert.setIcon(EASResource.getIcon("imgTbtn_restore"));
		setButtonDefaultStyl(this.btnSubmit);
		this.menuItemSubmit.setIcon(EASResource.getIcon("imgTbtn_save"));
		this.menuItemAddRow.setIcon(EASResource.getIcon("imgTbtn_addline"));
		this.menuItemDeleteRow.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		this.menuItemRevert.setIcon(EASResource.getIcon("imgTbtn_restore"));
		this.menuItemExpression.setIcon(EASResource.getIcon("imgTbtn_assistantaccountdetail"));
	}

	/**
	 * 获取query中的主键列名称，返回供编辑/删除时获取主键用，默认值为"id"，继承类可以重载
	 * 
	 */
	protected String getKeyFieldName() {
		return "acctNumber";
	}

	private void refresh() throws Exception {
		int index = this.tblMain.getSelectManager().getActiveRowIndex();
		int curIndex = 0;
		KDTSelectBlock selectBlock = tblMain.getSelectManager().get();

		if (selectBlock != null) {
			curIndex = selectBlock.getTop();
		} else {
			curIndex = 0;
		}
		String prjId = getSelectObjId();
		if (prjId == null) {
			return;
		}
		adjustDataMap.clear();
		// 目前只支持(明细及非明细)工程项目取数 by hpw 2009-11-14
		DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (!(proNode.getUserObject() instanceof CurProjectInfo)) {
			return;
		}
		fetchData(prjId);
		this.fillMainTable();
		if (index == -1) {
			return;
		}
		this.tblMain.getSelectManager().select(index, 0);
		tblMain.getLayoutManager().scrollRowToShow(curIndex);
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception {
		if (e.getSelectBlock().equals(e.getPrevSelectBlock())) {
			return;
		}

		IRow newRow = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex());
		if (newRow == null) {
			return;
		}
		CostAccountInfo acct = (CostAccountInfo) newRow.getUserObject();
		if (acct == null && this.curAcct == null) {
			return;
		}
		if (acct != null && this.curAcct != null) {
			if (acct.getId().toString().equals(this.curAcct.getId().toString())) {
				return;
			}
		}
		int index = this.tblMain.getSelectManager().getActiveRowIndex();
		if (index == -1) {
			return;
		}

		this.productDyAmount.put("index", newRow.getRowIndex() + "");
		if (e.getPrevSelectBlock() != null && this.isEditEntry()) {
			if (MsgBox.showConfirm2(this, AimCostHandler.getResource("NoSave")) == MsgBox.YES) {
				if (!this.verify()) {
					EventListener[] listeners = this.tblMain.getListeners(KDTSelectListener.class);
					for (int i = 0; i < listeners.length; i++) {
						this.tblMain.getListenerList().remove(KDTSelectListener.class, (KDTSelectListener) listeners[i]);
					}
					this.tblMain.getSelectManager().select(e.getPrevSelectBlock());
					for (int i = 0; i < listeners.length; i++) {
						this.tblMain.getListenerList().add(KDTSelectListener.class, (KDTSelectListener) listeners[i]);
					}
					return;
				}
				CostAccountInfo oldAcct = (CostAccountInfo) this.tblMain.getRow(e.getPrevSelectBlock().getTop()).getUserObject();
				if (oldAcct == null) {
					return;
				}
				this.submitByAcct(oldAcct);
				actionRefresh_actionPerformed(null);
			} else {
				this.fillMainTable();
				this.tblMain.getSelectManager().select(index, index);
			}
		}
		clearEntrys();
		IRow row = this.tblMain.getRow(index);
		DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (proNode.isLeaf() && row.getUserObject() != null) {
			this.actionAttachment.setEnabled(true);
			this.actionAddRow.setEnabled(true);
			this.actionDeleteRow.setEnabled(true);
			this.actionSubmit.setEnabled(true);
			setBtnStatus();
			this.curAcct = (CostAccountInfo) row.getUserObject();
			this.fillEntryTable();
		}
	}

	/**
	 * 屏蔽回车键
	 */
	public void actionView_actionPerformed(ActionEvent e) throws Exception {

	}

	private void clearEntrys() {
		this.actionAttachment.setEnabled(false);
		this.actionSubmit.setEnabled(false);
		this.actionAddRow.setEnabled(false);
		this.actionDeleteRow.setEnabled(false);
		this.tblAdjustRecord.removeRows();
		this.tblIntendingCost.removeRows();
		this.tblAdjustRecord.setUserObject(null);
		this.tblIntendingCost.setUserObject(null);
		this.curAcct = null;
	}

	public boolean verify() {
		for (int i = 1; i < this.tblAdjustRecord.getRowCount(); i++) {
			IRow row = this.tblAdjustRecord.getRow(i);
			if (row.getCell("costAmount").getValue() == null) {
				this.panelDown.setSelectedComponent(tblAdjustRecord);
				this.setMessageText(AimCostHandler.getResource("AmountNull"));
				this.showMessage();
				tblAdjustRecord.getSelectManager().select(0, 6);
				tblAdjustRecord.getSelectManager().select(row.getRowIndex(), 7);
				return false;
			} else {
				BigDecimal value = (BigDecimal) row.getCell("costAmount").getValue();
				if (value.compareTo(FDCHelper.ZERO) == 0) {
					this.panelDown.setSelectedComponent(tblAdjustRecord);
					this.setMessageText(AimCostHandler.getResource("AmountNull"));
					this.showMessage();
					tblAdjustRecord.getSelectManager().select(0, 6);
					tblAdjustRecord.getSelectManager().select(row.getRowIndex(), 7);
					return false;
				}
				if (value.compareTo(FDCHelper.MAX_VALUE) > 0) {
					this.panelDown.setSelectedComponent(tblAdjustRecord);
					this.setMessageText(AimCostHandler.getResource("MaxValue"));
					this.showMessage();
					tblAdjustRecord.getSelectManager().select(0, 6);
					tblAdjustRecord.getSelectManager().select(row.getRowIndex(), 7);
					return false;
				}
			}
		}
		for (int i = 0; i < this.tblIntendingCost.getRowCount(); i++) {
			IRow row = this.tblIntendingCost.getRow(i);
			if (row.getCell("costAmount").getValue() == null) {
				this.panelDown.setSelectedComponent(tblIntendingCost);
				this.setMessageText(AimCostHandler.getResource("AmountNull"));
				this.showMessage();
				tblIntendingCost.getSelectManager().select(0, 6);
				tblIntendingCost.getSelectManager().select(row.getRowIndex(), 4);
				return false;
			} else {
				BigDecimal value = (BigDecimal) row.getCell("costAmount").getValue();
				if (value.compareTo(FDCHelper.ZERO) == 0) {
					this.panelDown.setSelectedComponent(tblIntendingCost);
					this.setMessageText(AimCostHandler.getResource("AmountNull"));
					this.showMessage();
					tblIntendingCost.getSelectManager().select(0, 6);
					tblIntendingCost.getSelectManager().select(row.getRowIndex(), 4);
					return false;
				}
				if (value.compareTo(FDCHelper.MAX_VALUE) > 0) {
					this.panelDown.setSelectedComponent(tblIntendingCost);
					this.setMessageText(AimCostHandler.getResource("MaxValue"));
					this.showMessage();
					tblIntendingCost.getSelectManager().select(0, 6);
					tblIntendingCost.getSelectManager().select(row.getRowIndex(), 4);
					return false;
				}
			}
		}
		return true;
	}

	protected void tblAdjustRecord_editStopped(KDTEditEvent e) throws Exception {
		super.tblAdjustRecord_editStopped(e);
		int rowIndex = e.getRowIndex();
		int columnIndex = e.getColIndex();
		IRow row = tblAdjustRecord.getRow(rowIndex);
		if (tblAdjustRecord.getColumnKey(columnIndex).equals("workload") || tblAdjustRecord.getColumnKey(columnIndex).equals("price")) {
			BigDecimal workload = (BigDecimal) row.getCell("workload").getValue();
			BigDecimal price = (BigDecimal) row.getCell("price").getValue();
			if (workload == null) {
				workload = FDCHelper.ZERO;
			}
			if (price == null) {
				price = FDCHelper.ZERO;
			}
			if (workload.compareTo(FDCHelper.ZERO) == 0 && price.compareTo(FDCHelper.ZERO) == 0) {
				row.getCell("costAmount").getStyleAttributes().setLocked(false);
				row.getCell("workload").setValue(null);
				row.getCell("price").setValue(null);
			} else {
				BigDecimal aimCost = workload.multiply(price);
				row.getCell("costAmount").setValue(aimCost.setScale(2, BigDecimal.ROUND_HALF_UP));
				row.getCell("costAmount").getStyleAttributes().setLocked(true);
			}
		}
		if (tblAdjustRecord.getColumnKey(columnIndex).equals("workload") || tblAdjustRecord.getColumnKey(columnIndex).equals("price")
				|| tblAdjustRecord.getColumnKey(columnIndex).equals("costAmount")
				|| tblAdjustRecord.getColumnKey(columnIndex).equals("product")) {
			detailChange();
		}
	}

	private void detailChange() throws Exception {
		int index = -1;
		if ((String) this.productDyAmount.get("index") == null) {

			index = this.tblMain.getSelectManager().getActiveRowIndex();
		} else {

			index = Integer.parseInt((String) this.productDyAmount.get("index"));
		}
		IRow mainRow = this.tblMain.getRow(index);
		AdjustRecordEntryCollection adjusts = this.getAdjustRecordCollection();
		this.updateDynamicData(mainRow, adjusts);
		BigDecimal adjustSum = FDCHelper.ZERO;
		for (int i = 0; i < adjusts.size(); i++) {
			AdjustRecordEntryInfo adjust = adjusts.get(i);
			BigDecimal costAmount = adjust.getCostAmount();
			if (costAmount != null) {
				adjustSum = adjustSum.add(costAmount);
			}
		}
		this.tblAdjustRecord.getRow(0).getCell("costAmount").setValue(adjustSum);
		// 重新将主表当前行赋值
		IRow row = null;
		BigDecimal costAmount = null;
		BigDecimal amount = null;

		String length = (String) productDyAmount.get("rowCount");

		for (int i = Integer.parseInt(length); i < this.tblAdjustRecord.getRowCount(); i++) {
			row = this.tblAdjustRecord.getRow(i);
			costAmount = (BigDecimal) row.getCell("costAmount").getValue();
			ProductTypeInfo products = (ProductTypeInfo) row.getCell("product").getValue();
			if (products != null) {
				if (productDyAmount.get(products.getId().toString() + 3) != null && amount == null) {
					amount = (BigDecimal) productDyAmount.get(products.getId().toString() + 3);

				} else if (productDyAmount.get(products.getId().toString() + 3) == null) {
					amount = null;
				} else {
					amount = (BigDecimal) mainRow.getCell(products.getId().toString() + 3).getValue();
				}
				// 赋值动态成本和已发生
				costAmount = FDCHelper.add(costAmount, amount);
				mainRow.getCell(products.getId().toString() + 3).setValue(costAmount);
				mainRow.getCell(products.getId().toString() + 2).setValue(
						FDCHelper.subtract(costAmount, mainRow.getCell(products.getId().toString() + 1).getValue()));

			}
			mainRow.getCell("intendingHappen").setValue(
					FDCHelper.subtract(mainRow.getCell("dynamicCost").getValue(), mainRow.getCell("hasHappen").getValue()));
		}
		// this.updateIntendingData(mainRow, this.getIntendingCollection());
		this.setAimUnionData();
		//		this.setUnionDataChange();
		setUnionData();
		setTotalData();
	}

	protected void tblIntendingCost_editStopped(KDTEditEvent e) throws Exception {
		super.tblIntendingCost_editStopped(e);
		int rowIndex = e.getRowIndex();
		int columnIndex = e.getColIndex();
		IRow row = tblIntendingCost.getRow(rowIndex);
		if (tblIntendingCost.getColumnKey(columnIndex).equals("workload") || tblIntendingCost.getColumnKey(columnIndex).equals("price")) {
			BigDecimal workload = (BigDecimal) row.getCell("workload").getValue();
			BigDecimal price = (BigDecimal) row.getCell("price").getValue();
			if (workload == null) {
				workload = FDCHelper.ZERO;
			}
			if (price == null) {
				price = FDCHelper.ZERO;
			}
			if (workload.compareTo(FDCHelper.ZERO) == 0 && price.compareTo(FDCHelper.ZERO) == 0) {
				row.getCell("costAmount").getStyleAttributes().setLocked(false);
				row.getCell("workload").setValue(null);
				row.getCell("price").setValue(null);
			} else {
				BigDecimal aimCost = workload.multiply(price);
				row.getCell("costAmount").setValue(aimCost);
				row.getCell("costAmount").getStyleAttributes().setLocked(true);
			}
		}
		if (tblIntendingCost.getColumnKey(columnIndex).equals("workload") || tblIntendingCost.getColumnKey(columnIndex).equals("price")
				|| tblIntendingCost.getColumnKey(columnIndex).equals("costAmount")
				|| tblIntendingCost.getColumnKey(columnIndex).equals("product")) {
			this.detailChange();
		}

	}

	public boolean isEditEntry() {
		this.tblAdjustRecord.getSelectManager().select(0, 0);
		this.tblIntendingCost.getSelectManager().select(0, 0);
		AdjustRecordEntryCollection adjCol = (AdjustRecordEntryCollection) this.tblAdjustRecord.getUserObject();
		IntendingCostEntryCollection intendCol = (IntendingCostEntryCollection) this.tblIntendingCost.getUserObject();
		if (adjCol == null || intendCol == null) {
			return false;
		}
		int count = 0;
		for (int i = 0; i < this.tblAdjustRecord.getRowCount(); i++) {
			if (this.tblAdjustRecord.getRow(i).getUserObject() != null) {
				count++;
			}
		}
		if (adjCol.size() != count) {
			return true;
		}
		for (int i = 0; i < this.tblAdjustRecord.getRowCount(); i++) {
			if (this.tblAdjustRecord.getRow(i).getUserObject() != null) {
				IRow row = this.tblAdjustRecord.getRow(i);
				AdjustRecordEntryInfo info = (AdjustRecordEntryInfo) row.getUserObject();
				if (info.getId() == null) {
					return true;
				}
				String[] compareyKeys = new String[] { "adjustAcctName", "adjustType", "adjustReason", "workload", "unit", "price",
						"costAmount", "product", "adjustDate", "description" };
				String[] columnKeys = new String[] { "acctName", "adjustType", "adjustReason", "workload", "unit", "price", "costAmount",
						"product", "adjustDate", "description" };
				for (int j = 0; j < compareyKeys.length; j++) {
					if (!FDCHelper.isEqual(info.get(compareyKeys[j]), row.getCell(columnKeys[j]).getValue())) {
						return true;
					}
				}
			}
		}
		if (intendCol.size() != this.tblIntendingCost.getRowCount()) {
			return true;
		}
		for (int i = 0; i < this.tblIntendingCost.getRowCount(); i++) {
			IRow row = this.tblIntendingCost.getRow(i);
			IntendingCostEntryInfo info = (IntendingCostEntryInfo) row.getUserObject();
			if (info.getId() == null) {
				return true;
			}
			String[] compareyKeys = new String[] { "costAcctName", "workload", "unit", "price", "costAmount", "product", "description" };
			String[] columnKeys = new String[] { "acctName", "workload", "unit", "price", "costAmount", "product", "description" };
			for (int j = 0; j < compareyKeys.length; j++) {
				if (!FDCHelper.isEqual(info.get(compareyKeys[j]), row.getCell(columnKeys[j]).getValue())) {
					return true;
				}
			}
		}
		return false;
	}

	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception {
		DynamicCostInfo dynamicInfo = this.dyGetter.getDynamicInfo(this.curAcct.getId().toString());
		if (dynamicInfo == null || dynamicInfo.getId() == null) {
			MsgBox.showInfo(AimCostHandler.getResource("NoSaveNotAttach"));
			return;
		}
		String boID = dynamicInfo.getId().toString();
		AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
		if (boID == null) {
			return;
		}
		acm.showAttachmentListUIByBoID(boID, this);
	}

	public void actionExpression_actionPerformed(ActionEvent e) throws Exception {
		KDTable table = (KDTable) this.panelDown.getSelectedComponent();
		int rowIndex = table.getSelectManager().getActiveRowIndex();
		int colIndex = table.getSelectManager().getActiveColumnIndex();
		if (rowIndex == -1) {
			return;
		}
		if (table.getColumn(colIndex).getEditor() != null
				&& table.getColumn(colIndex).getEditor().getComponent() instanceof KDFormattedTextField) {
			ICell cell = table.getRow(rowIndex).getCell(colIndex);
			if (cell.getStyleAttributes().isLocked()) {
				return;
			}
			String expression = (String) cell.getUserObject();
			if (expression == null) {
				if (cell.getValue() != null && cell.getValue() instanceof BigDecimal) {
					expression = cell.getValue().toString();
				}
			}
			String re = ExpressionUI.showExpressionUI(expression);
			if (re == null || re.length() == 0) {
				return;
			}
			BigDecimal result = null;
			try {
				result = new BigDecimal(FDCHelper.getScriptResult(re).toString());
			} catch (Exception e1) {
				MsgBox.showError(AimCostHandler.getResource("ExpressionErrer"));
				handUIExceptionAndAbort(e1);
			}
			if (result != null) {
				cell.setUserObject(re);
				cell.setValue(result);
				KDTEditEvent editEvent = new KDTEditEvent(cell);
				editEvent.setRowIndex(rowIndex);
				editEvent.setColIndex(colIndex);
				if (table.getName().equals("tblAdjustRecord")) {
					this.tblAdjustRecord_editStopped(editEvent);
				} else {
					this.tblIntendingCost_editStopped(editEvent);
				}
			}
		}
	}

	public void actionRevert_actionPerformed(ActionEvent e) throws Exception {
		super.actionRevert_actionPerformed(e);
		this.refresh();
	}

	public void actionAll_actionPerformed(ActionEvent e) throws Exception {

		isAll = true;
		this.actionRefresh_actionPerformed(e);
	}

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		adjustsMap = new HashMap();
		this.refresh();
	}

	public boolean destroyWindow() {

		// 按需求改成有三个按钮的提示对话框 Jianxing_zhou 2007-8-20

		if (this.isEditEntry()) {
			int option = MsgBox.showConfirm3(this, AimCostHandler.getResource("NoSave"));
			if (option == MsgBox.CANCEL) {
				return false;
			} else if (option == MsgBox.YES) {
				if (!this.verify()) {
					abort();
				}
				btnSubmit.doClick();
			}
		}
		return super.destroyWindow();

	}

	private void setTotalData() {
		/*
		 * 已拆分成本小计行＝开发成本 合计行＝合同成本（已提交状态） 未拆分成本小计＝合计－已拆分小计 //有误 合计=未拆分成本小计－已拆分小计
		 */
		// 已发生
		String id = getSelectObjId();
		if (id == null || !BOSUuid.read(id).getType().equals(new CurProjectInfo().getBOSType())) {
			return;
		}
		IRow rowTotal = tblMain.getRow(0);
		IRow rowNoSplit = tblMain.getRow(1);
		IRow rowSplit = tblMain.getRow(2);
		List rootRowList = new ArrayList();
		for (int i = 3; i < tblMain.getRowCount(); i++) {
			final IRow row = tblMain.getRow(i);
			if (row.getTreeLevel() == 0) {
				rootRowList.add(row);
			}
		}
		//没有成本科目的情况
		if (rootRowList.size() <= 0) {
			return;
		}
		BigDecimal aimCost = FDCHelper.ZERO;
		BigDecimal dynamicCost = FDCHelper.ZERO;
		BigDecimal hasHappen = FDCHelper.ZERO;
		BigDecimal intendingHappen = FDCHelper.ZERO;
		for (int i = 0; i < rootRowList.size(); i++) {
			final IRow row = (IRow) rootRowList.get(i);
			aimCost = aimCost.add(FDCHelper.toBigDecimal(row.getCell("aimCost").getValue()));
			hasHappen = hasHappen.add(FDCHelper.toBigDecimal(row.getCell("hasHappen").getValue()));
			intendingHappen = intendingHappen.add(FDCHelper.toBigDecimal(row.getCell("intendingHappen").getValue()));
			dynamicCost = dynamicCost.add(FDCHelper.toBigDecimal(row.getCell("dynamicCost").getValue()));
		}

		rowSplit.getCell("aimCost").setValue(aimCost);
		rowSplit.getCell("hasHappen").setValue(hasHappen);
		rowSplit.getCell("intendingHappen").setValue(intendingHappen);
		rowSplit.getCell("dynamicCost").setValue(dynamicCost);

		// 合计
		rowTotal.getCell("aimCost").setValue(aimCost);
		rowTotal.getCell("dynamicCost").setValue(dynamicCost);

		BigDecimal happenAmt = nosplitAmt.add(hasHappen);
		rowNoSplit.getCell("hasHappen").setValue(nosplitAmt);
		rowNoSplit.getCell("intendingHappen").setValue(nosplitAmt.negate());
		rowTotal.getCell("hasHappen").setValue(happenAmt);
		BigDecimal tempAmt = intendingHappen.add(nosplitAmt.negate());
		rowTotal.getCell("intendingHappen").setValue(tempAmt);
	}

	private void fetchData(String prjId) throws EASBizException, BOSException {
		// modify by lihaiou,2013.08.08. modify for performance,只有末级几天才能进来查数据, 关键是treemain.getLastSelectPathComponent很慢
		/*DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
		// modify by lihaiou, 2013.08.07 code refactory ,应该在点击之后就进行判断
		/*if(!(proNode.getUserObject() instanceof CurProjectInfo)){
			return;
		}*/
		
		TimeTools.getInstance().msValuePrintln("start fetchData");
		//当前期间在后台数据类的时候根本没有用到，而且获取期间非常慢，故此注释掉, modify by lihaiou,2013.08.08. modify for performance
		//获取当前期间
		//PeriodInfo curPeriod = FDCUtils.getCurrentPeriod(null, prjId, true);
		PeriodInfo curPeriod = null;
		//取当前期间数据
		final DynamicCostMap dynamicCostMap = FDCCostRptFacadeFactory.getRemoteInstance().getDynamicCost(prjId, curPeriod, true);
		// modify by lihaiou. end 
		if (dynamicCostMap == null) {
			return;
		}
		this.productTypeGetter = dynamicCostMap.getDyProductTypeGetter();
		this.dyGetter = dynamicCostMap.getDyCostSplitDataGetter();
		this.aimGetter = dynamicCostMap.getAimCostSplitDataGetter();
		this.happenGetter = dynamicCostMap.getHappenDataGetter();
		this.nosplitAmt = FDCHelper.toBigDecimal(dynamicCostMap.getNoSplitAmt());
		this.acctMap = dynamicCostMap.getAcctMap();
		this.adjustMap = dynamicCostMap.getAdjustMap();
		TimeTools.getInstance().msValuePrintln("end fetchData");
	}

	/**
	 * @description 显示可拆分科目事件
	 * @author 陈伟
	 * @createDate 2011-8-3
	 * @param e
	 * @throws Exception
	 * @version EAS7.0
	 * @see com.kingdee.eas.fdc.aimcost.client.AbstractDynamicCostUI#actionSplit_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionSplit_actionPerformed(ActionEvent e) throws Exception {
		// 当前按钮显示内容
		String txt = this.btnSplit.getText();
		if (txt.equals(FDCAimcostClientHelper.getRes("showSplit"))) {
			this.btnSplit.setText(FDCAimcostClientHelper.getRes("showAll"));
			this.isSplitBtn.setText(FDCAimcostClientHelper.getRes("showAll"));
			isShowSplitAcct = true;
		} else {
			this.btnSplit.setText(FDCAimcostClientHelper.getRes("showSplit"));
			this.isSplitBtn.setText(FDCAimcostClientHelper.getRes("showSplit"));
			isShowSplitAcct = false;
		}
		refresh();
		this.tblMain.getSelectManager().set(1, 1);
	}
}