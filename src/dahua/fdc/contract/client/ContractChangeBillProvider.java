package com.kingdee.eas.fdc.contract.client;

import com.kingdee.bos.ctrl.reportone.r1.print.data.R1PrintDataSource;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.fdc.basedata.util.FDCNoteDataProvider;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class ContractChangeBillProvider extends FDCNoteDataProvider {


	public IRowSet getMainBillRowSet(R1PrintDataSource ds) throws Exception {
		return super.getMainBillRowSet(ds);
	}

	private IMetaDataPK attchQuery;

	public ContractChangeBillProvider(String billId, IMetaDataPK mainQuery,IMetaDataPK attchQuery) {
		super(billId, mainQuery);
		this.attchQuery=attchQuery;
	}

	public IRowSet getOtherSubRowSet(R1PrintDataSource ds) throws Exception {
		if ("ATTCH".equals(ds.getId().toUpperCase()))// 假设主数据源名称为mainbill
        {
            //返加主数据源的结果集
        	return  getAttchRowSet(ds);
        }
		return super.getMainBillRowSet(ds);
	}
	
	public IRowSet getAttchRowSet(R1PrintDataSource ds) {
		IRowSet iRowSet = null;
        try {
        	IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(attchQuery);
            exec.option().isAutoTranslateEnum= true;
            EntityViewInfo ev = new EntityViewInfo();
            FilterInfo filter = new FilterInfo();
            filter.getFilterItems().add(new FilterItemInfo("boAttchAsso.boID", billId, CompareType.EQUALS));
            ev.setFilter(filter);
            exec.setObjectView(ev);
            iRowSet = exec.executeQuery();	
            iRowSet.beforeFirst();
		} catch (Exception e) {
			e.printStackTrace();
			SysUtil.abort();
		}
		return iRowSet;
	}

}
