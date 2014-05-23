package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CostTempFactory
{
    private CostTempFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invest.ICostTemp getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.ICostTemp)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7130F81E") ,com.kingdee.eas.port.pm.invest.ICostTemp.class);
    }
    
    public static com.kingdee.eas.port.pm.invest.ICostTemp getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.ICostTemp)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7130F81E") ,com.kingdee.eas.port.pm.invest.ICostTemp.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invest.ICostTemp getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.ICostTemp)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7130F81E"));
    }
    public static com.kingdee.eas.port.pm.invest.ICostTemp getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.ICostTemp)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7130F81E"));
    }
}