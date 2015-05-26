package com.kingdee.eas.custom.richfacade;

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
import java.util.Date;



public abstract class AbstractEASRichFacadeControllerBean extends AbstractBizControllerBean implements EASRichFacadeController
{
    protected AbstractEASRichFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("452A57AD");
    }

    public String[] saveTempData(Context ctx, String String) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("86d1255d-b20e-41bd-abb4-4582d981a6ad"), new Object[]{ctx, String});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            String[] retValue = (String[])_saveTempData(ctx, String);
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
    protected String[] _saveTempData(Context ctx, String String) throws BOSException
    {    	
        return null;
    }

    public String[] saveExamBill(Context ctx, Date date, String String) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("03b69072-3b9b-4771-9661-3ac7065ec749"), new Object[]{ctx, date, String});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            String[] retValue = (String[])_saveExamBill(ctx, date, String);
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
    protected String[] _saveExamBill(Context ctx, Date date, String String) throws BOSException
    {    	
        return null;
    }

}