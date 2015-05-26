package com.kingdee.eas.custom.richbase;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SaleTypeFactory
{
    private SaleTypeFactory()
    {
    }
    public static com.kingdee.eas.custom.richbase.ISaleType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.richbase.ISaleType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B0178717") ,com.kingdee.eas.custom.richbase.ISaleType.class);
    }
    
    public static com.kingdee.eas.custom.richbase.ISaleType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.richbase.ISaleType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B0178717") ,com.kingdee.eas.custom.richbase.ISaleType.class, objectCtx);
    }
    public static com.kingdee.eas.custom.richbase.ISaleType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.richbase.ISaleType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B0178717"));
    }
    public static com.kingdee.eas.custom.richbase.ISaleType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.richbase.ISaleType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B0178717"));
    }
}