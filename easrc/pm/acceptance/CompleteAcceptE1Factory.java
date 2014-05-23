package com.kingdee.eas.port.pm.acceptance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CompleteAcceptE1Factory
{
    private CompleteAcceptE1Factory()
    {
    }
    public static com.kingdee.eas.port.pm.acceptance.ICompleteAcceptE1 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.acceptance.ICompleteAcceptE1)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("046CA528") ,com.kingdee.eas.port.pm.acceptance.ICompleteAcceptE1.class);
    }
    
    public static com.kingdee.eas.port.pm.acceptance.ICompleteAcceptE1 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.acceptance.ICompleteAcceptE1)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("046CA528") ,com.kingdee.eas.port.pm.acceptance.ICompleteAcceptE1.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.acceptance.ICompleteAcceptE1 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.acceptance.ICompleteAcceptE1)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("046CA528"));
    }
    public static com.kingdee.eas.port.pm.acceptance.ICompleteAcceptE1 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.acceptance.ICompleteAcceptE1)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("046CA528"));
    }
}