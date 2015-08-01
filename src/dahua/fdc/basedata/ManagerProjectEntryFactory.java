package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ManagerProjectEntryFactory
{
    private ManagerProjectEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IManagerProjectEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IManagerProjectEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("53DDECF4") ,com.kingdee.eas.fdc.basedata.IManagerProjectEntry.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IManagerProjectEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IManagerProjectEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("53DDECF4") ,com.kingdee.eas.fdc.basedata.IManagerProjectEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IManagerProjectEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IManagerProjectEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("53DDECF4"));
    }
    public static com.kingdee.eas.fdc.basedata.IManagerProjectEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IManagerProjectEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("53DDECF4"));
    }
}