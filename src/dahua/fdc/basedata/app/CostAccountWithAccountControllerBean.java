package com.kingdee.eas.fdc.basedata.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.IORMappingDAO;
import com.kingdee.bos.dao.ormapping.ORMappingDAO;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.db.TempTablePool;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.master.account.AccountTableInfo;
import com.kingdee.eas.basedata.master.account.AccountTools;
import com.kingdee.eas.basedata.master.account.AccountViewCollection;
import com.kingdee.eas.basedata.master.account.AccountViewFactory;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.master.account.IAccountView;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountInfo;
import com.kingdee.eas.fdc.basedata.CostAccountWithAcctFacadeFactory;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ICostAccountWithAccount;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class CostAccountWithAccountControllerBean extends AbstractCostAccountWithAccountControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.basedata.app.CostAccountWithAccountControllerBean");
    protected void _update(Context ctx, IObjectPK pk, IObjectValue model)
	throws BOSException, EASBizException {
		super._update(ctx,pk,model);
	}
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
	EASBizException {
		super._delete(ctx,pk);
	}
	protected void _submitAll(Context ctx, IObjectCollection objCol) throws BOSException, EASBizException {
		if(objCol==null)
			return ;
		
		int count = objCol.size();
		ICostAccountWithAccount iCostAccountWithAccount = CostAccountWithAccountFactory.getLocalInstance(ctx);
		EntityViewInfo evi = new EntityViewInfo();
//		evi.getSelector().add("*");
//		evi.getSelector().add("costAccount.*");
//		evi.getSelector().add("costAccount.curProject.name");
//		evi.getSelector().add("costAccount.fullOrgUnit.id");
//		evi.getSelector().add("account.*");
		FilterInfo filter = new FilterInfo();//财务组织隔离
		filter.getFilterItems().add(new FilterItemInfo("costAccount.fullOrgUnit.id",ContextUtil.getCurrentFIUnit(ctx).getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("costAccount.curProject.fullOrgUnit.id",ContextUtil.getCurrentFIUnit(ctx).getId().toString()));
		filter.setMaskString("#0 or #1");
		evi.setFilter(filter);
		CostAccountWithAccountCollection oldCollection  = iCostAccountWithAccount.getCostAccountWithAccountCollection(evi);
		CostAccountWithAccountCollection newCollection  = (CostAccountWithAccountCollection)objCol;
		//对于旧集合里面的对象,在新集合里面没有的,要删除
		for(int i = 0;i<oldCollection.size();i++){			
				if(!newCollection.containsKey((oldCollection.get(i).getId()))){
					_delete(ctx,new ObjectUuidPK(oldCollection.get(i).getId().toString()));
				}			
		}
		//保存或者更新新集合对象
		Set prjIdSet=new HashSet();
		for (int i = 0; i <count; i++) {
			CostAccountWithAccountInfo info = (CostAccountWithAccountInfo)objCol.getObject(i);
			if(info.getCostAccount()!=null&&info.getCostAccount().getCurProject()!=null){
				prjIdSet.add(info.getCostAccount().getCurProject().getId().toString()); 
			}
			_submit(ctx,info);
		}
		
		for(Iterator iter=prjIdSet.iterator();iter.hasNext();){
			Map param=new HashMap();
			param.put("PROJECTID", iter.next());
			CostAccountWithAcctFacadeFactory.getLocalInstance(ctx).update(param);
		}
	}

	protected String _getLogInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		return null;
	}
	//此方法没有被调用
	protected void _importGroupData(Context ctx) throws BOSException,
			EASBizException {
		
		ICostAccountWithAccount iCostAccountWithAccount = CostAccountWithAccountFactory.getLocalInstance(ctx);
		//获取当前科目或项目所在组织下的所有科目对应关系数据
		Set set=new HashSet();
		EntityViewInfo view2=new EntityViewInfo();
		FilterInfo filter2=new FilterInfo();
		filter2.appendFilterItem("costAccount.fullOrgUnit.id",ContextUtil.getCurrentFIUnit(ctx).getId().toString());
		filter2.getFilterItems().add(new FilterItemInfo("costAccount.curProject.fullOrgUnit.id",ContextUtil.getCurrentFIUnit(ctx).getId().toString()));
		filter2.setMaskString("#0 or #1");
		view2.setFilter(filter2);
		view2.getSelector().add("costAccount.longNumber");
		CostAccountWithAccountCollection c2 = iCostAccountWithAccount.getCostAccountWithAccountCollection(view2);
		for(int i=0;i<c2.size();i++){
			set.add(c2.get(i).getCostAccount().getLongNumber());
		}
		//获取集团下的所有科目对应关系数据，不含上面集合中已有的成本科目对应数据
		FilterInfo filter = new FilterInfo();//财务组织隔离
		filter.getFilterItems().add(new FilterItemInfo("costAccount.fullOrgUnit.id",OrgConstants.DEF_CU_ID));
		
		if(set.size()>0){
			filter.getFilterItems().add(new FilterItemInfo("costAccount.longNumber",set,CompareType.NOTINCLUDE));
			filter.setMaskString("#0 and #1");
		}
		
		EntityViewInfo evi = new EntityViewInfo();
		evi.getSelector().add("*");
		evi.getSelector().add("costAccount.name");
		evi.getSelector().add("costAccount.longNumber");
		evi.getSelector().add("account.id");
		evi.getSelector().add("account.isLeaf");
		evi.setFilter(filter);
		CostAccountWithAccountCollection costAccountWithAccountCollection = iCostAccountWithAccount.getCostAccountWithAccountCollection(evi);
		if(costAccountWithAccountCollection.size()==0) return;
		
		AccountViewInfo accountViewInfo=null;
		set=new HashSet();
		HashMap map=new HashMap();
		for(int i=0;i<costAccountWithAccountCollection.size();i++){
			accountViewInfo=costAccountWithAccountCollection.get(i).getAccount();
			if(accountViewInfo.isIsLeaf()){
				String longNumber = costAccountWithAccountCollection.get(i).getCostAccount().getLongNumber();
				set.add(longNumber);
				map.put(longNumber, accountViewInfo);
			}
		}
		
		EntityViewInfo view=new EntityViewInfo();
		filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("longNumber",set,CompareType.INCLUDE));
		filter.appendFilterItem("fullOrgUnit.id",ContextUtil.getCurrentFIUnit(ctx).getId().toString());
		filter.getFilterItems().add(new FilterItemInfo("curProject.id",null,CompareType.EQUALS));
		filter.setMaskString("#0 and #1 and #2");
		view.getSelector().add("longNumber");
		view.getSelector().add("name");
		
		view.setFilter(filter);
		
		CostAccountCollection costAccountCollection = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(view);
		for(int i=0;i<costAccountCollection.size();i++){
			CostAccountWithAccountInfo info=new CostAccountWithAccountInfo();
			CostAccountInfo costAccountInfo = costAccountCollection.get(i);
			accountViewInfo = (AccountViewInfo) map.get(costAccountInfo.getLongNumber());
			if(accountViewInfo==null || !accountViewInfo.isIsLeaf()){
				continue;
			}
			info.setCostAccount(costAccountInfo);
			info.setAccount(accountViewInfo);
			info.setDescription("集团数据引入");
			_addnew(ctx, info);
		}		
	}
	
	/* 重写该方法，用临时表进行性能优化
	 * 实现的功能：
		1.将集团下的模板引入到下级工程项目
		2.如果下级工程项目的成本科目已经对应了会计科目则不在引入
		3.如果成本科目对应的会计科目在公司里面有更明细的科目则引入它的下级明细科目的第一个科目
		 by sxhong 2009-05-18 18:11:36
	 * @see com.kingdee.eas.fdc.basedata.app.AbstractCostAccountWithAccountControllerBean#_importGroupData(com.kingdee.bos.Context, java.util.HashSet)
	 */
	protected void _importGroupData(Context ctx,HashSet nodeIdSet) throws BOSException, 
			EASBizException{
		//使用新的算法以优化性能 by sxhong
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx); 
		//1.判断是否该公司存在会计科目
		builder.appendSql("select 1 from T_BD_AccountView where fcompanyid in (select ffullorgunit from T_FDC_CurProject where ");
		builder.appendParam("fid", nodeIdSet.toArray());
		builder.appendSql(")");
		while(!builder.isExist()){
			throw new EASBizException(new NumericExceptionSubItem("100","该财务组织没有会计科目!"));
		}
		
		builder.clear();
		
		AccountTableInfo tableInfo = null;
		CompanyOrgUnitInfo fiOrgInfo = ContextUtil.getCurrentFIUnit(ctx);
		if(fiOrgInfo != null && fiOrgInfo.getAccountTable() != null){
			tableInfo = fiOrgInfo.getAccountTable();
		}
		else{
			tableInfo = AccountTools.getDefaultAccountTableByCU(ContextUtil.getCurrentCtrlUnit(ctx));	
		}
		
		//创建临时表
		TempTablePool pool=TempTablePool.getInstance(ctx);
		
		String createSql="create table temptable (fcostaccountid varchar(44),faccountid varchar(44),finvoiceaccountid varchar (44), fcompanyid varchar(44))";
		String temptable=null;
		try {
			temptable = pool.createTempTable(createSql);
			String desc="集团数据引入";
			//2.将下级工程项目下没有设置对应关系的模板数据引入到该项目下
			builder.appendSql("insert into "+temptable+" (fcostaccountid,faccountid,finvoiceaccountid,fcompanyid)  \n"); //需要导入的模板数据缓存至临时表
			builder.appendSql("( \n"); 
			builder.appendSql("select prjCostAcct.fid,acct.fid,invoiceAcct.fid,acct.fcompanyid from T_FDC_CostAccountWithAccount withAcct \n"); //取模板数据  
			builder.appendSql("inner join T_FDC_CostAccount groupCostAcct on groupCostAcct.fid=withAcct.fcostaccountid   \n"); //模板数据对应的成本科目
			builder.appendSql("inner join T_FDC_CostAccount prjCostAcct on prjCostAcct.flongnumber=groupCostAcct.flongnumber \n");	//当前要分配的工程项目成本科目与模板数据成本科目长编码要匹配
			builder.appendSql("inner join T_FDC_CurProject prj on prj.fid=prjCostAcct.fcurProject  \n"); //当前要分配的工程项目的成本科目
			builder.appendSql("left outer join T_BD_AccountView groupAcct on groupAcct.fid=withAcct.faccountid  \n"); //模板会计科目
			builder.appendSql("left outer join T_BD_AccountView groupInvoiceAcct on groupInvoiceAcct.fid=withAcct.finvoiceAccountid  \n"); //模板发票科目 
			builder.appendSql("left outer join T_BD_AccountView acct on acct.flongnumber=groupAcct.flongnumber   and prj.ffullorgunit=acct.fcompanyid and acct.faccounttableid=? \n"); //模板会计科目长编码与当前要分配的工程项目财务组织ID确定要分配的会计科目
			builder.appendSql("left outer join T_BD_AccountView invoiceAcct on invoiceAcct.flongnumber=groupInvoiceAcct.flongnumber   and prj.ffullorgunit=invoiceAcct.fcompanyid and invoiceAcct.faccounttableid=?  \n"); //模板发票科目长编码与当前要分配的工程项目财务组织ID确定要分配的发票科目
			builder.appendSql("where groupCostAcct.ffullorgunit=? and \n"); // 集团成本科目条件确定模板数据
			builder.addParam(tableInfo.getId().toString());
			builder.addParam(tableInfo.getId().toString());
			//--集团CU  
			builder.addParam(OrgConstants.DEF_CU_ID);
			//--加上工程项目的过滤条件
			builder.appendParam("prjCostAcct.fcurProject", nodeIdSet.toArray());
			//--过滤掉项目中已经存在的成本科目
			builder.appendSql("and not exists (select 1 from T_FDC_CostAccountWithAccount where fcostaccountid=prjCostAcct.fid) \n");
			//R101228-259 成本科目与会计科目对应关系引入报错 ，修改成只导入该组织下存在对应会计科目（发票科目）的记录  by zhiyuan_tang 2011-01-21
			builder.appendSql(" and (acct.fid is not null or invoiceAcct.fid is not null ) \n");
			builder.appendSql(") \n");
			builder.execute();

			//3.如果该科目有明细会计科目取明细会计科目
			builder.clear();
			//会计科目
			builder.appendSql("  UPDATE "+temptable+" SET faccountid = (  \n");
			builder.appendSql("  select fid from T_BD_AccountView  where flongnumber=(  \n");
			builder.appendSql("  SELECT min(childAcct.flongnumber) FROM T_BD_AccountView parentAcct   \n");
			builder.appendSql("  INNER JOIN T_BD_AccountView childAcct ON childAcct.fcompanyid = parentAcct.fcompanyid   \n");
			builder.appendSql("  WHERE ((parentAcct.fid = "+temptable+".faccountid  ");
			builder.appendSql("  AND charindex((parentAcct.flongnumber || '!'),(childAcct.flongnumber || '!')) = 1)   \n");
			builder.appendSql("  AND childAcct.fisleaf = 1)) and fcompanyid="+temptable+".fcompanyid  \n");
			builder.appendSql("  )  WHERE EXISTS (SELECT 1 FROM T_BD_Accountview WHERE (fid = "+temptable+".faccountid AND fisleaf = 0))  \n");
			builder.execute();
			builder.clear();
			
			//发票科目
			builder.clear();
			builder.appendSql("  UPDATE "+temptable+" SET finvoiceaccountid = (  \n");
			builder.appendSql("  select fid from T_BD_AccountView  where flongnumber=( ");
			builder.appendSql("  SELECT min(childAcct.flongnumber) FROM T_BD_AccountView parentAcct   \n");
			builder.appendSql("  INNER JOIN T_BD_AccountView childAcct ON childAcct.fcompanyid = parentAcct.fcompanyid   \n");
			builder.appendSql("  WHERE ((parentAcct.fid = "+temptable+".finvoiceaccountid  ");
			builder.appendSql("  AND charindex((parentAcct.flongnumber || '!'),(childAcct.flongnumber || '!')) = 1)   \n");
			builder.appendSql("  AND childAcct.fisleaf = 1)) and fcompanyid="+temptable+".fcompanyid  \n");
			builder.appendSql("  )  WHERE EXISTS (SELECT 1 FROM T_BD_Accountview WHERE (fid = "+temptable+".finvoiceaccountid AND fisleaf = 0))  \n");
			builder.execute();
			
			//4.引入数据:从临时表中将数据引入到T_FDC_CostAccountWithAccount
			builder.clear();
			builder.appendSql("insert into T_FDC_CostAccountWithAccount (fid,fcostaccountid,faccountid,finvoiceaccountid,fdescription_l2) (select newbosid('256C7E39'),fcostaccountid,faccountid,finvoiceaccountid,'"+desc+"' from "+temptable+" order by fcompanyid) \n");
			builder.execute();

			//TODO 同步未引用的科目
/*			builder.clear();
			builder.appendSql("update T_FDC_CostAccountWithAccount set faccountid=(");
			builder.appendSql("  select top 1 childAcct.fid from T_BD_AccountView childAcct,T_FDC_CostAccountWithAccount withAcct,T_BD_AccountView groupAcct,T_FDC_CostAccount groupCostAcct,T_FDC_CostAccount costAcct ");
			builder.appendSql("  where costacct.fid=T_FDC_CostAccountWithAccount.fid and withAcct.fcostaccountid=groupCostAcct.fid and withAcct.faccountid=groupAcct.fid ");
			builder.appendSql("  and groupCostAcct.ffullorgunit=? and groupCostAcct.flongnumber=costacct.flongnumber and ");
			
			builder.appendSql(") where faccountid is null and fcostaccountid in (select fid from T_fdc_costAccount where fcurProject=?)");
			 select acct.fid,acct.flongnumber from T_FDC_CostAccountWithAccount withAcct 
			 inner join T_FDC_CostAccount groupCostAcct on groupCostAcct.fid=withAcct.fcostaccountid   
			 inner join T_FDC_CostAccount prjCostAcct on prjCostAcct.flongnumber=groupCostAcct.flongnumber 
			 inner join T_FDC_CurProject prj on prj.fid=prjCostAcct.fcurProject  
			 left outer join T_BD_AccountView groupAcct on groupAcct.fid=withAcct.faccountid  
			 left outer join T_BD_AccountView acct on instr(acct.flongnumber||'!',groupAcct.flongnumber||'!')=1 and prj.ffullorgunit=acct.fcompanyid  and acct.fisleaf=1
			 where groupCostAcct.ffullorgunit='00000000-0000-0000-0000-000000000000CCE7AED4' and 
			  prjCostAcct.fcurProject in ('ycOUmZm8St6IjhnQBY58I/nl6Ss=') 
			  and prjCostAcct.fid='TqANjIjVRiWvAu19PsJfu4Qj/24='
			  order by acct.flongnumber
*/
			
		} catch (Exception e1) {
			throw new BOSException(e1);
		}finally{
			if(temptable!=null){
				pool.releaseTable(temptable);
			}
		}

		//生成对应关系全表
		Map param=new HashMap();
		for(Iterator iter=nodeIdSet.iterator();iter.hasNext();){
			String prjId=(String)iter.next();
			param.put("PROJECTID", prjId);
			CostAccountWithAcctFacadeFactory.getLocalInstance(ctx).update(param);
		}
		//已使用新的算法以优化性能
		if(true){
			return;
		}
		//后面的代码已废弃，以后删除 by sxhong 2009-05-18 18:14:31
		ICostAccountWithAccount iCostAccountWithAccount = CostAccountWithAccountFactory.getLocalInstance(ctx);
		IAccountView iAccount = AccountViewFactory.getLocalInstance(ctx);
		EntityViewInfo evi = new EntityViewInfo();
		evi.getSelector().add("*");
		evi.getSelector().add("costAccount.name");
		evi.getSelector().add("costAccount.longNumber");
		evi.getSelector().add("account.id");
		evi.getSelector().add("account.longNumber");
		evi.getSelector().add("account.isLeaf");
		
		for (Iterator iter = nodeIdSet.iterator(); iter.hasNext();) {
			String projectId = (String) iter.next();
			
			//获取当前项目所在财务组织的会计科目
			EntityViewInfo viewA = new EntityViewInfo();
			SelectorItemCollection selector=new SelectorItemCollection();
			selector.add("fullOrgUnit.id");
			FilterInfo filterA = new FilterInfo();
			CurProjectInfo infoA = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(BOSUuid.read(projectId)),selector);
			filterA.getFilterItems().add(new FilterItemInfo("companyID.id", infoA.getFullOrgUnit().getId().toString()));
			viewA.setFilter(filterA);
			viewA.getSelector().add("id");
			viewA.getSelector().add("longNumber");
			viewA.getSelector().add("isLeaf");
			AccountViewCollection avc = iAccount.getAccountViewCollection(viewA);
			AccountViewInfo accountViewInfo=null;
			HashMap myMap = new HashMap();
			if(avc.size()==0){
				throw new EASBizException(new NumericExceptionSubItem("100","该财务组织没有会计科目!"));
			}else{
				for (int i = 0; i < avc.size(); i++) {
					accountViewInfo=avc.get(i);
					if(accountViewInfo != null && accountViewInfo.isIsLeaf()){
						
						//通过sql查询获取最明细的会计科目,
						String longNumParam = accountViewInfo.getLongNumber();
						
						builder = new FDCSQLBuilder(ctx);
						builder.appendSql("select Fnumber, FLongNumber from T_BD_AccountView where flongnumber = ? ");
						builder.addParam( longNumParam );
						builder.appendSql( " or charindex( ?" );
						builder.addParam( longNumParam );
						builder.appendSql( " ||'!', flongnumber )= 1  ");
						builder.appendSql( " order by Fnumber ") ;
						
						String longestLonNum = accountViewInfo.getLongNumber();
						
						try
						{
							IRowSet rowSet = builder.executeQuery();
							HashMap tempMap = new HashMap();
							List longNumList = new ArrayList();
							while(rowSet.next())
							{
								String number = rowSet.getString("Fnumber");
								String longNum = rowSet.getString("FLongnumber");
								longNumList.add(longNum);
								tempMap.put(longNum, number);
							}
							
							if(longNumList.size() > 0)
							{
								longestLonNum = longNumList.get(0).toString();
							}
							for(int j = 0; j < longNumList.size(); ++j )
							{
								String tempStr = longNumList.get(j).toString();
								if(tempStr.length() > longestLonNum.length())
								{
									longestLonNum = tempStr ;
								}
							}
							if(tempMap.get(longestLonNum).equals(longestLonNum))
							{
								longestLonNum = accountViewInfo.getLongNumber() ;
							}
						} catch (SQLException e)
						{
							logger.error(e.getMessage(), e);
							throw new BOSException(e);
						}
						
						accountViewInfo.setLongNumber(longestLonNum);
						
						String longNumber = accountViewInfo.getLongNumber();
						myMap.put(longNumber, accountViewInfo);
					}
				}
			}

			//获取当前项目已有的成本科目对应会计科目数据
			Set set = new HashSet();
			EntityViewInfo view2 = new EntityViewInfo();
			FilterInfo filter2 = new FilterInfo();
			filter2.getFilterItems().add(
					new FilterItemInfo("costAccount.curProject.id", projectId));
			view2.setFilter(filter2);
			view2.getSelector().add("costAccount.longNumber");
			CostAccountWithAccountCollection c2 = iCostAccountWithAccount
					.getCostAccountWithAccountCollection(view2);
			for (int i = 0; i < c2.size(); i++) {
				set.add(c2.get(i).getCostAccount().getLongNumber());
			}
			
			//获取集团下的科目对应模板
			FilterInfo filter = new FilterInfo();//财务组织隔离
			filter.getFilterItems().add(new FilterItemInfo("costAccount.fullOrgUnit.id",OrgConstants.DEF_CU_ID));
			if (set.size() > 0) {
				filter.getFilterItems().add(
						new FilterItemInfo("costAccount.longNumber", set,
								CompareType.NOTINCLUDE));
				filter.setMaskString("#0 and #1");
			}
			evi.setFilter(filter);
			CostAccountWithAccountCollection costAccountWithAccountCollection = iCostAccountWithAccount
					.getCostAccountWithAccountCollection(evi);
			if (costAccountWithAccountCollection.size() == 0)
				continue;
			
			//将成本科目放在map中，键位成本科目的长编码
			set = new HashSet();
			HashMap map = new HashMap();
			for (int i = 0; i < costAccountWithAccountCollection.size(); i++) 
			{
				accountViewInfo=costAccountWithAccountCollection.get(i).getAccount();
				
				//通过sql查询获取最明细的会计科目,
				String longNumParam = accountViewInfo.getLongNumber();
				
				builder = new FDCSQLBuilder(ctx);
				builder.appendSql("select Fnumber, FLongNumber from T_BD_AccountView where flongnumber = ? ");
				builder.addParam( longNumParam );
				builder.appendSql( " or charindex( ?" );
				builder.addParam( longNumParam );
				builder.appendSql( " ||'!', flongnumber )= 1  ");
				builder.appendSql( " order by Fnumber ") ;
				
				String longestLonNum = accountViewInfo.getLongNumber();
				
				try
				{
					IRowSet rowSet = builder.executeQuery();
					HashMap tempMap = new HashMap();
					List longNumList = new ArrayList();
					while(rowSet.next())
					{
						String number = rowSet.getString("Fnumber");
						String longNum = rowSet.getString("FLongnumber");
						longNumList.add(longNum);
						tempMap.put(longNum, number);
					}
					
					if(longNumList.size() > 0)
					{
						longestLonNum = longNumList.get(0).toString();
					}
					for(int j = 0; j < longNumList.size(); ++j )
					{
						String tempStr = longNumList.get(j).toString();
						if(tempStr.length() > longestLonNum.length())
						{
							longestLonNum = tempStr ;
						}
					}
					if(tempMap.get(longestLonNum).equals(longestLonNum))
					{
						longestLonNum = accountViewInfo.getLongNumber() ;
					}
				} catch (SQLException e)
				{
					logger.error(e.getMessage(), e);
					throw new BOSException(e);
				}
				
				accountViewInfo.setLongNumber(longestLonNum);
				
				if(accountViewInfo.isIsLeaf()){
					String longNumber = costAccountWithAccountCollection.get(i)
							.getCostAccount().getLongNumber();
					set.add(longNumber);
					map.put(longNumber, accountViewInfo);
				}
			}
			
			EntityViewInfo view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("longNumber", set, CompareType.INCLUDE));
			filter.getFilterItems().add(
					new FilterItemInfo("curProject.id", projectId));
			view.getSelector().add("longNumber");
			view.getSelector().add("name");
			
			view.setFilter(filter);
			
			CostAccountCollection costAccountCollection = CostAccountFactory
					.getLocalInstance(ctx).getCostAccountCollection(view);
			for (int i = 0; i < costAccountCollection.size(); i++) {
				CostAccountWithAccountInfo info = new CostAccountWithAccountInfo();
				CostAccountInfo costAccountInfo = costAccountCollection.get(i);
				accountViewInfo = (AccountViewInfo) map.get(costAccountInfo.getLongNumber());
				
				if(!myMap.containsKey(accountViewInfo.getLongNumber())){
					continue;
				}
					
				if(accountViewInfo == null || myMap.get(accountViewInfo.getLongNumber()).equals(Boolean.FALSE)){
					continue;
				}
				
				accountViewInfo = (AccountViewInfo) myMap.get(accountViewInfo.getLongNumber());				
				
				info.setCostAccount(costAccountInfo);
				info.setAccount(accountViewInfo);
				info.setDescription("集团数据引入");
				_addnew(ctx, info);
			}
		}
	}
	
	protected void _submitAll(Context ctx, IObjectCollection objCol,String nodeID) throws BOSException, EASBizException {
		if(objCol==null || nodeID==null)
			return ;
		
		int size = objCol.size();

		FilterInfo filter = new FilterInfo();
		boolean isProject=false;
		if (OrgConstants.DEF_CU_ID.equals(nodeID)) {
			filter.getFilterItems().add(
					new FilterItemInfo("costAccount.fullOrgUnit.id", nodeID));
		} else {
			filter.getFilterItems().add(
					new FilterItemInfo("costAccount.curProject.id", nodeID));
			isProject=true;
		}
		
		//对于旧集合里面的对象,在新集合里面没有的,要删除
		//改成像先删除再添加处理更简单
		CostAccountWithAccountFactory.getLocalInstance(ctx).delete(filter);

		//保存或者更新新集合对象
		CostAccountWithAccountInfo tempInfo=new CostAccountWithAccountInfo();
		IORMappingDAO dao=ORMappingDAO.getInstance(tempInfo.getBOSType(), ctx, getConnection(ctx));
		for (int i = 0; i <size; i++) {
			CostAccountWithAccountInfo info = (CostAccountWithAccountInfo)objCol.getObject(i);
				//使用批量接口  by sxhong 2009-07-08 16:48:39
//				_submit(ctx,info);
				dao.addNewBatch(info);
		}
		dao.executeBatch();
		
		//如下用于初始化数据
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select count(*) as count from T_FDC_AllCostAcctWithAcct ");
		IRowSet rowSet=builder.executeQuery(ctx);
		if(rowSet!=null&&rowSet.size()>0){
			try{
				rowSet.next();
				int count=rowSet.getInt("count");
				if(count==0){
					//update all company data
					builder.clear();
					builder.appendSql("select distinct ffullorgunit from T_FDC_CurProject where fid in (select fcurProject from T_FDC_CostAccount acct inner join T_FDC_CostAccountWithAccount withAcct on withAcct.fcostaccountid=acct.fid )");
					rowSet=builder.executeQuery();
					Map param=new HashMap();
					while(rowSet.next()){
						String companyId=rowSet.getString("ffullorgunit");
						param.put("COMPANYID", companyId);
						CostAccountWithAcctFacadeFactory.getLocalInstance(ctx).update(param);
					}
					isProject=false;//已经在这个地方更新了，则不要在更新了
				}
			}catch(SQLException e){
				throw new BOSException(e);
			}
			
		}
		
		if(isProject){
			Map param=new HashMap();
			param.put("PROJECTID", nodeID);
			CostAccountWithAcctFacadeFactory.getLocalInstance(ctx).update(param);
		}
	}
	
}