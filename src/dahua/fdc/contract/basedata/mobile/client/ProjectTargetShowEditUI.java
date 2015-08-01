/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.mobile.client;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.FocusTraversalPolicy;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.CharUtils;
import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.ISortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.IKDTextComponent;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.UIFocusTraversalPolicy;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.TargetGroupEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.client.FdcEditorHelper;
import com.kingdee.eas.fdc.basedata.client.FdcF7InitHelper;
import com.kingdee.eas.fdc.basedata.client.FdcNameRender;
import com.kingdee.eas.fdc.basedata.mobile.FdcMobileEntryInfo;
import com.kingdee.eas.fdc.basedata.mobile.ProjectTargetEntryCollection;
import com.kingdee.eas.fdc.basedata.mobile.ProjectTargetEntryInfo;
import com.kingdee.eas.fdc.basedata.mobile.ProjectTargetShowBillFactory;
import com.kingdee.eas.fdc.basedata.mobile.ProjectTargetShowBillInfo;
import com.kingdee.eas.fdc.basedata.mobile.ProjectTargetShowEntryCollection;
import com.kingdee.eas.fdc.basedata.mobile.ProjectTargetShowEntryInfo;
import com.kingdee.eas.fdc.basedata.mobile.ProjectTargetShowItemCollection;
import com.kingdee.eas.fdc.basedata.mobile.ProjectTargetShowItemInfo;
import com.kingdee.eas.fdc.basedata.mobile.ProjectTargetShowUtil;
import com.kingdee.eas.fdc.basedata.util.FdcCollectionUtil;
import com.kingdee.eas.fdc.basedata.util.FdcColumnName;
import com.kingdee.eas.fdc.basedata.util.FdcMapUtil;
import com.kingdee.eas.fdc.basedata.util.FdcObjectCollectionUtil;
import com.kingdee.eas.fdc.basedata.util.FdcObjectUtil;
import com.kingdee.eas.fdc.basedata.util.FdcStringUtil;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ProjectTargetShowEditUI extends AbstractProjectTargetShowEditUI {
	private static final Logger logger = CoreUIObject.getLogger(ProjectTargetShowEditUI.class);

	// ////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////

	protected static final String COL_ENTRY_ID = "id";
	protected static final String COL_ENTRY_SEQ_NUM = "seqNum";
	protected static final String COL_ENTRY_NUMBER = "number";
	protected static final String COL_ENTRY_NAME = "name";

	protected static final String COL_ITEM_ID = "id";
	protected static final String COL_ITEM_SEQ_NUM = "seqNum";
	protected static final String COL_ITEM_NUMBER = "number";
	protected static final String COL_ITEM_NAME = "name";
	protected static final String COL_ITEM_PROJECT_TARGET_ENTRY = "projectTargetEntry";
	protected static final String COL_ITEM_BILL_ID = "bill.id";
	protected static final String COL_ITEM_PARENT_ID = "parent.id";

	// ////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////

	// 旧值Map
	private Map<String, ProjectTargetShowEntryInfo> oldEntryValueMap = new LinkedHashMap<String, ProjectTargetShowEntryInfo>();
	// 新值Map
	private Map<String, ProjectTargetShowEntryInfo> newEntryValueMap = new LinkedHashMap<String, ProjectTargetShowEntryInfo>();

	// 旧值Map
	private Map<String, ProjectTargetShowItemInfo> oldItemValueMap = new LinkedHashMap<String, ProjectTargetShowItemInfo>();
	// 新值Map
	private Map<String, ProjectTargetShowItemInfo> newItemValueMap = new LinkedHashMap<String, ProjectTargetShowItemInfo>();

	// 是否已经改变
	private boolean isModify = false;
	
	// 是否已经初始化表格
	private boolean isInitTable = false;

	// 是否重新取数
	private boolean isReGetData = false;

	// ////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////

	// 分录集合
	protected ProjectTargetShowEntryCollection entryClos;

	// 细目集合映射
	protected Map<String, ProjectTargetShowItemCollection> itemMap;

	// ////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////

	/**
	 * output class constructor
	 */
	public ProjectTargetShowEditUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();

		 // 绑定数据
		bindingData();
	}

	/**
	 * 描述：绑定数据
	 * 
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-2
	 */
	private void bindingData() {
		editData.setNumber(txtNumber.getText());
		editData.setName(txtName.getText());
		editData.setState((FDCBillStateEnum) comboState.getSelectedItem());
		editData.setDescription(txtDescription.getText());

		// editData.getEntries().clear();
		//
		// KDTable table = getEntryTable();
		// for (int i = 0, size = table.getRowCount(); i < size; i++) {
		// IRow row = table.getRow(i);
		//
		// // 绑定数据
		// bindingEntryData(row, true);
		// }
		// table = getItemTable();
		// for (int i = 0, size = table.getRowCount(); i < size; i++) {
		// IRow row = table.getRow(i);
		//			
		// // 绑定数据
		// bindingItemData(row, true);
		// }

		// 存储分录数据行
		storeEntryData();
	}

	/**
	 * 描述：
	 * 
	 * @param row
	 * @param isAddToCols
	 * @author rd_skyiter_wang
	 * @createDate 2014-8-22
	 */
	private void bindingEntryData(IRow row, boolean isAddToCols) {

		// //////////////////////////////////////////////////////////////////////////
		// 分录
		int entrySeq = row.getRowIndex();
		ProjectTargetShowEntryInfo entryInfo = (ProjectTargetShowEntryInfo) row.getUserObject();
		if (null == entryInfo) {
			entryInfo = (ProjectTargetShowEntryInfo) createNewEntryData(row);

			row.setUserObject(entryInfo);
		}
		if (isAddToCols) {
			editData.getEntries().add(entryInfo);
		}

		if (null == entryInfo.getId()) {
			entryInfo.setId(BOSUuid.create(entryInfo.getBOSType()));
		}
		entryInfo.setSeq(++entrySeq);

		entryInfo.setSeqNum((String) row.getCell(COL_ENTRY_SEQ_NUM).getValue());
		entryInfo.setNumber((String) row.getCell(COL_ENTRY_NUMBER).getValue());
		entryInfo.setName((String) row.getCell(COL_ENTRY_NAME).getValue());

		// //////////////////////////////////////////////////////////////////////////
	}

	/**
	 * 描述：
	 * 
	 * @param row
	 * @param isAddToCols
	 * @author rd_skyiter_wang
	 * @createDate 2014-8-22
	 */
	private void bindingItemData(IRow row, boolean isAddToCols) {
		// //////////////////////////////////////////////////////////////////////////
		// 分录
		int itemSeq = row.getRowIndex();
		ProjectTargetShowItemInfo itemInfo = (ProjectTargetShowItemInfo) row.getUserObject();
		if (null == itemInfo) {
			itemInfo = (ProjectTargetShowItemInfo) createNewItemData(row);

			row.setUserObject(itemInfo);
		}
		if (isAddToCols) {
			// editData.getEntries().add(itemInfo);
		}

		if (null == itemInfo.getId()) {
			itemInfo.setId(BOSUuid.create(itemInfo.getBOSType()));
		}
		itemInfo.setSeq(++itemSeq);

		itemInfo.setSeqNum((String) row.getCell(COL_ITEM_SEQ_NUM).getValue());
		itemInfo.setNumber((String) row.getCell(COL_ITEM_NUMBER).getValue());
		itemInfo.setProjectTargetEntry((ProjectTargetEntryInfo) row.getCell(COL_ITEM_PROJECT_TARGET_ENTRY).getValue());
		itemInfo.setBill((ProjectTargetShowBillInfo) row.getCell(COL_ITEM_BILL_ID).getValue());
		itemInfo.setParent((ProjectTargetShowEntryInfo) row.getCell(COL_ITEM_PARENT_ID).getValue());

		// //////////////////////////////////////////////////////////////////////////
	}

	// /////////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////////

	/**
	 * 加载数据到界面
	 */
	public void loadFields() {
		// 注销监听器
		detachListeners();

		super.loadFields();
		
		// 保存旧的数据
		editData.put("oldProjectTargetShowInfo", this.editData.clone());

		// /////////////////////////////////////////////////////////////////

		// 加载单据头
		loadHead();

		// /////////////////////////////////////////////////////////////////

		// // 加载分录
		// loadEntries();
		// // 加载字段后
		// afterLoadFields();

		// 加载分录数据行
		loadEntryData();
		// 选择分录行
		selectEntryTable();

		// /////////////////////////////////////////////////////////////////

		// 注册监听器
		registeShortKey();

		// 最后加上监听器
		attachListeners();
	}

	/**
	 * 描述：加载单据头
	 * 
	 * @author rd_skyiter_wang
	 * @createDate 2014-8-12
	 */
	protected void loadHead() {
	}

	/**
	 * 描述：加载分录
	 * 
	 * @author rd_skyiter_wang
	 * @createDate 2014-8-12
	 */
	protected void loadEntries() {
		if (!isInitTable) {
			try {
				initTableStyle();
			} catch (Exception e) {
				this.handUIExceptionAndAbort(e);
			}
		}

		List<KDTable> tableList = getTableList();
		for (KDTable table : tableList) {
			if (null != table) {
				table.removeRows();
				table.setUserObject(null);
			}
		}

		// //////////////////////////////////////////////////////////////////////////

		// 加载成本科目行
		loadEntryRows();

		// 重新锁定列_用作编辑
		reLockColumnForEdit();
	}

	/**
	 * 描述：加载成本科目行
	 * 
	 * @author rd_skyiter_wang
	 * @createDate 2014-8-10
	 */
	private void loadEntryRows() {
		if (null == editData || FdcObjectCollectionUtil.isEmpty(editData.getEntries())) {
			return;
		}

		// //////////////////////////////////////////////////////////////////////////
		// //////////////////////////////////////////////////////////////////////////

		ProjectTargetShowEntryCollection entries = editData.getEntries();
		for (int i = 0; i < entries.size(); i++) {
			ProjectTargetShowEntryInfo entryInfo = (ProjectTargetShowEntryInfo) entries.get(i);
			KDTable table = getEntryTable();

			IRow row = table.addRow();
			loadEntryFields(table, row, entryInfo);
		}

		List<KDTable> tableList = getTableList();
		for (KDTable table : tableList) {
			if (null != table) {
				int colIndex = table.getColumnIndex(COL_ENTRY_SEQ_NUM);
				// 排序
				table.getSortMange().sort(colIndex, ISortManager.SORT_ASCEND);
			}
		}

		// //////////////////////////////////////////////////////////////////////////
		// //////////////////////////////////////////////////////////////////////////

		oldEntryValueMap.clear();
		newEntryValueMap.clear();
		Map<String, ProjectTargetShowEntryInfo> entryMap = FdcObjectCollectionUtil.parseStringIdMap(entries);
		oldEntryValueMap.putAll(entryMap);
		newEntryValueMap.putAll(entryMap);
	}

	/**
	 * 描述：重新锁定列_用作编辑
	 * 
	 * @author rd_skyiter_wang
	 * @createDate 2014-8-24
	 */
	private void reLockColumnForEdit() {
		boolean isView = OprtState.VIEW.equals(this.getOprtState());
		if (isView || FDCTableHelper.isEmpty(kdtEntries)) {
			return;
		}
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	@Override
	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return ProjectTargetShowBillFactory.getRemoteInstance();
	}

	@Override
	protected KDTable getDetailTable() {
		// TODO Auto-generated method stub
		return super.getDetailTable();

		// return null;
	}

	@Override
	protected KDTextField getNumberCtrl() {
		// TODO Auto-generated method stub
		return txtNumber;
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	@Override
	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
	}

	/**
	 * 表头设置
	 * 
	 * @throws Exception
	 */
	protected void initHeadStyle() throws Exception {
		super.initHeadStyle();

		// txtSeqNum.setMaxLength(80);
		txtNumber.setMaxLength(80);
		txtName.setMaxLength(255);
		txtDescription.setMaxLength(255);

		// txtSeqNum.setRequired(true);
		txtNumber.setRequired(true);
		txtName.setRequired(true);
		txtDescription.setRequired(false);
	}

	/**
	 * 表体设置
	 * 
	 * @throws Exception
	 */
	protected void initTableStyle() throws Exception {
		super.initTableStyle();

		// 初始化表格列表
		if (FdcCollectionUtil.isEmpty(getTableList())) {
			initTableList();
		}

		isInitTable = true;
	}

	/**
	 * 描述：表体设置
	 * 
	 * @param table
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-1
	 */
	protected void initTableStyle(KDTable table) {
		if (table.equals(getEntryTable())) {
			initEntryTableStyle();
		} else if (table.equals(getItemTable())) {
			initItemTableStyle();
		}
	}

	/**
	 * 描述：
	 * 
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-4
	 */
	private void initEntryTableStyle() {
		KDTable table = getEntryTable();

		// /////////////////////////////////////////////////////////////////////

		// if (isEditting || isSuperEditting) {
		// table.getColumn(COL_ENTRY_SEQ_NUM).getStyleAttributes().setHided(false);
		// }
		table.getColumn(COL_ENTRY_SEQ_NUM).getStyleAttributes().setHided(!isSuperEditting);
		table.getColumn(COL_ENTRY_NUMBER).getStyleAttributes().setHided(!isSuperEditting);

		// /////////////////////////////////////////////////////////////////////

		// 编辑器
		table.getColumn(COL_ENTRY_SEQ_NUM).setEditor(FdcEditorHelper.getTextEditor(80));
		table.getColumn(COL_ENTRY_NUMBER).setEditor(FdcEditorHelper.getTextEditor(80));
		table.getColumn(COL_ENTRY_NAME).setEditor(FdcEditorHelper.getTextEditor(255));

		// /////////////////////////////////////////////////////////////////////

		// 必录
		table.getColumn(COL_ENTRY_SEQ_NUM).setRequired(true);
		table.getColumn(COL_ENTRY_NUMBER).setRequired(true);
		table.getColumn(COL_ENTRY_NAME).setRequired(true);

		// /////////////////////////////////////////////////////////////////////

		// 排序管理器
		KDTSortManager sortManager = getSortManager(table);
		sortManager.setSortAuto(true);
		table.getColumn(COL_ENTRY_SEQ_NUM).setSortable(true);
		table.setSortMange(sortManager);
	}

	/**
	 * 描述：
	 * 
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-4
	 */
	private void initItemTableStyle() {
		KDTable table = getItemTable();

		// /////////////////////////////////////////////////////////////////////

		// if (isEditting || isSuperEditting) {
		// table.getColumn(COL_ITEM_SEQ_NUM).getStyleAttributes().setHided(false);
		// }
		table.getColumn(COL_ITEM_SEQ_NUM).getStyleAttributes().setHided(!isSuperEditting);
		table.getColumn(COL_ITEM_NUMBER).getStyleAttributes().setHided(!isSuperEditting);

		// /////////////////////////////////////////////////////////////////////

		// 编辑器
		table.getColumn(COL_ITEM_SEQ_NUM).setEditor(FdcEditorHelper.getTextEditor(80));
		table.getColumn(COL_ITEM_NUMBER).setEditor(FdcEditorHelper.getTextEditor(80));
		table.getColumn(COL_ITEM_NAME).setEditor(FdcEditorHelper.getTextEditor(255));
		// 初始化项目指标分录F7编辑器
		initProjectTargetEntryF7Editor(table);

		// /////////////////////////////////////////////////////////////////////

		// 必录
		table.getColumn(COL_ITEM_SEQ_NUM).setRequired(true);
		table.getColumn(COL_ITEM_NUMBER).setRequired(true);
		table.getColumn(COL_ITEM_NAME).setRequired(true);
		table.getColumn(COL_ITEM_PROJECT_TARGET_ENTRY).setRequired(true);

		// /////////////////////////////////////////////////////////////////////

		// 排序管理器
		KDTSortManager sortManager = getSortManager(table);
		sortManager.setSortAuto(true);
		table.getColumn(COL_ITEM_SEQ_NUM).setSortable(true);
		table.setSortMange(sortManager);

		// /////////////////////////////////////////////////////////////////////
		// /////////////////////////////////////////////////////////////////////
	}
	

	/**
	 * 描述：初始化项目指标分录F7编辑器
	 * 
	 * @param table
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-5
	 */
	private void initProjectTargetEntryF7Editor(KDTable table) {
		KDBizPromptBox prmtBox = new KDBizPromptBox();

		FilterInfo filterInfo = new FilterInfo();
		FdcF7InitHelper.initProjectTargetEntryF7(prmtBox, this, filterInfo);

		// 编辑器
		table.getColumn(COL_ITEM_PROJECT_TARGET_ENTRY).setEditor(FdcEditorHelper.getBizPromptEditor(prmtBox));
		// 渲染器
		table.getColumn(COL_ITEM_PROJECT_TARGET_ENTRY).setRenderer(FdcNameRender.getInstance());
	}

	protected void initWorkButton() {
		super.initWorkButton();

		// /////////////////////////////////////////////////////////////////////

		btnAddItem.setIcon(EASResource.getIcon("imgTbtn_addline"));
		btnInsertItem.setIcon(EASResource.getIcon("imgTbtn_insert"));
		btnRemoveItem.setIcon(EASResource.getIcon("imgTbtn_deleteline"));

		// /////////////////////////////////////////////////////////////////////

		remove(btnAutoGetData);
		
		remove(btnAddItem);
		remove(btnInsertItem);
		remove(btnRemoveItem);

		if (isSuperEditting) {
			toolBar.addComponentAfterComponent(btnAutoGetData, btnSubmit);
			
			containerItem.addButton(btnAddItem);
			containerItem.addButton(btnInsertItem);
			containerItem.addButton(btnRemoveItem);
		}

		// /////////////////////////////////////////////////////////////////////
	}

	protected void setButtonStatus() {
		super.setButtonStatus();
	}

	// ////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////

	/**
	 * 分录表格编辑事件
	 * 
	 * @throws Exception
	 */
	protected void afterEntryEditStopped(KDTable table, Object oldValue, Object newValue, int colIndex, int rowIndex)
			throws Exception {
		// 清单表格
		if (table.equals(getItemTable())) {
			afterItemEditStopped(table, oldValue, newValue, colIndex, rowIndex);

			return;
		}

		// ////////////////////////////////////////////////////////////////
		// ////////////////////////////////////////////////////////////////
		
		super.afterEntryEditStopped(table, oldValue, newValue, colIndex, rowIndex);

		String key = table.getColumnKey(colIndex);
		IRow row = table.getRow(rowIndex);

		// 排序编号
		if (COL_ENTRY_SEQ_NUM.equals(key)) {
			String seqNum = (String) newValue;
			if (null != seqNum) {
				seqNum = seqNum.trim().toLowerCase();
			}

			if (FdcStringUtil.isNotBlank(seqNum) && !CharUtils.isAsciiAlpha(seqNum.charAt(0))) {
				MsgBox.showWarning(this, "排序编码 必须 以字母开头");

				detachListeners();
				// 清空单元格
				row.getCell(COL_ENTRY_SEQ_NUM).setValue(null);
				// 获取焦点
				table.getEditManager().editCellAt(rowIndex, colIndex);
				attachListeners();

				// return;
				// SysUtil.abort();
			} else {
				// 保存当前细目
				curEntryIndex = rowIndex;
				storeCurItems();

				// 排序
				table.getSortMange().sort(colIndex, ISortManager.SORT_ASCEND);
				// 上下表格联动问题：不能使用selectRow方法，不然会触发beforeSelectEntry-->storeCurItems方法，
				// 而当前细目还没有清空，会将其设置到新的分录下面
				refreshItem();
			}
		}

		// 添加对象值到Map中
		addRowValueToMapForEdit(table, row, key, newValue);

		// 设置是否已经修改
		this.setModify(true);
	}
	

	/**
	 * 描述：添加行对象值到Map中(用作编辑)
	 * 
	 * @param table
	 * @param map
	 * @param row
	 * @param key
	 * @param newValue
	 * @author rd_skyiter_wang
	 * @createDate 2014-8-7
	 */
	private void addRowValueToMapForEdit(KDTable table, IRow row, String key, Object newValue) {
		CoreBaseInfo entryInfo = (CoreBaseInfo) row.getUserObject();
		if (null == entryInfo) {
			return;
		}
		String idStr = entryInfo.getId().toString();

		if (table.equals(getEntryTable())) {
			// 绑定数据
			bindingEntryData(row, false);
			
			newEntryValueMap.put(idStr, (ProjectTargetShowEntryInfo) entryInfo);
		} else if (table.equals(getItemTable())) {
			// 绑定数据
			bindingItemData(row, false);
			
			newItemValueMap.put(idStr, (ProjectTargetShowItemInfo) entryInfo);
		}
		
	}

	// //////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////

	/**
	 * 重设组件
	 */
	/**
	 * 描述：
	 * 
	 * @author rd_skyiter_wang
	 * @createDate 2014-10-31
	 * @see com.kingdee.eas.fdc.basedata.mobile.client.FdcMobileEditUI#resetComponent()
	 */
	public void resetComponent() {
		boolean flag = isEditting || isSuperEditting;

		// /////////////////////////////////////////////////////////////////////

		FDCClientHelper.setActionEnV(actionEdit, flag);
		FDCClientHelper.setActionEnV(actionSubmit, flag);

		FDCClientHelper.setActionEnV(actionAudit, false);
		FDCClientHelper.setActionEnV(actionUnAudit, false);
		FDCClientHelper.setActionEnV(actionAuditResult, false);
		FDCClientHelper.setActionEnV(actionMultiapprove, false);

		FDCClientHelper.setActionEnV(actionNextPerson, false);
		FDCClientHelper.setActionEnV(actionViewDoProccess, false);
		FDCClientHelper.setActionEnV(actionViewSubmitProccess, false);
		FDCClientHelper.setActionEnV(actionCalculator, false);

		// /////////////////////////////////////////////////////////////////////

		containerMain.removeAllButton();

		// 只有(超级)编辑状态下，才能自动取数
		if (flag) {
			toolBar.addComponentAfterComponent(btnAutoGetData, btnSubmit);

			actionAutoGetData.setVisible(true);
		}
		// 只有(超级)编辑状态下，才能新增、插入、删除
		if (flag) {
			containerMain.addButton(btnAddEntry);
			// containerMain.addButton(btnInsertEntry);
			containerMain.addButton(btnRemoveEntry);

			// toolBar.addComponentAfterComponent(btnAddEntry, btnRemove);
			// // toolBar.addComponentAfterComponent(btnInsertEntry, btnAddEntry);
			// toolBar.addComponentAfterComponent(btnRemoveEntry, btnAddEntry);

			actionAddEntry.setVisible(true);
			// actionInsertEntry.setVisible(true);
			actionRemoveEntry.setVisible(true);
		}

		// /////////////////////////////////////////////////////////////////////

		btnAutoGetData.setEnabled(flag);
		actionAddEntry.setEnabled(flag);
		// actionInsertEntry.setEnabled(isSuperEditting);
		actionRemoveEntry.setEnabled(flag);

		// /////////////////////////////////////////////////////////////////////

		containerItem.removeAllButton();

		// 只有(超级)编辑状态下，才能新增、插入、删除
		if (flag) {
			containerItem.addButton(btnAddItem);
			// containerItem.addButton(btnInsertItem);
			containerItem.addButton(btnRemoveItem);

			// toolBar.addComponentAfterComponent(btnAddItem, btnRemove);
			// // toolBar.addComponentAfterComponent(btnInsertItem, btnAddItem);
			// toolBar.addComponentAfterComponent(btnRemoveItem, btnAddItem);

			actionAddItem.setVisible(true);
			// actionInsertItem.setVisible(true);
			actionRemoveItem.setVisible(true);
		}

		// /////////////////////////////////////////////////////////////////////

		actionAddItem.setEnabled(flag);
		// actionInsertItem.setEnabled(isSuperEditting);
		actionRemoveItem.setEnabled(flag);

		// /////////////////////////////////////////////////////////////////////

		if (getOprtState().equals(OprtState.VIEW)) {
			List<KDTable> tableList = getTableList();

			for (KDTable table : tableList) {
				if (null != table) {
					table.getStyleAttributes().setLocked(true);
				}
			}
		}

		// super.resetComponent();

		// /////////////////////////////////////////////////////////////////////
		// /////////////////////////////////////////////////////////////////////

		// 只有超级编辑状态下，才能修改"排序编码"、"编码"、"名称"等字段
		txtNumber.setEnabled(isSuperEditting);
		txtName.setEnabled(isSuperEditting);
		txtDescription.setEnabled(isSuperEditting);
		contState.setVisible(isSuperEditting);
		contAuditor.setVisible(isSuperEditting);
		contAuditTime.setVisible(isSuperEditting);

		// /////////////////////////////////////////////////////////////////////

		List<KDTable> tableList = getTableList();

		for (KDTable table : tableList) {
			if (null != table) {
				resetComponent(table);
			}
		}

		// /////////////////////////////////////////////////////////////////////

		afterLoadFields();
	}

	/**
	 * 描述：
	 * 
	 * @param table
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-1
	 */
	private void resetComponent(KDTable table) {
		boolean flag = isEditting || isSuperEditting;

		if (table.equals(getEntryTable())) {
			// table.getColumn(COL_ENTRY_SEQ_NUM).getStyleAttributes().setHided(!flag);
			table.getColumn(COL_ENTRY_SEQ_NUM).getStyleAttributes().setHided(!isSuperEditting);
			table.getColumn(COL_ENTRY_NUMBER).getStyleAttributes().setHided(!isSuperEditting);

			table.getStyleAttributes().setLocked(!flag);
			table.getColumn(COL_ENTRY_SEQ_NUM).getStyleAttributes().setLocked(!flag);
		} else if (table.equals(getItemTable())) {
			// table.getColumn(COL_ENTRY_SEQ_NUM).getStyleAttributes().setHided(!flag);
			table.getColumn(COL_ITEM_SEQ_NUM).getStyleAttributes().setHided(!isSuperEditting);
			table.getColumn(COL_ITEM_NUMBER).getStyleAttributes().setHided(!isSuperEditting);

			table.getStyleAttributes().setLocked(!flag);
			table.getColumn(COL_ITEM_SEQ_NUM).getStyleAttributes().setLocked(!flag);
		}

	}

	// //////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////

	/**
	 * 重新设置细目表体样式
	 * 
	 * @param row
	 * @throws Exception
	 */
	protected void resetItemTableStyle(IRow row) {
		KDTable tblItem = getItemTable();
		tblItem.checkParsed();
	}

	// //////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////

	/**
	 * 新建单据行，返回一个新的分录行的默认值
	 */
	protected IObjectValue createNewEntryData(KDTable table) {
		ProjectTargetShowEntryInfo newData = new ProjectTargetShowEntryInfo();
		newData.setId(BOSUuid.create(newData.getBOSType()));

		// 单据
		newData.setBill(editData);

		return newData;
	}

	/**
	 * 新建单据行，返回一个新的分录行的默认值
	 */
	protected IObjectValue createNewEntryData(IRow row) {
		ProjectTargetShowEntryInfo newData = new ProjectTargetShowEntryInfo();
		newData.setId(BOSUuid.create(newData.getBOSType()));

		// 单据
		newData.setBill(editData);

		return newData;
	}

	/**
	 * 显示单据行
	 */
	public void loadEntryFields(KDTable table, IRow row, IObjectValue obj) {
		// super.loadEntryFields(table, row, obj);
		ProjectTargetShowEntryInfo info = (ProjectTargetShowEntryInfo) obj;
		
		row.getCell(FdcColumnName.COL_ID).setValue(info.getId());
		// row.getCell(COL_ENTRY_ID).setValue(info.getId());
		if (null == info.getSeqNum()) {
			int seq = 10000 + row.getRowIndex() + 1;
			
			// 初始化排序编码
			initSeqNum(seq, info);
		}
		row.getCell(COL_ENTRY_SEQ_NUM).setValue(info.getSeqNum());
		row.getCell(COL_ENTRY_NUMBER).setValue(info.getNumber());
		row.getCell(COL_ENTRY_NAME).setValue(info.getName());

		// ///////////////////////////////////////////////////////////////////////////////

		boolean flag = isEditting || isSuperEditting;
		if (flag) {
			row.getCell(COL_ENTRY_NAME).getStyleAttributes().setLocked(!flag);
		}

		// ///////////////////////////////////////////////////////////////////////////////

		row.setUserObject(info);
	}

	/**
	 * 描述：初始化排序编码
	 * 
	 * @param seq
	 * @param info
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-13
	 */
	protected void initSeqNum(int seq, FdcMobileEntryInfo info) {
		super.initSeqNum(seq, info);
	}

	/**
	 * 保存单据行
	 */
	protected void storeEntryFields(KDTable table, IRow row, IObjectValue obj) {
		// super.storeEntryFields(table, row, obj);
		ProjectTargetShowEntryInfo info = (ProjectTargetShowEntryInfo) obj;

		Object cellValue = null;

		cellValue = row.getCell(COL_ENTRY_ID).getValue();
		if (cellValue != null) {
			info.setId(BOSUuid.read(cellValue.toString()));
		} else {
			info.setId(null);
		}

		cellValue = row.getCell(COL_ENTRY_SEQ_NUM).getValue();
		if (cellValue != null) {
			info.setSeqNum(cellValue.toString());
		} else {
			info.setSeqNum(null);
		}

		cellValue = row.getCell(COL_ENTRY_NUMBER).getValue();
		if (cellValue != null) {
			info.setNumber(cellValue.toString());
		} else {
			info.setNumber(null);
		}

		cellValue = row.getCell(COL_ENTRY_NAME).getValue();
		if (cellValue != null) {
			info.setName(cellValue.toString());
		} else {
			info.setName(null);
		}
	}
	
	// //////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////

	// 存储分录数据行
	protected void storeEntryData() {
		editData.getEntries().clear();
		
		entryClos.clear();

		KDTable table = getEntryTable();
		int count = table.getRowCount();
		ProjectTargetShowEntryInfo entryInfo = null;
		IRow row = null;

		for (int i = 0; i < count; i++) {
			row = table.getRow(i);
			entryInfo = (ProjectTargetShowEntryInfo) row.getUserObject();
			storeEntryFields(table, row, entryInfo);

			entryClos.addObject(entryInfo);
		}

		// ////////////////////////////////////////////////////////////////
		// ////////////////////////////////////////////////////////////////

		storeItems();

		this.editData.put("entryClos", entryClos);
		this.editData.put("itemMap", itemMap);
	}

	// 加载分录数据行
	protected void loadEntryData() {
		KDTable table = getEntryTable();
		table.checkParsed();
		table.removeRows();

		IObjectCollection entryCols = getEntryClos();

		ProjectTargetShowEntryInfo entryInfo = null;
		IRow row = null;
		for (int i = 0, size = entryCols.size(); i < size; i++) {
			entryInfo = (ProjectTargetShowEntryInfo) entryCols.getObject(i);
			row = table.addRow();
			loadEntryFields(table, row, entryInfo);
		}

		this.editData.put("entryClos", entryCols);
	}

	protected IObjectCollection getEntryClos() {
		boolean isAddNew = getOprtState().equals(OprtState.ADDNEW);
		if (isAddNew || isReGetData) {
			if (null != this.editData) {
				entryClos = editData.getEntries();
			}
			if (null == entryClos) {
				entryClos = new ProjectTargetShowEntryCollection();
			}
			return entryClos;
		}

		// //////////////////////////////////////////////////////////////////////
		
		String id = null;

		if (null != this.editData && null != this.editData.getId()) {
			id = this.editData.getId().toString();

			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("bill.id", id));

			try {
				// 取得分录集合
				entryClos = ProjectTargetShowUtil.getEntryCols(null, filter);
			} catch (Exception e) {
				e.printStackTrace();
				this.handUIException(e);
				SysUtil.abort();
			}
		}

		if (null == entryClos) {
			entryClos = new ProjectTargetShowEntryCollection();
		}

		return entryClos;
	}

	// //////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////

	/**
	 * 描述：
	 * 
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-10-31
	 * @see com.kingdee.eas.fdc.basedata.mobile.client.FdcMobileEditUI#createNewData()
	 */
	@Override
	protected IObjectValue createNewData() {
		ProjectTargetShowBillInfo newData = new ProjectTargetShowBillInfo();

		// ////////////////////////////////////////////////////////////////

		createNewData(newData);
		if (null == newData.getId()) {
			newData.setId(BOSUuid.create(newData.getBOSType()));
		}
		
		// ////////////////////////////////////////////////////////////////

		// 编码、名称，设置默认值，不可编辑
		// String createTimeFormate = DateFormatUtils.format(newData.getCreateTime(), "yyyyMMdd-HHmmss");
		String createTimeFormate = "001";

		String entityName = "ProjectTargetShow";
		String number = "NUM-" + entityName + "-" + createTimeFormate;
		newData.setNumber(number);

		String entityAlias = "项目指标展示";
		String name = "NAM-" + entityAlias + "-" + createTimeFormate;
		newData.setName(name);
		
		String description = entityAlias;
		newData.setDescription(description);
		
		// ////////////////////////////////////////////////////////////////

		// 创建项目指标显示分录
		createProjectTargetShowEntrys(newData);

		// ////////////////////////////////////////////////////////////////

		return newData;
	}

	/**
	 * 描述：创建项目指标显示分录
	 * 
	 * @param newData
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-3
	 */
	private void createProjectTargetShowEntrys(ProjectTargetShowBillInfo newData) {
		// ////////////////////////////////////////////////////////////////

		// 获取项目指标分录
		ProjectTargetEntryCollection projectTargetEntryCollection = fetchProjectTargetEntrys();
		if (FdcObjectCollectionUtil.isEmpty(projectTargetEntryCollection)) {
			return;
		}

		// ////////////////////////////////////////////////////////////////
		
		ProjectTargetShowEntryCollection projectTargetShowEntryCollection = newData.getEntries();
		projectTargetShowEntryCollection.clear();
		if (null == itemMap) {
			itemMap = new HashMap<String, ProjectTargetShowItemCollection>();
		}
		
		Map targetGroupMap = FdcObjectCollectionUtil.parsePropertyMap(projectTargetEntryCollection, "targetGroup");
		Set targetGroupKeySet = targetGroupMap.keySet();
		for (Iterator iterator = targetGroupKeySet.iterator(); iterator.hasNext();) {
			Object targetGroupValue = (String) iterator.next();
			TargetGroupEnum targetGroup = null;
			if (targetGroupValue instanceof String) {
				targetGroup = TargetGroupEnum.getEnum((String) targetGroupValue);
			} else if (targetGroupValue instanceof TargetGroupEnum) {
				targetGroup = (TargetGroupEnum) targetGroupValue;
			}
			
			ProjectTargetShowEntryInfo projectTargetShowEntryInfo = new ProjectTargetShowEntryInfo();
			projectTargetShowEntryInfo.setBill(newData);
			projectTargetShowEntryInfo.setId(BOSUuid.create(projectTargetShowEntryInfo.getBOSType()));
			projectTargetShowEntryInfo.setName(targetGroup.getAlias());
			
			ProjectTargetShowItemCollection projectTargetShowItemCollection = projectTargetShowEntryInfo.getEntries();
			String entryId = projectTargetShowEntryInfo.getId().toString();
			itemMap.put(entryId, projectTargetShowItemCollection);

			List infoList = (List) targetGroupMap.get(targetGroupValue);
			for (Iterator iterator2 = infoList.iterator(); iterator2.hasNext();) {
				ProjectTargetEntryInfo projectTargetEntryInfo = (ProjectTargetEntryInfo) iterator2.next();
				if (!projectTargetEntryInfo.isIsEnable()) {
					continue;
				}

				ProjectTargetShowItemInfo projectTargetShowItemInfo = new ProjectTargetShowItemInfo();
				projectTargetShowItemInfo.setBill(newData);
				projectTargetShowItemInfo.setParent(projectTargetShowEntryInfo);

				projectTargetShowItemInfo.setId(BOSUuid.create(projectTargetShowItemInfo.getBOSType()));
				projectTargetShowItemInfo.setProjectTargetEntry(projectTargetEntryInfo);
				projectTargetShowItemInfo.setSeqNum(projectTargetEntryInfo.getSeqNum());
				projectTargetShowItemInfo.setNumber(projectTargetEntryInfo.getNumber());
				projectTargetShowItemInfo.setName(projectTargetEntryInfo.getName());

				// ////////////////////////////////////////////////////////////////

				projectTargetShowItemCollection.add(projectTargetShowItemInfo);
			}
			
			projectTargetShowEntryCollection.add(projectTargetShowEntryInfo);
		}
	}
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.kingdee.eas.fdc.basedata.mobile.client.FdcMobileEditUI#getProjectTargetEntrysFilter()
	 */
	@Override
	protected FilterInfo getProjectTargetEntrysFilter() {
		FilterInfo filter = new FilterInfo();

		// // 数字列
		// filter.appendFilterItem("dataType", DataTypeEnum.NUMBER_VALUE);
		// 启用
		filter.appendFilterItem("isEnable", Boolean.TRUE);

		return filter;
	}

	// ////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////

	/**
	 * 增加单据行前的相关处理
	 */
	protected void beforeAddEntry(KDTable table) {
	}

	/**
	 * 插入单据行前的相关处理
	 */
	protected void beforeInsertEntry(KDTable table) {
	}

	/**
	 * 删除单据行前的相关处理
	 */
	protected void beforeRemoveEntry(KDTable table) {
		super.beforeRemoveEntry(table);
		
		FDCTableHelper.checkSelectedAndAbort(this, table);
	}

	/**
	 * 增加单据行后的相关处理
	 */
	protected void afterAddEntry(KDTable table, IObjectValue lineData) {
	}

	/**
	 * 插入单据行后的相关处理
	 */
	protected void afterInsertEntry(KDTable table, IObjectValue lineData) {
	}

	/**
	 * 删除单据行后的相关处理
	 */
	protected void afterRemoveEntry(KDTable table, IObjectValue lineData) {
		removeCurItems(lineData);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.kingdee.eas.fdc.basedata.mobile.client.FdcMobileEditUI#afterLoadFields()
	 */
	@Override
	protected void afterLoadFields() {
		// TODO Auto-generated method stub
		super.afterLoadFields();
	}

	/**
	 * 描述：加载字段后
	 * 
	 * @param table
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-1
	 */
	protected void afterLoadFields(KDTable table) {
		boolean isView = getOprtState().equals(OprtState.VIEW);
		if (isView) {
			table.getStyleAttributes().setLocked(true);
			return;
		}

		boolean flag = isEditting || isSuperEditting;
		table.getColumn(COL_ENTRY_NAME).getStyleAttributes().setLocked(!flag);
		if (table.equals(getItemTable())) {
			table.getColumn(COL_ITEM_PROJECT_TARGET_ENTRY).getStyleAttributes().setLocked(!flag);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.kingdee.eas.framework.client.CoreBillEditUI#doBeforeSubmit(java.awt.event.ActionEvent)
	 */
	@Override
	protected void doBeforeSubmit(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.doBeforeSubmit(e);
	}

	// ////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.kingdee.eas.fdc.basedata.mobile.client.FdcMobileEditUI#initTableList()
	 */
	@Override
	protected List<KDTable> initTableList() {
		List<KDTable> tableList = super.initTableList();

		if (null == tableList) {
			tableList = new ArrayList<KDTable>();

			setTableList(tableList);
		}

		tableList.add(kdtEntries);
		tableList.add(kdtItems);

		// ////////////////////////////////////////////////////////////////

		return tableList;
	}

	// ////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.kingdee.eas.fdc.basedata.mobile.client.FdcMobileEditUI#verifyInputForSubmint()
	 */
	@Override
	protected void verifyInputForSubmint() throws Exception {
		// TODO Auto-generated method stub
		super.verifyInputForSubmint();

		verifyInputTable(null);
	}

	// 检查单据体
	protected void verifyInputTable(ActionEvent e) throws Exception {
		KDTable tblEntry = getEntryTable();
		KDTable tblItem = getItemTable();

		// 分录表格始终不能为空
		FDCClientVerifyHelper.verifyEmpty(this, tblEntry);
		// FDCClientVerifyHelper.verifyEmpty(this, tblItem);
		
		if (FDCTableHelper.isNotEmpty(tblEntry)) {
			// 验证表格非空
			FDCClientVerifyHelper.verifyInput(this, tblEntry);
			
			// 验证表格列值是否重复
			FDCClientVerifyHelper.verifyTableCoulmnValueDup(this, tblEntry, COL_ENTRY_SEQ_NUM);
			FDCClientVerifyHelper.verifyTableCoulmnValueDup(this, tblEntry, COL_ENTRY_NUMBER);
			FDCClientVerifyHelper.verifyTableCoulmnValueDup(this, tblEntry, COL_ENTRY_NAME);
		}
		if (FDCTableHelper.isNotEmpty(tblItem)) {
			// 验证表格非空
			FDCClientVerifyHelper.verifyInput(this, tblItem);
			
			// 验证表格列值是否重复
			FDCClientVerifyHelper.verifyTableCoulmnValueDup(this, tblItem, COL_ITEM_SEQ_NUM);
			FDCClientVerifyHelper.verifyTableCoulmnValueDup(this, tblItem, COL_ITEM_NUMBER);
			FDCClientVerifyHelper.verifyTableCoulmnValueDup(this, tblItem, COL_ITEM_NAME);
		}

		// //////////////////////////////////////////////////////////////////////

		storeItems();

		// //////////////////////////////////////////////////////////////////////

		IRow row = null;
		String entryId = null;

		// 初始化Map
		// initEntryIdIndexMap();
		for (int i = 0, rowCount = kdtEntries.getRowCount(); i < rowCount; i++) {
			row = kdtEntries.getRow(i);

			entryId = row.getCell(COL_ENTRY_ID).getValue().toString();
			entryIdIndexMap.put(entryId, i);
		}

		// //////////////////////////////////////////////////////////////////////

		if (FdcMapUtil.isNotEmpty(itemMap)) {
			// 取得所有细目
			ProjectTargetShowItemCollection allItemCols = getItemCols();
			Map<String, ProjectTargetShowItemCollection> allItemMap = new HashMap<String, ProjectTargetShowItemCollection>();
			Map<String, List<String>> itemSeqNumMap = new HashMap<String, List<String>>();
			Map<String, List<String>> itemNumberMap = new HashMap<String, List<String>>();
			ProjectTargetShowEntryInfo entryInfo = null;
			ProjectTargetShowItemInfo itemInfo = null;
			ProjectTargetShowItemCollection itemCols = null;
			List<String> itemSeqNumList = null;
			List<String> itemNumberList = null;
			boolean flag = false;

			for (int i = 0, size = allItemCols.size(); i < size; i++) {
				itemInfo = allItemCols.get(i);

				entryInfo = itemInfo.getParent();
				entryId = entryInfo.getId().toString();

				itemCols = (ProjectTargetShowItemCollection) allItemMap.get(entryId);
				if (null == itemCols) {
					itemCols = new ProjectTargetShowItemCollection();
					allItemMap.put(entryId, itemCols);
				}
				itemCols.add(itemInfo);
			}

			// //////////////////////////////////////////////////////////////////

			// //////////////////////////////////////////////////////////////////

			Set<String> entryIdSet = entryIdIndexMap.keySet();
			for (Iterator<String> iterator = entryIdSet.iterator(); iterator.hasNext();) {
				entryId = iterator.next();

				if (itemMap.containsKey(entryId)) {
					itemCols = (ProjectTargetShowItemCollection) itemMap.get(entryId);
				} else if (allItemMap.containsKey(entryId)) {
					itemCols = (ProjectTargetShowItemCollection) allItemMap.get(entryId);
					itemMap.put(entryId, itemCols);
				} else {
					itemCols = new ProjectTargetShowItemCollection();
					itemMap.put(entryId, itemCols);
				}

				for (int i = 0, size = itemCols.size(); i < size; i++) {
					itemInfo = itemCols.get(i);

					entryInfo = itemInfo.getParent();
					entryId = entryInfo.getId().toString();

					itemSeqNumList = itemSeqNumMap.get(entryId);
					if (null == itemSeqNumList) {
						itemSeqNumList = new ArrayList<String>();
						itemSeqNumMap.put(entryId, itemSeqNumList);
					}

					itemNumberList = itemNumberMap.get(entryId);
					if (null == itemNumberList) {
						itemNumberList = new ArrayList<String>();
						itemNumberMap.put(entryId, itemNumberList);
					}

					try {
						flag = false;
						flag = checkItemInfo(entryInfo, itemInfo, itemSeqNumList, itemNumberList, i);
					} finally {
						if (!flag) {
							int rowIndex = (Integer) entryIdIndexMap.get(entryId);
							// 选择行
							FDCTableHelper.selectRow(kdtEntries, rowIndex);
						}
					}
				}
			}

		}

		// //////////////////////////////////////////////////////////////////////

		if (FDCTableHelper.isNotEmpty(tblEntry) && tblEntry.getRowCount() > 4) {
			int rs = MsgBox.showConfirm2(this, "前台页签总数超过4个，可能会影响移动端的展示体验，是否继续？");
			if (MsgBox.isCancel(rs)) {
				SysUtil.abort();
			}
		}
		
		// //////////////////////////////////////////////////////////////////////
	}

	// 校验细目明细数据输入的合法性
	private boolean checkItemInfo(ProjectTargetShowEntryInfo entryInfo, ProjectTargetShowItemInfo itemInfo,
			List<String> itemSeqNumList, List<String> itemNumberList, int j) throws Exception {
		String entryName = entryInfo.getName();

		// //////////////////////////////////////////////////////////////////////
		// //////////////////////////////////////////////////////////////////////

		if (FdcStringUtil.isBlank(itemInfo.getSeqNum())) {
			MsgBox.showInfo("名称为\" " + entryName + " \"的前台页签名称分录对应的细目，\"排序编号\"不能为空(第" + (j + 1) + "行)");

			abort();
		}
		if (FdcStringUtil.isBlank(itemInfo.getNumber())) {
			MsgBox.showInfo("名称为\" " + entryName + " \"的前台页签名称分录对应的细目，\"编号\"不能为空(第" + (j + 1) + "行)");

			abort();
		}
		if (FdcStringUtil.isBlank(itemInfo.getName())) {
			MsgBox.showInfo("名称为\" " + entryName + " \"的前台页签名称分录对应的细目，\"名称\"不能为空(第" + (j + 1) + "行)");
			
			abort();
		}
		if (null == itemInfo.getProjectTargetEntry()) {
			MsgBox.showInfo("名称为\" " + entryName + " \"的前台页签名称分录对应的细目，\"项目指标分录\"不能为空(第" + (j + 1) + "行)");

			abort();
		}

		// //////////////////////////////////////////////////////////////////////
		// //////////////////////////////////////////////////////////////////////

		String itemSeqNum = itemInfo.getSeqNum().trim();
		if (itemSeqNumList.contains(itemSeqNum)) {
			MsgBox.showInfo("名称为\" " + entryName + " \"的前台页签名称分录对应的细目，\"排序编号\"不能重复(第" + (j + 1) + "行)");

			abort();
		}

		String itemNumber = itemInfo.getNumber().trim();
		if (itemNumberList.contains(itemNumber)) {
			MsgBox.showInfo("名称为\" " + entryName + " \"的前台页签名称分录对应的细目，\"编号\"不能重复(第" + (j + 1) + "行)");

			abort();
		}

		itemSeqNumList.add(itemSeqNum);
		itemNumberList.add(itemNumber);

		// //////////////////////////////////////////////////////////////////////
		// //////////////////////////////////////////////////////////////////////

		return true;
	}

	// ////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		String selectorAll = System.getProperty("selector.all");
		if (StringUtils.isEmpty(selectorAll)) {
			selectorAll = "true";
		}
		sic.add(new SelectorItemInfo("entries.*"));
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("entries.*"));
		} else {
		}
		sic.add(new SelectorItemInfo("entries.seqNum"));
		sic.add(new SelectorItemInfo("entries.description"));
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("creator.*"));
		} else {
			sic.add(new SelectorItemInfo("creator.id"));
			sic.add(new SelectorItemInfo("creator.number"));
			sic.add(new SelectorItemInfo("creator.name"));
		}
		sic.add(new SelectorItemInfo("createTime"));
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("lastUpdateUser.*"));
		} else {
			sic.add(new SelectorItemInfo("lastUpdateUser.id"));
			sic.add(new SelectorItemInfo("lastUpdateUser.number"));
			sic.add(new SelectorItemInfo("lastUpdateUser.name"));
		}
		sic.add(new SelectorItemInfo("lastUpdateTime"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("description"));
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("auditor.*"));
		} else {
			sic.add(new SelectorItemInfo("auditor.id"));
			sic.add(new SelectorItemInfo("auditor.number"));
			sic.add(new SelectorItemInfo("auditor.name"));
		}
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("auditTime"));
		return sic;
	}

	// ////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		setSaveAction(false);

		// super.actionSubmit_actionPerformed(e);

		// 验证输入
		verifyInput(e);

		// if (isModify()) {
		// 绑定数据
		bindingData();

		getBizInterface().submit(editData);

		// //////////////////////////////////////////////////////////////////////////

		// this.loadFields();
		oldEntryValueMap.clear();
		newEntryValueMap.clear();
		this.setModify(false);
		this.setOprtState(STATUS_EDIT);
		// }
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * 是否已经修改
	 * 
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillEditUI#isModify()
	 */
	public boolean isModify() {
		// TODO Auto-generated method stub
		// return super.isModify();

		if (this.isModify) {
			return this.isModify;
		}

		// //////////////////////////////////////////////////////////////////////////

		boolean isAddNew = OprtState.ADDNEW.equals(this.getOprtState());
		if (isAddNew) {
			return isAddNew;
		}

		// 表头
		// //////////////////////////////////////////////////////////////////////////

		boolean tempIsModify = !FDCHelper.isEqual(editData.getNumber(), txtNumber.getText());
		if (tempIsModify) {
			return tempIsModify;
		}
		tempIsModify = !FDCHelper.isEqual(editData.getName(), txtName.getText());
		if (tempIsModify) {
			return tempIsModify;
		}
		tempIsModify = !FDCHelper.isEqual(editData.getDescription(), txtDescription.getText());
		if (tempIsModify) {
			return tempIsModify;
		}

		// 表体
		// //////////////////////////////////////////////////////////////////////////

		boolean isValueChange = !FdcObjectUtil.isEquals(oldEntryValueMap, newEntryValueMap);
		if (isValueChange) {
			return isValueChange;
		}
		isValueChange = !FdcObjectUtil.isEquals(oldItemValueMap, newItemValueMap);
		if (isValueChange) {
			return isValueChange;
		}

		// //////////////////////////////////////////////////////////////////////////

		// isModify = super.isModify();
		this.isModify = false;

		return this.isModify;
	}

	/**
	 * 描述：设置是否已经修改
	 * 
	 * @param isModify
	 * @author rd_skyiter_wang
	 * @createDate 2014-8-20
	 */
	public void setModify(boolean isModify) {
		this.isModify = isModify;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.kingdee.eas.fdc.basedata.mobile.client.FdcMobileEditUI#afterOnLoad()
	 */
	@Override
	protected void afterOnLoad() throws Exception {
		// TODO Auto-generated method stub
		super.afterOnLoad();

		// // 初始化表格列表
		// initTableList();
	}

	// ////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////

	/**
	 * 在指定表格中新增行（新增到最后一行）
	 * 
	 * @param table
	 */
	protected void addEntry(KDTable table) {
		super.addEntry(table);

		setModify(true);
	}

	/**
	 * 在指定表格中插入行（在当前选中行前插入，如果当前未选中任何行的话，则新增到最后一行）
	 * 
	 * @param table
	 */
	protected void insertEntry(KDTable table) {
		super.insertEntry(table);

		setModify(true);
	}

	/**
	 * 在指定表格中删除当前选中行 增加隔行删除功能 2007-03-12
	 * 
	 * @param table
	 */
	protected void removeEntry(KDTable table) {
		super.removeEntry(table);

		setModify(true);
	}

	// ////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////

	/**
	 * 选中单据行前的相关处理
	 */
	protected void beforeSelectEntry(KDTable table, IObjectValue lineData) {
		if (isReGetData) {
			return;
		}
		
		// 先保存上次数据
		storeCurItems();
	}

	/**
	 * 选中单据行后的相关处理
	 */
	protected void afterSelectEntry(KDTable table, IObjectValue lineData) {
		// 刷新下表格
		ProjectTargetShowEntryInfo curEntryInfo = (ProjectTargetShowEntryInfo) lineData;
		if (curEntryInfo != null) {
			try {
				refreshItem();
			} catch (Exception e) {
				this.handUIException(e);
				SysUtil.abort();
			}
		}
	}

	/**
	 * 选择明细表格
	 * 
	 * @throws Exception
	 */
	public void selectEntryTable() {
		if (null == itemMap) {
			itemMap = new HashMap<String, ProjectTargetShowItemCollection>();
		}

		boolean isAddNew = getOprtState().equals(OprtState.ADDNEW);
		if (!isAddNew) {
			itemMap.clear();
		}
		if (curEntryIndex < 0 && getEntryTable().getRowCount() > 0) {
			getEntryTable().checkParsed();
			getItemTable().checkParsed();

			// 默认选择第一行
			FDCTableHelper.selectRow(getEntryTable(), 0);
			this.curEntryIndex = 0;
		} else {
			FDCTableHelper.selectRow(getEntryTable(), curEntryIndex);
		}
	}
	
	// ////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////

	private void storeItems() {
		// 最后保存一下当前选择的分录行数据
		storeCurItems();

		this.editData.put("entryClos", entryClos);
		this.editData.put("itemMap", itemMap);
	}

	private void storeCurItems() {
		IRow entryCurRow = getEntryTable().getRow(curEntryIndex);
		if (entryCurRow == null) {
			return;
		}
		IObjectValue detailData = (IObjectValue) entryCurRow.getUserObject();

		if (detailData != null) {
			String entryID = ((ProjectTargetShowEntryInfo) detailData).getId().toString();
			ProjectTargetShowItemCollection itemCols = new ProjectTargetShowItemCollection();

			KDTable tblItem = getItemTable();
			for (int i = 0; i < tblItem.getRowCount(); i++) {
				IRow row = tblItem.getRow(i);

				ProjectTargetShowItemInfo item = (ProjectTargetShowItemInfo) row.getUserObject();
				storeItemFields(tblItem, row, (IObjectValue) item);
				item.setParent((ProjectTargetShowEntryInfo) detailData);

				itemCols.add(item);
			}

			itemMap.put(entryID, itemCols);
		}
	}

	private void removeCurItems(IObjectValue detailData) {
		if (detailData != null) {
			String entryID = ((ProjectTargetShowEntryInfo) detailData).getId().toString();
			itemMap.remove(entryID);
		}
	}

	// ////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////

	// ////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////

	/**
	 * 刷新细目
	 * 
	 * @throws Exception
	 */
	protected void refreshItem() throws Exception {
		// 刷新细目表格
		refreshItemTable();
		// 刷新细目按钮
		refreshItemButtons();
	}

	/**
	 * 刷新细目表格
	 * 
	 * @throws Exception
	 */
	protected void refreshItemTable() throws Exception {
		KDTable tbl = getMainTable();

		String parentId = getSelectedKeyValue(tbl);

		if (FdcStringUtil.isNotBlank(parentId)) {
			// 取得细目集合
			ProjectTargetShowItemCollection cols = getItemCols(parentId);
			// 填充细目表格
			fillItemTable(cols);
		} else {
			// 清空细目表格
			getItemTable().removeRows();
		}

	}

	/**
	 * 刷新细目按钮
	 * 
	 * @throws Exception
	 */
	protected void refreshItemButtons() throws Exception {
		boolean flag = FDCTableHelper.isNotEmpty(getItemTable());
	}

	/**
	 * 取得细目集合
	 * 
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	protected ProjectTargetShowItemCollection getItemCols() throws Exception {
		ProjectTargetShowItemCollection cols = new ProjectTargetShowItemCollection();

		if (null != this.editData && null != this.editData.getId()) {
			String billId = this.editData.getId().toString();

			FilterInfo filter = new FilterInfo();
			filter.appendFilterItem("bill.id", billId);

			// 取得细目集合
			cols = ProjectTargetShowUtil.getItemCols(null, filter);
		}

		return cols;
	}

	/**
	 * 取得细目集合
	 * 
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	protected ProjectTargetShowItemCollection getItemCols(String parentId) throws Exception {
		ProjectTargetShowItemCollection cols = new ProjectTargetShowItemCollection();

		if (itemMap.containsKey(String.valueOf(parentId))) {
			cols = (ProjectTargetShowItemCollection) itemMap.get(parentId);
		} else {
			if (parentId != null) {
				FilterInfo filter = new FilterInfo();
				filter.appendFilterItem("parent.id", parentId);

				cols = ProjectTargetShowUtil.getItemCols(null, filter);
			}
		}

		return cols;
	}

	/**
	 * 填充细目表格
	 * 
	 * @param clos
	 * @throws Exception
	 */
	protected void fillItemTable(ProjectTargetShowItemCollection clos) throws Exception {
		KDTable tbl = getItemTable();
		// 清空细目表格
		tbl.removeRows();

		ProjectTargetShowItemInfo itemInfo = null;
		IRow row = null;
		for (Iterator iterator = clos.iterator(); iterator.hasNext();) {
			itemInfo = (ProjectTargetShowItemInfo) iterator.next();
			row = tbl.addRow();

			// 增加细目表格行
			addItemTableLine(row, itemInfo);
		}

		// 格式化表格精度
		this.formatTableScale(tbl);
	}

	/**
	 * 增加细目表格行
	 * 
	 * @param row
	 * @param itemInfo
	 * @throws Exception
	 */
	protected void addItemTableLine(IRow row, ProjectTargetShowItemInfo itemInfo) {
		row.setUserObject(itemInfo);

		row.getCell(FdcColumnName.COL_ID).setValue(itemInfo.getId());
		if (null == itemInfo.getSeqNum()) {
			int seq = 10000 + row.getRowIndex() + 1;
			String seqNum = "SeqNum" + seq;
			String number = isSuperEditting ? "SystemDefine" : "UserDefine";
			number += seq;
			itemInfo.setSeqNum(seqNum);
			itemInfo.setNumber(number);
		}
		row.getCell(COL_ITEM_SEQ_NUM).setValue(itemInfo.getSeqNum());
		row.getCell(FdcColumnName.COL_NUMBER).setValue(itemInfo.getNumber());
		row.getCell(FdcColumnName.COL_NAME).setValue(itemInfo.getName());
		row.getCell(COL_ITEM_PROJECT_TARGET_ENTRY).setValue(itemInfo.getProjectTargetEntry());
		row.getCell(COL_ITEM_BILL_ID).setValue(itemInfo.getBill());
		row.getCell(COL_ITEM_PARENT_ID).setValue(itemInfo.getParent());

		// 重新设置细目表体样式
		resetItemTableStyle(row);
	}

	// ////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////

	// ////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////

	protected ItemEditAdapter itemEditLs = new ItemEditAdapter();

	// 分录表格编辑事件
	protected class ItemEditAdapter extends KDTEditAdapter {
		public void editStopped(KDTEditEvent evt) {
			try {
				tblItem_editStopped(evt);
			} catch (Exception e) {
				e.printStackTrace();
				ProjectTargetShowEditUI.this.handleException(e);
			}
		}

		public void editValueChanged(KDTEditEvent evt) {
			try {
				// tblItem_editValueChanged(evt);
			} catch (Exception e) {
				e.printStackTrace();
				ProjectTargetShowEditUI.this.handleException(e);
			}
		}
	}

	// 分录表格编辑事件
	protected void tblItem_editStopped(KDTEditEvent e) throws Exception {
		Object oldValue = e.getOldValue();
		Object newValue = e.getValue();

		if (FDCUtils.isEqual(oldValue, newValue)) {
			return;
		}

		KDTable tblDetail = (KDTable) e.getSource();
		int colIndex = e.getColIndex();
		int rowIndex = e.getRowIndex();

		afterItemEditStopped(tblDetail, oldValue, newValue, colIndex, rowIndex);
	}

	protected void tblItem_editValueChanged(KDTEditEvent e) throws Exception {
		Object oldValue = e.getOldValue();
		Object newValue = e.getValue();

		if (FDCUtils.isEqual(oldValue, newValue)) {
			return;
		}

		KDTable tblDetail = (KDTable) e.getSource();
		int colIndex = e.getColIndex();
		int rowIndex = e.getRowIndex();

		afterItemEditStopped(tblDetail, oldValue, newValue, colIndex, rowIndex);
	}

	/**
	 * output tblItem_tableSelectChanged method
	 */
	protected void tblItem_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception {
		KDTable tblItem = (KDTable) e.getSource();

		if (tblItem.getSelectManager().size() > 0) {
			int top = tblItem.getSelectManager().get().getTop();

			if (isTableColumnSelected(tblItem)) {
				return;
			}

			if (tblItem.getRow(top) == null) {
				return;
			}
			IObjectValue detailData = (IObjectValue) tblItem.getRow(top).getUserObject();
			beforeSelectItem(tblItem, detailData);
			// setRow(top);

			afterSelectItem(tblItem, detailData);
		}
	}

	@Override
	protected void kdtItems_tableSelectChanged(KDTSelectEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.kdtItems_tableSelectChanged(e);

		tblItem_tableSelectChanged(e);
	}

	/**
	 * 分录表格编辑事件
	 * 
	 * @throws Exception
	 */
	protected void afterItemEditStopped(KDTable table, Object oldValue, Object newValue, int colIndex, int rowIndex)
			throws Exception {
		String key = table.getColumnKey(colIndex);
		IRow row = table.getRow(rowIndex);

		// 排序编号
		if (COL_ITEM_SEQ_NUM.equals(key)) {
			String seqNum = (String) newValue;
			if (null != seqNum) {
				seqNum = seqNum.trim().toLowerCase();
			}

			if (FdcStringUtil.isNotBlank(seqNum) && !CharUtils.isAsciiAlpha(seqNum.charAt(0))) {
				MsgBox.showWarning(this, "排序编码 必须 以字母开头");

				// detachListeners();
				row.getCell(COL_ITEM_SEQ_NUM).setValue(null);
				// attachListeners();

				SysUtil.abort();
			}

			// 保存当前细目
			// curEntryIndex = rowIndex;
			// storeCurItems();

			// 排序
			table.getSortMange().sort(colIndex, ISortManager.SORT_ASCEND);
			// 选中第一行
			// FDCTableHelper.selectRow(table, 0);
		}
		// 项目指标分录
		else if (COL_ITEM_PROJECT_TARGET_ENTRY.equals(key)) {
			ProjectTargetEntryInfo projectTargetEntryInfo = (ProjectTargetEntryInfo) newValue;

			String name = null;
			if (null != projectTargetEntryInfo) {
				name = projectTargetEntryInfo.getName();
				
				// 名称
				row.getCell(COL_ITEM_NAME).setValue(name);
			}
		}

		addRowValueToMapForEdit(table, row, key, newValue);
	}

	/**
	 * output actionAddItem_actionPerformed method
	 */
	public void actionAddItem_actionPerformed(ActionEvent e) throws Exception {
		if (getItemTable() != null) {
			addItem(getItemTable());
			appendFootRow(getItemTable());
		}
	}

	/**
	 * output actionInsertItem_actionPerformed method
	 */
	public void actionInsertItem_actionPerformed(ActionEvent e) throws Exception {
		if (getItemTable() != null) {
			insertItem(getItemTable());
			appendFootRow(getItemTable());
		}
	}

	/**
	 * output actionCopyLine_actionPerformed method
	 */
	public void actionCopyLine_actionPerformed(ActionEvent e) throws Exception {
	}

	/**
	 * output actionRemoveItem_actionPerformed method
	 */
	public void actionRemoveItem_actionPerformed(ActionEvent e) throws Exception {

		if (getItemTable() != null) {
			removeItem(getItemTable());
			appendFootRow(getItemTable());
			// 实现删除后的焦点策略 2007-10-09 haiti_yang
			if (getItemTable().getRowCount() == 0) {
				FocusTraversalPolicy policy = null;
				Container container = null;
				Component initComponent = null;
				if (this.getFocusTraversalPolicy() != null
						&& this.getFocusTraversalPolicy() instanceof UIFocusTraversalPolicy) {
					policy = this.getFocusTraversalPolicy();
					container = this;
					Component[] traverComponent = ((UIFocusTraversalPolicy) policy).getComponents();
					for (int i = 0; i < traverComponent.length; i++) {
						if (traverComponent[i] == this.getItemTable()) {
							initComponent = traverComponent[i];
							break;
						}
					}
					if (initComponent == null) {
						initComponent = policy.getLastComponent(container);
						initComponent.requestFocusInWindow();
					} else if (initComponent != null) {
						Component component = policy.getComponentBefore(container, initComponent);
						while ((component instanceof IKDTextComponent) == false || component.isEnabled() == false) {
							component = policy.getComponentBefore(container, component);
						}
						component.requestFocusInWindow();
					}
				} else if (policy == null) {
					if (this.getUIWindow() instanceof Dialog) {
						policy = ((Dialog) uiWindow).getFocusTraversalPolicy();
						container = (Dialog) uiWindow;
					} else if (this.getUIWindow() instanceof Window) {
						policy = ((Window) uiWindow).getFocusTraversalPolicy();
						container = (Window) uiWindow;
					}

					if (policy != null) {
						try {
							Component component = policy.getComponentBefore(container, getItemTable());
							while ((component instanceof IKDTextComponent) == false || component.isEnabled() == false) {
								component = policy.getComponentBefore(container, component);
							}
							component.requestFocusInWindow();
						} catch (Exception ex) {
						}
					}
				}
			}
		}
	}

	/**
	 * 在指定表格中新增行（新增到最后一行）
	 * 
	 * @param table
	 */
	protected void addItem(KDTable table) {
		if (table == null) {
			return;
		}
		beforeAddItem(table);
		IObjectValue detailData = createNewItemData(table);
		if (detailData != null) {
			IRow row = table.addRow();
			// getUILifeCycleHandler().fireOnAddNewLine(table,detailData);
			loadItemFields(table, row, detailData);
			afterAddItem(table, detailData);
		}
	}

	/**
	 * 在指定表格中插入行（在当前选中行前插入，如果当前未选中任何行的话，则新增到最后一行）
	 * 
	 * @param table
	 */
	protected void insertItem(KDTable table) {
		if (table == null) {
			return;
		}
		beforeInsertItem(table);
		IObjectValue detailData = createNewItemData(table);
		IRow row = null;

		if (table.getSelectManager().size() > 0) {
			int top = table.getSelectManager().get().getTop();

			if (isTableColumnSelected(table)) {
				row = table.addRow();
			} else {
				row = table.addRow(top);
			}
		} else {
			row = table.addRow();
		}

		// getUILifeCycleHandler().fireOnAddNewLine(table,detailData);
		loadItemFields(table, row, detailData);
		afterInsertItem(table, detailData);
	}

	/**
	 * 在指定表格中删除当前选中行 增加隔行删除功能 2007-03-12
	 * 
	 * @param table
	 */
	protected void removeItem(KDTable table) {
		if (table == null) {
			return;
		}

		beforeRemoveItem(table);
		if ((table.getSelectManager().size() == 0))
		// || isTableColumnSelected(table))
		{
			MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_NoneEntry"));

			return;
		}

		// [begin]进行删除分录的提示处理。
		if (confirmRemove()) {
			// 获取选择块的总个数
			KDTSelectManager selectManager = table.getSelectManager();
			int size = selectManager.size();
			KDTSelectBlock selectBlock = null;
			// 因为先择块的顺序可能并非是表中行的顺序，所以先要排序使选择块的顺序正好是由小到大
			Set indexSet = new HashSet();

			for (int blockIndex = 0; blockIndex < size; blockIndex++) {
				selectBlock = selectManager.get(blockIndex);
				int top = selectBlock.getBeginRow();
				int bottom = selectBlock.getEndRow();
				if (table.getRow(top) == null) {
					MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_NoneEntry"));
					return;
				}
				for (int i = top; i <= bottom; i++) {
					indexSet.add(new Integer(i));
				}
			}
			Integer[] indexArr = new Integer[indexSet.size()];
			Object[] indexObj = indexSet.toArray();
			System.arraycopy(indexObj, 0, indexArr, 0, indexArr.length);
			Arrays.sort(indexArr);
			if (indexArr == null)
				return;
			for (int i = indexArr.length - 1; i >= 0; i--) {
				int rowIndex = Integer.parseInt(String.valueOf(indexArr[i]));
				IObjectValue detailData = (IObjectValue) table.getRow(rowIndex).getUserObject();
				table.removeRow(rowIndex);
				IObjectCollection collection = (IObjectCollection) table.getUserObject();

				if (collection == null) {
					logger.error("collection not be binded to table");
				} else {
					// Modify By Jacky Zhang
					if (detailData != null) {
						int index = getCollectionIndex(collection, detailData);
						// 避免有合计行的分录处理。
						if (index >= 0 && collection.size() > index) {
							collection.removeObject(index);
						}
					}
				}
				afterRemoveItem(table, detailData);
			}

			// 如果现在有记录定位到第一行
			if (table.getRow(0) != null)
				table.getSelectManager().select(0, 0);
		}
	}

	/**
	 * 增加单据行前的相关处理
	 */
	protected void beforeAddItem(KDTable table) {
		// 选择细目之前，必须先选择分录
		FDCClientVerifyHelper.checkSelected(this, getEntryTable());
	}

	/**
	 * 插入单据行前的相关处理
	 */
	protected void beforeInsertItem(KDTable table) {
		// 选择细目之前，必须先选择分录
		FDCClientVerifyHelper.checkSelected(this, getEntryTable());
	}

	/**
	 * 删除单据行前的相关处理
	 */
	protected void beforeRemoveItem(KDTable table) {
		// 选择细目之前，必须先选择分录
		FDCClientVerifyHelper.checkSelected(this, getEntryTable());
	}

	/**
	 * 增加单据行后的相关处理
	 */
	protected void afterAddItem(KDTable table, IObjectValue lineData) {
	}

	/**
	 * 插入单据行后的相关处理
	 */
	protected void afterInsertItem(KDTable table, IObjectValue lineData) {
	}

	/**
	 * 删除单据行后的相关处理
	 */
	protected void afterRemoveItem(KDTable table, IObjectValue lineData) {
	}

	/**
	 * 选中单据行前的相关处理
	 */

	protected void beforeSelectItem(KDTable table, IObjectValue lineData) {
		// 选择细目之前，必须先选择分录
		FDCClientVerifyHelper.checkSelected(this, getEntryTable());
	}

	/**
	 * 选中单据行后的相关处理
	 */
	protected void afterSelectItem(KDTable table, IObjectValue lineData) {
	}

	/**
	 * 新建单据行，返回一个新的分录行的默认值
	 */
	protected IObjectValue createNewItemData(KDTable table) {
		ProjectTargetShowItemInfo newData = new ProjectTargetShowItemInfo();
		newData.setId(BOSUuid.create(newData.getBOSType()));

		newData.setBill(editData);
		// 父实体
		ProjectTargetShowEntryInfo parent = (ProjectTargetShowEntryInfo) getEntryTable().getRow(curEntryIndex)
				.getUserObject();
		newData.setParent(parent);

		return newData;
	}

	/**
	 * 新建单据行，返回一个新的分录行的默认值
	 */
	protected IObjectValue createNewItemData(IRow row) {
		ProjectTargetShowItemInfo newData = new ProjectTargetShowItemInfo();
		newData.setId(BOSUuid.create(newData.getBOSType()));

		// 单据
		newData.setBill(editData);
		// 父实体
		ProjectTargetShowEntryInfo parent = (ProjectTargetShowEntryInfo) getEntryTable().getRow(curEntryIndex)
				.getUserObject();
		newData.setParent(parent);

		return newData;
	}

	/**
	 * 显示单据行
	 */
	protected void loadItemFields(KDTable table, IRow row, IObjectValue obj) {
		addItemTableLine(row, (ProjectTargetShowItemInfo) obj);
	}

	/**
	 * 保存单据行
	 */
	protected void storeItemFields(KDTable table, IRow row, IObjectValue obj) {
		// super.storeItemFields(table, row, obj);
		ProjectTargetShowItemInfo info = (ProjectTargetShowItemInfo) obj;

		Object cellValue = null;

		cellValue = row.getCell(COL_ITEM_ID).getValue();
		if (cellValue != null) {
			info.setId(BOSUuid.read(cellValue.toString()));
		} else {
			info.setId(null);
		}

		cellValue = row.getCell(COL_ITEM_SEQ_NUM).getValue();
		if (cellValue != null) {
			info.setSeqNum(cellValue.toString());
		} else {
			info.setSeqNum(null);
		}

		cellValue = row.getCell(COL_ITEM_NUMBER).getValue();
		if (cellValue != null) {
			info.setNumber(cellValue.toString());
		} else {
			info.setNumber(null);
		}

		cellValue = row.getCell(COL_ITEM_NAME).getValue();
		if (cellValue != null) {
			info.setName(cellValue.toString());
		} else {
			info.setName(null);
		}

		cellValue = row.getCell(COL_ITEM_PROJECT_TARGET_ENTRY).getValue();
		info.setProjectTargetEntry((ProjectTargetEntryInfo) cellValue);

		cellValue = row.getCell(COL_ITEM_BILL_ID).getValue();
		if (cellValue != null) {
			info.setBill((ProjectTargetShowBillInfo) cellValue);
		} else {
			info.setBill(null);
		}

		cellValue = row.getCell(COL_ITEM_PARENT_ID).getValue();
		if (cellValue != null) {
			info.setParent((ProjectTargetShowEntryInfo) cellValue);
		} else {
			info.setParent(null);
		}
	}

	// ////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////

	/**
	 * 描述：
	 * 
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-4
	 */
	protected KDTable getItemTable() {
		// TODO Auto-generated method stub
		return kdtItems;
	}

	// ////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////

	// 因为目前ObjectValue比较是按值比较，但集合中使用，如果分录值相同，
	// 都会删除找到的第一个，会出错。自行实现按指针比较。2007-2-5
	private int getCollectionIndex(IObjectCollection collection, IObjectValue obj) {
		int index = -1;
		if (collection == null) {
			return index;
		}
		for (int i = collection.size() - 1; i >= 0; i--) {
			if (obj == collection.getObject(i)) {
				index = i;
				return index;
			}
		}
		return index;
	}
	
	// //////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////

	@Override
	protected void kdtEntries_tableSelectChanged(KDTSelectEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.kdtEntries_tableSelectChanged(e);

		tblEntry_tableSelectChanged(e);
	}

	// //////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.kingdee.eas.fdc.basedata.mobile.client.AbstractProjectTargetShowEditUI#actionAutoGetData_actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionAutoGetData_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionAutoGetData_actionPerformed(e);

		getItemTable().removeRows();
		getEntryTable().removeRows();
		
		editData.getEntries().clear();
		itemMap = null;
		
		// 创建项目指标显示分录
		createProjectTargetShowEntrys(this.editData);
		
		isReGetData = true;
		// 加载分录数据行
		loadEntryData();
		FDCTableHelper.selectRow(getEntryTable(), 0);
		isReGetData = false;
		
		this.isModify = true;
	}

}