package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class REAutoRememberFactory
{
    private REAutoRememberFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IREAutoRemember getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IREAutoRemember)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2088EE81") ,com.kingdee.eas.fdc.basedata.IREAutoRemember.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IREAutoRemember getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IREAutoRemember)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2088EE81") ,com.kingdee.eas.fdc.basedata.IREAutoRemember.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IREAutoRemember getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IREAutoRemember)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2088EE81"));
    }
    public static com.kingdee.eas.fdc.basedata.IREAutoRemember getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IREAutoRemember)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2088EE81"));
    }
}