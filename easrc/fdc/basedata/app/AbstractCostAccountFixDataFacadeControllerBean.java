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

import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;



public abstract class AbstractCostAccountFixDataFacadeControllerBean extends AbstractBizControllerBean implements CostAccountFixDataFacadeController
{
    protected AbstractCostAccountFixDataFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("5C70EA0B");
    }

    public void fixOrgSourseID(Context ctx, BOSUuid orgID, List parentOrgList) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("2086257e-e901-40b4-949a-d94d1c2929c3"), new Object[]{ctx, orgID, parentOrgList});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _fixOrgSourseID(ctx, orgID, parentOrgList);
            }
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _fixOrgSourseID(Context ctx, BOSUuid orgID, List parentOrgList) throws BOSException, EASBizException;

    public void fixPrjSourseID(Context ctx, BOSUuid projectID, List parentNodeList) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("22f9a5c2-e210-40b2-b0eb-a86ea964816d"), new Object[]{ctx, projectID, parentNodeList});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _fixPrjSourseID(ctx, projectID, parentNodeList);
            }
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _fixPrjSourseID(Context ctx, BOSUuid projectID, List parentNodeList) throws BOSException, EASBizException;

}