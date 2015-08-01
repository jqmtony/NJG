package com.kingdee.eas.fdc.contract.client;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.reportone.r1.print.data.R1PrintDataSource;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.util.FDCNoteDataProvider;
import com.kingdee.eas.fdc.contract.ContractChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class ChangeAuditBillDataProvider extends FDCNoteDataProvider {

	private Map dataMap = new HashMap();

	/** 合同变更发起 ID */
	private String billId = null;

	/** 当前单据下发单位合同ID集合 */
	private Set idSet = new HashSet();

	private Date createTime = null;

	public ChangeAuditBillDataProvider(String billId, IMetaDataPK mainQuery) {
		super(billId, mainQuery);
		this.billId = billId;
	}

	public IRowSet getMainBillRowSet(R1PrintDataSource ds) throws Exception {
		IRowSet iRowSet = super.getMainBillRowSet(ds);
		try {
			iRowSet.beforeFirst();
			while (iRowSet.next()) {
				createTime = iRowSet.getDate("createTime");
			}
			iRowSet.beforeFirst();
		} catch (SQLException e) {
			// TODO ×????ú?? catch ?é
			e.printStackTrace();
			throw new BOSException(e);
		}
		return iRowSet;
	}

	/***************************************************************************
	 * ????????
	 * 
	 * @param ds
	 * @return
	 */
	private IRowSet getSub1RowSet(BOSQueryDataSource ds) {
		IRowSet iRowSet = null;
		IRowSet myRs = null;
		try {
			IQueryExecutor exec = QueryExecutorFactory
					.getRemoteInstance(new MetaDataPK(
							"com.kingdee.eas.fdc.contract.app.EntryChangeAuditPrintQuery"));
			exec.option().isAutoTranslateEnum = true;
			EntityViewInfo ev = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("parent.id", billId, CompareType.EQUALS));
			ev.setFilter(filter);
			exec.setObjectView(ev);
			iRowSet = exec.executeQuery();
			Map map = new HashMap();
			//在套打中变更内容出现多条数据的时候
			//			StringBuilder sql = new StringBuilder();
			//			sql
			//					.append("select billEntry.fid as id,auditEntry.FChangeContent || ';' as content from T_CON_ChangeSupplierEntry billEntry \n");
			//			sql.append("inner join T_CON_SupplierContentEntry contentEntry on billEntry.fid = contentEntry.fparentid \n");
			//			sql.append("inner join T_CON_ChangeAuditEntry auditEntry on contentEntry.fcontentid = auditEntry.fid \n");
			//			sql.append("where billEntry.fparentid = '" + billId + "' ORDER BY billEntry.fid,auditEntry.FSeq \n");
			//myRs = FDCSQLFacadeFactory.getRemoteInstance().executeQuery(sql.toString(), null);
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder
					.appendSql("select billEntry.fid as id,auditEntry.FChangeContent || ';' as content from T_CON_ChangeSupplierEntry billEntry ");
			builder.appendSql("inner join T_CON_SupplierContentEntry contentEntry on billEntry.fid = contentEntry.fparentid ");
			builder.appendSql("inner join T_CON_ChangeAuditEntry auditEntry on contentEntry.fcontentid = auditEntry.fid ");
			builder.appendSql("where billEntry.fparentid = '" + billId + "' ORDER BY billEntry.fid,auditEntry.FSeq ");
			myRs = builder.executeQuery();
			while (myRs.next()) {
				String id = myRs.getString("id");
				String content = myRs.getString("content");
				if (map.containsKey(id)) {
					String existsContent = map.get(id).toString();
					map.put(id, existsContent + content);
				} else {
					map.put(id, content);
				}
			}
			iRowSet.beforeFirst();
			while (iRowSet.next()) {
				String entryId = iRowSet.getString("ID");
				if (map.containsKey(entryId)) {
					String content = map.get(entryId).toString();
					iRowSet.updateString("AUDITCONTENT", content);
				}
			}
			iRowSet.beforeFirst();

		} catch (Exception e) {
			// TODO ×????ú?? catch ?é
			e.printStackTrace();
			SysUtil.abort();
		}

		return iRowSet;
	}

	
	private IRowSet getSub2RowSet(BOSQueryDataSource ds) {
		IRowSet iRowSet = null;
		try {
			IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(new MetaDataPK("com.kingdee.eas.fdc.contract.app.ChangeAuditForPrintQuery"));
			exec.option().isAutoTranslateEnum = true;
			EntityViewInfo ev = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", billId, CompareType.EQUALS));
			ev.setFilter(filter);
			SorterItemCollection coll = new SorterItemCollection();
			coll.add(new SorterItemInfo("entrys.seq"));
			ev.setSorter(coll);
			exec.setObjectView(ev);
			iRowSet = exec.executeQuery();
			iRowSet.beforeFirst();
		} catch (Exception e) {
			e.printStackTrace();
			SysUtil.abort();
		}

		return iRowSet;
	}

	public IRowSet getOtherSubRowSet(BOSQueryDataSource ds) {
		// getSub2RowSet 之所以去掉，是因为在合同变更签证申请套打模板中不需要变更内容，所以去掉此方法
		return getSub1RowSet(ds);

	}

	/**
	 * ???????????á???????????±???¤??????
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
	 * ???????????¤±?±???±??????????¤????(????±???)
	 * 
	 * @param idSet
	 * @param createTime
	 * 
	 * @param
	 * 
	 */
	private void initContractChangeAmt(Set idSet, Date createTime) {
		BigDecimal amt = FDCHelper.ZERO;
		BigDecimal oriAmt = FDCHelper.ZERO;

		ContractChangeBillCollection colls = null;

		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("amount");
		view.getSelector().add("originalAmount");
		view.getSelector().add("balanceAmount");
		view.getSelector().add("oriBalanceAmount");
		view.getSelector().add("contractBill.id");
		view.getSelector().add("contractBill.amount");
		view.getSelector().add("contractBill.originalAmount");
		view.getSelector().add("changeAudit.id");
		view.setFilter(new FilterInfo());
		if (idSet != null && idSet.size() > 0) {
			view.getFilter().getFilterItems().add(
					new FilterItemInfo("contractBill.id", idSet,
							CompareType.INCLUDE));
		} 
		view.getFilter().getFilterItems().add(
				new FilterItemInfo("changeAudit.createTime", createTime,
						CompareType.LESS_EQUALS));
		FilterInfo curFilter = new FilterInfo();
		curFilter.getFilterItems().add(new FilterItemInfo("changeAudit.id", this.billId));
		try {
			view.getFilter().mergeFilter(curFilter, "or");
		} catch (BOSException e1) {
			// TODO ×????ú?? catch ?é
			e1.printStackTrace();
			
			SysUtil.abort();
		}
//		 view.getFilter().getFilterItems().add(
//		 new FilterItemInfo("state", FDCBillStateEnum.VISA_VALUE));
		try {
			colls = ContractChangeBillFactory.getRemoteInstance()
					.getContractChangeBillCollection(view);
		} catch (BOSException e) {
			
			e.printStackTrace();
			SysUtil.abort();
		}
		if (colls == null || colls.size() == 0) {
			return;
		}

		for (int i = 0; i < colls.size(); i++) {
			BigDecimal amount = FDCHelper.ZERO;

			ContractChangeBillInfo info = colls.get(i);
			amt = getBalanceAmount(info.getAmount(), info.getBalanceAmount());
			oriAmt = getBalanceAmount(info.getOriginalAmount(), info
					.getOriBalanceAmount());
			if (info != null && info.getContractBill() != null
					&& info.getContractBill().getId() != null) {
				String conId = info.getContractBill().getId().toString();
				String conIdOri = conId + "ori";
				String conIdPer = conId + "per";
				if(this.billId.equals(info.getChangeAudit().getId().toString())){
					amt = info.getAmount();
					oriAmt = info.getOriginalAmount();
					dataMap.put(conIdPer, amt);
				}  else {
					// ???????????¤????±?±?????
					if (dataMap.get(conId) != null) {
						BigDecimal allAmount = FDCHelper.toBigDecimal(dataMap
								.get(conId));
						allAmount = FDCHelper.add(allAmount, amt);
						dataMap.put(conId, setValue(allAmount));
					} else {
						dataMap.put(conId, setValue(amt));
					}
				}
				
				BigDecimal percent = FDCHelper.divide(FDCHelper.add(dataMap.get(conIdPer),
						dataMap.get(conId)),
						info.getContractBill().getAmount(), 5,
						BigDecimal.ROUND_HALF_UP);
				dataMap.put(conId + "percent", percent);
				if (dataMap.get(conIdOri) != null) {
					BigDecimal allOriAmount = FDCHelper.toBigDecimal(dataMap
							.get(conIdOri));
					allOriAmount = FDCHelper.add(allOriAmount, oriAmt);
					dataMap.put(conIdOri, setValue(allOriAmount));
				} else {
					dataMap.put(conIdOri, setValue(oriAmt));
				}
			}
		}

	}

	public static String setValue(Object o) {
		if (o == null)
			return "";
		else if (o instanceof BigDecimal) {
			if (o.equals(FDCHelper.ZERO))
				return "";
			else
				return String.valueOf(((BigDecimal) o).setScale(2,
						BigDecimal.ROUND_HALF_UP));
		}
		return String.valueOf(o);
	}
}
