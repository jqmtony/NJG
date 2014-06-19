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
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import java.util.ArrayList;
import java.util.List;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import java.util.Set;



public abstract class AbstractCostAccountFacadeControllerBean extends AbstractBizControllerBean implements CostAccountFacadeController
{
    protected AbstractCostAccountFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("2ACED1A8");
    }

    public void assignOrgsCostAccount(Context ctx, IObjectPK orgPK) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("b06823ef-010d-1000-e000-0001c0a813bb"), new Object[]{ctx, orgPK});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _assignOrgsCostAccount(ctx, orgPK);
            }
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
    protected abstract void _assignOrgsCostAccount(Context ctx, IObjectPK orgPK) throws BOSException, EASBizException;

    public void disAssignOrgsCostAccount(Context ctx, IObjectPK orgPK) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("b06823ef-010d-1000-e000-0003c0a813bb"), new Object[]{ctx, orgPK});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _disAssignOrgsCostAccount(ctx, orgPK);
            }
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
    protected abstract void _disAssignOrgsCostAccount(Context ctx, IObjectPK orgPK) throws BOSException, EASBizException;

    public void disAssignSelectOrgsCostAccount(Context ctx, CostAccountCollection costAccounts) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("e2865ad7-010d-1000-e000-0039c0a813bb"), new Object[]{ctx, costAccounts});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _disAssignSelectOrgsCostAccount(ctx, costAccounts);
            }
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
    protected abstract void _disAssignSelectOrgsCostAccount(Context ctx, IObjectCollection costAccounts) throws BOSException, EASBizException;

    public List assignProjsCostAccount(Context ctx, IObjectPK projectPK) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("b06823ef-010d-1000-e000-0002c0a813bb"), new Object[]{ctx, projectPK});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            List retValue = (List)_assignProjsCostAccount(ctx, projectPK);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (List)svcCtx.getMethodReturnValue();
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
    protected abstract List _assignProjsCostAccount(Context ctx, IObjectPK projectPK) throws BOSException, EASBizException;

    public void disAssignProjsCostAccount(Context ctx, IObjectPK projectPK) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("b06823ef-010d-1000-e000-000cc0a813bb"), new Object[]{ctx, projectPK});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _disAssignProjsCostAccount(ctx, projectPK);
            }
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
    protected abstract void _disAssignProjsCostAccount(Context ctx, IObjectPK projectPK) throws BOSException, EASBizException;

    public void disAssignSelectProjsCostAccount(Context ctx, CostAccountCollection costAccounts) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("e2865ad7-010d-1000-e000-003ac0a813bb"), new Object[]{ctx, costAccounts});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _disAssignSelectProjsCostAccount(ctx, costAccounts);
            }
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
    protected abstract void _disAssignSelectProjsCostAccount(Context ctx, IObjectCollection costAccounts) throws BOSException, EASBizException;

    public List assignOrgProject(Context ctx, IObjectPK orgPK) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("c39e134f-010d-1000-e000-0005c0a813bb"), new Object[]{ctx, orgPK});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            List retValue = (List)_assignOrgProject(ctx, orgPK);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (List)svcCtx.getMethodReturnValue();
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
    protected abstract List _assignOrgProject(Context ctx, IObjectPK orgPK) throws BOSException, EASBizException;

    public void disAssignOrgProject(Context ctx, IObjectPK orgPK) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("c39e134f-010d-1000-e000-0006c0a813bb"), new Object[]{ctx, orgPK});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _disAssignOrgProject(ctx, orgPK);
            }
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
    protected abstract void _disAssignOrgProject(Context ctx, IObjectPK orgPK) throws BOSException, EASBizException;

    public ArrayList disAssignSelectOrgProject(Context ctx, CostAccountCollection costAccounts) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("e2865ad7-010d-1000-e000-003bc0a813bb"), new Object[]{ctx, costAccounts});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            ArrayList retValue = (ArrayList)_disAssignSelectOrgProject(ctx, costAccounts);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (ArrayList)svcCtx.getMethodReturnValue();
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
    protected abstract ArrayList _disAssignSelectOrgProject(Context ctx, IObjectCollection costAccounts) throws BOSException, EASBizException;

    public void handleEnterDB(Context ctx, CostAccountInfo model, boolean isEnterDB) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("8c8f80ee-0115-1000-e000-0004c0a81296"), new Object[]{ctx, model, new Boolean(isEnterDB)});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _handleEnterDB(ctx, model, isEnterDB);
            }
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
    protected abstract void _handleEnterDB(Context ctx, IObjectValue model, boolean isEnterDB) throws BOSException, EASBizException;

    public List assignToNextLevel(Context ctx, IObjectPK orgPK, Set nodeIds, boolean isProjectSet) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("7c81eee0-0118-1000-e000-0001c0a812a7"), new Object[]{ctx, orgPK, nodeIds, new Boolean(isProjectSet)});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            List retValue = (List)_assignToNextLevel(ctx, orgPK, nodeIds, isProjectSet);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (List)svcCtx.getMethodReturnValue();
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
    protected abstract List _assignToNextLevel(Context ctx, IObjectPK orgPK, Set nodeIds, boolean isProjectSet) throws BOSException, EASBizException;

}