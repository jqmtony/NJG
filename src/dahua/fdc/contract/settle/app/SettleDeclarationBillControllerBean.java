package com.kingdee.eas.fdc.contract.settle.app;

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
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.SystemEnum;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.contract.settle.SettleDeclarationBillInfo;
import com.kingdee.eas.framework.app.CoreBillBaseControllerBean;
import com.kingdee.eas.fdc.contract.settle.SettleDeclarationBillCollection;
import com.kingdee.eas.framework.ObjectBaseCollection;

public class SettleDeclarationBillControllerBean extends AbstractSettleDeclarationBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.settle.app.SettleDeclarationBillControllerBean");
    
    protected void _Audit(Context ctx, IObjectValue model) throws BOSException {//审核
		
		try {
			SettleDeclarationBillInfo info=(SettleDeclarationBillInfo)model;
			
			info = getSettleDeclarationBillInfo(ctx,new ObjectUuidPK(info.getId().toString()));
			
//			info.setAuditData(SysUtil.getAppServerTime(ctx));//时间
//			
//			info.setAuditor(ContextUtil.getCurrentUserInfo(ctx));//审批人
			
			info.setBillState(com.kingdee.eas.fdc.contract.settle.app.BillStateEnum.AUDIT);
			
			_update(ctx, new ObjectUuidPK(info.getId()), info);
		} catch (EASBizException e) {
			e.printStackTrace();
		}
	}
    
    protected void _UnAudit(Context ctx, IObjectValue model) throws BOSException {//反审核
		
		try {
			SettleDeclarationBillInfo info=(SettleDeclarationBillInfo)model;
			
			info = getSettleDeclarationBillInfo(ctx,new ObjectUuidPK(info.getId().toString()));
			
//			info.setAuditData(SysUtil.getAppServerTime(ctx));//时间
//			
//			info.setAuditor(ContextUtil.getCurrentUserInfo(ctx));//审批人
			
			info.setBillState(com.kingdee.eas.fdc.contract.settle.app.BillStateEnum.TEMPORARILYSAVED);
			
			_update(ctx, new ObjectUuidPK(info.getId()), info);
		} catch (EASBizException e) {
			e.printStackTrace();
		}
	}
    
    protected void _InTrial(Context ctx, IObjectValue model) throws BOSException {//送审
		
		try {
			SettleDeclarationBillInfo info=(SettleDeclarationBillInfo)model;
			
			
			info.setState(com.kingdee.eas.fdc.contract.settle.app.TrialStatusEnum.InTrial);//在审
			
			_update(ctx, new ObjectUuidPK(info.getId()), info);
		} catch (EASBizException e) {
			e.printStackTrace();
		}
	
//    	super._InTrial(ctx, model);
    }
    
    protected void _Approved(Context ctx, IObjectValue model) throws BOSException {//审定
    	
    	try {
			SettleDeclarationBillInfo info=(SettleDeclarationBillInfo)model;
			
			
			info.setState(com.kingdee.eas.fdc.contract.settle.app.TrialStatusEnum.Approved);//审定
			
			_update(ctx, new ObjectUuidPK(info.getId()), info);
		} catch (EASBizException e) {
			e.printStackTrace();
		}
    	
//    	super._Approved(ctx, model);
    }
}