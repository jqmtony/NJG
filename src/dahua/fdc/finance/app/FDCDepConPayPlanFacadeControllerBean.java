package com.kingdee.eas.fdc.finance.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.PayPlanCycleEnum;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.finance.ContractPayPlanCollection;
import com.kingdee.eas.fdc.finance.ContractPayPlanFactory;
import com.kingdee.eas.fdc.finance.ContractPayPlanInfo;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanBillCollection;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanBillFactory;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanBillInfo;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanEntryInfo;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanItemCollection;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanItemInfo;
import com.kingdee.eas.fdc.finance.IContractPayPlan;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.jdbc.rowset.IRowSet;

public class FDCDepConPayPlanFacadeControllerBean extends AbstractFDCDepConPayPlanFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.finance.app.FDCDepConPayPlanFacadeControllerBean");

	/**
	 * 工程项目明细科目长编码
	 * @param ctx 
	 */
	private Set getleafCostAccountNumber(String prjID, Context ctx) throws BOSException {
		Set set = new HashSet();
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("longnumber");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		view.getFilter().getFilterItems().add(new FilterItemInfo("curProject.id", prjID));
		view.getFilter().getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.TRUE));
		view.getFilter().getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		CostAccountCollection costAccountCollection = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(view);
		for (int i = 0; i < costAccountCollection.size(); i++) {
			set.add(costAccountCollection.get(i).getLongNumber());
		}
		return set;
	}

	/**
	 * 描述：如果项目分期中科目在非明细项目中不存在，返回上级科目编码
	 * @param aimCostMap 
	 * @param bigDecimal 
	 * @Author：keyan_zhao
	 * @CreateTime：2012-11-28
	 */
	private String getMapUpCostAccount(String longNumber, Set set) {
		for (Iterator ite = set.iterator(); ite.hasNext();) {
			String lNumber = (String) ite.next();
			if (longNumber.startsWith(lNumber)) {
				return lNumber;
			}
		}
		return null;
	}

	private String SetConvertToString(Set idSet) {

		if (idSet == null || idSet.isEmpty()) {
			return "";
		}
		StringBuffer filter = new StringBuffer();
		filter.append("( ");
		Iterator iter = idSet.iterator();
		int i = 0;
		int size = idSet.size();
		while (iter.hasNext()) {
			String id = (String) iter.next();
			filter.append("'").append(id).append("'");
			if (i < size - 1) {
				filter.append(",");
			}
			i++;
		}
		filter.append(" ) ");
		return filter.toString();
	}

	/**
	 * 项目付款计划执行表取数
	 * @param prjIds
	 * @param param
	 * @return
	 */
    protected Map _getProjectPayPlanExeData(Context ctx, Set prjIds, Map param)throws BOSException, EASBizException
    {
    	Map retMap = new HashMap();
    	if(prjIds.size()==0){
    		throw new NullPointerException("cann't get prjIds");
    	}
    	String proID = (String) param.get("proID");
		Set accountNumber = getleafCostAccountNumber(proID, ctx);
    	Integer startYear = ((Integer)param.get("startYear"));
        Integer startMonth =  ((Integer)param.get("startMonth"));
    	Integer endYear = ((Integer)param.get("endYear"));
    	Integer endMonth =  ((Integer)param.get("endMonth"));
    	Date startDate = new Date();
    	startDate.setYear(startYear.intValue()-1900);
    	startDate.setMonth(startMonth.intValue()-1);
    	
    	Date endDate = new Date();
    	endDate.setYear(startYear.intValue()-1900);
    	endDate.setMonth(startMonth.intValue()-1);
    	
    	//实际数
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("select costaccountid,sum(splitAmt) splitAmt,year,month from ( \n");
    	builder
				.appendSql("select acc.flongnumber costaccountid,entry.fpayedamt splitAmt,year(pay.fbizDate) year,month(pay.fbizDate) month from t_fnc_paymentsplitentry entry \n");
		builder.appendSql("left join t_fdc_costaccount acc on acc.fid= entry.fcostaccountid \n");
    	builder.appendSql("inner join t_fnc_paymentsplit split on split.fid=entry.fparentid \n");
    	builder.appendSql("inner join t_cas_paymentbill pay on pay.fid=split.fpaymentbillid \n");
    	builder.appendSql("where ");
    	builder.appendSql("pay.fcurprojectid in " + SetConvertToString(prjIds));
		builder.appendSql(" and acc.fcurproject in " + SetConvertToString(prjIds));
    	
    	//add by zkyan 合同付款拆分到产品类型的时候，项目资金计划执行情况表中“时间支付金额”翻倍。and  entry.fproductid is not null 

		builder.appendSql(" and entry.fcostaccountid is not null and  entry.fproductid is  null ");
    	builder.appendSql(" and \n");
    	if (startYear.equals(endYear)) {
			builder.appendSql("year(pay.fbizDate)=" + startYear
					+ " and month(pay.fbizDate)>= " + startMonth
					+ " and month(pay.fbizDate)<=" + endMonth + " \n");
		} else {
			builder.appendSql("((year(pay.fbizDate)=" + startYear
					+ " and month(pay.fbizDate)>=" + startMonth+")");
			builder.appendSql(" or ");
			builder.appendSql("(year(pay.fbizDate)=" + endYear + " and month(pay.fbizDate)<="
					+ endMonth + ")) \n");
		}
    	builder.appendSql(")t group by costaccountid,year,month \n");
    	IRowSet rs = builder.executeQuery();
    	try{
    		while(rs.next()){
    			String key = "split"+rs.getInt("year")+rs.getInt("month");
    			BigDecimal splitAmt = rs.getBigDecimal("splitAmt");
    			key = rs.getString("costaccountid") + key;

				String longNumber = rs.getString("costaccountid");
				if (accountNumber.contains(longNumber)) {
					if (retMap.containsKey(key)) {
						Object object = retMap.get(key);
						retMap.put(key, FDCHelper.add(object, splitAmt));
					} else {
						retMap.put(key, splitAmt);
					}
				} else {
					longNumber = getMapUpCostAccount(longNumber, accountNumber);
					key = longNumber + "split" + rs.getInt("year") + rs.getInt("month");
					if (retMap.containsKey(key)) {
						Object object = retMap.get(key);
						retMap.put(key, FDCHelper.add(object, splitAmt));
					} else {
						retMap.put(key, splitAmt);
					}
				}
    			
    		}
    	}catch(SQLException e){
    		throw new BOSException(e); 
    	}
    	
    	// 计划数
    	builder.clear();

		// 之前只取已签订合同拆分，现在添加待签订合同、无合同拆分 by emanon
		builder.appendSql(" select a.costaccountid as costaccountid,a.year as year,a.month as month,sum(a.planAmt) as planAmt from ");
		builder.appendSql(" (select ");
		builder.appendSql("  acc.Flongnumber as costaccountid,  ");
		builder.appendSql(" year(he.FMonth) as year, ");
		builder.appendSql(" month(he.FMonth) as month, ");
		builder.appendSql(" he.FSptPay as planAmt ");
		builder.appendSql(" from T_FNC_FDCProDepSplit as split ");
		builder.appendSql(" left join T_FNC_FDCProDepSplitHasCon as hc on hc.FHeadID = split.FID ");
		builder.appendSql(" left join t_fdc_costaccount as acc on acc.fid=hc.FCostAccountID ");
		builder.appendSql(" left join T_FNC_FDCProDepSplitHasConEty as he on he.FParentID = hc.FID ");
		builder.appendSql(" left join T_FNC_FDCProDepConPlan as pp on pp.FID = FFDCProDep ");

		//--begin （已签定合同 ） 若多个资金计划有重复的月份，取最新计划的月份 	
		
		/*  以下是截取的语句段 （已签定合同 ），可以在查询分析器中调试用到
			select a.costaccountid as costaccountid,a.year as year,a.month as month,sum(a.planAmt) as planAmt from  
			
				(select  hc.FCostAccountID as costaccountid,   year(he.FMonth) as year,  month(he.FMonth) as month,  he.FSptPay as planAmt  
				from T_FNC_FDCProDepSplit as split 
				 left join T_FNC_FDCProDepSplitHasCon as hc on hc.FHeadID = split.FID 
				 left join T_FNC_FDCProDepSplitHasConEty as he on he.FParentID = hc.FID 
				 left join T_FNC_FDCProDepConPlan as pp on pp.FID = split.FFDCProDep
				 inner join (  select year,month,max(totalMonth) tm  from  
					(select pp.fyear*12+pp.fmonth as totalMonth,YEAR(he.FMonth) as year, MONTH(he.FMonth) as month  
					from T_FNC_FDCProDepSplit as split
					left join T_FNC_FDCProDepSplitHasCon as hc on hc.FHeadID = split.FID 
					left join T_FNC_FDCProDepSplitHasConEty as he on he.FParentID = hc.FID  
					left join T_FNC_FDCProDepConPlan as pp on pp.FID = split.FFDCProDep  
					where hc.FCostAccountID is not null  and split.FState = '4AUDITTED'  ) b  group by year, month )bb  --bb 即 包含最新计划的总月份，tm
			 	on (bb.year = YEAR(he.FMonth)  and Month(he.FMonth) = bb.month and bb.tm = (pp.fyear*12+pp.fmonth))  -- 用bb.tm = (pp.fyear*12+pp.fmonth)表示pp是最新的计划
				where hc.FCostAccountID is not null  and split.FState = '4AUDITTED' ) as a  
			
			where (a.year > 2011 or ( a.year = 2011 and a.month >=  6 ))
			 and ( a.year < 2011 or ( a.year = 2011 and a.month <=  9))
			 group by a.costaccountid,a.year,a.month  order by a.costaccountid 
		 */
		
		builder.appendSql(" inner join ( ");
		builder.appendSql(" select year,month,max(totalMonth) tm  from ");
		builder.appendSql(" (select pp.fyear*12+pp.fmonth as totalMonth,YEAR(he.FMonth) as year, MONTH(he.FMonth) as month ");
		builder.appendSql(" from T_FNC_FDCProDepSplit as split ");
		builder.appendSql(" left join T_FNC_FDCProDepSplitHasCon as hc on hc.FHeadID = split.FID ");
		builder.appendSql(" left join T_FNC_FDCProDepSplitHasConEty as he on he.FParentID = hc.FID ");
		builder.appendSql(" left join T_FNC_FDCProDepConPlan as pp on pp.FID = split.FFDCProDep ");
		builder.appendSql(" where hc.FCostAccountID is not null ");
		builder.appendSql(" and split.FState = '4AUDITTED' ");
		builder.appendSql(" ) b ");
		builder.appendSql(" group by year, month )bb ");
		 // bb.tm = (pp.fyear*12+pp.fmonth) 表示 pp计划是最新的计划
		builder.appendSql(" on (bb.year = YEAR(he.FMonth) and bb.month = Month(he.FMonth) and bb.tm = (pp.fyear*12+pp.fmonth))");
		// -- end
		
		builder.appendSql(" where hc.FCostAccountID is not null ");
		builder.appendSql(" and split.FState = '4AUDITTED' ");
		builder.appendSql(" and acc.fcurproject in " + SetConvertToString(prjIds));

		builder.appendSql(" Union All ");
		builder.appendSql(" select ");
		builder.appendSql("  acc.Flongnumber as costaccountid, ");
		builder.appendSql(" year(he.FMonth) as year, ");
		builder.appendSql(" month(he.FMonth) as month, ");
		builder.appendSql(" he.FSptPay as planAmt ");
		builder.appendSql(" from T_FNC_FDCProDepSplit as split ");
		builder.appendSql(" left join T_FNC_FDCProDepSplitUnCon as hc on hc.FHeadID = split.FID ");
		builder.appendSql(" left join t_fdc_costaccount as acc on acc.fid=hc.FCostAccountID ");
		builder.appendSql(" left join T_FNC_FDCProDepSplitUnConEntry as he on he.FParentID = hc.FID ");
		builder.appendSql(" left join T_FNC_FDCProDepConPlan as pp on pp.FID = FFDCProDep ");

		//--begin （待签订合同 ） 若多个资金计划有重复的月份，取最新计划的月份 	
		builder.appendSql(" inner join ( ");
		builder.appendSql(" select year,month,max(totalMonth) tm  from ");
		builder.appendSql(" (select pp.fyear*12+pp.fmonth as totalMonth,YEAR(he.FMonth) as year, MONTH(he.FMonth) as month ");
		builder.appendSql(" from T_FNC_FDCProDepSplit as split ");
		builder.appendSql(" left join T_FNC_FDCProDepSplitUnCon as hc on hc.FHeadID = split.FID ");
		builder.appendSql(" left join T_FNC_FDCProDepSplitUnConEntry as he on he.FParentID = hc.FID ");
		builder.appendSql(" left join T_FNC_FDCProDepConPlan as pp on pp.FID = split.FFDCProDep ");
		builder.appendSql(" where hc.FCostAccountID is not null ");
		builder.appendSql(" and split.FState = '4AUDITTED' ");
		builder.appendSql(" ) b ");
		builder.appendSql(" group by year, month )bb ");
		builder.appendSql(" on (bb.year = YEAR(he.FMonth)  and bb.month = Month(he.FMonth) and bb.tm = (pp.fyear*12+pp.fmonth))");
		// -- end
		
		builder.appendSql(" where hc.FCostAccountID is not null ");
		builder.appendSql(" and split.FState = '4AUDITTED' ");
		builder.appendSql(" and acc.fcurproject in " + SetConvertToString(prjIds));

		builder.appendSql(" Union All ");
		
    	builder.appendSql(" select  acc.Flongnumber as costaccountid, ");
		builder.appendSql(" year(he.FMonth) as year, month(he.FMonth) as month, he.FSptPay as planAmt ");
    	builder.appendSql(" from T_FNC_FDCProDepSplit as split ");
		builder.appendSql(" left join T_FNC_FDCProDepSplitNoCon as hc on hc.FHeadID = split.FID ");
		builder.appendSql(" left join t_fdc_costaccount as acc on acc.fid=hc.FCostAccountID ");
		builder.appendSql(" left join T_FNC_FDCProDepSplitNoConEntry as he on he.FParentID = hc.FID ");
		builder.appendSql(" left join T_FNC_FDCProDepConPlan as pp on pp.FID = FFDCProDep ");
    	
    	//--begin  （无合同）若多个资金计划有重复的月份，取最新计划的月份 	
		builder.appendSql(" inner join ( ");
		builder.appendSql(" select year,month,max(totalMonth) tm  from ");
		builder.appendSql(" (select pp.fyear*12+pp.fmonth as totalMonth,YEAR(he.FMonth) as year, MONTH(he.FMonth) as month ");
		builder.appendSql(" from T_FNC_FDCProDepSplit as split ");
		builder.appendSql(" left join T_FNC_FDCProDepSplitNoCon as hc on hc.FHeadID = split.FID ");
		builder.appendSql(" left join T_FNC_FDCProDepSplitNoConEntry as he on he.FParentID = hc.FID ");
		builder.appendSql(" left join T_FNC_FDCProDepConPlan as pp on pp.FID = split.FFDCProDep ");
		builder.appendSql(" where hc.FCostAccountID is not null ");
		builder.appendSql(" and split.FState = '4AUDITTED' ");
		builder.appendSql(" ) b ");
		builder.appendSql(" group by year, month )bb ");
		builder.appendSql(" on (bb.year = YEAR(he.FMonth) and bb.month = Month(he.FMonth) and bb.tm = (pp.fyear*12+pp.fmonth))");
		// -- end
    	
    	builder.appendSql(" where hc.FCostAccountID is not null ");
		builder.appendSql(" and split.FState = '4AUDITTED' ");
		builder.appendSql(" and acc.fcurproject in " + SetConvertToString(prjIds));
		builder.appendSql(" ) as a ");
		builder.appendSql(" where (a.year > ").appendSql(startYear.toString());
		builder.appendSql(" or ( a.year = ").appendSql(startYear.toString());
		builder.appendSql(" and a.month >=  ").appendSql(startMonth.toString());
		builder.appendSql(" )) and ( a.year < ").appendSql(endYear.toString());
		builder.appendSql(" or ( a.year = ").appendSql(endYear.toString());
		builder.appendSql(" and a.month <=  ").appendSql(endMonth.toString()).appendSql("))");
		builder.appendSql(" group by a.costaccountid,a.year,a.month ");
		builder.appendSql(" order by a.costaccountid ");
    	

    	rs = builder.executeQuery();
		try {
			while (rs.next()) {
				String key = "plan" + rs.getInt("year") + rs.getInt("month");
				BigDecimal planAmt = rs.getBigDecimal("planAmt");
				
				key = rs.getString("costaccountid") + key;

				String longNumber = rs.getString("costaccountid");
				if (accountNumber.contains(longNumber)) {
					if (retMap.containsKey(key)) {
						Object object = retMap.get(key);
						retMap.put(key, FDCHelper.add(object, planAmt));
					} else {
						retMap.put(key, planAmt);
					}
				} else {
					longNumber = getMapUpCostAccount(longNumber, accountNumber);
					key = longNumber + "split" + rs.getInt("year") + rs.getInt("month");
					if (retMap.containsKey(key)) {
						Object object = retMap.get(key);
						retMap.put(key, FDCHelper.add(object, planAmt));
					} else {
						retMap.put(key, planAmt);
					}
				}
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		return retMap;
    }
    
    /**
     * 同步合同付款计划,原则：审批与反审批都要保证取部门合同付款计划的审批状态的最新月份数据(详细的描述有空再补充)
     * 循环太多，后续再优化
     * 
     * @author hpw Date:2010-03-04
     * 
     * @param id
     * @param isAudit 是否审批
     */
	protected void _autoUpdateConPayPlan(Context ctx, String id, boolean isAudit)
			throws BOSException, EASBizException {
		if(id==null){
			return;
		}
		
		//当月
		FDCDepConPayPlanBillInfo info = null;
		//上一月
		FDCDepConPayPlanBillInfo preInfo = null;
		//下一月
		FDCDepConPayPlanBillInfo nextInfo = null;
		
		int cycle =0;
		info = getFDCDepConPayPlanInfo(ctx, id, info, 0, 0,0,0);
//		deptId = info.getDeptment().getId().toString();
		
		preInfo = getFDCDepConPayPlanInfo(ctx, id, info, -1,0,info.getYear(),info.getMonth());
		
		nextInfo = getFDCDepConPayPlanInfo(ctx, id, info, 1,0,info.getYear(),info.getMonth());
		
//		if(info.getEntrys()==null||info.getEntrys().size()==0){
//			return;
//		}
		
		// 1.取出所有已签定合同分录
		Set conIds = new HashSet();
		Map entryMap = new HashMap();
//		for(Iterator iter=info.getEntrys().iterator();iter.hasNext();){
//			FDCDepConPayPlanEntryInfo entryInfo = (FDCDepConPayPlanEntryInfo)iter.next();
//			if(entryInfo.isIsUnsettledCon()){
//				continue;
//			}
//			String contractId = entryInfo.getContractBillId();
//			if(contractId!=null){
//				conIds.add(contractId);
//			}
//			entryMap.put(contractId, entryInfo);
//		}
		
		Map entryNextMap = new HashMap();
		Map entryPreMap = new HashMap();

//		if (nextInfo != null && nextInfo.getEntrys() != null
//				&& nextInfo.getEntrys().size() != 0) {
//			for (Iterator iter = nextInfo.getEntrys().iterator(); iter
//					.hasNext();) {
//				FDCDepConPayPlanEntryInfo entryInfo = (FDCDepConPayPlanEntryInfo) iter
//						.next();
//				if (entryInfo.isIsUnsettledCon()) {
//					continue;
//				}
//				String contractId = entryInfo.getContractBillId();
//				if (entryInfo.getItems() != null
//						&& entryInfo.getItems().size() > 0) {
//					for (Iterator iter2 = entryInfo.getItems().iterator(); iter2
//							.hasNext();) {
//						FDCDepConPayPlanItemInfo item = (FDCDepConPayPlanItemInfo) iter2
//								.next();
//						// 空记录
//						if (isEmpty(item.getAuditAmt(), item.getAuditProp())) {
//							continue;
//						}
//						String key = contractId + item.getYear() + "_"
//								+ (item.getMonth());
//						entryNextMap.put(key, item);
//					}
//				}
//			}
//		}
//		if (!isAudit) {
//			if (preInfo != null && preInfo.getEntrys() != null
//					&& preInfo.getEntrys().size() != 0) {
//				for (Iterator iter = preInfo.getEntrys().iterator(); iter
//						.hasNext();) {
//					FDCDepConPayPlanEntryInfo entryInfo = (FDCDepConPayPlanEntryInfo) iter
//							.next();
//					if (entryInfo.isIsUnsettledCon()) {
//						continue;
//					}
//					String contractId = entryInfo.getContractBillId();
//					if (entryInfo.getItems() != null
//							&& entryInfo.getItems().size() > 0) {
//						for (Iterator iter2 = entryInfo.getItems().iterator(); iter2
//								.hasNext();) {
//							FDCDepConPayPlanItemInfo item = (FDCDepConPayPlanItemInfo) iter2
//									.next();
//							// 空记录
//							if (isEmpty(item.getAuditAmt(), item.getAuditProp())) {
//								continue;
//							}
//							String key = contractId + item.getYear() + "_"
//									+ (item.getMonth());
//							entryPreMap.put(key, item);
//						}
//					}
//				}
//			}
//		}
		
		
		// 2.找出分录的合同，已有的合同付款计划的
		Map conPayPlanMap = getContractPayPlanMap(ctx, conIds);
		
		// 3.当反审批时，删除本单据分录月份的合同付款计划数据，如果上一个月的部门合同付款计划中
		// 4.合同信息
		Map conMap = getContractBillMap(ctx, conIds);

		// 5.
		CoreBaseCollection newPayPlanColls = new CoreBaseCollection();// 新增的计划
		CoreBaseCollection updatePayPlanColls = new CoreBaseCollection();// 需要更新的计划
		CoreBaseCollection deletePayPlanColls = new CoreBaseCollection(); // 需要删除的计划
		Set deleteIds = new HashSet();
		
		for (Iterator iter = conMap.keySet().iterator(); iter.hasNext();) {
			String contractId = (String) iter.next();
			FDCDepConPayPlanEntryInfo entryInfo = (FDCDepConPayPlanEntryInfo) entryMap
					.get(contractId);
			if(entryInfo==null||entryInfo.getItems()==null||entryInfo.getItems().size()==0){
				continue;
			}
			ContractBillInfo conInfo = (ContractBillInfo) conMap
					.get(contractId);
			if(isAudit){
				if (conPayPlanMap.containsKey(contractId)) {
					updatePayPlanInfos(newPayPlanColls, updatePayPlanColls, conPayPlanMap,info, entryMap, entryInfo, conInfo,entryNextMap);
				} else {
					addNewPayPlanInfos(newPayPlanColls, info, entryInfo, conInfo);
				}
			}else{
				deletePayPlanInfos(deleteIds,updatePayPlanColls, conPayPlanMap,info,entryMap,entryInfo,conInfo,entryNextMap,entryPreMap);
			}
		}
		IContractPayPlan iContractPayPlan = ContractPayPlanFactory.getLocalInstance(ctx);
		if(newPayPlanColls.size()>0){
			iContractPayPlan.addnew(newPayPlanColls);
		}
		if(updatePayPlanColls.size()>0){
			// 支持集合更新？
			iContractPayPlan.update(updatePayPlanColls);
		}
		if(deleteIds.size()>0){
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id",deleteIds, CompareType.INCLUDE));
			iContractPayPlan.delete(filter);
		}
	}
	
	private void deletePayPlanInfos(Set deleteIds, CoreBaseCollection updatePayPlanColls, Map conPayPlanMap, FDCDepConPayPlanBillInfo depInfo, Map entryMap, FDCDepConPayPlanEntryInfo entryInfo, ContractBillInfo conInfo, Map entryNextMap, Map entryPreMap) {
		Map infosMap = (Map)conPayPlanMap.get(conInfo.getId().toString());
		for (Iterator iter = entryInfo.getItems().iterator(); iter.hasNext();) {
			FDCDepConPayPlanItemInfo item = (FDCDepConPayPlanItemInfo) iter
					.next();
			String key = item.getYear()+"_"+(item.getMonth());
			String entryKey = conInfo.getId().toString()+key;
			if(entryNextMap.containsKey(entryKey)){
				continue;
			}
			if(entryPreMap.containsKey(entryKey)){
				FDCDepConPayPlanItemInfo preItem = (FDCDepConPayPlanItemInfo)entryPreMap.get(entryKey);
				if(preItem==null){
					continue;
				}
				// 空记录
				if (isEmpty(preItem.getAuditAmt(), preItem.getAuditProp())) {
					continue;
				}
				ContractPayPlanInfo oldInfo = (ContractPayPlanInfo)infosMap.get(key);
				oldInfo.setCompany((CompanyOrgUnitInfo) conInfo
						.getCurProject().getFullOrgUnit().cast(
								CompanyOrgUnitInfo.class));
				oldInfo.setPayAmount(preItem.getAuditAmt());
				oldInfo.setPayProportion(preItem.getAuditProp());
				BigDecimal payOriAmount = FDCHelper.divide(preItem.getAuditAmt(),
						conInfo.getExRate(), 2, BigDecimal.ROUND_HALF_UP);
				oldInfo.setPayOriAmount(payOriAmount);
				
				updatePayPlanColls.add(oldInfo);
			}else{
				if (infosMap == null) {
					continue;
				}
				ContractPayPlanInfo oldInfo = (ContractPayPlanInfo)infosMap.get(key);
				if(oldInfo!=null){
					deleteIds.add(oldInfo.getId().toString());
				}
			}
		}
	}

	private void updatePayPlanInfos(CoreBaseCollection newPayPlanColls,
			CoreBaseCollection updatePayPlanColls, Map conPayPlanMap,
			FDCDepConPayPlanBillInfo depInfo, Map entryMap, FDCDepConPayPlanEntryInfo entryInfo, ContractBillInfo conInfo, Map entryNextMap) {
		String desc = "部门合同付款计划 \""+depInfo.getNumber()+"\" 生成";
		Map infosMap = (Map)conPayPlanMap.get(conInfo.getId().toString());
		
		for (Iterator iter = entryInfo.getItems().iterator(); iter.hasNext();) {
			FDCDepConPayPlanItemInfo item = (FDCDepConPayPlanItemInfo) iter
					.next();
			// 空记录不生成
			if (isEmpty(item.getAuditAmt(), item.getAuditProp())) {
				continue;
			}
			
			String key = item.getYear()+"_"+(item.getMonth());
			String entryKey = conInfo.getId().toString()+key;
			if(entryNextMap.containsKey(entryKey)){
				continue;
			}
			if(infosMap.containsKey(key)){
				// 存在时重新设置金额
				ContractPayPlanInfo oldInfo = (ContractPayPlanInfo)infosMap.get(key);
				oldInfo.setCompany((CompanyOrgUnitInfo) conInfo
						.getCurProject().getFullOrgUnit().cast(
								CompanyOrgUnitInfo.class));
				oldInfo.setPayAmount(item.getAuditAmt());
				oldInfo.setPayProportion(item.getAuditProp());
				BigDecimal payOriAmount = FDCHelper.divide(item.getAuditAmt(),
						conInfo.getExRate(), 2, BigDecimal.ROUND_HALF_UP);
				oldInfo.setPayOriAmount(payOriAmount);
				
				updatePayPlanColls.add(oldInfo);
			}else{
				ContractPayPlanInfo newPayPlanInfo = new ContractPayPlanInfo();
				newPayPlanInfo.setCompany((CompanyOrgUnitInfo) conInfo
						.getCurProject().getFullOrgUnit().cast(
								CompanyOrgUnitInfo.class));
				newPayPlanInfo.setCurProject(conInfo.getCurProject());
				newPayPlanInfo.setContractId(conInfo);
				newPayPlanInfo.setCurrecy(conInfo.getCurrency());
				newPayPlanInfo.setExchangeRate(conInfo.getExRate());
				newPayPlanInfo.setPayAmount(item.getAuditAmt());
				newPayPlanInfo.setPayProportion(item.getAuditProp());
				BigDecimal payOriAmount = FDCHelper.divide(item.getAuditAmt(),
						conInfo.getExRate(), 2, BigDecimal.ROUND_HALF_UP);
				newPayPlanInfo.setPayOriAmount(payOriAmount);
				java.util.Date payDate = new java.util.Date();
				payDate.setYear(item.getYear()-1900);
				payDate.setMonth(item.getMonth()-1);
				newPayPlanInfo.setPayDate(payDate);
				newPayPlanInfo.setDescription(desc);
				
				newPayPlanColls.add(newPayPlanInfo);
			}
			
		}
		
	}

	private void addNewPayPlanInfos(CoreBaseCollection newPayPlanColls,FDCDepConPayPlanBillInfo depInfo,
			FDCDepConPayPlanEntryInfo entryInfo, ContractBillInfo conInfo) {
		FDCDepConPayPlanItemCollection items = entryInfo.getItems();
		if (items == null || items.size() == 0) {
			return;
		}
		String desc = "部门合同付款计划 \"" + depInfo.getNumber() + "\" 生成";
		for (Iterator iter = items.iterator(); iter.hasNext();) {
			FDCDepConPayPlanItemInfo item = (FDCDepConPayPlanItemInfo) iter
					.next();
			// 空记录不生成
			if (isEmpty(item.getAuditAmt(), item.getAuditProp())) {
				continue;
			}
			ContractPayPlanInfo newPayPlanInfo = new ContractPayPlanInfo();
			newPayPlanInfo.setCompany((CompanyOrgUnitInfo) conInfo
					.getCurProject().getFullOrgUnit().cast(
							CompanyOrgUnitInfo.class));
			newPayPlanInfo.setCurProject(conInfo.getCurProject());
			newPayPlanInfo.setContractId(conInfo);
			newPayPlanInfo.setCurrecy(conInfo.getCurrency());
			newPayPlanInfo.setExchangeRate(conInfo.getExRate());
			newPayPlanInfo.setPayAmount(item.getAuditAmt());
			newPayPlanInfo.setPayProportion(item.getAuditProp());
			BigDecimal payOriAmount = FDCHelper.divide(item.getAuditAmt(),
					conInfo.getExRate(), 2, BigDecimal.ROUND_HALF_UP);
			newPayPlanInfo.setPayOriAmount(payOriAmount);
			java.util.Date payDate = new java.util.Date();
			payDate.setYear(item.getYear()-1900);
			payDate.setMonth(item.getMonth()-1);
			newPayPlanInfo.setPayDate(payDate);
			newPayPlanInfo.setDescription(desc);
			
			newPayPlanColls.add(newPayPlanInfo);
		}
	}
	
	private boolean isEmpty(BigDecimal amount, BigDecimal prop) {
		return FDCHelper.toBigDecimal(amount).compareTo(FDCHelper.ZERO) == 0
				&& FDCHelper.toBigDecimal(prop).compareTo(FDCHelper.ZERO) == 0;
	}
	
	private Map getContractPayPlanMap(Context ctx, Set ids) throws BOSException{
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractId.id", ids, CompareType.INCLUDE));
		view.setSelector(sic);
		view.setFilter(filter);
		ContractPayPlanCollection colls = ContractPayPlanFactory
				.getLocalInstance(ctx).getContractPayPlanCollection(view);
		
		Map retMap = new HashMap();
		Map infosMap = null;
		for(int i=0;i<colls.size();i++){
			ContractPayPlanInfo info = colls.get(i);
			String key = info.getPayDate().getYear()+1900+"_"+(info.getPayDate().getMonth()+1);
			if(retMap.containsKey(info.getContractId().getId().toString())){
				infosMap = (Map) retMap.get(info.getContractId().getId().toString());
				infosMap.put(key, info);
			}else{
				infosMap = new HashMap();
				infosMap.put(key, info);
				retMap.put(info.getContractId().getId().toString(), infosMap);
			}
		}
		return retMap;
	}
	
	private Map getContractBillMap(Context ctx,
			Set ids) throws BOSException {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("exRate");
		sic.add("currency");
		sic.add("curProject");
		sic.add("curProject.fullOrgUnit");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", ids, CompareType.INCLUDE));
		view.setSelector(sic);
		view.setFilter(filter);
		ContractBillCollection colls = ContractBillFactory
				.getLocalInstance(ctx).getContractBillCollection(view);
		Map retMap = new HashMap();
		for(int i=0;i<colls.size();i++){
			ContractBillInfo info = colls.get(i);
			retMap.put(info.getId().toString(), info);
		}
		return retMap;
	}
	
	private FDCDepConPayPlanBillInfo getFDCDepConPayPlanInfo(Context ctx,
			String id, FDCDepConPayPlanBillInfo billInfo, int type, int cycle,int year,int month) throws BOSException {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("number");
		sic.add("name");
		sic.add("deptment");
		sic.add("payPlanCycle.*");
		sic.add("year");
		sic.add("month");
		sic.add("state");

		sic.add("curProject.id");
		sic.add("curProject.name");
		sic.add("curProject.number");
		sic.add("curProject.codingNumber");
		sic.add("curProject.displayName");
		sic.add("curProject.fullOrgUnit.name");

		sic.add("period.number");
		sic.add("period.periodNumber");
		sic.add("period.beginDate");
		sic.add("period.periodYear");

		sic.add("entrys.*");
		sic.add("entrys.items.*");
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setSelector(sic);
		view.setFilter(filter);
		int enumValue = 0;
		int preYear =  year;
		int preMonth = month;
		int nextYear =  year;
		int nextMonth = month;
		if(type==0){
			filter.getFilterItems().add(new FilterItemInfo("id", id));
		}else if(type==-1){
			preMonth = preMonth -1;
			if(preMonth<1){
				preYear = preYear-1;
				preMonth = 12;
			}
			filter.getFilterItems().add(new FilterItemInfo("year", new Integer(preYear)));
			filter.getFilterItems().add(new FilterItemInfo("month", new Integer(preMonth)));
			filter.getFilterItems().add(new FilterItemInfo("deptment.id", billInfo.getDeptment().getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED));
			if(billInfo!=null&&billInfo.getPayPlanCycle()!=null&&billInfo.getPayPlanCycle().getCycle()!=null){
				PayPlanCycleEnum cycleEnum = billInfo.getPayPlanCycle().getCycle();
				enumValue = new Integer(cycleEnum+"").intValue();
			}
		}else if(type==1){
			nextMonth = nextMonth +1;
			if(nextMonth>12){
				nextYear = nextYear+1;
				nextMonth = 1;
			}
			filter.getFilterItems().add(new FilterItemInfo("year", new Integer(nextYear)));
			filter.getFilterItems().add(new FilterItemInfo("month", new Integer(nextMonth)));
			filter.getFilterItems().add(new FilterItemInfo("deptment.id", billInfo.getDeptment().getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED));
			if(billInfo!=null&&billInfo.getPayPlanCycle()!=null&&billInfo.getPayPlanCycle().getCycle()!=null){
				PayPlanCycleEnum cycleEnum = billInfo.getPayPlanCycle().getCycle();
				enumValue = new Integer(cycleEnum+"").intValue();
			}
		}
//		filter.getFilterItems().add(new FilterItemInfo("entrys.isUnsettledCon", Boolean.FALSE));
		FDCDepConPayPlanBillCollection infos = FDCDepConPayPlanBillFactory
				.getLocalInstance(ctx).getFDCDepConPayPlanBillCollection(view);
		FDCDepConPayPlanBillInfo info = null;
		info = infos.get(0);
		if(type==0){
			return info;
		}else if(type==-1&&info==null){
			cycle++;
			if(cycle<enumValue){
				return getFDCDepConPayPlanInfo(ctx, null, billInfo, type,cycle,preYear,preMonth);
			}else{
				return info;
			}
		}else if(type==1&&info==null){
			cycle++;
			if(cycle<enumValue){
				return getFDCDepConPayPlanInfo(ctx, null, billInfo, type,cycle,nextYear,nextMonth);
			}else{
				return info;
			}
		}
		return info;
	}
}