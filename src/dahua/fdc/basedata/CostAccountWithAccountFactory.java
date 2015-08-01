package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CostAccountWithAccountFactory
{
    private CostAccountWithAccountFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.ICostAccountWithAccount getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICostAccountWithAccount)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("256C7E39") ,com.kingdee.eas.fdc.basedata.ICostAccountWithAccount.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.ICostAccountWithAccount getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICostAccountWithAccount)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("256C7E39") ,com.kingdee.eas.fdc.basedata.ICostAccountWithAccount.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.ICostAccountWithAccount getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICostAccountWithAccount)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("256C7E39"));
    }
    public static com.kingdee.eas.fdc.basedata.ICostAccountWithAccount getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICostAccountWithAccount)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("256C7E39"));
    }
}