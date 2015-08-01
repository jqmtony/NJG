package com.kingdee.eas.fdc.contract.client;

import java.sql.SQLException;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ConChangeSplitFactory;
import com.kingdee.eas.fdc.contract.ConSplitExecStateEnum;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.SettlementCostSplitFactory;
import com.kingdee.eas.fdc.finance.PaySplitVoucherRefersEnum;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusFactory;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusInfo;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

public class SplitClearClientHelper {

	public static void checkClearable(CoreUIObject owner, String contractId, boolean isCheckVoucher)
			throws Exception {
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("curProject.id");
		String prjId = null;
		BOSObjectType withoutTxtConBosType = new ContractWithoutTextInfo().getBOSType();
		boolean isConWithoutTxt = false;
		isConWithoutTxt = (BOSUuid.read(contractId)).getType().equals(withoutTxtConBosType);
		if (isConWithoutTxt) {
			ContractWithoutTextInfo contract = ContractWithoutTextFactory.getRemoteInstance()
					.getContractWithoutTextInfo(new ObjectUuidPK(BOSUuid.read(contractId)), selector);
			prjId = contract.getCurProject().getId().toString();
		} else {
			ContractBillInfo contract = ContractBillFactory.getRemoteInstance().getContractBillInfo(
					new ObjectUuidPK(BOSUuid.read(contractId)), selector);
			prjId = contract.getCurProject().getId().toString();
		}
		ProjectPeriodStatusInfo prjInfo = new ProjectPeriodStatusInfo();
		EntityViewInfo viewPrj = new EntityViewInfo();
		FilterInfo filterPrj = new FilterInfo();
		filterPrj.appendFilterItem("project.id", prjId);
		viewPrj.setFilter(filterPrj);
		viewPrj.getSelector().add("*");
		viewPrj.getSelector().add("costPeriod.*");
		viewPrj.getSelector().add("finacialPeriod.*");
		prjInfo = ProjectPeriodStatusFactory.getRemoteInstance().getProjectPeriodStatusCollection(viewPrj).get(0);
		if (prjInfo != null && prjInfo.isIsCostEnd() && !prjInfo.isIsFinacialEnd()) {
			MsgBox.showWarning(owner, "本工程项目已经成本月结，但是财务成本未月结，不允许作废!");
			SysUtil.abort();
		}

		if (isCheckVoucher) {
			checkVoucher(owner, contractId);
		}

	}

	public static void checkSettleClearable(CoreUIObject owner, String contractId, boolean isCheckVoucher)
			throws Exception {
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("curProject.id");
		ContractBillInfo contract = ContractBillFactory.getRemoteInstance().getContractBillInfo(
				new ObjectUuidPK(BOSUuid.read(contractId)), selector);
		String prjId = contract.getCurProject().getId().toString();

		ProjectPeriodStatusInfo prjInfo = new ProjectPeriodStatusInfo();
		EntityViewInfo viewPrj = new EntityViewInfo();
		FilterInfo filterPrj = new FilterInfo();
		filterPrj.appendFilterItem("project.id", prjId);
		viewPrj.setFilter(filterPrj);
		viewPrj.getSelector().add("*");
		viewPrj.getSelector().add("costPeriod.*");
		viewPrj.getSelector().add("finacialPeriod.*");
		prjInfo = ProjectPeriodStatusFactory.getRemoteInstance().getProjectPeriodStatusCollection(viewPrj).get(0);
		if (prjInfo.isIsCostEnd() && !prjInfo.isIsFinacialEnd()) {
			MsgBox.showWarning(owner, "本工程项目已经成本月结，但是财务成本未月结，不允许作废!");
			SysUtil.abort();
		}

		if (isCheckVoucher) {
			checkVoucher(owner, contractId);
		}

	}

	/**
	 * 检查是否被凭证引用 by sxhong 2008/12/14
	 * 
	 * @param owner
	 * @param contractId
	 * @throws EASBizException
	 * @throws BOSException
	 * @throws SQLException
	 */
	private static void checkVoucher(CoreUIObject owner, String contractId) throws EASBizException, BOSException,
			SQLException {
		String msg = "不存在已生成凭证的付款单/付款拆分，不需要作废，请直接删除相关的拆分!";
		if (isAdjustVourcherModel()) {

			msg = "不存在被付款单引用生成凭证的付款拆分或工程量拆分、或者生成凭证的调整单，不需要作废，请直接删除后续相关的拆分之后直接修改拆分!";
			if (isSeparateModel()) {
				msg = "不存在被工程量确认单以及付款单引用生成凭证的拆分或者生成凭证的调整单，不需要作废，请直接删除后续相关的拆分之后直接修改拆分!";
			}
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql("select fisCostSplit,fconsplitexecstate from T_Con_ContractBill where fid=?");
			builder.addParam(contractId);
			IRowSet rowSet = builder.executeQuery();
			boolean isCostSplit = true;
			String conSplitExecState = null;
			boolean isNoText = false;
			if (rowSet.next()) {
				isCostSplit = rowSet.getBoolean("fisCostSplit");
				conSplitExecState = rowSet.getString("fconsplitexecstate");
			} else {
				builder.clear();
				builder
						.appendSql("select fisCostSplit,fconsplitexecstate from t_con_contractwithouttext where fid=?");
				builder.addParam(contractId);
				rowSet = builder.executeQuery();
				if (rowSet.next()) {
					isCostSplit = rowSet.getBoolean("fisCostSplit");
					conSplitExecState = rowSet.getString("fconsplitexecstate");
					isNoText = true;
				}
			}
			builder.clear();
			if (ConSplitExecStateEnum.INVALID_VALUE.equals(conSplitExecState)) {
				MsgBox.showWarning(owner, "合同已经进入待处理流程，不需要作废");
				SysUtil.abort();
			}
			if (isCostSplit) {
				if (!isNoText)
					builder
							.appendSql("select top 1 fid from T_FNC_PaymentSplit where fisInvalid=0 and fcontractBillid=?  and FVoucherRefer <>?");
				else
					builder
							.appendSql("select top 1 fid from T_FNC_PaymentSplit where fisInvalid=0 and Fconwithouttextid=?  and FVoucherRefer <>?");
			} else {
				if (!isNoText)
					builder
							.appendSql("select top 1 fid from T_FNC_PaymentNoCostSplit where fisInvalid=0 and fcontractBillid=?  and FVoucherRefer <>?");
				else
					builder
							.appendSql("select top 1 fid from T_FNC_PaymentNoCostSplit where fisInvalid=0 and Fconwithouttextid=?  and FVoucherRefer <>?");
			}
			builder.addParam(contractId);
			builder.addParam(PaySplitVoucherRefersEnum.NOTREFER_VALUE);
			boolean isExist = builder.isExist();

			/**
			 * 
			 */

			FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
			sqlBuilder
					.appendSql("select pe.fid from t_fnc_fdccostvoucherentry pe left join t_fnc_paymentsplit ps on pe.fparentid=ps.fpaymentbillid where ");
			if (!isNoText)
				sqlBuilder.appendParam("ps.fcontractBillid", contractId);
			else
				sqlBuilder.appendParam("ps.Fconwithouttextid", contractId);
			sqlBuilder.appendSql(" union all ");
			sqlBuilder
					.appendSql("select pe.fid from t_fnc_fdccostvoucherentry pe left join t_fnc_paymentnocostsplit ps on pe.fparentid=ps.fpaymentbillid where ");
			if (!isNoText)
				sqlBuilder.appendParam("ps.fcontractBillid", contractId);
			else
				sqlBuilder.appendParam("ps.Fconwithouttextid", contractId);
			// 加上工程量确认单的判断
			sqlBuilder.appendSql(" union all ");
			sqlBuilder
					.appendSql("select pe.fid from t_fnc_workloadcostvoucherentry pe left join t_fnc_paymentsplit ps on pe.fparentid=ps.fworkloadbillid where ");
			if (!isNoText)
				sqlBuilder.appendParam("ps.fcontractBillid", contractId);
			else
				sqlBuilder.appendParam("ps.Fconwithouttextid", contractId);

			boolean isHaveFDCPayOrCostEntry = sqlBuilder.isExist();

			if (!isExist && !isHaveFDCPayOrCostEntry) {
				//				MsgBox.showWarning(owner, msg);
				//				SysUtil.abort();
			}

			// 判断是否存在未生成凭证但是已经拆分的工程量确认单及付款单，不然会导致生成的调整单有问题
			builder.clear();
			builder
					.appendSql("select 1 from T_fnc_workloadconfirmbill where fcontractbillid=? and ffivouchered<>1  and exists (select 1 from T_fnc_paymentsplit where fworkloadbillid=T_fnc_workloadconfirmbill.fid)");
			builder.appendSql(" union all ");
			builder
					.appendSql("select 1 from T_cas_paymentbill where fcontractbillid=? and ffivouchered<>1  and exists (select 1 from T_fnc_paymentsplit where fpaymentbillid=T_fnc_paymentsplit.fid)");
			builder.addParam(contractId);
			builder.addParam(contractId);
			if (builder.isExist()) {
				//				MsgBox.showWarning(owner, "存在未生成凭证但是已经拆分的工程量确认单或付款单，请先生成凭证或删除后再执行本操作！");
				//				SysUtil.abort();
			}

		} else {
			// 付款拆分
			EntityViewInfo payBillView = new EntityViewInfo();
			FilterInfo payBillFilter = new FilterInfo();
			payBillFilter.getFilterItems().add(new FilterItemInfo("contractBillId", contractId));
			payBillFilter.getFilterItems().add(new FilterItemInfo("fiVouchered", Boolean.TRUE));
			payBillView.setFilter(payBillFilter);
			payBillView.getSelector().add("id");

			PaymentBillCollection paymentBillCollection = PaymentBillFactory.getRemoteInstance()
					.getPaymentBillCollection(payBillView);

			if (paymentBillCollection.size() == 0) {
				MsgBox.showWarning(owner, msg);
				SysUtil.abort();
			}
		}

	}

	// 检查是否存在之后期间的新的拆分版本，如果是，将需要走清除流程
	public static boolean isNewVersionContract(String contractID) throws BOSException, SQLException,
			EASBizException {
		boolean isMoreThanOne = false;
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.clear();
		builder.appendSql("select count(*) as count from t_con_contractcostsplit where fcontractbillid=?");
		builder.addParam(contractID);
		IRowSet rowSet = builder.executeQuery();
		int oriSize = 0;
		if (rowSet.size() > 0) {
			rowSet.next();
			oriSize = rowSet.getInt("count");
			if (oriSize > 1)
				isMoreThanOne = true;
		}

		if (isMoreThanOne == false) {
			builder = new FDCSQLBuilder();
			builder.clear();
			builder.appendSql("select fid from t_con_contractchangebill where fcontractbillid=?");
			builder.addParam(contractID);
			rowSet = builder.executeQuery();
			while (rowSet.next()) {
				String changeId = rowSet.getString("fid");
				builder = new FDCSQLBuilder();
				builder.clear();
				builder
						.appendSql("select count(*) as count from t_con_conchangesplit split where split.FContractChangeID=?");
				builder.addParam(changeId);
				rowSet = builder.executeQuery();
				oriSize = 0;
				if (rowSet.size() > 0) {
					rowSet.next();
					oriSize = rowSet.getInt("count");
					if (oriSize > 1) {
						isMoreThanOne = true;
						break;
					}
				}
			}
		}

		if (isMoreThanOne == false) {
			builder = new FDCSQLBuilder();
			builder.clear();
			builder
					.appendSql("select count(*) as count from t_con_settlementcostsplit split where split.fsettlementbillid"
							+ " in (select settle.fid from t_con_contractsettlementbill settle where settle.fcontractbillid=?)");
			builder.addParam(contractID);
			rowSet = builder.executeQuery();
			oriSize = 0;
			if (rowSet.size() > 0) {
				rowSet.next();
				oriSize = rowSet.getInt("count");
				if (oriSize > 1)
					isMoreThanOne = true;
			}
		}

		if (isMoreThanOne == false) {
			return false;
		} else {
			builder = new FDCSQLBuilder();
			builder.clear();
			builder.appendSql("select FCurProjectID from t_con_contractbill where fid=?");
			builder.addParam(contractID);
			rowSet = builder.executeQuery();
			if (rowSet.size() > 0) {
				rowSet.next();
				String prjId = rowSet.getString("FCurProjectID");
				// 工程项目的当前期间
				PeriodInfo currentPeriod = FDCUtils.getCurrentPeriod(null, prjId, true);
				if (currentPeriod != null) {

					// 是否存在期间在之后的拆分
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(
							new FilterItemInfo("period.number", Integer.toString(currentPeriod.getNumber()),
									CompareType.GREATER));
					filter.getFilterItems().add(new FilterItemInfo("contractBill.id", contractID));
					if (ContractCostSplitFactory.getRemoteInstance().exists(filter))
						return true;

					filter = new FilterInfo();
					filter.getFilterItems().add(
							new FilterItemInfo("period.number", Integer.toString(currentPeriod.getNumber()),
									CompareType.GREATER));
					filter.getFilterItems().add(new FilterItemInfo("contractChange.contractBill.id", contractID));
					if (ConChangeSplitFactory.getRemoteInstance().exists(filter))
						return true;

					filter = new FilterInfo();
					filter.getFilterItems().add(
							new FilterItemInfo("period.number", Integer.toString(currentPeriod.getNumber()),
									CompareType.GREATER));
					filter.getFilterItems().add(new FilterItemInfo("settlementBill.contractBill.id", contractID));
					if (SettlementCostSplitFactory.getRemoteInstance().exists(filter))
						return true;

					filter = new FilterInfo();
					filter.getFilterItems().add(
							new FilterItemInfo("period.number", Integer.toString(currentPeriod.getNumber()),
									CompareType.GREATER));
					filter.getFilterItems().add(new FilterItemInfo("paymentBill.contractBillId", contractID));
					if (PaymentSplitFactory.getRemoteInstance().exists(filter))
						return true;
				}
			}
		}
		return false;
	}

	// 检查是否存在之后期间的新的拆分版本，如果是，将需要走清除流程
	public static boolean isNewVersionSettlement(String settleID) throws BOSException, SQLException,
			EASBizException {
		boolean isMoreThanOne = false;
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.clear();
		builder.appendSql("select count(*) as count from t_con_settlementcostsplit where fsettlementbillid=?");
		builder.addParam(settleID);
		IRowSet rowSet = builder.executeQuery();
		int oriSize = 0;
		if (rowSet.size() > 0) {
			rowSet.next();
			oriSize = rowSet.getInt("count");
			if (oriSize > 1)
				isMoreThanOne = true;
		}

		if (isMoreThanOne == false) {
			return false;
		} else {
			builder = new FDCSQLBuilder();
			builder.clear();
			builder
					.appendSql("select FCurProjectId,FContractBillID from T_CON_ContractSettlementBill where fid=?");
			builder.addParam(settleID);
			rowSet = builder.executeQuery();
			if (rowSet.size() > 0) {
				rowSet.next();
				String prjId = rowSet.getString("FCurProjectId");
				String contractId = rowSet.getString("FContractBillID");

				// 工程项目的当前期间
				PeriodInfo currentPeriod = FDCUtils.getCurrentPeriod(null, prjId, true);
				if (currentPeriod != null) {

					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(
							new FilterItemInfo("period.number", Integer.toString(currentPeriod.getNumber()),
									CompareType.GREATER));
					filter.getFilterItems().add(new FilterItemInfo("settlementBill.id", settleID));
					if (SettlementCostSplitFactory.getRemoteInstance().exists(filter))
						return true;

					filter = new FilterInfo();
					filter.getFilterItems().add(
							new FilterItemInfo("period.number", Integer.toString(currentPeriod.getNumber()),
									CompareType.GREATER));
					filter.getFilterItems().add(new FilterItemInfo("paymentBill.contractBillId", contractId));
					if (PaymentSplitFactory.getRemoteInstance().exists(filter))
						return true;
				}
			}
		}
		return false;
	}

	// 检查是否存在之后期间的新的拆分版本，如果是，将需要走清除流程
	public static boolean isNewVersionPayment(String payID) throws BOSException, SQLException, EASBizException {
		boolean isMoreThanOne = false;
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.clear();
		builder.appendSql("select count(*) as count from t_fnc_paymentsplit where fpaymentBillid=?");
		builder.addParam(payID);
		IRowSet rowSet = builder.executeQuery();
		int oriSize = 0;
		if (rowSet.size() > 0) {
			rowSet.next();
			oriSize = rowSet.getInt("count");
			if (oriSize > 1)
				isMoreThanOne = true;
		}

		if (isMoreThanOne == false) {
			return false;
		} else {
			builder = new FDCSQLBuilder();
			builder.clear();
			builder.appendSql("select FCurProjectID from t_cas_paymentbill where fid=?");
			builder.addParam(payID);
			rowSet = builder.executeQuery();
			if (rowSet.size() > 0) {
				rowSet.next();
				String prjId = rowSet.getString("FCurProjectID");
				// 工程项目的当前期间
				PeriodInfo currentPeriod = FDCUtils.getCurrentPeriod(null, prjId, false);
				if (currentPeriod != null) {
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(
							new FilterItemInfo("period.number", Integer.toString(currentPeriod.getNumber()),
									CompareType.GREATER));
					filter.getFilterItems().add(new FilterItemInfo("paymentBill.id", payID));
					if (PaymentSplitFactory.getRemoteInstance().exists(filter))
						return true;
				}
			}
		}
		return false;
	}

	// 检查是否存在之后期间的新的拆分版本，如果是，将需要走清除流程
	public static boolean isNewVersionChange(String changeID) throws BOSException, SQLException, EASBizException {
		boolean isMoreThanOne = false;
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.clear();
		builder.appendSql("select FCurProjectId,FContractBillID from T_CON_ContractChangeBill where fid=?");
		builder.addParam(changeID);
		String prjId = null;
		String contractId = null;
		IRowSet rowSet = builder.executeQuery();
		if (rowSet.size() > 0) {
			rowSet.next();
			prjId = rowSet.getString("FCurProjectId");
			contractId = rowSet.getString("FContractBillID");
		}

		builder = new FDCSQLBuilder();
		builder.clear();
		builder.appendSql("select count(*) as count from t_con_conchangesplit where FContractChangeID=?");
		builder.addParam(changeID);
		rowSet = builder.executeQuery();
		int oriSize = 0;
		if (rowSet.size() > 0) {
			rowSet.next();
			oriSize = rowSet.getInt("count");
			if (oriSize > 1)
				isMoreThanOne = true;
		}

		builder = new FDCSQLBuilder();
		builder.clear();
		builder.appendSql("select count(*) as count from t_con_settlementcostsplit split ");
		builder.appendSql("inner join  t_con_contractsettlementbill settle on split.fsettlementbillid = settle.fid ");
		builder.appendSql("where settle.fcontractbillid=? ");
		builder.addParam(contractId);
		rowSet = builder.executeQuery();
		oriSize = 0;
		if (rowSet.size() > 0) {
			rowSet.next();
			oriSize = rowSet.getInt("count");
			if (oriSize > 1)
				isMoreThanOne = true;
		}

		if (isMoreThanOne == false) {
			return false;
		} else {

			// 工程项目的当前期间
			PeriodInfo currentPeriod = FDCUtils.getCurrentPeriod(null, prjId, true);
			if (currentPeriod != null) {

				// 是否存在期间在之后的拆分
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("period.number", Integer.toString(currentPeriod.getNumber()),
								CompareType.GREATER));
				filter.getFilterItems().add(new FilterItemInfo("contractChange.id", changeID));
				if (ConChangeSplitFactory.getRemoteInstance().exists(filter))
					return true;

				filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("period.number", Integer.toString(currentPeriod.getNumber()),
								CompareType.GREATER));
				filter.getFilterItems().add(new FilterItemInfo("settlementBill.contractBill.id", contractId));
				if (SettlementCostSplitFactory.getRemoteInstance().exists(filter))
					return true;

				filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("period.number", Integer.toString(currentPeriod.getNumber()),
								CompareType.GREATER));
				filter.getFilterItems().add(new FilterItemInfo("paymentBill.contractBillId", contractId));
				if (PaymentSplitFactory.getRemoteInstance().exists(filter))
					return true;

			}
		}
		return false;
	}

	private static boolean isAdjustVourcherModel() throws EASBizException, BOSException {
		String companyId = SysContext.getSysContext().getCurrentFIUnit().getId().toString();
		boolean isAdjustVourcherMode = FDCUtils.isAdjustVourcherModel(null, companyId);
		return isAdjustVourcherMode;
	}

	/**
	 * 工程量与付款分离模式
	 * 
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private static boolean isSeparateModel() throws EASBizException, BOSException {
		String companyId = SysContext.getSysContext().getCurrentFIUnit().getId().toString();
		boolean isSeparateModel = FDCUtils.getDefaultFDCParamByKey(null, companyId,
				FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT);
		return isSeparateModel;
	}
}
