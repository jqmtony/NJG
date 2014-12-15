package com.kingdee.eas.xr.helper.common;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.kdf.data.datasource.DSParam;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.framework.print.IMultiapproveDataFilter;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.jdbc.rowset.IRowSet;

public class QueryTaoDaDataProvider implements com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate {

	private Set ids = null;
	private String queryPath = null;
	private IMetaDataPK mpk = new MetaDataPK("");
	
	public QueryTaoDaDataProvider() {
	}
	
	public QueryTaoDaDataProvider(List id, String queryPath) {
		this.ids = new HashSet(id);
		this.queryPath = queryPath;
	}
	
	public IRowSet execute(BOSQueryDataSource ds) {
		retrieveParameterFromDataSourceParams(ds);
		IRowSet rs = null;
		if(queryPath == null || "".equals(queryPath)) {
			return null;
		}else {
			mpk = new MetaDataPK(queryPath);
		}
		try {
			//动态查询执行器
			IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(mpk);
			//自动转换查询中的枚举字段
			exec.option().isAutoTranslateEnum = true;
			//构造过来条件
			EntityViewInfo ev = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			if(paramBillIdValue != null)
				filter.getFilterItems().add(new FilterItemInfo("id", paramBillIdValue, CompareType.EQUALS));
			else
				filter.getFilterItems().add(new FilterItemInfo("id", ids, CompareType.INCLUDE));
			ev.setFilter(filter);
			exec.setObjectView(ev);
			String str = ds.getID(); // 得到打印数据源query的名称
			
			
			System.out.println("======================" + exec.getSQL());
			rs = exec.executeQuery();
			return rs;
		}catch(Exception e) {
			ExceptionHandler.handle((CoreUI)null,e);
		}
		return null;
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
	public Set getIds() {
		return ids;
	}

	public void setIds(Set ids) {
		this.ids = ids;
	}
	private Object paramBillIdValue;
	private IMultiapproveDataFilter multiapproveDataFilter;
}
