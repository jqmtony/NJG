package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PreprojectE1Factory
{
    private PreprojectE1Factory()
    {
    }
    public static com.kingdee.eas.port.pm.invest.IPreprojectE1 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IPreprojectE1)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("AF100E7F") ,com.kingdee.eas.port.pm.invest.IPreprojectE1.class);
    }
    
    public static com.kingdee.eas.port.pm.invest.IPreprojectE1 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IPreprojectE1)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("AF100E7F") ,com.kingdee.eas.port.pm.invest.IPreprojectE1.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invest.IPreprojectE1 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IPreprojectE1)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("AF100E7F"));
    }
    public static com.kingdee.eas.port.pm.invest.IPreprojectE1 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IPreprojectE1)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("AF100E7F"));
    }
}