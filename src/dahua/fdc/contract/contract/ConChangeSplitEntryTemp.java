package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.app.*;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBase;

public class ConChangeSplitEntryTemp extends CoreBase implements IConChangeSplitEntryTemp
{
    public ConChangeSplitEntryTemp()
    {
        super();
        registerInterface(IConChangeSplitEntryTemp.class, this);
    }
    public ConChangeSplitEntryTemp(Context ctx)
    {
        super(ctx);
        registerInterface(IConChangeSplitEntryTemp.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F5F9D143");
    }
    private ConChangeSplitEntryTempController getController() throws BOSException
    {
        return (ConChangeSplitEntryTempController)getBizController();
    }
}