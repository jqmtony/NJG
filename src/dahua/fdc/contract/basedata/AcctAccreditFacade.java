package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.basedata.app.*;
import java.util.Map;
import com.kingdee.bos.framework.*;
import java.util.Set;

public class AcctAccreditFacade extends AbstractBizCtrl implements IAcctAccreditFacade
{
    public AcctAccreditFacade()
    {
        super();
        registerInterface(IAcctAccreditFacade.class, this);
    }
    public AcctAccreditFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IAcctAccreditFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("50C1977A");
    }
    private AcctAccreditFacadeController getController() throws BOSException
    {
        return (AcctAccreditFacadeController)getBizController();
    }
    /**
     *批量保存用户-User defined method
     *@param accreditUsers 授权用户
     */
    public void saveAcctAccreditUsers(AcctAccreditUserCollection accreditUsers) throws BOSException, EASBizException
    {
        try {
            getController().saveAcctAccreditUsers(getContext(), accreditUsers);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取授权科目id-User defined method
     *@param prjId 工程项目ID
     *@return
     */
    public Set getPrjAccreditAcctSet(String prjId) throws BOSException
    {
        try {
            return getController().getPrjAccreditAcctSet(getContext(), prjId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取组织授权科目-User defined method
     *@param orgUnitId 组织ID
     *@return
     */
    public Set getOrgAccreditAcctSet(String orgUnitId) throws BOSException
    {
        try {
            return getController().getOrgAccreditAcctSet(getContext(), orgUnitId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *界面取数-User defined method
     *@param param 参数列表
     *@return
     */
    public Map fetchData(Map param) throws BOSException, EASBizException
    {
        try {
            return getController().fetchData(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}