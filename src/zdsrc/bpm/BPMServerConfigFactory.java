package com.kingdee.eas.bpm;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BPMServerConfigFactory
{
    private BPMServerConfigFactory()
    {
    }
    public static com.kingdee.eas.bpm.IBPMServerConfig getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.bpm.IBPMServerConfig)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F3297283") ,com.kingdee.eas.bpm.IBPMServerConfig.class);
    }
    
    public static com.kingdee.eas.bpm.IBPMServerConfig getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.bpm.IBPMServerConfig)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F3297283") ,com.kingdee.eas.bpm.IBPMServerConfig.class, objectCtx);
    }
    public static com.kingdee.eas.bpm.IBPMServerConfig getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.bpm.IBPMServerConfig)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F3297283"));
    }
    public static com.kingdee.eas.bpm.IBPMServerConfig getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.bpm.IBPMServerConfig)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F3297283"));
    }
}