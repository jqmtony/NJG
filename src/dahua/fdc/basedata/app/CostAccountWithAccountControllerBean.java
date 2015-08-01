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
		FilterInfo filter = new FilterInfo();//������֯����
		filter.getFilterItems().add(new FilterItemInfo("costAccount.fullOrgUnit.id",ContextUtil.getCurrentFIUnit(ctx).getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("costAccount.curProject.fullOrgUnit.id",ContextUtil.getCurrentFIUnit(ctx).getId().toString()));
		filter.setMaskString("#0 or #1");
		evi.setFilter(filter);
		CostAccountWithAccountCollection oldCollection  = iCostAccountWithAccount.getCostAccountWithAccountCollection(evi);
		CostAccountWithAccountCollection newCollection  = (CostAccountWithAccountCollection)objCol;
		//���ھɼ�������Ķ���,���¼�������û�е�,Ҫɾ��
		for(int i = 0;i<oldCollection.size();i++){			
				if(!newCollection.containsKey((oldCollection.get(i).getId()))){
					_delete(ctx,new ObjectUuidPK(oldCollection.get(i).getId().toString()));
				}			
		}
		//������߸����¼��϶���
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
	//�˷���û�б�����
	protected void _importGroupData(Context ctx) throws BOSException,
			EASBizException {
		
		ICostAccountWithAccount iCostAccountWithAccount = CostAccountWithAccountFactory.getLocalInstance(ctx);
		//��ȡ��ǰ��Ŀ����Ŀ������֯�µ����п�Ŀ��Ӧ��ϵ����
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
		//��ȡ�����µ����п�Ŀ��Ӧ��ϵ���ݣ��������漯�������еĳɱ���Ŀ��Ӧ����
		FilterInfo filter = new FilterInfo();//������֯����
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
			info.setDescription("������������");
			_addnew(ctx, info);
		}		
	}
	
	/* ��д�÷���������ʱ����������Ż�
	 * ʵ�ֵĹ��ܣ�
		1.�������µ�ģ�����뵽�¼�������Ŀ
		2.����¼�������Ŀ�ĳɱ���Ŀ�Ѿ���Ӧ�˻�ƿ�Ŀ��������
		3.����ɱ���Ŀ��Ӧ�Ļ�ƿ�Ŀ�ڹ�˾�����и���ϸ�Ŀ�Ŀ�����������¼���ϸ��Ŀ�ĵ�һ����Ŀ
		 by sxhong 2009-05-18 18:11:36
	 * @see com.kingdee.eas.fdc.basedata.app.AbstractCostAccountWithAccountControllerBean#_importGroupData(com.kingdee.bos.Context, java.util.HashSet)
	 */
	protected void _importGroupData(Context ctx,HashSet nodeIdSet) throws BOSException, 
			EASBizException{
		//ʹ���µ��㷨���Ż����� by sxhong
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx); 
		//1.�ж��Ƿ�ù�˾���ڻ�ƿ�Ŀ
		builder.appendSql("select 1 from T_BD_AccountView where fcompanyid in (select ffullorgunit from T_FDC_CurProject where ");
		builder.appendParam("fid", nodeIdSet.toArray());
		builder.appendSql(")");
		while(!builder.isExist()){
			throw new EASBizException(new NumericExceptionSubItem("100","�ò�����֯û�л�ƿ�Ŀ!"));
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
		
		//������ʱ��
		TempTablePool pool=TempTablePool.getInstance(ctx);
		
		String createSql="create table temptable (fcostaccountid varchar(44),faccountid varchar(44),finvoiceaccountid varchar (44), fcompanyid varchar(44))";
		String temptable=null;
		try {
			temptable = pool.createTempTable(createSql);
			String desc="������������";
			//2.���¼�������Ŀ��û�����ö�Ӧ��ϵ��ģ���������뵽����Ŀ��
			builder.appendSql("insert into "+temptable+" (fcostaccountid,faccountid,finvoiceaccountid,fcompanyid)  \n"); //��Ҫ�����ģ�����ݻ�������ʱ��
			builder.appendSql("( \n"); 
			builder.appendSql("select prjCostAcct.fid,acct.fid,invoiceAcct.fid,acct.fcompanyid from T_FDC_CostAccountWithAccount withAcct \n"); //ȡģ������  
			builder.appendSql("inner join T_FDC_CostAccount groupCostAcct on groupCostAcct.fid=withAcct.fcostaccountid   \n"); //ģ�����ݶ�Ӧ�ĳɱ���Ŀ
			builder.appendSql("inner join T_FDC_CostAccount prjCostAcct on prjCostAcct.flongnumber=groupCostAcct.flongnumber \n");	//��ǰҪ����Ĺ�����Ŀ�ɱ���Ŀ��ģ�����ݳɱ���Ŀ������Ҫƥ��
			builder.appendSql("inner join T_FDC_CurProject prj on prj.fid=prjCostAcct.fcurProject  \n"); //��ǰҪ����Ĺ�����Ŀ�ĳɱ���Ŀ
			builder.appendSql("left outer join T_BD_AccountView groupAcct on groupAcct.fid=withAcct.faccountid  \n"); //ģ���ƿ�Ŀ
			builder.appendSql("left outer join T_BD_AccountView groupInvoiceAcct on groupInvoiceAcct.fid=withAcct.finvoiceAccountid  \n"); //ģ�巢Ʊ��Ŀ 
			builder.appendSql("left outer join T_BD_AccountView acct on acct.flongnumber=groupAcct.flongnumber   and prj.ffullorgunit=acct.fcompanyid and acct.faccounttableid=? \n"); //ģ���ƿ�Ŀ�������뵱ǰҪ����Ĺ�����Ŀ������֯IDȷ��Ҫ����Ļ�ƿ�Ŀ
			builder.appendSql("left outer join T_BD_AccountView invoiceAcct on invoiceAcct.flongnumber=groupInvoiceAcct.flongnumber   and prj.ffullorgunit=invoiceAcct.fcompanyid and invoiceAcct.faccounttableid=?  \n"); //ģ�巢Ʊ��Ŀ�������뵱ǰҪ����Ĺ�����Ŀ������֯IDȷ��Ҫ����ķ�Ʊ��Ŀ
			builder.appendSql("where groupCostAcct.ffullorgunit=? and \n"); // ���ųɱ���Ŀ����ȷ��ģ������
			builder.addParam(tableInfo.getId().toString());
			builder.addParam(tableInfo.getId().toString());
			//--����CU  
			builder.addParam(OrgConstants.DEF_CU_ID);
			//--���Ϲ�����Ŀ�Ĺ�������
			builder.appendParam("prjCostAcct.fcurProject", nodeIdSet.toArray());
			//--���˵���Ŀ���Ѿ����ڵĳɱ���Ŀ
			builder.appendSql("and not exists (select 1 from T_FDC_CostAccountWithAccount where fcostaccountid=prjCostAcct.fid) \n");
			//R101228-259 �ɱ���Ŀ���ƿ�Ŀ��Ӧ��ϵ���뱨�� ���޸ĳ�ֻ�������֯�´��ڶ�Ӧ��ƿ�Ŀ����Ʊ��Ŀ���ļ�¼  by zhiyuan_tang 2011-01-21
			builder.appendSql(" and (acct.fid is not null or invoiceAcct.fid is not null ) \n");
			builder.appendSql(") \n");
			builder.execute();

			//3.����ÿ�Ŀ����ϸ��ƿ�Ŀȡ��ϸ��ƿ�Ŀ
			builder.clear();
			//��ƿ�Ŀ
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
			
			//��Ʊ��Ŀ
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
			
			//4.��������:����ʱ���н��������뵽T_FDC_CostAccountWithAccount
			builder.clear();
			builder.appendSql("insert into T_FDC_CostAccountWithAccount (fid,fcostaccountid,faccountid,finvoiceaccountid,fdescription_l2) (select newbosid('256C7E39'),fcostaccountid,faccountid,finvoiceaccountid,'"+desc+"' from "+temptable+" order by fcompanyid) \n");
			builder.execute();

			//TODO ͬ��δ���õĿ�Ŀ
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

		//���ɶ�Ӧ��ϵȫ��
		Map param=new HashMap();
		for(Iterator iter=nodeIdSet.iterator();iter.hasNext();){
			String prjId=(String)iter.next();
			param.put("PROJECTID", prjId);
			CostAccountWithAcctFacadeFactory.getLocalInstance(ctx).update(param);
		}
		//��ʹ���µ��㷨���Ż�����
		if(true){
			return;
		}
		//����Ĵ����ѷ������Ժ�ɾ�� by sxhong 2009-05-18 18:14:31
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
			
			//��ȡ��ǰ��Ŀ���ڲ�����֯�Ļ�ƿ�Ŀ
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
				throw new EASBizException(new NumericExceptionSubItem("100","�ò�����֯û�л�ƿ�Ŀ!"));
			}else{
				for (int i = 0; i < avc.size(); i++) {
					accountViewInfo=avc.get(i);
					if(accountViewInfo != null && accountViewInfo.isIsLeaf()){
						
						//ͨ��sql��ѯ��ȡ����ϸ�Ļ�ƿ�Ŀ,
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

			//��ȡ��ǰ��Ŀ���еĳɱ���Ŀ��Ӧ��ƿ�Ŀ����
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
			
			//��ȡ�����µĿ�Ŀ��Ӧģ��
			FilterInfo filter = new FilterInfo();//������֯����
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
			
			//���ɱ���Ŀ����map�У���λ�ɱ���Ŀ�ĳ�����
			set = new HashSet();
			HashMap map = new HashMap();
			for (int i = 0; i < costAccountWithAccountCollection.size(); i++) 
			{
				accountViewInfo=costAccountWithAccountCollection.get(i).getAccount();
				
				//ͨ��sql��ѯ��ȡ����ϸ�Ļ�ƿ�Ŀ,
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
				info.setDescription("������������");
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
		
		//���ھɼ�������Ķ���,���¼�������û�е�,Ҫɾ��
		//�ĳ�����ɾ������Ӵ������
		CostAccountWithAccountFactory.getLocalInstance(ctx).delete(filter);

		//������߸����¼��϶���
		CostAccountWithAccountInfo tempInfo=new CostAccountWithAccountInfo();
		IORMappingDAO dao=ORMappingDAO.getInstance(tempInfo.getBOSType(), ctx, getConnection(ctx));
		for (int i = 0; i <size; i++) {
			CostAccountWithAccountInfo info = (CostAccountWithAccountInfo)objCol.getObject(i);
				//ʹ�������ӿ�  by sxhong 2009-07-08 16:48:39
//				_submit(ctx,info);
				dao.addNewBatch(info);
		}
		dao.executeBatch();
		
		//�������ڳ�ʼ������
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
					isProject=false;//�Ѿ�������ط������ˣ���Ҫ�ڸ�����
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