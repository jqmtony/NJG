package com.kingdee.eas.fdc.gcftbiaoa.app;

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
import java.math.BigDecimal;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.aimcost.ForecastChangeVisInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.gcftbiaoa.GcftbInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.SystemEnum;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.gcftbiaoa.GcftbCollection;
import com.kingdee.eas.framework.app.CoreBillBaseControllerBean;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.NumericExceptionSubItem;

public class GcftbControllerBean extends AbstractGcftbControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.gcftbiaoa.app.GcftbControllerBean");
    
    
	protected void _audit(Context ctx, IObjectValue model) throws BOSException {
		super._audit(ctx, model);
		GcftbInfo info = (GcftbInfo)model;
		try {
			info.setAuditDate(SysUtil.getAppServerTime(ctx));
			info.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
			info.setStatus(FDCBillStateEnum.AUDITTED);
			
			info.setIsLast(true);
			
			FDCSQLBuilder fdcSB = new FDCSQLBuilder(ctx);
			fdcSB.setBatchType(FDCSQLBuilder.STATEMENT_TYPE);
			
			StringBuffer sql = new StringBuffer();			
			sql.append("update CT_001_Gcftb set CFIsLast = 0 where CFEngineeringProje= '");
			sql.append(info.getEngineeringProject().getId()).append("'");
			fdcSB.addBatch(sql.toString());
			fdcSB.executeBatch();
			
			updatePartial(ctx, info,getSelectorItem());
		}catch (EASBizException e) {
			e.printStackTrace();
		}
	}
	private SelectorItemCollection getSelectorItem(){
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("auditDate");
		sic.add("status");
		sic.add("isLast");
		sic.add("auditor.id");
		return sic;
	}

	protected void _unaudit(Context ctx, IObjectValue model)
			throws BOSException {
		super._unaudit(ctx, model);
		GcftbInfo info = (GcftbInfo)model;
		try {
			info.setAuditDate(null);
			info.setAuditor(null);
			info.setStatus(FDCBillStateEnum.SUBMITTED);
			
			info.setIsLast(false);
			
			FDCSQLBuilder fdcSB = new FDCSQLBuilder(ctx);
			fdcSB.setBatchType(FDCSQLBuilder.STATEMENT_TYPE);
			
			StringBuffer sql = new StringBuffer();
			sql.append("update CT_001_Gcftb set CFIsLast = 1 where CFEngineeringProje = '");
			sql.append(info.getEngineeringProject().getId()).append("' and  CFBbh='"+(Integer.parseInt(info.getBbh())-1)+"'");
			fdcSB.addBatch(sql.toString());
			fdcSB.executeBatch();
			
			updatePartial(ctx, info,getSelectorItem());
		}catch (EASBizException e) {
			e.printStackTrace();
		}

	}
    
	   protected IObjectPK _save(Context ctx, IObjectValue model)throws BOSException, EASBizException {
	    	checkDataStatus(ctx,model,"SAVE");
	    	return super._save(ctx, model);
	    }
	    
	    protected IObjectPK _submit(Context ctx, IObjectValue model)throws BOSException, EASBizException {
	    	checkDataStatus(ctx,model,"SUBMIT");
	    	GcftbInfo info = (GcftbInfo)model;
	    	info.setStatus(FDCBillStateEnum.SUBMITTED);
	    	return super._submit(ctx, model);
	    }
	    
	    protected void _delete(Context ctx, IObjectPK pk) throws BOSException,EASBizException {
	    	checkDataStatus(ctx,getGcftbInfo(ctx, pk),"DELETE");
	    	super._delete(ctx, pk);
	    }
	    
	    private void checkDataStatus(Context ctx,IObjectValue model,String action) throws EASBizException, BOSException{
	    	GcftbInfo info = (GcftbInfo)model;
	    	if(!exists(ctx, new	ObjectUuidPK(info.getId())))
	    		return;
	    	if(info.getStatus().equals(FDCBillStateEnum.AUDITTED)){
				throw new EASBizException(new NumericExceptionSubItem("100","不符合条件的操作！"));
			}
	    }
    
}