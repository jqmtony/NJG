package com.kingdee.eas.fdc.contract.client;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.reportone.r1.print.data.R1PrintDataSource;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.util.FDCNoteDataProvider;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class ContractBillRevisePrintDataProvider extends FDCNoteDataProvider {
private static final Logger logger = Logger.getLogger(ContractBillRevisePrintDataProvider.class);
	
	private String packagStr = "";
	
	// 每一个query 对应一个对应的过滤条件
	public Map queryAndFilterMap = null;
	// 如果有需要对应的query 有对应一个位置
	public Map queryLocStrMap = null;
	
	public ContractBillRevisePrintDataProvider(String billId, IMetaDataPK mainQuery) {
		super(billId, mainQuery);
	
	}
	
	public ContractBillRevisePrintDataProvider(String billId, IMetaDataPK mainQuery, String comPackageStr) {
		super(billId, mainQuery);
		packagStr = comPackageStr;
	}
	
	public ContractBillRevisePrintDataProvider(String billId, IMetaDataPK mainQuery, Map queryAndFilterMap) {
		super(billId, mainQuery);
		this.queryAndFilterMap = queryAndFilterMap;
	}
	
	public ContractBillRevisePrintDataProvider(String billId, IMetaDataPK mainQuery, String comPackageStr, Map queryAndFilterMap, Map queryLocStrMap) {
		super(billId, mainQuery);
		this.packagStr = comPackageStr;
		this.queryAndFilterMap = queryAndFilterMap;
		this.queryLocStrMap = queryLocStrMap;
	}
	
	public ContractBillRevisePrintDataProvider(String billId, IMetaDataPK mainQuery, String comPackageStr, Map queryAndFilterMap) {
		super(billId, mainQuery);
		this.packagStr = comPackageStr;
		this.queryAndFilterMap = queryAndFilterMap;
		//		this.queryLocStrMap = queryLocStrMap;
	}
	

	
	public Map getQueryAndFilterMap() {
		return queryAndFilterMap;
	}
	public void setQueryAndFilterMap(Map queryAndFilterMap) {
		this.queryAndFilterMap = queryAndFilterMap;
	}
	
	
	
	
	public String getPackagStr() {
		return packagStr;
	}

	public void setPackagStr(String packagStr) {
		this.packagStr = packagStr;
	}

	public void addQueryLocStr(String queryStr,String loc){
		if(getQueryAndFilterMap()==null){
			queryLocStrMap = new HashMap();
			queryLocStrMap.put(queryStr, loc);
		}else{
			if(null!=queryLocStrMap.get(queryStr)){		
				queryLocStrMap.remove(queryStr);
			}
			queryLocStrMap.put(queryStr, loc);
		}
		
		
	}
	
	
	public void addQueryAndFilter(String queryStr,FilterInfo filter){
		if(getQueryAndFilterMap()==null){
			queryAndFilterMap = new HashMap();
			queryAndFilterMap.put(queryStr, filter);
		}else{
			if(null!=queryAndFilterMap.get(queryStr)){		
				queryAndFilterMap.remove(queryStr);
			}
			queryAndFilterMap.put(queryStr, filter);
		}
		
	}
	
	public IRowSet getData(R1PrintDataSource ds) throws Exception {		
		
//		if(ds.getId().toUpperCase().trim().equals(mainQueryStr.toUpperCase())){
//			return getMainBillRowSet(ds);
//		}
//		else if(ds.getId().toUpperCase().trim().equals(contractRevisePayItemQueryStr.toUpperCase())){
//			return  getContractRevisePayItemRowSet(ds);
//		}
//		else if(ds.getId().toUpperCase().trim().equals(ContractReviseBailPrintQuery.toUpperCase())){
//			return  getFDCContractReviseBailRowSet(ds);
//		}
//		
		if(queryAndFilterMap!=null&&queryAndFilterMap.entrySet()!=null){
			//循环比的在map 中的query名字
			//必须取出来比，因为如果直接在map中用 ds.getid get 在套打配置数据元的时候
			//数据元的名字就必须和query大小写一致。
			for(Iterator it = queryAndFilterMap.keySet().iterator();it.hasNext();){
				String queryStr = (String) it.next();
				//这样循环比对大小不用一致
				if(ds.getId().toUpperCase().trim().equals(queryStr.toUpperCase())){
					Object temp = queryAndFilterMap.get(queryStr);
					if(temp!=null){
						return getRowSetByQueryAndFilter(queryStr,(FilterInfo)temp);
					}
				}
			}
			
		}
		return super.getData(ds);
	}
	
	//根据query 过滤 
	public IRowSet getRowSetByQueryAndFilter(String queryStr,FilterInfo filter){
		IRowSet iRowSet = null;
		if(queryLocStrMap!=null){//找到一个对应的路径
			if(queryLocStrMap.get(queryStr)!=null){
				packagStr = (String) queryLocStrMap.get(queryStr);
			}
		}
		if(packagStr==null||("").equals(packagStr)){
			FDCMsgBox.showWarning("请套打查询路径有误!");
			logger.error("请套打查询路径有误!");
			SysUtil.abort();
		}
		try {
			IMetaDataPK temp = null;
			if(packagStr.endsWith(".")){
				temp = new MetaDataPK(packagStr+queryStr);
			}else{
				temp = new MetaDataPK(packagStr+"."+queryStr);
			}
			IQueryExecutor exec = QueryExecutorFactory
					.getRemoteInstance(temp);
			exec.option().isAutoTranslateEnum = true;
			EntityViewInfo ev = new EntityViewInfo();
			ev.setFilter(filter);
			exec.setObjectView(ev);
			iRowSet = exec.executeQuery();
			iRowSet.beforeFirst();
		} catch (Exception e) {
			logger.debug(e.getMessage(), e);
			e.printStackTrace();
			SysUtil.abort();
		}

		return iRowSet;
		
	}

}
