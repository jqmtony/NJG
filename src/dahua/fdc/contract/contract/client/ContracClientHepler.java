package com.kingdee.eas.fdc.contract.client;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;

public class ContracClientHepler 
{
    /**
     * 汇总
     * @param table
     */
    public static void setUnionData(KDTable table,List sumCols,SumRowBase base){
    	for(int i=0;i<table.getRowCount();i++){
    		IRow row=table.getRow(i);
    		if(row.getTreeLevel()==0){
    			_setUnionSubRow(table, row, sumCols,base);
    		}
    	}
    }
    
    private static void _setUnionSubRow(KDTable table,IRow row,List sumCols,SumRowBase base) {
		//非成本科目行
    	int level=row.getTreeLevel();
    	if(base.isLeaf(row)){
    		return;
    	}
		List subRows=new ArrayList();
		for(int i=row.getRowIndex()+1;i<table.getRowCount();i++){
			IRow tmpRow=table.getRow(i);
			if(tmpRow.getTreeLevel()==level+1){
				_setUnionSubRow(table,tmpRow,sumCols,base);
				subRows.add(tmpRow);
			}
			if(tmpRow.getTreeLevel()<=level){
				break;
			}
		}
		sumRow(row, subRows, sumCols);
    }
    /**
	 * 将subRows按cols列汇总到row
	 * @param row
	 * @param subRows
	 * @param cols
	 */
    public static void sumRow(IRow row,List subRows,List cols){
		if(cols.size()==0){
			return;
		}
		for(Iterator iter=cols.iterator();iter.hasNext();){
			//先清空再汇总
			row.getCell((String)iter.next()).setValue(null);
		}

		for(Iterator iter=subRows.iterator();iter.hasNext();){
			IRow tmpRow=(IRow)iter.next();
			for(Iterator iter2=cols.iterator();iter2.hasNext();){
				String key=(String)iter2.next();
				row.getCell(key).setValue(FDCNumberHelper.add(row.getCell(key).getValue(), tmpRow.getCell(key).getValue()));
			}
		}
	}
    
    /**
     * 
     * 汇总递归基
     * @author xiaohong_shi
     *
     */
    public static abstract class SumRowBase{
    	abstract boolean isLeaf(IRow row);
    }
}
