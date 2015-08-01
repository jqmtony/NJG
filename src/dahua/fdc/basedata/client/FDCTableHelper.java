package com.kingdee.eas.fdc.basedata.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.ActionMap;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.export.ExportManager;
import com.kingdee.bos.ctrl.kdf.export.KDTables2KDSBook;
import com.kingdee.bos.ctrl.kdf.export.KDTables2KDSBookVO;
import com.kingdee.bos.ctrl.kdf.kds.KDSBook;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTAction;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTEditHelper;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTMenuManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTTransferAction;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.Kdt2Kds;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.render.IBasicRender;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDMultiLangArea;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.util.FdcArrayUtil;
import com.kingdee.eas.fdc.basedata.util.FdcCollectionUtil;
import com.kingdee.eas.framework.AbstractCoreBaseInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

public class FDCTableHelper implements FDCColorConstants{
	
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.basedata.client.FDCTableHelper");
	
	private FDCTableHelper(){}

	/**
	 *Excel风格的行列索引加行索引显示,用于定位单元格设置公式
	 */
	public static void setDebugHead(KDTable table) {
		table.setHeadDisplayMode(KDTStyleConstants.HEAD_DISPLAY_EXCEL);
		IRow addHeadRow = table.addHeadRow();
		for (int i = 0; i < table.getColumnCount(); i++) {
			addHeadRow.getCell(i).setValue((i + 1) + "");
		}
		table.getIndexColumn().getStyleAttributes().setHided(false);
	}
	
	public static void setExpErrDisplayNull(KDTable table){
//		ScriptErrorReporter rep = (ScriptErrorReporter)table.getScriptManager().getErrorHandler();
//		rep.setDiv0Value("");
		
		//也可以用如下的方法
		table.getScriptManager().setDiv0DefaultValue("0");
	}
	public static void setPaseMode(KDTable table){
		//设置表格拷贝的模式为值拷贝
		((KDTTransferAction)table.getActionMap().get(KDTAction.PASTE)).setPasteMode(KDTEditHelper.VALUE);
	}
	/**
	 * @param table @see com.kingdee.bos.ctrl.kdf.table.KDTableHelper
	 * @param mode KDTEditHelper 常量 {@link com.kingdee.bos.ctrl.kdf.table.KDTEditHelper}
	 */
	public static void setCutCopyPaseMode(KDTable table,int mode){
		//设置表格拷贝的模式为值拷贝
		((KDTTransferAction)table.getActionMap().get(KDTAction.PASTE)).setPasteMode(mode);
		((KDTTransferAction)table.getActionMap().get(KDTAction.CUT)).setCutMode(mode);
		((KDTTransferAction)table.getActionMap().get(KDTAction.COPY)).setCopyMode(mode);
	}
	
	public static void enableEnterInTable(KDTable table) {
		KDTableHelper.setEnterKeyJumpOrientation(table, KDTableHelper.HORIZON);
	}

    //回车自动新增行。
	public static void enableAutoAddLine(KDTable table)
    {
		KDTableHelper.updateEnterWithTab(table, true);

	}

	public static void disableAutoAddLine(KDTable table)
    {
		KDTableHelper.updateEnterWithTab(table, false);
	}

    //表格中最后一行，按向下箭头新增一行。
	public static void enableAutoAddLineDownArrow(KDTable table)
    {
        KDTableHelper.downArrowAutoAddRow(table, true,null);

    }
    //表格中最后一行，按向下箭头新增一行。
	public static void disableAutoAddLineDownArrow(KDTable table)
    {
        KDTableHelper.downArrowAutoAddRow(table, false,null);

    }
	/**
	 * 禁用复制
	 * @param table
	 */
	public static void disableCopy(final KDTable table){
		ActionMap actionMap = table.getActionMap();
		actionMap.remove(KDTAction.COPY);
		actionMap.remove(KDTAction.CUT);
	}
	
	/**
	 * 禁用delete键
	 * @param table
	 */
	public static void disableDelete(final KDTable table){
		ActionMap actionMap = table.getActionMap();
		actionMap.remove(KDTAction.CUT);
		actionMap.remove(KDTAction.DELETE);
		actionMap.remove(KDTAction.PASTE);
		//也可用以下代码实现
//		
//		table.setBeforeAction(new BeforeActionListener(){
//			public void beforeAction(BeforeActionEvent e)
//			{
//				if(BeforeActionEvent.ACTION_DELETE==e.getType()||BeforeActionEvent.ACTION_PASTE==e.getType()){
//					for (int i = 0; i <table.getSelectManager().size(); i++)
//					{
//						KDTSelectBlock block = table.getSelectManager().get(i);
//						for (int rowIndex = block.getBeginRow(); rowIndex <= block.getEndRow(); rowIndex++)
//						{
//							for(int colIndex=block.getBeginCol();colIndex<=block.getEndCol();colIndex++){
//								int amount_index=1;	
//								if(colIndex==amount_index) {
//									e.setCancel(true);
//									continue;
//								}
//							}
////							e.setCancel(true);
//						}
//					}
//				}
//			}
//		});
	}
	
	/**
	 * 列可移动
	 * @param table
	 * @param moveable
	 */
	public static void setColumnMoveable(KDTable table,boolean moveable){
		table.setColumnMoveable(moveable);
	}
	
	/**
	 * 隐藏索引列
	 * @param table
	 * @param hided
	 */
	public static void setIndexColumnHided(KDTable table,boolean hided){
		// 隐藏行索引
		table.getIndexColumn().getStyleAttributes().setHided(hided);
	}
	
	public static int[] getSelectedRows(KDTable table) {
		return KDTableUtil.getSelectedRows(table);
	}
	
	public static IRow getSelectedRow(KDTable table){
		return KDTableUtil.getSelectedRow(table);
	}
	
	public int getSelectedRowCount(KDTable table){
		return KDTableUtil.getSelectedRowCount(table);
	}

	/**
	 * 描述：屏蔽表格右键菜单
	 * 
	 * @param tblMain
	 * @param menuManager
	 * @author rd_skyiter_wang
	 * @createDate 2014-8-21
	 */
	public static void disableTableMenu(KDTable tblMain, KDTMenuManager menuManager) {
		// 屏蔽右键菜单
		if (null != menuManager && null != menuManager.getMenu()) {
			menuManager.getMenu().removeAll();
		}
	
		// 清除所有鼠标监听器
		KDTMouseListener[] listeners = tblMain.getListeners(KDTMouseListener.class);
		if (FdcArrayUtil.isNotEmpty(listeners)) {
			for (int i = 0; i < listeners.length; i++) {
				KDTMouseListener eventListener = listeners[i];
				tblMain.removeKDTMouseListener(eventListener);
			}
		}
	}

/**
	 * 描述：设置表格是否可以排序
	 * 
	 * @param tblMain
	 * @param flag
	 * @author rd_skyiter_wang
	 * @createDate 2014-8-21
	 */
	public static void setTableSortable(KDTable tblMain, boolean flag) {
		KDTSortManager sm = tblMain.getSortMange();
		if (null != sm) {
			sm.setEnableSortable(flag);
			sm.setSortAuto(flag);
		}
	}

	/*	*包不一致，暂时注释，等下一版本
 * //**
	 * 获取指定位置的单元格的行列号。
	 * 
	 * @param table
	 * @param x
	 * @param y
	 * @return
	 */
	public static Point cellAtPosition(KDTable table, int x, int y)
	{
		return KDTableHelper.cellAtPosition(table, x, y);
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * 设置列冻结
	 * 
	 * @param table
	 *            表格
	 * @param colIndex
	 *            列索引
	 * @param isLock
	 *            是否锁定
	 */
	public static void setColumnLocked(KDTable table, int colIndex, boolean isLock) {
		IColumn column = table.getColumn(colIndex);
		if (null != column) {
			StyleAttributes style = column.getStyleAttributes();
			style.setOwner(null);
			style.setLocked(isLock);
			table.setColumnStyle(colIndex, style, false);
		}
	}

	/**
	 * 设置列冻结
	 * 
	 * @param table
	 *            表格
	 * @param colKey
	 *            列名称
	 * @param isLock
	 *            是否锁定
	 */
	public static void setColumnLocked(KDTable table, String colKey, boolean isLock) {
		IColumn column = table.getColumn(colKey);

		if (null != column) {
			StyleAttributes style = column.getStyleAttributes();
			style.setOwner(null);
			style.setLocked(isLock);
			table.setColumnStyle(column.getColumnIndex(), style, false);
		}
	}

	/**
	 * 设置列冻结
	 * 
	 * @param table
	 *            表格
	 * @param colKeys
	 *            列名称数组
	 * @param isLock
	 *            是否锁定
	 */
	public static void setColumnLocked(KDTable table, String[] colKeys, boolean isLock) {
		for (int i = 0; i < colKeys.length; i++) {
			String colKey = colKeys[i];

			setColumnLocked(table, colKey, isLock);
		}
	}

	/**
	 * 设置列隐藏
	 * 
	 * @param table
	 *            表格
	 * @param colIndex
	 *            列索引
	 * @param isHided
	 *            是否隐藏
	 */
	public static void setColumnHided(KDTable table, int colIndex, boolean isHided) {
		IColumn column = table.getColumn(colIndex);
		if (null != column) {
			StyleAttributes style = column.getStyleAttributes();

			style.setHided(isHided);
		}
	}

	/**
	 * 设置列隐藏
	 * 
	 * @param table
	 *            表格
	 * @param colKey
	 *            列名称
	 * @param isHided
	 *            是否隐藏
	 */
	public static void setColumnHided(KDTable table, String colKey, boolean isHided) {
		IColumn column = table.getColumn(colKey);
		if (null != column) {
			StyleAttributes style = column.getStyleAttributes();

			style.setHided(isHided);
		}
	}

	/**
	 * 设置列隐藏
	 * 
	 * @param table
	 *            表格
	 * @param colKeys
	 *            列名称数组
	 * @param isHided
	 *            是否隐藏
	 */
	public static void setColumnHided(KDTable table, String[] colKeys, boolean isHided) {
		for (int i = 0; i < colKeys.length; i++) {
			String colKey = colKeys[i];

			setColumnHided(table, colKey, isHided);
		}
	}

	/**
	 * 设置列是否必录
	 * 
	 * @param table
	 *            表格
	 * @param colIndex
	 *            列索引
	 * @param isRequired
	 *            是否必录
	 */
	public static void setColumnRequired(KDTable table, int colIndex, boolean isRequired) {
		IColumn column = table.getColumn(colIndex);
		if (null != column) {
			column.setRequired(isRequired);
		}
	}

	/**
	 * 设置列是否必录
	 * 
	 * @param table
	 *            表格
	 * @param colKey
	 *            列名称
	 * @param isRequired
	 *            是否必录
	 */
	public static void setColumnRequired(KDTable table, String colKey, boolean isRequired) {
		IColumn column = table.getColumn(colKey);
		if (null != column) {
			column.setRequired(isRequired);
		}
	}

	/**
	 * 设置列是否必录
	 * 
	 * @param table
	 *            表格
	 * @param colKeys
	 *            列名称数组
	 * @param isRequired
	 *            是否必录
	 */
	public static void setColumnRequired(KDTable table, String[] colKeys, boolean isRequired) {
		for (int i = 0; i < colKeys.length; i++) {
			String colKey = colKeys[i];

			setColumnRequired(table, colKey, isRequired);
		}
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * 描述：设置列宽度
	 * 
	 * @param table
	 *            表格
	 * @param colIndex
	 *            列索引
	 * @param width
	 *            宽度
	 * @author rd_skyiter_wang
	 * @createDate 2014-9-10
	 */
	public static void setColumnWidth(KDTable table, int colIndex, int width) {
		IColumn column = table.getColumn(colIndex);
		if (null != column) {
			column.setWidth(width);
		}
	}

	/**
	 * 描述：设置列宽度
	 * 
	 * @param table
	 *            表格
	 * @param colKey
	 *            列名称
	 * @param width
	 *            宽度
	 * @author rd_skyiter_wang
	 * @createDate 2014-9-10
	 */
	public static void setColumnWidth(KDTable table, String colKey, int width) {
		IColumn column = table.getColumn(colKey);
		if (null != column) {
			column.setWidth(width);
		}
	}

	/**
	 * 描述：设置列宽度
	 * 
	 * @param table
	 *            表格
	 * @param colKeys
	 *            列名称数组
	 * @param width
	 *            宽度
	 * @author rd_skyiter_wang
	 * @createDate 2014-9-10
	 */
	public static void setColumnWidth(KDTable table, String[] colKeys, int width) {
		for (int i = 0; i < colKeys.length; i++) {
			String colKey = colKeys[i];

			setColumnWidth(table, colKey, width);
		}
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * 设置行冻结
	 * 
	 * @param table
	 * @param rowIndex
	 *            冻结位置
	 * @param isLock
	 */
	public static void setRowLocked(KDTable table, int rowIndex, boolean isLock)
	{
		IRow row = table.getRow(rowIndex);
		if (row != null)
		{
			StyleAttributes style = row.getStyleAttributes();
			style.setOwner(null);
			style.setLocked(!isLock);
			style.setLocked(isLock);
			table.setRowStyle(rowIndex, style, false);
		}
	}
	
	/**
	 * 统一修改所有行的高度。
	 * @param table
	 * @param h
	 */
	public static void setAllRowHeight(KDTable table, int h)
	{
		KDTableHelper.setAllRowHeight(table, h);
	}
	
	/**
	 * 获取table单元格显示的内容。
	 * @param table
	 * @param rIndex
	 * @param cIndex
	 * @return
	 */
	public static Object getCellDisplayValue(KDTable table, int rIndex, int cIndex)
	{
		return KDTableHelper.getCellDisplayValue(table, rIndex, cIndex);
	}

/*	*//**
	 * 根据表列的内容获取表列的最大宽度
	 * @param table
	 * @param colIndex
	 * @return
	 *//*
	public static int getMaxColumnWidth(KDTable table,int colIndex, boolean ishead) 
	{
		return KDTableHelper.getMaxColumnWidth(table, colIndex, ishead);
	}
	
	*//**
	 * 调整列为最合适的列宽
	 * @param colIndex 列索引
	 * @return 列宽改变 true, 列宽没改变false
	 *//*
	public static boolean autoFitColumnWidth(KDTable table, int colIndex)
	{
		return KDTableHelper.autoFitColumnWidth(table, colIndex);
	}
	
	*//**
	 * 调整列为最合适的列宽
	 * @param colIndex 列索引
	 * @return 列宽改变 true, 列宽没改变false
	 *//*
	public static boolean autoFitColumnWidth(KDTable table, int colIndex,int extend)
	{
		return KDTableHelper.autoFitColumnWidth(table, colIndex, extend);
	}
	*//**
	 * 调整列为最合适的列宽
	 * @param colIndex 列索引
	 * @return 列宽改变 true, 列宽没改变false
	 *//*
	public static boolean autoFitRowHeight(KDTable table, int rowIndex)
	{
		return KDTableHelper.autoFitRowHeight(table, rowIndex);
	}
	
	*//**
	 * 调整列为最合适的列宽
	 * @param colIndex 列索引
	 * @return 列宽改变 true, 列宽没改变false
	 *//*
	public static boolean autoFitRowHeight(KDTable table, int rowIndex,int extend)
	{
		return KDTableHelper.autoFitRowHeight(table, rowIndex, extend);
	}*/

	
	
	public static void exportToExcel(KDTable table, boolean isSelected) throws IOException {
		File tempFile = File.createTempFile("eastemp", ".xls");
		table.getIOManager().setTempFileDirection(tempFile.getPath());
		table.getIOManager().exportExcelToTempFile(isSelected);
		tempFile.deleteOnExit();
	}

	// 多个KDTable导出到excel

	public static void exportToExcel(Collection tables) throws Exception {
		exportToExcel(tables, true);
	}

	public static void exportToExcel(JTabbedPane tabbedPane) throws Exception {
		exportToExcel(tabbedPane, true);
	}

	public static void exportToExcel(JTabbedPane tabbedPane, boolean withHead) throws Exception {
		List list = new ArrayList();
		for (int i = 0, count = tabbedPane.getTabCount(); i < count; i++) {
			list.add(tabbedPane.getComponentAt(i));
		}
		exportToExcel(list, withHead);
	}

	public static void exportToExcel(Collection tables, boolean withHead) throws Exception {
		File tempFile = KDTMenuManager.createTempFile(File.createTempFile("fdc" + System.currentTimeMillis(), ".xls").getPath());
		if (tempFile != null) {
			String tempFileName = tempFile.getAbsolutePath();
			ExportManager man = new ExportManager();
			KDSBook book = new KDSBook("kdtable export");
			for (Iterator iterator = tables.iterator(); iterator.hasNext();) {
				KDTable table = (KDTable) iterator.next();
				Kdt2Kds trans = new Kdt2Kds(table, book);
				trans.setWithHead(withHead);
				trans.setWithIndexColumn(false);
				trans.setWithHiddenCol(false);
				trans.setRange(null);
				book.addSheet(null, trans.transform(table.getName() == null ? table.getID() : table.getName()));
			}
			man.exportToExcel(book, tempFileName);
			KDTMenuManager.openFileInExcel(tempFileName);
		}
	}
	
	public static void addTableMenu(KDTable table){
		KDTMenuManager tm=new KDTMenuManager(table);
	}

	public static KDTDefaultCellEditor getCellNumberEditor(){
		KDFormattedTextField kdc = new KDFormattedTextField();
        kdc.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
        kdc.setPrecision(2);
//        kdc.setMinimumValue(FDCHelper.MIN_VALUE);
        kdc.setMinimumValue(FDCHelper.ZERO);
        kdc.setMaximumValue(FDCHelper.MAX_VALUE);
        kdc.setHorizontalAlignment(KDFormattedTextField.RIGHT);
        kdc.setSupportedEmpty(true);
        kdc.setVisible(true);
        kdc.setEnabled(true);
//        kdc.setRequired(false);
        KDTDefaultCellEditor editor = new KDTDefaultCellEditor(kdc);
        return editor;
	}
	public static KDTDefaultCellEditor getCellNumberEditor(BigDecimal min,BigDecimal max,boolean isRequired){
		if(min==null){
			min=FDCHelper.MIN_VALUE;
		}
		if(max==null){
			max=FDCHelper.MAX_VALUE;
		}
		KDFormattedTextField kdc = new KDFormattedTextField();
        kdc.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
        kdc.setPrecision(2);
        kdc.setMinimumValue(min);
        kdc.setMaximumValue(max);
        kdc.setHorizontalAlignment(KDFormattedTextField.RIGHT);
        kdc.setSupportedEmpty(true);
        kdc.setVisible(true);
        kdc.setEnabled(true);
        kdc.setRequired(isRequired);
        KDTDefaultCellEditor editor = new KDTDefaultCellEditor(kdc);
        return editor;
	}
	public static KDTIndexColumn getMyKDTIndexColumn(KDTable table){
		KDTIndexColumn myKdtIndexColumn=new KDTIndexColumn(table){
			public String getText(int rowIndex) {
				//为所有不隐藏的行
				int myRowIndex=1;
				for(int i=0;i<rowIndex;i++){
					if(table.getRow(i).getStyleAttributes().isHided()){
						continue;
					}
					myRowIndex++;
				}
				return myRowIndex+"";
			}
		    public com.kingdee.bos.ctrl.kdf.util.style.Style getStyle()
		    {
		        return table.getIndexColumn().getStyle();
		    }
		    public int getWidth()
		    {
		        return table.getIndexColumn().getWidth();
		    }
		};
		return myKdtIndexColumn;
	}
	/**
	 * 禁用右键导出功能,万科的权限要求成本数据库的Excel不允许导出
	 */
	public static void disableExport(CoreUI ui, KDTable table) {
		String menuItemExportExcel = "menuItemExportExcel";
		String menuItemExportSelected = "menuItemExportSelected";
		String menuMail = "menuMail";
		String menuPublishReport = "menuPublishReport";
		if (table == null || ui == null) {
			return;
		}
		KDTMenuManager tm = ui.getMenuManager(table);
		if (tm != null) {
			Component[] menus = tm.getMenu().getComponents();
			for (int i = 0; i < menus.length; i++) {
				// 隐藏处理。
				if (menus[i] instanceof JMenuItem) {
					JMenuItem menu = (JMenuItem) menus[i];
					if (menu != null && menu.getName() != null && 
							(menu.getName().equalsIgnoreCase(menuItemExportExcel) 
									|| menu.getName().equalsIgnoreCase(menuItemExportSelected)
									||menu.getName().equalsIgnoreCase(menuMail)
									||menu.getName().equalsIgnoreCase(menuPublishReport))) {
						menu.setVisible(false);
					} 
				}
			}
		}
	}
	public static void disableExportByPerimission(CoreUI ui,KDTable table,String actionPK) throws EASBizException, BOSException{
		if(!PermissionFactory.getRemoteInstance().hasFunctionPermission(
				new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId().toString()),
				new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId().toString()),
				new MetaDataPK(ui.getClass().getName()),
				new MetaDataPK(actionPK))){
			disableExport(ui, table);
		}
	}
	
	public static KDTDefaultCellEditor getTxtCellEditor(int length,boolean isRequired){
		KDTextField textField = new KDTextField();
		textField.setMaxLength(length);
		textField.setRequired(isRequired);
		KDTDefaultCellEditor txtEditor = new KDTDefaultCellEditor(textField);
		return txtEditor;
	}
	public static KDTDefaultCellEditor getF7CellEditor(KDBizPromptBox f7,boolean isRequired){
		KDTDefaultCellEditor txtEditor = new KDTDefaultCellEditor(f7);
		f7.setRequired(isRequired);
		return txtEditor;
	}

	// 更多方法@see com.kingdee.bos.ctrl.kdf.table.KDTableHelper
	
	/**
	 * 导出多个Table 生成到Excel
	 * @param tables 他的项是一个Object数据 objs[0]=title,object[1]=table
	 * @throws Exception
	 */
	public static void exportExcel(CoreUI ui,List tables) throws Exception
    {
    	//Excel 不允许页签名同,如果相同则修改页签名加-i
		int index=1;
		for(int i=0;i<tables.size();i++){
			Object objs[]=(Object[])tables.get(i);
            String title = (String)objs[0];
            
            for(int j=i+1;j<tables.size();j++){
            	Object tempObjs[]=(Object[])tables.get(j);
                String title1 = (String)tempObjs[0];
                if(title!=null&&title1!=null&&title1.equals(title)){
                	title=title+"_"+index;
                	index++;
                	objs[0]=title;
                	continue;
                }
            }
            
		}
        ExportManager exportM = new ExportManager();
        String path = null;
        File tempFile = File.createTempFile("eastemp",".xls");
        path = tempFile.getCanonicalPath();
        KDTables2KDSBookVO[] tablesVO = new KDTables2KDSBookVO[tables.size()];
        for(int i =0;i<tables.size();i++)
        {
        	Object objs[]=(Object[])tables.get(i);
            String title = (String)objs[0];
            KDTable table=(KDTable)objs[1];
            tablesVO[i] = new KDTables2KDSBookVO(table);
            title=title.replaceAll("[{\\\\}{\\*}{\\?}{\\[}{\\]}{\\/}]", "|");
			tablesVO[i].setTableName(title);
        }
        KDSBook book = null;
        book = KDTables2KDSBook.getInstance().exportKDTablesToKDSBook(tablesVO,true,true);

        exportM.exportToExcel(book, path);
        
		// 尝试用excel打开
		try
		{
			KDTMenuManager.openFileInExcel(path);
			tempFile.deleteOnExit();
		}
		catch (IOException e2)
		{
			e2.printStackTrace();
			// @AbortException
			
			// excel打开失败，保存到指定位置
			KDFileChooser fileChooser = new KDFileChooser();
			int result = fileChooser.showSaveDialog(ui);
			if (result == KDFileChooser.APPROVE_OPTION)
			{
				// File dest = this.getFileChooser().getSelectedFile();
				File dest = fileChooser.getSelectedFile();
				try
				{
					// 重命名临时文件到指定目标
					File src = new File(path);
					if (dest.exists())
						dest.delete();
					src.renameTo(dest);
				}
				catch (Exception e3)
				{
					// @AbortException
					ui.handUIExceptionAndAbort(e3);
				}
			}
		}

    }

	
	/**
	 * 设置table根据某列输入的内容自动换行，并且自适应行高
	 * @param table 待设置的table
	 * @param cloumnKey 依据的列的key值
	 * @param maxLength 允许的最大长度
	 * @author owen_wen 2010-12-07
	 */
	public static void autoFitRowHeight(KDTable table, String cloumnKey, int maxLength){
		// 设置 cloumnKey 列的样式
		KDMultiLangArea textField = new KDMultiLangArea();
		textField.setMaxLength(maxLength);
		textField.setAutoscrolls(true);
		textField.setEditable(true);
		KDTDefaultCellEditor cellEditor = new KDTDefaultCellEditor(textField);
		table.getColumn(cloumnKey).getStyleAttributes().setWrapText(true);
		
		for (int i = 0; i< table.getRowCount(); i++) {			
			KDTableHelper.autoFitRowHeight(table, i, 5);
			if (table.getRow(i).getCell(cloumnKey).getValue() != null)
				textField.setDefaultLangItemData(table.getRow(i).getCell(cloumnKey).getValue().toString());
			table.getRow(i).getCell(cloumnKey).setEditor(cellEditor);			
		}
	}

	/**
	 * 为KDTable产生合计行footRow，并返回。
	 * <p>
	 * <li>处理了table.getFootManager()为null的情况，当为null时，为之生成一个FootManager
	 * 
	 * @param table
	 *            待产生合计行的KDTable
	 * @return 合计行
	 * @Author：owen_wen
	 * @CreateTime：2012-10-24
	 */
	public static IRow generateFootRow(KDTable table) {
		IRow footRow = null;
		KDTFootManager footRowManager = table.getFootManager();
		if (footRowManager == null) {
			String total = EASResource.getString(FrameWorkClientUtils.strResource + "Msg_Total");
			footRowManager = new KDTFootManager(table);
			footRowManager.addFootView();
			table.setFootManager(footRowManager);
			footRow = footRowManager.addFootRow(0);
			footRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
			table.getIndexColumn().setWidthAdjustMode(KDTIndexColumn.WIDTH_MANUAL);
			table.getIndexColumn().setWidth(30);
			footRowManager.addIndexText(0, total);
		} else {
			footRow = footRowManager.getFootRow(0);
		}

		return footRow;
	}

	/**
	 * 功能：添加合计行 <br>
	 * <b>注意：该方法会对table进行全行扫描，意味着会实模式取数，当数据量很大时一次全部取出会有性能问题。<b>
	 * @author owen_wen 2010-12-09
	 * @param table	 指定的KDTable
	 * @param fields   需要合计的列
	 */
	public static IRow apendFootRow(KDTable table, String fields[]) {
		int size = fields.length;
		if (size == 0)
			return null;
		Map sumValue = new HashMap();
		// 利用getRowCount的到的行可能不正确
		int count = table.getRowCount3();

		for (int i = 0; i < fields.length; i++) {
			sumValue.put(fields[i], new BigDecimal("0.00"));
		}
		IRow footRow = null;
		KDTFootManager footManager = table.getFootManager();
		if (footManager == null) {
			footManager = new KDTFootManager(table);
			footManager.addFootView();
			table.setFootManager(footManager);
		}
		// 计算所有指定行的合计值
		footRow = footManager.getFootRow(0);
		for (int i = 0; i < count; i++) {
			IRow row = table.getRow(i);
			for (int j = 0; j < fields.length; j++) {
				sumValueForCell(row, fields[j], sumValue);
			}
		}

		if (footRow == null) {
			footRow = footManager.addFootRow(0);
		}

		String total = EASResource.getString(FrameWorkClientUtils.strResource + "Msg_Total");

		table.getIndexColumn().setWidthAdjustMode(KDTIndexColumn.WIDTH_MANUAL);
		table.getIndexColumn().setWidth(30);
		footManager.addIndexText(0, total);
		footRow.getStyleAttributes().setBackground(new Color(0xf6, 0xf6, 0xbf));
		for (int i = 0; i < size; i++) {
			String colName = fields[i];
			// 设置合计行显示样式
			footRow.getCell(colName).getStyleAttributes().setNumberFormat(FDCHelper.strDataFormat);
			footRow.getCell(colName).getStyleAttributes().setHorizontalAlign(
					HorizontalAlignment.RIGHT);
			footRow.getCell(colName).getStyleAttributes().setFontColor(Color.black);
		}

		// 设置合计行的值
		for (int i = 0; i < fields.length; i++) {
			footRow.getCell(fields[i]).setValue(sumValue.get(fields[i]));
		}
		
		return footRow;
	}
	
	private static void sumValueForCell(IRow row, String key, Map sumValue) {
		ICell cell = row.getCell(key);

		if (cell != null) {
			Object obj = cell.getValue();
			if (obj != null) {
				BigDecimal keyValue = (BigDecimal) sumValue.get(key);
				keyValue = keyValue.add(new BigDecimal(obj.toString()));
				sumValue.put(key, keyValue);
			}
		}
	}
	

	/**
	 * 根据指定的行和列，在后面添加行并计算合计
	 * 
	 * @author emanon 2011-03-02
	 * 
	 * @param beginRow
	 *            开始计算行，必须大于0
	 * @param endRow
	 *            结束计算行，必须小于table总行数
	 * @param cols
	 *            需要计算合计的列名数组
	 * @param table
	 *            需要计算合计的表格
	 */
	public static IRow calcSumForTable(int beginRow, int endRow, String[] cols,
			KDTable table) {
		int len = cols.length;
		if (table == null || beginRow < 0 || endRow >= table.getRowCount()
				|| beginRow > endRow || len < 1) {
			return null;
		}
		// 计算
		Object[] sum = new Object[len];
		for (int i = beginRow; i <= endRow; i++) {
			IRow row = table.getRow(i);
			for (int j = 0; j < len; j++) {
				ICell cell = row.getCell(cols[j]);
				if (cell == null) {
					continue;
				}
				Object obj = cell.getValue();
				if (obj instanceof BigDecimal) {
					BigDecimal old = (BigDecimal) sum[j];
					BigDecimal cur = (BigDecimal) obj;
					old = old == null ? FDCHelper.ZERO : old;
					cur = cur == null ? FDCHelper.ZERO : cur;
					old = old.add(cur);
					sum[j] = old;
				}
			}
		}
		// 添加行填值
		IRow row = table.addRow(endRow + 1);
		row.getStyleAttributes().setLocked(true);
		row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
		for (int i = 0; i < len; i++) {
			ICell cell = row.getCell(cols[i]);
			if (cell == null) {
				continue;
			}
			cell.setValue(sum[i]);
		}
		return row;
	}

	/**
	 * 根据指定的行和列，在后面添加行并计算合计
	 * 
	 * @author emanon 2011-03-02
	 * 
	 * @param beginRow
	 *            开始计算行，必须大于0
	 * @param endRow
	 *            结束计算行，必须小于table总行数
	 * @param cols
	 *            需要计算合计的列号数组
	 * @param table
	 *            需要计算合计的表格
	 */
	public static IRow calcSumForTable(int beginRow, int endRow, int[] cols,
			KDTable table) {
		int len = cols.length;
		if (table == null || beginRow < 0 || endRow >= table.getRowCount()
				|| beginRow > endRow || len < 1) {
			return null;
		}
		// 计算
		Object[] sum = new Object[len];
		for (int i = beginRow; i <= endRow; i++) {
			IRow row = table.getRow(i);
			for (int j = 0; j < len; j++) {
				ICell cell = row.getCell(cols[j]);
				if (cell == null) {
					continue;
				}
				Object obj = cell.getValue();
				if (obj instanceof BigDecimal) {
					BigDecimal old = (BigDecimal) sum[j];
					BigDecimal cur = (BigDecimal) obj;
					old = old == null ? FDCHelper.ZERO : old;
					cur = cur == null ? FDCHelper.ZERO : cur;
					old = old.add(cur);
					sum[j] = old;
				}
			}
		}
		// 添加行填值
		IRow row = table.addRow(endRow + 1);
		row.getStyleAttributes().setLocked(true);
		row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
		for (int i = 0; i < len; i++) {
			ICell cell = row.getCell(cols[i]);
			if (cell == null) {
				continue;
			}
			cell.setValue(sum[i]);
		}
		return row;
	}
	
	/**
	 * 检查是否有选中表格中的行，如果没有，给出提示信息“请先选择记录行！” ，
	 * 并中断当前线程，后续业务不做处理
	 * @param table 要检查的table
	 * @Author：owen_wen
	 * @CreateTime：2012-10-9
	 */
	public static void checkSelectedAndAbort(Component ui, KDTable table) {
		if (table.getSelectManager().getActiveRowIndex() == -1) {
			MsgBox.showWarning(ui, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
			SysUtil.abort();
		}
	}

	/**
	 * 
	 * 描述：带级次的table金额列向上汇总
	 * @param table 待按级次向上汇总的表
	 * @param rowIndex 从哪一行开始汇总
	 * @param columnNames 要汇总哪些列
	 * @Author：owen_wen
	 * @CreateTime：2013-5-20
	 * @deprecated 该方法不具有通用性（行对象必须是CostAccountInfo），建发的新招标补丁发放后需要删除掉 Aded by Owen_wen 2013-10-11
	 */
	public static void calcTotalAmount(KDTable table, int rowIndex, String[] columnNames) {
		if (table.getRowCount() <= 1) {
			return;
		}

		int length = columnNames.length;
		BigDecimal[] totalAmt = new BigDecimal[length];

		CostAccountInfo costAccountInfo = null;
		CostAccountInfo parentCostAccountInfo = null;
		IRow row = null;
		IRow parentRow = null;
		int i = 0;
		int j = 0;
		// 带级次的table金额列向上汇总，原来的算法是从上向下递归的，存在严重性能问题。
		// 现在改为从下向上扫描，可以大大缩短扫描次数 by skyiter_wang 2013/09/10
		for (rowIndex = table.getRowCount() - 1; rowIndex >= 0; rowIndex--) {
			row = table.getRow(rowIndex);
			costAccountInfo = (CostAccountInfo) row.getUserObject();
			for (i = 0; i < length; i++) {
				totalAmt[i] = FDCNumberHelper.toBigDecimal(row.getCell(columnNames[i]).getValue());
			}

			for (j = rowIndex - 1; j >= 0; j--) {
				parentRow = table.getRow(j);
				parentCostAccountInfo = (CostAccountInfo) parentRow.getUserObject();
				if (null != costAccountInfo.getParent() && costAccountInfo.getParent().getId().equals(parentCostAccountInfo.getId())) {
					for (i = 0; i < length; i++) {
						totalAmt[i] = FDCNumberHelper.add(totalAmt[i], parentRow.getCell(columnNames[i]).getValue());
						parentRow.getCell(columnNames[i]).setValue(totalAmt[i]);
					}

					// 找到第一层上级，就直接终止循环。上级的上级的金额累加，由上级来完成
					break;
				}
			}
		}
	}

	/**
	 * 
	 * 描述：带级次的table金额列向上汇总
	 * @param table 待按级次向上汇总的表
	 * @param beginRowIndex 从哪一行开始汇总
	 * @param columnNames 要汇总哪些列
	 * @param idColumnName id列
	 * @param parentIdColumnName 父级ID列
	 * @Author：owen_wen
	 * @CreateTime：2013-5-20
	 */
	public static void calcTotalAmount(KDTable table, int beginRowIndex, String[] columnNames, String idColumnName, String parentIdColumnName) {
		if (table.getRowCount() <= 1) {
			return;
		}
		
		if (table.getColumn(idColumnName) == null || table.getColumn(parentIdColumnName) == null) {
			throw new IllegalArgumentException("表格未定义id列或父级ID列，请检查入口参数");
		}

		int columnSize = columnNames.length;
		BigDecimal[] totalAmt = new BigDecimal[columnSize];
		
		// 带级次的table金额列向上汇总，原来的算法是从上向下递归的，存在严重性能问题。
		// 现在改为从下向上扫描，可以大大缩短扫描次数 by skyiter_wang 2013/09/10
		
		Set parentIds = new HashSet();
		for (int rowIndex = table.getRowCount() - 1; rowIndex >= beginRowIndex; rowIndex--) {
			IRow row = table.getRow(rowIndex);
			for (int i = 0; i < columnSize; i++) {
				totalAmt[i] = FDCNumberHelper.toBigDecimal(row.getCell(columnNames[i]).getValue());
			}
			
			for (int j = rowIndex - 1; j >= beginRowIndex; j--) {
				IRow parentRow = table.getRow(j);
				Object parentId = row.getCell(parentIdColumnName).getValue();
				if (parentId != null && parentRow.getCell(idColumnName).getValue().toString().equals(parentId.toString())) {
					//是否首次找到parent，如果是首次找到，需要先清空parents行中的值，否则是累加
					boolean isFirstFindParent = parentIds.add(parentRow.getCell(idColumnName).getValue().toString()); 
					for (int i = 0; i < columnSize; i++) {
						if (!isFirstFindParent) {
							totalAmt[i] = FDCNumberHelper.add(totalAmt[i], parentRow.getCell(columnNames[i]).getValue());
						}
						parentRow.getCell(columnNames[i]).setValue(totalAmt[i]);
					}

					// 找到第一层上级，就直接终止循环。上级的上级的金额累加，由上级来完成
					break;
				}
			}
		}
	}
	
	/**
	 * 
	 * 描述：带级次的table金额列向上汇总
	 * @param table 待按级次向上汇总的表
	 * @param beginRowIndex 从哪一行开始汇总
	 * @param columnNames 要汇总哪些列
	 * @Author：owen_wen
	 * @CreateTime：2013-5-20
	 */
	public static void calcTotalAmount(KDTable table, String[] columnNames) {
		calcTotalAmount(table, 0, columnNames, "id", "parent.id");
	}

	/**
	 * 取得单元格映射
	 * 
	 * @param table
	 *            表格
	 * @param columNames
	 *            列名数组
	 * @param startRowIndex
	 *            起始行索引
	 * @param endRowIndex
	 *            结束行索引
	 * @return
	 */
	public static Map getCellMap(KDTable table, String[] columNames, int startRowIndex, int endRowIndex) {
		Map cellMap = new LinkedHashMap();

		int rowCount = table.getRowCount();
		if (startRowIndex <= endRowIndex && startRowIndex < rowCount) {
			int minRowIndex = (0 > startRowIndex) ? 0 : startRowIndex;
			int maxRowIndex = (rowCount - 1 < endRowIndex) ? (rowCount - 1) : endRowIndex;
			int length = columNames.length;

			String columName = null;
			ICell cell = null;
			List cellList = null;

			for (int i = 0; i < length; i++) {
				columName = columNames[i];
				cellList = (List) cellMap.get(columName);
				if (null == cellList) {
					cellList = new ArrayList();
					cellMap.put(columName, cellList);
				}

				for (int j = minRowIndex; j < maxRowIndex; j++) {
					cell = table.getCell(j, columName);
					if (null != cell) {
						cellList.add(cell);
					} else {
						break;
					}
				}

			}
		}

		return cellMap;
	}

	/**
	 * 取得单元格映射
	 * 
	 * @param table
	 *            表格
	 * @param columNames
	 *            列名数组
	 * @return
	 */
	public static Map getCellMap(KDTable table, String[] columNames) {
		return getCellMap(table, columNames, 0, table.getRowCount() - 1);
	}

	/**
	 * 取得单元格列表
	 * 
	 * @param table
	 *            表格
	 * @param columName
	 *            列名
	 * @param startRowIndex
	 *            起始行索引
	 * @param endRowIndex
	 *            结束行索引
	 * @return
	 */
	public static List getCellList(KDTable table, String columName, int startRowIndex, int endRowIndex) {
		Map cellMap = getCellMap(table, new String[] { columName }, startRowIndex, endRowIndex);

		return (List) cellMap.get(columName);
	}

	/**
	 * 取得单元格列表
	 * 
	 * @param table
	 *            表格
	 * @param columName
	 *            列名
	 * @return
	 */
	public static List getCellList(KDTable table, String columName) {
		return getCellList(table, columName, 0, table.getRowCount());
	}

	/**
	 * 取得单元格值映射
	 * 
	 * @param table
	 *            表格
	 * @param columNames
	 *            列名数组
	 * @param startRowIndex
	 *            起始行索引
	 * @param endRowIndex
	 *            结束行索引
	 * @return
	 */
	public static Map getCellValueMap(KDTable table, String[] columNames, int startRowIndex, int endRowIndex) {
		Map cellValueMap = new LinkedHashMap();

		int rowCount = table.getRowCount();
		if (startRowIndex <= endRowIndex && startRowIndex < rowCount) {
			int minRowIndex = (0 > startRowIndex) ? 0 : startRowIndex;
			int maxRowIndex = (rowCount - 1 > endRowIndex) ? (rowCount - 1) : endRowIndex;
			int length = columNames.length;

			String columName = null;
			ICell cell = null;
			Object cellValue = null;
			List cellValueList = null;

			for (int i = 0; i < length; i++) {
				columName = columNames[i];
				cellValueList = (List) cellValueMap.get(columName);
				if (null == cellValueList) {
					cellValueList = new ArrayList();
					cellValueMap.put(columName, cellValueList);
				}

				for (int j = minRowIndex; j < maxRowIndex; j++) {
					cell = table.getCell(j, columName);
					if (null != cell) {
						cellValue = cell.getValue();
						cellValueList.add(cellValue);
					} else {
						break;
					}
				}

			}
		}

		return cellValueMap;
	}

	/**
	 * 取得单元格值映射
	 * 
	 * @param table
	 *            表格
	 * @param columNames
	 *            列名数组
	 * @return
	 */
	public static Map getCellValueMap(KDTable table, String[] columNames) {
		return getCellValueMap(table, columNames, 0, table.getRowCount() - 1);
	}

	/**
	 * 取得单元格值列表
	 * 
	 * @param table
	 *            表格
	 * @param columName
	 *            列名
	 * @param startRowIndex
	 *            起始行索引
	 * @param endRowIndex
	 *            结束行索引
	 * @return
	 */
	public static List getCellValueList(KDTable table, String columName, int startRowIndex, int endRowIndex) {
		Map cellValueMap = getCellValueMap(table, new String[] { columName }, startRowIndex, endRowIndex);

		return (List) cellValueMap.get(columName);
	}

	/**
	 * 取得单元格值列表
	 * 
	 * @param table
	 *            表格
	 * @param columName
	 *            列名
	 * @return
	 */
	public static List getCellValueList(KDTable table, String columName) {
		return getCellValueList(table, columName, 0, table.getRowCount());
	}

	/**
	 * 取得单元格值
	 * 
	 * @param table
	 *            表格
	 * @param columName
	 *            列名
	 * @param rowIndex
	 *            行索引
	 * @return
	 */
	public static Object getCellValue(KDTable table, String columName, int rowIndex) {
		Object value = null;

		List list = getCellValueList(table, columName, rowIndex, rowIndex);
		if (FdcCollectionUtil.isNotEmpty(list)) {
			value = list.get(0);
		}

		return value;
	}

	/**
	 * 取得单元格对应的字符串值映射
	 * 
	 * @param table
	 *            表格
	 * @param columNames
	 *            列名数组
	 * @param startRowIndex
	 *            起始行索引
	 * @param endRowIndex
	 *            结束行索引
	 * @return
	 */
	public static Map getCellStringValueMap(KDTable table, String[] columNames, int startRowIndex, int endRowIndex) {
		Map cellStringValueMap = new LinkedHashMap();

		int rowCount = table.getRowCount();
		if (startRowIndex <= endRowIndex && startRowIndex < rowCount) {
			int minRowIndex = (0 > startRowIndex) ? 0 : startRowIndex;
			int maxRowIndex = (rowCount - 1 > endRowIndex) ? (rowCount - 1) : endRowIndex;
			int length = columNames.length;

			String columName = null;
			ICell cell = null;
			Object cellStringValue = null;
			List cellStringValueList = null;

			for (int i = 0; i < length; i++) {
				columName = columNames[i];
				cellStringValueList = (List) cellStringValueMap.get(columName);
				if (null == cellStringValueList) {
					cellStringValueList = new ArrayList();
					cellStringValueMap.put(columName, cellStringValueList);
				}

				for (int j = minRowIndex; j < maxRowIndex; j++) {
					cell = table.getCell(j, columName);
					if (null != cell) {
						cellStringValue = cell.getValue();

						if (null != cellStringValue) {
							cellStringValueList.add(cellStringValue.toString());
						} else {
							cellStringValueList.add(cellStringValue);
						}

					} else {
						break;
					}
				}

			}
		}

		return cellStringValueMap;
	}

	/**
	 * 取得单元格对应的字符串值映射
	 * 
	 * @param table
	 *            表格
	 * @param columNames
	 *            列名数组
	 * @return
	 */
	public static Map getCellStringValueMap(KDTable table, String[] columNames) {
		return getCellStringValueMap(table, columNames, 0, table.getRowCount() - 1);
	}

	/**
	 * 取得单元格对应的字符串值列表
	 * 
	 * @param table
	 *            表格
	 * @param columName
	 *            列名
	 * @param startRowIndex
	 *            起始行索引
	 * @param endRowIndex
	 *            结束行索引
	 * @return
	 */
	public static List getCellStringValueList(KDTable table, String columName, int startRowIndex, int endRowIndex) {
		Map cellStringValueMap = getCellStringValueMap(table, new String[] { columName }, startRowIndex, endRowIndex);

		return (List) cellStringValueMap.get(columName);
	}

	/**
	 * 取得单元格对应的字符串值列表
	 * 
	 * @param table
	 *            表格
	 * @param columName
	 *            列名
	 * @return
	 */
	public static List getCellStringValueList(KDTable table, String columName) {
		return getCellStringValueList(table, columName, 0, table.getRowCount());
	}

	/**
	 * 取得单元格对应的字符串值
	 * 
	 * @param table
	 *            表格
	 * @param columName
	 *            列名
	 * @param rowIndex
	 *            行索引
	 * @return
	 */
	public static Object getCellStringValue(KDTable table, String columName, int rowIndex) {
		Object value = null;

		List list = getCellStringValueList(table, columName, rowIndex, rowIndex);
		if (FdcCollectionUtil.isNotEmpty(list)) {
			value = list.get(0);
		}

		return value;
	}

	/**
	 * 是否为空
	 * 
	 * @param table
	 * @return
	 */
	public static boolean isEmpty(KDTable table) {
		return (null == table) || (0 == table.getRowCount());
	}

	/**
	 * 是否不为空
	 * 
	 * @param table
	 * @return
	 */
	public static boolean isNotEmpty(KDTable table) {
		return !isEmpty(table);
	}

	/**
	 * 取得行ID列表
	 * 
	 * @param table
	 *            表格
	 * @param columName
	 *            列名
	 * @param rowIndex
	 *            行索引
	 * @return
	 */
	public static List getRowIdList(KDTable table) {
		List idList = new ArrayList();

		if (FDCTableHelper.isEmpty(table)) {
			return idList;
		}

		int rowCount = table.getRowCount();
		IRow firstRow = table.getRow(0);
		Object firstRowObject = firstRow.getUserObject();
		if (firstRowObject instanceof AbstractCoreBaseInfo) {
			IRow row = null;
			CoreBaseInfo rowObject = null;
			BOSUuid id = null;
			String idStr = null;

			for (int i = 0; i < rowCount; i++) {
				row = table.getRow(i);
				rowObject = (CoreBaseInfo) row.getUserObject();
				id = rowObject.getId();
				idStr = id.toString();

				idList.add(idStr);
			}
		}

		return idList;
	}

	// //////////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////////

	/**
	 * 描述：取得每行的单元格值Map
	 * 
	 * @param table
	 *            表格
	 * @return
	 * @author skyiter_wang
	 * @createDate 2014-1-2
	 */
	public static Map getRowValuesMap(KDTable table) {
		Map rowValuesMap = new LinkedHashMap();

		if (FDCTableHelper.isEmpty(table)) {
			return rowValuesMap;
		}

		int startRowIndex = 0;
		int endRowIndex = table.getRowCount() - 1;

		rowValuesMap = getRowValuesMap(table, startRowIndex, endRowIndex);

		return rowValuesMap;
	}

	/**
	 * 描述：取得每行的单元格值Map
	 * 
	 * @param table
	 *            表格
	 * @param startRowIndex
	 *            起始行索引
	 * @param endRowIndex
	 *            结束行索引
	 * @return 每行的单元格值Map；Key：行索引，Value：每行的[单元格下标+单元格值]列表
	 * @author skyiter_wang
	 * @createDate 2014-1-2
	 */
	public static Map getRowValuesMap(KDTable table, int startRowIndex, int endRowIndex) {
		Map rowValuesMap = new LinkedHashMap();

		if (FDCTableHelper.isEmpty(table)) {
			return rowValuesMap;
		}

		int rowCount = table.getRowCount();
		int minRowIndex = (0 > startRowIndex) ? 0 : startRowIndex;
		int maxRowIndex = (rowCount - 1 < endRowIndex) ? (rowCount - 1) : endRowIndex;

		IRow row = null;
		ICell cell = null;
		String cellIndexStr = null;
		Object cellValue = null;
		List cellValueList = null;

		for (int i = minRowIndex; i < maxRowIndex; i++) {
			row = table.getRow(i);

			cellValueList = new ArrayList();
			rowValuesMap.put(i + "", cellValueList);

			for (int j = 0; j > -1; j++) {
				cell = row.getCell(j);

				if (null != cell) {
					cellValue = cell.getValue();
					cellIndexStr = "[" + i + "," + j + "]";
					cellValueList.add(cellIndexStr + ":" + cellValue);
				} else {
					j = -2;
				}
			}

		}

		return rowValuesMap;
	}

	/**
	 * 描述：取得单元格值Map
	 * 
	 * @param table
	 *            表格
	 * @return
	 * @author skyiter_wang
	 * @createDate 2014-1-2
	 */
	public static Map getCellValueMap(KDTable table) {
		Map cellValueMap = new LinkedHashMap();

		if (FDCTableHelper.isEmpty(table)) {
			return cellValueMap;
		}

		int startRowIndex = 0;
		int endRowIndex = table.getRowCount() - 1;

		cellValueMap = getCellValueMap(table, startRowIndex, endRowIndex);

		return cellValueMap;
	}

	/**
	 * 描述：取得单元格值Map
	 * 
	 * @param table
	 *            表格
	 * @param startRowIndex
	 *            起始行索引
	 * @param endRowIndex
	 *            结束行索引
	 * @return
	 * @author skyiter_wang
	 * @createDate 2014-1-2
	 */
	public static Map getCellValueMap(KDTable table, int startRowIndex, int endRowIndex) {
		Map cellValueMap = new LinkedHashMap();

		if (FDCTableHelper.isEmpty(table)) {
			return cellValueMap;
		}

		int rowCount = table.getRowCount();
		int minRowIndex = (0 > startRowIndex) ? 0 : startRowIndex;
		int maxRowIndex = (rowCount - 1 < endRowIndex) ? (rowCount - 1) : endRowIndex;

		IRow row = null;
		ICell cell = null;
		String cellIndexStr = null;
		Object cellValue = null;

		for (int i = minRowIndex; i < maxRowIndex; i++) {
			row = table.getRow(i);

			for (int j = 0; j > -1; j++) {
				cell = row.getCell(j);

				if (null != cell) {
					cellValue = cell.getValue();
					cellIndexStr = "[" + i + "," + j + "]";

					cellValueMap.put(cellIndexStr, cellValue);
				} else {
					j = -2;
				}
			}
		}

		return cellValueMap;
	}

	// //////////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////////

	// //////////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////////

	/**
	 * 描述：取得行用户对象Map
	 * 
	 * @param table
	 *            表格
	 * @return 每行的用户对象Map；Key：行索引，Value：每行的用户对象
	 * @author skyiter_wang
	 * @createDate 2014-1-2
	 */
	public static Map getRowUserObjectMap(KDTable table) {
		Map rowValuesMap = new LinkedHashMap();

		if (FDCTableHelper.isEmpty(table)) {
			return rowValuesMap;
		}

		int startRowIndex = 0;
		int endRowIndex = table.getRowCount() - 1;

		rowValuesMap = getRowUserObjectMap(table, startRowIndex, endRowIndex);

		return rowValuesMap;
	}

	/**
	 * 描述：取得行用户对象Map
	 * 
	 * @param table
	 *            表格
	 * @param startRowIndex
	 *            起始行索引
	 * @param endRowIndex
	 *            结束行索引
	 * @return 每行的用户对象Map；Key：行索引，Value：每行的用户对象
	 * @author skyiter_wang
	 * @createDate 2014-1-2
	 */
	public static Map getRowUserObjectMap(KDTable table, int startRowIndex, int endRowIndex) {
		Map rowValuesMap = new LinkedHashMap();

		if (FDCTableHelper.isEmpty(table)) {
			return rowValuesMap;
		}

		int rowCount = table.getRowCount();
		int minRowIndex = (0 > startRowIndex) ? 0 : startRowIndex;
		int maxRowIndex = (rowCount - 1 < endRowIndex) ? (rowCount - 1) : endRowIndex;

		IRow row = null;
		Object rowUserObject = null;

		for (int i = minRowIndex; i <= maxRowIndex; i++) {
			row = table.getRow(i);

			rowUserObject = row.getUserObject();
			rowValuesMap.put(i + "", rowUserObject);
		}

		return rowValuesMap;
	}

	/**
	 * 描述：取得单元格用户对象Map(以行为单位)
	 * 
	 * @param table
	 *            表格
	 * @return 每行的用户对象Map；Key：行索引，Value：每行的[单元格下标+单元格用户对象]列表
	 * @author skyiter_wang
	 * @createDate 2014-1-2
	 */
	public static Map getCellUserObjectMap4Row(KDTable table) {
		Map rowValuesMap = new LinkedHashMap();

		if (FDCTableHelper.isEmpty(table)) {
			return rowValuesMap;
		}

		int startRowIndex = 0;
		int endRowIndex = table.getRowCount() - 1;

		rowValuesMap = getCellUserObjectMap4Row(table, startRowIndex, endRowIndex);

		return rowValuesMap;
	}

	/**
	 * 描述：取得单元格用户对象Map(以行为单位)
	 * 
	 * @param table
	 *            表格
	 * @param startRowIndex
	 *            起始行索引
	 * @param endRowIndex
	 *            结束行索引
	 * @return 每行的用户对象Map；Key：行索引，Value：每行的[单元格下标+单元格用户对象]列表
	 * @author skyiter_wang
	 * @createDate 2014-1-2
	 */
	public static Map getCellUserObjectMap4Row(KDTable table, int startRowIndex, int endRowIndex) {
		Map rowValuesMap = new LinkedHashMap();

		if (FDCTableHelper.isEmpty(table)) {
			return rowValuesMap;
		}

		int rowCount = table.getRowCount();
		int minRowIndex = (0 > startRowIndex) ? 0 : startRowIndex;
		int maxRowIndex = (rowCount - 1 < endRowIndex) ? (rowCount - 1) : endRowIndex;

		IRow row = null;
		ICell cell = null;
		String cellIndexStr = null;
		Object cellUserObject = null;
		List cellUserObjectList = null;

		for (int i = minRowIndex; i <= maxRowIndex; i++) {
			row = table.getRow(i);

			cellUserObjectList = new ArrayList();
			rowValuesMap.put(i + "", cellUserObjectList);

			for (int j = 0; j > -1; j++) {
				cell = row.getCell(j);

				if (null != cell) {
					cellUserObject = cell.getUserObject();
					cellIndexStr = "[" + i + "," + j + "]";
					cellUserObjectList.add(cellIndexStr + ":" + cellUserObject);
				} else {
					j = -2;
				}
			}

		}

		return rowValuesMap;
	}

	/**
	 * 描述：取得单元格用户对象Map
	 * 
	 * @param table
	 *            表格
	 * @return 每单元格的用户对象Map；Key：[单元格行下标索引,单元格列下标索引]，Value：单元格用户对象
	 * @author skyiter_wang
	 * @createDate 2014-1-2
	 */
	public static Map getCellUserObjectMap(KDTable table) {
		Map cellUserObjectMap = new LinkedHashMap();

		if (FDCTableHelper.isEmpty(table)) {
			return cellUserObjectMap;
		}

		int startRowIndex = 0;
		int endRowIndex = table.getRowCount() - 1;

		cellUserObjectMap = getCellUserObjectMap(table, startRowIndex, endRowIndex);

		return cellUserObjectMap;
	}

	/**
	 * 描述：取得单元格用户对象Map
	 * 
	 * @param table
	 *            表格
	 * @param startRowIndex
	 *            起始行索引
	 * @param endRowIndex
	 *            结束行索引
	 * @return 每单元格的用户对象Map；Key：[单元格行下标索引,单元格列下标索引]，Value：单元格用户对象
	 * @author skyiter_wang
	 * @createDate 2014-1-2
	 */
	public static Map getCellUserObjectMap(KDTable table, int startRowIndex, int endRowIndex) {
		Map cellUserObjectMap = new LinkedHashMap();

		if (FDCTableHelper.isEmpty(table)) {
			return cellUserObjectMap;
		}

		int rowCount = table.getRowCount();
		int minRowIndex = (0 > startRowIndex) ? 0 : startRowIndex;
		int maxRowIndex = (rowCount - 1 < endRowIndex) ? (rowCount - 1) : endRowIndex;

		IRow row = null;
		ICell cell = null;
		String cellIndexStr = null;
		Object cellUserObject = null;

		for (int i = minRowIndex; i < maxRowIndex; i++) {
			row = table.getRow(i);

			for (int j = 0; j > -1; j++) {
				cell = row.getCell(j);

				if (null != cell) {
					cellUserObject = cell.getUserObject();
					cellIndexStr = "[" + i + "," + j + "]";

					cellUserObjectMap.put(cellIndexStr, cellUserObject);
				} else {
					j = -2;
				}
			}
		}

		return cellUserObjectMap;
	}

	// //////////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////////

	/**
	 * 格式化表格数字列
	 * <p>
	 * 1、如果参数strDataFormat不为空，则参数qtyPre不起作用<br>
	 * 2、末尾会自动去掉0<br>
	 * 
	 * @deprecated 推荐使用FDCTableHelper.formatTableNumber2(KDTable table, String columnName, int scale)， 需要传入精度
	 * @param table
	 *            表格
	 * @param columnName
	 *            列名
	 * @param strDataFormat
	 *            数据格式
	 * @param scale
	 *            精度
	 */
	public static void formatTableNumber2(KDTable table, String columnName, String strDataFormat, int scale) {
		formatTableNumber2(table, columnName, strDataFormat, scale, false);
	}

	/**
	 * 格式化表格数字列
	 * <p>
	 * 1、如果参数strDataFormat不为空，则参数qtyPre不起作用<br>
	 * 2、末尾会自动去掉0<br>
	 * 
	 * @deprecated 推荐使用FDCTableHelper.formatTableNumber2(KDTable table, String columnName, int scale)， 需要传入精度
	 * @param table
	 *            表格
	 * @param columnName
	 *            列名
	 * @param strDataFormat
	 *            数据格式
	 * @param scale
	 *            精度
	 * @param isShowZero
	 *            是否显示末尾0
	 */
	public static void formatTableNumber2(KDTable table, String columnName, String strDataFormat, int scale,
			boolean isShowZero) {
		if (table.getColumn(columnName) != null) {
			// String strDataFormat = "#,###.##;-#,###.##";
			if (strDataFormat == null) {
				// 取得小数格式，末尾会自动去掉0
				strDataFormat = FDCRenderHelper.getDecimalFormat2(scale);
			}

			StyleAttributes sa = table.getColumn(columnName).getStyleAttributes();
			sa.setNumberFormat(strDataFormat);
			sa.setHorizontalAlign(HorizontalAlignment.RIGHT); // 右对齐

			// 0不显示
			if (!isShowZero) {
				FDCTableHelper.setRender(table, columnName, FdcZeroBaseRender.getInstance());
			}
		}
	}

	/**
	 * 格式化表格数字列
	 * <p>
	 * 1、末尾会自动去掉0
	 * 
	 * @param table
	 *            表格
	 * @param columnName
	 *            列名
	 * @param scale
	 *            小数位数
	 */
	public static void formatTableNumber2(KDTable table, String columnName, int scale) {
		boolean isShowZero = false;
		if (table != null && !StringUtils.isEmpty(columnName)) {
			IColumn column = table.getColumn(columnName);
			if (column != null) {
				IBasicRender basicRender = column.getRenderer();
				if (basicRender != null && basicRender instanceof FdcZeroBaseRender) {
					isShowZero = ((FdcZeroBaseRender) basicRender).isShowZero();
				}
			}
		}
		formatTableNumber2(table, columnName, scale, isShowZero);
	}

	/**
	 * 格式化表格数字列
	 * <p>
	 * 1、末尾会自动去掉0
	 * 
	 * @param table
	 *            表格
	 * @param columnName
	 *            列名
	 * @param scale
	 *            小数位数
	 * @param isShowZero
	 *            是否显示末尾0
	 */
	public static void formatTableNumber2(KDTable table, String columnName, int scale, boolean isShowZero) {

		// 取得小数格式，末尾会自动去掉0
		String dataFormat = FDCRenderHelper.getDecimalFormat2(scale);

		FDCTableHelper.formatTableNumber2(table, columnName, dataFormat, scale, isShowZero);

		// /////////////////////////////////////////////////////////////////
		// 初始化userProperties，防止报错
		table.putUserProperty("INIT_USERPROPERTIES", Boolean.TRUE);
		Map scaleMap = (Map) table.getUserProperty("TABLE_SCALE_MAP");
		if (null == scaleMap) {
			scaleMap = new HashMap();
			table.putUserProperty("TABLE_SCALE_MAP", scaleMap);
		}

		Map columnMap = new HashMap();
		columnMap.put("SCALE", new Integer(scale));
		columnMap.put("DISSCALE", new Integer(scale));
		scaleMap.put(columnName, columnMap);
		// /////////////////////////////////////////////////////////////////
	}

	/**
	 * 格式化表格数字列
	 * <p>
	 * 1、末尾会自动去掉0
	 * 
	 * @param table
	 *            表格
	 * @param columnNames
	 *            列名数组
	 * @param scale
	 *            小数位数
	 */
	public static void formatTableNumber2(KDTable table, String[] columnNames, int scale) {
		for (int i = 0, length = columnNames.length; i < length; i++) {
			if (table.getColumn(columnNames[i]) != null) {
				formatTableNumber2(table, columnNames[i], scale);
			}
		}
	}

	/**
	 * 格式化表格数字列
	 * <p>
	 * 1、末尾会自动去掉0
	 * 
	 * @param table
	 *            表格
	 * @param columnNameList
	 *            列名List
	 * @param scale
	 *            小数位数
	 */
	public static void formatTableNumber2(KDTable table, List columnNameList, int scale) {
		if (FdcCollectionUtil.isNotEmpty(columnNameList)) {
			// 清除掉集合中的重复值和Null值
			FdcCollectionUtil.clearDuplicateAndNull(columnNameList);

			String[] columnNames = (String[]) columnNameList.toArray(new String[0]);
			formatTableNumber2(table, columnNames, scale);
		}
	}

	// ///////////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////////

	// 设置渲染器
	public static void setRender(KDTable table, String columnName, FdcBaseRender render) {
		IColumn column = table.getColumn(columnName);
		if (column != null) {
			column.setRenderer(render);
		}
	}

	// 设置渲染器
	public static void setRender(KDTable table, String[] columnNames, FdcBaseRender[] renders) {
		for (int i = 0, count = columnNames.length; i < count; i++) {
			setRender(table, columnNames[i], renders[i]);
		}
	}

	// 设置渲染器
	public static void setRender(KDTable table, List columnNameList, FdcBaseRender[] render) {
		if (FdcCollectionUtil.isNotEmpty(columnNameList)) {
			String[] columnNames = (String[]) columnNameList.toArray(new String[0]);
			setRender(table, columnNames, render);
		}
	}

	// 设置渲染器
	public static void setRender(KDTable table, String[] columnNames, FdcBaseRender render) {
		for (int i = 0, count = columnNames.length; i < count; i++) {
			setRender(table, columnNames[i], render);
		}
	}

	// 设置渲染器
	public static void setRender(KDTable table, List columnNameList, FdcBaseRender render) {
		if (FdcCollectionUtil.isNotEmpty(columnNameList)) {
			String[] columnNames = (String[]) columnNameList.toArray(new String[0]);
			setRender(table, columnNames, render);
		}
	}

	// ///////////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////////

	/**
	 * 取得所有列名
	 * 
	 * @param table
	 * @return
	 */
	public static String[] getColumnKeys(KDTable table) {
		String[] columNames = null;

		int columnCount = table.getColumnCount();
		columNames = new String[columnCount];

		for (int i = 0; i < columnCount; i++) {
			columNames[i] = table.getColumnKey(i);
		}

		return columNames;
	}

	/**
	 * 根据是否必录，取得所有列名
	 * 
	 * @param table
	 * @param isRequired
	 * @return
	 */
	public static String[] getColumnKeysOfRequired(KDTable table, boolean isRequired) {
		String[] columnKeys = null;

		int columnCount = table.getColumnCount();
		List columnKeyList = new ArrayList();
		IColumn column = null;

		for (int i = 0; i < columnCount; i++) {
			column = table.getColumn(i);
			if (column.isRequired() == isRequired) {
				columnKeyList.add(column.getKey());
			}
		}

		columnKeys = (String[]) columnKeyList.toArray(new String[0]);

		return columnKeys;
	}

	/**
	 * 根据是否隐藏，取得所有列名
	 * 
	 * @param table
	 * @param isHided
	 * @return
	 */
	public static String[] getColumnKeysOfHided(KDTable table, boolean isHided) {
		String[] columnKeys = null;

		int columnCount = table.getColumnCount();
		List columnKeyList = new ArrayList();
		IColumn column = null;

		for (int i = 0; i < columnCount; i++) {
			column = table.getColumn(i);
			if (column.getStyleAttributes().isHided() == isHided) {
				columnKeyList.add(column.getKey());
			}
		}

		columnKeys = (String[]) columnKeyList.toArray(new String[0]);

		return columnKeys;
	}

	/**
	 * 根据是否必录、是否隐藏，取得所有列名
	 * 
	 * @param table
	 * @param isRequired
	 * @param isHided
	 * @return
	 */
	public static String[] getColumnKeysOfRequiredHided(KDTable table, boolean isRequired, boolean isHided) {
		String[] columnKeys = null;

		int columnCount = table.getColumnCount();
		List columnKeyList = new ArrayList();
		IColumn column = null;

		for (int i = 0; i < columnCount; i++) {
			column = table.getColumn(i);
			if (column.isRequired() == isRequired && column.getStyleAttributes().isHided() == isHided) {
				columnKeyList.add(column.getKey());
			}
		}

		columnKeys = (String[]) columnKeyList.toArray(new String[0]);

		return columnKeys;
	}

	/**
	 * 根据是否必录、是否隐藏、是否锁定，取得所有列名
	 * 
	 * @param table
	 *            表格
	 * @param isRequired
	 *            是否必录
	 * @param isHided
	 *            是否隐藏
	 * @param isLocked
	 *            是否锁定
	 * @return
	 */
	public static String[] getColumnKeysOfRequiredHidedLocked(KDTable table, boolean isRequired, boolean isHided,
			boolean isLocked) {
		String[] columnKeys = null;

		int columnCount = table.getColumnCount();
		List columnKeyList = new ArrayList();
		IColumn column = null;

		for (int i = 0; i < columnCount; i++) {
			column = table.getColumn(i);
			if (column.isRequired() == isRequired && column.getStyleAttributes().isHided() == isHided
					&& column.getStyleAttributes().isLocked() == isLocked) {
				columnKeyList.add(column.getKey());
			}
		}

		columnKeys = (String[]) columnKeyList.toArray(new String[0]);

		return columnKeys;
	}

	// ///////////////////////////////////////////////////////////////////////////////

	/**
	 * 取得所有列名"是否必录"属性映射
	 * 
	 * @param table
	 * @return
	 */
	public static Map getColumnKeyOfRequiredMap(KDTable table) {
		Map map = new HashMap();

		int columnCount = table.getColumnCount();
		IColumn column = null;
		String key = null;

		for (int i = 0; i < columnCount; i++) {
			column = table.getColumn(i);

			key = column.getKey();
			map.put(key, Boolean.valueOf(column.isRequired()));
		}

		return map;
	}

	/**
	 * 取得所有列名"是否隐藏"属性映射
	 * 
	 * @param table
	 * @return
	 */
	public static Map getColumnKeyHidedMap(KDTable table) {
		Map map = new HashMap();

		int columnCount = table.getColumnCount();
		IColumn column = null;
		String key = null;

		for (int i = 0; i < columnCount; i++) {
			column = table.getColumn(i);

			key = column.getKey();
			map.put(key, Boolean.valueOf(column.getStyleAttributes().isHided()));
		}

		return map;
	}

	/**
	 * 取得所有列名"是否必录"、"是否隐藏"属性映射
	 * 
	 * @param table
	 * @return
	 */
	public static Map getColumnKeyRequiredHidedMap(KDTable table) {
		Map map = new HashMap();

		int columnCount = table.getColumnCount();
		IColumn column = null;
		String key = null;
		String str = null;

		for (int i = 0; i < columnCount; i++) {
			column = table.getColumn(i);

			key = column.getKey();
			str = "[isRequired：" + column.isRequired() + ", isHided:" + column.getStyleAttributes().isHided() + "]";
			map.put(key, str);
		}

		return map;
	}

	// ///////////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////////

	/**
	 * 取得列名映射
	 * <p>
	 * 1、其中key存放列名<br>
	 * 2、其中value存放列标签<br>
	 * 
	 * 
	 * @param table
	 *            金蝶表格控件
	 */
	public static Map getColumnKeyNameMap(KDTable table) {
		Map columnKeyNameMap = new LinkedHashMap();

		String[] columnKeys = FDCTableHelper.getColumnKeys(table);
		for (int i = 0; i < columnKeys.length; i++) {
			String key = columnKeys[i];
			String value = FDCTableHelper.getHeadName(table, key);
			columnKeyNameMap.put(key, value);
		}

		return columnKeyNameMap;
	}

	/**
	 * 取得表头行名称
	 * 
	 * @param table
	 *            表格
	 * @param key
	 *            列键
	 * @return
	 */
	public static String getHeadName(KDTable table, String key) {
		String headName = "";

		IRow headRow = null;
		ICell headCell = null;
		for (int i = 0, rowCount = table.getHeadRowCount(); i < rowCount; i++) {
			if (!"".equals(headName)) {
				headName += "-";
			}

			headRow = table.getHeadRow(i);
			headCell = headRow.getCell(key);
			headName += (String) headCell.getValue();
		}

		return headName;
	}

	/**
	 * 调试打印列名映射
	 * 
	 * @param table
	 *            金蝶表格控件
	 */
	public static void debugPrintColumnKeyNameMap(KDTable table) {
		Map columnKeyNameMap = getColumnKeyNameMap(table);

		System.out.println("-----------------------------------------------");
		MapUtils.debugPrint(System.out, "columnKeyNameMap", columnKeyNameMap);
		System.out.println("-----------------------------------------------");
	}

	/**
	 * 取得列键映射
	 * <p>
	 * 1、其中key存放列键<br>
	 * 2、其中value存放列索引<br>
	 * 
	 * 
	 * @param table
	 *            金蝶表格控件
	 */
	public static Map getColumnKeyIndexMap(KDTable table) {
		Map columnKeyIndexMap = new LinkedHashMap();

		int columnCount = table.getColumnCount();
		for (int i = 0; i < columnCount; i++) {
			String key = table.getColumnKey(i);
			Integer value = Integer.valueOf(i);
			columnKeyIndexMap.put(key, value);
		}

		return columnKeyIndexMap;
	}

	/**
	 * 调试打印列键映射
	 * 
	 * @param table
	 *            金蝶表格控件
	 */
	public static void debugPrintColumnKeyIndexMap(KDTable table) {
		Map columnKeyIndexMap = getColumnKeyIndexMap(table);

		System.out.println("-----------------------------------------------");
		MapUtils.debugPrint(System.out, "columnKeyIndexMap", columnKeyIndexMap);
		System.out.println("-----------------------------------------------");
	}

	// ////////////////////////////////////////////////////////

	/**
	 * 调试打印取得所有列名"是否必录"属性映射
	 * 
	 * @param table
	 *            金蝶表格控件
	 * @param isRequired
	 *            是否必录
	 */
	public static void debugPrintColumnKeyOfRequiredMap(KDTable table) {
		Map columnKeyRequiredMap = getColumnKeyOfRequiredMap(table);

		System.out.println("-----------------------------------------------");
		MapUtils.debugPrint(System.out, "columnKeyRequiredMap", columnKeyRequiredMap);
		System.out.println("isRequired=true : " + Arrays.asList(getColumnKeysOfRequired(table, true)));
		System.out.println("isRequired=false : " + Arrays.asList(getColumnKeysOfRequired(table, false)));
		System.out.println("-----------------------------------------------");
	}

	/**
	 * 调试打印取得所有列名"是否隐藏"属性映射
	 * 
	 * @param table
	 *            金蝶表格控件
	 * @param isHided
	 *            是否隐藏
	 */
	public static void debugPrintColumnKeyHidedMap(KDTable table) {
		Map columnKeyHidedMap = getColumnKeyHidedMap(table);

		System.out.println("-----------------------------------------------");
		MapUtils.debugPrint(System.out, "columnKeyHidedMap", columnKeyHidedMap);
		System.out.println("isHided=true : " + Arrays.asList(getColumnKeysOfHided(table, true)));
		System.out.println("isHided=false : " + Arrays.asList(getColumnKeysOfHided(table, false)));
		System.out.println("-----------------------------------------------");
	}

	/**
	 * 调试打印取得所有列名"是否必录"、"是否隐藏"属性映射
	 * 
	 * @param table
	 *            金蝶表格控件
	 */
	public static void debugPrintColumnKeyRequiredHidedMap(KDTable table) {
		Map columnKeyRequiredHidedMap = getColumnKeyRequiredHidedMap(table);

		System.out.println("-----------------------------------------------");
		MapUtils.debugPrint(System.out, "columnKeyRequiredHidedMap", columnKeyRequiredHidedMap);
		System.out.println("isRequired=true : " + Arrays.asList(getColumnKeysOfRequired(table, true)));
		System.out.println("isRequired=false : " + Arrays.asList(getColumnKeysOfRequired(table, false)));
		System.out.println("isHided=true : " + Arrays.asList(getColumnKeysOfHided(table, true)));
		System.out.println("isHided=false : " + Arrays.asList(getColumnKeysOfHided(table, false)));
		System.out.println("-----------------------------------------------");
	}

	// ////////////////////////////////////////////////////////

	/**
	 * 描述：调试打印表格每行的单元格值映射
	 * 
	 * @param table
	 *            金蝶表格控件
	 * @author rd_skyiter_wang
	 * @createDate 2014-7-30
	 */
	public static void debugPrintRowValuesMap(KDTable table) {
		// 取得每行的单元格值Map
		Map rowValuesMap = FDCTableHelper.getRowValuesMap(table);

		System.out.println("-----------------------------------------------");
		MapUtils.debugPrint(System.out, "rowValuesMap", rowValuesMap);
		System.out.println("-----------------------------------------------");
	}

	/**
	 * 描述：调试打印表格指定行列的单元格值映射
	 * 
	 * @param table
	 *            金蝶表格控件
	 * @param startRowIndex
	 *            起始行索引
	 * @param endRowIndex
	 *            结束行索引
	 * @author rd_skyiter_wang
	 * @createDate 2014-8-12
	 */
	public static void debugPrintRowValuesMap(KDTable table, int startRowIndex, int endRowIndex) {
		// 取得每行的单元格值Map
		Map rowValuesMap = FDCTableHelper.getRowValuesMap(table, startRowIndex, endRowIndex);

		System.out.println("-----------------------------------------------");
		MapUtils.debugPrint(System.out, "rowValuesMap", rowValuesMap);
		System.out.println("-----------------------------------------------");
	}

	/**
	 * 描述：调试打印表格指定行的单元格值映射
	 * 
	 * @param table
	 *            金蝶表格控件
	 * @param row
	 *            行
	 * @param endRowIndex
	 *            结束行索引
	 * @author rd_skyiter_wang
	 * @createDate 2014-8-12
	 */
	public static void debugPrintRowValuesMap(KDTable table, IRow row) {
		int startRowIndex = row.getRowIndex();
		int endRowIndex = row.getRowIndex();

		// 调试打印表格指定行列的单元格值映射
		FDCTableHelper.debugPrintRowValuesMap(table, startRowIndex, endRowIndex);
	}

	// ////////////////////////////////////////////////////////

	// ////////////////////////////////////////////////////////

	/**
	 * 描述：调试打印表格每行的单元格用户对象映射
	 * 
	 * @param table
	 *            金蝶表格控件
	 * @author rd_skyiter_wang
	 * @createDate 2014-7-30
	 */
	public static void debugPrintCellUserObjectMap4Row(KDTable table) {
		// 取得每行的单元格用户对象Map
		Map cellUserObjectMap4Row = FDCTableHelper.getCellUserObjectMap4Row(table);

		System.out.println("-----------------------------------------------");
		MapUtils.debugPrint(System.out, "cellUserObjectMap4Row", cellUserObjectMap4Row);
		System.out.println("-----------------------------------------------");
	}

	/**
	 * 描述：调试打印表格指定行列的单元格用户对象映射
	 * 
	 * @param table
	 *            金蝶表格控件
	 * @param startRowIndex
	 *            起始行索引
	 * @param endRowIndex
	 *            结束行索引
	 * @author rd_skyiter_wang
	 * @createDate 2014-8-12
	 */
	public static void debugPrintCellUserObjectMap4Row(KDTable table, int startRowIndex, int endRowIndex) {
		// 取得每行的单元格用户对象Map
		Map cellUserObjectMap4Row = FDCTableHelper.getCellUserObjectMap4Row(table, startRowIndex, endRowIndex);

		System.out.println("-----------------------------------------------");
		MapUtils.debugPrint(System.out, "cellUserObjectMap4Row", cellUserObjectMap4Row);
		System.out.println("-----------------------------------------------");
	}

	/**
	 * 描述：调试打印表格指定行的单元格用户对象映射
	 * 
	 * @param table
	 *            金蝶表格控件
	 * @param row
	 *            行
	 * @param endRowIndex
	 *            结束行索引
	 * @author rd_skyiter_wang
	 * @createDate 2014-8-12
	 */
	public static void debugPrintCellUserObjectMap4Row(KDTable table, IRow row) {
		int startRowIndex = row.getRowIndex();
		int endRowIndex = row.getRowIndex();

		// 调试打印表格指定行列的单元格用户对象映射
		FDCTableHelper.debugPrintCellUserObjectMap4Row(table, startRowIndex, endRowIndex);
	}

	// ////////////////////////////////////////////////////////

	// ////////////////////////////////////////////////////////

	/**
	 * 描述：调试打印表格每行的行用户对象映射
	 * 
	 * @param table
	 *            金蝶表格控件
	 * @author rd_skyiter_wang
	 * @createDate 2014-7-30
	 */
	public static void debugPrintRowUserObjectMap(KDTable table) {
		// 取得每行的用户对象Map
		Map rowUserObjectMap = FDCTableHelper.getRowUserObjectMap(table);

		System.out.println("-----------------------------------------------");
		MapUtils.debugPrint(System.out, "rowUserObjectMap", rowUserObjectMap);
		System.out.println("-----------------------------------------------");
	}

	/**
	 * 描述：调试打印表格指定行列的行用户对象映射
	 * 
	 * @param table
	 *            金蝶表格控件
	 * @param startRowIndex
	 *            起始行索引
	 * @param endRowIndex
	 *            结束行索引
	 * @author rd_skyiter_wang
	 * @createDate 2014-8-12
	 */
	public static void debugPrintRowUserObjectMap(KDTable table, int startRowIndex, int endRowIndex) {
		// 取得每行的用户对象Map
		Map rowUserObjectMap = FDCTableHelper.getRowUserObjectMap(table, startRowIndex, endRowIndex);

		System.out.println("-----------------------------------------------");
		MapUtils.debugPrint(System.out, "rowUserObjectMap", rowUserObjectMap);
		System.out.println("-----------------------------------------------");
	}

	/**
	 * 描述：调试打印表格指定行的行用户对象映射
	 * 
	 * @param table
	 *            金蝶表格控件
	 * @param row
	 *            行
	 * @param endRowIndex
	 *            结束行索引
	 * @author rd_skyiter_wang
	 * @createDate 2014-8-12
	 */
	public static void debugPrintRowUserObjectMap(KDTable table, IRow row) {
		int startRowIndex = row.getRowIndex();
		int endRowIndex = row.getRowIndex();

		// 调试打印表格指定行列的行用户对象映射
		FDCTableHelper.debugPrintRowUserObjectMap(table, startRowIndex, endRowIndex);
	}

	// ////////////////////////////////////////////////////////

	/**
	 * 描述：调试打印表格
	 * 
	 * @param table
	 *            金蝶表格控件
	 * @author rd_skyiter_wang
	 * @createDate 2014-7-30
	 */
	public static void debugPrint(KDTable table) {
		debugPrint(table, true);
	}

	/**
	 * 描述：调试打印表格
	 * 
	 * @param table
	 *            金蝶表格控件
	 * @param isPrintRowValuesMap
	 *            是否打印每行的单元格值映射
	 * @author rd_skyiter_wang
	 * @createDate 2014-7-30
	 */
	public static void debugPrint(KDTable table, boolean isPrintRowValuesMap) {
		// 调试打印列键映射
		debugPrintColumnKeyIndexMap(table);

		// 调试打印列名映射
		debugPrintColumnKeyNameMap(table);

		// 调试打印取得所有列名"是否必录"、"是否隐藏"属性映射
		debugPrintColumnKeyRequiredHidedMap(table);

		if (isPrintRowValuesMap) {
			// 打印表格每行的单元格值映射
			debugPrintRowValuesMap(table);
		}
	}

	// ///////////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////////

	/**
	 * 格式化表格列
	 * 
	 * @param table
	 *            表格
	 * @param columnName
	 *            列名
	 * @param format
	 *            格式
	 */
	public static void formatTable(KDTable table, String columnName, String format) {
		table.getColumn(columnName).getStyleAttributes().setNumberFormat(format);
	}

	/**
	 * 格式化日期
	 * 
	 * @param table
	 *            表格
	 * @param columnName
	 *            列名
	 */
	public static void formatTableDate(KDTable table, String columnName) {
		if (table.getColumn(columnName) != null) {
			formatTable(table, columnName, FDCClientHelper.DATE_FORMAT);
		}
	}

	/**
	 * 格式化日期
	 * 
	 * @param table
	 *            表格
	 * @param columnNames
	 *            列名数组
	 */
	public static void formatTableDate(KDTable table, String[] columnNames) {
		for (int i = 0, length = columnNames.length; i < length; i++) {
			formatTableDate(table, columnNames[i]);
		}
	}

	/**
	 * 格式化日期
	 * 
	 * @param table
	 *            表格
	 * @param columnNameList
	 *            列名List
	 */
	public static void formatTableDate(KDTable table, List columnNameList) {
		if (FdcCollectionUtil.isNotEmpty(columnNameList)) {
			// 清除掉集合中的重复值和Null值
			FdcCollectionUtil.clearDuplicateAndNull(columnNameList);

			String[] columnNames = (String[]) columnNameList.toArray(new String[0]);
			formatTableDate(table, columnNames);
		}
	}

	/**
	 * 格式化日期时间
	 * 
	 * @param table
	 *            表格
	 * @param columnName
	 *            列名
	 */
	public static void formatTableDateTime(KDTable table, String columnName) {
		formatTable(table, columnName, FDCClientHelper.TIME_FORMAT);
	}

	// ///////////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////////

	/**
	 * 选择行
	 * 
	 * @param table
	 * @param rowIndex
	 */
	public static void selectRow(KDTable table, int rowIndex) {
		int columnCount = table.getColumnCount();

		// 恢复选中原行
		table.getSelectManager().select(rowIndex, 0, rowIndex, columnCount);
		// 将指定行滚动至可视区域
		table.scrollToVisible(rowIndex, 0);
	}

	// ///////////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////////

	/**
	 * 取得其它行单元格映射
	 * 
	 * @param table
	 *            表格
	 * @param columNames
	 *            列名数组
	 * @param startRowIndex
	 *            起始行索引
	 * @param endRowIndex
	 *            结束行索引
	 * @param row
	 *            行
	 * @return
	 */
	public static Map getOtherCellMap(KDTable table, String[] columNames, int startRowIndex, int endRowIndex, IRow row) {
		Map cellMap = new LinkedHashMap();

		int rowCount = table.getRowCount();
		if (startRowIndex <= endRowIndex && startRowIndex < rowCount) {
			int mixRowIndex = (0 > startRowIndex) ? 0 : startRowIndex;
			int maxRowIndex = (rowCount - 1 > endRowIndex) ? (rowCount - 1) : endRowIndex;
			int rowIndex = row.getRowIndex();
			int length = columNames.length;

			String columName = null;
			ICell cell = null;
			List cellList = null;

			for (int i = 0; i < length; i++) {
				columName = columNames[i];
				cellList = (List) cellMap.get(columName);
				if (null == cellList) {
					cellList = new ArrayList();
					cellMap.put(columName, cellList);
				}

				for (int j = mixRowIndex; j < maxRowIndex; j++) {
					if (j == rowIndex) {
						continue;
					}

					cell = table.getCell(j, columName);
					if (null != cell) {
						cellList.add(cell);
					} else {
						break;
					}
				}

			}
		}

		return cellMap;
	}

	/**
	 * 取得其它行单元格映射
	 * 
	 * @param table
	 *            表格
	 * @param columNames
	 *            列名数组
	 * @param row
	 *            行
	 * @return
	 */
	public static Map getOtherCellMap(KDTable table, String[] columNames, IRow row) {
		return getOtherCellMap(table, columNames, 0, table.getRowCount() - 1, row);
	}

	/**
	 * 取得其它行单元格列表
	 * 
	 * @param table
	 *            表格
	 * @param columName
	 *            列名
	 * @param startRowIndex
	 *            起始行索引
	 * @param endRowIndex
	 *            结束行索引
	 * @param row
	 *            行
	 * @return
	 */
	public static List getOtherCellList(KDTable table, String columName, int startRowIndex, int endRowIndex, IRow row) {
		Map cellMap = getOtherCellMap(table, new String[] { columName }, startRowIndex, endRowIndex, row);

		return (List) cellMap.get(columName);
	}

	/**
	 * 取得其它行单元格列表
	 * 
	 * @param table
	 *            表格
	 * @param columName
	 *            列名
	 * @param row
	 *            行
	 * @return
	 */
	public static List getOtherCellList(KDTable table, String columName, IRow row) {
		return getOtherCellList(table, columName, 0, table.getRowCount(), row);
	}

	/**
	 * 取得其它行单元格值映射
	 * 
	 * @param table
	 *            表格
	 * @param columNames
	 *            列名数组
	 * @param startRowIndex
	 *            起始行索引
	 * @param endRowIndex
	 *            结束行索引
	 * @param row
	 *            行
	 * @return
	 */
	public static Map getOtherCellValueMap(KDTable table, String[] columNames, int startRowIndex, int endRowIndex,
			IRow row) {
		Map cellValueMap = new LinkedHashMap();

		int rowCount = table.getRowCount();
		if (startRowIndex <= endRowIndex && startRowIndex < rowCount) {
			int mixRowIndex = (0 > startRowIndex) ? 0 : startRowIndex;
			int maxRowIndex = (rowCount - 1 > endRowIndex) ? (rowCount - 1) : endRowIndex;
			int rowIndex = row.getRowIndex();
			int length = columNames.length;

			String columName = null;
			ICell cell = null;
			Object cellValue = null;
			List cellValueList = null;

			for (int i = 0; i < length; i++) {
				columName = columNames[i];
				cellValueList = (List) cellValueMap.get(columName);
				if (null == cellValueList) {
					cellValueList = new ArrayList();
					cellValueMap.put(columName, cellValueList);
				}

				for (int j = mixRowIndex; j < maxRowIndex; j++) {
					if (j == rowIndex) {
						continue;
					}

					cell = table.getCell(j, columName);
					if (null != cell) {
						cellValue = cell.getValue();
						cellValueList.add(cellValue);
					} else {
						break;
					}
				}

			}
		}

		return cellValueMap;
	}

	/**
	 * 取得其它行单元格值映射
	 * 
	 * @param table
	 *            表格
	 * @param columNames
	 *            列名数组
	 * @param row
	 *            行
	 * @return
	 */
	public static Map getOtherCellValueMap(KDTable table, String[] columNames, IRow row) {
		return getOtherCellValueMap(table, columNames, 0, table.getRowCount() - 1, row);
	}

	/**
	 * 取得其它行单元格值列表
	 * 
	 * @param table
	 *            表格
	 * @param columName
	 *            列名
	 * @param startRowIndex
	 *            起始行索引
	 * @param endRowIndex
	 *            结束行索引
	 * @param row
	 *            行
	 * @return
	 */
	public static List getOtherCellValueList(KDTable table, String columName, int startRowIndex, int endRowIndex,
			IRow row) {
		Map cellValueMap = getOtherCellValueMap(table, new String[] { columName }, startRowIndex, endRowIndex, row);

		return (List) cellValueMap.get(columName);
	}

	/**
	 * 取得其它行单元格值列表
	 * 
	 * @param table
	 *            表格
	 * @param columName
	 *            列名
	 * @param row
	 *            行
	 * @return
	 */
	public static List getOtherCellValueList(KDTable table, String columName, IRow row) {
		return getOtherCellValueList(table, columName, 0, table.getRowCount(), row);
	}

	/**
	 * 是否包含单元格
	 * 
	 * @param table
	 * @param columName
	 * @param cell
	 * @param startRowIndex
	 * @param endRowIndex
	 * @return
	 */
	public static boolean containsCell(KDTable table, String columName, ICell cell, int startRowIndex, int endRowIndex) {
		boolean flag = false;

		List cellList = getCellList(table, columName, startRowIndex, endRowIndex);
		if (FdcCollectionUtil.isNotEmpty(cellList) && cellList.contains(cell)) {
			flag = true;
		}

		return flag;
	}

	/**
	 * 是否包含单元格
	 * 
	 * @param table
	 * @param columName
	 * @param cell
	 * @return
	 */
	public static boolean containsCell(KDTable table, String columName, ICell cell) {
		return containsCell(table, columName, cell, 0, table.getRowCount());
	}

	/**
	 * 是否包含单元格值
	 * 
	 * @param table
	 * @param columName
	 * @param value
	 * @param startRowIndex
	 * @param endRowIndex
	 * @return
	 */
	public static boolean containsCellValue(KDTable table, String columName, Object value, int startRowIndex,
			int endRowIndex) {
		boolean flag = false;

		List cellValueList = getCellValueList(table, columName, startRowIndex, endRowIndex);
		if (FdcCollectionUtil.isNotEmpty(cellValueList) && cellValueList.contains(value)) {
			flag = true;
		}

		return flag;
	}

	/**
	 * 是否包含单元格值
	 * 
	 * @param table
	 * @param columName
	 * @param value
	 * @return
	 */
	public static boolean containsCellValue(KDTable table, String columName, Object value) {
		return containsCellValue(table, columName, value, 0, table.getRowCount());
	}

	/**
	 * 取得选择的单元格值
	 * 
	 * @param comp
	 *            容器
	 * @param table
	 *            表格
	 * @param key
	 *            列键
	 * @return
	 */
	public static Object getSelectCellValue(Component comp, KDTable table, String key) {
		Object value = null;

		FDCClientVerifyHelper.checkSelected(comp, table);
		IRow row = KDTableUtil.getSelectedRow(table);
		value = getSelectCellValue(comp, row, key);

		return value;
	}

	/**
	 * 取得选择的单元格值
	 * 
	 * @param comp
	 *            容器
	 * @param row
	 *            行
	 * @param key
	 *            列键
	 * @return
	 */
	public static Object getSelectCellValue(Component comp, IRow row, String key) {
		Object value = null;

		ICell cell = row.getCell(key);
		if (null != cell) {
			value = cell.getValue();
		}

		return value;
	}

	/**
	 * 取得唯一选择的单元格值
	 * 
	 * @param comp
	 *            容器
	 * @param table
	 *            表格
	 * @return
	 */
	public static Object getOnlySelectCellValue(Component comp, KDTable table) {
		Object value = null;

		FDCClientVerifyHelper.checkSelected(comp, table);

		List blocks = table.getSelectManager().getBlocks();
		int left = -1;
		int top = -1;
		int bottom = -1;
		if (blocks.size() > 1) {
			MsgBox.showWarning(comp, "只能选择一行");
			SysUtil.abort();
		}

		KDTSelectBlock block = (KDTSelectBlock) blocks.get(0);
		top = block.getTop();
		bottom = block.getBottom();

		if (top != bottom) {
			MsgBox.showWarning(comp, "只能选择一行");
			SysUtil.abort();
		}
		if (block.getLeft() != block.getRight()) {
			MsgBox.showWarning(comp, "只能选择一列");
			SysUtil.abort();
		}
		if (-1 == left) {
			left = block.getLeft();
		} else if (block.getLeft() == left) {
			MsgBox.showWarning(comp, "只能选择一列");
			SysUtil.abort();
		}

		ICell cell = table.getRow(top).getCell(left);
		value = cell.getValue();

		return value;
	}

	/**
	 * 设置列值
	 * 
	 * @param table
	 *            表格
	 * @param key
	 *            所在列的key
	 * @param value
	 *            值
	 * @return
	 */
	public static boolean setColumnValue(KDTable table, String key, Object value) {
		boolean flag = false;

		IRow row = null;
		ICell cell = null;
		for (int i = 0, rowCount = table.getRowCount(); i < rowCount; i++) {
			row = table.getRow(i);
			cell = row.getCell(key);
			if (null != cell) {
				cell.setValue(value);
			}
		}

		return flag;
	}

	/**
	 * 设置单元格值
	 * 
	 * @param row
	 *            行
	 * @param key
	 *            单元格所在列的key
	 * @param value
	 *            值
	 * @return
	 */
	public static boolean setCellValue(IRow row, String key, Object value) {
		boolean flag = false;

		ICell cell = row.getCell(key);
		if (null != cell) {
			cell.setValue(value);
		}

		return flag;
	}
	
	// ///////////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////////

	/**
	 * 描述：是否显示0
	 * 
	 * @param kdtEntries
	 *            表格
	 * @param key
	 *            列名
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-12-2
	 */
	public static boolean isShowZero(KDTable kdtEntries, String key) {
		boolean isShowZero = false;

		IColumn column = kdtEntries.getColumn(key);
		isShowZero = isShowZero(column);

		return isShowZero;
	}

	/**
	 * 描述：是否显示0
	 * 
	 * @param column
	 *            列
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-12-2
	 */
	public static boolean isShowZero(IColumn column) {
		boolean isShowZero = false;

		IBasicRender basicRender = column.getRenderer();
		if (basicRender instanceof FdcZeroBaseRender) {
			isShowZero = ((FdcZeroBaseRender) basicRender).isShowZero();
		}
		// 没有设置0渲染器
		else {
			isShowZero = true;
		}

		return isShowZero;
	}

	// ///////////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////////
	
}
