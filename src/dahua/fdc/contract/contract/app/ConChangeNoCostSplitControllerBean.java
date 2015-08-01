package com.kingdee.eas.fdc.contract.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ConChangeNoCostSplitInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.FDCUtils;

public class ConChangeNoCostSplitControllerBean extends AbstractConChangeNoCostSplitControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.ConChangeNoCostSplitControllerBean");

    protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    	IObjectPK pk = super._save(ctx, model);
    	ConChangeNoCostSplitInfo splitBillInfo=null;
		SelectorItemCollection selectorGet = new SelectorItemCollection();
		selectorGet.add("id");
		selectorGet.add("contractChange.curProject.id");
		selectorGet.add("contractChange.period.number");
		selectorGet.add("period.id");
		selectorGet.add("period.number");
		splitBillInfo = super.getConChangeNoCostSplitInfo(ctx, pk, selectorGet);
		String prjID = splitBillInfo.getContractChange().getCurProject().getId().toString();
		PeriodInfo currentPeriod = FDCUtils.getCurrentPeriod(ctx,prjID, true);
		PeriodInfo contractPeriod = splitBillInfo.getContractChange().getPeriod();
		if(currentPeriod!=null&&splitBillInfo.getPeriod()==null){
			if(contractPeriod!=null&&contractPeriod.getNumber()>currentPeriod.getNumber()){
				FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
				builder
						.appendSql("update T_CON_ConChangeNoCostSplit set FPeriodID=? where fid=?");
				builder.addParam(contractPeriod.getId().toString());
				builder.addParam(splitBillInfo.getId().toString());
				builder.executeUpdate();			
			}else{
				FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
				builder
						.appendSql("update T_CON_ConChangeNoCostSplit set FPeriodID=? where fid=?");
				builder.addParam(currentPeriod.getId().toString());
				builder.addParam(splitBillInfo.getId().toString());
				builder.executeUpdate();
			}
		}
		ContractChangeBillFactory.getLocalInstance(ctx).nonCostChangeSplit(splitBillInfo.getContractChange().getId());
		return pk;
    }
}