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

public class SpecialtyTypeEntry extends CoreBillEntryBase implements ISpecialtyTypeEntry
{
    public SpecialtyTypeEntry()
    {
        super();
        registerInterface(ISpecialtyTypeEntry.class, this);
    }
    public SpecialtyTypeEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ISpecialtyTypeEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("0E915F75");
    }
    private SpecialtyTypeEntryController getController() throws BOSException
    {
        return (SpecialtyTypeEntryController)getBizController();
    }
}