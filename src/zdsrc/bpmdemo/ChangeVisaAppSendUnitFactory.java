package com.kingdee.eas.bpmdemo;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ChangeVisaAppSendUnitFactory
{
    private ChangeVisaAppSendUnitFactory()
    {
    }
    public static com.kingdee.eas.bpmdemo.IChangeVisaAppSendUnit getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IChangeVisaAppSendUnit)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4134F01E") ,com.kingdee.eas.bpmdemo.IChangeVisaAppSendUnit.class);
    }
    
    public static com.kingdee.eas.bpmdemo.IChangeVisaAppSendUnit getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IChangeVisaAppSendUnit)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4134F01E") ,com.kingdee.eas.bpmdemo.IChangeVisaAppSendUnit.class, objectCtx);
    }
    public static com.kingdee.eas.bpmdemo.IChangeVisaAppSendUnit getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IChangeVisaAppSendUnit)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4134F01E"));
    }
    public static com.kingdee.eas.bpmdemo.IChangeVisaAppSendUnit getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IChangeVisaAppSendUnit)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4134F01E"));
    }
}