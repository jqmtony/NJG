package com.kingdee.eas.port.pm.invest.investplan;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProgrammingEntryEconomyEntryFactory
{
    private ProgrammingEntryEconomyEntryFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invest.investplan.IProgrammingEntryEconomyEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IProgrammingEntryEconomyEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D1CC0B1A") ,com.kingdee.eas.port.pm.invest.investplan.IProgrammingEntryEconomyEntry.class);
    }
    
    public static com.kingdee.eas.port.pm.invest.investplan.IProgrammingEntryEconomyEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IProgrammingEntryEconomyEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D1CC0B1A") ,com.kingdee.eas.port.pm.invest.investplan.IProgrammingEntryEconomyEntry.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invest.investplan.IProgrammingEntryEconomyEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IProgrammingEntryEconomyEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D1CC0B1A"));
    }
    public static com.kingdee.eas.port.pm.invest.investplan.IProgrammingEntryEconomyEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IProgrammingEntryEconomyEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D1CC0B1A"));
    }
}