/*
 * @(#)ContractFootRowHelp.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.fdc.contract.client;

import java.math.BigDecimal;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;

/**
 * 描述: 设置合同列表的合计行
 * @author keyan_zhao  date:2012-8-21
 * @version EAS6.1
 */
public class ContractFootRowHelp {
	private String[] footColumns; // 待统计的列
	private KDTable table;
	private String columnKey; // 是否要加到合计行中，由此列的值决定

	public ContractFootRowHelp(String[] cols, KDTable tab, String columnKey) {
		footColumns = cols;
		table = tab;
		this.columnKey = columnKey;
	}

	/**
	 * 描述：设置合计行  <br>
	 * <b>注意：该方法会对table进行全行扫描，意味着会实模式取数，当数据量很大时一次全部取出会有性能问题。<b>
	 * @Author：keyan_zhao
	 * @CreateTime：2012-8-21
	 */
	public void setFootRow() {
		int columnsSize = footColumns.length;
		BigDecimal[] amouts = new BigDecimal[columnsSize];
		if (columnsSize > 0 && table.getRowCount() > 0) {
			for (int j = 0; j < table.getRowCount(); j++) {
				IRow row = table.getRow(j);
				if (row.getCell(columnKey).getValue() == null || ((Boolean) row.getCell(columnKey).getValue()).booleanValue()) {
					for (int i = 0; i < columnsSize; i++) {
						amouts[i] = FDCHelper.add(amouts[i], row.getCell(footColumns[i]).getValue());
					}
				}
			}
		}
		for (int i = 0; i < columnsSize; i++) {
			FDCTableHelper.generateFootRow(table).getCell(footColumns[i]).setValue(amouts[i]);
		}
	}

}
