package com.kingdee.eas.bpmdemo;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ChangeVisaAppAssEntryFactory
{
    private ChangeVisaAppAssEntryFactory()
    {
    }
    public static com.kingdee.eas.bpmdemo.IChangeVisaAppAssEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IChangeVisaAppAssEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DED5DD63") ,com.kingdee.eas.bpmdemo.IChangeVisaAppAssEntry.class);
    }
    
    public static com.kingdee.eas.bpmdemo.IChangeVisaAppAssEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IChangeVisaAppAssEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DED5DD63") ,com.kingdee.eas.bpmdemo.IChangeVisaAppAssEntry.class, objectCtx);
    }
    public static com.kingdee.eas.bpmdemo.IChangeVisaAppAssEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IChangeVisaAppAssEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DED5DD63"));
    }
    public static com.kingdee.eas.bpmdemo.IChangeVisaAppAssEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IChangeVisaAppAssEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DED5DD63"));
    }
}