package com.kingdee.eas.custom.richbase;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ReceTypeFactory
{
    private ReceTypeFactory()
    {
    }
    public static com.kingdee.eas.custom.richbase.IReceType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.richbase.IReceType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0C73A9A5") ,com.kingdee.eas.custom.richbase.IReceType.class);
    }
    
    public static com.kingdee.eas.custom.richbase.IReceType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.richbase.IReceType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0C73A9A5") ,com.kingdee.eas.custom.richbase.IReceType.class, objectCtx);
    }
    public static com.kingdee.eas.custom.richbase.IReceType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.richbase.IReceType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0C73A9A5"));
    }
    public static com.kingdee.eas.custom.richbase.IReceType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.richbase.IReceType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0C73A9A5"));
    }
}