package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class JudgesComfirmEntryFactory
{
    private JudgesComfirmEntryFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invite.IJudgesComfirmEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IJudgesComfirmEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2A348FD8") ,com.kingdee.eas.port.pm.invite.IJudgesComfirmEntry.class);
    }
    
    public static com.kingdee.eas.port.pm.invite.IJudgesComfirmEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IJudgesComfirmEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2A348FD8") ,com.kingdee.eas.port.pm.invite.IJudgesComfirmEntry.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invite.IJudgesComfirmEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IJudgesComfirmEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2A348FD8"));
    }
    public static com.kingdee.eas.port.pm.invite.IJudgesComfirmEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IJudgesComfirmEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2A348FD8"));
    }
}