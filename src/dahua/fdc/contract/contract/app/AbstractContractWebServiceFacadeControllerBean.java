package com.kingdee.eas.fdc.contract.app;

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

import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.common.EASBizException;



public abstract class AbstractContractWebServiceFacadeControllerBean extends AbstractBizControllerBean implements ContractWebServiceFacadeController
{
    protected AbstractContractWebServiceFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("E3EE6728");
    }

    public void setContract(Context ctx, ContractBillInfo contractBill) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("ca5b673a-0115-1000-e000-000ec0a81296"), new Object[]{ctx, contractBill});
            invokeServiceBefore(svcCtx);
            _setContract(ctx, contractBill);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _setContract(Context ctx, IObjectValue contractBill) throws BOSException, EASBizException;

    public boolean validate(Context ctx, ContractBillInfo contractBill) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("ca5b673a-0115-1000-e000-0012c0a81296"), new Object[]{ctx, contractBill});
            invokeServiceBefore(svcCtx);
            boolean retValue = (boolean)_validate(ctx, contractBill);
            svcCtx.setMethodReturnValue(new Boolean(retValue));
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract boolean _validate(Context ctx, IObjectValue contractBill) throws BOSException, EASBizException;

}