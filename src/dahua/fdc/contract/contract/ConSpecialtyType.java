package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.app.*;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;

public class ConSpecialtyType extends CoreBillEntryBase implements IConSpecialtyType
{
    public ConSpecialtyType()
    {
        super();
        registerInterface(IConSpecialtyType.class, this);
    }
    public ConSpecialtyType(Context ctx)
    {
        super(ctx);
        registerInterface(IConSpecialtyType.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("8563B731");
    }
    private ConSpecialtyTypeController getController() throws BOSException
    {
        return (ConSpecialtyTypeController)getBizController();
    }
}