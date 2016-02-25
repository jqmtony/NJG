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

import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.gcftbiaoa.ConfirquantitesInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.NumericExceptionSubItem;

public class ConfirquantitesControllerBean extends AbstractConfirquantitesControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.gcftbiaoa.app.ConfirquantitesControllerBean");
    
    protected void _actionAudit(Context ctx, IObjectValue model)throws BOSException {
    	super._actionAudit(ctx, model);
    	ConfirquantitesInfo info = (ConfirquantitesInfo)model;
    	try {
			info.setAuditDate(SysUtil.getAppServerTime(ctx));
			info.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
			info.setStatus(FDCBillStateEnum.AUDITTED);
			
			info.setIsLast(true);
			
			FDCSQLBuilder fdcSB = new FDCSQLBuilder(ctx);
			fdcSB.setBatchType(FDCSQLBuilder.STATEMENT_TYPE);
			
			StringBuffer sql = new StringBuffer();
			sql.append("update CT_001_Confirquantites set CFIsLast = 0 where CFContractNumberID = '");
			sql.append(info.getContractNumber().getId()).append("'");
			fdcSB.addBatch(sql.toString());
			fdcSB.executeBatch();
			
			updatePartial(ctx, info,getSelectorItem());
		} catch (EASBizException e) {
			e.printStackTrace();
		}
    }
    
    private SelectorItemCollection getSelectorItem(){
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add("auditDate");
    	sic.add("status");
    	sic.add("isLast");
    	sic.add("auditor");
    	return sic;
    }
    
    public void actionUnAudit(Context ctx, ConfirquantitesInfo model)throws BOSException {
    	super.actionUnAudit(ctx, model);
    	ConfirquantitesInfo info = (ConfirquantitesInfo)model;
    	try {
			info.setAuditDate(null);
			info.setAuditor(null);
			info.setStatus(FDCBillStateEnum.SUBMITTED);
			
			info.setIsLast(false);
			
			FDCSQLBuilder fdcSB = new FDCSQLBuilder(ctx);
			fdcSB.setBatchType(FDCSQLBuilder.STATEMENT_TYPE);
			
			StringBuffer sql = new StringBuffer();
			sql.append("update CT_001_Confirquantites set CFIsLast = 1 where CFContractNumberID = '");
			sql.append(info.getContractNumber().getId()).append("' and  CFVersion='"+FDCHelper.subtract(info.getVersion(), BigDecimal.ONE)+"'");
			fdcSB.addBatch(sql.toString());
			fdcSB.executeBatch();
			
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
    	ConfirquantitesInfo info = (ConfirquantitesInfo)model;
    	info.setStatus(FDCBillStateEnum.SUBMITTED);
    	return super._submit(ctx, model);
    }
    
    protected void _delete(Context ctx, IObjectPK pk) throws BOSException,EASBizException {
    	checkDataStatus(ctx,getConfirquantitesInfo(ctx, pk),"DELETE");
    	super._delete(ctx, pk);
    }
    
    private void checkDataStatus(Context ctx,IObjectValue model,String action) throws EASBizException, BOSException{
    	ConfirquantitesInfo info = (ConfirquantitesInfo)model;
    	if(!exists(ctx, new	ObjectUuidPK(info.getId())))
    		return;
    	if(info.getStatus().equals(FDCBillStateEnum.AUDITTED)){
			throw new EASBizException(new NumericExceptionSubItem("100","不符合条件的操作！"));
		}
    }
}