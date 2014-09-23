package com.kingdee.eas.bpm;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BPMLogFactory
{
    private BPMLogFactory()
    {
    }
    public static com.kingdee.eas.bpm.IBPMLog getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.bpm.IBPMLog)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("44F49766") ,com.kingdee.eas.bpm.IBPMLog.class);
    }
    
    public static com.kingdee.eas.bpm.IBPMLog getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.bpm.IBPMLog)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("44F49766") ,com.kingdee.eas.bpm.IBPMLog.class, objectCtx);
    }
    public static com.kingdee.eas.bpm.IBPMLog getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.bpm.IBPMLog)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("44F49766"));
    }
    public static com.kingdee.eas.bpm.IBPMLog getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.bpm.IBPMLog)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("44F49766"));
    }
}