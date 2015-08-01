package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CounterclaimTypeFactory
{
    private CounterclaimTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.ICounterclaimType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICounterclaimType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("51EEC60C") ,com.kingdee.eas.fdc.basedata.ICounterclaimType.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.ICounterclaimType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICounterclaimType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("51EEC60C") ,com.kingdee.eas.fdc.basedata.ICounterclaimType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.ICounterclaimType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICounterclaimType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("51EEC60C"));
    }
    public static com.kingdee.eas.fdc.basedata.ICounterclaimType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICounterclaimType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("51EEC60C"));
    }
}