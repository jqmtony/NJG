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
	
	// ÿһ��query ��Ӧһ����Ӧ�Ĺ�������
	public Map queryAndFilterMap = null;
	// �������Ҫ��Ӧ��query �ж�Ӧһ��λ��
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
			//ѭ���ȵ���map �е�query����
			//����ȡ�����ȣ���Ϊ���ֱ����map���� ds.getid get ���״���������Ԫ��ʱ��
			//����Ԫ�����־ͱ����query��Сдһ�¡�
			for(Iterator it = queryAndFilterMap.keySet().iterator();it.hasNext();){
				String queryStr = (String) it.next();
				//����ѭ���ȶԴ�С����һ��
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
	
	//����query ���� 
	public IRowSet getRowSetByQueryAndFilter(String queryStr,FilterInfo filter){
		IRowSet iRowSet = null;
		if(queryLocStrMap!=null){//�ҵ�һ����Ӧ��·��
			if(queryLocStrMap.get(queryStr)!=null){
				packagStr = (String) queryLocStrMap.get(queryStr);
			}
		}
		if(packagStr==null||("").equals(packagStr)){
			FDCMsgBox.showWarning("���״��ѯ·������!");
			logger.error("���״��ѯ·������!");
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
