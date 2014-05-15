package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PreprojectFactory
{
    private PreprojectFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invest.IPreproject getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IPreproject)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BF2EE6B3") ,com.kingdee.eas.port.pm.invest.IPreproject.class);
    }
    
    public static com.kingdee.eas.port.pm.invest.IPreproject getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IPreproject)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BF2EE6B3") ,com.kingdee.eas.port.pm.invest.IPreproject.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invest.IPreproject getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IPreproject)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BF2EE6B3"));
    }
    public static com.kingdee.eas.port.pm.invest.IPreproject getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IPreproject)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BF2EE6B3"));
    }
}