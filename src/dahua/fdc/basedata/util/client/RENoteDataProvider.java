package com.kingdee.eas.fdc.basedata.util.client;
import java.math.BigDecimal;

import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class RENoteDataProvider implements BOSQueryDelegate { //重新复写打印类
		// 决裁单ID
		private EntityViewInfo query = null;
		private String id = null;
		
		public RENoteDataProvider() {
			super();
		}
		public RENoteDataProvider(String id)
		{
			this.id=id;
		}
		public RENoteDataProvider(EntityViewInfo query)
		{
			this.query=query;
		}
		public IRowSet execute(BOSQueryDataSource ds) { //得到打印数据源
			IRowSet rowSet = null;
			try {
				IQueryExecutor iqec = null;
				String str = ds.getID(); // 得到打印数据源query的名称
				iqec.setObjectView(query);
				rowSet = iqec.executeQuery();
				 while(rowSet.next()){
					 if(rowSet.getString("htjszj")!=null){
              			   BigDecimal jsj = UIRuleUtil.getBigDecimal(rowSet.getString("htjszj"));
              			   rowSet.updateObject("number", jsj); 
              		   }else{
              			   rowSet.updateObject("number", UIRuleUtil.getBigDecimal(rowSet.getString("htzj"))); 
              		   }
				 }
			} catch (Exception e) {
				e.printStackTrace();
			}		
			return rowSet;
		}	
}