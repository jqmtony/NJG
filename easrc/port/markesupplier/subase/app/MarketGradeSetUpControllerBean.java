package com.kingdee.eas.port.markesupplier.subase.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.port.markesupplier.subase.MarketGradeSetUpFactory;
import com.kingdee.eas.port.markesupplier.subase.MarketGradeSetUpInfo;

public class MarketGradeSetUpControllerBean extends AbstractMarketGradeSetUpControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.port.markesupplier.subase.app.MarketGradeSetUpControllerBean");
    
    protected IObjectPK _addnew(Context ctx, IObjectValue model)throws BOSException, EASBizException {
    	MarketGradeSetUpInfo info = (MarketGradeSetUpInfo)model;
    	info.setIsEnable(true);
    	return super._addnew(ctx, model);
    }
    
    protected void _cancel(Context ctx, IObjectPK pk, IObjectValue model)throws BOSException, EASBizException {
    	super._cancel(ctx, pk, model);
    	MarketGradeSetUpInfo Info = (MarketGradeSetUpInfo)model;
    	Info.setIsEnable(false);
    	MarketGradeSetUpFactory.getLocalInstance(ctx).update(new ObjectUuidPK(Info.getId()), Info);
    }
    
    protected void _cancelCancel(Context ctx, IObjectPK pk, IObjectValue model)throws BOSException, EASBizException {
    	super._cancelCancel(ctx, pk, model);
    	MarketGradeSetUpInfo Info = (MarketGradeSetUpInfo)model;
    	Info.setIsEnable(true);
    	MarketGradeSetUpFactory.getLocalInstance(ctx).update(new ObjectUuidPK(Info.getId()), Info);
    }
}