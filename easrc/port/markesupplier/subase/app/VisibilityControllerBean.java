package com.kingdee.eas.port.markesupplier.subase.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.port.markesupplier.subase.VisibilityFactory;
import com.kingdee.eas.port.markesupplier.subase.VisibilityInfo;

public class VisibilityControllerBean extends AbstractVisibilityControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.port.markesupplier.subase.app.VisibilityControllerBean");
    
    protected IObjectPK _addnew(Context ctx, IObjectValue model)throws BOSException, EASBizException {
    	VisibilityInfo info = (VisibilityInfo)model;
    	info.setIsEnable(true);
    	return super._addnew(ctx, model);
    }
    
    protected void _cancel(Context ctx, IObjectPK pk, IObjectValue model)throws BOSException, EASBizException {
    	super._cancel(ctx, pk, model);
    	VisibilityInfo Info = (VisibilityInfo)model;
    	Info.setIsEnable(false);
    	VisibilityFactory.getLocalInstance(ctx).update(new ObjectUuidPK(Info.getId()), Info);
    }
    
    protected void _cancelCancel(Context ctx, IObjectPK pk, IObjectValue model)throws BOSException, EASBizException {
    	super._cancelCancel(ctx, pk, model);
    	VisibilityInfo Info = (VisibilityInfo)model;
    	Info.setIsEnable(true);
    	VisibilityFactory.getLocalInstance(ctx).update(new ObjectUuidPK(Info.getId()), Info);
    }
}