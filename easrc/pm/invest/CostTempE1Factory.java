package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CostTempE1Factory
{
    private CostTempE1Factory()
    {
    }
    public static com.kingdee.eas.port.pm.invest.ICostTempE1 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.ICostTempE1)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E8D3712A") ,com.kingdee.eas.port.pm.invest.ICostTempE1.class);
    }
    
    public static com.kingdee.eas.port.pm.invest.ICostTempE1 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.ICostTempE1)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E8D3712A") ,com.kingdee.eas.port.pm.invest.ICostTempE1.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invest.ICostTempE1 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.ICostTempE1)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E8D3712A"));
    }
    public static com.kingdee.eas.port.pm.invest.ICostTempE1 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.ICostTempE1)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E8D3712A"));
    }
}