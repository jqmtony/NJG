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
	 *Excel����������������������ʾ,���ڶ�λ��Ԫ�����ù�ʽ
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
		
		//Ҳ���������µķ���
		table.getScriptManager().setDiv0DefaultValue("0");
	}
	public static void setPaseMode(KDTable table){
		//���ñ�񿽱���ģʽΪֵ����
		((KDTTransferAction)table.getActionMap().get(KDTAction.PASTE)).setPasteMode(KDTEditHelper.VALUE);
	}
	/**
	 * @param table @see com.kingdee.bos.ctrl.kdf.table.KDTableHelper
	 * @param mode KDTEditHelper ���� {@link com.kingdee.bos.ctrl.kdf.table.KDTEditHelper}
	 */
	public static void setCutCopyPaseMode(KDTable table,int mode){
		//���ñ�񿽱���ģʽΪֵ����
		((KDTTransferAction)table.getActionMap().get(KDTAction.PASTE)).setPasteMode(mode);
		((KDTTransferAction)table.getActionMap().get(KDTAction.CUT)).setCutMode(mode);
		((KDTTransferAction)table.getActionMap().get(KDTAction.COPY)).setCopyMode(mode);
	}
	
	public static void enableEnterInTable(KDTable table) {
		KDTableHelper.setEnterKeyJumpOrientation(table, KDTableHelper.HORIZON);
	}

    //�س��Զ������С�
	public static void enableAutoAddLine(KDTable table)
    {
		KDTableHelper.updateEnterWithTab(table, true);

	}

	public static void disableAutoAddLine(KDTable table)
    {
		KDTableHelper.updateEnterWithTab(table, false);
	}

    //��������һ�У������¼�ͷ����һ�С�
	public static void enableAutoAddLineDownArrow(KDTable table)
    {
        KDTableHelper.downArrowAutoAddRow(table, true,null);

    }
    //��������һ�У������¼�ͷ����һ�С�
	public static void disableAutoAddLineDownArrow(KDTable table)
    {
        KDTableHelper.downArrowAutoAddRow(table, false,null);

    }
	/**
	 * ���ø���
	 * @param table
	 */
	public static void disableCopy(final KDTable table){
		ActionMap actionMap = table.getActionMap();
		actionMap.remove(KDTAction.COPY);
		actionMap.remove(KDTAction.CUT);
	}
	
	/**
	 * ����delete��
	 * @param table
	 */
	public static void disableDelete(final KDTable table){
		ActionMap actionMap = table.getActionMap();
		actionMap.remove(KDTAction.CUT);
		actionMap.remove(KDTAction.DELETE);
		actionMap.remove(KDTAction.PASTE);
		//Ҳ�������´���ʵ��
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
	 * �п��ƶ�
	 * @param table
	 * @param moveable
	 */
	public static void setColumnMoveable(KDTable table,boolean moveable){
		table.setColumnMoveable(moveable);
	}
	
	/**
	 * ����������
	 * @param table
	 * @param hided
	 */
	public static void setIndexColumnHided(KDTable table,boolean hided){
		// ����������
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
	 * ���������α���Ҽ��˵�
	 * 
	 * @param tblMain
	 * @param menuManager
	 * @author rd_skyiter_wang
	 * @createDate 2014-8-21
	 */
	public static void disableTableMenu(KDTable tblMain, KDTMenuManager menuManager) {
		// �����Ҽ��˵�
		if (null != menuManager && null != menuManager.getMenu()) {
			menuManager.getMenu().removeAll();
		}
	
		// ���������������
		KDTMouseListener[] listeners = tblMain.getListeners(KDTMouseListener.class);
		if (FdcArrayUtil.isNotEmpty(listeners)) {
			for (int i = 0; i < listeners.length; i++) {
				KDTMouseListener eventListener = listeners[i];
				tblMain.removeKDTMouseListener(eventListener);
			}
		}
	}

/**
	 * ���������ñ���Ƿ��������
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

	/*	*����һ�£���ʱע�ͣ�����һ�汾
 * //**
	 * ��ȡָ��λ�õĵ�Ԫ������кš�
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
	 * �����ж���
	 * 
	 * @param table
	 *            ���
	 * @param colIndex
	 *            ������
	 * @param isLock
	 *            �Ƿ�����
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
	 * �����ж���
	 * 
	 * @param table
	 *            ���
	 * @param colKey
	 *            ������
	 * @param isLock
	 *            �Ƿ�����
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
	 * �����ж���
	 * 
	 * @param table
	 *            ���
	 * @param colKeys
	 *            ����������
	 * @param isLock
	 *            �Ƿ�����
	 */
	public static void setColumnLocked(KDTable table, String[] colKeys, boolean isLock) {
		for (int i = 0; i < colKeys.length; i++) {
			String colKey = colKeys[i];

			setColumnLocked(table, colKey, isLock);
		}
	}

	/**
	 * ����������
	 * 
	 * @param table
	 *            ���
	 * @param colIndex
	 *            ������
	 * @param isHided
	 *            �Ƿ�����
	 */
	public static void setColumnHided(KDTable table, int colIndex, boolean isHided) {
		IColumn column = table.getColumn(colIndex);
		if (null != column) {
			StyleAttributes style = column.getStyleAttributes();

			style.setHided(isHided);
		}
	}

	/**
	 * ����������
	 * 
	 * @param table
	 *            ���
	 * @param colKey
	 *            ������
	 * @param isHided
	 *            �Ƿ�����
	 */
	public static void setColumnHided(KDTable table, String colKey, boolean isHided) {
		IColumn column = table.getColumn(colKey);
		if (null != column) {
			StyleAttributes style = column.getStyleAttributes();

			style.setHided(isHided);
		}
	}

	/**
	 * ����������
	 * 
	 * @param table
	 *            ���
	 * @param colKeys
	 *            ����������
	 * @param isHided
	 *            �Ƿ�����
	 */
	public static void setColumnHided(KDTable table, String[] colKeys, boolean isHided) {
		for (int i = 0; i < colKeys.length; i++) {
			String colKey = colKeys[i];

			setColumnHided(table, colKey, isHided);
		}
	}

	/**
	 * �������Ƿ��¼
	 * 
	 * @param table
	 *            ���
	 * @param colIndex
	 *            ������
	 * @param isRequired
	 *            �Ƿ��¼
	 */
	public static void setColumnRequired(KDTable table, int colIndex, boolean isRequired) {
		IColumn column = table.getColumn(colIndex);
		if (null != column) {
			column.setRequired(isRequired);
		}
	}

	/**
	 * �������Ƿ��¼
	 * 
	 * @param table
	 *            ���
	 * @param colKey
	 *            ������
	 * @param isRequired
	 *            �Ƿ��¼
	 */
	public static void setColumnRequired(KDTable table, String colKey, boolean isRequired) {
		IColumn column = table.getColumn(colKey);
		if (null != column) {
			column.setRequired(isRequired);
		}
	}

	/**
	 * �������Ƿ��¼
	 * 
	 * @param table
	 *            ���
	 * @param colKeys
	 *            ����������
	 * @param isRequired
	 *            �Ƿ��¼
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
	 * �����������п��
	 * 
	 * @param table
	 *            ���
	 * @param colIndex
	 *            ������
	 * @param width
	 *            ���
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
	 * �����������п��
	 * 
	 * @param table
	 *            ���
	 * @param colKey
	 *            ������
	 * @param width
	 *            ���
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
	 * �����������п��
	 * 
	 * @param table
	 *            ���
	 * @param colKeys
	 *            ����������
	 * @param width
	 *            ���
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
	 * �����ж���
	 * 
	 * @param table
	 * @param rowIndex
	 *            ����λ��
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
	 * ͳһ�޸������еĸ߶ȡ�
	 * @param table
	 * @param h
	 */
	public static void setAllRowHeight(KDTable table, int h)
	{
		KDTableHelper.setAllRowHeight(table, h);
	}
	
	/**
	 * ��ȡtable��Ԫ����ʾ�����ݡ�
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
	 * ���ݱ��е����ݻ�ȡ���е������
	 * @param table
	 * @param colIndex
	 * @return
	 *//*
	public static int getMaxColumnWidth(KDTable table,int colIndex, boolean ishead) 
	{
		return KDTableHelper.getMaxColumnWidth(table, colIndex, ishead);
	}
	
	*//**
	 * ������Ϊ����ʵ��п�
	 * @param colIndex ������
	 * @return �п�ı� true, �п�û�ı�false
	 *//*
	public static boolean autoFitColumnWidth(KDTable table, int colIndex)
	{
		return KDTableHelper.autoFitColumnWidth(table, colIndex);
	}
	
	*//**
	 * ������Ϊ����ʵ��п�
	 * @param colIndex ������
	 * @return �п�ı� true, �п�û�ı�false
	 *//*
	public static boolean autoFitColumnWidth(KDTable table, int colIndex,int extend)
	{
		return KDTableHelper.autoFitColumnWidth(table, colIndex, extend);
	}
	*//**
	 * ������Ϊ����ʵ��п�
	 * @param colIndex ������
	 * @return �п�ı� true, �п�û�ı�false
	 *//*
	public static boolean autoFitRowHeight(KDTable table, int rowIndex)
	{
		return KDTableHelper.autoFitRowHeight(table, rowIndex);
	}
	
	*//**
	 * ������Ϊ����ʵ��п�
	 * @param colIndex ������
	 * @return �п�ı� true, �п�û�ı�false
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

	// ���KDTable������excel

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
				//Ϊ���в����ص���
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
	 * �����Ҽ���������,��Ƶ�Ȩ��Ҫ��ɱ����ݿ��Excel��������
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
				// ���ش���
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

	// ���෽��@see com.kingdee.bos.ctrl.kdf.table.KDTableHelper
	
	/**
	 * �������Table ���ɵ�Excel
	 * @param tables ��������һ��Object���� objs[0]=title,object[1]=table
	 * @throws Exception
	 */
	public static void exportExcel(CoreUI ui,List tables) throws Exception
    {
    	//Excel ������ҳǩ��ͬ,�����ͬ���޸�ҳǩ����-i
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
        
		// ������excel��
		try
		{
			KDTMenuManager.openFileInExcel(path);
			tempFile.deleteOnExit();
		}
		catch (IOException e2)
		{
			e2.printStackTrace();
			// @AbortException
			
			// excel��ʧ�ܣ����浽ָ��λ��
			KDFileChooser fileChooser = new KDFileChooser();
			int result = fileChooser.showSaveDialog(ui);
			if (result == KDFileChooser.APPROVE_OPTION)
			{
				// File dest = this.getFileChooser().getSelectedFile();
				File dest = fileChooser.getSelectedFile();
				try
				{
					// ��������ʱ�ļ���ָ��Ŀ��
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
	 * ����table����ĳ������������Զ����У���������Ӧ�и�
	 * @param table �����õ�table
	 * @param cloumnKey ���ݵ��е�keyֵ
	 * @param maxLength �������󳤶�
	 * @author owen_wen 2010-12-07
	 */
	public static void autoFitRowHeight(KDTable table, String cloumnKey, int maxLength){
		// ���� cloumnKey �е���ʽ
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
	 * ΪKDTable�����ϼ���footRow�������ء�
	 * <p>
	 * <li>������table.getFootManager()Ϊnull���������Ϊnullʱ��Ϊ֮����һ��FootManager
	 * 
	 * @param table
	 *            �������ϼ��е�KDTable
	 * @return �ϼ���
	 * @Author��owen_wen
	 * @CreateTime��2012-10-24
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
	 * ���ܣ���Ӻϼ��� <br>
	 * <b>ע�⣺�÷������table����ȫ��ɨ�裬��ζ�Ż�ʵģʽȡ�������������ܴ�ʱһ��ȫ��ȡ�������������⡣<b>
	 * @author owen_wen 2010-12-09
	 * @param table	 ָ����KDTable
	 * @param fields   ��Ҫ�ϼƵ���
	 */
	public static IRow apendFootRow(KDTable table, String fields[]) {
		int size = fields.length;
		if (size == 0)
			return null;
		Map sumValue = new HashMap();
		// ����getRowCount�ĵ����п��ܲ���ȷ
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
		// ��������ָ���еĺϼ�ֵ
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
			// ���úϼ�����ʾ��ʽ
			footRow.getCell(colName).getStyleAttributes().setNumberFormat(FDCHelper.strDataFormat);
			footRow.getCell(colName).getStyleAttributes().setHorizontalAlign(
					HorizontalAlignment.RIGHT);
			footRow.getCell(colName).getStyleAttributes().setFontColor(Color.black);
		}

		// ���úϼ��е�ֵ
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
	 * ����ָ�����к��У��ں�������в�����ϼ�
	 * 
	 * @author emanon 2011-03-02
	 * 
	 * @param beginRow
	 *            ��ʼ�����У��������0
	 * @param endRow
	 *            ���������У�����С��table������
	 * @param cols
	 *            ��Ҫ����ϼƵ���������
	 * @param table
	 *            ��Ҫ����ϼƵı��
	 */
	public static IRow calcSumForTable(int beginRow, int endRow, String[] cols,
			KDTable table) {
		int len = cols.length;
		if (table == null || beginRow < 0 || endRow >= table.getRowCount()
				|| beginRow > endRow || len < 1) {
			return null;
		}
		// ����
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
		// �������ֵ
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
	 * ����ָ�����к��У��ں�������в�����ϼ�
	 * 
	 * @author emanon 2011-03-02
	 * 
	 * @param beginRow
	 *            ��ʼ�����У��������0
	 * @param endRow
	 *            ���������У�����С��table������
	 * @param cols
	 *            ��Ҫ����ϼƵ��к�����
	 * @param table
	 *            ��Ҫ����ϼƵı��
	 */
	public static IRow calcSumForTable(int beginRow, int endRow, int[] cols,
			KDTable table) {
		int len = cols.length;
		if (table == null || beginRow < 0 || endRow >= table.getRowCount()
				|| beginRow > endRow || len < 1) {
			return null;
		}
		// ����
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
		// �������ֵ
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
	 * ����Ƿ���ѡ�б���е��У����û�У�������ʾ��Ϣ������ѡ���¼�У��� ��
	 * ���жϵ�ǰ�̣߳�����ҵ��������
	 * @param table Ҫ����table
	 * @Author��owen_wen
	 * @CreateTime��2012-10-9
	 */
	public static void checkSelectedAndAbort(Component ui, KDTable table) {
		if (table.getSelectManager().getActiveRowIndex() == -1) {
			MsgBox.showWarning(ui, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
			SysUtil.abort();
		}
	}

	/**
	 * 
	 * �����������ε�table��������ϻ���
	 * @param table �����������ϻ��ܵı�
	 * @param rowIndex ����һ�п�ʼ����
	 * @param columnNames Ҫ������Щ��
	 * @Author��owen_wen
	 * @CreateTime��2013-5-20
	 * @deprecated �÷���������ͨ���ԣ��ж��������CostAccountInfo�������������б겹�����ź���Ҫɾ���� Aded by Owen_wen 2013-10-11
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
		// �����ε�table��������ϻ��ܣ�ԭ�����㷨�Ǵ������µݹ�ģ����������������⡣
		// ���ڸ�Ϊ��������ɨ�裬���Դ������ɨ����� by skyiter_wang 2013/09/10
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

					// �ҵ���һ���ϼ�����ֱ����ֹѭ�����ϼ����ϼ��Ľ���ۼӣ����ϼ������
					break;
				}
			}
		}
	}

	/**
	 * 
	 * �����������ε�table��������ϻ���
	 * @param table �����������ϻ��ܵı�
	 * @param beginRowIndex ����һ�п�ʼ����
	 * @param columnNames Ҫ������Щ��
	 * @param idColumnName id��
	 * @param parentIdColumnName ����ID��
	 * @Author��owen_wen
	 * @CreateTime��2013-5-20
	 */
	public static void calcTotalAmount(KDTable table, int beginRowIndex, String[] columnNames, String idColumnName, String parentIdColumnName) {
		if (table.getRowCount() <= 1) {
			return;
		}
		
		if (table.getColumn(idColumnName) == null || table.getColumn(parentIdColumnName) == null) {
			throw new IllegalArgumentException("���δ����id�л򸸼�ID�У�������ڲ���");
		}

		int columnSize = columnNames.length;
		BigDecimal[] totalAmt = new BigDecimal[columnSize];
		
		// �����ε�table��������ϻ��ܣ�ԭ�����㷨�Ǵ������µݹ�ģ����������������⡣
		// ���ڸ�Ϊ��������ɨ�裬���Դ������ɨ����� by skyiter_wang 2013/09/10
		
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
					//�Ƿ��״��ҵ�parent��������״��ҵ�����Ҫ�����parents���е�ֵ���������ۼ�
					boolean isFirstFindParent = parentIds.add(parentRow.getCell(idColumnName).getValue().toString()); 
					for (int i = 0; i < columnSize; i++) {
						if (!isFirstFindParent) {
							totalAmt[i] = FDCNumberHelper.add(totalAmt[i], parentRow.getCell(columnNames[i]).getValue());
						}
						parentRow.getCell(columnNames[i]).setValue(totalAmt[i]);
					}

					// �ҵ���һ���ϼ�����ֱ����ֹѭ�����ϼ����ϼ��Ľ���ۼӣ����ϼ������
					break;
				}
			}
		}
	}
	
	/**
	 * 
	 * �����������ε�table��������ϻ���
	 * @param table �����������ϻ��ܵı�
	 * @param beginRowIndex ����һ�п�ʼ����
	 * @param columnNames Ҫ������Щ��
	 * @Author��owen_wen
	 * @CreateTime��2013-5-20
	 */
	public static void calcTotalAmount(KDTable table, String[] columnNames) {
		calcTotalAmount(table, 0, columnNames, "id", "parent.id");
	}

	/**
	 * ȡ�õ�Ԫ��ӳ��
	 * 
	 * @param table
	 *            ���
	 * @param columNames
	 *            ��������
	 * @param startRowIndex
	 *            ��ʼ������
	 * @param endRowIndex
	 *            ����������
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
	 * ȡ�õ�Ԫ��ӳ��
	 * 
	 * @param table
	 *            ���
	 * @param columNames
	 *            ��������
	 * @return
	 */
	public static Map getCellMap(KDTable table, String[] columNames) {
		return getCellMap(table, columNames, 0, table.getRowCount() - 1);
	}

	/**
	 * ȡ�õ�Ԫ���б�
	 * 
	 * @param table
	 *            ���
	 * @param columName
	 *            ����
	 * @param startRowIndex
	 *            ��ʼ������
	 * @param endRowIndex
	 *            ����������
	 * @return
	 */
	public static List getCellList(KDTable table, String columName, int startRowIndex, int endRowIndex) {
		Map cellMap = getCellMap(table, new String[] { columName }, startRowIndex, endRowIndex);

		return (List) cellMap.get(columName);
	}

	/**
	 * ȡ�õ�Ԫ���б�
	 * 
	 * @param table
	 *            ���
	 * @param columName
	 *            ����
	 * @return
	 */
	public static List getCellList(KDTable table, String columName) {
		return getCellList(table, columName, 0, table.getRowCount());
	}

	/**
	 * ȡ�õ�Ԫ��ֵӳ��
	 * 
	 * @param table
	 *            ���
	 * @param columNames
	 *            ��������
	 * @param startRowIndex
	 *            ��ʼ������
	 * @param endRowIndex
	 *            ����������
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
	 * ȡ�õ�Ԫ��ֵӳ��
	 * 
	 * @param table
	 *            ���
	 * @param columNames
	 *            ��������
	 * @return
	 */
	public static Map getCellValueMap(KDTable table, String[] columNames) {
		return getCellValueMap(table, columNames, 0, table.getRowCount() - 1);
	}

	/**
	 * ȡ�õ�Ԫ��ֵ�б�
	 * 
	 * @param table
	 *            ���
	 * @param columName
	 *            ����
	 * @param startRowIndex
	 *            ��ʼ������
	 * @param endRowIndex
	 *            ����������
	 * @return
	 */
	public static List getCellValueList(KDTable table, String columName, int startRowIndex, int endRowIndex) {
		Map cellValueMap = getCellValueMap(table, new String[] { columName }, startRowIndex, endRowIndex);

		return (List) cellValueMap.get(columName);
	}

	/**
	 * ȡ�õ�Ԫ��ֵ�б�
	 * 
	 * @param table
	 *            ���
	 * @param columName
	 *            ����
	 * @return
	 */
	public static List getCellValueList(KDTable table, String columName) {
		return getCellValueList(table, columName, 0, table.getRowCount());
	}

	/**
	 * ȡ�õ�Ԫ��ֵ
	 * 
	 * @param table
	 *            ���
	 * @param columName
	 *            ����
	 * @param rowIndex
	 *            ������
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
	 * ȡ�õ�Ԫ���Ӧ���ַ���ֵӳ��
	 * 
	 * @param table
	 *            ���
	 * @param columNames
	 *            ��������
	 * @param startRowIndex
	 *            ��ʼ������
	 * @param endRowIndex
	 *            ����������
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
	 * ȡ�õ�Ԫ���Ӧ���ַ���ֵӳ��
	 * 
	 * @param table
	 *            ���
	 * @param columNames
	 *            ��������
	 * @return
	 */
	public static Map getCellStringValueMap(KDTable table, String[] columNames) {
		return getCellStringValueMap(table, columNames, 0, table.getRowCount() - 1);
	}

	/**
	 * ȡ�õ�Ԫ���Ӧ���ַ���ֵ�б�
	 * 
	 * @param table
	 *            ���
	 * @param columName
	 *            ����
	 * @param startRowIndex
	 *            ��ʼ������
	 * @param endRowIndex
	 *            ����������
	 * @return
	 */
	public static List getCellStringValueList(KDTable table, String columName, int startRowIndex, int endRowIndex) {
		Map cellStringValueMap = getCellStringValueMap(table, new String[] { columName }, startRowIndex, endRowIndex);

		return (List) cellStringValueMap.get(columName);
	}

	/**
	 * ȡ�õ�Ԫ���Ӧ���ַ���ֵ�б�
	 * 
	 * @param table
	 *            ���
	 * @param columName
	 *            ����
	 * @return
	 */
	public static List getCellStringValueList(KDTable table, String columName) {
		return getCellStringValueList(table, columName, 0, table.getRowCount());
	}

	/**
	 * ȡ�õ�Ԫ���Ӧ���ַ���ֵ
	 * 
	 * @param table
	 *            ���
	 * @param columName
	 *            ����
	 * @param rowIndex
	 *            ������
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
	 * �Ƿ�Ϊ��
	 * 
	 * @param table
	 * @return
	 */
	public static boolean isEmpty(KDTable table) {
		return (null == table) || (0 == table.getRowCount());
	}

	/**
	 * �Ƿ�Ϊ��
	 * 
	 * @param table
	 * @return
	 */
	public static boolean isNotEmpty(KDTable table) {
		return !isEmpty(table);
	}

	/**
	 * ȡ����ID�б�
	 * 
	 * @param table
	 *            ���
	 * @param columName
	 *            ����
	 * @param rowIndex
	 *            ������
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
	 * ������ȡ��ÿ�еĵ�Ԫ��ֵMap
	 * 
	 * @param table
	 *            ���
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
	 * ������ȡ��ÿ�еĵ�Ԫ��ֵMap
	 * 
	 * @param table
	 *            ���
	 * @param startRowIndex
	 *            ��ʼ������
	 * @param endRowIndex
	 *            ����������
	 * @return ÿ�еĵ�Ԫ��ֵMap��Key����������Value��ÿ�е�[��Ԫ���±�+��Ԫ��ֵ]�б�
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
	 * ������ȡ�õ�Ԫ��ֵMap
	 * 
	 * @param table
	 *            ���
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
	 * ������ȡ�õ�Ԫ��ֵMap
	 * 
	 * @param table
	 *            ���
	 * @param startRowIndex
	 *            ��ʼ������
	 * @param endRowIndex
	 *            ����������
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
	 * ������ȡ�����û�����Map
	 * 
	 * @param table
	 *            ���
	 * @return ÿ�е��û�����Map��Key����������Value��ÿ�е��û�����
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
	 * ������ȡ�����û�����Map
	 * 
	 * @param table
	 *            ���
	 * @param startRowIndex
	 *            ��ʼ������
	 * @param endRowIndex
	 *            ����������
	 * @return ÿ�е��û�����Map��Key����������Value��ÿ�е��û�����
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
	 * ������ȡ�õ�Ԫ���û�����Map(����Ϊ��λ)
	 * 
	 * @param table
	 *            ���
	 * @return ÿ�е��û�����Map��Key����������Value��ÿ�е�[��Ԫ���±�+��Ԫ���û�����]�б�
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
	 * ������ȡ�õ�Ԫ���û�����Map(����Ϊ��λ)
	 * 
	 * @param table
	 *            ���
	 * @param startRowIndex
	 *            ��ʼ������
	 * @param endRowIndex
	 *            ����������
	 * @return ÿ�е��û�����Map��Key����������Value��ÿ�е�[��Ԫ���±�+��Ԫ���û�����]�б�
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
	 * ������ȡ�õ�Ԫ���û�����Map
	 * 
	 * @param table
	 *            ���
	 * @return ÿ��Ԫ����û�����Map��Key��[��Ԫ�����±�����,��Ԫ�����±�����]��Value����Ԫ���û�����
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
	 * ������ȡ�õ�Ԫ���û�����Map
	 * 
	 * @param table
	 *            ���
	 * @param startRowIndex
	 *            ��ʼ������
	 * @param endRowIndex
	 *            ����������
	 * @return ÿ��Ԫ����û�����Map��Key��[��Ԫ�����±�����,��Ԫ�����±�����]��Value����Ԫ���û�����
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
	 * ��ʽ�����������
	 * <p>
	 * 1���������strDataFormat��Ϊ�գ������qtyPre��������<br>
	 * 2��ĩβ���Զ�ȥ��0<br>
	 * 
	 * @deprecated �Ƽ�ʹ��FDCTableHelper.formatTableNumber2(KDTable table, String columnName, int scale)�� ��Ҫ���뾫��
	 * @param table
	 *            ���
	 * @param columnName
	 *            ����
	 * @param strDataFormat
	 *            ���ݸ�ʽ
	 * @param scale
	 *            ����
	 */
	public static void formatTableNumber2(KDTable table, String columnName, String strDataFormat, int scale) {
		formatTableNumber2(table, columnName, strDataFormat, scale, false);
	}

	/**
	 * ��ʽ�����������
	 * <p>
	 * 1���������strDataFormat��Ϊ�գ������qtyPre��������<br>
	 * 2��ĩβ���Զ�ȥ��0<br>
	 * 
	 * @deprecated �Ƽ�ʹ��FDCTableHelper.formatTableNumber2(KDTable table, String columnName, int scale)�� ��Ҫ���뾫��
	 * @param table
	 *            ���
	 * @param columnName
	 *            ����
	 * @param strDataFormat
	 *            ���ݸ�ʽ
	 * @param scale
	 *            ����
	 * @param isShowZero
	 *            �Ƿ���ʾĩβ0
	 */
	public static void formatTableNumber2(KDTable table, String columnName, String strDataFormat, int scale,
			boolean isShowZero) {
		if (table.getColumn(columnName) != null) {
			// String strDataFormat = "#,###.##;-#,###.##";
			if (strDataFormat == null) {
				// ȡ��С����ʽ��ĩβ���Զ�ȥ��0
				strDataFormat = FDCRenderHelper.getDecimalFormat2(scale);
			}

			StyleAttributes sa = table.getColumn(columnName).getStyleAttributes();
			sa.setNumberFormat(strDataFormat);
			sa.setHorizontalAlign(HorizontalAlignment.RIGHT); // �Ҷ���

			// 0����ʾ
			if (!isShowZero) {
				FDCTableHelper.setRender(table, columnName, FdcZeroBaseRender.getInstance());
			}
		}
	}

	/**
	 * ��ʽ�����������
	 * <p>
	 * 1��ĩβ���Զ�ȥ��0
	 * 
	 * @param table
	 *            ���
	 * @param columnName
	 *            ����
	 * @param scale
	 *            С��λ��
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
	 * ��ʽ�����������
	 * <p>
	 * 1��ĩβ���Զ�ȥ��0
	 * 
	 * @param table
	 *            ���
	 * @param columnName
	 *            ����
	 * @param scale
	 *            С��λ��
	 * @param isShowZero
	 *            �Ƿ���ʾĩβ0
	 */
	public static void formatTableNumber2(KDTable table, String columnName, int scale, boolean isShowZero) {

		// ȡ��С����ʽ��ĩβ���Զ�ȥ��0
		String dataFormat = FDCRenderHelper.getDecimalFormat2(scale);

		FDCTableHelper.formatTableNumber2(table, columnName, dataFormat, scale, isShowZero);

		// /////////////////////////////////////////////////////////////////
		// ��ʼ��userProperties����ֹ����
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
	 * ��ʽ�����������
	 * <p>
	 * 1��ĩβ���Զ�ȥ��0
	 * 
	 * @param table
	 *            ���
	 * @param columnNames
	 *            ��������
	 * @param scale
	 *            С��λ��
	 */
	public static void formatTableNumber2(KDTable table, String[] columnNames, int scale) {
		for (int i = 0, length = columnNames.length; i < length; i++) {
			if (table.getColumn(columnNames[i]) != null) {
				formatTableNumber2(table, columnNames[i], scale);
			}
		}
	}

	/**
	 * ��ʽ�����������
	 * <p>
	 * 1��ĩβ���Զ�ȥ��0
	 * 
	 * @param table
	 *            ���
	 * @param columnNameList
	 *            ����List
	 * @param scale
	 *            С��λ��
	 */
	public static void formatTableNumber2(KDTable table, List columnNameList, int scale) {
		if (FdcCollectionUtil.isNotEmpty(columnNameList)) {
			// ����������е��ظ�ֵ��Nullֵ
			FdcCollectionUtil.clearDuplicateAndNull(columnNameList);

			String[] columnNames = (String[]) columnNameList.toArray(new String[0]);
			formatTableNumber2(table, columnNames, scale);
		}
	}

	// ///////////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////////

	// ������Ⱦ��
	public static void setRender(KDTable table, String columnName, FdcBaseRender render) {
		IColumn column = table.getColumn(columnName);
		if (column != null) {
			column.setRenderer(render);
		}
	}

	// ������Ⱦ��
	public static void setRender(KDTable table, String[] columnNames, FdcBaseRender[] renders) {
		for (int i = 0, count = columnNames.length; i < count; i++) {
			setRender(table, columnNames[i], renders[i]);
		}
	}

	// ������Ⱦ��
	public static void setRender(KDTable table, List columnNameList, FdcBaseRender[] render) {
		if (FdcCollectionUtil.isNotEmpty(columnNameList)) {
			String[] columnNames = (String[]) columnNameList.toArray(new String[0]);
			setRender(table, columnNames, render);
		}
	}

	// ������Ⱦ��
	public static void setRender(KDTable table, String[] columnNames, FdcBaseRender render) {
		for (int i = 0, count = columnNames.length; i < count; i++) {
			setRender(table, columnNames[i], render);
		}
	}

	// ������Ⱦ��
	public static void setRender(KDTable table, List columnNameList, FdcBaseRender render) {
		if (FdcCollectionUtil.isNotEmpty(columnNameList)) {
			String[] columnNames = (String[]) columnNameList.toArray(new String[0]);
			setRender(table, columnNames, render);
		}
	}

	// ///////////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////////

	/**
	 * ȡ����������
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
	 * �����Ƿ��¼��ȡ����������
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
	 * �����Ƿ����أ�ȡ����������
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
	 * �����Ƿ��¼���Ƿ����أ�ȡ����������
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
	 * �����Ƿ��¼���Ƿ����ء��Ƿ�������ȡ����������
	 * 
	 * @param table
	 *            ���
	 * @param isRequired
	 *            �Ƿ��¼
	 * @param isHided
	 *            �Ƿ�����
	 * @param isLocked
	 *            �Ƿ�����
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
	 * ȡ����������"�Ƿ��¼"����ӳ��
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
	 * ȡ����������"�Ƿ�����"����ӳ��
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
	 * ȡ����������"�Ƿ��¼"��"�Ƿ�����"����ӳ��
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
			str = "[isRequired��" + column.isRequired() + ", isHided:" + column.getStyleAttributes().isHided() + "]";
			map.put(key, str);
		}

		return map;
	}

	// ///////////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////////

	/**
	 * ȡ������ӳ��
	 * <p>
	 * 1������key�������<br>
	 * 2������value����б�ǩ<br>
	 * 
	 * 
	 * @param table
	 *            ������ؼ�
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
	 * ȡ�ñ�ͷ������
	 * 
	 * @param table
	 *            ���
	 * @param key
	 *            �м�
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
	 * ���Դ�ӡ����ӳ��
	 * 
	 * @param table
	 *            ������ؼ�
	 */
	public static void debugPrintColumnKeyNameMap(KDTable table) {
		Map columnKeyNameMap = getColumnKeyNameMap(table);

		System.out.println("-----------------------------------------------");
		MapUtils.debugPrint(System.out, "columnKeyNameMap", columnKeyNameMap);
		System.out.println("-----------------------------------------------");
	}

	/**
	 * ȡ���м�ӳ��
	 * <p>
	 * 1������key����м�<br>
	 * 2������value���������<br>
	 * 
	 * 
	 * @param table
	 *            ������ؼ�
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
	 * ���Դ�ӡ�м�ӳ��
	 * 
	 * @param table
	 *            ������ؼ�
	 */
	public static void debugPrintColumnKeyIndexMap(KDTable table) {
		Map columnKeyIndexMap = getColumnKeyIndexMap(table);

		System.out.println("-----------------------------------------------");
		MapUtils.debugPrint(System.out, "columnKeyIndexMap", columnKeyIndexMap);
		System.out.println("-----------------------------------------------");
	}

	// ////////////////////////////////////////////////////////

	/**
	 * ���Դ�ӡȡ����������"�Ƿ��¼"����ӳ��
	 * 
	 * @param table
	 *            ������ؼ�
	 * @param isRequired
	 *            �Ƿ��¼
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
	 * ���Դ�ӡȡ����������"�Ƿ�����"����ӳ��
	 * 
	 * @param table
	 *            ������ؼ�
	 * @param isHided
	 *            �Ƿ�����
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
	 * ���Դ�ӡȡ����������"�Ƿ��¼"��"�Ƿ�����"����ӳ��
	 * 
	 * @param table
	 *            ������ؼ�
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
	 * ���������Դ�ӡ���ÿ�еĵ�Ԫ��ֵӳ��
	 * 
	 * @param table
	 *            ������ؼ�
	 * @author rd_skyiter_wang
	 * @createDate 2014-7-30
	 */
	public static void debugPrintRowValuesMap(KDTable table) {
		// ȡ��ÿ�еĵ�Ԫ��ֵMap
		Map rowValuesMap = FDCTableHelper.getRowValuesMap(table);

		System.out.println("-----------------------------------------------");
		MapUtils.debugPrint(System.out, "rowValuesMap", rowValuesMap);
		System.out.println("-----------------------------------------------");
	}

	/**
	 * ���������Դ�ӡ���ָ�����еĵ�Ԫ��ֵӳ��
	 * 
	 * @param table
	 *            ������ؼ�
	 * @param startRowIndex
	 *            ��ʼ������
	 * @param endRowIndex
	 *            ����������
	 * @author rd_skyiter_wang
	 * @createDate 2014-8-12
	 */
	public static void debugPrintRowValuesMap(KDTable table, int startRowIndex, int endRowIndex) {
		// ȡ��ÿ�еĵ�Ԫ��ֵMap
		Map rowValuesMap = FDCTableHelper.getRowValuesMap(table, startRowIndex, endRowIndex);

		System.out.println("-----------------------------------------------");
		MapUtils.debugPrint(System.out, "rowValuesMap", rowValuesMap);
		System.out.println("-----------------------------------------------");
	}

	/**
	 * ���������Դ�ӡ���ָ���еĵ�Ԫ��ֵӳ��
	 * 
	 * @param table
	 *            ������ؼ�
	 * @param row
	 *            ��
	 * @param endRowIndex
	 *            ����������
	 * @author rd_skyiter_wang
	 * @createDate 2014-8-12
	 */
	public static void debugPrintRowValuesMap(KDTable table, IRow row) {
		int startRowIndex = row.getRowIndex();
		int endRowIndex = row.getRowIndex();

		// ���Դ�ӡ���ָ�����еĵ�Ԫ��ֵӳ��
		FDCTableHelper.debugPrintRowValuesMap(table, startRowIndex, endRowIndex);
	}

	// ////////////////////////////////////////////////////////

	// ////////////////////////////////////////////////////////

	/**
	 * ���������Դ�ӡ���ÿ�еĵ�Ԫ���û�����ӳ��
	 * 
	 * @param table
	 *            ������ؼ�
	 * @author rd_skyiter_wang
	 * @createDate 2014-7-30
	 */
	public static void debugPrintCellUserObjectMap4Row(KDTable table) {
		// ȡ��ÿ�еĵ�Ԫ���û�����Map
		Map cellUserObjectMap4Row = FDCTableHelper.getCellUserObjectMap4Row(table);

		System.out.println("-----------------------------------------------");
		MapUtils.debugPrint(System.out, "cellUserObjectMap4Row", cellUserObjectMap4Row);
		System.out.println("-----------------------------------------------");
	}

	/**
	 * ���������Դ�ӡ���ָ�����еĵ�Ԫ���û�����ӳ��
	 * 
	 * @param table
	 *            ������ؼ�
	 * @param startRowIndex
	 *            ��ʼ������
	 * @param endRowIndex
	 *            ����������
	 * @author rd_skyiter_wang
	 * @createDate 2014-8-12
	 */
	public static void debugPrintCellUserObjectMap4Row(KDTable table, int startRowIndex, int endRowIndex) {
		// ȡ��ÿ�еĵ�Ԫ���û�����Map
		Map cellUserObjectMap4Row = FDCTableHelper.getCellUserObjectMap4Row(table, startRowIndex, endRowIndex);

		System.out.println("-----------------------------------------------");
		MapUtils.debugPrint(System.out, "cellUserObjectMap4Row", cellUserObjectMap4Row);
		System.out.println("-----------------------------------------------");
	}

	/**
	 * ���������Դ�ӡ���ָ���еĵ�Ԫ���û�����ӳ��
	 * 
	 * @param table
	 *            ������ؼ�
	 * @param row
	 *            ��
	 * @param endRowIndex
	 *            ����������
	 * @author rd_skyiter_wang
	 * @createDate 2014-8-12
	 */
	public static void debugPrintCellUserObjectMap4Row(KDTable table, IRow row) {
		int startRowIndex = row.getRowIndex();
		int endRowIndex = row.getRowIndex();

		// ���Դ�ӡ���ָ�����еĵ�Ԫ���û�����ӳ��
		FDCTableHelper.debugPrintCellUserObjectMap4Row(table, startRowIndex, endRowIndex);
	}

	// ////////////////////////////////////////////////////////

	// ////////////////////////////////////////////////////////

	/**
	 * ���������Դ�ӡ���ÿ�е����û�����ӳ��
	 * 
	 * @param table
	 *            ������ؼ�
	 * @author rd_skyiter_wang
	 * @createDate 2014-7-30
	 */
	public static void debugPrintRowUserObjectMap(KDTable table) {
		// ȡ��ÿ�е��û�����Map
		Map rowUserObjectMap = FDCTableHelper.getRowUserObjectMap(table);

		System.out.println("-----------------------------------------------");
		MapUtils.debugPrint(System.out, "rowUserObjectMap", rowUserObjectMap);
		System.out.println("-----------------------------------------------");
	}

	/**
	 * ���������Դ�ӡ���ָ�����е����û�����ӳ��
	 * 
	 * @param table
	 *            ������ؼ�
	 * @param startRowIndex
	 *            ��ʼ������
	 * @param endRowIndex
	 *            ����������
	 * @author rd_skyiter_wang
	 * @createDate 2014-8-12
	 */
	public static void debugPrintRowUserObjectMap(KDTable table, int startRowIndex, int endRowIndex) {
		// ȡ��ÿ�е��û�����Map
		Map rowUserObjectMap = FDCTableHelper.getRowUserObjectMap(table, startRowIndex, endRowIndex);

		System.out.println("-----------------------------------------------");
		MapUtils.debugPrint(System.out, "rowUserObjectMap", rowUserObjectMap);
		System.out.println("-----------------------------------------------");
	}

	/**
	 * ���������Դ�ӡ���ָ���е����û�����ӳ��
	 * 
	 * @param table
	 *            ������ؼ�
	 * @param row
	 *            ��
	 * @param endRowIndex
	 *            ����������
	 * @author rd_skyiter_wang
	 * @createDate 2014-8-12
	 */
	public static void debugPrintRowUserObjectMap(KDTable table, IRow row) {
		int startRowIndex = row.getRowIndex();
		int endRowIndex = row.getRowIndex();

		// ���Դ�ӡ���ָ�����е����û�����ӳ��
		FDCTableHelper.debugPrintRowUserObjectMap(table, startRowIndex, endRowIndex);
	}

	// ////////////////////////////////////////////////////////

	/**
	 * ���������Դ�ӡ���
	 * 
	 * @param table
	 *            ������ؼ�
	 * @author rd_skyiter_wang
	 * @createDate 2014-7-30
	 */
	public static void debugPrint(KDTable table) {
		debugPrint(table, true);
	}

	/**
	 * ���������Դ�ӡ���
	 * 
	 * @param table
	 *            ������ؼ�
	 * @param isPrintRowValuesMap
	 *            �Ƿ��ӡÿ�еĵ�Ԫ��ֵӳ��
	 * @author rd_skyiter_wang
	 * @createDate 2014-7-30
	 */
	public static void debugPrint(KDTable table, boolean isPrintRowValuesMap) {
		// ���Դ�ӡ�м�ӳ��
		debugPrintColumnKeyIndexMap(table);

		// ���Դ�ӡ����ӳ��
		debugPrintColumnKeyNameMap(table);

		// ���Դ�ӡȡ����������"�Ƿ��¼"��"�Ƿ�����"����ӳ��
		debugPrintColumnKeyRequiredHidedMap(table);

		if (isPrintRowValuesMap) {
			// ��ӡ���ÿ�еĵ�Ԫ��ֵӳ��
			debugPrintRowValuesMap(table);
		}
	}

	// ///////////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////////

	/**
	 * ��ʽ�������
	 * 
	 * @param table
	 *            ���
	 * @param columnName
	 *            ����
	 * @param format
	 *            ��ʽ
	 */
	public static void formatTable(KDTable table, String columnName, String format) {
		table.getColumn(columnName).getStyleAttributes().setNumberFormat(format);
	}

	/**
	 * ��ʽ������
	 * 
	 * @param table
	 *            ���
	 * @param columnName
	 *            ����
	 */
	public static void formatTableDate(KDTable table, String columnName) {
		if (table.getColumn(columnName) != null) {
			formatTable(table, columnName, FDCClientHelper.DATE_FORMAT);
		}
	}

	/**
	 * ��ʽ������
	 * 
	 * @param table
	 *            ���
	 * @param columnNames
	 *            ��������
	 */
	public static void formatTableDate(KDTable table, String[] columnNames) {
		for (int i = 0, length = columnNames.length; i < length; i++) {
			formatTableDate(table, columnNames[i]);
		}
	}

	/**
	 * ��ʽ������
	 * 
	 * @param table
	 *            ���
	 * @param columnNameList
	 *            ����List
	 */
	public static void formatTableDate(KDTable table, List columnNameList) {
		if (FdcCollectionUtil.isNotEmpty(columnNameList)) {
			// ����������е��ظ�ֵ��Nullֵ
			FdcCollectionUtil.clearDuplicateAndNull(columnNameList);

			String[] columnNames = (String[]) columnNameList.toArray(new String[0]);
			formatTableDate(table, columnNames);
		}
	}

	/**
	 * ��ʽ������ʱ��
	 * 
	 * @param table
	 *            ���
	 * @param columnName
	 *            ����
	 */
	public static void formatTableDateTime(KDTable table, String columnName) {
		formatTable(table, columnName, FDCClientHelper.TIME_FORMAT);
	}

	// ///////////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////////

	/**
	 * ѡ����
	 * 
	 * @param table
	 * @param rowIndex
	 */
	public static void selectRow(KDTable table, int rowIndex) {
		int columnCount = table.getColumnCount();

		// �ָ�ѡ��ԭ��
		table.getSelectManager().select(rowIndex, 0, rowIndex, columnCount);
		// ��ָ���й�������������
		table.scrollToVisible(rowIndex, 0);
	}

	// ///////////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////////

	/**
	 * ȡ�������е�Ԫ��ӳ��
	 * 
	 * @param table
	 *            ���
	 * @param columNames
	 *            ��������
	 * @param startRowIndex
	 *            ��ʼ������
	 * @param endRowIndex
	 *            ����������
	 * @param row
	 *            ��
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
	 * ȡ�������е�Ԫ��ӳ��
	 * 
	 * @param table
	 *            ���
	 * @param columNames
	 *            ��������
	 * @param row
	 *            ��
	 * @return
	 */
	public static Map getOtherCellMap(KDTable table, String[] columNames, IRow row) {
		return getOtherCellMap(table, columNames, 0, table.getRowCount() - 1, row);
	}

	/**
	 * ȡ�������е�Ԫ���б�
	 * 
	 * @param table
	 *            ���
	 * @param columName
	 *            ����
	 * @param startRowIndex
	 *            ��ʼ������
	 * @param endRowIndex
	 *            ����������
	 * @param row
	 *            ��
	 * @return
	 */
	public static List getOtherCellList(KDTable table, String columName, int startRowIndex, int endRowIndex, IRow row) {
		Map cellMap = getOtherCellMap(table, new String[] { columName }, startRowIndex, endRowIndex, row);

		return (List) cellMap.get(columName);
	}

	/**
	 * ȡ�������е�Ԫ���б�
	 * 
	 * @param table
	 *            ���
	 * @param columName
	 *            ����
	 * @param row
	 *            ��
	 * @return
	 */
	public static List getOtherCellList(KDTable table, String columName, IRow row) {
		return getOtherCellList(table, columName, 0, table.getRowCount(), row);
	}

	/**
	 * ȡ�������е�Ԫ��ֵӳ��
	 * 
	 * @param table
	 *            ���
	 * @param columNames
	 *            ��������
	 * @param startRowIndex
	 *            ��ʼ������
	 * @param endRowIndex
	 *            ����������
	 * @param row
	 *            ��
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
	 * ȡ�������е�Ԫ��ֵӳ��
	 * 
	 * @param table
	 *            ���
	 * @param columNames
	 *            ��������
	 * @param row
	 *            ��
	 * @return
	 */
	public static Map getOtherCellValueMap(KDTable table, String[] columNames, IRow row) {
		return getOtherCellValueMap(table, columNames, 0, table.getRowCount() - 1, row);
	}

	/**
	 * ȡ�������е�Ԫ��ֵ�б�
	 * 
	 * @param table
	 *            ���
	 * @param columName
	 *            ����
	 * @param startRowIndex
	 *            ��ʼ������
	 * @param endRowIndex
	 *            ����������
	 * @param row
	 *            ��
	 * @return
	 */
	public static List getOtherCellValueList(KDTable table, String columName, int startRowIndex, int endRowIndex,
			IRow row) {
		Map cellValueMap = getOtherCellValueMap(table, new String[] { columName }, startRowIndex, endRowIndex, row);

		return (List) cellValueMap.get(columName);
	}

	/**
	 * ȡ�������е�Ԫ��ֵ�б�
	 * 
	 * @param table
	 *            ���
	 * @param columName
	 *            ����
	 * @param row
	 *            ��
	 * @return
	 */
	public static List getOtherCellValueList(KDTable table, String columName, IRow row) {
		return getOtherCellValueList(table, columName, 0, table.getRowCount(), row);
	}

	/**
	 * �Ƿ������Ԫ��
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
	 * �Ƿ������Ԫ��
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
	 * �Ƿ������Ԫ��ֵ
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
	 * �Ƿ������Ԫ��ֵ
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
	 * ȡ��ѡ��ĵ�Ԫ��ֵ
	 * 
	 * @param comp
	 *            ����
	 * @param table
	 *            ���
	 * @param key
	 *            �м�
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
	 * ȡ��ѡ��ĵ�Ԫ��ֵ
	 * 
	 * @param comp
	 *            ����
	 * @param row
	 *            ��
	 * @param key
	 *            �м�
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
	 * ȡ��Ψһѡ��ĵ�Ԫ��ֵ
	 * 
	 * @param comp
	 *            ����
	 * @param table
	 *            ���
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
			MsgBox.showWarning(comp, "ֻ��ѡ��һ��");
			SysUtil.abort();
		}

		KDTSelectBlock block = (KDTSelectBlock) blocks.get(0);
		top = block.getTop();
		bottom = block.getBottom();

		if (top != bottom) {
			MsgBox.showWarning(comp, "ֻ��ѡ��һ��");
			SysUtil.abort();
		}
		if (block.getLeft() != block.getRight()) {
			MsgBox.showWarning(comp, "ֻ��ѡ��һ��");
			SysUtil.abort();
		}
		if (-1 == left) {
			left = block.getLeft();
		} else if (block.getLeft() == left) {
			MsgBox.showWarning(comp, "ֻ��ѡ��һ��");
			SysUtil.abort();
		}

		ICell cell = table.getRow(top).getCell(left);
		value = cell.getValue();

		return value;
	}

	/**
	 * ������ֵ
	 * 
	 * @param table
	 *            ���
	 * @param key
	 *            �����е�key
	 * @param value
	 *            ֵ
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
	 * ���õ�Ԫ��ֵ
	 * 
	 * @param row
	 *            ��
	 * @param key
	 *            ��Ԫ�������е�key
	 * @param value
	 *            ֵ
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
	 * �������Ƿ���ʾ0
	 * 
	 * @param kdtEntries
	 *            ���
	 * @param key
	 *            ����
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
	 * �������Ƿ���ʾ0
	 * 
	 * @param column
	 *            ��
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
		// û������0��Ⱦ��
		else {
			isShowZero = true;
		}

		return isShowZero;
	}

	// ///////////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////////
	
}
