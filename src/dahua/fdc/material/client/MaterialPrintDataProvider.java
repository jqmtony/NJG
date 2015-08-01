package com.kingdee.eas.fdc.material.client;

import com.kingdee.bos.ctrl.reportone.r1.print.data.R1PrintDataSource;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.eas.fdc.basedata.util.FDCNoteDataProvider;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * ������ϸ�����������ṩ��
 * @author liangliang_ye
 *
 */
public class MaterialPrintDataProvider extends FDCNoteDataProvider {

	public MaterialPrintDataProvider(String billId, IMetaDataPK mainQuery) {
		super(billId, mainQuery);
	}

	public IRowSet getOtherSubRowSet(R1PrintDataSource ds) throws Exception {
		return super.getMainBillRowSet(ds);
	}

}