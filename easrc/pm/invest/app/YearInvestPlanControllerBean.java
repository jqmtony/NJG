package com.kingdee.eas.port.pm.invest.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
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
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.xr.XRBillBaseCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.port.pm.invest.IYearInvestPlan;
import com.kingdee.eas.port.pm.invest.ObjectStateEnum;
import com.kingdee.eas.port.pm.invest.YearInvestPlanCollection;
import com.kingdee.eas.port.pm.invest.YearInvestPlanFactory;
import com.kingdee.eas.xr.app.XRBillBaseControllerBean;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.port.pm.invest.YearInvestPlanInfo;
import com.kingdee.util.NumericExceptionSubItem;

public class YearInvestPlanControllerBean extends AbstractYearInvestPlanControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.port.pm.invest.app.YearInvestPlanControllerBean");
    public void _audit(Context ctx, IObjectPK pk) throws BOSException,EASBizException {
    	super._audit(ctx, pk);
    	YearInvestPlanInfo info = getYearInvestPlanInfo(ctx, pk);
    	info.setObjectState(ObjectStateEnum.declared);
    	YearInvestPlanFactory.getLocalInstance(ctx).update(pk, info);
    }
    
    protected void _unAudit(Context ctx, IObjectPK pk) throws BOSException,EASBizException {
    	super._unAudit(ctx, pk);
    	YearInvestPlanInfo info = getYearInvestPlanInfo(ctx, pk);
    	if(info.getObjectState().equals(ObjectStateEnum.throughAudit)||info.getObjectState().equals(ObjectStateEnum.accredit)||info.getObjectState().equals(ObjectStateEnum.approval)||info.getObjectState().equals(ObjectStateEnum.veto)){
    		throw new EASBizException(new NumericExceptionSubItem("100","当前状态下不能反审核！"));
    	}
    	info.setObjectState(ObjectStateEnum.save);
    	YearInvestPlanFactory.getLocalInstance(ctx).update(pk, info);
    }
    
    public void unAudit(Context ctx, IObjectPK[] pks) throws BOSException,EASBizException {
    	super.unAudit(ctx, pks);
    	for (int i = 0; i < pks.length; i++) {
    		YearInvestPlanInfo info = getYearInvestPlanInfo(ctx, pks[i]);
        	info.setObjectState(ObjectStateEnum.save);
        	YearInvestPlanFactory.getLocalInstance(ctx).update(pks[i], info);
		}
    }
}