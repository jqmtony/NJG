/*
 * @(#)FDCClientVerifyHelper.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;

import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTEditManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.IKDTextComponent;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDLabelContainer;
import com.kingdee.bos.ctrl.swing.KDMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.bos.ctrl.swing.KDSplitPane;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.ctrl.swing.KDTextArea;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.commonquery.CompareTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberValueVerifyRule;
import com.kingdee.eas.fdc.basedata.MathOperateEnum;
import com.kingdee.eas.fdc.basedata.util.FdcArrayUtil;
import com.kingdee.eas.fdc.basedata.util.FdcCollectionUtil;
import com.kingdee.eas.fdc.basedata.util.FdcMethodUtil;
import com.kingdee.eas.fdc.basedata.util.FdcObjectCollectionUtil;
import com.kingdee.eas.fdc.basedata.util.FdcStringUtil;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.DateTimeUtils;

/**
 * ���������ز� �ͻ���У������
 * 
 * @author ����
 * @email SkyIter@live.com
 * @createDate 2014-12-2
 * @version 1.0, 2014-12-2
 * @see
 * @since JDK1.4
 */
public class FDCClientVerifyHelper {

	private static String path = "com.kingdee.eas.fm.common.FMResource";

	private static final BigDecimal MAX_VALUE = new BigDecimal("9999999999999.99");

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * ������У��kdtable��Ԫ���Ƿ�Ϊ��
	 * 
	 * @param ui
	 *            UI����
	 * @param resourcePath
	 *            ��Դ·��
	 * @param kdtEntries
	 *            ���
	 * @param row
	 *            ��
	 * @param colIndex
	 *            ������
	 * @param resName
	 *            ��Դ��
	 * @author rd_skyiter_wang
	 * @createDate 2014-12-2
	 */
	public static void verifyInput(CoreUIObject ui, String resourcePath, KDTable kdtEntries, IRow row, int colIndex,
			String resName) {
		if (FDCHelper.isEmpty(row.getCell(colIndex).getValue())) {
			kdtEntries.getEditManager().editCellAt(row.getRowIndex(), colIndex);
			MsgBox.showWarning(ui, EASResource.getString(resourcePath, resName));

			SysUtil.abort();
		}
	}

	/**
	 * У��kdtable��Ԫ���Ƿ�Ϊ��
	 * 
	 * @param ui
	 *            UI����
	 * @param kdtEntries
	 *            ���
	 * @param key
	 *            ����
	 */
	public static void verifyInput(CoreUIObject ui, KDTable kdtEntries, String key) {
		IRow row = null;
		for (int j = 0; j < kdtEntries.getRowCount(); j++) {
			row = kdtEntries.getRow(j);
			verifyInput(ui, kdtEntries, row, key);
		}
	}

	/**
	 * ������У��kdtable��Ԫ���Ƿ�Ϊ��
	 * 
	 * @param ui
	 *            UI����
	 * @param kdtEntries
	 *            ���
	 * @param row
	 *            ��
	 * @param key
	 *            ����
	 * @author rd_skyiter_wang
	 * @createDate 2014-12-2
	 */
	public static void verifyInput(CoreUIObject ui, KDTable kdtEntries, IRow row, String key) {
		if (FDCHelper.isEmpty(row.getCell(key).getValue())) {
			int colIndex = kdtEntries.getColumnIndex(key);

			kdtEntries.getEditManager().editCellAt(row.getRowIndex(), colIndex);

			// String headValue = (String) kdtEntries.getHeadRow(0).getCell(key).getValue();
			String headValue = FDCTableHelper.getHeadName(kdtEntries, key);
			String msg = headValue + " " + EASResource.getString(path, "CanNotBeNull");
			msg = msg.replaceAll("#", " " + headValue + " ");
			MsgBox.showWarning(ui, msg);

			SysUtil.abort();
		}
	}

	/**
	 * У��ǿ�
	 * 
	 * @param ui
	 *            UI����
	 * @param kdtEntries
	 *            ���
	 * @param keys
	 *            �е�keyֵ����
	 */
	public static void verifyInput(CoreUIObject ui, KDTable kdtEntries, String[] keys) {
		if (FDCTableHelper.isNotEmpty(kdtEntries) && FdcArrayUtil.isNotEmpty(keys)) {
			for (int i = 0, len = keys.length; i < len; i++) {
				verifyInput(ui, kdtEntries, keys[i]);
			}
		}
	}

	/**
	 * У���������б�¼���Ƿ�ǿ�
	 * <p>
	 * ��¼����������
	 * 
	 * @param ui
	 *            UI����
	 * @param kdtEntries
	 *            ���
	 */
	public static void verifyInput(CoreUIObject ui, KDTable kdtEntries) {
		// ȡ�����б�¼��������
		String[] columnNames = FDCTableHelper.getColumnKeysOfRequiredHided(kdtEntries, true, false);

		// У����(��������0�Ƿ��������⴦��)�ǿ�
		verifyInputForZeroRender(ui, kdtEntries, columnNames);
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * У����(��������)�����б�¼���Ƿ�ǿ�
	 * 
	 * @param ui
	 *            UI����
	 * @param kdtEntries
	 *            ���
	 * @param isVerifyLocked
	 *            �Ƿ�ҲҪ��֤������
	 */
	public static void verifyInputForLocked(CoreUIObject ui, KDTable kdtEntries, boolean isVerifyLocked) {
		// ȡ�����б�¼�������ء���\��������
		String[] columnNames = null;

		// �Ƿ�ҲҪ��֤������
		if (isVerifyLocked) {
			columnNames = FDCTableHelper.getColumnKeysOfRequiredHided(kdtEntries, true, false);
		} else {
			columnNames = FDCTableHelper.getColumnKeysOfRequiredHidedLocked(kdtEntries, true, false, false);
		}

		// // ��У��������Ԫ�񣺲������Ƿ������������Ԫ����������ô�˵�Ԫ���ǲ���У���
		// boolean isNotVerifyLockedCell = true;
		//		
		// verifyInputForLocked(ui, kdtEntries, columnNames, isNotVerifyLockedCell);

		boolean isNotVerifyLockedCell = !isVerifyLocked;
		verifyInputForLocked(ui, kdtEntries, columnNames, isNotVerifyLockedCell);
	}

	/**
	 * У����(��������)�ǿ�
	 * 
	 * @param ui
	 *            UI����
	 * @param kdtEntries
	 *            ���
	 * @param keys
	 *            �е�keyֵ����
	 * @param isNotVerifyLockedCell
	 *            ��У��������Ԫ��
	 */
	public static void verifyInputForLocked(CoreUIObject ui, KDTable kdtEntries, String[] keys,
			boolean isNotVerifyLockedCell) {
		if (FDCTableHelper.isNotEmpty(kdtEntries) && FdcArrayUtil.isNotEmpty(keys)) {
			for (int i = 0, len = keys.length; i < len; i++) {
				// verifyInputForLocked(ui, kdtEntries, keys[i], isNotVerifyLockedCell, isAllowZero);

				String key = keys[i];
				IColumn column = kdtEntries.getColumn(key);
				if (column == null) {
					continue;
				}

				// �Ƿ���ʾ0
				boolean isShowZero = FDCTableHelper.isShowZero(column);
				verifyInputForLocked(ui, kdtEntries, key, isNotVerifyLockedCell, isShowZero);
			}
		}
	}

	/**
	 * ������У��kdtable(��������)��Ԫ���Ƿ�Ϊ��
	 * 
	 * @param ui
	 *            UI����
	 * @param kdtEntries
	 *            ���
	 * @param key
	 *            ����
	 * @param isNotVerifyLockedCell
	 *            ��У��������Ԫ��
	 * @param isAllowZero
	 *            �Ƿ�����0
	 * @author rd_skyiter_wang
	 * @createDate 2014-8-28
	 */
	public static void verifyInputForLocked(CoreUIObject ui, KDTable kdtEntries, String key,
			boolean isNotVerifyLockedCell, boolean isAllowZero) {
		IRow row = null;
		for (int j = 0; j < kdtEntries.getRowCount(); j++) {
			row = kdtEntries.getRow(j);
			verifyInputForLocked(ui, kdtEntries, row, key, isNotVerifyLockedCell, isAllowZero);
		}
	}

	/**
	 * ������У��kdtable(��������)��Ԫ���Ƿ�Ϊ��
	 * 
	 * @param ui
	 *            UI����
	 * @param kdtEntries
	 *            ���
	 * @param row
	 *            ��
	 * @param key
	 *            ����
	 * @param isNotVerifyLockedCell
	 *            ��У��������Ԫ��
	 * @param isAllowZero
	 *            �Ƿ�����0
	 * @author rd_skyiter_wang
	 * @createDate 2014-8-28
	 */
	public static void verifyInputForLocked(CoreUIObject ui, KDTable kdtEntries, IRow row, String key,
			boolean isNotVerifyLockedCell, boolean isAllowZero) {
		// if (FDCHelper.isEmpty(row.getCell(key).getValue())) {
		if (FDCHelper.isEmpty(row.getCell(key).getValue(), isAllowZero)) {
			int colIndex = kdtEntries.getColumnIndex(key);
			if (isNotVerifyLockedCell) {
				// ��У��������Ԫ��
				boolean isLocked = row.getCell(colIndex).getStyleAttributes().isLocked();
				if (isLocked) {
					return;
				}
			}

			// ��ñ༭����
			kdtEntries.getEditManager().editCellAt(row.getRowIndex(), colIndex);

			// String headValue = (String) kdtEntries.getHeadRow(0).getCell(key).getValue();
			String headValue = FDCTableHelper.getHeadName(kdtEntries, key);
			String msg = headValue + " " + EASResource.getString(path, "CanNotBeNull");
			msg = msg.replaceAll("#", " " + headValue + " ");
			MsgBox.showWarning(ui, msg);

			SysUtil.abort();
		}
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * У����(��������0�Ƿ��������⴦��)�ǿ�
	 * 
	 * @param ui
	 *            UI����
	 * @param kdtEntries
	 *            ���
	 * @param keys
	 *            �е�keyֵ����
	 */
	public static void verifyInputForZeroRender(CoreUIObject ui, KDTable kdtEntries, String[] keys) {
		if (FDCTableHelper.isNotEmpty(kdtEntries) && FdcArrayUtil.isNotEmpty(keys)) {
			for (int i = 0, len = keys.length; i < len; i++) {
				// verifyInput(ui, kdtEntries, keys[i]);

				String key = keys[i];
				IColumn column = kdtEntries.getColumn(key);
				if (column == null) {
					continue;
				}

				// �Ƿ���ʾ0
				boolean isShowZero = FDCTableHelper.isShowZero(column);
				verifyInputForZeroRender(ui, kdtEntries, key, isShowZero);
			}
		}
	}

	/**
	 * ������У����(��������0�Ƿ��������⴦��)�ǿ�
	 * 
	 * @param ui
	 *            UI����
	 * @param kdtEntries
	 *            ���
	 * @param key
	 *            �е�keyֵ
	 * @param isAllowZero
	 *            �Ƿ�����0
	 * @author rd_skyiter_wang
	 * @createDate 2014-12-2
	 */
	public static void verifyInputForZeroRender(CoreUIObject ui, KDTable kdtEntries, String key, boolean isAllowZero) {
		IRow row = null;
		for (int j = 0; j < kdtEntries.getRowCount(); j++) {
			row = kdtEntries.getRow(j);
			verifyInputForZeroRender(ui, kdtEntries, row, key, isAllowZero);
		}
	}

	/**
	 * У����(��������0�Ƿ��������⴦��)�ǿ�
	 * 
	 * @param ui
	 *            UI����
	 * @param kdtEntries
	 *            ���
	 * @param row
	 *            ��
	 * @param key
	 *            �е�keyֵ
	 * @param isAllowZero
	 *            �Ƿ�����0
	 * @author rd_skyiter_wang
	 * @createDate 2014-12-2
	 */
	public static void verifyInputForZeroRender(CoreUIObject ui, KDTable kdtEntries, IRow row, String key,
			boolean isAllowZero) {
		if (FDCHelper.isEmpty(row.getCell(key).getValue(), isAllowZero)) {
			int colIndex = kdtEntries.getColumnIndex(key);
			kdtEntries.getEditManager().editCellAt(row.getRowIndex(), colIndex);
			String headValue = "";
			for (int i = 0; i < kdtEntries.getHeadRowCount(); i++) {
				if (!"".equals(headValue)) {
					headValue += "-";
				}
				headValue += (String) kdtEntries.getHeadRow(i).getCell(key).getValue();
			}
			String msg = headValue + " " + EASResource.getString(path, "CanNotBeNull");
			msg = msg.replaceAll("#", " " + headValue + " ");
			MsgBox.showWarning(ui, msg);
			SysUtil.abort();
		}
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////
	
	/**
	 * У��kdtable��Ԫ���ֵ�Ƿ����0
	 * @param ui
	 * @param kdtEntries
	 * @param i ����ĳһ�е�����
	 */
	public static void verifyInputIsZero(CoreUIObject ui, KDTable kdtEntries, IRow row, String key) {
	    int colIndex = kdtEntries.getColumnIndex(key);
    
	    Object obj = row.getCell(key).getValue();
	    
		if (obj instanceof BigDecimal && ((BigDecimal)obj).floatValue()==0) {
			kdtEntries.getEditManager().editCellAt(row.getRowIndex(), colIndex);
			
			String headValue = (String)kdtEntries.getHeadRow(0).getCell(key).getValue();
			String msg = headValue+" "+EASResource.getString(path, "CanNotBeZero");
			msg = msg.replaceAll("#", " "+headValue+" ");
			MsgBox.showWarning(ui, msg);
	
			SysUtil.abort();
		}
	}
	
	/**
	 * У��kdtable��Ԫ���ֵ�Ƿ���ڽ�����ֵ9,999,999,999,999.99
	 * @param ui
	 * @param kdtEntries
	 * @param i ����ĳһ�е�����
	 */
	public static void verifyInputIsMaxValue(CoreUIObject ui, KDTable kdtEntries, IRow row, String key) {
	    int colIndex = kdtEntries.getColumnIndex(key);
    
	    Object obj = row.getCell(key).getValue();
	    
		if (!FDCHelper.isEmpty(obj) && obj instanceof BigDecimal
				&& ((BigDecimal) obj).compareTo(MAX_VALUE) == 1) {
			
			kdtEntries.getEditManager().editCellAt(row.getRowIndex(), colIndex);
			
			String headValue = (String)kdtEntries.getHeadRow(0).getCell(key).getValue();
			String msg = headValue+" "+EASResource.getString(path, "CanNotBeZero");
			msg = msg.replaceAll("#", " "+headValue+" ");
			MsgBox.showWarning(ui, msg);
	
			SysUtil.abort();
		}
	}
	
	/**
	 * У��kdtable�Ƿ�Ϊ��
	 * @param ui
	 * @param kdtEntries
	 */
	public static  void verifyEmpty(CoreUIObject ui, KDTable kdtEntries) {
	    
	    if (kdtEntries == null || kdtEntries.getRowCount() < 1) {
	        MsgBox.showWarning(ui, EASResource.getString(path, "EntryCanNotBeNull"));
			SysUtil.abort();
	    }
	}
	
	/**
	 *  У���ı����Ƿ�Ϊ��
	 */
	public  static void verifyEmpty(CoreUIObject ui, String resourcePath, KDTextField txtNumber,
			String msg) {
		String txt = txtNumber.getText();
		String tooltip = txtNumber.getToolTipText();
		if (!txtNumber.isRequired())
			return;
		if (txt == null || txt.trim().equals("")) {
			txtNumber.requestFocus(true);
			MsgBox.showWarning(ui, ((resourcePath==null || msg==null))?((tooltip!=null?tooltip:"")+getMessage(txtNumber)):EASResource.getString(resourcePath, msg));
			SysUtil.abort();	
		}
	}
	
	public  static void verifyEmpty(CoreUIObject ui, KDTextField txtNumber) {
	    verifyEmpty(ui, null, txtNumber, null);
	}
	
	public  static void verifyEmpty(CoreUIObject ui, String resourcePath, JFormattedTextField txtField,
			String msg, boolean isAbort) {
		String txt = txtField.getText();
		if (txt == null || txt.trim().equals("")) {
			txtField.requestFocus(true);
			MsgBox.showWarning(ui, ((resourcePath==null || msg==null))?getMessage(txtField):EASResource.getString(resourcePath, msg));
			if(isAbort){
				SysUtil.abort();	
			}
		}
	}
	
	public  static void verifyEmpty(CoreUIObject ui, JFormattedTextField txtNumber) {
	    verifyEmpty(ui, null, txtNumber, null, true);
	}
	
	/**
     * @return
     */
    private static String getMessage(Component component)
    {
        String text = getCompLabelText(component);
        return text+EASResource.getString(path, "CanNotBeNull");
    }

	private static String getCompLabelText(Component component) {
		String text = "";
        if(component.getParent() instanceof KDLabelContainer)
        {
            text = ((KDLabelContainer) component.getParent()).getBoundLabelText();
        }
        //add by yangzk @ 2006-3-7
        else if(component instanceof KDTextArea)//�����kdtextarea�����Ǿ���jscrollpane�İ�װ
        {
        	Container cont = component.getParent();
        	if(cont!=null)
        	{
        		Container cont2 = cont.getParent();
        		if(cont2 instanceof KDScrollPane)
        		{
        			if(cont2.getParent() instanceof KDLabelContainer)
        			{
        				text = ((KDLabelContainer) cont2.getParent()).getBoundLabelText();
        			}
        		}
        	}
        }
		return text;
	}

    /**
	 * У��f7�Ƿ�Ϊ��
     * @param isAbort TODO
	 */
	public static void verifyEmpty(CoreUIObject ui, String resourcePath, KDBizPromptBox bizBox, String msg, boolean isAbort){
	    Object content = bizBox.getData();
	    String tooltip = bizBox.getToolTipText();
	    if(!bizBox.isRequired())
	    	return;
	    if(content==null||(content.getClass().isArray() &&FDCHelper.isEmpty(((Object[])content)))){
	        bizBox.requestFocus(true);
			MsgBox.showWarning(ui, (resourcePath==null || msg==null)?((tooltip!=null?tooltip:"")+getMessage(bizBox)):EASResource.getString(resourcePath, msg));
			if(isAbort) SysUtil.abort();	
	    }
	}
	
	public  static void verifyEmpty(CoreUIObject ui, KDBizPromptBox bizBox) {
	    verifyEmpty(ui, null, bizBox, null, true);
	}
	
	public  static void verifyEmpty(CoreUIObject ui, KDComboBox comboBox) {
	    verifyEmpty(ui, null, comboBox, null);
	}
	
	/**
	 * У���������Ƿ�Ϊ��
	 */
	public static void verifyEmpty(CoreUIObject ui, String resourcePath, KDMultiLangBox multLangBox, String msg){
		if(!multLangBox.isRequired())
			return;
	    Object content = multLangBox.getSelectedItem();
	    if(content == null||content.toString().trim().length()<=0){
	    	multLangBox.requestFocus(true);
			MsgBox.showWarning(ui, (resourcePath==null || msg==null)?getMessage(multLangBox):EASResource.getString(resourcePath, msg));
			SysUtil.abort();	
	    }
	}
	
	public  static void verifyEmpty(CoreUIObject ui, KDMultiLangBox multLangBox) {
	    verifyEmpty(ui, null, multLangBox, null);
	}
	
	public static void verifyEmpty(CoreUIObject ui, String resourcePath, KDComboBox comboBox, String msg){
		if(!comboBox.isRequired())
			return;
	    Object content = comboBox.getSelectedItem();
	    if(content == null){
	        comboBox.requestFocus(true);
			MsgBox.showWarning(ui, (resourcePath==null || msg==null)?getMessage(comboBox):EASResource.getString(resourcePath, msg));
			SysUtil.abort();	
	    }
	}
	
	
	/**
	 *У�����ڿؼ��Ƿ�Ϊ�� 
	 */
	public static void verifyEmpty(CoreUIObject ui, String resourcePath, KDDatePicker datePicker, String msg){
		if(!datePicker.isRequired())
			return;
	    String content = datePicker.getText();
	    if(content == null || content.equals("")){
	        datePicker.requestFocus(true);
			MsgBox.showWarning(ui, (resourcePath==null || msg==null)?getMessage(datePicker):EASResource.getString(resourcePath, msg));
			SysUtil.abort();	
	    }
	}
	
	public  static void verifyEmpty(CoreUIObject ui, KDDatePicker datePicker) {
	    verifyEmpty(ui, null, datePicker, null);
	}
	
	/**
	 *У����ʼ�����뵽�����ڵıȽ�
	 */
	public static void verifyDateFromTo(CoreUIObject ui, String resourcePath, Date dateFrom, Date dateTo, String msg){

		dateFrom = DateTimeUtils.truncateDate(dateFrom);
		dateTo = DateTimeUtils.truncateDate(dateTo);
		if( dateFrom.compareTo(dateTo)==0 ){
			dateTo = FDCDateHelper.getNextDay(dateTo);
		}
		if( dateFrom.compareTo(dateTo)>0 ){
			MsgBox.showWarning(ui, EASResource.getString(resourcePath, msg));
			SysUtil.abort();
		}
	}

	public static void verifyEmpty(CoreUIObject ui, String resourcePath, KDFormattedTextField txtNumber, String msg) {
		if(!txtNumber.isRequired())
			return;
		Object  txt = txtNumber.getNumberValue();
		if (txt == null ) {
			txtNumber.requestFocus(true);
			MsgBox.showWarning(ui, (resourcePath==null || msg==null)?getMessage(txtNumber):EASResource.getString(resourcePath, msg));
			SysUtil.abort();	
		}		
	}
	
	public static void verifyEmpty(CoreUIObject ui, String resourcePath, KDTextArea txtArea, String msg) {
		if(!txtArea.isRequired())
			return;
		String  txt = txtArea.getText();
		if (txt == null||txt.trim().length()<=0) {
			txtArea.requestFocus(true);
			MsgBox.showWarning(ui, (resourcePath==null || msg==null)?getMessage(txtArea):EASResource.getString(resourcePath, msg));
			SysUtil.abort();	
		}		
	}
	
	public static void verifyEmpty(CoreUIObject ui, KDTextArea txtArea,String name){
		if(!txtArea.isRequired())
			return;
		String  txt = txtArea.getText();
		if (txt == null||txt.trim().length()<=0) {
			txtArea.requestFocus(true);
			MsgBox.showWarning(name+EASResource.getString(path, "CanNotBeNull"));
			SysUtil.abort();	
		}
	}
	
	public  static void verifyEmpty(CoreUIObject ui,  KDTextArea txtArea) {
	    verifyEmpty(ui, null, txtArea, null);
	}
	
	public  static void verifyEmpty(CoreUIObject ui, KDFormattedTextField txtNumber) {
	    verifyEmpty(ui, null, txtNumber, null);
	}
	
    /**
     * @param row
     */
    public static void checkStringLength(Component ui, String resourcePath,IRow row,
            String colName, int length) {
        String name = (String) row.getCell(colName).getValue();
        if (name != null && name.length() > length) {
            
//            MsgBox.showWarning(ui, name
//                    + EASResource.getString(resourcePath, 
//                            "StringLengthGreaterthan") + length);
            MsgBox.showWarning(ui,EASResource.getString(resourcePath, "StringLengthGreaterthan") + length +":" +"\n\n"+name);
            SysUtil.abort();
    
        }        
    }
    
    /**
     * �ж϶����Ƿ�Ϊ��
     */
	public static void verifyEmpty(CoreUIObject ui, String resourcePath, Object value, String msg) {		
		if (FDCHelper.isEmpty(value)){
			MsgBox.showWarning(ui, EASResource.getString(resourcePath, msg));
			SysUtil.abort();	
		}		
	}
	
	/**
	 * �ж϶����Ƿ�Ϊ��
	 * @param ui ��ǰUI
	 * @param resourcePath ��Դ·��
	 * @param value ����
	 * @param msg ��Դ������
	 * @param ctrlName �ؼ�����
	 */
	public static void verifyEmpty(CoreUIObject ui, String resourcePath, Object value, String msg, String ctrlName) {
		if (FDCHelper.isEmpty(value)){
			MsgBox.showWarning(ui, EASResource.getString(resourcePath, msg));
			setComponentFocus(ui, ctrlName);
			SysUtil.abort();	
		}		
	}
	
	/**
	 * ���ÿؼ��Ľ���
	 * @param ui ����UI
	 * @param componentName Ҫ���ý���Ŀؼ�����
	 */
	public static void setComponentFocus(JComponent ui, String componentName) {
    	Component[] comps = ui.getComponents();
    	if (comps != null && comps.length > 0)
        {
            for (int i = 0; i < comps.length; i++)
            {
                if (comps[i] instanceof KDPanel
                        || comps[i] instanceof KDScrollPane
                        || comps[i] instanceof KDSplitPane
                        || comps[i] instanceof KDTabbedPane
                        || comps[i] instanceof KDLabelContainer
                        || comps[i] instanceof KDContainer
                        || comps[i] instanceof KDPanel
                        || comps[i] instanceof JPanel)
                {
                    setComponentFocus((JComponent)comps[i], componentName);
                }
                else
                {
                	if(comps[i] != null && comps[i].getName() != null && comps[i].getName().equals(componentName)) {
        				comps[i].requestFocus();
        			}
                }
            }
        }
    }
	
    /**
     * �ж϶����Ƿ�Ϊ�ղ�ֵ��Ϊ0
     */
	public static void verifyEmptyAndNoZero(CoreUIObject ui, String resourcePath, Object value, String msg) {		
	    if (value == null ) {
			MsgBox.showWarning(ui, EASResource.getString(resourcePath, msg));
			SysUtil.abort();	
		}
	    if(value instanceof BigDecimal){
	        if(((BigDecimal)value).compareTo(FDCHelper.ZERO)==0){
	            MsgBox.showWarning(ui, EASResource.getString(resourcePath, msg));
				SysUtil.abort();    
	        }
	    }
	}
	
	public static void verifyEmptyAndNoZero(CoreUIObject ui, KDFormattedTextField txt) {
		
		verifyEmpty(ui, txt);
		
		BigDecimal value = txt.getBigDecimalValue();
		
		if (value.compareTo(FDCHelper.ZERO) == 0) {
			txt.requestFocus(true);
			String msg = getCompLabelText(txt) + EASResource.getString(path, "CanNotBeZero");
			MsgBox.showWarning(ui, msg);
			SysUtil.abort();
		}
	}
    
    /**
     * 
     * ������У�鿪ʼ���ڲ��ܴ��ڽ�������
     * @param ui
     * @param beginDate
     * @param endDate
     * @author:liupd
     * ����ʱ�䣺2006-1-12 <p>
     */
    public static void verifyBeginEndDateRel(CoreUIObject ui, Date beginDate, Date endDate) {
        if(beginDate != null && endDate != null && beginDate.after(endDate)) {
            MsgBox.showWarning(ui, EASResource.getString(path, "beginDateNotGreaterEndDate"));
            SysUtil.abort();
        }
    }

    
    /**
	 * 
	 * ������������Required �� true�Ŀؼ����в�����Ϊ�յļ���
	 * 
	 * @param ui
	 *            ��ǰUIʵ��
	 * @author:liupd ����ʱ�䣺2006-8-1
	 *               <p>
	 */
	public static void verifyRequire(CoreUIObject ui){
		Component[] comps = null;
		boolean isFocusPolicy=false;
		final FocusTraversalPolicy focusTraversalPolicy = ui.getFocusTraversalPolicy();
		if(focusTraversalPolicy instanceof com.kingdee.bos.ui.UIFocusTraversalPolicy){
			com.kingdee.bos.ui.UIFocusTraversalPolicy policy=(com.kingdee.bos.ui.UIFocusTraversalPolicy)focusTraversalPolicy;
			comps=policy.getComponents();
			
		}
		if(comps!=null){
			isFocusPolicy=true;
		}else{
			comps = ui.getComponents();
		}
		for(int i=0;i< comps.length; i++){
			Component comp = comps[i];
			if(!comp.isEnabled()) continue;
			if (isFocusPolicy||comp instanceof KDLabelContainer){
				JComponent editor =null;
				if(isFocusPolicy){
					editor=(JComponent)comp;
				}else{
					KDLabelContainer ct = (KDLabelContainer) comp;
					editor=ct.getBoundEditor();
				}
				// ��Щ���editor����Ϊ�գ���Զ��
				if (editor == null || !editor.isEnabled())
					continue;
				if (editor instanceof KDTextField){
					KDTextField txtEditor = (KDTextField) editor;
					if (txtEditor.isRequired()){
						FDCClientVerifyHelper.verifyEmpty(ui, txtEditor);
					}
				}
				else if (editor instanceof KDComboBox){
					KDComboBox txtEditor = (KDComboBox) editor;
					if (txtEditor.isRequired()){
						FDCClientVerifyHelper.verifyEmpty(ui, txtEditor);
					}
				}
				else if (editor instanceof KDDatePicker){
					KDDatePicker txtEditor = (KDDatePicker) editor;
					if (txtEditor.isRequired()){
						FDCClientVerifyHelper.verifyEmpty(ui, txtEditor);
					}
				}
				else if (editor instanceof KDBizPromptBox){
					KDBizPromptBox txtEditor = (KDBizPromptBox) editor;
					if (txtEditor.isRequired()){
						FDCClientVerifyHelper.verifyEmpty(ui, txtEditor);
					}
				}
				else if (editor instanceof KDFormattedTextField){
					KDFormattedTextField txtEditor = (KDFormattedTextField) editor;
					if (txtEditor.isRequired()){
						FDCClientVerifyHelper.verifyEmpty(ui, txtEditor);
					}
				}
				else if (editor instanceof KDBizMultiLangBox) {
					KDBizMultiLangBox txtEditor = (KDBizMultiLangBox) editor;
					if (txtEditor.isRequired()) {
						FDCClientVerifyHelper.verifyEmpty(ui, txtEditor);
					}
				}
				
			}
		}
		
	}

    
    /**
     * 
     * ������У��KDTable�е�BigDecimal Value�Ƿ���ϸ�����У�����
     * @param ui �������
     * @param table KDTable
     * @param rule У�����
     * @author:liupd
     * ����ʱ�䣺2006-4-6 <p>
     */
    public static void verifyTableMaxMinValue(CoreUIObject ui, KDTable table, FDCNumberValueVerifyRule rule) {
    	
    	int rowCount = table.getRowCount();
    	int colCount = table.getColumnCount();
    	
    	IRow headRow = table.getHeadRow(0);
    	
    	final String resPath = "com.kingdee.eas.fm.common.client.FMCommonClientResource";
    	final String resNameMax = "exceedMaxValue";
    	final String resNameMin = "lessMinValue";
    	
    	for (int i = 0; i < rowCount; i++) {
    		
			for (int j = 0; j < colCount; j++) {
				
				if(table.getColumn(j).getStyleAttributes().isHided()) {
					continue;
				}
				
				Object obj = table.getCell(i, j).getValue();
				
				if(obj != null && obj instanceof BigDecimal) {
					
					BigDecimal numValue = (BigDecimal)obj;
					
					String colCapital = (String)headRow.getCell(j).getValue();
					
					if(numValue.compareTo(rule.getMaxValue()) > 0) {
						String message = FDCClientHelper.formatMessage(resPath,
								resNameMax, new Object[] {
										String.valueOf(i + 1), colCapital, rule.getMaxValue() });
						MsgBox.showWarning(ui, message);
			            SysUtil.abort();
					}
					
					if(numValue.compareTo(rule.getMinValue()) < 0) {
						String message = FDCClientHelper.formatMessage(resPath,
								resNameMin, new Object[] {
										String.valueOf(i + 1), colCapital, rule.getMinValue() });
						MsgBox.showWarning(ui, message);
			            SysUtil.abort();
					}
				}
			}
		}
    }
    
    /**
     * �õ�������(����������)����Ҷ�ڵ�ؼ�(��״�ṹ��ƽ)
     * @author yangzk
     * @param ui
     * @return Set�е�Ԫ��������Component
     */
    private static Set getAllComponents(Container container)
	{
		Component[] components = container.getComponents();
		Set compSet = new HashSet();
		for (int i = 0, n = components.length; i < n; i++)
		{
			Component comp = components[i];
			compSet.add(comp);
			if (comp instanceof Container)
			{
				compSet.addAll(getAllComponents((Container) comp));
			}			
		}
		return compSet;
	}
    
    /**
	 * ��֤ui������required=true��Ҷ�ӽڵ�ؼ��Ƿ�Ϊ��
	 * 
	 * @author yangzk
	 * @param ui
	 *            ��У���ui
	 */
    public static void verifyUIControlEmpty(CoreUI ui)
    {
    	Set allLeafComponents = getAllComponents(ui);
    	Iterator it = allLeafComponents.iterator();
    	while(it.hasNext())
    	{
    		Component comp = (Component) it.next();
    		if(!(comp instanceof IKDTextComponent))
    		{
    			continue;
    		}
    		IKDTextComponent txtComp = (IKDTextComponent)comp;
    		if(!txtComp.isRequired())
    		{
    			continue;
    		}
    		if(comp.getName()==null){
    			continue ;
    		}
    		
    		if(comp instanceof KDTextField)
    		{
    			verifyEmpty(ui,(KDTextField)comp);
    		}
    		else if(comp instanceof KDBizPromptBox)
    		{
    			verifyEmpty(ui,(KDBizPromptBox)comp);
    		}
    		else if(comp instanceof KDComboBox)
    		{
    			verifyEmpty(ui,(KDComboBox)comp);
    		}
    		else if(comp instanceof KDMultiLangBox)
    		{
    			verifyEmpty(ui,(KDMultiLangBox)comp);
    		}
    		else if(comp instanceof KDDatePicker)
    		{
    			verifyEmpty(ui,(KDDatePicker)comp);
    		}
    		else if(comp instanceof KDFormattedTextField)
    		{
    			verifyEmpty(ui,(KDFormattedTextField)comp);
    		}
    		else if(comp instanceof KDTextArea)
    		{
    			verifyEmpty(ui,(KDTextArea)comp);
    		}
    		
    	}
    }
    
	/**
	 * ����Ƿ�Ϊ�գ���Ϊ�գ���msg����������ʾ��Ϣ
	 * 
	 * @author rd_owen_wen 2017-08-02
	 * @param datePicker
	 * @param msg
	 */
	static public void verifyEmpty(CoreUIObject ui, KDDatePicker datePicker, String msg) {
		if (com.kingdee.util.StringUtils.isEmpty(datePicker.getText())) {
			FDCMsgBox.showInfo(ui, msg);
			SysUtil.abort();
		}
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * ��֤���ͬһ�е�Ԫ��ֵ�Ƿ���ͬ
	 * 
	 * @param ui
	 *            UI����
	 * @param table
	 *            ���
	 * @param row
	 *            ��
	 * @param key
	 *            ����
	 * @param value
	 *            ��Ԫ��ֵ
	 * @param warning
	 *            ������Ϣ
	 * @param isSame
	 *            �Ƿ�Ҫ��ͬ
	 * @param attachListenersName
	 *            װ������� ��������
	 * @param detachListenersName
	 *            ��ж������ ��������
	 */
	public static void verifyTableCoulmnValueIsSame(CoreUIObject ui, KDTable table, IRow row, String key, Object value,
			String warning, boolean isSame, String attachListenersName, String detachListenersName) {
		if (null != value) {
			// ȡ�������е�Ԫ��ֵ�б�
			List costAccountList = FDCTableHelper.getOtherCellValueList(table, key, row);
			// ����������е��ظ�ֵ��Nullֵ
			FdcCollectionUtil.clearDuplicateAndNull(costAccountList);
			boolean flag = false;

			if (FdcCollectionUtil.isNotEmpty(costAccountList)) {
				// �����CoreBaseInfo���ͣ���Ƚ�id
				if (value instanceof CoreBaseInfo) {
					CoreBaseInfo info = (CoreBaseInfo) value;
					String id = info.getId().toString();
					List idList = FdcObjectCollectionUtil.parseStringIdList(costAccountList);

					if (isSame ^ idList.contains(id)) {
						flag = true;
					}
				}
				// ����ֱ�ӱȽ�ֵ
				else if (isSame ^ costAccountList.contains(value)) {
					flag = true;
				}
			}

			if (flag) {
				MsgBox.showWarning(ui, warning);

				// ��ж������
				FdcMethodUtil.invokeNoParameterMethod(ui, detachListenersName, true);
				// ��յ�Ԫ��ֵ
				row.getCell(key).setValue(null);
				// װ�������
				FdcMethodUtil.invokeNoParameterMethod(ui, attachListenersName, true);

				SysUtil.abort();
			}
		}
	}

	/**
	 * ��֤���ͬһ�е�Ԫ��ֵ�Ƿ���ͬ
	 * <p>
	 * 1��װ������� �������� ΪattachListeners<br>
	 * 2����ж������ �������� ΪdetachListeners<br>
	 * 
	 * @param ui
	 *            UI����
	 * @param table
	 *            ���
	 * @param row
	 *            ��
	 * @param key
	 *            ����
	 * @param value
	 *            ��Ԫ��ֵ
	 * @param warning
	 *            ������Ϣ
	 * @param isSame
	 *            �Ƿ�Ҫ��ͬ
	 */
	public static void verifyTableCoulmnValueIsSame(CoreUIObject ui, KDTable table, IRow row, String key, Object value,
			String warning, boolean isSame) {
		verifyTableCoulmnValueIsSame(ui, table, row, key, value, warning, isSame, "attachListeners", "detachListeners");
	}

	/**
	 * ��֤���ͬһ�е�Ԫ��ֵ�Ƿ���ͬ
	 * <p>
	 * 1��װ������� �������� ΪattachListeners<br>
	 * 2����ж������ �������� ΪdetachListeners<br>
	 * 3��������Ϣ ��ʽ Ϊ��ͷ������ + ������ͬ/��ͬ
	 * 
	 * @param ui
	 *            UI����
	 * @param table
	 *            ���
	 * @param row
	 *            ��
	 * @param key
	 *            ����
	 * @param value
	 *            ��Ԫ��ֵ
	 */
	public static void verifyTableCoulmnValueIsSame(CoreUIObject ui, KDTable table, IRow row, String key, Object value,
			boolean isSame) {
		// ȡ�ñ�ͷ������
		String headName = FDCTableHelper.getHeadName(table, key);

		String warning = null;
		if (isSame) {
			warning = headName + " ������ͬ";
		} else {
			warning = headName + " ���벻ͬ";
		}

		verifyTableCoulmnValueIsSame(ui, table, row, key, value, warning, isSame);
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * ��֤�����ֵ�Ƿ��ظ�
	 * 
	 * @param ui
	 *            UI����
	 * @param table
	 *            ���
	 */
	public static void verifyTableCoulmnValueDup(CoreUIObject ui, KDTable table) {
		if (FDCTableHelper.isEmpty(table)) {
			return;
		}
		String[] keyArr = FDCTableHelper.getColumnKeys(table);
		verifyTableCoulmnValueDup(ui, table, keyArr);
	}

	/**
	 * ��֤�����ֵ�Ƿ��ظ�
	 * 
	 * @param ui
	 *            UI����
	 * @param table
	 *            ���
	 * @param keyArr
	 *            ��������
	 */
	public static void verifyTableCoulmnValueDup(CoreUIObject ui, KDTable table, String[] keyArr) {
		if (FDCTableHelper.isEmpty(table) || FdcArrayUtil.isEmpty(keyArr)) {
			return;
		}

		String key = null;
		for (int i = 0, length = keyArr.length; i < length; i++) {
			key = keyArr[i];

			verifyTableCoulmnValueDup(ui, table, key);
		}
	}

	/**
	 * ��֤�����ֵ�Ƿ��ظ�
	 * 
	 * @param ui
	 *            UI����
	 * @param table
	 *            ���
	 * @param key
	 *            ����
	 */
	public static void verifyTableCoulmnValueDup(CoreUIObject ui, KDTable table, String key) {
		if (FDCTableHelper.isEmpty(table) || null == table.getColumn(key)) {
			return;
		}
		// ȡ�ñ�ͷ������
		String headName = FDCTableHelper.getHeadName(table, key);
		String warning = headName + "�����ظ�";
		verifyTableCoulmnValueDup(ui, table, key, warning);
	}

	/**
	 * ��֤�����ֵ�Ƿ��ظ�
	 * 
	 * @param ui
	 *            UI����
	 * @param table
	 *            ���
	 * @param key
	 *            ����
	 * @param warning
	 *            ������Ϣ
	 */
	public static void verifyTableCoulmnValueDup(CoreUIObject ui, KDTable table, String key, String warning) {
		if (FDCTableHelper.isEmpty(table) || null == table.getColumn(key)) {
			return;
		}
		Map<Integer, Object> valueMap = new HashMap<Integer, Object>();

		int rowIndex = -1;
		int colIndex = table.getColumnIndex(key);
		IRow row = null;
		ICell cell = null;
		Object value = null;
		for (int i = 0, rowCount = table.getRowCount(); i < rowCount; i++) {
			row = table.getRow(i);
			cell = row.getCell(key);
			value = cell.getValue();

			rowIndex = row.getRowIndex();
			if (!FDCHelper.isEmpty(value) && valueMap.containsValue(value)) {
				MsgBox.showWarning(ui, warning);
				table.getEditManager().editCellAt(rowIndex, colIndex);
				SysUtil.abort();
			}
			valueMap.put(rowIndex, value);
		}
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * ��֤�����ֵ�Ƿ��ظ�(����CoreBase����.id)
	 * 
	 * @param ui
	 *            UI����
	 * @param table
	 *            ���
	 * @param isCoreBaseObject
	 *            �Ƿ�CoreBase����
	 */
	public static void verifyTableCoulmnValueDup(CoreUIObject ui, KDTable table, boolean isCoreBaseObject) {
		if (FDCTableHelper.isEmpty(table)) {
			return;
		}
		String[] keyArr = FDCTableHelper.getColumnKeys(table);
		verifyTableCoulmnValueDup(ui, table, keyArr, isCoreBaseObject);
	}

	/**
	 * ��֤�����ֵ�Ƿ��ظ�(����CoreBase����.id)
	 * 
	 * @param ui
	 *            UI����
	 * @param table
	 *            ���
	 * @param keyArr
	 *            ��������
	 * @param isCoreBaseObject
	 *            �Ƿ�CoreBase����
	 */
	public static void verifyTableCoulmnValueDup(CoreUIObject ui, KDTable table, String[] keyArr,
			boolean isCoreBaseObject) {
		if (FDCTableHelper.isEmpty(table) || FdcArrayUtil.isEmpty(keyArr)) {
			return;
		}

		String key = null;
		for (int i = 0, length = keyArr.length; i < length; i++) {
			key = keyArr[i];

			verifyTableCoulmnValueDup(ui, table, key, isCoreBaseObject);
		}
	}

	/**
	 * ��֤�����ֵ�Ƿ��ظ�(����CoreBase����.id)
	 * 
	 * @param ui
	 *            UI����
	 * @param table
	 *            ���
	 * @param key
	 *            ����
	 * @param isCoreBaseObject
	 *            �Ƿ�CoreBase����
	 */
	public static void verifyTableCoulmnValueDup(CoreUIObject ui, KDTable table, String key, boolean isCoreBaseObject) {
		if (FDCTableHelper.isEmpty(table) || null == table.getColumn(key)) {
			return;
		}
		// ȡ�ñ�ͷ������
		String headName = FDCTableHelper.getHeadName(table, key);
		String warning = headName + "�����ظ�";
		verifyTableCoulmnValueDup(ui, table, key, warning, isCoreBaseObject);
	}

	/**
	 * ��֤�����ֵ�Ƿ��ظ�(����CoreBase����.id)
	 * 
	 * @param ui
	 *            UI����
	 * @param table
	 *            ���
	 * @param key
	 *            ����
	 * @param warning
	 *            ������Ϣ
	 * @param isCoreBaseObject
	 *            �Ƿ�CoreBase����
	 */
	public static void verifyTableCoulmnValueDup(CoreUIObject ui, KDTable table, String key, String warning,
			boolean isCoreBaseObject) {
		if (FDCTableHelper.isEmpty(table) || null == table.getColumn(key)) {
			return;
		}
		Map<Integer, Object> valueMap = new HashMap<Integer, Object>();

		int rowIndex = -1;
		int colIndex = table.getColumnIndex(key);
		IRow row = null;
		ICell cell = null;
		Object value = null;
		CoreBaseInfo coreBaseInfo = null;
		for (int i = 0, rowCount = table.getRowCount(); i < rowCount; i++) {
			row = table.getRow(i);
			cell = row.getCell(key);
			if (isCoreBaseObject) {
				coreBaseInfo = (CoreBaseInfo) cell.getValue();
				value = (null != coreBaseInfo) ? coreBaseInfo.getId() : null;
			} else {
				value = cell.getValue();
			}

			if (null == value) {
				continue;
			} else if (value instanceof String && FdcStringUtil.isBlank((String) value)) {
				continue;
			}

			rowIndex = row.getRowIndex();
			if (valueMap.containsValue(value)) {
				MsgBox.showWarning(ui, warning);
				table.getEditManager().editCellAt(rowIndex, colIndex);
				SysUtil.abort();
			}
			valueMap.put(rowIndex, value);
		}
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * ��֤�Ƿ����
	 * 
	 * @param ui
	 *            UI����
	 * @param kdfTextField1
	 *            ��ʽ�������1
	 * @param label1
	 *            ��ǩ1
	 * @param kdfTextField2
	 *            ��ʽ�������2
	 * @param label2
	 *            ��ǩ2
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 */
	public static void verifyIsGreaterThan(CoreUIObject ui, KDFormattedTextField kdfTextField1, String label1,
			KDFormattedTextField kdfTextField2, String label2, boolean isIgnoreNull) {
		FDCClientVerifyHelper.verifyMathOperate(ui, kdfTextField1, label1, kdfTextField2, label2, isIgnoreNull,
				CompareTypeEnum.GREATE);
	}

	/**
	 * ��֤�Ƿ�С��
	 * 
	 * @param ui
	 *            UI����
	 * @param kdfTextField1
	 *            ��ʽ�������1
	 * @param label1
	 *            ��ǩ1
	 * @param kdfTextField2
	 *            ��ʽ�������2
	 * @param label2
	 *            ��ǩ2
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 */
	public static void verifyIsLessThan(CoreUIObject ui, KDFormattedTextField kdfTextField1, String label1,
			KDFormattedTextField kdfTextField2, String label2, boolean isIgnoreNull) {
		FDCClientVerifyHelper.verifyMathOperate(ui, kdfTextField1, label1, kdfTextField2, label2, isIgnoreNull,
				CompareTypeEnum.LITTE);
	}

	/**
	 * ��֤�Ƿ�С��
	 * 
	 * @param ui
	 *            UI����
	 * @param kdfTextField1
	 *            ��ʽ�������1
	 * @param label1
	 *            ��ǩ1
	 * @param kdfTextField2
	 *            ��ʽ�������2
	 * @param label2
	 *            ��ǩ2
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 */
	public static void verifyIsEqual(CoreUIObject ui, KDFormattedTextField kdfTextField1, String label1,
			KDFormattedTextField kdfTextField2, String label2, boolean isIgnoreNull) {
		FDCClientVerifyHelper.verifyMathOperate(ui, kdfTextField1, label1, kdfTextField2, label2, isIgnoreNull,
				CompareTypeEnum.EQUAL);
	}

	/**
	 * ��֤�Ƿ����
	 * 
	 * @param ui
	 *            UI����
	 * @param kdfTextField1
	 *            ��ʽ�������1
	 * @param kdfTextField2
	 *            ��ʽ�������2
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 */
	public static void verifyIsGreaterThan(CoreUIObject ui, KDFormattedTextField kdfTextField1,
			KDFormattedTextField kdfTextField2, boolean isIgnoreNull) {
		String label1 = getCompLabelText(kdfTextField1);
		String label2 = getCompLabelText(kdfTextField2);

		verifyIsGreaterThan(ui, kdfTextField1, label1, kdfTextField2, label2, isIgnoreNull);
	}

	/**
	 * ��֤�Ƿ�С��
	 * 
	 * @param ui
	 *            UI����
	 * @param kdfTextField1
	 *            ��ʽ�������1
	 * @param kdfTextField2
	 *            ��ʽ�������2
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 */
	public static void verifyIsLessThan(CoreUIObject ui, KDFormattedTextField kdfTextField1,
			KDFormattedTextField kdfTextField2, boolean isIgnoreNull) {
		String label1 = getCompLabelText(kdfTextField1);
		String label2 = getCompLabelText(kdfTextField2);

		verifyIsLessThan(ui, kdfTextField1, label1, kdfTextField2, label2, isIgnoreNull);
	}

	/**
	 * ��֤�Ƿ�С��
	 * 
	 * @param ui
	 *            UI����
	 * @param kdfTextField1
	 *            ��ʽ�������1
	 * @param kdfTextField2
	 *            ��ʽ�������2
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 */
	public static void verifyIsEqual(CoreUIObject ui, KDFormattedTextField kdfTextField1,
			KDFormattedTextField kdfTextField2, boolean isIgnoreNull) {
		String label1 = getCompLabelText(kdfTextField1);
		String label2 = getCompLabelText(kdfTextField2);

		verifyIsEqual(ui, kdfTextField1, label1, kdfTextField2, label2, isIgnoreNull);
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * ��֤�Ƿ����
	 * 
	 * @param ui
	 *            UI����
	 * @param kdfTextField
	 *            ��ʽ�������
	 * @param label
	 *            ��ǩ
	 * @param compareValue
	 *            �Ƚ�ֵ
	 * @param compareLabel
	 *            �Ƚϱ�ǩ
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 */
	public static void verifyIsGreaterThan(CoreUIObject ui, KDFormattedTextField kdfTextField, String label,
			Object compareValue, String compareLabel, boolean isIgnoreNull) {
		FDCClientVerifyHelper.verifyMathOperate(ui, kdfTextField, label, compareValue, compareLabel, isIgnoreNull,
				CompareTypeEnum.GREATE);
	}

	/**
	 * ��֤�Ƿ�С��
	 * 
	 * @param ui
	 *            UI����
	 * @param kdfTextField
	 *            ��ʽ�������
	 * @param label
	 *            ��ǩ
	 * @param compareValue
	 *            �Ƚ�ֵ
	 * @param compareLabel
	 *            �Ƚϱ�ǩ
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 */
	public static void verifyIsLessThan(CoreUIObject ui, KDFormattedTextField kdfTextField, String label,
			Object compareValue, String compareLabel, boolean isIgnoreNull) {
		FDCClientVerifyHelper.verifyMathOperate(ui, kdfTextField, label, compareValue, compareLabel, isIgnoreNull,
				CompareTypeEnum.LITTE);
	}

	/**
	 * ��֤�Ƿ�С��
	 * 
	 * @param ui
	 *            UI����
	 * @param kdfTextField
	 *            ��ʽ�������
	 * @param label
	 *            ��ǩ
	 * @param compareValue
	 *            �Ƚ�ֵ
	 * @param compareLabel
	 *            �Ƚϱ�ǩ
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 */
	public static void verifyIsEqual(CoreUIObject ui, KDFormattedTextField kdfTextField, String label,
			Object compareValue, String compareLabel, boolean isIgnoreNull) {
		FDCClientVerifyHelper.verifyMathOperate(ui, kdfTextField, label, compareValue, compareLabel, isIgnoreNull,
				CompareTypeEnum.EQUAL);
	}

	/**
	 * ��֤�Ƿ����
	 * 
	 * @param ui
	 *            UI����
	 * @param kdfTextField
	 *            ��ʽ�������
	 * @param compareValue
	 *            �Ƚ�ֵ
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 */
	public static void verifyIsGreaterThan(CoreUIObject ui, KDFormattedTextField kdfTextField, Object compareValue,
			boolean isIgnoreNull) {
		String label = getCompLabelText(kdfTextField);
		String compareLabel = (null != compareValue) ? compareValue.toString() : null;

		verifyIsGreaterThan(ui, kdfTextField, label, compareValue, compareLabel, isIgnoreNull);
	}

	/**
	 * ��֤�Ƿ�С��
	 * 
	 * @param ui
	 *            UI����
	 * @param kdfTextField
	 *            ��ʽ�������
	 * @param compareValue
	 *            �Ƚ�ֵ
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 */
	public static void verifyIsLessThan(CoreUIObject ui, KDFormattedTextField kdfTextField, Object compareValue,
			boolean isIgnoreNull) {
		String label = getCompLabelText(kdfTextField);
		String compareLabel = (null != compareValue) ? compareValue.toString() : null;

		verifyIsLessThan(ui, kdfTextField, label, compareValue, compareLabel, isIgnoreNull);
	}

	/**
	 * ��֤�Ƿ�С��
	 * 
	 * @param ui
	 *            UI����
	 * @param kdfTextField
	 *            ��ʽ�������
	 * @param compareValue
	 *            �Ƚ�ֵ
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 */
	public static void verifyIsEqual(CoreUIObject ui, KDFormattedTextField kdfTextField, Object compareValue,
			boolean isIgnoreNull) {
		String label = getCompLabelText(kdfTextField);
		String compareLabel = (null != compareValue) ? compareValue.toString() : null;

		verifyIsEqual(ui, kdfTextField, label, compareValue, compareLabel, isIgnoreNull);
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * ��֤�Ƿ����
	 * 
	 * @param ui
	 *            UI����
	 * @param table
	 *            ���
	 * @param row
	 *            ��
	 * @param key
	 *            ����
	 * @param label
	 *            ��ǩ
	 * @param compareValue
	 *            �Ƚ�ֵ
	 * @param compareLabel
	 *            �Ƚϱ�ǩ
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 */
	public static void verifyIsGreaterThan(CoreUIObject ui, KDTable table, IRow row, String key, String label,
			Object compareValue, String compareLabel, boolean isIgnoreNull) {
		FDCClientVerifyHelper.verifyMathOperate(ui, table, row, key, label, compareValue, compareLabel, isIgnoreNull,
				CompareTypeEnum.GREATE);
	}

	/**
	 * ��֤�Ƿ�С��
	 * 
	 * @param ui
	 *            UI����
	 * @param table
	 *            ���
	 * @param row
	 *            ��
	 * @param key
	 *            ����
	 * @param label
	 *            ��ǩ
	 * @param compareValue
	 *            �Ƚ�ֵ
	 * @param compareLabel
	 *            �Ƚϱ�ǩ
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 */
	public static void verifyIsLessThan(CoreUIObject ui, KDTable table, IRow row, String key, String label,
			Object compareValue, String compareLabel, boolean isIgnoreNull) {
		FDCClientVerifyHelper.verifyMathOperate(ui, table, row, key, label, compareValue, compareLabel, isIgnoreNull,
				CompareTypeEnum.LITTE);
	}

	/**
	 * ��֤�Ƿ����
	 * 
	 * @param ui
	 *            UI����
	 * @param table
	 *            ���
	 * @param row
	 *            ��
	 * @param key
	 *            ����
	 * @param label
	 *            ��ǩ
	 * @param compareValue
	 *            �Ƚ�ֵ
	 * @param compareLabel
	 *            �Ƚϱ�ǩ
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 */
	public static void verifyIsEqual(CoreUIObject ui, KDTable table, IRow row, String key, String label,
			Object compareValue, String compareLabel, boolean isIgnoreNull) {
		FDCClientVerifyHelper.verifyMathOperate(ui, table, row, key, label, compareValue, compareLabel, isIgnoreNull,
				CompareTypeEnum.EQUAL);
	}

	/**
	 * ��֤�Ƿ����
	 * 
	 * @param ui
	 *            UI����
	 * @param table
	 *            ���
	 * @param row
	 *            ��
	 * @param key
	 *            ����
	 * @param compareValue
	 *            �Ƚ�ֵ
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 */
	public static void verifyIsGreaterThan(CoreUIObject ui, KDTable table, IRow row, String key, Object compareValue,
			boolean isIgnoreNull) {
		String label = FDCTableHelper.getHeadName(table, key);
		String compareLabel = (null != compareValue) ? compareValue.toString() : null;

		verifyIsGreaterThan(ui, table, row, key, label, compareValue, compareLabel, isIgnoreNull);
	}

	/**
	 * ��֤�Ƿ�С��
	 * 
	 * @param ui
	 *            UI����
	 * @param table
	 *            ���
	 * @param row
	 *            ��
	 * @param key
	 *            ����
	 * @param compareValue
	 *            �Ƚ�ֵ
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 */
	public static void verifyIsLessThan(CoreUIObject ui, KDTable table, IRow row, String key, Object compareValue,
			boolean isIgnoreNull) {
		String label = FDCTableHelper.getHeadName(table, key);
		String compareLabel = (null != compareValue) ? compareValue.toString() : null;

		verifyIsLessThan(ui, table, row, key, label, compareValue, compareLabel, isIgnoreNull);
	}

	/**
	 * ��֤�Ƿ����
	 * 
	 * @param ui
	 *            UI����
	 * @param table
	 *            ���
	 * @param row
	 *            ��
	 * @param key
	 *            ����
	 * @param compareValue
	 *            �Ƚ�ֵ
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 */
	public static void verifyIsEqual(CoreUIObject ui, KDTable table, IRow row, String key, Object compareValue,
			boolean isIgnoreNull) {
		String label = FDCTableHelper.getHeadName(table, key);
		String compareLabel = (null != compareValue) ? compareValue.toString() : null;

		verifyIsEqual(ui, table, row, key, label, compareValue, compareLabel, isIgnoreNull);
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * ��֤�Ƿ����
	 * 
	 * @param ui
	 *            UI����
	 * @param table
	 *            ���
	 * @param row
	 *            ��
	 * @param key1
	 *            ����1
	 * @param key2
	 *            ����2
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 */
	public static void verifyIsGreaterThan(CoreUIObject ui, KDTable table, IRow row, String key1, String key2,
			boolean isIgnoreNull) {
		String label1 = FDCTableHelper.getHeadName(table, key1);
		String label2 = FDCTableHelper.getHeadName(table, key2);

		verifyIsGreaterThan(ui, table, row, key1, label1, key2, label2, isIgnoreNull);
	}

	/**
	 * ��֤�Ƿ�С��
	 * 
	 * @param ui
	 *            UI����
	 * @param table
	 *            ���
	 * @param row
	 *            ��
	 * @param key1
	 *            ����1
	 * @param key2
	 *            ����2
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 */
	public static void verifyIsLessThan(CoreUIObject ui, KDTable table, IRow row, String key1, String key2,
			boolean isIgnoreNull) {
		String label1 = FDCTableHelper.getHeadName(table, key1);
		String label2 = FDCTableHelper.getHeadName(table, key2);

		verifyIsLessThan(ui, table, row, key1, label1, key2, label2, isIgnoreNull);
	}

	/**
	 * ��֤�Ƿ����
	 * 
	 * @param ui
	 *            UI����
	 * @param table
	 *            ���
	 * @param row
	 *            ��
	 * @param key1
	 *            ����1
	 * @param key2
	 *            ����2
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 */
	public static void verifyIsEqual(CoreUIObject ui, KDTable table, IRow row, String key1, String key2,
			boolean isIgnoreNull) {
		String label1 = FDCTableHelper.getHeadName(table, key1);
		String label2 = FDCTableHelper.getHeadName(table, key2);

		verifyIsEqual(ui, table, row, key1, label1, key2, label2, isIgnoreNull);
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * ��֤�Ƿ����
	 * 
	 * @param ui
	 *            UI����
	 * @param table
	 *            ���
	 * @param key1
	 *            ����1
	 * @param label1
	 *            ��ǩ1
	 * @param key2
	 *            ����2
	 * @param label2
	 *            ��ǩ2
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 */
	public static void verifyIsGreaterThan(CoreUIObject ui, KDTable table, String key1, String label1, String key2,
			String label2, boolean isIgnoreNull) {
		int colIndex1 = table.getColumnIndex(key1);
		int colIndex2 = table.getColumnIndex(key2);

		IRow row = null;
		for (int i = 0, rowCount = table.getRowCount(); i < rowCount; i++) {
			row = table.getRow(i);

			FDCClientVerifyHelper.verifyMathOperate(ui, table, row, colIndex1, label1, colIndex2, label2, isIgnoreNull,
					CompareTypeEnum.GREATE);
		}
	}

	/**
	 * ��֤�Ƿ�С��
	 * 
	 * @param ui
	 *            UI����
	 * @param table
	 *            ���
	 * @param key1
	 *            ����1
	 * @param label1
	 *            ��ǩ1
	 * @param key2
	 *            ����2
	 * @param label2
	 *            ��ǩ2
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 */
	public static void verifyIsLessThan(CoreUIObject ui, KDTable table, String key1, String label1, String key2,
			String label2, boolean isIgnoreNull) {
		int colIndex1 = table.getColumnIndex(key1);
		int colIndex2 = table.getColumnIndex(key2);

		IRow row = null;
		for (int i = 0, rowCount = table.getRowCount(); i < rowCount; i++) {
			row = table.getRow(i);

			FDCClientVerifyHelper.verifyMathOperate(ui, table, row, colIndex1, label1, colIndex2, label2, isIgnoreNull,
					CompareTypeEnum.LITTE);
		}
	}

	/**
	 * ��֤�Ƿ����
	 * 
	 * @param ui
	 *            UI����
	 * @param table
	 *            ���
	 * @param key1
	 *            ����1
	 * @param label1
	 *            ��ǩ1
	 * @param key2
	 *            ����2
	 * @param label2
	 *            ��ǩ2
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 */
	public static void verifyIsEqual(CoreUIObject ui, KDTable table, String key1, String label1, String key2,
			String label2, boolean isIgnoreNull) {
		int colIndex1 = table.getColumnIndex(key1);
		int colIndex2 = table.getColumnIndex(key2);

		IRow row = null;
		for (int i = 0, rowCount = table.getRowCount(); i < rowCount; i++) {
			row = table.getRow(i);

			FDCClientVerifyHelper.verifyMathOperate(ui, table, row, colIndex1, label1, colIndex2, label2, isIgnoreNull,
					CompareTypeEnum.EQUAL);
		}
	}

	/**
	 * ��֤�Ƿ����
	 * 
	 * @param ui
	 *            UI����
	 * @param table
	 *            ���
	 * @param key1
	 *            ����1
	 * @param key2
	 *            ����2
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 */
	public static void verifyIsGreaterThan(CoreUIObject ui, KDTable table, String key1, String key2,
			boolean isIgnoreNull) {
		String label1 = FDCTableHelper.getHeadName(table, key1);
		String label2 = FDCTableHelper.getHeadName(table, key2);

		verifyIsGreaterThan(ui, table, key1, label1, key2, label2, isIgnoreNull);
	}

	/**
	 * ��֤�Ƿ�С��
	 * 
	 * @param ui
	 *            UI����
	 * @param table
	 *            ���
	 * @param key1
	 *            ����1
	 * @param key2
	 *            ����2
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 */
	public static void verifyIsLessThan(CoreUIObject ui, KDTable table, String key1, String key2, boolean isIgnoreNull) {
		String label1 = FDCTableHelper.getHeadName(table, key1);
		String label2 = FDCTableHelper.getHeadName(table, key2);

		verifyIsLessThan(ui, table, key1, label1, key2, label2, isIgnoreNull);
	}

	/**
	 * ��֤�Ƿ����
	 * 
	 * @param ui
	 *            UI����
	 * @param table
	 *            ���
	 * @param key1
	 *            ����1
	 * @param key2
	 *            ����2
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 */
	public static void verifyIsEqual(CoreUIObject ui, KDTable table, String key1, String key2, boolean isIgnoreNull) {
		String label1 = FDCTableHelper.getHeadName(table, key1);
		String label2 = FDCTableHelper.getHeadName(table, key2);

		verifyIsEqual(ui, table, key1, label1, key2, label2, isIgnoreNull);
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * ��֤�Ƿ����
	 * 
	 * @param ui
	 *            UI����
	 * @param table
	 *            ���
	 * @param key
	 *            ����
	 * @param label
	 *            ��ǩ
	 * @param compareValue
	 *            �Ƚ�ֵ
	 * @param compareLabel
	 *            �Ƚϱ�ǩ
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 */
	public static void verifyIsGreaterThan(CoreUIObject ui, KDTable table, String key, String label,
			Object compareValue, String compareLabel, boolean isIgnoreNull) {
		int colIndex = table.getColumnIndex(key);

		IRow row = null;
		for (int i = 0, rowCount = table.getRowCount(); i < rowCount; i++) {
			row = table.getRow(i);

			FDCClientVerifyHelper.verifyMathOperate(ui, table, row, colIndex, label, compareValue, compareLabel,
					isIgnoreNull, CompareTypeEnum.GREATE);
		}
	}

	/**
	 * ��֤�Ƿ�С��
	 * 
	 * @param ui
	 *            UI����
	 * @param table
	 *            ���
	 * @param key
	 *            ����
	 * @param label
	 *            ��ǩ
	 * @param compareValue
	 *            �Ƚ�ֵ
	 * @param compareLabel
	 *            �Ƚϱ�ǩ
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 */
	public static void verifyIsLessThan(CoreUIObject ui, KDTable table, String key, String label, Object compareValue,
			String compareLabel, boolean isIgnoreNull) {
		int colIndex = table.getColumnIndex(key);

		IRow row = null;
		for (int i = 0, rowCount = table.getRowCount(); i < rowCount; i++) {
			row = table.getRow(i);

			FDCClientVerifyHelper.verifyMathOperate(ui, table, row, colIndex, label, compareValue, compareLabel,
					isIgnoreNull, CompareTypeEnum.LITTE);
		}
	}

	/**
	 * ��֤�Ƿ����
	 * 
	 * @param ui
	 *            UI����
	 * @param table
	 *            ���
	 * @param key
	 *            ����
	 * @param label
	 *            ��ǩ
	 * @param compareValue
	 *            �Ƚ�ֵ
	 * @param compareLabel
	 *            �Ƚϱ�ǩ
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 */
	public static void verifyIsEqual(CoreUIObject ui, KDTable table, String key, String label, Object compareValue,
			String compareLabel, boolean isIgnoreNull) {
		int colIndex = table.getColumnIndex(key);

		IRow row = null;
		for (int i = 0, rowCount = table.getRowCount(); i < rowCount; i++) {
			row = table.getRow(i);

			FDCClientVerifyHelper.verifyMathOperate(ui, table, row, colIndex, label, compareValue, compareLabel,
					isIgnoreNull, CompareTypeEnum.EQUAL);
		}
	}

	/**
	 * ��֤�Ƿ����
	 * 
	 * @param ui
	 *            UI����
	 * @param table
	 *            ���
	 * @param key
	 *            ����
	 * @param compareValue
	 *            �Ƚ�ֵ
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 */
	public static void verifyIsGreaterThan(CoreUIObject ui, KDTable table, String key, Object compareValue,
			boolean isIgnoreNull) {
		String label = FDCTableHelper.getHeadName(table, key);
		String compareLabel = (null != compareValue) ? compareValue.toString() : null;

		verifyIsGreaterThan(ui, table, key, label, compareValue, compareLabel, isIgnoreNull);
	}

	/**
	 * ��֤�Ƿ�С��
	 * 
	 * @param ui
	 *            UI����
	 * @param table
	 *            ���
	 * @param key
	 *            ����
	 * @param compareValue
	 *            �Ƚ�ֵ
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 */
	public static void verifyIsLessThan(CoreUIObject ui, KDTable table, String key, Object compareValue,
			boolean isIgnoreNull) {
		String label = FDCTableHelper.getHeadName(table, key);
		String compareLabel = (null != compareValue) ? compareValue.toString() : null;

		verifyIsLessThan(ui, table, key, label, compareValue, compareLabel, isIgnoreNull);
	}

	/**
	 * ��֤�Ƿ����
	 * 
	 * @param ui
	 *            UI����
	 * @param table
	 *            ���
	 * @param key
	 *            ����
	 * @param compareValue
	 *            �Ƚ�ֵ
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 */
	public static void verifyIsEqual(CoreUIObject ui, KDTable table, String key, Object compareValue,
			boolean isIgnoreNull) {
		String label = FDCTableHelper.getHeadName(table, key);
		String compareLabel = (null != compareValue) ? compareValue.toString() : null;

		verifyIsEqual(ui, table, key, label, compareValue, compareLabel, isIgnoreNull);
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * ��֤��������ֵ
	 * 
	 * @param ui
	 *            UI����
	 * @param kdfTextField1
	 *            ��ʽ�������1
	 * @param label1
	 *            ��ǩ1
	 * @param kdfTextField2
	 *            ��ʽ�������2
	 * @param label2
	 *            ��ǩ2
	 * @param compareValue
	 *            �Ƚ�ֵ
	 * @param compareLabel
	 *            �Ƚϱ�ǩ
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 * @param mathOperateEnum
	 *            ��������ö��
	 * @param compareTypeEnum
	 *            �Ƚ�����ö��
	 */
	public static void verifyMathOperate(CoreUIObject ui, KDFormattedTextField kdfTextField1, String label1,
			KDFormattedTextField kdfTextField2, String label2, Object compareValue, String compareLabel,
			boolean isIgnoreNull, MathOperateEnum mathOperateEnum, CompareTypeEnum compareTypeEnum) {
		boolean flag = false;

		BigDecimal value1 = kdfTextField1.getBigDecimalValue();
		BigDecimal value2 = kdfTextField2.getBigDecimalValue();
		BigDecimal value = null;

		if (isIgnoreNull && (null == value1 || null == value2)) {
			return;
		}

		if (MathOperateEnum.ADD.equals(mathOperateEnum)) {
			value = FDCNumberHelper.add(value1, value2);
		} else if (MathOperateEnum.SUBTRACT.equals(mathOperateEnum)) {
			value = FDCNumberHelper.subtract(value1, value2);
		} else if (MathOperateEnum.MULTIPLY.equals(mathOperateEnum)) {
			value = FDCNumberHelper.multiply(value1, value2, 20);
		} else if (MathOperateEnum.DIVIDE.equals(mathOperateEnum)) {
			value = FDCNumberHelper.divide(value1, value2, 10);
		}

		if (CompareTypeEnum.GREATE.equals(compareTypeEnum)) {
			flag = FDCNumberHelper.isGreaterThan(value, compareValue);
		} else if (CompareTypeEnum.LITTE.equals(compareTypeEnum)) {
			flag = FDCNumberHelper.isLessThan(value, compareValue);
		} else if (CompareTypeEnum.EQUAL.equals(compareTypeEnum)) {
			flag = FDCNumberHelper.isEqual(value, compareValue);
		}

		if (flag) {
			String operateAlias = mathOperateEnum.getAlias();
			String compareAlias = compareTypeEnum.getAlias();
			String msg = "(" + label1 + " " + operateAlias + " " + label2 + ") " + compareAlias + " " + compareLabel;

			kdfTextField1.requestFocus(true);
			MsgBox.showWarning(ui, msg);
			SysUtil.abort();
		}
	}

	/**
	 * ��֤��������ֵ
	 * 
	 * @param ui
	 *            UI����
	 * @param kdfTextField1
	 *            ��ʽ�������1
	 * @param kdfTextField2
	 *            ��ʽ�������2
	 * @param compareValue
	 *            �Ƚ�ֵ
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 * @param mathOperateEnum
	 *            ��������ö��
	 * @param compareTypeEnum
	 *            �Ƚ�����ö��
	 */
	public static void verifyMathOperate(CoreUIObject ui, KDFormattedTextField kdfTextField1,
			KDFormattedTextField kdfTextField2, Object compareValue, boolean isIgnoreNull,
			MathOperateEnum mathOperateEnum, CompareTypeEnum compareTypeEnum) {
		String label1 = getCompLabelText(kdfTextField1);
		String label2 = getCompLabelText(kdfTextField1);
		String compareLabel = (null != compareValue) ? compareValue.toString() : null;

		verifyMathOperate(ui, kdfTextField1, label1, kdfTextField2, label2, compareValue, compareLabel, isIgnoreNull,
				mathOperateEnum, compareTypeEnum);
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * ��֤��������ֵ
	 * 
	 * @param ui
	 *            UI����
	 * @param table
	 *            ���
	 * @param row
	 *            ��
	 * @param key1
	 *            ����1
	 * @param label1
	 *            ��ǩ1
	 * @param key2
	 *            ����2
	 * @param label2
	 *            ��ǩ2
	 * @param compareValue
	 *            �Ƚ�ֵ
	 * @param compareLabel
	 *            �Ƚϱ�ǩ
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 * @param mathOperateEnum
	 *            ��������ö��
	 * @param compareTypeEnum
	 *            �Ƚ�����ö��
	 */
	public static void verifyMathOperate(CoreUIObject ui, KDTable table, IRow row, String key1, String label1,
			String key2, String label2, Object compareValue, String compareLabel, boolean isIgnoreNull,
			MathOperateEnum mathOperateEnum, CompareTypeEnum compareTypeEnum) {
		boolean flag = false;

		Object value1 = row.getCell(key1).getValue();
		Object value2 = row.getCell(key2).getValue();
		Object value = null;

		if (isIgnoreNull && (null == value1 || null == value2)) {
			return;
		}

		if (MathOperateEnum.ADD.equals(mathOperateEnum)) {
			value = FDCNumberHelper.add(value1, value2);
		} else if (MathOperateEnum.SUBTRACT.equals(mathOperateEnum)) {
			value = FDCNumberHelper.subtract(value1, value2);
		} else if (MathOperateEnum.MULTIPLY.equals(mathOperateEnum)) {
			value = FDCNumberHelper.multiply(value1, value2, 20);
		} else if (MathOperateEnum.DIVIDE.equals(mathOperateEnum)) {
			value = FDCNumberHelper.divide(value1, value2, 10);
		}

		if (CompareTypeEnum.GREATE.equals(compareTypeEnum)) {
			flag = FDCNumberHelper.isGreaterThan(value, compareValue);
		} else if (CompareTypeEnum.LITTE.equals(compareTypeEnum)) {
			flag = FDCNumberHelper.isLessThan(value, compareValue);
		} else if (CompareTypeEnum.EQUAL.equals(compareTypeEnum)) {
			flag = FDCNumberHelper.isEqual(value, compareValue);
		}

		if (flag) {
			String operateAlias = mathOperateEnum.getAlias();
			String compareAlias = compareTypeEnum.getAlias();
			String msg = "(" + label1 + " " + operateAlias + " " + label2 + ") " + compareAlias + " " + compareLabel;

			int rowIndex = row.getRowIndex();
			int colIndex = table.getColumnIndex(key1);
			if (row.getCell(key1).getStyleAttributes().isLocked()) {
				colIndex = table.getColumnIndex(key2);
			}

			KDTEditManager editManager = table.getEditManager();
			editManager.editCellAt(rowIndex, colIndex);
			MsgBox.showWarning(ui, msg);
			SysUtil.abort();
		}

	}

	/**
	 * ��֤��������ֵ
	 * 
	 * @param ui
	 *            UI����
	 * @param table
	 *            ���
	 * @param key1
	 *            ����1
	 * @param label1
	 *            ��ǩ1
	 * @param key2
	 *            ����2
	 * @param label2
	 *            ��ǩ2
	 * @param compareValue
	 *            �Ƚ�ֵ
	 * @param compareLabel
	 *            �Ƚϱ�ǩ
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 * @param mathOperateEnum
	 *            ��������ö��
	 * @param compareTypeEnum
	 *            �Ƚ�����ö��
	 */
	public static void verifyMathOperate(CoreUIObject ui, KDTable table, String key1, String label1, String key2,
			String label2, Object compareValue, String compareLabel, boolean isIgnoreNull,
			MathOperateEnum mathOperateEnum, CompareTypeEnum compareTypeEnum) {
		boolean flag = false;

		IRow row = null;
		for (int i = 0, rowCount = table.getRowCount(); i < rowCount; i++) {
			row = table.getRow(i);
			flag = FDCClientHelper.isGreaterThan(ui, table, row, key1, key2, isIgnoreNull);

			if (flag) {
				verifyMathOperate(ui, table, row, key1, label1, key2, label2, compareValue, compareLabel, isIgnoreNull,
						mathOperateEnum, compareTypeEnum);
			}
		}
	}

	/**
	 * ��֤��������ֵ
	 * 
	 * @param ui
	 *            UI����
	 * @param table
	 *            ���
	 * @param row
	 *            ��
	 * @param key1
	 *            ����1
	 * @param key2
	 *            ����2
	 * @param compareValue
	 *            �Ƚ�ֵ
	 * @param compareLabel
	 *            �Ƚϱ�ǩ
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 * @param mathOperateEnum
	 *            ��������ö��
	 * @param compareTypeEnum
	 *            �Ƚ�����ö��
	 */
	public static void verifyMathOperate(CoreUIObject ui, KDTable table, IRow row, String key1, String key2,
			Object compareValue, boolean isIgnoreNull, MathOperateEnum mathOperateEnum, CompareTypeEnum compareTypeEnum) {
		String label1 = FDCTableHelper.getHeadName(table, key1);
		String label2 = FDCTableHelper.getHeadName(table, key2);
		String compareLabel = (null != compareValue) ? compareValue.toString() : null;

		verifyMathOperate(ui, table, row, key1, label1, key2, label2, compareValue, compareLabel, isIgnoreNull,
				mathOperateEnum, compareTypeEnum);
	}

	/**
	 * ��֤��������ֵ
	 * 
	 * @param ui
	 *            UI����
	 * @param key1
	 *            ����1
	 * @param key2
	 *            ����2
	 * @param compareValue
	 *            �Ƚ�ֵ
	 * @param compareLabel
	 *            �Ƚϱ�ǩ
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 * @param mathOperateEnum
	 *            ��������ö��
	 * @param compareTypeEnum
	 *            �Ƚ�����ö��
	 */
	public static void verifyMathOperate(CoreUIObject ui, KDTable table, String key1, String key2, Object compareValue,
			boolean isIgnoreNull, MathOperateEnum mathOperateEnum, CompareTypeEnum compareTypeEnum) {
		String label1 = FDCTableHelper.getHeadName(table, key1);
		String label2 = FDCTableHelper.getHeadName(table, key2);
		String compareLabel = (null != compareValue) ? compareValue.toString() : null;

		verifyMathOperate(ui, table, key1, label1, key2, label2, compareValue, compareLabel, isIgnoreNull,
				mathOperateEnum, compareTypeEnum);
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * ��֤��������ֵ
	 * 
	 * @param ui
	 *            UI����
	 * @param kdfTextField1
	 *            ��ʽ�������1
	 * @param label1
	 *            ��ǩ1
	 * @param kdfTextField2
	 *            ��ʽ�������2
	 * @param label2
	 *            ��ǩ2
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 * @param compareTypeEnum
	 *            �Ƚ�����ö��
	 */
	public static void verifyMathOperate(CoreUIObject ui, KDFormattedTextField kdfTextField1, String label1,
			KDFormattedTextField kdfTextField2, String label2, boolean isIgnoreNull, CompareTypeEnum compareTypeEnum) {
		boolean flag = false;

		BigDecimal value1 = kdfTextField1.getBigDecimalValue();
		BigDecimal value2 = kdfTextField2.getBigDecimalValue();

		if (isIgnoreNull && (null == value1 || null == value2)) {
			return;
		}

		if (CompareTypeEnum.GREATE.equals(compareTypeEnum)) {
			flag = FDCNumberHelper.isGreaterThan(value1, value2);
		} else if (CompareTypeEnum.LITTE.equals(compareTypeEnum)) {
			flag = FDCNumberHelper.isLessThan(value1, value2);
		} else if (CompareTypeEnum.EQUAL.equals(compareTypeEnum)) {
			flag = FDCNumberHelper.isEqual(value1, value2);
		}

		if (flag) {
			String compareAlias = compareTypeEnum.getAlias();
			String msg = label1 + " " + compareAlias + " " + label2;

			if (kdfTextField1.isEnabled()) {
				kdfTextField1.requestFocus(true);
			} else {
				kdfTextField2.requestFocus(true);
			}

			MsgBox.showWarning(ui, msg);
			SysUtil.abort();
		}
	}

	/**
	 * ��֤��������ֵ
	 * 
	 * @param ui
	 *            UI����
	 * @param kdfTextField1
	 *            ��ʽ�������1
	 * @param kdfTextField2
	 *            ��ʽ�������2
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 * @param compareTypeEnum
	 *            �Ƚ�����ö��
	 */
	public static void verifyMathOperate(CoreUIObject ui, KDFormattedTextField kdfTextField1,
			KDFormattedTextField kdfTextField2, boolean isIgnoreNull, CompareTypeEnum compareTypeEnum) {
		String label1 = getCompLabelText(kdfTextField1);
		String label2 = getCompLabelText(kdfTextField1);

		verifyMathOperate(ui, kdfTextField1, label1, kdfTextField2, label2, isIgnoreNull, compareTypeEnum);
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * ��֤��������ֵ
	 * 
	 * @param ui
	 *            UI����
	 * @param kdfTextField
	 *            ��ʽ�������
	 * @param label
	 *            ��ǩ
	 * @param compareValue
	 *            �Ƚ�ֵ
	 * @param compareLabel
	 *            �Ƚϱ�ǩ
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 * @param compareTypeEnum
	 *            �Ƚ�����ö��
	 */
	public static void verifyMathOperate(CoreUIObject ui, KDFormattedTextField kdfTextField, String label,
			Object compareValue, String compareLabel, boolean isIgnoreNull, CompareTypeEnum compareTypeEnum) {
		boolean flag = false;

		BigDecimal value = kdfTextField.getBigDecimalValue();

		if (isIgnoreNull && (null == value || null == compareValue)) {
			return;
		}

		if (CompareTypeEnum.GREATE.equals(compareTypeEnum)) {
			flag = FDCNumberHelper.isGreaterThan(value, compareValue);
		} else if (CompareTypeEnum.LITTE.equals(compareTypeEnum)) {
			flag = FDCNumberHelper.isLessThan(value, compareValue);
		} else if (CompareTypeEnum.EQUAL.equals(compareTypeEnum)) {
			flag = FDCNumberHelper.isEqual(value, compareValue);
		}

		if (flag) {
			String compareAlias = compareTypeEnum.getAlias();
			String msg = label + " " + compareAlias + " " + compareLabel;

			kdfTextField.requestFocus(true);

			MsgBox.showWarning(ui, msg);
			SysUtil.abort();
		}
	}

	/**
	 * ��֤��������ֵ
	 * 
	 * @param ui
	 *            UI����
	 * @param kdfTextField
	 *            ��ʽ�������
	 * @param compareValue
	 *            �Ƚ�ֵ
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 * @param compareTypeEnum
	 *            �Ƚ�����ö��
	 */
	public static void verifyMathOperate(CoreUIObject ui, KDFormattedTextField kdfTextField, Object compareValue,
			boolean isIgnoreNull, CompareTypeEnum compareTypeEnum) {
		String label = getCompLabelText(kdfTextField);
		String compareLabel = getCompLabelText(kdfTextField);

		verifyMathOperate(ui, kdfTextField, label, compareValue, compareLabel, isIgnoreNull, compareTypeEnum);
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * ��֤��������ֵ
	 * 
	 * @param ui
	 *            UI����
	 * @param table
	 *            ���
	 * @param row
	 *            ��
	 * @param colIndex1
	 *            ������1
	 * @param label1
	 *            ��ǩ1
	 * @param colIndex2
	 *            ������2
	 * @param label2
	 *            ��ǩ2
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 * @param compareTypeEnum
	 *            �Ƚ�����ö��
	 */
	private static void verifyMathOperate(CoreUIObject ui, KDTable table, IRow row, int colIndex1, String label1,
			int colIndex2, String label2, boolean isIgnoreNull, CompareTypeEnum compareTypeEnum) {
		boolean flag = false;

		Object value1 = row.getCell(colIndex1).getValue();
		Object value2 = row.getCell(colIndex2).getValue();

		if (isIgnoreNull && (null == value1 || null == value2)) {
			return;
		}

		if (CompareTypeEnum.GREATE.equals(compareTypeEnum)) {
			flag = FDCNumberHelper.isGreaterThan(value1, value2);
		} else if (CompareTypeEnum.LITTE.equals(compareTypeEnum)) {
			flag = FDCNumberHelper.isLessThan(value1, value2);
		} else if (CompareTypeEnum.EQUAL.equals(compareTypeEnum)) {
			flag = FDCNumberHelper.isEqual(value1, value2);
		}

		if (flag) {
			KDTEditManager editManager = table.getEditManager();
			String compareAlias = compareTypeEnum.getAlias();
			String msg = label1 + " " + compareAlias + " " + label2;

			int rowIndex = row.getRowIndex();
			int colIndex = colIndex1;
			int rs = editManager.editCellAt(rowIndex, colIndex);
			// ������
			if (rs == KDTEditManager.CELL_LOCKED) {
				colIndex = colIndex2;
				editManager.editCellAt(rowIndex, colIndex);
			}

			MsgBox.showWarning(ui, msg);
			SysUtil.abort();
		}
	}

	/**
	 * ��֤��������
	 * 
	 * @param ui
	 *            UI����
	 * @param table
	 *            ���
	 * @param row
	 *            ��
	 * @param colIndex
	 *            ������
	 * @param label
	 *            ��ǩ
	 * @param compareValue
	 *            �Ƚ�ֵ
	 * @param compareLabel
	 *            �Ƚϱ�ǩ
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 * @param compareTypeEnum
	 *            �Ƚ�����ö��
	 */
	private static void verifyMathOperate(CoreUIObject ui, KDTable table, IRow row, int colIndex, String label,
			Object compareValue, String compareLabel, boolean isIgnoreNull, CompareTypeEnum compareTypeEnum) {
		boolean flag = false;

		Object value = row.getCell(colIndex).getValue();

		if (isIgnoreNull && (null == value || null == compareValue)) {
			return;
		}

		if (CompareTypeEnum.GREATE.equals(compareTypeEnum)) {
			flag = FDCNumberHelper.isGreaterThan(value, compareValue);
		} else if (CompareTypeEnum.LITTE.equals(compareTypeEnum)) {
			flag = FDCNumberHelper.isLessThan(value, compareValue);
		} else if (CompareTypeEnum.EQUAL.equals(compareTypeEnum)) {
			flag = FDCNumberHelper.isEqual(value, compareValue);
		}

		if (flag) {
			KDTEditManager editManager = table.getEditManager();
			String compareAlias = compareTypeEnum.getAlias();
			String msg = label + " " + compareAlias + " " + compareLabel;

			int rowIndex = row.getRowIndex();
			editManager.editCellAt(rowIndex, colIndex);

			MsgBox.showWarning(ui, msg);
			SysUtil.abort();
		}
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * ��֤��������ֵ
	 * 
	 * @param ui
	 *            UI����
	 * @param table
	 *            ���
	 * @param row
	 *            ��
	 * @param key1
	 *            ����1
	 * @param label1
	 *            ��ǩ1
	 * @param key2
	 *            ����2
	 * @param label2
	 *            ��ǩ2
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 * @param compareTypeEnum
	 *            �Ƚ�����ö��
	 */
	public static void verifyMathOperate(CoreUIObject ui, KDTable table, IRow row, String key1, String label1,
			String key2, String label2, boolean isIgnoreNull, CompareTypeEnum compareTypeEnum) {
		int colIndex1 = row.getCell(key1).getColumnIndex();
		int colIndex2 = row.getCell(key2).getColumnIndex();

		verifyMathOperate(ui, table, row, colIndex1, label1, colIndex2, label2, isIgnoreNull, compareTypeEnum);
	}

	/**
	 * ��֤��������ֵ
	 * 
	 * @param ui
	 *            UI����
	 * @param table
	 *            ���
	 * @param key1
	 *            ����1
	 * @param label1
	 *            ��ǩ1
	 * @param key2
	 *            ����2
	 * @param label2
	 *            ��ǩ2
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 * @param compareTypeEnum
	 *            �Ƚ�����ö��
	 */
	public static void verifyMathOperate(CoreUIObject ui, KDTable table, String key1, String label1, String key2,
			String label2, boolean isIgnoreNull, CompareTypeEnum compareTypeEnum) {
		int colIndex1 = table.getColumnIndex(key1);
		int colIndex2 = table.getColumnIndex(key2);

		IRow row = null;
		for (int i = 0, rowCount = table.getRowCount(); i < rowCount; i++) {
			row = table.getRow(i);

			verifyMathOperate(ui, table, row, colIndex1, label1, colIndex2, label2, isIgnoreNull, compareTypeEnum);
		}
	}

	/**
	 * ��֤��������ֵ
	 * 
	 * @param ui
	 *            UI����
	 * @param table
	 *            ���
	 * @param row
	 *            ��
	 * @param key1
	 *            ����1
	 * @param key2
	 *            ����2
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 * @param compareTypeEnum
	 *            �Ƚ�����ö��
	 */
	public static void verifyMathOperate(CoreUIObject ui, KDTable table, IRow row, String key1, String key2,
			boolean isIgnoreNull, CompareTypeEnum compareTypeEnum) {
		String label1 = FDCTableHelper.getHeadName(table, key1);
		String label2 = FDCTableHelper.getHeadName(table, key2);

		verifyMathOperate(ui, table, row, key1, label1, key2, label2, isIgnoreNull, compareTypeEnum);
	}

	/**
	 * ��֤��������ֵ
	 * 
	 * @param ui
	 *            UI����
	 * @param key1
	 *            ����1
	 * @param key2
	 *            ����2
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 * @param compareTypeEnum
	 *            �Ƚ�����ö��
	 */
	public static void verifyMathOperate(CoreUIObject ui, KDTable table, String key1, String key2,
			boolean isIgnoreNull, CompareTypeEnum compareTypeEnum) {
		String label1 = FDCTableHelper.getHeadName(table, key1);
		String label2 = FDCTableHelper.getHeadName(table, key2);

		verifyMathOperate(ui, table, key1, label1, key2, label2, isIgnoreNull, compareTypeEnum);
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * ��֤��������ֵ
	 * 
	 * @param ui
	 *            UI����
	 * @param table
	 *            ���
	 * @param row
	 *            ��
	 * @param key
	 *            ����
	 * @param label
	 *            ��ǩ
	 * @param compareValue
	 *            �Ƚ�ֵ
	 * @param compareLabel
	 *            �Ƚϱ�ǩ
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 * @param compareTypeEnum
	 *            �Ƚ�����ö��
	 */
	public static void verifyMathOperate(CoreUIObject ui, KDTable table, IRow row, String key, String label,
			Object compareValue, String compareLabel, boolean isIgnoreNull, CompareTypeEnum compareTypeEnum) {
		int colIndex = row.getCell(key).getColumnIndex();

		verifyMathOperate(ui, table, row, colIndex, label, compareValue, compareLabel, isIgnoreNull, compareTypeEnum);
	}

	/**
	 * ��֤��������ֵ
	 * 
	 * @param ui
	 *            UI����
	 * @param table
	 *            ���
	 * @param key
	 *            ����
	 * @param label
	 *            ��ǩ
	 * @param compareValue
	 *            �Ƚ�ֵ
	 * @param compareLabel
	 *            �Ƚϱ�ǩ
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 * @param compareTypeEnum
	 *            �Ƚ�����ö��
	 */
	public static void verifyMathOperate(CoreUIObject ui, KDTable table, String key, String label, Object compareValue,
			String compareLabel, boolean isIgnoreNull, CompareTypeEnum compareTypeEnum) {
		int colIndex = table.getColumnIndex(key);

		IRow row = null;
		for (int i = 0, rowCount = table.getRowCount(); i < rowCount; i++) {
			row = table.getRow(i);

			verifyMathOperate(ui, table, row, colIndex, label, compareValue, compareLabel, isIgnoreNull,
					compareTypeEnum);
		}
	}

	/**
	 * ��֤��������ֵ
	 * 
	 * @param ui
	 *            UI����
	 * @param table
	 *            ���
	 * @param row
	 *            ��
	 * @param key
	 *            ����
	 * @param compareValue
	 *            �Ƚ�ֵ
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 * @param compareTypeEnum
	 *            �Ƚ�����ö��
	 */
	public static void verifyMathOperate(CoreUIObject ui, KDTable table, IRow row, String key, Object compareValue,
			boolean isIgnoreNull, CompareTypeEnum compareTypeEnum) {
		String label = FDCTableHelper.getHeadName(table, key);
		String compareLabel = (null != compareValue) ? compareValue.toString() : null;

		verifyMathOperate(ui, table, row, key, label, compareValue, compareLabel, isIgnoreNull, compareTypeEnum);
	}

	/**
	 * ��֤��������ֵ
	 * 
	 * @param ui
	 *            UI����
	 * @param key
	 *            ����
	 * @param compareValue
	 *            �Ƚ�ֵ
	 * @param ignoreNull
	 *            �Ƿ���Կ�ֵ
	 * @param compareTypeEnum
	 *            �Ƚ�����ö��
	 */
	public static void verifyMathOperate(CoreUIObject ui, KDTable table, String key, Object compareValue,
			boolean isIgnoreNull, CompareTypeEnum compareTypeEnum) {
		String label = FDCTableHelper.getHeadName(table, key);
		String compareLabel = (null != compareValue) ? compareValue.toString() : null;

		verifyMathOperate(ui, table, key, label, compareValue, compareLabel, isIgnoreNull, compareTypeEnum);
	}

	/**
	 * ��鵱ǰ����Ƿ�ѡ����
	 * 
	 * @param comp
	 * @param table
	 */
	public static void checkSelected(Component comp, KDTable table) {
		if (table.getRowCount() == 0 || table.getSelectManager().size() == 0) {
			MsgBox.showWarning(comp, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
			SysUtil.abort();
		}
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////
}
