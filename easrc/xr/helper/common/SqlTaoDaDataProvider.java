package com.kingdee.eas.xr.helper.common;

import java.text.SimpleDateFormat;
import java.util.List;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.kdf.data.datasource.DSParam;
import com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.jdbc.rowset.IRowSet;

public class SqlTaoDaDataProvider implements BOSQueryDelegate {
	private String fid;
	private String sql;
	private List listFid;

	public SqlTaoDaDataProvider() {
	}
	public SqlTaoDaDataProvider(String sql) {
		this.fid = fid;
		this.sql = sql;
	}
	

	
	public IRowSet execute(BOSQueryDataSource ds) {
		retrieveParameterFromDataSourceParams(ds);
		String str = ds.getID(); // 得到打印数据源query的名称
		IRowSet rs = null;
		FDCSQLBuilder builder = new FDCSQLBuilder();
		StringBuffer sbsql = new StringBuffer();
		if(paramBillIdValue != null){
			Object billInfo;
			try {
				billInfo = (Object) DynamicObjectFactory.getRemoteInstance().getValue(new ObjectUuidPK(paramBillIdValue.toString()).getObjectType(),new ObjectUuidPK(paramBillIdValue.toString()));
				
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		builder.setPrepareStatementSql(sql.toString());
		try {
			rs = builder.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	private void retrieveParameterFromDataSourceParams(BOSQueryDataSource ds)
    {
		List params = ds.getParams();
		if(params != null && params.size() > 0)
        {
			for(int i = 0; i < params.size(); i++)
            {
				DSParam param = (DSParam)params.get(i);
				if("id".equalsIgnoreCase(param.getColName()) && param.getValue() != null && param.getValue().getValue() != null)
					paramBillIdValue = param.getValue().getValue();
            }
        }
    }
	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}
	
	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
	
	public List getListFid() {
		return listFid;
	}

	public void setListFid(List listFid) {
		this.listFid = listFid;
	}
	private Object paramBillIdValue;
}
