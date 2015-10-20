package com.kingdee.eas.fdc.contract.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;

import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.SettNoCostSplitCollection;
import com.kingdee.eas.fdc.contract.SettlementCostSplitInfo;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.fdc.basedata.FDCNoCostSplitBillCollection;
import com.kingdee.eas.fdc.basedata.app.FDCNoCostSplitBillControllerBean;
import com.kingdee.eas.fdc.contract.SettNoCostSplitInfo;

public class SettNoCostSplitControllerBean extends AbstractSettNoCostSplitControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.SettNoCostSplitControllerBean");
    
    protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    	IObjectPK pk=super._save(ctx, model);
		SettNoCostSplitInfo splitBillInfo=null;
		SelectorItemCollection selectorGet = new SelectorItemCollection();
		selectorGet.add("id");
		selectorGet.add("settlementBill.contractBill.curProject.id");
		selectorGet.add("SettlementBill.period.number");
		selectorGet.add("period.id");
		selectorGet.add("period.number");
		splitBillInfo = super.getSettNoCostSplitInfo(ctx, pk, selectorGet);
		String prjID = splitBillInfo.getSettlementBill().getContractBill().getCurProject().getId().toString();
		PeriodInfo currentPeriod = FDCUtils.getCurrentPeriod(ctx,prjID, true);
		PeriodInfo contractPeriod = splitBillInfo.getSettlementBill().getPeriod();
		if(currentPeriod!=null&&splitBillInfo.getPeriod()==null){
			if(contractPeriod!=null&&contractPeriod.getNumber()>currentPeriod.getNumber()){
				FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
				builder
						.appendSql("update T_CON_SettNoCostSplit set FPeriodID=? where fid=?");
				builder.addParam(contractPeriod.getId().toString());
				builder.addParam(splitBillInfo.getId().toString());
				builder.executeUpdate();			
			}else{
				FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
				builder
						.appendSql("update T_CON_SettNoCostSplit set FPeriodID=? where fid=?");
				builder.addParam(currentPeriod.getId().toString());
				builder.addParam(splitBillInfo.getId().toString());
				builder.executeUpdate();
			}
		}
    	return pk;
    }
}