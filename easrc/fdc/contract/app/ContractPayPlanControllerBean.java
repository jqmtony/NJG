package com.kingdee.eas.fdc.contract.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractPayPlanFactory;
import com.kingdee.eas.fdc.contract.ContractPayPlanInfo;
import com.kingdee.util.NumericExceptionSubItem;

public class ContractPayPlanControllerBean extends AbstractContractPayPlanControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.ContractPayPlanControllerBean");

	protected boolean isUseName() {
		return false;
	}
	protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		super._audit(ctx, billId);
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("contractBill.id");
		sel.add("version");
		ContractPayPlanInfo info =this.getContractPayPlanInfo(ctx, new ObjectUuidPK(billId), sel);
		String contractId = info.getContractBill().getId().toString();
		int version = info.getVersion();
		version = version-1;
		
		FDCSQLBuilder fdcSB = new FDCSQLBuilder(ctx);
		fdcSB.setBatchType(FDCSQLBuilder.STATEMENT_TYPE);
		
		StringBuffer sql = new StringBuffer();
		sql.append("update T_CON_ContractPayPlan set FIsLatest = 1 where fid = '").append(billId.toString()).append("'");
		fdcSB.addBatch(sql.toString());
		
		sql.setLength(0);
		sql.append("update T_CON_ContractPayPlan set FIsLatest = 0 where FContractBillId = '");
		sql.append(contractId).append("' ");
		sql.append("and FVersion = ").append(version);
		fdcSB.addBatch(sql.toString());
		fdcSB.executeBatch();
    }
    protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		super._unAudit(ctx, billId);
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("contractBill.id");
		sel.add("version");
		sel.add("isLatest");
		ContractPayPlanInfo info =this.getContractPayPlanInfo(ctx, new ObjectUuidPK(billId), sel);
		if(!info.isIsLatest()){
			throw new EASBizException(new NumericExceptionSubItem("100","�����°汾���ܷ�������"));
		}
		
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractBill.id",info.getContractBill().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("version",info.getVersion(),CompareType.GREATER));
		if(ContractPayPlanFactory.getLocalInstance(ctx).exists(filter)){
			throw new EASBizException(new NumericExceptionSubItem("100","�������޶������ܽ��з�����������"));
		}
		
		String contractId = info.getContractBill().getId().toString();
		int version = info.getVersion();
		version = version-1;
		
		FDCSQLBuilder fdcSB = new FDCSQLBuilder(ctx);
		fdcSB.setBatchType(FDCSQLBuilder.STATEMENT_TYPE);
		
		StringBuffer sql = new StringBuffer();
		sql.append("update T_CON_ContractPayPlan set FIsLatest = 0 where fid = '").append(billId.toString()).append("'");
		fdcSB.addBatch(sql.toString());
		
		sql.setLength(0);
		sql.append("update T_CON_ContractPayPlan set FIsLatest = 1 where FContractBillId = '");
		sql.append(contractId).append("' ");
		sql.append("and FVersion = ").append(version);
		fdcSB.addBatch(sql.toString());
		fdcSB.executeBatch();
	}
}