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
import com.kingdee.eas.fdc.basedata.IncomeAccountInfo;
import com.kingdee.bos.dao.IObjectPK;
import java.util.ArrayList;
import com.kingdee.eas.fdc.basedata.IncomeAccountCollection;
import java.util.List;
import java.util.Set;



public abstract class AbstractIncomeAccountFacadeControllerBean extends AbstractBizControllerBean implements IncomeAccountFacadeController
{
    protected AbstractIncomeAccountFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("804F29CC");
    }

    public void assignOrgsIncomeAccount(Context ctx, IObjectPK orgPK) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("ee10dc96-0965-452f-8c47-1dd14ec4245b"), new Object[]{ctx, orgPK});
            invokeServiceBefore(svcCtx);
            _assignOrgsIncomeAccount(ctx, orgPK);
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
    protected abstract void _assignOrgsIncomeAccount(Context ctx, IObjectPK orgPK) throws BOSException, EASBizException;

    public void disAssignOrgsIncomeAccount(Context ctx, IObjectPK orgPK) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("e0872aee-02bf-4195-98d6-348e49536765"), new Object[]{ctx, orgPK});
            invokeServiceBefore(svcCtx);
            _disAssignOrgsIncomeAccount(ctx, orgPK);
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
    protected abstract void _disAssignOrgsIncomeAccount(Context ctx, IObjectPK orgPK) throws BOSException, EASBizException;

    public void disAssignSelectOrgsIncomeAccount(Context ctx, IncomeAccountCollection IncomeAccounts) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("2135c073-0c42-4dac-a44e-4b2e62ad5760"), new Object[]{ctx, IncomeAccounts});
            invokeServiceBefore(svcCtx);
            _disAssignSelectOrgsIncomeAccount(ctx, IncomeAccounts);
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
    protected abstract void _disAssignSelectOrgsIncomeAccount(Context ctx, IObjectCollection IncomeAccounts) throws BOSException, EASBizException;

    public List assignProjsIncomeAccount(Context ctx, IObjectPK projectPK) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("eed32714-8968-427a-ba46-6980373145cc"), new Object[]{ctx, projectPK});
            invokeServiceBefore(svcCtx);
            List retValue = (List)_assignProjsIncomeAccount(ctx, projectPK);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
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
    protected abstract List _assignProjsIncomeAccount(Context ctx, IObjectPK projectPK) throws BOSException, EASBizException;

    public void disAssignProjsIncomeAccount(Context ctx, IObjectPK projectPK) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("eb7b66d3-c659-487a-ba26-b2ba8a6ec6d0"), new Object[]{ctx, projectPK});
            invokeServiceBefore(svcCtx);
            _disAssignProjsIncomeAccount(ctx, projectPK);
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
    protected abstract void _disAssignProjsIncomeAccount(Context ctx, IObjectPK projectPK) throws BOSException, EASBizException;

    public void disAssignSelectProjsIncomeAccount(Context ctx, IncomeAccountCollection IncomeAccounts) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("6bd8b4ee-29ec-4c4e-9755-34140bff86ec"), new Object[]{ctx, IncomeAccounts});
            invokeServiceBefore(svcCtx);
            _disAssignSelectProjsIncomeAccount(ctx, IncomeAccounts);
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
    protected abstract void _disAssignSelectProjsIncomeAccount(Context ctx, IObjectCollection IncomeAccounts) throws BOSException, EASBizException;

    public List assignOrgProject(Context ctx, IObjectPK orgPK) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("33cd7144-e457-422b-b270-d25038a7fee9"), new Object[]{ctx, orgPK});
            invokeServiceBefore(svcCtx);
            List retValue = (List)_assignOrgProject(ctx, orgPK);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
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
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("e5b332cb-9b5d-4241-97f7-ea350b20744b"), new Object[]{ctx, orgPK});
            invokeServiceBefore(svcCtx);
            _disAssignOrgProject(ctx, orgPK);
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

    public ArrayList disAssignSelectOrgProject(Context ctx, IncomeAccountCollection IncomeAccounts) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("cd789a9a-4fa8-4cb9-8246-2cdc9042bb1d"), new Object[]{ctx, IncomeAccounts});
            invokeServiceBefore(svcCtx);
            ArrayList retValue = (ArrayList)_disAssignSelectOrgProject(ctx, IncomeAccounts);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
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
    protected abstract ArrayList _disAssignSelectOrgProject(Context ctx, IObjectCollection IncomeAccounts) throws BOSException, EASBizException;

    public void handleEnterDB(Context ctx, IncomeAccountInfo model, boolean isEnterDB) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d23945c5-c752-41d7-a62d-9ae2720d5ff2"), new Object[]{ctx, model, new Boolean(isEnterDB)});
            invokeServiceBefore(svcCtx);
            _handleEnterDB(ctx, model, isEnterDB);
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
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("cc2b321e-0b27-4eba-b449-104db678c5bb"), new Object[]{ctx, orgPK, nodeIds, new Boolean(isProjectSet)});
            invokeServiceBefore(svcCtx);
            List retValue = (List)_assignToNextLevel(ctx, orgPK, nodeIds, isProjectSet);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
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