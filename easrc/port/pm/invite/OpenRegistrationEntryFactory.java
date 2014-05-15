package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class OpenRegistrationEntryFactory
{
    private OpenRegistrationEntryFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invite.IOpenRegistrationEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IOpenRegistrationEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3DE52066") ,com.kingdee.eas.port.pm.invite.IOpenRegistrationEntry.class);
    }
    
    public static com.kingdee.eas.port.pm.invite.IOpenRegistrationEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IOpenRegistrationEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3DE52066") ,com.kingdee.eas.port.pm.invite.IOpenRegistrationEntry.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invite.IOpenRegistrationEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IOpenRegistrationEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3DE52066"));
    }
    public static com.kingdee.eas.port.pm.invite.IOpenRegistrationEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IOpenRegistrationEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3DE52066"));
    }
}