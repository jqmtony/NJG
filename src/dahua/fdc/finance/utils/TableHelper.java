package com.kingdee.eas.fdc.finance.utils;

import java.math.BigDecimal;

import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.eas.util.client.EASResource;

public class TableHelper {
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
	 * 给指定table数字列求和
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
}
