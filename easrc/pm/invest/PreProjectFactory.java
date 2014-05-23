package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PreProjectFactory
{
    private PreProjectFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invest.IPreProject getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IPreProject)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("22677E93") ,com.kingdee.eas.port.pm.invest.IPreProject.class);
    }
    
    public static com.kingdee.eas.port.pm.invest.IPreProject getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IPreProject)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("22677E93") ,com.kingdee.eas.port.pm.invest.IPreProject.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invest.IPreProject getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IPreProject)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("22677E93"));
    }
    public static com.kingdee.eas.port.pm.invest.IPreProject getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IPreProject)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("22677E93"));
    }
}