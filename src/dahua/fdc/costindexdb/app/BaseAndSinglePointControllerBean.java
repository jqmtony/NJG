package com.kingdee.eas.fdc.costindexdb.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.SysContextConstant;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.DbUtil;

public class BaseAndSinglePointControllerBean extends AbstractBaseAndSinglePointControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.costindexdb.app.BaseAndSinglePointControllerBean");
    
    protected void _audit(Context ctx, IObjectValue model) throws BOSException {
    	try {
    		BaseAndSinglePointInfo abinfo = (BaseAndSinglePointInfo)model;
    		abinfo.setPointBillStatus(FDCBillStateEnum.AUDITTED);
    		abinfo.setAuditor((UserInfo)ctx.get(SysContextConstant.USERINFO));
			abinfo.setAudiTime(SysUtil.getAppServerTime(ctx));
			abinfo.setIsLatest(true);
			update(ctx,new ObjectUuidPK(abinfo.getId()),abinfo);
			DbUtil.execute(ctx,"update CT_COS_BaseAndSinglePoint set CFIsLatest='0' where CFPointBillStatus='4AUDITTED' and CFProjectId='"+abinfo.getProjectId()+"' and fid<>'"+abinfo.getId().toString()+"'");
		} catch (EASBizException e) {
			e.printStackTrace();
		}
    }
    
    protected void _unAdudit(Context ctx, IObjectValue model) throws BOSException {
    	
    }
    
    protected void _refix(Context ctx, IObjectValue model) throws BOSException {
    	
    }
    
    protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    	BaseAndSinglePointInfo bpinfo = (BaseAndSinglePointInfo)model;
    	bpinfo.setPointBillStatus(FDCBillStateEnum.SUBMITTED);
    	return super._submit(ctx, model);
    }
}