package com.kingdee.eas.port.markesupplier.subase.app;

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
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.port.markesupplier.subase.MarketSupplierAttachListFactory;
import com.kingdee.eas.port.markesupplier.subase.MarketSupplierFileTypFactory;
import com.kingdee.eas.port.markesupplier.subase.MarketSupplierAttachListInfo;

public class MarketSupplierAttachListControllerBean extends AbstractMarketSupplierAttachListControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.port.markesupplier.subase.app.MarketSupplierAttachListControllerBean");
    protected IObjectPK _addnew(Context ctx, IObjectValue model)throws BOSException, EASBizException {
    	MarketSupplierAttachListInfo info = (MarketSupplierAttachListInfo)model;
    	info.setIsEnable(true);
    	return super._addnew(ctx, model);
    }
    
    protected void _cancel(Context ctx, IObjectPK pk, IObjectValue model)throws BOSException, EASBizException {
    	super._cancel(ctx, pk, model);
    	MarketSupplierAttachListInfo Info = (MarketSupplierAttachListInfo)model;
    	Info.setIsEnable(false);
    	MarketSupplierAttachListFactory.getLocalInstance(ctx).update(new ObjectUuidPK(Info.getId()), Info);
    }
    
    protected void _cancelCancel(Context ctx, IObjectPK pk, IObjectValue model)throws BOSException, EASBizException {
    	super._cancelCancel(ctx, pk, model);
    	MarketSupplierAttachListInfo Info = (MarketSupplierAttachListInfo)model;
    	Info.setIsEnable(true);
    	MarketSupplierAttachListFactory.getLocalInstance(ctx).update(new ObjectUuidPK(Info.getId()), Info);
    }
}