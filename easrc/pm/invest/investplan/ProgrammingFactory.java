package com.kingdee.eas.port.pm.invest.investplan;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProgrammingFactory
{
    private ProgrammingFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invest.investplan.IProgramming getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IProgramming)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1D602CAC") ,com.kingdee.eas.port.pm.invest.investplan.IProgramming.class);
    }
    
    public static com.kingdee.eas.port.pm.invest.investplan.IProgramming getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IProgramming)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1D602CAC") ,com.kingdee.eas.port.pm.invest.investplan.IProgramming.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invest.investplan.IProgramming getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IProgramming)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1D602CAC"));
    }
    public static com.kingdee.eas.port.pm.invest.investplan.IProgramming getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IProgramming)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1D602CAC"));
    }
}