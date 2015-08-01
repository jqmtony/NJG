package com.kingdee.eas.fdc.contract.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.ExchangeAuxCollection;
import com.kingdee.eas.basedata.assistant.ExchangeAuxFactory;
import com.kingdee.eas.basedata.assistant.ExchangeAuxInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class ContractFacadeControllerBean extends AbstractContractFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.ContractFacadeControllerBean");
    
    protected Map _getContractNumberAndName(Context ctx, String id, boolean isWithOut)throws BOSException, EASBizException
    {
		SelectorItemCollection mainsic = new SelectorItemCollection();
		mainsic.add(new SelectorItemInfo("number"));
		mainsic.add(new SelectorItemInfo("name"));
		
		FDCBillInfo info = null;
		if(isWithOut){
			info = ContractWithoutTextFactory.getLocalInstance(ctx).getContractWithoutTextInfo(new ObjectUuidPK(BOSUuid.read(id)));
		}else{
			info = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(BOSUuid.read(id)));
		}
		
		Map map = new HashMap();
		map.put("contractNumber",info.getNumber());
		map.put("contractName",info.getName());
		map.put("info",info);
    	
        return map;
    }
    
    protected Map _getContractNumberAndNameMap(Context ctx, Map  idList )throws BOSException, EASBizException
    {
    	BOSObjectType  withoutTextContract=new ContractWithoutTextInfo().getBOSType();
    	Set contractIdList =		  new HashSet();
    	Set contractNotextIdList = new HashSet();  	
       	
       	Set set = idList.keySet();
       	Iterator it = set.iterator();
       	
		while (it.hasNext()) {
			String id = (String)it.next();
			if(BOSUuid.read(id).getType().equals(withoutTextContract)){
				contractNotextIdList.add(id);
			}else{
				contractIdList.add(id);	
			}
		}
		
    	Map contract = getContractNumberAndName( ctx, contractIdList , false);
    	Map contractNoText = getContractNumberAndName( ctx, contractNotextIdList , true);
    	 
    	if(contract!=null){
	    	if(contractNoText!=null){
	    		contract.putAll(contractNoText);
	    	}
    	}else{
    		contract = contractNoText;
    	}
    	 
    	return contract;
    }
    
    private Map getContractNumberAndName(Context ctx, Set idList , boolean isWithOut)throws BOSException, EASBizException
    {

		if(idList.size()==0){
			return null;
		}
		
//		EntityViewInfo view = new EntityViewInfo();
//		
//		SelectorItemCollection mainsic = view.getSelector();
//		mainsic.add(new SelectorItemInfo("number"));
//		mainsic.add(new SelectorItemInfo("name"));
//		
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("id", idList,	CompareType.INCLUDE));
//		view.setFilter(filter);
//		
//		FDCBillInfo info = null;
//		FDCBillCollection col = null;
		
		String sql = null;
		if(isWithOut){
			sql = "select fid,fnumber ,fname from t_con_contractWithoutText where fid in (";

			//col = ContractWithoutTextFactory.getLocalInstance(ctx).getFDCBillCollection(view);
		}else{
			sql = "select fid,fnumber ,fname from t_con_contractBill where fid in (";
			//col = ContractBillFactory.getLocalInstance(ctx).getFDCBillCollection(view);
		}
		
		Iterator it =  idList.iterator();
		int j=0;
		while (it.hasNext()) {
			String id = (String)it.next();
			if (j != 0) {
				sql += ",";
			}
			sql += "'" + id + "'";
			j++;
		}
		sql += ")";
		
		
		Map map = new HashMap();
		IRowSet rs = DbUtil.executeQuery(ctx,sql);
		if(rs!=null){
			try {
				while(rs.next()){
					String id = rs.getString("fid");
					String number = rs.getString("fnumber");
					String name = rs.getString("fname");
					
					FDCBillInfo bill = new FDCBillInfo();
					bill.setNumber(number);
					bill.setName(name);
					
					map.put(id,bill);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BOSException(e);
			}
		}
		

//		map.put("contractNumber",info.getNumber());
//		map.put("contractName",info.getName());
//		map.put("info",info);
    	
//		for (int i = 0; i < col.size(); i++) {
//			info = col.get(i);
//			map.put(info.getId().toString(),info);
//		}
		
        return map;
    }

    
	protected BigDecimal _getProjectAmount(Context ctx, String projectId,String id) throws BOSException, EASBizException {
		
		BigDecimal amount = FDCHelper.ZERO;
		
		String selectSql = "select fcurprojectId , sum(famount)  amount" +
				" from T_Con_ContractBill  where 	(fstate = '4AUDITTED' or fstate = '2SUBMITTED' )" +
				" and FIsAmtWithoutCost=0 and FIsCostSplit=1 and fcurprojectId=?" +
				(id!=null?" and  fid<>'"+id+"'":""	)+
				" group by fcurprojectId	";		

		IRowSet rs = DbUtil.executeQuery(ctx,selectSql,new Object[]{projectId});
		try {
			if(rs!=null && rs.next()) {
				amount = rs.getBigDecimal("amount");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
		
		 selectSql = "select fcurprojectId , sum(famount)  amount" +
			" from t_con_contractwithouttext  where 	(fstate = '4AUDITTED' or fstate = '2SUBMITTED' )" +
			" and FIsCostSplit=1  and fcurprojectId=? " +
			(id!=null?" and  fid<>'"+id+"'":""	)+
			"group by fcurprojectId	";		

		rs = DbUtil.executeQuery(ctx,selectSql,new Object[]{projectId});
		try {
			if(rs!=null && rs.next()) {
				amount = amount.add(rs.getBigDecimal("amount"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
		
		
		return amount;
	}

	//得到累次结算价  
	protected Map _getTotalSettlePrice(Context ctx, Map param) throws BOSException, EASBizException {
		Map result = null;
		String contractId = (String)param.get("ContractBillId");
		String id = (String)param.get("ID");
		String sql = "select sum(FCurOriginalAmount) FOriginalAmount, sum(FCurSettlePrice) FSettlePrice  from t_con_contractSettlementbill where FContractBillId=? " ;
			
		Object[] sqlParam = new Object[]{contractId};
		if(id!=null){
			sql +=" and fid<>?";
			sqlParam = new Object[]{contractId,id};//id为最终结算单单据Id
		}
		
		sql +=" and fstate = '4AUDITTED' Group by FContractBillId";
		
		IRowSet rs = DbUtil.executeQuery(ctx,sql,sqlParam);
		BigDecimal amount = null;
		try {
			if(rs!=null && rs.next()) {
				result = new HashMap();
				amount =rs.getBigDecimal("FOriginalAmount");
				result.put("OriginalAmount",amount);
				amount = rs.getBigDecimal("FSettlePrice");
				result.put("SettlePrice",amount);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
		
		
		return result;
	}

	//从汇兑关系获取汇率精度
	protected Map _getExRatePre(Context ctx, String exTableId, String objCurId) throws BOSException, EASBizException {
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("exchangeTable.id",exTableId));
		filter.getFilterItems().add(new FilterItemInfo("targetCurrency.id",objCurId));
		view.getSelector().add("sourceCurrency.id");
		view.getSelector().add("precision");
		
		ExchangeAuxCollection exCol = ExchangeAuxFactory.getLocalInstance(ctx).getExchangeAuxCollection(view);
		Map exMap = new HashMap();
		if(exCol!=null && exCol.size()>0){
			for(int j=0;j<exCol.size();j++){
				ExchangeAuxInfo ex = exCol.get(j);
				
				exMap.put(ex.getSourceCurrency().getId().toString(),new Integer(ex.getPrecision()));
			}
		}
		
		return exMap;
	}
	
	/**
	 * 批量获取合同最新造价
	 */
	protected Map _getLastPrice(Context ctx, Map lastPriceMap) throws BOSException, EASBizException {

       	Set set = lastPriceMap.keySet();
       	Iterator it = set.iterator();
       	Map amountMap = new HashMap();
       	
		while (it.hasNext()) {
			String id = (String)it.next();
			try {
				BigDecimal amount = FDCUtils.getContractLastPrice (ctx,id,true);
				amountMap.put(id,amount);
			}  catch (SQLException e) {
				e.printStackTrace();
				throw new BOSException(e);
			}
			
		}
		
		return amountMap;
	}

	/**
	 * 获得合同变更金额
	 */
	protected Map _getChangeAmt(Context ctx, String[] contractIds) throws BOSException, EASBizException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);		
		try {
			Map changeAmtMap = new HashMap();
			/**
			 * 如果变更未结算 取变更金额；若已结算，则取结算金额
			 */
			if (contractIds != null) {
				// 变更
				builder.clear();
				builder.appendSql("select FContractBillID,sum(fchangeAmount) as changeAmount from ( ");
				builder.appendSql("select FContractBillID,FBalanceAmount as fchangeAmount from T_CON_ContractChangeBill ");
				builder.appendSql("where FHasSettled=1 and ");
				builder.appendParam("FContractBillID", contractIds, "varchar(44)");
				builder.appendSql(" and (");
				builder.appendParam("FState", FDCBillStateEnum.AUDITTED_VALUE);
				builder.appendSql(" or ");
				builder.appendParam("FState", FDCBillStateEnum.VISA_VALUE);
				builder.appendSql(" or ");
				builder.appendParam("FState", FDCBillStateEnum.ANNOUNCE_VALUE);
				builder.appendSql(" ) union all ");
				builder.appendSql("select FContractBillID,FAmount as fchangeAmount from T_CON_ContractChangeBill ");
				builder.appendSql("where FHasSettled=0 and ");
				builder.appendParam("FContractBillID", contractIds, "varchar(44)");
				builder.appendSql(" and (");
				builder.appendParam("FState", FDCBillStateEnum.AUDITTED_VALUE);
				builder.appendSql(" or ");
				builder.appendParam("FState", FDCBillStateEnum.VISA_VALUE);
				builder.appendSql(" or ");
				builder.appendParam("FState", FDCBillStateEnum.ANNOUNCE_VALUE);
				builder.appendSql(" )) change group by FContractBillID");

				IRowSet rs = builder.executeQuery();
				try {
					while (rs.next()) {
						String contractId = rs.getString("FContractBillID");
						BigDecimal changeAmount = rs.getBigDecimal("changeAmount");
						changeAmtMap.put(contractId, changeAmount);
					}
				} catch (SQLException e) {
					e.printStackTrace();
					throw new BOSException(e);
				}
			}
			return changeAmtMap;
		} finally {
			builder.releasTempTables();
		}
	}

	/**
	 * 批量取合同最新造价
	 */
	protected Map _getLastAmt(Context ctx, String[] contractIds) throws BOSException, EASBizException {

		if(contractIds==null || contractIds.length==0){
			return new HashMap();
		}
		BigDecimal bdZero = FDCNumberHelper.ZERO;
		String noSettleContractIdList =  null;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select FID,FHasSettled,FSettleAmt,FAmount from T_CON_ContractBill where ");
		builder.appendParam("fid",contractIds);
		
		IRowSet rs = builder.executeQuery();
		if(rs==null||rs.size()==0){
			return new HashMap();
		}
		Map lastAmtMap = new HashMap(rs.size());
		try {
			while (rs.next()) {
				String contractId = rs.getString("FID");
				BigDecimal contractAmount = FDCNumberHelper.ZERO;
				if(rs.getBoolean("FHasSettled")){
					contractAmount = rs.getBigDecimal("FSettleAmt");
				}else{
					if(noSettleContractIdList == null){
						noSettleContractIdList = contractId;
					}else{					
						noSettleContractIdList = noSettleContractIdList + "," + contractId;
					}
					contractAmount = rs.getBigDecimal("FAmount");
				}	
				lastAmtMap.put(contractId, contractAmount == null ? bdZero : contractAmount);
			}
		} catch (SQLException e1) {
			// TODO 自动生成 catch 块
			e1.printStackTrace();
			throw new BOSException(e1);
		}
		/*
		 * 万科最新要求,未结算的合同最新造价=合同金额+变更金额+奖励-索赔,扣款(已被申请单关联的) 
		 * by sxhong 2007/09/28
		 */		
		if (noSettleContractIdList != null) {
			String[] noSettleContractIdArray = FDCHelper.stringToStrArray(noSettleContractIdList);
			//变更
			builder.clear();
			builder.appendSql("select FContractBillID,sum(fchangeAmount) as changeAmount from ( ");
			builder.appendSql("select FContractBillID,FBalanceAmount as fchangeAmount from T_CON_ContractChangeBill ");
			builder.appendSql("where FHasSettled=1 and ");
			builder.appendParam("FContractBillID",noSettleContractIdArray);
			builder.appendSql(" and (");
			builder.appendParam("FState",FDCBillStateEnum.AUDITTED_VALUE);
			builder.appendSql(" or ");
			builder.appendParam("FState",FDCBillStateEnum.VISA_VALUE);
			builder.appendSql(" or ");
			builder.appendParam("FState",FDCBillStateEnum.ANNOUNCE_VALUE);
			builder.appendSql(" ) union all ");
			builder.appendSql("select FContractBillID,FAmount as fchangeAmount from T_CON_ContractChangeBill ");
			builder.appendSql("where FHasSettled=0 and ");
			builder.appendParam("FContractBillID",noSettleContractIdArray);
			builder.appendSql(" and (");
			builder.appendParam("FState",FDCBillStateEnum.AUDITTED_VALUE);
			builder.appendSql(" or ");
			builder.appendParam("FState",FDCBillStateEnum.VISA_VALUE);
			builder.appendSql(" or ");
			builder.appendParam("FState",FDCBillStateEnum.ANNOUNCE_VALUE);
			builder.appendSql(" )) change group by FContractBillID");
				
			rs = builder.executeQuery();
			try {
				while (rs.next()) {
					String contractId = rs.getString("FContractBillID");
					BigDecimal changeAmount = rs.getBigDecimal("changeAmount");	
					if(lastAmtMap.containsKey(contractId) && changeAmount != null){
						lastAmtMap.put(contractId,((BigDecimal)lastAmtMap.get(contractId)).add(changeAmount));
					}				
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BOSException(e);
			}
			
			/*//奖励	
			builder.clear();
			builder.appendSql("select FContractID,sum(famount) as amount from T_CON_GuerdonBill where ");
			builder.appendParam("FContractID",noSettleContractIdArray);
			builder.appendSql(" and ");
			builder.appendParam("fstate",FDCBillStateEnum.AUDITTED_VALUE);
			builder.appendSql(" AND fisGuerdoned = 1 group by fcontractid ");
			rs = builder.executeQuery();
			try {
				while (rs.next()) {
					String contractId = rs.getString("FContractID");
					BigDecimal guerdonAmt = rs.getBigDecimal("amount");	
					if(lastAmtMap.containsKey(contractId) && guerdonAmt != null){
						lastAmtMap.put(contractId,((BigDecimal)lastAmtMap.get(contractId)).add(guerdonAmt));
					}				
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			//违约
			builder.clear();
			builder.appendSql("select FContractID,sum(famount) as amount from T_CON_CompensationBill where ");
			builder.appendParam("FContractID",noSettleContractIdArray);
			builder.appendSql(" and ");
			builder.appendParam("fstate",FDCBillStateEnum.AUDITTED_VALUE);
			builder.appendSql(" AND fisCompensated = 1 group by fcontractid ");
			rs = builder.executeQuery();
			try {
				while (rs.next()) {
					String contractId = rs.getString("FContractID");
					BigDecimal compenseAmt = rs.getBigDecimal("amount");	
					if(lastAmtMap.containsKey(contractId) && compenseAmt != null){
						lastAmtMap.put(contractId,((BigDecimal)lastAmtMap.get(contractId)).subtract(compenseAmt));
					}				
				}
			} catch (SQLException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			}
			
			//扣款
			builder.clear();
			builder.appendSql("select prb.FContractID,sum(doprb.famount) as amount from T_CON_DeductOfPayReqBill doprb ");
			builder.appendSql("inner join T_CON_PayRequestBill prb on prb.fid = doprb.fpayRequestBillId ");
			builder.appendSql("where ");
			builder.appendParam("prb.FContractID",noSettleContractIdArray);
			builder.appendSql(" group by prb.FContractID ");
			rs = builder.executeQuery();
			try {
				while (rs.next()) {
					String contractId = rs.getString("FContractID");
					BigDecimal deductAmt = rs.getBigDecimal("amount");	
					if(lastAmtMap.containsKey(contractId) && deductAmt != null){
						lastAmtMap.put(contractId,((BigDecimal)lastAmtMap.get(contractId)).subtract(deductAmt));
					}				
				}
			} catch (SQLException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			}*/
		}
		return lastAmtMap;
	}
	/**
	 * 批量取合同已实现产值
	 * Modified by Owen_wen 2010-8-13
	 */
	protected Map _getTotalSettlePrice(Context ctx, Set contractIds) throws BOSException, EASBizException {
		Map result = new HashMap();
		FDCSQLBuilder fdcBuilder = new FDCSQLBuilder(ctx);
		try {
			fdcBuilder
					.appendSql("select FContractBillId,sum(FCurOriginalAmount) FOriginalAmount, sum(FCurSettlePrice) FSettlePrice  from t_con_contractSettlementbill where");
			fdcBuilder.appendParam("FContractBillId", contractIds.toArray(), "varchar(44)");
			fdcBuilder.appendSql("and fstate='4AUDITTED' Group by FContractBillId");
			logger.debug(fdcBuilder.getTestSql());
			IRowSet rs = fdcBuilder.executeQuery();
			fdcBuilder.releasTempTables();
			BigDecimal amount = null;
			try {
				if (rs != null) {
					while (rs.next()) {
						String key = (String) rs.getString("FContractBillId");
						amount = rs.getBigDecimal("FOriginalAmount");
						result.put(key, amount);
						amount = rs.getBigDecimal("FSettlePrice");
						result.put(key.concat("_SettlePrice"), amount);
					}
	
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BOSException(e);
			}
		} finally {
			fdcBuilder.releasTempTables();
		}
		
		
		return result;
	}

}