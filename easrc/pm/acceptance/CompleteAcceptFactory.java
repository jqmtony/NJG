package com.kingdee.eas.port.pm.acceptance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CompleteAcceptFactory
{
    private CompleteAcceptFactory()
    {
    }
    public static com.kingdee.eas.port.pm.acceptance.ICompleteAccept getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.acceptance.ICompleteAccept)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B0E3139C") ,com.kingdee.eas.port.pm.acceptance.ICompleteAccept.class);
    }
    
    public static com.kingdee.eas.port.pm.acceptance.ICompleteAccept getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.acceptance.ICompleteAccept)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B0E3139C") ,com.kingdee.eas.port.pm.acceptance.ICompleteAccept.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.acceptance.ICompleteAccept getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.acceptance.ICompleteAccept)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B0E3139C"));
    }
    public static com.kingdee.eas.port.pm.acceptance.ICompleteAccept getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.acceptance.ICompleteAccept)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B0E3139C"));
    }
}