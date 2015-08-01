package com.kingdee.eas.fdc.contract.client;

import com.kingdee.bos.ctrl.reportone.r1.print.data.R1PrintDataSource;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.eas.fdc.basedata.util.FDCNoteDataProvider;
import com.kingdee.jdbc.rowset.IRowSet;

public class CompensationBillPrintProvider extends FDCNoteDataProvider {

	public CompensationBillPrintProvider(String billId, IMetaDataPK mainQuery) {
		super(billId, mainQuery);
		// TODO Auto-generated constructor stub
	}
	public IRowSet getOtherSubRowSet(R1PrintDataSource ds) throws Exception {
		return super.getMainBillRowSet(ds);
	}
}
