/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.mobile.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.ISortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.DataTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
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
import com.kingdee.eas.fdc.basedata.mobile.ProjectTargetValueBillFactory;
import com.kingdee.eas.fdc.basedata.mobile.ProjectTargetValueBillInfo;
import com.kingdee.eas.fdc.basedata.mobile.ProjectTargetValueEntryCollection;
import com.kingdee.eas.fdc.basedata.mobile.ProjectTargetValueEntryInfo;
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
public class ProjectTargetValueEditUI extends AbstractProjectTargetValueEditUI {
	private static final Logger logger = CoreUIObject.getLogger(ProjectTargetValueEditUI.class);

	protected static final String COL_ENTRY_TARGET_GROUP = "targetGroup";
	protected static final String COL_ENTRY_ID = "id";
	protected static final String COL_ENTRY_SEQ_NUM = "seqNum";
	// protected static final String COL_ENTRY_NUMBER = "number";
	// protected static final String COL_ENTRY_NAME = "name";
	protected static final String COL_ENTRY_PROJECT_TARGET_ENTRY = "projectTargetEntry";
	protected static final String COL_ENTRY_DATA_TYPE = "dataType";
	protected static final String COL_ENTRY_UNIT = "unit";
	protected static final String COL_ENTRY_VALUE = "value";
	protected static final String COL_ENTRY_IS_LIGHT = "isLight";
	protected static final String COL_ENTRY_DESCRIPTION = "description";
	// protected static final String COL_ENTRY_IS_PRE_DEFINE = "isPreDefine";
	// protected static final String COL_ENTRY_IS_AUTO_GET_DATA = "isAutoGetData";
	// protected static final String COL_ENTRY_IS_ENABLE = "isEnable";

	// ////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////

	/** ���_���_ӳ�� */
	protected Map<KDTable, KDPanel> tablePanelMap = new LinkedHashMap<KDTable, KDPanel>();
	/** ���_���_ӳ�� */
	protected Map<KDPanel, KDTable> panelTableMap = new LinkedHashMap<KDPanel, KDTable>();
	/** ָ�����_���_ӳ�� */
	protected Map<TargetGroupEnum, KDTable> targetGroupTableMap = new LinkedHashMap<TargetGroupEnum, KDTable>();
	/** ���_ָ�����_ӳ�� */
	protected Map<KDTable, TargetGroupEnum> tableTargetGroupMap = new LinkedHashMap<KDTable, TargetGroupEnum>();

	// ////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////

	// ��ֵMap
	private Map<String, ProjectTargetValueEntryInfo> oldValueMap = new LinkedHashMap<String, ProjectTargetValueEntryInfo>();
	// ��ֵMap
	private Map<String, ProjectTargetValueEntryInfo> newValueMap = new LinkedHashMap<String, ProjectTargetValueEntryInfo>();

	// �Ƿ��Ѿ��ı�
	private boolean isModify = false;

	// �Ƿ��Ѿ���ʼ�����
	private boolean isInitTable = false;

	// ////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////

	/**
	 * output class constructor
	 */
	public ProjectTargetValueEditUI() throws Exception {
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
	 * ������������
	 * 
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-2
	 */
	private void bindingData() {
		editData.setOrgUnit((FullOrgUnitInfo) prmtOrgUnit.getValue());
		editData.setCurProject((CurProjectInfo) prmtCurProject.getValue());
		editData.setYear(FDCNumberHelper.toBigDecimal(combYear.getSelectedItem()));
		editData.setMonth(FDCNumberHelper.toBigDecimal(combMonth.getSelectedItem()));
		
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

				// ������
				bindingData(targetGroup, row, true);
			}
		}
	}

	/**
	 * ������
	 * 
	 * @param targetGroup
	 * @param row
	 * @param isAddToCols
	 * @author rd_skyiter_wang
	 * @createDate 2014-8-22
	 */
	private void bindingData(TargetGroupEnum targetGroup, IRow row, boolean isAddToCols) {

		// //////////////////////////////////////////////////////////////////////////
		// ��¼
		int entrySeq = row.getRowIndex();
		ProjectTargetValueEntryInfo entryInfo = (ProjectTargetValueEntryInfo) row.getUserObject();
		if (null == entryInfo) {
			entryInfo = (ProjectTargetValueEntryInfo) createNewEntryData(targetGroup, row);

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
		entryInfo
				.setProjectTargetEntry((ProjectTargetEntryInfo) row.getCell(COL_ENTRY_PROJECT_TARGET_ENTRY).getValue());

		// entryInfo.setValue((String) row.getCell(COL_ENTRY_VALUE).getValue());
		// ����ֵ
		pareseValue(row, entryInfo);
		
		entryInfo.setIsLight((Boolean) row.getCell(COL_ENTRY_IS_LIGHT).getValue());
		entryInfo.setDescription((String) row.getCell(COL_ENTRY_DESCRIPTION).getValue());

		// //////////////////////////////////////////////////////////////////////////
	}

	// /////////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////////

	/**
	 * �������ݵ�����
	 */
	public void loadFields() {
		// ע��������
		detachListeners();

		super.loadFields();

		// /////////////////////////////////////////////////////////////////

		// ���ص���ͷ
		loadHead();

		// ���ط�¼
		loadEntries();

		// /////////////////////////////////////////////////////////////////

		// ����ɵ�����
		editData.put("oldProjectTargetValueInfo", this.editData.clone());

		// �����ֶκ�
		afterLoadFields();

		// ע�������
		registeShortKey();

		// �����ϼ�����
		attachListeners();
	}

	/**
	 * ���������ص���ͷ
	 * 
	 * @author rd_skyiter_wang
	 * @createDate 2014-8-12
	 */
	protected void loadHead() {
	}

	/**
	 * ���������ط�¼
	 * 
	 * @author rd_skyiter_wang
	 * @createDate 2014-8-12
	 */
	protected void loadEntries() {
		
		// //////////////////////////////////////////////////////////////////////////
		
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

		// ���سɱ���Ŀ��
		loadEntryRows();

		// ����������_�����༭
		reLockColumnForEdit();
	}

	/**
	 * ���������سɱ���Ŀ��
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

		ProjectTargetValueEntryCollection entries = editData.getEntries();
		for (int i = 0; i < entries.size(); i++) {
			ProjectTargetValueEntryInfo entryInfo = (ProjectTargetValueEntryInfo) entries.get(i);
			if (null == entryInfo.getSeqNum()) {
				continue;
			}
			
			TargetGroupEnum targetGroup = entryInfo.getTargetGroup();
			KDTable table = getTable(targetGroup);

			IRow row = table.addRow();
			loadEntryFields(table, row, entryInfo);
		}

		List<KDTable> tableList = getTableList();
		for (KDTable table : tableList) {
			if (null != table) {
				int colIndex = table.getColumnIndex(COL_ENTRY_SEQ_NUM);
				// ����
				table.getSortMange().sort(colIndex, ISortManager.SORT_ASCEND);
			}
		}

		// //////////////////////////////////////////////////////////////////////////
		// //////////////////////////////////////////////////////////////////////////

		oldValueMap.clear();
		newValueMap.clear();
		Map<String, ProjectTargetValueEntryInfo> entryMap = FdcObjectCollectionUtil.parseStringIdMap(entries);
		oldValueMap.putAll(entryMap);
		newValueMap.putAll(entryMap);
	}

	/**
	 * ����������������_�����༭
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
		return ProjectTargetValueBillFactory.getRemoteInstance();
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
	 * ��ͷ����
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

		// txtSeqNum.setEnabled(true);
		txtNumber.setEnabled(false);
		txtName.setEnabled(false);
		// txtDescription.setEnabled(true);

		// //////////////////////////////////////////////////////////////////////////

		Calendar calendar = Calendar.getInstance();
		
		BigDecimal yearObj = (null != editData) ? editData.getYear() : null;
		BigDecimal monthObj = (null != editData) ? editData.getMonth() : null;
		int currentYear = (null != yearObj) ? yearObj.intValue() : calendar.get(Calendar.YEAR);
		int currentMonth = (null != monthObj) ? monthObj.intValue() : calendar.get(Calendar.MONTH) + 1;
		
		// ǰ��20��
		for (int i = currentYear - 20, maxYear = currentYear + 20; i <= maxYear; i++) {
			combYear.addItem(new Integer(i));
		}
		// Ĭ��ѡ��ǰ��
		for (int i = 0; i < combYear.getItemCount(); i++) {
			int tempYear = Integer.parseInt(combYear.getItemAt(i).toString());
			if (tempYear == currentYear) {
				combYear.setSelectedIndex(i);
			}
		}

		// 1�µ�12��
		for (int i = 1, maxMonth = 12; i <= maxMonth; i++) {
			combMonth.addItem(new Integer(i));
		}
		// Ĭ��ѡ����
		for (int i = 0; i < combMonth.getItemCount(); i++) {
			int tempMonth = Integer.parseInt(combMonth.getItemAt(i).toString());
			if (tempMonth == currentMonth) {
				combMonth.setSelectedIndex(i);
			}
		}

		// //////////////////////////////////////////////////////////////////////////
	}

	/**
	 * ��������
	 * 
	 * @throws Exception
	 */
	protected void initTableStyle() throws Exception {
		super.initTableStyle();

		// ��ʼ������б�
		if (FdcCollectionUtil.isEmpty(getTableList())) {
			initTableList();
		}

		isInitTable = true;
	}

	/**
	 * ��������������
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
		table.getColumn(COL_ENTRY_SEQ_NUM).getStyleAttributes().setHided(true);
		// table.getColumn(COL_ENTRY_SEQ_NUM).getStyleAttributes().setHided(!isSuperEditting);

		// /////////////////////////////////////////////////////////////////////

		// �༭��
		table.getColumn(COL_ENTRY_SEQ_NUM).setEditor(FdcEditorHelper.getTextEditor(80));
		// ��ʼ����Ŀָ���¼F7�༭��
		initProjectTargetEntryF7Editor(table);
		table.getColumn(COL_ENTRY_VALUE).setEditor(FdcEditorHelper.getTextEditor(80));
		table.getColumn(COL_ENTRY_DESCRIPTION).setEditor(FdcEditorHelper.getTextEditor(255));
		
		// /////////////////////////////////////////////////////////////////////

		// ��¼
		table.getColumn(COL_ENTRY_SEQ_NUM).setRequired(true);

		// /////////////////////////////////////////////////////////////////////

		// ��ӹ����ֶΣ�ʼ������
		table.getColumn(COL_ENTRY_DATA_TYPE).getStyleAttributes().setLocked(true);
		table.getColumn(COL_ENTRY_UNIT).getStyleAttributes().setLocked(true);
		// ����
		table.getColumn(COL_ENTRY_PROJECT_TARGET_ENTRY).getStyleAttributes().setLocked(true);

		// /////////////////////////////////////////////////////////////////////

		// ��ӹ����ֶΣ�����ɫ
		table.getColumn(COL_ENTRY_DATA_TYPE).getStyleAttributes().setBackground(Color.LIGHT_GRAY);
		table.getColumn(COL_ENTRY_UNIT).getStyleAttributes().setBackground(Color.LIGHT_GRAY);

		// /////////////////////////////////////////////////////////////////////

		// ���������
		KDTSortManager sortManager = getSortManager(table);
		sortManager.setSortAuto(true);
		table.getColumn(COL_ENTRY_SEQ_NUM).setSortable(true);
		table.setSortMange(sortManager);
	}

	/**
	 * ��������ʼ����Ŀָ���¼F7�༭��
	 * 
	 * @param table
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-5
	 */
	private void initProjectTargetEntryF7Editor(KDTable table) {
		KDBizPromptBox prmtBox = new KDBizPromptBox();
		
		FilterInfo filterInfo = new FilterInfo();
		TargetGroupEnum targetGroup = getTargetGroup(table);
		filterInfo.appendFilterItem("targetGroup", targetGroup.getValue());
		FdcF7InitHelper.initProjectTargetEntryF7(prmtBox, this, filterInfo);
		
		table.getColumn(COL_ENTRY_PROJECT_TARGET_ENTRY).setEditor(FdcEditorHelper.getBizPromptEditor(prmtBox));
		// ��Ⱦ��
		table.getColumn(COL_ENTRY_PROJECT_TARGET_ENTRY).setRenderer(FdcNameRender.getInstance());
	}
	
	/**
	 * ��������ʼ��ֵ�༭��
	 * 
	 * @param table
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-5
	 */
	private void initValueEditor(KDTable table, IRow row) {
		ProjectTargetEntryInfo projectTargetEntryInfo = (ProjectTargetEntryInfo) row.getCell(
				COL_ENTRY_PROJECT_TARGET_ENTRY).getValue();
		
		ICell cell = row.getCell(COL_ENTRY_VALUE);
		ICellEditor editor = null;
		if (null == projectTargetEntryInfo) {
			editor = FdcEditorHelper.getTextEditor(80);
			cell.setEditor(editor);
			cell.setValue(null);
			
			return;
		} 
		
		DataTypeEnum dataTypeEnum = projectTargetEntryInfo.getDataType();
		if (DataTypeEnum.NUMBER.equals(dataTypeEnum)) {
			editor = FdcEditorHelper.getNumberEditor(2, false);
		} else if (DataTypeEnum.STRING.equals(dataTypeEnum)) {
			editor = FdcEditorHelper.getTextEditor(80);
		} else if (DataTypeEnum.BOOL.equals(dataTypeEnum)) {
			editor = FdcEditorHelper.getKDCheckBoxEditor(new KDCheckBox());
			
			// �����ͣ�����Ĭ��ֵ:False
			if (null == cell.getValue()) {
				cell.setValue(Boolean.FALSE);
			}
		} else if (DataTypeEnum.DATE.equals(dataTypeEnum)) {
			editor = FdcEditorHelper.getDateEditor();
			
			// ��ʽ��
			cell.getStyleAttributes().setNumberFormat(FDCClientHelper.DATE_FORMAT);
		}

		cell.setEditor(editor);
	}

	/**
	 * ��������ʽ��ֵ
	 * 
	 * @param table
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-5
	 */
	private void formateValue(KDTable table, IRow row, ProjectTargetValueEntryInfo info) {
		ProjectTargetEntryInfo projectTargetEntryInfo = (ProjectTargetEntryInfo) row.getCell(
				COL_ENTRY_PROJECT_TARGET_ENTRY).getValue();
		Object valueFormat = null;
		String value = info.getValue();
		if (null == projectTargetEntryInfo || null == value) {
			row.getCell(COL_ENTRY_VALUE).setValue(null);
			
			return;
		} 
		
		DataTypeEnum dataTypeEnum = projectTargetEntryInfo.getDataType();
		if (DataTypeEnum.NUMBER.equals(dataTypeEnum)) {
			valueFormat = FDCNumberHelper.toBigDecimal(value);
		} else if (DataTypeEnum.STRING.equals(dataTypeEnum)) {
			valueFormat = value;
		} else if (DataTypeEnum.BOOL.equals(dataTypeEnum)) {
			valueFormat = BooleanUtils.toBooleanObject(value);
		} else if (DataTypeEnum.DATE.equals(dataTypeEnum)) {
			String[] parsePatterns = { "yyyy-MM-dd", "MM-dd", "dd" };
			try {
				valueFormat = DateUtils.parseDate(value, parsePatterns);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		row.getCell(COL_ENTRY_VALUE).setValue(valueFormat);
	}

	/**
	 * ����������ֵ
	 * 
	 * @param table
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-5
	 */
	private void pareseValue(IRow row, ProjectTargetValueEntryInfo info) {
		ProjectTargetEntryInfo projectTargetEntryInfo = (ProjectTargetEntryInfo) row.getCell(
				COL_ENTRY_PROJECT_TARGET_ENTRY).getValue();
		Object valueObj = row.getCell(COL_ENTRY_VALUE).getValue();
		if (null == projectTargetEntryInfo || null == valueObj) {
			info.setValue(null);

			return;
		}

		String value = null;
		DataTypeEnum dataTypeEnum = projectTargetEntryInfo.getDataType();
		if (DataTypeEnum.NUMBER.equals(dataTypeEnum)) {
			value = ObjectUtils.toString(valueObj);
		} else if (DataTypeEnum.STRING.equals(dataTypeEnum)) {
			value = ObjectUtils.toString(valueObj);
		} else if (DataTypeEnum.BOOL.equals(dataTypeEnum)) {
			value = ObjectUtils.toString(valueObj);
		} else if (DataTypeEnum.DATE.equals(dataTypeEnum)) {
			value = DateFormatUtils.format((Date) valueObj, FDCClientHelper.DATE_FORMAT);
		}

		info.setValue(value);
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
	 * ��¼���༭�¼�
	 * 
	 * @throws Exception
	 */
	protected void afterEntryEditStopped(KDTable table, Object oldValue, Object newValue, int colIndex, int rowIndex)
			throws Exception {
		super.afterEntryEditStopped(table, oldValue, newValue, colIndex, rowIndex);

		String key = table.getColumnKey(colIndex);
		IRow row = table.getRow(rowIndex);

		// ������
		if (COL_ENTRY_SEQ_NUM.equals(key)) {
			String seqNum = (String) newValue;
			if (null != seqNum) {
				seqNum = seqNum.trim().toLowerCase();
			}
			
			if (FdcStringUtil.isNotBlank(seqNum) && !CharUtils.isAsciiAlpha(seqNum.charAt(0))) {
				MsgBox.showWarning(this, "������� ���� ����ĸ��ͷ");
				
				detachListeners();
				// ��յ�Ԫ��
				row.getCell(COL_ENTRY_SEQ_NUM).setValue(null);
				// ��ȡ����
				table.getEditManager().editCellAt(rowIndex, colIndex);
				attachListeners();
				
				// return;
				// SysUtil.abort();
			} else {
				// ����
				table.getSortMange().sort(colIndex, ISortManager.SORT_ASCEND);
			}
		} 
		// ����
		else if (COL_ENTRY_IS_LIGHT.equals(key)) {
			Boolean isLight = (Boolean) newValue;

			ICell discriptionCell = row.getCell(COL_ENTRY_DESCRIPTION);
			discriptionCell.getStyleAttributes().setLocked(!isLight);
			if (!isLight) {
				discriptionCell.setValue(null);
			}
		}

		// ��Ӷ���ֵ��Map��
		addRowValueToMapForEdit(newValueMap, row, key, newValue);

		// �����Ƿ��Ѿ��޸�
		this.setModify(true);
	}
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.kingdee.eas.fdc.basedata.mobile.client.FdcMobileEditUI#afterEntryEditChanged(com.kingdee.bos.ctrl.kdf.table.KDTable,
	 *      java.lang.Object, java.lang.Object, int, int)
	 */
	@Override
	protected void afterEntryEditChanged(KDTable table, Object oldValue, Object newValue, int colIndex, int rowIndex)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterEntryEditChanged(table, oldValue, newValue, colIndex, rowIndex);

		String key = table.getColumnKey(colIndex);
		IRow row = table.getRow(rowIndex);

		// ����
		if (COL_ENTRY_IS_LIGHT.equals(key)) {
			Boolean isLight = (Boolean) newValue;

			ICell discriptionCell = row.getCell(COL_ENTRY_DESCRIPTION);
			discriptionCell.getStyleAttributes().setLocked(!isLight);
			if (!isLight) {
				discriptionCell.setValue(null);
			}
		}
	}

	/**
	 * ����������ж���ֵ��Map��(�����༭)
	 * 
	 * @param map
	 * @param row
	 * @param key
	 * @param newValue
	 * @author rd_skyiter_wang
	 * @createDate 2014-8-7
	 */
	private void addRowValueToMapForEdit(Map<String, ProjectTargetValueEntryInfo> map, IRow row, String key, Object newValue) {
		ProjectTargetValueEntryInfo entryInfo = (ProjectTargetValueEntryInfo) row.getUserObject();
		if (null == entryInfo) {
			return;
		}
		String idStr = entryInfo.getId().toString();

		TargetGroupEnum targetGroup = entryInfo.getTargetGroup();
		// ������
		bindingData(targetGroup, row, false);

		map.put(idStr, entryInfo);
	}

	// //////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////

	/**
	 * �������
	 */
	/**
	 * ������
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

		// // ֻ��(����)�༭״̬�£��������������롢ɾ��
		// if (flag) {
		// // containerMain.addButton(btnAddEntry);
		// // // containerMain.addButton(btnInsertEntry);
		// // containerMain.addButton(btnRemoveEntry);
		//
		// toolBar.addComponentAfterComponent(btnAddEntry, btnRemove);
		// // toolBar.addComponentAfterComponent(btnInsertEntry, btnAddEntry);
		// toolBar.addComponentAfterComponent(btnRemoveEntry, btnAddEntry);
		//
		// actionAddEntry.setVisible(true);
		// // actionInsertEntry.setVisible(true);
		// actionRemoveEntry.setVisible(true);
		// }
		//
		// // /////////////////////////////////////////////////////////////////////
		//
		// actionAddEntry.setEnabled(flag);
		// // actionInsertEntry.setEnabled(isSuperEditting);
		// actionRemoveEntry.setEnabled(flag);

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

		// ֻ�г����༭״̬�£������޸�"�������"��"����"��"����"���ֶ�
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
	 * ������
	 * 
	 * @param table
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-1
	 */
	private void resetComponent(KDTable table) {
		boolean flag = isEditting || isSuperEditting;

		// table.getColumn(COL_ENTRY_SEQ_NUM).getStyleAttributes().setHided(!flag);
		// table.getColumn(COL_ENTRY_SEQ_NUM).getStyleAttributes().setHided(!isSuperEditting);
		table.getColumn(COL_ENTRY_SEQ_NUM).getStyleAttributes().setHided(true);

		table.getStyleAttributes().setLocked(!isSuperEditting);
		table.getColumn(COL_ENTRY_SEQ_NUM).getStyleAttributes().setLocked(!flag);
	}

	// //////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////

	/**
	 * �½������У�����һ���µķ�¼�е�Ĭ��ֵ
	 */
	protected IObjectValue createNewEntryData(KDTable table) {
		ProjectTargetValueEntryInfo newData = new ProjectTargetValueEntryInfo();
		newData.setId(BOSUuid.create(newData.getBOSType()));

		// ����
		newData.setBill(editData);

		TargetGroupEnum targetGroup = getTargetGroup(table);
		newData.setTargetGroup(targetGroup);

		return newData;
	}

	/**
	 * �½������У�����һ���µķ�¼�е�Ĭ��ֵ
	 */
	protected IObjectValue createNewEntryData(TargetGroupEnum targetGroup, IRow row) {
		ProjectTargetValueEntryInfo newData = new ProjectTargetValueEntryInfo();
		newData.setId(BOSUuid.create(newData.getBOSType()));

		// ����
		newData.setBill(editData);

		newData.setTargetGroup(targetGroup);

		return newData;
	}

	/**
	 * ��ʾ������
	 */
	public void loadEntryFields(KDTable table, IRow row, IObjectValue obj) {
		// super.loadEntryFields(table, row, obj);
		ProjectTargetValueEntryInfo info = (ProjectTargetValueEntryInfo) obj;

		row.getCell(COL_ENTRY_TARGET_GROUP).setValue(info.getTargetGroup());
		// row.getCell(COL_ENTRY_ID).setValue(info.getId());
		if (null == info.getSeqNum()) {
			int seq = 10000 + row.getRowIndex() + 1;
			
			// ��ʼ���������
			initSeqNum(seq, info);
		}
		row.getCell(COL_ENTRY_SEQ_NUM).setValue(info.getSeqNum());
		ProjectTargetEntryInfo projectTargetEntryInfo = info.getProjectTargetEntry();
		row.getCell(COL_ENTRY_PROJECT_TARGET_ENTRY).setValue(projectTargetEntryInfo);
		if (null != projectTargetEntryInfo) {
			row.getCell(COL_ENTRY_DATA_TYPE).setValue(projectTargetEntryInfo.getDataType());
			row.getCell(COL_ENTRY_UNIT).setValue(projectTargetEntryInfo.getUnit());
		}
		// row.getCell(COL_ENTRY_VALUE).setValue(info.getValue());
		// ��ʼ��ֵ��ʽ
		formateValue(table, row, info);
		row.getCell(COL_ENTRY_IS_LIGHT).setValue(info.isIsLight());
		row.getCell(COL_ENTRY_DESCRIPTION).setValue(info.getDescription());

		// ///////////////////////////////////////////////////////////////////////////////

		boolean flag = isEditting || isSuperEditting;
		if (flag) {
			row.getCell(COL_ENTRY_DESCRIPTION).getStyleAttributes().setLocked(!flag);
		}

		ICell cell = row.getCell(COL_ENTRY_PROJECT_TARGET_ENTRY);
		ProjectTargetEntryInfo value = (ProjectTargetEntryInfo) cell.getValue();
		if (Boolean.FALSE.equals(value.isIsPreDefine())) {
			row.getStyleAttributes().setFontColor(Color.RED);
		}

		// ///////////////////////////////////////////////////////////////////////////////

		row.setUserObject(info);
	}

	/**
	 * ��������ʼ���������
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
	 * ���浥����
	 */
	protected void storeEntryFields(KDTable table, IRow row, IObjectValue obj) {
		// super.storeEntryFields(table, row, obj);
		ProjectTargetValueEntryInfo info = (ProjectTargetValueEntryInfo) obj;

		Object cellValue = null;

		cellValue = row.getCell(COL_ENTRY_ID).getValue();
		if (cellValue != null) {
			info.setId(BOSUuid.read(cellValue.toString()));
		} else {
			info.setId(null);
		}
		
		cellValue = row.getCell(COL_ENTRY_TARGET_GROUP).getValue();
		if (cellValue != null) {
			info.setTargetGroup((TargetGroupEnum) cellValue);
		} else {
			info.setTargetGroup(null);
		}

		cellValue = row.getCell(COL_ENTRY_SEQ_NUM).getValue();
		if (cellValue != null) {
			info.setSeqNum(cellValue.toString());
		} else {
			info.setSeqNum(null);
		}

		cellValue = row.getCell(COL_ENTRY_PROJECT_TARGET_ENTRY).getValue();
		if (cellValue != null) {
			info.setProjectTargetEntry((ProjectTargetEntryInfo) cellValue);
		} else {
			info.setProjectTargetEntry(null);
		}

		// cellValue = row.getCell(COL_ENTRY_VALUE).getValue();
		// if (cellValue != null) {
		// info.setValue(cellValue.toString());
		// } else {
		// info.setValue(null);
		// }
		// ����ֵ
		pareseValue(row, info);

		cellValue = row.getCell(COL_ENTRY_IS_LIGHT).getValue();
		if (cellValue != null) {
			info.setIsLight((Boolean) cellValue);
		} else {
			info.setIsLight(false);
		}

		cellValue = row.getCell(COL_ENTRY_DESCRIPTION).getValue();
		info.setDescription((String) cellValue);
	}

	// //////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////

	/**
	 * ������
	 * 
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-10-31
	 * @see com.kingdee.eas.fdc.basedata.mobile.client.FdcMobileEditUI#createNewData()
	 */
	@Override
	protected IObjectValue createNewData() {
		ProjectTargetValueBillInfo newData = new ProjectTargetValueBillInfo();

		// ////////////////////////////////////////////////////////////////

		createNewData(newData);
		if (null == newData.getId()) {
			newData.setId(BOSUuid.create(newData.getBOSType()));
		}
		
		newData.setOrgUnit(orgUnitInfo);
		newData.setCurProject(curProject);
		
		// ////////////////////////////////////////////////////////////////
		
		// ���롢���ƣ�����Ĭ��ֵ�����ɱ༭
		String createTimeFormate = DateFormatUtils.format(newData.getCreateTime(), "yyyyMMdd-HHmmss");
		
		String entityName = curProject.getNumber();
		String number = "NUM-" + entityName + "-" + createTimeFormate;
		newData.setNumber(number);

		String entityAlias = curProject.getName();
		String name = "NAM-" + entityAlias + "-" + createTimeFormate;
		newData.setName(name);
		
		String description = entityAlias;
		newData.setDescription(description);
		
		// ////////////////////////////////////////////////////////////////

		// ������Ŀָ��ֵ��¼
		createProjectTargetValueEntrys(newData);

		// ////////////////////////////////////////////////////////////////

		return newData;
	}

	/**
	 * ������������Ŀָ��ֵ��¼
	 * 
	 * @param newData
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-3
	 */
	private void createProjectTargetValueEntrys(ProjectTargetValueBillInfo newData) {
		// ////////////////////////////////////////////////////////////////

		// ��ȡ��Ŀָ���¼
		ProjectTargetEntryCollection projectTargetEntryCollection = fetchProjectTargetEntrys();
		if (FdcObjectCollectionUtil.isEmpty(projectTargetEntryCollection)) {
			return;
		}

		// ////////////////////////////////////////////////////////////////
		
		ProjectTargetValueEntryCollection projectTargetValueEntryCollection = newData.getEntries();
		String projectName = newData.getCurProject().getName();

		for (int i = 0; i < projectTargetEntryCollection.size(); i++) {
			ProjectTargetEntryInfo projectTargetEntryInfo = projectTargetEntryCollection.get(i);
			if (!projectTargetEntryInfo.isIsEnable()) {
				continue;
			}

			ProjectTargetValueEntryInfo projectTargetValueEntryInfo = new ProjectTargetValueEntryInfo();
			projectTargetValueEntryInfo.setBill(newData);

			projectTargetValueEntryInfo.setId(BOSUuid.create(projectTargetValueEntryInfo.getBOSType()));
			projectTargetValueEntryInfo.setTargetGroup(projectTargetEntryInfo.getTargetGroup());
			projectTargetValueEntryInfo.setProjectTargetEntry(projectTargetEntryInfo);
			projectTargetValueEntryInfo.setSeqNum(projectTargetEntryInfo.getSeqNum());
			// projectTargetValueEntryInfo.setNumber(projectTargetEntryInfo.getNumber());
			// projectTargetValueEntryInfo.setName(projectTargetEntryInfo.getName());
			// projectTargetValueEntryInfo.setDescription(projectTargetEntryInfo.getDescription()
			// );

			// ////////////////////////////////////////////////////////////////

			// ��Ŀ���ƣ�����Ĭ��ֵ�����ɱ༭
			if ("��Ŀ����".equals(projectTargetEntryInfo.getName())) {
				projectTargetValueEntryInfo.setValue(projectName);
			}

			// ////////////////////////////////////////////////////////////////

			projectTargetValueEntryCollection.add(projectTargetValueEntryInfo);
		}
	}

	/**
	 * �����������Ŀָ���¼������
	 * 
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-13
	 */
	protected FilterInfo getProjectTargetEntrysFilter() {
		FilterInfo filter = new FilterInfo();

		// // ������
		// filter.appendFilterItem("dataType", DataTypeEnum.NUMBER_VALUE);
		// ����
		filter.appendFilterItem("isEnable", Boolean.TRUE);
		
		return filter;
	}

	/**
	 * ������
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

		// List valueList = FDCClientHelper.getSelectedObjectValues(table, COL_ENTRY_IS_PRE_DEFINE);
		// if (valueList.contains(Boolean.TRUE)) {
		// MsgBox.showWarning(this, "������ɾ��Ԥ�����ָ����");
		// SysUtil.abort();
		// }
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
	 * �����������ֶκ�
	 * 
	 * @param table
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-1
	 */
	protected void afterLoadFields(KDTable table) {
		// boolean flag = isEditting || isSuperEditting;
		boolean isView = getOprtState().equals(OprtState.VIEW);
		if (isView) {
			table.getStyleAttributes().setLocked(true);
		}

		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			ICell cell = row.getCell(COL_ENTRY_PROJECT_TARGET_ENTRY);
			Object value = cell.getValue();
			if (Boolean.FALSE.equals(((ProjectTargetEntryInfo) value).isIsPreDefine())) {
				row.getStyleAttributes().setFontColor(Color.RED);
			}
			// ��Ŀ���ƣ�����Ĭ��ֵ�����ɱ༭
			if ("��Ŀ����".equals(((ProjectTargetEntryInfo) value).getName())) {
				row.getCell(COL_ENTRY_VALUE).getStyleAttributes().setLocked(true);
				// ����ɫ����ɫ
				row.getCell(COL_ENTRY_VALUE).getStyleAttributes().setBackground(Color.RED);
			}
			
			// ��ʼ��ֵ�༭��
			initValueEditor(table, row);
			
			if (isView) {
				continue;
			}
			
			cell = row.getCell(COL_ENTRY_IS_LIGHT);
			value = cell.getValue();
			row.getCell(COL_ENTRY_DESCRIPTION).getStyleAttributes().setLocked(Boolean.FALSE.equals(value));
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

		// ////////////////////////////////////////////////////////////////
		// ////////////////////////////////////////////////////////////////

		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();

		filterItems.add(new FilterItemInfo("curProject.id", ((CurProjectInfo) prmtCurProject.getValue()).getId()
				.toString()));
		filterItems.add(new FilterItemInfo("year", ((Integer) combYear.getSelectedItem())));
		filterItems.add(new FilterItemInfo("month", ((Integer) combMonth.getSelectedItem())));
		filterItems.add(new FilterItemInfo("id", editData.getId().toString(), CompareType.NOTEQUALS));

		boolean isExists = getBizInterface().exists(filter);
		if (isExists) {
			MsgBox.showWarning(this, "��ǰ��Ŀ��ǰ�·ݣ�ֻ������һ����¼");
			SysUtil.abort();
		}

		// ////////////////////////////////////////////////////////////////
		// ////////////////////////////////////////////////////////////////

		List<KDTable> tableList = getTableList();

		for (KDTable table : tableList) {
			if (null != table) {
				try {
					// ��֤���ǿ�
					FDCClientVerifyHelper.verifyInput(this, table);
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
		sic.add(new SelectorItemInfo("entries.projectTargetEntry.*"));
		sic.add(new SelectorItemInfo("entries.projectTargetEntry.unit.id"));
		sic.add(new SelectorItemInfo("entries.projectTargetEntry.unit.number"));
		sic.add(new SelectorItemInfo("entries.projectTargetEntry.unit.name"));
		
		sic.add(new SelectorItemInfo("year"));
		sic.add(new SelectorItemInfo("month"));
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
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("orgUnit.*"));
		} else {
			sic.add(new SelectorItemInfo("orgUnit.id"));
			sic.add(new SelectorItemInfo("orgUnit.number"));
			sic.add(new SelectorItemInfo("orgUnit.name"));
		}
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("curProject.*"));
		} else {
			sic.add(new SelectorItemInfo("curProject.id"));
			sic.add(new SelectorItemInfo("curProject.number"));
			sic.add(new SelectorItemInfo("curProject.name"));
		}
		
		return sic;
	}

	// ////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillEditUI#actionSave_actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		this.actionSubmit_actionPerformed(e);
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		setSaveAction(false);

		// super.actionSubmit_actionPerformed(e);

		// ��֤����
		verifyInput(e);

		if (isModify()) {
			// ������
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
	 * �Ƿ��Ѿ��޸�
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
		
		// ��ͷ
		// //////////////////////////////////////////////////////////////////////////

		boolean tempIsModify = !FDCHelper.isEqual(editData.getYear(), FDCNumberHelper.toBigDecimal(combYear
				.getSelectedItem()));
		if (tempIsModify) {
			return tempIsModify;
		}
		tempIsModify = !FDCHelper.isEqual(editData.getMonth(), FDCNumberHelper.toBigDecimal(combMonth.getSelectedItem()));
		if (tempIsModify) {
			return tempIsModify;
		}
		tempIsModify = !FDCHelper.isEqual(editData.getNumber(), txtNumber.getText());
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

		// ����
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
	 * �����������Ƿ��Ѿ��޸�
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

		// txtSeqNum.setEnabled(true);
		txtNumber.setEnabled(false);
		txtName.setEnabled(false);
		// txtDescription.setEnabled(true);
	}

	// ////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////

	/**
	 * ��ָ������������У����������һ�У�
	 * 
	 * @param table
	 */
	protected void addEntry(KDTable table) {
		super.addEntry(table);

		setModify(true);
	}

	/**
	 * ��ָ������в����У��ڵ�ǰѡ����ǰ���룬�����ǰδѡ���κ��еĻ��������������һ�У�
	 * 
	 * @param table
	 */
	protected void insertEntry(KDTable table) {
		super.insertEntry(table);

		setModify(true);
	}

	/**
	 * ��ָ�������ɾ����ǰѡ���� ���Ӹ���ɾ������ 2007-03-12
	 * 
	 * @param table
	 */
	protected void removeEntry(KDTable table) {
		super.removeEntry(table);

		setModify(true);
	}
	
	// ////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////
	
}