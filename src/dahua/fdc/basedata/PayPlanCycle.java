package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.basedata.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.CoreBaseCollection;

public class PayPlanCycle extends FDCDataBase implements IPayPlanCycle
{
    public PayPlanCycle()
    {
        super();
        registerInterface(IPayPlanCycle.class, this);
    }
    public PayPlanCycle(Context ctx)
    {
        super(ctx);
        registerInterface(IPayPlanCycle.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("39599767");
    }
    private PayPlanCycleController getController() throws BOSException
    {
        return (PayPlanCycleController)getBizController();
    }
}