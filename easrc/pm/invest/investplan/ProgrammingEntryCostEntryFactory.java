package com.kingdee.eas.port.pm.invest.investplan;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProgrammingEntryCostEntryFactory
{
    private ProgrammingEntryCostEntryFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invest.investplan.IProgrammingEntryCostEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IProgrammingEntryCostEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4F1A141F") ,com.kingdee.eas.port.pm.invest.investplan.IProgrammingEntryCostEntry.class);
    }
    
    public static com.kingdee.eas.port.pm.invest.investplan.IProgrammingEntryCostEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IProgrammingEntryCostEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4F1A141F") ,com.kingdee.eas.port.pm.invest.investplan.IProgrammingEntryCostEntry.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invest.investplan.IProgrammingEntryCostEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IProgrammingEntryCostEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4F1A141F"));
    }
    public static com.kingdee.eas.port.pm.invest.investplan.IProgrammingEntryCostEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IProgrammingEntryCostEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4F1A141F"));
    }
}