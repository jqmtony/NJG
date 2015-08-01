package com.kingdee.eas.fdc.basedata.client;

import java.util.Properties;

import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.eas.base.common.IDCRegister;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;

/**
 * 房地产基础资料类型验证
 * 
 * @author jianxing_zhou
 * 
 */
class FDCBaseTypeValidator {

	// 是否启用验证
	private static final boolean isValidate = true;
	// 编码所在列
	private static final String numberKey = "number";
	// 也可能是长编码
	private static final String longNumberKey = "longNumber";
	// 名称所在列
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
//TODO		此方法有问题必须通过服务器端进行校验,暂时通过传入ID做判断,待年后再改 2008/1/28 sxhong
		String number = txtNumber.getText();
		String name = bizName.getSelectedItemData().toString();
		for (int i = 0, count = table.getRowCount(); i < count; i++) {
			// 编码
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
				// 相同类型下名称不能重复
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
		//TODO 此方法有问题必须通过服务器端进行校验,暂时通过传入ID做判断 待年后再改 2008/1/28 sxhong
		String number = txtNumber.getText();
		String name = bizName.getSelectedItemData().toString();
		for (int i = 0, count = table.getRowCount(); i < count; i++) {
			// 编码
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
				// 名称
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
//		// 编码是否为空
//		String number = txtNumber.getText();
//		if (number == null || number.trim().length() < 1) {
//			txtNumber.requestFocus(true);
//			throw new FDCBasedataException(FDCBasedataException.NUMBER_IS_EMPTY);
//		}
//		// 名称是否为空
//		String name = bizName.getSelectedItem().toString();
//		if (name == null || name.trim().length() < 1) {
//			bizName.requestFocus(true);
//			throw new FDCBasedataException(FDCBasedataException.NAME_IS_EMPTY);
//		}
//
//	}
	/**
	 * 判断是否为系统预设数据
	 */
	public static boolean isSystemDefaultData(String id){
		
		return false;
	}
	
	public static class SystemDefaultData{
		Properties p=new Properties();
//		InputStream in=
	}
	
}
