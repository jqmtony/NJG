package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.basedata.app.*;
import java.util.Map;
import com.kingdee.bos.framework.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CostAccountFacade extends AbstractBizCtrl implements ICostAccountFacade
{
    public CostAccountFacade()
    {
        super();
        registerInterface(ICostAccountFacade.class, this);
    }
    public CostAccountFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ICostAccountFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("2ACED1A8");
    }
    private CostAccountFacadeController getController() throws BOSException
    {
        return (CostAccountFacadeController)getBizController();
    }
    /**
     *分配组织节点下的成本科目-User defined method
     *@param orgPK 组织pk
     */
    public void assignOrgsCostAccount(IObjectPK orgPK) throws BOSException, EASBizException
    {
        try {
            getController().assignOrgsCostAccount(getContext(), orgPK);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反分配-User defined method
     *@param orgPK orgPK
     */
    public void disAssignOrgsCostAccount(IObjectPK orgPK) throws BOSException, EASBizException
    {
        try {
            getController().disAssignOrgsCostAccount(getContext(), orgPK);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *disAssignSelectOrgsCostAccount-User defined method
     *@param costAccounts costAccounts
     */
    public void disAssignSelectOrgsCostAccount(CostAccountCollection costAccounts) throws BOSException, EASBizException
    {
        try {
            getController().disAssignSelectOrgsCostAccount(getContext(), costAccounts);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *分配工程项目节点下的成本科目-User defined method
     *@param projectPK 工程项目pk
     *@return
     */
    public List assignProjsCostAccount(IObjectPK projectPK) throws BOSException, EASBizException
    {
        try {
            return getController().assignProjsCostAccount(getContext(), projectPK);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反分配-User defined method
     *@param projectPK projectPK
     */
    public void disAssignProjsCostAccount(IObjectPK projectPK) throws BOSException, EASBizException
    {
        try {
            getController().disAssignProjsCostAccount(getContext(), projectPK);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *disAssignSelectProjsCostAccount-User defined method
     *@param costAccounts costAccounts
     */
    public void disAssignSelectProjsCostAccount(CostAccountCollection costAccounts) throws BOSException, EASBizException
    {
        try {
            getController().disAssignSelectProjsCostAccount(getContext(), costAccounts);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *分配(从组织到工程项目)-User defined method
     *@param orgPK orgPK
     *@return
     */
    public List assignOrgProject(IObjectPK orgPK) throws BOSException, EASBizException
    {
        try {
            return getController().assignOrgProject(getContext(), orgPK);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反分配(从组织收回工程项目相关的成本科目)-User defined method
     *@param orgPK orgPK
     */
    public void disAssignOrgProject(IObjectPK orgPK) throws BOSException, EASBizException
    {
        try {
            getController().disAssignOrgProject(getContext(), orgPK);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *disAssignSelectOrgProject-User defined method
     *@param costAccounts costAccounts
     *@return
     */
    public ArrayList disAssignSelectOrgProject(CostAccountCollection costAccounts) throws BOSException, EASBizException
    {
        try {
            return getController().disAssignSelectOrgProject(getContext(), costAccounts);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *处理是否进入成本数据库-User defined method
     *@param model 成本科目
     *@param isEnterDB isEnterDB
     */
    public void handleEnterDB(CostAccountInfo model, boolean isEnterDB) throws BOSException, EASBizException
    {
        try {
            getController().handleEnterDB(getContext(), model, isEnterDB);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *分配到下一级-User defined method
     *@param orgPK orgPK
     *@param nodeIds nodeIds
     *@param isProjectSet isProjectSet
     *@return
     */
    public List assignToNextLevel(IObjectPK orgPK, Set nodeIds, boolean isProjectSet) throws BOSException, EASBizException
    {
        try {
            return getController().assignToNextLevel(getContext(), orgPK, nodeIds, isProjectSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *批量更新测算阶段数据-User defined method
     *@param idSet idSet
     *@param isChecked isChecked
     *@return
     */
    public boolean updateStageData(Set idSet, boolean isChecked) throws BOSException
    {
        try {
            return getController().updateStageData(getContext(), idSet, isChecked);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *判断明细节点-User defined method
     *@param id id
     *@param orgId orgId
     *@return
     */
    public boolean checkCurrentCostAccountIsDetailsNode(String id, String orgId) throws BOSException
    {
        try {
            return getController().checkCurrentCostAccountIsDetailsNode(getContext(), id, orgId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *updateToLeafNode-User defined method
     *@param idSet idSet
     */
    public void updateToLeafNode(Set idSet) throws BOSException
    {
        try {
            getController().updateToLeafNode(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *assignCostAccountBatch-User defined method
     *@param orgSet orgSet
     *@param projectSet projectSet
     *@param costAccountList costAccountList
     *@param isAddMap isAddMap
     *@return
     */
    public List assignCostAccountBatch(Set orgSet, Set projectSet, Set costAccountList, Map isAddMap) throws BOSException
    {
        try {
            return getController().assignCostAccountBatch(getContext(), orgSet, projectSet, costAccountList, isAddMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *disAssignAccountBatch-User defined method
     *@param orgSet orgSet
     *@param projectSet projectSet
     *@param costAccountList costAccountList
     *@return
     */
    public List disAssignAccountBatch(Set orgSet, Set projectSet, Map costAccountList) throws BOSException
    {
        try {
            return getController().disAssignAccountBatch(getContext(), orgSet, projectSet, costAccountList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *更新科目测算阶段值-User defined method
     *@param paramValue 参数
     *@return
     */
    public RetValue updateAccountStage(ParamValue paramValue) throws BOSException, EASBizException
    {
        try {
            return getController().updateAccountStage(getContext(), paramValue);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}