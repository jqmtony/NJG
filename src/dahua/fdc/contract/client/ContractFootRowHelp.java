/*
 * @(#)ContractFootRowHelp.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.fdc.contract.client;

import java.math.BigDecimal;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;

/**
 * ����: ���ú�ͬ�б�ĺϼ���
 * @author keyan_zhao  date:2012-8-21
 * @version EAS6.1
 */
public class ContractFootRowHelp {
	private String[] footColumns; // ��ͳ�Ƶ���
	private KDTable table;
	private String columnKey; // �Ƿ�Ҫ�ӵ��ϼ����У��ɴ��е�ֵ����

	public ContractFootRowHelp(String[] cols, KDTable tab, String columnKey) {
		footColumns = cols;
		table = tab;
		this.columnKey = columnKey;
	}

	/**
	 * ���������úϼ���  <br>
	 * <b>ע�⣺�÷������table����ȫ��ɨ�裬��ζ�Ż�ʵģʽȡ�������������ܴ�ʱһ��ȫ��ȡ�������������⡣<b>
	 * @Author��keyan_zhao
	 * @CreateTime��2012-8-21
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
