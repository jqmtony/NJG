package com.kingdee.eas.fdc.basedata.util;

import java.sql.Types;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.report.forapp.kdnote.client.DefaultNoteDataProvider;
import com.kingdee.bos.ctrl.reportone.r1.print.data.R1PrintDataSortItem;
import com.kingdee.bos.ctrl.reportone.r1.print.data.R1PrintDataSource;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.fdc.basedata.FDCBillWFAuditUtil;
import com.kingdee.eas.fdc.basedata.FDCBillWFFacadeFactory;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.jdbc.rowset.impl.ColInfo;
import com.kingdee.jdbc.rowset.impl.DynamicRowSet;
/**
 * RENoteDataProvider data = new RENoteDataProvider(id);
 * KDNoteHelper appHlp = new KDNoteHelper();
 * appHlp.printPreview(getTDFileName(), data, getWindowAncestor(this));
 * @author zhiqiao_yang
 *
 */
public class FDCNoteDataProvider extends DefaultNoteDataProvider {
	private static Logger log = Logger.getLogger("com.kingdee.eas.fdc.basedata.util.client.RENoteDataProvider");
	protected String billId;
	public List auditAllList;
	// 默认数据源：用于支持旧版本单数据源的模板
	protected IMetaDataPK mainQuery;

	public FDCNoteDataProvider(String billId, IMetaDataPK mainQuery) {
		this.billId = billId;
		this.mainQuery = mainQuery;
		Set dataIds = new HashSet();
		dataIds.add(billId);
		setCustomFilterMaker(new DefaultIdFilterMaker(dataIds));
	}
	
	public IRowSet getData(R1PrintDataSource ds) throws Exception {
		// 增加默认数据源
		if (ds.getReference() == null) {
			addMetaDataPK(ds.getId(), mainQuery);
		}
		if ("MAINBILL".equals(ds.getId().toUpperCase()))// 假设主数据源名称为mainbill
		{
			// 返加主数据源的结果集
			return getMainBillRowSet(ds);
		} else if (ds.getId().toUpperCase().startsWith("AUDITINFO")) {
			// 返回参数值为paramVal的从数据源1的结果集
			return getAuditData(ds);
		} else {

			return getOtherSubRowSet(ds);
		}
	}

	// 支持旧接口
	public IRowSet getMainBillRowSet(R1PrintDataSource ds) throws Exception {
		return super.getData(ds);
	}

	public IRowSet getOtherSubRowSet(R1PrintDataSource ds) throws Exception {
		return super.getData(ds);
	}

	public IRowSet getDirectData(R1PrintDataSource ds) throws Exception {
		return super.getData(ds);
	}
	/****
	 * 获取多级审批信息的结果 可以在工作流中设置某一个节点的信息不打印 套打中审批信息的设置，数据源名称必须是auditInfo加一个数字；
	 * 若需一并获取历史审批信息，数据源名称必须是auditInfoWithHis加一个数字;
	 * 
	 * 另外需设置参数与主单据（mainbill）关联 
	 * @param ds
	 * @return
	 */
	public IRowSet getAuditData(R1PrintDataSource ds) {
		DynamicRowSet drs = null;
		try {
			String[] col = FDCBillWFAuditUtil.AuditInfoCols;

			drs = new DynamicRowSet(col.length);
			R1PrintDataSortItem[] fields = ds.getSortFields();
			int sort = 0;
			for(int i=0;i<fields.length;i++){
				R1PrintDataSortItem printDataSortItem = fields[i];
				String sortField = printDataSortItem.getSortField();
				int sortType = printDataSortItem.getSortType();
				if(sortField.equals("createTime")){
					sort=sortType;
					break;
				}
			}
			for (int i = 0; i < col.length; i++) {
				ColInfo ci = new ColInfo();
				ci.colType = Types.VARCHAR;
				ci.columnName = col[i];
				ci.nullable = 1;
				drs.setColInfo(i + 1, ci);
			}
			drs.beforeFirst();

			boolean showHistory = false;
			String id = ds.getId();
			if (auditAllList == null) { 
				showHistory = id.startsWith("auditInfoWithHis");
				auditAllList = FDCBillWFFacadeFactory.getRemoteInstance().getWFAuditResultForPrint2(sort+billId,showHistory);
			}
			String prefixString = showHistory?"auditInfoWithHis":"auditInfo";
			int index = Integer.parseInt(id.substring(prefixString.length(), id.length()));
			if (index - 1 < 0 || index - 1 >= auditAllList.size()) {
				return drs;
			}
			List auditList = (List) auditAllList.get(index - 1);

			int ind = 0;
			for (Iterator it = auditList.iterator(); it.hasNext();) {
				Map auditInfo = (HashMap) it.next();
				drs.moveToInsertRow();
				Set keySet = auditInfo.keySet();
				for (Iterator keys = keySet.iterator(); keys.hasNext();) {
					String iKey = (String) keys.next();
					drs.updateString(iKey, (String) auditInfo.get(iKey));
				}
				drs.updateString(FDCBillWFAuditUtil.ID, String.valueOf(++ind));
				drs.updateString(FDCBillWFAuditUtil.BillID, billId);
				drs.insertRow();
			}
			drs.beforeFirst();
			return drs;

		} catch (Exception e) {
			// @AbortException
			log.error(e.getCause(), e);
		}
		return drs;
	}
	
	/**
	 * 
	 * @param ds
	 * @return
	 */
	public IRowSet getAttchRowSet(R1PrintDataSource ds) {
		IRowSet iRowSet = null;
		try {
			IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(ds.getReference());
			exec.option().isAutoTranslateEnum = true;
			EntityViewInfo ev = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("boAttchAsso.boID", billId, CompareType.EQUALS));
			ev.setFilter(filter);
			exec.setObjectView(ev);
			iRowSet = exec.executeQuery();
			iRowSet.beforeFirst();
		} catch (Exception e) {
			// @AbortException
			log.error(e.getMessage(), e);
		}
		return iRowSet;
	}
}
