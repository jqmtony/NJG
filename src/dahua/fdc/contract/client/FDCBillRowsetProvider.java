package com.kingdee.eas.fdc.contract.client;

import java.sql.Types;

import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.jdbc.rowset.impl.ColInfo;
import com.kingdee.jdbc.rowset.impl.DynamicRowSet;

public class FDCBillRowsetProvider implements BOSQueryDelegate{

	static public final String[] col = new String[] {
		"number",
		"curProject.name"
	};
	
	private com.kingdee.eas.fdc.contract.PayRequestBillInfo fdcBill = null;
	
	public FDCBillRowsetProvider ( FDCBillInfo editData){
		fdcBill = (com.kingdee.eas.fdc.contract.PayRequestBillInfo)editData;
	}
	
	public IRowSet execute(BOSQueryDataSource ds) {
		
		
		
		int colCount = col.length;
		DynamicRowSet drs = null;
		
		try {
			drs = new DynamicRowSet(colCount);
			for (int i = 0; i < colCount; i++) {
				ColInfo ci = new ColInfo();
				ci.colType = Types.VARCHAR;
				ci.columnName = col[i];
				ci.nullable = 1;
				drs.setColInfo(i + 1, ci);
			}

			drs.beforeFirst();
			
			drs.moveToInsertRow();
			//在此把数据传递给实现类，drs.updateString(key,value)  key 指的是套打模板中定义的字段编码，Value指的是当前单据的属性值
			drs.updateString("number",fdcBill.getNumber());
			drs.updateString("curProject.name",fdcBill.getCurProject().getDisplayName());
			
//			drs.updateString("projectName", "");
			drs.insertRow();
			
			drs.beforeFirst();
		} catch (Exception ex) {
			ex.printStackTrace();
			SysUtil.abort();
		}
		
		return drs;		
	}

}
