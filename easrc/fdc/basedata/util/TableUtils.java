package com.kingdee.eas.fdc.basedata.util;
import java.awt.Component;
import java.awt.Rectangle;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTViewManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.framework.client.multiDetail.DetailPanel;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.util.enums.EnumUtils;

public class TableUtils {

    private static String strDataFormat = "#,##0.00;-#,##0.00";
	
	public TableUtils() {
		super();
	}
	
    /**
     * 描述：指定控件设置编辑
     * 
     * @param c
     * @param enable
     * @see com.kingdee.eas.fi.fa.basedata.FaUtils#enableComponent(Component c, boolean enable)
     */
    public static void enableComponent(CoreUIObject ui) {
    	Component[] components = ui.getComponents();
		Component component = null;
		for (int i = 0, size = components.length; i < size; i++) {
			component = components[i];
			if (component instanceof DetailPanel) {
				DetailPanel dp = (DetailPanel) component;
				Component[] dpcomponents = null;
				Component dpcomponent = null;
				dpcomponents = dp.getComponents();
				for (int j = 0, dpsize = dpcomponents.length; j < dpsize; j++) {
					dpcomponent = dpcomponents[j];
					KDPanel panel = (KDPanel) dpcomponent;
					Component[] pcomponents = null;
					Component pcomponent = null;
					pcomponents = panel.getComponents();
					for (int k = 0, psize = pcomponents.length; k < psize; k++) {
						pcomponent = pcomponents[k];
						if ("btnAddEntryLine".equals(pcomponent.getName())
								|| "btnInsertEntryLine".equals(pcomponent
										.getName())
								|| "btnRemoveEntryLine".equals(pcomponent
										.getName())
								|| "btnAddnewLine".equals(pcomponent.getName())
								|| "btnInsertLine".equals(pcomponent.getName())
								|| "btnRemoveLines"
										.equals(pcomponent.getName())
								|| "btnAddSwapLine"
										.equals(pcomponent.getName())
								|| "btnInsertSwapLine".equals(pcomponent
										.getName())
								|| "btnRemoveSwapLine".equals(pcomponent
										.getName())
								) {
							pcomponent.setEnabled(false);
						}
					}
				}
			} else {
				component.setEnabled(false);
			}
		}
    }
	/**
	 * 得到一个0－－1.0E17的Integer CellEditor
	 * @author chetao 		Date 2011-08-18
	 * @return
	 */
	public  static KDTDefaultCellEditor getCellIntegerNumberEdit(){
		KDFormattedTextField kdc = new KDFormattedTextField();
        kdc.setDataType(KDFormattedTextField.INTEGER_TYPE);
        kdc.setPrecision(0);
        kdc.setMinimumValue(FDCHelper.ZERO);
        kdc.setMaximumValue(FDCHelper.MAX_VALUE);
        kdc.setHorizontalAlignment(KDFormattedTextField.RIGHT);
        kdc.setSupportedEmpty(true);
        kdc.setVisible(true);
        kdc.setEnabled(true);
        KDTDefaultCellEditor editor = new KDTDefaultCellEditor(kdc);
        return editor;
	}
	
	/**
	 * 得到一个-1.0E17－－1.0E17的BigDecimal CellEditor
	 * @author chetao  		Date 2011-08-18
	 * @return
	 */
	public static KDTDefaultCellEditor getCellNumberEdit(){
		KDFormattedTextField kdc = new KDFormattedTextField();
        kdc.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
        kdc.setPrecision(2);
        kdc.setMinimumValue(FDCHelper.ZERO);
        kdc.setMaximumValue(FDCHelper.MAX_VALUE);
        kdc.setHorizontalAlignment(KDFormattedTextField.RIGHT);
        kdc.setSupportedEmpty(true);
        kdc.setVisible(true);
        kdc.setEnabled(true);
        KDTDefaultCellEditor editor = new KDTDefaultCellEditor(kdc);
        return editor;
	}
	//构建分录产品类型为f7控件
	public static KDBizPromptBox  getF7productType(String queryName,EntityViewInfo view){
		KDBizPromptBox f7productType = new KDBizPromptBox();
		f7productType.setQueryInfo(queryName);
		f7productType.setEntityViewInfo(view);
		f7productType.setEditable(true);
		f7productType.setDisplayFormat("$name$");
		f7productType.setEditFormat("$number$");
		f7productType.setCommitFormat("$number$");		
		return f7productType;
	}
	/**
	 * 设置合计行
	 * 可以对多列求和，
	 * @param columnName，列名数据
	 * **/
	public static void getFootRow(KDTable tblMain,String[] columnName){
		IRow footRow = null;
        KDTFootManager footRowManager = tblMain.getFootManager();
        if(footRowManager == null)
        {
            String total = EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Total");
            footRowManager = new KDTFootManager(tblMain);
            footRowManager.addFootView();
            tblMain.setFootManager(footRowManager);
            footRow = footRowManager.addFootRow(0);
            footRow.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
            tblMain.getIndexColumn().setWidthAdjustMode((short)1);
            tblMain.getIndexColumn().setWidth(30);
            footRowManager.addIndexText(0, total);
        } else
        {
            footRow = footRowManager.getFootRow(0);
        }
        int columnCount = tblMain.getColumnCount();
        for(int c = 0; c < columnCount; c++)
        {
            String fieldName = tblMain.getColumn(c).getKey();
            for(int i = 0; i < columnName.length; i++)
            {
                String colName = (String)columnName[i];
                if(colName.equalsIgnoreCase(fieldName))
                {
                    ICell cell = footRow.getCell(c);
                    cell.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                    cell.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
                    cell.getStyleAttributes().setFontColor(java.awt.Color.BLACK);
                    cell.setValue(getColumnValueSum(tblMain,colName));
                }
            }

        }
        footRow.getStyleAttributes().setBackground(new java.awt.Color(246, 246, 191));



	}
	
	
	/**
	 * 设置合计行
	 * 可以对多列求和，
	 * @param columnName，列名数据
	 * **/
	public static void getFootRow(KDTable tblMain,Set columnName){
		IRow footRow = null;
        KDTFootManager footRowManager = tblMain.getFootManager();
        if(footRowManager == null)
        {
            String total = EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Total");
            footRowManager = new KDTFootManager(tblMain);
            footRowManager.addFootView();
            tblMain.setFootManager(footRowManager);
            footRow = footRowManager.addFootRow(0);
            footRow.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
            tblMain.getIndexColumn().setWidthAdjustMode((short)1);
            tblMain.getIndexColumn().setWidth(30);
            footRowManager.addIndexText(0, total);
        } else
        {
            footRow = footRowManager.getFootRow(0);
        }
        int columnCount = tblMain.getColumnCount();
        for(int c = 0; c < columnCount; c++)
        {
        	if(columnName.contains(String.valueOf(c))){
        		
	            ICell cell = footRow.getCell(c);
	            cell.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
	            cell.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
	            cell.getStyleAttributes().setFontColor(java.awt.Color.BLACK);
	            if(c%4!=0){
	            	cell.setValue(getColumnValueSum(tblMain,c));
        	    }else{
        	    	cell.setValue(FDCHelper.divide(getColumnValueSum(tblMain,c+1),getColumnValueSum(tblMain,c-2) ));
        	    }
        	    	
        	}
        }
        footRow.getStyleAttributes().setBackground(new java.awt.Color(246, 246, 191));
	}
	
	
	/**
	 * 设置合计行
	 * 可以对多列求和，
	 * @param columnName，列名数据
	 * **/
	public static void getMonthFootRow(KDTable tblMain,Set columnName){
		IRow footRow = null;
        KDTFootManager footRowManager = tblMain.getFootManager();
        if(footRowManager == null)
        {
            String total = EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Total");
            footRowManager = new KDTFootManager(tblMain);
            footRowManager.addFootView();
            tblMain.setFootManager(footRowManager);
            footRow = footRowManager.addFootRow(0);
            footRow.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
            tblMain.getIndexColumn().setWidthAdjustMode((short)1);
            tblMain.getIndexColumn().setWidth(30);
            footRowManager.addIndexText(0, total);
        } else
        {
            footRow = footRowManager.getFootRow(0);
        }
        int columnCount = tblMain.getColumnCount();
        for(int c = 0; c < columnCount; c++)
        {
        	if(columnName.contains(String.valueOf(c))){
        		
	            ICell cell = footRow.getCell(c);
	            cell.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
	            cell.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
	            cell.getStyleAttributes().setFontColor(java.awt.Color.BLACK);
	            if(c%10!=6&&c%10!=7){
	            	cell.setValue(getColumnValueSum(tblMain,c));
        	    }else{
        	    	cell.setValue(FDCHelper.divide(getColumnValueSum(tblMain,c+2),getColumnValueSum(tblMain,c-4) ));
        	    }
        	    	
        	}
        }
        footRow.getStyleAttributes().setBackground(new java.awt.Color(246, 246, 191));
	}
	
    /**
     * 给指定table数字列求和
     * 
     * @param table 表格
     * @param columnName 表格列名
     */
    public static BigDecimal getColumnValueSum(KDTable table,int columnName) {
    	BigDecimal sum = new BigDecimal(0);
        for(int i=0;i<table.getRowCount();i++){
        	if(table.getRow(i).getCell(columnName).getValue()!=null 
        			&& table.getRow(i).getCell(columnName).getValue() instanceof BigDecimal)
        		sum = sum.add((BigDecimal)table.getRow(i).getCell(columnName).getValue());
        	else if(table.getRow(i).getCell(columnName).getValue()!=null 
        			&& table.getRow(i).getCell(columnName).getValue() instanceof String){
        		String value = (String)table.getRow(i).getCell(columnName).getValue();
        		value = value.replaceAll(",", "");
        		sum = sum.add(new BigDecimal(value));
        	}else if(table.getRow(i).getCell(columnName).getValue() instanceof Integer){
        		String value = table.getRow(i).getCell(columnName).getValue().toString();
        		sum = sum.add(new BigDecimal(value));
        	}
        }
        return sum;
    }
	/**
     * 获取表格选中行的某列值
     * SystemStatusCtrolUtils
     */
	public static String[] getSelectRowFieldValue(KDTable kdtTable, String fieldname)
    {
        if(!isSelectRow(kdtTable))
            return null;
        KDTSelectManager sm = kdtTable.getSelectManager();
        int blockCount = sm.size();
        int keyField = kdtTable.getColumnIndex(fieldname);
        ArrayList retList = new ArrayList();
        Object value = null;
        for(int i = 0; i < blockCount; i++)
        {
            KDTSelectBlock block = sm.get(i);
            int j = block.getTop();
            for(int bottom = block.getBottom(); j <= bottom; j++)
            {
                IRow row = kdtTable.getRow(j);
                value = row.getCell(keyField).getValue();
                if(value != null && value.toString().trim().length() != 0)
                {
                    String retStr = value.toString();
                    retList.add(retStr);
                }
            }

        }
        retList.trimToSize();
        if(retList.size() == 0)
        {
            return null;
        } else
        {
            String retStrs[] = new String[retList.size()];
            retStrs = (String[])retList.toArray(retStrs);
            return retStrs;
        }
    }

    public static boolean isSelectRow(KDTable kdtTable)
    {
        KDTSelectManager sm = kdtTable.getSelectManager();
        return sm.size() > 0;
    }
	
    /**
     * 冻结表格的前几列
     * 
     * @param table 表格
     * @param count 冻结列数
     * @see com.kingdee.eas.fi.fa.basedata.FaUtils#freezeTable(KDTable table, int count)
     */
    public static void freezeTable(KDTable table, int count) {
    	
        if (table == null) {
            return;
        }
        KDTViewManager viewmgr = table.getViewManager();
        if (viewmgr != null && !viewmgr.isFreeze()) {
            viewmgr.setFreezeView(0, count);
            table.reLayoutAndPaint();
        }
    }
    
    
    /**
     * 删除给定表中选中的行 如果没有选中行，删除最后一行。
     * 
     * @param kDTable 表格
     * @see com.kingdee.eas.fi.fa.manage.client.FaClientUtils#tableDelLine(KDTable kDTable)
     */
    public static void tableDelLine(KDTable kDTable) {
        if (kDTable.getSelectManager().get() == null) {
            return;
        }

        int ibeginRow = kDTable.getSelectManager().get().getBeginRow();
        int iEndrow = kDTable.getSelectManager().get().getEndRow();
        int ilastRow = kDTable.getRowCount();

        if ((ibeginRow != -1) && (iEndrow != -1)) {
            for (int i = ibeginRow; i < (iEndrow + 1); i++) {
                IRow row = kDTable.getRow(i);

                if (row == null) {
                    continue;
                }
                row = kDTable.removeRow(i);
				iEndrow--;
				i--;
            }
        } else { // 没有选中行时删除最后一行，看要不要？
            if (ilastRow > 0) {
                kDTable.removeRow(ilastRow - 1);
            }
        }
    }

    /**
     * 在表最未行加一行 返回所加的行
     * 
     * @param kDTable 表格
     * @see com.kingdee.eas.fi.fa.manage.client.FaClientUtils#tableDelLine(KDTable kDTable)
     */
    public static IRow tableAddLastLine(KDTable kDTable) {
        IRow row = null;
        int iRow = kDTable.getRowCount();

        if (iRow != -1) {
            row = kDTable.addRow(iRow);
        }
        return row;
    }
    
    /**
     * 在表中增加一列
     * 
     * @param kDTable	表格
     * @param keyName	列键名
     * @param celName	列名
     * @param index	索引
     * @param width	列宽
     */
    public static void tableAddColumn(KDTable table, String keyValue, String celName, int index, int width) {
    	//	列指定插入位置
    	table.addColumn(index);
    	//	表头指定插入位置
    	IRow headRow = table.getHeadRow(0);
    	//	获取表头行对象
    	headRow.getCell(index).setValue(celName);
    	headRow.setCell(index, headRow.getCell(index));
    	
    	//	获取列对象
    	IColumn col = table.getColumn(index);
    	col.setKey(keyValue);
    	col.setWidth(width);
    }
    
    
    /**
     * 在表中的一个列中为复选框设置显示状态
     * @param table 表格
     * @param cellName 单元格名
     * @param flag 选择状态 true 选择 fales 没选择
     */
    public static void checkRows(KDTable table, String cellName, boolean flag) {
    	
        Boolean check = Boolean.valueOf(flag);
        int iCount = table.getRowCount();
        for (int i = 0; i < iCount; i++) {
            IRow row = table.getRow(i);
            if(!"locked".equals(row.getUserObject()))
                row.getCell(cellName).setValue(check);
        }
    }
    
    
	/**
	 * 给表中的复选框设置显示状态的事件处理方法
	 * @param kdtable 表格
	 * @param cellName 列名
	 * @param e 事件对象
	 */
	public static void tableCheckChanged(KDTable kdtable, String cellName, KDTEditEvent e) {
    	
        if(!cellName.equals(kdtable.getColumnKey(e.getColIndex())))
            return;
        kdtable.getRow(e.getRowIndex()).getCell(e.getColIndex()).setValue(e.getValue());
    }
    
	
    /**
     * 格式化日期，将日期格式化为“YYYY-MM-DD”
     * 
     * @param table 表格
     * @param columnKey 列键名
     * @see com.kingdee.eas.fi.fa.manage.client.FaClientUtils#fmtDate(KDTable table, String columnKey)
     */
    public static void fmtDate(KDTable table, String columnKey) {
	    if(table.getColumn(columnKey)!=null)	
	    	table.getColumn(columnKey).getStyleAttributes().setNumberFormat("yyyy-MM-dd");
    }

    
    /**
     * 格式化日期，将日期格式化为“YYYY-MM-DD”
     * 
     * @param table 表格
     * @param columnKeys 列键名
     * @see com.kingdee.eas.fi.fa.manage.client.FaClientUtils#fmtDate(KDTable table, String[] columnKey)
     */
    public static void fmtDate(KDTable table, String[] columnKeys) {
        for (int i = 0; i < columnKeys.length; i++) {
            fmtDate(table, columnKeys[i]);
        }
    }
    
    /**
     * 给指定table设定金额格式，并且右对齐
     * 
     * @param table 表格
     * @param columnName 列名
     */
    public static void changeTableNumberFormat(KDTable table, String columnName) {
    	if(table.getColumn(columnName)!=null){
    		table.getColumn(columnName).getStyleAttributes().setNumberFormat(strDataFormat);
            table.getColumn(columnName).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT); // 右对齐
    	}
    }

    /**
     * 给指定table设定金额格式，并且右对齐
     * 
     * @param table 表格
     * @param columnIndex 列索引
     * @param format 格式化
     */
    public static void changeTableNumberFormat(KDTable table, int columnIndex, String format) {
    	if(table.getColumn(columnIndex)!=null){
	        table.getColumn(columnIndex).getStyleAttributes().setNumberFormat(format);
	        table.getColumn(columnIndex).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT); // 右对齐
    	}
    }

    /**
     * 给指定table设定金额格式，并且右对齐
     * 
     * @param table 表格
     * @param columnNames 列名数组
     */
    public static void changeTableNumberFormat(KDTable table, String[] columnNames) {
        for (int i = 0; i < columnNames.length; i++)
            changeTableNumberFormat(table, columnNames[i]);
    }

    /**
     * 给指定table设定金额格式，并且右对齐
     * 
     * @param table 表格
     * @param columnNames 列名数组
     * @param format 格式化
     */
    public static void changeTableNumberFormat(KDTable table, String[] columnNames, String format) {
        for (int i = 0; i < columnNames.length; i++)
            changeTableNumberFormat(table, columnNames[i], format);
    }
    
    /**
     * 给指定table设定金额格式，并且右对齐
     * 
     * @param table 表格
     * @param columnNames 列名数组
     * @param format 格式化
     */
    public static void changeTableNumberFormat(KDTable table, String columnName, String format) {
        table.getColumn(columnName).getStyleAttributes().setNumberFormat(format);
        table.getColumn(columnName).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT); // 右对齐
    }
    
    
    /**
     * 给指定table设定下拉框的（枚举）
     * 
     * @param table 表格
     * @param classEnumName 枚举的类名
     * @param columnName 表格列名
     */
    public static void setTableComboBox(KDTable table, String classEnumName,String columnName) {
    	
        KDComboBox tableComboBox = new KDComboBox();
        tableComboBox.setName(table.getName()+"_"+columnName+"_ComboBox");
        tableComboBox.setVisible(true);
        List l = EnumUtils.getEnumList(classEnumName);
        tableComboBox.addItem("");//增加为空的选择
        tableComboBox.addItems(l.toArray());
        KDTDefaultCellEditor tableColumnCellEditor = new KDTDefaultCellEditor(tableComboBox);
        table.getColumn(columnName).setEditor(tableColumnCellEditor);
    }
    /**
     * 给指定table数字列求和
     * 
     * @param table 表格
     * @param columnName 表格列名
     */
    public static BigDecimal getColumnValueSum(KDTable table,String columnName) {
    	BigDecimal sum = new BigDecimal(0);
        for(int i=0;i<table.getRowCount();i++){
        	if(table.getRow(i).getCell(columnName).getValue()!=null ){
        		if( table.getRow(i).getCell(columnName).getValue() instanceof BigDecimal)
            		sum = sum.add((BigDecimal)table.getRow(i).getCell(columnName).getValue());
            	else if(table.getRow(i).getCell(columnName).getValue() instanceof String){
            		String value = (String)table.getRow(i).getCell(columnName).getValue();
            		if(value.indexOf("零")==-1 && value.indexOf("[]")==-1){
            			value = value.replaceAll(",", "");
                		sum = sum.add(new BigDecimal(value));
            		}
            	}
            	else if(table.getRow(i).getCell(columnName).getValue() instanceof Integer){
            		String value = table.getRow(i).getCell(columnName).getValue().toString();
            		sum = sum.add(new BigDecimal(value));
            	}
        	}
        }
        return sum;
    }
    public static List getMapFieldValues(KDTable table, String fieldNames[])
    {
        List allList = new ArrayList();
        if(table != null && fieldNames != null)
        {
            for(int i = 0; i < table.getRowCount(); i++)
            {
                Map map = getMapFieldValues(table, i, fieldNames);
                allList.add(map);
            }

        }
        return allList;
    }
    /**
	 * 根据指定行号，列名数组 获取单元格值的集合 与 列名的映射
	 * @param table
	 */
    public static Map getMapFieldValues(KDTable table, int rowIndex, String fieldNames[])
    {
        Map map = new HashMap();
        List list = getFieldValues(table, rowIndex, fieldNames);
        for(int j = 0; j < fieldNames.length; j++)
            map.put(fieldNames[j], list.get(j));

        return map;
    }
    /**
	 * 根据指定行号，列名数组 获取单元格值的集合
	 * @param table
	 */
    public static List getFieldValues(KDTable table, int rowIndex, String fieldNames[])
    {
        List values = new ArrayList();
        if(table != null && rowIndex > -1 && fieldNames != null)
        {
            int size = fieldNames.length;
            for(int i = 0; i < size; i++)
                values.add(i, getFieldValue(table, rowIndex, fieldNames[i]));

        }
        return values;
    }
    /**
	 * 根据指定行号，列名 获取单元格值
	 * @param table
	 */
    public static Object getFieldValue(KDTable table, int rowIndex, String fieldName)
    {
        Object result = null;
        ICell cell = getCell(table, rowIndex, fieldName);
        if(cell != null)
            result = cell.getValue();
        return result;
    }
    /**
	 * 根据指定行号，列名 获取单元格
	 * @param table
	 */
    public static ICell getCell(KDTable table, int rowIndex, String fieldName)
    {
        ICell cell = null;
        if(table != null && rowIndex > -1 && fieldName != null && fieldName.trim().length() != 0)
            cell = table.getRow(rowIndex).getCell(fieldName);
        return cell;
    }
    /**
	 * 隐藏table上的增删插按钮
	 * @param table
	 */
	public static void hidePanelButtons(KDTable table){
		Component c = table.getParent().getParent();  
		if (c instanceof DetailPanel) {  
			 DetailPanel panel = (DetailPanel) c;
			 Component[] components = panel.getComponents();  
			 for(int i = 0; i < components.length; i++){  
				 Component component = components[i];  
				 if (component instanceof KDPanel) {  
					 KDPanel kdPanel = (KDPanel) component;  
					 if("controlPanel".equals(kdPanel.getName())){  
						 panel.remove(kdPanel);  
					 }  
					 if("entryPanel".equals(kdPanel.getName())){  
						 Rectangle entryRect = new Rectangle(0, 0,table.getWidth(),table.getHeight()+ 29);  
						 kdPanel.setBounds(entryRect);  
						 kdPanel.putClientProperty("OriginalBounds",entryRect);  
						 panel.add(kdPanel, new KDLayout.Constraints(KDLayout.Constraints.ANCHOR_TOP  
								 | KDLayout.Constraints.ANCHOR_RIGHT | KDLayout.Constraints.ANCHOR_LEFT  
								 | KDLayout.Constraints.ANCHOR_BOTTOM , entryRect));  
					 }  
				 }  
			 }  	
		}   
	} 
	/**
	 * 以list方式填充table
	 */
	protected void fillTableWithList(KDTable table,List records) {
		for (int i = records.size(), j = 0; j < i; j++) {
			Object record = records.get(j);
			if (record instanceof List) {
				List list = (List) record;
				IRow row = table.addRow();
				for (int k = 0, x = list.size(); k < x; k++) {
					row.getCell(k).setValue(list.get(k));
				}
			}
		}
	}
	
	//设置合计行以及融合, 目标货值和年度分解falg 为true，月度分解flag为false
	
	public static void setTotalRow2(KDTable table,boolean flag){
		
		boolean ishouse = false;
		boolean isbusiness = false;
		boolean ispark = false;
		boolean ispublicBuild =false;
		int house =-1;
		int lastHouse =-1;
		int business = -1;
		int lastBusiness =-1;
		int park = -1;
		int lastPark =-1;
		int publicBuild = -1;
		int lastPublicBuild =-1;
		KDTMergeManager mm = table.getMergeManager();
//		for(int i=0;i<table.getRowCount();i++){
//			PlanIndexTypeEnum planIndexType = (PlanIndexTypeEnum)table.getCell(i, 0).getValue();
//			if(PlanIndexTypeEnum.house.equals(planIndexType)){
//				if(!ishouse){
//					ishouse = true;
//					house = i;
//				}
//				lastHouse =i;
//			}else if(PlanIndexTypeEnum.business.equals(planIndexType)){
//				if(!isbusiness){
//					isbusiness = true;
//					business = i;
//				}
//				lastBusiness =i;
//			}else if(PlanIndexTypeEnum.parking.equals(planIndexType)){
//				if(!ispark){
//					ispark = true;
//					park = i;
//				}
//				lastPark =i;
//			}else if(PlanIndexTypeEnum.publicBuild.equals(planIndexType)){
//				if(!ispublicBuild){
//					ispublicBuild = true;
//					publicBuild = i;
//				}
//				lastPublicBuild = i;
//			}
//		}
		if(ishouse){
			sumRow(table,lastHouse+1,flag);
			business++;
			park++;
			publicBuild++;
			lastBusiness++;
			lastPark++;
			lastPublicBuild++;
			mm.mergeBlock(house, 0, lastHouse+1, 0, KDTMergeManager.SPECIFY_MERGE);
		}
		if(isbusiness){
			sumRow(table,lastBusiness+1,flag);
			park++;
			publicBuild++;
			lastPark++;
			lastPublicBuild++;
			mm.mergeBlock(business, 0, lastBusiness+1, 0, KDTMergeManager.SPECIFY_MERGE);
		}
		if(ispark){
			sumRow(table,lastPark+1,flag);
			publicBuild++;
			lastPublicBuild++;
			mm.mergeBlock(park, 0, lastPark+1, 0, KDTMergeManager.SPECIFY_MERGE);
		}
		if(ispublicBuild){
			sumRow(table,lastPublicBuild+1,flag);
			mm.mergeBlock(publicBuild, 0, lastPublicBuild+1, 0, KDTMergeManager.SPECIFY_MERGE);
		}	
	}
	/**
     * 融合主题信息
     * */
    public static void mergeThemeRow(KDTable table,String columnName,int colIndex){
        String theme = "";
        String lastTheme = "";
        boolean isMulTheme = false;
        KDTMergeManager mm = table.getMergeManager();
        int rowIndx = 0;
        int endIndx = 0;
        for(int i=0 ;i<table.getRowCount();i++){
        	endIndx = i;
        	theme = (String)table.getRow(i).getCell(columnName).getValue(); //当前主题
        	if(i>0){
        		lastTheme = (String)table.getRow(i-1).getCell(columnName).getValue(); //上一主题
            	if(!theme.equals(lastTheme)){ // 获取当前主题 与 上一主题 不相同，所在的行号
            		mm.mergeBlock(rowIndx, colIndex,endIndx-1, colIndex); // 将最后相同的主题融合
            		rowIndx = endIndx;
            	}
        	}
        }
     }
	public static void sumRow(KDTable table,int i,boolean flag){
		IRow row = table.addRow(i);
		row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
		row.getStyleAttributes().setLocked(true);
		row.getCell(1).setValue("合计");
//		row.getCell(0).setValue((PlanIndexTypeEnum)table.getRow(i-1).getCell(0).getValue());
//		row.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
//		row.setEditor(CommerceHelper.getKDFormattedTextDecimalEditor());
		if(flag)
			reSetExpressions(table,row,i);
		else
			getReSetExpressions(table,row,i);
			
	}
	

	private static void reSetExpressions(KDTable table,IRow row,int n){
		int m = n-1;
		for(;m>=0;m--){
			if(table.getRow(m).getCell(1).getValue()instanceof String){
				break;
			}
		}
		for(int j = 0; j<table.getColumnCount();j++){
			if(j==0||j==1||j%4==0){
				continue;
			}
			String c ="";
			String exp = "";
			if(j<=25){
			   c=String.valueOf(((char)('A'+j)));
			}else{
			   c=(char)('A'+j/26-1)+String.valueOf(((char)('A'+j%26)));
			}
			exp="=sum("+c+(m+2)+":"+c+(n)+")";
			row.getCell(j).setExpressions(exp);
			if(j%4==1){
				Number subTotalArea = (Number)row.getCell(j-3).getValue();
				Number subTotalAmount = (Number)row.getCell(j).getValue();
				BigDecimal price = FDCHelper.divide(subTotalAmount,subTotalArea );
			    row.getCell(j-1).setValue(price);
			}
		}
	}
	
	private static void getReSetExpressions(KDTable table,IRow row,int n){
		int m = n-1;
		for(;m>=0;m--){
			if(table.getRow(m).getCell(1).getValue()instanceof String){
				break;
			}
		}
		for(int j = 0; j<table.getColumnCount();j++){
			if(j==0||j==1||j%10==6||j%10==7){
				continue;
			}
			String c ="";
			String exp = "";
			if(j<=25){
			   c=String.valueOf(((char)('A'+j)));
			}else{
			   c=(char)('A'+j/26-1)+String.valueOf(((char)('A'+j%26)));
			}
			exp="=sum("+c+(m+2)+":"+c+(n)+")";
			row.getCell(j).setExpressions(exp);
		}
	}
	
	//目标总货值设置合计行以及融合
	public static void setTotalRow(KDTable table){
		int business = 0;
		int park = 0;
		int publicBuild = 0;
		KDTMergeManager mm = table.getMergeManager();
//		for(int i=0;i<table.getRowCount();i++){
//			PlanIndexTypeEnum planIndexType = (PlanIndexTypeEnum)table.getCell(i, 0).getValue();
//			if(PlanIndexTypeEnum.business.equals(planIndexType)){
//				if(business==0){
//					business = i;
//					IRow row = table.addRow(i);
////					row.setEditor(CellBinder.getCellNumberEdit());
//					row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
//					row.getStyleAttributes().setLocked(true);
//					row.getCell(1).setValue("合计");
//					row.getCell(0).setValue(PlanIndexTypeEnum.house);
//					row.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
//					row.setEditor( CommerceHelper.getKDFormattedTextDecimalEditor());
//				}
//			}else if(PlanIndexTypeEnum.parking.equals(planIndexType)){
//				if(park==0){
//					park = i;
//					IRow row = table.addRow(i);
//					row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
//					row.getStyleAttributes().setLocked(true);
//					row.getCell(1).setValue("合计");
//					row.getCell(0).setValue(PlanIndexTypeEnum.business);
//					row.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
//					row.setEditor( CommerceHelper.getKDFormattedTextDecimalEditor());
//				}
//			}else if(PlanIndexTypeEnum.publicBuild.equals(planIndexType)){
//				if(publicBuild==0){
//					publicBuild=i;
//					IRow row = table.addRow(i);
//					row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
//					row.getStyleAttributes().setLocked(true);
//					row.getCell(1).setValue("合计");
//					row.getCell(0).setValue(PlanIndexTypeEnum.parking);
//					row.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
//					row.setEditor( CommerceHelper.getKDFormattedTextDecimalEditor());
//				}
//			}
//			if(i==table.getRowCount()-1){
//				IRow row = table.addRow(++i);
//				row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
//				row.getStyleAttributes().setLocked(true);
//				row.getCell(1).setValue("合计");
//				row.getCell(0).setValue(PlanIndexTypeEnum.publicBuild);
//				row.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
//				row.setEditor( CommerceHelper.getKDFormattedTextDecimalEditor());
//			}
//		}
		mm.mergeBlock(0, 0, business, 0, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(business+1, 0, park, 0, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(park+1, 0, publicBuild, 0, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(publicBuild+1, 0, table.getRowCount()-1, 0, KDTMergeManager.SPECIFY_MERGE);
	}
	
	public static String getTableXml(){
		String kdtEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> " +
		"<DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"" +
		"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\">" +
		"<Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\">" +
		"<t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\">" +
		"<t:ColumnGroup><t:Column t:key=\"type\" t:width=\"70\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" />" +
		"<t:Column t:key=\"productType\" t:width=\"90\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" />" +
		"<t:Column t:key=\"areaPlan\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" />" +
		"<t:Column t:key=\"areaActual\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" />" +
		"<t:Column t:key=\"ploidyPlan\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" />" +
		"<t:Column t:key=\"ploidyActual\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" />" +
		"<t:Column t:key=\"pricePlan\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" />" +
		"<t:Column t:key=\"priceActual\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" />" +
		"<t:Column t:key=\"amountPlan\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" />" +
		"<t:Column t:key=\"amountActual\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" />" +
		"<t:Column t:key=\"recoverPlan\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" />" +
		"<t:Column t:key=\"recoverActual\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" />" +
		"<t:Column t:key=\"areaPlan1\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" />" +
		"<t:Column t:key=\"areaActual1\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" />" +
		"<t:Column t:key=\"ploidyPlan1\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" />" +
		"<t:Column t:key=\"ploidyActual1\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" />" +
		"<t:Column t:key=\"pricePlan1\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" />" +
		"<t:Column t:key=\"priceActual1\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" />" +
		"<t:Column t:key=\"amountPlan1\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" />" +
		"<t:Column t:key=\"amountActual1\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" />" +
		"<t:Column t:key=\"recoverPlan1\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" />" +
		"<t:Column t:key=\"recoverActual1\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" />" +
		"<t:Column t:key=\"areaPlan2\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"22\" />" +
		"<t:Column t:key=\"areaActual2\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"23\" />" +
		"<t:Column t:key=\"ploidyPlan2\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"24\" />" +
		"<t:Column t:key=\"ploidyActual2\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"25\" />" +
		"<t:Column t:key=\"pricePlan2\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"26\" />" +
		"<t:Column t:key=\"priceActual2\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"27\" />" +
		"<t:Column t:key=\"amountPlan2\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"28\" />" +
		"<t:Column t:key=\"amountActual2\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"29\" />" +
		"<t:Column t:key=\"recoverPlan2\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"30\" />" +
		"<t:Column t:key=\"recoverActual2\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"31\" />" +
		"<t:Column t:key=\"areaPlan3\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"32\" />" +
		"<t:Column t:key=\"areaActual3\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"33\" />" +
		"<t:Column t:key=\"ploidyPlan3\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"34\" />" +
		"<t:Column t:key=\"ploidyActual3\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"35\" />" +
		"<t:Column t:key=\"pricePlan3\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"36\" />" +
		"<t:Column t:key=\"priceActual3\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"37\" />" +
		"<t:Column t:key=\"amountPlan3\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"38\" />" +
		"<t:Column t:key=\"amountActual3\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"39\" />" +
		"<t:Column t:key=\"recoverPlan3\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"40\" />" +
		"<t:Column t:key=\"recoverActual3\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"41\" />" +
		"<t:Column t:key=\"areaPlan4\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"42\" />" +
		"<t:Column t:key=\"areaActual4\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"43\" />" +
		"<t:Column t:key=\"ploidyPlan4\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"44\" />" +
		"<t:Column t:key=\"ploidyActual4\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"45\" />" +
		"<t:Column t:key=\"pricePlan4\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"46\" />" +
		"<t:Column t:key=\"priceActual4\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"47\" />" +
		"<t:Column t:key=\"amountPlan4\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"48\" />" +
		"<t:Column t:key=\"amountActual4\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"49\" />" +
		"<t:Column t:key=\"recoverPlan4\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"50\" />" +
		"<t:Column t:key=\"recoverActual4\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"51\" />" +
		"<t:Column t:key=\"areaPlan5\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"52\" />" +
		"<t:Column t:key=\"areaActual5\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"53\" />" +
		"<t:Column t:key=\"ploidyPlan5\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"54\" />" +
		"<t:Column t:key=\"ploidyActual5\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"55\" />" +
		"<t:Column t:key=\"pricePlan5\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"56\" />" +
		"<t:Column t:key=\"priceActual5\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"57\" />" +
		"<t:Column t:key=\"amountPlan5\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"58\" />" +
		"<t:Column t:key=\"amountActual5\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"59\" />" +
		"<t:Column t:key=\"recoverPlan5\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"60\" />" +
		"<t:Column t:key=\"recoverActual5\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"61\" />" +
		"<t:Column t:key=\"areaPlan6\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"62\" />" +
		"<t:Column t:key=\"areaActual6\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"63\" />" +
		"<t:Column t:key=\"ploidyPlan6\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"64\" />" +
		"<t:Column t:key=\"ploidyActual6\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"65\" />" +
		"<t:Column t:key=\"pricePlan6\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"66\" />" +
		"<t:Column t:key=\"priceActual6\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"67\" />" +
		"<t:Column t:key=\"amountPlan6\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"68\" />" +
		"<t:Column t:key=\"amountActual6\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"69\" />" +
		"<t:Column t:key=\"recoverPlan6\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"70\" />" +
		"<t:Column t:key=\"recoverActual6\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"71\" />" +
		"<t:Column t:key=\"areaPlan7\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"72\" />" +
		"<t:Column t:key=\"areaActual7\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"73\" />" +
		"<t:Column t:key=\"ploidyPlan7\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"74\" />" +
		"<t:Column t:key=\"ploidyActual7\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"75\" />" +
		"<t:Column t:key=\"pricePlan7\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"76\" />" +
		"<t:Column t:key=\"priceActual7\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"77\" />" +
		"<t:Column t:key=\"amountPlan7\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"78\" />" +
		"<t:Column t:key=\"amountActual7\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"79\" />" +
		"<t:Column t:key=\"recoverPlan7\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"80\" />" +
		"<t:Column t:key=\"recoverActual7\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"81\" />" +
		"<t:Column t:key=\"areaPlan8\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"82\" />" +
		"<t:Column t:key=\"areaActual8\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"83\" />" +
		"<t:Column t:key=\"ploidyPlan8\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"84\" />" +
		"<t:Column t:key=\"ploidyActual8\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"85\" />" +
		"<t:Column t:key=\"pricePlan8\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"86\" />" +
		"<t:Column t:key=\"priceActual8\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"87\" />" +
		"<t:Column t:key=\"amountPlan8\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"88\" />" +
		"<t:Column t:key=\"amountActual8\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"89\" />" +
		"<t:Column t:key=\"recoverPlan8\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"90\" />" +
		"<t:Column t:key=\"recoverActual8\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"91\" />" +
		"<t:Column t:key=\"areaPlan9\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"92\" />" +
		"<t:Column t:key=\"areaActual9\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"93\" />" +
		"<t:Column t:key=\"ploidyPlan9\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"94\" />" +
		"<t:Column t:key=\"ploidyActual9\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"95\" />" +
		"<t:Column t:key=\"pricePlan9\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"96\" />" +
		"<t:Column t:key=\"priceActual9\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"97\" />" +
		"<t:Column t:key=\"amountPlan9\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"98\" />" +
		"<t:Column t:key=\"amountActual9\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"99\" />" +
		"<t:Column t:key=\"recoverPlan9\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"100\" />" +
		"<t:Column t:key=\"recoverActual9\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"101\" />" +
		"<t:Column t:key=\"areaPlan10\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"102\" />" +
		"<t:Column t:key=\"areaActual10\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"103\" />" +
		"<t:Column t:key=\"ploidyPlan10\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"104\" />" +
		"<t:Column t:key=\"ploidyActual10\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"105\" />" +
		"<t:Column t:key=\"pricePlan10\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"106\" />" +
		"<t:Column t:key=\"priceActual10\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"107\" />" +
		"<t:Column t:key=\"amountPlan10\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"108\" />" +
		"<t:Column t:key=\"amountActual10\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"109\" />" +
		"<t:Column t:key=\"recoverPlan10\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"110\" />" +
		"<t:Column t:key=\"recoverActual10\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"111\" />" +
		"<t:Column t:key=\"areaPlan11\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"112\" />" +
		"<t:Column t:key=\"areaActual11\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"113\" />" +
		"<t:Column t:key=\"ploidyPlan11\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"114\" />" +
		"<t:Column t:key=\"ploidyActual11\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"115\" />" +
		"<t:Column t:key=\"pricePlan11\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"116\" />" +
		"<t:Column t:key=\"priceActual11\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"117\" />" +
		"<t:Column t:key=\"amountPlan11\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"118\" />" +
		"<t:Column t:key=\"amountActual11\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"119\" />" +
		"<t:Column t:key=\"recoverPlan11\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"120\" />" +
		"<t:Column t:key=\"recoverActual11\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"121\" />" +
		"<t:Column t:key=\"areaPlan12\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"122\" />" +
		"<t:Column t:key=\"areaActual12\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"123\" />" +
		"<t:Column t:key=\"ploidyPlan12\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"124\" />" +
		"<t:Column t:key=\"ploidyActual12\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"125\" />" +
		"<t:Column t:key=\"pricePlan12\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"126\" />" +
		"<t:Column t:key=\"priceActual12\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"127\" />" +
		"<t:Column t:key=\"amountPlan12\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"128\" />" +
		"<t:Column t:key=\"amountActual12\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"129\" />" +
		"<t:Column t:key=\"recoverPlan12\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"130\" />" +
		"<t:Column t:key=\"recoverActual12\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"131\" />" +
		"</t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{type}</t:Cell><t:Cell>$Resource{productType}" +
		"</t:Cell><t:Cell>$Resource{areaPlan}</t:Cell><t:Cell>$Resource{areaActual}</t:Cell><t:Cell>$Resource{ploidyPlan}</t:Cell><t:Cell>$Resource{ploidyActual}" +
		"</t:Cell><t:Cell>$Resource{pricePlan}</t:Cell><t:Cell>$Resource{priceActual}</t:Cell><t:Cell>$Resource{amountPlan}</t:Cell><t:Cell>$Resource{amountActual}" +
		"</t:Cell><t:Cell>$Resource{recoverPlan}</t:Cell><t:Cell>$Resource{recoverActual}</t:Cell><t:Cell>$Resource{areaPlan1}</t:Cell><t:Cell>$Resource{areaActual1}" +
		"</t:Cell><t:Cell>$Resource{ploidyPlan1}</t:Cell><t:Cell>$Resource{ploidyActual1}</t:Cell><t:Cell>$Resource{pricePlan1}</t:Cell><t:Cell>$Resource{priceActual1}" +
		"</t:Cell><t:Cell>$Resource{amountPlan1}</t:Cell><t:Cell>$Resource{amountActual1}</t:Cell><t:Cell>$Resource{recoverPlan1}</t:Cell><t:Cell>$Resource{recoverActual1}" +
		"</t:Cell><t:Cell>$Resource{areaPlan2}</t:Cell><t:Cell>$Resource{areaActual2}</t:Cell><t:Cell>$Resource{ploidyPlan2}</t:Cell><t:Cell>$Resource{ploidyActual2}" +
		"</t:Cell><t:Cell>$Resource{pricePlan2}</t:Cell><t:Cell>$Resource{priceActual2}</t:Cell><t:Cell>$Resource{amountPlan2}" +
		"</t:Cell><t:Cell>$Resource{amountActual2}</t:Cell><t:Cell>$Resource{recoverPlan2}" +
		"</t:Cell><t:Cell>$Resource{recoverActual2}</t:Cell><t:Cell>$Resource{areaPlan3}" +
		"</t:Cell><t:Cell>$Resource{areaActual3}</t:Cell><t:Cell>$Resource{ploidyPlan3}" +
		"</t:Cell><t:Cell>$Resource{ploidyActual3}</t:Cell><t:Cell>$Resource{pricePlan3}" +
		"</t:Cell><t:Cell>$Resource{priceActual3}</t:Cell><t:Cell>$Resource{amountPlan3}" +
		"</t:Cell><t:Cell>$Resource{amountActual3}</t:Cell><t:Cell>$Resource{recoverPlan3}" +
		"</t:Cell><t:Cell>$Resource{recoverActual3}</t:Cell><t:Cell>$Resource{areaPlan4}" +
		"</t:Cell><t:Cell>$Resource{areaActual4}</t:Cell><t:Cell>$Resource{ploidyPlan4}" +
		"</t:Cell><t:Cell>$Resource{ploidyActual4}</t:Cell><t:Cell>$Resource{pricePlan4}" +
		"</t:Cell><t:Cell>$Resource{priceActual4}</t:Cell><t:Cell>$Resource{amountPlan4}" +
		"</t:Cell><t:Cell>$Resource{amountActual4}</t:Cell><t:Cell>$Resource{recoverPlan4}" +
		"</t:Cell><t:Cell>$Resource{recoverActual4}</t:Cell><t:Cell>$Resource{areaPlan5}" +
		"</t:Cell><t:Cell>$Resource{areaActual5}</t:Cell><t:Cell>$Resource{ploidyPlan5}" +
		"</t:Cell><t:Cell>$Resource{ploidyActual5}</t:Cell><t:Cell>$Resource{pricePlan5}" +
		"</t:Cell><t:Cell>$Resource{priceActual5}</t:Cell><t:Cell>$Resource{amountPlan5}" +
		"</t:Cell><t:Cell>$Resource{amountActual5}</t:Cell><t:Cell>$Resource{recoverPlan5}" +
		"</t:Cell><t:Cell>$Resource{recoverActual5}</t:Cell><t:Cell>$Resource{areaPlan6}" +
		"</t:Cell><t:Cell>$Resource{areaActual6}</t:Cell><t:Cell>$Resource{ploidyPlan6}" +
		"</t:Cell><t:Cell>$Resource{ploidyActual6}</t:Cell><t:Cell>$Resource{pricePlan6}" +
		"</t:Cell><t:Cell>$Resource{priceActual6}</t:Cell><t:Cell>$Resource{amountPlan6}" +
		"</t:Cell><t:Cell>$Resource{amountActual6}</t:Cell><t:Cell>$Resource{recoverPlan6}" +
		"</t:Cell><t:Cell>$Resource{recoverActual6}</t:Cell><t:Cell>$Resource{areaPlan7}" +
		"</t:Cell><t:Cell>$Resource{areaActual7}</t:Cell><t:Cell>$Resource{ploidyPlan7}" +
		"</t:Cell><t:Cell>$Resource{ploidyActual7}</t:Cell><t:Cell>$Resource{pricePlan7}" +
		"</t:Cell><t:Cell>$Resource{priceActual7}</t:Cell><t:Cell>$Resource{amountPlan7}" +
		"</t:Cell><t:Cell>$Resource{amountActual7}</t:Cell><t:Cell>$Resource{recoverPlan7}" +
		"</t:Cell><t:Cell>$Resource{recoverActual7}</t:Cell><t:Cell>$Resource{areaPlan8}" +
		"</t:Cell><t:Cell>$Resource{areaActual8}</t:Cell><t:Cell>$Resource{ploidyPlan8}" +
		"</t:Cell><t:Cell>$Resource{ploidyActual8}</t:Cell><t:Cell>$Resource{pricePlan8}" +
		"</t:Cell><t:Cell>$Resource{priceActual8}</t:Cell><t:Cell>$Resource{amountPlan8}" +
		"</t:Cell><t:Cell>$Resource{amountActual8}</t:Cell><t:Cell>$Resource{recoverPlan8}" +
		"</t:Cell><t:Cell>$Resource{recoverActual8}</t:Cell><t:Cell>$Resource{areaPlan9}" +
		"</t:Cell><t:Cell>$Resource{areaActual9}</t:Cell><t:Cell>$Resource{ploidyPlan9}" +
		"</t:Cell><t:Cell>$Resource{ploidyActual9}</t:Cell><t:Cell>$Resource{pricePlan9}" +
		"</t:Cell><t:Cell>$Resource{priceActual9}</t:Cell><t:Cell>$Resource{amountPlan9}" +
		"</t:Cell><t:Cell>$Resource{amountActual9}</t:Cell><t:Cell>$Resource{recoverPlan9}" +
		"</t:Cell><t:Cell>$Resource{recoverActual9}</t:Cell><t:Cell>$Resource{areaPlan10}" +
		"</t:Cell><t:Cell>$Resource{areaActual10}</t:Cell><t:Cell>$Resource{ploidyPlan10}" +
		"</t:Cell><t:Cell>$Resource{ploidyActual10}</t:Cell><t:Cell>$Resource{pricePlan10}" +
		"</t:Cell><t:Cell>$Resource{priceActual10}</t:Cell><t:Cell>$Resource{amountPlan10}" +
		"</t:Cell><t:Cell>$Resource{amountActual10}</t:Cell><t:Cell>$Resource{recoverPlan10}" +
		"</t:Cell><t:Cell>$Resource{recoverActual10}</t:Cell><t:Cell>$Resource{areaPlan11}" +
		"</t:Cell><t:Cell>$Resource{areaActual11}</t:Cell><t:Cell>$Resource{ploidyPlan11}" +
		"</t:Cell><t:Cell>$Resource{ploidyActual11}</t:Cell><t:Cell>$Resource{pricePlan11}" +
		"</t:Cell><t:Cell>$Resource{priceActual11}</t:Cell><t:Cell>$Resource{amountPlan11}" +
		"</t:Cell><t:Cell>$Resource{amountActual11}</t:Cell><t:Cell>$Resource{recoverPlan11}" +
		"</t:Cell><t:Cell>$Resource{recoverActual11}</t:Cell><t:Cell>$Resource{areaPlan12}" +
		"</t:Cell><t:Cell>$Resource{areaActual12}</t:Cell><t:Cell>$Resource{ploidyPlan12}" +
		"</t:Cell><t:Cell>$Resource{ploidyActual12}</t:Cell><t:Cell>$Resource{pricePlan12}" +
		"</t:Cell><t:Cell>$Resource{priceActual12}</t:Cell><t:Cell>$Resource{amountPlan12}" +
		"</t:Cell><t:Cell>$Resource{amountActual12}</t:Cell><t:Cell>$Resource{recoverPlan12}" +
		"</t:Cell><t:Cell>$Resource{recoverActual12}</t:Cell></t:Row>" +
		"<t:Row t:name=\"header2\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{type_Row2}</t:Cell>" +
		"<t:Cell>$Resource{productType_Row2}</t:Cell><t:Cell>$Resource{areaPlan_Row2}</t:Cell><t:Cell>$Resource{areaActual_Row2}</t:Cell>" +
		"<t:Cell>$Resource{ploidyPlan_Row2}</t:Cell><t:Cell>$Resource{ploidyActual_Row2}</t:Cell><t:Cell>$Resource{pricePlan_Row2}</t:Cell>" +
		"<t:Cell>$Resource{priceActual_Row2}</t:Cell><t:Cell>$Resource{amountPlan_Row2}</t:Cell><t:Cell>$Resource{amountActual_Row2}</t:Cell>" +
		"<t:Cell>$Resource{recoverPlan_Row2}</t:Cell><t:Cell>$Resource{recoverActual_Row2}</t:Cell><t:Cell>$Resource{areaPlan1_Row2}</t:Cell>" +
		"<t:Cell>$Resource{areaActual1_Row2}</t:Cell><t:Cell>$Resource{ploidyPlan1_Row2}</t:Cell><t:Cell>$Resource{ploidyActual1_Row2}</t:Cell>" +
		"<t:Cell>$Resource{pricePlan1_Row2}</t:Cell><t:Cell>$Resource{priceActual1_Row2}</t:Cell><t:Cell>$Resource{amountPlan1_Row2}</t:Cell>" +
		"<t:Cell>$Resource{amountActual1_Row2}</t:Cell><t:Cell>$Resource{recoverPlan1_Row2}</t:Cell><t:Cell>$Resource{recoverActual1_Row2}</t:Cell>" +
		"<t:Cell>$Resource{areaPlan2_Row2}</t:Cell><t:Cell>$Resource{areaActual2_Row2}</t:Cell><t:Cell>$Resource{ploidyPlan2_Row2}</t:Cell>" +
		"<t:Cell>$Resource{ploidyActual2_Row2}</t:Cell><t:Cell>$Resource{pricePlan2_Row2}</t:Cell><t:Cell>$Resource{priceActual2_Row2}</t:Cell>" +
		"<t:Cell>$Resource{amountPlan2_Row2}</t:Cell><t:Cell>$Resource{amountActual2_Row2}</t:Cell><t:Cell>$Resource{recoverPlan2_Row2}</t:Cell>" +
		"<t:Cell>$Resource{recoverActual2_Row2}</t:Cell><t:Cell>$Resource{areaPlan3_Row2}</t:Cell><t:Cell>$Resource{areaActual3_Row2}</t:Cell>" +
		"<t:Cell>$Resource{ploidyPlan3_Row2}</t:Cell><t:Cell>$Resource{ploidyActual3_Row2}</t:Cell><t:Cell>$Resource{pricePlan3_Row2}</t:Cell>" +
		"<t:Cell>$Resource{priceActual3_Row2}</t:Cell><t:Cell>$Resource{amountPlan3_Row2}</t:Cell><t:Cell>$Resource{amountActual3_Row2}</t:Cell>" +
		"<t:Cell>$Resource{recoverPlan3_Row2}</t:Cell><t:Cell>$Resource{recoverActual3_Row2}</t:Cell><t:Cell>$Resource{areaPlan4_Row2}</t:Cell>" +
		"<t:Cell>$Resource{areaActual4_Row2}</t:Cell><t:Cell>$Resource{ploidyPlan4_Row2}</t:Cell><t:Cell>$Resource{ploidyActual4_Row2}</t:Cell>" +
		"<t:Cell>$Resource{pricePlan4_Row2}</t:Cell><t:Cell>$Resource{priceActual4_Row2}</t:Cell><t:Cell>$Resource{amountPlan4_Row2}</t:Cell>" +
		"<t:Cell>$Resource{amountActual4_Row2}</t:Cell><t:Cell>$Resource{recoverPlan4_Row2}</t:Cell><t:Cell>$Resource{recoverActual4_Row2}</t:Cell>" +
		"<t:Cell>$Resource{areaPlan5_Row2}</t:Cell><t:Cell>$Resource{areaActual5_Row2}</t:Cell><t:Cell>$Resource{ploidyPlan5_Row2}</t:Cell>" +
		"<t:Cell>$Resource{ploidyActual5_Row2}</t:Cell><t:Cell>$Resource{pricePlan5_Row2}</t:Cell><t:Cell>$Resource{priceActual5_Row2}</t:Cell>" +
		"<t:Cell>$Resource{amountPlan5_Row2}</t:Cell><t:Cell>$Resource{amountActual5_Row2}</t:Cell><t:Cell>$Resource{recoverPlan5_Row2}</t:Cell>" +
		"<t:Cell>$Resource{recoverActual5_Row2}</t:Cell><t:Cell>$Resource{areaPlan6_Row2}</t:Cell><t:Cell>$Resource{areaActual6_Row2}</t:Cell>" +
		"<t:Cell>$Resource{ploidyPlan6_Row2}</t:Cell><t:Cell>$Resource{ploidyActual6_Row2}</t:Cell><t:Cell>$Resource{pricePlan6_Row2}</t:Cell>" +
		"<t:Cell>$Resource{priceActual6_Row2}</t:Cell><t:Cell>$Resource{amountPlan6_Row2}</t:Cell><t:Cell>$Resource{amountActual6_Row2}</t:Cell>" +
		"<t:Cell>$Resource{recoverPlan6_Row2}</t:Cell><t:Cell>$Resource{recoverActual6_Row2}</t:Cell><t:Cell>$Resource{areaPlan7_Row2}</t:Cell>" +
		"<t:Cell>$Resource{areaActual7_Row2}</t:Cell><t:Cell>$Resource{ploidyPlan7_Row2}</t:Cell><t:Cell>$Resource{ploidyActual7_Row2}</t:Cell>" +
		"<t:Cell>$Resource{pricePlan7_Row2}</t:Cell><t:Cell>$Resource{priceActual7_Row2}</t:Cell><t:Cell>$Resource{amountPlan7_Row2}</t:Cell>" +
		"<t:Cell>$Resource{amountActual7_Row2}</t:Cell><t:Cell>$Resource{recoverPlan7_Row2}</t:Cell><t:Cell>$Resource{recoverActual7_Row2}</t:Cell>" +
		"<t:Cell>$Resource{areaPlan8_Row2}</t:Cell><t:Cell>$Resource{areaActual8_Row2}</t:Cell><t:Cell>$Resource{ploidyPlan8_Row2}</t:Cell>" +
		"<t:Cell>$Resource{ploidyActual8_Row2}</t:Cell><t:Cell>$Resource{pricePlan8_Row2}</t:Cell><t:Cell>$Resource{priceActual8_Row2}</t:Cell>" +
		"<t:Cell>$Resource{amountPlan8_Row2}</t:Cell><t:Cell>$Resource{amountActual8_Row2}</t:Cell><t:Cell>$Resource{recoverPlan8_Row2}</t:Cell>" +
		"<t:Cell>$Resource{recoverActual8_Row2}</t:Cell><t:Cell>$Resource{areaPlan9_Row2}</t:Cell><t:Cell>$Resource{areaActual9_Row2}</t:Cell>" +
		"<t:Cell>$Resource{ploidyPlan9_Row2}</t:Cell><t:Cell>$Resource{ploidyActual9_Row2}</t:Cell><t:Cell>$Resource{pricePlan9_Row2}</t:Cell>" +
		"<t:Cell>$Resource{priceActual9_Row2}</t:Cell><t:Cell>$Resource{amountPlan9_Row2}</t:Cell><t:Cell>$Resource{amountActual9_Row2}</t:Cell>" +
		"<t:Cell>$Resource{recoverPlan9_Row2}</t:Cell><t:Cell>$Resource{recoverActual9_Row2}</t:Cell><t:Cell>$Resource{areaPlan10_Row2}</t:Cell>" +
		"<t:Cell>$Resource{areaActual10_Row2}</t:Cell><t:Cell>$Resource{ploidyPlan10_Row2}</t:Cell><t:Cell>$Resource{ploidyActual10_Row2}</t:Cell>" +
		"<t:Cell>$Resource{pricePlan10_Row2}</t:Cell><t:Cell>$Resource{priceActual10_Row2}</t:Cell><t:Cell>$Resource{amountPlan10_Row2}</t:Cell>" +
		"<t:Cell>$Resource{amountActual10_Row2}</t:Cell><t:Cell>$Resource{recoverPlan10_Row2}</t:Cell><t:Cell>$Resource{recoverActual10_Row2}</t:Cell>" +
		"<t:Cell>$Resource{areaPlan11_Row2}</t:Cell><t:Cell>$Resource{areaActual11_Row2}</t:Cell><t:Cell>$Resource{ploidyPlan11_Row2}</t:Cell>" +
		"<t:Cell>$Resource{ploidyActual11_Row2}</t:Cell><t:Cell>$Resource{pricePlan11_Row2}</t:Cell><t:Cell>$Resource{priceActual11_Row2}</t:Cell>" +
		"<t:Cell>$Resource{amountPlan11_Row2}</t:Cell><t:Cell>$Resource{amountActual11_Row2}</t:Cell><t:Cell>$Resource{recoverPlan11_Row2}</t:Cell>" +
		"<t:Cell>$Resource{recoverActual11_Row2}</t:Cell><t:Cell>$Resource{areaPlan12_Row2}</t:Cell><t:Cell>$Resource{areaActual12_Row2}</t:Cell>" +
		"<t:Cell>$Resource{ploidyPlan12_Row2}</t:Cell><t:Cell>$Resource{ploidyActual12_Row2}</t:Cell><t:Cell>$Resource{pricePlan12_Row2}</t:Cell>" +
		"<t:Cell>$Resource{priceActual12_Row2}</t:Cell><t:Cell>$Resource{amountPlan12_Row2}</t:Cell><t:Cell>$Resource{amountActual12_Row2}</t:Cell>" +
		"<t:Cell>$Resource{recoverPlan12_Row2}</t:Cell><t:Cell>$Resource{recoverActual12_Row2}</t:Cell></t:Row>" +
		"<t:Row t:name=\"header3\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{type_Row3}</t:Cell>" +
		"<t:Cell>$Resource{productType_Row3}</t:Cell><t:Cell>$Resource{areaPlan_Row3}</t:Cell><t:Cell>$Resource{areaActual_Row3}</t:Cell>" +
		"<t:Cell>$Resource{ploidyPlan_Row3}</t:Cell><t:Cell>$Resource{ploidyActual_Row3}</t:Cell><t:Cell>$Resource{pricePlan_Row3}</t:Cell>" +
		"<t:Cell>$Resource{priceActual_Row3}</t:Cell><t:Cell>$Resource{amountPlan_Row3}</t:Cell><t:Cell>$Resource{amountActual_Row3}</t:Cell>" +
		"<t:Cell>$Resource{recoverPlan_Row3}</t:Cell><t:Cell>$Resource{recoverActual_Row3}</t:Cell><t:Cell>$Resource{areaPlan1_Row3}</t:Cell>" +
		"<t:Cell>$Resource{areaActual1_Row3}</t:Cell><t:Cell>$Resource{ploidyPlan1_Row3}</t:Cell><t:Cell>$Resource{ploidyActual1_Row3}</t:Cell>" +
		"<t:Cell>$Resource{pricePlan1_Row3}</t:Cell><t:Cell>$Resource{priceActual1_Row3}</t:Cell><t:Cell>$Resource{amountPlan1_Row3}</t:Cell>" +
		"<t:Cell>$Resource{amountActual1_Row3}</t:Cell><t:Cell>$Resource{recoverPlan1_Row3}</t:Cell><t:Cell>$Resource{recoverActual1_Row3}</t:Cell>" +
		"<t:Cell>$Resource{areaPlan2_Row3}</t:Cell><t:Cell>$Resource{areaActual2_Row3}</t:Cell><t:Cell>$Resource{ploidyPlan2_Row3}</t:Cell>" +
		"<t:Cell>$Resource{ploidyActual2_Row3}</t:Cell><t:Cell>$Resource{pricePlan2_Row3}</t:Cell><t:Cell>$Resource{priceActual2_Row3}</t:Cell>" +
		"<t:Cell>$Resource{amountPlan2_Row3}</t:Cell><t:Cell>$Resource{amountActual2_Row3}</t:Cell><t:Cell>$Resource{recoverPlan2_Row3}</t:Cell>" +
		"<t:Cell>$Resource{recoverActual2_Row3}</t:Cell><t:Cell>$Resource{areaPlan3_Row3}</t:Cell><t:Cell>$Resource{areaActual3_Row3}</t:Cell>" +
		"<t:Cell>$Resource{ploidyPlan3_Row3}</t:Cell><t:Cell>$Resource{ploidyActual3_Row3}</t:Cell><t:Cell>$Resource{pricePlan3_Row3}</t:Cell>" +
		"<t:Cell>$Resource{priceActual3_Row3}</t:Cell><t:Cell>$Resource{amountPlan3_Row3}</t:Cell><t:Cell>$Resource{amountActual3_Row3}</t:Cell>" +
		"<t:Cell>$Resource{recoverPlan3_Row3}</t:Cell><t:Cell>$Resource{recoverActual3_Row3}</t:Cell><t:Cell>$Resource{areaPlan4_Row3}</t:Cell>" +
		"<t:Cell>$Resource{areaActual4_Row3}</t:Cell><t:Cell>$Resource{ploidyPlan4_Row3}</t:Cell><t:Cell>$Resource{ploidyActual4_Row3}</t:Cell>" +
		"<t:Cell>$Resource{pricePlan4_Row3}</t:Cell><t:Cell>$Resource{priceActual4_Row3}</t:Cell><t:Cell>$Resource{amountPlan4_Row3}</t:Cell>" +
		"<t:Cell>$Resource{amountActual4_Row3}</t:Cell><t:Cell>$Resource{recoverPlan4_Row3}</t:Cell><t:Cell>$Resource{recoverActual4_Row3}</t:Cell>" +
		"<t:Cell>$Resource{areaPlan5_Row3}</t:Cell><t:Cell>$Resource{areaActual5_Row3}</t:Cell><t:Cell>$Resource{ploidyPlan5_Row3}</t:Cell>" +
		"<t:Cell>$Resource{ploidyActual5_Row3}</t:Cell><t:Cell>$Resource{pricePlan5_Row3}</t:Cell><t:Cell>$Resource{priceActual5_Row3}</t:Cell>" +
		"<t:Cell>$Resource{amountPlan5_Row3}</t:Cell><t:Cell>$Resource{amountActual5_Row3}</t:Cell><t:Cell>$Resource{recoverPlan5_Row3}</t:Cell>" +
		"<t:Cell>$Resource{recoverActual5_Row3}</t:Cell><t:Cell>$Resource{areaPlan6_Row3}</t:Cell><t:Cell>$Resource{areaActual6_Row3}</t:Cell>" +
		"<t:Cell>$Resource{ploidyPlan6_Row3}</t:Cell><t:Cell>$Resource{ploidyActual6_Row3}</t:Cell><t:Cell>$Resource{pricePlan6_Row3}</t:Cell>" +
		"<t:Cell>$Resource{priceActual6_Row3}</t:Cell><t:Cell>$Resource{amountPlan6_Row3}</t:Cell><t:Cell>$Resource{amountActual6_Row3}</t:Cell>" +
		"<t:Cell>$Resource{recoverPlan6_Row3}</t:Cell><t:Cell>$Resource{recoverActual6_Row3}</t:Cell><t:Cell>$Resource{areaPlan7_Row3}</t:Cell>" +
		"<t:Cell>$Resource{areaActual7_Row3}</t:Cell><t:Cell>$Resource{ploidyPlan7_Row3}</t:Cell><t:Cell>$Resource{ploidyActual7_Row3}</t:Cell>" +
		"<t:Cell>$Resource{pricePlan7_Row3}</t:Cell><t:Cell>$Resource{priceActual7_Row3}</t:Cell><t:Cell>$Resource{amountPlan7_Row3}</t:Cell>" +
		"<t:Cell>$Resource{amountActual7_Row3}</t:Cell><t:Cell>$Resource{recoverPlan7_Row3}</t:Cell><t:Cell>$Resource{recoverActual7_Row3}</t:Cell>" +
		"<t:Cell>$Resource{areaPlan8_Row3}</t:Cell><t:Cell>$Resource{areaActual8_Row3}</t:Cell><t:Cell>$Resource{ploidyPlan8_Row3}</t:Cell>" +
		"<t:Cell>$Resource{ploidyActual8_Row3}</t:Cell><t:Cell>$Resource{pricePlan8_Row3}</t:Cell><t:Cell>$Resource{priceActual8_Row3}</t:Cell>" +
		"<t:Cell>$Resource{amountPlan8_Row3}</t:Cell><t:Cell>$Resource{amountActual8_Row3}</t:Cell><t:Cell>$Resource{recoverPlan8_Row3}</t:Cell>" +
		"<t:Cell>$Resource{recoverActual8_Row3}</t:Cell><t:Cell>$Resource{areaPlan9_Row3}</t:Cell><t:Cell>$Resource{areaActual9_Row3}</t:Cell>" +
		"<t:Cell>$Resource{ploidyPlan9_Row3}</t:Cell><t:Cell>$Resource{ploidyActual9_Row3}</t:Cell><t:Cell>$Resource{pricePlan9_Row3}</t:Cell>" +
		"<t:Cell>$Resource{priceActual9_Row3}</t:Cell><t:Cell>$Resource{amountPlan9_Row3}</t:Cell><t:Cell>$Resource{amountActual9_Row3}</t:Cell>" +
		"<t:Cell>$Resource{recoverPlan9_Row3}</t:Cell><t:Cell>$Resource{recoverActual9_Row3}</t:Cell><t:Cell>$Resource{areaPlan10_Row3}</t:Cell>" +
		"<t:Cell>$Resource{areaActual10_Row3}</t:Cell><t:Cell>$Resource{ploidyPlan10_Row3}</t:Cell><t:Cell>$Resource{ploidyActual10_Row3}</t:Cell>" +
		"<t:Cell>$Resource{pricePlan10_Row3}</t:Cell><t:Cell>$Resource{priceActual10_Row3}</t:Cell><t:Cell>$Resource{amountPlan10_Row3}</t:Cell>" +
		"<t:Cell>$Resource{amountActual10_Row3}</t:Cell><t:Cell>$Resource{recoverPlan10_Row3}</t:Cell><t:Cell>$Resource{recoverActual10_Row3}</t:Cell>" +
		"<t:Cell>$Resource{areaPlan11_Row3}</t:Cell><t:Cell>$Resource{areaActual11_Row3}</t:Cell><t:Cell>$Resource{ploidyPlan11_Row3}</t:Cell>" +
		"<t:Cell>$Resource{ploidyActual11_Row3}</t:Cell><t:Cell>$Resource{pricePlan11_Row3}</t:Cell><t:Cell>$Resource{priceActual11_Row3}</t:Cell>" +
		"<t:Cell>$Resource{amountPlan11_Row3}</t:Cell><t:Cell>$Resource{amountActual11_Row3}</t:Cell><t:Cell>$Resource{recoverPlan11_Row3}</t:Cell>" +
		"<t:Cell>$Resource{recoverActual11_Row3}</t:Cell><t:Cell>$Resource{areaPlan12_Row3}</t:Cell><t:Cell>$Resource{areaActual12_Row3}</t:Cell>" +
		"<t:Cell>$Resource{ploidyPlan12_Row3}</t:Cell><t:Cell>$Resource{ploidyActual12_Row3}</t:Cell><t:Cell>$Resource{pricePlan12_Row3}</t:Cell>" +
		"<t:Cell>$Resource{priceActual12_Row3}</t:Cell><t:Cell>$Resource{amountPlan12_Row3}</t:Cell><t:Cell>$Resource{amountActual12_Row3}</t:Cell>" +
		"<t:Cell>$Resource{recoverPlan12_Row3}</t:Cell><t:Cell>$Resource{recoverActual12_Row3}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions>" +
		"<t:MergeBlocks><t:Head><t:Block t:top=\"0\" t:left=\"0\" t:bottom=\"2\" t:right=\"0\" /><t:Block t:top=\"0\" t:left=\"1\" t:bottom=\"2\" t:right=\"1\" />" +
		"<t:Block t:top=\"0\" t:left=\"2\" t:bottom=\"0\" t:right=\"11\" /><t:Block t:top=\"0\" t:left=\"12\" t:bottom=\"0\" t:right=\"21\" />" +
		"<t:Block t:top=\"0\" t:left=\"22\" t:bottom=\"0\" t:right=\"31\" /><t:Block t:top=\"0\" t:left=\"32\" t:bottom=\"0\" t:right=\"41\" />" +
		"<t:Block t:top=\"0\" t:left=\"42\" t:bottom=\"0\" t:right=\"51\" /><t:Block t:top=\"0\" t:left=\"52\" t:bottom=\"0\" t:right=\"61\" />" +
		"<t:Block t:top=\"0\" t:left=\"62\" t:bottom=\"0\" t:right=\"71\" /><t:Block t:top=\"0\" t:left=\"72\" t:bottom=\"0\" t:right=\"81\" />" +
		"<t:Block t:top=\"0\" t:left=\"82\" t:bottom=\"0\" t:right=\"91\" /><t:Block t:top=\"0\" t:left=\"92\" t:bottom=\"0\" t:right=\"101\" />" +
		"<t:Block t:top=\"0\" t:left=\"102\" t:bottom=\"0\" t:right=\"111\" /><t:Block t:top=\"0\" t:left=\"112\" t:bottom=\"0\" t:right=\"121\" />" +
		"<t:Block t:top=\"0\" t:left=\"122\" t:bottom=\"0\" t:right=\"131\" /><t:Block t:top=\"1\" t:left=\"2\" t:bottom=\"1\" t:right=\"3\" />" +
		"<t:Block t:top=\"1\" t:left=\"4\" t:bottom=\"1\" t:right=\"5\" /><t:Block t:top=\"1\" t:left=\"6\" t:bottom=\"1\" t:right=\"7\" />" +
		"<t:Block t:top=\"1\" t:left=\"8\" t:bottom=\"1\" t:right=\"9\" /><t:Block t:top=\"1\" t:left=\"10\" t:bottom=\"1\" t:right=\"11\" />" +
		"<t:Block t:top=\"1\" t:left=\"12\" t:bottom=\"1\" t:right=\"13\" /><t:Block t:top=\"1\" t:left=\"14\" t:bottom=\"1\" t:right=\"15\" />" +
		"<t:Block t:top=\"1\" t:left=\"16\" t:bottom=\"1\" t:right=\"17\" /><t:Block t:top=\"1\" t:left=\"18\" t:bottom=\"1\" t:right=\"19\" />" +
		"<t:Block t:top=\"1\" t:left=\"20\" t:bottom=\"1\" t:right=\"21\" /><t:Block t:top=\"1\" t:left=\"22\" t:bottom=\"1\" t:right=\"23\" />" +
		"<t:Block t:top=\"1\" t:left=\"24\" t:bottom=\"1\" t:right=\"25\" /><t:Block t:top=\"1\" t:left=\"26\" t:bottom=\"1\" t:right=\"27\" />" +
		"<t:Block t:top=\"1\" t:left=\"28\" t:bottom=\"1\" t:right=\"29\" /><t:Block t:top=\"1\" t:left=\"30\" t:bottom=\"1\" t:right=\"31\" />" +
		"<t:Block t:top=\"1\" t:left=\"32\" t:bottom=\"1\" t:right=\"33\" /><t:Block t:top=\"1\" t:left=\"34\" t:bottom=\"1\" t:right=\"35\" />" +
		"<t:Block t:top=\"1\" t:left=\"36\" t:bottom=\"1\" t:right=\"37\" /><t:Block t:top=\"1\" t:left=\"38\" t:bottom=\"1\" t:right=\"39\" />" +
		"<t:Block t:top=\"1\" t:left=\"40\" t:bottom=\"1\" t:right=\"41\" /><t:Block t:top=\"1\" t:left=\"42\" t:bottom=\"1\" t:right=\"43\" />" +
		"<t:Block t:top=\"1\" t:left=\"44\" t:bottom=\"1\" t:right=\"45\" /><t:Block t:top=\"1\" t:left=\"46\" t:bottom=\"1\" t:right=\"47\" />" +
		"<t:Block t:top=\"1\" t:left=\"48\" t:bottom=\"1\" t:right=\"49\" /><t:Block t:top=\"1\" t:left=\"50\" t:bottom=\"1\" t:right=\"51\" />" +
		"<t:Block t:top=\"1\" t:left=\"52\" t:bottom=\"1\" t:right=\"53\" /><t:Block t:top=\"1\" t:left=\"54\" t:bottom=\"1\" t:right=\"55\" />" +
		"<t:Block t:top=\"1\" t:left=\"56\" t:bottom=\"1\" t:right=\"57\" /><t:Block t:top=\"1\" t:left=\"58\" t:bottom=\"1\" t:right=\"59\" />" +
		"<t:Block t:top=\"1\" t:left=\"60\" t:bottom=\"1\" t:right=\"61\" /><t:Block t:top=\"1\" t:left=\"62\" t:bottom=\"1\" t:right=\"63\" />" +
		"<t:Block t:top=\"1\" t:left=\"64\" t:bottom=\"1\" t:right=\"65\" /><t:Block t:top=\"1\" t:left=\"66\" t:bottom=\"1\" t:right=\"67\" />" +
		"<t:Block t:top=\"1\" t:left=\"68\" t:bottom=\"1\" t:right=\"69\" /><t:Block t:top=\"1\" t:left=\"70\" t:bottom=\"1\" t:right=\"71\" />" +
		"<t:Block t:top=\"1\" t:left=\"72\" t:bottom=\"1\" t:right=\"73\" /><t:Block t:top=\"1\" t:left=\"74\" t:bottom=\"1\" t:right=\"75\" />" +
		"<t:Block t:top=\"1\" t:left=\"76\" t:bottom=\"1\" t:right=\"77\" /><t:Block t:top=\"1\" t:left=\"78\" t:bottom=\"1\" t:right=\"79\" />" +
		"<t:Block t:top=\"1\" t:left=\"80\" t:bottom=\"1\" t:right=\"81\" /><t:Block t:top=\"1\" t:left=\"82\" t:bottom=\"1\" t:right=\"83\" />" +
		"<t:Block t:top=\"1\" t:left=\"84\" t:bottom=\"1\" t:right=\"85\" /><t:Block t:top=\"1\" t:left=\"86\" t:bottom=\"1\" t:right=\"87\" />" +
		"<t:Block t:top=\"1\" t:left=\"88\" t:bottom=\"1\" t:right=\"89\" /><t:Block t:top=\"1\" t:left=\"90\" t:bottom=\"1\" t:right=\"91\" />" +
		"<t:Block t:top=\"1\" t:left=\"92\" t:bottom=\"1\" t:right=\"93\" /><t:Block t:top=\"1\" t:left=\"94\" t:bottom=\"1\" t:right=\"95\" />" +
		"<t:Block t:top=\"1\" t:left=\"96\" t:bottom=\"1\" t:right=\"97\" /><t:Block t:top=\"1\" t:left=\"98\" t:bottom=\"1\" t:right=\"99\" />" +
		"<t:Block t:top=\"1\" t:left=\"100\" t:bottom=\"1\" t:right=\"101\" /><t:Block t:top=\"1\" t:left=\"102\" t:bottom=\"1\" t:right=\"103\" />" +
		"<t:Block t:top=\"1\" t:left=\"104\" t:bottom=\"1\" t:right=\"105\" /><t:Block t:top=\"1\" t:left=\"106\" t:bottom=\"1\" t:right=\"107\" />" +
		"<t:Block t:top=\"1\" t:left=\"108\" t:bottom=\"1\" t:right=\"109\" /><t:Block t:top=\"1\" t:left=\"110\" t:bottom=\"1\" t:right=\"111\" />" +
		"<t:Block t:top=\"1\" t:left=\"112\" t:bottom=\"1\" t:right=\"113\" /><t:Block t:top=\"1\" t:left=\"114\" t:bottom=\"1\" t:right=\"115\" />" +
		"<t:Block t:top=\"1\" t:left=\"116\" t:bottom=\"1\" t:right=\"117\" /><t:Block t:top=\"1\" t:left=\"118\" t:bottom=\"1\" t:right=\"119\" />" +
		"<t:Block t:top=\"1\" t:left=\"120\" t:bottom=\"1\" t:right=\"121\" /><t:Block t:top=\"1\" t:left=\"122\" t:bottom=\"1\" t:right=\"123\" />" +
		"<t:Block t:top=\"1\" t:left=\"124\" t:bottom=\"1\" t:right=\"125\" /><t:Block t:top=\"1\" t:left=\"126\" t:bottom=\"1\" t:right=\"127\" />" +
		"<t:Block t:top=\"1\" t:left=\"128\" t:bottom=\"1\" t:right=\"129\" /><t:Block t:top=\"1\" t:left=\"130\" t:bottom=\"1\" t:right=\"131\" />" +
		"</t:Head></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		return kdtEntryStrXML;
	}
	
}
