package com.kingdee.eas.fdc.basedata.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.invite.InviteProjectFactory;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.util.NumericExceptionSubItem;

public class ValuationModeControllerBean extends AbstractValuationModeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.basedata.app.ValuationModeControllerBean");
    
    protected IObjectPK _save(Context ctx, IObjectValue model)
    		throws BOSException, EASBizException {
    	this.checkNameDup(ctx, (DataBaseInfo) model);
    	return super._save(ctx, model);
    }
    
    protected IObjectPK _submit(Context ctx, IObjectValue model)
    		throws BOSException, EASBizException {
    	this.checkNameDup(ctx, (DataBaseInfo) model);
    	return super._submit(ctx, model);
    }
    
    protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
    		EASBizException {
    	String modId = pk.toString();
		if (InviteProjectFactory.getLocalInstance(ctx).exists("where valuationMode.id  ='" + modId + "'")) {
    		throw new EASBizException(new NumericExceptionSubItem("ERROR", "计价模式被引用，不能删除"));
    	}
    	super._delete(ctx, pk);
    }
}