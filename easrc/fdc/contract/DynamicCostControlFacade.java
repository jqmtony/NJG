package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.report.CommRptBase;
import com.kingdee.eas.fdc.contract.app.*;

public class DynamicCostControlFacade extends CommRptBase implements IDynamicCostControlFacade
{
    public DynamicCostControlFacade()
    {
        super();
        registerInterface(IDynamicCostControlFacade.class, this);
    }
    public DynamicCostControlFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IDynamicCostControlFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("EE4CE446");
    }
    private DynamicCostControlFacadeController getController() throws BOSException
    {
        return (DynamicCostControlFacadeController)getBizController();
    }
    /**
     *Пьее-User defined method
     */
    public void photo() throws BOSException, EASBizException
    {
        try {
            getController().photo(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}