package com.kingdee.eas.fdc.basedata.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
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
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.PaySplitUtilFacadeFactory;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.basedata.ProjectStatusFactory;
import com.kingdee.eas.fdc.basedata.ProjectStatusInfo;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextCollection;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.finance.IPaymentSplit;
import com.kingdee.eas.fdc.finance.PaymentSplitCollection;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitInfo;
import com.kingdee.eas.fdc.finance.TraceOldSplitVoucherFacadeFactory;
import com.kingdee.eas.fdc.finance.VoucherAdjustReasonEnum;
import com.kingdee.eas.fi.gl.IVoucher;
import com.kingdee.eas.fi.gl.VoucherFactory;
import com.kingdee.eas.fi.gl.VoucherInfo;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.DateTimeUtils;

/**
 * 专门抽象出一个类用于处理项目状态变化引起的凭证变化
 * @author xiaohong_shi
 *
 */
public class FDCTraceVoucherAppHelper {
	public static void _traceVoucher4Get(Context ctx, IObjectPK projectPK)
			throws BOSException, EASBizException {

		CurProjectInfo info = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(projectPK);
		boolean isAdjust = isAdjustModel(ctx);
		if (info.getProjectStatus() != null
				&& info.getProjectStatus().getId().toString().equals(
						ProjectStatusInfo.notGetID) && info.isIsLeaf()) {
			info.setProjectStatus(ProjectStatusFactory.getLocalInstance(ctx)
					.getProjectStatusInfo(
							new ObjectUuidPK(BOSUuid
									.read(ProjectStatusInfo.notIntiID))));

			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("projectStatus"));
			sic.add(new SelectorItemInfo("costOrg"));
			CurProjectFactory.getLocalInstance(ctx).updatePartial(info, sic);
			if (!isAdjust) {
				traceVoucher4GetRedPattern(ctx, info);
			} else {
				traceVoucher4GetAdjustPattern(ctx, info);
			}
		} else {
			throw new FDCException(FDCException.PRJSTATEWRONG);
		}
	}

	public static void _traceVoucher4Flow(Context ctx, IObjectPK projectPK)
			throws BOSException, EASBizException {
		CurProjectInfo info = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(projectPK);
		boolean isAdjust = isAdjustModel(ctx);
		if (info.getProjectStatus() != null
				&& info.getProjectStatus().getId().toString().equals(
						ProjectStatusInfo.notGetID) && info.isIsLeaf()) {
			info.setProjectStatus(ProjectStatusFactory.getLocalInstance(ctx)
					.getProjectStatusInfo(
							new ObjectUuidPK(BOSUuid
									.read(ProjectStatusInfo.flowID))));
			PeriodInfo currentPeriod = FDCUtils.getCurrentPeriod(ctx, info
					.getId().toString(), false);
			CostCenterOrgUnitInfo costOrg = FDCHelper.getCostCenter(info, ctx);
			info.setCostOrg(costOrg);
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder
					.appendSql("update T_FDC_CurProject set fcostorgid=?,fprojectstatusid=? where fid=?");
			builder.addParam(info.getCostOrg().getId().toString());
			builder.addParam(info.getProjectStatus().getId().toString());
			builder.addParam(info.getId().toString());
			builder.execute();
			if (costOrg != null) {
				if (!isAdjust)
					traceVoucher4FlowRedPattern(ctx, info, currentPeriod,
							costOrg);
				else
					traceVoucher4FlowAdjustPattern(ctx, info, currentPeriod,
							costOrg);
			} else {
				throw new FDCException(FDCException.PRJNOCOST);
			}
		} else {
			throw new FDCException(FDCException.PRJSTATEWRONG);
		}

	}

	private  static void traceVoucher4FlowAdjustPattern(Context ctx,
			CurProjectInfo info, PeriodInfo currentPeriod,
			CostCenterOrgUnitInfo costOrg) throws BOSException, EASBizException {
		tractVoucherAdjustPattern(ctx, info, VoucherAdjustReasonEnum.STATUSFLOW);
	}

	/**
	 * @param ctx
	 * @param info
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private  static void tractVoucherAdjustPattern(Context ctx, CurProjectInfo info,
			VoucherAdjustReasonEnum voucherAdjustReasonEnum)
			throws BOSException, EASBizException {
		EntityViewInfo viewContract = new EntityViewInfo();
		FilterInfo filterContract = new FilterInfo();
		filterContract.getFilterItems().add(
				new FilterItemInfo("curProject.id", info.getId().toString()));
		viewContract.setFilter(filterContract);
		viewContract.getSelector().add("id");
		viewContract.getSelector().add("isCoseSplit");
		/***********************************************************************
		 * 找到相关合同
		 */
		ContractBillCollection contractColl = ContractBillFactory
				.getLocalInstance(ctx).getContractBillCollection(viewContract);
		List idList = new ArrayList();

		for (Iterator it = contractColl.iterator(); it.hasNext();) {
			ContractBillInfo contractBillInfo = (ContractBillInfo) it.next();
			if (contractBillInfo.isIsCoseSplit())
				idList.add(contractBillInfo.getId().toString());
		}
		if (idList.size() > 0)
			TraceOldSplitVoucherFacadeFactory.getLocalInstance(ctx)
					.traceAdjustContracts(idList, false,
							voucherAdjustReasonEnum);

		viewContract.getSelector().clear();
		viewContract.getSelector().add("id");
		viewContract.getSelector().add("isCostSplit");
		ContractWithoutTextCollection contractWithoutTextColl = ContractWithoutTextFactory
				.getLocalInstance(ctx).getContractWithoutTextCollection(
						viewContract);
		idList.clear();
		for (Iterator it = contractWithoutTextColl.iterator(); it.hasNext();) {
			ContractWithoutTextInfo contractWithoutTextInfo = (ContractWithoutTextInfo) it
					.next();
			if (contractWithoutTextInfo.isIsCostSplit())
				idList.add(contractWithoutTextInfo.getId().toString());
		}
		if (idList.size() > 0)
			TraceOldSplitVoucherFacadeFactory
					.getLocalInstance(ctx)
					.traceAdjustContracts(idList, true, voucherAdjustReasonEnum);
	}

	/**
	 * 保留以前的红冲模式
	 * 
	 * @param ctx
	 * @param info
	 * @param currentPeriod
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private  static void traceVoucher4FlowRedPattern(Context ctx, CurProjectInfo info,
			PeriodInfo currentPeriod, CostCenterOrgUnitInfo costOrg)
			throws BOSException, EASBizException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);

		/*
		 * info.setCostOrg(getCostOrg(ctx, info)); SelectorItemCollection sic =
		 * new SelectorItemCollection(); sic.add(new
		 * SelectorItemInfo("projectStatus.id")); sic.add(new
		 * SelectorItemInfo("costOrg.id")); super._updatePartial(ctx, info,
		 * sic);
		 */

		// 凭证处理 预付帐款转费用
		Set idSet = new HashSet();
		idSet.add(PaymentTypeInfo.progressID);
		idSet.add(PaymentTypeInfo.settlementID);

		EntityViewInfo viewContract = new EntityViewInfo();
		FilterInfo filterContract = new FilterInfo();
		filterContract.getFilterItems().add(
				new FilterItemInfo("curProject.id", info.getId().toString()));
		viewContract.setFilter(filterContract);
		/***********************************************************************
		 * 找到相关合同
		 */
		ContractBillCollection contractColl = ContractBillFactory
				.getLocalInstance(ctx).getContractBillCollection(viewContract);
		IPaymentSplit paymentSplit = PaymentSplitFactory.getLocalInstance(ctx);
		IVoucher voucher = VoucherFactory.getLocalInstance(ctx);
		VoucherInfo newVoucher = new VoucherInfo();
		for (Iterator it = contractColl.iterator(); it.hasNext();) {
			ContractBillInfo contract = (ContractBillInfo) it.next();
			EntityViewInfo viewSplit = new EntityViewInfo();
			FilterInfo filterSplit = new FilterInfo();
			filterSplit.getFilterItems().add(
					new FilterItemInfo("paymentBill.contractBillId", contract
							.getId().toString()));
			filterSplit.getFilterItems().add(
					new FilterItemInfo("paymentBill.fdcPayType.payType.id",
							idSet, CompareType.INCLUDE));
			filterSplit.getFilterItems().add(
					new FilterItemInfo("Fivouchered", Boolean.TRUE));
			filterSplit.getFilterItems().add(
					new FilterItemInfo("isRedVouchered", Boolean.TRUE,
							CompareType.NOTEQUALS));
			filterSplit.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
							CompareType.NOTEQUALS));
			filterSplit.getFilterItems().add(
					new FilterItemInfo("completePrjAmt", null,
							CompareType.NOTEQUALS));
			viewSplit.setFilter(filterSplit);
			viewSplit.getSelector().add("id");
			viewSplit.getSelector().add("state");
			viewSplit.getSelector().add("createTime");
			viewSplit.getSelector().add("paymentBill.id");
			viewSplit.getSelector().add("paymentBill.fdcPayReqID");
			viewSplit.getSelector().add("hisVoucher.id");
			viewSplit.getSelector().add("isRedVouchered");
			viewSplit.getSelector().add("Fivouchered");
			PaymentSplitCollection splitColl = paymentSplit
					.getPaymentSplitCollection(viewSplit);
			int splitSize = splitColl.size();
			IObjectPK[] pks = new IObjectPK[splitSize];
			List idList = new ArrayList();
			PaymentSplitInfo splitInfo = new PaymentSplitInfo();
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("Fivouchered");
			if (splitSize > 0) {
				for (int i = 0; i < splitSize; i++) {
					splitInfo = splitColl.get(i);
					pks[i] = new ObjectUuidPK(splitInfo.getId());
					EntityViewInfo mapping = new EntityViewInfo();
					FilterInfo mappingFilter = new FilterInfo();
					mappingFilter.getFilterItems().add(
							new FilterItemInfo("srcEntityID", splitInfo
									.getBOSType()));
					mappingFilter.getFilterItems().add(
							new FilterItemInfo("destEntityID", newVoucher
									.getBOSType()));
					mappingFilter.getFilterItems().add(
							new FilterItemInfo("srcObjectID", splitInfo.getId()
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
							Date bookedDate = DateTimeUtils
									.truncateDate(splitInfo.getCreateTime());
//							if (currentPeriod != null) {
//								String payreqID = splitInfo.getPaymentBill()
//										.getFdcPayReqID();
//								SelectorItemCollection reqPer = new SelectorItemCollection();
//								reqPer.add("id");
//								reqPer.add("period.number");
//								reqPer.add("period.beginDate");
//								reqPer.add("period.endDate");
//								PayRequestBillInfo reqInfo = PayRequestBillFactory
//										.getLocalInstance(ctx)
//										.getPayRequestBillInfo(
//												new ObjectUuidPK(BOSUuid
//														.read(payreqID)),
//												reqPer);
//								PeriodInfo contractPeriod = reqInfo.getPeriod();
//								if (contractPeriod != null
//										&& contractPeriod.getNumber() > currentPeriod
//												.getNumber()) {
//									if (bookedDate.before(contractPeriod
//											.getBeginDate())) {
//										bookedDate = contractPeriod
//												.getBeginDate();
//									} else if (bookedDate.after(contractPeriod
//											.getEndDate())) {
//										bookedDate = contractPeriod
//												.getEndDate();
//									}
//									builder = new FDCSQLBuilder(ctx);
//									builder.clear();
//									builder
//											.appendSql("update T_FNC_PaymentSplit set FPeriodId=?, FBookedDate=? where FID=?");
//									builder.addParam(contractPeriod.getId()
//											.toString());
//									builder.addParam(FDCDateHelper
//											.getSqlDate(bookedDate));
//									builder.addParam(splitInfo.getId()
//											.toString());
//									builder.executeUpdate();
//
//									builder = new FDCSQLBuilder(ctx);
//									builder.clear();
//									builder
//											.appendSql("update T_FNC_PaymentSplit set FIslastVerThisPeriod=0 where FPeriodID=? and fid<>? and Fpaymentbillid=?");
//									builder.addParam(contractPeriod.getId()
//											.toString());
//									builder.addParam(splitInfo.getId()
//											.toString());
//									builder.addParam(splitInfo.getPaymentBill()
//											.getId().toString());
//									builder.executeUpdate();
//								} else if (currentPeriod != null) {
//									if (bookedDate.before(currentPeriod
//											.getBeginDate())) {
//										bookedDate = currentPeriod
//												.getBeginDate();
//									} else if (bookedDate.after(currentPeriod
//											.getEndDate())) {
//										bookedDate = currentPeriod.getEndDate();
//									}
//									builder = new FDCSQLBuilder(ctx);
//									builder.clear();
//									builder
//											.appendSql("update T_FNC_PaymentSplit set FPeriodId=?, FBookedDate=? where FID=?");
//									builder.addParam(currentPeriod.getId()
//											.toString());
//									builder.addParam(FDCDateHelper
//											.getSqlDate(bookedDate));
//									builder.addParam(splitInfo.getId()
//											.toString());
//									builder.executeUpdate();
//
//									builder = new FDCSQLBuilder(ctx);
//									builder.clear();
//									builder
//											.appendSql("update T_FNC_PaymentSplit set FIslastVerThisPeriod=0 where FPeriodID=? and fid<>? and Fpaymentbillid=?");
//									builder.addParam(currentPeriod.getId()
//											.toString());
//									builder.addParam(splitInfo.getId()
//											.toString());
//									builder.addParam(splitInfo.getPaymentBill()
//											.getId().toString());
//									builder.executeUpdate();
//								}
//							}
							splitInfo.setFivouchered(false);
							paymentSplit.updatePartial(splitInfo, selector);
						}
					}
				}
			}
			if (idList.size() > 0) {
				IObjectPK pk = voucher.reverseSaveBatch(idList);
				PaySplitUtilFacadeFactory.getLocalInstance(ctx)
						.traceReverseVoucher(pk);
				paymentSplit.generateVoucher(pks);
			}
		}
		EntityViewInfo viewSplit = new EntityViewInfo();
		FilterInfo filterSplit = new FilterInfo();
		filterSplit.getFilterItems().add(
				new FilterItemInfo("paymentBill.curProject.id", info.getId()
						.toString()));
		filterSplit.getFilterItems().add(
				new FilterItemInfo("isConWithoutText", Boolean.TRUE));
		filterSplit.getFilterItems().add(
				new FilterItemInfo("paymentBill.fdcPayType.payType.id", idSet,
						CompareType.INCLUDE));
		filterSplit.getFilterItems().add(
				new FilterItemInfo("Fivouchered", Boolean.TRUE));
		filterSplit.getFilterItems().add(
				new FilterItemInfo("isRedVouchered", Boolean.TRUE,
						CompareType.NOTEQUALS));
		filterSplit.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
						CompareType.NOTEQUALS));
		filterSplit.getFilterItems().add(
				new FilterItemInfo("completePrjAmt", null,
						CompareType.NOTEQUALS));
		viewSplit.setFilter(filterSplit);
		viewSplit.getSelector().add("id");
		viewSplit.getSelector().add("state");
		viewSplit.getSelector().add("paymentBill.id");
		viewSplit.getSelector().add("hisVoucher.id");
		viewSplit.getSelector().add("isRedVouchered");
		viewSplit.getSelector().add("Fivouchered");
		PaymentSplitCollection splitColl = paymentSplit
				.getPaymentSplitCollection(viewSplit);
		int splitSize = splitColl.size();
		IObjectPK[] pks = new IObjectPK[splitSize];
		PaymentSplitInfo splitInfo = new PaymentSplitInfo();
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("Fivouchered");
		if (splitSize > 0) {
			for (int i = 0; i < splitSize; i++) {
				splitInfo = splitColl.get(i);
				pks[i] = new ObjectUuidPK(splitInfo.getId());
				EntityViewInfo mapping = new EntityViewInfo();
				FilterInfo mappingFilter = new FilterInfo();
				mappingFilter.getFilterItems().add(
						new FilterItemInfo("srcEntityID", splitInfo
								.getBOSType()));
				mappingFilter.getFilterItems().add(
						new FilterItemInfo("destEntityID", newVoucher
								.getBOSType()));
				mappingFilter.getFilterItems().add(
						new FilterItemInfo("srcObjectID", splitInfo.getId()
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
						List idList = new ArrayList();
						idList
								.add(splitInfo.getHisVoucher().getId()
										.toString());
						Date bookedDate = DateTimeUtils.truncateDate(splitInfo
								.getCreateTime());
//						if (currentPeriod != null) {
//							String payreqID = splitInfo.getPaymentBill()
//									.getFdcPayReqID();
//							SelectorItemCollection reqPer = new SelectorItemCollection();
//							reqPer.add("id");
//							reqPer.add("period.number");
//							reqPer.add("period.beginDate");
//							reqPer.add("period.endDate");
//							PayRequestBillInfo reqInfo = PayRequestBillFactory
//									.getLocalInstance(ctx)
//									.getPayRequestBillInfo(
//											new ObjectUuidPK(BOSUuid
//													.read(payreqID)), reqPer);
//							PeriodInfo contractPeriod = reqInfo.getPeriod();
//							if (contractPeriod != null
//									&& contractPeriod.getNumber() > currentPeriod
//											.getNumber()) {
//								if (bookedDate.before(contractPeriod
//										.getBeginDate())) {
//									bookedDate = contractPeriod.getBeginDate();
//								} else if (bookedDate.after(contractPeriod
//										.getEndDate())) {
//									bookedDate = contractPeriod.getEndDate();
//								}
//								builder = new FDCSQLBuilder(ctx);
//								builder.clear();
//								builder
//										.appendSql("update T_FNC_PaymentSplit set FPeriodId=?, FBookedDate=? where FID=?");
//								builder.addParam(contractPeriod.getId()
//										.toString());
//								builder.addParam(FDCDateHelper
//										.getSqlDate(bookedDate));
//								builder.addParam(splitInfo.getId().toString());
//								builder.executeUpdate();
//
//								builder = new FDCSQLBuilder(ctx);
//								builder.clear();
//								builder
//										.appendSql("update T_FNC_PaymentSplit set FIslastVerThisPeriod=0 where FPeriodID=? and fid<>? and Fpaymentbillid=?");
//								builder.addParam(contractPeriod.getId()
//										.toString());
//								builder.addParam(splitInfo.getId().toString());
//								builder.addParam(splitInfo.getPaymentBill()
//										.getId().toString());
//								builder.executeUpdate();
//							} else if (currentPeriod != null) {
//								if (bookedDate.before(currentPeriod
//										.getBeginDate())) {
//									bookedDate = currentPeriod.getBeginDate();
//								} else if (bookedDate.after(currentPeriod
//										.getEndDate())) {
//									bookedDate = currentPeriod.getEndDate();
//								}
//								builder = new FDCSQLBuilder(ctx);
//								builder.clear();
//								builder
//										.appendSql("update T_FNC_PaymentSplit set FPeriodId=?, FBookedDate=? where FID=?");
//								builder.addParam(currentPeriod.getId()
//										.toString());
//								builder.addParam(FDCDateHelper
//										.getSqlDate(bookedDate));
//								builder.addParam(splitInfo.getId().toString());
//								builder.executeUpdate();
//
//								builder = new FDCSQLBuilder(ctx);
//								builder.clear();
//								builder
//										.appendSql("update T_FNC_PaymentSplit set FIslastVerThisPeriod=0 where FPeriodID=? and fid<>? and Fpaymentbillid=?");
//								builder.addParam(currentPeriod.getId()
//										.toString());
//								builder.addParam(splitInfo.getId().toString());
//								builder.addParam(splitInfo.getPaymentBill()
//										.getId().toString());
//								builder.executeUpdate();
//							}
//						}
						splitInfo.setFivouchered(false);
						paymentSplit.updatePartial(splitInfo, selector);
						IObjectPK pk = voucher.reverseSaveBatch(idList);
						PaySplitUtilFacadeFactory.getLocalInstance(ctx)
								.traceReverseVoucher(pk);
						paymentSplit.generateVoucher(pks[i]);
					}
				}
			}
		}
	}

	private  static void traceVoucher4GetAdjustPattern(Context ctx, CurProjectInfo info)
			throws BOSException, EASBizException, FDCException {
		tractVoucherAdjustPattern(ctx, info, VoucherAdjustReasonEnum.STATUSGET);

	}

	/**
	 * @param ctx
	 * @param info
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws FDCException
	 */
	private  static void traceVoucher4GetRedPattern(Context ctx, CurProjectInfo info)
			throws BOSException, EASBizException, FDCException {
		PeriodInfo currentPeriod = FDCUtils.getCurrentPeriod(ctx, info.getId()
				.toString(), false);

		Set idSet = new HashSet();
		idSet.add(PaymentTypeInfo.progressID);
		idSet.add(PaymentTypeInfo.settlementID);

		// 凭证处理 预付帐款转成本
		EntityViewInfo viewContract = new EntityViewInfo();
		FilterInfo filterContract = new FilterInfo();
		filterContract.getFilterItems().add(
				new FilterItemInfo("curProject.id", info.getId().toString()));
		viewContract.setFilter(filterContract);
		ContractBillCollection contractColl = ContractBillFactory
				.getLocalInstance(ctx).getContractBillCollection(viewContract);
		IPaymentSplit paymentSplit = PaymentSplitFactory.getLocalInstance(ctx);
		IVoucher voucher = VoucherFactory.getLocalInstance(ctx);
		VoucherInfo newVoucher = new VoucherInfo();
		for (Iterator it = contractColl.iterator(); it.hasNext();) {
			ContractBillInfo contract = (ContractBillInfo) it.next();
			EntityViewInfo viewSplit = new EntityViewInfo();
			FilterInfo filterSplit = new FilterInfo();
			filterSplit.getFilterItems().add(
					new FilterItemInfo("paymentBill.contractBillId", contract
							.getId().toString()));
			filterSplit.getFilterItems().add(
					new FilterItemInfo("paymentBill.fdcPayType.payType.id",
							idSet, CompareType.INCLUDE));
			filterSplit.getFilterItems().add(
					new FilterItemInfo("Fivouchered", Boolean.TRUE));
			filterSplit.getFilterItems().add(
					new FilterItemInfo("isRedVouchered", Boolean.TRUE,
							CompareType.NOTEQUALS));
			filterSplit.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
							CompareType.NOTEQUALS));
			filterSplit.getFilterItems().add(
					new FilterItemInfo("completePrjAmt", null,
							CompareType.NOTEQUALS));
			viewSplit.setFilter(filterSplit);
			viewSplit.getSelector().add("id");
			viewSplit.getSelector().add("state");
			viewSplit.getSelector().add("createTime");
			viewSplit.getSelector().add("paymentBill.id");
			viewSplit.getSelector().add("paymentBill.fdcPayReqID");
			viewSplit.getSelector().add("hisVoucher.id");
			viewSplit.getSelector().add("isRedVouchered");
			viewSplit.getSelector().add("Fivouchered");
			PaymentSplitCollection splitColl = paymentSplit
					.getPaymentSplitCollection(viewSplit);
			int splitSize = splitColl.size();
			IObjectPK[] pks = new IObjectPK[splitSize];
			List idList = new ArrayList();
			PaymentSplitInfo splitInfo = new PaymentSplitInfo();
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("Fivouchered");
			if (splitSize > 0) {
				for (int i = 0; i < splitSize; i++) {
					splitInfo = splitColl.get(i);
					pks[i] = new ObjectUuidPK(splitInfo.getId());
					EntityViewInfo mapping = new EntityViewInfo();
					FilterInfo mappingFilter = new FilterInfo();
					mappingFilter.getFilterItems().add(
							new FilterItemInfo("srcEntityID", splitInfo
									.getBOSType()));
					mappingFilter.getFilterItems().add(
							new FilterItemInfo("destEntityID", newVoucher
									.getBOSType()));
					mappingFilter.getFilterItems().add(
							new FilterItemInfo("srcObjectID", splitInfo.getId()
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
							splitInfo.setFivouchered(false);
							Date bookedDate = DateTimeUtils
									.truncateDate(splitInfo.getCreateTime());
//							if (currentPeriod != null) {
//								String payreqID = splitInfo.getPaymentBill()
//										.getFdcPayReqID();
//								SelectorItemCollection reqPer = new SelectorItemCollection();
//								reqPer.add("id");
//								reqPer.add("period.number");
//								reqPer.add("period.beginDate");
//								reqPer.add("period.endDate");
//								PayRequestBillInfo reqInfo = PayRequestBillFactory
//										.getLocalInstance(ctx)
//										.getPayRequestBillInfo(
//												new ObjectUuidPK(BOSUuid
//														.read(payreqID)),
//												reqPer);
//								PeriodInfo contractPeriod = reqInfo.getPeriod();
//								if (contractPeriod != null
//										&& contractPeriod.getNumber() > currentPeriod
//												.getNumber()) {
//									if (bookedDate.before(contractPeriod
//											.getBeginDate())) {
//										bookedDate = contractPeriod
//												.getBeginDate();
//									} else if (bookedDate.after(contractPeriod
//											.getEndDate())) {
//										bookedDate = contractPeriod
//												.getEndDate();
//									}
//									FDCSQLBuilder builder = new FDCSQLBuilder(
//											ctx);
//									builder.clear();
//									builder
//											.appendSql("update T_FNC_PaymentSplit set FPeriodId=?, FBookedDate=? where FID=?");
//									builder.addParam(contractPeriod.getId()
//											.toString());
//									builder.addParam(FDCDateHelper
//											.getSqlDate(bookedDate));
//									builder.addParam(splitInfo.getId()
//											.toString());
//									builder.executeUpdate();
//
//									builder = new FDCSQLBuilder(ctx);
//									builder.clear();
//									builder
//											.appendSql("update T_FNC_PaymentSplit set FIslastVerThisPeriod=0 where FPeriodID=? and fid<>? and Fpaymentbillid=?");
//									builder.addParam(contractPeriod.getId()
//											.toString());
//									builder.addParam(splitInfo.getId()
//											.toString());
//									builder.addParam(splitInfo.getPaymentBill()
//											.getId().toString());
//									builder.executeUpdate();
//								} else if (currentPeriod != null) {
//									if (bookedDate.before(currentPeriod
//											.getBeginDate())) {
//										bookedDate = currentPeriod
//												.getBeginDate();
//									} else if (bookedDate.after(currentPeriod
//											.getEndDate())) {
//										bookedDate = currentPeriod.getEndDate();
//									}
//									FDCSQLBuilder builder = new FDCSQLBuilder(
//											ctx);
//									builder.clear();
//									builder
//											.appendSql("update T_FNC_PaymentSplit set FPeriodId=?, FBookedDate=? where FID=?");
//									builder.addParam(currentPeriod.getId()
//											.toString());
//									builder.addParam(FDCDateHelper
//											.getSqlDate(bookedDate));
//									builder.addParam(splitInfo.getId()
//											.toString());
//									builder.executeUpdate();
//
//									builder = new FDCSQLBuilder(ctx);
//									builder.clear();
//									builder
//											.appendSql("update T_FNC_PaymentSplit set FIslastVerThisPeriod=0 where FPeriodID=? and fid<>? and Fpaymentbillid=?");
//									builder.addParam(currentPeriod.getId()
//											.toString());
//									builder.addParam(splitInfo.getId()
//											.toString());
//									builder.addParam(splitInfo.getPaymentBill()
//											.getId().toString());
//									builder.executeUpdate();
//								}
//							}
							paymentSplit.updatePartial(splitInfo, selector);
						}
					}
				}
			}
			if (idList.size() > 0) {
				IObjectPK pk = voucher.reverseSaveBatch(idList);
				PaySplitUtilFacadeFactory.getLocalInstance(ctx)
						.traceReverseVoucher(pk);
				paymentSplit.generateVoucher(pks);
			}
		}
		EntityViewInfo viewSplit = new EntityViewInfo();
		FilterInfo filterSplit = new FilterInfo();
		filterSplit.getFilterItems().add(
				new FilterItemInfo("paymentBill.curProject.id", info.getId()
						.toString()));
		filterSplit.getFilterItems().add(
				new FilterItemInfo("isConWithoutText", Boolean.TRUE));
		filterSplit.getFilterItems().add(
				new FilterItemInfo("paymentBill.fdcPayType.payType.id", idSet,
						CompareType.INCLUDE));
		filterSplit.getFilterItems().add(
				new FilterItemInfo("Fivouchered", Boolean.TRUE));
		filterSplit.getFilterItems().add(
				new FilterItemInfo("isRedVouchered", Boolean.TRUE,
						CompareType.NOTEQUALS));
		filterSplit.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
						CompareType.NOTEQUALS));
		filterSplit.getFilterItems().add(
				new FilterItemInfo("completePrjAmt", null,
						CompareType.NOTEQUALS));
		viewSplit.setFilter(filterSplit);
		viewSplit.getSelector().add("id");
		viewSplit.getSelector().add("state");
		viewSplit.getSelector().add("paymentBill.id");
		viewSplit.getSelector().add("hisVoucher.id");
		viewSplit.getSelector().add("isRedVouchered");
		viewSplit.getSelector().add("Fivouchered");
		PaymentSplitCollection splitColl = paymentSplit
				.getPaymentSplitCollection(viewSplit);
		int splitSize = splitColl.size();
		IObjectPK[] pks = new IObjectPK[splitSize];
		PaymentSplitInfo splitInfo = new PaymentSplitInfo();
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("Fivouchered");
		if (splitSize > 0) {
			for (int i = 0; i < splitSize; i++) {
				splitInfo = splitColl.get(i);
				pks[i] = new ObjectUuidPK(splitInfo.getId());
				EntityViewInfo mapping = new EntityViewInfo();
				FilterInfo mappingFilter = new FilterInfo();
				mappingFilter.getFilterItems().add(
						new FilterItemInfo("srcEntityID", splitInfo
								.getBOSType()));
				mappingFilter.getFilterItems().add(
						new FilterItemInfo("destEntityID", newVoucher
								.getBOSType()));
				mappingFilter.getFilterItems().add(
						new FilterItemInfo("srcObjectID", splitInfo.getId()
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
						List idList = new ArrayList();
						idList
								.add(splitInfo.getHisVoucher().getId()
										.toString());
						splitInfo.setFivouchered(false);
						Date bookedDate = DateTimeUtils.truncateDate(splitInfo
								.getCreateTime());
//						if (currentPeriod != null) {
//							String payreqID = splitInfo.getPaymentBill()
//									.getFdcPayReqID();
//							SelectorItemCollection reqPer = new SelectorItemCollection();
//							reqPer.add("id");
//							reqPer.add("period.number");
//							reqPer.add("period.beginDate");
//							reqPer.add("period.endDate");
//							PayRequestBillInfo reqInfo = PayRequestBillFactory
//									.getLocalInstance(ctx)
//									.getPayRequestBillInfo(
//											new ObjectUuidPK(BOSUuid
//													.read(payreqID)), reqPer);
//							PeriodInfo contractPeriod = reqInfo.getPeriod();
//							if (contractPeriod != null
//									&& contractPeriod.getNumber() > currentPeriod
//											.getNumber()) {
//								if (bookedDate.before(contractPeriod
//										.getBeginDate())) {
//									bookedDate = contractPeriod.getBeginDate();
//								} else if (bookedDate.after(contractPeriod
//										.getEndDate())) {
//									bookedDate = contractPeriod.getEndDate();
//								}
//								FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
//								builder.clear();
//								builder
//										.appendSql("update T_FNC_PaymentSplit set FPeriodId=?, FBookedDate=? where FID=?");
//								builder.addParam(contractPeriod.getId()
//										.toString());
//								builder.addParam(FDCDateHelper
//										.getSqlDate(bookedDate));
//								builder.addParam(splitInfo.getId().toString());
//								builder.executeUpdate();
//
//								builder = new FDCSQLBuilder(ctx);
//								builder.clear();
//								builder
//										.appendSql("update T_FNC_PaymentSplit set FIslastVerThisPeriod=0 where FPeriodID=? and fid<>? and Fpaymentbillid=?");
//								builder.addParam(contractPeriod.getId()
//										.toString());
//								builder.addParam(splitInfo.getId().toString());
//								builder.addParam(splitInfo.getPaymentBill()
//										.getId().toString());
//								builder.executeUpdate();
//							} else if (currentPeriod != null) {
//								if (bookedDate.before(currentPeriod
//										.getBeginDate())) {
//									bookedDate = currentPeriod.getBeginDate();
//								} else if (bookedDate.after(currentPeriod
//										.getEndDate())) {
//									bookedDate = currentPeriod.getEndDate();
//								}
//								FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
//								builder.clear();
//								builder
//										.appendSql("update T_FNC_PaymentSplit set FPeriodId=?, FBookedDate=? where FID=?");
//								builder.addParam(currentPeriod.getId()
//										.toString());
//								builder.addParam(FDCDateHelper
//										.getSqlDate(bookedDate));
//								builder.addParam(splitInfo.getId().toString());
//								builder.executeUpdate();
//
//								builder = new FDCSQLBuilder(ctx);
//								builder.clear();
//								builder
//										.appendSql("update T_FNC_PaymentSplit set FIslastVerThisPeriod=0 where FPeriodID=? and fid<>? and Fpaymentbillid=?");
//								builder.addParam(currentPeriod.getId()
//										.toString());
//								builder.addParam(splitInfo.getId().toString());
//								builder.addParam(splitInfo.getPaymentBill()
//										.getId().toString());
//								builder.executeUpdate();
//							}
//						}
						paymentSplit.updatePartial(splitInfo, selector);
						IObjectPK pk = voucher.reverseSaveBatch(idList);
						PaySplitUtilFacadeFactory.getLocalInstance(ctx)
								.traceReverseVoucher(pk);
						paymentSplit.generateVoucher(pks[i]);
					}
				}
			}
		}

	}

	/**
	 * 是否复杂一体化调整凭证模式
	 * 
	 * @param ctx
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private static boolean isAdjustModel(Context ctx) throws EASBizException,
			BOSException {
		String companyId = ContextUtil.getCurrentFIUnit(ctx).getId().toString();
		return FDCUtils.isAdjustVourcherModel(ctx, companyId);
	}
}
