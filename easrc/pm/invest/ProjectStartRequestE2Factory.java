package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectStartRequestE2Factory
{
    private ProjectStartRequestE2Factory()
    {
    }
    public static com.kingdee.eas.port.pm.invest.IProjectStartRequestE2 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProjectStartRequestE2)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DB0D84D6") ,com.kingdee.eas.port.pm.invest.IProjectStartRequestE2.class);
    }
    
    public static com.kingdee.eas.port.pm.invest.IProjectStartRequestE2 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProjectStartRequestE2)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DB0D84D6") ,com.kingdee.eas.port.pm.invest.IProjectStartRequestE2.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invest.IProjectStartRequestE2 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProjectStartRequestE2)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DB0D84D6"));
    }
    public static com.kingdee.eas.port.pm.invest.IProjectStartRequestE2 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProjectStartRequestE2)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DB0D84D6"));
    }
}