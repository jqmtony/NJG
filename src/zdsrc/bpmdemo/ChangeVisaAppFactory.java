package com.kingdee.eas.bpmdemo;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ChangeVisaAppFactory
{
    private ChangeVisaAppFactory()
    {
    }
    public static com.kingdee.eas.bpmdemo.IChangeVisaApp getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IChangeVisaApp)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("178064D2") ,com.kingdee.eas.bpmdemo.IChangeVisaApp.class);
    }
    
    public static com.kingdee.eas.bpmdemo.IChangeVisaApp getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IChangeVisaApp)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("178064D2") ,com.kingdee.eas.bpmdemo.IChangeVisaApp.class, objectCtx);
    }
    public static com.kingdee.eas.bpmdemo.IChangeVisaApp getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IChangeVisaApp)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("178064D2"));
    }
    public static com.kingdee.eas.bpmdemo.IChangeVisaApp getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IChangeVisaApp)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("178064D2"));
    }
}