package com.kingdee.eas.fdc.material.app;

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

import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.RetValue;



public abstract class AbstractMaterialRptFacadeControllerBean extends AbstractBizControllerBean implements MaterialRptFacadeController
{
    protected AbstractMaterialRptFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("21F8DE43");
    }

    public RetValue getMaterialContractValues(Context ctx, RetValue param) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("01c7d156-582b-49e9-b061-b2b5c69184a9"), new Object[]{ctx, param});
            invokeServiceBefore(svcCtx);
            RetValue retValue = (RetValue)_getMaterialContractValues(ctx, param);
            svcCtx.setMethodReturnValue(retValue);
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
    protected abstract RetValue _getMaterialContractValues(Context ctx, RetValue param) throws BOSException, EASBizException;

    public RetValue getPartAMaterialValues(Context ctx, RetValue param) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("47e28560-b23d-48f7-b5c4-998327ff4f97"), new Object[]{ctx, param});
            invokeServiceBefore(svcCtx);
            RetValue retValue = (RetValue)_getPartAMaterialValues(ctx, param);
            svcCtx.setMethodReturnValue(retValue);
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
    protected abstract RetValue _getPartAMaterialValues(Context ctx, RetValue param) throws BOSException, EASBizException;

    public RetValue getMaterialContractMonthValues(Context ctx, RetValue param) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("93357cbf-3675-4044-8847-b3d252fff4fc"), new Object[]{ctx, param});
            invokeServiceBefore(svcCtx);
            RetValue retValue = (RetValue)_getMaterialContractMonthValues(ctx, param);
            svcCtx.setMethodReturnValue(retValue);
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
    protected abstract RetValue _getMaterialContractMonthValues(Context ctx, RetValue param) throws BOSException, EASBizException;

}