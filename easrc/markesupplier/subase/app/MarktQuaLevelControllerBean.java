package com.kingdee.eas.port.markesupplier.subase.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.port.markesupplier.subase.MarktQuaLevelFactory;
import com.kingdee.eas.port.markesupplier.subase.MarktQuaLevelInfo;

public class MarktQuaLevelControllerBean extends AbstractMarktQuaLevelControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.port.markesupplier.subase.app.MarktQuaLevelControllerBean");
    
    protected IObjectPK _addnew(Context ctx, IObjectValue model)throws BOSException, EASBizException {
    	MarktQuaLevelInfo info = (MarktQuaLevelInfo)model;
    	info.setIsEnable(true);
    	return super._addnew(ctx, model);
    }
    
    protected void _cancel(Context ctx, IObjectPK pk, IObjectValue model)throws BOSException, EASBizException {
    	super._cancel(ctx, pk, model);
    	MarktQuaLevelInfo Info = (MarktQuaLevelInfo)model;
    	Info.setIsEnable(false);
    	MarktQuaLevelFactory.getLocalInstance(ctx).update(new ObjectUuidPK(Info.getId()), Info);
    }
    
    protected void _cancelCancel(Context ctx, IObjectPK pk, IObjectValue model)throws BOSException, EASBizException {
    	super._cancelCancel(ctx, pk, model);
    	MarktQuaLevelInfo Info = (MarktQuaLevelInfo)model;
    	Info.setIsEnable(true);
    	MarktQuaLevelFactory.getLocalInstance(ctx).update(new ObjectUuidPK(Info.getId()), Info);
    }
}