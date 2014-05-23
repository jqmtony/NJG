package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class YIPlanAccredFactory
{
    private YIPlanAccredFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invest.IYIPlanAccred getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IYIPlanAccred)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5FA92066") ,com.kingdee.eas.port.pm.invest.IYIPlanAccred.class);
    }
    
    public static com.kingdee.eas.port.pm.invest.IYIPlanAccred getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IYIPlanAccred)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5FA92066") ,com.kingdee.eas.port.pm.invest.IYIPlanAccred.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invest.IYIPlanAccred getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IYIPlanAccred)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5FA92066"));
    }
    public static com.kingdee.eas.port.pm.invest.IYIPlanAccred getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IYIPlanAccred)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5FA92066"));
    }
}