package com.kingdee.eas.fdc.basedata.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.AllCostAcctWithAcctInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.jdbc.rowset.IRowSet;

public class CostAccountWithAcctFacadeControllerBean extends AbstractCostAccountWithAcctFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.basedata.app.CostAccountWithAcctFacadeControllerBean");
    
    /**
     * 插入SQL//TODO 可以后改成final
     */
    private static String insertSql="insert into t_fdc_allcostacctwithacct (fid,fcompanyid,fprojectId,fcostAccountid,faccountid,FInvoiceAcctID) values(?,?,?,?,?,?);";
    protected void _update(Context ctx, Map param)throws BOSException, EASBizException
    {
    	String companyId=(String)param.get("COMPANYID");
    	String projectId=(String)param.get("PROJECTID");
    	if(projectId!=null){
    		updateProject(ctx,projectId);
    	}else{
    		//update this company
    		updateCompany(ctx,companyId);
    	}
    }

	/**
	 * 更新一个项目的对应关系到全成本科目与会计科目的对应关系
	 * @param ctx
	 * @param projectId
	 * @throws BOSException
	 */
	private void updateProject(Context ctx,String projectId) throws BOSException {
		//update this project
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		//delete first
		builder.appendSql("delete from t_fdc_allcostacctwithacct where fprojectid=?");
		builder.addParam(projectId);
		builder.execute();
		
		//then insert
		builder.clear();
		builder.appendSql("select acct.fcurProject||acct.flongnumber as objectId,faccountid,finvoiceAccountid from t_Fdc_Costaccountwithaccount withAcct ");
		builder.appendSql("inner join T_FDC_CostAccount acct on withAcct.fcostAccountid=acct.fid where fcurProject=?");
		builder.addParam(projectId);
		IRowSet rowSet=builder.executeQuery();
		if(rowSet==null||rowSet.size()==0){
			return;
		}
		Map costWithAccountMap=new HashMap();
		try{
			while(rowSet.next()){
				costWithAccountMap.put(rowSet.getString("objectId")+ACCTSUFFIX, rowSet.getString("faccountid"));
				costWithAccountMap.put(rowSet.getString("objectId")+INVOICEACCTSUFFIX, rowSet.getString("finvoiceAccountid"));
			}
		}catch(SQLException e){
			throw new BOSException(e);
		}
		
		//get child costaccount
		builder.clear();
		builder.appendSql("select distinct subAcct.fid as fcostaccountid,subAcct.flongnumber as flongnumber from t_Fdc_Costaccountwithaccount withAcct ");
		builder.appendSql(" inner join T_FDC_CostAccount parentAcct on parentAcct.fid=withAcct.fcostAccountid ");
		builder.appendSql(" inner join T_FDC_CostAccount subAcct on parentAcct.fcurProject=subAcct.fcurProject ");
		builder.appendSql("where parentAcct.fcurProject=? and charindex(parentAcct.flongnumber||'!',subAcct.flongnumber||'!')=1 ");
		builder.addParam(projectId);
		rowSet=builder.executeQuery();
		List paramList=new ArrayList();
		
		try{
			AllCostAcctWithAcctInfo tempInfo=new AllCostAcctWithAcctInfo();
			String myCompanyId=null;
			while(rowSet.next()){
				String newId=BOSUuid.create(tempInfo.getBOSType()).toString();
				String subCostAcctId=rowSet.getString("fcostaccountid");
				String subCostAcctLongNumber=rowSet.getString("flongnumber");
				String acctId=getParentCostAcctWithAccount(projectId, subCostAcctLongNumber, costWithAccountMap);
				String invoiceAcctId=getParentCostAcctWithInvoiceAccount(projectId, subCostAcctLongNumber, costWithAccountMap);
				if(acctId==null){
					logger.info("can't get acctId");
					continue;
				}
				paramList.add(Arrays.asList(new String[]{newId,myCompanyId,projectId,subCostAcctId,acctId,invoiceAcctId}));
			}
			builder.executeBatch(insertSql, paramList);
			
			//update companyId
			builder.clear();
			builder.appendSql("update t_fdc_allcostacctwithacct set fcompanyId=(select ffullorgunit from T_FDC_CurProject where fid=t_fdc_allcostacctwithacct.fprojectid) where fcompanyId is null");
			builder.execute();
/*			//将对应关系里面有的对应关系移动到t_fdc_allcostacctwithacct 里面
			charindex(parentAcct.flongnumber||'!',subAcct.flongnumber||'!')=1 包含对应关系的数据，不用在插入了
			builder.clear();
			builder.appendSql("insert into t_fdc_allcostacctwithacct (fid,fcompanyid,fprojectId,fcostAccountid,faccountid,FInvoiceAcctID) ");
			builder.appendSql(" select newbosid('D3160728'),prj.ffullorgunit,costAcct.fcurProject,costAcct.fid,with.faccountid,null from t_Fdc_Costaccountwithaccount withAcct");
			builder.appendSql(" inner join T_FDC_CostAccount costAcct on withAcct.fcostAccountId=costAcct.fid ");
			builder.appendSql(" inner join T_FDC_CurProject prj on prj.fid=costAcct.fcurproject ");
			builder.appendSql(" where prj.fid=? ");
			builder.addParam(projectId);
			builder.execute();*/
			
		}catch(SQLException e){
			throw new BOSException(e);
		}
	}
    
	/**
	 * 更新一个实体财务组织的对应关系到全成本科目与会计科目的对应关系
	 * @param ctx
	 * @param companyId
	 * @throws BOSException
	 */
	private void updateCompany(Context ctx,String companyId) throws BOSException {
		//update this project
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		//delete first
		builder.appendSql("delete from t_fdc_allcostacctwithacct where fcompanyId=?");
		builder.addParam(companyId);
		builder.execute();
		
		//then insert 
		builder.clear();
		builder.appendSql("select acct.fcurProject||acct.flongnumber as objectId,faccountid,finvoiceAccountid from t_Fdc_Costaccountwithaccount withAcct ");
		builder.appendSql("inner join T_FDC_CostAccount acct on withAcct.fcostAccountid=acct.fid ");
		builder.appendSql("inner join T_FDC_CurProject prj on prj.fid=acct.fcurproject where prj.ffullorgunit=? ");
		builder.addParam(companyId);
		IRowSet rowSet=builder.executeQuery();
		if(rowSet==null||rowSet.size()==0){
			return;
		}
		Map costWithAccountMap=new HashMap();
		try{
			while(rowSet.next()){
				costWithAccountMap.put(rowSet.getString("objectId")+ACCTSUFFIX, rowSet.getString("faccountid"));
				costWithAccountMap.put(rowSet.getString("objectId")+INVOICEACCTSUFFIX, rowSet.getString("finvoiceAccountid"));
			}
		}catch(SQLException e){
			throw new BOSException(e);
		}
		
		//get child costaccount
		builder.clear();
		builder.appendSql("select distinct subAcct.fcurProject as fcurProjectId,subAcct.fid as fcostaccountid,subAcct.flongnumber as flongnumber from t_Fdc_Costaccountwithaccount withAcct ");
		builder.appendSql(" inner join T_FDC_CostAccount parentAcct on parentAcct.fid=withAcct.fcostAccountid ");
		builder.appendSql(" inner join T_FDC_CurProject prj on prj.fid=parentAcct.fcurproject ");
		builder.appendSql(" inner join T_FDC_CostAccount subAcct on parentAcct.fcurProject=subAcct.fcurProject ");
		builder.appendSql(" where prj.ffullorgunit=?  and charindex(parentAcct.flongnumber||'!',subAcct.flongnumber||'!')=1 ");
		builder.addParam(companyId);
		rowSet=builder.executeQuery();
		List paramList=new ArrayList();
		
		try{
			AllCostAcctWithAcctInfo tempInfo=new AllCostAcctWithAcctInfo();
			String myCompanyId=companyId;
			while(rowSet.next()){
				String newId=BOSUuid.create(tempInfo.getBOSType()).toString();
				String subPrjId=rowSet.getString("fcurProjectId");
				String subCostAcctId=rowSet.getString("fcostaccountid");
				String subCostAcctLongNumber=rowSet.getString("flongnumber");
				String acctId=getParentCostAcctWithAccount(subPrjId, subCostAcctLongNumber, costWithAccountMap);
				String invoiceAcctId=getParentCostAcctWithInvoiceAccount(subPrjId, subCostAcctLongNumber, costWithAccountMap);
				if(acctId==null){
					logger.info("can't get acctId");
				}
				paramList.add(Arrays.asList(new String[]{newId,myCompanyId,subPrjId,subCostAcctId,acctId,invoiceAcctId}));
			}
			builder.executeBatch(insertSql, paramList);
			
		}catch(SQLException e){
			throw new BOSException(e);
		}
	}
	/**
	 * 成本科目对应的会计科目的后缀
	 */
	private final static String ACCTSUFFIX="_acct";
	/**
	 * 成本科目对应的发票会计科目的后缀
	 */
	private final static String INVOICEACCTSUFFIX="_invoiceAcct";
	
    /**
     * 取成本科目对应的会计科目，如果本级没有对应关系，则取他上级对应的会计科目
     * @param projectId
     * @param longNumber
     * @param costWithAccountMap
     * @return
     */
    private String getParentCostAcctWithAccount(String projectId,String longNumber,Map costWithAccountMap){
    	for(String parentLongNumber=longNumber;parentLongNumber!=null;parentLongNumber=parentLongNumber.substring(0, parentLongNumber.lastIndexOf('!'))){
    		String key = projectId+parentLongNumber+ACCTSUFFIX;
			if(costWithAccountMap.get(key)!=null){
    			return (String)costWithAccountMap.get(key);
    		}
			//将循环的控制条件换到此处的原因是以为存在一级科目的对应关系 如：6001
			if(parentLongNumber.indexOf('!')<0){
				break;
			}
    	}
    	
    	return null;
    }
    
    /**
     * 取成本科目对应的发票会计科目，如果本级没有对应关系，则取他上级对应的发票会计科目
     * @param projectId
     * @param longNumber
     * @param costWithAccountMap
     * @return
     */
    private String getParentCostAcctWithInvoiceAccount(String projectId,String longNumber,Map costWithAccountMap){
    	for(String parentLongNumber=longNumber;parentLongNumber!=null;parentLongNumber=parentLongNumber.substring(0, parentLongNumber.lastIndexOf('!'))){
    		String key = projectId+parentLongNumber+INVOICEACCTSUFFIX;
			if(costWithAccountMap.get(key)!=null){
    			return (String)costWithAccountMap.get(key);
    		}
			//将循环的控制条件换到此处的原因是因为存在一级科目的对应关系 如：6001
			if(parentLongNumber.indexOf('!')<0){
				break;
			}
    	}
    	
    	return null;
    }
    
    /* 
     * 在新增科目或者分配科目的时候使用
     * @see com.kingdee.eas.fdc.basedata.app.AbstractCostAccountWithAcctFacadeControllerBean#_insert(com.kingdee.bos.Context, java.util.Map)
     */
    protected void _insert(Context ctx, Map param)throws BOSException, EASBizException
    {
    	Set acctIdSet=(Set)param.get("acctIdSet");
    	if(acctIdSet==null||acctIdSet.size()==0){
    		return;
    	}
    	Object[] ids = acctIdSet.toArray();
    	//delete first
    	if(ids.length>500){
    		for(int i=0;i<ids.length/500+1;i++){
    			Object idsSplit[];
    			if(i==ids.length/500)
    				idsSplit = new Object[ids.length - i*500];
    			else 
    				idsSplit = new Object[500];
    			for(int j=0;j<500;j++){
    				if(i*500+j>=ids.length)	break;
    				idsSplit[j] = ids[i*500+j];
    			}
    			innerInsert(ctx, idsSplit);
    		}
    	}else{
    		innerInsert(ctx, ids);
    	}
    }

	private void innerInsert(Context ctx, Object[] ids) throws BOSException {
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
    	builder.appendSql("delete from T_fdc_allcostacctwithacct where ");
    	builder.appendParam("fcostaccountid", ids);
    	builder.execute();
    	//get costaccountwith
    	builder.clear();
		builder.appendSql("select parentAcct.fcurProject||parentAcct.flongnumber as objectId,faccountid,finvoiceAccountid from t_Fdc_Costaccountwithaccount withAcct ");
		builder.appendSql(" inner join T_FDC_CostAccount parentAcct on withAcct.fcostAccountid=parentAcct.fid ");
		builder.appendSql(" inner join T_FDC_CostAccount subAcct on parentAcct.fcurProject=subAcct.fcurProject ");
		builder.appendSql(" where charindex(parentAcct.flongnumber||'!',subAcct.flongNumber||'!')=1 and ");
		builder.appendParam("subAcct.fid", ids);
		IRowSet rowSet=builder.executeQuery();
		if(rowSet==null||rowSet.size()==0){
			return;
		}
		Map costAcctWithAcctMap=new HashMap();
		try{
			while(rowSet.next()){
				costAcctWithAcctMap.put(rowSet.getString("objectId")+ACCTSUFFIX, rowSet.getString("faccountid"));
				costAcctWithAcctMap.put(rowSet.getString("objectId")+INVOICEACCTSUFFIX, rowSet.getString("finvoiceAccountid"));
			}
			
			//get subAccount
			builder.clear();
			
			builder.appendSql("select fcurProject as fcurProjectid,fid as fcostAccountid ,flongnumber from T_FDC_CostAccount where ");
			builder.appendParam("fid", ids);
			rowSet=builder.executeQuery();
			AllCostAcctWithAcctInfo tempInfo=new AllCostAcctWithAcctInfo();
			String myCompanyId=null;
			List paramList=new ArrayList();
			while(rowSet.next()){
				String newId=BOSUuid.create(tempInfo.getBOSType()).toString();
				String subPrjId=rowSet.getString("fcurProjectId");
				String subCostAcctId=rowSet.getString("fcostaccountid");
				String subCostAcctLongNumber=rowSet.getString("flongnumber");
				String acctId=getParentCostAcctWithAccount(subPrjId, subCostAcctLongNumber, costAcctWithAcctMap);
				String invoiceAcctId=getParentCostAcctWithInvoiceAccount(subPrjId, subCostAcctLongNumber, costAcctWithAcctMap);
				if(acctId==null){
					logger.info("can't get acctId");
				}
				paramList.add(Arrays.asList(new String[]{newId,myCompanyId,subPrjId,subCostAcctId,acctId,invoiceAcctId}));
			}
			builder.executeBatch(insertSql, paramList);
			
			//update companyId
			builder.clear();
			builder.appendSql("update t_fdc_allcostacctwithacct set fcompanyId=(select ffullorgunit from T_FDC_CurProject where fid=t_fdc_allcostacctwithacct.fprojectid) where fcompanyId is null");
			builder.execute();
			
		}catch(Exception e){
			throw new BOSException(e);
		}
	}
}