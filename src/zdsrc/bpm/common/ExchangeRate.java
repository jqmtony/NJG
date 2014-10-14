package com.kingdee.eas.bpm.common;

import java.math.BigDecimal;
import java.sql.SQLException;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.jdbc.rowset.IRowSet;

public class ExchangeRate {
	/**
	 * 查询汇率,返回rowSet
	 * @param newCurrency
	 * @param exchangeDate
	 * @return rowSet
	 * @throws BOSException 
	 * @throws SQLException 
	 */
	public static IRowSet queryRate(String newCurrency,String exchangeDate,String quoteprice){
		IQueryExecutor iqec = QueryExecutorFactory.getRemoteInstance(new MetaDataPK("com.kingdee.eas.basedata.assistant.app","ExchangeRateQuery"));
		EntityViewInfo envi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		// 原币
		filter.getFilterItems().add(new FilterItemInfo("sourceCurrency.name", newCurrency,CompareType.EQUALS));
		// 结汇日期即生效时间
		//filter.getFilterItems().add(new FilterItemInfo("availTime", exchangeDate,CompareType.EQUALS));
		//filter.getFilterItems().add(new FilterItemInfo("availTime", exchangeDate,CompareType.LESS_EQUALS));
		
		filter.getFilterItems().add(new FilterItemInfo("quoteprice",quoteprice,CompareType.EQUALS));
		envi.setFilter(filter);
		SorterItemInfo siInfo = new SorterItemInfo("availTime");
		siInfo.setSortType(SortType.ASCEND);//升序
		envi.getSorter().add(siInfo);
		iqec.setObjectView(envi);
		IRowSet rowSet = null;		
		try {
			rowSet = iqec.executeQuery();
		} catch (BOSException e1) {
			// TODO 自动生成 catch 块
			e1.printStackTrace();
		}
		return rowSet;
	}
	/**
	 * 得汇率
	 * @param newCurrency
	 * @param exchangeDate
	 * @return
	 * @throws BOSException 
	 * @throws SQLException 
	 */
	public static BigDecimal getRate(String newCurrency,String exchangeDate,String quoteprice){
		BigDecimal price = null;
		IRowSet rowSet =  queryRate(newCurrency , exchangeDate,quoteprice);
		
		// 设置汇率值
		if(rowSet != null){
			if((rowSet).size()>=1){
				try {
					while (rowSet.next()) {	
						price=rowSet.getBigDecimal("convertRate");
					}
				} catch (SQLException e) {
					// TODO 自动生成 catch 块
					e.printStackTrace();
				}
			}
		}
	    return price;
	}
	
	
}