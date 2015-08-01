package com.kingdee.eas.fdc.contract.client;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.reportone.r1.print.data.R1PrintDataSource;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.util.FDCNoteDataProvider;
import com.kingdee.eas.fdc.contract.ContractChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.jdbc.rowset.IRowSet;

public class ContractChangeBillDataProvider extends FDCNoteDataProvider {

	private Map dataMap = new HashMap();

	private String billId = null;

	public ContractChangeBillDataProvider(String billId, IMetaDataPK mainQuery) {
		super(billId, mainQuery);
		this.billId = billId;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillDataProvider#getMainBillRowSet(com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource)
	 */
	public IRowSet getMainBillRowSet(R1PrintDataSource ds) throws Exception {
		IRowSet iRowSet = super.getMainBillRowSet(ds);

		try {
			iRowSet.beforeFirst();
			while (iRowSet.next()) {
				iRowSet.updateObject("balanceAmount", getBalanceAmount(iRowSet
						.getBigDecimal("amount"), iRowSet
						.getBigDecimal("balanceAmount")));
				iRowSet.updateObject("oriBalanceAmount", getBalanceAmount(
						iRowSet.getBigDecimal("originalAmount"), iRowSet
								.getBigDecimal("oriBalanceAmount")));
				initContractChangeAmt(iRowSet.getString("contractBill.id"), iRowSet.getDate("createTime"));
				iRowSet.updateObject("allAmount", dataMap.get("allAmount"));
				iRowSet.updateObject("allOriAmount", dataMap
						.get("allOriAmount"));
				iRowSet.updateObject("changeTimes", dataMap.get("changeTimes"));
				iRowSet.updateObject("percent", FDCHelper.divide(FDCHelper.add(getBalanceAmount(
						iRowSet.getBigDecimal("originalAmount"), iRowSet
						.getBigDecimal("oriBalanceAmount")),dataMap.get("allOriAmount")),iRowSet
						.getBigDecimal("contractBill.originalAmount")));
			}
			iRowSet.beforeFirst();
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		return iRowSet;
	}

	public IRowSet getOtherSubRowSet(R1PrintDataSource ds) throws Exception {
		if ("SUB1".equals(ds.getId().toUpperCase())) {// 奥园套打
			return super.getOtherSubRowSet(ds);
		} else {
			IRowSet iRowSet = null;
			try {
				IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(new MetaDataPK("com.kingdee.eas.fdc.contract.app.ContractChangeForPrintQuery"));
				exec.option().isAutoTranslateEnum = true;
				EntityViewInfo ev = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("id", billId, CompareType.EQUALS));
				ev.setFilter(filter);
				exec.setObjectView(ev);
				iRowSet = exec.executeQuery();
				iRowSet.beforeFirst();
				
				if (iRowSet.next()) {
					initContractChangeAmt(iRowSet.getString("contractBill.id"),null);
					iRowSet.updateObject("totalCash", dataMap.get("allAmount"));
				}
				iRowSet.beforeFirst();
			} catch (Exception e) {
				throw new BOSException(e);
			}

			return iRowSet;
		}
	}

	/**
	 * 测算金额：结算金额为零时取预算金额
	 * 
	 * @param measureAmt
	 * @param balanceAmt
	 * @return
	 */
	private BigDecimal getBalanceAmount(BigDecimal measureAmt,
			BigDecimal balanceAmt) {
		if (FDCHelper.toBigDecimal(balanceAmt).compareTo(FDCHelper.ZERO) != 0) {
			return balanceAmt;
		} else if (FDCHelper.toBigDecimal(measureAmt).compareTo(FDCHelper.ZERO) != 0) {
			return measureAmt;
		}
		return null;
	}

	/**
	 * 合同累计签证本币原币金额，签证次数(不含本次)
	 * 
	 * @param contractId
	 * @param date 
	 */
	private void initContractChangeAmt(String contractId, Date date) throws BOSException {
		BigDecimal allAmount = FDCHelper.ZERO;
		BigDecimal allOriAmount = FDCHelper.ZERO;
		ContractChangeBillCollection colls = null;

		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("amount");
		view.getSelector().add("originalAmount");
		view.getSelector().add("balanceAmount");
		view.getSelector().add("oriBalanceAmount");
		view.getSelector().add("contractBill.amount");
		view.getSelector().add("contractBill.originalAmount");
		view.setFilter(new FilterInfo());
		if(date!=null)
		view.getFilter().getFilterItems().add(
				new FilterItemInfo("id", this.billId, CompareType.NOTEQUALS));
//		view.getFilter().getFilterItems().add(
//				new FilterItemInfo("state", FDCBillStateEnum.VISA_VALUE));
		view.getFilter().getFilterItems().add(
				new FilterItemInfo("contractBill.id", contractId));
		if(date!=null)
		view.getFilter().getFilterItems().add(
				new FilterItemInfo("createTime", date, CompareType.LESS_EQUALS));
			colls = ContractChangeBillFactory.getRemoteInstance()
					.getContractChangeBillCollection(view);
		if (colls == null || colls.size() == 0) {
			return;
		}

		dataMap.put("changeTimes", new Integer(colls.size()));
		for (int i = 0; i < colls.size(); i++) {
			ContractChangeBillInfo info = colls.get(i);
			allAmount = FDCHelper.add(allAmount, getBalanceAmount(info
					.getAmount(), info.getBalanceAmount()));
			allOriAmount = FDCHelper.add(allOriAmount, getBalanceAmount(info
					.getOriginalAmount(), info.getOriBalanceAmount()));
		}
		dataMap.put("allAmount", setValue(allAmount));
		dataMap.put("allOriAmount", setValue(allOriAmount));
	}

	public static String setValue(Object o) {
		if (o == null)
			return "";
		else if (o instanceof BigDecimal) {
			if (FDCHelper.toBigDecimal(o).compareTo(FDCHelper.ZERO)==0)
				return "";
			else
				return String.valueOf(((BigDecimal) o).setScale(2,
						BigDecimal.ROUND_HALF_UP));
		}
		return String.valueOf(o);
	}
}
