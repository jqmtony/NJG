package com.kingdee.eas.fdc.contract.app;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.bot.BOTRelationCollection;
import com.kingdee.bos.metadata.bot.BOTRelationFactory;
import com.kingdee.bos.metadata.bot.BOTRelationInfo;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CostSplitBillTypeEnum;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCostSplit;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.PaySplitUtilFacadeFactory;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.contract.ConChangeNoCostSplitCollection;
import com.kingdee.eas.fdc.contract.ConChangeNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ConChangeNoCostSplitInfo;
import com.kingdee.eas.fdc.contract.ConChangeSplitCollection;
import com.kingdee.eas.fdc.contract.ConChangeSplitFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitInfo;
import com.kingdee.eas.fdc.contract.ConNoCostSplitCollection;
import com.kingdee.eas.fdc.contract.ConNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ConNoCostSplitInfo;
import com.kingdee.eas.fdc.contract.ConSplitExecStateEnum;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.IConChangeNoCostSplit;
import com.kingdee.eas.fdc.contract.IConChangeSplit;
import com.kingdee.eas.fdc.contract.ISettNoCostSplit;
import com.kingdee.eas.fdc.contract.ISettlementCostSplit;
import com.kingdee.eas.fdc.contract.InvalidCostSplitHelper;
import com.kingdee.eas.fdc.contract.SettNoCostSplitCollection;
import com.kingdee.eas.fdc.contract.SettNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.SettNoCostSplitInfo;
import com.kingdee.eas.fdc.contract.SettlementCostSplitCollection;
import com.kingdee.eas.fdc.contract.SettlementCostSplitFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitInfo;
import com.kingdee.eas.fdc.finance.FDCAdjustBillFactory;
import com.kingdee.eas.fdc.finance.IPaymentNoCostSplit;
import com.kingdee.eas.fdc.finance.IPaymentNoCostSplitEntry;
import com.kingdee.eas.fdc.finance.IPaymentSplit;
import com.kingdee.eas.fdc.finance.IPaymentSplitEntry;
import com.kingdee.eas.fdc.finance.IWorkLoadConfirmBill;
import com.kingdee.eas.fdc.finance.PaySplitVoucherRefersEnum;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitCollection;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitEntryFactory;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitCollection;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitInfo;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillFactory;
import com.kingdee.eas.fi.cas.IPaymentBill;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.fi.gl.IVoucher;
import com.kingdee.eas.fi.gl.VoucherFactory;
import com.kingdee.eas.fi.gl.VoucherInfo;
import com.kingdee.jdbc.rowset.IRowSet;

public class ClearSplitFacadeControllerBean extends
		AbstractClearSplitFacadeControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.contract.app.ClearSplitFacadeControllerBean");

	private FDCCostSplit fdcCostSplit = null;

	private boolean isCostSplit(Context ctx, String id) throws EASBizException,
			BOSException {
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("id");
		selector.add("isCoseSplit");
		ContractBillInfo info = ContractBillFactory.getLocalInstance(ctx)
				.getContractBillInfo(new ObjectUuidPK(id), selector);
		return info.isIsCoseSplit();
	}
	
	private boolean getFinanicalByContractID(Context ctx, String contractId) throws BOSException, EASBizException{
		boolean isFinacial = false;
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",contractId));
		view.setFilter(filter);
		view.getSelector().add("curProject.id");
		view.getSelector().add("curProject.fullOrgUnit.id");
		
		ContractBillInfo info = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(view).get(0);
		isFinacial = FDCUtils.IsFinacial(ctx, info.getCurProject().getFullOrgUnit().getId().toString());
		return isFinacial;
	}

	/**
	 * //更新合同的拆分状态 by sxhong
	 * 
	 * @param ctx
	 * @param contractid
	 * @throws EASBizException 
	 */
	private void updateContractSplitState(Context ctx, String contractId,
			boolean isHasVoucher) throws BOSException, EASBizException {
//		if(!getFinanicalByContractID(ctx, contractId))
//			return;
		if (isHasVoucher) {
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder
					.appendSql("update T_CON_ContractBill set FSplitState=?,FConSplitExecState=? where fid=?");
			builder.addParam(CostSplitStateEnum.NOSPLIT_VALUE);
			builder.addParam(ConSplitExecStateEnum.INVALID_VALUE);
			builder.addParam(contractId);
			builder.executeUpdate();
		} else {
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder
					.appendSql("update T_CON_ContractBill set FSplitState=?,FConSplitExecState=?  where fid=?");

			/**
			 * 简单模式到复杂模式，合同没有生成凭证也进入待处理合同
			 */
			builder.addParam(CostSplitStateEnum.NOSPLIT_VALUE);
			builder.addParam(ConSplitExecStateEnum.INVALID_VALUE);
			builder.addParam(contractId);
			builder.executeUpdate();
		}
	}

	// 仅作废跟合同拆分相关的变更拆分
	protected void _clearSplitBill(Context ctx, String contractID)
			throws BOSException, EASBizException {
		if (fdcCostSplit == null) {
			fdcCostSplit = new FDCCostSplit(ctx);
		}
		boolean isAdjust = isAdjustModel(ctx,contractID);
		IPaymentBill ipaymentbill = PaymentBillFactory.getLocalInstance(ctx);
		IWorkLoadConfirmBill iworkload = WorkLoadConfirmBillFactory.getLocalInstance(ctx);
		boolean isHasVoucher = isHasVoucher(ctx, contractID, isAdjust,
				ipaymentbill, iworkload);
		Timestamp updateTime = getTime();

		/**
		 * 清除付款拆分
		 */
		EntityViewInfo payBillView = new EntityViewInfo();
		FilterInfo payBillFilter = new FilterInfo();
		payBillFilter.getFilterItems().add(
				new FilterItemInfo("contractBillId", contractID));
		// payBillFilter.getFilterItems().add(
		// new FilterItemInfo("fiVouchered", Boolean.TRUE));
		payBillView.setFilter(payBillFilter);
		payBillView.getSelector().add("id");

		PaymentBillCollection paymentBillCollection = PaymentBillFactory
				.getLocalInstance(ctx).getPaymentBillCollection(payBillView);

		Set payIdSet = new HashSet();

		for (Iterator iter = paymentBillCollection.iterator(); iter.hasNext();) {
			PaymentBillInfo element = (PaymentBillInfo) iter.next();
			String id = element.getId().toString();

			payIdSet.add(id);
		}

		EntityViewInfo paySplitView = getPaySplitView(contractID);
		SelectorItemCollection psUpdSelector = getPsUpSelector(isAdjust);
		/*
		 * 点击“作废合同拆分”时，修改逻辑校验：
		l	合同拆分没有被后续拆分引用时，提示：该合同拆分没有被后续拆分引用，将只作废本合同拆分！（注：后续拆分--工程量拆分、结算拆分、合同付款拆分）
		2	合同拆分被后续拆分引用时，提示：该合同拆分被后续拆分引用，作废合同拆分时将作废后续拆分，且该合同进入待处理流程，确认是否操作？

		 */
		boolean isExistOtherSplit = false;
		
		
		if (isCostSplit(ctx, contractID)) {
			PaymentSplitCollection paymentSplitCollection = PaymentSplitFactory
					.getLocalInstance(ctx).getPaymentSplitCollection(
							paySplitView);
			FilterInfo filterPay = getFilterPay(contractID);
			boolean hasSettlePayed = PaymentSplitFactory.getLocalInstance(ctx)
					.exists(filterPay);
			// VoucherInfo newVoucher = new VoucherInfo();
			for (Iterator iter = paymentSplitCollection.iterator(); iter
					.hasNext();) {
				PaymentSplitInfo element = (PaymentSplitInfo) iter.next();
				// if(!element.isIsRedVouchered()){
				// 作废付款拆分
				setPaymentSplitInvalid(isAdjust, ipaymentbill, hasSettlePayed, element);
				element.setLastUpdateTime(updateTime);
				// 删除成本数据
				// fdcCostSplit.deleteCostSplit(ctx,
				// CostSplitBillTypeEnum.PAYMENTSPLIT, element
				// .getPaymentBill().getId());
				// 作废成本数据
				BOSUuid costId=null;
				if(element.isIsWorkLoadBill()){
					costId=element.getWorkLoadConfirmBill().getId();
				}else{
					costId=element.getPaymentBill().getId();
				}
				InvalidCostSplitHelper.invalidCostSplit(ctx,
						CostSplitBillTypeEnum.PAYMENTSPLIT, costId, element.getId());
				PaymentSplitFactory.getLocalInstance(ctx).updatePartial(
						element, psUpdSelector);
				// }
				
				
				isExistOtherSplit = true;
			}
			/**
			 * 清除结算拆分
			 */

			EntityViewInfo settSplitView = new EntityViewInfo();
			FilterInfo settSplitFilter = new FilterInfo();
			settSplitFilter.getFilterItems().add(
					new FilterItemInfo("contractBill.id", contractID));
			settSplitFilter.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
							CompareType.NOTEQUALS));
			settSplitView.setFilter(settSplitFilter);
			settSplitView.getSelector().add("id");
			settSplitView.getSelector().add("state");
			settSplitView.getSelector().add("settlementBill.id");

			SettlementCostSplitCollection settlementCostSplitCollection = SettlementCostSplitFactory
					.getLocalInstance(ctx).getSettlementCostSplitCollection(
							settSplitView);
			for (Iterator it = settlementCostSplitCollection.iterator(); it
					.hasNext();) {

				SettlementCostSplitInfo info = (SettlementCostSplitInfo) it
						.next();
				info.setLastUpdateTime(updateTime);
				updateSettlementCostSplit(ctx, isAdjust, info);
				// fdcCostSplit.deleteCostSplit(ctx,
				// CostSplitBillTypeEnum.SETTLEMENTSPLIT, element
				// .getId());
				// 作废成本数据
				InvalidCostSplitHelper.invalidCostSplit(ctx,
						CostSplitBillTypeEnum.SETTLEMENTSPLIT, info
								.getSettlementBill().getId(), info.getId());
				
				isExistOtherSplit = true;
			}

			// 作废变更拆分
			Set splitSet = new HashSet();
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.clear();
			builder
					.appendSql("select distinct splitentry.fparentid from t_con_conchangesplitentry splitentry "
							+ "where splitentry.fsplitbillid=(select split.fid from t_con_contractcostsplit split "
							+ "where split.fcontractbillid=? and split.fisinvalid=0)");//db2类型校验 不支持split.fisinvalid='0'
			builder.addParam(contractID);
			IRowSet rowSetSplit = builder.executeQuery();
			try {
				while (rowSetSplit.next()) {
					splitSet.add(rowSetSplit.getString("fparentid"));
				}
				if (splitSet.size() > 0) {
					EntityViewInfo changeView = new EntityViewInfo();
					FilterInfo changeFilter = new FilterInfo();
					changeFilter.getFilterItems().add(
							new FilterItemInfo("id", splitSet,
									CompareType.INCLUDE));
					changeView.setFilter(changeFilter);
					changeView.getSelector().add("id");
					changeView.getSelector().add("contractChange.id");
					ConChangeSplitCollection conChangeSplitCollection = ConChangeSplitFactory
							.getLocalInstance(ctx).getConChangeSplitCollection(
									changeView);
					for (Iterator iter = conChangeSplitCollection.iterator(); iter
							.hasNext();) {

						ConChangeSplitInfo info = (ConChangeSplitInfo) iter
								.next();

						info.setState(FDCBillStateEnum.INVALID);
						info.setIsInvalid(true);
						SelectorItemCollection updSelector = new SelectorItemCollection();
						updSelector.add("state");
						updSelector.add("isInvalid");

						ConChangeSplitFactory.getLocalInstance(ctx)
								.updatePartial(info, updSelector);
						// 作废成本数据
						InvalidCostSplitHelper.invalidCostSplit(ctx,
								CostSplitBillTypeEnum.CNTRCHANGESPLIT, info
										.getContractChange().getId(), info
										.getId());
					}
				}

			} catch (SQLException e) {
				logger.error(e);
				throw new BOSException(e);
			}

			// 作废合同拆分 contractBill
			EntityViewInfo conView = new EntityViewInfo();
			FilterInfo conFilter = new FilterInfo();
			conFilter.getFilterItems().add(
					new FilterItemInfo("contractBill.id", contractID));
			conFilter.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
							CompareType.NOTEQUALS));
			conView.setFilter(conFilter);
			conView.getSelector().add("id");
			ContractCostSplitCollection contractCostSplitCollection = ContractCostSplitFactory
					.getLocalInstance(ctx).getContractCostSplitCollection(
							conView);
			if (contractCostSplitCollection.size() > 0) {

				ContractCostSplitInfo info = contractCostSplitCollection.get(0);

				info.setState(FDCBillStateEnum.INVALID);
				info.setIsInvalid(true);
				info.setLastUpdateTime(updateTime);
				SelectorItemCollection updSelector = new SelectorItemCollection();
				updSelector.add("state");
				updSelector.add("isInvalid");
				updSelector.add("lastUpdateTime");
				ContractCostSplitFactory.getLocalInstance(ctx).updatePartial(
						info, updSelector);

				// 作废成本数据
				InvalidCostSplitHelper.invalidCostSplit(ctx,
						CostSplitBillTypeEnum.CONTRACTSPLIT, BOSUuid
								.read(contractID), info.getId());
			}
			// fdcCostSplit.deleteCostSplit(ctx,
			// CostSplitBillTypeEnum.CONTRACTSPLIT, BOSUuid
			// .read(contractID));
			
			
			// 更新合同的拆分状态 by sxhong
			updateContractSplitState(ctx, contractID, isExistOtherSplit);

		}

		else {
			// 作废付款拆分
			PaymentNoCostSplitCollection paymentNoSplitCollection = PaymentNoCostSplitFactory
					.getLocalInstance(ctx).getPaymentNoCostSplitCollection(
							paySplitView);
			FilterInfo filterPay = getFilterPay(contractID);
			boolean hasSettlePayed = PaymentNoCostSplitFactory
					.getLocalInstance(ctx).exists(filterPay);
			// VoucherInfo newVoucher = new VoucherInfo();
			for (Iterator iter = paymentNoSplitCollection.iterator(); iter
					.hasNext();) {
				PaymentNoCostSplitInfo element = (PaymentNoCostSplitInfo) iter
						.next();
				// if(element.isIsRedVouchered()){
				// 作废付款拆分
				setPaymentNoCostSplitInvalid(isAdjust, ipaymentbill, hasSettlePayed, element);
				PaymentNoCostSplitFactory.getLocalInstance(ctx).updatePartial(
						element, psUpdSelector);
				// }

			}

			// 更改付款单凭证标志
			if(!isAdjust){
				PaymentBillInfo paymentBill = null;
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add(new SelectorItemInfo("fiVouchered"));
				for (Iterator iter = payIdSet.iterator(); iter.hasNext();) {
					String element = (String) iter.next();

					paymentBill = new PaymentBillInfo();
					paymentBill.setId(BOSUuid.read(element));
					paymentBill.setFiVouchered(false);

					PaymentBillFactory.getLocalInstance(ctx).updatePartial(
							paymentBill, selector);
				}
			}
			
			// 作废结算拆分
			EntityViewInfo settSplitView = new EntityViewInfo();
			FilterInfo settSplitFilter = new FilterInfo();
			settSplitFilter.getFilterItems().add(
					new FilterItemInfo("contractBill.id", contractID));
			settSplitFilter.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
							CompareType.NOTEQUALS));
			settSplitView.setFilter(settSplitFilter);
			settSplitView.getSelector().add("id");
			settSplitView.getSelector().add("state");
			settSplitView.getSelector().add("settlementBill.id");

			SettNoCostSplitCollection settNoCostSplitCollection = SettNoCostSplitFactory
					.getLocalInstance(ctx).getSettNoCostSplitCollection(
							settSplitView);
			for (Iterator it = settNoCostSplitCollection.iterator(); it
					.hasNext();) {
				SettNoCostSplitInfo info = (SettNoCostSplitInfo) it.next();
				updateSettNoCostSplit(ctx, info,isAdjust);
			}

			// 作废变更拆分
			EntityViewInfo changeView = new EntityViewInfo();
			FilterInfo changeFilter = new FilterInfo();
			changeFilter.getFilterItems().add(
					new FilterItemInfo("contractChange.contractBill.id",
							contractID));
			changeFilter.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
							CompareType.NOTEQUALS));
			changeView.setFilter(changeFilter);
			changeView.getSelector().add("id");
			changeView.getSelector().add("contractChange.id");
			ConChangeNoCostSplitCollection conChangeSplitCollection = ConChangeNoCostSplitFactory
					.getLocalInstance(ctx).getConChangeNoCostSplitCollection(
							changeView);
			for (Iterator iter = conChangeSplitCollection.iterator(); iter
					.hasNext();) {

				ConChangeNoCostSplitInfo info = (ConChangeNoCostSplitInfo) iter
						.next();

				info.setState(FDCBillStateEnum.INVALID);
				info.setIsInvalid(true);
				info.setLastUpdateTime(updateTime);
				SelectorItemCollection updSelector = new SelectorItemCollection();
				updSelector.add("state");
				updSelector.add("isInvalid");
				updSelector.add("lastUpdateTime");
				ConChangeNoCostSplitFactory.getLocalInstance(ctx)
						.updatePartial(info, updSelector);
			}

			// 作废合同拆分
			EntityViewInfo conView = new EntityViewInfo();
			FilterInfo conFilter = new FilterInfo();
			conFilter.getFilterItems().add(
					new FilterItemInfo("contractBill.id", contractID));
			conFilter.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
							CompareType.NOTEQUALS));
			conView.setFilter(conFilter);
			conView.getSelector().add("id");
			ConNoCostSplitCollection conNoCostSplitCollection = ConNoCostSplitFactory
					.getLocalInstance(ctx).getConNoCostSplitCollection(conView);
			if (conNoCostSplitCollection.size() > 0) {

				ConNoCostSplitInfo info = conNoCostSplitCollection.get(0);

				info.setState(FDCBillStateEnum.INVALID);
				info.setIsInvalid(true);
				info.setLastUpdateTime(updateTime);
				SelectorItemCollection updSelector = new SelectorItemCollection();
				updSelector.add("state");
				updSelector.add("isInvalid");
				updSelector.add("lastUpdateTime");
				ConNoCostSplitFactory.getLocalInstance(ctx).updatePartial(info,
						updSelector);
			}
			// 更新合同的拆分状态 by sxhong
			updateContractSplitState(ctx, contractID, isHasVoucher);
		}

		
	}

	private boolean isHasVoucher(Context ctx, String contractID,
			boolean isAdjust, IPaymentBill ipaymentbill,
			IWorkLoadConfirmBill iworkload) throws BOSException,
			EASBizException {
		boolean isHasVoucher = false;
		FilterInfo hasVoucher = new FilterInfo();
		hasVoucher.getFilterItems().add(
				new FilterItemInfo("contractBillId", contractID));
		hasVoucher.getFilterItems().add(
				new FilterItemInfo("fiVouchered", Boolean.TRUE));
		
		FilterInfo workloadHasVoucher = new FilterInfo();
		workloadHasVoucher.getFilterItems().add(
				new FilterItemInfo("contractBill.Id", contractID));
		workloadHasVoucher.getFilterItems().add(
				new FilterItemInfo("fiVouchered", Boolean.TRUE));
		
		/***
		 * 仅仅使用fiVouchered还不行,因为用户可以手工删除凭证，但是，其实已经同步过了
		 */
		if(isAdjust){
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.appendSql("select payentry.fid from t_fnc_fdcpayvoucherentry payentry inner join t_cas_paymentbill payment on payentry.fparentid=payment.fid where payment.fcontractbillid=?");
			builder.appendSql(" union all ");
			builder.appendSql("select costentry.fid from t_fnc_fdccostvoucherentry costentry inner join t_cas_paymentbill payment on costentry.fparentid=payment.fid where payment.fcontractbillid=?");
			builder.appendSql(" union all ");
			builder.appendSql("select payentry.fid from t_fnc_workloadpayvoucherentry payentry inner join t_fnc_workloadconfirmbill workload on payentry.fparentid=workload.fid where workload.fcontractbillid=?");
			builder.appendSql(" union all ");
			builder.appendSql("select costentry.fid from t_fnc_workloadcostvoucherentry costentry inner join t_fnc_workloadconfirmbill workload on costentry.fparentid=workload.fid where workload.fcontractbillid=?");
			builder.addParam(contractID);
			builder.addParam(contractID);
			builder.addParam(contractID);
			builder.addParam(contractID);
			if(builder.isExist()){
				isHasVoucher = true;
			}
		}
		else if (ipaymentbill.exists(hasVoucher)||iworkload.exists(workloadHasVoucher)) {
			isHasVoucher = true;
		}
		return isHasVoucher;
	}

	/**
	 * @param ctx
	 * @param info
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void updateSettNoCostSplit(Context ctx, SettNoCostSplitInfo info,boolean isAdjust) throws BOSException, EASBizException {
		info.setState(FDCBillStateEnum.INVALID);
		info.setIsInvalid(true);
		SelectorItemCollection updSelector = new SelectorItemCollection();
		updSelector.add("state");
		updSelector.add("isInvalid");
		updSelector.add("lastUpdateTime");
		if(isAdjust){
			updSelector.add("isBeforeAdjust");
			info.setIsBeforeAdjust(true);
		}
		
		SettNoCostSplitFactory.getLocalInstance(ctx).updatePartial(
				info, updSelector);
	}

	/**
	 * @return
	 */
	private SelectorItemCollection getPsUpSelector(boolean isAdjust) {
		SelectorItemCollection psUpdSelector = new SelectorItemCollection();
		psUpdSelector.add("state");
		psUpdSelector.add("hisVoucher.id");
		psUpdSelector.add("isInvalid");
		psUpdSelector.add("lastUpdateTime");//最后更新时间即作废时间
		if(isAdjust){
			psUpdSelector.add("isBeforeAdjust");
			psUpdSelector.add("Fivouchered");
			psUpdSelector.add("voucherRefer");
		}
		return psUpdSelector;
	}

	/**
	 * @param isAdjust
	 * @param ipaymentbill
	 * @param hasSettlePayed
	 * @param element
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void setPaymentSplitInvalid(boolean isAdjust, IPaymentBill ipaymentbill, boolean hasSettlePayed, PaymentSplitInfo element) throws BOSException, EASBizException {
		element.setState(FDCBillStateEnum.INVALID);
		
		/***
		 * 调整凭证模式
		 */
		if (isAdjust){
			element.setIsInvalid(true);
			
			element.setIsBeforeAdjust(true);
			//			if(element.getVoucherRefer()!=null&&!element.getVoucherRefer().equals(PaySplitVoucherRefersEnum.NOTREFER))
			//				element.setIsBeforeAdjust(true);
			//			else{
			//				//没有被调整单关联说明没有被引用，这时候必定存在拆分isBeforeAdjust=1 的情况
			//				element.setIsBeforeAdjust(false);
			//			}
		}
		/***
		 * 旧的红冲模式
		 */
		else{
			/***
			 * 如果不存在第一笔结算款
			 */
			if (!hasSettlePayed) {
				element.setIsInvalid(true);
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add(new SelectorItemInfo("fiVouchered"));
				element.getPaymentBill().setFiVouchered(false);
				ipaymentbill.updatePartial(element.getPaymentBill(),
						selector);
			} 
			/***
			 * 如果是关联的付款单是结算款或保修款
			 */
			else if (element.getPaymentBill().getFdcPayType()
					.getPayType().getId().toString().equals(
							PaymentTypeInfo.settlementID)
					|| element.getPaymentBill().getFdcPayType()
							.getPayType().getId().toString().equals(
									PaymentTypeInfo.keepID)) {
				element.setIsInvalid(true);
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add(new SelectorItemInfo("fiVouchered"));
				element.getPaymentBill().setFiVouchered(false);
				ipaymentbill.updatePartial(element.getPaymentBill(),
						selector);
			} else {
				element.setIsInvalid(false);
			}
		}
	}

	/**
	 * @param isAdjust
	 * @param ipaymentbill
	 * @param hasSettlePayed
	 * @param element
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void setPaymentNoCostSplitInvalid(boolean isAdjust, IPaymentBill ipaymentbill, boolean hasSettlePayed, PaymentNoCostSplitInfo element) throws BOSException, EASBizException {
		element.setState(FDCBillStateEnum.INVALID);
		/***
		 * 调整模式
		 */
		if (isAdjust){
			element.setIsInvalid(true);
			
			if(element.getVoucherRefer()!=null&&!element.getVoucherRefer().equals(PaySplitVoucherRefersEnum.NOTREFER))
				element.setIsBeforeAdjust(true);
			else
				element.setIsBeforeAdjust(false);
		}
		/***
		 * 红冲模式
		 */
		else{
			if (!hasSettlePayed) {
				element.setIsInvalid(true);
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add(new SelectorItemInfo("fiVouchered"));
				element.getPaymentBill().setFiVouchered(false);
				ipaymentbill.updatePartial(element.getPaymentBill(),
						selector);
			} else if (element.getPaymentBill().getFdcPayType()
					.getPayType().getId().toString().equals(
							PaymentTypeInfo.settlementID)
					|| element.getPaymentBill().getFdcPayType()
							.getPayType().getId().toString().equals(
									PaymentTypeInfo.keepID)) {
				element.setIsInvalid(true);
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add(new SelectorItemInfo("fiVouchered"));
				element.getPaymentBill().setFiVouchered(false);
				ipaymentbill.updatePartial(element.getPaymentBill(),
						selector);
			} else {
				element.setIsInvalid(false);
			}
		}
	}

	// 作废合同下所有的变更拆分
	protected void _clearAllSplit(Context ctx, String contractID,
			boolean isContract) throws BOSException, EASBizException {
		if (fdcCostSplit == null) {
			fdcCostSplit = new FDCCostSplit(ctx);
		}
		boolean isAdjust = isAdjustModel(ctx,contractID);
		IPaymentBill ipaymentbill = PaymentBillFactory.getLocalInstance(ctx);
		IWorkLoadConfirmBill iworkload = WorkLoadConfirmBillFactory.getLocalInstance(ctx);
		boolean isHasVoucher = isHasVoucher(ctx, contractID, isAdjust,
				ipaymentbill, iworkload);
		Timestamp updateTime = getTime();
		/**
		 * 清除付款拆分
		 */
		EntityViewInfo payBillView = new EntityViewInfo();
		FilterInfo payBillFilter = new FilterInfo();
		payBillFilter.getFilterItems().add(
				new FilterItemInfo("contractBillId", contractID));
		payBillView.setFilter(payBillFilter);
		payBillView.getSelector().add("id");

		PaymentBillCollection paymentBillCollection = PaymentBillFactory
				.getLocalInstance(ctx).getPaymentBillCollection(payBillView);

		Set payIdSet = new HashSet();

		for (Iterator iter = paymentBillCollection.iterator(); iter.hasNext();) {
			PaymentBillInfo element = (PaymentBillInfo) iter.next();
			String id = element.getId().toString();

			payIdSet.add(id);
		}
		
		boolean isExistOtherSplit = false;
		EntityViewInfo paySplitView = getPaySplitView(contractID);
		SelectorItemCollection psUpdSelector = getPsUpSelector(isAdjust);
		if (isCostSplit(ctx, contractID)) {
			PaymentSplitCollection paymentSplitCollection = PaymentSplitFactory
					.getLocalInstance(ctx).getPaymentSplitCollection(
							paySplitView);
			FilterInfo filterPay = getFilterPay(contractID);
			boolean hasSettlePayed = PaymentSplitFactory.getLocalInstance(ctx)
					.exists(filterPay);
			for (Iterator iter = paymentSplitCollection.iterator(); iter
					.hasNext();) {
				PaymentSplitInfo element = (PaymentSplitInfo) iter.next();
				// if(!element.isIsRedVouchered()){
				// 作废付款拆分
				setPaymentSplitInvalid(isAdjust, ipaymentbill, hasSettlePayed, element);
				// fdcCostSplit.deleteCostSplit(ctx,
				// CostSplitBillTypeEnum.PAYMENTSPLIT, element
				// .getPaymentBill().getId());
				// 作废成本数据
				BOSUuid costId=null;
				if(element.isIsWorkLoadBill()){
					costId=element.getWorkLoadConfirmBill().getId();
				}else{
					costId=element.getPaymentBill().getId();
				}
				element.setLastUpdateTime(updateTime);
				InvalidCostSplitHelper.invalidCostSplit(ctx,
						CostSplitBillTypeEnum.PAYMENTSPLIT, costId, element.getId());
				PaymentSplitFactory.getLocalInstance(ctx).updatePartial(
						element, psUpdSelector);

				isExistOtherSplit = true;
			}
			/**
			 * 清除结算拆分
			 */
			EntityViewInfo settSplitView = new EntityViewInfo();
			FilterInfo settSplitFilter = new FilterInfo();
			settSplitFilter.getFilterItems().add(
					new FilterItemInfo("contractBill.id", contractID));
			settSplitFilter.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
							CompareType.NOTEQUALS));
			settSplitView.setFilter(settSplitFilter);
			settSplitView.getSelector().add("id");
			settSplitView.getSelector().add("state");
			settSplitView.getSelector().add("settlementBill.id");

			SettlementCostSplitCollection settlementCostSplitCollection = SettlementCostSplitFactory
					.getLocalInstance(ctx).getSettlementCostSplitCollection(
							settSplitView);
			for (Iterator it = settlementCostSplitCollection.iterator(); it
					.hasNext();) {

				SettlementCostSplitInfo info = (SettlementCostSplitInfo) it
						.next();
				info.setLastUpdateTime(updateTime);
				updateSettlementCostSplit(ctx, isAdjust, info);
				// fdcCostSplit.deleteCostSplit(ctx,
				// CostSplitBillTypeEnum.SETTLEMENTSPLIT, element
				// .getId());

				// 作废成本数据
				InvalidCostSplitHelper.invalidCostSplit(ctx,
						CostSplitBillTypeEnum.SETTLEMENTSPLIT, info
								.getSettlementBill().getId(), info.getId());
				
				isExistOtherSplit = true;
			}

			// 作废变更拆分
			EntityViewInfo changeView = new EntityViewInfo();
			FilterInfo changeFilter = new FilterInfo();
			changeFilter.getFilterItems().add(
					new FilterItemInfo("contractChange.contractBill.id",
							contractID));
			changeFilter.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
							CompareType.NOTEQUALS));
			changeView.setFilter(changeFilter);
			changeView.getSelector().add("id");
			changeView.getSelector().add("contractChange.id");
			ConChangeSplitCollection conChangeSplitCollection = ConChangeSplitFactory
					.getLocalInstance(ctx).getConChangeSplitCollection(
							changeView);
			for (Iterator iter = conChangeSplitCollection.iterator(); iter
					.hasNext();) {

				ConChangeSplitInfo info = (ConChangeSplitInfo) iter.next();

				info.setState(FDCBillStateEnum.INVALID);
				info.setIsInvalid(true);
				info.setLastUpdateTime(updateTime);
				SelectorItemCollection updSelector = new SelectorItemCollection();
				updSelector.add("state");
				updSelector.add("isInvalid");
				updSelector.add("lastUpdateTime");
				ConChangeSplitFactory.getLocalInstance(ctx).updatePartial(info,
						updSelector);
				// fdcCostSplit.deleteCostSplit(ctx,
				// CostSplitBillTypeEnum.CNTRCHANGESPLIT, info
				// .getContractChange().getId());

				// 作废成本数据
				InvalidCostSplitHelper.invalidCostSplit(ctx,
						CostSplitBillTypeEnum.CNTRCHANGESPLIT, info
								.getContractChange().getId(), info.getId());
			}

			// 作废合同拆分 contractBill
			EntityViewInfo conView = new EntityViewInfo();
			FilterInfo conFilter = new FilterInfo();
			conFilter.getFilterItems().add(
					new FilterItemInfo("contractBill.id", contractID));
			conFilter.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
							CompareType.NOTEQUALS));
			conView.setFilter(conFilter);
			conView.getSelector().add("id");
			ContractCostSplitCollection contractCostSplitCollection = ContractCostSplitFactory
					.getLocalInstance(ctx).getContractCostSplitCollection(
							conView);
			if (contractCostSplitCollection.size() > 0) {

				ContractCostSplitInfo info = contractCostSplitCollection.get(0);

				info.setState(FDCBillStateEnum.INVALID);
				info.setIsInvalid(true);
				info.setLastUpdateTime(updateTime);
				SelectorItemCollection updSelector = new SelectorItemCollection();
				updSelector.add("state");
				updSelector.add("isInvalid");
				updSelector.add("lastUpdateTime");
				ContractCostSplitFactory.getLocalInstance(ctx).updatePartial(
						info, updSelector);

				// 作废成本数据
				InvalidCostSplitHelper.invalidCostSplit(ctx,
						CostSplitBillTypeEnum.CONTRACTSPLIT, BOSUuid
								.read(contractID), info.getId());
			}
			// fdcCostSplit.deleteCostSplit(ctx,
			// CostSplitBillTypeEnum.CONTRACTSPLIT, BOSUuid
			// .read(contractID));
			
			
			// 更新合同的拆分状态 by sxhong
			updateContractSplitState(ctx, contractID, isExistOtherSplit);
		}

		else {
			// 作废付款拆分
			PaymentNoCostSplitCollection paymentNoSplitCollection = PaymentNoCostSplitFactory
					.getLocalInstance(ctx).getPaymentNoCostSplitCollection(
							paySplitView);
			FilterInfo filterPay = getFilterPay(contractID);
			boolean hasSettlePayed = PaymentNoCostSplitFactory
					.getLocalInstance(ctx).exists(filterPay);
			// VoucherInfo newVoucher = new VoucherInfo();
			for (Iterator iter = paymentNoSplitCollection.iterator(); iter
					.hasNext();) {
				PaymentNoCostSplitInfo element = (PaymentNoCostSplitInfo) iter
						.next();
				// if(element.isIsRedVouchered()){
				// 作废付款拆分
				element.setLastUpdateTime(updateTime);
				setPaymentNoCostSplitInvalid(isAdjust, ipaymentbill, hasSettlePayed, element);
				PaymentNoCostSplitFactory.getLocalInstance(ctx).updatePartial(
						element, psUpdSelector);
			}

			// 作废结算拆分
			EntityViewInfo settSplitView = new EntityViewInfo();
			FilterInfo settSplitFilter = new FilterInfo();
			settSplitFilter.getFilterItems().add(
					new FilterItemInfo("contractBill.id", contractID));
			settSplitFilter.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
							CompareType.NOTEQUALS));
			settSplitView.setFilter(settSplitFilter);
			settSplitView.getSelector().add("id");
			settSplitView.getSelector().add("state");
			settSplitView.getSelector().add("settlementBill.id");

			SettNoCostSplitCollection settNoCostSplitCollection = SettNoCostSplitFactory
					.getLocalInstance(ctx).getSettNoCostSplitCollection(
							settSplitView);
			for (Iterator it = settNoCostSplitCollection.iterator(); it
					.hasNext();) {
				SettNoCostSplitInfo info = (SettNoCostSplitInfo) it.next();
				info.setLastUpdateTime(updateTime);
				updateSettNoCostSplit(ctx, info,isAdjust);
			}

			// 作废变更拆分
			EntityViewInfo changeView = new EntityViewInfo();
			FilterInfo changeFilter = new FilterInfo();
			changeFilter.getFilterItems().add(
					new FilterItemInfo("contractChange.contractBill.id",
							contractID));
			changeFilter.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
							CompareType.NOTEQUALS));
			changeView.setFilter(changeFilter);
			changeView.getSelector().add("id");
			changeView.getSelector().add("contractChange.id");
			ConChangeNoCostSplitCollection conChangeSplitCollection = ConChangeNoCostSplitFactory
					.getLocalInstance(ctx).getConChangeNoCostSplitCollection(
							changeView);
			for (Iterator iter = conChangeSplitCollection.iterator(); iter
					.hasNext();) {

				ConChangeNoCostSplitInfo info = (ConChangeNoCostSplitInfo) iter
						.next();

				info.setState(FDCBillStateEnum.INVALID);
				info.setIsInvalid(true);
				info.setLastUpdateTime(updateTime);
				SelectorItemCollection updSelector = new SelectorItemCollection();
				updSelector.add("state");
				updSelector.add("isInvalid");
				updSelector.add("lastUpdateTime");
				ConChangeNoCostSplitFactory.getLocalInstance(ctx)
						.updatePartial(info, updSelector);
			}

			// 作废合同拆分
			EntityViewInfo conView = new EntityViewInfo();
			FilterInfo conFilter = new FilterInfo();
			conFilter.getFilterItems().add(
					new FilterItemInfo("contractBill.id", contractID));
			conFilter.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
							CompareType.NOTEQUALS));
			conView.setFilter(conFilter);
			conView.getSelector().add("id");
			ConNoCostSplitCollection conNoCostSplitCollection = ConNoCostSplitFactory
					.getLocalInstance(ctx).getConNoCostSplitCollection(conView);
			if (conNoCostSplitCollection.size() > 0) {

				ConNoCostSplitInfo info = conNoCostSplitCollection.get(0);

				info.setState(FDCBillStateEnum.INVALID);
				info.setIsInvalid(true);
				info.setLastUpdateTime(updateTime);
				SelectorItemCollection updSelector = new SelectorItemCollection();
				updSelector.add("state");
				updSelector.add("isInvalid");
				updSelector.add("lastUpdateTime");
				ConNoCostSplitFactory.getLocalInstance(ctx).updatePartial(info,
						updSelector);
			}
			
			// 更新合同的拆分状态 by sxhong
			updateContractSplitState(ctx, contractID, isHasVoucher);
		}
	}

	/**
	 * @param ctx
	 * @param isAdjust
	 * @param info
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void updateSettlementCostSplit(Context ctx, boolean isAdjust, SettlementCostSplitInfo info) throws BOSException, EASBizException {
		info.setState(FDCBillStateEnum.INVALID);
		info.setIsInvalid(true);
		SelectorItemCollection updSelector = new SelectorItemCollection();
		updSelector.add("state");
		updSelector.add("isInvalid");
		updSelector.add("lastUpdateTime");
		if(isAdjust){
			updSelector.add("isBeforeAdjust");
			info.setIsBeforeAdjust(true);
		}
		SettlementCostSplitFactory.getLocalInstance(ctx).updatePartial(
				info, updSelector);
	}

	private boolean isNoTextCostSplit(Context ctx, String id)
			throws EASBizException, BOSException {
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("id");
		selector.add("isCostSplit");
		ContractWithoutTextInfo info = ContractWithoutTextFactory
				.getLocalInstance(ctx).getContractWithoutTextInfo(
						new ObjectUuidPK(id), selector);
		return info.isIsCostSplit();
	}

	private void updateConNoTextSplitState(Context ctx, String contractId,
			boolean isHasVoucher) throws BOSException, EASBizException {
//		if(!getFinanicalByContractID(ctx, contractId))
//			return;
		if (isHasVoucher) {
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder
					.appendSql("update T_CON_ContractWithoutText set FConSplitExecState=? where fid=?");
			builder.addParam(ConSplitExecStateEnum.INVALID_VALUE);
			builder.addParam(contractId);
			builder.executeUpdate();
		}
	}

	protected void _clearNoTextSplit(Context ctx, String id)
			throws BOSException, EASBizException {
		if (fdcCostSplit == null) {
			fdcCostSplit = new FDCCostSplit(ctx);
		}
		boolean isAdjust = isAdjustModel(ctx,id);
		IPaymentBill ipaymentbill = PaymentBillFactory.getLocalInstance(ctx);
		IWorkLoadConfirmBill iworkload = WorkLoadConfirmBillFactory.getLocalInstance(ctx);
		boolean isHasVoucher = isHasVoucher(ctx, id, isAdjust,
				ipaymentbill, iworkload);
		Timestamp updateTime = getTime();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("paymentBill.contractBillId", id));
		// filter.getFilterItems().add(
		// new FilterItemInfo("fiVouchered", Boolean.TRUE));
		filter.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
						CompareType.NOTEQUALS));
		view.setFilter(filter);
		view.getSelector().add("paymentBill.id");
		view.getSelector().add("paymentBill.fiVouchered");
		SelectorItemCollection psUpdSelector = getPsUpSelector(isAdjust);
		if (isNoTextCostSplit(ctx, id)) {
			PaymentSplitCollection coll = PaymentSplitFactory.getLocalInstance(
					ctx).getPaymentSplitCollection(view);
			for (int i = 0; i < coll.size(); i++) {
				PaymentSplitInfo info = coll.get(i);
				info.setState(FDCBillStateEnum.INVALID);
				info.setIsInvalid(true);
				info.setLastUpdateTime(updateTime);
				if(isAdjust){
					/*					
					if(info.getPaymentBill().isFiVouchered()){
						//是否已经在待处理里面，如果在的话，本章张付款拆分了不用置为isBeforeAdjust=1 modify by sxhong 2009-08-01 20:44:13
						FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
						builder.appendSql("select 1 from T_CON_ContractWithoutText where fid=? and FConSplitExecState=?");
						builder.addParam(id);
						builder.addParam(ConSplitExecStateEnum.INVALID_VALUE);
						if(!builder.isExist()){
						
						}
					}*/
					//上面的判断不用的原则是无文本合同在待处理里面，无文本付款拆分肯定没有被引用  by sxhong 2009-08-02 00:44:33
					if(info.getVoucherRefer()!=null&&!info.getVoucherRefer().equals(PaySplitVoucherRefersEnum.NOTREFER)){
						info.setIsBeforeAdjust(true);
					}else{
						info.setIsBeforeAdjust(false);
					}
				}else{
					//don't fiVouchered false if adjust model
					SelectorItemCollection selector = new SelectorItemCollection();
					selector.add(new SelectorItemInfo("fiVouchered"));
					info.getPaymentBill().setFiVouchered(false);
					ipaymentbill.updatePartial(info.getPaymentBill(), selector);
				}
				PaymentSplitFactory.getLocalInstance(ctx).updatePartial(info,
						psUpdSelector);

				// 作废成本数据
				InvalidCostSplitHelper.invalidCostSplit(ctx,
						CostSplitBillTypeEnum.PAYMENTSPLIT, BOSUuid.read(id),
						info.getId());

				// 作废成本数据
				InvalidCostSplitHelper.invalidCostSplit(ctx,
						CostSplitBillTypeEnum.NOTEXTCONSPLIT, BOSUuid.read(id),
						info.getId());
				// fdcCostSplit.deleteCostSplit(ctx,
				// CostSplitBillTypeEnum.PAYMENTSPLIT, BOSUuid.read(id));
				// fdcCostSplit.deleteCostSplit(ctx,
				// CostSplitBillTypeEnum.NOTEXTCONSPLIT, BOSUuid.read(id));
			}
		} else {
			PaymentNoCostSplitCollection coll = PaymentNoCostSplitFactory
					.getLocalInstance(ctx)
					.getPaymentNoCostSplitCollection(view);
			for (int i = 0; i < coll.size(); i++) {
				PaymentNoCostSplitInfo info = coll.get(i);
				setPaymentNoCostSplitInvalid(isAdjust, ipaymentbill, false, info);
				PaymentNoCostSplitFactory.getLocalInstance(ctx).updatePartial(
						info, psUpdSelector);
			}
		}
		updateConNoTextSplitState(ctx, id, isHasVoucher);
	}

	protected void _clearSettle(Context ctx, String contractId)
			throws BOSException, EASBizException {
		if (fdcCostSplit == null) {
			fdcCostSplit = new FDCCostSplit(ctx);
		}
		boolean isAdjust = isAdjustModel(ctx,contractId);
		IPaymentBill ipaymentbill = PaymentBillFactory.getLocalInstance(ctx);
		IWorkLoadConfirmBill iworkload = WorkLoadConfirmBillFactory.getLocalInstance(ctx);
		boolean isHasVoucher = isHasVoucher(ctx, contractId, isAdjust,
				ipaymentbill, iworkload);
		Timestamp updateTime = getTime();
		/**
		 * 清除付款拆分
		 */
		EntityViewInfo payBillView = new EntityViewInfo();
		FilterInfo payBillFilter = new FilterInfo();
		payBillFilter.getFilterItems().add(
				new FilterItemInfo("contractBillId", contractId));
		payBillView.setFilter(payBillFilter);
		payBillView.getSelector().add("id");

		PaymentBillCollection paymentBillCollection = ipaymentbill
				.getPaymentBillCollection(payBillView);

		Set payIdSet = new HashSet();

		for (Iterator iter = paymentBillCollection.iterator(); iter.hasNext();) {
			PaymentBillInfo element = (PaymentBillInfo) iter.next();
			String id = element.getId().toString();

			payIdSet.add(id);
		}

		EntityViewInfo paySplitView = getPaySplitView(contractId);
		SelectorItemCollection psUpdSelector = getPsUpSelector(isAdjust);
		if (isCostSplit(ctx, contractId)) {
			PaymentSplitCollection paymentSplitCollection = PaymentSplitFactory
					.getLocalInstance(ctx).getPaymentSplitCollection(
							paySplitView);
			FilterInfo filterPay = getFilterPay(contractId);
			boolean hasSettlePayed = PaymentSplitFactory.getLocalInstance(ctx)
					.exists(filterPay);
			for (Iterator iter = paymentSplitCollection.iterator(); iter
					.hasNext();) {
				PaymentSplitInfo element = (PaymentSplitInfo) iter.next();
				// 作废付款拆分
				setPaymentSplitInvalid(isAdjust, ipaymentbill, hasSettlePayed, element);
				// fdcCostSplit.deleteCostSplit(ctx,
				// CostSplitBillTypeEnum.PAYMENTSPLIT, element
				// .getPaymentBill().getId());
				// 作废成本数据
				BOSUuid costId=null;
				if(element.isIsWorkLoadBill()){
					costId=element.getWorkLoadConfirmBill().getId();
				}else{
					costId=element.getPaymentBill().getId();
				}
				InvalidCostSplitHelper.invalidCostSplit(ctx,
						CostSplitBillTypeEnum.PAYMENTSPLIT, costId, element.getId());;
				PaymentSplitFactory.getLocalInstance(ctx).updatePartial(
						element, psUpdSelector);

			}
			/**
			 * 清除结算拆分
			 */
			EntityViewInfo settSplitView = new EntityViewInfo();
			FilterInfo settSplitFilter = new FilterInfo();
			settSplitFilter.getFilterItems().add(
					new FilterItemInfo("contractBill.id", contractId));
			settSplitFilter.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
							CompareType.NOTEQUALS));
			settSplitView.setFilter(settSplitFilter);
			settSplitView.getSelector().add("id");
			settSplitView.getSelector().add("state");
			settSplitView.getSelector().add("settlementBill.id");

			SettlementCostSplitCollection settlementCostSplitCollection = SettlementCostSplitFactory
					.getLocalInstance(ctx).getSettlementCostSplitCollection(
							settSplitView);
			for (Iterator it = settlementCostSplitCollection.iterator(); it
					.hasNext();) {
				SettlementCostSplitInfo info = (SettlementCostSplitInfo) it
						.next();
				info.setLastUpdateTime(updateTime);
				updateSettlementCostSplit(ctx, isAdjust, info);

				// fdcCostSplit.deleteCostSplit(ctx,
				// CostSplitBillTypeEnum.SETTLEMENTSPLIT, element
				// .getId());

				// 作废成本数据
				InvalidCostSplitHelper.invalidCostSplit(ctx,
						CostSplitBillTypeEnum.SETTLEMENTSPLIT, info
								.getSettlementBill().getId(), info.getId());
			}
		}

		else {
			// 作废付款拆分
			PaymentNoCostSplitCollection paymentNoSplitCollection = PaymentNoCostSplitFactory
					.getLocalInstance(ctx).getPaymentNoCostSplitCollection(
							paySplitView);
			FilterInfo filterPay = getFilterPay(contractId);
			boolean hasSettlePayed = PaymentNoCostSplitFactory
					.getLocalInstance(ctx).exists(filterPay);
			for (Iterator iter = paymentNoSplitCollection.iterator(); iter
					.hasNext();) {
				PaymentNoCostSplitInfo element = (PaymentNoCostSplitInfo) iter
						.next();
				// 作废付款拆分
				setPaymentNoCostSplitInvalid(isAdjust, ipaymentbill, hasSettlePayed, element);
				PaymentNoCostSplitFactory.getLocalInstance(ctx).updatePartial(
						element, psUpdSelector);
			}

			// 作废结算拆分
			EntityViewInfo settSplitView = new EntityViewInfo();
			FilterInfo settSplitFilter = new FilterInfo();
			settSplitFilter.getFilterItems().add(
					new FilterItemInfo("contractBill.id", contractId));
			settSplitFilter.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
							CompareType.NOTEQUALS));
			settSplitView.setFilter(settSplitFilter);
			settSplitView.getSelector().add("id");
			settSplitView.getSelector().add("state");
			settSplitView.getSelector().add("settlementBill.id");

			SettNoCostSplitCollection settNoCostSplitCollection = SettNoCostSplitFactory
					.getLocalInstance(ctx).getSettNoCostSplitCollection(
							settSplitView);
			for (Iterator it = settNoCostSplitCollection.iterator(); it
					.hasNext();) {
				SettNoCostSplitInfo info = (SettNoCostSplitInfo) it.next();
				updateSettNoCostSplit(ctx, info,isAdjust);
				}
		}

		// 更新合同的拆分状态 by sxhong
		updateContractSplitStatebySett(ctx, contractId, isHasVoucher);
	}

	/**
	 * //更新合同的拆分状态 by sxhong
	 * 
	 * @param ctx
	 * @param contractid
	 * @throws EASBizException 
	 */
	private void updateContractSplitStatebySett(Context ctx, String contractId,
			boolean isHasVoucher) throws BOSException, EASBizException {
//		if(!getFinanicalByContractID(ctx, contractId))
//			return;
		//		if (isHasVoucher) {
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder
					.appendSql("update T_CON_ContractBill set FConSplitExecState=? where fid=?");
			builder.addParam(ConSplitExecStateEnum.INVALID_VALUE);
			builder.addParam(contractId);
			builder.executeUpdate();
		//		}
	}

	protected void _clearWithoutTextSplit(Context ctx, String id)
			throws BOSException, EASBizException {
		if (fdcCostSplit == null) {
			fdcCostSplit = new FDCCostSplit(ctx);
		}
		boolean isAdjust = isAdjustModel(ctx,id);
		IPaymentBill ipaymentbill = PaymentBillFactory.getLocalInstance(ctx);
		IWorkLoadConfirmBill iworkload = WorkLoadConfirmBillFactory.getLocalInstance(ctx);
		boolean isHasVoucher = isHasVoucher(ctx, id, isAdjust,
				ipaymentbill, iworkload);
		Timestamp updateTime = getTime();
		EntityViewInfo view = getPaySplitView(id);
		SelectorItemCollection psUpdSelector = getPsUpSelector(isAdjust);
		VoucherInfo newVoucher = new VoucherInfo();
		List idList = new ArrayList();
		IVoucher voucher = VoucherFactory.getLocalInstance(ctx);
		if (isNoTextCostSplit(ctx, id)) {
			PaymentSplitCollection coll = PaymentSplitFactory.getLocalInstance(
					ctx).getPaymentSplitCollection(view);
			for (int i = 0; i < coll.size(); i++) {
				PaymentSplitInfo info = coll.get(i);
				info.setState(FDCBillStateEnum.INVALID);
				info.setIsInvalid(true);
				info.setLastUpdateTime(updateTime);
				if(isAdjust){
					if(info.getVoucherRefer()!=null&&!info.getVoucherRefer().equals(PaySplitVoucherRefersEnum.NOTREFER)){
						info.setIsBeforeAdjust(true);
					}else{
						info.setIsBeforeAdjust(false);
					}
					PaymentSplitFactory.getLocalInstance(ctx).updatePartial(info,
						psUpdSelector);
				}else{
					SelectorItemCollection selector = new SelectorItemCollection();
					selector.add(new SelectorItemInfo("fiVouchered"));
					info.getPaymentBill().setFiVouchered(false);
					ipaymentbill.updatePartial(info.getPaymentBill(), selector);
				}
				
				if (info.isFivouchered()) {
					EntityViewInfo mapping = new EntityViewInfo();
					FilterInfo mappingFilter = new FilterInfo();
					mappingFilter.getFilterItems()
							.add(
									new FilterItemInfo("srcEntityID", info
											.getBOSType()));
					mappingFilter.getFilterItems().add(
							new FilterItemInfo("destEntityID", newVoucher
									.getBOSType()));
					mappingFilter.getFilterItems().add(
							new FilterItemInfo("srcObjectID", info.getId()
									.toString()));
					mapping.setFilter(mappingFilter);
					mapping.getSelector().add("id");
					mapping.getSelector().add("destObjectID");
					BOTRelationCollection relationColl = BOTRelationFactory
							.getLocalInstance(ctx).getCollection(mapping);
					for (Iterator it = relationColl.iterator(); it.hasNext();) {
						BOTRelationInfo botInfo = (BOTRelationInfo) it.next();
						BOSUuid voucherId = BOSUuid.read(botInfo
								.getDestObjectID());
						SelectorItemCollection origen = new SelectorItemCollection();
						origen.add("id");
						origen.add("hasReversed");
						VoucherInfo oldInfo = (VoucherInfo) voucher.getValue(
								new ObjectUuidPK(voucherId), origen);
						if (!oldInfo.isHasReversed()) {
							idList.add(voucherId.toString());
						}
					}
				}
				// 作废成本数据
				InvalidCostSplitHelper.invalidCostSplit(ctx,
						CostSplitBillTypeEnum.PAYMENTSPLIT, BOSUuid.read(id),
						info.getId());

				// 作废成本数据
				InvalidCostSplitHelper.invalidCostSplit(ctx,
						CostSplitBillTypeEnum.NOTEXTCONSPLIT, BOSUuid.read(id),
						info.getId());
				// fdcCostSplit.deleteCostSplit(ctx,
				// CostSplitBillTypeEnum.PAYMENTSPLIT, BOSUuid.read(id));
				// fdcCostSplit.deleteCostSplit(ctx,
				// CostSplitBillTypeEnum.NOTEXTCONSPLIT, BOSUuid.read(id));
			}
		} else {
			PaymentNoCostSplitCollection coll = PaymentNoCostSplitFactory
					.getLocalInstance(ctx)
					.getPaymentNoCostSplitCollection(view);
			for (int i = 0; i < coll.size(); i++) {
				PaymentNoCostSplitInfo info = coll.get(i);
				info.setLastUpdateTime(updateTime);
				setPaymentNoCostSplitInvalid(isAdjust, ipaymentbill, false, info);
				PaymentNoCostSplitFactory.getLocalInstance(ctx).updatePartial(
						info, psUpdSelector);

				if (info.isFivouchered()) {
					EntityViewInfo mapping = new EntityViewInfo();
					FilterInfo mappingFilter = new FilterInfo();
					mappingFilter.getFilterItems()
							.add(
									new FilterItemInfo("srcEntityID", info
											.getBOSType()));
					mappingFilter.getFilterItems().add(
							new FilterItemInfo("destEntityID", newVoucher
									.getBOSType()));
					mappingFilter.getFilterItems().add(
							new FilterItemInfo("srcObjectID", info.getId()
									.toString()));
					mapping.setFilter(mappingFilter);
					mapping.getSelector().add("id");
					mapping.getSelector().add("destObjectID");
					BOTRelationCollection relationColl = BOTRelationFactory
							.getLocalInstance(ctx).getCollection(mapping);
					for (Iterator it = relationColl.iterator(); it.hasNext();) {
						BOTRelationInfo botInfo = (BOTRelationInfo) it.next();
						BOSUuid voucherId = BOSUuid.read(botInfo
								.getDestObjectID());
						SelectorItemCollection origen = new SelectorItemCollection();
						origen.add("id");
						origen.add("hasReversed");
						VoucherInfo oldInfo = (VoucherInfo) voucher.getValue(
								new ObjectUuidPK(voucherId), origen);
						if (!oldInfo.isHasReversed()) {
							idList.add(voucherId.toString());
						}
					}
				}
			}
		}

		if (!isAdjust&&idList.size() > 0) {
			IObjectPK newVoucherPk = voucher.reverseSaveBatch(idList);
			PaySplitUtilFacadeFactory.getLocalInstance(ctx)
					.traceReverseVoucher(newVoucherPk);
		}
		if(isAdjust){
			/***
			 * 设置合同进入待处理合同
			 */
			updateConNoTextSplitState(ctx, id, isHasVoucher);
		}
	}

	protected void _clearPaymentSplit(Context ctx, List idList)
			throws BOSException, EASBizException {
		if (fdcCostSplit == null) {
			fdcCostSplit = new FDCCostSplit(ctx);
		}
		Timestamp updateTime = getTime();
		IPaymentBill ipaymentbill = PaymentBillFactory.getLocalInstance(ctx);
		IWorkLoadConfirmBill iworkload = WorkLoadConfirmBillFactory.getLocalInstance(ctx);
		for (int i = 0, size = idList.size(); i < size; i++) {
			String contractId = idList.get(i).toString();
			
			boolean isAdjust = isAdjustModel(ctx,contractId);
			boolean isHasVoucher = isHasVoucher(ctx, contractId, isAdjust,
					ipaymentbill, iworkload);
			/**
			 * 清除付款拆分
			 */
			EntityViewInfo payBillView = new EntityViewInfo();
			FilterInfo payBillFilter = new FilterInfo();
			payBillFilter.getFilterItems().add(
					new FilterItemInfo("contractBillId", contractId));
			payBillView.setFilter(payBillFilter);
			payBillView.getSelector().add("id");

			PaymentBillCollection paymentBillCollection = ipaymentbill
					.getPaymentBillCollection(payBillView);

			Set payIdSet = new HashSet();

			for (Iterator iter = paymentBillCollection.iterator(); iter
					.hasNext();) {
				PaymentBillInfo element = (PaymentBillInfo) iter.next();
				String id = element.getId().toString();

				payIdSet.add(id);
			}

			EntityViewInfo paySplitView = getPaySplitView(contractId);
			SelectorItemCollection psUpdSelector = getPsUpSelector(isAdjust);
			if (isCostSplit(ctx, contractId)) {
				PaymentSplitCollection paymentSplitCollection = PaymentSplitFactory
						.getLocalInstance(ctx).getPaymentSplitCollection(
								paySplitView);
				FilterInfo filterPay = getFilterPay(contractId);
				boolean hasSettlePayed = PaymentSplitFactory.getLocalInstance(
						ctx).exists(filterPay);
				for (Iterator iter = paymentSplitCollection.iterator(); iter
						.hasNext();) {
					PaymentSplitInfo element = (PaymentSplitInfo) iter.next();
					element.setLastUpdateTime(updateTime);
					// 作废付款拆分
					setPaymentSplitInvalid(isAdjust, ipaymentbill, hasSettlePayed, element);
					// fdcCostSplit.deleteCostSplit(ctx,
					// CostSplitBillTypeEnum.PAYMENTSPLIT, element
					// .getPaymentBill().getId());
					// 作废成本数据
					BOSUuid costId=null;
					if(element.isIsWorkLoadBill()){
						costId=element.getWorkLoadConfirmBill().getId();
					}else{
						costId=element.getPaymentBill().getId();
					}
					InvalidCostSplitHelper.invalidCostSplit(ctx,
							CostSplitBillTypeEnum.PAYMENTSPLIT, costId, element.getId());
					PaymentSplitFactory.getLocalInstance(ctx).updatePartial(
							element, psUpdSelector);

				}
			}

			else {
				// 作废付款拆分
				PaymentNoCostSplitCollection paymentNoSplitCollection = PaymentNoCostSplitFactory
						.getLocalInstance(ctx).getPaymentNoCostSplitCollection(
								paySplitView);
				FilterInfo filterPay = getFilterPay(contractId);
				boolean hasSettlePayed = PaymentNoCostSplitFactory
						.getLocalInstance(ctx).exists(filterPay);
				for (Iterator iter = paymentNoSplitCollection.iterator(); iter
						.hasNext();) {
					PaymentNoCostSplitInfo element = (PaymentNoCostSplitInfo) iter
							.next();
					element.setLastUpdateTime(updateTime);
					// 作废付款拆分
					setPaymentNoCostSplitInvalid(isAdjust, ipaymentbill, hasSettlePayed, element);
					PaymentNoCostSplitFactory.getLocalInstance(ctx)
							.updatePartial(element, psUpdSelector);
				}
			}
			// 更新合同的拆分状态 by sxhong
			updateContractSplitStatebySett(ctx, contractId, isHasVoucher);
		}

	}

	/**
	 * @param contractId
	 * @return
	 */
	private EntityViewInfo getPaySplitView(String contractId) {
		//判断是合同还是无文本合同
		boolean isNoText=BOSUuid.read(contractId).getType().equals(new ContractWithoutTextInfo().getBOSType());
		EntityViewInfo paySplitView = new EntityViewInfo();
		FilterInfo paySplitFilter = new FilterInfo();
		if(isNoText){
			paySplitFilter.getFilterItems().add(new FilterItemInfo("paymentBill.contractBillId", contractId, CompareType.EQUALS));
		}else{
			paySplitFilter.getFilterItems().add(new FilterItemInfo("contractBill.id", contractId, CompareType.EQUALS));
		}

		paySplitFilter.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
						CompareType.NOTEQUALS));
		paySplitView.setFilter(paySplitFilter);
		paySplitView.getSelector().add("id");
		paySplitView.getSelector().add("state");
		paySplitView.getSelector().add("paymentBill.id");
		paySplitView.getSelector().add("hisVoucher.id");
		paySplitView.getSelector().add("isRedVouchered");
		paySplitView.getSelector().add("Fivouchered");
		paySplitView.getSelector().add("voucherRefer");
		paySplitView.getSelector().add("paymentBill.fdcPayType.payType.id");
		paySplitView.getSelector().add("paymentBill.Fivouchered");
		paySplitView.getSelector().add("isWorkLoadBill");
		paySplitView.getSelector().add("workLoadConfirmBill.id");
		return paySplitView;
	}

	/**
	 * @param contractId
	 * @return
	 */
	private FilterInfo getFilterPay(String contractId) {
		FilterInfo filterPay = new FilterInfo();
		filterPay.getFilterItems().add(
				new FilterItemInfo("paymentBill.contractBillId",
						contractId));
		filterPay.getFilterItems().add(
				new FilterItemInfo("isProgress", Boolean.TRUE));
		filterPay.getFilterItems().add(
				new FilterItemInfo("Fivouchered", Boolean.TRUE));
		filterPay.getFilterItems().add(
				new FilterItemInfo("state",
						FDCBillStateEnum.INVALID_VALUE,
						CompareType.NOTEQUALS));
		return filterPay;
	}

	protected String _clearPeriodConSplit(Context ctx, String billId,
			String type) throws BOSException, EASBizException {
		boolean isAdjustVourcherMode = isAdjustModel(ctx,billId);
		String result = null;
		String contractId=null;
		PeriodInfo currentPeriod=null;
		if (type.equals("contract")) {
			contractId=billId;
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.clear();
			builder
					.appendSql("select FCurProjectID from t_con_contractbill where fid=?");
			builder.addParam(billId);
			IRowSet rowSet = builder.executeQuery();
			if (rowSet.size() > 0) {
				try {
					rowSet.next();
					String prjId = rowSet.getString("FCurProjectID");
					// 工程项目的当前期间
					currentPeriod = FDCUtils.getCurrentPeriod(ctx,
							prjId, true);
					if (currentPeriod != null) {
						String vourcherNumber=existVourcher(ctx, billId,currentPeriod,false,isAdjustVourcherMode);
						if(vourcherNumber!=null){
							return vourcherNumber;
						} else {
							// 删除发生在后续期间的所有拆分(包括合同拆分,变更拆分,结算拆分,付款拆分)
							FilterInfo filter = new FilterInfo();
							filter.getFilterItems().add(new FilterItemInfo("period.number", Integer.toString(currentPeriod.getNumber()), CompareType.GREATER));
							filter.getFilterItems().add(new FilterItemInfo("contractBill.id", billId));
							ContractCostSplitFactory.getLocalInstance(ctx).delete(filter);

							filter = new FilterInfo();
							filter.getFilterItems().add(new FilterItemInfo("period.number", Integer.toString(currentPeriod.getNumber()), CompareType.GREATER));
							filter.getFilterItems().add(new FilterItemInfo("contractChange.contractBill.id", billId));
							ConChangeSplitFactory.getLocalInstance(ctx).delete(filter);

							filter = new FilterInfo();
							filter.getFilterItems().add(new FilterItemInfo("period.number", Integer.toString(currentPeriod.getNumber()), CompareType.GREATER));
							filter.getFilterItems().add(new FilterItemInfo("settlementBill.contractBill.id", billId));
							SettlementCostSplitFactory.getLocalInstance(ctx).delete(filter);

							filter = new FilterInfo();
							filter.getFilterItems().add(new FilterItemInfo("period.number", Integer.toString(currentPeriod.getNumber()), CompareType.GREATER));
							filter.getFilterItems().add(new FilterItemInfo("paymentBill.contractBillId", billId));
							PaymentSplitFactory.getLocalInstance(ctx).delete(filter);
						}
					}
				} catch (SQLException e) {
					logger.error(e);
					throw new BOSException(e);
				}
			}
		} else if (type.equals("change")) {
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.clear();
			builder.appendSql("select FCurProjectID,FContractBillID from t_con_contractchangebill where fid=?");
			builder.addParam(billId);
			IRowSet rowSet = builder.executeQuery();
			if (rowSet.size() > 0) {
				try {
					rowSet.next();
					String prjId = rowSet.getString("FCurProjectID");
					contractId = rowSet.getString("FContractBillID");
					// 工程项目的当前期间
					currentPeriod = FDCUtils.getCurrentPeriod(ctx,
							prjId, true);
					if (currentPeriod != null) {
						String vourcherNumber=existVourcher(ctx, billId,currentPeriod,false,isAdjustVourcherMode);
						if(vourcherNumber!=null){
							return vourcherNumber;
						} else {
							// 删除发生在后续期间的所有拆分(包括合同拆分,变更拆分,结算拆分,付款拆分)
							FilterInfo filter = new FilterInfo();
							filter.getFilterItems().add(new FilterItemInfo("period.number", Integer.toString(currentPeriod.getNumber()), CompareType.GREATER));
							filter.getFilterItems().add(new FilterItemInfo("contractChange.id", billId));
							ConChangeSplitFactory.getLocalInstance(ctx).delete(filter);

							filter = new FilterInfo();
							filter.getFilterItems().add(new FilterItemInfo("period.number", Integer.toString(currentPeriod.getNumber()), CompareType.GREATER));
							filter.getFilterItems().add(new FilterItemInfo("settlementBill.contractBill.id", contractId));
							SettlementCostSplitFactory.getLocalInstance(ctx).delete(filter);

							filter = new FilterInfo();
							filter.getFilterItems().add(new FilterItemInfo("period.number", Integer.toString(currentPeriod.getNumber()), CompareType.GREATER));
							filter.getFilterItems().add(new FilterItemInfo("paymentBill.contractBillId", contractId));
							PaymentSplitFactory.getLocalInstance(ctx).delete(filter);
						}
					}
				} catch (SQLException e) {
					logger.error(e);
					throw new BOSException(e);
				}
			}
		} else if (type.equals("settlement")) {
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.clear();
			builder
					.appendSql("select FCurProjectId, FContractBillID from T_CON_ContractSettlementBill where fid=?");
			builder.addParam(billId);
			IRowSet rowSet = builder.executeQuery();
			if (rowSet.size() > 0) {
				try {
					rowSet.next();
					String prjId = rowSet.getString("FCurProjectId");
					contractId = rowSet.getString("FContractBillID");
					// 工程项目的当前期间
					currentPeriod = FDCUtils.getCurrentPeriod(ctx,
							prjId, true);
					if (currentPeriod != null) {
						String vourcherNumber=existVourcher(ctx, billId,currentPeriod,false,isAdjustVourcherMode);
						if(vourcherNumber!=null){
							return vourcherNumber;
						} else {
							// 删除发生在后续期间的所有拆分(结算拆分,付款拆分)
							FilterInfo filter = new FilterInfo();
							filter.getFilterItems().add(new FilterItemInfo("period.number", Integer.toString(currentPeriod.getNumber()), CompareType.GREATER));
							filter.getFilterItems().add(new FilterItemInfo("settlementBill.id", billId));
							SettlementCostSplitFactory.getLocalInstance(ctx).delete(filter);

							filter = new FilterInfo();
							filter.getFilterItems().add(new FilterItemInfo("period.number", Integer.toString(currentPeriod.getNumber()), CompareType.GREATER));
							filter.getFilterItems().add(new FilterItemInfo("paymentBill.contractBillId", contractId));
							PaymentSplitFactory.getLocalInstance(ctx).delete(filter);
						}
					}
				} catch (SQLException e) {
					logger.error(e);
					throw new BOSException(e);
				}
			}
		} else if (type.equals("payment")) {
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.clear();
			builder
					.appendSql("select FCurProjectID,FContractBillID from t_cas_paymentbill where fid=?");
			builder.addParam(billId);
			IRowSet rowSet = builder.executeQuery();
			if (rowSet.size() > 0) {
				try {
					rowSet.next();
					String prjId = rowSet.getString("FCurProjectID");
					contractId = rowSet.getString("FContractBillID");
					// 工程项目的当前期间
					currentPeriod = FDCUtils.getCurrentPeriod(ctx,
							prjId, false);
					if (currentPeriod != null) {
						String vourcherNumber=existVourcher(ctx, billId,currentPeriod,true,isAdjustVourcherMode);
						if(vourcherNumber!=null){
							return vourcherNumber;
						} else {
							// 删除发生在后续期间的所有拆分(包括合同拆分,变更拆分,结算拆分,付款拆分)
							FilterInfo filter = new FilterInfo();
							filter.getFilterItems().add(new FilterItemInfo("period.number", Integer.toString(currentPeriod.getNumber()), CompareType.GREATER));
							filter.getFilterItems().add(new FilterItemInfo("paymentBill.id", billId));
							PaymentSplitFactory.getLocalInstance(ctx).delete(filter);
						}
					}
				} catch (SQLException e) {
					logger.error(e);
					throw new BOSException(e);
				}
			}
		}

		//清除调整单
		if(contractId!=null&&currentPeriod!=null&&isAdjustVourcherMode){
			clearAdjustBill(ctx, contractId, currentPeriod);
			
		}
		return result;
	}

	/**
	 * 是否存在下一个期间的凭证
	 * by sxhong 2008/12/14
	 * @param ctx
	 * @param billId isPayment==true?付款单ID:合同ID
	 * @param isPayment 是否是付款单
	 * @return
	 * @throws BOSException 
	 * @throws SQLException 
	 */
	private String existVourcher(Context ctx,String billId,PeriodInfo currentPeriod,boolean isPayment,boolean isAdjustVourcherMode) throws BOSException, SQLException{
		StringBuffer vourcherNumber=new StringBuffer();
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		if(!isPayment){
			String contractId=billId;
			if(!isAdjustVourcherMode){
				StringBuffer sql=new StringBuffer();
				sql.append("select voucher.fnumber from t_gl_voucher voucher where voucher.fid in ");
				sql.append("(select relation.FDestObjectID from T_BOT_Relation relation where relation.FSrcObjectID in ");
				sql.append("(select split.fid from t_fnc_paymentsplit split where split.fpaymentbillid in ");
				sql.append("(select payment.fid from t_cas_paymentbill payment where payment.fcontractBillId=?) ");
				sql.append(" and split.fperiodid in (select period.fid from t_bd_period period where period.fnumber>?)))");
				
				builder.appendSql(sql.toString());
				builder.addParam(contractId);
				builder.addParam(Integer.toString(currentPeriod.getNumber()));
				IRowSet rowSet = builder.executeQuery();
				if (rowSet.size() > 0) {
					while (rowSet.next()) {
						if(vourcherNumber.length()>1){
							vourcherNumber.append("、");
						}
						vourcherNumber.append(rowSet.getString("fnumber"));
					}
				}
			}else{
				//调整凭证模式
				StringBuffer sql=new StringBuffer();
				sql.append("select voucher.fnumber from t_gl_voucher voucher where voucher.fid in ");
				sql.append("(select relation.FDestObjectID from T_BOT_Relation relation where relation.FSrcObjectID in ");
				sql.append("(select payment.fid from t_cas_paymentbill payment where payment.fcontractBillId=?) ");
				sql.append(" and voucher.fperiodid in (select period.fid from t_bd_period period where period.fnumber>?))");
				builder.clear();
				builder.appendSql(sql.toString());
				builder.addParam(contractId);
				builder.addParam(Integer.toString(currentPeriod.getNumber()));
				IRowSet rowSet = builder.executeQuery();
				if (rowSet.size() > 0) {
					//bug by hpw 2010.07.27
					while (rowSet.next()) {
						if(vourcherNumber.length()>1){
							vourcherNumber.append("、");
						}
						vourcherNumber.append(rowSet.getString("fnumber"));
					}
				}
				
				sql.setLength(0);
				sql.append("select voucher.fnumber from t_gl_voucher voucher where voucher.fid in ");
				sql.append("(select relation.FDestObjectID from T_BOT_Relation relation where relation.FSrcObjectID in ");
				sql.append("(select adjustBill.fid from T_FNC_FDCAdjustBill adjustBill where adjustBill.fcontractBillId=?) ");
				sql.append(" and voucher.fperiodid in (select period.fid from t_bd_period period where period.fnumber>?))");
				
				builder.clear();
				builder.appendSql(sql.toString());
				builder.addParam(contractId);
				builder.addParam(Integer.toString(currentPeriod.getNumber()));
				rowSet = builder.executeQuery();
				if (rowSet.size() > 0) {
					while (rowSet.next()) {
						if(vourcherNumber.length()>1){
							vourcherNumber.append("、");
						}
						vourcherNumber.append(rowSet.getString("fnumber"));
					}
				}
			}

		}else{
			//付款单
			String paymentId=billId;
			if(!isAdjustVourcherMode){
				StringBuffer sql=new StringBuffer();
				sql.append("select voucher.fnumber from t_gl_voucher voucher where voucher.fid in ");
				sql.append("(select relation.FDestObjectID from T_BOT_Relation relation where relation.FSrcObjectID in ");
				sql.append("(select split.fid from t_fnc_paymentsplit split where split.fpaymentbillid =?");
				sql.append(" and split.fperiodid in (select period.fid from t_bd_period period where period.fnumber>?)))");
				
				builder.appendSql(sql.toString());
				builder.addParam(paymentId);
				builder.addParam(Integer.toString(currentPeriod.getNumber()));
				IRowSet rowSet = builder.executeQuery();
				if (rowSet.size() > 0) {
					while (rowSet.next()) {
						if(vourcherNumber.length()>1){
							vourcherNumber.append("、");
						}
						vourcherNumber.append(rowSet.getString("fnumber"));
					}
				}
			}else{
				//调整凭证模式
				StringBuffer sql=new StringBuffer();
				sql.append("select voucher.fnumber from t_gl_voucher voucher where voucher.fid in ");
				sql.append("(select relation.FDestObjectID from T_BOT_Relation relation where relation.FSrcObjectID=? ");
				sql.append(" and voucher.fperiodid in (select period.fid from t_bd_period period where period.fnumber>?))");
				builder.clear();
				builder.appendSql(sql.toString());
				builder.addParam(paymentId);
				builder.addParam(Integer.toString(currentPeriod.getNumber()));
				IRowSet rowSet = builder.executeQuery();
				if (rowSet.size() > 0) {
					while (rowSet.next()) {
						if(vourcherNumber.length()>1){
							vourcherNumber.append("、");
						}
						vourcherNumber.append(rowSet.getString("fnumber"));
					}
				}
				
				sql.setLength(0);
				sql.append("select voucher.fnumber from t_gl_voucher voucher where voucher.fid in ");
				sql.append("(select relation.FDestObjectID from T_BOT_Relation relation where relation.FSrcObjectID in ");
				sql.append("(select adjustBill.fid from T_FNC_FDCAdjustBill adjustBill where adjustBill.fcontractBillId in ");
				sql.append("(select payment.fcontractBillId from t_cas_paymentbill payment where payment.fid=?)) ");
				sql.append(" and voucher.fperiodid in (select period.fid from t_bd_period period where period.fnumber>?))");
				
				builder.clear();
				builder.appendSql(sql.toString());
				builder.addParam(paymentId);
				builder.addParam(Integer.toString(currentPeriod.getNumber()));
				rowSet = builder.executeQuery();
				if (rowSet.size() > 0) {
					while (rowSet.next()) {
						if(vourcherNumber.length()>1){
							vourcherNumber.append("、");
						}
						vourcherNumber.append(rowSet.getString("fnumber"));
					}
				}
			}
		}
		if(vourcherNumber.length()==0){
			return null;
		}else{
			return vourcherNumber.toString();
		}
	}
	
	/**
	 * 如果是调整模式则清除拆分的同时清除调整单 by sxhong 2008/12/14
	 * @param ctx
	 * @param contractId
	 * @param currentPeriod
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private void clearAdjustBill(Context ctx,String contractId,PeriodInfo currentPeriod) throws EASBizException, BOSException{
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("period.number", Integer.toString(currentPeriod.getNumber()), CompareType.GREATER));
		filter.getFilterItems().add(new FilterItemInfo("contractBill.id", contractId));
		FDCAdjustBillFactory.getLocalInstance(ctx).delete(filter);
	}
	
	protected void _clearChangeSplit(Context ctx, String changeId)
			throws BOSException, EASBizException {
		if (fdcCostSplit == null) {
			fdcCostSplit = new FDCCostSplit(ctx);
		}
		boolean isAdjust = false;
		Timestamp updateTime = getTime();
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.clear();
		builder
				.appendSql("select FContractBillID from T_CON_ContractChangeBill where fid=?");
		builder.addParam(changeId);
		IRowSet rowSetSplit = builder.executeQuery();
		String contractID = null;
		try {
			if (rowSetSplit.next()) {
				contractID = rowSetSplit.getString("FContractBillID");
				isAdjust = isAdjustModel(ctx, contractID);
				IPaymentBill ipaymentbill = PaymentBillFactory
						.getLocalInstance(ctx);
				IWorkLoadConfirmBill iworkload = WorkLoadConfirmBillFactory.getLocalInstance(ctx);
				boolean isHasVoucher = isHasVoucher(ctx, contractID, isAdjust,
						ipaymentbill, iworkload);
				/**
				 * 清除付款拆分
				 */
				EntityViewInfo payBillView = new EntityViewInfo();
				FilterInfo payBillFilter = new FilterInfo();
				payBillFilter.getFilterItems().add(
						new FilterItemInfo("contractBillId", contractID));
				payBillView.setFilter(payBillFilter);
				payBillView.getSelector().add("id");

				PaymentBillCollection paymentBillCollection = PaymentBillFactory
						.getLocalInstance(ctx).getPaymentBillCollection(
								payBillView);

				Set payIdSet = new HashSet();

				for (Iterator iter = paymentBillCollection.iterator(); iter
						.hasNext();) {
					PaymentBillInfo element = (PaymentBillInfo) iter.next();
					String id = element.getId().toString();

					payIdSet.add(id);
				}

				EntityViewInfo paySplitView = getPaySplitView(contractID);
				SelectorItemCollection psUpdSelector = getPsUpSelector(isAdjust);
				if (isCostSplit(ctx, contractID)) {
					PaymentSplitCollection paymentSplitCollection = PaymentSplitFactory
							.getLocalInstance(ctx).getPaymentSplitCollection(
									paySplitView);
					FilterInfo filterPay = getFilterPay(contractID);
					boolean hasSettlePayed = PaymentSplitFactory
							.getLocalInstance(ctx).exists(filterPay);
					for (Iterator iter = paymentSplitCollection.iterator(); iter
							.hasNext();) {
						PaymentSplitInfo element = (PaymentSplitInfo) iter
								.next();
						// if(!element.isIsRedVouchered()){
						// 作废付款拆分
						setPaymentSplitInvalid(isAdjust, ipaymentbill, hasSettlePayed, element);
						element.setLastUpdateTime(updateTime);
						// fdcCostSplit.deleteCostSplit(ctx,
						// CostSplitBillTypeEnum.PAYMENTSPLIT, element
						// .getPaymentBill().getId());
						// 作废成本数据
						BOSUuid costId=null;
						if(element.isIsWorkLoadBill()){
							costId=element.getWorkLoadConfirmBill().getId();
						}else{
							costId=element.getPaymentBill().getId();
						}
						InvalidCostSplitHelper.invalidCostSplit(ctx,
								CostSplitBillTypeEnum.PAYMENTSPLIT, costId, element.getId());
						PaymentSplitFactory.getLocalInstance(ctx)
								.updatePartial(element, psUpdSelector);

					}
					/**
					 * 清除结算拆分
					 */
					EntityViewInfo settSplitView = new EntityViewInfo();
					FilterInfo settSplitFilter = new FilterInfo();
					settSplitFilter.getFilterItems().add(
							new FilterItemInfo("contractBill.id", contractID));
					settSplitFilter.getFilterItems().add(
							new FilterItemInfo("state",
									FDCBillStateEnum.INVALID_VALUE,
									CompareType.NOTEQUALS));
					settSplitView.setFilter(settSplitFilter);
					settSplitView.getSelector().add("id");
					settSplitView.getSelector().add("state");
					settSplitView.getSelector().add("settlementBill.id");

					SettlementCostSplitCollection settlementCostSplitCollection = SettlementCostSplitFactory
							.getLocalInstance(ctx)
							.getSettlementCostSplitCollection(settSplitView);
					for (Iterator it = settlementCostSplitCollection.iterator(); it
							.hasNext();) {
						SettlementCostSplitInfo info = (SettlementCostSplitInfo) it
								.next();
						info.setState(FDCBillStateEnum.INVALID);
						info.setIsInvalid(true);
						info.setLastUpdateTime(updateTime);
						SelectorItemCollection updSelector = new SelectorItemCollection();
						updSelector.add("state");
						updSelector.add("isInvalid");
						updSelector.add("lastUpdateTime");
						SettlementCostSplitFactory.getLocalInstance(ctx)
								.updatePartial(info, updSelector);
						// fdcCostSplit.deleteCostSplit(ctx,
						// CostSplitBillTypeEnum.SETTLEMENTSPLIT, element
						// .getId());

						// 作废成本数据
						InvalidCostSplitHelper.invalidCostSplit(ctx,
								CostSplitBillTypeEnum.SETTLEMENTSPLIT, info
										.getSettlementBill().getId(), info
										.getId());
					}

					// 作废变更拆分
					EntityViewInfo changeView = new EntityViewInfo();
					FilterInfo changeFilter = new FilterInfo();
					changeFilter.getFilterItems().add(
							new FilterItemInfo(
									"contractChange.contractBill.id",
									contractID));
					changeFilter.getFilterItems().add(
							new FilterItemInfo("state",
									FDCBillStateEnum.INVALID_VALUE,
									CompareType.NOTEQUALS));
					changeFilter.getFilterItems().add(
							new FilterItemInfo("contractChange.id", changeId));
					changeView.setFilter(changeFilter);
					changeView.getSelector().add("id");
					changeView.getSelector().add("contractChange.id");
					ConChangeSplitCollection conChangeSplitCollection = ConChangeSplitFactory
							.getLocalInstance(ctx).getConChangeSplitCollection(
									changeView);
					for (Iterator iter = conChangeSplitCollection.iterator(); iter
							.hasNext();) {

						ConChangeSplitInfo info = (ConChangeSplitInfo) iter
								.next();

						info.setState(FDCBillStateEnum.INVALID);
						info.setIsInvalid(true);
						info.setLastUpdateTime(updateTime);
						SelectorItemCollection updSelector = new SelectorItemCollection();
						updSelector.add("state");
						updSelector.add("isInvalid");
						updSelector.add("lastUpdateTime");
						ConChangeSplitFactory.getLocalInstance(ctx)
								.updatePartial(info, updSelector);
						// fdcCostSplit.deleteCostSplit(ctx,
						// CostSplitBillTypeEnum.CNTRCHANGESPLIT, info
						// .getContractChange().getId());

						// 作废成本数据
						InvalidCostSplitHelper.invalidCostSplit(ctx,
								CostSplitBillTypeEnum.CNTRCHANGESPLIT, info
										.getContractChange().getId(), info
										.getId());
					}
				}

				else {
					// 作废付款拆分
					PaymentNoCostSplitCollection paymentNoSplitCollection = PaymentNoCostSplitFactory
							.getLocalInstance(ctx)
							.getPaymentNoCostSplitCollection(paySplitView);
					FilterInfo filterPay = getFilterPay(contractID);
					boolean hasSettlePayed = PaymentNoCostSplitFactory
							.getLocalInstance(ctx).exists(filterPay);
					// VoucherInfo newVoucher = new VoucherInfo();
					for (Iterator iter = paymentNoSplitCollection.iterator(); iter
							.hasNext();) {
						PaymentNoCostSplitInfo element = (PaymentNoCostSplitInfo) iter
								.next();
						element.setLastUpdateTime(updateTime);
						// if(element.isIsRedVouchered()){
						// 作废付款拆分
						setPaymentNoCostSplitInvalid(isAdjust, ipaymentbill, hasSettlePayed, element);
						PaymentNoCostSplitFactory.getLocalInstance(ctx)
								.updatePartial(element, psUpdSelector);
					}

					// 作废结算拆分
					EntityViewInfo settSplitView = new EntityViewInfo();
					FilterInfo settSplitFilter = new FilterInfo();
					settSplitFilter.getFilterItems().add(
							new FilterItemInfo("contractBill.id", contractID));
					settSplitFilter.getFilterItems().add(
							new FilterItemInfo("state",
									FDCBillStateEnum.INVALID_VALUE,
									CompareType.NOTEQUALS));
					settSplitView.setFilter(settSplitFilter);
					settSplitView.getSelector().add("id");
					settSplitView.getSelector().add("state");
					settSplitView.getSelector().add("settlementBill.id");

					SettNoCostSplitCollection settNoCostSplitCollection = SettNoCostSplitFactory
							.getLocalInstance(ctx)
							.getSettNoCostSplitCollection(settSplitView);
					for (Iterator it = settNoCostSplitCollection.iterator(); it
							.hasNext();) {
						SettNoCostSplitInfo info = (SettNoCostSplitInfo) it
								.next();
						info.setState(FDCBillStateEnum.INVALID);
						info.setIsInvalid(true);
						info.setLastUpdateTime(updateTime);
						SelectorItemCollection updSelector = new SelectorItemCollection();
						updSelector.add("state");
						updSelector.add("isInvalid");
						updSelector.add("lastUpdateTime");
						SettNoCostSplitFactory.getLocalInstance(ctx)
								.updatePartial(info, updSelector);
					}

					// 作废变更拆分

					builder = new FDCSQLBuilder(ctx);
					builder.clear();
					builder
							.appendSql("update T_CON_ConChangeNoCostSplit set FIsInvalid=1,FState=?,FLastUpdateTime=? where FContractChangeID=? and FIsInvalid=0 ");//db2类型校验 不支持split.fisinvalid='0' 
					builder.addParam(FDCBillStateEnum.INVALID_VALUE);
					builder.addParam(updateTime);
					builder.addParam(changeId);
					builder.executeUpdate();

				}

				// 更新合同的拆分状态 by sxhong
				updateContractSplitStatebySett(ctx, contractID, isHasVoucher);
			}
		} catch (SQLException e) {
			logger.error(e);
			throw new BOSException(e);
		}
	}

	protected List _getToInvalidContract(Context ctx, List idList)
			throws BOSException, EASBizException {
		List list = new ArrayList();
		// 先得到所有已拆分且为非作废状态的合同
		EntityViewInfo viewContract = new EntityViewInfo();
		FilterInfo filterContract = new FilterInfo();
		filterContract.getFilterItems()
				.add(
						new FilterItemInfo("curProject.id", FDCHelper.list2Set(idList),
								CompareType.INCLUDE));
		filterContract.getFilterItems().add(
				new FilterItemInfo("splitState",
						CostSplitStateEnum.ALLSPLIT_VALUE));
		filterContract.getFilterItems().add(
				new FilterItemInfo("conSplitExecState",
						ConSplitExecStateEnum.INVALID_VALUE,
						CompareType.NOTEQUALS));
		viewContract.setFilter(filterContract);
		viewContract.getSelector().add("id");
		viewContract.getSelector().add("isCoseSplit");
		ContractBillCollection con = ContractBillFactory.getLocalInstance(ctx)
				.getContractBillCollection(viewContract);
		ContractBillInfo conInfo = new ContractBillInfo();
		ISettlementCostSplit iSettle = SettlementCostSplitFactory
				.getLocalInstance(ctx);
		IConChangeSplit iChange = ConChangeSplitFactory.getLocalInstance(ctx);
		IPaymentSplit iPayment = PaymentSplitFactory.getLocalInstance(ctx);
		IPaymentSplitEntry iPaymentEntry = PaymentSplitEntryFactory
				.getLocalInstance(ctx);

		ISettNoCostSplit iSettleNo = SettNoCostSplitFactory
				.getLocalInstance(ctx);
		IConChangeNoCostSplit iChangeNo = ConChangeNoCostSplitFactory
				.getLocalInstance(ctx);
		IPaymentNoCostSplit iPaymentNo = PaymentNoCostSplitFactory
				.getLocalInstance(ctx);
		IPaymentNoCostSplitEntry iPaymentNoEntry = PaymentNoCostSplitEntryFactory
				.getLocalInstance(ctx);

		for (Iterator it = con.iterator(); it.hasNext();) {
			conInfo = (ContractBillInfo) it.next();
			String conId = conInfo.getId().toString();
			FilterInfo filterSettle = new FilterInfo();
			filterSettle.getFilterItems().add(
					new FilterItemInfo("contractBill.id", conId));
			filterSettle.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
							CompareType.NOTEQUALS));
			filterSettle.getFilterItems().add(
					new FilterItemInfo("settlementBill.isFinalSettle", Boolean.TRUE));
			if (conInfo.isIsCoseSplit()) {
				if (iSettle.exists(filterSettle)) {
					// 有结算拆分，但是没有引用结算拆分的付款拆分。即合同结算已拆分，但是没有付结算款，或者说付的结算款是未拆分状态？
					FilterInfo filterPaySett = new FilterInfo();
					filterPaySett.getFilterItems().add(
							new FilterItemInfo("contractBill.id", conId));
					filterPaySett.getFilterItems().add(
							new FilterItemInfo("paymentBill.fdcPayType.payType.id", PaymentTypeInfo.settlementID));
					filterPaySett.getFilterItems().add(
							new FilterItemInfo("state",
									FDCBillStateEnum.INVALID_VALUE,
									CompareType.NOTEQUALS));
					if(!iPayment.exists(filterPaySett)){
						FilterInfo filterPay = new FilterInfo();
						filterPay.getFilterItems().add(
								new FilterItemInfo("contractBill.id", conId));
						filterPay.getFilterItems().add(
								new FilterItemInfo("state",
										FDCBillStateEnum.INVALID_VALUE,
										CompareType.NOTEQUALS));
						filterPay.getFilterItems().add(
								new FilterItemInfo(
										"paymentBill.fdcPayType.payType.id",
										PaymentTypeInfo.progressID,
										CompareType.EQUALS));
						filterPay.getFilterItems().add(
								new FilterItemInfo("hasEffected", Boolean.TRUE,
										CompareType.NOTEQUALS));
						if (iPayment.exists(filterPay)) {
							list.add(conId);
						}
					}
					continue;
				} else {
					// 存在增加了变更拆分，但是没有引用的付款拆分。这时候肯定没有结算拆分，排除掉结算拆分的情况
					EntityViewInfo viewChange = new EntityViewInfo();
					FilterInfo filterChange = new FilterInfo();
					filterChange.getFilterItems().add(
							new FilterItemInfo("contractBill.id", conId));
					filterChange.getFilterItems().add(
							new FilterItemInfo("state",
									FDCBillStateEnum.INVALID_VALUE,
									CompareType.NOTEQUALS));
					viewChange.setFilter(filterChange);
					SorterItemInfo sorterChange = new SorterItemInfo(
							"createTime");
					sorterChange.setSortType(SortType.DESCEND);
					viewChange.getSorter().add(sorterChange);
					viewChange.getSelector().add("id");
					viewChange.getSelector().add("contractChange.id");
					ConChangeSplitCollection changeColl = iChange
							.getConChangeSplitCollection(viewChange);
					Iterator itChange = changeColl.iterator();
					if (itChange.hasNext()) {
						ConChangeSplitInfo changeInfo = changeColl.get(0);
						String changeSplitId = changeInfo.getId().toString();
						String changeId = changeInfo.getContractChange()
								.getId().toString();
						EntityViewInfo viewPayment = new EntityViewInfo();
						FilterInfo filterPayment = new FilterInfo();
						filterPayment.getFilterItems().add(
								new FilterItemInfo("contractBill.id", conId));
						filterPayment.getFilterItems().add(
								new FilterItemInfo("state",
										FDCBillStateEnum.INVALID_VALUE,
										CompareType.NOTEQUALS));
						viewPayment.setFilter(filterPayment);
						SorterItemInfo sorterPayment = new SorterItemInfo(
								"createTime");
						sorterPayment.setSortType(SortType.DESCEND);
						viewPayment.getSorter().add(sorterPayment);
						viewPayment.getSelector().add("id");
						viewPayment.getSelector().add("entrys.splitBillId");
						viewPayment.getSelector().add("entrys.level");
						PaymentSplitCollection paymentColl = iPayment
								.getPaymentSplitCollection(viewPayment);
						for (Iterator paymentIt = paymentColl.iterator(); paymentIt
								.hasNext();) {
							PaymentSplitInfo paymentInfo = (PaymentSplitInfo) paymentIt
									.next();

							FilterInfo filterPaymentSplit = new FilterInfo();
							filterPaymentSplit.getFilterItems().add(
									new FilterItemInfo(("Parent.id"),
											paymentInfo.getId().toString()));
							filterPaymentSplit.getFilterItems().add(
									new FilterItemInfo(("splitBillId"),null));
							if (iPaymentEntry.exists(filterPaymentSplit)) {
								filterPaymentSplit = new FilterInfo();
								filterPaymentSplit.getFilterItems()
										.add(
												new FilterItemInfo(
														("Parent.id"),
														paymentInfo.getId()
																.toString()));
								filterPaymentSplit.getFilterItems().add(
										new FilterItemInfo(("splitBillId"),
												changeSplitId,
												CompareType.EQUALS));

								if (!iPaymentEntry.exists(filterPaymentSplit)) {
									list.add(conId);
								}
								continue;
							} else {
								filterPaymentSplit = new FilterInfo();
								filterPaymentSplit.getFilterItems()
										.add(
												new FilterItemInfo(
														("Parent.id"),
														paymentInfo.getId()
																.toString()));
								filterPaymentSplit.getFilterItems().add(
										new FilterItemInfo(("costBillId"),
												changeId, CompareType.EQUALS));

								if (!iPaymentEntry.exists(filterPaymentSplit)) {
									list.add(conId);
								}
								continue;
							}
						}
					}
				}
			} else {
				if (iSettleNo.exists(filterSettle)) {
					// 有结算拆分，但是没有引用结算拆分的付款拆分。即合同结算已拆分，但是没有付结算款，或者说付的结算款是未拆分状态？
					FilterInfo filterPaySett = new FilterInfo();
					filterPaySett.getFilterItems().add(
							new FilterItemInfo("contractBill.id", conId));
					filterPaySett.getFilterItems().add(
							new FilterItemInfo("paymentBill.fdcPayType.payType.id", PaymentTypeInfo.settlementID));
					filterPaySett.getFilterItems().add(
							new FilterItemInfo("state",
									FDCBillStateEnum.INVALID_VALUE,
									CompareType.NOTEQUALS));
					if(!iPaymentNo.exists(filterPaySett)){
						FilterInfo filterPay = new FilterInfo();
						filterPay.getFilterItems().add(
								new FilterItemInfo("contractBill.id", conId));
						filterPay.getFilterItems().add(
								new FilterItemInfo("state",
										FDCBillStateEnum.INVALID_VALUE,
										CompareType.NOTEQUALS));
						filterPay.getFilterItems().add(
								new FilterItemInfo("hasEffected", Boolean.TRUE,
										CompareType.NOTEQUALS));
						filterPay.getFilterItems().add(
								new FilterItemInfo(
										"paymentBill.fdcPayType.payType.id",
										PaymentTypeInfo.progressID,CompareType.EQUALS));
						if (iPaymentNo.exists(filterPay)) {
							list.add(conId);
						}
					}
					continue;
				} else {
					// 存在增加了变更拆分，但是没有引用的付款拆分。这时候肯定没有结算拆分，排除掉结算拆分的情况
					EntityViewInfo viewChange = new EntityViewInfo();
					FilterInfo filterChange = new FilterInfo();
					filterChange.getFilterItems().add(
							new FilterItemInfo("contractBill.id", conId));
					filterChange.getFilterItems().add(
							new FilterItemInfo("state",
									FDCBillStateEnum.INVALID_VALUE,
									CompareType.NOTEQUALS));
					viewChange.setFilter(filterChange);
					SorterItemInfo sorterChange = new SorterItemInfo(
							"createTime");
					sorterChange.setSortType(SortType.DESCEND);
					viewChange.getSorter().add(sorterChange);
					viewChange.getSelector().add("id");
					viewChange.getSelector().add("contractChange.id");
					ConChangeNoCostSplitCollection changeColl = iChangeNo.getConChangeNoCostSplitCollection(
									viewChange);
					Iterator itChange = changeColl.iterator();
					if (itChange.hasNext()) {
						ConChangeNoCostSplitInfo changeInfo = changeColl.get(0);
						String changeSplitId = changeInfo.getId().toString();
						String changeId = changeInfo.getContractChange()
								.getId().toString();
						EntityViewInfo viewPayment = new EntityViewInfo();
						FilterInfo filterPayment = new FilterInfo();
						filterPayment.getFilterItems().add(
								new FilterItemInfo("contractBill.id", conId));
						filterPayment.getFilterItems().add(
								new FilterItemInfo("state",
										FDCBillStateEnum.INVALID_VALUE,
										CompareType.NOTEQUALS));
						viewPayment.setFilter(filterPayment);
						SorterItemInfo sorterPayment = new SorterItemInfo(
								"createTime");
						sorterPayment.setSortType(SortType.DESCEND);
						viewPayment.getSorter().add(sorterPayment);
						viewPayment.getSelector().add("id");
						viewPayment.getSelector().add("entrys.splitBillId");
						viewPayment.getSelector().add("entrys.level");
						PaymentNoCostSplitCollection paymentColl = iPaymentNo
								.getPaymentNoCostSplitCollection(viewPayment);
						for (Iterator paymentIt = paymentColl.iterator(); paymentIt
								.hasNext();) {
							PaymentNoCostSplitInfo paymentInfo = (PaymentNoCostSplitInfo) paymentIt
									.next();

							FilterInfo filterPaymentSplit = new FilterInfo();
							filterPaymentSplit.getFilterItems().add(
									new FilterItemInfo(("Parent.id"),
											paymentInfo.getId().toString()));
							filterPaymentSplit.getFilterItems().add(
									new FilterItemInfo(("splitBillId"),
											null,CompareType.NOTEQUALS));
							if (iPaymentNoEntry.exists(filterPaymentSplit)) {
								filterPaymentSplit = new FilterInfo();
								filterPaymentSplit.getFilterItems()
										.add(
												new FilterItemInfo(
														("Parent.id"),
														paymentInfo.getId()
																.toString()));
								filterPaymentSplit.getFilterItems().add(
										new FilterItemInfo(("splitBillId"),
												changeSplitId,
												CompareType.EQUALS));

								if (!iPaymentNoEntry.exists(filterPaymentSplit)) {
									list.add(conId);
								}
								continue;
							} else {
								filterPaymentSplit = new FilterInfo();
								filterPaymentSplit.getFilterItems()
										.add(
												new FilterItemInfo(
														("Parent.id"),
														paymentInfo.getId()
																.toString()));
								filterPaymentSplit.getFilterItems().add(
										new FilterItemInfo(("costBillId"),
												changeId, CompareType.EQUALS));

								if (!iPaymentNoEntry.exists(filterPaymentSplit)) {
									list.add(conId);
								}
								continue;
							}
						}
					}
				}
			}
		}
		return list;
	}
	
	private boolean isAdjustModel(Context ctx, String contractID) throws EASBizException, BOSException{
		// 取工程项目对应的财务组织
		String companyId = null;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		String table = FDCUtils.isContractBill(ctx, contractID)?"t_con_contractbill":"t_con_contractwithouttext";
		builder.appendSql("select ffullorgunit from t_fdc_curproject where fid=(select fcurprojectid from "+table+" where fid=? ) ");
		builder.addParam(contractID);
		IRowSet rowSet=builder.executeQuery();
		try{
			if(rowSet.next()){
				companyId = rowSet.getString("ffullorgunit");
			}
		}catch(SQLException e){
			throw new BOSException(e);
		}
		
//		String companyId=ContextUtil.getCurrentFIUnit(ctx).getId().toString();
		return FDCUtils.isAdjustVourcherModel(ctx, companyId);
	}
	
	private Timestamp getTime()
    {
        Timestamp createtime;
        Date date = new Date();
        long time = date.getTime();
        Calendar d = Calendar.getInstance();
        d.setTime(new Timestamp(time));
        d.set(Calendar.MILLISECOND,0);
        createtime = new Timestamp(d.getTime().getTime());
        return createtime;
    }
}