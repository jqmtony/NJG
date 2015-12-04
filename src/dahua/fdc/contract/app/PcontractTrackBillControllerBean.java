package com.kingdee.eas.fdc.contract.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.SysContextConstant;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.contract.PcontractTrackBillCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.SystemEnum;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.app.CoreBillBaseControllerBean;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.fdc.contract.PcontractTrackBillInfo;
import com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointInfo;
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