/*
 * @(#)FDCZeroFilterParse.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.fdc.basedata.util;

import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IUserCellDisplayParser;

/**
 * 描述: 如果单元格内容为0或0.0或0.00，则不显示<p>
 * @author owen_wen  date:2011-11-16
 * @version EAS6.1
 */
public class FDCFilterZeroParser  implements IUserCellDisplayParser{

	/**
	 * @see com.kingdee.bos.ctrl.kdf.table.IUserCellDisplayParser#parse(int, int, com.kingdee.bos.ctrl.kdf.table.ICell, java.lang.Object)
	 */
	public Object parse(int rowIndex, int colIndex, ICell cell, Object value) {
		if (value == null)
			return value;
		String strValue = value.toString();
		if ("0".equals(strValue) || "0.0".equals(strValue) || "0.00".equals(strValue)) {
			return "";
		}
		return value;
	}

}
