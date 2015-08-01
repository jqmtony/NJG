package com.kingdee.eas.fdc.finance.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.bot.BOTRelationCollection;
import com.kingdee.bos.metadata.bot.BOTRelationFactory;
import com.kingdee.bos.metadata.bot.BOTRelationInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.botp.BOTBillOperStateEnum;
import com.kingdee.eas.base.dap.DAPTransformResult;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.VoucherTypeInfo;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.BeforeAccountViewFactory;
import com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountInfo;
import com.kingdee.eas.fdc.basedata.CostSplitBillTypeEnum;
import com.kingdee.eas.fdc.basedata.CostSplitException;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryCollection;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo;
import com.kingdee.eas.fdc.basedata.IBeforeAccountView;
import com.kingdee.eas.fdc.basedata.ICostAccount;
import com.kingdee.eas.fdc.basedata.ICostAccountWithAccount;
import com.kingdee.eas.fdc.basedata.ISplitImporter;
import com.kingdee.eas.fdc.basedata.PaySplitUtilFacadeFactory;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.contract.ContractBaseDataFactory;
import com.kingdee.eas.fdc.contract.ContractBaseDataInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractSettlementBillCollection;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.IContractBaseData;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.contract.app.ContractStateHelper;
import com.kingdee.eas.fdc.finance.FDCPaymentBillCollection;
import com.kingdee.eas.fdc.finance.FDCPaymentBillFactory;
import com.kingdee.eas.fdc.finance.FDCPaymentBillInfo;
import com.kingdee.eas.fdc.finance.IPaySplit4Voucher;
import com.kingdee.eas.fdc.finance.IPaymentSplitEntry;
import com.kingdee.eas.fdc.finance.ISplitImportHandler;
import com.kingdee.eas.fdc.finance.PaySplit4VoucherFactory;
import com.kingdee.eas.fdc.finance.PaySplit4VoucherInfo;
import com.kingdee.eas.fdc.finance.PaymentAutoSplitHelper;
import com.kingdee.eas.fdc.finance.PaymentCostSplitImportHandler;
import com.kingdee.eas.fdc.finance.PaymentCostSplitImporter;
import com.kingdee.eas.fdc.finance.PaymentSplitCollection;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryCollection;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitException;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitHelper;
import com.kingdee.eas.fdc.finance.PaymentSplitInfo;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillFactory;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillInfo;
import com.kingdee.eas.fi.cas.IPaymentBill;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.fi.gl.IVoucher;
import com.kingdee.eas.fi.gl.VoucherFactory;
import com.kingdee.eas.fi.gl.VoucherInfo;
import com.kingdee.eas.fm.common.ContextHelperFactory;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.Result;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.NumericExceptionSubItem;

public class PaymentSplitControllerBean extends
		AbstractPaymentSplitControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.finance.app.PaymentSplitControllerBean");

	protected String _getLogInfo(Context ctx, IObjectPK pk)
			throws BOSException, EASBizException {
		PaymentSplitInfo info = (PaymentSplitInfo) super._getValue(ctx, pk);
		String retValue = "";
		if (info.getPaymentBill() != null) {
			String id = info.getPaymentBill().getId().toString();
			PaymentBillInfo test = PaymentBillFactory.getLocalInstance(ctx)
					.getPaymentBillInfo(new ObjectUuidPK(BOSUuid.read(id)));
			if (test.getNumber() != null) {
				retValue = test.getNumber();
			}
		}
		return retValue;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.kingdee.eas.framework.app.CoreBaseControllerBean#_addnew(com.kingdee.bos.Context,
	 *      com.kingdee.bos.dao.IObjectCollection)
	 */
	protected Result _addnew(Context ctx, IObjectCollection colls)
			throws BOSException, EASBizException {
		return super._addnew(ctx, colls);
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean#_addnew(com.kingdee.bos.Context,
	 *      com.kingdee.bos.dao.IObjectPK, com.kingdee.bos.dao.IObjectValue)
	 */
	protected void _addnew(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		super._addnew(ctx, pk, model);
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean#_addnew(com.kingdee.bos.Context,
	 *      com.kingdee.bos.dao.IObjectValue)
	 */
	protected IObjectPK _addnew(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		return super._addnew(ctx, model);
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.kingdee.eas.framework.app.CoreBaseControllerBean#_save(com.kingdee.bos.Context,
	 *      com.kingdee.bos.dao.IObjectCollection)
	 */
	protected Result _save(Context ctx, IObjectCollection colls)
			throws BOSException, EASBizException {
		return super._save(ctx, colls);
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean#_save(com.kingdee.bos.Context,
	 *      com.kingdee.bos.dao.IObjectPK, com.kingdee.bos.dao.IObjectValue)
	 */
	protected void _save(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		super._save(ctx, pk, model);
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.kingdee.eas.framework.app.CoreBillBaseControllerBean#_save(com.kingdee.bos.Context,
	 *      com.kingdee.bos.dao.IObjectValue)
	 */
	protected IObjectPK _save(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		// return super._save(ctx, model);
		PaymentSplitInfo bill = (PaymentSplitInfo) model;
		if(bill.isIsWorkLoadBill()){
			return _saveWorkLoadSplit(ctx, model);
		}else{
			//暂时将归属成本金额全部更新成0,以后付款申请单会控制保证
			String companyId=ContextUtil.getCurrentFIUnit(ctx).getId().toString();			
			Map paramMap = FDCUtils.getDefaultFDCParam(ctx, companyId); //FDCUtils.getDefaultFDCParamByKey(ctx, companyId, FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT);
			boolean separtFormPayment=FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT);
			boolean isInvoiceMrg=FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_INVOICEMRG);
			if(!bill.isIsConWithoutText()&&separtFormPayment){
				//update by david_yang PT048096 2011.04.12 发票模式下 全项目动态成本标中已实现成本为空
//				bill.setCompletePrjAmt(FDCHelper.ZERO);
//				for(Iterator iter=bill.getEntrys().iterator();iter.hasNext();){
//					PaymentSplitEntryInfo entry=(PaymentSplitEntryInfo)iter.next();
//					entry.setAmount(FDCHelper.ZERO);
//				}
			}
			if(bill.isIsConWithoutText()&&isInvoiceMrg){
				//处理无文本的发票拆分金额
				bill.setInvoiceAmt(bill.getAmount());
				for(Iterator iter=bill.getEntrys().iterator();iter.hasNext();){
					PaymentSplitEntryInfo entry=(PaymentSplitEntryInfo)iter.next();
					entry.setInvoiceAmt(entry.getAmount());
					entry.setSplitedInvoiceAmt(entry.getSplitedCostAmt());
				}
			}
		}
		if (bill.getPaymentBill() != null && bill.getPaymentBill().getCompany() != null && bill.getPaymentBill().getCompany().getId() != null) {
			String id = bill.getPaymentBill().getCompany().getId().toString();
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("company.id", id));
			view.setFilter(filter);
			IBeforeAccountView iBefore = BeforeAccountViewFactory.getLocalInstance(ctx);
			boolean has = iBefore.exists(filter);
			if (has) {
				BeforeAccountViewInfo info = iBefore.getBeforeAccountViewCollection(view).get(0);
				bill.setBeAccount(info);
			}
			// 设置orgUnit为付款单的公司ID by sxhong 2008-11-03 16:28:03
			FullOrgUnitInfo orgUnit = new FullOrgUnitInfo();
			orgUnit.setId(BOSUuid.read(id));
			bill.setOrgUnit(orgUnit);
		}

		if (bill.getPaymentBill() != null && bill.getPaymentBill().getContractBillId() != null) {
			String contractId = bill.getPaymentBill().getContractBillId();
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractId", contractId));
			view.setFilter(filter);
			IContractBaseData iBefore = ContractBaseDataFactory.getLocalInstance(ctx);
			boolean has = iBefore.exists(filter);
			if (has) {
				ContractBaseDataInfo info = iBefore.getContractBaseDataCollection(view).get(0);
				bill.setContractBaseData(info);
			}
			if (isConWithoutTxt(bill)) {
				bill.setConWithoutText(ContractWithoutTextFactory.getLocalInstance(ctx).getContractWithoutTextInfo(new ObjectUuidPK(BOSUuid.read(contractId))));
			} else {
				bill.setContractBill(ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId))));
			}
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("paymentBill.id", bill.getPaymentBill().getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("Fivouchered", Boolean.TRUE));
			view.setFilter(filter);
			int voucherSize = super.getPaymentSplitCollection(ctx, view).size();
			bill.setVoucherTimes(voucherSize);
		}

		if (bill.getPaymentBill() != null && bill.getPaymentBill().getPayerAccount() != null) {
			if (bill.getPaymentBill().getPayerAccount().isIsBank() || bill.getPaymentBill().getPayerAccount().isIsCash() || bill.getPaymentBill().getPayerAccount().isIsCashEquivalent()) {
				bill.setIsNeedTransit(true);
				bill.setTransitAccount(FDCUtils.getDefaultFDCParamAccount(ctx, bill.getPaymentBill().getCompany().getId().toString()));
			}
		}
		bill.setIslastVerThisPeriod(true);
		IObjectPK pk = super._save(ctx, bill);
		PaymentSplitInfo splitBillInfo = null;
		SelectorItemCollection selectorGet = new SelectorItemCollection();
		selectorGet.add("id");
		selectorGet.add("createTime");
		selectorGet.add("paymentBill.id");
		selectorGet.add("paymentBill.curProject.id");
		selectorGet.add("paymentBill.CU.id");
		selectorGet.add("paymentBill.fdcPayReqID");
		selectorGet.add("paymentBill.company.id");
		selectorGet.add("period.id");
		selectorGet.add("period.number");
		selectorGet.add("period.beginDate");
		selectorGet.add("period.endDate");
		selectorGet.add("isConWithoutText");
		selectorGet.add("paymentBill.id");
		selectorGet.add("paymentBill.contractBillId");
		selectorGet.add("paymentBill.fdcPayType.payType.id");
		selectorGet.add("isProgress");
		selectorGet.add("splitState");
		splitBillInfo = super.getPaymentSplitInfo(ctx, pk, selectorGet);
		String prjID = splitBillInfo.getPaymentBill().getCurProject().getId().toString();
		PeriodInfo currentPeriod = FDCUtils.getCurrentPeriod(ctx, prjID, false);
		Date bookedDate = DateTimeUtils.truncateDate(splitBillInfo.getCreateTime());
		String payreqID = splitBillInfo.getPaymentBill().getFdcPayReqID();
		String conId = splitBillInfo.getPaymentBill().getContractBillId();
		SelectorItemCollection reqPer = new SelectorItemCollection();
		reqPer.add("id");
		reqPer.add("period.number");
		reqPer.add("period.beginDate");
		reqPer.add("period.endDate");
		reqPer.add("projectPriceInContract");
		reqPer.add("createTime");
		PayRequestBillInfo reqInfo = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillInfo(new ObjectUuidPK(BOSUuid.read(payreqID)), reqPer);
		if (currentPeriod != null) {
			PeriodInfo contractPeriod = reqInfo.getPeriod();
			if (contractPeriod != null && contractPeriod.getNumber() > currentPeriod.getNumber()) {
				if (bookedDate.before(contractPeriod.getBeginDate())) {
					bookedDate = contractPeriod.getBeginDate();
				} else if (bookedDate.after(contractPeriod.getEndDate())) {
					bookedDate = contractPeriod.getEndDate();
				}
				FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
				builder.clear();
				builder.appendSql("update T_FNC_PaymentSplit set FPeriodId=?, FBookedDate=? where FID=?");
				builder.addParam(contractPeriod.getId().toString());
				builder.addParam(FDCDateHelper.getSqlDate(bookedDate));
				builder.addParam(splitBillInfo.getId().toString());
				builder.executeUpdate();

				builder = new FDCSQLBuilder(ctx);
				builder.clear();
				builder.appendSql("update T_FNC_PaymentSplit set FIslastVerThisPeriod=0 where FPeriodID=? and fid<>? and Fpaymentbillid=?");
				builder.addParam(contractPeriod.getId().toString());
				builder.addParam(splitBillInfo.getId().toString());
				builder.addParam(splitBillInfo.getPaymentBill().getId().toString());
				builder.executeUpdate();
			} else if (currentPeriod != null) {
				if (bookedDate.before(currentPeriod.getBeginDate())) {
					bookedDate = currentPeriod.getBeginDate();
				} else if (bookedDate.after(currentPeriod.getEndDate())) {
					bookedDate = currentPeriod.getEndDate();
				}
				FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
				builder.clear();
				builder.appendSql("update T_FNC_PaymentSplit set FPeriodId=?, FBookedDate=? where FID=?");
				builder.addParam(currentPeriod.getId().toString());
				builder.addParam(FDCDateHelper.getSqlDate(bookedDate));
				builder.addParam(splitBillInfo.getId().toString());
				builder.executeUpdate();

				builder = new FDCSQLBuilder(ctx);
				builder.clear();
				builder.appendSql("update T_FNC_PaymentSplit set FIslastVerThisPeriod=0 where FPeriodID=? and fid<>? and Fpaymentbillid=?");
				builder.addParam(currentPeriod.getId().toString());
				builder.addParam(splitBillInfo.getId().toString());
				builder.addParam(splitBillInfo.getPaymentBill().getId().toString());
				builder.executeUpdate();
			}
		}
		String companyId = splitBillInfo.getPaymentBill().getCompany().getId().toString();
		//红冲模式才调用删除 2009-05-20
		if (FDCUtils.IsFinacial(ctx, companyId)
				&& !FDCUtils.isAdjustVourcherModel(ctx, companyId)) {
			if (!splitBillInfo.isIsConWithoutText() && splitBillInfo.getPaymentBill() != null && splitBillInfo.getPaymentBill().getFdcPayType() != null
					&& splitBillInfo.getPaymentBill().getFdcPayType().getPayType().getId().toString().equals(PaymentTypeInfo.settlementID) && splitBillInfo.isIsProgress()) {
				EntityViewInfo viewPro = new EntityViewInfo();
				FilterInfo filterPro = new FilterInfo();
				filterPro.getFilterItems().add(new FilterItemInfo("paymentBill.contractBillId", splitBillInfo.getPaymentBill().getContractBillId()));
				filterPro.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
				filterPro.getFilterItems().add(new FilterItemInfo("paymentBill.fdcPayType.payType.id", PaymentTypeInfo.progressID));
				viewPro.setFilter(filterPro);
				viewPro.getSelector().add("id");
				viewPro.getSelector().add("hisVoucher.id");
				viewPro.getSelector().add("paymentBill.id");
				PaymentSplitCollection payColl = PaymentSplitFactory.getLocalInstance(ctx).getPaymentSplitCollection(viewPro);
				// 这个方法应该写成require的，保证事务一致性,隐藏危机，但是方法不可追溯，暂时因为补丁不修改了。
				for (Iterator iter = payColl.iterator(); iter.hasNext();) {
					PaymentSplitInfo element = (PaymentSplitInfo) iter.next();
					deleteCostSplit(ctx, CostSplitBillTypeEnum.PAYMENTSPLIT, element.getPaymentBill().getId());
					// InvalidCostSplitHelper.invalidCostSplit(ctx,
					// CostSplitBillTypeEnum.PAYMENTSPLIT,
					// element.getPaymentBill().getId(),
					// element.getId());
				}
			}
		}

		splitBillInfo.setId(BOSUuid.read(pk.toString()));
		updateEntrySeq(ctx, splitBillInfo);
		collectCostSplit(ctx, CostSplitBillTypeEnum.PAYMENTSPLIT, splitBillInfo.getPaymentBill(), splitBillInfo.getId(), bill.getEntrys());

		if (splitBillInfo.getPaymentBill().getContractBillId() != null && isConWithoutTxt(splitBillInfo)) {
			collectCostSplit(ctx, CostSplitBillTypeEnum.NOTEXTCONSPLIT, splitBillInfo.getPaymentBill(), splitBillInfo.getId(), bill.getEntrys());
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.appendSql("update T_Con_Contractwithouttext set FSplitState=? where fid=?");
			builder.addParam(splitBillInfo.getSplitState().getValue());
			builder.addParam(splitBillInfo.getPaymentBill().getContractBillId());
			builder.execute();
		}
		// 已拆分成本大于成本拆分，第一笔结算超时，更新申请单已完工,本申请制单日期后其它结算类型已完工更新为0
		if (model.get("firstSettCompletePrjAmt") != null) {
			BigDecimal completePrjAmt = FDCHelper.toBigDecimal(model.get("firstSettCompletePrjAmt"));
			BigDecimal prop = FDCHelper.toBigDecimal(
					FDCHelper.divide(reqInfo.getProjectPriceInContract(), completePrjAmt, 4, BigDecimal.ROUND_HALF_UP)).multiply(
					FDCHelper.ONE_HUNDRED);
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.appendSql("update t_con_payrequestbill set fcompleteprjamt=?,FPaymentProportion=? where fid=?");
			builder.addParam(completePrjAmt);
			builder.addParam(prop);
			builder.addParam(payreqID);
			builder.execute();
			
			builder.clear();
			builder.appendSql("update t_con_payrequestbill set fcompleteprjamt=0,FPaymentProportion=0 ");
			builder.appendSql("where fcontractid=? and fid<>? and FPaymentType in ");
			builder.appendSql(" ( select fid from t_fdc_paymenttype where FPayTypeID=? or FPayTypeID=? ) ");
			builder.appendSql("and fcreatetime>? ");
			builder.addParam(conId);
			builder.addParam(payreqID);
			builder.addParam(PaymentTypeInfo.settlementID);
			builder.addParam(PaymentTypeInfo.keepID);
			builder.addParam(reqInfo.getCreateTime());
			builder.execute();
			
		}
		return pk;
	}
	
	
	/**
	 * @see com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean#_submit(com.kingdee.bos.Context, com.kingdee.bos.dao.IObjectValue)
	 */
	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		PaymentSplitInfo bill = (PaymentSplitInfo) model;
		if (bill.isIsWorkLoadBill()) {
			return _saveWorkLoadSplit(ctx, model);
		} else {
			//暂时将归属成本金额全部更新成0,以后付款申请单会控制保证
			String companyId = ContextUtil.getCurrentFIUnit(ctx).getId().toString();
			Map paramMap = FDCUtils.getDefaultFDCParam(ctx, companyId); //FDCUtils.getDefaultFDCParamByKey(ctx, companyId, FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT);
			boolean separtFormPayment = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT);
			boolean isInvoiceMrg = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_INVOICEMRG);
			if (!bill.isIsConWithoutText() && separtFormPayment) {
				//update by david_yang PT048096 2011.04.12 发票模式下 全项目动态成本标中已实现成本为空
				//				bill.setCompletePrjAmt(FDCHelper.ZERO);
				//				for(Iterator iter=bill.getEntrys().iterator();iter.hasNext();){
				//					PaymentSplitEntryInfo entry=(PaymentSplitEntryInfo)iter.next();
				//					entry.setAmount(FDCHelper.ZERO);
				//				}
			}
			if (bill.isIsConWithoutText() && isInvoiceMrg) {
				//处理无文本的发票拆分金额
				bill.setInvoiceAmt(bill.getAmount());
				for (Iterator iter = bill.getEntrys().iterator(); iter.hasNext();) {
					PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) iter.next();
					entry.setInvoiceAmt(entry.getAmount());
					entry.setSplitedInvoiceAmt(entry.getSplitedCostAmt());
				}
			}
		}
		if (bill.getPaymentBill() != null && bill.getPaymentBill().getCompany() != null
				&& bill.getPaymentBill().getCompany().getId() != null) {
			String id = bill.getPaymentBill().getCompany().getId().toString();
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("company.id", id));
			view.setFilter(filter);
			IBeforeAccountView iBefore = BeforeAccountViewFactory.getLocalInstance(ctx);
			boolean has = iBefore.exists(filter);
			if (has) {
				BeforeAccountViewInfo info = iBefore.getBeforeAccountViewCollection(view).get(0);
				bill.setBeAccount(info);
			}
			// 设置orgUnit为付款单的公司ID by sxhong 2008-11-03 16:28:03
			FullOrgUnitInfo orgUnit = new FullOrgUnitInfo();
			orgUnit.setId(BOSUuid.read(id));
			bill.setOrgUnit(orgUnit);
		}

		if (bill.getPaymentBill() != null && bill.getPaymentBill().getContractBillId() != null) {
			String contractId = bill.getPaymentBill().getContractBillId();
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractId", contractId));
			view.setFilter(filter);
			IContractBaseData iBefore = ContractBaseDataFactory.getLocalInstance(ctx);
			boolean has = iBefore.exists(filter);
			if (has) {
				ContractBaseDataInfo info = iBefore.getContractBaseDataCollection(view).get(0);
				bill.setContractBaseData(info);
			}
			if (isConWithoutTxt(bill)) {
				bill.setConWithoutText(ContractWithoutTextFactory.getLocalInstance(ctx).getContractWithoutTextInfo(
						new ObjectUuidPK(BOSUuid.read(contractId))));
			} else {
				bill.setContractBill(ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(
						new ObjectUuidPK(BOSUuid.read(contractId))));
			}
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("paymentBill.id", bill.getPaymentBill().getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("Fivouchered", Boolean.TRUE));
			view.setFilter(filter);
			int voucherSize = super.getPaymentSplitCollection(ctx, view).size();
			bill.setVoucherTimes(voucherSize);
		}

		if (bill.getPaymentBill() != null && bill.getPaymentBill().getPayerAccount() != null) {
			if (bill.getPaymentBill().getPayerAccount().isIsBank() || bill.getPaymentBill().getPayerAccount().isIsCash()
					|| bill.getPaymentBill().getPayerAccount().isIsCashEquivalent()) {
				bill.setIsNeedTransit(true);
				bill.setTransitAccount(FDCUtils.getDefaultFDCParamAccount(ctx, bill.getPaymentBill().getCompany().getId().toString()));
			}
		}
		bill.setIslastVerThisPeriod(true);
		IObjectPK pk = super._submit(ctx, bill);
		PaymentSplitInfo splitBillInfo = null;
		SelectorItemCollection selectorGet = new SelectorItemCollection();
		selectorGet.add("id");
		selectorGet.add("createTime");
		selectorGet.add("paymentBill.id");
		selectorGet.add("paymentBill.curProject.id");
		selectorGet.add("paymentBill.CU.id");
		selectorGet.add("paymentBill.fdcPayReqID");
		selectorGet.add("paymentBill.company.id");
		selectorGet.add("period.id");
		selectorGet.add("period.number");
		selectorGet.add("period.beginDate");
		selectorGet.add("period.endDate");
		selectorGet.add("isConWithoutText");
		selectorGet.add("paymentBill.id");
		selectorGet.add("paymentBill.contractBillId");
		selectorGet.add("paymentBill.fdcPayType.payType.id");
		selectorGet.add("isProgress");
		selectorGet.add("splitState");
		splitBillInfo = super.getPaymentSplitInfo(ctx, pk, selectorGet);
		String prjID = splitBillInfo.getPaymentBill().getCurProject().getId().toString();
		PeriodInfo currentPeriod = FDCUtils.getCurrentPeriod(ctx, prjID, false);
		Date bookedDate = DateTimeUtils.truncateDate(splitBillInfo.getCreateTime());
		String payreqID = splitBillInfo.getPaymentBill().getFdcPayReqID();
		String conId = splitBillInfo.getPaymentBill().getContractBillId();
		SelectorItemCollection reqPer = new SelectorItemCollection();
		reqPer.add("id");
		reqPer.add("period.number");
		reqPer.add("period.beginDate");
		reqPer.add("period.endDate");
		reqPer.add("projectPriceInContract");
		reqPer.add("createTime");
		PayRequestBillInfo reqInfo = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillInfo(
				new ObjectUuidPK(BOSUuid.read(payreqID)), reqPer);
		if (currentPeriod != null) {
			PeriodInfo contractPeriod = reqInfo.getPeriod();
			if (contractPeriod != null && contractPeriod.getNumber() > currentPeriod.getNumber()) {
				if (bookedDate.before(contractPeriod.getBeginDate())) {
					bookedDate = contractPeriod.getBeginDate();
				} else if (bookedDate.after(contractPeriod.getEndDate())) {
					bookedDate = contractPeriod.getEndDate();
				}
				FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
				builder.clear();
				builder.appendSql("update T_FNC_PaymentSplit set FPeriodId=?, FBookedDate=? where FID=?");
				builder.addParam(contractPeriod.getId().toString());
				builder.addParam(FDCDateHelper.getSqlDate(bookedDate));
				builder.addParam(splitBillInfo.getId().toString());
				builder.executeUpdate();

				builder = new FDCSQLBuilder(ctx);
				builder.clear();
				builder.appendSql("update T_FNC_PaymentSplit set FIslastVerThisPeriod=0 where FPeriodID=? and fid<>? and Fpaymentbillid=?");
				builder.addParam(contractPeriod.getId().toString());
				builder.addParam(splitBillInfo.getId().toString());
				builder.addParam(splitBillInfo.getPaymentBill().getId().toString());
				builder.executeUpdate();
			} else if (currentPeriod != null) {
				if (bookedDate.before(currentPeriod.getBeginDate())) {
					bookedDate = currentPeriod.getBeginDate();
				} else if (bookedDate.after(currentPeriod.getEndDate())) {
					bookedDate = currentPeriod.getEndDate();
				}
				FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
				builder.clear();
				builder.appendSql("update T_FNC_PaymentSplit set FPeriodId=?, FBookedDate=? where FID=?");
				builder.addParam(currentPeriod.getId().toString());
				builder.addParam(FDCDateHelper.getSqlDate(bookedDate));
				builder.addParam(splitBillInfo.getId().toString());
				builder.executeUpdate();

				builder = new FDCSQLBuilder(ctx);
				builder.clear();
				builder.appendSql("update T_FNC_PaymentSplit set FIslastVerThisPeriod=0 where FPeriodID=? and fid<>? and Fpaymentbillid=?");
				builder.addParam(currentPeriod.getId().toString());
				builder.addParam(splitBillInfo.getId().toString());
				builder.addParam(splitBillInfo.getPaymentBill().getId().toString());
				builder.executeUpdate();
			}
		}
		String companyId = splitBillInfo.getPaymentBill().getCompany().getId().toString();
		//红冲模式才调用删除 2009-05-20
		if (FDCUtils.IsFinacial(ctx, companyId) && !FDCUtils.isAdjustVourcherModel(ctx, companyId)) {
			if (!splitBillInfo.isIsConWithoutText() && splitBillInfo.getPaymentBill() != null
					&& splitBillInfo.getPaymentBill().getFdcPayType() != null
					&& splitBillInfo.getPaymentBill().getFdcPayType().getPayType().getId().toString().equals(PaymentTypeInfo.settlementID)
					&& splitBillInfo.isIsProgress()) {
				EntityViewInfo viewPro = new EntityViewInfo();
				FilterInfo filterPro = new FilterInfo();
				filterPro.getFilterItems().add(
						new FilterItemInfo("paymentBill.contractBillId", splitBillInfo.getPaymentBill().getContractBillId()));
				filterPro.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
				filterPro.getFilterItems().add(new FilterItemInfo("paymentBill.fdcPayType.payType.id", PaymentTypeInfo.progressID));
				viewPro.setFilter(filterPro);
				viewPro.getSelector().add("id");
				viewPro.getSelector().add("hisVoucher.id");
				viewPro.getSelector().add("paymentBill.id");
				PaymentSplitCollection payColl = PaymentSplitFactory.getLocalInstance(ctx).getPaymentSplitCollection(viewPro);
				// 这个方法应该写成require的，保证事务一致性,隐藏危机，但是方法不可追溯，暂时因为补丁不修改了。
				for (Iterator iter = payColl.iterator(); iter.hasNext();) {
					PaymentSplitInfo element = (PaymentSplitInfo) iter.next();
					deleteCostSplit(ctx, CostSplitBillTypeEnum.PAYMENTSPLIT, element.getPaymentBill().getId());
					// InvalidCostSplitHelper.invalidCostSplit(ctx,
					// CostSplitBillTypeEnum.PAYMENTSPLIT,
					// element.getPaymentBill().getId(),
					// element.getId());
				}
			}
		}

		splitBillInfo.setId(BOSUuid.read(pk.toString()));
		updateEntrySeq(ctx, splitBillInfo);
		collectCostSplit(ctx, CostSplitBillTypeEnum.PAYMENTSPLIT, splitBillInfo.getPaymentBill(), splitBillInfo.getId(), bill.getEntrys());

		if (splitBillInfo.getPaymentBill().getContractBillId() != null && isConWithoutTxt(splitBillInfo)) {
			collectCostSplit(ctx, CostSplitBillTypeEnum.NOTEXTCONSPLIT, splitBillInfo.getPaymentBill(), splitBillInfo.getId(), bill
					.getEntrys());
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.appendSql("update T_Con_Contractwithouttext set FSplitState=? where fid=?");
			builder.addParam(splitBillInfo.getSplitState().getValue());
			builder.addParam(splitBillInfo.getPaymentBill().getContractBillId());
			builder.execute();
		}
		// 已拆分成本大于成本拆分，第一笔结算超时，更新申请单已完工,本申请制单日期后其它结算类型已完工更新为0
		if (model.get("firstSettCompletePrjAmt") != null) {
			BigDecimal completePrjAmt = FDCHelper.toBigDecimal(model.get("firstSettCompletePrjAmt"));
			BigDecimal prop = FDCHelper.toBigDecimal(
					FDCHelper.divide(reqInfo.getProjectPriceInContract(), completePrjAmt, 4, BigDecimal.ROUND_HALF_UP)).multiply(
					FDCHelper.ONE_HUNDRED);
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.appendSql("update t_con_payrequestbill set fcompleteprjamt=?,FPaymentProportion=? where fid=?");
			builder.addParam(completePrjAmt);
			builder.addParam(prop);
			builder.addParam(payreqID);
			builder.execute();

			builder.clear();
			builder.appendSql("update t_con_payrequestbill set fcompleteprjamt=0,FPaymentProportion=0 ");
			builder.appendSql("where fcontractid=? and fid<>? and FPaymentType in ");
			builder.appendSql(" ( select fid from t_fdc_paymenttype where FPayTypeID=? or FPayTypeID=? ) ");
			builder.appendSql("and fcreatetime>? ");
			builder.addParam(conId);
			builder.addParam(payreqID);
			builder.addParam(PaymentTypeInfo.settlementID);
			builder.addParam(PaymentTypeInfo.keepID);
			builder.addParam(reqInfo.getCreateTime());
			builder.execute();

		}
		return pk;
	}
	
	private IObjectPK _saveWorkLoadSplit(Context ctx,IObjectValue model) throws EASBizException, BOSException{
		PaymentSplitInfo info=(PaymentSplitInfo)model;
		info.setIslastVerThisPeriod(true);
		IObjectPK pk = super._save(ctx, model);
		//设置期间
		String prjID = info.getCurProject().getId().toString();
		// 财务期间
		PeriodInfo currentPeriod = FDCUtils.getCurrentPeriod(ctx, prjID, false);
		//业务日期(制单日期即拆分日期)
		Date bookedDate = DateTimeUtils.truncateDate(info.getCreateTime());
		if (currentPeriod != null) {
			SelectorItemCollection tempSelector = new SelectorItemCollection();
			tempSelector.add("id");
			tempSelector.add("period.number");
			tempSelector.add("period.beginDate");
			tempSelector.add("period.endDate");
			/**
			 *  与罗忠慧讨论，取拆分对应单据工程量确认单的期间
			 *  错误：当成本月结后，冻结财务，则期间成下一期间，导致工程量与拆分期间不一致
			 */
			//ContractBillInfo contract=ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(info.getContractBill().getId().toString()),tempSelector);
			WorkLoadConfirmBillInfo billInfo =WorkLoadConfirmBillFactory.getLocalInstance(ctx).getWorkLoadConfirmBillInfo(new ObjectUuidPK(info.getWorkLoadConfirmBill().getId().toString()),tempSelector);
			// 单据期间
			PeriodInfo billPeriod = billInfo.getPeriod();
			/**
			 *  单据期间 > 财务期间: 业务日期 < 单据期间开始日期，业务日期=单据期间开始日期;
			 *                      业务日期 > 单据期间结束日期，业务日期=单据期间结束日期.
			 *                      业务期间 = 单据期间
			 */
			if (billPeriod != null && billPeriod.getNumber() > currentPeriod.getNumber()) {
				if (bookedDate.before(billPeriod.getBeginDate())) {
					bookedDate = billPeriod.getBeginDate();
				} else if (bookedDate.after(billPeriod.getEndDate())) {
					bookedDate = billPeriod.getEndDate();
				}
				FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
				builder.clear();
				builder.appendSql("update T_FNC_PaymentSplit set FPeriodId=?, FBookedDate=? where FID=?");
				builder.addParam(billPeriod.getId().toString());
				builder.addParam(FDCDateHelper.getSqlDate(bookedDate));
				builder.addParam(info.getId().toString());
				builder.executeUpdate();

				builder = new FDCSQLBuilder(ctx);
				builder.clear();
				builder.appendSql("update T_FNC_PaymentSplit set FIslastVerThisPeriod=0 where FPeriodID=? and fid<>? and FWorkLoadBillID=?");
				builder.addParam(billPeriod.getId().toString());
				builder.addParam(info.getId().toString());
				builder.addParam(info.getWorkLoadConfirmBill().getId().toString());
				builder.executeUpdate();
			} else if (currentPeriod != null) {
				/**
				 * 单据期间 < 财务期间: 业务日期   > 财务开始日期,业务日期=财务期间开始日期 
				 *                     业务日期 < 财务结束日期,业务日期=财务结束日期
				 *                     业务期间 = 财务期间
				 */
				if (bookedDate.before(currentPeriod.getBeginDate())) {
					bookedDate = currentPeriod.getBeginDate();
				} else if (bookedDate.after(currentPeriod.getEndDate())) {
					bookedDate = currentPeriod.getEndDate();
				}
				FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
				builder.clear();
				builder.appendSql("update T_FNC_PaymentSplit set FPeriodId=?, FBookedDate=? where FID=?");
				builder.addParam(currentPeriod.getId().toString());
				builder.addParam(FDCDateHelper.getSqlDate(bookedDate));
				builder.addParam(info.getId().toString());
				builder.executeUpdate();

				//将以前的拆分更新成不是最新版本
				builder = new FDCSQLBuilder(ctx);
				builder.clear();
				builder.appendSql("update T_FNC_PaymentSplit set FIslastVerThisPeriod=0 where FPeriodID=? and fid<>? and FWorkLoadBillID=?");
				builder.addParam(currentPeriod.getId().toString());
				builder.addParam(info.getId().toString());
				builder.addParam(info.getWorkLoadConfirmBill().getId().toString());
				builder.executeUpdate();
			}
		}
		
		info.setId(BOSUuid.read(pk.toString()));
		updateEntrySeq(ctx, info);
		collectCostSplit(ctx, CostSplitBillTypeEnum.PAYMENTSPLIT, info.getWorkLoadConfirmBill(), info.getId(), info.getEntrys());
		return pk;
	}
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		// 基类已实现调用 _delete(Context ctx, IObjectPK[] arrayPK)
		super._delete(ctx, pk);
	}
	public void _delete(Context ctx, IObjectPK[] arrayPK) throws BOSException,
			EASBizException {
		int i = 0;
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		for (i = 0; i < arrayPK.length; i++) {
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("paymentBill.id");
			selector.add("paymentBill.contractBillId");
			selector.add("workLoadConfirmBill.id");
			selector.add("isWorkLoadBill");
			PaymentSplitInfo splitBill = getPaymentSplitInfo(ctx, arrayPK[i],selector);
			if(splitBill.isIsWorkLoadBill()){
				BOSUuid workLoadId = splitBill.getWorkLoadConfirmBill().getId();
				deleteCostSplit(ctx, CostSplitBillTypeEnum.PAYMENTSPLIT, workLoadId);
				
			}else{
				BOSUuid costBillId = splitBill.getPaymentBill().getId();
				deleteCostSplit(ctx, CostSplitBillTypeEnum.PAYMENTSPLIT, costBillId);
				if (splitBill.getPaymentBill().getContractBillId() != null
						&& isConWithoutTxt(splitBill)) {
					deleteCostSplit(ctx, CostSplitBillTypeEnum.NOTEXTCONSPLIT,
							costBillId);
					builder.clear();
					builder.appendSql("update T_Con_Contractwithouttext set FSplitState=? where fid=?");
					builder.addParam(CostSplitStateEnum.NOSPLIT_VALUE);
					builder.addParam(splitBill.getPaymentBill().getContractBillId());
					builder.execute();
				}
			}
		}
		super._delete(ctx, arrayPK);
	}

	public IObjectPK[] _delete(Context ctx, String oql) throws BOSException,
			EASBizException {
		return super._delete(ctx, oql);
	}


	/**
	 * 判断选择行是不是无文本合同，选择多行返回false
	 * 
	 * @author sxhong Date 2006-12-1
	 * @param table
	 * @return isConWithoutTxt
	 */
	private boolean isConWithoutTxt(PaymentSplitInfo billInfo) {
		BOSObjectType withoutTxtConBosType = new ContractWithoutTextInfo()
				.getBOSType();
		boolean isConWithoutTxt = false;
		isConWithoutTxt = BOSUuid.read(
				billInfo.getPaymentBill().getContractBillId()).getType()
				.equals(withoutTxtConBosType);
		return isConWithoutTxt;
	}

	protected void _reverseSave(Context ctx, IObjectPK srcBillPK,
			IObjectValue srcBillVO, BOTBillOperStateEnum bOTBillOperStateEnum,
			IObjectValue bOTRelationInfo) throws BOSException, EASBizException {

		super._reverseSave(ctx, srcBillPK, srcBillVO, bOTBillOperStateEnum,
				bOTRelationInfo);
		BOTRelationInfo botRelation = (BOTRelationInfo) bOTRelationInfo;
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("beAccount.*");
		sic.add("beAccount.proAccount.*");
		sic.add("paymentBill.id");
		sic.add("paymentBill.contractBillId");
		sic.add("paymentBill.fdcPayType.*");
		sic.add("paymentBill.fiVouchered");
		sic.add("paymentBill.accountant");
		sic.add("paymentBill.voucher");
		sic.add("paymentBill.voucherType");
		sic.add("paymentBill.company.id");
		PaymentSplitInfo payBillInfo = (PaymentSplitInfo) getValue(ctx,
				srcBillPK, sic);
		if (!isConWithoutTxt(payBillInfo)) {
			ContractStateHelper.synToExecState(ctx, payBillInfo
					.getPaymentBill().getContractBillId());
		}
		IPaymentBill paymentBill = PaymentBillFactory.getLocalInstance(ctx);
		if (new VoucherInfo().getBOSType().toString().equals(
				botRelation.getDestEntityID())) {
			// String companyId =
			// payBillInfo.getPaymentBill().getCompany().getId().toString();
			UserInfo userInfo = ContextHelperFactory.getLocalInstance(ctx)
					.getCurrentUser();
			if (BOTBillOperStateEnum.ADDNEW.equals(bOTBillOperStateEnum)) {
				payBillInfo.getPaymentBill().setFiVouchered(true);
				payBillInfo.getPaymentBill().setAccountant(userInfo);
				// payBillInfo.setBillStatus(BillStatusEnum.VOUCHERED);
				VoucherInfo voucherInfo = (VoucherInfo) VoucherFactory
						.getLocalInstance(ctx).getValue(
								new ObjectStringPK(botRelation
										.getDestObjectID()));
				if(payBillInfo.getVoucherTimes()>0){
					VoucherTypeInfo type = FDCUtils.getDefaultFDCParamVoucherType(
							ctx, payBillInfo.getPaymentBill().getCompany().getId().toString());
					if(type!=null){
						FDCSQLBuilder builderUpdate = new FDCSQLBuilder(ctx);
						builderUpdate.clear();
						builderUpdate
								.appendSql("update t_gl_voucher set FVoucherTypeID=? where fid=?");
						builderUpdate.addParam(type.getId().toString());
						builderUpdate.addParam(voucherInfo.getId().toString());
						builderUpdate.executeUpdate();
					}
				}
				payBillInfo.getPaymentBill().setVoucher(voucherInfo);
				payBillInfo.getPaymentBill().setVoucherType(
						voucherInfo.getVoucherType());
				payBillInfo.getPaymentBill().setVoucherNumber(voucherInfo.getNumber());
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("fiVouchered");
				selector.add("accountant");
				selector.add("voucher");
				selector.add("voucherType");
				selector.add("voucherNumber");
				paymentBill.updatePartial(payBillInfo.getPaymentBill(),
						selector);

			} else if (BOTBillOperStateEnum.DELETE.equals(bOTBillOperStateEnum)) {
				payBillInfo.getPaymentBill().setFiVouchered(false);
				payBillInfo.getPaymentBill().setAccountant(null);
				// payBillInfo.setBillStatus(BillStatusEnum.PAYED);
				payBillInfo.getPaymentBill().setVoucher(null);
				payBillInfo.getPaymentBill().setVoucherType(null);
				payBillInfo.getPaymentBill().setVoucherNumber(null);
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("fiVouchered");
				selector.add("accountant");
				selector.add("voucher");
				selector.add("voucherType");
				selector.add("voucherNumber");
				paymentBill.updatePartial(payBillInfo.getPaymentBill(),
						selector);
			}
		}
	}

	protected DAPTransformResult _generateVoucher(Context ctx,
			IObjectCollection sourceBillCollection, IObjectPK botMappingPK)
			throws BOSException, EASBizException {
		DAPTransformResult result = super._generateVoucher(ctx, sourceBillCollection, botMappingPK);
		_afterVoucher(ctx, sourceBillCollection);
		return result;
	}

	protected void _generateVoucher(Context ctx, IObjectPK sourceBillPk)
			throws BOSException, EASBizException {

		super._generateVoucher(ctx, sourceBillPk);
		IObjectCollection sourceBillCollection = new CoreBillBaseCollection();
		PaymentSplitInfo info = super.getPaymentSplitInfo(ctx, sourceBillPk);
		sourceBillCollection.addObject(info);
		_afterVoucher(ctx, sourceBillCollection);
	}

	protected DAPTransformResult _generateVoucher(Context ctx, IObjectPK[] sourceBillPkList,
			IObjectPK botMappingPK, SelectorItemCollection botpSelectors)
			throws BOSException, EASBizException {
		return super._generateVoucher(ctx, sourceBillPkList, botMappingPK,
				botpSelectors);
	}

	protected void _generateVoucher(Context ctx, IObjectPK[] sourceBillPkList)
			throws BOSException, EASBizException {
		super._generateVoucher(ctx, sourceBillPkList);
		int size = sourceBillPkList.length;
		IObjectCollection sourceBillCollection = new CoreBillBaseCollection();
		for (int i = 0; i < size; i++) {
			PaymentSplitInfo info = super.getPaymentSplitInfo(ctx,
					sourceBillPkList[i]);
			sourceBillCollection.addObject(info);
		}
		_afterVoucher(ctx, sourceBillCollection);
	}

	protected SelectorItemCollection getBOTPSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("transitAccount.id");
		sic.add("transitAccount.longNumber");
		sic.add("transitAccount.longName");
		sic.add("contractBaseData.id");
		sic.add("contractBaseData.number");
		sic.add("contractBaseData.name");
		// sic.add("paymentBill.*");
		sic.add("paymentBill.company.*");
		sic.add("paymentBill.currency.*");
		sic.add("paymentBill.payerAccount.id");
		sic.add("paymentBill.payerAccount.longNumber");
		sic.add("paymentBill.payerAccount.longName");
		sic.add("paymentBill.payerAccountBank.*");
		sic.add("paymentBill.actFdcPayeeName.*");
		sic.add("paymentBill.fdcPayType.payType.id");
		sic.add("paymentBill.curProject.projectStatus.id");
		sic.add("paymentBill.curProject.costOrg.*");
		sic.add("beAccount.*");
		sic.add("beAccount.proAccount.id");
		sic.add("beAccount.proAccount.longNumber");
		sic.add("beAccount.proAccount.longName");
		sic.add("beAccount.beforeOtherAccount.id");
		sic.add("beAccount.beforeOtherAccount.longNumber");
		sic.add("beAccount.beforeOtherAccount.longName");
		sic.add("beAccount.proAccount.id");
		sic.add("beAccount.proAccount.longNumber");
		sic.add("beAccount.proAccount.longName");
		sic.add("beAccount.settAccount.id");
		sic.add("beAccount.settAccount.longNumber");
		sic.add("beAccount.settAccount.longName");
		sic.add("entrys.*");
		sic.add("entrys.costAccount.*");
		sic.add("entrys.costAccount.curProject.longNumber");
		sic.add("entrys.costAccount.curProject.longName");
		sic.add("entrys.product.*");
		sic.add("entrys.apportionType.id");
		sic.add("entrys.accountView.id");
		sic.add("entrys.accountView.longNumber");
		sic.add("entrys.accountView.longName");
		sic.add("voucherEntrys.*");
		sic.add("voucherEntrys.transitAccount.id");
		sic.add("voucherEntrys.transitAccount.longNumber");
		sic.add("voucherEntrys.transitAccount.longName");
		sic.add("voucherEntrys.bankAccount.*");
		sic.add("voucherEntrys.accountView.id");
		sic.add("voucherEntrys.accountView.longNumber");
		sic.add("voucherEntrys.accountView.longName");
		sic.add("voucherEntrys.currency.*");
		return sic;
	}

	public SelectorItemCollection getSelectItemForBTP() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("transitAccount.id");
		sic.add("transitAccount.longNumber");
		sic.add("transitAccount.longName");
		sic.add("contractBaseData.id");
		sic.add("contractBaseData.number");
		sic.add("contractBaseData.name");
		// sic.add("paymentBill.*");
		sic.add("paymentBill.company.*");
		sic.add("paymentBill.currency.*");
		sic.add("paymentBill.payerAccount.id");
		sic.add("paymentBill.payerAccount.longNumber");
		sic.add("paymentBill.payerAccount.longName");
		sic.add("paymentBill.payerAccountBank.*");
		sic.add("paymentBill.actFdcPayeeName.*");
		sic.add("paymentBill.fdcPayType.payType.id");
		sic.add("paymentBill.curProject.projectStatus.id");
		sic.add("paymentBill.curProject.costOrg.*");
		sic.add("beAccount.*");
		sic.add("beAccount.proAccount.id");
		sic.add("beAccount.proAccount.longNumber");
		sic.add("beAccount.proAccount.longName");
		sic.add("beAccount.beforeOtherAccount.id");
		sic.add("beAccount.beforeOtherAccount.longNumber");
		sic.add("beAccount.beforeOtherAccount.longName");
		sic.add("beAccount.proAccount.id");
		sic.add("beAccount.proAccount.longNumber");
		sic.add("beAccount.proAccount.longName");
		sic.add("beAccount.settAccount.id");
		sic.add("beAccount.settAccount.longNumber");
		sic.add("beAccount.settAccount.longName");
		sic.add("entrys.*");
		sic.add("entrys.costAccount.*");
		sic.add("entrys.costAccount.curProject.longNumber");
		sic.add("entrys.costAccount.curProject.longName");
		sic.add("entrys.product.*");
		sic.add("entrys.apportionType.id");
		sic.add("entrys.accountView.id");
		sic.add("entrys.accountView.longNumber");
		sic.add("entrys.accountView.longName");
		sic.add("voucherEntrys.*");
		sic.add("voucherEntrys.transitAccount.id");
		sic.add("voucherEntrys.transitAccount.longNumber");
		sic.add("voucherEntrys.transitAccount.longName");
		sic.add("voucherEntrys.bankAccount.*");
		sic.add("voucherEntrys.accountView.id");
		sic.add("voucherEntrys.accountView.longNumber");
		sic.add("voucherEntrys.accountView.longName");
		sic.add("voucherEntrys.currency.*");
		return sic;
	}

	protected boolean _deleteVoucher(Context ctx, IObjectPK sourceBillPk)
			throws BOSException, EASBizException {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("paymentBill.id");
		sic.add("paymentBill.contractBillId");
		sic.add("paymentBill.fdcPayType.payType.id");
		sic.add("paymentBill.fiVouchered");
		PaymentSplitInfo payBillInfo = super.getPaymentSplitInfo(ctx,
				sourceBillPk, sic);
		if (!isConWithoutTxt(payBillInfo)) {
			if (payBillInfo.getPaymentBill() != null
					&& payBillInfo.getPaymentBill().getFdcPayType() != null
					&& payBillInfo.getPaymentBill().getFdcPayType()
							.getPayType() != null
					&& payBillInfo.getPaymentBill().getFdcPayType()
							.getPayType().getId().toString().equals(
									PaymentTypeInfo.progressID)) {
				FilterInfo filter = new FilterInfo();
				// filter.getFilterItems().add(new
				// FilterItemInfo("paymentBill.fdcPayType..payType.id",payBillInfo.getPaymentBill().getContractBillId()));
				filter.getFilterItems().add(
						new FilterItemInfo("isProgress", Boolean.TRUE));
				filter.getFilterItems().add(
						new FilterItemInfo("Fivouchered", Boolean.TRUE));
				filter.getFilterItems().add(
						new FilterItemInfo("paymentBill.contractBillId",
								payBillInfo.getPaymentBill()
										.getContractBillId()));
				if (super.exists(ctx, filter)) {
					throw new com.kingdee.eas.fdc.finance.PaymentSplitException(
							PaymentSplitException.HAD_SETTLE);
				}
			}
		}
		boolean success = super._deleteVoucher(ctx, sourceBillPk);
		if (success) {
			// PaymentSplitInfo payBillInfo = PaymentSplitFactory
			// .getLocalInstance(ctx).getPaymentSplitInfo(sourceBillPk);
			payBillInfo.setHisStatus(null);
			payBillInfo.setVoucherTimes(0);
			payBillInfo.setHisVoucher(null);
			SelectorItemCollection sele = new SelectorItemCollection();
			sele.add("hisStatus");
			sele.add("voucherTimes");
			sele.add("hisVoucher");
			PaymentSplitFactory.getLocalInstance(ctx).updatePartial(
					payBillInfo, sele);
		}
		return success;
	}

	protected void _traceData(Context ctx, List idList) throws BOSException,
			EASBizException {
		Set idSet = FDCHelper.list2Set(idList);
		EntityViewInfo viewSplit = new EntityViewInfo();
		FilterInfo filterSplit = new FilterInfo();
		filterSplit.getFilterItems().add(
				new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		viewSplit.setFilter(filterSplit);
		viewSplit.getSelector().add("*");
		viewSplit.getSelector().add("voucherEntrys.*");
		viewSplit.getSelector().add("paymentBill.id");
		viewSplit.getSelector().add("paymentBill.payerAccount.id");
		viewSplit.getSelector().add("paymentBill.payerAccount.isBank");
		viewSplit.getSelector().add("paymentBill.payerAccount.isCash");
		viewSplit.getSelector()
				.add("paymentBill.payerAccount.isCashEquivalent");
		viewSplit.getSelector().add("paymentBill.company.id");
		viewSplit.getSelector().add("paymentBill.company.name");
		viewSplit.getSelector().add("paymentBill.company.number");
		viewSplit.getSelector().add("beAccount.*");
		viewSplit.getSelector().add("beAccount.proAccount.*");
		viewSplit.getSelector().add("beAccount.settAccount.*");
		PaymentSplitCollection splitColl = super.getPaymentSplitCollection(ctx,
				viewSplit);
		int splitSize = splitColl.size();
		PaymentSplitInfo splitInfo = new PaymentSplitInfo();
		IBeforeAccountView iBefore = BeforeAccountViewFactory
				.getLocalInstance(ctx);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("beAccount.*");
		selector.add("transitAccount.*");
		selector.add("isNeedTransit");
		selector.add("voucherTimes");
		SelectorItemCollection selectorEntry = new SelectorItemCollection();
		selectorEntry.add("transitAccount.*");
		selectorEntry.add("isNeedTransit");
		IPaySplit4Voucher voucherSplit = PaySplit4VoucherFactory
				.getLocalInstance(ctx);
		for (int i = 0; i < splitSize; i++) {
			splitInfo = (PaymentSplitInfo) splitColl.get(i);
			// if (splitInfo.getBeAccount() == null) {
			if (splitInfo.getPaymentBill() != null
					&& splitInfo.getPaymentBill().getCompany() != null
					&& splitInfo.getPaymentBill().getCompany().getId() != null) {
				String id = splitInfo.getPaymentBill().getCompany().getId()
						.toString();
				AccountViewInfo info = FDCUtils.getDefaultFDCParamAccount(ctx,
						id);
				if (splitInfo.getPaymentBill() != null
						&& splitInfo.getPaymentBill().getPayerAccount() != null
						&& (splitInfo.getPaymentBill().getPayerAccount()
								.isIsBank()
								|| splitInfo.getPaymentBill().getPayerAccount()
										.isIsCash() || splitInfo
								.getPaymentBill().getPayerAccount()
								.isIsCashEquivalent())) {
					splitInfo.setIsNeedTransit(true);
					if (info.getId() != null) {
						splitInfo.setTransitAccount(info);
					} else
						throw new FDCException(FDCException.NOACCOUNTVIEW);
				}
				for (int entry = 0, size = splitInfo.getVoucherEntrys().size(); entry < size; entry++) {
					if (splitInfo.getVoucherEntrys().get(entry)
							.isIsNeedTransit()) {
						if (info.getId() != null) {
							splitInfo.getVoucherEntrys().get(entry)
									.setTransitAccount(info);
							voucherSplit.updatePartial(splitInfo
									.getVoucherEntrys().get(entry),
									selectorEntry);
						} else
							throw new FDCException(FDCException.NOACCOUNTVIEW);
					}
				}
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("company.id", id));
				view.setFilter(filter);
				boolean has = iBefore.exists(filter);
				if (has) {
					BeforeAccountViewInfo account = iBefore
							.getBeforeAccountViewCollection(view).get(0);
					splitInfo.setBeAccount(account);
				} else
					throw new CostSplitException(CostSplitException.NOBEACCOUNT);
				view = new EntityViewInfo();
				filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("paymentBill.id", splitInfo
								.getPaymentBill().getId().toString()));
				filter.getFilterItems().add(
						new FilterItemInfo("Fivouchered", Boolean.TRUE));
				view.setFilter(filter);
				int voucherSize = super.getPaymentSplitCollection(ctx, view)
						.size();
				splitInfo.setVoucherTimes(voucherSize);
			}
			// }
			super.updatePartial(ctx, splitInfo, selector);
		}
		int size = idList.size();
		for (int i = 0; i < size; i++) {
			String id = idList.get(i).toString();
			setAccoutForSplit(ctx, id);
		}
	}

	private void setAccoutForSplit(Context ctx, String id)
			throws EASBizException, BOSException {
		ICostAccountWithAccount iCostAccountWithAccount = CostAccountWithAccountFactory
				.getLocalInstance(ctx);
		IPaymentSplitEntry iPaymentSplitEntry = PaymentSplitEntryFactory
				.getLocalInstance(ctx);
		PaymentSplitEntryCollection coll;
		CostAccountWithAccountCollection entryColl = null;
		CostAccountWithAccountInfo entryInfo = new CostAccountWithAccountInfo();
		ICostAccount iCostAccount = CostAccountFactory.getLocalInstance(ctx);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.id", id));
		filter.getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.TRUE));
		view.setFilter(filter);
		view.getSelector().add(new SelectorItemInfo("id"));
		view.getSelector().add(new SelectorItemInfo("seq"));
		view.getSelector().add(new SelectorItemInfo("costAccount.*"));
		view.getSelector().add(new SelectorItemInfo("costAccount.parent.id"));
		view.getSelector().add(new SelectorItemInfo("accountView.*"));
		coll = iPaymentSplitEntry.getPaymentSplitEntryCollection(view);
		int entrySize = coll.size();
		for (int j = 0; j < entrySize; j++) {
			PaymentSplitEntryInfo info = coll.get(j);
			CostAccountInfo costAcc = info.getCostAccount();
			getAccForEntry(info, costAcc, iCostAccountWithAccount, entryColl,
					entryInfo, iPaymentSplitEntry, iCostAccount);
		}
	}

	private void getAccForEntry(PaymentSplitEntryInfo info,
			CostAccountInfo costAcc,
			ICostAccountWithAccount iCostAccountWithAccount,
			CostAccountWithAccountCollection entryColl,
			CostAccountWithAccountInfo entryInfo,
			IPaymentSplitEntry iPaymentSplitEntry, ICostAccount iCostAccount)
			throws EASBizException, BOSException {
		EntityViewInfo entryView = new EntityViewInfo();
		FilterInfo entryFilter = new FilterInfo();
		entryFilter.getFilterItems().add(
				new FilterItemInfo("costAccount.id", costAcc.getId()));
		if (!iCostAccountWithAccount.exists(entryFilter)) {
			if (costAcc.getLevel() == 1) {
				throw new PaymentSplitException(
						PaymentSplitException.NO_ACCOUNT);
			} else {
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("id");
				selector.add("level");
				selector.add("parent.*");
				selector.add("curProject.name");
				selector.add("fullOrgUnit.name");
				if (costAcc.getParent().getId() != null) {
					CostAccountInfo parent = iCostAccount.getCostAccountInfo(
							new ObjectUuidPK(costAcc.getParent().getId()),
							selector);
					getAccForEntry(info, parent, iCostAccountWithAccount,
							entryColl, entryInfo, iPaymentSplitEntry,
							iCostAccount);
				} else {
					throw new PaymentSplitException(
							PaymentSplitException.ACCOUNT_WRONG);
				}
			}
		}
		entryView.setFilter(entryFilter);
		entryColl = iCostAccountWithAccount
				.getCostAccountWithAccountCollection(entryView);
		if (entryColl.size() == 1) {
			entryInfo = entryColl.get(0);
			if (entryInfo.getAccount() != null) {
				info.setAccountView(entryInfo.getAccount());
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("accountView");
				iPaymentSplitEntry.updatePartial(info, selector);
			}
		}
	}

	protected void _afterVoucher(Context ctx,
			IObjectCollection sourceBillCollection) throws BOSException,
			EASBizException {
		CoreBillBaseCollection coll = (CoreBillBaseCollection) sourceBillCollection;
		int size = coll.size();
		PaymentSplitInfo info = new PaymentSplitInfo();
		for (int i = 0; i < size; i++) {
			info = (PaymentSplitInfo) coll.get(i);
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("*");
			sic.add("hisStatus.*");
			sic.add("hisVoucher.*");
			sic.add("beAccount.*");
			sic.add("beAccount.proAccount.*");
			sic.add("paymentBill.id");
			sic.add("paymentBill.contractBillId");
			sic.add("paymentBill.fdcPayType.payType.id");
			sic.add("paymentBill.fiVouchered");
			sic.add("paymentBill.accountant");
			sic.add("paymentBill.voucher.*");
			sic.add("paymentBill.voucherType");
			sic.add("paymentBill.company");
			sic.add("paymentBill.curProject.projectStatus.*");
			PaymentSplitInfo payBillInfo = PaymentSplitFactory
					.getLocalInstance(ctx).getPaymentSplitInfo(
							new ObjectUuidPK(info.getId()), sic);
			payBillInfo.setHisStatus(payBillInfo.getPaymentBill()
					.getCurProject().getProjectStatus());
			int times = payBillInfo.getVoucherTimes();
			payBillInfo.setVoucherTimes(times + 1);
			payBillInfo
					.setHisVoucher(payBillInfo.getPaymentBill().getVoucher());
			SelectorItemCollection sele = new SelectorItemCollection();
			sele.add("hisStatus");
			sele.add("voucherTimes");
			sele.add("hisVoucher");
			super.updatePartial(ctx, payBillInfo, sele);
			IVoucher voucher = VoucherFactory.getLocalInstance(ctx);
			if (!payBillInfo.isIsConWithoutText()
					&& payBillInfo.getPaymentBill() != null
					&& payBillInfo.getPaymentBill().getFdcPayType() != null
					&& payBillInfo.getPaymentBill().getFdcPayType()
							.getPayType().getId().toString().equals(
									PaymentTypeInfo.settlementID)
					&& payBillInfo.isIsProgress()) {

				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("paymentBill.contractBillId",
								payBillInfo.getPaymentBill()
										.getContractBillId()));
				filter.getFilterItems().add(
						new FilterItemInfo("state",
								FDCBillStateEnum.INVALID_VALUE,
								CompareType.NOTEQUALS));
				filter.getFilterItems().add(
						new FilterItemInfo("paymentBill.fdcPayType.payType.id",
								PaymentTypeInfo.progressID));
				view.setFilter(filter);
				view.getSelector().add("id");
				view.getSelector().add("hisVoucher.id");
				view.getSelector().add("paymentBill.id");
				PaymentSplitCollection payColl = PaymentSplitFactory
						.getLocalInstance(ctx).getPaymentSplitCollection(view);
				List idList = new ArrayList();
				VoucherInfo newVoucher = new VoucherInfo();
				for (Iterator iter = payColl.iterator(); iter.hasNext();) {
					PaymentSplitInfo element = (PaymentSplitInfo) iter.next();
					EntityViewInfo mapping = new EntityViewInfo();
					FilterInfo mappingFilter = new FilterInfo();
					mappingFilter.getFilterItems().add(
							new FilterItemInfo("srcEntityID", element
									.getBOSType()));
					mappingFilter.getFilterItems().add(
							new FilterItemInfo("destEntityID", newVoucher
									.getBOSType()));
					mappingFilter.getFilterItems().add(
							new FilterItemInfo("srcObjectID", element.getId()
									.toString()));
					mapping.setFilter(mappingFilter);
					mapping.getSelector().add("id");
					mapping.getSelector().add("destObjectID");
					mapping.getSorter().add(new SorterItemInfo("date"));
					BOTRelationCollection relationColl = BOTRelationFactory
							.getLocalInstance(ctx).getCollection(mapping);
					if (relationColl.size() > 0) {
						BOTRelationInfo botInfo = relationColl.get(relationColl
								.size() - 1);
						SelectorItemCollection voucherSelector = new SelectorItemCollection();
						voucherSelector.add("id");
						voucherSelector.add("hasReversed");
						VoucherInfo oldInfo = (VoucherInfo) voucher.getValue(
								new ObjectUuidPK(BOSUuid.read(botInfo
										.getDestObjectID())), voucherSelector);
						if (!oldInfo.isHasReversed()) {
							idList.add(botInfo.getDestObjectID());
							element.setIsRedVouchered(true);
							SelectorItemCollection selector = new SelectorItemCollection();
							selector.add("hisVoucher");
							selector.add("isRedVouchered");
							super.updatePartial(ctx, element, selector);
						}
					}
				}
				if (idList.size() > 0) {
					IObjectPK pk = voucher.reverseSaveBatch(idList);
					PaySplitUtilFacadeFactory.getLocalInstance(ctx)
							.traceReverseVoucher(pk);
				}
			}
		}
	}

	protected void _traceSplitByPay(Context ctx, String id)
			throws BOSException, EASBizException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("paymentBill.id", id));
		filter.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
						CompareType.NOTEQUALS));
		view.setFilter(filter);
		if(super.exists(ctx,filter)){
			PaymentSplitCollection coll = super.getPaymentSplitCollection(ctx, view);
			for(Iterator it = coll.iterator();it.hasNext();){
				PaymentSplitInfo info = (PaymentSplitInfo) it.next();
				setAccoutForSplit(ctx, info.getId().toString());
			}
		}
	}
	//目前只有工程量调用 
	protected IObjectValue _getNewData(Context ctx, Map param)
			throws BOSException ,EASBizException{
		String contractId=null;
		String projectId=null;
		String paymentId=(String)param.get("paymentId");
		String workLoadBillId=(String)param.get("workLoadBillId");
		if(paymentId==null&&workLoadBillId==null){
			throw new NullPointerException("paymentid and workloadId can't all be null!");
		}

		PaymentBillInfo paymentInfo=null;
		WorkLoadConfirmBillInfo workLoadInfo = null;
		

		if (paymentId != null) {
			// TODO 补全付款信息
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("id");
			selector.add("contractBillId");
			paymentInfo = PaymentBillFactory.getLocalInstance(ctx).getPaymentBillInfo(new ObjectUuidPK(paymentId), selector);
			contractId=paymentInfo.getContractBillId();
		} else if (workLoadBillId != null) {
			// 补全工程量单据信息
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("id");
			selector.add("contractBill.id");
			selector.add("contractBill.curProject.id");
			selector.add("number");
			selector.add("hasSettled");
			selector.add("workLoad");
			selector.add("period.id");
			selector.add("period.number");
			workLoadInfo = WorkLoadConfirmBillFactory.getLocalInstance(ctx).getWorkLoadConfirmBillInfo(new ObjectUuidPK(workLoadBillId), selector);
			contractId=workLoadInfo.getContractBill().getId().toString();
			if(workLoadInfo.isHasSettled()){
				//24、	结算后的工程量拆分时，加上校验，必须结算单拆分后才可以拆分此工程量确认单。否则无法带出保修款的拆分
				FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
				builder.appendSql("select 1 from T_Con_SettlementCostSplit where fsettlementBillId in (select fid from T_Con_ContractSettlementBill where fcontractbillid=? and fisFinalSettle=1) and fstate<>? ");
				builder.addParam(contractId);
				builder.addParam(FDCBillStateEnum.INVALID_VALUE);
				if(!builder.isExist()){
					throw new EASBizException(new NumericExceptionSubItem("100","结算后的工程量拆分必须在结算单拆分后才可以拆分！"));
				}
			}
		}
		PeriodInfo period=null;
		if(projectId==null){
			period=null;
		}else{
			period=FDCUtils.getCurrentPeriod(ctx, projectId, false);
		}
		ISplitImporter importer=new PaymentCostSplitImporter(ctx,contractId,period);
		PaymentSplitInfo info=(PaymentSplitInfo)importer.importSplit();
		
		if(paymentId!=null){
			info.setPaymentBill(paymentInfo);
			info.setIsWorkLoadBill(false);
	
			// 项目资金计划 - modified by zhaoqin on 2013/10/15
			paymentInfo = (PaymentBillInfo)param.get("paymentInfo");
			ContractBillInfo contractBillInfo = (ContractBillInfo)param.get("contractBillInfo");
			info.setContractBill(contractBillInfo);
			info.setPaymentBill(paymentInfo);
			dealHeadData(ctx, info);
						
		}else if(workLoadBillId!=null){
			//补全工程量单据信息
			info.setWorkLoadConfirmBill(workLoadInfo);
			info.setIsWorkLoadBill(true);
			info.setIsProgress(false);
			info.setPayAmount(FDCHelper.ZERO);
			info.setCompletePrjAmt(workLoadInfo.getWorkLoad());
		}
		ISplitImportHandler handler=new PaymentCostSplitImportHandler(ctx);
		handler.handle(info);
		
		if (null == info.getCreateTime()) {
			info.setCreateTime(new Timestamp(System.currentTimeMillis()));
		}
		if (null == info.getLastUpdateTime()) {
			info.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
		}
		
		return info;
	}

	/**
	 * 项目资金计划：自动拆分 - 付款拆分自动根据合同关联的合约规划的科目及比例自动拆分
	 * 受参数 "FDC901_ISAUTOSPLIT" 控制
	 * @param ctx
	 * @param billId
	 * @throws BOSException
	 * @throws EASBizException
	 * @Author：zhaoqin
	 * @CreateTime：2013-8-23
	 */
	public void _autoSplit(Context ctx, String paymentId) throws BOSException, EASBizException {
		PaymentBillInfo paymentInfo = getPaymentBillInfo(ctx, paymentId);
		ContractBillInfo contractBillInfo = getContractBillInfo(ctx, paymentInfo.getContractBillId());
		
		// 是否为成本拆分
		if (null == contractBillInfo || !contractBillInfo.isIsCoseSplit())
			return;

		/**
		 * 是否自动拆分: FDC901_ISAUTOSPLIT
		 *  0: 合同自动拆分
		 *  1: 全部自动拆分
		 *  2: 不自动拆分
		 */
		String autoSplit = FDCUtils.getFDCParamByKey(ctx, contractBillInfo.getCurProject().getCostCenter().getId().toString(), 
				FDCConstants.FDC_PARAM_ISAUTOSPLIT);
		if (null == autoSplit && !"1".equals(autoSplit))
			return;

		// 是否为完全拆分，是否关连了框架合约
		if (null == contractBillInfo.getSplitState() || !contractBillInfo.getSplitState().equals(CostSplitStateEnum.ALLSPLIT)
				|| contractBillInfo.getProgrammingContract() == null) {
			logger.info("不符合自动拆分的条件,不能自动拆分.");
			throw new EASBizException(new NumericExceptionSubItem("100", "不符合自动拆分的条件,不能自动拆分."));
		}
		
		Map param = new HashMap();
		param.put("paymentId", paymentId);
		param.put("paymentInfo", paymentInfo);
		param.put("contractBillInfo", contractBillInfo);
		PaymentSplitInfo model = (PaymentSplitInfo) _getNewData(ctx, param);
		model.setState(FDCBillStateEnum.SAVED);
		
		PaymentSplitHelper.handleCostAdjustModelSplitedAmt(ctx, model, contractBillInfo.getId().toString());
		splitBaseOnProp(ctx, model);
		
		model.setSplitState(CostSplitStateEnum.ALLSPLIT);
		_addnew(ctx, model);
	}

	/**
	 * 描述：根据框架合约拆分
	 * @param ctx
	 * @param model
	 * @throws BOSException
	 * @throws EASBizException
	 * @Author：zhaoqin
	 * @CreateTime：2013-8-27
	 */
	private void splitBaseOnProp(Context ctx, PaymentSplitInfo model) throws BOSException, EASBizException {
		IObjectPK comPK = new ObjectUuidPK(model.getContractBill().getOrgUnit().getId().toString());
		HashMap hmParamIn = new HashMap();
		hmParamIn.put(FDCConstants.FDC_PARAM_INVOICEMRG, comPK);
		hmParamIn.put(FDCConstants.FDC_PARAM_SIMPLEINVOICE, comPK);
		//hmParamIn.put(FDCConstants.FDC_PARAM_SIMPLEFINACIAL, comPK);
		//hmParamIn.put(FDCConstants.FDC_PARAM_SIMPLEFINACIALEXTEND, comPK);
		//hmParamIn.put(FDCConstants.FDC_PARAM_FINACIAL, comPK);
		//hmParamIn.put(FDCConstants.FDC_PARAM_SIMPLEINVOICE, comPK);
		
		HashMap params = FDCUtils.getParamHashMapBatch(ctx, hmParamIn);
		boolean isInvoiceMgr = FDCUtils.getParamValue(params, FDCConstants.FDC_PARAM_INVOICEMRG);
		boolean isSimpleInvoice = FDCUtils.getParamValue(params, FDCConstants.FDC_PARAM_SIMPLEINVOICE);
		//boolean isSimpleFinancial = FDCUtils.getParamValue(params, FDCConstants.FDC_PARAM_SIMPLEFINACIAL);
		//boolean isSimpleFinancialExtend = FDCUtils.getParamValue(params, FDCConstants.FDC_PARAM_SIMPLEFINACIALEXTEND);
		//boolean isFinacial = FDCUtils.getParamValue(params, FDCConstants.FDC_PARAM_FINACIAL);
		
		model.setDescription("splitBaseOnProp"); //按比例拆分
		PaymentSplitEntryCollection entrys = model.getEntrys();
		FDCSplitBillEntryCollection fdcEntrys = new FDCSplitBillEntryCollection();
		BigDecimal splitCostAmtSum = FDCHelper.ZERO; //成本拆分金额个明细行合计
		BigDecimal hadSplitCostAmtSum = FDCHelper.ZERO; //已拆分付款金额各明细行合计
		BigDecimal hadSplitPayedAmtSum = FDCHelper.ZERO; //已拆分付款金额各明细行合计
		BigDecimal hadSplitInvoiceAmtSum = FDCHelper.ZERO; 
		for (int i = 0; i < entrys.size(); i++) {
			PaymentSplitEntryInfo entry = entrys.get(i);
			fdcEntrys.add(entry);
			entry.getContractAmt();
			
			splitCostAmtSum = splitCostAmtSum.add(FDCHelper.toBigDecimal(entry.getCostAmt()));
			hadSplitCostAmtSum = hadSplitCostAmtSum.add(FDCHelper.toBigDecimal(entry.getSplitedCostAmt()));
			hadSplitPayedAmtSum = hadSplitPayedAmtSum.add(FDCHelper.toBigDecimal(entry.getSplitedPayedAmt()));
			hadSplitInvoiceAmtSum = hadSplitInvoiceAmtSum.add(FDCHelper.toBigDecimal(entry.getSplitedInvoiceAmt()));
		}
		
		//归属成本金额
		HashMap costMap = new HashMap();
		costMap.put("headCostAmt", model.getCompletePrjAmt());//表头的实付金额
		costMap.put("splitCostAmtSum", splitCostAmtSum);
		costMap.put("hadSplitCostAmtSum", hadSplitCostAmtSum);
		PaymentAutoSplitHelper.splitCostAmtBaseOnProp(fdcEntrys, costMap);
		calcAmount(0, model, fdcEntrys);
		
		//归属付款金额
		HashMap payedMap = new HashMap();
		payedMap.put("headPayedAmt", model.getAmount());//表头的实付金额
		payedMap.put("splitCostAmtSum", splitCostAmtSum);//成本拆分金额个明细行合计
		payedMap.put("hadSplitPayedAmtSum", hadSplitPayedAmtSum);//已拆分付款金额各明细行合计
		PaymentAutoSplitHelper.splitPayedAmtBaseOnProp(fdcEntrys, payedMap);

		if (isInvoiceMgr || isSimpleInvoice) {
			//归属发票金额
			HashMap invoiceMap = new HashMap();
			invoiceMap.put("headInvoiceAmt", model.getInvoiceAmt());//表头的发票金额
			invoiceMap.put("splitCostAmtSum", splitCostAmtSum);//成本拆分金额个明细行合计
			invoiceMap.put("hadSplitInvoiceAmtSum", hadSplitInvoiceAmtSum);// 已拆分发票金额各明细行合计
			PaymentAutoSplitHelper.splitInvoiceAmtBaseOnProp(fdcEntrys, invoiceMap);
			calInvoiceAmt(isInvoiceMgr, isSimpleInvoice, model, entrys);
		}
	}
	
	private void calcAmount(int rowIndex, PaymentSplitInfo model, FDCSplitBillEntryCollection fdcEntrys) {
		BigDecimal amountTotal = FDCHelper.ZERO;
		BigDecimal amount = FDCHelper.ZERO;
		//计算拆分总金额
		for (int i = 0; i < fdcEntrys.size(); i++) {
			FDCSplitBillEntryInfo entry = fdcEntrys.get(i);
			if (entry.getLevel() == 0) {
				amount = entry.getAmount();
				if (amount != null) {
					amountTotal = amountTotal.add(amount);
				}
			}
		}
		amountTotal = amountTotal.setScale(2, BigDecimal.ROUND_HALF_UP);
		model.setAmount(amountTotal);

	}

	private void calInvoiceAmt(boolean isInvoiceMgr, boolean isSimpleInvoice, PaymentSplitInfo model, PaymentSplitEntryCollection entrys) {
		if (!isInvoiceMgr && !isSimpleInvoice) {
			return;
		}
		BigDecimal amountTotal = FDCHelper.ZERO;
		for (int i = 0; i < entrys.size(); i++) {
			PaymentSplitEntryInfo entry = entrys.get(i);
			BigDecimal amount = entry.getInvoiceAmt();
			if (amount == null) {
				amount = FDCHelper.ZERO;
			} else if (entry.getLevel() == 0) {
				amountTotal = amountTotal.add(amount);
			}
		}
		model.setInvoiceAmt(amountTotal);
	}

	private PaymentBillInfo getPaymentBillInfo(Context ctx, String paymentId) throws BOSException, EASBizException {
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("id");
		selectors.add("number");
		selectors.add("name");
		selectors.add("amount");
		selectors.add("localAmt");//取本币
		selectors.add("projectPriceInContract");
		selectors.add("contractBillId");
		selectors.add("fdcPayReqID");
		selectors.add("fdcPayType.payType.id");
		selectors.add("fdcPayType.number");
		selectors.add("fdcPayType.name");
		selectors.add("curProject.id");
		selectors.add("payerAccount.id");
		selectors.add("payerAccount.isBank");
		selectors.add("payerAccount.isCash");
		selectors.add("payerAccount.isCashEquivalent");
		selectors.add("company.id");
		selectors.add("actPayAmt");
		selectors.add("actPayLocAmt");
		selectors.add("curProject.id");
		selectors.add("curProject.longNumber");
		selectors.add("curProject.fullOrgUnit.id");
		return PaymentBillFactory.getLocalInstance(ctx).getPaymentBillInfo(new ObjectUuidPK(BOSUuid.read(paymentId)), selectors);
	}
	
	private ContractBillInfo getContractBillInfo(Context ctx, String contractid) throws BOSException, EASBizException {
		BOSObjectType conWithoutTxt = new ContractWithoutTextInfo().getBOSType();
		BOSObjectType curCon = BOSUuid.read(contractid).getType();
		// 无文本合同
		if(conWithoutTxt.equals(curCon))
			return null;		
		
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("hasSettled");
		selector.add("id");
		selector.add("isCoseSplit");
		selector.add("isWorkLoadConfirm");
		selector.add("splitState");
		selector.add("settleAmt");
		selector.add("programmingContract.id");
		selector.add("orgUnit.id");
		selector.add("curProject.costCenter.id");
		return ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractid)), selector);
	}
	
	private PayRequestBillInfo getPayRequestBillInfo(Context ctx, String payReqID) throws BOSException, EASBizException  {
		SelectorItemCollection selectorReq = new SelectorItemCollection();
		selectorReq.add("id");
		selectorReq.add("completePrjAmt");
		selectorReq.add("invoiceAmt");
		return PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillInfo(new ObjectUuidPK(BOSUuid.read(payReqID)), selectorReq);
	}
	
	private Map getParams(Context ctx, PaymentBillInfo paymentInfo) throws BOSException, EASBizException {
		String comId = null;
		if(null != paymentInfo.getCurProject().getFullOrgUnit().getId())
			comId = paymentInfo.getCurProject().getFullOrgUnit().getId().toString();
		//财务组织
		if(StringUtils.isEmpty(comId))
			comId = ContextUtil.getCurrentFIUnit(ctx).getId().toString();
		//财务级参数
		IObjectPK comPK = new ObjectUuidPK(comId);
		HashMap hmParamIn = new HashMap();
	    hmParamIn.put(FDCConstants.FDC_PARAM_ADJUSTVOURCHER, comPK);
	    hmParamIn.put(FDCConstants.FDC_PARAM_SIMPLEFINACIAL, comPK);
	    hmParamIn.put(FDCConstants.FDC_PARAM_SIMPLEFINACIALEXTEND, comPK);
	    hmParamIn.put(FDCConstants.FDC_PARAM_SIMPLEINVOICE, comPK);
	    hmParamIn.put(FDCConstants.FDC_PARAM_FINACIAL, comPK);
	    hmParamIn.put(FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT, comPK);
	    hmParamIn.put(FDCConstants.FDC_PARAM_INVOICEMRG, comPK);
	    return FDCUtils.getParamHashMapBatch(ctx, hmParamIn);
	    /*
		boolean isAdjustVourcherMode=FDCUtils.getBooleanValue4FDCParamByKey(ctx, comId, FDCConstants.FDC_PARAM_ADJUSTVOURCHER);
		boolean isSimpleFinancial = FDCUtils.getBooleanValue4FDCParamByKey(ctx, comId, FDCConstants.FDC_PARAM_SIMPLEFINACIAL);
		boolean isSimpleFinancialExtend = FDCUtils.getBooleanValue4FDCParamByKey(ctx, comId, FDCConstants.FDC_PARAM_SIMPLEFINACIALEXTEND);
		boolean isSimpleInvoice = FDCUtils.getBooleanValue4FDCParamByKey(ctx, comId, FDCConstants.FDC_PARAM_SIMPLEINVOICE);
		boolean isFinacial = FDCUtils.getBooleanValue4FDCParamByKey(ctx, comId, FDCConstants.FDC_PARAM_FINACIAL);
		boolean isWorkLoadSeparate = FDCUtils.getBooleanValue4FDCParamByKey(ctx, comId, FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT);
		boolean invoiceMgr = FDCUtils.getBooleanValue4FDCParamByKey(ctx, comId, FDCConstants.FDC_PARAM_INVOICEMRG);
		*/
	}
	
	private void dealHeadData(Context ctx, PaymentSplitInfo objectValue) throws BOSException, EASBizException {
		createNewData(ctx, objectValue, objectValue.getPaymentBill(), objectValue.getContractBill());
	}
	
	/**
	 * 项目资金计划
	 * @author zhaoqin
	 * @date 2013/10/15
	 */
	private PaymentSplitInfo createNewData(Context ctx, PaymentSplitInfo objectValue, PaymentBillInfo costBillInfo, 
			ContractBillInfo contractInfo) throws BOSException, EASBizException {
		Map paramMap = getParams(ctx, costBillInfo);
		boolean isSimpleFinancial = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_SIMPLEFINACIAL);
		boolean isSimpleFinancialExtend = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_SIMPLEFINACIALEXTEND);
		boolean isSimpleInvoice = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_SIMPLEINVOICE);
		boolean isFinacial = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_FINACIAL);
		boolean isInvoiceMgr = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_INVOICEMRG);
		
		PayRequestBillInfo payReqInfo = getPayRequestBillInfo(ctx, costBillInfo.getFdcPayReqID());
		
		//PaymentSplitInfo objectValue = new PaymentSplitInfo();
		objectValue.setCreator((ContextUtil.getCurrentUserInfo(ctx)));
		objectValue.setPaymentBill(costBillInfo);
		objectValue.setContractBill(contractInfo);
		objectValue.setIsInvalid(false);

		if(isSimpleFinancial){
			if(isSimpleFinancialExtend){
				objectValue.setCompletePrjAmt(costBillInfo.getProjectPriceInContract());
				objectValue.setPayAmount(costBillInfo.getProjectPriceInContract());
			}else{
				objectValue.setPayAmount(costBillInfo.getLocalAmt());
				objectValue.setCompletePrjAmt(costBillInfo.getLocalAmt());
			}
		}else if(isFinacial){
			//调整模式付款金额=合同内工程款
			//红冲模式也=合同内工程款  2009-5-16
			objectValue.setPayAmount(costBillInfo.getProjectPriceInContract());
			costBillInfo.setActPayAmt(costBillInfo.getProjectPriceInContract());
		} else{
			//未启用简单或复杂模式取实付款 2009-05-20 茂业要求
			objectValue.setPayAmount(costBillInfo.getProjectPriceInContract());
		}
		if ((!isSimpleFinancial || isSimpleInvoice || isInvoiceMgr) && costBillInfo.getFdcPayReqID() != null){
			/*
			 * 如果多张付款单,那么成本归属只要第一张的:是否已存在拆分的付款单
			 * 第一张付款单应该是最先生成的那张，不能随便取；以为完工及发票都放在第一张上面，如果随便取得话，将会导致单据跟拆分的不一致
			 */
			boolean isClosed = true ;	//应该指第一张单据
			FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
			//判断是否还有比当前付款单更早的了,如果存在则表明当前单据不是第一张
			builder.appendSql("select 1 from T_CAS_PaymentBill a,T_CAS_PaymentBill b where a.ffdcPayReqID=b.ffdcPayReqID and b.fid=? and a.fcreateTime<b.fcreateTime");
			builder.addParam(costBillInfo.getId().toString());
			if (builder.isExist()) {
				isClosed = false ;
			}

			//设置发票金额
			//R110702-0041：发票金额取数发生变更，之前取付款申请的数据，现在取付款单的数据
			//objectValue.setInvoiceAmt(payReqInfo.getInvoiceAmt());
			SelectorItemCollection selectorReq = new SelectorItemCollection();
			selectorReq.add("id");
			selectorReq.add("invoiceAmt");
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("paymentBill.id", costBillInfo.getId()));
			view.setFilter(filter);
			view.setSelector(selectorReq);
			FDCPaymentBillCollection fdcPaymentCol = FDCPaymentBillFactory.getLocalInstance(ctx).getFDCPaymentBillCollection(view);
			if (fdcPaymentCol != null && fdcPaymentCol.size() > 0) {
				FDCPaymentBillInfo fdcPaymentInfo = fdcPaymentCol.get(0);
				objectValue.setInvoiceAmt(fdcPaymentInfo.getInvoiceAmt());
			} else {
				objectValue.setInvoiceAmt(FDCHelper.ZERO);
			}
			objectValue.setIsColsed(isClosed);
			
			//非简单模式时
			BigDecimal completePrjAmt = FDCConstants.ZERO;
			//结算款
			if (costBillInfo.getFdcPayType().getPayType().getId()
					.toString().equals(PaymentTypeInfo.settlementID)) {
				handleSettlePayment(ctx, objectValue, costBillInfo,isClosed, contractInfo, paramMap);
			}
			//保修款
			else if (costBillInfo.getFdcPayType().getPayType().getId().toString().equals(PaymentTypeInfo.keepID)) {
				objectValue.setIsProgress(false);
				objectValue.setCompletePrjAmt(null);
				objectValue.setPayAmount(costBillInfo.getActPayAmt());
			}				
			//进度款
			else {
				if (isClosed && payReqInfo.getCompletePrjAmt() != null) {
					completePrjAmt = FDCHelper.toBigDecimal(payReqInfo.getCompletePrjAmt(), 2);
				}
				objectValue.setCompletePrjAmt(completePrjAmt);
				objectValue.setPayAmount(costBillInfo.getActPayAmt());
			}
		}

		objectValue.setPaymentBill(costBillInfo);
		if(costBillInfo.getCurProject()!=null)
			objectValue.setCurProject(costBillInfo.getCurProject());
		objectValue.setContractBill(contractInfo);
		objectValue.setIsConWithoutText(false);
		objectValue.setFivouchered(false);
		//修改拆分的小数位，以免由于小数位导致拆分不能完全，影响范围全部拆分,拆分实际只支持两位小数 by zhiyuan_tang 2010/11/29
		objectValue.setCompletePrjAmt(FDCHelper.toBigDecimal(objectValue.getCompletePrjAmt(), 2));
		objectValue.setPayAmount(FDCHelper.toBigDecimal(objectValue.getPayAmount(), 2));
		objectValue.setInvoiceAmt(FDCHelper.toBigDecimal(objectValue.getInvoiceAmt(), 2));
		return objectValue;
	}
	
	/**
	 * 处理结算款
	 */
	private void handleSettlePayment(Context ctx, PaymentSplitInfo objectValue,
			PaymentBillInfo costBillInfo, boolean isClosed, ContractBillInfo conInfo, Map paramMap) throws BOSException, EASBizException {
		FilterInfo filterPay = new FilterInfo();
		filterPay.getFilterItems().add(new FilterItemInfo("paymentBill.contractBillId", costBillInfo.getContractBillId()));
		filterPay.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
		filterPay.getFilterItems().add(new FilterItemInfo("paymentBill.fdcPayType.payType.id", PaymentTypeInfo.settlementID));
		filterPay.getFilterItems().add(new FilterItemInfo("isProgress", Boolean.TRUE));
		if (exists(ctx, filterPay)) {
			objectValue.setIsProgress(false);
			objectValue.setCompletePrjAmt(null);
			objectValue.setPayAmount(costBillInfo.getProjectPriceInContract());
		} else
			handleFirstSettlePayment(objectValue, costBillInfo, isClosed, conInfo, paramMap);
	}
	
	/**
	 * 第一笔结算款处理
	 */
	private void handleFirstSettlePayment(PaymentSplitInfo objectValue,
			PaymentBillInfo costBillInfo, boolean isClosed, ContractBillInfo conInfo, Map paramMap) throws BOSException, EASBizException {
		BigDecimal completePrjAmt = FDCHelper.ZERO;//已完工工程量
		BigDecimal progressTotal=FDCHelper.ZERO;//进度款已完工之和
		BigDecimal progressAmtTotal=FDCHelper.ZERO;//进度款已付款之和
		
		boolean isAdjustVourcherModel =FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_ADJUSTVOURCHER);
		boolean isFinacial = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_FINACIAL);
		boolean isWorkLoadSeparate = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT);
		
		if (conInfo.isHasSettled() && conInfo.getSettleAmt() != null) {
			BigDecimal amountSplit = FDCHelper.ZERO;
			amountSplit = amountSplit.setScale(2);
			EntityViewInfo viewSplit = new EntityViewInfo();
			FilterInfo filterSplit = new FilterInfo();
			filterSplit.getFilterItems().add(new FilterItemInfo("paymentBill.contractBillId", costBillInfo.getContractBillId()));
			filterSplit.getFilterItems().add(new FilterItemInfo("paymentBill.fdcPayType.payType.id", PaymentTypeInfo.progressID));
			if (isFinacial && !isAdjustVourcherModel) {
				filterSplit.getFilterItems().add(new FilterItemInfo("Fivouchered", Boolean.TRUE));
			}
			filterSplit.getFilterItems().add(new FilterItemInfo("isInvalid", Boolean.TRUE, CompareType.NOTEQUALS));
			viewSplit.setFilter(filterSplit);
			viewSplit.getSelector().add("id");
			viewSplit.getSelector().add("payAmount");
			viewSplit.getSelector().add("completePrjAmt");
			viewSplit.getSelector().add("paymentBill.actPayAmt");
			viewSplit.getSelector().add("paymentBill.actPayLocAmt");
			viewSplit.getSelector().add("paymentBill.company.id");
			viewSplit.getSelector().add("paymentBill.payerAccount.*");
			viewSplit.getSelector().add("paymentBill.payerAccountBank.*");
			viewSplit.getSelector().add("paymentBill.currency.*");
			viewSplit.getSelector().add("paymentBill.exchangeRate");
			viewSplit.getSelector().add("paymentBill.fdcPayReqID");
			PaymentSplitCollection splitColl = PaymentSplitFactory.getRemoteInstance().getPaymentSplitCollection(viewSplit);
			PaymentSplitInfo splitInfo = new PaymentSplitInfo();
			int sizeSplit = splitColl.size();
			for (int i = 0; i < sizeSplit; i++) {
				splitInfo = splitColl.get(i);
				PaySplit4VoucherInfo forEntry = new PaySplit4VoucherInfo();
				forEntry.setParent(objectValue);
				forEntry.setPaymentBill(splitInfo.getPaymentBill());
				forEntry.setAccountView(splitInfo.getPaymentBill().getPayerAccount());
				forEntry.setBankAccount(splitInfo.getPaymentBill().getPayerAccountBank());
				forEntry.setCurrency(splitInfo.getPaymentBill().getCurrency());
				forEntry.setAmount(splitInfo.getPaymentBill().getActPayLocAmt());
				forEntry.setExchangeRate(splitInfo.getPaymentBill().getExchangeRate());
				if (splitInfo.getPaymentBill() != null
						&& splitInfo.getPaymentBill().getPayerAccount() != null
						&& (splitInfo.getPaymentBill().getPayerAccount().isIsBank()
								|| splitInfo.getPaymentBill().getPayerAccount().isIsCash() 
								|| splitInfo.getPaymentBill().getPayerAccount().isIsCashEquivalent())) {
					forEntry.setIsNeedTransit(true);
					forEntry.setTransitAccount(FDCUtils.getDefaultFDCParamAccount(null, splitInfo.getPaymentBill().getCompany().getId().toString()));
				}
				//TODO 后续改成批量获取
				SelectorItemCollection payReqSelector=new SelectorItemCollection();
				payReqSelector.add("id");
				payReqSelector.add("costAmount");
				String payReqID = splitInfo.getPaymentBill().getFdcPayReqID();
				PayRequestBillInfo request = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(
								new ObjectUuidPK(BOSUuid.read(payReqID)),payReqSelector);
				forEntry.setPayRequestBill(request);
				forEntry.setCostAmount(request.getCostAmount());
				objectValue.getVoucherEntrys().add(forEntry);
				if (splitInfo.getPayAmount() != null)
					amountSplit = amountSplit.add(splitInfo.getPayAmount());
				//计算进度款已完工之和
				progressTotal=FDCHelper.add(progressTotal, splitInfo.getCompletePrjAmt());
				//计算进度款已付款之和
				progressAmtTotal=FDCHelper.add(progressAmtTotal, splitInfo.getPayAmount());
			}
			if(isAdjustVourcherModel){
				objectValue.setPayAmount(costBillInfo.getProjectPriceInContract());
			}else if(isFinacial){
				//TODO 红冲的时候应该所有实付款这和
				objectValue.setPayAmount(FDCNumberHelper.add(costBillInfo.getActPayLocAmt(),progressAmtTotal));
			}else{
				//未启用简单或复杂模式取实付款 2009-05-20 
				objectValue.setPayAmount(costBillInfo.getProjectPriceInContract());
			}
			// 如果多张付款单,那么成本归属只要第一张的
	
			if (isClosed) {
				completePrjAmt = conInfo.getSettleAmt();
				if (isAdjustVourcherModel || (!isFinacial)) {// 支持简单模式:
					// 否则已拆分大于成本拆分无法保存
					// if(isAdjustVourcherModel() ||
					// (!isSimpleFinancial()&&!isFinacial())){
					//已完工工程量＝结算价－进度款已完工工程量之和
					completePrjAmt=FDCNumberHelper.subtract(completePrjAmt,progressTotal);
					objectValue.put("firstSettCompletePrjAmt", completePrjAmt);
				}
			}
			completePrjAmt = FDCHelper.toBigDecimal(completePrjAmt);
			objectValue.setCompletePrjAmt(completePrjAmt);
	
			// 质保金
			EntityViewInfo viewSett = new EntityViewInfo();
			FilterInfo filterSett = new FilterInfo();
			filterSett.getFilterItems().add(new FilterItemInfo("contractBill.id", costBillInfo.getContractBillId()));
			filterSett.getFilterItems().add(new FilterItemInfo("isFinalSettle", Boolean.TRUE,CompareType.EQUALS));
			viewSett.setFilter(filterSett);
			viewSett.getSelector().add("id");
			viewSett.getSelector().add("qualityGuarante");
			ContractSettlementBillCollection settColl = ContractSettlementBillFactory
					.getRemoteInstance().getContractSettlementBillCollection(viewSett);
			if (settColl.size() == 1) {
				ContractSettlementBillInfo settInfo = settColl.get(0);
				// if (settInfo.getQualityGuarante() != null)
				objectValue.setQualityAmount(FDCHelper.toBigDecimal(settInfo.getQualityGuarante()));
			}
			objectValue.setIsProgress(true);
		}
		
		if(isWorkLoadSeparate){
			objectValue.setIsProgress(false);
		}
	}

}