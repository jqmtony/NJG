package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FdcRptBaseFacade;
import com.kingdee.eas.fdc.basedata.IFdcRptBaseFacade;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.finance.app.*;
import com.kingdee.bos.framework.*;

public class FinanceRptFacade extends FdcRptBaseFacade implements IFinanceRptFacade
{
    public FinanceRptFacade()
    {
        super();
        registerInterface(IFinanceRptFacade.class, this);
    }
    public FinanceRptFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IFinanceRptFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("17BD1285");
    }
    private FinanceRptFacadeController getController() throws BOSException
    {
        return (FinanceRptFacadeController)getBizController();
    }
}