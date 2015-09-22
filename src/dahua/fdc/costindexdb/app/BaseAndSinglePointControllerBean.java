package com.kingdee.eas.fdc.costindexdb.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.SysContextConstant;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointInfo;
import com.kingdee.eas.util.SysUtil;

public class BaseAndSinglePointControllerBean extends AbstractBaseAndSinglePointControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.costindexdb.app.BaseAndSinglePointControllerBean");
    
    @Override
    protected void _audit(Context ctx, IObjectValue model) throws BOSException {
    	try {
    		BaseAndSinglePointInfo abinfo = (BaseAndSinglePointInfo)model;
    		abinfo.setPointBillStatus(FDCBillStateEnum.AUDITTED);
    		abinfo.setAuditor((UserInfo)ctx.get(SysContextConstant.USERINFO));
			abinfo.setAudiTime(SysUtil.getAppServerTime(ctx));
			abinfo.setIsLatest(true);
			update(ctx,new ObjectUuidPK(abinfo.getId()),abinfo);
		} catch (EASBizException e) {
			e.printStackTrace();
		}
    }
    
    @Override
    protected void _unAdudit(Context ctx, IObjectValue model) throws BOSException {
    	
    }
    
    @Override
    protected void _refix(Context ctx, IObjectValue model) throws BOSException {
    	
    }
}