package com.kingdee.eas.fdc.photomanager.app;

import org.apache.log4j.Logger;
import com.kingdee.bos.*;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;

import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.photomanager.PhotoAuditInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.NumericExceptionSubItem;

public class PhotoAuditControllerBean extends AbstractPhotoAuditControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.photomanager.app.PhotoAuditControllerBean");
    
    protected void _audit(Context ctx, IObjectValue model) throws BOSException {
    	PhotoAuditInfo info = (PhotoAuditInfo)model;
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
    
    protected IObjectPK _submit(Context ctx, IObjectValue model)throws BOSException, EASBizException {
    	checkDataStatus(ctx,model,"SUBMIT");
    	PhotoAuditInfo info = (PhotoAuditInfo)model;
    	info.setStatus(FDCBillStateEnum.SUBMITTED);
    	return super._submit(ctx, model);
    }
    
    
    private void checkDataStatus(Context ctx,IObjectValue model,String action) throws EASBizException, BOSException{
    	PhotoAuditInfo info = (PhotoAuditInfo)model;
    	if(!exists(ctx, new	ObjectUuidPK(info.getId())))
    		return;
    	if(info.getStatus().equals(FDCBillStateEnum.AUDITTED)){  
			throw new EASBizException(new NumericExceptionSubItem("100","不符合条件的操作！"));
		}
    }
    
    protected void _unAudit(Context ctx, IObjectValue model)throws BOSException {
    	PhotoAuditInfo info = (PhotoAuditInfo)model;
    	try {
			info.setAuditDate(null);
			info.setAuditor(null);
			info.setStatus(FDCBillStateEnum.SUBMITTED);
			
			updatePartial(ctx, info,getSelectorItem());
		} catch (EASBizException e) {
			e.printStackTrace();
		}
    }
}