package com.kingdee.eas.fdc.dahuaschedule.schedule.app;

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
import com.kingdee.eas.framework.Result;
import com.kingdee.eas.framework.LineResult;
import com.kingdee.eas.framework.exception.EASMultiException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;

import java.lang.String;



public abstract class AbstractDahuaScheduleFacadeControllerBean extends AbstractBizControllerBean implements DahuaScheduleFacadeController
{
    protected AbstractDahuaScheduleFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("C5579C32");
    }

    public String[] createTempData(Context ctx, String xml) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("3bac21a9-eeb8-4874-a21d-9a3045a33dd2"), new Object[]{ctx, xml});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            String[] retValue = (String[])_createTempData(ctx, xml);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (String[])svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected String[] _createTempData(Context ctx, String xml) throws BOSException
    {    	
        return null;
    }

    public String[] createScheduleBill(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("ccb6cb4a-d8cc-4cb2-9667-c36fe1f43a4b"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            String[] retValue = (String[])_createScheduleBill(ctx);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (String[])svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected String[] _createScheduleBill(Context ctx) throws BOSException
    {    	
        return null;
    }

}