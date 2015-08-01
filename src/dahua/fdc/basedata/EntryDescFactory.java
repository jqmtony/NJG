package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EntryDescFactory
{
    private EntryDescFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IEntryDesc getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IEntryDesc)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("796F8B71") ,com.kingdee.eas.fdc.basedata.IEntryDesc.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IEntryDesc getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IEntryDesc)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("796F8B71") ,com.kingdee.eas.fdc.basedata.IEntryDesc.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IEntryDesc getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IEntryDesc)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("796F8B71"));
    }
    public static com.kingdee.eas.fdc.basedata.IEntryDesc getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IEntryDesc)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("796F8B71"));
    }
}