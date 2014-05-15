package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class OpenRegistrationFactory
{
    private OpenRegistrationFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invite.IOpenRegistration getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IOpenRegistration)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D4333B6C") ,com.kingdee.eas.port.pm.invite.IOpenRegistration.class);
    }
    
    public static com.kingdee.eas.port.pm.invite.IOpenRegistration getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IOpenRegistration)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D4333B6C") ,com.kingdee.eas.port.pm.invite.IOpenRegistration.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invite.IOpenRegistration getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IOpenRegistration)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D4333B6C"));
    }
    public static com.kingdee.eas.port.pm.invite.IOpenRegistration getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IOpenRegistration)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D4333B6C"));
    }
}