package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.basedata.app.*;
import com.kingdee.bos.framework.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class IncomeAccountFacade extends AbstractBizCtrl implements IIncomeAccountFacade
{
    public IncomeAccountFacade()
    {
        super();
        registerInterface(IIncomeAccountFacade.class, this);
    }
    public IncomeAccountFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IIncomeAccountFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("804F29CC");
    }
    private IncomeAccountFacadeController getController() throws BOSException
    {
        return (IncomeAccountFacadeController)getBizController();
    }
    /**
     *分配组织节点下的收入科目-User defined method
     *@param orgPK 组织pk
     */
    public void assignOrgsIncomeAccount(IObjectPK orgPK) throws BOSException, EASBizException
    {
        try {
            getController().assignOrgsIncomeAccount(getContext(), orgPK);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反分配-User defined method
     *@param orgPK orgPK
     */
    public void disAssignOrgsIncomeAccount(IObjectPK orgPK) throws BOSException, EASBizException
    {
        try {
            getController().disAssignOrgsIncomeAccount(getContext(), orgPK);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *disAssignSelectOrgsIncomeAccount-User defined method
     *@param IncomeAccounts IncomeAccounts
     */
    public void disAssignSelectOrgsIncomeAccount(IncomeAccountCollection IncomeAccounts) throws BOSException, EASBizException
    {
        try {
            getController().disAssignSelectOrgsIncomeAccount(getContext(), IncomeAccounts);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *分配工程项目节点下的收入科目-User defined method
     *@param projectPK 工程项目pk
     *@return
     */
    public List assignProjsIncomeAccount(IObjectPK projectPK) throws BOSException, EASBizException
    {
        try {
            return getController().assignProjsIncomeAccount(getContext(), projectPK);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反分配-User defined method
     *@param projectPK projectPK
     */
    public void disAssignProjsIncomeAccount(IObjectPK projectPK) throws BOSException, EASBizException
    {
        try {
            getController().disAssignProjsIncomeAccount(getContext(), projectPK);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *disAssignSelectProjsIncomeAccount-User defined method
     *@param IncomeAccounts IncomeAccounts
     */
    public void disAssignSelectProjsIncomeAccount(IncomeAccountCollection IncomeAccounts) throws BOSException, EASBizException
    {
        try {
            getController().disAssignSelectProjsIncomeAccount(getContext(), IncomeAccounts);
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
     *反分配(从组织收回工程项目相关的收入科目)-User defined method
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
     *@param IncomeAccounts IncomeAccounts
     *@return
     */
    public ArrayList disAssignSelectOrgProject(IncomeAccountCollection IncomeAccounts) throws BOSException, EASBizException
    {
        try {
            return getController().disAssignSelectOrgProject(getContext(), IncomeAccounts);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *处理是否进入成本数据库-User defined method
     *@param model 收入科目
     *@param isEnterDB isEnterDB
     */
    public void handleEnterDB(IncomeAccountInfo model, boolean isEnterDB) throws BOSException, EASBizException
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
}