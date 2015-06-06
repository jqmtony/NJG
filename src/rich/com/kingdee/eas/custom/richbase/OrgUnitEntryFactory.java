package com.kingdee.eas.custom.richbase;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class OrgUnitEntryFactory
{
    private OrgUnitEntryFactory()
    {
    }
    public static com.kingdee.eas.custom.richbase.IOrgUnitEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.richbase.IOrgUnitEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3AB256C0") ,com.kingdee.eas.custom.richbase.IOrgUnitEntry.class);
    }
    
    public static com.kingdee.eas.custom.richbase.IOrgUnitEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.richbase.IOrgUnitEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3AB256C0") ,com.kingdee.eas.custom.richbase.IOrgUnitEntry.class, objectCtx);
    }
    public static com.kingdee.eas.custom.richbase.IOrgUnitEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.richbase.IOrgUnitEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3AB256C0"));
    }
    public static com.kingdee.eas.custom.richbase.IOrgUnitEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.richbase.IOrgUnitEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3AB256C0"));
    }
}