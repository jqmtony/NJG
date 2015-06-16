package com.kingdee.eas.custom.richbase;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CustomerSyncLogFactory
{
    private CustomerSyncLogFactory()
    {
    }
    public static com.kingdee.eas.custom.richbase.ICustomerSyncLog getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.richbase.ICustomerSyncLog)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("90108935") ,com.kingdee.eas.custom.richbase.ICustomerSyncLog.class);
    }
    
    public static com.kingdee.eas.custom.richbase.ICustomerSyncLog getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.richbase.ICustomerSyncLog)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("90108935") ,com.kingdee.eas.custom.richbase.ICustomerSyncLog.class, objectCtx);
    }
    public static com.kingdee.eas.custom.richbase.ICustomerSyncLog getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.richbase.ICustomerSyncLog)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("90108935"));
    }
    public static com.kingdee.eas.custom.richbase.ICustomerSyncLog getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.richbase.ICustomerSyncLog)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("90108935"));
    }
}