package com.kingdee.eas.fdc.basedata.client;

import java.util.Properties;

import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.eas.base.common.IDCRegister;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;

/**
 * ���ز���������������֤
 * 
 * @author jianxing_zhou
 * 
 */
class FDCBaseTypeValidator {

	// �Ƿ�������֤
	private static final boolean isValidate = true;
	// ����������
	private static final String numberKey = "number";
	// Ҳ�����ǳ�����
	private static final String longNumberKey = "longNumber";
	// ����������
	private static final String nameKey = "name";

	/**
	 * 
	 * @param table
	 * @param txtNumber
	 * @param bizName
	 * @param type
	 * @param typeCol
	 * @param id TODO
	 * @param numberCol
	 * @param nameCol
	 * @throws FDCBasedataException
	 */
	public static void validate(KDTable table, KDTextField txtNumber, KDBizMultiLangBox bizName, String type, String typeCol, String id) throws FDCBasedataException {
		if (!isValidate)
			return;
//TODO		�˷������������ͨ���������˽���У��,��ʱͨ������ID���ж�,������ٸ� 2008/1/28 sxhong
		String number = txtNumber.getText();
		String name = bizName.getSelectedItemData().toString();
		for (int i = 0, count = table.getRowCount(); i < count; i++) {
			// ����
			ICell cell = table.getCell(i, numberKey);
			if (cell == null)
				cell = table.getCell(i, longNumberKey);
			ICell IDcell = table.getCell(i, "id");
			if(IDcell==null||IDcell.getValue()==null){
				return;
			}
			String thisId=IDcell.getValue().toString();
			if(id==null||!id.equals(thisId)){
				if (number.equalsIgnoreCase((String) cell.getValue())) {
					txtNumber.requestFocus(true);
					throw new FDCBasedataException(FDCBasedataException.NUMBER_ALREADY_EXIST, new Object[] { number });
				}
				// ��ͬ���������Ʋ����ظ�
				if (name.equalsIgnoreCase((String) table.getCell(i, nameKey).getValue()) && type.equals(table.getCell(i, typeCol).getValue())) {
					bizName.requestFocus(true);
					throw new FDCBasedataException(FDCBasedataException.NAME_ALREADY_EXIST, new Object[] { name });
				}

			}
		}
	}

	public static void validate(KDTable table, KDTextField txtNumber, KDBizMultiLangBox bizName, String id) throws FDCBasedataException {
		if (!isValidate)
			return;
		//TODO �˷������������ͨ���������˽���У��,��ʱͨ������ID���ж� ������ٸ� 2008/1/28 sxhong
		String number = txtNumber.getText();
		String name = bizName.getSelectedItemData().toString();
		for (int i = 0, count = table.getRowCount(); i < count; i++) {
			// ����
			ICell cell = table.getCell(i, numberKey);
			ICell IDcell = table.getCell(i, "id");
			if(IDcell==null||IDcell.getValue()==null){
				return;
			}
			String thisId=IDcell.getValue().toString();
			if (cell == null)
				cell = table.getCell(i, longNumberKey);
			
			if(id==null||!id.equals(thisId)){
				if(cell != null && cell.getValue() != null){
					if (number.equalsIgnoreCase((String) cell.getValue())) {
						txtNumber.requestFocus(true);
						throw new FDCBasedataException(FDCBasedataException.NUMBER_ALREADY_EXIST, new Object[] { number });
					}
				}
				// ����
				if(table.getCell(i, nameKey) != null && table.getCell(i, nameKey).getValue() != null){
					if (name.equalsIgnoreCase((String) table.getCell(i, nameKey).getValue())) {
						bizName.requestFocus(true);
						throw new FDCBasedataException(FDCBasedataException.NAME_ALREADY_EXIST, new Object[] { name });
					}
				}
			}

		}
	}

//	public static void validate(KDTextField txtNumber, KDBizMultiLangBox bizName) throws FDCBasedataException {
//		if (!isValidate)
//			return;
//		// �����Ƿ�Ϊ��
//		String number = txtNumber.getText();
//		if (number == null || number.trim().length() < 1) {
//			txtNumber.requestFocus(true);
//			throw new FDCBasedataException(FDCBasedataException.NUMBER_IS_EMPTY);
//		}
//		// �����Ƿ�Ϊ��
//		String name = bizName.getSelectedItem().toString();
//		if (name == null || name.trim().length() < 1) {
//			bizName.requestFocus(true);
//			throw new FDCBasedataException(FDCBasedataException.NAME_IS_EMPTY);
//		}
//
//	}
	/**
	 * �ж��Ƿ�ΪϵͳԤ������
	 */
	public static boolean isSystemDefaultData(String id){
		
		return false;
	}
	
	public static class SystemDefaultData{
		Properties p=new Properties();
//		InputStream in=
	}
	
}
