package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectStartRequestEntryFactory
{
    private ProjectStartRequestEntryFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invest.IProjectStartRequestEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProjectStartRequestEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6A524B69") ,com.kingdee.eas.port.pm.invest.IProjectStartRequestEntry.class);
    }
    
    public static com.kingdee.eas.port.pm.invest.IProjectStartRequestEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProjectStartRequestEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6A524B69") ,com.kingdee.eas.port.pm.invest.IProjectStartRequestEntry.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invest.IProjectStartRequestEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProjectStartRequestEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6A524B69"));
    }
    public static com.kingdee.eas.port.pm.invest.IProjectStartRequestEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProjectStartRequestEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6A524B69"));
    }
}