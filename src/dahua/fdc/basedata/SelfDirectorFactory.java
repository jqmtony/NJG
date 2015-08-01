package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SelfDirectorFactory
{
    private SelfDirectorFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.ISelfDirector getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ISelfDirector)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6ED3B6EA") ,com.kingdee.eas.fdc.basedata.ISelfDirector.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.ISelfDirector getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ISelfDirector)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6ED3B6EA") ,com.kingdee.eas.fdc.basedata.ISelfDirector.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.ISelfDirector getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ISelfDirector)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6ED3B6EA"));
    }
    public static com.kingdee.eas.fdc.basedata.ISelfDirector getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ISelfDirector)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6ED3B6EA"));
    }
}