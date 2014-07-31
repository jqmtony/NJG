package com.kingdee.eas.port.pm.invest.investplan;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProgrammingEntryFactory
{
    private ProgrammingEntryFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invest.investplan.IProgrammingEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IProgrammingEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7D5CF726") ,com.kingdee.eas.port.pm.invest.investplan.IProgrammingEntry.class);
    }
    
    public static com.kingdee.eas.port.pm.invest.investplan.IProgrammingEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IProgrammingEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7D5CF726") ,com.kingdee.eas.port.pm.invest.investplan.IProgrammingEntry.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invest.investplan.IProgrammingEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IProgrammingEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7D5CF726"));
    }
    public static com.kingdee.eas.port.pm.invest.investplan.IProgrammingEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IProgrammingEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7D5CF726"));
    }
}