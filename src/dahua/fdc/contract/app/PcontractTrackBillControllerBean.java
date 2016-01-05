package com.kingdee.eas.fdc.contract.app;

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
import com.kingdee.eas.fdc.contract.PcontractTrackBillInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.DbUtil;

public class PcontractTrackBillControllerBean extends AbstractPcontractTrackBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.PcontractTrackBillControllerBean");
    
    protected IObjectPK _submit(Context ctx, IObjectValue model)
    		throws BOSException, EASBizException {
    	PcontractTrackBillInfo info = (PcontractTrackBillInfo)model;
    	info.setTrackBillStatus(FDCBillStateEnum.SUBMITTED);
    	return super._submit(ctx, model);
    }
    
    protected void _audit(Context ctx, IObjectValue model) throws BOSException {
    	try {
    		PcontractTrackBillInfo abinfo = (PcontractTrackBillInfo)model;
    		abinfo.setTrackBillStatus(FDCBillStateEnum.AUDITTED);
    		abinfo.setAuditor((UserInfo)ctx.get(SysContextConstant.USERINFO));
			abinfo.setAuditTime(SysUtil.getAppServerTime(ctx));
			abinfo.setIsNew(true);
			update(ctx,new ObjectUuidPK(abinfo.getId()),abinfo);
			//·¢ËÍÔ¤¾¯ÏûÏ¢
//			DHWarnMsgFacadeFactory.getLocalInstance(ctx).programmingGZWarnMsg(abinfo.getId().toString(), 0);
			DbUtil.execute(ctx,"update CT_CON_PcontractTrackBill set CFIsNew=0 where CFTrackBillStatus='4AUDITTED' and CFCurProjectID='"+abinfo.getCurProject().getId().toString()+"' and fid<>'"+abinfo.getId().toString()+"'");
		} catch (EASBizException e) {
			e.printStackTrace();
		}
    }
    
    protected void _unaudit(Context ctx, IObjectValue model)
    		throws BOSException {
		try {
			PcontractTrackBillInfo abinfo = (PcontractTrackBillInfo)model;
			abinfo.setTrackBillStatus(FDCBillStateEnum.SAVED);
			abinfo.setAuditor(null);
			abinfo.setAuditTime(null);
			abinfo.setIsNew(false);
			update(ctx,new ObjectUuidPK(abinfo.getId()),abinfo);
			DbUtil.execute(ctx,"update CT_CON_PcontractTrackBill set CFIsNew=1 where CFTrackBillStatus='4AUDITTED' and CFCurProjectID='"+abinfo.getCurProject().getId().toString()+"' and CFVersion="+(abinfo.getVersion()-1)+"");
		} catch (EASBizException e) {
			e.printStackTrace();
		}
    }
    
    protected void _fix(Context ctx, IObjectValue model) throws BOSException {
    	// TODO Auto-generated method stub
    	super._fix(ctx, model);
    }
}