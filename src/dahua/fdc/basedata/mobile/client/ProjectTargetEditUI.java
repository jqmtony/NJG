/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.mobile.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.CharUtils;
import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.ISortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.MeasureUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.basedata.DataTypeEnum;
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
import com.kingdee.eas.fdc.basedata.mobile.ProjectTargetBillFactory;
import com.kingdee.eas.fdc.basedata.mobile.ProjectTargetBillInfo;
import com.kingdee.eas.fdc.basedata.mobile.ProjectTargetEntryCollection;
import com.kingdee.eas.fdc.basedata.mobile.ProjectTargetEntryInfo;
import com.kingdee.eas.fdc.basedata.util.FdcCollectionUtil;
import com.kingdee.eas.fdc.basedata.util.FdcObjectCollectionUtil;
import com.kingdee.eas.fdc.basedata.util.FdcObjectUtil;
import com.kingdee.eas.fdc.basedata.util.FdcStringUtil;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ProjectTargetEditUI extends AbstractProjectTargetEditUI {
	private static final Logger logger = CoreUIObject.getLogger(ProjectTargetEditUI.class);

	protected static final String COL_ENTRY_TARGET_GROUP = "targetGroup";
	protected static final String COL_ENTRY_ID = "id";
	protected static final String COL_ENTRY_SEQ_NUM = "seqNum";
	protected static final String COL_ENTRY_NUMBER = "number";
	protected static final String COL_ENTRY_NAME = "name";
	protected static final String COL_ENTRY_DATA_TYPE = "dataType";
	protected static final String COL_ENTRY_UNIT = "unit";
	protected static final String COL_ENTRY_DESCRIPTION = "description";
	protected static final String COL_ENTRY_IS_PRE_DEFINE = "isPreDefine";
	protected static final String COL_ENTRY_IS_AUTO_GET_DATA = "isAutoGetData";
	protected static final String COL_ENTRY_IS_ENABLE = "isEnable";

	// ////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////

	/** 表格_面板_映射 */
	protected Map<KDTable, KDPanel> tablePanelMap = new LinkedHashMap<KDTable, KDPanel>();
	/** 面板_表格_映射 */
	protected Map<KDPanel, KDTable> panelTableMap = new LinkedHashMap<KDPanel, KDTable>();
	/** 指标分组_表格_映射 */
	protected Map<TargetGroupEnum, KDTable> targetGroupTableMap = new LinkedHashMap<TargetGroupEnum, KDTable>();
	/** 表格_指标分组_映射 */
	protected Map<KDTable, TargetGroupEnum> tableTargetGroupMap = new LinkedHashMap<KDTable, TargetGroupEnum>();

	// ////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////

	// 旧值Map
	private Map<String, ProjectTargetEntryInfo> oldValueMap = new LinkedHashMap<String, ProjectTargetEntryInfo>();
	// 新值Map
	private Map<String, ProjectTargetEntryInfo> newValueMap = new LinkedHashMap<String, ProjectTargetEntryInfo>();

	// 是否已经改变
	private boolean isModify = false;

	// 是否已经初始化表格
	private boolean isInitTable = false;

	// ////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////

	/**
	 * output class constructor
	 */
	public ProjectTargetEditUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();

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

		editData.getEntries().clear();
		List<KDTable> tableList = getTableList();
		for (KDTable table : tableList) {
			TargetGroupEnum targetGroup = getTargetGroup(table);
			for (int i = 0, size = table.getRowCount(); i < size; i++) {
				IRow row = table.getRow(i);

				// 绑定数据
				bindingData(targetGroup, row, true);
			}
		}
	}

	/**
	 * 描述：
	 * 
	 * @param targetGroup
	 * @param row
	 * @param isAddToCols
	 * @author rd_skyiter_wang
	 * @createDate 2014-8-22
	 */
	private void bindingData(TargetGroupEnum targetGroup, IRow row, boolean isAddToCols) {

		// //////////////////////////////////////////////////////////////////////////
		// 分录
		int entrySeq = row.getRowIndex();
		ProjectTargetEntryInfo entryInfo = (ProjectTargetEntryInfo) row.getUserObject();
		if (null == entryInfo) {
			entryInfo = (ProjectTargetEntryInfo) createNewEntryData(targetGroup, row);

			row.setUserObject(entryInfo);
		}
		if (isAddToCols) {
			editData.getEntries().add(entryInfo);
		}

		if (null == entryInfo.getId()) {
			entryInfo.setId(BOSUuid.create(entryInfo.getBOSType()));
		}
		entryInfo.setSeq(++entrySeq);

		entryInfo.setTargetGroup((TargetGroupEnum) row.getCell(COL_ENTRY_TARGET_GROUP).getValue());
		entryInfo.setSeqNum((String) row.getCell(COL_ENTRY_SEQ_NUM).getValue());
		entryInfo.setNumber((String) row.getCell(COL_ENTRY_NUMBER).getValue());
		entryInfo.setName((String) row.getCell(COL_ENTRY_NAME).getValue());
		entryInfo.setDataType((DataTypeEnum) row.getCell(COL_ENTRY_DATA_TYPE).getValue());
		entryInfo.setUnit((MeasureUnitInfo) row.getCell(COL_ENTRY_UNIT).getValue());
		entryInfo.setDescription((String) row.getCell(COL_ENTRY_DESCRIPTION).getValue());
		entryInfo.setIsPreDefine((Boolean) row.getCell(COL_ENTRY_IS_PRE_DEFINE).getValue());
		entryInfo.setIsAutoGetData((Boolean) row.getCell(COL_ENTRY_IS_AUTO_GET_DATA).getValue());
		entryInfo.setIsEnable((Boolean) row.getCell(COL_ENTRY_IS_ENABLE).getValue());

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

		// /////////////////////////////////////////////////////////////////

		// 加载单据头
		loadHead();

		// 加载分录
		loadEntries();

		// /////////////////////////////////////////////////////////////////

		// 保存旧的数据
		editData.put("oldProjectTargetInfo", this.editData.clone());

		// 加载字段后
		afterLoadFields();

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

		ProjectTargetEntryCollection entries = editData.getEntries();
		for (int i = 0; i < entries.size(); i++) {
			ProjectTargetEntryInfo entryInfo = (ProjectTargetEntryInfo) entries.get(i);
			TargetGroupEnum targetGroup = entryInfo.getTargetGroup();
			KDTable table = getTable(targetGroup);

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

		oldValueMap.clear();
		newValueMap.clear();
		Map<String, ProjectTargetEntryInfo> entryMap = FdcObjectCollectionUtil.parseStringIdMap(entries);
		oldValueMap.putAll(entryMap);
		newValueMap.putAll(entryMap);
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
		return ProjectTargetBillFactory.getRemoteInstance();
	}

	@Override
	protected KDTable getDetailTable() {
		// TODO Auto-generated method stub
		// return super.getDetailTable();

		return null;
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
		// if (isEditting || isSuperEditting) {
		// table.getColumn(COL_ENTRY_SEQ_NUM).getStyleAttributes().setHided(false);
		// }
		table.getColumn(COL_ENTRY_TARGET_GROUP).getStyleAttributes().setHided(true);
		table.getColumn(COL_ENTRY_SEQ_NUM).getStyleAttributes().setHided(!isSuperEditting);
		table.getColumn(COL_ENTRY_NUMBER).getStyleAttributes().setHided(!isSuperEditting);

		// /////////////////////////////////////////////////////////////////////

		// 编辑器
		table.getColumn(COL_ENTRY_SEQ_NUM).setEditor(FdcEditorHelper.getTextEditor(80));
		table.getColumn(COL_ENTRY_NUMBER).setEditor(FdcEditorHelper.getTextEditor(80));
		table.getColumn(COL_ENTRY_NAME).setEditor(FdcEditorHelper.getTextEditor(255));
		// 初始化数据类型下拉框编辑器
		initDataTypeComboBoxEditor(table);
		// 初始化计量单位F7编辑器
		initMeasureUnitF7Editor(table);
		table.getColumn(COL_ENTRY_DESCRIPTION).setEditor(FdcEditorHelper.getTextEditor(255));

		// /////////////////////////////////////////////////////////////////////

		// 必录
		table.getColumn(COL_ENTRY_SEQ_NUM).setRequired(true);
		table.getColumn(COL_ENTRY_NUMBER).setRequired(true);
		table.getColumn(COL_ENTRY_NAME).setRequired(true);
		table.getColumn(COL_ENTRY_DATA_TYPE).setRequired(true);

		// /////////////////////////////////////////////////////////////////////

		// 排序管理器
		KDTSortManager sortManager = getSortManager(table);
		sortManager.setSortAuto(true);
		table.getColumn(COL_ENTRY_SEQ_NUM).setSortable(true);
		table.setSortMange(sortManager);
	}

	/**
	 * 描述：初始化数据类型下拉框编辑器
	 * 
	 * @param table
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-5
	 */
	private void initDataTypeComboBoxEditor(KDTable table) {
		KDComboBox combDataType = new KDComboBox();
		combDataType.addItems(DataTypeEnum.getEnumList().toArray());

		// 编辑器
		table.getColumn(COL_ENTRY_DATA_TYPE).setEditor(FdcEditorHelper.getKDComboBoxEditor(combDataType));
		// 无需渲染器
	}

	/**
	 * 描述：初始化计量单位F7编辑器
	 * 
	 * @param table
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-5
	 */
	private void initMeasureUnitF7Editor(KDTable table) {
		KDBizPromptBox prmtBox = new KDBizPromptBox();

		FilterInfo filterInfo = new FilterInfo();
		FdcF7InitHelper.initMeasureUnitF7(prmtBox, this, filterInfo);

		// 编辑器
		table.getColumn(COL_ENTRY_UNIT).setEditor(FdcEditorHelper.getBizPromptEditor(prmtBox));
		// 渲染器
		table.getColumn(COL_ENTRY_UNIT).setRenderer(FdcNameRender.getInstance());
	}

	protected void initWorkButton() {
		super.initWorkButton();
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
				// 排序
				table.getSortMange().sort(colIndex, ISortManager.SORT_ASCEND);
			}
		}
		// 数据类型
		else if (COL_ENTRY_DATA_TYPE.equals(key)) {
			DataTypeEnum dataTypeEnum = (DataTypeEnum) newValue;

			if (DataTypeEnum.BOOL.equals(dataTypeEnum)) {
				row.getCell(COL_ENTRY_UNIT).setValue(null);
				row.getCell(COL_ENTRY_UNIT).getStyleAttributes().setLocked(true);
			} else {
				row.getCell(COL_ENTRY_UNIT).getStyleAttributes().setLocked(false);
			}
		}

		// 添加对象值到Map中
		addRowValueToMapForEdit(newValueMap, row, key, newValue);

		// 设置是否已经修改
		this.setModify(true);
	}

	/**
	 * 描述：添加行对象值到Map中(用作编辑)
	 * 
	 * @param map
	 * @param row
	 * @param key
	 * @param newValue
	 * @author rd_skyiter_wang
	 * @createDate 2014-8-7
	 */
	private void addRowValueToMapForEdit(Map<String, ProjectTargetEntryInfo> map, IRow row, String key, Object newValue) {
		ProjectTargetEntryInfo entryInfo = (ProjectTargetEntryInfo) row.getUserObject();
		if (null == entryInfo) {
			return;
		}
		String idStr = entryInfo.getId().toString();

		TargetGroupEnum targetGroup = entryInfo.getTargetGroup();
		// 绑定数据
		bindingData(targetGroup, row, false);

		map.put(idStr, entryInfo);
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

		// 只有(超级)编辑状态下，才能新增、插入、删除
		if (flag) {
			// containerMain.addButton(btnAddEntry);
			// // containerMain.addButton(btnInsertEntry);
			// containerMain.addButton(btnRemoveEntry);

			toolBar.addComponentAfterComponent(btnAddEntry, btnRemove);
			// toolBar.addComponentAfterComponent(btnInsertEntry, btnAddEntry);
			toolBar.addComponentAfterComponent(btnRemoveEntry, btnAddEntry);

			actionAddEntry.setVisible(true);
			// actionInsertEntry.setVisible(true);
			actionRemoveEntry.setVisible(true);
		}

		// /////////////////////////////////////////////////////////////////////

		actionAddEntry.setEnabled(flag);
		// actionInsertEntry.setEnabled(isSuperEditting);
		actionRemoveEntry.setEnabled(flag);

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

		// table.getColumn(COL_ENTRY_SEQ_NUM).getStyleAttributes().setHided(!flag);
		table.getColumn(COL_ENTRY_SEQ_NUM).getStyleAttributes().setHided(!isSuperEditting);
		table.getColumn(COL_ENTRY_NUMBER).getStyleAttributes().setHided(!isSuperEditting);

		table.getStyleAttributes().setLocked(!isSuperEditting);
		table.getColumn(COL_ENTRY_SEQ_NUM).getStyleAttributes().setLocked(!flag);
		table.getColumn(COL_ENTRY_IS_ENABLE).getStyleAttributes().setLocked(!flag);
	}

	// //////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////

	/**
	 * 新建单据行，返回一个新的分录行的默认值
	 */
	protected IObjectValue createNewEntryData(KDTable table) {
		ProjectTargetEntryInfo newData = new ProjectTargetEntryInfo();
		newData.setId(BOSUuid.create(newData.getBOSType()));

		// 单据
		newData.setBill(editData);

		newData.setIsPreDefine(isSuperEditting);
		newData.setIsAutoGetData(false);
		newData.setIsEnable(true);

		TargetGroupEnum targetGroup = getTargetGroup(table);
		newData.setTargetGroup(targetGroup);

		return newData;
	}

	/**
	 * 新建单据行，返回一个新的分录行的默认值
	 */
	protected IObjectValue createNewEntryData(TargetGroupEnum targetGroup, IRow row) {
		ProjectTargetEntryInfo newData = new ProjectTargetEntryInfo();
		newData.setId(BOSUuid.create(newData.getBOSType()));

		// 单据
		newData.setBill(editData);

		newData.setIsPreDefine(isSuperEditting);
		newData.setIsAutoGetData(false);
		newData.setIsEnable(true);

		newData.setTargetGroup(targetGroup);

		return newData;
	}

	/**
	 * 显示单据行
	 */
	public void loadEntryFields(KDTable table, IRow row, IObjectValue obj) {
		// super.loadEntryFields(table, row, obj);
		ProjectTargetEntryInfo info = (ProjectTargetEntryInfo) obj;

		row.getCell(COL_ENTRY_TARGET_GROUP).setValue(info.getTargetGroup());
		// row.getCell(COL_ENTRY_ID).setValue(info.getId());
		if (null == info.getSeqNum()) {
			int seq = 10000 + row.getRowIndex() + 1;
			
			// 初始化排序编码
			initSeqNum(seq, info);
		}
		row.getCell(COL_ENTRY_SEQ_NUM).setValue(info.getSeqNum());
		row.getCell(COL_ENTRY_NUMBER).setValue(info.getNumber());
		row.getCell(COL_ENTRY_NAME).setValue(info.getName());
		row.getCell(COL_ENTRY_DATA_TYPE).setValue(info.getDataType());
		row.getCell(COL_ENTRY_UNIT).setValue(info.getUnit());
		row.getCell(COL_ENTRY_DESCRIPTION).setValue(info.getDescription());
		row.getCell(COL_ENTRY_IS_PRE_DEFINE).setValue(info.isIsPreDefine());
		row.getCell(COL_ENTRY_IS_AUTO_GET_DATA).setValue(info.isIsAutoGetData());
		row.getCell(COL_ENTRY_IS_ENABLE).setValue(info.isIsEnable());

		// ///////////////////////////////////////////////////////////////////////////////

		boolean flag = isEditting || isSuperEditting;
		if (flag) {
			row.getCell(COL_ENTRY_NAME).getStyleAttributes().setLocked(!flag);
			row.getCell(COL_ENTRY_DATA_TYPE).getStyleAttributes().setLocked(!flag);
			row.getCell(COL_ENTRY_UNIT).getStyleAttributes().setLocked(!flag);
			row.getCell(COL_ENTRY_DESCRIPTION).getStyleAttributes().setLocked(!flag);
			row.getCell(COL_ENTRY_IS_ENABLE).getStyleAttributes().setLocked(!flag);
		}

		ICell cell = row.getCell(COL_ENTRY_IS_PRE_DEFINE);
		Object value = cell.getValue();
		if (Boolean.FALSE.equals(value)) {
			row.getStyleAttributes().setFontColor(Color.RED);
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
		ProjectTargetEntryInfo info = (ProjectTargetEntryInfo) obj;

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

		cellValue = row.getCell(COL_ENTRY_DATA_TYPE).getValue();
		if (cellValue != null) {
			info.setDataType((DataTypeEnum) cellValue);
		} else {
			info.setDataType(null);
		}

		cellValue = row.getCell(COL_ENTRY_UNIT).getValue();
		if (cellValue != null) {
			info.setUnit((MeasureUnitInfo) cellValue);
		} else {
			info.setUnit(null);
		}

		cellValue = row.getCell(COL_ENTRY_DESCRIPTION).getValue();
		info.setDescription((String) cellValue);
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
		ProjectTargetBillInfo newData = new ProjectTargetBillInfo();

		// ////////////////////////////////////////////////////////////////

		createNewData(newData);
		if (null == newData.getId()) {
			newData.setId(BOSUuid.create(newData.getBOSType()));
		}
		
		// ////////////////////////////////////////////////////////////////

		// 编码、名称，设置默认值，不可编辑
		// String createTimeFormate = DateFormatUtils.format(newData.getCreateTime(), "yyyyMMdd-HHmmss");
		String createTimeFormate = "001";

		String entityName = "ProjectTarget";
		String number = "NUM-" + entityName + "-" + createTimeFormate;
		newData.setNumber(number);

		String entityAlias = "项目指标";
		String name = "NAM-" + entityAlias + "-" + createTimeFormate;
		newData.setName(name);
		
		String description = entityAlias;
		newData.setDescription(description);
		
		// ////////////////////////////////////////////////////////////////

		return newData;
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
		// // 启用
		// filter.appendFilterItem("isEnable", Boolean.TRUE);

		return filter;
	}

	// ////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////

	/**
	 * 描述：
	 * 
	 * @param table
	 * @author rd_skyiter_wang
	 * @createDate 2014-10-31
	 * @see com.kingdee.eas.fdc.basedata.mobile.client.FdcMobileEditUI#beforeRemoveEntry(com.kingdee.bos.ctrl.kdf.table.KDTable)
	 */
	@Override
	protected void beforeRemoveEntry(KDTable table) {
		// TODO Auto-generated method stub
		super.beforeRemoveEntry(table);

		FDCTableHelper.checkSelectedAndAbort(this, table);
		if (isSuperEditting) {
			return;
		}

		List valueList = FDCClientHelper.getSelectedObjectValues(table, COL_ENTRY_IS_PRE_DEFINE);
		if (valueList.contains(Boolean.TRUE)) {
			MsgBox.showWarning(this, "不允许删除预定义的指标项");
			SysUtil.abort();
		}
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
		boolean flag = isEditting || isSuperEditting;
		boolean isView = getOprtState().equals(OprtState.VIEW);
		if (isView) {
			table.getStyleAttributes().setLocked(true);
		}

		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			ICell cell = row.getCell(COL_ENTRY_IS_PRE_DEFINE);
			Object value = cell.getValue();
			if (Boolean.FALSE.equals(value)) {
				row.getStyleAttributes().setFontColor(Color.RED);
			}

			if (isView) {
				continue;
			}

			if (isEditting && Boolean.FALSE.equals(value)) {
				row.getCell(COL_ENTRY_NAME).getStyleAttributes().setLocked(!flag);
				row.getCell(COL_ENTRY_UNIT).getStyleAttributes().setLocked(!flag);
				row.getCell(COL_ENTRY_DATA_TYPE).getStyleAttributes().setLocked(!flag);
				row.getCell(COL_ENTRY_DESCRIPTION).getStyleAttributes().setLocked(!flag);
			} else if (isSuperEditting) {
				row.getCell(COL_ENTRY_NAME).getStyleAttributes().setLocked(!flag);
				row.getCell(COL_ENTRY_UNIT).getStyleAttributes().setLocked(!flag);
				row.getCell(COL_ENTRY_DATA_TYPE).getStyleAttributes().setLocked(!flag);
				row.getCell(COL_ENTRY_DESCRIPTION).getStyleAttributes().setLocked(!flag);
			}

			// 如果“数据类型”为布尔型，则“单位”不可编辑
			DataTypeEnum dataTypeEnum = (DataTypeEnum) row.getCell(COL_ENTRY_DATA_TYPE).getValue();
			if (DataTypeEnum.BOOL.equals(dataTypeEnum)) {
				row.getCell(COL_ENTRY_UNIT).setValue(null);
				row.getCell(COL_ENTRY_UNIT).getStyleAttributes().setLocked(true);
			} else {
				row.getCell(COL_ENTRY_UNIT).getStyleAttributes().setLocked(false);
			}

			// “启用”始终可编辑
			row.getCell(COL_ENTRY_IS_ENABLE).getStyleAttributes().setLocked(false);
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
		tableList.add(kdtProjectSchedu);
		tableList.add(kdtCostFinance);
		tableList.add(kdtSaleStock);
		tableList.add(kdtOther);

		// ////////////////////////////////////////////////////////////////

		tablePanelMap.put(kdtEntries, baseInfoPanel);
		tablePanelMap.put(kdtProjectSchedu, projectScheduPanel);
		tablePanelMap.put(kdtCostFinance, costFinancePanel);
		tablePanelMap.put(kdtSaleStock, saleStockPanel);
		tablePanelMap.put(kdtOther, otherPanel);

		panelTableMap.put(baseInfoPanel, kdtEntries);
		panelTableMap.put(projectScheduPanel, kdtProjectSchedu);
		panelTableMap.put(costFinancePanel, kdtCostFinance);
		panelTableMap.put(saleStockPanel, kdtSaleStock);
		panelTableMap.put(otherPanel, kdtOther);

		targetGroupTableMap.put(TargetGroupEnum.BASE_INFO_$_EX, kdtEntries);
		targetGroupTableMap.put(TargetGroupEnum.PROJECT_SCHEDULE_$_EX, kdtProjectSchedu);
		targetGroupTableMap.put(TargetGroupEnum.COST_FINANCE_$_EX, kdtCostFinance);
		targetGroupTableMap.put(TargetGroupEnum.SALE_STOCK_$_EX, kdtSaleStock);
		targetGroupTableMap.put(TargetGroupEnum.OTHER_$_EX, kdtOther);

		tableTargetGroupMap.put(kdtEntries, TargetGroupEnum.BASE_INFO_$_EX);
		tableTargetGroupMap.put(kdtProjectSchedu, TargetGroupEnum.PROJECT_SCHEDULE_$_EX);
		tableTargetGroupMap.put(kdtCostFinance, TargetGroupEnum.COST_FINANCE_$_EX);
		tableTargetGroupMap.put(kdtSaleStock, TargetGroupEnum.SALE_STOCK_$_EX);
		tableTargetGroupMap.put(kdtOther, TargetGroupEnum.OTHER_$_EX);

		// ////////////////////////////////////////////////////////////////

		return tableList;
	}

	protected KDPanel getPanel(KDTable table) {
		KDPanel panel = null;

		if (null != tablePanelMap) {
			panel = tablePanelMap.get(table);
		}

		return panel;
	}

	protected KDTable getTable(KDPanel panel) {
		KDTable table = null;

		if (null != panelTableMap) {
			table = panelTableMap.get(panel);
		}

		return table;
	}

	protected KDTable getTable(TargetGroupEnum targetGroup) {
		KDTable table = null;

		if (null != targetGroupTableMap) {
			table = targetGroupTableMap.get(targetGroup);
		}

		return table;
	}

	protected TargetGroupEnum getTargetGroup(KDTable table) {
		TargetGroupEnum targetGroup = null;

		if (null != tableTargetGroupMap) {
			targetGroup = tableTargetGroupMap.get(table);
		}

		return targetGroup;
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

		List<KDTable> tableList = getTableList();

		for (KDTable table : tableList) {
			if (null != table) {
				try {
					// 验证表格非空
					FDCClientVerifyHelper.verifyInput(this, table);

					// 验证表格列值是否重复
					FDCClientVerifyHelper.verifyTableCoulmnValueDup(this, table, COL_ENTRY_SEQ_NUM);
					FDCClientVerifyHelper.verifyTableCoulmnValueDup(this, table, COL_ENTRY_NUMBER);
					FDCClientVerifyHelper.verifyTableCoulmnValueDup(this, table, COL_ENTRY_NAME);
				} catch (Exception e) {
					KDPanel panel = getPanel(table);
					kDTabbedPane1.setSelectedComponent(panel);

					throw e;
				}
			}
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.kingdee.eas.fdc.basedata.mobile.client.FdcMobileEditUI#getSelectedTable()
	 */
	@Override
	protected KDTable getSelectedTable() {
		KDPanel panel = (KDPanel) kDTabbedPane1.getSelectedComponent();
		KDTable table = getTable(panel);

		// TODO Auto-generated method stub
		return table;
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
		sic.add(new SelectorItemInfo("entries.number"));
		sic.add(new SelectorItemInfo("entries.name"));
		sic.add(new SelectorItemInfo("entries.isPreDefine"));
		sic.add(new SelectorItemInfo("entries.isAutoGetData"));
		sic.add(new SelectorItemInfo("entries.isEnable"));
		sic.add(new SelectorItemInfo("entries.targetGroup"));
		sic.add(new SelectorItemInfo("entries.unit.id"));
		sic.add(new SelectorItemInfo("entries.unit.number"));
		sic.add(new SelectorItemInfo("entries.unit.name"));
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

		if (isModify()) {
			// 绑定数据
			bindingData();

			getBizInterface().submit(editData);

			// //////////////////////////////////////////////////////////////////////////

			// this.loadFields();
			oldValueMap.clear();
			newValueMap.clear();
			this.setModify(false);
			this.setOprtState(STATUS_EDIT);
		}
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

		boolean isValueChange = !FdcObjectUtil.isEquals(oldValueMap, newValueMap);
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

	// //////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////

}