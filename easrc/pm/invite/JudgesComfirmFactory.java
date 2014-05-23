package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class JudgesComfirmFactory
{
    private JudgesComfirmFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invite.IJudgesComfirm getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IJudgesComfirm)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("AF146CBA") ,com.kingdee.eas.port.pm.invite.IJudgesComfirm.class);
    }
    
    public static com.kingdee.eas.port.pm.invite.IJudgesComfirm getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IJudgesComfirm)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("AF146CBA") ,com.kingdee.eas.port.pm.invite.IJudgesComfirm.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invite.IJudgesComfirm getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IJudgesComfirm)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("AF146CBA"));
    }
    public static com.kingdee.eas.port.pm.invite.IJudgesComfirm getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IJudgesComfirm)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("AF146CBA"));
    }
}