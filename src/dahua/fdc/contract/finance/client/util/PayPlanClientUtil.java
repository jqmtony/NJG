package com.kingdee.eas.fdc.finance.client.util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.render.CellCheckBoxRenderer;
import com.kingdee.bos.ctrl.kdf.util.render.CellTextRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.schedule.FDCScheduleCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fm.common.client.FMClientHelper;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.jdbc.rowset.IRowSet;

public class PayPlanClientUtil {
	
	/**
	 * 
	 * 描述：初始化表格金额输入控件
	 * 
	 */
	public static KDFormattedTextField initFormattedTextCell(KDTable tblMain,
			String name, int precision) {
		KDFormattedTextField formatField = new KDFormattedTextField(
				KDFormattedTextField.BIGDECIMAL_TYPE);
		formatField.setPrecision(precision);
		FMClientHelper.initCurrencyField(formatField);
		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(formatField);
		tblMain.getColumn(name).setEditor(editor);
		tblMain.getColumn(name).getStyleAttributes().setNumberFormat(
				getKDTCurrencyFormat(precision));
		tblMain.getColumn(name).getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		return formatField;
	}

	/**
	 * 
	 * 描述：初始化表格CheckBox
	 * 
	 */
	public static KDCheckBox initCheckBoxCell(KDTable tblMain, String name) {
		KDCheckBox box = new KDCheckBox();
		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(box);
		tblMain.getColumn(name).setEditor(editor);
		tblMain.getColumn(name).setRenderer(new CellCheckBoxRenderer());
		return box;
	}

	/**
	 * 
	 * 描述：初始化表格ComboBox
	 * 
	 */
	public static KDComboBox initComboBoxCell(KDTable tblMain, String name) {
		KDComboBox box = new KDComboBox();
		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(box);
		tblMain.getColumn(name).setEditor(editor);
		tblMain.getColumn(name).setRenderer(new CellTextRender());
		return box;
	}

	/**
	 * 
	 * 描述：初始化DatePicker
	 * 
	 */
	public static KDDatePicker initDateCell(KDTable tblMain, String name) {
		KDDatePicker box = new KDDatePicker();
		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(box);
		tblMain.getColumn(name).setEditor(editor);
		tblMain.getColumn(name).getStyleAttributes().setNumberFormat(
				"yyyy-MM-dd");
		return box;
	}

	/**
	 * 
	 * 描述：初始化表格f7控件
	 * 
	 */
	public static KDBizPromptBox initF7Cell(KDTable tblMain, String name) {
		KDBizPromptBox box = new KDBizPromptBox();
		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(box);
		tblMain.getColumn(name).setEditor(editor);
		return box;
	}
	
	/**
	 * 初始化长度为80的文字录入
	 * @param tblMain
	 * @param name
	 */
	public static void initTextColumn(KDTable tblMain, String name,int length){
		if(tblMain.getColumn(name) != null){
			KDTextField txt = new KDTextField();
			txt.setMaxLength(length);
			KDTDefaultCellEditor editor = new KDTDefaultCellEditor(txt);
			tblMain.getColumn(name).setEditor(editor);
		}
	}
	
	public static String getKDTCurrencyFormat(int precision){
		 return "%r-[ ]0." + precision + "n";
	}
	
	public static void initRequiedColumn(KDTable tblMain, String[] names,boolean requied){
		for (int i = 0; i < names.length; i++) {
			IColumn column = tblMain.getColumn(names[i]);
			if(column != null){
				column.setRequired(requied);
			}
		}

	}
	
	
	public static void doAutoMerge(KDTable tblMain,String columnKey,KDTEditEvent e) {
		if (tblMain.getColumn(e.getColIndex()).getKey()
				.equals(columnKey)) {
			int colIndex = tblMain.getColumnIndex(columnKey);
			Set set = tblMain.getMergeManager().getMergeBlockSet();

			Iterator it = set.iterator();
			while (it.hasNext()) {
				KDTMergeBlock b = (KDTMergeBlock) it.next();
				Object v = tblMain.getCell(b.getTop(), colIndex)
						.getValue();
				for (int i = b.getTop(); i <= b.getBottom(); i++) {
					tblMain.getCell(i, colIndex).setValue(v);
				}
			}

			Map map = new HashMap();

			for (int i = 0, rowCount = tblMain.getRowCount(); i < rowCount; i++) {
				Object v = tblMain.getCell(i, e.getColIndex()).getValue();

				if (i == e.getRowIndex()) {
					v = e.getValue();
				}
				if (v == null) {
					continue;
				}
				CoreBaseInfo info = (CoreBaseInfo) v;

				String key = info.getId().toString();
				if (map.containsKey(key)) {
					List lst = (List) map.get(key);
					lst.add(new Integer(i));
					map.put(key, lst);
				} else {
					List lst = new ArrayList();
					lst.add(new Integer(i));
					map.put(key, lst);
				}
			}

			tblMain.getMergeManager().removeAllMergeBlock();
			set = map.keySet();
			it = set.iterator();
			while (it.hasNext()) {
				String key = (String) it.next();

				List lst = (List) map.get(key);

				boolean mergeMode = false;
				for (int i = 0; i < lst.size(); i++) {
					int index = ((Integer) lst.get(i)).intValue();

					for (int j = i + 1; j < lst.size(); j++) {
						int nextIndex = ((Integer) lst.get(j)).intValue();
						// 还没进入融合模式
						if (!mergeMode) {
							if (nextIndex - index == j - i) {
								// 进入融合模式
								mergeMode = true;
							}
						} else {
							if (nextIndex - index != j - i) {
								nextIndex = ((Integer) lst.get(j - 1)).intValue();
								merge(tblMain, columnKey,index, nextIndex);
								mergeMode = false;
								i = j - 1;
								continue;
							}
						}
					}

					if (mergeMode) {
						int nextIndex = ((Integer) lst.get(lst.size() - 1)).intValue();
						merge(tblMain, columnKey,index, nextIndex);
						break;
					}
				}
			}
		}
	}

	private static void merge(KDTable tblMain,String columnKey,int start, int end) {
		int colIndex = tblMain.getColumnIndex(columnKey);
		tblMain.getMergeManager().mergeBlock(start, colIndex, end,
				colIndex, KDTMergeManager.SPECIFY_MERGE);
	}

}
