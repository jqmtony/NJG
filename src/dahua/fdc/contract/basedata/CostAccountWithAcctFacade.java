package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.basedata.app.*;
import java.util.Map;
import com.kingdee.bos.framework.*;

public class CostAccountWithAcctFacade extends AbstractBizCtrl implements ICostAccountWithAcctFacade
{
    public CostAccountWithAcctFacade()
    {
        super();
        registerInterface(ICostAccountWithAcctFacade.class, this);
    }
    public CostAccountWithAcctFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ICostAccountWithAcctFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("C4029B01");
    }
    private CostAccountWithAcctFacadeController getController() throws BOSException
    {
        return (CostAccountWithAcctFacadeController)getBizController();
    }
    /**
     *更新对应关系表-User defined method
     *@param param 参数
     */
    public void update(Map param) throws BOSException, EASBizException
    {
        try {
            getController().update(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *用于少量更新的场合-User defined method
     *@param param 参数
     */
    public void insert(Map param) throws BOSException, EASBizException
    {
        try {
            getController().insert(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}