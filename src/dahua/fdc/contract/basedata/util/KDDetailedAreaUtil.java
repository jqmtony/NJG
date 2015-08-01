package com.kingdee.eas.fdc.basedata.util;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import com.kingdee.bos.ctrl.kdf.table.BasicView;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;

public class KDDetailedAreaUtil {

	/**
	 * �����ڵ�ǰѡ�е�Ԫ����������ϸ�ؼ���ʾֵ
	 * 
	 * @param owner
	 * @param table
	 */
	public static void showDialog(JComponent owner, KDTable table) {
		int rowIndex = table.getSelectManager().getActiveRowIndex();
		int colIndex = table.getSelectManager().getActiveColumnIndex();

		ICell cell = table.getCell(rowIndex, colIndex);
		if (cell.getValue() == null) {
			return;
		}
		BasicView view = table.getViewManager().getView(5);
		Point p = view.getLocationOnScreen();
		Rectangle rect = view.getCellRectangle(rowIndex, colIndex);
		KDDetailedAreaDialog dialog;
		Window parent = SwingUtilities.getWindowAncestor(owner);
		if (parent instanceof Dialog) {
			dialog = new KDDetailedAreaDialog((Dialog) parent, 250, 200, true);
		} else if (parent instanceof Frame) {
			dialog = new KDDetailedAreaDialog((Frame) parent, 250, 200, true);
		} else {
			dialog = new KDDetailedAreaDialog(true);
		}
		dialog.setData((String) cell.getValue());
		dialog.setPRENTE_X(p.x + rect.x + rect.width);
		dialog.setPRENTE_Y(p.y + rect.y);
		dialog.setMaxLength(500);
		dialog.setEditable(false);

		dialog.show();
	}
	
	public static void showDialog(JComponent owner, KDTable table, int X, int Y, int len) {
		int rowIndex = table.getSelectManager().getActiveRowIndex();
		int colIndex = table.getSelectManager().getActiveColumnIndex();

		ICell cell = table.getCell(rowIndex, colIndex);
		if (cell.getValue() == null) {
			return;
		}
		BasicView view = table.getViewManager().getView(5);
		Point p = view.getLocationOnScreen();
		Rectangle rect = view.getCellRectangle(rowIndex, colIndex);
		KDDetailedAreaDialog dialog;
		Window parent = SwingUtilities.getWindowAncestor(owner);
		if (parent instanceof Dialog) {
			dialog = new KDDetailedAreaDialog((Dialog) parent, X, Y, true);
		} else if (parent instanceof Frame) {
			dialog = new KDDetailedAreaDialog((Frame) parent, X, Y, true);
		} else {
			dialog = new KDDetailedAreaDialog(true);
		}
		dialog.setData((String) cell.getValue());
		dialog.setPRENTE_X(p.x + rect.x + rect.width);
		dialog.setPRENTE_Y(p.y + rect.y);
		dialog.setMaxLength(len);
		dialog.setEditable(false);

		dialog.show();
	}

	/**
	 * ���õ�Ԫ���ܻ���
	 * 
	 * @param cell
	 */
	public static void setWrapFalse(ICell cell) {
		if (cell != null) {
			cell.getStyleAttributes().setWrapText(false);
		}
	}

	/**
	 * ���ñ��ĳ�в���KDDetailedArea��Ϊ�༭��
	 * 
	 * @param table
	 * @param colName
	 *            ����
	 * @param isRequired
	 *            �Ƿ��¼
	 * @param maxLength
	 *            �����¼�볤��
	 * @author owen_wen 2013-01-18
	 */
	public static void setDetailCellEditor(KDTable table, String colName, boolean isRequired, int maxLength) {
		KDDetailedArea area = new KDDetailedArea(280, 250);
		area.setRequired(isRequired);
		area.setEnabled(true);
		area.setMaxLength(maxLength);
		table.getColumn(colName).setEditor(new KDTDefaultCellEditor(area));
	}
}
