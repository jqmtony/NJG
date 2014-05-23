package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectStartRequestFactory
{
    private ProjectStartRequestFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invest.IProjectStartRequest getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProjectStartRequest)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3BA1FA89") ,com.kingdee.eas.port.pm.invest.IProjectStartRequest.class);
    }
    
    public static com.kingdee.eas.port.pm.invest.IProjectStartRequest getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProjectStartRequest)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3BA1FA89") ,com.kingdee.eas.port.pm.invest.IProjectStartRequest.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invest.IProjectStartRequest getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProjectStartRequest)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3BA1FA89"));
    }
    public static com.kingdee.eas.port.pm.invest.IProjectStartRequest getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProjectStartRequest)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3BA1FA89"));
    }
}