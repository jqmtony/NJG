package com.kingdee.eas.fdc.aimcost;

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
import java.util.Map;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.aimcost.app.*;

public class AimDynCostGetFacade extends AbstractBizCtrl implements IAimDynCostGetFacade
{
    public AimDynCostGetFacade()
    {
        super();
        registerInterface(IAimDynCostGetFacade.class, this);
    }
    public AimDynCostGetFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IAimDynCostGetFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("08BDF4F8");
    }
    private AimDynCostGetFacadeController getController() throws BOSException
    {
        return (AimDynCostGetFacadeController)getBizController();
    }
    /**
     *getAimDynCost-User defined method
     *@param proId ÏîÄ¿id
     *@return
     */
    public Map getAimDynCost(String proId) throws BOSException, EASBizException
    {
        try {
            return getController().getAimDynCost(getContext(), proId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}