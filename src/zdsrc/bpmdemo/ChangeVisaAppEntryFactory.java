package com.kingdee.eas.bpmdemo;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ChangeVisaAppEntryFactory
{
    private ChangeVisaAppEntryFactory()
    {
    }
    public static com.kingdee.eas.bpmdemo.IChangeVisaAppEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IChangeVisaAppEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A777E6C0") ,com.kingdee.eas.bpmdemo.IChangeVisaAppEntry.class);
    }
    
    public static com.kingdee.eas.bpmdemo.IChangeVisaAppEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IChangeVisaAppEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A777E6C0") ,com.kingdee.eas.bpmdemo.IChangeVisaAppEntry.class, objectCtx);
    }
    public static com.kingdee.eas.bpmdemo.IChangeVisaAppEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IChangeVisaAppEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A777E6C0"));
    }
    public static com.kingdee.eas.bpmdemo.IChangeVisaAppEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IChangeVisaAppEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A777E6C0"));
    }
}