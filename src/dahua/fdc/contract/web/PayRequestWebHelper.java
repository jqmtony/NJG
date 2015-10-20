package com.kingdee.eas.fdc.contract.web;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.DeductTypeCollection;
import com.kingdee.eas.fdc.basedata.DeductTypeFactory;
import com.kingdee.eas.fdc.basedata.DeductTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.CompensationOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.CompensationOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.CompensationOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.GuerdonOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.GuerdonOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.GuerdonOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctItemTypeEnum;
import com.kingdee.eas.fdc.finance.FDCBudgetPeriodInfo;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 付款申请单web帮助类
 * @author kelvin_yang
 *
 */
public class PayRequestWebHelper {
	
	private static Map dataMap = null;
	
	/**
	 * 获取数据
	 * @param ctx
	 * @param payReqId
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static Map fetchInitData(Context ctx,String payReqId) throws BOSException, EASBizException {
		dataMap = new HashMap();
		PayRequestBillInfo info = PayRequestBillFactory.getLocalInstance(ctx)
			.getPayRequestBillInfo(new ObjectUuidPK(payReqId),getSelectors());
		
		String contractBillId = info.getContractId();		
		if(contractBillId!=null){	
			reloadGuerdonValue(ctx,info);
			reloadCompensationValue(ctx,info);
			DeductTypeCollection deductTypeColl = getDeductTypeColl(ctx);
			if(BOSUuid.read(contractBillId).getType().equals(new ContractBillInfo().getBOSType())){
				dataMap.put("typeColl",deductTypeColl);				
			}
			reloadDeductValue(ctx,info,deductTypeColl);
		}
		
		dataMap.put("info",info);
		return dataMap;
	}
	/**
	 * 获取“期间”
	 * @author ling_peng
	 */
	public static FDCBudgetPeriodInfo getFDCBudgetPeriod(PayRequestBillInfo info,Context ctx) throws BOSException, EASBizException{
		
		FDCBudgetPeriodInfo period=null;
			
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
			if(period==null&&info.getId()!=null){
				builder.appendSql("select top 1 period.fid id ,period.fyear year,period.fmonth month from T_FNC_PayRequestAcctPay acctPay  ");
				builder.appendSql("inner join T_FNC_FDCBudgetPeriod period on period.fid=acctPay.fperiodid ");
				builder.appendSql("where FPayRequestBillId=?");
				builder.addParam(info.getId().toString());
				try {
					IRowSet rowSet = builder.executeQuery();
					if (rowSet.next()) {
						int year=rowSet.getInt("year");
						int month=rowSet.getInt("month");
						String id=rowSet.getString("id");
						period=FDCBudgetPeriodInfo.getPeriod(year, month, false);
						period.setId(BOSUuid.read(id));
					}
				}catch(Exception e){
					throw new BOSException(e);
				}
			}
		//当前月份
		if(period==null){
			period=FDCBudgetPeriodInfo.getPeriod(info.getPayDate(),false);
			builder.clear();
			builder.appendSql("select fid from T_FNC_FDCBudgetPeriod where fyear=? and fmonth=? and fisYear=?");
	    	builder.addParam(new Integer(period.getYear()));
	    	builder.addParam(new Integer(period.getMonth()));
	    	builder.addParam(Boolean.FALSE);
	    	IRowSet rowSet=builder.executeQuery();
	    	try {
				if (rowSet.size() > 0) {
					rowSet.next();
					period.setId(BOSUuid.read(rowSet.getString("fid")));
				}
			}catch(SQLException e){
	    		throw new BOSException(e);
	    	}
		}
		
		return period;
	}
	/**
	 *  查看付款申请单预算信息
	 * @author ling_peng
	 */
	
	public static Map fetchCurrentMonthConInfo(Context ctx,/*String payReqId*/PayRequestBillInfo info,FDCBudgetPeriodInfo periodInfo) throws BOSException, EASBizException {

		Map	curMonthMap = new HashMap();
		
		String payReqId=info.getId().toString();
		String contractId=info.getContractId().toString();
		String projectId=info.getCurProject().getId().toString();
		String periodId=periodInfo.getId().toString();
		
	
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		
    	//合同本月计划付款金额  planAmt
    	builder.clear();
    	builder.appendSql("select sum(entry.famount) planamt from  t_fnc_fdcmonthbudgetacctentry entry ");
    	builder.appendSql("inner join t_fnc_fdcmonthbudgetacct head on entry.fparentid=head.fid ");
    	builder.appendSql("inner join t_fnc_fdcbudgetperiod period on head.ffdcperiodid=period.fid ");
    	builder.appendSql("where entry.fcontractbillid=? and entry.fprojectid=? and head.fprojectid=? and head.fstate=? and period.fyear=? and period.fmonth=? and entry.fitemtype=? ");
    	builder.appendSql("and head.fislatestver=1 ");
    	builder.appendSql("group by entry.fcostaccountid");
    	builder.addParam(contractId);
    	builder.addParam(projectId);
    	builder.addParam(projectId);
    	builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
    	builder.addParam(new Integer(periodInfo.getYear()));
    	builder.addParam(new Integer(periodInfo.getMonth()));
    	builder.addParam(FDCBudgetAcctItemTypeEnum.CONTRACT_VALUE);
    	IRowSet	rowSet=builder.executeQuery();
    	
    	int planAmountTotal=0;
    	BigDecimal planAmount=new BigDecimal("0");
    	try{
	    	while(rowSet.next()){
	    		if(rowSet.getBigDecimal("planamt")!=null){
	    			planAmountTotal+=rowSet.getBigDecimal("planamt").intValue();
	    		}
	    	}
	    	planAmount=BigDecimal.valueOf(planAmountTotal);
	    	curMonthMap.put("planAmount", planAmount);
    	}catch(SQLException e){
    		throw new BOSException(e);
    	}
    	
//    	String state=null;
//    	
//    	builder.clear();
//    	builder.appendSql("select fstate from t_con_payrequestbill where fid=? ");
//    	builder.addParam(payReqId);
//    	rowSet=builder.executeQuery();
//    	if(rowSet.size()==1){
//    		try {
//				rowSet.next();
//				state = rowSet.getString("fstate");
//			}catch (SQLException e) {
//    			throw new BOSException(e);
//    		}
//		}
    	
    	//合同本月已请款金额  requestedAmt 
    	
    int requestAmtInt=0;
 	if(info.getState()!=FDCBillStateEnum.AUDITTED){
 		builder.clear();
 		builder.appendSql("select sum (acctpay.famount) amount from t_fnc_payrequestacctpay acctpay ");
    	builder.appendSql("inner join t_con_payrequestbill payreq on payreq.fid=acctpay.fpayrequestbillid ");
    	builder.appendSql("inner join t_fnc_fdcbudgetperiod period on acctpay.fperiodId = period.fid ");
    	builder.appendSql("where payreq.fcontractid=? and acctpay.fcontractid=? and payreq.fstate=? and payreq.fid<>? and period.fyear=? and period.fmonth=?");
    	builder.addParam(contractId);
    	builder.addParam(contractId);
    	builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
    	builder.addParam(payReqId);
    	builder.addParam(new Integer(periodInfo.getYear()));
    	builder.addParam(new Integer(periodInfo.getMonth()));
    	rowSet=builder.executeQuery();
    	try {
			while (rowSet.next()) {
				curMonthMap.put("requestAmt",rowSet.getBigDecimal("amount"));
				if(rowSet.getBigDecimal("amount")!=null){
					requestAmtInt=rowSet.getBigDecimal("amount").intValue();
				}
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
    	}else{
        	builder.clear();
        	builder.appendSql("select flstallamount from t_fnc_payrequestacctpay where fpayrequestbillid=? and fperiodId=? and fcontractId=? and fprojectId=? ");
        	builder.addParam(payReqId);
        	builder.addParam(periodId);
        	builder.addParam(contractId);
        	builder.addParam(projectId);
        	rowSet=builder.executeQuery();
        	BigDecimal requestAmt=new BigDecimal("0");
         	try {
         		while (rowSet.next()) {
         			if(rowSet.getBigDecimal("flstallamount")!=null){
         				requestAmtInt+=rowSet.getBigDecimal("flstallamount").intValue();
         			}
         		}
         		requestAmt=BigDecimal.valueOf(requestAmtInt);
         		curMonthMap.put("requestAmt", requestAmt);
         	} catch (SQLException e) {
         		throw new BOSException(e);
         	}
            	
    	}
 	
		
		//本次请款金额 amount
		builder.clear();
    	builder.appendSql("select famount from t_fnc_payrequestacctpay where fpayrequestbillid=? and fperiodId=? and fcontractId=? and fprojectId=? ");
    	builder.addParam(payReqId);
    	builder.addParam(periodId);
    	builder.addParam(contractId);
    	builder.addParam(projectId);
    	rowSet=builder.executeQuery();
    	int amountInt=0;
    	BigDecimal amount=new BigDecimal("0");
    	try {
			while (rowSet.next()) {
				if(rowSet.getBigDecimal("famount")!=null){
					amountInt+=rowSet.getBigDecimal("famount").intValue();
				}
			}
			amount=BigDecimal.valueOf(amountInt);
			curMonthMap.put("amount", amount);
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		
		//合同本月计划付款余额    balanceAmt
		int balanceAmtInt=planAmountTotal-requestAmtInt;
		BigDecimal bananceAmt=new BigDecimal(balanceAmtInt);
		curMonthMap.put("bananceAmt", bananceAmt);
    	
		return curMonthMap;
	}


	
	/**
	 * 奖励取数
	 * @param ctx
	 * @param editData
	 * @throws BOSException
	 */
	private static 	void reloadGuerdonValue(Context ctx,PayRequestBillInfo editData) throws BOSException {

    	// 进行刷新操作从数据库内取
		BigDecimal originalamount = FDCHelper.ZERO;
		BigDecimal amount = FDCHelper.ZERO;
		BigDecimal lstPaidAmt = FDCHelper.ZERO;
		BigDecimal lstReqAmt = FDCHelper.ZERO;
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("amount");
		view.getSelector().add("originalAmount");
		// view.getSelector().add("guerdon.exRate");
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("payRequestBill.contractId", editData
				.getContractId());
		view.setFilter(filter);
		// TODO 再加一个时间过滤
		Timestamp createTime = editData.getCreateTime();
		filter.getFilterItems().add(
				new FilterItemInfo("payRequestBill.createTime", createTime,
						CompareType.LESS_EQUALS));
		GuerdonOfPayReqBillCollection c = GuerdonOfPayReqBillFactory
				.getLocalInstance(ctx).getGuerdonOfPayReqBillCollection(view);
		for (int i = 0; i < c.size(); i++) {
			GuerdonOfPayReqBillInfo info = c.get(i);
			if (info.getAmount() != null) {
				if (info.getPayRequestBill().getId().equals(editData.getId())) {
					amount = amount.add(FDCHelper
							.toBigDecimal(info.getAmount()));
					originalamount = originalamount.add(FDCHelper
							.toBigDecimal(info.getOriginalAmount()));
				} else {
					if (info.isHasPaid()) {
						lstPaidAmt = info.getAmount().add(lstPaidAmt);
					}

					lstReqAmt = info.getAmount().add(lstReqAmt);
				}
			}
		}
		if (amount.compareTo(FDCHelper.ZERO) == 0) {
			amount = null;
		}
		if (originalamount.compareTo(FDCHelper.ZERO) == 0) {
			originalamount = null;
		}
		if (lstPaidAmt.compareTo(FDCHelper.ZERO) == 0) {
			lstPaidAmt = null;
		}
		if (lstReqAmt.compareTo(FDCHelper.ZERO) == 0) {
			lstReqAmt = null;
		}
		editData.put("guerdonAmt", amount);
		editData.put("guerdonOriginalAmt", originalamount);
		editData.put("lstguerdonPaidAmt", lstPaidAmt);
		editData.put("lstGuerdonReqAmt", lstReqAmt);
    }
	private static 	void reloadCompensationValue(Context ctx,PayRequestBillInfo editData) throws BOSException{

    	BigDecimal originalamount = FDCHelper.ZERO;
		BigDecimal amount = FDCHelper.ZERO;
		BigDecimal lstPaidAmt = FDCHelper.ZERO;
		BigDecimal lstReqAmt = FDCHelper.ZERO;
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("payRequestBill.contractId", editData
				.getContractId());
		view.setFilter(filter);
		Timestamp createTime = editData.getCreateTime();
		filter.getFilterItems().add(
				new FilterItemInfo("payRequestBill.createTime", createTime,
						CompareType.LESS_EQUALS));

		CompensationOfPayReqBillCollection c = CompensationOfPayReqBillFactory
			.getLocalInstance(ctx).getCompensationOfPayReqBillCollection(view);
		for (int i = 0; i < c.size(); i++) {
			CompensationOfPayReqBillInfo info = c.get(i);
			if (info.getAmount() != null) {
				if (info.getPayRequestBill().getId().equals(editData.getId())) {
					amount = amount.add(FDCHelper
							.toBigDecimal(info.getAmount()));
					originalamount = originalamount.add(FDCHelper
							.toBigDecimal(info.getOriginalAmount()));
				} else {
					if (info.isHasPaid()) {
						lstPaidAmt = info.getAmount().add(lstPaidAmt);
					}

					lstReqAmt = info.getAmount().add(lstReqAmt);
				}
			}
		}
		if (amount.compareTo(FDCHelper.ZERO) == 0) {
			amount = null;
		}
		if (originalamount.compareTo(FDCHelper.ZERO) == 0) {
			originalamount = null;
		}
		if (lstPaidAmt.compareTo(FDCHelper.ZERO) == 0) {
			lstPaidAmt = null;
		}
		if (lstReqAmt.compareTo(FDCHelper.ZERO) == 0) {
			lstReqAmt = null;
		}
		editData.put("compensationAmt",amount);
		editData.put("compensationOriginalAmt",originalamount);
		editData.put("lstCompensationPaidAmt",lstPaidAmt);
		editData.put("lstCompensationReqAmt",lstReqAmt);
    		
    }
	private static void reloadDeductValue(Context ctx,PayRequestBillInfo editData,
			DeductTypeCollection deductTypeCollection) throws BOSException {
		String contractId = editData.getContractId();
		if (contractId == null) return;

		HashMap map = new HashMap();
		EntityViewInfo view;
		FilterInfo filter;
		FilterItemCollection items;

		/*
		 * 
		 */
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		view.setFilter(filter);
		final SorterItemInfo sorterItemInfo = new SorterItemInfo("number");
		sorterItemInfo.setSortType(SortType.ASCEND);
		view.getSorter().add(sorterItemInfo);
		view.getSelector().add("id");
		DeductTypeInfo typeInfo = null;
		DeductTypeCollection typeColl = deductTypeCollection;
		for (int i = 0; i < typeColl.size(); i++) {
			// new Object[3]保存 0实付额,1累计额,2发生额,3发生额原币
			typeInfo = typeColl.get(i);
			Object[] o = new Object[4];
			map.put(typeInfo.getId().toString(), o);
		}
		if (map.size() <= 0) {
			return;
		}
		/*
		 * 保存后数据直接从DeductOfPayReqBill内取 累计额:实付,申请,发生额,发生额原币
		 */
		view = new EntityViewInfo();
		filter = new FilterInfo();
		items = filter.getFilterItems();
		items = filter.getFilterItems();
		items.add(new FilterItemInfo("payRequestBill.id", editData.getId().toString(), CompareType.EQUALS));
		view.setFilter(filter);
		DeductOfPayReqBillInfo info;
		DeductOfPayReqBillCollection c = DeductOfPayReqBillFactory
				.getLocalInstance(ctx).getDeductOfPayReqBillCollection(view);
		for (int i = 0; i < c.size(); i++) {
			info = c.get(i);
			Object o = map.get(info.getDeductType().getId().toString());
			if (o != null) {
				Object[] arrays = (Object[]) o;
				/*
				 * 发生额原币
				 */
				arrays[3] = info.getOriginalAmount();
				/*
				 * 发生额
				 */
				arrays[2] = info.getAmount();

				/*
				 * 申请
				 */
				arrays[1] = info.getAllReqAmt();

				/*
				 * 实付
				 */
				arrays[0] = info.getAllPaidAmt();

			}
		}
		dataMap.put("deductMap",map);
	}
	public static SelectorItemCollection getSelectors() {
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("*");
		selector.add("entrys.*");
		selector.add("orgUnit.name");
		selector.add("orgUnit.number");
		selector.add("orgUnit.displayName");

		selector.add("CU.name");

		selector.add("auditor.name");
		selector.add("creator.name");

		selector.add("useDepartment.number");
		selector.add("useDepartment.name");

		selector.add("curProject.name");
		selector.add("curProject.number");
		selector.add("curProject.displayName");
		selector.add("curProject.fullOrgUnit.name");
		selector.add("curProject.codingNumber");

		selector.add("currency.number");
		selector.add("currency.name");

		selector.add("supplier.number");
		selector.add("supplier.name");

		selector.add("realSupplier.number");
		selector.add("realSupplier.name");

		selector.add("settlementType.number");
		selector.add("settlementType.name");

		selector.add("paymentType.number");
		selector.add("paymentType.name");
		selector.add("paymentType.payType.id");

		selector.add("period.number");
		selector.add("period.beginDate");
		selector.add("period.periodNumber");
		selector.add("period.periodYear");
		
		return selector;
	}
	/**
	 * 获得扣款类型
	 * @param ctx
	 * @return
	 * @throws BOSException
	 */
	private static DeductTypeCollection getDeductTypeColl(Context ctx) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		//过滤甲供材
		filter.getFilterItems().add(new FilterItemInfo("id", DeductTypeInfo.partAMaterialType, CompareType.NOTEQUALS));
		view.setFilter(filter);
		final SorterItemInfo sorterItemInfo = new SorterItemInfo("number");
		sorterItemInfo.setSortType(SortType.ASCEND);
		view.getSorter().add(sorterItemInfo);
		view.getSelector().add("number");
		view.getSelector().add("name");
		DeductTypeCollection deductTypeCollection = DeductTypeFactory.getLocalInstance(ctx).getDeductTypeCollection(view);
		return deductTypeCollection;
	}
	public static Map getPayDetail(Context ctx,String payReqId)throws BOSException, EASBizException {
		Map detailMap = new HashMap();
		SelectorItemCollection sic =new SelectorItemCollection();
		sic.add("contractId");
		sic.add("createTime");		
		PayRequestBillInfo info = PayRequestBillFactory.getLocalInstance(ctx)
			.getPayRequestBillInfo(new ObjectUuidPK(payReqId),sic);
		String contractId = info.getContractId();
		Timestamp createTime = info.getCreateTime();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.appendFilterItem("contractId", contractId);
		filter.getFilterItems().add(
				new FilterItemInfo("createTime", createTime,
						CompareType.LESS));
		view.getSelector().add("entrys.paymentBill.id");
		Set ids = new HashSet();
		PayRequestBillCollection c = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillCollection(view);
		if(c.size()>0){
			for(Iterator it=c.iterator();it.hasNext();){
				PayRequestBillInfo payRequestInfo = (PayRequestBillInfo)it.next();
				if(payRequestInfo.getEntrys().size()>0){
					for(int i=0;i<payRequestInfo.getEntrys().size();i++){
						ids.add(payRequestInfo.getEntrys().get(i).getPaymentBill().getId().toString());
					}
				}
			}
		}
		if(ids == null || ids.size() <= 0)
			ids.add("no data");
		//R101130-139  关联关系由inner join改成left join，否则付款类型不存在（被删除）的记录选不出来  by zhiyuan_tang
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql(" select feeType.FName_l2 as Name , sum(payment.FAmount) as Amount from T_CAS_PaymentBill payment ");
		builder.appendSql(" left join T_CAS_FeeType feeType on payment.FFeeTypeId = feeType.FID where  ");
		builder.appendParam("payment.fid",ids.toArray());
		//builder.appendSql("payment.fid = 'jutb/LJJSzO36E67QdiO6kAoToE='");
		builder.appendSql(" and ");
		builder.appendParam(" payment.FBillStatus",new Integer(BillStatusEnum.PAYED_VALUE));
		builder.appendSql(" group by feeType.FName_l2");
		IRowSet rs = builder.executeQuery();
		try {
			while(rs.next()){
				String name = rs.getString("Name");
				BigDecimal amount = rs.getBigDecimal("Amount");
				detailMap.put(name,amount);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
		return detailMap;
	}
}
