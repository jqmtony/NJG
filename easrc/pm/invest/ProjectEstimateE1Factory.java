package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectEstimateE1Factory
{
    private ProjectEstimateE1Factory()
    {
    }
    public static com.kingdee.eas.port.pm.invest.IProjectEstimateE1 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProjectEstimateE1)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1E3C8970") ,com.kingdee.eas.port.pm.invest.IProjectEstimateE1.class);
    }
    
    public static com.kingdee.eas.port.pm.invest.IProjectEstimateE1 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProjectEstimateE1)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1E3C8970") ,com.kingdee.eas.port.pm.invest.IProjectEstimateE1.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invest.IProjectEstimateE1 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProjectEstimateE1)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1E3C8970"));
    }
    public static com.kingdee.eas.port.pm.invest.IProjectEstimateE1 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProjectEstimateE1)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1E3C8970"));
    }
}