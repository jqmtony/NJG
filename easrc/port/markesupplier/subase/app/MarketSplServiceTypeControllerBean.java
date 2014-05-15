package com.kingdee.eas.port.markesupplier.subase.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.port.markesupplier.subase.MarketSplServiceTypeFactory;
import com.kingdee.eas.port.markesupplier.subase.MarketSplServiceTypeInfo;

public class MarketSplServiceTypeControllerBean extends AbstractMarketSplServiceTypeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.port.markesupplier.subase.app.MarketSplServiceTypeControllerBean");
    
    protected IObjectPK _addnew(Context ctx, IObjectValue model)throws BOSException, EASBizException {
    	MarketSplServiceTypeInfo info = (MarketSplServiceTypeInfo)model;
    	info.setIsEnable(true);
    	return super._addnew(ctx, model);
    }
    
    protected void _cancel(Context ctx, IObjectPK pk, IObjectValue model)throws BOSException, EASBizException {
    	super._cancel(ctx, pk, model);
    	MarketSplServiceTypeInfo Info = (MarketSplServiceTypeInfo)model;
    	Info.setIsEnable(false);
    	MarketSplServiceTypeFactory.getLocalInstance(ctx).update(new ObjectUuidPK(Info.getId()), Info);
    }
    
    protected void _cancelCancel(Context ctx, IObjectPK pk, IObjectValue model)throws BOSException, EASBizException {
    	super._cancelCancel(ctx, pk, model);
    	MarketSplServiceTypeInfo Info = (MarketSplServiceTypeInfo)model;
    	Info.setIsEnable(true);
    	MarketSplServiceTypeFactory.getLocalInstance(ctx).update(new ObjectUuidPK(Info.getId()), Info);
    }
}