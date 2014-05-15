package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectEstimateFactory
{
    private ProjectEstimateFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invest.IProjectEstimate getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProjectEstimate)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7C6F69E4") ,com.kingdee.eas.port.pm.invest.IProjectEstimate.class);
    }
    
    public static com.kingdee.eas.port.pm.invest.IProjectEstimate getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProjectEstimate)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7C6F69E4") ,com.kingdee.eas.port.pm.invest.IProjectEstimate.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invest.IProjectEstimate getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProjectEstimate)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7C6F69E4"));
    }
    public static com.kingdee.eas.port.pm.invest.IProjectEstimate getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProjectEstimate)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7C6F69E4"));
    }
}