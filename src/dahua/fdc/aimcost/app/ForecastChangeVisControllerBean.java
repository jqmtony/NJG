package com.kingdee.eas.fdc.aimcost.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.ForecastChangeVisInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.NumericExceptionSubItem;

public class ForecastChangeVisControllerBean extends AbstractForecastChangeVisControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.aimcost.app.ForecastChangeVisControllerBean");
    
    protected void _actionAudit(Context ctx, IObjectValue model)throws BOSException {
    	super._actionAudit(ctx, model);
    	ForecastChangeVisInfo info = (ForecastChangeVisInfo)model;
    	try {
			info.setAuditDate(SysUtil.getAppServerTime(ctx));
			info.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
			info.setStatus(FDCBillStateEnum.AUDITTED);
			updatePartial(ctx, info,getSelectorItem());
		} catch (EASBizException e) {
			e.printStackTrace();
		}
    }
    
    private SelectorItemCollection getSelectorItem(){
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add("auditDate");
    	sic.add("status");
    	sic.add("auditor.id");
    	return sic;
    }
    
    public void actionUnAudit(Context ctx, ForecastChangeVisInfo model)throws BOSException {
    	super.actionUnAudit(ctx, model);
    	ForecastChangeVisInfo info = (ForecastChangeVisInfo)model;
    	try {
			info.setAuditDate(null);
			info.setAuditor(null);
			info.setStatus(FDCBillStateEnum.SUBMITTED);
			updatePartial(ctx, info,getSelectorItem());
		} catch (EASBizException e) {
			e.printStackTrace();
		}
    }
    
    protected IObjectPK _save(Context ctx, IObjectValue model)throws BOSException, EASBizException {
    	checkDataStatus(ctx,model,"SAVE");
    	return super._save(ctx, model);
    }
    
    protected IObjectPK _submit(Context ctx, IObjectValue model)throws BOSException, EASBizException {
    	checkDataStatus(ctx,model,"SUBMIT");
    	ForecastChangeVisInfo info = (ForecastChangeVisInfo)model;
    	info.setStatus(FDCBillStateEnum.SUBMITTED);
    	return super._submit(ctx, model);
    }
    
    protected void _delete(Context ctx, IObjectPK pk) throws BOSException,EASBizException {
    	checkDataStatus(ctx,getForecastChangeVisInfo(ctx, pk),"DELETE");
    	super._delete(ctx, pk);
    }
    
    private void checkDataStatus(Context ctx,IObjectValue model,String action) throws EASBizException, BOSException{
    	ForecastChangeVisInfo info = (ForecastChangeVisInfo)model;
    	if(!exists(ctx, new	ObjectUuidPK(info.getId())))
    		return;
    	if(info.getStatus().equals(FDCBillStateEnum.AUDITTED)){
			throw new EASBizException(new NumericExceptionSubItem("100","不符合条件的操作！"));
		}
    }
}