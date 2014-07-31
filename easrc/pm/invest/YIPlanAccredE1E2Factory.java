package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class YIPlanAccredE1E2Factory
{
    private YIPlanAccredE1E2Factory()
    {
    }
    public static com.kingdee.eas.port.pm.invest.IYIPlanAccredE1E2 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IYIPlanAccredE1E2)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2BD69B7F") ,com.kingdee.eas.port.pm.invest.IYIPlanAccredE1E2.class);
    }
    
    public static com.kingdee.eas.port.pm.invest.IYIPlanAccredE1E2 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IYIPlanAccredE1E2)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2BD69B7F") ,com.kingdee.eas.port.pm.invest.IYIPlanAccredE1E2.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invest.IYIPlanAccredE1E2 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IYIPlanAccredE1E2)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2BD69B7F"));
    }
    public static com.kingdee.eas.port.pm.invest.IYIPlanAccredE1E2 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IYIPlanAccredE1E2)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2BD69B7F"));
    }
}