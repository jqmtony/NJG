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
import java.util.Map;
import java.util.Set;
import com.kingdee.eas.fdc.basedata.AcctAccreditUserCollection;



public abstract class AbstractAcctAccreditFacadeControllerBean extends AbstractBizControllerBean implements AcctAccreditFacadeController
{
    protected AbstractAcctAccreditFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("50C1977A");
    }

    public void saveAcctAccreditUsers(Context ctx, AcctAccreditUserCollection accreditUsers) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("712b089e-011e-1000-e000-005ac0a810e4"), new Object[]{ctx, accreditUsers});
            invokeServiceBefore(svcCtx);
            _saveAcctAccreditUsers(ctx, accreditUsers);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _saveAcctAccreditUsers(Context ctx, IObjectCollection accreditUsers) throws BOSException, EASBizException;

    public Set getPrjAccreditAcctSet(Context ctx, String prjId) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("712b089e-011e-1000-e000-005bc0a810e4"), new Object[]{ctx, prjId});
            invokeServiceBefore(svcCtx);
            Set retValue = (Set)_getPrjAccreditAcctSet(ctx, prjId);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract Set _getPrjAccreditAcctSet(Context ctx, String prjId) throws BOSException;

    public Set getOrgAccreditAcctSet(Context ctx, String orgUnitId) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("a49619b2-011e-1000-e000-0001c0a810e4"), new Object[]{ctx, orgUnitId});
            invokeServiceBefore(svcCtx);
            Set retValue = (Set)_getOrgAccreditAcctSet(ctx, orgUnitId);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract Set _getOrgAccreditAcctSet(Context ctx, String orgUnitId) throws BOSException;

    public Map fetchData(Context ctx, Map param) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("712b089e-011e-1000-e000-005ec0a810e4"), new Object[]{ctx, param});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_fetchData(ctx, param);
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
    protected abstract Map _fetchData(Context ctx, Map param) throws BOSException, EASBizException;

}