package com.kingdee.eas.fdc.contract.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.param.ParamSimpleInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;
/**
 * @author Cassiel_peng 2009-10-16
 * @work 合同执行情况表存在严重性能问题，先抽取一个服务端类处理部分数据，以后有时间了将影响性能的取数都移到该类中
 */

public class ContractExecFacadeControllerBean extends AbstractContractExecFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.ContractExecFacadeControllerBean");
  //工程量确认流程与付款流程分离
	private static boolean isSeparateFromPayment(Context ctx) throws EASBizException, BOSException {
		boolean retValue=false;
		String  companyID = ContextUtil.getCurrentFIUnit(ctx).getId().toString();
			retValue=FDCUtils.getDefaultFDCParamByKey(ctx, companyID, FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT);
		return retValue;
	}
	private static boolean isSimpleFinancial(Context ctx, String fullOrgUnitID) throws EASBizException, BOSException {
		boolean retValue=false;
			retValue=FDCUtils.getDefaultFDCParamByKey(ctx, fullOrgUnitID, FDCConstants.FDC_PARAM_SIMPLEFINACIAL);
		return retValue;
	}
	public Map __getCompletePrjAmtForNoTextContract(Context ctx, Set idSet)throws BOSException, EASBizException{
		Map completeAmtMap = new HashMap();
		for (Iterator iter = idSet.iterator();iter.hasNext();) {
			completeAmtMap.put(iter.next(), FDCHelper.ZERO);
		}
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		try {
			builder.appendSql("select FContractId,sum(FCompletePrjAmt) as FCompletePrjAmt from T_CON_PayRequestBill ");
			builder.appendSql("where  FState = '4AUDITTED' ");
			builder.appendParam("and FContractId  ", idSet.toArray(), "varchar(44)");
			builder.appendSql("  group by FContractId");
			IRowSet rowSet = builder.executeQuery();
			try {
				while (rowSet.next()) {
					String contractId = rowSet.getString("FContractId");
					BigDecimal completePrjAmt = FDCHelper.toBigDecimal(rowSet.getBigDecimal("FCompletePrjAmt"), 2);
					completeAmtMap.put(contractId, completePrjAmt);
				}
			} catch (SQLException e) {
				logger.error(e.getCause(), e);
				throw new BOSException(e);
			}
		} finally {
			builder.releasTempTables();
		}
		return completeAmtMap;
	}
	private void handleParams(Context ctx, Map orgId2ContractIdSet, Set simpleFIContractIds, Set noSimpleFIContractIds,
			Set separatePayContractIds, Set noSeparatePayContractIds) throws EASBizException, BOSException {
		ArrayList paramNumbers = new ArrayList();
		paramNumbers.add(FDCConstants.FDC_PARAM_SIMPLEFINACIAL);
		paramNumbers.add(FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT);
		ArrayList orgIds = new ArrayList();
		orgIds.addAll(orgId2ContractIdSet.keySet());
		ArrayList paramValues = ParamControlFactory.getLocalInstance(ctx).getParamHashMapBatch(paramNumbers, orgIds);
		if (paramValues != null) {
			for (Iterator iter = paramValues.iterator(); iter.hasNext();) {
				ParamSimpleInfo param = (ParamSimpleInfo) iter.next();
				if (param.getOrgUnitPK() != null) {
					String orgId = param.getOrgUnitPK().toString();
					boolean value = Boolean.valueOf(param.getParamValue()).booleanValue();
					if (FDCConstants.FDC_PARAM_SIMPLEFINACIAL.equals(param.getParamNumber())) {
						if (value) {
							simpleFIContractIds.addAll((Collection) orgId2ContractIdSet.get(orgId));
						} else {
							noSimpleFIContractIds.addAll((Collection) orgId2ContractIdSet.get(orgId));
						}
					} else if (FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT.equals(param.getParamNumber())) {
						if (value) {
							separatePayContractIds.addAll((Collection) orgId2ContractIdSet.get(orgId));
						} else {
							noSeparatePayContractIds.addAll((Collection) orgId2ContractIdSet.get(orgId));
						}
					}
				}
			}
		}
	}

	public Map _getCompleteAmt(Context ctx, Map orgId2ContractIdSet) throws BOSException, EASBizException {
		Set simpleFIContractIds = new HashSet();
		Set noSimpleFIContractIds = new HashSet();
		Set separatePayContractIds = new HashSet();
		Set noSeparatePayContractIds = new HashSet();
		handleParams(ctx, orgId2ContractIdSet, simpleFIContractIds, noSimpleFIContractIds, separatePayContractIds, noSeparatePayContractIds);
		Map ret = new HashMap();
		for (Iterator iter = orgId2ContractIdSet.values().iterator(); iter.hasNext();) {
			Set contractIdSet = (Set) iter.next();
			for (Iterator contractIter = contractIdSet.iterator(); contractIter.hasNext();) {
				ret.put(contractIter.next(), FDCHelper.ZERO);
			}
		}
		getCompleteAmt(ctx, ret, simpleFIContractIds, noSimpleFIContractIds, separatePayContractIds, noSeparatePayContractIds);
		return ret;
	}
	private void getCompleteAmt(Context ctx, Map ret, Set simpleFIContractIds, Set noSimpleFIContractIds, Set separatePayContractIds,
			Set noSeparatePayContractIds) throws BOSException, EASBizException {
		Map tempMap = new HashMap();
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		try {
			// 合同已最终结算并且启用简单模式财务成本一体化
			if (!simpleFIContractIds.isEmpty()) {
				builder.clear();
				builder
						.appendSql("select payReq.FContractId, sum(payReq.FCompletePrjAmt) as FCompletePrjAmt from T_CON_PayRequestBill  payReq ");
				builder.appendSql("inner join T_CON_ContractBill con on con.Fid=payReq.FContractId ");
				builder.appendSql("where payReq.FState='4AUDITTED'  and con.FHasSettled=1  ");
				builder.appendParam("and payReq.FContractId  ", simpleFIContractIds.toArray(), "varchar(44)");
				builder.appendSql("  group by payReq.FContractId ");
				IRowSet rowSet = builder.executeQuery();
				
				try {
					while (rowSet.next()) {
						String contractId = rowSet.getString("FContractId");
						BigDecimal completeAmt = FDCHelper.toBigDecimal(rowSet.getBigDecimal("FCompletePrjAmt"), 2);
						tempMap.put(contractId, completeAmt);
					}
				} catch (SQLException e) {
					e.printStackTrace();
					throw new BOSException(e);
				}
			}
			if (!noSimpleFIContractIds.isEmpty()) {// 合同已最终结算并且未启用简单模式财务成本一体化
				builder.clear();
				builder.appendSql("select FID, FSettleAmt  from T_CON_ContractBill  ");
				builder.appendSql("where FHasSettled=1 and fstate='4AUDITTED'  ");
				builder.appendParam("and FID ", noSimpleFIContractIds.toArray(), "varchar(44)");
				IRowSet rowSet = builder.executeQuery();
				
				try {
					while (rowSet.next()) {
						String contractId = rowSet.getString("FID");
						BigDecimal settlePrice = FDCHelper.toBigDecimal(rowSet.getBigDecimal("FSettleAmt"), 2);
						tempMap.put(contractId, settlePrice);
					}
				} catch (SQLException e) {
					e.printStackTrace();
					throw new BOSException(e);
				}
			}
			// 合同未最终结算并且工程量确认流程与付款流程未分离
			if (!noSeparatePayContractIds.isEmpty()) {
				builder.clear();
				builder.appendSql("select FContractId,sum(FCompletePrjAmt) as FCompletePrjAmt from T_CON_PayRequestBill pay ");
				builder.appendSql(" inner join t_fdc_paymenttype type on pay.FPaymentType=type.fid ");
				builder.appendSql("where  FState = '4AUDITTED' ");
				builder.appendParam("and FContractId  ", noSeparatePayContractIds.toArray(), "varchar(44)");
				builder.appendSql(" and type.fname_l2!='预付款'");
				builder.appendSql("  group by FContractId");
				IRowSet rowSet = builder.executeQuery();
				try {
					while (rowSet.next()) {
						String contractId = rowSet.getString("FContractId");
						BigDecimal complateAmt = FDCHelper.toBigDecimal(rowSet.getBigDecimal("FCompletePrjAmt"), 2);
						ret.put(contractId, complateAmt);
					}
				} catch (SQLException e) {
					e.printStackTrace();
					throw new BOSException(e);
				}
			}
			boolean separate = false;
			if (!separatePayContractIds.isEmpty()) {
				{// 合同未最终结算并且工程量确认流程与付款流程分离
					separate=true;
					builder.clear();
					builder.appendSql("select FContractBillId,sum(FworkLoad) as FworkLoad from T_FNC_WorkLoadConfirmBill ");
					builder.appendSql("where FState = '4AUDITTED' ");
					builder.appendParam("and FContractBillId ", separatePayContractIds.toArray(), "varchar(44)");
					builder.appendSql("  group by FContractBillId ");
					IRowSet rowSet = builder.executeQuery();
					
					try {
						while (rowSet.next()) {
							String contractId = rowSet.getString("FContractBillId");
							BigDecimal workLoad = FDCHelper.toBigDecimal(rowSet.getBigDecimal("FworkLoad"), 2);
							ret.put(contractId, workLoad);
						}
					} catch (SQLException e) {
						throw new BOSException(e);
					}
				}
				Set hashSet = tempMap.keySet();
				
				if(!separate) { //added by ken_liu...工程量确认流程与付款流程分离时依然取工程量确认单上的完工工程量
					for (Iterator tempIdIter = hashSet.iterator(); tempIdIter.hasNext();) {
						String idKey = (String) tempIdIter.next();
						ret.put(idKey, tempMap.get(idKey));
					}
				}
			}
			
			//不确认工程量的合同，已完工工程量，未结算时等于审批状态的付款金额
			builder.clear();
			builder.appendSql("select con.fid FContractBillId,sum(pay.famount) famount from t_cas_paymentbill pay ");
			builder.appendSql("inner join T_CON_ContractBill con on con.fid=pay.fcontractbillid ");
			builder.appendSql("inner join T_FDC_ContractType type on type.fid=con.FContractTypeID ");
			builder
					.appendSql("where type.FIsWorkLoadConfirm = 0 and (pay.fBillStatus=12 or pay.fBillStatus=15) and con.FHasSettled=0 ");
			separatePayContractIds.addAll(noSeparatePayContractIds);
			separatePayContractIds.addAll(noSimpleFIContractIds);
			separatePayContractIds.addAll(simpleFIContractIds);
			//如果参数集合为空或者是空集合就不添加这个过滤条件     add by jian_cao  because合同执行情况表报错R130325-0139
			if (separatePayContractIds != null && separatePayContractIds.size() > 0) {
				builder.appendParam(" and con.fid ", separatePayContractIds.toArray(), "varchar(44) ");
			}
			builder.appendSql(" group by con.fid ");
			IRowSet rowSet = builder.executeQuery();

			try {
				while (rowSet.next()) {
					String contractId = rowSet.getString("FContractBillId");
					BigDecimal workLoad = FDCHelper.toBigDecimal(rowSet.getBigDecimal("famount"), 2);
					ret.put(contractId, workLoad);
				}
			} catch (SQLException e) {
				throw new BOSException(e);
			}
		} finally {
			builder.releasTempTables();
		}
	}
	public Map __getCompleteAmt(Context ctx, String fullOrgUnitID, Set idSet)throws BOSException, EASBizException{
		Map orgId2ContractIdSet = new HashMap();
		orgId2ContractIdSet.put(fullOrgUnitID, idSet);
		return _getCompleteAmt(ctx, orgId2ContractIdSet);
	}
	//发票金额本币
	protected Map _getAllInvoiceAmt(Context ctx, Set idSet) throws BOSException, EASBizException {
		Map iAmtMap = new HashMap();
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.clear();
		
		builder.appendSql("select Fcontractid ,sum(finvoiceAmt) as allFinvoiceAmt from T_CON_PayRequestBill where ");
		builder.appendParam("Fcontractid ", idSet.toArray(),"varchar(44) ");
		builder.appendSql(" group by Fcontractid ");
		IRowSet rowSet = builder.executeQuery();
		try {
			while (rowSet.next()) {
				String contractId = rowSet.getString("Fcontractid");
				BigDecimal finvoiceAmt = FDCHelper.toBigDecimal(rowSet.getBigDecimal("allFinvoiceAmt"), 2);
				iAmtMap.put(contractId, finvoiceAmt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
		return iAmtMap;
	}
	//发票金额原币
	protected Map _getAllInvoiceOriAmt(Context ctx, Set idSet) throws BOSException, EASBizException {
		Map iAmtMap = new HashMap();
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.clear();
		
		builder.appendSql("select Fcontractid ,sum(FinvoiceOriAmt) as allFinvoiceOriAmt from T_CON_PayRequestBill where ");
		builder.appendParam("Fcontractid ", idSet.toArray(),"varchar(44) ");
		builder.appendSql(" group by Fcontractid ");
		IRowSet rowSet = builder.executeQuery();
		try {
			while (rowSet.next()) {
				String contractId = rowSet.getString("Fcontractid");
				BigDecimal finvoiceAmt = FDCHelper.toBigDecimal(rowSet.getBigDecimal("allFinvoiceOriAmt"), 2);
				iAmtMap.put(contractId, finvoiceAmt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
		return iAmtMap;
	}
	protected Map _getAdjustSumMap(Context ctx, Set idSet) throws BOSException, EASBizException {
		Map adjustSumMap = new HashMap();
		
		/**
		 * 疯了啊，idSet超过2000/3在sqlserver里就会报参数不能2000个错误，太纠结了，时间有限，for循环吧，kill me
		 */
		for(int i=0;i<501;i++){
			idSet.add(BOSUuid.create("0D6DD1F4").toString());
		}
		if(idSet.size()>500){
			Set set = new HashSet();
			int i=0;
			for(Iterator ite= idSet.iterator();ite.hasNext();){
				i++;
				Object next = ite.next();
				set.add(next);
				if(i%500==0){
					getAdjMap(ctx, set, adjustSumMap);
					set.clear();
				}else{
					if(!ite.hasNext()){
						getAdjMap(ctx, set, adjustSumMap);
					}
				}
				
			}
		}
		
		return adjustSumMap;
	}
	public Map getAdjMap(Context ctx, Set idSet,Map adjustSumMap) throws BOSException,
			EASBizException {
		Map adjustLeafMap = null;
		Map lastMap = null;
		String contractid = "";
		String paymentbillid = "";
		BigDecimal adjustAmt = FDCHelper.ZERO;
		try {
			FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
			builder.clear();
			//奖励
			builder.appendSql("select tpb.fcontractbillid as fcontractid ,tgp.fpaymentbillid as fpaymentbillid,N'奖励' as adjustSum,  sum(tgp.famount) as amount from T_CON_GuerdonOfPayReqBill tgp left join ");
			builder.appendSql("T_CAS_PaymentBill tpb on tgp.fpaymentbillid = tpb.fid where tgp.fpaspaid = 1 and ");
			builder.appendParam("tpb.fcontractbillid", idSet.toArray(),"varchar(44) ");
			builder.appendSql(" group by  tpb.fcontractbillid,tgp.fpaymentbillid ");
			
			//违约
			builder.appendSql("union all ");
			builder.appendSql("select tcp.fcontractbillid as fcontractid,tcb.fpaymentbillid as fpaymentbillid ,N'违约' as adjustSum ,sum(tcb.famount) as amount from T_CON_CompensationOfPayReqBill tcb left join ");
			builder.appendSql("T_CAS_PaymentBill tcp on tcb.fpaymentbillid = tcp.fid  where tcb.fpaspaid = 1 and ");
			builder.appendParam("tcp.fcontractbillid", idSet.toArray(),"varchar(44) ");
			builder.appendSql(" group by  tcp.fcontractbillid,tcb.fpaymentbillid ");
			//扣款
			builder.appendSql("union all  ");
			builder.appendSql("select tcp.fcontractbillid as fcontractid,tcd.fpaymentbillid as fpaymentbillid, tfd.fname_l2 as adjustSum, sum(tcd.famount) as amount  from T_CON_DeductOfPayReqBill tcd left join ");
			builder.appendSql("T_CAS_PaymentBill tcp on tcd.fpaymentbillid = tcp.fid left join T_FDC_DeductType tfd on tcd.FDeductTypeID = tfd.fid where ");
			builder.appendParam("tcp.fcontractbillid", idSet.toArray(),"varchar(44) ");
			builder.appendSql("group by tcp.fcontractbillid ,tfd.fname_l2 ,tcd.fpaymentbillid ");
			builder.getTestSql();
			IRowSet rowSet = builder.executeQuery();
			if(rowSet.size()>0){
				while (rowSet.next()) {
					adjustAmt=FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"));
					if(adjustAmt.compareTo(FDCHelper.ZERO)!=1){
						continue;
					}
					contractid=rowSet.getString("fcontractid");
					paymentbillid=rowSet.getString("fpaymentbillid");
					if(adjustSumMap.containsKey(contractid)){
						adjustLeafMap = (Map) adjustSumMap.get(contractid);
						if(adjustLeafMap.containsKey(paymentbillid)){
							lastMap = (Map) adjustLeafMap.get(paymentbillid);
							lastMap.put(rowSet.getString("adjustSum"), adjustAmt);
						}else{
							lastMap = new HashMap();
							lastMap.put(rowSet.getString("adjustSum"), adjustAmt);
							adjustLeafMap.put(paymentbillid, lastMap);
						}
					}else{
						adjustLeafMap = new HashMap();
						lastMap = new HashMap();
						lastMap.put(rowSet.getString("adjustSum"), adjustAmt);
						adjustLeafMap.put(paymentbillid, lastMap);
						adjustSumMap.put(contractid, adjustLeafMap);
					}
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
		return adjustSumMap;
	}
	protected Map _getInvoiceAmt(Context ctx, Set idSet) throws BOSException, EASBizException {
		Map payAmtMap = new HashMap();
		Map lastMap = null;
		BigDecimal amount = FDCHelper.ZERO;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.clear();
		//by tim_gao 应该取收款单上的
//		builder.appendSql("select tcp.fcontractbillid as fcontractbillid ,tcp.fid as fid ,sum(tpr.finvoiceAmt) as amount from T_CAS_PaymentBill tcp left join T_CON_PayRequestBill tpr on tcp.ffdcPayReqID = tpr.fid where ");
//		builder.appendParam("tcp.fcontractbillid ", idSet.toArray(),"varchar(44) ");
//		builder.appendSql("group by tcp.fcontractbillid,tcp.fid ");
		
		builder.appendSql("select tcp.fcontractbillid as fcontractbillid ,tcp.fid as fid ,sum(tpr.FInvoiceAmt) as amount from T_CAS_PaymentBill tcp left join T_FNC_FDCPaymentBill tpr on tcp.fid = tpr.FPaymentBillID where ");
		builder.appendParam("tcp.fcontractbillid ", idSet.toArray(),"varchar(44) ");
		builder.appendSql("group by tcp.fcontractbillid,tcp.fid ");
		
		IRowSet rowSet = builder.executeQuery();
		try {
			while (rowSet.next()) {
				amount=FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"),2);
				/* modified by zhaoqin for R140311-0335,R20150112-0111 on 2015/04/07 */
				//if(amount.compareTo(FDCHelper.ZERO)!=1){
				if(amount.compareTo(FDCHelper.ZERO) == 0){
					continue;
				}
				String contractId = rowSet.getString("fcontractbillid");
				if(payAmtMap.containsKey(contractId)){
					lastMap = (Map) payAmtMap.get(contractId);
					lastMap.put(rowSet.getString("fid"), amount);
				}else{
					lastMap = new HashMap();
					lastMap.put(rowSet.getString("fid"), amount);
					payAmtMap.put(contractId, lastMap);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
		return payAmtMap;
	}
	protected Map _getInvoiceOriAmt(Context ctx, Set idSet) throws BOSException, EASBizException {
		Map payOriAmtMap = new HashMap();
		Map lastMap = null;
		BigDecimal amount = FDCHelper.ZERO;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.clear();
		//同上修改
		builder.appendSql("select tcp.fcontractbillid as fcontractbillid ,tcp.fid as fid ,sum(tpr.finvoiceOriAmt) as amount from T_CAS_PaymentBill tcp left join T_FNC_FDCPaymentBill tpr on tcp.fid = tpr.FPaymentBillID where ");
		builder.appendParam("tcp.fcontractbillid ", idSet.toArray(),"varchar(44) ");
		builder.appendSql("group by tcp.fcontractbillid,tcp.fid ");
		IRowSet rowSet = builder.executeQuery();
		try {
			while (rowSet.next()) {
				amount=FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"),2);
				/* modified by zhaoqin for R140311-0335,R20150112-0111 on 2015/04/07 */
				//if(amount.compareTo(FDCHelper.ZERO)!=1){
				if(amount.compareTo(FDCHelper.ZERO) == 0){
					continue;
				}
				String contractId = rowSet.getString("fcontractbillid");
				if(payOriAmtMap.containsKey(contractId)){
					lastMap = (Map) payOriAmtMap.get(contractId);
					lastMap.put(rowSet.getString("fid"), amount);
				}else{
					lastMap = new HashMap();
					lastMap.put(rowSet.getString("fid"), amount);
					payOriAmtMap.put(contractId, lastMap);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
		return payOriAmtMap;
	}
}