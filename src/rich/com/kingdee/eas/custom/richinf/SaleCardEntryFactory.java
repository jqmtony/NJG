package com.kingdee.eas.custom.richinf;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SaleCardEntryFactory
{
    private SaleCardEntryFactory()
    {
    }
    public static com.kingdee.eas.custom.richinf.ISaleCardEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.ISaleCardEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6259986D") ,com.kingdee.eas.custom.richinf.ISaleCardEntry.class);
    }
    
    public static com.kingdee.eas.custom.richinf.ISaleCardEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.ISaleCardEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6259986D") ,com.kingdee.eas.custom.richinf.ISaleCardEntry.class, objectCtx);
    }
    public static com.kingdee.eas.custom.richinf.ISaleCardEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.ISaleCardEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6259986D"));
    }
    public static com.kingdee.eas.custom.richinf.ISaleCardEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.ISaleCardEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6259986D"));
    }
}