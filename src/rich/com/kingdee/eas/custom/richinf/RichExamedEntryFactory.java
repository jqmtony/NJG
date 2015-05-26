package com.kingdee.eas.custom.richinf;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RichExamedEntryFactory
{
    private RichExamedEntryFactory()
    {
    }
    public static com.kingdee.eas.custom.richinf.IRichExamedEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichExamedEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0FC258CA") ,com.kingdee.eas.custom.richinf.IRichExamedEntry.class);
    }
    
    public static com.kingdee.eas.custom.richinf.IRichExamedEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichExamedEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0FC258CA") ,com.kingdee.eas.custom.richinf.IRichExamedEntry.class, objectCtx);
    }
    public static com.kingdee.eas.custom.richinf.IRichExamedEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichExamedEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0FC258CA"));
    }
    public static com.kingdee.eas.custom.richinf.IRichExamedEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichExamedEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0FC258CA"));
    }
}