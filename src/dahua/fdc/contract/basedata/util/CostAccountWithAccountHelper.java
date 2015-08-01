package com.kingdee.eas.fdc.basedata.util;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.jdbc.rowset.IRowSet;

public class CostAccountWithAccountHelper{
	private CostAccountWithAccountHelper(){}
	
	
	/**
	 * 通过传入的成本科目获取其对应关系，返回的Map 格式如下：
	 * key: project+costaccountid
	 * value: CostAccountWithAccountInfo   这个info是动态构建的只有会计科目，发票科目
	 * @param ctx
	 * @param costAcctIdSet
	 * @return
	 * @throws BOSException
	 */
	public static Map getCostAcctWithAcctMapByCostAccountIds(Context ctx,Set costAcctIdSet) throws BOSException{
		Map retMap=null;
		if(costAcctIdSet==null||costAcctIdSet.size()==0){
			return new HashMap();
		}
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select fprojectid,fcostaccountid,faccountid,finvoiceacctid from t_fdc_allcostacctwithacct ");
		builder.appendSql(" where ");
		builder.appendParam("fcostaccountid", costAcctIdSet.toArray());
		
		IRowSet rowSet=builder.executeQuery();
		try {
			retMap=handleRowSet(rowSet);
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		return retMap==null?new HashMap():retMap; 
	}
	
	/**
	 * 通过传入的工程项目获取其对应关系，返回的Map 格式如下：
	 * key: project+costaccountid
	 * value: CostAccountWithAccountInfo   这个info是动态构建的只有会计科目，发票科目
	 * @param ctx
	 * @param projectIdSet
	 * @return
	 * @throws BOSException
	 */
	public static Map getCostAcctWithAcctMapByProjectIds(Context ctx,Set projectIdSet) throws BOSException{
		Map retMap=null;
		if(projectIdSet==null||projectIdSet.size()==0){
			return new HashMap();
		}
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select fprojectid,fcostaccountid,faccountid,finvoiceacctid from t_fdc_allcostacctwithacct ");
		builder.appendSql(" where ");
		builder.appendParam("fprojectid", projectIdSet.toArray());
		
		IRowSet rowSet=builder.executeQuery();
		try {
			retMap=handleRowSet(rowSet);
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		return retMap==null?new HashMap():retMap; 
		
	}
	
	/**
	 * 通过传入的实体财务组织获取其对应关系，返回的Map 格式如下：
	 * key: project+costaccountid
	 * value: CostAccountWithAccountInfo   这个info是动态构建的只有会计科目，发票科目
	 * @param ctx
	 * @param companyIdSet
	 * @return
	 * @throws BOSException
	 */
	public static Map getCostAcctWithAcctMapByCompayIds(Context ctx,Set companyIdSet) throws BOSException{
		Map retMap=null;
		if(companyIdSet==null||companyIdSet.size()==0){
			return new HashMap();
		}
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select fprojectid,fcostaccountid,faccountid,finvoiceacctid from t_fdc_allcostacctwithacct ");
		builder.appendSql(" where ");
		builder.appendParam("fprojectid", companyIdSet.toArray());
		
		IRowSet rowSet=builder.executeQuery();
		try {
			retMap=handleRowSet(rowSet);
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		return retMap==null?new HashMap():retMap; 
		
	}
	
	/**
	 * 处理返回的结果集 
	 * @param rowSet
	 * @return
	 * @throws SQLException
	 */
	private static Map handleRowSet(IRowSet rowSet) throws SQLException {
		Map retMap=new HashMap();
		//用于存放会计科目的info对象，避免再次创建
		Map accountMap=new HashMap();
		Map invoiceAccountMap=new HashMap();
		Map costAcctWithAcctInfoMap=new HashMap();

		while(rowSet.next()){
			String prjId=rowSet.getString("fprojectid");
			String costAcctId=rowSet.getString("fcostaccountid");
			String acctId=rowSet.getString("faccountid");
			String invoiceAcctId=rowSet.getString("finvoiceacctid");
			String key=prjId+"_"+costAcctId;
			String acctKey = acctId+invoiceAcctId;
			if(costAcctWithAcctInfoMap.containsKey(acctKey)){
				retMap.put(key, costAcctWithAcctInfoMap.get(key));
				continue;
			}
			
			CostAccountWithAccountInfo withInfo=new CostAccountWithAccountInfo();
			//处理会计科目
			if(acctId!=null){
				if(accountMap.containsKey(acctId)){
					withInfo.setAccount((AccountViewInfo)accountMap.get(acctId));
				}else{
					AccountViewInfo acctInfo=new AccountViewInfo();
					acctInfo.setId(BOSUuid.read(acctId));
					accountMap.put(acctId, acctInfo);
					withInfo.setAccount(acctInfo);
				}
			}
			//处理发票会计科目
			if(invoiceAcctId!=null){
				if(invoiceAccountMap.containsKey(invoiceAcctId)){
					withInfo.setAccount((AccountViewInfo)accountMap.get(acctId));
				}else{
					AccountViewInfo acctInfo=new AccountViewInfo();
					acctInfo.setId(BOSUuid.read(invoiceAcctId));
					accountMap.put(invoiceAcctId, acctInfo);
					//设置到发票对应科目
					withInfo.setInvoiceAccount(acctInfo);
				}
			}
			costAcctWithAcctInfoMap.put(key, withInfo);
			
			retMap.put(key, costAcctWithAcctInfoMap.get(key));
			
		}
		return retMap;
	}
}
