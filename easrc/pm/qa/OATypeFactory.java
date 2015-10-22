package com.kingdee.eas.port.pm.qa;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class OATypeFactory
{
    private OATypeFactory()
    {
    }
    public static com.kingdee.eas.port.pm.qa.IOAType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.qa.IOAType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("398C90AE") ,com.kingdee.eas.port.pm.qa.IOAType.class);
    }
    
    public static com.kingdee.eas.port.pm.qa.IOAType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.qa.IOAType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("398C90AE") ,com.kingdee.eas.port.pm.qa.IOAType.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.qa.IOAType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.qa.IOAType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("398C90AE"));
    }
    public static com.kingdee.eas.port.pm.qa.IOAType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.qa.IOAType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("398C90AE"));
    }
}