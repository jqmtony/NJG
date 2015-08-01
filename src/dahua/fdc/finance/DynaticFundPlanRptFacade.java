package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.finance.app.*;
import com.kingdee.bos.framework.*;

public class DynaticFundPlanRptFacade extends FinanceRptFacade implements IDynaticFundPlanRptFacade
{
    public DynaticFundPlanRptFacade()
    {
        super();
        registerInterface(IDynaticFundPlanRptFacade.class, this);
    }
    public DynaticFundPlanRptFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IDynaticFundPlanRptFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("20B5786B");
    }
    private DynaticFundPlanRptFacadeController getController() throws BOSException
    {
        return (DynaticFundPlanRptFacadeController)getBizController();
    }
}