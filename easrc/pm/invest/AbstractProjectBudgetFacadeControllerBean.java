package com.kingdee.eas.port.pm.invest;

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



public abstract class AbstractProjectBudgetFacadeControllerBean extends AbstractBizControllerBean implements ProjectBudgetFacadeController
{
    protected AbstractProjectBudgetFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("1FC99E48");
    }

    public String[] subBudgetAmount(Context ctx, String projectNumber, String year, String budgetNumber, String amount, String stag, boolean isBack, String backAmount) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("3bcea400-bde7-4119-90a5-16daef9bfd9d"), new Object[]{ctx, projectNumber, year, budgetNumber, amount, stag, new Boolean(isBack), backAmount});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            String[] retValue = (String[])_subBudgetAmount(ctx, projectNumber, year, budgetNumber, amount, stag, isBack, backAmount);
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
    protected String[] _subBudgetAmount(Context ctx, String projectNumber, String year, String budgetNumber, String amount, String stag, boolean isBack, String backAmount) throws BOSException
    {    	
        return null;
    }

    public String[] backBudgetAmount(Context ctx, String projectNumber, String year, String budgetNumber, String amount, String stag, boolean isBack, String backAmount) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("e46d2aee-6c41-4a0e-a159-997d0107f6fc"), new Object[]{ctx, projectNumber, year, budgetNumber, amount, stag, new Boolean(isBack), backAmount});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            String[] retValue = (String[])_backBudgetAmount(ctx, projectNumber, year, budgetNumber, amount, stag, isBack, backAmount);
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
    protected String[] _backBudgetAmount(Context ctx, String projectNumber, String year, String budgetNumber, String amount, String stag, boolean isBack, String backAmount) throws BOSException
    {    	
        return null;
    }

}