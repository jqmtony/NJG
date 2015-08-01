/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.mobile.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.CharUtils;
import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.ISortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.MeasureUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.basedata.DataTypeEnum;
import com.kingdee.eas.fdc.basedata.FdcStatisticsTypeEnum;
import com.kingdee.eas.fdc.basedata.TargetGroupEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.client.FdcEditorHelper;
import com.kingdee.eas.fdc.basedata.client.FdcF7InitHelper;
import com.kingdee.eas.fdc.basedata.client.FdcNameRender;
import com.kingdee.eas.fdc.basedata.mobile.FdcMobileEntryInfo;
import com.kingdee.eas.fdc.basedata.mobile.GlobalTargetBillFactory;
import com.kingdee.eas.fdc.basedata.mobile.GlobalTargetBillInfo;
import com.kingdee.eas.fdc.basedata.mobile.GlobalTargetEntryCollection;
import com.kingdee.eas.fdc.basedata.mobile.GlobalTargetEntryInfo;
import com.kingdee.eas.fdc.basedata.mobile.ProjectTargetEntryCollection;
import com.kingdee.eas.fdc.basedata.mobile.ProjectTargetEntryInfo;
import com.kingdee.eas.fdc.basedata.util.FdcObjectCollectionUtil;
import com.kingdee.eas.fdc.basedata.util.FdcStringUtil;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class GlobalTargetEditUI extends AbstractGlobalTargetEditUI {
	private static final Logger logger = CoreUIObject.getLogger(GlobalTargetEditUI.class);

	protected static final String COL_ENTRY_ID = "id";
	protected static final String COL_ENTRY_SEQ_NUM = "seqNum";
	protected static final String COL_ENTRY_NUMBER = "number";
	protected static final String COL_ENTRY_NAME = "name";
	protected static final String COL_ENTRY_PROJECT_TARGET_ENTRY = "projectTargetEntry";
	protected static final String COL_ENTRY_DATA_TYPE = "dataType";
	protected static final String COL_ENTRY_UNIT = "unit";
	protected static final String COL_ENTRY_STATISTICS_TYPE = "statisticsType";
	protected static final String COL_ENTRY_DESCRIPTION = "description";
	protected static final String COL_ENTRY_IS_PRE_DEFINE = "isPreDefine";
	protected static final String COL_ENTRY_IS_AUTO_GET_DATA = "isAutoGetData";
	protected static final String COL_ENTRY_IS_ENABLE = "isEnable";

	// ////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////

	// 是否重新取数
	private boolean isReGetData = false;

	// 分录集合
	protected GlobalTargetEntryCollection entryClos;

	// ////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////

	/**
	 * output class constructor
	 */
	public GlobalTargetEditUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * 加载数据到界面
	 */
	public void loadFields() {
		// 注销监听器
		detachListeners();

		super.loadFields();

		// 保存旧的数据
		editData.put("oldGlobalTargetInfo", this.editData.clone());

		afterLoadFields();

		// 注册监听器
		registeShortKey();

		// 最后加上监听器
		attachListeners();
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	@Override
	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return GlobalTargetBillFactory.getRemoteInstance();
	}

	@Override
	protected KDTable getDetailTable() {
		// TODO Auto-generated method stub
		return super.getDetailTable();
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
		table.getColumn(COL_ENTRY_SEQ_NUM).getStyleAttributes().setHided(!isSuperEditting);
		table.getColumn(COL_ENTRY_NUMBER).getStyleAttributes().setHided(!isSuperEditting);
		table.getColumn(COL_ENTRY_NAME).getStyleAttributes().setHided(!isSuperEditting);

		// /////////////////////////////////////////////////////////////////////

		// 编辑器
		table.getColumn(COL_ENTRY_SEQ_NUM).setEditor(FdcEditorHelper.getTextEditor(80));
		table.getColumn(COL_ENTRY_NUMBER).setEditor(FdcEditorHelper.getTextEditor(80));
		table.getColumn(COL_ENTRY_NAME).setEditor(FdcEditorHelper.getTextEditor(255));
		// 初始化项目指标分录F7编辑器
		initProjectTargetEntryF7Editor(table);
		// 初始化数据类型下拉框编辑器
		initDataTypeComboBoxEditor(table);
		// 初始化计量单位F7编辑器
		initMeasureUnitF7Editor(table);
		// 统计类型
		initStatisticsTypeComboBoxEditor(table);
		table.getColumn(COL_ENTRY_DESCRIPTION).setEditor(FdcEditorHelper.getTextEditor(255));

		// /////////////////////////////////////////////////////////////////////

		// 必录
		table.getColumn(COL_ENTRY_SEQ_NUM).setRequired(true);
		table.getColumn(COL_ENTRY_NUMBER).setRequired(true);
		// table.getColumn(COL_ENTRY_NAME).setRequired(true);
		table.getColumn(COL_ENTRY_PROJECT_TARGET_ENTRY).setRequired(true);

		// /////////////////////////////////////////////////////////////////////

		// 间接关联字段：始终锁定
		table.getColumn(COL_ENTRY_DATA_TYPE).getStyleAttributes().setLocked(true);
		table.getColumn(COL_ENTRY_UNIT).getStyleAttributes().setLocked(true);

		// /////////////////////////////////////////////////////////////////////

		// 间接关联字段：背景色
		table.getColumn(COL_ENTRY_DATA_TYPE).getStyleAttributes().setBackground(Color.LIGHT_GRAY);
		table.getColumn(COL_ENTRY_UNIT).getStyleAttributes().setBackground(Color.LIGHT_GRAY);

		// /////////////////////////////////////////////////////////////////////

		// 排序管理器
		KDTSortManager sortManager = getSortManager(table);
		sortManager.setSortAuto(true);
		table.getColumn(COL_ENTRY_SEQ_NUM).setSortable(true);
		table.setSortMange(sortManager);
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
		// 数字列
		filterInfo.appendFilterItem("dataType", DataTypeEnum.NUMBER_VALUE);
		FdcF7InitHelper.initProjectTargetEntryF7(prmtBox, this, filterInfo);

		// 编辑器
		table.getColumn(COL_ENTRY_PROJECT_TARGET_ENTRY).setEditor(FdcEditorHelper.getBizPromptEditor(prmtBox));
		// 渲染器
		table.getColumn(COL_ENTRY_PROJECT_TARGET_ENTRY).setRenderer(FdcNameRender.getInstance());
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

	/**
	 * 描述：初始化统计类型编辑器
	 * 
	 * @param table
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-5
	 */
	private void initStatisticsTypeComboBoxEditor(KDTable table) {
		KDComboBox combDataType = new KDComboBox();
		combDataType.addItems(FdcStatisticsTypeEnum.getEnumList().toArray());

		// 编辑器
		table.getColumn(COL_ENTRY_STATISTICS_TYPE).setEditor(FdcEditorHelper.getKDComboBoxEditor(combDataType));
		// 无需渲染器
	}

	protected void initWorkButton() {
		super.initWorkButton();
		
		// ////////////////////////////////////////////////////////////////

		remove(btnAutoGetData);
		
		// 只有(超级)编辑状态下，才能自动取数
		if (isSuperEditting) {
			toolBar.addComponentAfterComponent(btnAutoGetData, btnSubmit);
		}
		
		// ////////////////////////////////////////////////////////////////
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
				kdtEntries.getEditManager().editCellAt(rowIndex, colIndex);
				attachListeners();

				// return;
				// SysUtil.abort();
			} else {
				// 排序
				table.getSortMange().sort(colIndex, ISortManager.SORT_ASCEND);
			}
		}
		// 项目指标分录
		else if (COL_ENTRY_PROJECT_TARGET_ENTRY.equals(key)) {
			ProjectTargetEntryInfo projectTargetEntryInfo = (ProjectTargetEntryInfo) newValue;

			String name = null;
			DataTypeEnum dataTypeEnum = null;
			MeasureUnitInfo unitInfo = null;
			if (null != projectTargetEntryInfo) {
				name = projectTargetEntryInfo.getName();
				dataTypeEnum = projectTargetEntryInfo.getDataType();
				unitInfo = projectTargetEntryInfo.getUnit();

				// FdcStatisticsTypeEnum fdcStatisticsTypeEnum = (FdcStatisticsTypeEnum) row.getCell(
				// COL_ENTRY_STATISTICS_TYPE).getValue();
				// // count计数的时候，单位显示空
				// if (FdcStatisticsTypeEnum.COUNT.equals(fdcStatisticsTypeEnum)) {
				// unitInfo = null;
				// }
			}

			// 名称
			row.getCell(COL_ENTRY_NAME).setValue(name);
			// 数据类型
			row.getCell(COL_ENTRY_DATA_TYPE).setValue(dataTypeEnum);
			// 单位
			row.getCell(COL_ENTRY_UNIT).setValue(unitInfo);
		}
		// 数据类型
		else if (COL_ENTRY_DATA_TYPE.equals(key)) {
			DataTypeEnum dataTypeEnum = (DataTypeEnum) newValue;

			ICell isPreDefineCell = row.getCell(COL_ENTRY_IS_PRE_DEFINE);
			Object isPreDefineValue = isPreDefineCell.getValue();
			boolean flag = isEditting || isSuperEditting;

			if (isEditting && Boolean.FALSE.equals(isPreDefineValue)) {
				row.getCell(COL_ENTRY_PROJECT_TARGET_ENTRY).getStyleAttributes().setLocked(!flag);
				row.getCell(COL_ENTRY_STATISTICS_TYPE).getStyleAttributes().setLocked(!flag);
			} else if (isSuperEditting) {
				row.getCell(COL_ENTRY_PROJECT_TARGET_ENTRY).getStyleAttributes().setLocked(!flag);
				row.getCell(COL_ENTRY_STATISTICS_TYPE).getStyleAttributes().setLocked(!flag);
			}

			if (!DataTypeEnum.NUMBER.equals(dataTypeEnum)) {
				row.getCell(COL_ENTRY_STATISTICS_TYPE).setValue(FdcStatisticsTypeEnum.NONE);
				row.getCell(COL_ENTRY_STATISTICS_TYPE).getStyleAttributes().setLocked(true);
			}
		}
		// 统计类型
		else if (COL_ENTRY_STATISTICS_TYPE.equals(key)) {
			// FdcStatisticsTypeEnum fdcStatisticsTypeEnum = (FdcStatisticsTypeEnum) newValue;
			//
			// // count计数的时候，单位显示空
			// if (FdcStatisticsTypeEnum.COUNT.equals(fdcStatisticsTypeEnum)) {
			// row.getCell(COL_ENTRY_UNIT).setValue(null);
			// }
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
		super.resetComponent();

		// /////////////////////////////////////////////////////////////////////

		// 只有超级编辑状态下，才能修改"排序编码"、"编码"、"名称"等字段
		txtNumber.setEnabled(isSuperEditting);
		txtName.setEnabled(isSuperEditting);
		txtDescription.setEnabled(isSuperEditting);
		contState.setVisible(isSuperEditting);
		contAuditor.setVisible(isSuperEditting);
		contAuditTime.setVisible(isSuperEditting);

		// /////////////////////////////////////////////////////////////////////

		// 只有(超级)编辑状态下，才能自动取数
		if (isSuperEditting) {
			toolBar.addComponentAfterComponent(btnAutoGetData, btnSubmit);
		}
		
		btnAutoGetData.setEnabled(isSuperEditting);

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
		table.getColumn(COL_ENTRY_NAME).getStyleAttributes().setHided(!isSuperEditting);

		table.getStyleAttributes().setLocked(!isSuperEditting);
		table.getColumn(COL_ENTRY_SEQ_NUM).getStyleAttributes().setLocked(!flag);
		table.getColumn(COL_ENTRY_IS_ENABLE).getStyleAttributes().setLocked(!flag);

		// 间接关联字段：始终锁定
		table.getColumn(COL_ENTRY_DATA_TYPE).getStyleAttributes().setLocked(true);
		table.getColumn(COL_ENTRY_UNIT).getStyleAttributes().setLocked(true);
	}

	// //////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////

	/**
	 * 新建单据行，返回一个新的分录行的默认值
	 */
	protected IObjectValue createNewEntryData(KDTable table) {
		GlobalTargetEntryInfo newData = new GlobalTargetEntryInfo();
		newData.setId(BOSUuid.create(newData.getBOSType()));

		// 单据
		newData.setBill(editData);

		newData.setIsPreDefine(isSuperEditting);
		newData.setIsAutoGetData(false);
		newData.setIsEnable(true);

		return newData;
	}

	/**
	 * 显示单据行
	 */
	public void loadEntryFields(KDTable table, IRow row, IObjectValue obj) {
		// super.loadEntryFields(table, row, obj);
		GlobalTargetEntryInfo info = (GlobalTargetEntryInfo) obj;

		// row.getCell(COL_ENTRY_ID).setValue(info.getId());
		if (null == info.getSeqNum()) {
			int seq = 10000 + row.getRowIndex() + 1;
			
			// 初始化排序编码
			initSeqNum(seq, info);
		}
		row.getCell(COL_ENTRY_SEQ_NUM).setValue(info.getSeqNum());
		row.getCell(COL_ENTRY_NUMBER).setValue(info.getNumber());
		row.getCell(COL_ENTRY_NAME).setValue(info.getName());
		ProjectTargetEntryInfo projectTargetEntryInfo = info.getProjectTargetEntry();
		row.getCell(COL_ENTRY_PROJECT_TARGET_ENTRY).setValue(projectTargetEntryInfo);
		if (null != projectTargetEntryInfo) {
			row.getCell(COL_ENTRY_DATA_TYPE).setValue(projectTargetEntryInfo.getDataType());
			row.getCell(COL_ENTRY_UNIT).setValue(projectTargetEntryInfo.getUnit());
		}
		row.getCell(COL_ENTRY_STATISTICS_TYPE).setValue(info.getStatisticsType());
		row.getCell(COL_ENTRY_DESCRIPTION).setValue(info.getDescription());
		row.getCell(COL_ENTRY_IS_PRE_DEFINE).setValue(info.isIsPreDefine());
		row.getCell(COL_ENTRY_IS_AUTO_GET_DATA).setValue(info.isIsAutoGetData());
		row.getCell(COL_ENTRY_IS_ENABLE).setValue(info.isIsEnable());

		// ///////////////////////////////////////////////////////////////////////////////

		boolean flag = isEditting || isSuperEditting;
		if (flag) {
			row.getCell(COL_ENTRY_NAME).getStyleAttributes().setLocked(!flag);
			row.getCell(COL_ENTRY_PROJECT_TARGET_ENTRY).getStyleAttributes().setLocked(!flag);
			// row.getCell(COL_ENTRY_DATA_TYPE).getStyleAttributes().setLocked(!flag);
			// row.getCell(COL_ENTRY_UNIT).getStyleAttributes().setLocked(!flag);
			row.getCell(COL_ENTRY_STATISTICS_TYPE).getStyleAttributes().setLocked(!flag);
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
		GlobalTargetEntryInfo info = (GlobalTargetEntryInfo) obj;

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

		cellValue = row.getCell(COL_ENTRY_PROJECT_TARGET_ENTRY).getValue();
		if (cellValue != null) {
			info.setProjectTargetEntry((ProjectTargetEntryInfo) cellValue);
		} else {
			info.setProjectTargetEntry(null);
		}

		// //关联字段，不绑定
		// cellValue = row.getCell(COL_ENTRY_DATA_TYPE).getValue();
		// if (cellValue != null) {
		// info.setDataType((DataTypeEnum) cellValue);
		// } else {
		// info.setDataType(null);
		// }
		//
		// //关联字段，不绑定
		// cellValue = row.getCell(COL_ENTRY_UNIT).getValue();
		// if (cellValue != null) {
		// info.setUnit((MeasureUnitInfo) cellValue);
		// } else {
		// info.setUnit(null);
		// }

		cellValue = row.getCell(COL_ENTRY_STATISTICS_TYPE).getValue();
		if (cellValue != null) {
			info.setStatisticsType((FdcStatisticsTypeEnum) cellValue);
		} else {
			info.setStatisticsType(null);
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
		GlobalTargetBillInfo newData = new GlobalTargetBillInfo();

		// ////////////////////////////////////////////////////////////////

		createNewData(newData);
		if (null == newData.getId()) {
			newData.setId(BOSUuid.create(newData.getBOSType()));
		}
		
		// ////////////////////////////////////////////////////////////////

		// 编码、名称，设置默认值，不可编辑
		// String createTimeFormate = DateFormatUtils.format(newData.getCreateTime(), "yyyyMMdd-HHmmss");
		String createTimeFormate = "001";

		String entityName = "GlobalTarget";
		String number = "NUM-" + entityName + "-" + createTimeFormate;
		newData.setNumber(number);

		String entityAlias = "全局指标";
		String name = "NAM-" + entityAlias + "-" + createTimeFormate;
		newData.setName(name);
		
		String description = entityAlias;
		newData.setDescription(description);
		
		// ////////////////////////////////////////////////////////////////

		// 创建项目指标显示分录
		createGlobalTargetEntrys(newData);

		// ////////////////////////////////////////////////////////////////

		return newData;
	}

	/**
	 * 描述：创建全局指标分录
	 * 
	 * @param newData
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-13
	 */
	private void createGlobalTargetEntrys(GlobalTargetBillInfo newData) {
		// ////////////////////////////////////////////////////////////////

		// 获取项目指标分录
		ProjectTargetEntryCollection projectTargetEntryCollection = fetchProjectTargetEntrys();
		if (FdcObjectCollectionUtil.isEmpty(projectTargetEntryCollection)) {
			return;
		}

		// ////////////////////////////////////////////////////////////////

		GlobalTargetEntryCollection globalTargetEntryCollection = newData.getEntries();
		globalTargetEntryCollection.clear();

		Map targetGroupMap = FdcObjectCollectionUtil.parsePropertyMap(projectTargetEntryCollection, "targetGroup");
		Set targetGroupKeySet = targetGroupMap.keySet();
		int rowIndex = 0;
		for (Iterator iterator = targetGroupKeySet.iterator(); iterator.hasNext();) {
			Object targetGroupValue = (String) iterator.next();
			TargetGroupEnum targetGroup = null;
			if (targetGroupValue instanceof String) {
				targetGroup = TargetGroupEnum.getEnum((String) targetGroupValue);
			} else if (targetGroupValue instanceof TargetGroupEnum) {
				targetGroup = (TargetGroupEnum) targetGroupValue;
			}

			List infoList = (List) targetGroupMap.get(targetGroupValue);
			for (Iterator iterator2 = infoList.iterator(); iterator2.hasNext();) {
				ProjectTargetEntryInfo projectTargetEntryInfo = (ProjectTargetEntryInfo) iterator2.next();

				// if (!DataTypeEnum.NUMBER.equals(projectTargetEntryInfo.getDataType())
				// || !projectTargetEntryInfo.isIsEnable()) {
				// continue;
				// }
				
				GlobalTargetEntryInfo globalTargetEntryInfo = new GlobalTargetEntryInfo();
				globalTargetEntryInfo.setBill(newData);
				globalTargetEntryInfo.setId(BOSUuid.create(globalTargetEntryInfo.getBOSType()));

				globalTargetEntryInfo.setProjectTargetEntry(projectTargetEntryInfo);
				
				// globalTargetEntryInfo.setSeqNum(projectTargetEntryInfo.getSeqNum());
				// globalTargetEntryInfo.setNumber(projectTargetEntryInfo.getNumber());
				globalTargetEntryInfo.setName(projectTargetEntryInfo.getName());

				int seq = 10000 + ((rowIndex++) + 1);
				// 初始化排序编码
				initSeqNum(seq, globalTargetEntryInfo);
				
				globalTargetEntryInfo.setIsPreDefine(isSuperEditting);
				globalTargetEntryInfo.setIsEnable(true);
				
				// ////////////////////////////////////////////////////////////////

				globalTargetEntryCollection.add(globalTargetEntryInfo);
			}
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

		// 数字列
		filter.appendFilterItem("dataType", DataTypeEnum.NUMBER_VALUE);
		// 启用
		filter.appendFilterItem("isEnable", Boolean.TRUE);

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

		// 间接关联字段：始终锁定
		table.getColumn(COL_ENTRY_DATA_TYPE).getStyleAttributes().setLocked(true);
		table.getColumn(COL_ENTRY_UNIT).getStyleAttributes().setLocked(true);

		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);

			// 是否预定义
			ICell isPreDefineCell = row.getCell(COL_ENTRY_IS_PRE_DEFINE);
			Object isPreDefineValue = isPreDefineCell.getValue();
			if (Boolean.FALSE.equals(isPreDefineValue)) {
				row.getStyleAttributes().setFontColor(Color.RED);
			}

			// 数据类型
			ICell dataTypeCell = row.getCell(COL_ENTRY_DATA_TYPE);
			Object dataTypeValue = dataTypeCell.getValue();
			if (!DataTypeEnum.NUMBER.equals(dataTypeValue)) {
				row.getCell(COL_ENTRY_STATISTICS_TYPE).setValue(FdcStatisticsTypeEnum.NONE);
				row.getCell(COL_ENTRY_STATISTICS_TYPE).getStyleAttributes().setLocked(true);
			}

			// // 统计类型
			// ICell fdcStatisticsTypeCell = row.getCell(COL_ENTRY_STATISTICS_TYPE);
			// Object fdcStatisticsTypeValue = fdcStatisticsTypeCell.getValue();
			// // count计数的时候，单位显示空
			// if (FdcStatisticsTypeEnum.COUNT.equals(fdcStatisticsTypeValue)) {
			// row.getCell(COL_ENTRY_UNIT).setValue(null);
			// }

			if (isView) {
				continue;
			}

			if (isEditting && Boolean.FALSE.equals(isPreDefineValue)) {
				row.getCell(COL_ENTRY_NAME).getStyleAttributes().setLocked(!flag);
				row.getCell(COL_ENTRY_PROJECT_TARGET_ENTRY).getStyleAttributes().setLocked(!flag);
				// row.getCell(COL_ENTRY_DATA_TYPE).getStyleAttributes().setLocked(!flag);
				// row.getCell(COL_ENTRY_UNIT).getStyleAttributes().setLocked(!flag);
				row.getCell(COL_ENTRY_STATISTICS_TYPE).getStyleAttributes().setLocked(!flag);
				row.getCell(COL_ENTRY_DESCRIPTION).getStyleAttributes().setLocked(!flag);
			} else if (isSuperEditting) {
				row.getCell(COL_ENTRY_NAME).getStyleAttributes().setLocked(!flag);
				row.getCell(COL_ENTRY_PROJECT_TARGET_ENTRY).getStyleAttributes().setLocked(!flag);
				// row.getCell(COL_ENTRY_DATA_TYPE).getStyleAttributes().setLocked(!flag);
				// row.getCell(COL_ENTRY_UNIT).getStyleAttributes().setLocked(!flag);
				row.getCell(COL_ENTRY_STATISTICS_TYPE).getStyleAttributes().setLocked(!flag);
				row.getCell(COL_ENTRY_DESCRIPTION).getStyleAttributes().setLocked(!flag);
			}

			if (!DataTypeEnum.NUMBER.equals(dataTypeValue)) {
				row.getCell(COL_ENTRY_STATISTICS_TYPE).setValue(FdcStatisticsTypeEnum.NONE);
				row.getCell(COL_ENTRY_STATISTICS_TYPE).getStyleAttributes().setLocked(true);
			}
		}
	}

	// //////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.kingdee.eas.fdc.basedata.mobile.client.FdcMobileEditUI#verifyInputForSubmint()
	 */
	@Override
	protected void verifyInputForSubmint() throws Exception {
		// TODO Auto-generated method stub
		super.verifyInputForSubmint();

		// 验证表格列值是否重复
		FDCClientVerifyHelper.verifyTableCoulmnValueDup(this, getDetailTable(), COL_ENTRY_SEQ_NUM);
		FDCClientVerifyHelper.verifyTableCoulmnValueDup(this, getDetailTable(), COL_ENTRY_NUMBER);
		// FDCClientVerifyHelper.verifyTableCoulmnValueDup(this, getDetailTable(), COL_ENTRY_NAME);
		// 验证表格列值是否重复(包括CoreBase对象.id)
		FDCClientVerifyHelper.verifyTableCoulmnValueDup(this, getDetailTable(), COL_ENTRY_PROJECT_TARGET_ENTRY, true);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.kingdee.eas.fdc.basedata.mobile.client.AbstractGlobalTargetEditUI#getSelectors()
	 */
	@Override
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();

		return sic;
	}

	// //////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.kingdee.eas.fdc.basedata.mobile.client.AbstractGlobalTargetEditUI#actionAutoGetData_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionAutoGetData_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionAutoGetData_actionPerformed(e);

		getEntryTable().removeRows();

		editData.getEntries().clear();

		// 创建项目指标显示分录
		createGlobalTargetEntrys(this.editData);

		isReGetData = true;
		// 加载分录数据行
		loadEntryData();
		FDCTableHelper.selectRow(getEntryTable(), 0);
		isReGetData = false;

		// this.isModify = true;
	}
	


	// 加载分录数据行
	protected void loadEntryData() {
		KDTable table = getEntryTable();
		table.checkParsed();
		table.removeRows();

		IObjectCollection entryCols = getEntryClos();

		GlobalTargetEntryInfo entryInfo = null;
		IRow row = null;
		for (int i = 0, size = entryCols.size(); i < size; i++) {
			entryInfo = (GlobalTargetEntryInfo) entryCols.getObject(i);
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
		}

		// //////////////////////////////////////////////////////////////////////

		if (null == entryClos) {
			entryClos = new GlobalTargetEntryCollection();
		}

		return entryClos;
	}

	// //////////////////////////////////////////////////////////////////////

}