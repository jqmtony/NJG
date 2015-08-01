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
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import java.util.Map;
import com.kingdee.eas.fdc.basedata.ParamValue;
import java.util.ArrayList;
import java.util.List;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import java.util.Set;
import com.kingdee.eas.fdc.basedata.RetValue;



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
            _assignOrgsCostAccount(ctx, orgPK);
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
            _disAssignOrgsCostAccount(ctx, orgPK);
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
            _disAssignSelectOrgsCostAccount(ctx, costAccounts);
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
            List retValue = (List)_assignProjsCostAccount(ctx, projectPK);
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
    protected abstract List _assignProjsCostAccount(Context ctx, IObjectPK projectPK) throws BOSException, EASBizException;

    public void disAssignProjsCostAccount(Context ctx, IObjectPK projectPK) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("b06823ef-010d-1000-e000-000cc0a813bb"), new Object[]{ctx, projectPK});
            invokeServiceBefore(svcCtx);
            _disAssignProjsCostAccount(ctx, projectPK);
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
            _disAssignSelectProjsCostAccount(ctx, costAccounts);
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
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("c39e134f-010d-1000-e000-0006c0a813bb"), new Object[]{ctx, orgPK});
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

    public ArrayList disAssignSelectOrgProject(Context ctx, CostAccountCollection costAccounts) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("e2865ad7-010d-1000-e000-003bc0a813bb"), new Object[]{ctx, costAccounts});
            invokeServiceBefore(svcCtx);
            ArrayList retValue = (ArrayList)_disAssignSelectOrgProject(ctx, costAccounts);
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
    protected abstract ArrayList _disAssignSelectOrgProject(Context ctx, IObjectCollection costAccounts) throws BOSException, EASBizException;

    public void handleEnterDB(Context ctx, CostAccountInfo model, boolean isEnterDB) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("8c8f80ee-0115-1000-e000-0004c0a81296"), new Object[]{ctx, model, new Boolean(isEnterDB)});
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
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("7c81eee0-0118-1000-e000-0001c0a812a7"), new Object[]{ctx, orgPK, nodeIds, new Boolean(isProjectSet)});
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

    public boolean updateStageData(Context ctx, Set idSet, boolean isChecked) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("1e45cd57-b57d-4279-b4c1-35efa4baf436"), new Object[]{ctx, idSet, new Boolean(isChecked)});
            invokeServiceBefore(svcCtx);
            boolean retValue = (boolean)_updateStageData(ctx, idSet, isChecked);
            svcCtx.setMethodReturnValue(new Boolean(retValue));
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract boolean _updateStageData(Context ctx, Set idSet, boolean isChecked) throws BOSException;

    public boolean checkCurrentCostAccountIsDetailsNode(Context ctx, String id, String orgId) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("3ebe9079-c875-4daa-9d41-a541be1434b1"), new Object[]{ctx, id, orgId});
            invokeServiceBefore(svcCtx);
            boolean retValue = (boolean)_checkCurrentCostAccountIsDetailsNode(ctx, id, orgId);
            svcCtx.setMethodReturnValue(new Boolean(retValue));
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract boolean _checkCurrentCostAccountIsDetailsNode(Context ctx, String id, String orgId) throws BOSException;

    public void updateToLeafNode(Context ctx, Set idSet) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("1ff002ec-3046-4f09-89a9-dedbbbb8c654"), new Object[]{ctx, idSet});
            invokeServiceBefore(svcCtx);
            _updateToLeafNode(ctx, idSet);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _updateToLeafNode(Context ctx, Set idSet) throws BOSException;

    public List assignCostAccountBatch(Context ctx, Set orgSet, Set projectSet, Set costAccountList, Map isAddMap) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("ccefbb26-cf77-43e6-9864-d16810dffdca"), new Object[]{ctx, orgSet, projectSet, costAccountList, isAddMap});
            invokeServiceBefore(svcCtx);
            List retValue = (List)_assignCostAccountBatch(ctx, orgSet, projectSet, costAccountList, isAddMap);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract List _assignCostAccountBatch(Context ctx, Set orgSet, Set projectSet, Set costAccountList, Map isAddMap) throws BOSException;

    public List disAssignAccountBatch(Context ctx, Set orgSet, Set projectSet, Map costAccountList) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("5d408879-8f09-45c5-bc58-55ce22b9af71"), new Object[]{ctx, orgSet, projectSet, costAccountList});
            invokeServiceBefore(svcCtx);
            List retValue = (List)_disAssignAccountBatch(ctx, orgSet, projectSet, costAccountList);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract List _disAssignAccountBatch(Context ctx, Set orgSet, Set projectSet, Map costAccountList) throws BOSException;

    public RetValue updateAccountStage(Context ctx, ParamValue paramValue) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("db6b7bca-8465-4068-b8db-86b61ee37875"), new Object[]{ctx, paramValue});
            invokeServiceBefore(svcCtx);
            RetValue retValue = (RetValue)_updateAccountStage(ctx, paramValue);
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
    protected abstract RetValue _updateAccountStage(Context ctx, ParamValue paramValue) throws BOSException, EASBizException;

}