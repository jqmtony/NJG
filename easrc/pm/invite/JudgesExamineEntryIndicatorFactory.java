package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class JudgesExamineEntryIndicatorFactory
{
    private JudgesExamineEntryIndicatorFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invite.IJudgesExamineEntryIndicator getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IJudgesExamineEntryIndicator)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2635E5B9") ,com.kingdee.eas.port.pm.invite.IJudgesExamineEntryIndicator.class);
    }
    
    public static com.kingdee.eas.port.pm.invite.IJudgesExamineEntryIndicator getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IJudgesExamineEntryIndicator)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2635E5B9") ,com.kingdee.eas.port.pm.invite.IJudgesExamineEntryIndicator.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invite.IJudgesExamineEntryIndicator getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IJudgesExamineEntryIndicator)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2635E5B9"));
    }
    public static com.kingdee.eas.port.pm.invite.IJudgesExamineEntryIndicator getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IJudgesExamineEntryIndicator)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2635E5B9"));
    }
}