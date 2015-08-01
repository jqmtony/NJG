package com.kingdee.eas.fdc.finance.client;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.reportone.r1.print.data.R1PrintDataSource;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.util.FDCNoteDataProvider;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 工程量确认单套打数据提供者
 * 
 * @author owen_wen 2011-03-07
 * 
 */
public class WorkLoadConfirmBillDataProvider extends FDCNoteDataProvider {
	private static final Logger logger = Logger.getLogger(WorkLoadConfirmBillDataProvider.class);
	private boolean isBaseTask = true;
	private boolean isBaseContract;
	
	/**
	 * 是否基于合同填报工程量 - R140618-0228
	 * @author zhaoqin
	 * @date 2014/06/30
	 */
	public void setIsBaseContract(boolean isBaseContract) {
		this.isBaseContract = isBaseContract;
	}

	/**
	 * 是否基于进度任务工程量填报，需要外部指定，
	 * 
	 * @param isBaseTask
	 */
	public void setIsBaseTask(boolean isBaseTask) {
		this.isBaseTask = isBaseTask;
	}

	public WorkLoadConfirmBillDataProvider(String billId, IMetaDataPK mainQuery) {
		super(billId, mainQuery);
	}

	public IRowSet getMainBillRowSet(R1PrintDataSource ds) throws Exception {
		if (this.isBaseTask && !ds.getId().equalsIgnoreCase("WorkLoadConfirmBill_baseTask_ForPrintQuery")) {
			FDCMsgBox.showWarning("启用了参数数FDCSCH04_BASEONTASK，" + "套打模板中的Query数据源设置请选择WorkLoadConfirmBill_baseTask_ForPrintQuery");
			SysUtil.abort();
		}
		
		/* modified by zhoqin for R140618-0228 on 2014/06/30 */
		//if (!this.isBaseTask && !ds.getId().equalsIgnoreCase("WorkLoadConfirmBill_baseContract_ForPrintQuery")) {
		if (this.isBaseContract && !ds.getId().equalsIgnoreCase("WorkLoadConfirmBill_baseContract_ForPrintQuery")) {
			FDCMsgBox.showWarning("启用了参数数FDCSCH003，套打模板中的Query数据源设置请选择WorkLoadConfirmBill_baseContract_ForPrintQuery");
			SysUtil.abort();
		}
		
		IRowSet rowSet = super.getMainBillRowSet(ds);
		preRowSetForPeriod(rowSet);
		return rowSet;
	}

	/**
	 * 准备会计期间，确认期间：periodYear + "年" + periodNumber + "期"
	 * 
	 * @author owen_wen 2011-03-19
	 * @param ds
	 * @return
	 */
	private IRowSet preRowSetForPeriod(IRowSet rowSet) {
		try {
			while (rowSet.next()) {
				int periodYear = rowSet.getInt("period.periodYear");
				int periodNumber = rowSet.getInt("period.periodNumber");
				rowSet.updateString("period", periodYear + "年" + periodNumber + "期");
			}
			rowSet.first();
		} catch (SQLException e) {
			logger.debug(e.getMessage(), e);
			e.printStackTrace();
		}
		return rowSet;
	}
	
	public IRowSet getOtherSubRowSet(R1PrintDataSource ds) throws Exception {
		return getMainBillRowSet(ds);
	}

}
