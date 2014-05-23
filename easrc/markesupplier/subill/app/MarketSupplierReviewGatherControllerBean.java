package com.kingdee.eas.port.markesupplier.subill.app;

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
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.port.markesupplier.subase.IsGradeEnum;
import com.kingdee.eas.port.markesupplier.subase.SupplierState;
import com.kingdee.eas.port.markesupplier.subill.MarketSupplierReviewGatherCollection;
import com.kingdee.eas.port.markesupplier.subill.MarketSupplierReviewGatherFactory;
import com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockFactory;
import com.kingdee.eas.framework.SystemEnum;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.port.markesupplier.subill.MarketSupplierReviewGatherInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.app.CoreBillBaseControllerBean;
import com.kingdee.eas.framework.ObjectBaseCollection;

public class MarketSupplierReviewGatherControllerBean extends AbstractMarketSupplierReviewGatherControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.port.markesupplier.subill.app.MarketSupplierReviewGatherControllerBean");
    
    protected IObjectPK _save(Context ctx, IObjectValue model)throws BOSException, EASBizException {
    	MarketSupplierReviewGatherInfo Info = (MarketSupplierReviewGatherInfo)model;
    	Info.setState(SupplierState.Save);
    	return super._save(ctx, model);
    }
    
    protected IObjectPK _submit(Context ctx, IObjectValue model)throws BOSException, EASBizException {
    	MarketSupplierReviewGatherInfo Info = (MarketSupplierReviewGatherInfo)model;
    	Info.setState(SupplierState.submit);
    	return super._submit(ctx, model);
    }
    
    protected void _audit(Context ctx, IObjectValue model) throws BOSException {
    	super._audit(ctx, model);
    	MarketSupplierReviewGatherInfo Info = (MarketSupplierReviewGatherInfo)model;
    	try {
    		Info.setState(SupplierState.audit);
    		Info.setAuditDate(SysUtil.getAppServerTime(ctx));
    		Info.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
			MarketSupplierReviewGatherFactory.getLocalInstance(ctx).update(new ObjectUuidPK(Info.getId()), Info);
			
			if(Info.getIsPass().equals(IsGradeEnum.ELIGIBILITY))
				MarketSupplierStockFactory.getLocalInstance(ctx).addToSysSupplier(Info.getSupplier());
		} catch (EASBizException e) {
			e.printStackTrace();
		}
    }
    
    protected void _unAudit(Context ctx, IObjectValue model)throws BOSException {
    	super._unAudit(ctx, model);
    	MarketSupplierReviewGatherInfo Info = (MarketSupplierReviewGatherInfo)model;
    	try {
    		Info.setState(SupplierState.submit);
    		Info.setAuditDate(null);
    		Info.setAuditor(null);
			MarketSupplierReviewGatherFactory.getLocalInstance(ctx).update(new ObjectUuidPK(Info.getId()), Info);
		} catch (EASBizException e) {
			e.printStackTrace();
		}
    }
}