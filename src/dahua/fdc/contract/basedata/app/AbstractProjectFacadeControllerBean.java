package com.kingdee.eas.fdc.basedata.app;

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
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import java.util.Map;
import com.kingdee.eas.fdc.basedata.HisProjectInfo;
import com.kingdee.eas.fdc.basedata.HisProjectCollection;



public abstract class AbstractProjectFacadeControllerBean extends AbstractBizControllerBean implements ProjectFacadeController
{
    protected AbstractProjectFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("7640B361");
    }

    public HisProjectCollection getHisProjectCollection(Context ctx, String curProjectID) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("fabfbf9d-010c-1000-e000-0005c0a813bb"), new Object[]{ctx, curProjectID});
            invokeServiceBefore(svcCtx);
            HisProjectCollection retValue = (HisProjectCollection)_getHisProjectCollection(ctx, curProjectID);
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
    protected abstract IObjectCollection _getHisProjectCollection(Context ctx, String curProjectID) throws BOSException, EASBizException;

    public boolean updateHisProjectInfo(Context ctx, HisProjectInfo hisProjectInfo) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("fabfbf9d-010c-1000-e000-0007c0a813bb"), new Object[]{ctx, hisProjectInfo});
            invokeServiceBefore(svcCtx);
            boolean retValue = (boolean)_updateHisProjectInfo(ctx, hisProjectInfo);
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
    protected abstract boolean _updateHisProjectInfo(Context ctx, IObjectValue hisProjectInfo) throws BOSException, EASBizException;

    public boolean updateCurProjectInfo(Context ctx, CurProjectInfo curProjectInfo) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("fabfbf9d-010c-1000-e000-000ec0a813bb"), new Object[]{ctx, curProjectInfo});
            invokeServiceBefore(svcCtx);
            boolean retValue = (boolean)_updateCurProjectInfo(ctx, curProjectInfo);
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
    protected abstract boolean _updateCurProjectInfo(Context ctx, IObjectValue curProjectInfo) throws BOSException, EASBizException;

    public Map canAddNew(Context ctx, String curProjectId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("c02669dd-1643-4759-ac4f-cad18e913728"), new Object[]{ctx, curProjectId});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_canAddNew(ctx, curProjectId);
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
    protected abstract Map _canAddNew(Context ctx, String curProjectId) throws BOSException, EASBizException;

    public boolean checkBeforeModifyIsDevPrj(Context ctx, CurProjectInfo ObjectValue) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("b66182b4-48b3-4753-99d2-b4ce081e517a"), new Object[]{ctx, ObjectValue});
            invokeServiceBefore(svcCtx);
            boolean retValue = (boolean)_checkBeforeModifyIsDevPrj(ctx, ObjectValue);
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
    protected abstract boolean _checkBeforeModifyIsDevPrj(Context ctx, IObjectValue ObjectValue) throws BOSException, EASBizException;

}