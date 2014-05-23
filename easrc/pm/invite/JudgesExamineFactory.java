package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class JudgesExamineFactory
{
    private JudgesExamineFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invite.IJudgesExamine getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IJudgesExamine)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("279694DC") ,com.kingdee.eas.port.pm.invite.IJudgesExamine.class);
    }
    
    public static com.kingdee.eas.port.pm.invite.IJudgesExamine getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IJudgesExamine)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("279694DC") ,com.kingdee.eas.port.pm.invite.IJudgesExamine.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invite.IJudgesExamine getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IJudgesExamine)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("279694DC"));
    }
    public static com.kingdee.eas.port.pm.invite.IJudgesExamine getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IJudgesExamine)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("279694DC"));
    }
}