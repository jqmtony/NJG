package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BuildTypeFactory
{
    private BuildTypeFactory()
    {
    }
    public static com.kingdee.eas.port.pm.base.IBuildType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IBuildType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D9DECBA7") ,com.kingdee.eas.port.pm.base.IBuildType.class);
    }
    
    public static com.kingdee.eas.port.pm.base.IBuildType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IBuildType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D9DECBA7") ,com.kingdee.eas.port.pm.base.IBuildType.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.base.IBuildType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IBuildType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D9DECBA7"));
    }
    public static com.kingdee.eas.port.pm.base.IBuildType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IBuildType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D9DECBA7"));
    }
}