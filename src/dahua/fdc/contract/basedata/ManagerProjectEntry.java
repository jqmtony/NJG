package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.basedata.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBase;

public class ManagerProjectEntry extends CoreBase implements IManagerProjectEntry
{
    public ManagerProjectEntry()
    {
        super();
        registerInterface(IManagerProjectEntry.class, this);
    }
    public ManagerProjectEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IManagerProjectEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("53DDECF4");
    }
    private ManagerProjectEntryController getController() throws BOSException
    {
        return (ManagerProjectEntryController)getBizController();
    }
}