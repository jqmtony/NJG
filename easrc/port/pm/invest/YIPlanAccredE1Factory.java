package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class YIPlanAccredE1Factory
{
    private YIPlanAccredE1Factory()
    {
    }
    public static com.kingdee.eas.port.pm.invest.IYIPlanAccredE1 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IYIPlanAccredE1)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("19E2A772") ,com.kingdee.eas.port.pm.invest.IYIPlanAccredE1.class);
    }
    
    public static com.kingdee.eas.port.pm.invest.IYIPlanAccredE1 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IYIPlanAccredE1)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("19E2A772") ,com.kingdee.eas.port.pm.invest.IYIPlanAccredE1.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invest.IYIPlanAccredE1 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IYIPlanAccredE1)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("19E2A772"));
    }
    public static com.kingdee.eas.port.pm.invest.IYIPlanAccredE1 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IYIPlanAccredE1)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("19E2A772"));
    }
}