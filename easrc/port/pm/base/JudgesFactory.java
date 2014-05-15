package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class JudgesFactory
{
    private JudgesFactory()
    {
    }
    public static com.kingdee.eas.port.pm.base.IJudges getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IJudges)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("18EA4FBD") ,com.kingdee.eas.port.pm.base.IJudges.class);
    }
    
    public static com.kingdee.eas.port.pm.base.IJudges getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IJudges)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("18EA4FBD") ,com.kingdee.eas.port.pm.base.IJudges.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.base.IJudges getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IJudges)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("18EA4FBD"));
    }
    public static com.kingdee.eas.port.pm.base.IJudges getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IJudges)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("18EA4FBD"));
    }
}